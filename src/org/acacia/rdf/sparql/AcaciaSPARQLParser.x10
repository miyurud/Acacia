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

import x10.io.File;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.acacia.log.java.Logger_Java;
import org.acacia.util.Utils;

public class AcaciaSPARQLParser {


public def this(){}

public def Parsing(val query:String, val graphID:String):String{
	//SELECT ?name WHERE { ?person foaf:name ?name .} 
	//PREFIX ab: <http://learningsparql.com/ns/addressbook#> SELECT ?x WHERE {?x ab:name ?n . }
		var stream :ANTLRStringStream  =new ANTLRStringStream(query);			
		var lexer: SparqlLexer  =new SparqlLexer(stream);
		var tokenStream: CommonTokenStream =new CommonTokenStream(lexer);
		var parser: SparqlParser =new SparqlParser(tokenStream);
	     
	    var result:String="OK";
	    var index:Int;
		var path:String=Utils.call_getAcaciaProperty("org.acacia.server.runtime.location")+"/Sparql/data_"+graphID;
		var k:Int=0n;
	
		try {
	        	parser.query(); 
	        var triples:Rail[String]=extractTripples(query);
	        
	        var file:File = new File(path);
	        var graphData:Rail[String]=new Rail[String](file.size());
	        
	        for (line in file.lines()) {
	        graphData(k)=line;
	       
	        	k=k+1n;
	        
	        }
	            index=query.indexOf("SELECT");
	           
	            if(index>=0){
	            
	            selectQuery(graphData, triples);
	           
	            }
	        
       }catch (val e:RecognitionException) {
			// TODO Auto-generated catch block
       		result="Error while parsing: " + e.getMessage();
			Logger_Java.error(result);
		}
	       
		return result;
	}
	
	// this is a test class for executing query
	public def selectQuery(graphData:Rail[String], triples:Rail[String]):void{
			var tokens:Rail[String];
	        var intermediateResults:Rail[Rail[String]]=new Rail[Rail[String]](6); //no?
	        var index:Int=0n;
			
	        for(var i:Int=0n; i < triples.size-2; i++){
				tokens=triples(i).trim().split(" ");
			
				if(tokens(0).indexOf("?")>=0){
					
					if(tokens(2).indexOf("?")>=0){
						intermediateResults(index)=triplePattern1(tokens,graphData);
					}else{
						intermediateResults(index)=triplePattern2(tokens,graphData);
					}
				}else{
					if(tokens(2).indexOf("?")>=0){
						intermediateResults(index)=triplePattern3(tokens,graphData);
					}else{
							intermediateResults(index)=triplePattern4(tokens,graphData);
					}
				}
				
	            index=index+1n;
			}
					
			//AND results
					
			for(var i:Int=0n; i < intermediateResults.size-1; i++){
				Console.OUT.println(intermediateResults(i)(0));
			}
	}
	
	//read nodes,relationship,properties
	
	 public def extractTripples(query:String):Rail[String]{
			 var triples:Rail[String]=new Rail[String](6);// no?
			 var index:Int ;
			 
			 index=query.indexOf("{");  // simple query
			 Console.OUT.println(index);
			 
			 // ; also should be considered 
			 triples=query.substring(index+1n).trim().split(".");  //error
			 return triples;
	 }	
	
	//(?X , name, ?N)
	public def triplePattern1(tokens:Rail[String], graphData:Rail[String]):Rail[String]{
	
			var temp:Rail[String]=new Rail[String](6); //no?
			var k:Int=0n;
			
			//only predicate should be considered
			
			for(var i:Int=0n; i < graphData.size-1; i++){		
				if(graphData(i).indexOf(tokens(1).substring(3n))>=0){
					temp(k)=graphData(i);
					k=k+1n;
				}		
			}
			return temp;
	
	}
	
	//(?X , name, "John")
	public def triplePattern2(tokens:Rail[String], graphData:Rail[String]):Rail[String]{
	
			var temp:Rail[String]=new Rail[String](6); //no?
			var k:Int=0n;
			
			//predicate and object should be considered
			
			for(var i:Int=0n; i < graphData.size-1; i++){
				if(graphData(i).indexOf(tokens(1).substring(3n))>=0 && graphData(i).indexOf(tokens(2))>=0){
					temp(k)=graphData(i);
					k=k+1n;
				}
			}
			return temp;
	
	}
	
	//(d:100 , name, ?N)
	public def triplePattern3(tokens:Rail[String], graphData:Rail[String]):Rail[String]{
	
			var temp:Rail[String]=new Rail[String](6); //no?
			var k:Int=0n;
			
			//subject and predicate should be considered
			
			for(var i:Int=0n; i < graphData.size-1; i++){
				if(graphData(i).indexOf(tokens(0))>=0 && graphData(i).indexOf(tokens(1).substring(3n))>=0){		
					temp(k)=graphData(i);
					k=k+1n;
				}
			}
			return temp;
	}
	
	//(d:100 , name, "John")
	public def triplePattern4(tokens:Rail[String], graphData:Rail[String]):Rail[String]{
	
			var temp:Rail[String]=new Rail[String](6); //no?
			var k:Int=0n;
			
			//subject, predicate and object should be considered
			
			for(var i:Int=0n; i < graphData.size-1; i++){
				if(graphData(i).indexOf(tokens(0))>=0 && graphData(i).indexOf(tokens(1).substring(3n))>=0 && graphData(i).indexOf(tokens(2))>=0){
					temp(k)=graphData(i);
					k=k+1n;
				}
			}
	
			return temp;
	}
}