package org.acacia.rdf.sparql;

import x10.compiler.Native;
import x10.interop.Java.array;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.acacia.log.java.Logger_Java;

public class AcaciaSPARQLParser {


public def this(){}

public def Parsing(val query:String, val graphID:String):String{

//SELECT ?name WHERE { ?person foaf:name ?name .} 
	      var stream :ANTLRStringStream  =new ANTLRStringStream(query);			
	var lexer: SparqlLexer  =new SparqlLexer(stream);
	var tokenStream: CommonTokenStream =new CommonTokenStream(lexer);
	var parser: SparqlParser =new SparqlParser(tokenStream);
     
     	var result:String="OK";

		try {
	        	parser.query();   	
	    
	            
	        
       }catch (val e:RecognitionException) {
			// TODO Auto-generated catch block
       result="Error while parsing: " + e.getMessage();
			Logger_Java.error(result);
		Console.OUT.println(e.getMessage());
		}
	
       finally{
       
      // Console.OUT.println(result);
       }
	return result;

}

// this is a test class for executing query
public def selectQuery(val query:String):void{


 


}

//dummy data
public def data():void{

var vertices:Rail[String] = new Rail[String]();
var edges:Rail[String] = new Rail[String]();

}


}