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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;

import org.acacia.centralstore.AcaciaHashMapCentralStore;
import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;
import x10.core.Thread;
import x10.util.HashMap;
import x10.interop.Java;
import x10.util.ArrayList;
import x10.util.StringBuilder;
import x10.util.HashSet;
import org.acacia.localstore.AcaciaLocalStore;
import org.acacia.localstore.AcaciaLocalStoreFactory;
import org.acacia.server.java.AcaciaInstanceProtocol;


public class AcaciaBackEndServiceSession extends Thread {
//public class AcaciaFrontEndServiceSession extends java.lang.Thread{
	var sessionSkt:Socket = null;
	
	public def this(val socket:Socket){
		sessionSkt = socket;
	}
	
	public def run(){
		try{
			val buff:BufferedReader = new BufferedReader(new InputStreamReader(sessionSkt.getInputStream()));
			val out:PrintWriter = new PrintWriter(sessionSkt.getOutputStream());
	
			var msg:String = null;
	
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
                        
                    }catch(e:IOException){
                        Logger_Java.error("WWWW Error : " + e.getMessage());
                    }                    
                }else if(msg.equals(AcaciaBackEndProtocol.OUT_DEGREE_DISTRIBUTION_FOR_PARTITION)){
                    try{           
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		val graphID:String = buff.readLine();
                
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		val partitionID:String = buff.readLine();
                
                		val hmp:HashMap[Long,Long] = getOutDegreeDistribution(graphID, partitionID);
                		//System.out.println("Size of HMAP is : "+hmp.size());
                		val itr:Iterator[x10.util.Map.Entry[Long,Long]] = hmp.entries().iterator();
                		var sb:StringBuilder = new StringBuilder();
                		var ctr:int = 0n;
                		val WINDOW_SIZE:int = 1000n; //This measure is taken to avoid the memory error thrown by Java sockets.
                		while(itr.hasNext()){
                			val pairs:x10.util.Map.Entry[Long,Long] = itr.next();
                			sb.add(pairs.getKey() + "," + pairs.getValue() + ";");
                			if(ctr > WINDOW_SIZE){
                				//System.out.println("Sending : " + sb.toString());
                				out.println(sb.toString());
                				out.flush();
                				sb = new StringBuilder();
                				ctr = 0n;
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
                        
               		out.println(sb.toString());
               		out.flush();
                		
//                        try{
//                        	out.close();
//                        	sessionSkt.close();
//                        }catch(IOException e){
//                        	Logger_Java.error("Exitting from this session...");
//                        	break;
//                        } 
                	}catch(e:IOException){
                		Logger_Java.error("PPP Error : " + e.getMessage());
                	} 
			    }else if(msg.equals(AcaciaBackEndProtocol.IN_DEGREE_DISTRIBUTION_FOR_PARTITION)){
                    try{           
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		val graphID:String = buff.readLine();
                		
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		val partitionID:String = buff.readLine();
                                
                		val hmp:HashMap[Long,Long] = getInDegreeDistribution(graphID, partitionID);
                		val itr:Iterator[x10.util.Map.Entry[Long,Long]] = hmp.entries().iterator();
                		val sb:StringBuilder = new StringBuilder();
                		while(itr.hasNext()){
                			val pairs:x10.util.Map.Entry[Long,Long] = itr.next();
                			sb.add(pairs.getKey() + "," + pairs.getValue() + ";");
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
                	}catch(e:IOException){
                		Logger_Java.error("PPP Error : " + e.getMessage());
                	} 
			    }else if(msg.equals(AcaciaBackEndProtocol.WORLD_ONLY_AUTHFLOW_FOR_PARTITION)){
                    try{           
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		val graphID:String = buff.readLine();
                
                		out.println(AcaciaBackEndProtocol.OK);
                		out.flush();
                
                		val partitionID:String = buff.readLine();
                
                		val af:double = getWorldOnlyAuthorityFlow(graphID, partitionID);
                       
                		out.println(af);
                		out.flush();
                		
//                        try{
//                        	out.close();
//                        	sessionSkt.close();
//                        }catch(IOException e){
//                        	Logger_Java.error("Exitting from this session...");
//                        	break;
//                        } 
                	}catch(e:IOException){
                		Logger_Java.error("PPP Error : " + e.getMessage());
                	} 
			    }else if(msg.equals(AcaciaBackEndProtocol.LOCAL_TO_WORLD_AUTHFLOW_FOR_PARTITION)){
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		val graphID:String = buff.readLine();
            
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		val partitionID:String = buff.readLine();
            		
            		val hmp:HashMap[Long, Float] = getAuthorityScoresLocalToWorld(graphID, partitionID);
            		//System.out.println("Size of HMAP is : "+hmp.size());
            		val itr:Iterator[x10.util.Map.Entry[Long,Float]] = hmp.entries().iterator();
            		var sb:StringBuilder = new StringBuilder();
            		var ctr:int = 0n;
            		val WINDOW_SIZE:int = 1000n; //This measure is taken to avoid the memory error thrown by Java sockets.
            		while(itr.hasNext()){
            			val pairs:x10.util.Map.Entry[Long, Float] = itr.next();
            			sb.add(pairs.getKey() + "," + pairs.getValue() + ";");
            			if(ctr > WINDOW_SIZE){
            				//System.out.println("Sending : " + sb.toString());
            				out.println(sb.toString());
            				out.flush();
            				sb = new StringBuilder();
            				ctr = 0n;
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
            
            		val graphID:String = buff.readLine();
            
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		val partitionID:String = buff.readLine();
            		
            		val hmp:HashMap[Long, ArrayList[Long]] = getIncommingEdges(graphID, partitionID);
            		//System.out.println("Size of HMAP is : "+hmp.size());
            		val itr:Iterator[x10.util.Map.Entry[Long,ArrayList[Long]]] = hmp.entries().iterator();
            		var sb:StringBuilder = new StringBuilder();
            		var ctr:int = 0n;
            		var WINDOW_SIZE:int = 1000n; //This measure is taken to avoid the memory error thrown by Java sockets.
            		while(itr.hasNext()){
            			val pairs:x10.util.Map.Entry[Long, ArrayList[Long]] = itr.next();
            			sb.add(pairs.getKey());//First send the key
            			val lst:ArrayList[Long] = pairs.getValue();
            			val itr2:Iterator[Long] = lst.iterator();
            			while(itr2.hasNext()){
            				sb.add(",");
            				sb.add(itr2.next());
            			}
            			
            			sb.add(";");
            			//Now the sb is like <toID>,<fromid1>,<fromid2>,...;
            			if(ctr > WINDOW_SIZE){
            				//System.out.println("Sending : " + sb.toString());
            				out.println(sb.toString());
            				out.flush();
            				sb = new StringBuilder();
            				ctr = 0n;
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
            
            		val graphID:String = buff.readLine();
            
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		val partitionID:String = buff.readLine();
            		
            		val hmp:HashMap[Long,Float] = getAuthorityScoresWorldToLocal(graphID, partitionID);
            		//System.out.println("Size of HMAP is : "+hmp.size());
            		val itr:Iterator[x10.util.Map.Entry[Long,Float]] = hmp.entries().iterator();
            		var sb:StringBuilder = new StringBuilder();
            		var ctr:int = 0n;
            		val WINDOW_SIZE:int = 1000n; //This measure is taken to avoid the memory error thrown by Java sockets.
            		while(itr.hasNext()){
            			val pairs:x10.util.Map.Entry[Long,Float] = itr.next();
            			sb.add(pairs.getKey() + "," + pairs.getValue() + ";");
            			if(ctr > WINDOW_SIZE){
            				//System.out.println("Sending : " + sb.toString());
            				out.println(sb.toString());
            				out.flush();
            				sb = new StringBuilder();
            				ctr = 0n;
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
            
            		val graphID:String = buff.readLine();
            
            		out.println(AcaciaBackEndProtocol.OK);
            		out.flush();
            
            		val partitionID:String = buff.readLine();

            		val partRes:long = getIntersectingTraingles(graphID, partitionID);
            		//System.out.println("AAAAAAAAAAAAAAAAAAAAA234:" + partRes);
            		if(partRes == -1){
//            			System.out.println("Have to send the global list to the worker");
            			out.println("-1");
            			out.flush();
            			
            		    val centralPartionCount:int = Int.parseInt((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String])(0));
            		    var fromID:long = -1;
            		    var toID:long = -1;
            		    val hmp:HashMap[Long, HashSet[Long]] = new HashMap[Long, HashSet[Long]]();
            		    val WINDOW_SIZE:int = 1000n; //This measure is taken to avoid the memory error thrown by Java sockets.
//             		    
            		    for(var i:int = 0n; i < centralPartionCount; i++){
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
            		    	
            		    	val centralStoreBaseDir:String = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location");
            		    	//AcaciaHashMapNativeStore store = new AcaciaHashMapNativeStore(Integer.parseInt(graphID), i, centralStoreBaseDir, true);
            		    	val store:AcaciaLocalStore = AcaciaLocalStoreFactory.load(Int.parseInt(graphID), i, Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator + graphID + "_centralstore", true);
            		    	store.loadGraph();
            		    	
            		    	val edgeList:HashMap[Long, HashSet[Long]] = store.getUnderlyingHashMap();
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
                       		
                    		val itr:Iterator[x10.util.Map.Entry[Long,HashSet[Long]]] = edgeList.entries().iterator();
                    		var ctr:int = 0n;
                    		var key:long = 0;
                    		while(itr.hasNext()){
                    			val pairs:x10.util.Map.Entry[Long, HashSet[Long]] = itr.next();
                    			key = pairs.getKey();
                    			val lst2:HashSet[Long] = pairs.getValue();
                    			
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
                				var sb:StringBuilder = new StringBuilder();
                				//sb.append("v-");
                    			for(im:Long in lst2){
                    				sb.add(key + "," + im + ";");
    	                			if(ctr > WINDOW_SIZE){
    	                				//System.out.println("Sending : |" + sb.toString()+"|");
    	                				out.println(sb.toString());
    	                				out.flush();
    	                				sb = new StringBuilder();
    	                				ctr = 0n;
    	                			}
    	                			ctr++;
                    			}
                    			
                        		//We need to send the remaining set of values through the socket
                        		if(ctr > 0){
                        			//System.out.println("Sending : |" + sb.toString() + "| ctr:" + ctr);
        	        				out.println(sb.toString());
        	        				out.flush();
        	        				ctr = 0n;
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
		}catch(e:IOException){
			Logger_Java.error("QQQQ Error : " + e.getMessage());
		}
	}
		
	private def getAuthorityScoresLocalToWorld(graphID:String, partitionID:String):HashMap[Long, Float] {
	    var fromID:long = -1;
	    var toID:long = -1;
 
//	    System.out.println("--------------------- SSSSSSSSS 1 --------------------------");
	    val centralPartionCount:int = Int.parse((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String])(0n));
	    //int partionVertxCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.PARTITION where GRAPH_IDGRAPH=" + graphID + " and IDPARTITION=" + partitionID).value)[(int)0L]);
	    //int vcnt = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    //int worldOnlyVertexCount = vcnt - partionVertxCount; //This is the (N-n) term in APproxRank algo.
//	    System.out.println("--------------------- SSSSSSSSS 2 --------------------------");
	   
	    //HashMap<Long, ArrayList<Long>> worldConnectToLocal = new HashMap<Long, ArrayList<Long>>(); 
	    val fromDegreeDistribution:HashMap[Long, Long] = new HashMap[Long, Long](); 
	    //int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    var fromIDDegree:long = 0;
	    var vval:long = 0;
	    for(var i:int = 0n; i < centralPartionCount; i++){
	 		val c:java.sql.Connection = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		
	 		try{
	 			//Here we get all the edges that are comming from the world to the local sub graph.
	 			val stmt:java.sql.Statement = c.createStatement();
	 			//Note: Here we do not use partTo because we need to find all the out going degrees of fromIDs
	 			val rs:java.sql.ResultSet = stmt.executeQuery("SELECT idfrom,count(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartfrom=" + partitionID + " GROUP BY idfrom;" );
	 			
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1n);
	 						fromIDDegree = rs.getLong(2n);
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
	 		}catch(e:java.sql.SQLException){
	 			e.printStackTrace();
	 		} 
	    }

//	    System.out.println("--------------------- SSSSSSSSS 5 --------------------------");
	    //Note that next we need to divide these authority scores by the number of world only vertices (i.e., N-n).
	    val resultTemp:HashMap[Long, Float] = new HashMap[Long, Float](); 
	    val it3:Iterator[x10.util.Map.Entry[Long, Long]] = fromDegreeDistribution.entries().iterator();

	    var fID:long = 0;
	    var degree:long = 0;
	    var f:float = 0;
	    
	    while(it3.hasNext()){
	    	val et:x10.util.Map.Entry[Long, Long] = it3.next();
	    	fID = et.getKey();
	    	f = et.getValue();
	    	
	    	resultTemp.put(fID, ((1/f) as float));
	    }
//	    System.out.println("--------------------- SSSSSSSSS 6 --------------------------");
 		return resultTemp; //This is the final adjusted result	
	}

	/**
	 * This method processes the query requests to AcaciaForntEnd. This is the main function that answers the queries.
	 */
	public def process(msg:String, buff:BufferedReader, out:PrintWriter){
		var response:String ="";
		var str:String = null;
		
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
	private static def getOutDegreeDistribution(graphID:String, partitionID:String):HashMap[Long,Long]{
        //System.out.println("getting the out degree distribution for graph : " + graphID + " partitionID : " + partitionID);
//
//        return -1;
        val result:HashMap[Long,Long] = new HashMap[Long,Long](); 
	    var fromID:long = -1;
	    var fromDegree:long = -1;
	    
	    val centralPartionCount:int = Int.parse((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String])(0n));
	    //System.out.println("centralPartionCount : " + centralPartionCount);
	    for(var i:int = 0n; i < centralPartionCount; i++){
	 		val c:java.sql.Connection = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		//System.out.println("running for partionin" + i);
	 		try{
	 			//c.setAutoCommit(false);
	 			val stmt:java.sql.Statement = c.createStatement();
	 			//System.out.println("SELECT idfrom,COUNT(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartfrom=" + partitionID + " GROUP BY idfrom;");
	 			val rs:java.sql.ResultSet = stmt.executeQuery("SELECT idfrom,COUNT(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartfrom=" + partitionID + " GROUP BY idfrom;" );
	 			//java.sql.ResultSet rs = stmt.executeQuery("SELECT idfrom,COUNT(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " and (idpartfrom=" + partitionID + " or idpartto=" + partitionID + ") GROUP BY idfrom;" );
	 			//java.sql.ResultSet rs = stmt.executeQuery("SELECT idfrom,COUNT(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " GROUP BY idfrom;" );
	  			var v:int = 0n;
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1n);
	 						fromDegree = rs.getLong(2n);
	 						result.put(fromID, fromDegree);
	 						v++;
	 					}
	 			}else{
	 				Logger_Java.info("result is null");
	 			}
	 			c.close();
	 		}catch(e:java.sql.SQLException){
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
	private static def getIncommingEdges(graphID:String, partitionID:String):HashMap[Long, ArrayList[Long]]{
		val result:HashMap[Long, ArrayList[Long]] = new HashMap[Long, ArrayList[Long]]();
	    var fromID:long = -1;
	    var toID:long = -1;
	     
	    val centralPartionCount:int = Int.parseInt((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String])(0));
	    val partionVertxCount:int = Int.parseInt((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.PARTITION where GRAPH_IDGRAPH=" + graphID + " and IDPARTITION=" + partitionID).value as Rail[String])(0));
	    val vcnt:int = Int.parseInt((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String])(0));
	    val worldOnlyVertexCount:int = vcnt - partionVertxCount; //This is the (N-n) term in APproxRank algo.

	    for(var i:int = 0n; i < centralPartionCount; i++){
	 		val c:java.sql.Connection = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		
	 		try{
	 			//Here we get all the edges that are comming from the world to the local sub graph.
	 			val stmt:java.sql.Statement = c.createStatement();
	 			val rs:java.sql.ResultSet = stmt.executeQuery("SELECT idfrom,idto FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartto=" + partitionID + ";" );
	 
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1n);
	 						toID = rs.getLong(2n);
	 						
	 						//Here the key should be toID. The edge is from the external graph to the loacl graph. So the fromID is in the external graph.
	 						//but toID is on the local graph. We are interested of collecting all the fromIDs that connect with each toID.
	 						//Then we can calculate the authority flow from world to local by summing the inverse of out degrees of fromIDs
	 						//and dividing that value by the number of vertices that are on the external graph.
	 						if(!result.containsKey(toID)){
	 							val temp:ArrayList[Long] = new ArrayList[Long]();
	 							temp.add(fromID);
	 							result.put(toID, temp);
	 						}else{
	 							val temp:ArrayList[Long] = result.get(toID);
	 							temp.add(fromID);
	 							result.put(toID, temp);
	 						}
	 						
	 					}
	 			}
	 			c.close();
	 		}catch(e:java.sql.SQLException){
	 			e.printStackTrace();
	 		} 
	    }
	    
	    return result;
	}
	
	/**
	 * This method gets the in degree distribution of a specific graph partition from the external world.
	 */
	private static def getInDegreeDistribution(graphID:String, partitionID:String):HashMap[Long, Long]{
//        System.out.println("getting the out degree distribution for graph : " + graphID + " partitionID : " + partitionID);
//
//        return -1;
        val result:HashMap[Long, Long] = new HashMap[Long, Long](); 
	    var fromID:long = -1;
	    var fromDegree:long = -1;
	    
	    
	    val centralPartionCount:int = Int.parseInt((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String])(0));
	    
	    for(var i:int = 0n; i < centralPartionCount; i++){
	 		val c:java.sql.Connection = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		
	 		try{
	 			//c.setAutoCommit(false);
	 			val stmt:java.sql.Statement = c.createStatement();
	 			val rs:java.sql.ResultSet = stmt.executeQuery("SELECT idto,COUNT(idfrom) FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartto=" + partitionID + " GROUP BY idto;" );
	 
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1n);
	 						fromDegree = rs.getLong(2n);
	 						result.put(fromID, fromDegree);
	 					}
	 			}
	 			c.close();
	 		}catch(e:java.sql.SQLException){
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
	private def getAuthorityScoresWorldToLocal(graphID:String, partitionID:String):HashMap[Long, Float]{
        val result:HashMap[Long, Float] = new HashMap[Long, Float](); 
	    var fromID:long = -1;
	    var toID:long = -1;
	    val worldConnectToLocal:HashMap[Long, ArrayList[Long]] = new HashMap[Long, ArrayList[Long]](); 
//	    System.out.println("--------------------- SSSSSSSSS 1 --------------------------");
	    val centralPartionCount:int = Int.parseInt((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String])(0));
	    val partionVertxCount:int = Int.parseInt((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.PARTITION where GRAPH_IDGRAPH=" + graphID + " and IDPARTITION=" + partitionID).value as Rail[String])(0));
	    val vcnt:int = Int.parseInt((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select VERTEXCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String])(0));
	    val worldOnlyVertexCount:int = vcnt - partionVertxCount; //This is the (N-n) term in APproxRank algo.
//	    System.out.println("--------------------- SSSSSSSSS 2 --------------------------");
	    for(var i:int = 0n; i < centralPartionCount; i++){
	 		val c:java.sql.Connection = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
// 	 		
	 		try{
	 			//Here we get all the edges that are comming from the world to the local sub graph.
	 			val stmt:java.sql.Statement = c.createStatement();
	 			val rs:java.sql.ResultSet = stmt.executeQuery("SELECT idfrom,idto FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartto=" + partitionID + ";" );
	 
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1n);
	 						toID = rs.getLong(2n);
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
	 							val temp:ArrayList[Long] = new ArrayList[Long]();
	 							temp.add(fromID);
	 							worldConnectToLocal.put(toID, temp);
	 						}else{
	 							val temp:ArrayList[Long] = worldConnectToLocal.get(toID);
	 							temp.add(fromID);
	 							worldConnectToLocal.put(toID, temp);
	 						}
	 						
	 					}
	 			}
	 			c.close();
	 		}catch(e:java.sql.SQLException){
	 			e.printStackTrace();
	 		} 
	    }
//	    System.out.println("--------------------- SSSSSSSSS 3 --------------------------");
	    //Also we need to in degree distribution from world to local.
	    //This we get by calling the method getInDegreeDistribution()

	    //HashMap<Long, ArrayList<Long>> worldConnectToLocal = new HashMap<Long, ArrayList<Long>>(); 
	    val fromDegreeDistribution:HashMap[Long, Long] = new HashMap[Long, Long](); 
	    //int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    var fromIDDegree:long = 0;
	    var vval:long = 0;
	    for(var i:int = 0n; i < centralPartionCount; i++){
	 		val c:java.sql.Connection = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		
	 		try{
	 			//Here we get all the edges that are comming from the world to the local sub graph.
	 			val stmt:java.sql.Statement = c.createStatement();
	 			//Note: Here we do not use partTo because we need to find all the out going degrees of fromIDs
	 			val rs:java.sql.ResultSet = stmt.executeQuery("SELECT idfrom, count(idfrom) FROM (select idfrom from acacia_central.edgemap where idgraph=" + graphID + ") group by idfrom;" );
	 			
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1n);
	 						fromIDDegree = rs.getLong(2n);
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
	 		}catch(e:java.sql.SQLException){
	 			e.printStackTrace();
	 		} 
	    }
//	    System.out.println("--------------------- SSSSSSSSS 4--------------------------");
	    //+ This should be the total out degree distribution for world only vertices
	    //HashMap<Long, Long> inDegreeDistributionHashMap = getInDegreeDistribution(graphID, partitionID);
	    //HashMap<Long, Long> inDegreeDistributionHashMap = fromDegreeDistribution;
	    
	    //Next we calculate the authority scores world to local
	    val it:Iterator[x10.util.Map.Entry[Long, ArrayList[Long]]] = worldConnectToLocal.entries().iterator();
	    
//	    System.out.println("Size : " + worldConnectToLocal.size());
	    
	    var tID:long = 0;
	    var fID:long = 0;
	    var degree:long = 0;
	    var f:float = 0;
	    while(it.hasNext()){
	    	val p:x10.util.Map.Entry[Long, ArrayList[Long]] = it.next();
	    	val arr:ArrayList[Long] = p.getValue(); //The value contains the list of fromIDs that connect with this toID
	    	val itr:Iterator[Long] = arr.iterator();
	    	tID = p.getKey();
	    	f = 0;
//	    	System.out.println("--------------------- SSSSSSSSS 4--------------------------");
	    	//Next we iterate through all the fromIDs
	    	while(itr.hasNext()){
	    		fID = itr.next();
//	    		System.out.println("--------------------- SSSSSSSSS 4AAA--------------------------");
	    		degree = fromDegreeDistribution.get(fID);//This is the entire out degree of fromID
//	    		System.out.println("--------------------- SSSSSSSSS 4BBB--------------------------");
	    		if(result.containsKey(tID)){
//	    			System.out.println("--------------------- SSSSSSSSS 4CCC--------------------------");
                    f = result.get(tID);
//                    System.out.println("--------------------- SSSSSSSSS 4DDD--------------------------");
                    result.put(tID, (f + ((1.0/degree) as float))); //This is the summation of inverse of each out degree of fromIDs
	    		}else{
	    			result.put(tID, ((1.0/degree) as float));
	    		}
	    	}
	    }
//	    System.out.println("--------------------- SSSSSSSSS 5 --------------------------");
	    //Note that next we need to divide these authority scores by the number of world only vertices (i.e., N-n).
	    val resultTemp:HashMap[Long, Float] = new HashMap[Long, Float](); 
	    val it3:Iterator[x10.util.Map.Entry[Long, Float]] = result.entries().iterator();
	    
	    while(it3.hasNext()){
	    	val et:x10.util.Map.Entry[Long, Float] = it3.next();
	    	tID = et.getKey();
	    	f = et.getValue();
	    	
	    	resultTemp.put(tID, ((f/worldOnlyVertexCount) as float));
	    }
//	    System.out.println("--------------------- SSSSSSSSS 6 --------------------------");
 		return resultTemp; //This is the final adjusted result	
	}
	
	/**
	 * This is just a single value. Because there is only one logical vertex in the world.
	 */
	private def getWorldOnlyAuthorityFlow(graphID:String, partitionID:String):double{
		var result:long=-1;
		//-----------------------------------------------------------------------------------
		//Note August 24 2014 : The following code fragment is inefficient because for each method call it constructs
		//the out degree distribution for the entire graph. However, at this moment we follow this design.
		
        //Next at each peer we need to construct the out degree distribution.
        val sb:StringBuilder = new StringBuilder();
        var line:String = null;
        val lst:ArrayList[String] = new ArrayList[String]();
        try{
	        val reader:BufferedReader = new BufferedReader(new FileReader("machines.txt"));
	
	        while((line = reader.readLine()) != null){
	        	//Here we just read first line and then break. But this may not be the best option. Better iterate through all the
	        	//lines and accumulate those to a HashMap in future.
	        	lst.add(line.trim());
	        }
        }catch(ec:IOException){
        	ec.printStackTrace();
        }
        
        for(host:String in lst){
        	//Here we get the number of vertices located on the local graph on each host

    		try{
    			val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
    			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
    			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			var response:String = "";

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
    				sb.add(response);
    				result = 0;
    			}else{
    				result = -1;
    				break;
    			}
    			out.close();    			
    		}catch(e:UnknownHostException){
    			Logger_Java.error("Connecting to host (10) " + host + " got error message : " + e.getMessage());
    		}catch(ec:IOException){
    			Logger_Java.error("Connecting to host (10) " + host + " got error message : " + ec.getMessage());
    		}
    		
    		if(result == -1){
    			Logger_Java.info("There is an error in getting out degree from host : " + host);
    			return result;
    		}
        }    
        
        Logger_Java.info("======AAAAAAAAAA===========");
        
        val outDegreeEntireGraph:HashMap[Long,Long] = new HashMap[Long,Long]();
        val res1:Rail[String] = sb.toString().split(";");
        for(item:String in res1){
        	if(!item.trim().equals("")){
	        	val res2:Rail[String] = item.split(":");
	        	if(res2.size >= 2){
	        		//outDegreeDistribution[Integer.parseInt(res2[0])] = Integer.parseInt(res2[1]);
	        		outDegreeEntireGraph.put(Long.parseLong(res2(0)), Long.parseLong(res2(1)));
	        	}
        	}
        }
        //System.out.println(outDegreeEntireGraph.toString());
        
        val worldOnlyOutDegreeDistribution:HashMap[Long,Long] = getWorldOnlyOutDegreeDistribution(graphID, partitionID);
        var authFlow:double = 0;
        val itr:Iterator[x10.util.Map.Entry[Long,Long]] = worldOnlyOutDegreeDistribution.entries().iterator();
        
        while(itr.hasNext()){
        	val pairs:x10.util.Map.Entry[Long,Long] = itr.next();
        	val vertex:long = pairs.getKey();
        	val vertexWorldOnlyOutDeg:long = pairs.getValue();
        	
        	val vertexEntireGraphOutDeg:Long = outDegreeEntireGraph.get(vertex);
        	if(vertexEntireGraphOutDeg == -1){
        		//System.out.println("Out degree null for vertex : " + vertex);
        	}else{
        		authFlow += (vertexWorldOnlyOutDeg)/vertexEntireGraphOutDeg;
        	}
        }
        Logger_Java.info("Authority flow for sub graph : " + partitionID + " -> " + authFlow);
        result = authFlow as Long;
        Logger_Java.info("======BBBBBBBBBB===========");
		
		//-----------------------------------------------------------------------------------
		return result;
	}
	
	/**
	 * Out degree distribution only for the external world.
	 */
	private static def getWorldOnlyOutDegreeDistribution(graphID:String, partitionID:String):HashMap[Long,Long]{
//        System.out.println("getting the out degree distribution for graph : " + graphID + " partitionID : " + partitionID);
//
//        return -1;
        val result:HashMap[Long,Long] = new HashMap[Long,Long](); 
	    var fromID:long = -1;
	    var fromDegree:long = -1;
 		//java.sql.Connection c = org.acacia.centralstore.java.HSQLDBInterface.getConnection(graphID, partitionID);
 		
	    //"select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID);
	    val centralPartionCount:int = Int.parseInt((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String])(0));
	    
	    for(var i:int = 0n; i < centralPartionCount; i++){
	    	val c:java.sql.Connection = org.acacia.centralstore.java.HSQLDBInterface.getConnectionReadOnly(graphID, ""+i);
	 		try{
	 			//c.setAutoCommit(false);
	 			val stmt:java.sql.Statement = c.createStatement();
	 			val rs:java.sql.ResultSet = stmt.executeQuery("SELECT idfrom,COUNT(idto) FROM acacia_central.edgemap where idgraph=" + graphID + " and idpartfrom <> " + partitionID + " and idpartto <> " + partitionID + " GROUP BY idfrom;" );
	 
	 			if(rs != null){
	 					while(rs.next()){
	 						fromID = rs.getLong(1n);
	 						fromDegree = rs.getLong(2n);
	 						result.put(fromID, fromDegree);
	 					}
	 			}
	 			
	 			c.close();
	 		}catch(e:java.sql.SQLException){
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
	private def getIntersectingTraingles(graphID:String, partitionID:String):long{
		var result:long = -1;
		var fromID:long = -1;
		var toID:long = -1;
	    val centralPartionCount:int = Int.parseInt((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String])(0));
	    val edgeList:HashMap[Long, Long] = new HashMap[Long, Long]();
	    var globalSize:long = 0;
	    
	    for(var i:int = 0n; i < centralPartionCount; i++){	 
	    	val centralStoreBaseDir:String = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location");
	    	//AcaciaHashMapNativeStore store = new AcaciaHashMapNativeStore(Integer.parseInt(graphID), i, centralStoreBaseDir, true);
	    	//AcaciaHashMapCentralStore store = new AcaciaHashMapCentralStore(Integer.parseInt(graphID), i);
	    	//store.loadGraph();
	    	
	    	val store:AcaciaLocalStore = AcaciaLocalStoreFactory.load(Int.parseInt(graphID), i, Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator + graphID + "_centralstore", true);
	    	store.loadGraph();
	    	
	    	globalSize += store.getEdgeCount();
	    }

	    val localSize:long = Int.parseInt((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select EDGECOUNT from ACACIA_META.PARTITION where GRAPH_IDGRAPH=" + graphID + " and IDPARTITION=" + partitionID).value as Rail[String])(0));

	    if(localSize > globalSize){
	    	result = -1;
	    }
	    
		return result;
	}

}