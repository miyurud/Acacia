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

import x10.util.HashMap;
import x10.util.ArrayList;

public class Query {
	/**
	 * read nodes,relationship,properties
	 * @param query
	 * @return
	 */

    public def extractTriples(var query:String):Rail[String]{
    	var index:Int = 0n;
        var index2:Int = 0n;				 
        var triples:Rail[String] = null;		 

        // simple query
        index = query.indexOf("{"); 
        index2 = query.indexOf("}");		 

        triples = query.substring(index+1n, index2).trim().split(" .  ");  

        return triples;
    }
    
    /**
 	 * extract prefix from the triples
 	 * @param query
     * @return
     */
    public def extractPrefix(var query:String) : HashMap[String, String]{
    	var Prefix:HashMap[String, String] = new HashMap[String, String]();	
    	var prefix:Rail[String];
    	var key:String = null;
    	var value:String = null;

    	prefix = query.substring(0n, query.indexOf("SELECT")).trim().split("PREFIX");

    	for(var i:Int = 0n; i < prefix.size; i++){
    		if(prefix(i).length() > 0n){
    			key = prefix(i).trim().substring(0n, prefix(i).indexOf(":")-1n).trim();
    			value = prefix(i).trim().substring(prefix(i).indexOf("<"), prefix(i).indexOf(">")-1n).trim();
    			Prefix.put(key,value);
    		}
    	}

    	return Prefix;
    }

    public def modifiedTriples(var triples:Rail[String], var prefix:HashMap[String, String]) : ArrayList[Rail[String]]{
    	var triple:Triple = new Triple();
    	var tokens:Rail[String] = null;
    	var modifiedTriples:ArrayList[Rail[String]] = new ArrayList[Rail[String]]();

    	for(var i:Int = 0n; i < triples.size; i++){
    		tokens = new Rail[String](3);
    		tokens = triple.extractTokens(triples(i));

    		tokens(1) = prefix.get(tokens(1).substring(0n, tokens(1).indexOf(":")).trim()) + tokens(1).substring(tokens(1).indexOf(":") + 1n).trim();	

    		if (tokens(0).indexOf("?") >= 0n) {
    			if (tokens(2).indexOf("?") < 0n) {
    				if (!tokens(2).startsWith("<http")) {
    					tokens(2) = prefix.get(tokens(2).substring(0n, tokens(2).indexOf(":")).trim()) + tokens(2).substring(tokens(2).indexOf(":") + 1n).trim();
    				}else{
    					tokens(2) = tokens(2).substring(1n, tokens(2).length()-1n);
    				}
    			}
    		}else{
    			tokens(0) = prefix.get(tokens(0).substring(0n, tokens(0).indexOf(":")).trim()) + tokens(0).substring(tokens(0).indexOf(":") + 1n).trim();

    			if (tokens(2).indexOf("?") < 0n) {
    				if (!tokens(2).startsWith("<http")) {
    					tokens(2) = prefix.get(tokens(2).substring(0n, tokens(2).indexOf(":")).trim()) + tokens(2).substring(tokens(2).indexOf(":") + 1n).trim();
    				}else{
    					tokens(2) = tokens(2).substring(1n, tokens(2).length()-1n);
    				}
    			}
    		}

    		modifiedTriples.add(tokens);
    	}

    	return modifiedTriples;
    }
}