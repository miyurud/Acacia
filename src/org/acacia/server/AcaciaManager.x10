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

import x10.core.Thread;
import x10.util.HashMap;
import x10.util.HashSet;
import x10.util.StringBuilder;
import x10.util.ArrayList;

import java.rmi.UnknownHostException;
import java.net.Socket;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.ObjectInputStream;
import java.lang.InterruptedException;
import java.util.TreeSet;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

import org.acacia.rdf.sparql.InterimResult;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;
import org.acacia.util.Utils;
import org.acacia.log.java.Logger_Java;
import org.acacia.server.java.AcaciaInstanceProtocol;
import org.acacia.server.java.AcaciaBackEndProtocol;
import org.acacia.centralstore.AcaciaHashMapCentralStore;
import org.acacia.localstore.AcaciaLocalStore;
import org.acacia.localstore.AcaciaLocalStoreFactory;

public class AcaciaManager {

 	public static def run() throws java.net.UnknownHostException{

 	}
 
 	public static def sendFileThroughService(val host:String, val port:Int, val fileName:String, val filePath:String){
 
 		// var t:Thread = new Thread(){
  	// 		public def run(){
  				try{
                    Console.OUT.println("Connecting to : " + host + ":" + port);
	  				val socket:Socket = new Socket(host,port);
	  				val out:OutputStream = socket.getOutputStream();
	  				val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	  				val file:File = new File(filePath);
	   				val bin:BufferedInputStream = new BufferedInputStream(new FileInputStream(file));
	   				out.write(x10.interop.Java.convert(fileName.bytes()));
	   				out.flush();
	   				val response:String = reader.readLine();
	   				if (response.equals(AcaciaInstanceProtocol.SEND_FILE)){
	   					Console.OUT.println("Sending file");
	   					val buffer:Rail[Byte] = new  Rail[byte](8*1024);
	   					var c:Int = -1n;
	
	   					while((c=bin.read(x10.interop.Java.convert[Byte](buffer)))>0){
	   						out.write(x10.interop.Java.convert[Byte](buffer), 0n, c);
	   						out.flush();
	   					}
   					}
  				}catch(e1:java.net.UnknownHostException){
                    Console.OUT.println("Error Connectin to : " + host + ":" + port);
                    e1.printStackTrace();
  				}catch(e2:java.io.IOException){
                    Console.OUT.println("Error Connectin to : " + host + ":" + port);
                    e2.printStackTrace();
  				}
  	// 		}
 		// };
 		// t.start();
 	}
 
 public static def batchUploadReplication(host:String, port:Int, graphID:long, filePath:String, dataPort:Int, placeID:Int):boolean{
 var result:boolean = true;
 
 try{
 val socket:Socket  = new Socket(host, port);
 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 var response:String = "";
 
 //First we need to Handshake
 out.println(AcaciaInstanceProtocol.HANDSHAKE);
 out.flush();
 response = reader.readLine();
 
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
 out.println(java.net.InetAddress.getLocalHost().getHostName());
 out.flush();
 }
 
 out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_REPLICATION);
 out.flush();
 
 response = reader.readLine();

 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
 out.println(placeID);
 out.flush();
 response = reader.readLine();
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_GID))){				

 out.println(graphID);
 out.flush();
 response = reader.readLine();
 
 
 
 val fileName:String = filePath.substring(filePath.lastIndexOf("/")+1n);
 
 Console.OUT.println("filePath : " + filePath);
 Console.OUT.println("fileName : " + fileName);
 
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_NAME))){
 out.println(fileName);
 out.flush();
 
 response = reader.readLine();
 
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_LEN))){
 Console.OUT.println("File len : " + new File(filePath).length());
 out.println(new File(filePath).length());//File length in bytes
 out.flush();
 
 response = reader.readLine();
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_CONT))){
 sendFileThroughService(host, dataPort, fileName, filePath);
 }							
 }			
 }
 
 var counter:Int = 0n;
 
 while(true){
 out.println(AcaciaInstanceProtocol.FILE_RECV_CHK);
 out.flush();
 response = reader.readLine();

 if((response != null)&&(response.equals(AcaciaInstanceProtocol.FILE_RECV_WAIT))){
 Console.OUT.println("Checking file status : " + counter);
 counter++;
 
 Thread.currentThread().sleep(1000);
 //We sleep for 1 second, and try again.
 continue;
 }else{
 break;
 }
 }
 Console.OUT.println("File transfer complete...");
 
 //Next we wait till the batch upload completes
 while(true){
 out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_CHK);
 out.flush();
 
 response = reader.readLine();

 if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_WAIT))){
 Thread.currentThread().sleep(1000);
 //We sleep for 1 second, and try again.
 continue;
 }else{
 break;
 }
 }
 
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_ACK))){
 Console.OUT.println("Batch upload completed...");	
 }else{
 Console.OUT.println("There was an error in the upload parocess.");
 }
 }
 }
 }catch(e:java.net.UnknownHostException ){
 Logger_Java.error(e.getMessage());
 result = false;
 }catch(ec:java.io.IOException ){
 Logger_Java.error(ec.getMessage());
 result = false;
 }	
 
 return result;
 }
 
	 /**
	  * The following communication happens with the designated AcaciaInstance which is identified the host and the port.
	  * 
	  * @param host
	  * @param port
	  * @param graphID
	  * @param filePath
	  * @param dataPort
	  * @return
	  */
 	public static def batchUploadFile(host:String, port:Int, graphID:long, filePath:String, dataPort:Int):boolean{
 		var result:boolean = true;
 
  		try{
  			val socket:Socket  = new Socket(host, port);
  			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
  			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  			var response:String = "";
 
 			//First we need to Handshake
 			out.println(AcaciaInstanceProtocol.HANDSHAKE);
 			out.flush();
 			response = reader.readLine();
 			 
 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
  				out.println(java.net.InetAddress.getLocalHost().getHostName());
  				out.flush();
  			}
  
 		 	out.println(AcaciaInstanceProtocol.BATCH_UPLOAD);
 		 	out.flush();
 		 
		 	response = reader.readLine();

  			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
			 	out.println(graphID);
			 	out.flush();
			 	response = reader.readLine();
			 
			 	val fileName:String = filePath.substring(filePath.lastIndexOf("/")+1n);
			 
				Console.OUT.println("filePath : " + filePath);
			 	Console.OUT.println("fileName : " + fileName);
			 
 				if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_NAME))){
 					out.println(fileName);
 					out.flush();
 
 					response = reader.readLine();
 
 					if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_LEN))){
 						Console.OUT.println("File len : " + new File(filePath).length());
 						out.println(new File(filePath).length());//File length in bytes
 						out.flush();
 
 						response = reader.readLine();
 						if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_CONT))){
 							sendFileThroughService(host, dataPort, fileName, filePath);
 						}							
  					}			
  				}
 
 				var counter:Int = 0n;
 
 				while(true){
 					out.println(AcaciaInstanceProtocol.FILE_RECV_CHK);
 					out.flush();
 					response = reader.readLine();

 					if((response != null)&&(response.equals(AcaciaInstanceProtocol.FILE_RECV_WAIT))){
 						Console.OUT.println("Checking file status : " + counter);
 						counter++;
 						
 						Thread.currentThread().sleep(1000);
 						//We sleep for 1 second, and try again.
 						continue;
 					}else{
 						break;
 					}
 				}
  				Console.OUT.println("File transfer complete...");
 
 				//Next we wait till the batch upload completes
 				while(true){
 					out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_CHK);
 					out.flush();
 
 					response = reader.readLine();

  					if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_WAIT))){
	 					Thread.currentThread().sleep(1000);
 						//We sleep for 1 second, and try again.
	 					continue;
	 				}else{
	 					break;
	 				}
  				}
 
	 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_ACK))){
	 				Console.OUT.println("Batch upload completed...");	
	 			}else{
	 				Console.OUT.println("There was an error in the upload parocess.");
	 			}
	 		}
	 	}catch(e:java.net.UnknownHostException ){
	 		Logger_Java.error(e.getMessage());
	 		result = false;
	 	}catch(ec:java.io.IOException ){
	 		Logger_Java.error(ec.getMessage());
	 		result = false;
  		}	
 
 		return result;
 	}

 	public static def batchUploadCentralStore(host:String,port:Int,graphID:long,filePath:String,dataPort:int):boolean{
	 	var result:boolean = true;
	 
	 	Console.OUT.println(filePath);
	 
	 
	 	try{
	 		//System.out.println(">>>>> Connecting to host : " + host + " port : " + port);
	 		val socket:Socket = new Socket(host, port);
	 		val out:PrintWriter = new PrintWriter(socket.getOutputStream());
	 		val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	 		var response:String = "";
	
	 		//First we need to Handshake
	 		out.println(AcaciaInstanceProtocol.HANDSHAKE);
	 		out.flush();
	 		response = reader.readLine();
	 
	 		if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
	 			out.println(java.net.InetAddress.getLocalHost().getHostName());
	 			out.flush();
	  		}
	 
	 		out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_CENTRAL);
	 		out.flush();
	 
	 		response = reader.readLine();
	
	 		if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
	 			out.println(graphID);
	 			out.flush();
	 			response = reader.readLine();
	 			val fileName:String = filePath.substring(filePath.lastIndexOf("/")+1n);
	 
	 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_NAME))){
				 	out.println(fileName);
				 	out.flush();
				 
				 	response = reader.readLine();
	 
	 				if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_LEN))){
	 					//We need to have this type of construct to make sure we get the file created before we measure its size.
	 					Console.OUT.println("file path:" + filePath);
	 					val f:File = new File(filePath);
	 					var l:long = f.length();
	 
	 					while(l == 0){
	 						Console.OUT.println("====>File len : " + l);
	 						l = f.length();
	 
	 						try{
	 							Thread.currentThread().sleep(10);
	 						}catch(ex:Exception ){
	 							ex.printStackTrace();
	 						}
	 					}
	 
	 					out.println(l);//File length in bytes
	 					out.flush();
	 
	 					response = reader.readLine();
	 					if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_CONT))){
	 						sendFileThroughService(host, dataPort, fileName, filePath);
	 					}							
	 				}			
	 			}
 
	 			var counter:Int = 0n;
	 
	 			while(true){
	 				out.println(AcaciaInstanceProtocol.FILE_RECV_CHK);
	 				out.flush();
	 				response = reader.readLine();
	
	 				if((response != null)&&(response.equals(AcaciaInstanceProtocol.FILE_RECV_WAIT))){
	 					Console.OUT.println("Checking file status : " + counter);
	 					counter++;
	 					Thread.currentThread().sleep(1000);
	 					//We sleep for 1 second, and try again.
	 					continue;
	 				}else{
	 					break;
	 				}
	 			}
	 			Console.OUT.println("File transfer complete...");
	 
	 			//Next we wait till the batch upload completes
	 			while(true){
	 				out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_CHK);
	 				out.flush();
	 
	 				response = reader.readLine();
	
	 				if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_WAIT))){
	 					Thread.currentThread().sleep(1000);
	 					//We sleep for 1 second, and try again.
	 					continue;
	 				}else{
	 					break;
	 				}
	 			}
	 
	 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_ACK))){
	 				Console.OUT.println("Batch upload completed...");	
	 			}else{
	 				Console.OUT.println("There was an error in the upload parocess.");
	 			}
	 		}
	 	}catch(e:java.net.UnknownHostException){
	 		Logger_Java.error(e.getMessage());
	 		result = false;
	 	}catch(ec:java.io.IOException){
	 		Logger_Java.error(ec.getMessage());
	 		result = false;
	 	}	
 
 		return result;
 	}
 
 public static def batchUploadCentralReplication(host:String,port:Int,graphID:long,filePath:String,dataPort:int,placeID:Int):boolean{
 var result:boolean = true;
 
 try{
 val socket:Socket  = new Socket(host, port);
 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 var response:String = "";
 
 //First we need to Handshake
 out.println(AcaciaInstanceProtocol.HANDSHAKE);
 out.flush();
 response = reader.readLine();
 
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
 out.println(java.net.InetAddress.getLocalHost().getHostName());
 out.flush();
 }
 
 out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_REPLICATION_CENTRAL);
 out.flush();
 
 response = reader.readLine();

 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
 out.println(placeID);
 out.flush();
 response = reader.readLine();
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_GID))){				

 out.println(graphID);
 out.flush();
 response = reader.readLine();
 
 
 
 val fileName:String = filePath.substring(filePath.lastIndexOf("/")+1n);
 
 Console.OUT.println("filePath : " + filePath);
 Console.OUT.println("fileName : " + fileName);
 
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_NAME))){
 out.println(fileName);
 out.flush();
 
 response = reader.readLine();
 
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_LEN))){
 Console.OUT.println("File len : " + new File(filePath).length());
 out.println(new File(filePath).length());//File length in bytes
 out.flush();
 
 response = reader.readLine();
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_CONT))){
 sendFileThroughService(host, dataPort, fileName, filePath);
 }							
 }			
 }
 
 var counter:Int = 0n;
 
 while(true){
 out.println(AcaciaInstanceProtocol.FILE_RECV_CHK);
 out.flush();
 response = reader.readLine();

 if((response != null)&&(response.equals(AcaciaInstanceProtocol.FILE_RECV_WAIT))){
 Console.OUT.println("Checking file status : " + counter);
 counter++;
 
 Thread.currentThread().sleep(1000);
 //We sleep for 1 second, and try again.
 continue;
 }else{
 break;
 }
 }
 Console.OUT.println("File transfer complete...");
 
 //Next we wait till the batch upload completes
 while(true){
 out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_CHK);
 out.flush();
 
 response = reader.readLine();

 if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_WAIT))){
 Thread.currentThread().sleep(1000);
 //We sleep for 1 second, and try again.
 continue;
 }else{
 break;
 }
 }
 
 if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_ACK))){
 Console.OUT.println("Batch upload completed...");	
 }else{
 Console.OUT.println("There was an error in the upload parocess.");
 }
 }
 }
 }catch(e:java.net.UnknownHostException ){
 Logger_Java.error(e.getMessage());
 result = false;
 }catch(ec:java.io.IOException ){
 Logger_Java.error(ec.getMessage());
 result = false;
 }	
 
 return result;
 }
 
 	public static def batchUploadRDFStore(host:String, port:Int, graphID:long, filePath:String, dataPort:Int):boolean{
 		var result:boolean = true;
 
 		Console.OUT.println(filePath);
 
 
 		try{
 			//System.out.println(">>>>> Connecting to host : " + host + " port : " + port);
 			val socket:Socket = new Socket(host, port);
 			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
 			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 			var response:String = "";

 			//First we need to Handshake
 			out.println(AcaciaInstanceProtocol.HANDSHAKE);
 			out.flush();
 			response = reader.readLine();
 
 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
 				out.println(java.net.InetAddress.getLocalHost().getHostName());
 				out.flush();
 			}
 
 			out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_RDF);
 			out.flush();
 
 			response = reader.readLine();
 
 		}catch(e:java.net.UnknownHostException){
 			Logger_Java.error(e.getMessage());
 			result = false;
 		}catch(ec:java.io.IOException){
 			Logger_Java.error(ec.getMessage());
 			result = false;
 		}
 		return result;
 	}
 
	/**
	 * This method is used to insert new edge to Acacia Local Instance.
  	 * @param host
	 * @param graphID
	 * @param startVertexID
	 * @param endVertexID
	 */
 	public static def insertEdge(host:String , graphID:long , startVertexID:long,  endVertexID:long):boolean{		
		//The manger should contatct the appropriate AcaciaInstance and insert the edges.
	 	try{
	 		Console.OUT.println("Connecting to host : " + host + " at port : " + Conts_Java.ACACIA_INSTANCE_PORT);
	 		val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
	 		val out:PrintWriter = new PrintWriter(socket.getOutputStream());
	 		val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	 		var response:String = "";
	
	 		Console.OUT.println("OK Connected");
	 
	 		//First we need to Handshake
	 		out.println(AcaciaInstanceProtocol.HANDSHAKE);
	 		out.flush();
	 		response = reader.readLine();
	 
	 		if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
	 			out.println(java.net.InetAddress.getLocalHost().getHostName());
	 			out.flush();
	 		}
	 
	 		//First we must set the default graph instance to use. The default graph is session specific. 
	 		//Therefore each and every connection we make to the AcaciaInstance, we need to say we are using this particular graph.			
	 
	 		out.println(AcaciaInstanceProtocol.INSERT_EDGES);
	 		out.flush();
	
	 		response = reader.readLine();
	 
	 		if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
	 
	 			out.println(graphID);
	 			out.flush();
	 			response = reader.readLine();
	 
	 			if((response == null)&&(!response.equals(AcaciaInstanceProtocol.OK))){
	 				Logger_Java.error("Error in setting the default graph to : " + graphID + " on host : " + host);
	 			}
	 		}
	 
	 		out.println(startVertexID + " " + endVertexID);
	 		out.flush();
	 
	 		out.println(AcaciaInstanceProtocol.INSERT_EDGES_COMPLETE); //We need to say we are done with sending edges.
	 		out.flush();
	 
	 		if((response != null)&&(response.equals(AcaciaInstanceProtocol.INSERT_EDGES_ACK))){
	 			out.close();
	 		}			
	 	}catch(e:java.net.UnknownHostException){
	 		Logger_Java.error(e.getMessage());
	 		return false;
	 	}catch(ec:java.io.IOException){
	 		Logger_Java.error(ec.getMessage());
	 		return false;
	 	}
	 
	 	return true;
	 }
 
 	/**
  	 * This method is used to truncate a Acacia Local Instance.
  	 * @param host
  	 */
 	public static def truncateLocalInstance(host:String ,port:int){
 		try{
 			val socket:Socket = new Socket(host, port);
 			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
 			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 			var response:String = "";

 			//First we need to Handshake
 			out.println(AcaciaInstanceProtocol.HANDSHAKE);
 			out.flush();
 			response = reader.readLine();
 
 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
 				out.println(java.net.InetAddress.getLocalHost().getHostName());
 				out.flush();
 			}
 
 			out.println(AcaciaInstanceProtocol.TRUNCATE);
 			out.flush();

 			response = reader.readLine();
 
 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.TRUNCATE_ACK))){
 				Logger_Java.info("Local Instance Truncated");
 				out.close();
 			}
 			//Delting central store directory. Here we assume that central store is not kept in NFS
 			//However, its completely possible in doing so.
 			Logger_Java.info("Deleting the central store");
 			FileUtils.deleteDirectory(new java.io.File(Utils_Java.getAcaciaProperty("org.acacia.centralstore.location")));
 			Logger_Java.info("Done deleting the central store");
 		}catch(e:java.net.UnknownHostException){
 			Logger_Java.error(e.getMessage());
 		}catch(ec:java.io.IOException){
 			Logger_Java.error(ec.getMessage());
 		}		
 	}
 
 	public static def setDefaultGraph(host:String,graphID:Int):boolean{
		 try{
			 val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 var response:String = "";
			 
			 //First we need to Handshake
			 out.println(AcaciaInstanceProtocol.HANDSHAKE);
			 out.flush();
			 response = reader.readLine();
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
				 out.println(java.net.InetAddress.getLocalHost().getHostName());
				 out.flush();
			 }			
			 
			 out.println(AcaciaInstanceProtocol.SET_GRAPH_ID);
			 out.flush();
			 response = reader.readLine();
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				 out.println(graphID);
				 out.flush();
			 }
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SET_GRAPH_ID_ACK))){
			 	out.close();
			 }
		 }catch(e:java.net.UnknownHostException){
		 	Logger_Java.error(e.getMessage());
		 }catch(ec:java.io.IOException){
		 	Logger_Java.error(ec.getMessage());
		 }		
		 
		 return true;
	 }
 
	 public static def initializeGraphOnLocalInstance(host:String,graphID:Int):boolean{
	 	try{
			val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			var response:String = "";
			 
			//First we need to Handshake
			out.println(AcaciaInstanceProtocol.HANDSHAKE);
			out.flush();
			response = reader.readLine();
			 
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
				out.println(java.net.InetAddress.getLocalHost().getHostName());
				out.flush();
			}
			 
			 
			out.println(AcaciaInstanceProtocol.LOAD_GRAPH);
			out.flush();
			response = reader.readLine();
			 
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				out.println(graphID);
				out.flush();
			}
			 
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.LOAD_GRAPH_ACK))){
				out.close();
			}
		}catch(e:java.net.UnknownHostException){
			Logger_Java.error(e.getMessage());
		}catch(ec:java.io.IOException){
			Logger_Java.error(ec.getMessage());
		}		
		 
		return true;
	}
	 
	 /**
	  * This method is used to count the number of vertices available for a particular graph. This method is used to query each and every subgraph.
	  * 
	  * @param host
	  * 
	  * @deprecated : 26/9/2014, better use the countVertices(String graphID) method
	  */
	 public static def countVertices(host:String, graphID:String):long{
		 var result:long = -1;
		 try{
			 val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 var response:String = "";
			 
			 //First we need to Handshake
			 out.println(AcaciaInstanceProtocol.HANDSHAKE);
			 out.flush();
			 response = reader.readLine();
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
				 out.println(java.net.InetAddress.getLocalHost().getHostName());
				 out.flush();
			 }
			
			 out.println(AcaciaInstanceProtocol.COUNT_VERTICES);
			 out.flush();
			
			 response = reader.readLine();
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				 out.println(graphID);
				 out.flush();
			 }else{
			 	return -1;
			 }
			 
			 response = reader.readLine();
			 result = Long.parseLong(response);
			 out.close();
			 
			 return result;
	 
		}catch(e:java.net.UnknownHostException){
		 	Logger_Java.error("Connecting to host (1) " + host + " got error message : " + e.getMessage());
		}catch(ec:java.io.IOException){
			Logger_Java.error("Connecting to host (1) " + host + " got error message : " + ec.getMessage());
		}
	 
		return -1;
	}
 
 	public static def countVertices(host:String, graphID:String, partitionID:String):long{
 		var result:long = -1;
		 try{
			val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			var response:String = "";
		 
		 	//First we need to Handshake
		 	out.println(AcaciaInstanceProtocol.HANDSHAKE);
		 	out.flush();
		 	response = reader.readLine();
		 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
				 out.println(java.net.InetAddress.getLocalHost().getHostName());
				 out.flush();
			 }
			
			 out.println(AcaciaInstanceProtocol.COUNT_VERTICES);
			 out.flush();
			
			 response = reader.readLine();
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
			 	out.println(graphID);
			 	out.flush();
			 
			 	response = reader.readLine();
			 
			 	if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
					out.println(partitionID);
				 	out.flush();
			 	}else{
			 		return -1;
			 	}
			 
			 }else{
			 	return -1;
			 }
			 
			 response = reader.readLine();
			 result = Long.parseLong(response);
			 out.close();
			 
			 return result;
			 
		 }catch(e:java.net.UnknownHostException){
		 	Logger_Java.error("Connecting to host (1) " + host + " got error message : " + e.getMessage());
		 }catch(ec:java.io.IOException){
		 	Logger_Java.error("Connecting to host (1) " + host + " got error message : " + ec.getMessage());
		 }
		 
		 return -1;
	 }
 
		 public static def countVertices(graphID:String):long{
		 	val result:long = Long.parseLong(org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("SELECT VERTEXCOUNT FROM ACACIA_META.GRAPH WHERE IDGRAPH=" + graphID).getObjectArray()(0n) as String);
		 	return result;
		 }
		 
		 public static def countEdges(host:String,graphID:String,partitionID:String):long{
		 	var result:long = -1;
			 try{
			 	 val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
				 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
				 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				 var response:String = "";
				 
				 //First we need to Handshake
				 out.println(AcaciaInstanceProtocol.HANDSHAKE);
				 out.flush();
				 response = reader.readLine();
				 
				 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
					 out.println(java.net.InetAddress.getLocalHost().getHostName());
					 out.flush();
				 }
			
				 out.println(AcaciaInstanceProtocol.COUNT_EDGES);
				 out.flush();
				
				 response = reader.readLine();
				 
				 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
					 out.println(graphID);
					 out.flush();
			 
			 		 response = reader.readLine();
			 
					 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
						 out.println(partitionID);
						 out.flush();
					 }else{
					 	 return -1;
			 		 }
			 
			 	}else{
			 		return -1;
			 	}
			 
			 	response = reader.readLine();
			 	result = Long.parseLong(response);
			 	out.close();
			 
			 	return result;
		 
		 }catch(e:java.net.UnknownHostException){
		 	Logger_Java.error("Connecting to host (2) " + host + " got error message : " + e.getMessage());
		 }catch(ec:java.io.IOException){
		 	Logger_Java.error("Connecting to host (2) " + host + " got error message : " + ec.getMessage());
		 }
		 
		 return -1;
	 }
 
	 /**
	  * This method is used to count the number of vertices available for a particular graph.
	  * @param host
	  * @deprecated: Sept 26 2014, use countEdges(graphID) instead
	  */
	 public static def countEdges(host:String,graphID:String):long{
		 var result:long = -1;
		 try{
			 val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 var response:String = "";
			 
			 //First we need to Handshake
			 out.println(AcaciaInstanceProtocol.HANDSHAKE);
			 out.flush();
			 response = reader.readLine();
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
				 out.println(java.net.InetAddress.getLocalHost().getHostName());
				 out.flush();
			 }
			
			 out.println(AcaciaInstanceProtocol.COUNT_EDGES);
			 out.flush();
			
			 response = reader.readLine();
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				 out.println(graphID);
				 out.flush();
			 }else{
			 	 return -1;
			 }
			 
			 response = reader.readLine();
			 result = Long.parseLong(response);
			 out.close();
			 
			 return result;
		 
		 }catch(e:java.net.UnknownHostException){
		 	Logger_Java.error("Connecting to host (2) " + host + " got error message : " + e.getMessage());
		 }catch(ec:java.io.IOException){
		 	Logger_Java.error("Connecting to host (2) " + host + " got error message : " + ec.getMessage());
		 }
		 
		 return -1;
	 }
 
	 public static def countEdges(graphID:String):long{
	 	val result:long = Long.parseLong(org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("SELECT EDGECOUNT FROM ACACIA_META.GRAPH WHERE IDGRAPH=" + graphID).getObjectArray()(0n) as String);
	 	return result;		
	 }
 
	 /**
	  * This method is used to count the number of vertices available for a particular graph.
	  * @param host
	  */
	 public static def pageRank(host:String, graphID:String, hostList:String):String{
	 	 var result:String = null;
	 
		 try{
		 	val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
		 	val out:PrintWriter = new PrintWriter(socket.getOutputStream());
		 	val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 	var response:String = "";
		 
		 	//First we need to Handshake
		 	out.println(AcaciaInstanceProtocol.HANDSHAKE);
		 	out.flush();
		 	response = reader.readLine();
		 
		 	if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
		 		out.println(java.net.InetAddress.getLocalHost().getHostName());
		 		out.flush();
		 	}
		
		 	out.println(AcaciaInstanceProtocol.PAGE_RANK);
		 	out.flush();
		
		 	response = reader.readLine();
		 
		 	if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
		 		out.println(graphID);
		 		out.flush();
		 	}else{
		 		return "-1";
		 	}
		 
		 	response = reader.readLine();
		 	//In future we need not to send the host list to each and every worker. Instead we can use a service
		 	//such as Zookeeper for this task.
		 	if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
		 		out.println(hostList);
		 		out.flush();
		 	}else{
		 		return "-1";
		 	}
		 
		 	result = reader.readLine();
		 
		 	out.close();
		 
		 	return result;			
	 	}catch(e:java.net.UnknownHostException){
	 		Logger_Java.error("Connecting to host (3) " + host + " got error message : " + e.getMessage());
	 	}catch(ec:java.io.IOException){
	 		Logger_Java.error("Connecting to host (3) " + host + " got error message : " + ec.getMessage());
	 	}
	 
	 	return "-1";
	 }	

 	public static def pageRankTopK(host:String, graphID:String, partitionID:String, hostList:String, k:String):String{
 		var result:String = null;
 
 		try{
			 val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 var response:String = "";
			 
			 //First we need to Handshake
			 out.println(AcaciaInstanceProtocol.HANDSHAKE);
			 out.flush();
			 response = reader.readLine();
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
				 out.println(java.net.InetAddress.getLocalHost().getHostName());
				 out.flush();
			 }
			
			 out.println(AcaciaInstanceProtocol.PAGE_RANK_TOP_K);
			 out.flush();
			
			 response = reader.readLine();
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				 out.println(graphID);
				 out.flush();
			 }else{
			 return "-1";
			 }
			 
			 response = reader.readLine();
			
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				 out.println(partitionID);
				 out.flush();
			 }else{
			 	return "-1";
			 }
			 
			 response = reader.readLine();
			 //In future we need not to send the host list to each and every worker. Instead we can use a service
			 //such as Zookeeper for this task.
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				 out.println(hostList);
				 out.flush();
			 }else{
			 	return "-1";
			 }
			 
			 //Here we send the k value
			 result = reader.readLine();
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				 out.println(k);
				 out.flush();
			 }else{
			 	return "-1";
			 }
			 
			 result = reader.readLine();
			 
			 out.close();
			 
			 return result;			
		 }catch(e:java.net.UnknownHostException){
		 	Logger_Java.error("Connecting to host (4) " + host + " got error message : " + e.getMessage());
		 }catch(ec:java.io.IOException){
		 	Logger_Java.error("Connecting to host (4) " + host + " got error message : " + ec.getMessage());
		 }
 
 		 return "-1";		
 	}
 
	public static def countGlobalTraingles(graphID:String):long{
		 var result:long = -1;
		 var startVid:long = -1;
		 var endVid:long = -1;
		 val centralPartionCount:Int = Int.parse(org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).getObjectArray()(0n) as String);
		 //int startPartitionID = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("SELECT IDPARTITION FROM ACACIA_META.PARTITION WHERE GRAPH_IDGRAPH=" + graphID + " ORDER BY IDPARTITION LIMIT 1").value)[(int)0L]);//SELECT IDPARTITION FROM "ACACIA_META"."PARTITION" WHERE GRAPH_IDGRAPH=212 ORDER BY IDPARTITION LIMIT 1
		 val edgeList:HashMap[Long, Long] = new HashMap[Long, Long]();
		 val localSubGraphMap:HashMap[Long, TreeSet] = new HashMap[Long, TreeSet](); 
		 val vals:TreeSet = new TreeSet();
		 // //StringBuilder sbPersist = new StringBuilder();
		 // 
		 Console.OUT.println("Started constructing graph");
		 //System.out.println("Start partition ID : " + startPartitionID);
		 for(var i:Int = 0n; i < centralPartionCount; i++){
		 	Console.OUT.println("Partition id : " + i);
		 
		 	//val store:AcaciaHashMapCentralStore = new AcaciaHashMapCentralStore(Int.parse(graphID), i);
		 	//	    	store.loadGraph();
		 
		 	val store:AcaciaLocalStore = AcaciaLocalStoreFactory.load(Int.parse(graphID), i, Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder"), true);
		 	store.loadGraph();
		 
		 	val edgeList2:HashMap[Long, HashSet[Long]] = store.getUnderlyingHashMap() as HashMap[Long,HashSet[Long]];
		 	val itr:Iterator[x10.util.Map.Entry[Long, HashSet[Long]]] = edgeList2.entries().iterator();
		 	var firstVertex:long = 0l;
		 
		 	while(itr.hasNext()){
		 		var entr:x10.util.Map.Entry[Long, HashSet[Long]] = itr.next();
		 		firstVertex = entr.getKey();
		 		var hs:TreeSet = localSubGraphMap.get(firstVertex) as TreeSet;
		 
		 		if(hs == null){
		 			hs = new TreeSet();
		 		}
		 
		 		var hs2:HashSet[Long] = entr.getValue();
		 
		 		for(secondVertex:long in hs2){
		 			hs.add(secondVertex);
		 		}
		 
		 		localSubGraphMap.put(firstVertex, hs);
		 	}
		 
		 }
		 
		 Console.OUT.println("Done constructing graph");
		 
		 val degreeMap:TreeMap = new TreeMap();
		 val degreeReverseLookupMap:HashMap[Long, Long] = new HashMap[Long, Long]();
		 
		 var degree:long = -1;
		 startVid = -1;
		 var degreeSet:TreeSet = null;
		 itr:Iterator[x10.util.Map.Entry[Long, TreeSet]] = localSubGraphMap.entries().iterator();
		 while(itr.hasNext()){
		 	item:x10.util.Map.Entry[Long, TreeSet] = itr.next();
		 	startVid = item.getKey();
		 	degree = item.getValue().size();
		 
		 	if(degreeMap.containsKey(degree)){
		 		degreeSet = degreeMap.get(degree) as TreeSet;
		 		degreeSet.add(startVid);
		 		degreeMap.put(degree, degreeSet);
		 	}else{
		 		degreeSet = new TreeSet();
		 		degreeSet.add(startVid);
		 		degreeMap.put(degree, degreeSet);
		 	}
		 
		 	degreeReverseLookupMap.put(startVid, degree);
		 }
		 
		 Console.OUT.println("Completed degree reverse lookup map construction");
		 //We need to create the degree map as well.
		 
		 //System.out.println("Started counting algorithm at : " + getCurrentTimeStamp());
		 //Now we start count the traingles. But this happens only in the local graph.
		 //This might be a naive method of doing this. Good for the moment... Sept 30 2014
		 var traingleCount:long = 0;
		 var v1:long = 0;
		 var v2:long = 0;
		 var v3:long = 0;
		 //		long fullCount = 0;
		 val sb2:StringBuilder = new StringBuilder();
		 val traingleTree:HashMap[Long, HashMap[Long, ArrayList[Long]]] = new HashMap[Long, HashMap[Long, ArrayList[Long]]](); 
		 val degreeListVisited:ArrayList[Long] = new ArrayList[Long]();
		 
		 //itr1:Iterator[x10.util.Map.Entry[Long, TreeSet]] = degreeMap.entrySet().iterator() as Iterator[x10.util.Map.Entry[Long, TreeSet]];
		 val itr1U:java.util.Iterator = degreeMap.keySet().iterator();
		 while(itr1U.hasNext()){
		 	//item:x10.util.Map.Entry[Long, TreeSet] = itr1.next();
		    val kkkk:Long = itr1U.next() as Long;
		 	//System.out.println("Working on Degree : " + item.getKey());
		 	val vVertices:TreeSet = degreeMap.get(kkkk) as TreeSet;
		 
		 	//When one of these rounds completes we know that vertex vs has been completely explored for its triangles. Since we treat
		 	//a collection of vertices having the same degree at a time, we can do a degree based optimization. That is after this for loop
		 	//we can mark the degree as visited. Then for each second and third loops can be skipped if the vertices considered there are
		 	//having a degree that was marked before. Because we know that we have already visited all the vertices of that degree before.
		 	itr2:java.util.Iterator = vVertices.iterator() as java.util.Iterator;
		 
		 	while(itr2.hasNext()){
		 		v:Long = itr2.next() as Long;
		 
		 		val uList:TreeSet = localSubGraphMap.get(v); //We know for sure that v has not been visited yet
		 		if(uList != null){ //Because in local subgraph map we may mark only u -> v, but v may not have corresponding record in the map (i.e., v -> u).
		 			itr3:java.util.Iterator = uList.iterator() as java.util.Iterator;
		 			while(itr3.hasNext()){
		 				u:Long = itr3.next() as Long;
		 				 //second for loop
						 //						if(degreeReverseLookupMap.containsKey(u)){
						 //							degree = degreeReverseLookupMap.get(u);
						 //							if(degreeListVisited.contains(degree)){
						 //								//There is no point of considering the vertices of this degree. We have already counted triangles for all the
						 //								//vertices with this degree
						 //								continue;
						 //							}
						 //						}
		 
		 				val nuList:TreeSet = localSubGraphMap.get(u);
		 				if(nuList != null){
		 					itr4:java.util.Iterator = nuList.iterator() as java.util.Iterator;
		 					while(itr4.hasNext()){
		 						nu:Long = itr4.next() as Long;
		 						 //Third for loop
								 //								if(degreeReverseLookupMap.containsKey(nu)){
								 //									long degree2 = degreeReverseLookupMap.get(nu);
								 //									if(degreeListVisited.contains(degree2)){
								 //										//There is no point of considering the vertices of this degree. We have already counted triangles for all the
								 //										//vertices with this degree
								 //										continue;
								 //									}
								 //								}
		 						val nwList:TreeSet = localSubGraphMap.get(nu);
		
		 						if((nwList != null) && (nwList.contains(v))){ //We know for sure that v has not been visited yet
		 							 //fullCount++;
		 
		 
									 //At this point we have discovered a triangle. But now we need to makesure that we have not counted that triangle before.
									 //To do that we use the triangle tree data structure which keeps on track of the triangles we have marked so far.
									 //Note that the triangle tree may not be as efficient as we expect.
									 
									 //Here we have to be careful to first sort the three triangle vertices by their value. Then we do not
									 //get into the trouble of having multiple combinations of the same three vertices.
									 
		 							var tempArr:Rail[long]  = new Rail[long]();
		 							tempArr(0n) = v;
		 							tempArr(1n) = u;
		 							tempArr(2n) = nu;
		 							java.util.Arrays.sort(x10.interop.Java.convert(tempArr));
		 
		 							v1 = tempArr(0n);
									v2 = tempArr(1n);
									v3 = tempArr(2n);
		 
		 							//The top level vertices are represented by v1
		 							var itemRes:HashMap[Long, ArrayList[Long]] = traingleTree.get(v1);
		 							if(itemRes != null){
		 								if(itemRes.containsKey(v2)){
		 									val lst:ArrayList[Long] = itemRes.get(v2);
			 								if(!lst.contains(v3)){
			 									lst.add(v3);
			 									itemRes.put(v2, lst);
			 									traingleTree.put(v1, itemRes);
			 
			 									traingleCount++;
			 									//												sb2.append("" + v1 + " " + v2 + " " + v3 + "\n");
			 								}else{
			 									//We have discovered this triangle before. It is already recorded in the tree.
			 								}
		 								}else{
		 									//This means that the infrmation of the second vertex is still not added to the traingle tree.
		 									//This is a fresh round of accounting for v2.
		 									val newU:ArrayList[Long] = new ArrayList[Long]();
		 									newU.add(v3);
		 									itemRes.put(v2, newU);
		 									traingleTree.put(v1, itemRes);//here its just replacing the old itemRes
		 
		 									traingleCount++;
		 									//											sb2.append("" + v1 + " " + v2 + " " + v3 + "\n");
		 								}
		 							}else{
			 							//This means that there is even no record of the v1. So we need to add everything from the scratch.
			 							itemRes = new HashMap[Long, ArrayList[Long]]();
			 							val newU:ArrayList[Long] = new ArrayList[Long]();
			 							newU.add(v3);
			 							itemRes.put(v2, newU);
			 							traingleTree.put(v1, itemRes); //here we are adding a new itemRes
			 
			 							traingleCount++;
			 							//										sb2.append("" + v1 + " " + v2 + " " + v3 + "\n");
			 						}
			 					}
			 				}
			 			}
			 		}
			 	}
			}
		 
			degreeListVisited.add(kkkk);
			degreeMap.put(kkkk, null); //Release the memory for this Key's array list. This way we will get more memory to run the algorithm.
		}
		result = traingleCount;
		 
		return result;
 	}
 
 	public static def countTraingles(host:String, port:Int, graphID:String, partitionID:String):long{
 		var result:long = -1;
 
 		try{
 			Console.OUT.println("host : " + host + " port : " + port);
 			val socket:Socket = new Socket(host, port);
 			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
 			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 			var response:String = "";
 
 			//First we need to Handshake
 			out.println(AcaciaInstanceProtocol.HANDSHAKE);
 			out.flush();
 			response = reader.readLine();
			//Note : Oct 2 2014: This handShake Process has some issues. Original HandSHake is intended to communicate the
			// master's host address to the worker. But this seems quite odd technique.
 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
 				out.println(java.net.InetAddress.getLocalHost().getHostName());
 				out.flush();
 			}

 			out.println(AcaciaInstanceProtocol.TRIANGLES);
 			out.flush();

 			response = reader.readLine();
 
 			//First send the graph ID
 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
 				out.println(graphID);
 				out.flush();
 			}else{
 				return -1;
 			}
 
 			response = reader.readLine();
 
 			//Second send the partition ID
 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
 				out.println(partitionID);
 				out.flush();
 			}else{
 				return -1;
 			}
 
 			result = Long.parseLong(reader.readLine());
 			Console.OUT.println("For host " + host + " Triangle result : " + result);
 			out.close();
 
 			return result;			
 		}catch(e:java.net.UnknownHostException){
 			Logger_Java.error("Connecting to host (5) " + host + " got error message : " + e.getMessage());
 		}catch(ec:java.io.IOException){
 			Logger_Java.error("Connecting to host (5) " + host + " got error message : " + ec.getMessage());
 		}
 
 		return -1;
 	}
 
 	public static def getFreeSpaceInfo(host:String):String{
		 try{
		 	val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
		 	val out:PrintWriter = new PrintWriter(socket.getOutputStream());
		 	val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 	var response:String = "";
		 
		 	//First we need to Handshake
		 	out.println(AcaciaInstanceProtocol.HANDSHAKE);
		 	out.flush();
		 	response = reader.readLine();
		 
		 	if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
		 		out.println(java.net.InetAddress.getLocalHost().getHostName());
		 		out.flush();
		 	}
		
		 	out.println(AcaciaInstanceProtocol.STATUS);
		 	out.flush();
		
		 	response = reader.readLine();
		 	out.close();
		 	return host + ":" + response;			
		 }catch(e:java.net.UnknownHostException){
		 	Logger_Java.error("Connecting to host (6) " + host + " got error message : " + e.getMessage());
		 }catch(ec:java.io.IOException){
		 	Logger_Java.error("Connecting to host (6) " + host + " got error message : " + ec.getMessage());
		 }
		 
		 return null;
 	}
 
	 public static def getNPlaces(host:String):Int{
	 /*
		 try{
		 //here we are connecting to the first instance only. May be this might become a bottleneck in future.
		 val socket:Socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
		 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
		 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 var response:String = "";
		 
		 //First we need to Handshake
		 out.println(AcaciaInstanceProtocol.HANDSHAKE);
		 out.flush();
		 response = reader.readLine();
		 
		 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
		 out.println(java.net.InetAddress.getLocalHost().getHostName());
		 out.flush();
		 }
		 
		 out.println(AcaciaInstanceProtocol.NPLACES);
		 out.flush();
		 
		 response = reader.readLine();
		 out.close();
		 return Int.parse(response);			
		 }catch(e:java.net.UnknownHostException){
		 Logger_Java.error("Connecting to host (6) " + host + " got error message : " + e.getMessage());
		 }catch(ec:java.io.IOException){
		 Logger_Java.error("Connecting to host (6) " + host + " got error message : " + ec.getMessage());
		 }
		 
		 return -1n;
	  * */
	     return Int.parse(Utils.getAcaciaProperty("org.acacia.server.nplaces"));
	 }
 
	 /**
	  * Per each worker we have to run the SPARQL query in parallel.
	  * @param host
	  * @param port
	  * @param graphID
	  * @param partitionID
	  * @return
	  * @throws IOException 
	  * @throws UnknownHostException 
	  */
	 public static def runSPARQL(host:String, port:Int, graphID:String, partitionID:String, query:String, placeID:long, placesDetails:String) :ArrayList[InterimResult]{
		 var result:ArrayList[String] = new ArrayList[String]();
		 var arr:Rail[String] = null;
		 var data:ArrayList[InterimResult] = null;
		 try{
		 val socket:Socket = new Socket(host, port);
		 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
		 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 var response:String = "";		
		 var i:Int=0n;
		 		 
		 out.println(AcaciaInstanceProtocol.HANDSHAKE);
		 out.flush();
		 response = reader.readLine();
		 
		 Console.OUT.println("resp1:" + response);
		 
		 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
			 Console.OUT.println("ccc");
			 out.println(java.net.InetAddress.getLocalHost().getHostName());
			 out.flush();
			 
			 out.println(AcaciaInstanceProtocol.EXECUTE_QUERY);
			 out.flush();
			 response = reader.readLine();
			 Console.OUT.println("ccc:"+response);
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_QUERY))){
				 Console.OUT.println("|" + query + "|");
				 out.println(query);
				 out.flush();
				 response = reader.readLine();
				 Console.OUT.println("dddd:"+response);
				 
				 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_GID))){
					 Console.OUT.println("dend-gid:"+graphID);
					 out.println(graphID);
					 out.flush();
					 response = reader.readLine();
					 Console.OUT.println("eee:"+response);
					 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PARTITION_ID))){							
						 Console.OUT.println("partID:"+partitionID);
						 out.println(partitionID);
						 out.flush();
						 response = reader.readLine();
						 
						 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PLACEID))){		
							 out.println(placeID);
							 out.flush();
							 response = reader.readLine();
							 Console.OUT.println("bbbbb1:"+response);
			 
							 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PLACEDETAILS))){		
								 out.println(placesDetails);
								 out.flush();
								 response = reader.readLine();
								 Console.OUT.println("bbbbb2:"+response);
								 if(response.equals("Not empty")){
			 
									 out.println("Send");
									 out.flush();
									 
									 //Jan24, 2016: At this point we should find the Tripples available in our query and then
									 //send tripple object by tripple object to workers and get their results. 
									 //try{
									 	val ois = new ObjectInputStream(socket.getInputStream());
									 	data = ois.readObject() as ArrayList[InterimResult];
									 //}catch(e:java.lang.ClassNotFoundException){
									 	//Console.OUT.println(e.getMessage());
									 //}
									 
									 response = reader.readLine();
				 
				 					 while(!response.equals("Finish")){
										 result.add(response);
										 out.println("Send");
										 out.flush();
										 i+=1;
										 response = reader.readLine();
			 
				 					 }
			 					}else if(response.equals("Empty")){
			 						data = null;
				 				}
				 			}
				 		}
				 	}
			 	}
		 	}
		 }
		 reader.close();	
		
		 /*if(result != null){
		 	arr = result.toRail();
		 }*/
		 }catch(e:java.net.UnknownHostException){
		 	Logger_Java.error("Connecting to host (6) " + host + " got error message : " + e.getMessage());
		 }catch(ec:java.io.IOException){
		 	Logger_Java.error("Connecting to host (6) " + host + " got error message : " + ec.getMessage());
		 }catch(e:java.lang.ClassNotFoundException){
		 	Logger_Java.error("Connecting to host (6) " + host + " got error message : " + e.getMessage());
		 }
		 return data;
	 }
	 
	 public static def runSPARQLWithReplications(host:String, port:Int, graphID:String, partitionID:String, query:String, placeID:long, placesDetails:String, replicatingID:Int) :ArrayList[InterimResult]{
	 var result:ArrayList[String] = new ArrayList[String]();
	 var arr:Rail[String] = null;
	 var data:ArrayList[InterimResult] = null;
	 try{
	 val socket:Socket = new Socket(host, port);
	 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
	 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	 var response:String = "";		
	 var i:Int=0n;
	 
	 out.println(AcaciaInstanceProtocol.HANDSHAKE);
	 out.flush();
	 response = reader.readLine();
	 
	 Console.OUT.println("resp1:" + response);
	 
	 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
	 Console.OUT.println("ccc");
	 out.println(java.net.InetAddress.getLocalHost().getHostName());
	 out.flush();
	 
	 out.println(AcaciaInstanceProtocol.EXECUTE_QUERY_WITH_REPLICATION);
	 out.flush();
	 response = reader.readLine();
	 Console.OUT.println("ccc:"+response);
	 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_QUERY))){
	 Console.OUT.println("|" + query + "|");
	 out.println(query);
	 out.flush();
	 response = reader.readLine();
	 Console.OUT.println("dddd:"+response);
	 
	 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_GID))){
	 Console.OUT.println("dend-gid:"+graphID);
	 out.println(graphID);
	 out.flush();
	 response = reader.readLine();
	 Console.OUT.println("eee:"+response);
	 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PARTITION_ID))){							
	 Console.OUT.println("partID:"+partitionID);
	 out.println(partitionID);
	 out.flush();
	 response = reader.readLine();
	 
	 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PLACEID))){		
	 out.println(placeID);
	 out.flush();
	 response = reader.readLine();
	 Console.OUT.println("bbbbb1:"+response);
	 
	 if((response != null)&&(response.equals(AcaciaInstanceProtocol.REPLICATING_ID))){		
	 out.println(replicatingID);
	 out.flush();
	 response = reader.readLine();
	 Console.OUT.println("bbbbb1:"+response);
	 
	 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PLACEDETAILS))){		
	 out.println(placesDetails);
	 out.flush();
	 response = reader.readLine();
	 Console.OUT.println("bbbbb2:"+response);
	 if(response.equals("Not empty")){
	 
	 out.println("Send");
	 out.flush();
	 
	 //Jan24, 2016: At this point we should find the Tripples available in our query and then
	 //send tripple object by tripple object to workers and get their results. 
	 //try{
	 val ois = new ObjectInputStream(socket.getInputStream());
	 data = ois.readObject() as ArrayList[InterimResult];
	 //}catch(e:java.lang.ClassNotFoundException){
	 //Console.OUT.println(e.getMessage());
	 //}
	 
	 response = reader.readLine();
	 
	 while(!response.equals("Finish")){
	 result.add(response);
	 out.println("Send");
	 out.flush();
	 i+=1;
	 response = reader.readLine();
	 
	 }
	 }else if(response.equals("Empty")){
	 data = null;
	 }
	 }
	 }
	 }
	 }
	 }
	 }
	 }
	 reader.close();	
	 
	 /*if(result != null){
	  * arr = result.toRail();
	  * }*/
	 }catch(e:java.net.UnknownHostException){
	 Logger_Java.error("Connecting to host (6) " + host + " got error message : " + e.getMessage());
	 }catch(ec:java.io.IOException){
	 Logger_Java.error("Connecting to host (6) " + host + " got error message : " + ec.getMessage());
	 }catch(e:java.lang.ClassNotFoundException){
	 Logger_Java.error("Connecting to host (6) " + host + " got error message : " + e.getMessage());
	 }
	 return data;
	 }
	 
	 
	 /**
	  * Per each worker we have to run K Core Algorithm in parallel.
	  * @param host
	  * @param port
	  * @param graphID
	  * @param partitionID
	  * @return
	  * @throws IOException 
	  * @throws UnknownHostException 
	  */
	 public static def runKCore(host:String, port:Int, graphID:String, partitionID:String, kValue:String, placeID:long, placesDetails:String) throws java.net.UnknownHostException, java.io.IOException:Rail[String]{
		 
	 	 var result:ArrayList[String] = new ArrayList[String]();
		 val socket:Socket = new Socket(host, port);
		 val out:PrintWriter = new PrintWriter(socket.getOutputStream());
		 val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 var response:String = "";		
		 var i:Int=0n;
	 
		 out.println(AcaciaInstanceProtocol.HANDSHAKE);
		 out.flush();
		 response = reader.readLine();
	 
		 Console.OUT.println("resp1:" + response);
	 
		 if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
			 Console.OUT.println("aaa");
			 out.println(java.net.InetAddress.getLocalHost().getHostName());
			 out.flush();
			 
			 out.println(AcaciaInstanceProtocol.RUN_KCORE);
			 out.flush();
			 response = reader.readLine();
			 Console.OUT.println("RUN_KCORE: "+response);
			 
			 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_KVALUE))){
				 Console.OUT.println(kValue);
				 out.println(kValue);
				 out.flush();
				 response = reader.readLine();
				 Console.OUT.println("SEND_KVALUE: "+response);
	 
				 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_GID))){
					 Console.OUT.println(graphID);
					 out.println(graphID);
					 out.flush();
					 response = reader.readLine();
					 Console.OUT.println("SEND_GID "+response);
					 
					 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PARTITION_ID))){							
						 Console.OUT.println(partitionID);
						 out.println(partitionID);
						 out.flush();
						 response = reader.readLine();
						 Console.OUT.println("SEND_PARTITION_ID "+response);
	 
						 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PLACEID))){		
						 	 Console.OUT.println(placeID);
						 	 out.println(placeID);
							 out.flush();
							 response = reader.readLine();
							 Console.OUT.println("SEND_PLACEID: "+response);
	 
							 if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PLACEDETAILS))){		
								 out.println(placesDetails);
								 out.flush();
								 response = reader.readLine();
								 Console.OUT.println("SEND_PLACEDETAILS "+response);
								 
							 	 if(response.equals("Not empty")){
	 
									 out.println("Send");
									 out.flush();
									 
	 								 response = reader.readLine();
	 
									 while(!response.equals("Finish")){	
										 result.add(response);
										 out.println("Send");
										 out.flush();
										 i+=1;
	 									 response = reader.readLine();
	 
	 								 }
									 
							 	 }else if(response.equals("Empty")){
	 								result=null;
							 	 }
	 						}
	 					}
	 				}
	 			}
	 		}
	 	}
	 	reader.close();	
	 	var arr:Rail[String] = null;
	 	if(result != null){
	 		arr = result.toRail();
	 	}
	 
	 	return arr;
	 } 
	 
 
 	/**
  	 * This method is used to remove the vertices of a particular graph.
	 * @param host
	 */
 	public static def removeVertices(host:String, port:Int, graphID:String, partitionID:String):long{
 		var result:boolean = true;
 
 		try{
 			val socket:Socket = new Socket(host, port);
 			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
 			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 			var response:String = "";
 
 			//First we need to Handshake
 			out.println(AcaciaInstanceProtocol.HANDSHAKE);
 			out.flush();
 			response = reader.readLine();
 
 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
 				out.println(java.net.InetAddress.getLocalHost().getHostName());
 				out.flush();
 			}

 			out.println(AcaciaInstanceProtocol.DELETE);
 			out.flush();

 			response = reader.readLine();
 
 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
 				out.println(graphID);
 				out.flush();
 
 				response = reader.readLine();
 
 				if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PARTITION_ID))){
 					out.println(partitionID);
 					out.flush();
 				}else{
 					return -1;
 				}
 
 			}else{
 				return -1;
 			}
 
 			response = reader.readLine();
 
 			return Long.parseLong(response);
 
 		}catch(e:java.net.UnknownHostException){
 			Logger_Java.error("Connecting to host (7) " + host + " got error message : " + e.getMessage());
 		}catch(ec:java.io.IOException){
 			Logger_Java.error("Connecting to host (7) " + host + " got error message : " + ec.getMessage());
 		}
 
 		return -1;
 	}

 	public static def downloadCentralStore(graphID:Int, i:Int):boolean{
 		var result:boolean = true;
 
 		//First need to find where the central store partition is located.
 		var host:String = null;
 		var port:Int = 0n;
 
 		try{
 			val socket:Socket = new Socket(host, port);
 			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
 			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 			var response:String = "";

 			//First we need to Handshake
 			out.println(AcaciaInstanceProtocol.HANDSHAKE);
 			out.flush();
 			response = reader.readLine();
 
 			if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
 				out.println(java.net.InetAddress.getLocalHost().getHostName());
 				out.flush();
 			}
 
 			out.println(AcaciaInstanceProtocol.BATCH_UPLOAD);
 			out.flush();
 		}catch(e:java.net.UnknownHostException){
 			Logger_Java.error("Connecting to host (8) " + host + " got error message : " + e.getMessage());
 		}catch(ec:java.io.IOException){
 			Logger_Java.error("Connecting to host (8) " + host + " got error message : " + ec.getMessage());
 		}
 
 		return result;
 	}
}
