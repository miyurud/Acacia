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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import x10.util.ArrayList;
import x10.util.HashMap;
import x10.util.HashSet;
import x10.util.Map;
import x10.lang.Iterator;

import org.acacia.localstore.AcaciaHashMapNativeStore;
import org.acacia.util.Utils;


public class ExecuteQuery {	
	private var Prefix:HashMap[String, String]= new HashMap[String, String]();	
	private var graphData:ArrayList[String] = new ArrayList[String]();
 	private var store:AcaciaHashMapNativeStore;
 	private var variableCount:Long;
 	private var tokenizer:Tokenizer;
 	private var answerSet:AnswerSet;
	private var localSubGraphMap:java.util.HashMap;
	private var vertexPropertyMap:java.util.HashMap;
	private var relationshipMapWithProperties:java.util.HashMap;
	private var attributeMap:java.util.HashMap;
	private var predicateStore:java.util.HashMap;

 	private var graphID:String;
 	private var partitionID:String;
 	private var placeID:String;
 	private var replicatingId:String;
    private val baseDir:String = Utils.getAcaciaProperty("org.acacia.server.instance.datafolder");
    private var triplePatternLocal:TriplePattern = null;
    private var triplePatternCentral:TriplePattern = null;
    private var variableMap:HashMap[String,Int] = null;
 
    // //We need to have a consructor which will avoid the data from the local store being loaded multiple times.
    // public def this(var query:String, var graphID:String, var partitionID:String, var placeID:String){
	   //  tokenizer		= new Tokenizer(query);
	   //  variableMap 	= tokenizer.getVariableMap();
	   //  variableCount	= tokenizer.getVariableCount();
	   //  answerSet		= new AnswerSet(variableCount);
	   //  
	   //  store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), baseDir,false);
	   //  triplePatternLocal = new TriplePattern(store);
	   //  
	   //  store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), baseDir, true, Int.parseInt(placeID));
	   //  triplePatternCentral = new TriplePattern(store);
    // }
    
	public def executeQuery(var query:String, var graphID:String, var partitionID:String, var placeID:String) : ArrayList[String] {		
		var result:ArrayList[String] = new ArrayList[String]();
 		tokenizer		= new Tokenizer(query);
 		variableCount	= tokenizer.getVariableCount();
 		answerSet		= new AnswerSet(variableCount);

 		this.graphID		= graphID;
 		this.partitionID	= partitionID;
 		this.placeID		= placeID;

		try {
 			val qType = tokenizer.getQueryType();
 			var triples:ArrayList[Triple] = tokenizer.getTriples();
 			switch(qType){
 				case 0n:
 					execute(triples);
 					break;
 			}
 			val tempHashSet = new HashSet[String]();
 			val temp = answerSet.getAnswerSet();
 			for(var i:Int=0n;i<temp(0).size();i++){
 				var s:String = new String();
 				var last:Boolean = false;
 				for(var j:Long=0;j<variableCount;j++){
 					s = s + temp(j).get(i);
 					if(j != variableCount-1){
 						s = s + ", ";
 					}
 				}
 				tempHashSet.add(s);
 			}
 			result.addAll(tempHashSet);
		}catch (val e:Exception) {
			Console.OUT.println("Error in query format"+e);
		}

		return result;
	}
 
 	public def execute(val triples:ArrayList[Triple]){
 		var intermediateResults:ArrayList[ArrayList[String]] = new ArrayList[ArrayList[String]]();
  		
		 store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), baseDir,false);
		 val triplePatternLocal = new TriplePattern(store);
		 
		 store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), baseDir, true, Int.parseInt(placeID));
		 val triplePatternCentral = new TriplePattern(store);
		 val variableMap = tokenizer.getVariableMap();
 
 		for (var i:Int = 0n; i < triples.size(); i++) {
 			var triple:Triple = triples.get(i);
 			Console.OUT.println(triple.getSubject()+":"+triple.getPredicate()+":"+triple.getObject());
 			var result:HashSet[String] = new HashSet[String]();
 			val resultMapLocal = triplePatternLocal.execute(triple);
 			val resultMapCentral = triplePatternCentral.execute(triple);
 			val keySet = resultMapLocal.keySet();
 			val key = keySet.iterator().next();
 			val str = key.split(",");
 			val size = str.size;
 			result.addAll(resultMapLocal.get(key));
 			result.addAll(resultMapCentral.get(key));
 			Console.OUT.println("Data size : "+result.size());
 			Console.OUT.println("Ans size  : "+answerSet.getAnswerSet()(0).size());
 
 			if(size == 1){
 				answerSet.mergeAnswer(result,variableMap.get(key));
 			}else if(size == 2){
 				answerSet.mergeAnswer(result,variableMap.get(str(0)),variableMap.get(str(1)));
 			}
 			Console.OUT.println("Ans size  : "+answerSet.getAnswerSet()(0).size());
 		}
 	}
 
 public def executeWithoutMerge(var query:String, var graphID:String, var partitionID:String, var placeID:String):ArrayList[InterimResult]{
 	 var results:ArrayList[InterimResult] = new ArrayList[InterimResult]();
	 tokenizer		= new Tokenizer(query);
	 variableCount	= tokenizer.getVariableCount();
	 answerSet		= new AnswerSet(variableCount);
	
	 this.graphID		= graphID;
	 this.partitionID	= partitionID;
	 this.placeID		= placeID;
	 try {
		 val qType = tokenizer.getQueryType();
		 var triples:ArrayList[Triple] = tokenizer.getTriples();
	 
		 switch(qType){
			 case 0n:
				 var intermediateResults:ArrayList[ArrayList[String]] = new ArrayList[ArrayList[String]]();
	 
	 			 store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), baseDir,false);
	 			 val triplePatternLocal = new TriplePattern(store);
	 
	 			 store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), baseDir, true, Int.parseInt(placeID));
	 			 val triplePatternCentral = new TriplePattern(store);
	 			 val variableMap = tokenizer.getVariableMap();
	 
				 for (var i:Int = 0n; i < triples.size(); i++) {
					 var triple:Triple = triples.get(i);
					 Console.OUT.println(triple.getSubject()+":"+triple.getPredicate()+":"+triple.getObject());
					 var result:HashSet[String] = new HashSet[String]();
					 //val resultMapLocal = triplePatternLocal.execute(triple);
					 val resultMapCentral = triplePatternCentral.execute(triple);
					 // val keySet = resultMapLocal.keySet();
					 // val key = keySet.iterator().next();
					 // val str = key.split(",");
					 // val size = str.size;
					 var item:InterimResult = new InterimResult();
					 item.resultMapLocal = triplePatternLocal.execute(triple);
					 item.resultMapCentral = triplePatternCentral.execute(triple);
					 
					 results.add(item);
					 
				 }
	 			 break;
	 		}
	 }catch (val e:Exception) {
	 	Console.OUT.println("Error in query format"+e);
	 }
	 
	 return results;
 }
 
 public def executeWithoutMerge(var query:String, var graphID:String, var partitionID:String, var placeID:String,var replicatingID:String):ArrayList[InterimResult]{
 var results:ArrayList[InterimResult] = new ArrayList[InterimResult]();
 tokenizer		= new Tokenizer(query);
 variableCount	= tokenizer.getVariableCount();
 answerSet		= new AnswerSet(variableCount);
 
 this.graphID		= graphID;
 this.partitionID	= partitionID;
 this.placeID		= placeID;
 
 try {
 val qType = tokenizer.getQueryType();
 var triples:ArrayList[Triple] = tokenizer.getTriples();
 
 switch(qType){
 case 0n:
 var intermediateResults:ArrayList[ArrayList[String]] = new ArrayList[ArrayList[String]]();
 val dataFolder = baseDir + "/" + "replication" + "/" + replicatingID;
 store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), dataFolder,false);
 val triplePatternLocal = new TriplePattern(store);
 
 store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), dataFolder, true, Int.parseInt(placeID));
 val triplePatternCentral = new TriplePattern(store);
 val variableMap = tokenizer.getVariableMap();
 
 for (var i:Int = 0n; i < triples.size(); i++) {
 var triple:Triple = triples.get(i);
 Console.OUT.println(triple.getSubject()+":"+triple.getPredicate()+":"+triple.getObject());
 var result:HashSet[String] = new HashSet[String]();
 //val resultMapLocal = triplePatternLocal.execute(triple);
 val resultMapCentral = triplePatternCentral.execute(triple);
 // val keySet = resultMapLocal.keySet();
 // val key = keySet.iterator().next();
 // val str = key.split(",");
 // val size = str.size;
 var item:InterimResult = new InterimResult();
 item.resultMapLocal = triplePatternLocal.execute(triple);
 item.resultMapCentral = triplePatternCentral.execute(triple);
 
 results.add(item);
 
 }
 break;
 }
 }catch (val e:Exception) {
 Console.OUT.println("Error in query format"+e);
 }
 
 return results;
 }
 
 
 
 //     //The second type of execution. 
 // 
	//  public def executeQuery(var query:String, var graphID:String, var partitionID:String, var placeID:String, var triple:Triple) : ArrayList[String] {		
	// 	 var result:ArrayList[String] = new ArrayList[String]();
	// 	 answerSet		= new AnswerSet(variableCount);
	// 	
	// 	 this.graphID		= graphID;
	// 	 this.partitionID	= partitionID;
	// 	 this.placeID		= placeID;
	// 	
	// 	 try {
	// 		 val qType = tokenizer.getQueryType();
	// 		 switch(qType){
	// 			 case 0n:
	// 			 execute(triple);
	// 			 break;
	// 	 	}
	// 		 
	// 	    val tempHashSet = new HashSet[String]();
	// 	    val temp = answerSet.getAnswerSet();
	// 	    
	// 	    for(var i:Int=0n;i<temp(0).size();i++){
	// 			 var s:String = new String();
	// 			 var last:Boolean = false;
	// 			 
	// 			 for(var j:Long=0;j<variableCount;j++){
	// 				 s = s + temp(j).get(i);
	// 				 if(j != variableCount-1){
	// 					 s = s + ", ";
	// 				 }
	// 			 }
	// 			 tempHashSet.add(s);
	// 		 }
	// 		 result.addAll(tempHashSet);
	// 	 }catch (val e:Exception) {
	// 		 Console.OUT.println("Error in query format"+e);
	// 	 }
	// 	
	// 	 return result;
	//  } 
 // 
	//  public def execute(val triple:Triple){
	// 	 var intermediateResults:ArrayList[ArrayList[String]] = new ArrayList[ArrayList[String]]();
	// 	 
	// 	 store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), baseDir,false);
	// 	 val triplePatternLocal = new TriplePattern(store);
	// 	 
	// 	 store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), baseDir, true, Int.parseInt(placeID));
	// 	 val triplePatternCentral = new TriplePattern(store);
	// 	 val variableMap = tokenizer.getVariableMap();
	// 	 
	// 	 Console.OUT.println(triple.getSubject()+":"+triple.getPredicate()+":"+triple.getObject());
	// 	 var result:HashSet[String] = new HashSet[String]();
	// 	 val resultMapLocal = triplePatternLocal.execute(triple);
	// 	 val resultMapCentral = triplePatternCentral.execute(triple);
	// 	 val keySet = resultMapLocal.keySet();
	// 	 val key = keySet.iterator().next();
	// 	 val str = key.split(",");
	// 	 val size = str.size;
	// 	 result.addAll(resultMapLocal.get(key));
	// 	 result.addAll(resultMapCentral.get(key));
	// 	 Console.OUT.println("Data size : "+result.size());
	// 	 Console.OUT.println("Ans size  : "+answerSet.getAnswerSet()(0).size());
	// 	 
	// 	 if(size == 1){
	// 	   answerSet.mergeAnswer(result,variableMap.get(key));
	// 	 }else if(size == 2){
	// 	   answerSet.mergeAnswer(result,variableMap.get(str(0)),variableMap.get(str(1)));
	// 	 }
	// 	 Console.OUT.println("Ans size  : "+answerSet.getAnswerSet()(0).size());
	//  }
}
