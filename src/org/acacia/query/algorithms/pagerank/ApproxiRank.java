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

package org.acacia.query.algorithms.pagerank;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;
import org.acacia.localstore.java.AcaciaHashMapLocalStore;
import org.acacia.localstore.java.AcaciaLocalStore;
import org.acacia.server.java.AcaciaInstanceProtocol;
import org.acacia.server.java.AcaciaInstanceToManagerAPI;

public class ApproxiRank {
	/**
	 * 
	 * @param EntireGraphSize
	 * @param localGraphSize
	 * @return Returning a String from this method is a stupid idea. But for the moment have to do this.
	 */
	public static String run(String graphID, String partitionID, AcaciaLocalStore graphDBtmp, String peerHost, String serverHostName, int k) {
		int localGraphSize = 0; //This needs to be calculated
		int entireGraphSize = 0;
		
		//Just for the moment we cast the localStore to Hashmap local store
		AcaciaHashMapLocalStore graphDB = (AcaciaHashMapLocalStore)graphDBtmp;
		
//		//With the received graph database instance first we discover the out degree distribution for the graph
//		ExecutionEngine engine = new ExecutionEngine(graphDB);	
//		//Here the resMap will contain the local degree distribution.
//		HashMap<Integer, Integer> resMap = new HashMap<Integer, Integer>(); 
//		
//		//Here we get the out degree distribution
//		ExecutionResult execResult = engine.execute("start n=node(*) match n--m return n, n.vid, count(m)");
//		
//		for(Map<String, Object> row : execResult){	
//			int vid = -1;
//			int oDeg = -1;
//			
//			for(Entry<String, Object> column : row.entrySet()){
//					if(column.getKey().equals("n.vid")){
//						vid = Integer.parseInt("" + column.getValue());
//					}
//					
//					if(column.getKey().equals("count(m)")){
//						oDeg = Integer.parseInt(""+column.getValue());
//					}
//			}
//			
//			if(resMap.containsKey(vid)){
//					int v = resMap.get(vid);
//					v = v + oDeg;
//					resMap.put(vid, v);
//			}else{
//				localGraphSize++;
//				resMap.put(vid, oDeg);
//			}
//		}
		
		HashMap<Long, Long> resMap = graphDB.getOutDegreeDistributionHashMap();
		
		//Here we get all the edges of the local graph
//		execResult = engine.execute("start n=node(*) match n--m return n, n.vid, m.vid");
//		HashMap<Integer, ArrayList> localSubGraphMap = new HashMap<Integer, ArrayList>();
//		HashSet<Integer> uniqueVertexList = new HashSet<Integer>();
//		for(Map<String, Object> row : execResult){	
//			int startVid = -1;
//			int endVid = -1;
//			
//			for(Entry<String, Object> column : row.entrySet()){
//					if(column.getKey().equals("n.vid")){
//						startVid = Integer.parseInt(column.getValue().toString());
//					}
//					
//					if(column.getKey().equals("m.vid")){
//						endVid = Integer.parseInt(column.getValue().toString());
//					}
//					
//					if((startVid != -1)&&(endVid != -1)){
//						if(localSubGraphMap.containsKey(startVid)){
//							ArrayList lst = localSubGraphMap.get(startVid);
//							lst.add(endVid);
//							localSubGraphMap.put(startVid, lst);
//						}else{
//							ArrayList lst = new ArrayList();
//							lst.add(endVid);
//							localSubGraphMap.put(startVid, lst);
//						}
//												
//						uniqueVertexList.add(startVid);
//						
//						uniqueVertexList.add(endVid);
//						
//						startVid = -1;
//						endVid = -1;
//					}
//			}		
//		}
		
		HashSet<Long> uniqueVertexList = new HashSet<Long>();
		HashMap<Long, HashSet<Long>> localSubGraphMap = graphDB.getUnderlyingHashMap();
		uniqueVertexList = graphDB.getVertexList();
		
        String[] hostArr = peerHost.split(",");
        System.out.println("++++++++++++--->1");
        //Next we count the total vertex count
        
        for(String host : hostArr){
        	//Here we get the number of vertices located on the local graph
    		long result = -1;
    		try{
    			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
    			PrintWriter out = new PrintWriter(socket.getOutputStream());
    			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			String response = "";

    			out.println(AcaciaInstanceProtocol.COUNT_VERTICES);
    			out.flush();

    			response = reader.readLine();
    			
    			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
    				//System.out.println("host : " + host + " graphID : " + graphID);
    				out.println(graphID);
    				out.flush();
    			}else{
    				result = -1;
    			}
    			
    			response = reader.readLine();
    			result = Long.parseLong(response);    			

    			out.close();    			
    		}catch(UnknownHostException e){
    			Logger_Java.error("Connecting to host (8) " + host + " got error message : " + e.getMessage());
    		}catch(IOException ec){
    			Logger_Java.error("Connecting to host (8) " + host + " got error message : " + ec.getMessage());
    		}		
    		
    		if(result == -1){
    			entireGraphSize = -1;
    			break;
    		}else{
    			entireGraphSize += result;
    		}
        }        
                       
        if(entireGraphSize == -1){
        	//cannot proceed further
        	return "-1";
        }
        
		int D = entireGraphSize - localGraphSize;
        
        //At this point we have the local only degree distribution
        
        //Next step is to check which local vertices have the connectivity with the external world. This can be done by
        //refering the HSQLDB database
        
        //First we need to find out the id of the partition kept in this AcaciaInstance.
        //This can be done by reading the AcaciaInstance's catalog		
//		int partitionId = Utils_Java.getPartitionIDFromCatalog(graphID);
        
        //Next we request and get all the local to world degree distribution. This will be a list of vertices of this
        //graph partition which are connected with the extrenal world, each vertex will have its associated out degree.
        
        //AcaciaInstanceToManagerAPI
        //String serverHost, int graphID, int partitionID
        serverHostName = Utils_Java.getServerHost();
        
        //Next we need to obtain the authoruty flow for the world. May be this should be done only once per sub graph unless the
        //graph does not change
        double authFlow = AcaciaInstanceToManagerAPI.getAuthorityFlowWorldOnly(serverHostName, graphID, partitionID); 
        
        //2/8/2014
        //Now the problem is how to map the unique vertex id in the original graph to an id in the local graph?
        //Either we will have to construct an adjacency matrix of the same size to the entire graph or have to
        //convert the orginal ids to corresponding local ids.
        
        localGraphSize = uniqueVertexList.size();

        //int[] AapproxOutDegree = new int[localGraphSize + 1];
        double alpha = 0.85;
        double damp = 1 - alpha;
        
        int M = localGraphSize + 1;
        //float[][] AapproxTransitionProbMatrix = new float[M][M]; //If we use double here we get heap out of memory exception
        //+Note on 15th Sept 2014 : In the case of Top-K Pagerank scenario theGraphDatabaseServicere is no point of creating an entire adjacency matrix
        //in the JVM. Even if we create such matrix we may get Java Heap Out of Memory Error.(E.g., in the case of Hyves graph it will be (353725^2*4) Bytes)
        //Have to find solution for this.
        
        float[] AapproxTransitionProbMatrixOneRow = new float[M];
        
        int[] adjacencyIndex = new int[M];
        int counter = 0;

        for(long item : uniqueVertexList){
        	adjacencyIndex[counter] = (int)item;
        	counter++;
        }
        //The last item we assign -1 to represent the entire world
        adjacencyIndex[localGraphSize] = -1;
        
		int i = 0, j = 0;
		int startVid = 0;
		int endVid = 0;

        //The following is the degree distribution from local to world(lambda)
        HashMap hmapLambdaOutDegreeDistribution = AcaciaInstanceToManagerAPI.getLambdaOutDegreeDistribution(serverHostName, graphID, partitionID);
        //The following is the degree distribution from world(lambda) to local
        HashMap hmapLambdaInDegreeDistribution = AcaciaInstanceToManagerAPI.getLambdaInDegreeDistribution(serverHostName, graphID, partitionID);        
        HashMap<Long, Float> hMapAuthorityFlowWorldToLocal = AcaciaInstanceToManagerAPI.getAuthorityFlowWorldToLocal(serverHostName, graphID, partitionID);
        HashMap<Long, Float> hMapAuthorityFlowLocalToWorld = AcaciaInstanceToManagerAPI.getAuthorityFlowLocalToWorld(serverHostName, graphID, partitionID);
        
//        HashMap<Long, ArrayList<Long>> hMapFromVerticesWorldToLocalFLow = AcaciaInstanceToManagerAPI.getLambdaToLocalFlowFromVertices(serverHostName, graphID, partitionId);
//        System.out.println("------- Done step 5.54 --------");
        
        TreeMap<Double, Double> resTreeMap = new TreeMap<Double, Double>();
        int T = 2; //the number of iterations
        float mu = (float)(damp/entireGraphSize);//P_{idela}[i] if page i is local is 1/N, (1-alpha)P_{ideal} is
        for(i = 0; i < M; i++){ //This is for each and every vertex
	        	if(i < localGraphSize){//First quadrent
	        		//This obj contains the out degree of the vertex adjacencyIndex[i]  
	        		Long obj = resMap.get((long)adjacencyIndex[i]);
	        		if(obj != null){
	        	        HashSet<Long> lst = localSubGraphMap.get(adjacencyIndex[i]);        	        
	        	        int numEdges = lst.size();
	        	        double rank = 1.0;
	        	        //for (int t = 0; t < T; t++) {
	        	        for(int u = 0; u < numEdges; u++){
	        	        	rank += (float)((alpha/obj) + mu);
	        	        }
	        	        	        	        
	                    if(!resTreeMap.isEmpty()){
		                	if(resTreeMap.size() >= k){
		    	            	double lkey = (double)resTreeMap.lastKey();
		    	            	if(rank > lkey){
		    	            		resTreeMap.remove(lkey);
		    	            		resTreeMap.put(rank, (double)i);
		    	            	}
		                	}else{
		                		resTreeMap.put(rank, (double)i);
		                	}
		                }else{
		                	resTreeMap.put(rank, (double)i);
		                }	        	        
	        		}
	        	}else if(i == localGraphSize){ 
	        		//Here we need the list of edges that comes to the local graph from the world
	        		
	        		//Third quadrent
	        		//Float obj = hMapAuthorityFlowWorldToLocal.get(adjacencyIndex[i]);//The values in this HashMap are indexed based on the toID
	        		Iterator it = hMapAuthorityFlowWorldToLocal.entrySet().iterator();
	        		
	        		while(it.hasNext()){
	        			Map.Entry<Long, Float> pairs = (Map.Entry<Long, Float>)it.next();
	        			
//		        		if(obj != null){
		        			double rank = (double)((alpha/pairs.getValue()) + mu); //Here we just assume the authority flow from the world to local determines the pagerank socore of this page.
		        			
		        			//The following part of the code finds the top k ranks
		                    if(!resTreeMap.isEmpty()){
			                	if(resTreeMap.size() >= k){
			    	            	double lkey = (double)resTreeMap.lastKey();
			    	            	if(rank > lkey){
			    	            		resTreeMap.remove(lkey);
			    	            		resTreeMap.put(rank, (double)pairs.getKey());
			    	            	}
			                	}else{
			                		resTreeMap.put(rank, (double)pairs.getKey());
			                	}
			                }else{
			                	resTreeMap.put(rank, (double)pairs.getKey());
			                }
	        		}
	        	}else if(i > localGraphSize){ 
	        		
	        		//Fourth quadrent
	        		resTreeMap.put((double)((alpha/authFlow) + mu), (double)-1);
	        	}
        	}
        	   
        
        if(k==-1){
        	//This is general PageRank
	        StringBuilder sb = new StringBuilder();

	        //Just for the moment say to be implemented
	        sb.append("to be implemented...");
	        return sb.toString();
        }else{
        	//This is Top-K PageRank
        	StringBuilder sb = new StringBuilder();
        	Set set = resTreeMap.entrySet();
        	Iterator itrfinal = set.iterator();
        	while(itrfinal.hasNext()){
        		Map.Entry entr = (Map.Entry)itrfinal.next();
	        	sb.append(entr.getKey());
	        	sb.append(",");
	            sb.append(entr.getValue());
	            sb.append(";");
        	}
        	
        	return sb.toString();
        }
	}

}
