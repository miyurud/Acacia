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
import x10.util.Map;
import x10.util.ArrayList;
import x10.lang.Iterator;
import x10.util.StringBuilder;
import x10.interop.Java;

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
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Process;
import java.lang.Runtime;

import com.google.common.base.Splitter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.FileUtils;

import org.acacia.localstore.AcaciaHashMapLocalStore;
import org.acacia.localstore.AcaciaHashMapNativeStore;
import org.acacia.localstore.AcaciaLocalStore;
import org.acacia.localstore.AcaciaLocalStoreFactory;
import org.acacia.localstore.AcaciaLocalStoreTypes;
import org.acacia.log.Logger;
import org.acacia.util.Utils;
import org.acacia.util.java.Utils_Java;
import org.acacia.centralstore.AcaciaHashMapCentralStore;
import org.acacia.events.java.ShutdownEvent;
import org.acacia.events.java.ShutdownEventListener;
import org.acacia.events.java.DBTruncateEvent;
import org.acacia.events.java.DBTruncateEventListener;
import org.acacia.query.algorithms.triangles.Triangles;
import org.acacia.query.algorithms.pagerank.ApproxiRank;
import org.acacia.rdf.sparql.ExecuteQuery;
import org.acacia.rdf.sparql.ResultsCache;
import org.acacia.rdf.sparql.InterimResult;
import org.acacia.query.algorithms.kcore.KCore;
import org.acacia.server.java.AcaciaInstanceProtocol;

/**
 * Note that one AcaciaInstanceServiceSession will be run by only one place.
 * 
 * @author miyuru
 * 
 */
public class AcaciaInstanceServiceSession extends java.lang.Thread{	
    private var sessionSkt:Socket;
    //private GraphDatabaseService graphDB;//This is a reference to the original DB
    private var listener:DBTruncateEventListener;
    private var listenerShtdn:ShutdownEventListener;
    //private HashMap<Integer, GraphDatabaseService> graphDBMap = null;
    //Note : Feb 4 2015 - Since we need to deal with the <praphID>_<partitionID> scenario,
    //the key of the graph db map was changed to String
    private var graphDBMap:HashMap[String, AcaciaLocalStore] = null;
    private var loadedGraphs:ArrayList[String];
    private var defaultGraph:AcaciaLocalStore = null;
    private var defaultGraphID:String = null;
    private var dataFolder:String;
    private var serverHostName:String;
    private var sparqlQueryCache:ResultsCache = null;
    private var nonCached:Boolean = false;
    private var execute_query:ExecuteQuery = null;
    
	public def this(){
	}
	
	/**
	 * The constructor
	 * @param socket
	 */
	public def this(val socket:Socket, val db:HashMap[String, AcaciaLocalStore], val grp:ArrayList[String], val cache:ResultsCache, val cached:Boolean){
		sessionSkt = socket;
		graphDBMap = db;
		loadedGraphs = grp;
		dataFolder = Utils.getAcaciaProperty("org.acacia.server.instance.datafolder");
        sparqlQueryCache = cache;
        nonCached = cached;
	}
	
	public def setGraphDBMap(val db:HashMap[String, AcaciaLocalStore], val grp:ArrayList[String]) : void{
		graphDBMap = db;
		loadedGraphs = grp;
		dataFolder = Utils.getAcaciaProperty("org.acacia.server.instance.datafolder");
	}
	
	public def run() : void {
		//Logger.info("Running a new AcaciaInstanceServiceSession.");
		//First we need to check if the directory exist. If not we need to create it.
		val dir:File = new File("/tmp/dgr");
		
		if(!dir.exists()){
			Logger.info("Info : Creating /tmp/dgr directory");
			val result:Boolean = dir.mkdir();
			
			if(result){
				Logger.info("Directory /tmp/dgr created.");
			}
		}
		
		try{
			val inpStrm:InputStream = sessionSkt.getInputStream();
			val buff:BufferedReader = new BufferedReader(new InputStreamReader(inpStrm));
			val out:PrintWriter = new PrintWriter(sessionSkt.getOutputStream());
			var msg:String = "";
			
			while((msg = buff.readLine())!= null){
				Logger.info("msg : " + msg);
				
				//+Miyuru (May 2014) - This seems rather long if else structure. Better have some other mechanism to
				//query parsing if possible.
				if(msg.equals(AcaciaInstanceProtocol.HANDSHAKE)){
					out.println(AcaciaInstanceProtocol.HANDSHAKE_OK);
					out.flush();
					//Here we get the host name of the main server. We need this information for future
					//operations.
					serverHostName = buff.readLine();
					//Logger.info("serverHostName : " + serverHostName);
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
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//From here onwards we should receive a collection of edges
					msg = buff.readLine();
					
					var graphID:String = msg;
					
					Logger.info("graph id is : " + msg);
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//This is the partition ID
					msg = buff.readLine();
					
					setDefaultGraph(graphID, msg);
					
					//Just return an acknowledgement.
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine();
					
					while(!msg.equals(AcaciaInstanceProtocol.INSERT_EDGES_COMPLETE)){
						Logger.info("Adding Edge : " + msg);
						val vertsArr:Rail[String] = msg.split(" ");//We expect the edge to be split by a space.
						try{
							insertEdge(Long.parse(graphID), Long.parse(vertsArr(0)), Long.parse(vertsArr(1)));
						}catch(val ex:NumberFormatException){
							//Just ignore. Expect the messages only in the for <long> <long> <long>
							Logger.error("Error : " + ex.getMessage());
						}
						msg = buff.readLine();
					}
					
					out.println(AcaciaInstanceProtocol.INSERT_EDGES_ACK);
					out.flush();
				}else if (msg.equals(AcaciaInstanceProtocol.TRUNCATE)){					
					fireDBTruncateEvent(new DBTruncateEvent("Truncating Acacia Instance"));
					out.println(AcaciaInstanceProtocol.TRUNCATE_ACK);
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.COUNT_VERTICES)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					val graphID:String = msg;
					Logger.info("graph id is : " + graphID);
					Logger.info("Counting vertices, graph id is : " + graphID);
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					
					Logger.info("partition id is : " + msg);
					
					msg = countVertices(graphID, msg);
					
					out.println(msg);
					out.flush();
					Logger.info("vcount is : " + msg);
				}else if(msg.equals(AcaciaInstanceProtocol.COUNT_EDGES)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					val graphID:String = msg;
					Logger.info("graph id is : " + graphID);
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					
					Logger.info("partition id is : " + msg);
					
					msg = countEdges(graphID, msg);
					out.println(msg);
					out.flush();
					Logger.info("ecount is : " + msg);
				}else if(msg.equals(AcaciaInstanceProtocol.DELETE)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					val graphID:String = buff.readLine().trim();
					
					Logger.info("graph id is : " + graphID);
					
					//Now we want to get the exact partition id that we will be deleting.
					//Because there will be other places running in this node that will handle other
					//partitions corresponding to the same graph.
					out.println(AcaciaInstanceProtocol.SEND_PARTITION_ID);
					out.flush();
					
					msg = buff.readLine().trim();
					//msg = ""+deleteAllverticesandEdgesofGraph(msg);
					
					Logger.info("partition id is : " + msg);
					
					//we send the graphid and partitionid
					msg = ""+deleteGraph(graphID, msg);
					//We return the number of vertices deleted.
					out.println(msg);
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.LOAD_GRAPH)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					
					Logger.info("graph id is : " + msg);
					//int gid = Integer.parseInt(msg);
					val graphID:String = msg;
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim();
					val gid:String = graphID + "_" + msg;
					
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
					val graphID:String = msg;
					Logger.info("graph id is : " + graphID);
					
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
					val graphID:String = msg;
					
					Logger.info("graph id is : " + graphID);
					
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
					
					val graphID:String = msg;
										
					out.println(AcaciaInstanceProtocol.SEND_FILE_NAME);
					out.flush();
					
					msg = buff.readLine().trim();
					var fileName:String = msg;
					
					out.println(AcaciaInstanceProtocol.SEND_FILE_LEN);
					out.flush();
					
					msg = buff.readLine().trim();
					val fileLen:Long = Long.parseLong(msg);
					
					out.println(AcaciaInstanceProtocol.SEND_FILE_CONT);
					out.flush();
					
					//Here we need to get the file size and then check if the file size has been achieved.
					//Mere file exitance is not enough to continue, because we might have another thread writing data to the file. 
					
					val fullFilePath:String = "/tmp/dgr/" + fileName;
					val f:File = new File(fullFilePath);
					
					while((!f.exists()) && (f.length() < fileLen)){
						msg = buff.readLine().trim();
						
						Logger.info("curlen : " + f.length() + " fileLen : " + fileLen);
						
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
					
					Logger.info("Got the file : " + fileName);
					
					fileName = fileName.substring(fileName.indexOf("_") + 1n, fileName.indexOf("."));
					Logger.info("Partition ID : " + fileName);
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
				}else if(msg.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_REPLICATION)){
out.println(AcaciaInstanceProtocol.OK);
out.flush();

msg = buff.readLine().trim();

val placeID:String = msg;

out.println(AcaciaInstanceProtocol.SEND_GID);
out.flush();

msg = buff.readLine().trim();
val graphID:String = msg;

out.println(AcaciaInstanceProtocol.SEND_FILE_NAME);
out.flush();

msg = buff.readLine().trim();
var fileName:String = msg;

out.println(AcaciaInstanceProtocol.SEND_FILE_LEN);
out.flush();

msg = buff.readLine().trim();
val fileLen:Long = Long.parseLong(msg);

out.println(AcaciaInstanceProtocol.SEND_FILE_CONT);
out.flush();

//Here we need to get the file size and then check if the file size has been achieved.
//Mere file exitance is not enough to continue, because we might have another thread writing data to the file. 

val fullFilePath:String = "/tmp/dgr/" + fileName;
val f:File = new File(fullFilePath);

while((!f.exists()) && (f.length() < fileLen)){
msg = buff.readLine().trim();

Logger.info("curlen : " + f.length() + " fileLen : " + fileLen);

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

Logger.info("Got the file : " + fileName);

fileName = fileName.substring(fileName.indexOf("_") + 1n, fileName.indexOf("."));
Logger.info("Partition ID : " + fileName);
//here is where local store is constructed
//the fileName contains the graph partitionID. So we do not have to specifiy that again here.
unzipAndBatchUploadReplication(graphID, fileName,placeID);

while(!isUploadCompleted(fullFilePath)){
msg = buff.readLine().trim();

if(msg.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_CHK)){
out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_WAIT);
out.flush();
}
}

out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_ACK);//This is the sign of upload completeion.
out.flush();
}else if(msg.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_REPLICATION_CENTRAL)){
out.println(AcaciaInstanceProtocol.OK);
out.flush();

msg = buff.readLine().trim();

val placeID:String = msg;

out.println(AcaciaInstanceProtocol.SEND_GID);
out.flush();

msg = buff.readLine().trim();
val graphID:String = msg;

out.println(AcaciaInstanceProtocol.SEND_FILE_NAME);
out.flush();

msg = buff.readLine().trim();
var fileName:String = msg;

out.println(AcaciaInstanceProtocol.SEND_FILE_LEN);
out.flush();

msg = buff.readLine().trim();
val fileLen:Long = Long.parseLong(msg);

out.println(AcaciaInstanceProtocol.SEND_FILE_CONT);
out.flush();

//Here we need to get the file size and then check if the file size has been achieved.
//Mere file exitance is not enough to continue, because we might have another thread writing data to the file. 

val fullFilePath:String = "/tmp/dgr/" + fileName;
val f:File = new File(fullFilePath);

while((!f.exists()) && (f.length() < fileLen)){
msg = buff.readLine().trim();

Logger.info("curlen : " + f.length() + " fileLen : " + fileLen);

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

Logger.info("Got the file : " + fileName);

fileName = fileName.substring(fileName.indexOf("_") + 1n, fileName.lastIndexOf("_"));
Logger.info("Partition ID : " + fileName);
//here is where local store is constructed
//the fileName contains the graph partitionID. So we do not have to specifiy that again here.
unzipAndBatchUploadReplicationCentral(graphID, fileName,placeID);

while(!isUploadCompleted(fullFilePath)){
Console.OUT.println("uploading");
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
					
					val graphID:String = msg;
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
					
					val graphID:String = msg;
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the graph ID
					msg = buff.readLine().trim();
					//msg = outDegreeDistribution(msg);
					//out.println("result-from-" + msg);
					
					val partitionID:String = msg;
					
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
					
					val graphID:String = msg;
					
					//Next, we get the partitionID on which we are operating
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the graph ID
					msg = buff.readLine().trim();
					
					
					val partitionID:String = msg;
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					val hostList:String = buff.readLine().trim(); //Here we get the list of hosts involved in the Top-K PageRank calculation
					
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					msg = buff.readLine().trim(); //Here we get the K value of Top-K PageRank
					
					msg = pageRankTopKLocal(graphID, partitionID, hostList, Int.parseInt(msg));
					out.println(msg);
					out.flush();
				}else if (msg.equals(AcaciaInstanceProtocol.TRIANGLES)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the graph ID
					msg = buff.readLine().trim();
					
					val graphID:String = msg;
					
					//Once we get the graph ID we need to figureout on which partition are we going to operate on
					//We get this information from the master
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					
					//Here we will receve the graph ID
					msg = buff.readLine().trim();
					val partitionID:String = msg;
					msg = countTrainglesLocal(graphID, partitionID);
					out.println(msg);
					out.flush();
					
					/*---- must implement the world only triangle counting ---*/
					
					/*--------------------------------------------------------*/
					
				}else if(msg.equals(AcaciaInstanceProtocol.STATUS)){
					out.println(getInstanceStatus());
					out.flush();
				}else if(msg.equals(AcaciaInstanceProtocol.NPLACES)){
                    out.println(Place.places().size);
                    out.flush();
                } else if(msg.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_CENTRAL)){
					out.println(AcaciaInstanceProtocol.OK);
					out.flush();
					msg = buff.readLine().trim();
					Console.OUT.println(msg);
					val graphID:String = msg;

					out.println(AcaciaInstanceProtocol.SEND_FILE_NAME);
					out.flush();
					msg = buff.readLine().trim();
					var fileName:String = msg;
					Console.OUT.println(msg);

					out.println(AcaciaInstanceProtocol.SEND_FILE_LEN);
					out.flush();

					msg = buff.readLine().trim();
					val fileLen:Long = Long.parseLong(msg);
					Console.OUT.println(msg);

					out.println(AcaciaInstanceProtocol.SEND_FILE_CONT);
					out.flush();

					// Here we need to get the file size and then check if the
					// file size has been achieved.
					// Mere file exitance is not enough to continue, because we
					// might have another thread writing data to the file.

					val fullFilePath:String = "/tmp/dgr/" + fileName;
					val f:File = new File(fullFilePath);

					while ((!f.exists()) && (f.length() < fileLen)) {
						msg = buff.readLine().trim();

						Logger.info("curlen : " + f.length()
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

					Logger.info("Got the file--> : " + fileName);

					fileName = fileName.substring(fileName.indexOf("_") + 1n, fileName.lastIndexOf("_"));
					Logger.info("Partition ID : " + fileName);
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
					Console.OUT.println("RDF msg recieved"+msg);
					val graphID:String = msg;
				}else if(msg.equals(AcaciaInstanceProtocol.EXECUTE_QUERY)){
					out.println(AcaciaInstanceProtocol.SEND_QUERY);
					out.flush();
					msg = buff.readLine().trim();
					val query:String=msg;
					
					out.println(AcaciaInstanceProtocol.SEND_GID);
					out.flush();
					msg = buff.readLine().trim();
					val gID:String=msg;
					
					out.println(AcaciaInstanceProtocol.SEND_PARTITION_ID);
					out.flush();
					msg = buff.readLine().trim();
					val pID:String=msg;

					out.println(AcaciaInstanceProtocol.SEND_PLACEID);
					out.flush();
					msg = buff.readLine().trim();
					val placeID:String=msg;
					
					out.println(AcaciaInstanceProtocol.SEND_PLACEDETAILS);
					out.flush();
					msg = buff.readLine().trim();
					val placeDetails:String=msg;
					
                    val gIDi = Int.parse(gID);
                    val pIDi = Int.parse(pID);
                    val placeIDi = Int.parse(placeID);

                    //var result:ArrayList[String] = sparqlQueryCache.getFromCache(query,gIDi,pIDi,placeIDi);
                    var result:ArrayList[InterimResult] = null;
                    
                    //if((result == null) || !nonCached){
                    	//locally get the answers
                    	execute_query=new ExecuteQuery();  
                    	//result = execute_query.executeQuery(query,gID,pID,placeID);
                        result = execute_query.executeWithoutMerge(query,gID,pID,placeID);
                    
                        //Jan 24, 2016: For the moment we disable the caching feature
                        //sparqlQueryCache.putToCache(query,gIDi,pIDi,placeIDi, result);
                    //}
					
					//globally get the answers
					//Console.OUT.println(placeDetails);
					//loadDistributedCentralStoreData(gID,pID,placeID,placeDetails);
					
					if((result != null) && (!result.isEmpty())){
						out.println("Not empty");
						out.flush();
						//TODO:serialize
 						msg = buff.readLine().trim();
 						if(msg.equals("Send")){
 							val oos = new ObjectOutputStream(sessionSkt.getOutputStream());
 							oos.writeObject(result);
 						}


 						/*msg = buff.readLine().trim();
 						if(msg.equals("Send")){
 							out.print(result);
 							out.flush();
 						}*/
						
						out.println("Finish");
						out.flush();
					}
					else{
						out.println("Empty");
						out.flush();
					}
				 }else if(msg.equals(AcaciaInstanceProtocol.EXECUTE_QUERY_WITH_REPLICATION)){
				 out.println(AcaciaInstanceProtocol.SEND_QUERY);
				 out.flush();
				 msg = buff.readLine().trim();
				 val query:String=msg;
				 
				 out.println(AcaciaInstanceProtocol.SEND_GID);
				 out.flush();
				 msg = buff.readLine().trim();
				 val gID:String=msg;
				 
				 out.println(AcaciaInstanceProtocol.SEND_PARTITION_ID);
				 out.flush();
				 msg = buff.readLine().trim();
				 val pID:String=msg;

				 out.println(AcaciaInstanceProtocol.SEND_PLACEID);
				 out.flush();
				 msg = buff.readLine().trim();
				 val placeID:String=msg;
				 
				 out.println(AcaciaInstanceProtocol.REPLICATING_ID);
				 out.flush();
				 msg = buff.readLine().trim();
				 val replicatingID:String=msg;
				 
				 out.println(AcaciaInstanceProtocol.SEND_PLACEDETAILS);
				 out.flush();
				 msg = buff.readLine().trim();
				 val placeDetails:String=msg;
				 
				 val gIDi = Int.parse(gID);
				 val pIDi = Int.parse(pID);
				 val placeIDi = Int.parse(placeID);

				 //var result:ArrayList[String] = sparqlQueryCache.getFromCache(query,gIDi,pIDi,placeIDi);
				 var result:ArrayList[InterimResult] = null;
				 
				 //if((result == null) || !nonCached){
				 //locally get the answers
				 execute_query=new ExecuteQuery();  
				 //result = execute_query.executeQuery(query,gID,pID,placeID);
				 result = execute_query.executeWithoutMerge(query,gID,pID,placeID,replicatingID);
				 
				 //Jan 24, 2016: For the moment we disable the caching feature
				 //sparqlQueryCache.putToCache(query,gIDi,pIDi,placeIDi, result);
				 //}
				 
				 //globally get the answers
				 //Console.OUT.println(placeDetails);
				 //loadDistributedCentralStoreData(gID,pID,placeID,placeDetails);
				 
				 if((result != null) && (!result.isEmpty())){
				 out.println("Not empty");
				 out.flush();
				 //TODO:serialize
				 msg = buff.readLine().trim();
				 if(msg.equals("Send")){
				 val oos = new ObjectOutputStream(sessionSkt.getOutputStream());
				 oos.writeObject(result);
				 }


				 /*msg = buff.readLine().trim();
				  * if(msg.equals("Send")){
				  * out.print(result);
				  * out.flush();
				  * }*/
				 
				 out.println("Finish");
				 out.flush();
				 }
				 else{
				 out.println("Empty");
				 out.flush();
				 }
				 }else if(msg.equals(AcaciaInstanceProtocol.RUN_KCORE)){
					out.println(AcaciaInstanceProtocol.SEND_KVALUE);
					out.flush();
					msg = buff.readLine().trim();
					val kValue:String = msg;
					
					out.println(AcaciaInstanceProtocol.SEND_GID);
					out.flush();
					msg = buff.readLine().trim();
					val gID:String = msg;
					
					out.println(AcaciaInstanceProtocol.SEND_PARTITION_ID);
					out.flush();
					msg = buff.readLine().trim();
					val pID:String = msg;

					out.println(AcaciaInstanceProtocol.SEND_PLACEID);
					out.flush();
					msg = buff.readLine().trim();
					val placeID:String = msg;
					
					out.println(AcaciaInstanceProtocol.SEND_PLACEDETAILS);
					out.flush();
					msg = buff.readLine().trim();
					val placeDetails:String = msg;
					
 					val kCore:KCore = new KCore();  
					val result:ArrayList[Long] = kCore.getVertexIdsResults(kValue,gID,pID,placeID);				
					
 					if((result != null) && (!result.isEmpty())){
 						out.println("Not empty");
 						out.flush();

 						for(var i:Int=0n;i<result.size();i++){

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
				}
				else{
					Console.OUT.println("************************|" + msg + "|");
				 }
			}
		}catch(val e:IOException){
			Logger.error("Error : " + e.getMessage());
		}
	}

	private def loadDistributedCentralStoreData(val gID:String, val pID:String, val placeId:String, val placeDetails:String): void {	
		val baseDir:String = "/var/tmp/acad-localstore";//Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
		val places:Rail[String] = placeDetails.split(",");
		
        try{
			for(var i:Int=0n;i<places.size;i++){
				
				//placeid +host+prot
				val details:Rail[String]=places(i).split("/");
				
				if(details(0).equals(placeId)){
					continue;
				}else{				
					val socket:Socket = new Socket(details(1), Int.parseInt(details(2)));
					val out:PrintWriter = new PrintWriter(socket.getOutputStream());
					val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					var response:String = "";		
					
					out.println(AcaciaInstanceProtocol.HANDSHAKE);
					out.flush();
					response = reader.readLine();
					
					Console.OUT.println("resp1:" + response);
					
					if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
						Console.OUT.println("ccc");
						out.println(java.net.InetAddress.getLocalHost().getHostName());
						out.flush();
					
					//AcaciaHashMapNativeStore centralStore = new AcaciaHashMapNativeStore(Integer.parseInt(gID), Integer.parseInt(pID),baseDir,true,Integer.parseInt(placeId));
					//centralStore.loadGraph();
					
					}
				}
			}
        }catch(val ex:IOException){
            Logger.error("Error : " + ex.getMessage());
        }
	}


	private def countTrainglesLocal(val g:String, val p:String) : String {
		var result:String = null;
		var gid:String = g + "_" + p;
		//First we need to load the graph database server instance.
		var graphDB:AcaciaLocalStore = null;

		if (defaultGraph == null){	
			graphDB = graphDBMap.get(gid);
			if(graphDB == null){
				//We see whether the graph is offline
				if(isGraphDBExists(g, p)){
					loadLocalStore(g, p);
					//Console.OUT.println("+++++++++++++++++++++++++++++>");
				}
				
				graphDB = graphDBMap.get(gid);
				if(graphDB == null){		
					result = "-1";
					Console.OUT.println("==++-->The graph db is null");
					return result;
				}
			}
		}else{
			graphDB = defaultGraph;
		}
		//Console.OUT.println("++==++-->now running traingles");
        //+Miyuru: Temp comment: This temporary comment must be removed and Traingles class needs to be recompiled to get Triangle counting work.
        //until then this method will return a null value
		result = Triangles.run(graphDB, g, p, Utils_Java.getServerHost());
		Console.OUT.println("++==++-->result at (" + here.id + ") : " + result);
		return result;
	}

	private def pageRankTopKLocal(val graphID:String, val partitionID:String, val hostList:String, val k:Int) : String {
		var result:String = null;
		var graphDB:AcaciaLocalStore = null;
		var gid:String = graphID + "_" + partitionID;
		Logger.info("PPPPP1");

		if (defaultGraph == null){	
			Console.OUT.println("PPPPP2");
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
			Console.OUT.println("PPPPP3");
		}else{
			Console.OUT.println("PPPPP4");
			graphDB = defaultGraph;
		}
		//entireGraphSize = Integer.parseInt(countVertices(graphID));
		Console.OUT.println("Started Approx Rank");
        //+Miyuru : temporary comment
		//result = ApproxiRank.run(graphID, partitionID, graphDB, hostList, serverHostName, k);
		Console.OUT.println("Done Approx Rank");
		return result;
	}

	private def getInstanceStatus() : String {
        var dataFolder:String = Utils.getAcaciaProperty("org.acacia.server.instance.datafolder");
        var line:String = null;
        var strArr:Rail[String] = null;
        var instanceCapacity:String = null;
        var thisHost:String = null;
        
        try{
        	thisHost = java.net.InetAddress.getLocalHost().getHostName();
	        var reader:BufferedReader = new BufferedReader(new FileReader("conf/localstorage.txt"));
	
	        while((line = reader.readLine()) != null){
	        	//Here we just read first line and then break. But this may not be the best option. Better iterate through all the
	        	//lines and accumulate those to a HashMap in future.
	        	strArr = line.split(":");
	        	if(thisHost.equals(strArr(0))){
	        		break;
	        	}
	        }
	        reader.close();
        }catch(val e:UnknownHostException){
			Logger.error("Connecting to localhost got error message (11) : " + e.getMessage());
		}catch(val ec:IOException){
        	ec.printStackTrace();
        }
        
        instanceCapacity = strArr(1);
        
        //First we need to check whether the Acacia's data directory exists or not.
        var fileChk:File = new File(dataFolder);
        
        if(fileChk.exists() && fileChk.isDirectory()){
        	//There is no problem
        }else{
        	//Here we need to create the directory first
        	try{
        		FileUtils.forceMkdir(fileChk);
        	}catch(val ex:IOException){
        		Logger.error("Creating the Acacia data folder threw an Exception : " + ex.getMessage());
        	}
        }
        
        val size:Double = Math.round(FileUtils.sizeOfDirectory(new File(dataFolder)) * 1.0d/(1024*1024)); // Divide by 1024*1024 to convert to MB
        val d:Double = size/Double.parseDouble(instanceCapacity);
        val f:java.text.DecimalFormat = new java.text.DecimalFormat();
        f.setMaximumFractionDigits(2n);
        
        //The status message is of the format <Size of the local storage usage>:<Max local storage size>:<percentage of local storage used>
        instanceCapacity = size + ":" + instanceCapacity + ":" + f.format(d);
        
        Console.OUT.println("status : " + instanceCapacity);
        
		return instanceCapacity;
	}

	/**
	 * Batch upload process will be completed by deleting the transfered file. Therefore we check the status of the file and report the status.
	 * @param filePath
	 * @return
	 */
	private def isUploadCompleted(val filePath:String):Boolean{
		var result:Boolean = false;
		val f:File = new File(filePath);
		
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
	private def isGraphDBExists(val graphID:String, val partitionID:String):Boolean{
		val f:File = new File(dataFolder + "/" + graphID + "_" + partitionID);
		
		return f.isDirectory();
	}
	
	private def isGraphDBLoaded(val graphID:String, val graphPartitionID:String):Boolean{	
		return graphDBMap.containsKey(graphID + "_" + graphPartitionID);
	}
	
	public def unzipAndBatchUpload(val graphID:String, val partitionID:String) : void {
		//AcaciaHashMapLocalStore localStore = new AcaciaHashMapLocalStore(Integer.parseInt(graphID), Integer.parseInt(partitionID));
//		AcaciaHashMapLocalStore localStore = (AcaciaHashMapLocalStore)AcaciaLocalStoreFactory.create(Integer.parseInt(graphID), Integer.parseInt(partitionID), Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder"), false, AcaciaLocalStoreTypes.HASH_MAP_LOCAL_STORE);
//		localStore.loadGraph();
		
				try{
					//Unzipping starts here
					var r:Runtime = Runtime.getRuntime();
					
					//Next, we unzip the file
					var p:Process = r.exec("unzip /tmp/dgr/" + graphID + "_" +  partitionID + ".zip -d " + Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator  + graphID + "_" + partitionID);
					p.waitFor();
					
					val b:BufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					var line:String = "";
					
					while((line=b.readLine())!= null){
						Console.OUT.println(line);
					}
					
					p = r.exec("rm /tmp/dgr/" + graphID + "_" + partitionID + ".zip");
					p.waitFor();
					
					//Console.OUT.println("Completed unzipping");
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
					
				}catch(val e1:java.io.IOException){
                    Logger.error("Error : " + e1.getMessage());
				}catch(val e2:java.lang.InterruptedException){
                    Logger.error("Error : " + e2.getMessage());
	            }catch(val e:Exception){
                    Logger.error("Error : " + e.getMessage());
				}
	}

		public def unzipAndBatchUploadReplication(val graphID:String, val partitionID:String,val placeID:String) : void {
				
				try{
					//Unzipping starts here
					var r:Runtime = Runtime.getRuntime();
					
					//Next, we unzip the file
                                        val f:File = new File(Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator+"replication"+File.separator+placeID+File.separator);
                                        if(!f.exists()){
                                            f.mkdirs();
                                        }
					var p:Process = r.exec("unzip /tmp/dgr/" + graphID + "_" +  partitionID + ".zip -d " + Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator +"replication"+File.separator +placeID+File.separator + graphID + "_" + partitionID);
					p.waitFor();
					

					
					val b:BufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					var line:String = "";
					
					while((line=b.readLine())!= null){
						Console.OUT.println(line);
					}

					Console.OUT.println("Check 3| replication");
					
					Console.OUT.println("Deleting|" + "rm /tmp/dgr/" + graphID + "_" + partitionID + ".zip|");
					p = r.exec("rm /tmp/dgr/" + graphID + "_" + partitionID + ".zip");		
					p.waitFor();
					
					//Also we need to add a catalog record in the instance's local data store about the graph and its partition IDs
					writeCatalogRecord("" + graphID + ":" + partitionID);
					
				}catch(val e1:java.io.IOException){
                                    Logger.error("Error : " + e1.getMessage());
				}catch(val e2:java.lang.InterruptedException){
                                    Logger.error("Error : " + e2.getMessage());
	            }catch(val e:Exception){
                        Logger.error("Error : " + e.getMessage());
		    }
	}

public def unzipAndBatchUploadReplicationCentral(val graphID:String, val partitionID:String,val placeID:String) : void {

try{
//Unzipping starts here
var r:Runtime = Runtime.getRuntime();

//Next, we unzip the file
val f:File = new File(Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator+"replication"+File.separator+placeID+File.separator + graphID + "_centralstore");
if(!f.exists()){
f.mkdirs();
}
var p:Process = r.exec("unzip /tmp/dgr/" + graphID + "_" +  partitionID + "_trf.zip -d " + Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator +"replication"+File.separator +placeID+File.separator + graphID + "_centralstore" +File.separator + graphID + "_" + partitionID );
p.waitFor();



val b:BufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
var line:String = "";

while((line=b.readLine())!= null){
Console.OUT.println("Central store graph sending " + graphID + "_" + partitionID);
}
Console.OUT.println("Check 3| replication");

Console.OUT.println("Deleting|" + "rm /tmp/dgr/" + graphID + "_" + partitionID + "_trf.zip|");
p = r.exec("rm /tmp/dgr/" + graphID + "_" + partitionID + "_trf.zip");		
p.waitFor();



}catch(val e1:java.io.IOException){
Logger.error("Error : " + e1.getMessage());
}catch(val e2:java.lang.InterruptedException){
Logger.error("Error : " + e2.getMessage());
}catch(val e:Exception){
Logger.error("Error : " + e.getMessage());
}
}
	
	private def unzipAndBatchUploadCentralStore(val graphID:String, val partitionID:String) : void{
		try {
            val f:File = new File(Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder"));

            if(!f.isDirectory()){
               f.mkdir();
            }

			val f1:File = new File(Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator + graphID + "_centralstore");
			
			if(!f1.isDirectory()){
				f1.mkdir();
			}

			val r:Runtime = Runtime.getRuntime();
			var p:Process = r.exec("unzip /tmp/dgr/" + graphID + "_" + partitionID + "_trf.zip -d " + Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator + graphID + "_centralstore/" + graphID + "_" + partitionID);
			p.waitFor();

			val b:BufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			var line:String = "";

			while((line=b.readLine())!= null){
				Console.OUT.println("Central store graph sending " + graphID + "_" + partitionID);
			}
			
			Console.OUT.println("Deleting676767");
			Console.OUT.println("Deleting|" + "rm /tmp/dgr/" + graphID + "_" + partitionID + "_trf.zip|");
			p = r.exec("rm /tmp/dgr/" + graphID + "_" + partitionID + "_trf.zip");
			p.waitFor();
			
			Console.OUT.println("Completed unzipping");		
			
		}catch(val e1:java.io.IOException){
            Logger.info("Error : " + e1.getMessage());
		}catch(val e2:java.lang.InterruptedException){
            Logger.info("Error : " + e2.getMessage());
	    }catch (val e:Exception) {
			Logger.info("Error : " + e.getMessage());
		}
	}

	private def writeCatalogRecord(val record:String) : void{
        //First we need to check whether the Acacia's data directory exists or not.
        val fileChk:File = new File(dataFolder);
        
        if(fileChk.exists() && fileChk.isDirectory()){
        	//There is no problem
        }else{
        	//Here we need to create the directory first
        	try{
        		org.apache.commons.io.FileUtils.forceMkdir(fileChk);
        	}catch(val ex:IOException){
        		Logger.error("Creating the Acacia data folder threw an Exception : " + ex.getMessage());
        	}
        }				
		
		val catalog:File = new File(dataFolder + "/catalog");
		var b:Boolean = false;

		try{
			if(!catalog.exists()){
				b = catalog.createNewFile();
			}
			
			if(b){
				Console.OUT.println("The catalog file was newly created.");
			}
		
			val writer:BufferedWriter = new BufferedWriter(new FileWriter(catalog, true));//We are appending to the file rather than replacing
			writer.write(record);
			writer.write("\n");//We need a new line to separate between two records.
			writer.flush();
			writer.close();
		}catch(val e:IOException){
			Console.OUT.println("There is an error is writing to the AcaciaInstance's catalog.");
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
	private def deleteGraph(val graphID:String, val partitionID:String) : String {
		var result:String = "0";
		
		//We need to first turn-off the graph DB instance
		if (graphDBMap.containsKey(graphID)){
			Logger.info("Unloading the graph - " + graphID + ":" + partitionID);
			unloadLocalStore(graphID, partitionID);
		}else{
			Logger.info("The following graph was not loaded to the system - " + graphID + ":" + partitionID);
		}
		
		//Next we need to delete the file content from the acacia instance directory.
		try{
			Logger.info("Deleting the folder: " + dataFolder + "/" + graphID + "_" + partitionID);
			FileUtils.deleteDirectory(new File(dataFolder + "/" + graphID + "_" + partitionID));
			Logger.info("Deleting the folder: " + dataFolder + "/" + graphID + "_centralstore");
			FileUtils.deleteDirectory(new File(dataFolder + "/" + graphID + "_centralstore"));
			Logger.info("Done deleting.");
		}catch(var ec:IOException){
			Logger.error("Error in deleting the file : " + ec.getMessage());
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
				Logger.info("Cannot delete file : " + file.getName());
				result = "-1";
			}
		}
		*/
		
		//Also we need to update the local catalog after deleting the graph partition.
		//TODO in future : There will be a contention issue when multiple threads try to write to the same file.
		
		
		return result;
	}

	public def setDefaultGraph(val graphID:String, val partitionID:String):void{
		val gid:String = graphID + "_" + partitionID;
		defaultGraph = graphDBMap.get(gid);
                if(defaultGraph == null){
                    Console.OUT.println("Default graph is null");
                }else{
			defaultGraphID = gid;
			Console.OUT.println("The default graph is set to : " + gid);
                }
	}
	
	public def unSetDefaultGraph(val graphID:String, val partitionID:String):void{
		defaultGraph = null;
		defaultGraphID = null;
	}
	
	public def loadLocalStore(val graphID:String, val partitionID:String):void{
		val gid:String = graphID + "_" + partitionID;
		Console.OUT.println("gid:"+gid);
		//int graphID, int partitionID
		//AcaciaHashMapLocalStore graphDB = new AcaciaHashMapLocalStore(Integer.parseInt(graphID), Integer.parseInt(partitionID));
		val graphDB:AcaciaLocalStore = AcaciaLocalStoreFactory.load(Int.parseInt(graphID), Int.parseInt(partitionID), Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder"), false);
		graphDB.loadGraph();
		graphDBMap.put(gid, graphDB);
		loadedGraphs.add(gid);
		Console.OUT.println("getVertexCount():" + graphDB.getVertexCount());
//		try{
//			//Logger.info("Loaded the graph " + gid + " at " + java.net.InetAddress.getLocalHost().getHostName());
//		}catch(UnknownHostException ex){
//			Logger.error("Error : " + ex.getMessage());
//		}
		//Console.OUT.println("------------ Done Running from AAAAAAAAAAAAAAAAAAAAAAAAAAAA--------");
	}
	
	/**
	 * 28/10/2014
	 * We need not to worry about which partition we are dealing with when loading and unloading graphs.
	 * It is because we know each place is handling only one subgraph from each graph. So we can directly refer
	 * the data by graph id without use of partition id. 
	 * @param graphID
	 */
	public def unloadLocalStore(val graphID:String, val partitionID:String):void{
		val it:Iterator[Map.Entry[String, AcaciaLocalStore]] = graphDBMap.entries().iterator();
		var graphDBMap2:HashMap[String, AcaciaLocalStore] = new HashMap[String, AcaciaLocalStore]();
		var loadedGraphs2:ArrayList[String] = new ArrayList[String]();
		val gid:String = graphID + "_" + partitionID;
		
		while(it.hasNext()){
			val pairs:Map.Entry[String, AcaciaLocalStore] = it.next() as Map.Entry[String, AcaciaLocalStore];

            if (pairs.getKey().equals(gid)){
				(pairs.getValue() as AcaciaLocalStore).storeGraph();
				try{
					Logger.info("Unloaded the graph " + gid + " at " + java.net.InetAddress.getLocalHost().getHostName());
				}catch(val ex:UnknownHostException){
					Logger.error("Error : " + ex.getMessage());
				}
			}else{
				graphDBMap2.put(pairs.getKey(), pairs.getValue());
				loadedGraphs2.add(pairs.getKey());
			}
		}
		
		graphDBMap = graphDBMap2;
		loadedGraphs = loadedGraphs2; 
	}
	
	private def fireDBTruncateEvent(val evt:DBTruncateEvent):void{
		listener.truncateEventOccurred(evt);
	}
	
	private def fireShutdownEvent(val evt:ShutdownEvent):void{
		listenerShtdn.shutdownEventOccurred(evt);
	}
	
	/**
	 * This method inserts an edge to the graph DB
	 * @param startVertexID
	 * @param endVertexID
	 */
	public def insertEdge(val graphID:Long, val startVertexID:Long, val endVertexID:Long):void{		
		var graphDB:AcaciaLocalStore = null;
		
		if (defaultGraph == null){	
			graphDB = graphDBMap.get(""+ graphID) as AcaciaLocalStore;
			if(graphDB == null){
				Logger.error("Error : The graph database instance is NULL.");
			}
                        graphDB.addEdge(startVertexID, endVertexID);
		}else{
			graphDB = defaultGraph;
                        graphDB.addEdge(startVertexID, endVertexID);
		}
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
//			Console.OUT.println("1111============================");
//			Pattern p = Pattern.compile("\\d+(,[ ]\\d+)*");
//			Console.OUT.println("2222============================");
//			Console.OUT.println("resultStr : |" + resultStr+"|");
//			Matcher m = p.matcher(resultStr);
//			Console.OUT.println("wwwwww============================");
//			while(m.find()){
//				resultStr = m.group();
//				break;
//			}
//			Console.OUT.println("llllll============================");
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
	
	public def countVertices(val graphID:String, val partitionID:String):String{
		var result:String = "-1";	
		//var localStore:AcaciaHashMapLocalStore = new AcaciaHashMapLocalStore(Int.parseInt(graphID), Int.parseInt(partitionID));
        val baseDir:String = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");

        //native store
        val localStore:AcaciaHashMapNativeStore = new AcaciaHashMapNativeStore(Int.parse(graphID), Int.parse(partitionID),baseDir,false);
        
		localStore.loadGraph();
		result = "" + localStore.getVertexCount();
		
		//result = "" + (Long.parseLong(result) - 1); //By default there is avertex created by every Neo4j instance. Therefore we need to deduct one to compensate this.
		
		return result;
	}
	
	public def countEdges(val graphID:String, val partitionID:String):String{
		var result:String = "-1";		
        val baseDir:String = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");

        //native store
        val localStore:AcaciaHashMapNativeStore = new AcaciaHashMapNativeStore(Int.parse(graphID), Int.parse(partitionID),baseDir,false);
		localStore.loadGraph();
		result = "" + localStore.getEdgeCount();
		
		return result;
	}
	
	public def outDegreeDistribution(val graphID:String, val partitionID:String):String{
		//Logger.info("Out degree distribution calculation started at : " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
		val resultSB:StringBuilder = new StringBuilder();		
		var graphDB:AcaciaLocalStore = null;
		val gid:String = graphID + "_" + partitionID;
		
		if (defaultGraph == null){
			graphDB = graphDBMap.get(gid);
			if(graphDB == null){
				//We see whether the graph is offline
				if(isGraphDBExists(graphID, partitionID)){
					loadLocalStore(graphID, partitionID);
				}
				graphDB = graphDBMap.get(gid);
				if(graphDB == null){		
					resultSB.add("-1");
					return resultSB.toString();
				}
			}
		}else{
			graphDB = defaultGraph;
		}	
		
		var resMap:HashMap[Long, Long] = null;
		
//		//Next we get the out degree of each vertex
//		Console.OUT.println("ASDS111");
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
//		Console.OUT.println("ASDS222");
		
//        String dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
//        String line = null;
		
		//resMap = (graphDB as AcaciaHashMapLocalStore).getOutDegreeDistributionHashMap();
        resMap = (graphDB as AcaciaHashMapNativeStore).getOutDegreeDistributionHashMap();
		
        var partitionId:Int = -1n;
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
        Console.OUT.println("ASDS223");
        //partitionId = Utils_Java.getPartitionIDFromCatalog(Integer.parseInt(graphID));
        
        //Console.OUT.println("partition ID : " + partitionId);
        //Next we request and get all the local to world degree distribution. This will be a list of vertices of this
        //graph partition which are connected with the extrenal world, each vertex will have its associated out degree.
        
        //AcaciaInstanceToManagerAPI
        //String serverHost, int graphID, int partitionID
        var serverHostName:String = Utils.getServerHost();
		
		//Next, we need to resolve the out links that are from the vertices of this subgraph to the external world
        //+Miyuru : temporary comment
		val res:HashMap[String, String] = AcaciaInstanceToManagerAPI.getLambdaOutDegreeDistribution(serverHostName, graphID, partitionID);
        //val res:HashMap[String, String] = null;
        
        var vertex:Long = 0;
		var outDegree:Long = 0;
		var updatecount:Long = 0;
        var itr1:Iterator[Map.Entry[String, String]] = res.entries().iterator();
        
		while(itr1.hasNext()){
            var item:Map.Entry[String, String] = itr1.next();
			vertex = Int.parseInt(item.getKey());
			outDegree = Int.parseInt(item.getValue());
			
			if(resMap.containsKey(vertex)){
				outDegree += resMap.get(vertex) as Long;
				updatecount++;
			}
			
			resMap.put(vertex, outDegree);
		}
		
		Console.OUT.println("=========================> updated this many vertices : " + updatecount);
		var itr:Iterator[Map.Entry[Long, Long]] = resMap.entries().iterator();

		while(itr.hasNext()){
            var item:Map.Entry[Long, Long] = itr.next();
			resultSB.add("" + item.getKey() + "\t" + item.getValue()+";");
		}
		
		//org.acacia.log.Logger.info("Out degree distribution calculation completed at : " + org.acacia.util.Utils_Java.getCurrentTimeStamp());
		//return "2\t10;20\t2;40\t4";
		return resultSB.toString();
	}
	
	public def pageRankLocal(val graphID:String, val partitionID:String, val hostList:String):String{
		var result:String = null;
		var graphDB:AcaciaLocalStore = null;
		Console.OUT.println("PPPPP1");
		val gid:String = graphID + "_" + partitionID;
		
		if (defaultGraph == null){	
			Console.OUT.println("PPPPP2");
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
			Console.OUT.println("PPPPP3");
		}else{
			Console.OUT.println("PPPPP4");
			graphDB = defaultGraph;
		}
		//entireGraphSize = Integer.parseInt(countVertices(graphID));
		Console.OUT.println("Started Approx Rank");

        //+Miyuru : Oct 4 2015, just commenting out for the moment to facilitate the migration
		//result = ApproxiRank.run(graphID, partitionID, graphDB, hostList, serverHostName, -1n); //Here we send -1 meaning we want the entire pagerank vector, nit top-k pageranks.
		Console.OUT.println("Done Approx Rank");
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
	
	public def addDBTruncateEventListener(val listener:DBTruncateEventListener):void{
		this.listener = listener;
	}
	
	public def addShutdownEventListener(val listener:ShutdownEventListener):void{
		this.listenerShtdn = listener;
	}
}
