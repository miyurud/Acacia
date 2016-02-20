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

import x10.util.ArrayList;
import org.antlr.runtime.ANTLRStringStream;
import org.acacia.rdf.sparql.java.SparqlLexer;
import org.antlr.runtime.CommonTokenStream;
import org.acacia.rdf.sparql.java.SparqlParser;
import x10.util.HashMap;

public class Tokenizer {
 	private var query:String;
 	private var queryType:Int;
 	private var variableCount:Long;
 	private val prefices:HashMap[String,String] = new HashMap[String,String]();
 	private val triples:ArrayList[Triple] = new ArrayList[Triple]();
 	private val variableMap:HashMap[String,Int] = new HashMap[String,Int]();
 
	public def this(var query:String){
		this.query = query;
 		tokenize();
	}

 	public def getQueryType():Int{
 		return queryType;
 	}
 
 	public def getTriples():ArrayList[Triple]{
 		return triples;
 	}
 
 	public def getVariableCount():Long{
 		return variableCount;
 	}
 
 	public def getPrefices():HashMap[String,String]{
 		return prefices;
 	}
 
 	public def getVariableMap():HashMap[String,Int]{
 		return variableMap;
 	}

 	private def tokenize():void{
 		var stream:ANTLRStringStream = new ANTLRStringStream(query);
 		var lexer:SparqlLexer = new SparqlLexer(stream);
 		var tokenStream:CommonTokenStream = new CommonTokenStream(lexer);
 		var parser:SparqlParser = new SparqlParser(tokenStream);
 		try{
 			//validate query
 			parser.query();
 
 			//determine query type
 			if(query.indexOf("SELECT")>=0){
 				queryType = 0n;
 			}
 
 			//find no of variables
 			val tmpArr = query.substring(query.indexOf("SELECT")+6n,query.indexOf("WHERE")).trim().split(" ");
 			variableCount	= tmpArr.size;
 			for(var k:Int=0n;k<variableCount;k++){
 				variableMap.put(tmpArr(k),k);
 				Console.OUT.println(tmpArr(k)+" : "+k);
 			}
 			
 			//find prefices
 			val prefixRange	= query.substring(0n,query.indexOf("SELECT")).trim().split("PREFIX");
 
 			for(var i:Int = 0n; i < prefixRange.size; i++){
 				if(prefixRange(i).length() > 0n){
 					val key		= prefixRange(i).trim().substring(0n,prefixRange(i).indexOf(":")-1n).trim();
 					val value	= prefixRange(i).trim().substring(prefixRange(i).indexOf("<"),prefixRange(i).indexOf(">")-1n).trim();
 					prefices.put(key,value);
 				}
 			}
 			//find triples
 			val index1	= query.indexOf("{");  // simple query
 			val index2	= query.indexOf("}");		 
 
 			// ; also should be considered 
 			val triplesWithprefices = query.substring(index1+1n,index2).trim().split(" .  ");
 			for(var i:int=0n;i<triplesWithprefices.size;i++){
 				var Uneditedtokens:Rail[String] = triplesWithprefices(i).trim().split(" ");
 				val tokens:Rail[String] = new Rail[String](4);
 				
 				var index:int = Uneditedtokens(1).indexOf(":");
 				tokens(2) = prefices.get(Uneditedtokens(1).substring(0n, index).trim()) + Uneditedtokens(1).substring(index + 1n).trim();
 				if (Uneditedtokens(0).indexOf("?") >= 0n) {
 					tokens(1) = Uneditedtokens(0);
 					if (Uneditedtokens(2).indexOf("?") >= 0n) {
 						tokens(3) = Uneditedtokens(2);
 						tokens(0) = "0";
 					}else{
 						tokens(0) = "1";
 						if (!Uneditedtokens(2).startsWith("<http")) {
 							index = Uneditedtokens(2).indexOf(":");//Console.OUT.println(Uneditedtokens(2)+"FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
 							tokens(3) = prefices.get(Uneditedtokens(2).substring(0n, index).trim()) + Uneditedtokens(2).substring(index + 1n).trim();
 						} else {
 							tokens(3) = Uneditedtokens(2).substring(1n, Uneditedtokens(2).length()-1n);
 						}
 					}
 				}else{
 					if (!Uneditedtokens(0).startsWith("<http")) {
 						index = Uneditedtokens(0).indexOf(":");
 						Console.OUT.println(Uneditedtokens(0)+"FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
 						tokens(1) = prefices.get(Uneditedtokens(0).substring(0n, index).trim()) + Uneditedtokens(0).substring(index + 1n).trim();
 					} else {
 						tokens(1) = Uneditedtokens(0).substring(1n, Uneditedtokens(0).length()-1n);
 					}
 					if (Uneditedtokens(2).indexOf("?") >= 0n) {
 						tokens(3) = Uneditedtokens(2);
 						tokens(0) = "2";
 					}else{
 						tokens(0) = "3";
 						if (!Uneditedtokens(2).startsWith("<http")) {
 							index = Uneditedtokens(2).indexOf(":");
 							Console.OUT.println(Uneditedtokens(2)+"FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
 							tokens(3) = prefices.get(Uneditedtokens(2).substring(0n, index).trim()) + Uneditedtokens(2).substring(index + 1n).trim();
 						} else {
 							tokens(3) = Uneditedtokens(2).substring(1n, Uneditedtokens(2).length()-1n);
 						}		
 					}
 				}
 				val triple:Triple = new Triple(tokens(1),tokens(2),tokens(3),Int.parseInt(tokens(0)));
 				triples.add(triple);
 			}
 
 		}catch(e:org.antlr.runtime.RecognitionException){
 			Console.OUT.println(e.getMessage());
 		}
 	}
}