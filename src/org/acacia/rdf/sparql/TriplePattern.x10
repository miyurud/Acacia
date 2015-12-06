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
import x10.util.HashMap;

public class TriplePattern {
		private var tokens:Rail[String] = null;
		private var Prefix:HashMap[String, String];
		private var graphData:ArrayList[String];
		private var result:ArrayList[String];
	
		public def match(var tokens:Rail[String], var Prefix:HashMap[String, String], var graphData:ArrayList[String]) : void{
			this.tokens=tokens;
			this.Prefix=Prefix;
			this.graphData=graphData;			
			matchTriple();
		}
		
		public def matchTriple():void{
			result=new ArrayList[String]();
			
			if (tokens(0).indexOf("?") >= 0n) {
				if (tokens(2).indexOf("?") >= 0n) {
					result=matchTriplePattern1(tokens,graphData);
				} else {				
					
					/*if (!tokens[2].startsWith("<http")) {
						tokens[2] = Prefix.get(tokens[2].substring(0,
								tokens[2].indexOf(":")).trim())
								+ tokens[2].substring(
										tokens[2].indexOf(":") + 1).trim();
						
					} else {
						tokens[2] = tokens[2].substring(1, tokens[2].length()-1);
					}*/
					
					result=matchTriplePattern2(tokens,graphData);						
							
				}
				
			} else {
				/*tokens[0] = Prefix.get(tokens[0].substring(0,
						tokens[0].indexOf(":")).trim())
						+ tokens[0].substring(tokens[0].indexOf(":") + 1)
								.trim();*/
				if (tokens(2).indexOf("?") >= 0n) {
					result=matchTriplePattern3(tokens,graphData);
				} else {
					/*tokens[2] = Prefix.get(tokens[2].substring(0,
							tokens[2].indexOf(":")).trim())
							+ tokens[2].substring(tokens[2].indexOf(":") + 1)
									.trim();*/
					 
					result=matchTriplePattern4(tokens,graphData);
					
				}
			}
		}
	
		public def getResult():ArrayList[String]{
			return result;
		}
	
	// (?X , name, ?N)
		public def matchTriplePattern1(var tokens:Rail[String], var graphData:ArrayList[String]) : ArrayList[String] {
			var temp:ArrayList[String] = new ArrayList[String]();

			// only predicate should be considered

			for (var i:Int = 0n; i < graphData.size(); i++) {
				if (graphData.get(i).compareTo(tokens(1)) > 0n) {
					temp.add( graphData.get(i));
				}
			}
			return temp;
		}

		// (?X , name, "John")
		public def matchTriplePattern2(var tokens:Rail[String], var graphData:ArrayList[String]) :ArrayList[String] {
			var temp:ArrayList[String] = new ArrayList[String]();

			// predicate and object should be considered

			//System.out.println("token1 "+tokens[0]);
			//System.out.println("token11111111 "+tokens[1]);
			//System.out.println("token111111111 "+tokens[2]);
			
			for (var i:Int = 0n; i < graphData.size(); i++) {			
				
				if (graphData.get(i).compareTo(tokens(1)) > 0n ){	
					
					if(graphData.get(i).compareTo(tokens(2)) > 0n) {
                        temp.add(graphData.get(i));
      				}
				}
			}
			return temp;
		}

		// (d:100 , name, ?N)
		public def matchTriplePattern3(var tokens:Rail[String], var graphData:ArrayList[String]) : ArrayList[String] {
			var temp:ArrayList[String] = new ArrayList[String]();

			// subject and predicate should be considered

			for (var i:Int = 0n; i < graphData.size(); i++) {
				if ((graphData.get(i).compareTo(tokens(0)) > 0n) && (graphData.get(i).compareTo(tokens(1)) > 0n) ) {
					temp.add(graphData.get(i));
				}
			}
			return temp;
		}

		// (d:100 , name, "John")
		public def matchTriplePattern4(var tokens:Rail[String], var graphData:ArrayList[String]) : ArrayList[String] {
			var temp:ArrayList[String] = new ArrayList[String]();

			// subject, predicate and object should be considered

			for (var i:Int = 0n; i < graphData.size(); i++) {

				if ((graphData.get(i).compareTo(tokens(0)) > 0n) && (graphData.get(i).compareTo(tokens(1)) > 0n) && (graphData.get(i).compareTo(tokens(2)) > 0n)) {
					temp.add(graphData.get(i));
				}
			}
			return temp;
		}

}
