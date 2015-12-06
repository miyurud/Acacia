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

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import org.acacia.rdf.sparql.java.SparqlLexer;
import org.acacia.rdf.sparql.java.SparqlParser;

public class QueryParsing {
	/*
	 * Query parsing
	 */
    public def parsing(var query:String):void{
    	var stream:ANTLRStringStream = new ANTLRStringStream(query);
    	var lexer:SparqlLexer = new SparqlLexer(stream);
    	var tokenStream:CommonTokenStream = new CommonTokenStream(lexer);
    	var parser:SparqlParser = new SparqlParser(tokenStream);

    	try {
    		parser.query();
    	}catch (var e:org.antlr.runtime.RecognitionException) {
    		Console.OUT.println("Error in query format: " + e);
    	}
    }
}