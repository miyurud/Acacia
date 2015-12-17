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
import x10.util.HashSet;

public class SelectQueryExecution {
  //private var intermediateResults:HashSet[HashSet[String]] = null;

  public def executeSelect(var inputQuery:String, var graphID:String, var partitionID:String, var placeID:String):ArrayList[String]{
  	var result:ArrayList[String] = null;
    var nodeStore:HashMap[Long, HashSet[String]] = new HashMap[Long, HashSet[String]]();
    var ifSingleVariabled:Boolean = false;
    var ifInferenceHandled:Boolean = false;
    var intermediateResults:HashSet[HashSet[String]] = new HashSet[HashSet[String]]();
    var variableName:String = null;
    var unknowns:ArrayList[String] = new ArrayList[String](); 

  	//exract triple from the query
  	var query:Query = new Query();
    var prefix:HashMap[String, String] = query.extractPrefix(inputQuery);
    var triples:Rail[String] = query.extractTriples(inputQuery);
    var modifiedtriples:ArrayList[Rail[String]] = query.modifiedTriples(triples,prefix);

    //load inference data
    var dataLoading:DataLoading = new DataLoading();
    var inferenceData:HashMap[String, ArrayList[String]] = dataLoading.loadInferenceData();

    //should get a plan

    //inference handling
    var inferenceHandler:InferenceHandler = new InferenceHandler();	

    //triples are received as tokens
    var inferenceHandledTriples:ArrayList[Rail[String]] = new ArrayList[Rail[String]]();
    //inferenceHandledTriples=inferenceHandler.getInferenceHnadledTriples(inferenceData, modifiedtriples);

    dataLoading.loadGraphData(graphID, partitionID ,placeID);

    var graphData:ArrayList[String] = dataLoading.getGraphData();
    nodeStore = dataLoading.getVertexPropertyMap();
    
    inferenceHandledTriples = inferenceHandler.getInferenceHnadledTriples(inferenceData, modifiedtriples, nodeStore);
    ifInferenceHandled=inferenceHandler.getIfInferenceHandled();
    
    for(var i:Int = 0n; i < inferenceHandledTriples.size(); i++){
    	// var triplePattern:TriplePattern = new TriplePattern();
    	// triplePattern.match(inferenceHandledTriples.get(i),prefix, graphData);
    	// result = triplePattern.getResult();
    	// intermediateResults.add(result);
    
    //	System.out.println(inferenceHandledTriples.get(i)[0]+inferenceHandledTriples.get(i)[1]+inferenceHandledTriples.get(i)[2]);
    
	    if(inferenceHandledTriples.get(i)(0n).indexOf("?") > 0n){
		    variableName=inferenceHandledTriples.get(i)(0n).substring(1n);
		    
		    if(!unknowns.contains(variableName)){
		       unknowns.add(variableName);
		    }
	    }
	    
	    if(inferenceHandledTriples.get(i)(2).indexOf("?") > 0n){
	    	variableName=inferenceHandledTriples.get(i)(2).substring(1n);
	    
		    if(!unknowns.contains(variableName)){
		    	unknowns.add(variableName);
		    }
	    }
    }

    if(unknowns.size()==1){
      ifSingleVariabled=true;
    } else {
      ifSingleVariabled=false;
    }
    
    for(var i:Int = 0n; i < intermediateResults.size(); i++){
    	//Console.OUT.println(intermediateResults.get(i));
	    var triplePattern:TriplePattern = new TriplePattern();
	    triplePattern.match(inferenceHandledTriples.get(i),prefix, graphData);
	    result=triplePattern.getResult();
	    intermediateResults.add(result);
    }

    var finalResults:FinalResult = new FinalResult();
    
    if(ifSingleVariabled){
    	finalResults.joinResults(intermediateResults);
    }else{
        finalResults.joinResults(inferenceHandledTriples,intermediateResults, ifInferenceHandled, unknowns);
    }
    
    var FinalResults:ArrayList[String] = finalResults.getFinalResults();

    for(var i:Int = 0n; i < FinalResults.size(); i++){
    	//Console.OUT.println(FinalResults.get(i));
    }

    return FinalResults;
  }
}