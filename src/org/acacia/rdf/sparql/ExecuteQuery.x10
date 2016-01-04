/**
Copyright 2015 Acacia Team

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.acacia.rdf.sparql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import x10.util.ArrayList;
import x10.util.HashMap;
import x10.util.HashSet;
import x10.util.Map;
import x10.lang.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Copyright 2015 Acacia Team

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */import org.acacia.localstore.AcaciaHashMapNativeStore;


import org.acacia.util.Utils;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.acacia.rdf.sparql.java.SparqlLexer;
import org.acacia.rdf.sparql.java.SparqlParser;
//import org.antlr.runtime.RecognitionException;


//import scala.Console;

//import org.antlr.runtime.RecognitionException;

public class ExecuteQuery {	
	private var Prefix:HashMap[String, String]= new HashMap[String, String]();	
	private var graphData:ArrayList[String] = new ArrayList[String]();
    private var ontologyFilePath:String = null;

	public def executeQuery(var query:String, var graphID:String, var partitionID:String, var placeID:String) : ArrayList[String] {		
		var stream:ANTLRStringStream = new ANTLRStringStream(query);
		var lexer:SparqlLexer = new SparqlLexer(stream);
		var tokenStream:CommonTokenStream = new CommonTokenStream(lexer);
		var parser:SparqlParser = new SparqlParser(tokenStream);
		var index:Int = 0n;
		var result:ArrayList[String] = new ArrayList[String]();
		try {
			parser.query();
		
			// extract triples from the query
			var triples:Rail[String] = extractTriples(query);
			// get the data set
			
			loadData(graphID, partitionID, placeID);
			index = query.indexOf("SELECT");

			if (index >= 0) {
				// get the results of select query
 				var select:SelectQueryExecution=new SelectQueryExecution();
				result.addAll(select.executeSelect(query, graphID,partitionID,placeID));
			}
		}catch (val e:org.antlr.runtime.RecognitionException) {
			Console.OUT.println("Error in query format"+e);
		}

		return result;
	}

	/**
     * read nodes,relationship,properties 
     */
	public def extractTriples(val query:String):Rail[String]{
		 var index:Int = 0n;
		 var index2:Int = 0n;
		 var prefix:Rail[String] = null;
		 var key:String = null;
		 var value:String = null;
		 var triples:Rail[String] = null;
		 
		 prefix=query.substring(0n,query.indexOf("SELECT")).trim().split("PREFIX");
		 		 
		 for(var i:Int = 0n; i < prefix.size; i++){
		 	if(prefix(i).length() > 0n){
				key=prefix(i).trim().substring(0n,prefix(i).indexOf(":")-1n).trim();
			 	value=prefix(i).trim().substring(prefix(i).indexOf("<"),prefix(i).indexOf(">")-1n).trim();
			 	Prefix.put(key,value);
		 	} 
		 }
		 
		 index=query.indexOf("{");  // simple query
		 index2=query.indexOf("}");		 
		 
		
		 // ; also should be considered 
		 
		 triples=query.substring(index+1n,index2).trim().split(" .  ");  
		 
		 return triples;
	 }

	// load data
	public def loadData(val graphID:String, val partitionID:String, val placeID:String) : void {		
		val baseDir:String = Utils.getAcaciaProperty("org.acacia.server.instance.datafolder");

		// /var/tmp/acad-localstore
		//native store
		val nativeStore:AcaciaHashMapNativeStore = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), baseDir, false);
        //We will store the ontology on both local store as well as on the central store. But we will access the ontology file from the local store in this case.
        ontologyFilePath = nativeStore.getOntologyFilePath();
		getData(nativeStore, 0n);
		
		//central store
		val centralStore:AcaciaHashMapNativeStore = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(placeID), baseDir, true, Int.parseInt(placeID));
		getData(centralStore, 1n);
	}
	
	public def getData(val store:AcaciaHashMapNativeStore, val no:Int) : void{
		store.loadGraph();
		
		val localSubGraphMap:HashMap[Long, HashSet[Long]] 					 = store.getlocalSubGraphMap();
		val vertexPropertyMap:HashMap[Long, HashSet[String]] 				 = store.getvertexPropertyMap();
		val relationshipMapWithProperties:Rail[HashMap[Long, HashSet[Long]]] = store.getrelationshipMapWithProperties();
		val attributeMap:HashMap[Long, HashMap[Int, HashSet[String]]]	 	 = store.getattributeMap();
		val predicateStore:HashMap[Int, String]		 						 = store.getpredicateStore();
		
		//graph data should be aggregated and added to 'data'
		val predicateCount:Int = store.getPredicateCount();	
		
		for(var i:Int=0n; i < predicateCount; i++){
			val hMap:HashMap[Long, HashSet[Long]] = relationshipMapWithProperties(i);
			var predicate:String = null;
			
			if(hMap != null){				
				predicate = predicateStore.get(i);
				
				if(predicate == null){
					continue;
				}
			}else{
				continue;
			}
						
			val itr:Iterator[Map.Entry[Long, HashSet[Long]]] = hMap.entries().iterator();

			while(itr.hasNext()){
				val item:Map.Entry[Long, HashSet[Long]] = itr.next();
				val startVertexID:Long = item.getKey();
				val endVertices:HashSet[Long] = item.getValue();
				val firstHs:HashSet[String] = vertexPropertyMap.get(startVertexID);

				if(firstHs != null){
 					var itrHs:Iterator[String] = firstHs.iterator();
					val startVertexPropertyValue:String = itrHs.next();
					var itr2:Iterator[Long] = endVertices.iterator();
					while( itr2.hasNext() ){
                        		var endVertexID:Long = itr2.next();
						val secondHs:HashSet[String] = vertexPropertyMap.get(endVertexID);

						if(secondHs != null){
 							itrHs = secondHs.iterator();
							val endVertexPropertyValue:String = itrHs.next();;
							graphData.add(startVertexPropertyValue + "," + predicate + "," + endVertexPropertyValue);
							//data.add(startVertexPropertyValue + " " + predicate + " " + endVertexPropertyValue);								
							//System.out.println(startVertexPropertyValue + "," + predicate + "," + endVertexPropertyValue);				
							//System.out.println(startVertexPropertyValue + " " + predicate + " " + endVertexPropertyValue);
						}
					}
				}
			}
		}	
		
		
		
		}
	
	// this is a test class for executing query
		public def selectQuery(val graphData:ArrayList[String], val triples:Rail[String]):ArrayList[String]{
	 		 var intermediateResults:ArrayList[ArrayList[String]]= null;
	         val Results:ArrayList[String]= new ArrayList[String]();  			
	 		 var n:Int=0n;
	 				
		     intermediateResults= execute(triples, graphData);
			
			//AND results
			//considering only one variable  (?x - -)

			if(intermediateResults.size() == 1){
				
				for(var i:Int=0n; i < intermediateResults.get(0).size(); i++){
					var arr1:Rail[String] = intermediateResults.get(n).get(i).trim().split(",");					
				
					if(!Results.contains(arr1(0))){
	                    Results.add(arr1(0));
					}
				}
			}else{
				for(var i:Int=0n; i < intermediateResults.get(n).size(); i++){
					var arr1:Rail[String]=intermediateResults.get(n).get(i).trim().split(",");
				
					for(var j:Int=0n; j < intermediateResults.get(n+1n).size(); j++){
						var arr2:Rail[String]=intermediateResults.get(n+1n).get(j).trim().split(",");

						if(arr1(0).equals(arr2(0))){
						if(!Results.contains(arr1(0))){
	                        Results.add(arr1(0));
	               			
						}
					}
	              }	
			}
				
			}
	 		
			return Results;
		}


	public def execute(val triples:Rail[String], val graphData:ArrayList[String]) : ArrayList[ArrayList[String]] {

		var intermediateResults:ArrayList[ArrayList[String]] = new ArrayList[ArrayList[String]]();
		var flag:Int=0n;
		var token:String = null;
		var newToken:ArrayList[String] = null;
		
		for (var i:Int = 0n; i < triples.size; i++) {
			var result:ArrayList[String] = null;
			flag=0n;
			// get the tokens of the triples
			var tokens:Rail[String] = triples(i).trim().split(" ");

			// predicate
			
			if(tokens(1).indexOf("type") > -1n){
					flag=1n;				
			}
			
			token=tokens(2);
			tokens(1) = Prefix.get(tokens(1).substring(0n, tokens(1).indexOf(":")).trim()) + tokens(1).substring(tokens(1).indexOf(":") + 1n).trim();
			
			if (tokens(0).indexOf("?") >= 0n) {
				if (tokens(2).indexOf("?") >= 0n) {
					
					result=triplePattern1(tokens,graphData);
					if(flag == 1n && result.isEmpty()){
						
						// read rdf ontology file
						newToken=readRDFOntologyFile(token.substring(tokens(1).indexOf(":") + 1n),ontologyFilePath);
						
						for(var j:Int=0n;j<newToken.size();j++){
							tokens(2) = tokens(2).substring(0n, tokens(2).indexOf("#")+1n)+newToken.get(j);
					 		intermediateResults.add(triplePattern1(tokens,graphData));
						}
					}
					else {
						intermediateResults.add(result);
					}
				} else {
					if (!tokens(2).startsWith("<http")) {
						tokens(2) = Prefix.get(tokens(2).substring(0n, tokens(2).indexOf(":")).trim()) + tokens(2).substring(tokens(2).indexOf(":") + 1n).trim();
					} else {
						tokens(2) = tokens(2).substring(1n, tokens(2).length()-1n);
					}
					
					result=triplePattern2(tokens,graphData);
					
					if(flag == 1n && result.isEmpty()){
						// read rdf ontology file
						newToken=readRDFOntologyFile(token.substring(token.indexOf(":")+1n),ontologyFilePath);
						
						for(var j:Int=0n; j < newToken.size(); j++){						
							tokens(2) = tokens(2).substring(0n, tokens(2).indexOf("#")+1n)+newToken.get(j);
                            Console.OUT.println("new token2"+tokens(2));
					 		intermediateResults.add(triplePattern2(tokens,graphData));
						}
					}
					else {
						intermediateResults.add(result);
					}
				}
			} else {
				tokens(0) = Prefix.get(tokens(0).substring(0n, tokens(0).indexOf(":")).trim()) + tokens(0).substring(tokens(0).indexOf(":") + 1n).trim();

				if (tokens(2).indexOf("?") >= 0n) {
					result = triplePattern3(tokens,graphData);

					if(flag == 1n && result.isEmpty()){
						// read rdf ontology file
						newToken=readRDFOntologyFile(token.substring(tokens(1).indexOf(":") + 1n), ontologyFilePath);
						
						for(var j:Int=0n; j < newToken.size(); j++){
							tokens(2) = tokens(2).substring(0n, tokens(2).indexOf("#") + 1n)+newToken.get(j);
							intermediateResults.add(triplePattern3(tokens,graphData));
						}
					}
					else {
						intermediateResults.add(result);
					}					
				} else {
					tokens(2) = Prefix.get(tokens(2).substring(0n, tokens(2).indexOf(":")).trim()) + tokens(2).substring(tokens(2).indexOf(":") + 1n).trim();
					 
					result = triplePattern4(tokens,graphData);

					if(flag == 1n && result.isEmpty()){					 
						// read rdf ontology file
						newToken=readRDFOntologyFile(token.substring(tokens(1).indexOf(":") + 1n), ontologyFilePath);
						
						for(var j:Int = 0n; j < newToken.size(); j++){
							tokens(2) = tokens(2).substring(0n, tokens(2).indexOf("#") + 1n)+newToken.get(j);
						    intermediateResults.add(triplePattern4(tokens,graphData));
						}
					}
					else {
						intermediateResults.add(result);
					}
				}
			}
		}
		return intermediateResults;
	}

	public def readRDFOntologyFile(val token:String, val ontologyFilePath:String) : ArrayList[String]{
		var line:String = null;
        var nextline:String = null;
		var newtokens:ArrayList[String] =new ArrayList[String]();
		var newToken:String = null;
		
		try {

			/*File file = new File(filename);
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			if (doc.hasChildNodes()) {

				//printNote(doc.getChildNodes());

			}*/
			
			
			var fileReader:FileReader = new FileReader(ontologyFilePath);
	        var bufferedReader:BufferedReader =  new BufferedReader(fileReader);
	            
	        while((line = bufferedReader.readLine()) != null) {    	
	            	if(line.indexOf(token) > -1n){	            		
	            		while((nextline = bufferedReader.readLine())!= null){
	            			if(nextline.indexOf("rdfs:subClassOf rdf:resource=\"#") > -1n){
	            				newToken=nextline.substring(line.indexOf("=")+2n, line.indexOf(">")-1n);
	            				Console.OUT.print(newToken);
	            				newtokens.add(newToken);
	            				break;
	            			}
	            		}
	            		break;		
	            	}
	            }
	        
	            bufferedReader.close();
        } catch (var e1:java.io.IOException) {
            Console.OUT.println(e1.getMessage());
		} catch (var e:Exception) {
			Console.OUT.println(e.getMessage());
		}

		return newtokens;
	}
	
	// (?X , name, ?N)
	public def triplePattern1(val tokens:Rail[String], val graphData:ArrayList[String]) : ArrayList[String] {
		var temp:ArrayList[String] = new ArrayList[String]();
		// only predicate should be considered

		for (var i:Int = 0n; i < graphData.size(); i++) {
			if (graphData.get(i).indexOf(tokens(1)) > -1n) {
				temp.add( graphData.get(i));
			}
		}
		return temp;
	}

	// (?X , name, "John")
	public def triplePattern2(var tokens:Rail[String], var graphData:ArrayList[String]) : ArrayList[String] {
		var temp:ArrayList[String] = new ArrayList[String]();
		// predicate and object should be considered
		
		for (var i:Int = 0n; i < graphData.size(); i++) {
			if (graphData.get(i).indexOf(tokens(1)) > -1n){
					if(graphData.get(i).indexOf(tokens(2)) > -1n) {
						temp.add(graphData.get(i));
					}
			}
		}
		return temp;
	}

	// (d:100 , name, ?N)
	public def triplePattern3(var tokens:Rail[String], var graphData:ArrayList[String]) : ArrayList[String] {
		var temp:ArrayList[String] = new ArrayList[String]();
		// subject and predicate should be considered

		for (var i:Int = 0n; i < graphData.size(); i++) {
			if ((graphData.get(i).indexOf(tokens(0)) > -1n) && (graphData.get(i).indexOf(tokens(1)) > -1n)) {
				temp.add(graphData.get(i));
			}
		}
		return temp;
	}

	// (d:100 , name, "John")
	public def triplePattern4(var tokens:Rail[String], var graphData:ArrayList[String]) : ArrayList[String]{
		var temp:ArrayList[String] = new ArrayList[String]();
		// subject, predicate and object should be considered

		for (var i:Int = 0n; i < graphData.size(); i++) {
			if (	(graphData.get(i).indexOf(tokens(0)) > -1n) 
					&& (graphData.get(i).indexOf(tokens(1)) > -1n)
					&& (graphData.get(i).indexOf(tokens(2)) > -1n)) {
				temp.add(graphData.get(i));
			}
		}
		return temp;
	}
}
