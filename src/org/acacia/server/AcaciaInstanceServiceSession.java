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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.acacia.localstore.java.AcaciaHashMapLocalStore;
import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Utils_Java;
import org.acacia.centralstore.java.AcaciaHashMapCentralStore;
import org.acacia.events.java.ShutdownEvent;
import org.acacia.events.java.ShutdownEventListener;
import org.acacia.events.java.DBTruncateEvent;
import org.acacia.events.java.DBTruncateEventListener;
import org.acacia.query.algorithms.triangles.Triangles;

import com.google.common.base.Splitter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.acacia.query.algorithms.pagerank.ApproxiRank;
import org.acacia.rdf.sparql.ExecuteQuery;

/**
 * Note that one AcaciaInstanceServiceSession will be run by only one place.
 * 
 * @author miyuru
 * 
 */
public class AcaciaInstanceServiceSession extends Thread{	
	private Socket sessionSkt;
	//private GraphDatabaseService graphDB;//This is a reference to the original DB
	private DBTruncateEventListener listener;
	private ShutdownEventListener listenerShtdn;
	//private HashMap<Integer, GraphDatabaseService> graphDBMap = null;
	//Note : Feb 4 2015 - Since we need to deal with the <praphID>_<partitionID> scenario,
	//the key of the graph db map was changed to String
	private HashMap<String, AcaciaHashMapLocalStore> graphDBMap = null;
	private ArrayList<String> loadedGraphs;
	private AcaciaHashMapLocalStore defaultGraph = null;
	private String defaultGraphID=null;
	private String dataFolder;
	private String serverHostName;
	
	public AcaciaInstanceServiceSession(){
		
	}
	
	/**
	 * The constructor
	 * @param socket
	 */
	public AcaciaInstanceServiceSession(Socket socket, HashMap<String, AcaciaHashMapLocalStore> db, ArrayList<String> grp){
		sessionSkt = socket;
		graphDBMap = db;
		loadedGraphs = grp;
		dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
		//System.out.println("MMMMMMMMMMMMM");
	}
	
	public void setGraphDBMap(HashMap<String, AcaciaHashMapLocalStore> db, ArrayList<String> grp){
		graphDBMap = db;
		loadedGraphs = grp;
		dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
	}
	
	public void run(){
		//Logger_Java.info("Running a new AcaciaInstanceServiceSession.");
		//First we need to check if the directory exist. If not we need to create it.
		File dir = new File("/tmp/dgr");
		
		if(!dir.exists()){
			Logger_Java.info("Info : Creating /tmp/dgr directory");
			boolean result = dir.mkdir();
			
			if(result){
				Logger_Java.info("Directory /tmp/dgr created.");
			}
		}
		
		try{
			InputStream inpStrm = sessionSkt.getInputStream();
			BufferedReader buff = new BufferedReader(new InputStreamReader(inpStrm));
			PrintWriter out = new PrintWriter(sessionSkt.getOutputStream());
			String msg = "";
			
			byte[] line = new byte[10];
			//Logger_Java.info("reading line");
			while((msg = buff.readLine())!= null){
				Logger_Java.info("msg : " + msg);
				
				//+Miyuru (May 2014) - This seems rather long if else structure. Better have some other mechanism to
				//query parsing if possible.
				if(msg.equals(AcaciaInstanceProtocol.HANDSHAKE)){
					out.println(AcaciaInstanceProtocol.HANDSHAKE_OK);
					out.flush();
					//Here we get the host name of the main server. We need this information for future
					//operations.
					serverHostName = buff.readLine();
					//Logger_Java.info("serverHostName : " + serverHostName);
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.CLOSE)){
					out.println(AcaciaInstanceProtocol.CLOSE_ACK);
					out.flush();
					out.close();
					buff.close();
					sessionSkt.close();
				}else if(msg.equals(AcaciaInstanceProtocol.SHUTDOWN)){
					out.println(AcaciaInstanceProtocol.SHUTDOWN_ACK);
					out.flush();
					out.close();
					msg = sessionSkt.getRemoteSocketAddress().toString();
					sessionSkt.close();
					fireShutdownEvent(new ShutdownEvent("Got shutdown request from host : " + msg));
					break;
				}else if (msg.equals(AcaciaInstanceProtocol.READY)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.INSERT_EDGES)){
//					out.println(AcaciaInstanceProtocol.OK);
//					out.flush();
//					
//					//From here onwards we should receive a collection of edges
//					msg = buff.readLine();
//					
//					String graphID = msg;
//					
//					Logger_Java.info("graph id is : " + msg);
//					
//					out.println(AcaciaInstanceProtocol.OK);
//					out.flush();
//					
//					//This is the partition ID
//					msg = buff.readLine();
//					
//					setDefaultGraph(graphID, msg);
//					
//					//Just return an acknowledgement.
//					out.println(AcaciaInstanceProtocol.OK);
//					out.flush();
//					
//					msg = buff.readLine();
//					
//					while(!msg.equals(AcaciaInstanceProtocol.INSERT_EDGES_COMPLETE)){
//						Logger_Java.info("Adding Edge : " + msg);
//						String[] vertsArr = msg.split(" ");//We expect the edge to be split by a space.
//						try{
//							insertEdgeUsingIndex(Long.parseLong(vertsArr[0]), Long.parseLong(vertsArr[1]));
//						}catch(NumberFormatException ex){
//							//Just ignore. Expect the messages only in the for <long> <long> <long>
//							Logger_Java.error("Error : " + ex.getMessage());
//						}
//						msg = buff.readLine();
//					}
//					
//					out.println(AcaciaInstanceProtocol.INSERT_EDGES_ACK);
//					out.flush();
				}else if (msg.equals(AcaciaInstanceProtocol.TRUNCATE)){					
					fireDBTruncateEvent(new DBTruncateEvent("Truncating Acacia Instance"));
					out.println(AcaciaInstanceProtocol.TRUNCATE_ACK);
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.COUNT_VERTICES)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					String graphID = msg;
					Logger_Java.info("graph id is : " + graphID);
					Logger_Java.info("Counting vertices, graph id is : " + graphID);
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					
					Logger_Java.info("partition id is : " + msg);
					
					msg = countVertices(graphID, msg);
					
					out.println(msg);
					out.flush();
					Logger_Java.info("vcount is : " + msg);
				}else if(msg.equals(AcaciaInstanceProtocol.COUNT_EDGES)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					String graphID = msg;
					Logger_Java.info("graph id is : " + graphID);
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					
					Logger_Java.info("partition id is : " + msg);
					
					msg = countEdges(graphID, msg);
					out.println(msg);
					out.flush();
					Logger_Java.info("ecount is : " + msg);
				}else if(msg.equals(AcaciaInstanceProtocol.DELETE)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					String graphID = buff.readLine().trim();
					
					Logger_Java.info("graph id is : " + graphID);
					
					//Now we want to get the exact partition id that we will be deleting.
					//Because there will be other places running in this node that will handle other
					//partitions corresponding to the same graph.
					out.println(AcaciaInstanceProtocol.SEND_PARTITION_ID);
					out.flush();
					
					msg = buff.readLine().trim();
					//msg = ""+deleteAllverticesandEdgesofGraph(msg);
					
					Logger_Java.info("partition id is : " + msg);
					
					//we send the graphid and partitionid
					msg = ""+deleteGraph(graphID, msg);
					//We return the number of vertices deleted.
					out.println(msg);
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.LOAD_GRAPH)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					
					Logger_Java.info("graph id is : " + msg);
					//int gid = Integer.parseInt(msg);
					String graphID = msg;
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					String gid = graphID + "_" + msg;
					
                    //Need to load the graph only when required
					if (!graphDBMap.containsKey(gid)){
						loadLocalStore(graphID, msg);
					}
					
					//Just return an acknowledgement.
					out.println(AcaciaInstanceProtocol.LOAD_GRAPH_ACK);
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.UNLOAD_GRAPH)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					String graphID = msg;
					Logger_Java.info("graph id is : " + graphID);
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					
					unloadLocalStore(graphID, msg);
					
					//Just return an acknowledgement.
					out.println(AcaciaInstanceProtocol.UNLOAD_GRAPH_ACK);
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.SET_GRAPH_ID)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					String graphID = msg;
					
					Logger_Java.info("graph id is : " + graphID);
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					
					setDefaultGraph(graphID, msg);
					
					//Just return an acknowledgement.
					out.println(AcaciaInstanceProtocol.SET_GRAPH_ID_ACK);
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.BATCH_UPLOAD)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					
					String graphID = msg;
										
					out.println(AcaciaInstanceProtocol.SEND_FILE_NAME);
					out.flush();
					
					msg = buff.readLine().trim();
					String fileName = msg;
					
					out.println(AcaciaInstanceProtocol.SEND_FILE_LEN);
					out.flush();
					
					msg = buff.readLine().trim();
					long fileLen = Long.parseLong(msg);
					
					out.println(AcaciaInstanceProtocol.SEND_FILE_CONT);
					out.flush();
					
					//Here we need to get the file size and then check if the file size has been achieved.
					//Mere file exitance is not enough to continue, because we might have another thread writing data to the file. 
					
					String fullFilePath = "/tmp/dgr/" + fileName;
					File f = new File(fullFilePath);
					
					while((!f.exists()) && (f.length() < fileLen)){
						msg = buff.readLine().trim();
						
						Logger_Java.info("curlen : " + f.length() + " fileLen : " + fileLen);
						
						if(msg.equals(AcaciaInstanceProtocol.FILE_RECV_CHK)){
							out.println(AcaciaInstanceProtocol.FILE_RECV_WAIT);
							out.flush();
						}
					}
					
					msg = buff.readLine().trim();
					
					if(msg.equals(AcaciaInstanceProtocol.FILE_RECV_CHK)){
						out.println(AcaciaInstanceProtocol.FILE_ACK);
						out.flush();
					}
					
					Logger_Java.info("Got the file : " + fileName);
					
					fileName = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
					Logger_Java.info("Partition ID : " + fileName);
					//here is where local store is constructed
					//the fileName contains the graph partitionID. So we do not have to specifiy that again here.
					unzipAndBatchUpload(graphID, fileName);
					
					while(!isUploadCompleted(fullFilePath)){
						msg = buff.readLine().trim();
						
						if(msg.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_CHK)){
							out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_WAIT);
							out.flush();
						}
					}
					
					out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_ACK);//This is the sign of upload completeion.
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.OUT_DEGREE_DIST)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the graph ID
					msg = buff.readLine().trim();
					
					String graphID = msg;
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the partition ID
					msg = buff.readLine().trim();
					
					msg = outDegreeDistribution(graphID, msg);
					out.println(msg);
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.PAGE_RANK)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the graph ID
					msg = buff.readLine().trim();
					//msg = outDegreeDistribution(msg);
					//out.println("result-from-" + msg);
					
					String graphID = msg;
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the graph ID
					msg = buff.readLine().trim();
					//msg = outDegreeDistribution(msg);
					//out.println("result-from-" + msg);
					
					String partitionID = msg;
					
					//Next is the host list
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					
					msg = pageRankLocal(graphID, partitionID, msg);
					out.println(msg);
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.PAGE_RANK_TOP_K)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the graph ID
					msg = buff.readLine().trim();
					//msg = outDegreeDistribution(msg);
					//out.println("result-from-" + msg);
					
					String graphID = msg;
					
					//Next, we get the partitionID on which we are operating
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the graph ID
					msg = buff.readLine().trim();
					
					
					String partitionID = msg;
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					String hostList = buff.readLine().trim(); //Here we get the list of hosts involved in the Top-K PageRank calculation
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim(); //Here we get the K value of Top-K PageRank
					
					msg = pageRankTopKLocal(graphID, partitionID, hostList, Integer.parseInt(msg));
					out.println(msg);
					out.flush();
				}else if (msg.equals(AcaciaInstanceProtocol.TRIANGLES)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the graph ID
					msg = buff.readLine().trim();
					
					String graphID = msg;
					
					//Once we get the graph ID we need to figureout on which partition are we going to operate on
					//We get this information from the master
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the graph ID
					msg = buff.readLine().trim();
					String partitionID = msg;
					msg = countTrainglesLocal(graphID, partitionID);
					out.println(msg);
					out.flush();
					
				}else if(msg.equals(AcaciaInstanceProtocol.STATUS)){
					out.println(getInstanceStatus());
					out.flush();
				} else if(msg.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_CENTRAL)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					msg = buff.readLine().trim();
					System.out.println(msg);
					String graphID = msg;

					out.println(AcaciaInstanceProtocol.SEND_FILE_NAME);
					out.flush();
					msg = buff.readLine().trim();
					String fileName = msg;
					System.out.println(msg);

					out.println(AcaciaInstanceProtocol.SEND_FILE_LEN);
					out.flush();

					msg = buff.readLine().trim();
					long fileLen = Long.parseLong(msg);
					System.out.println(msg);

					out.println(AcaciaInstanceProtocol.SEND_FILE_CONT);
					out.flush();

					// Here we need to get the file size and then check if the
					// file size has been achieved.
					// Mere file exitance is not enough to continue, because we
					// might have another thread writing data to the file.

					String fullFilePath = "/tmp/dgr/" + fileName;
					File f = new File(fullFilePath);

					while ((!f.exists()) && (f.length() < fileLen)) {
						msg = buff.readLine().trim();

						Logger_Java.info("curlen : " + f.length()
								+ " fileLen : " + fileLen);

						if (msg.equals(AcaciaInstanceProtocol.FILE_RECV_CHK)) {
							out.println(AcaciaInstanceProtocol.FILE_RECV_WAIT);
							out.flush();
						}
					}

					msg = buff.readLine().trim();

					if (msg.equals(AcaciaInstanceProtocol.FILE_RECV_CHK)) {
						out.println(AcaciaInstanceProtocol.FILE_ACK);
						out.flush();
					}

					Logger_Java.info("Got the file--> : " + fileName);

					fileName = fileName.substring(fileName.indexOf("_") + 1,
							fileName.lastIndexOf("_"));
					Logger_Java.info("Partition ID : " + fileName);
					unzipAndBatchUploadCentralStore(graphID, fileName);

					while (!isUploadCompleted(fullFilePath)) {
						msg = buff.readLine().trim();

						if (msg.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_CHK)) {
							out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_WAIT);
							out.flush();
						}
					}

					out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_ACK);// This
																			// is
																			// the
																			// sign
																			// of
																			// upload
																			// completeion.
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_RDF)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					msg = buff.readLine().trim();
					System.out.println("RDF msg recieved"+msg);
					String graphID = msg;
				}else if(msg.equals(AcaciaInstanceProtocol.EXECUTE_QUERY)){
					out.println(AcaciaInstanceProtocol.SEND_QUERY);
					out.flush();
					msg = buff.readLine().trim();
					String query=msg;
					
					out.println(AcaciaInstanceProtocol.SEND_GID);
					out.flush();
					msg = buff.readLine().trim();
					String gID=msg;
					
					out.println(AcaciaInstanceProtocol.SEND_PARTITION_ID);
					out.flush();
					msg = buff.readLine().trim();
					String pID=msg;
					
					ExecuteQuery execute_query=new ExecuteQuery();  
					ArrayList<String> result=execute_query.executeQuery(query,gID,pID);
					
					if((result != null) && (!result.isEmpty())){
						out.println("Not empty");
						out.flush();
						
						for(int i=0;i<result.size();i++){
							
							msg = buff.readLine().trim();
							if(msg.equals("Send")){
							
						out.println(result.get(i));
						out.flush();
							}
						}
						out.println("Finish");
						out.flush();
					}
					else{
						out.println("Empty");
						out.flush();
					}
				}else{
					System.out.println("************************|" + msg + "|");
				}
			}
		}catch(IOException e){
			Logger_Java.error("Error : " + e.getMessage());
		}
	}

	private String countTrainglesLocal(String g, String p) {
		String result = null;
		String gid = g + "_" + p;
		//First we need to load the graph database server instance.
		AcaciaHashMapLocalStore graphDB = null;
		if (defaultGraph == null){	
			graphDB = graphDBMap.get(gid);
			if(graphDB == null){
				//We see whether the graph is offline
				if(isGraphDBExists(g, p)){
					loadLocalStore(g, p);
					//System.out.println("+++++++++++++++++++++++++++++>");
				}
				
				graphDB = graphDBMap.get(gid);
				if(graphDB == null){		
					result = "-1";
					//System.out.println("==++-->The graph db is null");
					return result;
				}
			}
		}else{
			graphDB = defaultGraph;
		}
		//System.out.println("++==++-->now running traingles");
		result = Triangles.run(graphDB, g, p, Utils_Java.getServerHost());
		//System.out.println("++==++-->result at (1) : " + result);
		return result;
	}

	private String pageRankTopKLocal(String graphID, String partitionID, String hostList, int k) {
		String result = null;
		AcaciaHashMapLocalStore graphDB = null;
		String gid = graphID + "_" + partitionID;
		Logger_Java.info("PPPPP1");
		if (defaultGraph == null){	
			System.out.println("PPPPP2");
			graphDB = graphDBMap.get(gid);
			if(graphDB == null){
				//We see whether the graph is offline
				if(isGraphDBExists(graphID, partitionID)){
					loadLocalStore(graphID, partitionID);
				}
				
				graphDB = graphDBMap.get(gid);
				if(graphDB == null){		
					result = "-1";
					return result;
				}
			}
			System.out.println("PPPPP3");
		}else{
			System.out.println("PPPPP4");
			graphDB = defaultGraph;
		}
		//entireGraphSize = Integer.parseInt(countVertices(graphID));
		System.out.println("Started Approx Rank");
		result = ApproxiRank.run(graphID, partitionID, graphDB, hostList, serverHostName, k);
		System.out.println("Done Approx Rank");
		return result;
	}

	private String getInstanceStatus() {
        String dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
        String line = null;
        String[] strArr = null;
        String instanceCapacity = null;
        String thisHost = null;
        
        try{
        	thisHost = java.net.InetAddress.getLocalHost().getHostName();
	        BufferedReader reader = new BufferedReader(new FileReader("conf/localstorage.txt"));
	
	        while((line = reader.readLine()) != null){
	        	//Here we just read first line and then break. But this may not be the best option. Better iterate through all the
	        	//lines and accumulate those to a HashMap in future.
	        	strArr = line.split(":");
	        	if(thisHost.equals(strArr[0])){
	        		break;
	        	}	        	
	        }
        }catch(UnknownHostException e){
			Logger_Java.error("Connecting to localhost got error message (11) : " + e.getMessage());
		}catch(IOException ec){
        	ec.printStackTrace();
        }
        
        instanceCapacity = strArr[1];
        
        //First we need to check whether the Acacia's data directory exists or not.
        File fileChk = new File(dataFolder);
        if(fileChk.exists() && fileChk.isDirectory()){
        	//There is no problem
        }else{
        	//Here we need to create the directory first
        	try{
        		FileUtils.forceMkdir(fileChk);
        	}catch(IOException ex){
        		Logger_Java.error("Creating the Acacia data folder threw an Exception : " + ex.getMessage());
        	}
        }
        
        long size = Math.round(FileUtils.sizeOfDirectory(new File(dataFolder))/(1024*1024)); // Divide by 1024*1024 to convert to MB
        double d = size/Double.parseDouble(instanceCapacity);
        java.text.DecimalFormat f = new java.text.DecimalFormat();
        f.setMaximumFractionDigits(2);
        
        //The status message is of the format <Size of the local storage usage>:<Max local storage size>:<percentage of local storage used>
        instanceCapacity = size + ":" + instanceCapacity + ":" + f.format(d);
        
        System.out.println("status : " + instanceCapacity);
        
		return instanceCapacity;
	}

	/**
	 * Batch upload process will be completed by deleting the transfered file. Therefore we check the status of the file and report the status.
	 * @param filePath
	 * @return
	 */
	private boolean isUploadCompleted(String filePath){
		boolean result = false;
		
		File f = new File(filePath);
		
		if(f.exists()){
			result = false;
		}else{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * This method checks if a given graph db exists in the AcaciaInstance.
	 * @param graphID
	 * @return
	 */
	private boolean isGraphDBExists(String graphID, String partitionID){
		File f = new File(dataFolder + "/" + graphID + "_" + partitionID);
		
		return f.isDirectory();
	}
	
	private boolean isGraphDBLoaded(String graphID, String graphPartitionID){	
		return graphDBMap.containsKey(graphID + "_" + graphPartitionID);
	}
	
	public void unzipAndBatchUpload(final String graphID, final String partitionID) {
		AcaciaHashMapLocalStore localStore = new AcaciaHashMapLocalStore(Integer.parseInt(graphID), Integer.parseInt(partitionID));
		localStore.loadGraph();
				try{
					//Unzipping starts here
					Runtime r = Runtime.getRuntime();
					
					//Next, we unzip the file
					Process p = r.exec("unzip /tmp/dgr/" + graphID + "_" +  partitionID + ".zip -d " + Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator  + graphID + "_" + partitionID);
					p.waitFor();
					
					BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = "";
					
					while((line=b.readLine())!= null){
						System.out.println(line);
					}
					System.out.println("Check 3");
					
					System.out.println("Deleting|" + "rm /tmp/dgr/" + graphID + "_" + partitionID + "_trf.zip|");
					p = r.exec("rm /tmp/dgr/" + graphID + "_" + partitionID + ".zip");
					p.waitFor();
					
					//System.out.println("Completed unzipping");
					//Unzipping completed
					/*
					 * August 3 2015 10 50pm: Wee need not have the following code since we are sending the serialized files.
			        Splitter splitter = null;
			        BufferedReader br;
		            br = new BufferedReader(new FileReader("/tmp/dgr/" + graphID + "_" +  partitionID), 10 * 1024 * 1024);
		            line = br.readLine();
		            
		            if(line.contains(" ")){
		            	splitter = Splitter.on(' ');
		            }else if(line.contains("	")){
		            	splitter = Splitter.on('	');
		            }
		            
		            while (line != null) {
			            Iterator<String> dataStrIterator = splitter.split(line).iterator();
			            Long startVertexID = Long.parseLong(dataStrIterator.next());
			            Long endVertexID = Long.parseLong(dataStrIterator.next());
			            
			            localStore.addEdge(startVertexID, endVertexID);
			            line = br.readLine();
		            }
		            
		            localStore.storeGraph();
		            */
					//Also we need to add a catalog record in the instance's local data store about the graph and its partition IDs
					writeCatalogRecord("" + graphID + ":" + partitionID);
					
				}catch(Exception e){
					e.printStackTrace();
				}
	}
	
	private void unzipAndBatchUploadCentralStore(String graphID, String partitionID) {
		try {
			File f = new File(Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator + graphID + "_centralstore");
			
			if(!f.isDirectory()){
				f.mkdir();
			}
			
			Runtime r = Runtime.getRuntime();
			Process p = r.exec("unzip /tmp/dgr/" + graphID + "_" + partitionID + "_trf.zip -d " + Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator + graphID + "_centralstore/" + graphID + "_" + partitionID);
			p.waitFor();

			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";

			while((line=b.readLine())!= null){
				System.out.println("Central store graph sending " + graphID + "_" + partitionID);
			}
			
			System.out.println("Deleting676767");
			System.out.println("Deleting|" + "rm /tmp/dgr/" + graphID + "_" + partitionID + "_trf.zip|");
			p = r.exec("rm /tmp/dgr/" + graphID + "_" + partitionID + "_trf.zip");
			p.waitFor();
			
			System.out.println("Completed unzipping");		
			
		} catch (Exception e) {
			Logger_Java.info("Error : " + e.getMessage());
		}
	}

	private void writeCatalogRecord(String record) {
        //First we need to check whether the Acacia's data directory exists or not.
        File fileChk = new File(dataFolder);
        if(fileChk.exists() && fileChk.isDirectory()){
        	//There is no problem
        }else{
        	//Here we need to create the directory first
        	try{
        		org.apache.commons.io.FileUtils.forceMkdir(fileChk);
        	}catch(IOException ex){
        		Logger_Java.error("Creating the Acacia data folder threw an Exception : " + ex.getMessage());
        	}
        }				
		
		File catalog = new File(dataFolder + "/catalog");
		boolean b = false;
		try{
			if(!catalog.exists()){
				b = catalog.createNewFile();
			}
			
			if(b){
				System.out.println("The catalog file was newly created.");
			}
		
			BufferedWriter writer = new BufferedWriter(new FileWriter(catalog, true));//We are appending to the file rather than replacing
			writer.write(record);
			writer.write("\n");//We need a new line to separate between two records.
			writer.flush();
			writer.close();
		}catch(IOException e){
			System.out.println("There is an error is writing to the AcaciaInstance's catalog.");
		}
	}
	
	/**
	 * We are deleting all the content corresponding to this subgraph.
	 * Note that it should be subgraph not the entire graph. Because there may be multiple places handling
	 * multiple subgraphs of the same graph located on a particular node. Therefore, every place should delete
	 * only the subgraph that it is responsible of.
	 * 
	 * This pattern of operation is required only when dealing with the graph data.
	 * 
	 * @param graphID
	 * @return
	 */
    //ToDO: In future we need to update the catalog file located in the data directory.
	private String deleteGraph(String graphID, String partitionID) {
		String result = "0";
		
		//We need to first turn-off the graph DB instance
		if (graphDBMap.containsKey(graphID)){
			Logger_Java.info("Unloading the graph - " + graphID + ":" + partitionID);
			unloadLocalStore(graphID, partitionID);
		}else{
			Logger_Java.info("The following graph was not loaded to the system - " + graphID + ":" + partitionID);
		}
		
		//Next we need to delete the file content from the acacia instance directory.
		try{
			Logger_Java.info("Deleting the folder: " + dataFolder + "/" + graphID + "_" + partitionID);
			FileUtils.deleteDirectory(new File(dataFolder + "/" + graphID + "_" + partitionID));
			Logger_Java.info("Done deleting.");
		}catch(IOException ec){
			Logger_Java.error("Error in deleting the file : " + ec.getMessage());
		}
		
		/*
		//Here we may have more than one file to handle.
		File folder = new File(dataFolder);
		File[] files = folder.listFiles(new FilenameFilter(){

			public boolean accept(File dir, String name) {
				return name.matches(graphID + "_*");
			}
			
		});
		
		for(File file : files){
			if(!file.delete()){
				Logger_Java.info("Cannot delete file : " + file.getName());
				result = "-1";
			}
		}
		*/
		
		//Also we need to update the local catalog after deleting the graph partition.
		//TODO in future : There will be a contention issue when multiple threads try to write to the same file.
		
		
		return result;
	}

	public void setDefaultGraph(String graphID, String partitionID){
		String gid = graphID + "_" + partitionID;
		defaultGraph = graphDBMap.get(gid);
		defaultGraphID = gid;
		System.out.println("The default graph is set to : " + gid);
	}
	
	public void unSetDefaultGraph(String graphID, String partitionID){
		defaultGraph = null;
		defaultGraphID = null;
	}
	
	public void loadLocalStore(String graphID, String partitionID){
		String gid = graphID + "_" + partitionID;
		//int graphID, int partitionID
		AcaciaHashMapLocalStore graphDB = new AcaciaHashMapLocalStore(Integer.parseInt(graphID), Integer.parseInt(partitionID));
		graphDB.loadGraph();//We need to load the graph explicitly
		graphDBMap.put(gid, graphDB);
		loadedGraphs.add(gid);
		
//		try{
//			//Logger_Java.info("Loaded the graph " + gid + " at " + java.net.InetAddress.getLocalHost().getHostName());
//		}catch(UnknownHostException ex){
//			Logger_Java.error("Error : " + ex.getMessage());
//		}
		//System.out.println("------------ Done Running from AAAAAAAAAAAAAAAAAAAAAAAAAAAA--------");
	}
	
	/**
	 * 28/10/2014
	 * We need not to worry about which partition we are dealing with when loading and unloading graphs.
	 * It is because we know each place is handling only one subgraph from each graph. So we can directly refer
	 * the data by graph id without use of partition id. 
	 * @param graphID
	 */
	public void unloadLocalStore(String graphID, String partitionID){
		Iterator it = graphDBMap.entrySet().iterator();
		HashMap<String, AcaciaHashMapLocalStore> graphDBMap2 = new HashMap<String, AcaciaHashMapLocalStore>();
		ArrayList<String> loadedGraphs2 = new ArrayList<String>();
		String gid = graphID + "_" + partitionID;
		
		while(it.hasNext()){
			Map.Entry<String, AcaciaHashMapLocalStore> pairs = (Map.Entry<String, AcaciaHashMapLocalStore>)it.next();
			if (pairs.getKey().equals(gid)){
				((AcaciaHashMapLocalStore)pairs.getValue()).shutdown();
				try{
					Logger_Java.info("Unloaded the graph " + gid + " at " + java.net.InetAddress.getLocalHost().getHostName());
				}catch(UnknownHostException ex){
					Logger_Java.error("Error : " + ex.getMessage());
				}
			}else{
				graphDBMap2.put(pairs.getKey(), pairs.getValue());
				loadedGraphs2.add(pairs.getKey());
			}
		}
		
		graphDBMap = graphDBMap2;
		loadedGraphs = loadedGraphs2; 
	}
	
	private void fireDBTruncateEvent(DBTruncateEvent evt){
		listener.truncateEventOccurred(evt);
	}
	
	private void fireShutdownEvent(ShutdownEvent evt){
		listenerShtdn.shutdownEventOccurred(evt);
	}
	
	/**
	 * This method inserts an edge to the graph DB
	 * @param startVertexID
	 * @param endVertexID
	 */
	public void insertEdge(long graphID, long startVertexID, long endVertexID){		
		AcaciaHashMapLocalStore graphDB = null;
		
		if (defaultGraph == null){	
			graphDB = graphDBMap.get(graphID);
			if(graphDB == null){
				Logger_Java.error("Error : The graph database instance is NULL.");
			}
		}else{
			graphDB = defaultGraph;
		}
		
		graphDB.addEdge(startVertexID, endVertexID);

		System.out.println("Done adding edge : " + startVertexID + " " + endVertexID);
	}
	
	/**
	 * This method returns the vertex's unique id if one exists. If not returns -1.
	 * @param graphID
	 * @param vertexID
	 * @return
	 */
	/*
	public long vertexExists(long graphID, long vertexID){
		String resultStr = "-1";
		GraphDatabaseService graphDB = null;
		
		if (defaultGraph == null){	
			graphDB = graphDBMap.get(graphID);
			if(graphDB == null){
				return -1;
			}
		}else{
			graphDB = defaultGraph;
		}
		
		ExecutionEngine engine = new ExecutionEngine(graphDB);
		ExecutionResult exResult = engine.execute("start n=node(*) where n.vid='" + vertexID + "' return n;");
		Iterator itr = exResult.iterator();

		if(itr.hasNext()){
			resultStr = itr.next().toString();
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(resultStr);
			
			while(m.find()){
				resultStr = m.group();
				break;
			}
		}
		
		if (!resultStr.trim().equals("")){
			return Long.parseLong(resultStr);
		}else{
			return -1;
		}
	}	
	*/
	
	/*
	public long deleteAllverticesandEdgesofGraph(String graphID){
		long vcnt = 0;
		String resultStr = "";
		GraphDatabaseService graphDB = null;
		
		if (defaultGraph == null){	
			graphDB = graphDBMap.get(Integer.parseInt(graphID));
			if(graphDB == null){
				return -1;
			}
		}else{
			graphDB = defaultGraph;
		}
		
		ExecutionEngine engine = new ExecutionEngine(graphDB);
		ExecutionResult exResult = engine.execute("start n=node(*) return collect(id(n));");
		Iterator itr = exResult.iterator();

		if(itr.hasNext()){
			resultStr = itr.next().toString();
//			
//			System.out.println("1111============================");
//			Pattern p = Pattern.compile("\\d+(,[ ]\\d+)*");
//			System.out.println("2222============================");
//			System.out.println("resultStr : |" + resultStr+"|");
//			Matcher m = p.matcher(resultStr);
//			System.out.println("wwwwww============================");
//			while(m.find()){
//				resultStr = m.group();
//				break;
//			}
//			System.out.println("llllll============================");
//			
			int beginIdx = resultStr.indexOf("[");
			int endIdx = resultStr.indexOf("]");
			resultStr = resultStr.substring(beginIdx+1, endIdx);
		}
			

		if(!resultStr.contains("collect")){
			String[] verts=resultStr.split(",");
			int len= verts.length;

			for(int c=0; c < len; c++){
				//This query returns the number of vertices that are affected(deleted).
				String vert = verts[c].trim();
				if (!vert.equals("")){
					ExecutionResult exResult2 = engine.execute("start n=node(" + vert + ") match n-[r]-() delete n,r;");
					Iterator itr2 = exResult.iterator();
		
					if(itr2.hasNext()){
						resultStr = itr2.next().toString();
						
						Pattern p = Pattern.compile("\\d+");
						Matcher m = p.matcher(resultStr);
						
						while(m.find()){
							vcnt += Long.parseLong(m.group());
							break;
						}
					}
				}
			}
		}
		
		return vcnt;//We return the number of vertices deleted.
	}
	
	*/
	
	public String countVertices(String graphID, String partitionID){
		String result = "-1";	
		AcaciaHashMapLocalStore localStore = new AcaciaHashMapLocalStore(Integer.parseInt(graphID), Integer.parseInt(partitionID));
		localStore.loadGraph();
		result = "" + localStore.getVertexCount();
		
		//result = "" + (Long.parseLong(result) - 1); //By default there is avertex created by every Neo4j instance. Therefore we need to deduct one to compensate this.
		
		return result;
	}
	
	public String countEdges(String graphID, String partitionID){
		String result = "-1";		
		AcaciaHashMapLocalStore localStore = new AcaciaHashMapLocalStore(Integer.parseInt(graphID), Integer.parseInt(partitionID));
		localStore.loadGraph();
		result = "" + localStore.getEdgeCount();
		
		return result;
	}
	
	public String outDegreeDistribution(String graphID, String partitionID){
		org.acacia.log.java.Logger_Java.info("Out degree distribution calculation started at : " + org.acacia.util.java.Utils_Java.getHostName());
		StringBuilder resultSB = new StringBuilder();		
		AcaciaHashMapLocalStore graphDB = null;
		String gid = graphID + "_" + partitionID;
		
		if (defaultGraph == null){
			System.out.println("MM1");
			graphDB = graphDBMap.get(gid);
			System.out.println("MM2");
			if(graphDB == null){
				//We see whether the graph is offline
				System.out.println("MM4");
				if(isGraphDBExists(graphID, partitionID)){
					System.out.println("Start loading the graph : " + graphID);
					loadLocalStore(graphID, partitionID);
					System.out.println("Loaded the graph : " + graphID);
				}
				System.out.println("MM5");
				graphDB = graphDBMap.get(gid);
				if(graphDB == null){		
					resultSB.append("-1");
					return resultSB.toString();
				}
			}
		}else{
			graphDB = defaultGraph;
		}	
		
		HashMap<Long, Long> resMap = null;
		
//		//Next we get the out degree of each vertex
//		System.out.println("ASDS111");
//		ExecutionEngine engine = new ExecutionEngine(graphDB);		
//		HashMap<Long, Long> resMap = new HashMap<Long, Long>(); 
//		ExecutionResult execResult = engine.execute("start n=node(*) match n-->m return n, n.vid, count(m)");
//		
//		for(Map<String, Object> row : execResult){	
//			long vid = -1;
//			long oDeg = -1;
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
//					long v = resMap.get(vid);
//					v = v + oDeg;
//					resMap.put(vid, v);
//			}else{
//				resMap.put(vid, oDeg);
//			}
//		}
//		System.out.println("ASDS222");
		
//        String dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
//        String line = null;
		
		resMap = graphDB.getOutDegreeDistributionHashMap();
		
        int partitionId = -1;
//        try{
//	        BufferedReader reader = new BufferedReader(new FileReader(dataFolder + "/catalog"));
//	
//	        while((line = reader.readLine()) != null){
//	        	if(line.startsWith("" + graphID + ":")){
//	        		break;
//	        	}
//	        }
//	        reader.close();
//        }catch(IOException ec){
//        	ec.printStackTrace();
//        }
//        
//        partitionId = Integer.parseInt(line.split(":")[1]);
        System.out.println("ASDS223");
        //partitionId = Utils_Java.getPartitionIDFromCatalog(Integer.parseInt(graphID));
        
        //System.out.println("partition ID : " + partitionId);
        //Next we request and get all the local to world degree distribution. This will be a list of vertices of this
        //graph partition which are connected with the extrenal world, each vertex will have its associated out degree.
        
        //AcaciaInstanceToManagerAPI
        //String serverHost, int graphID, int partitionID
        String serverHostName = Utils_Java.getServerHost();
		
		//Next, we need to resolve the out links that are from the vertices of this subgraph to the external world
		HashMap<String, String> res = AcaciaInstanceToManagerAPI.getLambdaOutDegreeDistribution(serverHostName, graphID, partitionID);
		long vertex = 0;
		long outDegree = 0;
		long updatecount = 0;
		for(Entry<String, String> item : res.entrySet()){
			vertex = Integer.parseInt(item.getKey());
			outDegree = Integer.parseInt(item.getValue());
			
			if(resMap.containsKey(vertex)){
				outDegree += resMap.get(vertex);
				updatecount++;
			}
			
			resMap.put(vertex, outDegree);
		}
		
		System.out.println("=========================> updated this many vertices : " + updatecount);
		
		for(Entry<Long, Long> item : resMap.entrySet()){
			resultSB.append("" + item.getKey() + ":" + item.getValue()+";");
		}
		
		org.acacia.log.java.Logger_Java.info("Out degree distribution calculation completed at : " + org.acacia.util.java.Utils_Java.getHostName());
		
		return resultSB.toString();
	}
	
	public String pageRankLocal(String graphID, String partitionID, String hostList){
		String result = null;
		AcaciaHashMapLocalStore graphDB = null;
		System.out.println("PPPPP1");
		String gid = graphID + "_" + partitionID;
		
		if (defaultGraph == null){	
			System.out.println("PPPPP2");
			graphDB = graphDBMap.get(gid);
			if(graphDB == null){
				//We see whether the graph is offline
				if(isGraphDBExists(graphID, partitionID)){
					loadLocalStore(graphID, partitionID);
				}
				
				graphDB = graphDBMap.get(gid);
				if(graphDB == null){		
					result = "-1";
					return result;
				}
			}
			System.out.println("PPPPP3");
		}else{
			System.out.println("PPPPP4");
			graphDB = defaultGraph;
		}
		//entireGraphSize = Integer.parseInt(countVertices(graphID));
		System.out.println("Started Approx Rank");
		result = ApproxiRank.run(graphID, partitionID, graphDB, hostList, serverHostName, -1); //Here we send -1 meaning we want the entire pagerank vector, nit top-k pageranks.
		System.out.println("Done Approx Rank");
		return result;
	}
	
//	public String removeVertices(String graphID){
//		String result = "-1";
////		GraphDatabaseService graphDB = null;
////		
////		if (defaultGraph == null){	
////			graphDB = graphDBMap.get(Integer.parseInt(graphID));
////			if(graphDB == null){
////				return result;
////			}
////		}else{
////			graphDB = defaultGraph;
////		}
////		
////		ExecutionEngine engine = new ExecutionEngine(graphDB);
////		ExecutionResult exResult = engine.execute("start n=node(*) return count(n);");
////		Iterator itr = exResult.iterator();
////
////		if(itr.hasNext()){
////			result = itr.next().toString();
////			Pattern p = Pattern.compile("\\d+");
////			Matcher m = p.matcher(result);
////			
////			while(m.find()){
////				result = m.group();
////			}
////		}
//		
//		//AcaciaHashMapLocalStore localStore = new AcaciaHashMapLocalStore();
//		
//		return result;
//	}	
	
	public void addDBTruncateEventListener(DBTruncateEventListener listener){
		this.listener = listener;
	}
	
	public void addShutdownEventListener(ShutdownEventListener listener){
		this.listenerShtdn = listener;
	}
}