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

package org.acacia.query.algorithms.kcore;

import org.acacia.localstore.AcaciaHashMapNativeStore;
import org.acacia.centralstore.AcaciaHashMapCentralStore;
import org.acacia.util.java.Utils_Java;
import org.acacia.util.Utils;

import x10.util.HashMap;
import x10.util.HashSet;
import x10.util.Map.Entry;
import x10.util.Set;
import x10.util.StringBuilder;
import x10.lang.Iterator;

import x10.io.File;

import org.apache.commons.io.FileUtils;
import x10.util.ArrayList;

/**
 * Class KCore
 */
public class KCore {

 	private val location = Utils.call_getAcaciaProperty("org.acacia.server.runtime.location")+"/KCoreFiles/";
	private var baseDir:String  = "";
 	private var nativeStoreLocalSubGraphMap:HashMap[Long, HashSet[Long]];
 	private var centralStoreLocalSubGraphMap:HashMap[Long, HashSet[Long]];
    private var KCorenessOfGraph:Long = 0;
    private var vertexIds:HashMap[Long,Long]= new HashMap[Long,Long]();
 
    public def getVertexIdsResults(graphID:String,partitionID:String,placeID:String):HashMap[Long,Long]{
    
	    try {
	    	loadGraphData(graphID, partitionID, placeID);
	    	//createFilesLocation();
	    	calculateKCoreness();
	    }catch(e:Exception){
	    	e.printStackTrace();
	    }finally{
	    	Console.OUT.println(vertexIds.size());
	    }
	    return vertexIds;
	    
    }

 	def loadGraphData(graphID:String, partitionID:String, placeID:String) {
 
 		baseDir = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
 
 		//native store
 		val nativeStore:AcaciaHashMapNativeStore = new AcaciaHashMapNativeStore(Int.parse(graphID), Int.parse(partitionID),baseDir,false);
  		nativeStore.loadGraph();
 		nativeStoreLocalSubGraphMap = nativeStore.getUnderlyingHashMap();
 
 		//central store
 		val centraleStore:AcaciaHashMapNativeStore = new AcaciaHashMapNativeStore(Int.parse(graphID), Int.parse(partitionID),baseDir,true,Int.parse(placeID));
 		centraleStore.loadGraph();
 		centralStoreLocalSubGraphMap = centraleStore.getUnderlyingHashMap();
 
	}
 
 	// Create the files/location to store results
 	def createFilesLocation() {
 
 		val f = new File(location);
 
 		if(!f.exists()){
 			f.mkdir();
 		}else{
 			//Delete the existing files
 			val dir:java.io.File = new java.io.File(location);
 			val files:x10.interop.Java.array[java.io.File] = dir.listFiles();

 			for(var i:Int = 0n; i < files.length; i++ ){
 				var delStatus:Boolean = false;
 				try{
 					if(files(i).isDirectory()){
 						FileUtils.deleteDirectory(files(i));
 					}else{
 						delStatus = files(i).delete();
 					}
 				}catch(var ex:java.io.IOException){
 					ex.printStackTrace();
 				}
 			}
 		}
 	}
	
 	// calculate kcore value
 	def calculateKCoreness():HashMap[Long,Long]{
 
 		var count:Long = 0;
 		var kValue:Long = 1;
 		var vertexCount:Long;
 
 		vertexIds = new HashMap[Long,Long]();
 		var tempStore:Set[Long] = new HashSet[Long]();
 		var keyStore:Set[Long] = new HashSet[Long]();
 
 		var itr:Iterator[Long];
 		keyStore.addAll(nativeStoreLocalSubGraphMap.keySet());
 		keyStore.addAll(centralStoreLocalSubGraphMap.keySet());
 
 		vertexCount = keyStore.size();
 		var i:Long = 0;
 
		while(vertexCount > 0){
			itr = tempStore.iterator();
 			while(itr.hasNext()){
 				keyStore.remove(itr.next());
 			}
 			itr = keyStore.iterator();
 			tempStore = new HashSet[Long]();
  				while(itr.hasNext()){
 				var vertexID:Long = itr.next();
 				var k:Long = countEdges(vertexID);
 				if(k <= kValue){
 					vertexIds.put(vertexID,kValue);
 					tempStore.add(vertexID);
 					removeVertexAndEdges(vertexID);
 					vertexCount--;
 				}
 			}
 			kValue = kValue + 1;
 		}
 		return vertexIds;
 	}

	//get the vertex count of original graph
	def getVertexCount(){

	 	var vertexCount:long = 0;
	 
	 	vertexCount = nativeStoreLocalSubGraphMap.keySet().size();
	 
	 	return vertexCount;
	}
	
	// get the count of edges of a paticular vertex
	def countEdges(vertexId:Long):Long{ 
	
 		var edgeCount:Long = 0;	
 
 		edgeCount = countEdgesInLocalSubGraphs(nativeStoreLocalSubGraphMap, vertexId)+
 					countEdgesInLocalSubGraphs(centralStoreLocalSubGraphMap, vertexId);
 
 	 	return edgeCount;
	}
 	// get the count in each subGraph
 	def countEdgesInLocalSubGraphs(store:HashMap[Long, HashSet[Long]],vertexId:Long):Long{
 		
 		var count:Long = 0;
 		val set:HashSet[Long] = store.get(vertexId);
 		if(set != null){
 			count = set.size();
 		}
 		
 		return count;
 	}
	
	// add the paticular vertex to the relavant file
	def addVertexToFile(vertexIdsToFile:ArrayList[Long], k:Long){
 		
 		val fileName:String = k+"-core";
		val file = new File(location+fileName);
		val printer = file.printer();
		//Console.OUT.println(attributeItem.getKey()+" "+attributeItem.getValue());
 		for(var i:Long = 0; i < vertexIdsToFile.size(); i++){
 			printer.println(vertexIdsToFile(i)+"");
 		}
 		printer.flush();
	
	    //Console.OUT.println("Write "+vertexId+"to the file"+k);
	}
	
	// remove the vertex and its edges from the original graph
 	def removeVertexAndEdges(vertexId:Long){

 		removeVertexAndEdgesFromLocalSubGraphs(nativeStoreLocalSubGraphMap, vertexId);
 		removeVertexAndEdgesFromLocalSubGraphs(centralStoreLocalSubGraphMap, vertexId);

 		//Console.OUT.println("Deleted "+vertexId);
	}
 
 	// remove vertices from a subgraph
 	def removeVertexAndEdgesFromLocalSubGraphs(var store:HashMap[Long, HashSet[Long]],vertexId:Long){
 
 		val valueStore:HashSet[Long] = new HashSet[Long]();
 		var itr:Iterator[HashMap.Entry[Long, HashSet[Long]]];
 
		var entry: HashMap.Entry[Long, HashSet[Long]]; 
		var key:Long = 0;
		var values:HashSet[Long]= new HashSet[Long]();
		store.delete(vertexId);
		itr  = store.entries().iterator();
		while(itr.hasNext()){
		 	entry = itr.next();
		 	key = entry.getKey();
		 	values = entry.getValue();
		 	if(values.contains(vertexId)){
		 		valueStore.add(key);
			}
		}
		val itrHS:Iterator[Long] = valueStore.iterator();
		while(itrHS.hasNext()){
			store.get(itrHS.next()).remove(vertexId);
 		}
 	}
 }
 
