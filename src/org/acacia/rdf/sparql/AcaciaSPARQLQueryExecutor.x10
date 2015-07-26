package org.acacia.rdf.sparql;

import x10.io.File;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.acacia.log.java.Logger_Java;
import org.acacia.util.Utils;

import x10.util.ArrayList;

public class AcaciaSPARQLQueryExecutor {


	public def this(){}

	public def executeQuery(val query:String, val graphID:String):ArrayList[String]{

		var stream :ANTLRStringStream  =new ANTLRStringStream(query);			
		var lexer: SparqlLexer  =new SparqlLexer(stream);
		var tokenStream: CommonTokenStream =new CommonTokenStream(lexer);
		var parser: SparqlParser =new SparqlParser(tokenStream);     
        var index:Int;
        var result:ArrayList[String]=null;
		var k:Int=0n;

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
		data.add("(R1 , name, john)");
		data.add("(R1 , email, J@ed.ex)");
		data.add("(R2 , name, paul)");
		data.add("(R3 , name, ringo)");
		data.add("(R3 , email, R@ed.ex)");
		data.add("(R3 , webPage, www.ringo.com)");
		
		return data;

	}

	// this is a test class for executing query
	public def selectQuery(graphData:ArrayList[String], triples:Rail[String]):ArrayList[String]{

		var tokens:Rail[String]; 		
 		var intermediateResults:ArrayList[ArrayList[String]]=new ArrayList[ArrayList[String]](); 
        var Results:ArrayList[String] = new ArrayList[String]();  	
 		var index:Int=0n;
 		var k:Int=0n;
 
		for(var i:Int=0n; i < triples.size; i++){
 			
			// get the tokens of the triples
			tokens=triples(i).trim().split(" ");

			if(tokens(0).indexOf("?")>=0){	
				if(tokens(2).indexOf("?")>=0){
					intermediateResults.set(triplePattern1(tokens,graphData),index);
				}
				else{
					intermediateResults.set(triplePattern2(tokens,graphData),index);
				}
			}
			else{
				if(tokens(2).indexOf("?")>=0){	
					intermediateResults.set(triplePattern3(tokens,graphData),index);
				}
				else{
					intermediateResults.set(triplePattern4(tokens,graphData),index);
				}
 			}
			index=index+1n;
						
		}
				
		//AND results
				
		for(var i:Int=0n; i < intermediateResults.size(); i++){
			for(var j:Int=0n; j < intermediateResults.get(i).size(); j++){

                        Results.set(intermediateResults.get(i).get(j),k);
               			k=k+1n;
              }
				
		}

 		
	return Results;
	}

	//read nodes,relationship,properties
 	public def extractTripples(query:String):Rail[String]{
 
		 var triples:Rail[String];		 
		 var index:Int,index2:Int ;
		 
		 index=query.indexOf("{");  // simple query
		 index2=query.indexOf("}");		 
		 
		 // ; also should be considered 
		 triples=query.substring(index+1n,index2).trim().split(".");  
		 return triples;
	 }



	//(?X , name, ?N)
	public def triplePattern1(tokens:Rail[String], graphData:ArrayList[String]):ArrayList[String]{

		var temp:ArrayList[String] = new ArrayList[String]();
		var k:Int=0n;
		
		//only predicate should be considered
		
		for(var i:Int=0n; i < graphData.size(); i++){
		
			if(graphData.get(i).indexOf(tokens(1).substring(3n))>=0){
		
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
		
		for(var i:Int=0n; i < graphData.size(); i++){
		
			if(graphData.get(i).indexOf(tokens(1).substring(3n))>=0 && graphData.get(i).indexOf(tokens(2).substring(1n,tokens(2).length()-1n))>=0){
		
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
		
			if(graphData.get(i).indexOf(tokens(0))>=0 && graphData.get(i).indexOf(tokens(1).substring(3n))>=0){
		
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
		
			if(graphData.get(i).indexOf(tokens(0))>=0 && graphData.get(i).indexOf(tokens(1).substring(3n))>=0 && graphData.get(i).indexOf(tokens(2).substring(1n,tokens(2).length()-1n))>=0){
		
				temp.set(graphData.get(i),k);
				k=k+1n;
			}
		
		}
		return temp;
	}

}