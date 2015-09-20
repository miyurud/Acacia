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

/**
 * Class KCore
 */
public class KCore {

	//Create a copy of the graph
	public def createARelationshipMapCopy(graphId:String){
	 	Console.OUT.println("Create a copy of relationshipMap of graph "+graphId);
	}
	
	// calculate K Coreness value 
	public def calculateKCoreness(){
	
	 	var edgesCount:Int = 0n;
	    var count:Int = 1n;
	    var vertexIds:Rail[Int]=null;
	    var vertexCount:int;
	    
	    createARelationshipMapCopy("");
	    vertexCount = getVertexCount();
	    
	    while(vertexCount>0){
	    	vertexIds = getVertexIdsOfSameKCoreness(count);
		    while(vertexIds.size > 0){
			    for(var i:Int = 0n; i< vertexIds.size; i+1){
			    	addVertexToFile(vertexIds(i), count);
			        removeVertexAndEdges(vertexIds(i));
			    }
			    vertexCount -= vertexIds.size;
			    vertexIds = getVertexIdsOfSameKCoreness(count);
		    }
		    count++;
	    }
	}
	
	// from the graph get the vertices which has the same degree 
	public def getVertexIdsOfSameKCoreness( k:Int):Rail[Int]{
	
	 	var vertexIds:Rail[Int]=null;
	    
	 	Console.OUT.println("Call countEdges which returns k value of a vertex");
	    Console.OUT.println("Save them to a HashMap");
	 	Console.OUT.println("Return vertexIds array which has the same degree "+k);
	
	 	return vertexIds;
	}
	
	//get the vertex count of original graph
	public def getVertexCount(){
	 	var vertexCount:int = 20n;
	 	return vertexCount;
	}
	
	// get the count of edges of a paticular vertex
	public def countEdges(vertexId:Int):Int{ 
	
	 	 var k:Int =1n;
	 	 Console.OUT.println("Get the degree of the "+vertexId+ " and return it as the value "+k);
	 	 return k;
	
	}
	
	// add the paticular vertex to the relavant file
	public def addVertexToFile(vertexId:Int, k:Int){
	
	 	  Console.OUT.println("Write "+vertexId+"to the file"+k);
	}
	
	// remove the vertex and its edges from the original graph
	public def removeVertexAndEdges(vertexId:Int){
	
	 	Console.OUT.println("Deleted "+vertexId+" and edgesIds");
	
	}
	
	// when required the k core value of a paticular vertex return it to the outside
	public def getKCoreValueOfAVertex(vertexId:Int):Int{
	
	    var kCoreness:Int = 0n;
	    
	 	return kCoreness;
	}

}