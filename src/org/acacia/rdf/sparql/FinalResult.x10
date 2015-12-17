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

import x10.util.HashSet;
import x10.util.ArrayList;

public class FinalResult {
  private var results:HashSet[String] = null;  

  /***
	 * only for single variable queries
	 * @param intermediateResults
	 */
  public def joinResults(var modifiedtriples:ArrayList[Rail[String]], var intermediateResults:ArrayList[HashSet[String]],  var ifInferenceHandled:Boolean){
    var n:Int = 0n;
    var key:Int = 0n;
    var size1:Int = 0n;
    //FinalResults= new HashSet<String>(); 
    var element:HashSet[String] = new HashSet[String]();
  
    // sort intermediateResults
    //System.out.println("intermediateResults.size()"+intermediateResults.size());
    for(var i:Int = 1n; i < intermediateResults.size(); i++){
      key = intermediateResults.get(i).size() as Int;
    
      element=intermediateResults.get(i);
      intermediateResults.removeAt(i);
  
      var j:Int = i - 1n;
      while (j >= 0n && intermediateResults.get(j).size() > key){
        j = j - 1n;
      }
  
      intermediateResults.addBefore(j+1, element);
    }
    
    //non inference handled
    if(!ifInferenceHandled){
      if(intermediateResults.get(0).size() != 0){
        //results = new HashSet[String](intermediateResults.get(0));
        results = intermediateResults.get(0);
  
        for(var i:Int = 1n; i < intermediateResults.size(); i++){
          size1 = results.size() as Int;
          //?
  
          if(intermediateResults.get(i).size()==0){
            results.clear();
            break;
          }
  
          var itr:Iterator[String] = results.iterator();
          var value:String = null;

          for(var a:Int = 0n; itr.hasNext(); a++) {
            value = itr.next();
            if(!intermediateResults.get(i).contains(value)){
              results.remove(value);
            }
          }
        }
      } else {
        results = new HashSet[String]();
        results.clear();
      }
      // when inference handled
    }else{
      results = new HashSet[String]();
      var size:Int = intermediateResults.size() as Int;
      var flag:Int = 0n;
  
      if(modifiedtriples.size() > 1n){
        size = intermediateResults.size() as Int - 1n;
        flag = 1n;
      }
  
      for(var i:Int = 0n; i < size; i++){
        for(var j:Int = 0n; j < intermediateResults.get(i).size(); j++){		
            var item:HashSet[String] = intermediateResults.get(i);
            var itr:Iterator[String] = item.iterator();
            
            while(itr.hasNext()){
              val s:String = itr.next();
              if(!results.contains(s)){
                results.add(s);
              }
            }
        }
      }
  
	  if(flag == 0n){
	    var itr:Iterator[String] = results.iterator();
	    var value:String = null;
	
	    for(var a:Int = 0n; itr.hasNext(); a++) {
	      value = itr.next();
	      if(!intermediateResults.get(intermediateResults.size()-1).contains(value)){
	        results.remove(value);
	      }
	    }
	  }
    }
  }

  public def joinResults(var inferenceHandledTriples:ArrayList[Rail[String]], var intermediateResults:ArrayList[ArrayList[String]], var ifInferenceHandled:Boolean, var unknowns:ArrayList[String]):void{
    var temp:ArrayList[Rail[String]] = new ArrayList[Rail[String]]();
    var temp2:ArrayList[String] = new ArrayList[String]();
  
    if(!ifInferenceHandled){
      for(var i:Int = 0n; i < intermediateResults.size(); i++){
        for(var j:Int = 0n; j < intermediateResults.get(i).size(); j++){
          
        }
      }
    }else{
      //when inference handled
    }
  }
  
  public def getFinalResults() : HashSet[String]{
  	return results;
  }
}