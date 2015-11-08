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
import org.acacia.util.java.Utils_Java;

import x10.util.HashMap;
import x10.util.HashSet;
import x10.util.Map.Entry;
import x10.util.Set;
import x10.io.File;

import org.acacia.util.Utils;
import org.apache.commons.io.FileUtils;

/**
 * Class KCore
 */
public class KCore {

 	private val location = Utils.call_getAcaciaProperty("org.acacia.server.runtime.location")+"/KCoreFiles/";
	private var baseDir:String  = "";
 	private var localSubGraphMap:HashMap[Long, HashSet[Long]];
    private var KCorenessOfGraph:Long = 0;
    
    public def this() {
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

 	public def loadGraphData(graphID:String, partitionID:String, placeID:String) {
 
 		baseDir = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
 
 		//native store
 		val nativeStore:AcaciaHashMapNativeStore = new AcaciaHashMapNativeStore(Int.parse(graphID), Int.parse(partitionID),baseDir,false);
 		createARelationshipMapCopy(nativeStore, 0); 
		//central store
 		//val centralStore:AcaciaHashMapNativeStore = new AcaciaHashMapNativeStore(Int.parse(graphID), Int.parse(placeID),baseDir,true, Int.parse(placeID));

 	}
 
	//Create a copy of the graph
	public def createARelationshipMapCopy(val store:AcaciaHashMapNativeStore, val graphId:Long){
	 	//Console.OUT.println("Create a copy of relationshipMap of graph "+graphId);
        
 		localSubGraphMap = store.getlocalSubGraphMap(); 
	}
	
	// calculate K Coreness value 
	public def calculateKCoreness(){
	
	 	var edgesCount:Long = 0n;
	    var count:Long = 1;
	    var vertexIds:Rail[Long];
	    var vertexIdsWriteToFile:Rail[Long];
	    var vertexCount:Long = 0;
	    var endOfArray:Long;
	    
	    vertexCount = getVertexCount();
	    // algorithm
	    while(vertexCount > 0){
	    	vertexIds = getVertexIdsOfSameKCoreness(count);
	    	vertexIdsWriteToFile = new Rail[Long](vertexCount);
	    	endOfArray = 0;
		    while(vertexIds.size > 0){
			    for(var i:Long = 0; i< vertexIds.size; i+1){
			    	vertexIdsWriteToFile(i+endOfArray);
			        removeVertexAndEdges(vertexIds(i));
			    }
			    vertexCount -= vertexIds.size;
			    vertexIds = getVertexIdsOfSameKCoreness(count);
			    endOfArray += vertexIds.size;
		    }
		    addVertexToFile(vertexIdsWriteToFile, count);
		    count++;
	    }
	    KCorenessOfGraph = --count;
	}
	
	// from the graph get the vertices which has the same degree 
	public def getVertexIdsOfSameKCoreness(count:Long):Rail[Long]{
	
  		var countVertexIds:Int = 0n;
	 	var vertexIds:Rail[Long]=null;
	 	var itr:Iterator[Long]  = localSubGraphMap.keySet().iterator();
		while(itr.hasNext()){
		 	vertexID:Long = itr.next();
		 	k:Long = countEdges(vertexID);
		 	if(k == count){
		 		vertexIds(countVertexIds) = vertexID;
		 	}
		 	countVertexIds++;
		 }
	    
	 	// Console.OUT.println("Call countEdges which returns k value of a vertex");
	  	// Console.OUT.println("Save them to a HashMap");
	 	// Console.OUT.println("Return vertexIds array which has the same degree "+count);
	
	 	return vertexIds;
	}
	
	//get the vertex count of original graph
	public def getVertexCount(){
	 	var vertexCount:long = 0;
	 	vertexCount = localSubGraphMap.keySet().size();
	 	return vertexCount;
	}
	
	// get the count of edges of a paticular vertex
	public def countEdges(vertexId:Long):Long{ 
	
 		var k:Long =0n;
		var itr:x10.lang.Iterator[x10.util.HashMap.Entry[Long, x10.util.HashSet[Long]]] = localSubGraphMap.entries().iterator();
				 
		while(itr.hasNext()){
			 var entry:x10.util.HashMap.Entry[Long, x10.util.HashSet[Long]] = itr.next();
			 var key:Long = entry.getKey();
			 if(key == vertexId){
			 	var hs:x10.util.HashSet[Long] = entry.getValue() as x10.util.HashSet[Long];
			    k = hs.size();
			 }
		}
	 	return k;
	}
	
	// add the paticular vertex to the relavant file
	public def addVertexToFile(vertexIdsToFile:Rail[Long], k:Long){
 		
 		val fileName:String = k+"-core";
		val file = new File(location+fileName);
		val printer = file.printer();
		//Console.OUT.println(attributeItem.getKey()+" "+attributeItem.getValue());
 		for(var i:Long = 0; i < vertexIdsToFile.size; i++){
 			printer.println(vertexIdsToFile(i)+"");
 		}
 		printer.flush();
	
	    //Console.OUT.println("Write "+vertexId+"to the file"+k);
	}
	
	// remove the vertex and its edges from the original graph
	public def removeVertexAndEdges(vertexId:Long){

		var itr:x10.lang.Iterator[x10.util.HashMap.Entry[Long, x10.util.HashSet[Long]]] = localSubGraphMap.entries(). iterator();
		while(itr.hasNext()){
			var entry:x10.util.HashMap.Entry[Long, x10.util.HashSet[Long]] = itr.next();
			var key:Long = entry.getKey();
			if(key == vertexId){
 				localSubGraphMap.remove(vertexId);
			}
		}

 		//Console.OUT.println("Deleted "+vertexId);
	}
	
	// when required the k core value of a paticular vertex return it to the outside
	public def getKCoreValueOfAVertex(vertexId:Long):Long{
	
	    var kCoreness:Long = 0;
	    
	    return kCoreness;
	}

}
