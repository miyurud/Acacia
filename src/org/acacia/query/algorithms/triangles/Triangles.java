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

package org.acacia.query.algorithms.triangles;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.acacia.localstore.java.AcaciaHashMapLocalStore;
import org.acacia.localstore.java.AcaciaLocalStore;
import org.acacia.log.java.Logger_Java;
import org.acacia.server.AcaciaInstanceToManagerAPI;
import org.acacia.util.java.Utils_Java;

/**
 * @author miyuru
 *
 */
public class Triangles {
	public static String run(AcaciaLocalStore graphDB, String graphID, String partitionID, String serverHostName) {
		AcaciaHashMapLocalStore hMapLocalStore = new AcaciaHashMapLocalStore(Integer.parseInt(graphID), Integer.parseInt(partitionID));
		hMapLocalStore.loadGraph();
		HashMap<Long, HashSet<Long>> localSubGraphMap = hMapLocalStore.getUnderlyingHashMap();		
		long nEdges = hMapLocalStore.getEdgeCount();		
		Logger_Java.info("nedges : " + nEdges);		
		HashMap<Long, Long> degreeDist = hMapLocalStore.getOutDegreeDistributionHashMap();
		TreeMap<Long, TreeSet<Long>> degreeMap = new TreeMap<Long, TreeSet<Long>>(); //<degree><list of vertices>
		HashMap<Long, Long> degreeReverseLookupMap = new HashMap<Long, Long>();
		TreeSet<Long> degreeSet = null;
		long startVid = -1;
		long degree = -1;
		
		Iterator<Long> itr = degreeDist.keySet().iterator();
				
		while(itr.hasNext()){
			startVid = itr.next();
			degree = degreeDist.get(startVid);
			if(degreeMap.containsKey(degree)){
				degreeSet = degreeMap.get(degree);
				degreeSet.add(startVid);
				degreeMap.put(degree, degreeSet);
			}else{
				degreeSet = new TreeSet<Long>();
				degreeSet.add(startVid);
				degreeMap.put(degree, degreeSet);
			}
			
			degreeReverseLookupMap.put(startVid, degree);
		}
		
		//System.out.println("Started counting algorithm at : " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
		//Now we start count the traingles. But this happens only in the local graph.
		//This might be a naive method of doing this. Good for the moment... Sept 30 2014
		long traingleCount = 0;
		long v1 = 0;
		long v2 = 0;
		long v3 = 0;
		long fullCount = 0;
		
		HashMap<Long, HashMap<Long, ArrayList<Long>>> traingleTree = new HashMap<Long, HashMap<Long, ArrayList<Long>>>(); 
		ArrayList<Long> degreeListVisited = new ArrayList<Long>();

		for(Map.Entry<Long, TreeSet<Long>> item: degreeMap.entrySet()){
			TreeSet<Long> vVertices = item.getValue();

			//When one of these rounds completes we know that vertex vs has been completely explored for its traingles. Since we treat
			//a collection of vertices having the same degree at a time, we can do a degree based optimization. That is after this for loop
			//we can mark the degree as visited. Then for each second and thrid loops can be skipped if the vertices considered there are
			//having a degree that was marked before. Because we know that we have already visited all the vertices of that degree before.
			for(Long v : vVertices){
				HashSet<Long> uList = localSubGraphMap.get(v); //We know for sure that v has not been visited yet
				if(uList != null){ //Because in local subgraph map we may mark only u -> v, but v may not have corresponding record in the map (i.e., v -> u).
					for(long u : uList){//second for loop
//						if(degreeReverseLookupMap.containsKey(u)){
//							long degree = degreeReverseLookupMap.get(u);
//							if(degreeListVisited.contains(degree)){
//								//There is no point of considering the vertices of this degree. We have already counted traingales for all the
//								//vertices with this degree
//								continue;
//							}
//						}
						
						HashSet<Long> nuList = localSubGraphMap.get(u);
						
						if(nuList != null){
							for(long nu : nuList){ //Third for loop
//								if(degreeReverseLookupMap.containsKey(nu)){
//									long degree2 = degreeReverseLookupMap.get(nu);
//									if(degreeListVisited.contains(degree2)){
//										//There is no point of considering the vertices of this degree. We have already counted traingales for all the
//										//vertices with this degree
//										continue;
//									}
//								}
								HashSet<Long> nwList = localSubGraphMap.get(nu);
								
								if((nwList != null) && (nwList.contains(v))){ //We know for sure that v has not been visited yet
									fullCount++;							
									//At this point we have discovered a traingle. But now we need to makesure that we have not counted that triangle before.
									//To do that we use the traingle tree data structure which keeps on track of the triangles we have marked so far.
									//Note that the traingle tree may not be as efficient as we expect.
									
									//Here we have to be careful to first sort the three traingle vertices by their value. Then we do not
									//get into the trouble of having multiple combinations of the same three vertices.
									
									long[] tempArr = new long[]{v, u, nu};
									java.util.Arrays.sort(tempArr);
									
									v1 = tempArr[0];
									v2 = tempArr[1];
									v3 = tempArr[2];
									
									//The top level vertices are represented by v1
									HashMap<Long, ArrayList<Long>> itemRes = traingleTree.get(v1);
									
									if(itemRes != null){
										if(itemRes.containsKey(v2)){
											ArrayList<Long> lst = itemRes.get(v2);
											if(!lst.contains(v3)){
												lst.add(v3);
												itemRes.put(v2, lst);
												traingleTree.put(v1, itemRes);
												System.out.println("v1:" + v1 + " v2:" + v2 + " v3:" + v3 );
												traingleCount++;
//												sb2.append("" + v1 + " " + v2 + " " + v3 + "\n");
											}else{
												//We have discovered this traingle before. It is already recorded in the tree.
											}
										}else{
											//This means that the information of the second vertex is still not added to the traingle tree.
											//This is a fresh round of accounting for v2
											ArrayList<Long> newU = new ArrayList<Long>();
											newU.add(v3);
											itemRes.put(v2, newU);
											traingleTree.put(v1, itemRes);//here its just replacing the old itemRes
											
											System.out.println("v1:" + v1 + " v2:" + v2 + " v3:" + v3 );
											traingleCount++;
//											sb2.append("" + v1 + " " + v2 + " " + v3 + "\n");
										}
									}else{
										//This means that there is even no record of the v1. So we need to add everything from the scratch.
										itemRes = new HashMap<Long, ArrayList<Long>>();
										ArrayList<Long> newU = new ArrayList<Long>();
										newU.add(v3);
										itemRes.put(v2, newU);
										traingleTree.put(v1, itemRes); //here we are adding a new itemRes
										
										System.out.println("v1:" + v1 + " v2:" + v2 + " v3:" + v3 );
										traingleCount++;
//										sb2.append("" + v1 + " " + v2 + " " + v3 + "\n");
									}
								}
							}
						}
					}
				}
			}
			
			degreeListVisited.add(item.getKey());
		}
		
		traingleTree = null; //Here we enable the tree object to be garbage collected.
		//Next task is to run the algorithm on the global graph only.
//	    System.out.println("fullCount : " + fullCount);
//	    System.out.println("traingleCount : " + traingleCount);
//		System.out.println("Done counting algorithm at : " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
		
		//First we need to find out the id of the partition kept in this AcaciaInstance.
        //This can be done by reading the AcaciaInstance's catalog
        String dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
        String line = null;
        int partitionId = -1;
        
        //partitionId = Utils_Java.getPartitionIDFromCatalog(graphID);
        
//        if(partitionId==-1){
//        	return "-1";
//        }
		
        //The following method may return the count of traingles that intersect between the local graph and the world.
//        traingleCount += AcaciaInstanceToManagerAPI.countIntersectingTraingles(serverHostName, graphID, partitionId);
        System.out.println("serverHostName : " + serverHostName);
        long intersectCount = AcaciaInstanceToManagerAPI.countIntersectingTraingles(serverHostName, graphID, partitionID, localSubGraphMap, degreeMap, degreeReverseLookupMap);
        System.out.println("intersect traingle Count : " + intersectCount);
        traingleCount += intersectCount;
		return "" + traingleCount;
	}
}
