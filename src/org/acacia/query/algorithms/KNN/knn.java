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

package org.acacia.query.algorithms.KNN;

import java.util.HashMap;
import java.util.HashSet;

import org.acacia.localstore.java.AcaciaHashMapLocalStore;
import org.acacia.localstore.java.AcaciaLocalStore;
import org.acacia.log.java.Logger_Java;
import org.acacia.server.AcaciaInstanceToManagerAPI;
import org.acacia.util.java.Utils_Java;

public class knn 
{
	public static HashSet<Long> run(AcaciaLocalStore graphDB, String serverHostName, Long vertexID) {
		Long kValue = Long.parseLong(Utils_Java.getAcaciaProperty("org.acacia.query.algorithms.knn.k_value"));
		return run(graphDB, null, null, serverHostName,kValue,vertexID);
	}	
	
	public static HashSet<Long> run(AcaciaLocalStore graphDB, String graphID, String partitionID, String serverHostName, Long vertexID) {
		Long kValue = Long.parseLong(Utils_Java.getAcaciaProperty("org.acacia.query.algorithms.knn.k_value"));
		return run(graphDB, graphID, partitionID, serverHostName,kValue,vertexID);
	}
	
	public static HashSet<Long> run(AcaciaLocalStore graphDB, String graphID, String partitionID, String serverHostName, Long kValue, Long vertexID) {
		HashMap<Long, HashSet<Long>> localSubGraphMap = graphDB.getUnderlyingHashMap();	
		
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
		
		System.out.println("Started KNN algorithm at : " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
		//Now we start creating K-nearest nabour graph. But this happens only in the local graph.
		//Currently this returns only the k-nearest vertices.
		//Can improve to return a graph that containd k-nearest vertices.
		
		HashSet<Long> neighboursPrev = localSubGraphMap.get(vertexID);
		HashSet<Long> result = neighboursPrev;
		
		for(int i=1;i<kValue;i++)
		{
			HashSet<Long> neighboursCurrent = null;
			if(neighboursPrev != null)
			{
				for(long v : neighboursPrev)
				{
					HashSet<Long> neighbourSet = localSubGraphMap.get(v);
					if(neighbourSet != null)
					{
						if(neighboursCurrent == null)
						{
							neighboursCurrent = neighbourSet;
						}
						else
						{
							neighboursCurrent.addAll(neighbourSet);
						}
					}
				}
			}
			if(neighboursCurrent != null)
			{
				neighboursPrev = neighboursCurrent;
				result.addAll(neighboursPrev);
			}			
		}
		
		//Next task is to run the algorithm on the global graph only.
		
		System.out.println("Done knn algorithm for local graph at : " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
		
		//First we need to find out the id of the partition kept in this AcaciaInstance.
        //This can be done by reading the AcaciaInstance's catalo
		
        //The following method may return the count of traingles that intersect between the local graph and the world.
//        if(serverHostName != null){
//        	//System.out.println("serverHostName : " + serverHostName);
//        	long intersectCount = AcaciaInstanceToManagerAPI.countIntersectingTraingles(serverHostName, graphID, partitionID, localSubGraphMap, degreeMap, degreeDist); //degreeReverseLookupMap=degreeDist
//        	System.out.println("intersect traingle Count : " + intersectCount);
//        	traingleCount += intersectCount;
//        }
		return result;
	}
}