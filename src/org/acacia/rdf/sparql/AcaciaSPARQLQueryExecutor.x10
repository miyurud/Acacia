package org.acacia.rdf.sparql;

import x10.io.File;
import x10.compiler.Native;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.acacia.log.java.Logger_Java;
import org.acacia.util.Utils;
import org.acacia.util.PlaceToNodeMapper;
import x10.util.HashMap;
import org.acacia.server.AcaciaServer;
import java.net.Socket;
//import java.io.PrintWriter;
//import java.io.BufferedReader;

import x10.util.ArrayList;

public class AcaciaSPARQLQueryExecutor {

		var Prefix:HashMap[String, String] = new HashMap[String, String]();

	public def this(){}

/*private static def getLiveHostIDList():HashMap[String, String]{

		val hostNameArr:Rail[String] = call_runSelect("SELECT name,idhost FROM ACACIA_META.HOST");
		val result:HashMap[String, String] = new HashMap[String, String]();
		var hosrArrlist:ArrayList[String] = new ArrayList[String](); 
		var nm:String = null;
		val hosts:Rail[String] = org.acacia.util.Utils.getPrivateHostList();
		val hostListLen:Int = hosts.size as Int;
		var intermRes:Rail[Long] = new Rail[Long](hostListLen);


		for(var i:Int=0n; i < hostListLen; i++){
		hosrArrlist.add(hosts(i));
		}

		val l:Long = hostNameArr.size;

		for(var i:Long=0; i < l; i++){
		val itm:Rail[String] =  hostNameArr(i).split(",");
		
			if(hosrArrlist.contains(itm(0))){
			result.put(itm(0), itm(1));
			
			}

}

return result;
}*/

	public def executeQuery(val query:String, val graphID:String, val partitionID:String):ArrayList[String]{

		var stream : ANTLRStringStream  =new ANTLRStringStream(query);			
		var lexer: SparqlLexer  =new SparqlLexer(stream);
		var tokenStream: CommonTokenStream =new CommonTokenStream(lexer);
		var parser: SparqlParser =new SparqlParser(tokenStream);     
        var index:Int;
        var result:ArrayList[String]=null;
		var k:Int=0n;
        //val filePath:String = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + "/" + graphID + "_" + partitionID;

		try {
	        parser.query(); 
	        //extract triples from the query
	        var triples:Rail[String]=extractTripples(query);	
	        //get the data set
	        var graphData:ArrayList[String] = new ArrayList[String]();	
	        graphData=loadData();
	       
	        index=query.indexOf("SELECT");
	        
	        if(index>=0){
	            //get the results of select query
	        	result=selectQuery(graphData, triples);	           
	            }	        
       }catch (val e:RecognitionException) {
			// TODO Auto-generated catch block
			Logger_Java.error("Error while parsing: " + e.getMessage());		
		}	
       
		return result;

	}

	//load data
	public def loadData():ArrayList[String]{

		var data:ArrayList[String] = new ArrayList[String]();

		var inputpath:String="/home/yasima/Acacia/x10dt/workspace/Acacia/data";
		val input:File = new File(inputpath);
		val i = input.openRead();
		for (line in i.lines()){

			data.add(line);


		}


		/*data.add("(R1 , name, john)");
		data.add("(R1 , email, J@ed.ex)");
		data.add("(R2 , name, paul)");
		data.add("(R3 , name, ringo)");
		data.add("(R3 , email, R@ed.ex)");
		data.add("(R3 , webPage, www.ringo.com)");*/
		
		return data;

	}

	// this is a test class for executing query
	public def selectQuery(graphData:ArrayList[String], triples:Rail[String]):ArrayList[String]{

		var tokens:Rail[String]; 		
 		var intermediateResults:ArrayList[ArrayList[String]]=new ArrayList[ArrayList[String]](); 
        var Results:ArrayList[String] = new ArrayList[String]();  	
 		var index:Int=0n;
 		var k:Int=0n;
 		var variables:ArrayList[String] = new ArrayList[String](); 
 
 
 //communicate with workers 
 
 //get place ids
		 /*val itr:Iterator[Place] = Place.places().iterator();
		 val placeToHostMap:HashMap[Long, String] = new HashMap[Long, String]();
		 
		 while(itr.hasNext()){
		 val p:Place = itr.next();		 
		 val hostName:String = PlaceToNodeMapper.getHost(p.id);		
		 
		 placeToHostMap.put(p.id, hostName);
		 
		 }
		 
		 var itr2:Iterator[x10.util.Map.Entry[Long, String]] = placeToHostMap.entries().iterator();
		 val hostIDMap:HashMap[String, String] = getLiveHostIDList();
		 
		 while(itr2.hasNext()){
		 val itemHost:x10.util.Map.Entry[Long, String] = itr2.next();
		 if(itemHost!=null){	
		 
		 async{
		 
		 call_connect(itemHost.getValue(),PlaceToNodeMapper.getInstancePort(itemHost.getKey()));
		 
		 }
		 
		 }
		 
		 }*/
	 
 
 
		for(var i:Int=0n; i < triples.size; i++){
 			
			// get the tokens of the triples
			tokens=triples(i).trim().split(" ");			
				
			//predicate

			tokens(1)=Prefix.get(tokens(1).substring(0n,tokens(1).indexOf(":")).trim())+tokens(1).substring(tokens(1).indexOf(":")+1n).trim();		

		

			if(tokens(0).indexOf("?")>=0){	
				if(tokens(2).indexOf("?")>=0){
					intermediateResults.set(triplePattern1(tokens,graphData),index);
				}
				else{
				
					if(!tokens(2).startsWith("<http")){
						tokens(2)=Prefix.get(tokens(2).substring(0n,tokens(2).indexOf(":")).trim())+tokens(2).substring(tokens(2).indexOf(":")+1n).trim();
					}
					else{
						tokens(2)=tokens(2).substring(1n,tokens(2).length());
					}
				
					intermediateResults.set(triplePattern2(tokens,graphData),index);
				}
			}
			else{
				tokens(0)=Prefix.get(tokens(0).substring(0n,tokens(0).indexOf(":")).trim())+tokens(0).substring(tokens(0).indexOf(":")+1n).trim();	
				if(tokens(2).indexOf("?")>=0){	
					intermediateResults.set(triplePattern3(tokens,graphData),index);
				}
				else{
					tokens(2)=Prefix.get(tokens(2).substring(0n,tokens(2).indexOf(":")).trim())+tokens(2).substring(tokens(2).indexOf(":")+1n).trim();
					intermediateResults.set(triplePattern4(tokens,graphData),index);
				}
 			}
			index=index+1n;
						
		}
				
		//AND results
		//considering only one variable  (?x - -)
		var n:Int=0n;

		for(var i:Int=0n; i < intermediateResults.get(n).size(); i++){

			var arr1:Rail[String]=intermediateResults.get(n).get(i).trim().split(",");

			for(var j:Int=0n; j < intermediateResults.get(n+1n).size(); j++){
				
				var arr2:Rail[String]=intermediateResults.get(n+1n).get(j).trim().split(",");

				if(arr1(0).equals(arr2(0))){

					if(!Results.contains(arr1(0))){
                        Results.set(arr1(0),k);
               			k=k+1n;
					}
				}
              }

								
		}

 		
	return Results;
	
	}

	//read nodes,relationship,properties
 	public def extractTripples(query:String):Rail[String]{
 
		 var triples:Rail[String];		 
		 var index:Int;
		 var index2:Int ;
		 var prefix:Rail[String];
		 var key:String;
		 var value:String;
		 
		 prefix=query.substring(0n,query.indexOf("SELECT")).trim().split("PREFIX");
		 		 
		 for(var i:Int=0n; i < prefix.size; i++){
		 
		 if(prefix(i).length()>0){
			 key=prefix(i).trim().substring(0n,prefix(i).indexOf(":")-1n).trim();
			 value=prefix(i).trim().substring(prefix(i).indexOf("<"),prefix(i).indexOf(">")-1n).trim();
			 Prefix.put(key,value);
		 }
			 
		 }
		 index=query.indexOf("{");  // simple query
		 index2=query.indexOf("}");		 
		 
		 // ; also should be considered 
		 triples=query.substring(index+1n,index2).trim().split(" .");  		 
		 return triples;
	 }



	//(?X , name, ?N)
	public def triplePattern1(tokens:Rail[String], graphData:ArrayList[String]):ArrayList[String]{

		var temp:ArrayList[String] = new ArrayList[String]();
		var k:Int=0n;
		
		//only predicate should be considered
		
		for(var i:Int=0n; i < graphData.size(); i++){
		
			if(graphData.get(i).indexOf(tokens(1))>=0){
		
				temp.set(graphData.get(i),k);
				k=k+1n;
			}		
		}
		return temp;

	}

	//(?X , name, "John")
	public def triplePattern2(tokens:Rail[String], graphData:ArrayList[String]):ArrayList[String]{

		var temp:ArrayList[String] = new ArrayList[String]();
		var k:Int=0n;
		
		//predicate and object should be considered
//Console.OUT.println(tokens(2));
		
		for(var i:Int=0n; i < graphData.size(); i++){
		
			if(graphData.get(i).indexOf(tokens(1))>=0 && graphData.get(i).indexOf(tokens(2).substring(1n,tokens(2).length()-1n))>=0){
		
				temp.set(graphData.get(i),k);
				k=k+1n;
			}
		
		}
		return temp;

	}

	//(d:100 , name, ?N)
	public def triplePattern3(tokens:Rail[String], graphData:ArrayList[String]):ArrayList[String]{

		var temp:ArrayList[String] = new ArrayList[String]();
		var k:Int=0n;
		
		//subject and predicate should be considered
		
		for(var i:Int=0n; i < graphData.size(); i++){
		
			if(graphData.get(i).indexOf(tokens(0))>=0 && graphData.get(i).indexOf(tokens(1))>=0){
		
				temp.set(graphData.get(i),k);
				k=k+1n;
			}
		
		}
		return temp;
	}

	//(d:100 , name, "John")
	public def triplePattern4(tokens:Rail[String], graphData:ArrayList[String]):ArrayList[String]{

		var temp:ArrayList[String] = new ArrayList[String]();
		var k:Int=0n;
		
		//subject, predicate and object should be considered
		
		for(var i:Int=0n; i < graphData.size(); i++){
		
			if(graphData.get(i).indexOf(tokens(0))>=0 && graphData.get(i).indexOf(tokens(1))>=0 && graphData.get(i).indexOf(tokens(2).substring(1n,tokens(2).length()-1n))>=0){
		
				temp.set(graphData.get(i),k);
				k=k+1n;
			}
		
		}
		return temp;
	}

//@Native("java", "org.acacia.metadata.db.java.MetaDataDBInterface.runSelect(#1)")
//static native def call_runSelect(String):Rail[String];

//@Native("java", "org.acacia.rdf.sparql.ConnectWorkers.connect(#1,#2)")
//static native def call_connect(String,Int):void;

}