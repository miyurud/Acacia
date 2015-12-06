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

public class FinalResult {
  private var results:ArrayList[String] = null;  

  /**
	 * only for single variable queries
	 * @param intermediateResults
	 */
  public def joinResults(val intermediateResults:ArrayList[ArrayList[String]]):void{
  	var n:Int = 0n;
  	results = new ArrayList[String](); 

    Console.OUT.print(intermediateResults.size());

    if(intermediateResults.size() == 1){
       for(var i:Int = 0n; i < intermediateResults.get(0).size(); i++){
         var arr1:Rail[String] = intermediateResults.get(n).get(i).trim().split(",");					

         if(!results.contains(arr1(0))){
            results.add(arr1(0));
         }
       }
    } else if(intermediateResults.size()>1) {
       for(var i:Int = 0n; i < intermediateResults.get(n).size(); i++){
         var arr1:Rail[String] = intermediateResults.get(n).get(i).trim().split(",");

         for(var j:Int = 0n; j < intermediateResults.get(n+1).size(); j++){
           var arr2:Rail[String] = intermediateResults.get(n+1).get(j).trim().split(",");

           if(arr1(0).compareTo(arr2(0)) == 0n){
             if(results.contains(arr1(0))){
             	results.add(arr1(0));
             }
           }
         }	
       }
    }
  }

  public def getFinalResults() : ArrayList[String]{
  	return results;
  }
}