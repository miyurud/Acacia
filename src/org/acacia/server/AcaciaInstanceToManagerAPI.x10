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

import x10.util.HashMap;
import x10.util.StringBuilder;
import java.net.Socket;
import org.acacia.util.java.Conts_Java;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.acacia.log.java.Logger_Java;
import org.acacia.server.java.AcaciaInstanceProtocol;
import org.acacia.server.java.AcaciaBackEndProtocol;
import java.io.InterruptedIOException;
import x10.util.ArrayList;
import x10.util.HashSet;
import java.util.TreeSet;
import java.util.TreeMap;

/**
 * This is the communication interface from AcaciaInstance (worker) with the Manager.
 * This is almost similar to the AcaciaManager but the communication happens in the opposite direction.
 * @author miyuru
 * 
 */
public class AcaciaInstanceToManagerAPI {

 	public static def getLambdaOutDegreeDistribution(serverHost:String, graphID:String, partitionID:String):HashMap[String, String]{
 		var sb:StringBuilder = null;
 		var socket:Socket = null;
 		try{
 			Console.OUT.println("server host is : " + serverHost);
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			//socket.setSoTimeout(2000);
			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			var response:String = "";

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
				sb.add(response);
				response = reader.readLine();
			}
			
			socket.close();
		}catch(e:java.net.UnknownHostException){
			Logger_Java.error(e.getMessage());
		}catch(e:InterruptedIOException){
			Logger_Java.error(e.getMessage());
		}catch(ec:java.io.IOException){
			Logger_Java.error(ec.getMessage());
 		}

		if(sb != null){
			var intrm:Rail[String] = sb.toString().split(";");
			val hMapResult:HashMap[String, String] = new HashMap[String, String]();
		
			for(str:String in intrm){
				var str2:Rail[String] = str.split(",");
				hMapResult.put(str2(0), str2(1));
			}

 			return hMapResult;
 		}else{
 			return null;
 		}
 	}

 	public static def getLambdaInDegreeDistribution(serverHost:String, graphID:String, partitionID:String):HashMap[String,String]{
		var sb:StringBuilder = null;
		var socket:Socket = null;
		try{
			Console.OUT.println("server host is : " + serverHost);
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			//socket.setSoTimeout(2000);
			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			var response:String = "";
			
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
			sb.add(response);
			
			socket.close();
		}catch(e:java.net.UnknownHostException){
			Logger_Java.error(e.getMessage());
		}catch(e:InterruptedIOException){
			Logger_Java.error(e.getMessage());
		}catch(ec:java.io.IOException){
			Logger_Java.error(ec.getMessage());
		}
		
		if(sb != null){
			var intrm:Rail[String] = sb.toString().split(";");
			var hMapResult:HashMap[String,String] = new HashMap[String,String]();
			
			for(str:String in intrm){
				var str2:Rail[String] = str.split(",");
				hMapResult.put(str2(0), str2(1));
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
 	public static def getLambdaToLocalFlowFromVertices(serverHost:String, graphID:String, partitionID:String):HashMap[Long, ArrayList[Long]]{
		var sb:StringBuilder = null;
		
		var socket:Socket = null;
		try{
			Console.OUT.println("server host is : " + serverHost);
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			//socket.setSoTimeout(2000);
			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			var response:String = "";
			
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
			sb.add(response);
			response = reader.readLine();
			}
			
			socket.close();
		}catch(e:java.net.UnknownHostException){
			Logger_Java.error(e.getMessage());
		}catch(e:InterruptedIOException){
			Logger_Java.error(e.getMessage());
		}catch(ec:java.io.IOException){
			Logger_Java.error(ec.getMessage());
		}
		
		if(sb != null){
			var intrm:Rail[String] = sb.toString().split(";");
			var hMapResult:HashMap[Long, ArrayList[Long]] = new HashMap[Long, ArrayList[Long]]();
		
			for(str:String in intrm){
				val str2:Rail[String] = str.split(",");
				var l:Int = str2.size as Int;
				val lst:ArrayList[Long] = new ArrayList[Long]();
				//We have to think whether it would have been better to have Long here. But arrays are not indexed in long in Java.
				//Maybe when the subgraph size is small we might be able to avoid using long...
				var k:long = Long.parseLong(str2(0));
				for(interm2:String in str2){
					lst.add(Long.parseLong(interm2));
				}
		
				hMapResult.put(k, lst);
			}
		
			return hMapResult;
		}else{
			return null;
		}		
 	}

	public static def getAuthorityFlowLocalToWorld(serverHost:String, graphID:String, partitionID:String):HashMap[Long, Float]{
		var socket:Socket = null;
		var sb:StringBuilder = null;
		
		try{
			Console.OUT.println("server host is : " + serverHost);
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			var response:String = "";
			
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
				sb.add(response);
				response = reader.readLine();
			}
			
			socket.close();
		}catch(e:java.net.UnknownHostException){
			Logger_Java.error(e.getMessage());
		}catch(e:InterruptedIOException){
			Logger_Java.error(e.getMessage());
		}catch(ec:java.io.IOException){
			Logger_Java.error(ec.getMessage());
		}
		
		if(sb != null){
			var intrm:Rail[String] = sb.toString().split(";");
			val hMapResult:HashMap[Long, Float] = new HashMap[Long, Float]();
		
			for(str:String in intrm){
				var str2:Rail[String] = str.split(",");
				hMapResult.put(Long.parseLong(str2(0)), Float.parseFloat(str2(1)));
			}
		
			return hMapResult;
		}else{
			return null;
		}		
	}

 	public static def getAuthorityFlowWorldToLocal(serverHost:String, graphID:String, partitionID:String):HashMap[Long, Float]{
		var round:Int = 0n;
		var socket:Socket = null;
		var sb:StringBuilder = null;
		
		try{
			Console.OUT.println("server host is : " + serverHost);
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			var response:String = "";
			
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
				sb.add(response);
				response = reader.readLine();
			}
			
			socket.close();
		}catch(e:java.net.UnknownHostException){
			Logger_Java.error(e.getMessage());
		}catch(e:InterruptedIOException){
			Logger_Java.error(e.getMessage());
		}catch(ec:java.io.IOException){
			Logger_Java.error(ec.getMessage());
		}
		
		if(sb != null){
			var intrm:Rail[String] = sb.toString().split(";");
			val hMapResult:HashMap[Long, Float] = new HashMap[Long, Float]();
		
			for(str:String in intrm){
				var str2:Rail[String] = str.split(",");
				hMapResult.put(Long.parseLong(str2(0)), Float.parseFloat(str2(1)));
			}
		
			return hMapResult;
		}else{
			return null;
		}
	}
 
	 public static def getAuthorityFlowWorldOnly(serverHost:String, graphID:String, partitionID:String):double{
		 var result:double = -1;
		
		 var socket:Socket = null;
		 try{
			 Console.OUT.println("server host is : " + serverHost);
			 socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			 //socket.setSoTimeout(2000);
			 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 var response:String = "";
			 
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
		 }catch(e:java.net.UnknownHostException){
		 	Logger_Java.error(e.getMessage());
		 }catch(e:InterruptedIOException){
		 	Logger_Java.error(e.getMessage());
		 }catch(ec:java.io.IOException){
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
 	public static def countIntersectingTraingles(serverHost:String, graphID:String, partitionID:String, localSubGraphMap:HashMap[Long, HashSet[Long]], degreeMap:TreeMap, degreeReverseLookupMap:HashMap[Long, Long]):long{
		var result:long = -1;
		var socket:Socket = null;
		//System.out.println("*********** Counting the intersecting traingles ***********");
		try{
			socket = new Socket(serverHost, Conts_Java.ACACIA_BACKEND_PORT);
			//socket.setSoTimeout(2000);
			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			var response:String = "";
			
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
			//We need to makesure that it is not null.
			if(response == null){
				return result;
			}
			
			//Here if it says -1, it means the master will count the intersecting triangles, because the size of the intesecting
			//traingles will be very large to be transmitted to worker. In this case worker need to send its local edge list
			//to the master. This might be the case when we partition the graph into fine grained pieces.
			
			//If the master sends some other value other than "-1", this means master intends the worker to conduct the
			//calculation of the intersecting traingles. In this case worker should start accumulating the data sent by the
			//master. It is because the data will be the list of intersecting edges.
			
			var sb:StringBuilder = new StringBuilder();
			
			if(!response.equals("-1")){
				Console.OUT.println("======= To be implemented =========");
				Console.OUT.println("Intersecting traingle counting at the worker, to be implemented....");
				Console.OUT.println("======= To be implemented =========");
			}else{	
				var fromID:long = 0;
				var toID:long = 0;
				//Need to read the response at least once.
				response = reader.readLine();
				while((response != null)&&(!response.equals(AcaciaBackEndProtocol.DONE))){
					//Utils_Java.writeToFile("/home/miyurud/tmp/111/111.txt", sb);
					sb.add(response);
					//Note that at the moment we are sending the sourve vertex id multiple times which is redundant task. Better to send the
					//source vertex once along with a sequence of destination vertices.
					response = reader.readLine();
				}
			
				val globalGraphIntersectionMap:HashMap[Long, HashSet[Long]] = new HashMap[Long, HashSet[Long]]();
				
				fromID = 0;
				toID = 0;
				//System.out.println("------->sb.length() : " + sb.length());
				if(sb.length() > 0){
					var intrm:Rail[String] = sb.toString().split(";");
					sb = null;//We release the memeory allocated for this StringBuilder object.
				
					for(str:String in intrm){
						var str2:Rail[String] = str.split(",");
						fromID = Long.parseLong(str2(0));
						toID = Long.parseLong(str2(1));
					
						//------------------ maye be we can think of some optimization here ---------------------
						var toIDList:HashSet[Long] = globalGraphIntersectionMap.get(fromID);
						if(toIDList != null){//There are already some records for this from vertexID
							toIDList.add(toID);
						}else{ //Need to add this from vertexID
							toIDList = new HashSet[Long]();
							toIDList.add(toID);
						}
			
						globalGraphIntersectionMap.put(fromID, toIDList);//Update the existing record.
			
						//We need to do this otherway around as well
						var fromIDList:HashSet[Long] = globalGraphIntersectionMap.get(toID);
						if(fromIDList != null){//There are already some records for this from vertexID
							fromIDList.add(fromID);
						}else{ //Need to add this from vertexID
							fromIDList = new HashSet[Long]();
							fromIDList.add(fromID);
						}
						
						globalGraphIntersectionMap.put(toID, fromIDList);//Update the existing record.						
					}
				}				
			
				//Now we need to updated the degreeMap which consists only of the local graph info.
				val ita:Iterator[x10.util.Map.Entry[Long, HashSet[Long]]] = globalGraphIntersectionMap.entries().iterator();
				while(ita.hasNext()){
					val pairs:x10.util.Map.Entry[Long,HashSet[Long]] = ita.next();
					if(degreeMap.containsKey(-1l)){
						var itSet:TreeSet = degreeMap.get(-1l) as TreeSet;
						itSet.add(pairs.getKey());
						degreeMap.put(-1l, itSet);
					}else{
						val itSet:TreeSet = new TreeSet();
						itSet.add(pairs.getKey());
						degreeMap.put(-1l, itSet);
					}
			
					//We need to add the record for reverse lookup as well.
					//degreeReverseLookupMap.put((Long) pairs.getKey(), -1l);
				}
			
				socket.close();//We do not need to communicate with the master from here onwards.
			
				//This time the traingle counting happens at the intersection of local and global graphs.
			
				//Now we start count the traingles. But this happens only in the local graph.
				//This might be a naive method of doing this. Good for the moment... Sept 30 2014
				var traingleCount:long = 0;
				var degree:long = -1;
				var v1:long = 0;
				var v2:long = 0;
				var v3:long = 0;
				var traingleTree:HashMap[Long, HashMap[Long, ArrayList[Long]]] = new HashMap[Long, HashMap[Long, ArrayList[Long]]]();
				val degreeListVisited:ArrayList[Long] = new ArrayList[Long]();
			
				//There are two ways of counting traingles in the case of graph intersection. First, is we can get two vertices that are
				//located in the local graph and then find a connection of that edge with a vertex in the global graph.
				//Second, is we can take two vertices which forms an edge in the global graph and then connect that with a vertex in the
				//local graph. Note that, at this point of time we are not interested of counting traingles from the vertices located
				//only within the intersecting global graph. This task will be done separately at the master itself.
				
				/*-------- The following code is searching the triangles only */
				//var itr:Iterator[x10.util.Map.Entry[Long, TreeSet]] = degreeMap.entrySet().iterator() as Iterator[x10.util.Map.Entry[Long, TreeSet]];
                var itr:java.util.Iterator = degreeMap.keySet().iterator();
                
				while(itr.hasNext()){
                    val kkey:Long = itr.next() as Long;
					//var item:TreeSet = degreeMap.get(kkey) as TreeSet;
					// for(Map.Entry<Long, TreeSet<Long>> item: degreeMap.entrySet()){				
					val vVertices:TreeSet = degreeMap.get(kkey) as TreeSet;
					val itr2:java.util.Iterator = vVertices.iterator() as java.util.Iterator;

					while(itr2.hasNext()){
						v:Long = itr2.next() as Long;
				
						//if(uList != null){ //Because in local subgraph map we may mark only u -> v, but v may not have corresponding record in the map (i.e., v -> u).
						val uList:HashSet[Long] = localSubGraphMap.get(v);
						if(uList != null){
							for(u:long in uList){
								//						    if(degreeReverseLookupMap.containsKey(u)){
								//						    	degree = degreeReverseLookupMap.get(u);
								//						    	if(degreeListVisited.contains(degree)){
								//						    		continue;
								//						    	}
								//						    }
			
								val nuList:HashSet[Long] = globalGraphIntersectionMap.get(u); //At this point we move on to the global intersection graph
								if(nuList != null){
									for(nu:long in nuList){
										//								    if(degreeReverseLookupMap.containsKey(nu)){
										//								    	long degree2 = degreeReverseLookupMap.get(nu);
										//								    	if(degreeListVisited.contains(degree2)){
										//								    		continue;
										//								    	}
										//								    }								
										
										//The neighbours of u must be chacked in the global intersection graph. There is no point of doing this search in the local graph.
										//Because if such relationship exists we can detect that at the local graph level. No need to check in the global intersection graph.
										val nwList:HashSet[Long] = globalGraphIntersectionMap.get(nu);
										if((nwList != null) && (nwList.contains(v))){
											//At this point we have discovered a traingle. But now we need to makesure that we have to counted that triangle before.
											//To do that we use the traingle tree data structure which keeps on track of the triangles we have marked so far.
											//Note that the traingle tree may not be as efficient as we expect.
										
											var tempArr:Rail[Long] = new Rail[Long](3);
											tempArr(0) = v;
											tempArr(1) = u;
											tempArr(2) = nu;
											java.util.Arrays.sort(x10.interop.Java.convert(tempArr));
											v1 = tempArr(0);
											v2 = tempArr(1);
											v3 = tempArr(2);
											
											//From this point onwards the full control is with the trangle tree
											var itemRes:HashMap[Long, ArrayList[Long]] = traingleTree.get(v1);
											if(itemRes != null){
												if(itemRes.containsKey(v2)){
													var lst:ArrayList[Long] = itemRes.get(v2);
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
													val newU:ArrayList[Long] = new ArrayList[Long]();
													newU.add(v3);
													itemRes.put(v2, newU);
													traingleTree.put(v1, itemRes);//here its just replacing the old itemRes
													
													traingleCount++;
												}
											}else{
												//This means that there is even no record of the v. So we need to add everything from the scratch.
												itemRes = new HashMap[Long, ArrayList[Long]]();
												val newU:ArrayList[Long] = new ArrayList[Long]();
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
					degreeListVisited.add(kkey);
				}
			
				traingleTree = null; //Here we enable the tree object to be garbage collected.
				//System.out.println("Total Traingles found : " + traingleCount);
				result = traingleCount;
			}
		}catch(e:java.net.UnknownHostException){
			Logger_Java.error(e.getMessage());
		}catch(e:InterruptedIOException){
			Logger_Java.error(e.getMessage());
		}catch(ec:java.io.IOException){
			Logger_Java.error(ec.getMessage());
		}
			return result;
		}

	}