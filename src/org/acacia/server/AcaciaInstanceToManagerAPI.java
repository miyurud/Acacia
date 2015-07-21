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

package org.acacia.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;

/**
 * This is the communication interface from AcaciaInstance (worker) with the Manager.
 * This is almost similar to the AcaciaManager but the communication happens in the opposite direction.
 * @author miyuru
 *
 */
public class AcaciaInstanceToManagerAPI{
		
	public static HashMap<String, String> getLambdaOutDegreeDistribution(String serverHost, String graphID, String partitionID){
		StringBuilder sb = null;
		Socket socket = null;
		try{
			System.out.println("server host is : " + serverHost);
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			//socket.setSoTimeout(2000);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
			//First we need to Handshake
			out.println(AcaciaBackEndProtocol.HANDSHAKE);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaBackEndProtocol.HANDSHAKE_OK))){
				out.println(java.net.InetAddress.getLocalHost().getHostName());
				out.flush();
			}			
			
			out.println(AcaciaBackEndProtocol.OUT_DEGREE_DISTRIBUTION_FOR_PARTITION);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//First we send the graph ID
				out.println(graphID);
				out.flush();
			}
			
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//Next, we send the partition ID 
				out.println(partitionID);
				out.flush();
			}
			
			response = reader.readLine();
			
			sb = new StringBuilder();

			while((response != null)&&(!response.equals(AcaciaBackEndProtocol.DONE))){
				sb.append(response);
				response = reader.readLine();
			}
			
			socket.close();
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(InterruptedIOException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}

		if(sb != null){
			String[] intrm = sb.toString().split(";");
			HashMap<String, String> hMapResult = new HashMap<String, String>();
			
			for(String str : intrm){
				String[] str2 = str.split(",");
				hMapResult.put(str2[0], str2[1]);
			}
						
			return hMapResult;
		}else{
			return null;
		}
	}
	
	public static HashMap getLambdaInDegreeDistribution(String serverHost, String graphID, String partitionID){
		StringBuilder sb = null;
		Socket socket = null;
		try{
			System.out.println("server host is : " + serverHost);
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			//socket.setSoTimeout(2000);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
			//First we need to Handshake
			out.println(AcaciaBackEndProtocol.HANDSHAKE);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaBackEndProtocol.HANDSHAKE_OK))){
				out.println(java.net.InetAddress.getLocalHost().getHostName());
				out.flush();
			}			
			
			out.println(AcaciaBackEndProtocol.IN_DEGREE_DISTRIBUTION_FOR_PARTITION);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//First we send the graph ID
				out.println(graphID);
				out.flush();
			}
			
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//Next, we send the partition ID 
				out.println(partitionID);
				out.flush();
			}
			
			response = reader.readLine();
			sb = new StringBuilder();
			sb.append(response);

			socket.close();
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(InterruptedIOException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}
		
		if(sb != null){
			String[] intrm = sb.toString().split(";");
			HashMap hMapResult = new HashMap();
			
			for(String str : intrm){
				String[] str2 = str.split(",");
				hMapResult.put(str2[0], str2[1]);
			}
						
			return hMapResult;
		}else{
			return null;
		}
	}

	/**
	 * This method gets all the fromVertices connected to a particular vertex located in the localgraph denoted by the partitionID
	 * 
	 * @param serverHost
	 * @param graphID
	 * @param partitionID
	 * @return a HashMap object with each key having the toID which is a vertex on the local graph and ArrayLists of fromID values.
	 */
	public static HashMap<Long, ArrayList<Long>> getLambdaToLocalFlowFromVertices(String serverHost, String graphID, String partitionID){
		StringBuilder sb = null;

		Socket socket = null;
		try{
			System.out.println("server host is : " + serverHost);
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			//socket.setSoTimeout(2000);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
			//First we need to Handshake
			out.println(AcaciaBackEndProtocol.HANDSHAKE);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaBackEndProtocol.HANDSHAKE_OK))){
				out.println(java.net.InetAddress.getLocalHost().getHostName());
				out.flush();
			}			
			
			out.println(AcaciaBackEndProtocol.WORLD_TO_LOCAL_FLOW_FROMIDS);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//First we send the graph ID
				out.println(graphID);
				out.flush();
			}
			
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//Next, we send the partition ID 
				out.println(partitionID);
				out.flush();
			}

			sb = new StringBuilder();
			response = reader.readLine();//We need to read at least the first line before we go to the while loop. 
			while((response != null)&&(!response.equals(AcaciaBackEndProtocol.DONE))){
				sb.append(response);
				response = reader.readLine();
			}
			
			socket.close();
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(InterruptedIOException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}

		if(sb != null){
			String[] intrm = sb.toString().split(";");
			HashMap<Long, ArrayList<Long>> hMapResult = new HashMap<Long, ArrayList<Long>>();
			
			for(String str : intrm){
				String[] str2 = str.split(",");
				int l = str2.length;
				ArrayList<Long> lst = new ArrayList<Long>();
				//We have to think whether it would have been better to have Long here. But arrays are not indexed in long in Java.
				//Maybe when the subgraph size is small we might be able to avoid using long...
				long k = Long.parseLong(str2[0]);
				for(String interm2 : str2){
					lst.add(Long.parseLong(interm2));
				}
				
				hMapResult.put(k, lst);
			}
						
			return hMapResult;
		}else{
			return null;
		}		
	}
	
	public static HashMap<Long, Float> getAuthorityFlowLocalToWorld(String serverHost, String graphID, String partitionID){
		Socket socket = null;
		StringBuilder sb = null;
		
		try{
			System.out.println("server host is : " + serverHost);
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
			//First we need to Handshake
			out.println(AcaciaBackEndProtocol.HANDSHAKE);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaBackEndProtocol.HANDSHAKE_OK))){
				out.println(java.net.InetAddress.getLocalHost().getHostName());
				out.flush();
			}			
			
			out.println(AcaciaBackEndProtocol.LOCAL_TO_WORLD_AUTHFLOW_FOR_PARTITION);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//First we send the graph ID
				out.println(graphID);
				out.flush();
			}
			
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//Next, we send the partition ID 
				out.println(partitionID);
				out.flush();
			}
		
			sb = new StringBuilder();
			response = reader.readLine();//We need to read at least the first line before we go to the while loop. 
			while((response != null)&&(!response.equals(AcaciaBackEndProtocol.DONE))){
				sb.append(response);
				response = reader.readLine();
			}
			
			socket.close();
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(InterruptedIOException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}

		if(sb != null){
			String[] intrm = sb.toString().split(";");
			HashMap<Long, Float> hMapResult = new HashMap<Long, Float>();
			
			for(String str : intrm){
				String[] str2 = str.split(",");
				hMapResult.put(Long.parseLong(str2[0]), Float.parseFloat(str2[1]));
			}
						
			return hMapResult;
		}else{
			return null;
		}		
	}
	
	public static HashMap<Long, Float> getAuthorityFlowWorldToLocal(String serverHost, String graphID, String partitionID){
		int round = 0;
		Socket socket = null;
		StringBuilder sb = null;
		
		try{
			System.out.println("server host is : " + serverHost);
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
			//First we need to Handshake
			out.println(AcaciaBackEndProtocol.HANDSHAKE);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaBackEndProtocol.HANDSHAKE_OK))){
				out.println(java.net.InetAddress.getLocalHost().getHostName());
				out.flush();
			}			
			
			out.println(AcaciaBackEndProtocol.WORLD_TO_LOCAL_AUTHFLOW_FOR_PARTITION);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//First we send the graph ID
				out.println(graphID);
				out.flush();
			}
			
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//Next, we send the partition ID 
				out.println(partitionID);
				out.flush();
			}
			
			sb = new StringBuilder();
			response = reader.readLine();//We need to read at least the first line before we go to the while loop. 
			while((response != null)&&(!response.equals(AcaciaBackEndProtocol.DONE))){
				sb.append(response);
				response = reader.readLine();
			}
			
			socket.close();
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(InterruptedIOException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}

		if(sb != null){
			String[] intrm = sb.toString().split(";");
			HashMap<Long, Float> hMapResult = new HashMap<Long, Float>();
			
			for(String str : intrm){
				String[] str2 = str.split(",");
				hMapResult.put(Long.parseLong(str2[0]), Float.parseFloat(str2[1]));
			}
						
			return hMapResult;
		}else{
			return null;
		}
	}
	
	public static double getAuthorityFlowWorldOnly(String serverHost, String graphID, String partitionID){
		double result = -1;

		Socket socket = null;
		try{
			System.out.println("server host is : " + serverHost);
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			//socket.setSoTimeout(2000);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
			//First we need to Handshake
			out.println(AcaciaBackEndProtocol.HANDSHAKE);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaBackEndProtocol.HANDSHAKE_OK))){
				out.println(java.net.InetAddress.getLocalHost().getHostName());
				out.flush();
			}			
			
			out.println(AcaciaBackEndProtocol.WORLD_ONLY_AUTHFLOW_FOR_PARTITION);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//First we send the graph ID
				out.println(graphID);
				out.flush();
			}
			
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//Next, we send the partition ID 
				out.println(partitionID);
				out.flush();
			}
			
			response = reader.readLine();
			result = Double.parseDouble(response);
			
			socket.close();
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(InterruptedIOException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}
		
		return result;
	}	
	
	/**
	 * The following method returns the number of traingles that may intersect between the local graph and the global graph.
	 * Note that this method do not count the world only traingles. Because when the local only and the intersecting triangles
	 * have been counted by each worker, the result is sent to Master to aggregate the results. At that point we can calculate the
	 * world only traingles. No need to send those information back and forth.
	 * 
	 * In the case of counting the intersecting traingles we have to make the decision whether to calculate those traingles
	 * in the worker or in the master. This depends on multiple factors such as the workload on master, the communication
	 * cost between the master and the worker.
	 * 
	 *  At this point of time it was decided to count the number of edges stored in both the master and worker and then decide
	 *  whether to conduct the processing at the worker or at the master.
	 *  
	 * @param serverHost
	 * @param graphID
	 * @param partitionID
	 * @return
	 */
	public static long countIntersectingTraingles(String serverHost, String graphID, String partitionID, HashMap<Long, HashSet<Long>> localSubGraphMap, TreeMap<Long, TreeSet<Long>> degreeMap, HashMap<Long, Long> degreeReverseLookupMap){
		long result = -1;
		Socket socket = null;
		System.out.println("*********** Counting the intersecting traingles ***********");
		try{
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			//socket.setSoTimeout(2000);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
			//First we need to Handshake
			out.println(AcaciaBackEndProtocol.HANDSHAKE);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaBackEndProtocol.HANDSHAKE_OK))){
				out.println(java.net.InetAddress.getLocalHost().getHostName());
				out.flush();
			}			
			
			out.println(AcaciaBackEndProtocol.INTERSECTING_TRIANGLE_COUNT);
			out.flush();
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//First we send the graph ID
				out.println(graphID);
				out.flush();
			}
			
			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				//Next, we send the partition ID 
				out.println(partitionID);
				out.flush();
			}
			
			response = reader.readLine();
			
			//Here if it says -1, it means the master will count the intersecting triangles, because the size of the intesecting
			//traingles will be very large to be transmitted to worker. In this case worker need to send its local edge list
			//to the master. This might be the case when we partition the graph into fine grained pices.
			
			//If the master sends some other value other than "-1", this means master intends the worker to conduct the
			//calculation of the intersecting traingles. In this case workser should start accumulating the data sent by the
			//master. It is because the data will be the list of intersecting edges.
			
			StringBuilder sb = new StringBuilder();
			
			if(!response.equals("-1")){
				System.out.println("======= To be implemented =========");
				System.out.println("Intersecting traingle counting at the worker, to be implemented....");
				System.out.println("======= To be implemented =========");
			}else{	
				long fromID = 0;
				long toID = 0;

				//Need to read the response at least once.
				response = reader.readLine();
				while((response != null)&&(!response.equals(AcaciaBackEndProtocol.DONE))){
					
					sb.append(response);
					//Note that at the moment we are sending the sourve vertex id multiple times which is redundant task. Better to send the
					//source vertex once along with a sequence of destination vertices.
					response = reader.readLine();
				}
				
				HashMap<Long, HashSet<Long>> globalGraphIntersectionMap = new HashMap<Long, HashSet<Long>>();
				
				fromID = 0;
				toID = 0;
				
				if(sb != null){
					String[] intrm = sb.toString().split(";");
					sb = null;//We release the memeory allocated for this StringBuilder object.
					
					for(String str : intrm){
						String[] str2 = str.split(",");
						fromID = Long.parseLong(str2[0]);
						toID = Long.parseLong(str2[1]);
						
						//------------------ maye be we can think of some optimization here ---------------------
						HashSet<Long> toIDList = globalGraphIntersectionMap.get(fromID);
						if(toIDList != null){//There are already some records for this from vertexID
							toIDList.add(toID);
						}else{ //Need to add this from vertexID
							toIDList = new HashSet<Long>();
							toIDList.add(toID);
						}
						
						globalGraphIntersectionMap.put(fromID, toIDList);//Update the existing record.
						
						//We need to do this otherway around as well
						HashSet<Long> fromIDList = globalGraphIntersectionMap.get(toID);
						if(fromIDList != null){//There are already some records for this from vertexID
							fromIDList.add(fromID);
						}else{ //Need to add this from vertexID
							fromIDList = new HashSet<Long>();
							fromIDList.add(fromID);
						}
						
						globalGraphIntersectionMap.put(toID, fromIDList);//Update the existing record.						
					}
				}				
				
				//Now we need to updated the degreeMap which consists only of the local graph info.
				Iterator ita = globalGraphIntersectionMap.entrySet().iterator();
				while(ita.hasNext()){
					Map.Entry pairs = (Map.Entry)ita.next();
					if(degreeMap.containsKey(-1l)){
						TreeSet<Long> itSet = degreeMap.get(-1l);
						itSet.add((Long)pairs.getKey());
						degreeMap.put(-1l, itSet);
					}else{
						TreeSet<Long> itSet = new TreeSet();
						itSet.add((Long)pairs.getKey());
						degreeMap.put(-1l, itSet);
					}
					
					//We need to add the record for reverse lookup as well.
					degreeReverseLookupMap.put((Long) pairs.getKey(), -1l);
				}
				
				socket.close();//We do not need to communicate with the master from here onwards.
				
				//This time the traingle counting happens at the intersection of local and global graphs.
				
				//Now we start count the traingles. But this happens only in the local graph.
				//This might be a naive method of doing this. Good for the moment... Sept 30 2014
				long traingleCount = 0;
				long degree = -1;
				long v1 = 0;
				long v2 = 0;
				long v3 = 0;
				HashMap<Long, HashMap<Long, ArrayList<Long>>> traingleTree = new HashMap<Long, HashMap<Long, ArrayList<Long>>>();
				ArrayList<Long> degreeListVisited = new ArrayList<Long>();
				
				//There are two ways of counting traingles in the case of graph intersection. First, is we can get two vertices that are
				//located in the local graph and then find a connection of that edge with a vertex in the global graph.
				//Second, is we can take two vertices which forms an edge in the global graph and then connect that with a vertex in the
				//local graph. Note that, at this point of time we are not interested of counting traingles from the vertices located
				//only within the intersecting global graph. This task will be done separately at the master itself.
				
				/*-------- The follwoing code is searching the triangles only */
				for(Map.Entry<Long, TreeSet<Long>> item: degreeMap.entrySet()){				
					TreeSet<Long> vVertices = item.getValue();
					
					for(long v : vVertices){
					//if(uList != null){ //Because in local subgraph map we may mark only u -> v, but v may not have corresponding record in the map (i.e., v -> u).
						HashSet<Long> uList = localSubGraphMap.get(v);
						if(uList != null){
						for(long u : uList){
						    if(degreeReverseLookupMap.containsKey(u)){
						    	degree = degreeReverseLookupMap.get(u);
						    	if(degreeListVisited.contains(degree)){
						    		continue;
						    	}
						    }
													
							HashSet<Long> nuList = globalGraphIntersectionMap.get(u); //At this point we move on to the global intersection graph
							if(nuList != null){
								for(long nu : nuList){
								    if(degreeReverseLookupMap.containsKey(nu)){
								    	long degree2 = degreeReverseLookupMap.get(nu);
								    	if(degreeListVisited.contains(degree2)){
								    		continue;
								    	}
								    }								
								
									//The neighbours of u must be chacked in the global intersection graph. There is no point of doing this search in the local graph.
									//Because if such relationship exists we can detect that at the local graph level. No need to check in the global intersection graph.
									HashSet<Long> nwList = globalGraphIntersectionMap.get(nu);
									if((nwList != null) && (nwList.contains(v))){
										//At this point we have discovered a traingle. But now we need to makesure that we have to counted that triangle before.
										//To do that we use the traingle tree data structure which keeps on track of the triangles we have marked so far.
										//Note that the traingle tree may not be as efficient as we expect.
																			
										long[] tempArr = new long[]{v, u, nu};
										java.util.Arrays.sort(tempArr);
										v1 = tempArr[0];
										v2 = tempArr[1];
										v3 = tempArr[2];
										
										//From this point onwards the full control is with the trangle tree
										HashMap<Long, ArrayList<Long>> itemRes = traingleTree.get(v1);
										if(itemRes != null){
											if(itemRes.containsKey(v2)){
												ArrayList<Long> lst = itemRes.get(v2);
												if(!lst.contains(v3)){
													lst.add(v3);
													itemRes.put(v2, lst);
													traingleTree.put(v1, itemRes);
													
													traingleCount++;
												}else{
													//We have discovered this trainge before. It is already recorded in the tree.
												}
											}else{
												//This means that the infrmation of the second vertex is still not added to the traingle tree.
												//This is a fresh round of accounting for u
												ArrayList<Long> newU = new ArrayList<Long>();
												newU.add(v3);
												itemRes.put(v2, newU);
												traingleTree.put(v1, itemRes);//here its just replacing the old itemRes
												
												traingleCount++;
											}
										}else{
											//This means that there is even no record of the v. So we need to add everything from the scratch.
											itemRes = new HashMap<Long, ArrayList<Long>>();
											ArrayList<Long> newU = new ArrayList<Long>();
											newU.add(v3);
											itemRes.put(v2, newU);
											traingleTree.put(v1, itemRes); //here we are adding a new itemRes
											
											traingleCount++;
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
				System.out.println("Total Traingles found : " + traingleCount);
				result = traingleCount;
			}
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(InterruptedIOException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}
				
		return result;
	}
}