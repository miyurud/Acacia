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

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;
import org.acacia.localstore.AcaciaHashMapLocalStore;
import org.acacia.localstore.AcaciaLocalStore;
import org.acacia.server.java.AcaciaInstanceProtocol;
import org.acacia.server.AcaciaInstanceToManagerAPI;
import x10.util.HashMap;
import x10.util.HashSet;
import x10.util.ArrayList;
import java.util.TreeMap;
import x10.util.StringBuilder;
import java.util.Set;

public class ApproxiRank {
	/**
	 * 
	 * @param EntireGraphSize
	 * @param localGraphSize
	 * @return Returning a String from this method is a stupid idea. But for the moment have to do this.
	 */
	public static def run(graphID:String, partitionID:String, graphDBtmp:AcaciaLocalStore, peerHost:String,var serverHostName:String, k:int):String{
		var localGraphSize:int = 0n; //This needs to be calculated
		var entireGraphSize:int = 0n;
		
		//Just for the moment we cast the localStore to Hashmap local store
		val graphDB:AcaciaHashMapLocalStore = graphDBtmp as AcaciaHashMapLocalStore;
		
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
		
		val resMap:HashMap[Long, Long] = graphDB.getOutDegreeDistributionHashMap();
		
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
		
		var uniqueVertexList:HashSet[Long] = new HashSet[Long]();
		val localSubGraphMap:HashMap[Long, HashSet[Long]] = graphDB.getUnderlyingHashMap();
		uniqueVertexList = graphDB.getVertexList();
		
        val hostArr:Rail[String] = peerHost.split(",");
        Console.OUT.println("++++++++++++--->1");
        //Next we count the total vertex count
        
        for(host:String in hostArr){
        	//Here we get the number of vertices located on the local graph
    		var result:long = -1;
    		try{
    			val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
    			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
    			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			var response:String = "";

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
    		}catch(e:UnknownHostException){
    			Logger_Java.error("Connecting to host (8) " + host + " got error message : " + e.getMessage());
    		}catch(ec:IOException){
    			Logger_Java.error("Connecting to host (8) " + host + " got error message : " + ec.getMessage());
    		}		
    		
    		if(result == -1){
    			entireGraphSize = -1n;
    			break;
    		}else{
    			entireGraphSize += result;
    		}
        }        
                       
        if(entireGraphSize == -1n){
        	//cannot proceed further
        	return "-1";
        }
        
		val D:int = entireGraphSize - localGraphSize;
        
        //At this point we have the local only degree distribution
        
        //Next step is to check which local vertices have the connectivity with the external world. This can be done by
        //refering the HSQLDB database
        
        //First we need to find out the id of the partition kept in this AcaciaInstance.
        //This can be done by reading the AcaciaInstance's catalog		
		val partitionId:int = Utils_Java.getPartitionIDFromCatalog(Int.parseInt(graphID));
        
        //Next we request and get all the local to world degree distribution. This will be a list of vertices of this
        //graph partition which are connected with the extrenal world, each vertex will have its associated out degree.
        
        //AcaciaInstanceToManagerAPI
        //String serverHost, int graphID, int partitionID
        serverHostName = Utils_Java.getServerHost();
        
        //Next we need to obtain the authoruty flow for the world. May be this should be done only once per sub graph unless the
        //graph does not change
        val authFlow:double = AcaciaInstanceToManagerAPI.getAuthorityFlowWorldOnly(serverHostName, graphID, partitionID); 
        
        //2/8/2014
        //Now the problem is how to map the unique vertex id in the original graph to an id in the local graph?
        //Either we will have to construct an adjacency matrix of the same size to the entire graph or have to
        //convert the orginal ids to corresponding local ids.
        
        localGraphSize = uniqueVertexList.size() as Int;

        //int[] AapproxOutDegree = new int[localGraphSize + 1];
        val alpha:double = 0.85;
        val damp:double = 1 - alpha;
        
        val M:int = localGraphSize + 1n;
        //float[][] AapproxTransitionProbMatrix = new float[M][M]; //If we use double here we get heap out of memory exception
        //+Note on 15th Sept 2014 : In the case of Top-K Pagerank scenario theGraphDatabaseServicere is no point of creating an entire adjacency matrix
        //in the JVM. Even if we create such matrix we may get Java Heap Out of Memory Error.(E.g., in the case of Hyves graph it will be (353725^2*4) Bytes)
        //Have to find solution for this.
        
        val AapproxTransitionProbMatrixOneRow:Rail[float] = new Rail[float](M);
        
        val adjacencyIndex:Rail[Int] = new Rail[Int](M);
        var counter:int = 0n;

        for(item:long in uniqueVertexList){
        	adjacencyIndex(counter) = item as Int;
        	counter++;
        }
        //The last item we assign -1 to represent the entire world
        adjacencyIndex(localGraphSize) = -1n;
        
		var i:int = 0n;
 		var j:int = 0n;
		var startVid:int = 0n;
		var endVid:int = 0n;

        //The following is the degree distribution from local to world(lambda)
        val hmapLambdaOutDegreeDistribution:HashMap[String,String] = AcaciaInstanceToManagerAPI.getLambdaOutDegreeDistribution(serverHostName, graphID, partitionID);
        //The following is the degree distribution from world(lambda) to local
        val hmapLambdaInDegreeDistribution:HashMap[String,String] = AcaciaInstanceToManagerAPI.getLambdaInDegreeDistribution(serverHostName, graphID, partitionID);        
       	val hMapAuthorityFlowWorldToLocal:HashMap[Long, Float] = AcaciaInstanceToManagerAPI.getAuthorityFlowWorldToLocal(serverHostName, graphID, partitionID);
        val hMapAuthorityFlowLocalToWorld:HashMap[Long, Float] = AcaciaInstanceToManagerAPI.getAuthorityFlowLocalToWorld(serverHostName, graphID, partitionID);
        
       	val hMapFromVerticesWorldToLocalFLow:HashMap[Long, ArrayList[Long]] = AcaciaInstanceToManagerAPI.getLambdaToLocalFlowFromVertices(serverHostName, graphID, partitionID);
       	Console.OUT.println("------- Done step 5.54 --------");
        
        val resTreeMap:TreeMap = new TreeMap();
        val T:int = 2n; //the number of iterations
        val mu:float = (damp/entireGraphSize) as float;//P_{idela}[i] if page i is local is 1/N, (1-alpha)P_{ideal} is
        for(i = 0n; i < M; i++){ //This is for each and every vertex
	        	if(i < localGraphSize){//First quadrent
	        		//This obj contains the out degree of the vertex adjacencyIndex[i]  
	        		val obj:Long = resMap.get(adjacencyIndex(i) as Long);
	        		if(obj != -1){
	        	        val lst:HashSet[Long] = localSubGraphMap.get(adjacencyIndex(i));        	        
	        	        val numEdges:int = lst.size() as Int;
	        	        var rank:double = 1.0;
	        	        //for (int t = 0; t < T; t++) {
	        	        for(var u:int = 0n; u < numEdges; u++){
	        	        	rank += ((alpha/obj) + mu) as Float;
	        	        }
	        	        	        	        
	                    if(!resTreeMap.isEmpty()){
		                	if(resTreeMap.size() >= k){
		    	            	val lkey:double = resTreeMap.lastKey() as double;
		    	            	if(rank > lkey){
		    	            		resTreeMap.remove(lkey);
		    	            		resTreeMap.put(rank, i as double);
		    	            	}
		                	}else{
		                		resTreeMap.put(rank, i as double);
		                	}
		                }else{
		                	resTreeMap.put(rank, i as double);
		                }	        	        
	        		}
	        	}else if(i == localGraphSize){ 
	        		//Here we need the list of edges that comes to the local graph from the world
	        		
	        		//Third quadrent
	        		//Float obj = hMapAuthorityFlowWorldToLocal.get(adjacencyIndex[i]);//The values in this HashMap are indexed based on the toID
	        		val it:Iterator[x10.util.Map.Entry[Long,Float]] = hMapAuthorityFlowWorldToLocal.entries().iterator();
	        		
	        		while(it.hasNext()){
	        			val pairs:x10.util.Map.Entry[Long, Float] = it.next();
	        			
//		        		if(obj != null){
		        			val rank:double = ((alpha/pairs.getValue()) + mu) as double; //Here we just assume the authority flow from the world to local determines the pagerank socore of this page.
		        			
		        			//The following part of the code finds the top k ranks
		                    if(!resTreeMap.isEmpty()){
			                	if(resTreeMap.size() >= k){
			    	            	val lkey:double = resTreeMap.lastKey() as double;
			    	            	if(rank > lkey){
			    	            		resTreeMap.remove(lkey);
			    	            		resTreeMap.put(rank, pairs.getKey() as double);
			    	            	}
			                	}else{
			                		resTreeMap.put(rank, pairs.getKey() as double);
			                	}
			                }else{
			                	resTreeMap.put(rank, pairs.getKey() as double);
			                }
	        		}
	        	}else if(i > localGraphSize){ 
	        		
	        		//Fourth quadrent
	        		resTreeMap.put(((alpha/authFlow) + mu) as double, -1 as double);
	        	}
        	}
        	   
        
        if(k==-1n){
        	//This is general PageRank
	        val sb:StringBuilder = new StringBuilder();

	        //Just for the moment say to be implemented
	        sb.add("to be implemented...");
	        return sb.toString();
        }else{
        	//This is Top-K PageRank
        	val sb:StringBuilder = new StringBuilder();
        	val set:Set = resTreeMap.entrySet();
        	val itrfinal:Iterator[x10.util.Map.Entry[Double,Double]] = set.iterator() as Iterator[x10.util.Map.Entry[Double,Double]];
        	while(itrfinal.hasNext()){
        		val entr:x10.util.Map.Entry[Double,Double] = itrfinal.next();
	        	sb.add(entr.getKey());
	        	sb.add(",");
	            sb.add(entr.getValue());
	            sb.add(";");
        	}
        	
        	return sb.toString();
        }
	}

}
