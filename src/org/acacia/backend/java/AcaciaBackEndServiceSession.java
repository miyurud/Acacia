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

package org.acacia.backend;

import java.io.File;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;

import org.acacia.centralstore.java.AcaciaHashMapCentralStore;
import org.acacia.localstore.java.AcaciaHashMapNativeStore;
import org.acacia.log.java.Logger_Java;
import org.acacia.server.AcaciaBackEndProtocol;
import org.acacia.server.AcaciaInstanceProtocol;
import org.acacia.server.AcaciaManager;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;

public class AcaciaBackEndServiceSession extends Thread {
//public class AcaciaFrontEndServiceSession extends java.lang.Thread{
	private Socket sessionSkt = null;
	
	public AcaciaBackEndServiceSession(Socket socket){
		sessionSkt = socket;
	}
	
	public void run(){
		try{
			BufferedReader buff = new BufferedReader(new InputStreamReader(sessionSkt.getInputStream()));
			PrintWriter out = new PrintWriter(sessionSkt.getOutputStream());
	
			String msg = null;
	
			while((msg = buff.readLine())!= null){
				if(msg.equals(AcaciaBackEndProtocol.EXIT)){
					out.println(AcaciaBackEndProtocol.EXIT_ACK);
					out.flush();
					sessionSkt.close();
					break;
				}else if(msg.equals(AcaciaBackEndProtocol.HANDSHAKE)){
                    out.println(AcaciaBackEndProtocol.HANDSHAKE_OK);
                    out.flush();
                    
                    try{
                    	//Here we should get the host name of the worker.
                    	msg = buff.readLine();
                    	//Logger_Java.info("Host name of the worker : " + msg);                        
                        
                    }catch(IOException e){
                        Logger_Java.error("WWWW Error : " + e.getMessage());
                    }                    
                }else if(msg.equals(AcaciaBackEndProtocol.OUT_DEGREE_DISTRIBUTION_FOR_PARTITION)){
                    try{           
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		String graphID = buff.readLine();
                
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		String partitionID = buff.readLine();
                
                		HashMap hmp = getOutDegreeDistribution(graphID, partitionID);
                		//System.out.println("Size of HMAP is : "+hmp.size());
                		Iterator itr = hmp.entrySet().iterator();
                		StringBuilder sb = new StringBuilder();
                		int ctr = 0;
                		int WINDOW_SIZE = 1000; //This measure is taken to avoid the memory error thrown by Java sockets.
                		while(itr.hasNext()){
                			Map.Entry pairs = (Map.Entry)itr.next();
                			sb.append(pairs.getKey() + "," + pairs.getValue() + ";");
                			if(ctr > WINDOW_SIZE){
                				//System.out.println("Sending : " + sb.toString());
                				out.println(sb.toString());
                				out.flush();
                				sb = new StringBuilder();
                				ctr = 0;
                			}
                			ctr++;
                		}
                		
                		//We need to send the remaining set of values through the socket
                		if(ctr > 0){
	        				out.println(sb.toString());
	        				out.flush();
                		}
        				//System.out.println("Sending : " + sb.toString());
//        				out.println(sb.toString());
//        				out.flush();
        				
        				out.println(AcaciaBackEndProtocol.DONE);
        				out.flush();
                        
//                		out.println(sb.toString());
//                		out.flush();
                		
//                        try{
//                        	out.close();
//                        	sessionSkt.close();
//                        }catch(IOException e){
//                        	Logger_Java.error("Exitting from this session...");
//                        	break;
//                        } 
                	}catch(IOException e){
                		Logger_Java.error("PPP Error : " + e.getMessage());
                	} 
			    }else if(msg.equals(AcaciaBackEndProtocol.IN_DEGREE_DISTRIBUTION_FOR_PARTITION)){
                    try{           
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		String graphID = buff.readLine();
                		
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		String partitionID = buff.readLine();
                                
                		HashMap hmp = getInDegreeDistribution(graphID, partitionID);
                		Iterator itr = hmp.entrySet().iterator();
                		StringBuilder sb = new StringBuilder();
                		while(itr.hasNext()){
                			Map.Entry pairs = (Map.Entry)itr.next();
                			sb.append(pairs.getKey() + "," + pairs.getValue() + ";");
                		}
                        
                		out.println(sb.toString());
                		out.flush();
                		
//                        try{
//                        	out.close();
//                        	sessionSkt.close();
//                        }catch(IOException e){
//                        	Logger_Java.error("Exitting from this session...");
//                        	break;
//                        } 
                	}catch(IOException e){
                		Logger_Java.error("PPP Error : " + e.getMessage());
                	} 
			    }else if(msg.equals(AcaciaBackEndProtocol.WORLD_ONLY_AUTHFLOW_FOR_PARTITION)){
                    try{           
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		String graphID = buff.readLine();
                
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		String partitionID = buff.readLine();
                
                		double af = getWorldOnlyAuthorityFlow(graphID, partitionID);
                       
                		out.println(af);
                		out.flush();
                		
//                        try{
//                        	out.close();
//                        	sessionSkt.close();
//                        }catch(IOException e){
//                        	Logger_Java.error("Exitting from this session...");
//                        	break;
//                        } 
                	}catch(IOException e){
                		Logger_Java.error("PPP Error : " + e.getMessage());
                	} 
			    }else if(msg.equals(AcaciaBackEndProtocol.LOCAL_TO_WORLD_AUTHFLOW_FOR_PARTITION)){
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		String graphID = buff.readLine();
            
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		String partitionID = buff.readLine();
            		
            		HashMap<Long, Float> hmp = getAuthorityScoresLocalToWorld(graphID, partitionID);
            		//System.out.println("Size of HMAP is : "+hmp.size());
            		Iterator itr = hmp.entrySet().iterator();
            		StringBuilder sb = new StringBuilder();
            		int ctr = 0;
            		int WINDOW_SIZE = 1000; //This measure is taken to avoid the memory error thrown by Java sockets.
            		while(itr.hasNext()){
            			Map.Entry<Long, Float> pairs = (Map.Entry<Long, Float>)itr.next();
            			sb.append(pairs.getKey() + "," + pairs.getValue() + ";");
            			if(ctr > WINDOW_SIZE){
            				//System.out.println("Sending : " + sb.toString());
            				out.println(sb.toString());
            				out.flush();
            				sb = new StringBuilder();
            				ctr = 0;
            			}
            			ctr++;
            		}
            		
            		//We need to send the remaining set of values through the socket
            		if(ctr > 0){
        				out.println(sb.toString());
        				out.flush();
            		}
    				
    				out.println(AcaciaBackEndProtocol.DONE);
    				out.flush();			    	
			    }else if(msg.equals(AcaciaBackEndProtocol.WORLD_TO_LOCAL_FLOW_FROMIDS)){
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		String graphID = buff.readLine();
            
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		String partitionID = buff.readLine();
            		
            		HashMap<Long, ArrayList<Long>> hmp = getIncommingEdges(graphID, partitionID);
            		//System.out.println("Size of HMAP is : "+hmp.size());
            		Iterator itr = hmp.entrySet().iterator();
            		StringBuilder sb = new StringBuilder();
            		int ctr = 0;
            		int WINDOW_SIZE = 1000; //This measure is taken to avoid the memory error thrown by Java sockets.
            		while(itr.hasNext()){
            			Map.Entry<Long, ArrayList<Long>> pairs = (Map.Entry<Long, ArrayList<Long>>)itr.next();
            			sb.append(pairs.getKey());//First send the key
            			ArrayList<Long> lst = pairs.getValue();
            			Iterator itr2 = lst.iterator();
            			while(itr2.hasNext()){
            				sb.append(",");
            				sb.append(itr2.next());
            			}
            			
            			sb.append(";");
            			//Now the sb is like <toID>,<fromid1>,<fromid2>,...;
            			if(ctr > WINDOW_SIZE){
            				//System.out.println("Sending : " + sb.toString());
            				out.println(sb.toString());
            				out.flush();
            				sb = new StringBuilder();
            				ctr = 0;
            			}
            			ctr++;
            		}
            		
            		//We need to send the remaining set of values through the socket
            		if(ctr > 0){
        				out.println(sb.toString());
        				out.flush();
            		}
    				
    				out.println(AcaciaBackEndProtocol.DONE);
    				out.flush();			    	
			    }else if(msg.equals(AcaciaBackEndProtocol.WORLD_TO_LOCAL_AUTHFLOW_FOR_PARTITION)){
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		String graphID = buff.readLine();
            
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		String partitionID = buff.readLine();
            		
            		HashMap hmp = getAuthorityScoresWorldToLocal(graphID, partitionID);
            		//System.out.println("Size of HMAP is : "+hmp.size());
            		Iterator itr = hmp.entrySet().iterator();
            		StringBuilder sb = new StringBuilder();
            		int ctr = 0;
            		int WINDOW_SIZE = 1000; //This measure is taken to avoid the memory error thrown by Java sockets.
            		while(itr.hasNext()){
            			Map.Entry pairs = (Map.Entry)itr.next();
            			sb.append(pairs.getKey() + "," + pairs.getValue() + ";");
            			if(ctr > WINDOW_SIZE){
            				//System.out.println("Sending : " + sb.toString());
            				out.println(sb.toString());
            				out.flush();
            				sb = new StringBuilder();
            				ctr = 0;
            			}
            			ctr++;
            		}
            		
            		//We need to send the remaining set of values through the socket
            		if(ctr > 0){
        				out.println(sb.toString());
        				out.flush();
            		}
    				
    				out.println(AcaciaBackEndProtocol.DONE);
    				out.flush();
			    }else if (msg.equals(AcaciaBackEndProtocol.INTERSECTING_TRIANGLE_COUNT)){
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		String graphID = buff.readLine();
            
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		String partitionID = buff.readLine();

            		long partRes = getIntersectingTraingles(graphID, partitionID);
            		System.out.println("AAAAAAAAAAAAAAAAAAAAA234:" + partRes);
            		if(partRes == -1){
//            			System.out.println("Have to send the global list to the worker");
            			out.println("-1");
            			out.flush();
            			
            		    int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
            		    long fromID = -1;
            		    long toID = -1;
            		    HashMap<Long, HashSet<Long>> hmp = new HashMap<Long, HashSet<Long>>();
            		    int WINDOW_SIZE = 1000; //This measure is taken to avoid the memory error thrown by Java sockets.
            		    
            		    for(int i = 0; i < centralPartionCount; i++){
            		    	//Here we should first bring the central store to the working directory and then construct the central store object with the
            		    	//local copy of the file. This seems to be the only solution we could find at the moment. A much more intelligent technique
            		    	//will not need to bring down the entire central store partition back to the local disk. Even bringing down the central store
            		    	//partitions can be done in an intelligent manner such that we do not exceed the disk quota available on the local disk.
            		    	//AcaciaManager.downloadCentralStore(Integer.parseInt(graphID), i);
            		    	
//            		    	AcaciaHashMapCentralStore store = new AcaciaHashMapCentralStore(Integer.parseInt(graphID), i);
//            		    	store.loadGraph();
            		    	
            		    	//The code for brining down the central store to worker's runtime data folder location should be coded here...
            		    	
            		    	
            		    	//Once we have the central store on our local directory, then we load it to the memory and extract its edge list.
            		    	//We have to do this for all the central store partitions because we need to have access to all of them's edge lists.
            		    	
            		    	String centralStoreBaseDir = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location");
            		    	AcaciaHashMapNativeStore store = new AcaciaHashMapNativeStore(Integer.parseInt(graphID), i, centralStoreBaseDir, true);
            		    	
            		    	HashMap<Long, HashSet<Long>> edgeList = (HashMap<Long, HashSet<Long>>)store.getUnderlyingHashMap();
//            		    	Iterator<Map.Entry<Long, HashSet<Long>>> itr = edgeList.entrySet().iterator();
//            		    	long firstVertex = 0l;
//            		    	
//            		    	while(itr.hasNext()){
//            		    		Map.Entry<Long, HashSet<Long>> entr = itr.next();
//            		    		firstVertex = entr.getKey();
//            		    		HashSet<Long> hs = (HashSet<Long>)hmp.get(firstVertex);
//            		    		
//            		    		if(hs == null){
//            		    			hs = new HashSet<Long>();
//            		    		}
//            		    		
//            		    		HashSet<Long> hs2 = entr.getValue();
//            		    		
//            		    		for(long secondVertex: hs2){
//            		    			hs.add(secondVertex);
//            		    		}
//            		    		
//            		    		hmp.put(firstVertex, hs);
//            		    	}
            		    	
                		    //Where we need to have some batched technique to send the edgelist. Because the edgelist size is going to be very large.
                       		
                    		Iterator itr = edgeList.entrySet().iterator();
                    		int ctr = 0;
                    		long key = 0;
                    		while(itr.hasNext()){
                    			Map.Entry<Long, HashSet<Long>> pairs = (Map.Entry)itr.next();
                    			key = pairs.getKey();
                    			HashSet<Long> lst2 = pairs.getValue();
                    			
                    			/*
                    			 The following method seems much more efficient. But it does not deliver all the edges. This is strange. we get lesser triangle count.
                    			 
                    			//First we send the key
                				out.println("k-" + pairs.getKey());
                				out.flush();
                				
                				//Next, we send all the neighbours of the key
                				StringBuilder sb = new StringBuilder();
                				//sb.append("v-");
                    			for(Long im: lst2){
                    				sb.append(im + ",");
    	                			if(ctr > WINDOW_SIZE){
    	                				//System.out.println("Sending : " + sb.toString());
    	                				out.println(sb.toString());
    	                				out.flush();
    	                				sb = new StringBuilder();
    	                				ctr = 0;
    	                			}
    	                			ctr++;
                    			}
                    			*/
                    		
                				//Next, we send all the neighbours of the key
                				StringBuilder sb = new StringBuilder();
                				//sb.append("v-");
                    			for(Long im: lst2){
                    				sb.append(key + "," + im + ";");
    	                			if(ctr > WINDOW_SIZE){
    	                				System.out.println("Sending : |" + sb.toString()+"|");
    	                				out.println(sb.toString());
    	                				out.flush();
    	                				sb = new StringBuilder();
    	                				ctr = 0;
    	                			}
    	                			ctr++;
                    			}
                    			
                        		//We need to send the remaining set of values through the socket
                        		if(ctr > 0){
                        			System.out.println("Sending : |" + sb.toString() + "| ctr:" + ctr);
        	        				out.println(sb.toString());
        	        				out.flush();
        	        				ctr = 0;
                        		}
                    		}
            		    }
        				
        				out.println(AcaciaBackEndProtocol.DONE);
        				out.flush();
            		}else{
            			//This part of the code still need to be implemented. But we just send -2.
        				out.println("-2");
        				out.flush();            		            		
            		}            		
            		
			    }else{
					process(msg, buff, out);
				}
			}			
		}catch(IOException e){
			Logger_Java.error("QQQQ Error : " + e.getMessage());
		}
	}
		
	private HashMap<Long, Float> getAuthorityScoresLocalToWorld(String graphID, String partitionID) {
	    long fromID = -1;
	    long toID = -1;
 
//	    System.out.println("--------------------- SSSSSSSSS 1 --------------------------");
	    int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    //int partionVertxCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.PARTITION where GRAPH_IDGRAPH=" + graphID + " and IDPARTITION=" + partitionID).value)[(int)0L]);
	    //int vcnt = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    //int worldOnlyVertexCount = vcnt - partionVertxCount; //This is the (N-n) term in APproxRank algo.
//	    System.out.println("--------------------- SSSSSSSSS 2 --------------------------");
	   
	    //HashMap<Long, ArrayList<Long>> worldConnectToLocal = new HashMap<Long, ArrayList<Long>>(); 
	    HashMap<Long, Long> fromDegreeDistribution = new HashMap<Long, Long>(); 
	    //int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    long fromIDDegree = 0;
	    long vval = 0;
	    for(int i = 0; i < centralPartionCount; i++){
	 		java.sql.Connection c = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		
	 		try{
	 			//Here we get all the edges that are comming from the world to the local sub graph.
	 			java.sql.Statement stmt = c.createStatement();
	 			//Note: Here we do not use partTo because we need to find all the out going degrees of fromIDs
	 			java.sql.ResultSet rs = stmt.executeQuery("SELECT idfrom,count(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartfrom=" + partitionID + " GROUP BY idfrom;" );
	 			
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1);
	 						fromIDDegree = rs.getLong(2);
	 						//we need to do this kind of check because we are traversing across multiple partitions of the
	 						//central store's edge list
	 						if(fromDegreeDistribution.containsKey(fromID)){
	 							vval = fromDegreeDistribution.get(fromID);
	 							fromDegreeDistribution.put(fromID, vval + fromIDDegree); //We update the existing out degree value.
	 						}else{
	 							fromDegreeDistribution.put(fromID, fromIDDegree);
	 						}
	 					}
	 			}
	 			c.close();
	 		}catch(java.sql.SQLException e){
	 			e.printStackTrace();
	 		} 
	    }

//	    System.out.println("--------------------- SSSSSSSSS 5 --------------------------");
	    //Note that next we need to divide these authority scores by the number of world only vertices (i.e., N-n).
	    HashMap<Long, Float> resultTemp = new HashMap<Long, Float>(); 
	    Iterator<Entry<Long, Long>> it3 = fromDegreeDistribution.entrySet().iterator();

	    long fID = 0;
	    long degree = 0;
	    float f = 0;
	    
	    while(it3.hasNext()){
	    	Map.Entry<Long, Long> et = it3.next();
	    	fID = et.getKey();
	    	f = et.getValue();
	    	
	    	resultTemp.put(fID, (float)(1/f));
	    }
//	    System.out.println("--------------------- SSSSSSSSS 6 --------------------------");
 		return resultTemp; //This is the final adjusted result	
	}

	/**
	 * This method processes the query requests to AcaciaForntEnd. This is the main function that answers the queries.
	 */
	public void process(String msg, BufferedReader buff, PrintWriter out){
		String response ="";
		String str = null;
		
		if(msg.equals(AcaciaBackEndProtocol.RUOK)){
			out.println(AcaciaBackEndProtocol.IMOK);
			out.flush();
		}else{
			//This is the default response
			out.println(AcaciaBackEndProtocol.SEND);
			out.flush();
		}
	}
	
	
	/**
	 * This method gets the out degree distribution of a specific graph partition.
	 */
	private static HashMap getOutDegreeDistribution(String graphID, String partitionID){
        //System.out.println("getting the out degree distribution for graph : " + graphID + " partitionID : " + partitionID);
//
//        return -1;
        HashMap result = new HashMap(); 
	    long fromID = -1;
	    long fromDegree = -1;
	    
	    int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    //System.out.println("centralPartionCount : " + centralPartionCount);
	    for(int i = 0; i < centralPartionCount; i++){
	 		java.sql.Connection c = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		//System.out.println("running for partionin" + i);
	 		try{
	 			//c.setAutoCommit(false);
	 			java.sql.Statement stmt = c.createStatement();
	 			//System.out.println("SELECT idfrom,COUNT(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartfrom=" + partitionID + " GROUP BY idfrom;");
	 			java.sql.ResultSet rs = stmt.executeQuery("SELECT idfrom,COUNT(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartfrom=" + partitionID + " GROUP BY idfrom;" );
	 			//java.sql.ResultSet rs = stmt.executeQuery("SELECT idfrom,COUNT(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " and (idpartfrom=" + partitionID + " or idpartto=" + partitionID + ") GROUP BY idfrom;" );
	 			//java.sql.ResultSet rs = stmt.executeQuery("SELECT idfrom,COUNT(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " GROUP BY idfrom;" );
	  			int v = 0;
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1);
	 						fromDegree = rs.getLong(2);
	 						result.put(fromID, fromDegree);
	 						v++;
	 					}
	 			}else{
	 				Logger_Java.info("result is null");
	 			}
	 			c.close();
	 		}catch(java.sql.SQLException e){
	 			e.printStackTrace();
	 		}
	    }	
 		//System.out.println("Result is : " + result);
 		
 		return result;
	}
	
	/**
	 * 
	 * @param graphID
	 * @param partitionID
	 * @return the results are indexed by the toID
	 */
	private static HashMap<Long, ArrayList<Long>> getIncommingEdges(String graphID, String partitionID){
		HashMap<Long, ArrayList<Long>> result = new HashMap<Long, ArrayList<Long>>();
	    long fromID = -1;
	    long toID = -1;
	     
	    int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    int partionVertxCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.PARTITION where GRAPH_IDGRAPH=" + graphID + " and IDPARTITION=" + partitionID).value)[(int)0L]);
	    int vcnt = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    int worldOnlyVertexCount = vcnt - partionVertxCount; //This is the (N-n) term in APproxRank algo.

	    for(int i = 0; i < centralPartionCount; i++){
	 		java.sql.Connection c = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		
	 		try{
	 			//Here we get all the edges that are comming from the world to the local sub graph.
	 			java.sql.Statement stmt = c.createStatement();
	 			java.sql.ResultSet rs = stmt.executeQuery("SELECT idfrom,idto FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartto=" + partitionID + ";" );
	 
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1);
	 						toID = rs.getLong(2);
	 						
	 						//Here the key should be toID. The edge is from the external graph to the loacl graph. So the fromID is in the external graph.
	 						//but toID is on the local graph. We are interested of collecting all the fromIDs that connect with each toID.
	 						//Then we can calculate the authority flow from world to local by summing the inverse of out degrees of fromIDs
	 						//and dividing that value by the number of vertices that are on the external graph.
	 						if(!result.containsKey(toID)){
	 							ArrayList temp = new ArrayList();
	 							temp.add(fromID);
	 							result.put(toID, temp);
	 						}else{
	 							ArrayList temp = result.get(toID);
	 							temp.add(fromID);
	 							result.put(toID, temp);
	 						}
	 						
	 					}
	 			}
	 			c.close();
	 		}catch(java.sql.SQLException e){
	 			e.printStackTrace();
	 		} 
	    }
	    
	    return result;
	}
	
	/**
	 * This method gets the in degree distribution of a specific graph partition from the external world.
	 */
	private static HashMap<Long, Long> getInDegreeDistribution(String graphID, String partitionID){
//        System.out.println("getting the out degree distribution for graph : " + graphID + " partitionID : " + partitionID);
//
//        return -1;
        HashMap<Long, Long> result = new HashMap<Long, Long>(); 
	    long fromID = -1;
	    long fromDegree = -1;
	    
	    
	    int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    
	    for(int i = 0; i < centralPartionCount; i++){
	 		java.sql.Connection c = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		
	 		try{
	 			//c.setAutoCommit(false);
	 			java.sql.Statement stmt = c.createStatement();
	 			java.sql.ResultSet rs = stmt.executeQuery("SELECT idto,COUNT(idfrom) FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartto=" + partitionID + " GROUP BY idto;" );
	 
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1);
	 						fromDegree = rs.getLong(2);
	 						result.put(fromID, fromDegree);
	 					}
	 			}
	 			c.close();
	 		}catch(java.sql.SQLException e){
	 			e.printStackTrace();
	 		} 
	    }
	    
 		return result;
	}
	
	/**
	 * This method calculates the authority scores which flow from world to local graph.
	 * @param graphID
	 * @param partitionID - This is the partition ID corresponding to the subgraph
	 * @return
	 */
	private HashMap<Long, Float> getAuthorityScoresWorldToLocal(String graphID, String partitionID){
        HashMap<Long, Float> result = new HashMap<Long, Float>(); 
	    long fromID = -1;
	    long toID = -1;
	    HashMap<Long, ArrayList<Long>> worldConnectToLocal = new HashMap<Long, ArrayList<Long>>(); 
//	    System.out.println("--------------------- SSSSSSSSS 1 --------------------------");
	    int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    int partionVertxCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.PARTITION where GRAPH_IDGRAPH=" + graphID + " and IDPARTITION=" + partitionID).value)[(int)0L]);
	    int vcnt = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    int worldOnlyVertexCount = vcnt - partionVertxCount; //This is the (N-n) term in APproxRank algo.
//	    System.out.println("--------------------- SSSSSSSSS 2 --------------------------");
	    for(int i = 0; i < centralPartionCount; i++){
	 		java.sql.Connection c = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		
	 		try{
	 			//Here we get all the edges that are comming from the world to the local sub graph.
	 			java.sql.Statement stmt = c.createStatement();
	 			java.sql.ResultSet rs = stmt.executeQuery("SELECT idfrom,idto FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartto=" + partitionID + ";" );
	 
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1);
	 						toID = rs.getLong(2);
	 						//result.put(fromID, fromDegree);
	 						/*
	 						if(!worldConnectToLocal.containsKey(fromID)){
	 							ArrayList temp = new ArrayList();
	 							temp.add(toID);
	 							worldConnectToLocal.put(fromID, temp);
	 						}else{
	 							ArrayList temp = worldConnectToLocal.get(fromID);
	 							temp.add(toID);
	 							worldConnectToLocal.put(fromID, temp);
	 						}*/
	 						
	 						//Here the key should be toID. The edge is from the external graph to the loacl graph. So the fromID is in the external graph.
	 						//but toID is on the local graph. We are interested of collecting all the fromIDs that connect with each toID.
	 						//Then we can calculate the authority flow from world to local by summing the inverse of out degrees of fromIDs
	 						//and dividing that value by the number of vertices that are on the external graph.
	 						if(!worldConnectToLocal.containsKey(toID)){
	 							ArrayList temp = new ArrayList();
	 							temp.add(fromID);
	 							worldConnectToLocal.put(toID, temp);
	 						}else{
	 							ArrayList temp = worldConnectToLocal.get(toID);
	 							temp.add(fromID);
	 							worldConnectToLocal.put(toID, temp);
	 						}
	 						
	 					}
	 			}
	 			c.close();
	 		}catch(java.sql.SQLException e){
	 			e.printStackTrace();
	 		} 
	    }
//	    System.out.println("--------------------- SSSSSSSSS 3 --------------------------");
	    //Also we need to in degree distribution from world to local.
	    //This we get by calling the method getInDegreeDistribution()

	    //HashMap<Long, ArrayList<Long>> worldConnectToLocal = new HashMap<Long, ArrayList<Long>>(); 
	    HashMap<Long, Long> fromDegreeDistribution = new HashMap<Long, Long>(); 
	    //int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    long fromIDDegree = 0;
	    long vval = 0;
	    for(int i = 0; i < centralPartionCount; i++){
	 		java.sql.Connection c = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		
	 		try{
	 			//Here we get all the edges that are comming from the world to the local sub graph.
	 			java.sql.Statement stmt = c.createStatement();
	 			//Note: Here we do not use partTo because we need to find all the out going degrees of fromIDs
	 			java.sql.ResultSet rs = stmt.executeQuery("SELECT idfrom, count(idfrom) FROM (select idfrom from acacia_central.edgemap where idgraph=" + graphID + ") group by idfrom;" );
	 			
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1);
	 						fromIDDegree = rs.getLong(2);
	 						//we need to do this kind of check because we are traversing across multiple partitions of the
	 						//central store's edge list
	 						if(fromDegreeDistribution.containsKey(fromID)){
	 							vval = fromDegreeDistribution.get(fromID);
	 							fromDegreeDistribution.put(fromID, vval + fromIDDegree); //We update the existing out degree value.
	 						}else{
	 							fromDegreeDistribution.put(fromID, fromIDDegree);
	 						}
	 					}
	 			}
	 			c.close();
	 		}catch(java.sql.SQLException e){
	 			e.printStackTrace();
	 		} 
	    }
//	    System.out.println("--------------------- SSSSSSSSS 4--------------------------");
	    //+ This should be the total out degree distribution for world only vertices
	    //HashMap<Long, Long> inDegreeDistributionHashMap = getInDegreeDistribution(graphID, partitionID);
	    //HashMap<Long, Long> inDegreeDistributionHashMap = fromDegreeDistribution;
	    
	    //Next we calculate the authority scores world to local
	    Iterator<Entry<Long, ArrayList<Long>>> it = worldConnectToLocal.entrySet().iterator();
	    
//	    System.out.println("Size : " + worldConnectToLocal.size());
	    
	    long tID = 0;
	    long fID = 0;
	    long degree = 0;
	    float f = 0;
	    while(it.hasNext()){
	    	Map.Entry<Long, ArrayList<Long>> p = (Map.Entry<Long, ArrayList<Long>>)it.next();
	    	ArrayList<Long> arr = p.getValue(); //The value contains the list of fromIDs that connect with this toID
	    	Iterator<Long> itr = arr.iterator();
	    	tID = p.getKey();
	    	f = 0;
//	    	System.out.println("--------------------- SSSSSSSSS 4--------------------------");
	    	//Next we iterate through all the fromIDs
	    	while(itr.hasNext()){
	    		fID = (Long)itr.next();
//	    		System.out.println("--------------------- SSSSSSSSS 4AAA--------------------------");
	    		degree = fromDegreeDistribution.get(fID);//This is the entire out degree of fromID
//	    		System.out.println("--------------------- SSSSSSSSS 4BBB--------------------------");
	    		if(result.containsKey(tID)){
//	    			System.out.println("--------------------- SSSSSSSSS 4CCC--------------------------");
                    f = result.get(tID);
//                    System.out.println("--------------------- SSSSSSSSS 4DDD--------------------------");
                    result.put(tID, (f + (float)(1.0/degree))); //This is the summation of inverse of each out degree of fromIDs
	    		}else{
	    			result.put(tID, (float)(1.0/degree));
	    		}
	    	}
	    }
//	    System.out.println("--------------------- SSSSSSSSS 5 --------------------------");
	    //Note that next we need to divide these authority scores by the number of world only vertices (i.e., N-n).
	    HashMap<Long, Float> resultTemp = new HashMap<Long, Float>(); 
	    Iterator<Entry<Long, Float>> it3 = result.entrySet().iterator();
	    
	    while(it3.hasNext()){
	    	Map.Entry<Long, Float> et = it3.next();
	    	tID = et.getKey();
	    	f = et.getValue();
	    	
	    	resultTemp.put(tID, (float)(f/worldOnlyVertexCount));
	    }
//	    System.out.println("--------------------- SSSSSSSSS 6 --------------------------");
 		return resultTemp; //This is the final adjusted result	
	}
	
	/**
	 * This is just a single value. Because there is only one logical vertex in the world.
	 */
	private double getWorldOnlyAuthorityFlow(String graphID, String partitionID) {
		double result=-1;
		//-----------------------------------------------------------------------------------
		//Note August 24 2014 : The following code fragment is inefficient because for each method call it constructs
		//the out degree distribution for the entire graph. However, at this moment we follow this design.
		
        //Next at each peer we need to construct the out degree distribution.
        StringBuilder sb = new StringBuilder();
        String line = null;
        ArrayList<String> lst = new ArrayList<String>();
        try{
	        BufferedReader reader = new BufferedReader(new FileReader("machines.txt"));
	
	        while((line = reader.readLine()) != null){
	        	//Here we just read first line and then break. But this may not be the best option. Better iterate through all the
	        	//lines and accumulate those to a HashMap in future.
	        	lst.add(line.trim());
	        }
        }catch(IOException ec){
        	ec.printStackTrace();
        }
        
        for(String host : lst){
        	//Here we get the number of vertices located on the local graph on each host

    		try{
    			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
    			PrintWriter out = new PrintWriter(socket.getOutputStream());
    			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			String response = "";

    			out.println(AcaciaInstanceProtocol.OUT_DEGREE_DIST);
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

    			if((response != null)&&(!response.equals("-1"))){
    				sb.append(response);
    				result = 0;
    			}else{
    				result = -1;
    				break;
    			}
    			out.close();    			
    		}catch(UnknownHostException e){
    			Logger_Java.error("Connecting to host (10) " + host + " got error message : " + e.getMessage());
    		}catch(IOException ec){
    			Logger_Java.error("Connecting to host (10) " + host + " got error message : " + ec.getMessage());
    		}
    		
    		if(result == -1){
    			Logger_Java.info("There is an error in getting out degree from host : " + host);
    			return result;
    		}
        }    
        
        Logger_Java.info("======AAAAAAAAAA===========");
        
        HashMap outDegreeEntireGraph = new HashMap();
        String[] res1 = sb.toString().split(";");
        for(String item : res1){
        	if(!item.trim().equals("")){
	        	String[] res2 = item.split(":");
	        	if(res2.length >= 2){
	        		//outDegreeDistribution[Integer.parseInt(res2[0])] = Integer.parseInt(res2[1]);
	        		outDegreeEntireGraph.put((long)Integer.parseInt(res2[0]), (long)Integer.parseInt(res2[1]));
	        	}
        	}
        }
        //System.out.println(outDegreeEntireGraph.toString());
        
        HashMap worldOnlyOutDegreeDistribution = getWorldOnlyOutDegreeDistribution(graphID, partitionID);
        double authFlow = 0;
        Iterator itr = worldOnlyOutDegreeDistribution.entrySet().iterator();
        
        while(itr.hasNext()){
        	Map.Entry pairs = (Map.Entry)itr.next();
        	long vertex = (Long) pairs.getKey();
        	long vertexWorldOnlyOutDeg = (Long) pairs.getValue();
        	
        	Long vertexEntireGraphOutDeg = (Long) outDegreeEntireGraph.get(vertex);
        	if(vertexEntireGraphOutDeg == null){
        		//System.out.println("Out degree null for vertex : " + vertex);
        	}else{
        		authFlow += ((double) vertexWorldOnlyOutDeg)/vertexEntireGraphOutDeg;
        	}
        }
        Logger_Java.info("Authority flow for sub graph : " + partitionID + " -> " + authFlow);
        result = authFlow;
        Logger_Java.info("======BBBBBBBBBB===========");
		
		//-----------------------------------------------------------------------------------
		return result;
	}
	
	/**
	 * Out degree distribution only for the external world.
	 */
	private static HashMap getWorldOnlyOutDegreeDistribution(String graphID, String partitionID){
//        System.out.println("getting the out degree distribution for graph : " + graphID + " partitionID : " + partitionID);
//
//        return -1;
        HashMap result = new HashMap(); 
	    long fromID = -1;
	    long fromDegree = -1;
 		//java.sql.Connection c = org.acacia.centralstore.java.HSQLDBInterface.getConnection(graphID, partitionID);
 		
	    //"select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID);
	    int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    
	    for(int i = 0; i < centralPartionCount; i++){
	    	java.sql.Connection c = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		try{
	 			//c.setAutoCommit(false);
	 			java.sql.Statement stmt = c.createStatement();
	 			java.sql.ResultSet rs = stmt.executeQuery("SELECT idfrom,COUNT(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartfrom <> " + partitionID + " and idpartto <> " + partitionID + " GROUP BY idfrom;" );
	 
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1);
	 						fromDegree = rs.getLong(2);
	 						result.put(fromID, fromDegree);
	 					}
	 			}
	 			
	 			c.close();
	 		}catch(java.sql.SQLException e){
	 			e.printStackTrace();
	 		}
	    }
 		//System.out.println("WOnly out deg dist : " + result.toString());
 		
 		return result;
	}
	
	/**
	 * 
	 * @param graphID
	 * @param partitionID
	 * @return -1 if the global graph size is smaller than the local graph. This means we need to send the intersecting global graph to the worker.
	 *         If this value is not -1, that means we do the calculation at the local graph. But then we need to ask the worker to send the 
	 *         edge list back to the master. This kind of scenarios will be less likely with highly partitioned graphs.
	 */
	private long getIntersectingTraingles(String graphID, String partitionID){
		long result = -1;
		long fromID = -1;
		long toID = -1;
	    int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    HashMap<Long, Long> edgeList = new HashMap<Long, Long>();
	    long globalSize = 0;
	    
	    for(int i = 0; i < centralPartionCount; i++){	 
	    	String centralStoreBaseDir = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location");
	    	AcaciaHashMapNativeStore store = new AcaciaHashMapNativeStore(Integer.parseInt(graphID), i, centralStoreBaseDir, true);
	    	//AcaciaHashMapCentralStore store = new AcaciaHashMapCentralStore(Integer.parseInt(graphID), i);
	    	store.loadGraph();
	    	globalSize += store.getEdgeCount();
	    }

	    long localSize = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select EDGECOUNT from ACACIA_META.PARTITION where GRAPH_IDGRAPH=" + graphID + " and IDPARTITION=" + partitionID).value)[(int)0L]);

	    if(localSize > globalSize){
	    	result = -1;
	    }
	    
		return result;
	}
}