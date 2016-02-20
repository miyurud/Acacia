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

public class ResultsCache {
   private var resultsMap:HashMap[Int, ArrayList[String]] = new HashMap[Int, ArrayList[String]]();
   private var queryIndex:HashMap[Int, Rail[Int]] = new HashMap[Int, Rail[Int]]();
   private var resultID:Int = 0n; //Unique result ID
   private var queryID:Int = 0n;  //Unique query ID
   
   public def getFromCache(var query:String, var graphID:Int, var partitionID:Int, var placeID:Int):ArrayList[String]{
       val queryItr:Iterator[Int] = queryIndex.keySet().iterator();
       val digest:Int = query.hashCode();
       
       while(queryItr.hasNext()){
          val qID:Int = queryItr.next();
          val record:Rail[Int] = queryIndex.get(qID);
          
          if((record(0) == digest) && (record(1) == graphID) && (record(2) == partitionID) && (record(3) == placeID)){
              return resultsMap.get(qID);
          }
       }
       
       return null;
   }
   
   public def putToCache(var query:String, var graphID:Int, var partitionID:Int, var placeID:Int, var result:ArrayList[String]):void{
       val digest:Int = query.hashCode();
       var record:Rail[Int] = new Rail[Int](4);
       record(0) = digest;
       record(1) = graphID;
       record(2) = partitionID;
       record(3) = placeID;
       queryIndex.put(queryID, record);
       resultsMap.put(queryID, result);
       queryID++;
   }
}