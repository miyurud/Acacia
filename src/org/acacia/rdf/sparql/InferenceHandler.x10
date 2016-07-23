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
import x10.util.Map;
import x10.util.HashSet;

public class InferenceHandler {
  private var ifInferenceHandled:Boolean=false;

  public def getInferenceHnadledTriples(var inferenceData:HashMap[String, ArrayList[String]], var triples:ArrayList[Rail[String]], var nodeStore:HashMap[Long, HashSet[String]] ):ArrayList[Rail[String]]{
	    var tokens:Rail[String] = null;
	    var inferenceHandledTriples:ArrayList[Rail[String]] = new ArrayList[Rail[String]]();
	    var newTokens:ArrayList[String];
	    var key:String = null;
	    var newToken:String = null;
	    var flag:Int = 0n;
	    var index:Int = 0n;
	    
	    for(var i:Int = 0n; i < triples.size(); i++){
	      tokens = new Rail[String](3);
	      tokens = triples.get(i);
	  
	      //type inference 
	      if(tokens(1).indexOf("type") > 0){
	        key = tokens(2).substring(tokens(2).indexOf(":")+1n);
	  
	        var itr1:Iterator[Map.Entry[Long, HashSet[String]]] = nodeStore.entries().iterator();
	  
	        while(itr1.hasNext()){
	          var entry:Map.Entry[Long, HashSet[String]] = itr1.next();
	  
	          if(entry.getValue().toString().indexOf(tokens(2)) > 0){              		
	            flag = 1n;
	            break;
	          }
	        }
	  
	        if(flag == 0n){
	          if(inferenceData.containsKey(key.substring(key.indexOf("#")+1n))){
	            var itr:Iterator[Map.Entry[String, ArrayList[String]]] = inferenceData.entries().iterator();
	            newTokens = new ArrayList[String]();
	  
	            while(itr.hasNext()){
	              var entry:Map.Entry[String, ArrayList[String]] = itr.next();
	
	              if(entry.getKey().equals(key.substring(key.indexOf("#")+1n))){
	                newTokens = entry.getValue();
	                break;
	              }
	            }
	  
	            if(newTokens.size() > 0){
	              ifInferenceHandled = true;
	              for(var j:Int = 0n; j < newTokens.size(); j++){
	                newToken = tokens(2).substring(0n, tokens(2).indexOf("#")+1n)+newTokens.get(j);
	                tokens(2) = newToken;
	                inferenceHandledTriples.addBefore(index, tokens);
	                index = index + 1n;
	              }
	            }
	          }else{
	            inferenceHandledTriples.addBefore(index,tokens);
	            index = index + 1n;
	          }
	        } else {
	          inferenceHandledTriples.addBefore(index, tokens);
	          index = index + 1n;
	        }
	     }else{
	       inferenceHandledTriples.addBefore(index, tokens);
	       index = index + 1n;
	     }
	  }
	  
	  return inferenceHandledTriples;
  } 
  
  public def getIfInferenceHandled():Boolean{
  	return ifInferenceHandled;
  }
}