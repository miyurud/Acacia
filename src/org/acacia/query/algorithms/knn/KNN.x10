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

package org.acacia.query.algorithms.knn;

import x10.util.HashMap;
import x10.util.HashSet;

import org.acacia.localstore.AcaciaHashMapLocalStore;
import org.acacia.localstore.AcaciaLocalStore;
import org.acacia.log.Logger;
import org.acacia.server.AcaciaInstanceToManagerAPI;
import org.acacia.util.java.Utils_Java;

public class KNN {

//Oct31, 2015 - commeting out the KNN class just for the moment
/*
    public static def run(val graphDB:AcaciaLocalStore, val serverHostName:String, val kValue:Int, val vertexID:Long) : HashSet {
        return run(graphDB, null, null, serverHostName, kValue, vertexID);
    }

	public static def run(val graphDB:AcaciaLocalStore, val graphID:String, val partitionID:String, val serverHostName:String, val kValue:Int, val vertexID:Long) : HashSet {
		val localSubGraphMap:HashMap = graphDB.getUnderlyingHashMap();	
		
		//How to get the list of all the vertices?
		
		
//		long nEdges = graphDB.getEdgeCount();		
//		HashMap<Long, Long> degreeDist = graphDB.getOutDegreeDistributionHashMap();// <VertexID,Size of the vertex (degree)>
//		TreeMap<Long, TreeSet<Long>> degreeMap = new TreeMap<Long, TreeSet<Long>>(); //<degree><list of vertices>		
//		TreeSet<Long> degreeSet = null;
//		long startVid = -1;
//		long degree = -1;
		
//		Iterator<Long> itr = degreeDist.keySet().iterator();
//		// Map vertices according to degrrees		
//		while(itr.hasNext()){
//			startVid = itr.next();
//			degree = degreeDist.get(startVid);
//			if(degreeMap.containsKey(degree)){
//				degreeSet = degreeMap.get(degree);
//				degreeSet.add(startVid);
//				degreeMap.put(degree, degreeSet);
//			}else{
//				degreeSet = new TreeSet<Long>();
//				degreeSet.add(startVid);
//				degreeMap.put(degree, degreeSet);
//			}			
//		}
		
		//System.out.println("Started KNN algorithm at : " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
		//Now we start creating K-nearest nabour graph. But this happens only in the local graph.
		//Currently this returns only the k-nearest vertices.
		//Can improve to return a graph that containd k-nearest vertices.
		
		var neighboursPrev:HashSet = localSubGraphMap.get(vertexID) as HashSet;
		val result:HashSet = neighboursPrev;
		
		for(var i:Int = 1n; i < kValue; i++){
			var neighboursCurrent:HashSet = null;

			if(neighboursPrev != null){
                var itr:java.util.Iterator = neighboursPrev.iterator();
                while(itr.hasNext()){
                  	var v:Long = itr.next() as Long;
					val neighbourSet:HashSet = localSubGraphMap.get(v) as HashSet;
	
					if(neighbourSet != null) {
						if(neighboursCurrent == null) {
							neighboursCurrent = neighbourSet;
						} else {
							neighboursCurrent.addAll(neighbourSet);
						}
					}
				}

				if(neighboursCurrent != null){
					neighboursPrev = neighboursCurrent;
					result.addAll(neighboursPrev);
				}			
			}
		}
		
		//Next task is to run the algorithm on the global graph only.
		
		//System.out.println("Done knn algorithm for local graph at : " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
		
		//First we need to find out the id of the partition kept in this AcaciaInstance.
        //This can be done by reading the AcaciaInstance's catalog
		
        //The following method may return the count of traingles that intersect between the local graph and the world.
//        if(serverHostName != null){
//        	//System.out.println("serverHostName : " + serverHostName);
//        	long intersectCount = AcaciaInstanceToManagerAPI.countIntersectingTraingles(serverHostName, graphID, partitionID, localSubGraphMap, degreeMap, degreeDist); //degreeReverseLookupMap=degreeDist
//        	System.out.println("intersect traingle Count : " + intersectCount);
//        	traingleCount += intersectCount;
//        }
		return result;
	}
 * */
}