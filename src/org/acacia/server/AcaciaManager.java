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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.acacia.centralstore.java.AcaciaHashMapCentralStore;
import org.acacia.localstore.java.AcaciaLocalStore;
import org.acacia.localstore.java.AcaciaLocalStoreFactory;
import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;
import org.acacia.rdf.sparql.java.ExecuteQuery;

import java.io.IOException;

public class AcaciaManager{
	public static void run() throws UnknownHostException{

	}
	
	public static void sendFileThroughService(final String host, final int port, final String fileName, final String filePath){
		Thread t = new Thread(new Runnable(){
			public void run(){
				try{
					Socket socket = new Socket(host, port);

					OutputStream out = socket.getOutputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
					File file = new File(filePath);
					BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
				
					out.write(fileName.getBytes());
					out.flush();

					String response = reader.readLine();
					
					if (response.equals(AcaciaInstanceProtocol.SEND_FILE)){
						System.out.println("Sending file");
						byte[] buffer = new  byte[8*1024];
						int c = -1;

						while((c=bin.read(buffer))>0){
							out.write(buffer, 0, c);
							out.flush();
						}
						System.out.println("----Done--->");

					}
					
					System.out.println("Done sending...");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});	
		
		t.start();
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
	public static boolean batchUploadFile(String host, int port, long graphID, String filePath, int dataPort){
		boolean result = true;
		
		try{
			Socket socket = new Socket(host, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

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
			
				String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
				
				System.out.println("filePath : " + filePath);
				System.out.println("fileName : " + fileName);
				
				if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_NAME))){
						out.println(fileName);
						out.flush();
						
						response = reader.readLine();
						
						if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_LEN))){
							System.out.println("File len : " + new File(filePath).length());
							out.println(new File(filePath).length());//File length in bytes
							out.flush();
							
							response = reader.readLine();
							if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_CONT))){
								sendFileThroughService(host, dataPort, fileName, filePath);
							}							
						}			
					}
				
					int counter = 0;
				
				    while(true){
						out.println(AcaciaInstanceProtocol.FILE_RECV_CHK);
						out.flush();
						response = reader.readLine();

						if((response != null)&&(response.equals(AcaciaInstanceProtocol.FILE_RECV_WAIT))){
							System.out.println("Checking file status : " + counter);
							counter++;
							
							try {
								Thread.currentThread().sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} //We sleep for 1 second, and try again.
							continue;
						}else{
							break;
						}
				    }
				    System.out.println("File transfer complete...");
				    
				    //Next we wait till the batch upload completes
				    while(true){
						out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_CHK);
						out.flush();
						
						response = reader.readLine();

						if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_WAIT))){
							try {
								Thread.currentThread().sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} //We sleep for 1 second, and try again.
							continue;
						}else{
							break;
						}
				    }
				    
				    if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_ACK))){
				    	System.out.println("Batch upload completed...");	
					}else{
						System.out.println("There was an error in the upload parocess.");
					}
			}
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
			result = false;
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
			result = false;
		}	
		
		return result;
	}
	
	public static boolean batchUploadCentralStore(String host, int port, long graphID, String filePath, int dataPort){
		boolean result = true;
		
		System.out.println(filePath);
		
		
		try{
			//System.out.println(">>>>> Connecting to host : " + host + " port : " + port);
			Socket socket = new Socket(host, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

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
				String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
				
				if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_NAME))){
						out.println(fileName);
						out.flush();
						
						response = reader.readLine();
						
						if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_FILE_LEN))){
							//We need to have this type of construct to make sure we get the file created before we measure its size.
							System.out.println("file path:" + filePath);
							File f = new File(filePath);
							long l = f.length();
							
							while(l == 0){
								System.out.println("====>File len : " + l);
								l = f.length();
								
								try{
									Thread.currentThread().sleep(10);
								}catch(Exception ex){
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
				
					int counter = 0;
				
				    while(true){
						out.println(AcaciaInstanceProtocol.FILE_RECV_CHK);
						out.flush();
						response = reader.readLine();

						if((response != null)&&(response.equals(AcaciaInstanceProtocol.FILE_RECV_WAIT))){
							System.out.println("Checking file status : " + counter);
							counter++;
							
							try {
								Thread.currentThread().sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} //We sleep for 1 second, and try again.
							continue;
						}else{
							break;
						}
				    }
				    System.out.println("File transfer complete...");
				    
				    //Next we wait till the batch upload completes
				    while(true){
						out.println(AcaciaInstanceProtocol.BATCH_UPLOAD_CHK);
						out.flush();
						
						response = reader.readLine();

						if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_WAIT))){
							try {
								Thread.currentThread().sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} //We sleep for 1 second, and try again.
							continue;
						}else{
							break;
						}
				    }
				    
				    if((response != null)&&(response.equals(AcaciaInstanceProtocol.BATCH_UPLOAD_ACK))){
				    	System.out.println("Batch upload completed...");	
					}else{
						System.out.println("There was an error in the upload parocess.");
					}
			}
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
			result = false;
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
			result = false;
		}	
		
		return result;
	}
	
	public static boolean batchUploadRDFStore(String host, int port, long graphID, String filePath, int dataPort){
		boolean result = true;
		
		System.out.println(filePath);
		
		
		try{
			//System.out.println(">>>>> Connecting to host : " + host + " port : " + port);
			Socket socket = new Socket(host, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

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
			
			}catch(UnknownHostException e){
				Logger_Java.error(e.getMessage());
				result = false;
			}catch(IOException ec){
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
	public static boolean insertEdge(String host, long graphID, long startVertexID, long endVertexID){		
		//The manger should contatct the appropriate AcaciaInstance and insert the edges.
		try{
			System.out.println("Connecting to host : " + host + " at port : " + Conts_Java.ACACIA_INSTANCE_PORT);
			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

			System.out.println("OK Connected");
			
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
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
			return false;
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method is used to truncate a Acacia Local Instance.
	 * @param host
	 */
	public static void truncateLocalInstance(String host, int port){
		try{
			Socket socket = new Socket(host, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

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
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}		
	}
	
	public static boolean setDefaultGraph(String host, int graphID){
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
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
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}		
		
		return true;
	}
	
	public static boolean initializeGraphOnLocalInstance(String host, int graphID){
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
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
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
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
	public static long countVertices(String host, String graphID){
		long result = -1;
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
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
			
		}catch(UnknownHostException e){
			Logger_Java.error("Connecting to host (1) " + host + " got error message : " + e.getMessage());
		}catch(IOException ec){
			Logger_Java.error("Connecting to host (1) " + host + " got error message : " + ec.getMessage());
		}
		
		return -1;
	}
	
	public static long countVertices(String host, String graphID, String partitionID){
		long result = -1;
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
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
			
		}catch(UnknownHostException e){
			Logger_Java.error("Connecting to host (1) " + host + " got error message : " + e.getMessage());
		}catch(IOException ec){
			Logger_Java.error("Connecting to host (1) " + host + " got error message : " + ec.getMessage());
		}
		
		return -1;
	}
	
	public static long countVertices(String graphID){
		long result = Long.parseLong(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("SELECT VERTEXCOUNt FROM ACACIA_META.GRAPH WHERE IDGRAPH=" + graphID).value)[(int)0L]);
        return result;
	}
	
	public static long countEdges(String host, String graphID, String partitionID){
		long result = -1;
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
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
			
		}catch(UnknownHostException e){
			Logger_Java.error("Connecting to host (2) " + host + " got error message : " + e.getMessage());
		}catch(IOException ec){
			Logger_Java.error("Connecting to host (2) " + host + " got error message : " + ec.getMessage());
		}
		
		return -1;
	}
	
	/**
	 * This method is used to count the number of vertices available for a particular graph.
	 * @param host
	 * @deprecated: Sept 26 2014, use countEdges(graphID) instead
	 */
	public static long countEdges(String host, String graphID){
		long result = -1;
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
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
			
		}catch(UnknownHostException e){
			Logger_Java.error("Connecting to host (2) " + host + " got error message : " + e.getMessage());
		}catch(IOException ec){
			Logger_Java.error("Connecting to host (2) " + host + " got error message : " + ec.getMessage());
		}
		
		return -1;
	}
	
	public static long countEdges(String graphID){
		long result = Long.parseLong(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("SELECT EDGECOUNT FROM ACACIA_META.GRAPH WHERE IDGRAPH=" + graphID).value)[(int)0L]);
        return result;		
	}
	
	/**
	 * This method is used to count the number of vertices available for a particular graph.
	 * @param host
	 */
	public static String pageRank(String host, String graphID, String hostList){
		String result = null;
		
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
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
		}catch(UnknownHostException e){
			Logger_Java.error("Connecting to host (3) " + host + " got error message : " + e.getMessage());
		}catch(IOException ec){
			Logger_Java.error("Connecting to host (3) " + host + " got error message : " + ec.getMessage());
		}
		
		return "-1";
	}	

	public static String pageRankTopK(String host, String graphID, String partitionID, String hostList, String k){
		String result = null;
		
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
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
		}catch(UnknownHostException e){
			Logger_Java.error("Connecting to host (4) " + host + " got error message : " + e.getMessage());
		}catch(IOException ec){
			Logger_Java.error("Connecting to host (4) " + host + " got error message : " + ec.getMessage());
		}
		
		return "-1";		
	}
	
	public static long countGlobalTraingles(String graphID){
		long result = -1;
		long startVid = -1;
		long endVid = -1;
	    int centralPartionCount = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(int)0L]);
	    //int startPartitionID = Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("SELECT IDPARTITION FROM ACACIA_META.PARTITION WHERE GRAPH_IDGRAPH=" + graphID + " ORDER BY IDPARTITION LIMIT 1").value)[(int)0L]);//SELECT IDPARTITION FROM "ACACIA_META"."PARTITION" WHERE GRAPH_IDGRAPH=212 ORDER BY IDPARTITION LIMIT 1
	    HashMap<Long, Long> edgeList = new HashMap<Long, Long>();
	    HashMap<Long, TreeSet<Long>> localSubGraphMap = new HashMap<Long, TreeSet<Long>>(); 
	    TreeSet<Long> vals = new TreeSet<Long>();
	    //StringBuilder sbPersist = new StringBuilder();
	    
	    System.out.println("Started constructing graph");
	    //System.out.println("Start partition ID : " + startPartitionID);
	    for(int i = 0; i < centralPartionCount; i++){
	    	System.out.println("Partition id : " + i);
	    	
//	    	AcaciaHashMapCentralStore store = new AcaciaHashMapCentralStore(Integer.parseInt(graphID), i);
//	    	store.loadGraph();
	    	
	    	AcaciaLocalStore store = AcaciaLocalStoreFactory.load(Integer.parseInt(graphID), i, Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator + graphID + "_centralstore", true);
	    	store.loadGraph();
	    	
	    	HashMap<Long, HashSet<Long>> edgeList2 = (HashMap<Long, HashSet<Long>>)store.getUnderlyingHashMap();
	    	Iterator<Map.Entry<Long, HashSet<Long>>> itr = edgeList2.entrySet().iterator();
	    	long firstVertex = 0l;
	    	
	    	while(itr.hasNext()){
	    		Map.Entry<Long, HashSet<Long>> entr = itr.next();
	    		firstVertex = entr.getKey();
	    		TreeSet<Long> hs = (TreeSet<Long>)localSubGraphMap.get(firstVertex);
	    		
	    		if(hs == null){
	    			hs = new TreeSet<Long>();
	    		}
	    		
	    		HashSet<Long> hs2 = entr.getValue();
	    		
	    		for(long secondVertex: hs2){
	    			hs.add(secondVertex);
	    		}
	    		
	    		localSubGraphMap.put(firstVertex, hs);
	    	}
	    	
	    }
	    
	    System.out.println("Done constructing graph");
	    
		TreeMap<Long, TreeSet<Long>> degreeMap = new TreeMap<Long, TreeSet<Long>>();
		HashMap<Long, Long> degreeReverseLookupMap = new HashMap<Long, Long>();
		
		long degree = -1;
		startVid = -1;
		TreeSet<Long> degreeSet = null;
		for(Entry<Long, TreeSet<Long>> item: localSubGraphMap.entrySet()){
			startVid = item.getKey();
			degree = item.getValue().size();
			
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
		
		System.out.println("Completed degree reverse lookup map construction");
		//We need to create the degree map as well.
		
		//System.out.println("Started counting algorithm at : " + getCurrentTimeStamp());
		//Now we start count the traingles. But this happens only in the local graph.
		//This might be a naive method of doing this. Good for the moment... Sept 30 2014
		long traingleCount = 0;
		long v1 = 0;
		long v2 = 0;
		long v3 = 0;
//		long fullCount = 0;
		StringBuilder sb2 = new StringBuilder();
		HashMap<Long, HashMap<Long, ArrayList<Long>>> traingleTree = new HashMap<Long, HashMap<Long, ArrayList<Long>>>(); 
		ArrayList<Long> degreeListVisited = new ArrayList<Long>();
		
		for(Map.Entry<Long, TreeSet<Long>> item: degreeMap.entrySet()){
			//System.out.println("Working on Degree : " + item.getKey());
			TreeSet<Long> vVertices = item.getValue();
			
			//When one of these rounds completes we know that vertex vs has been completely explored for its triangles. Since we treat
			//a collection of vertices having the same degree at a time, we can do a degree based optimization. That is after this for loop
			//we can mark the degree as visited. Then for each second and third loops can be skipped if the vertices considered there are
			//having a degree that was marked before. Because we know that we have already visited all the vertices of that degree before.
			for(Long v : vVertices){
				TreeSet<Long> uList = localSubGraphMap.get(v); //We know for sure that v has not been visited yet
				if(uList != null){ //Because in local subgraph map we may mark only u -> v, but v may not have corresponding record in the map (i.e., v -> u).
					for(long u : uList){//second for loop
//						if(degreeReverseLookupMap.containsKey(u)){
//							degree = degreeReverseLookupMap.get(u);
//							if(degreeListVisited.contains(degree)){
//								//There is no point of considering the vertices of this degree. We have already counted triangles for all the
//								//vertices with this degree
//								continue;
//							}
//						}
						
						TreeSet<Long> nuList = localSubGraphMap.get(u);
						if(nuList != null){
							for(long nu : nuList){ //Third for loop
//								if(degreeReverseLookupMap.containsKey(nu)){
//									long degree2 = degreeReverseLookupMap.get(nu);
//									if(degreeListVisited.contains(degree2)){
//										//There is no point of considering the vertices of this degree. We have already counted triangles for all the
//										//vertices with this degree
//										continue;
//									}
//								}
								TreeSet<Long> nwList = localSubGraphMap.get(nu);

								if((nwList != null) && (nwList.contains(v))){ //We know for sure that v has not been visited yet
									//fullCount++;
									
									
									//At this point we have discovered a triangle. But now we need to makesure that we have not counted that triangle before.
									//To do that we use the triangle tree data structure which keeps on track of the triangles we have marked so far.
									//Note that the triangle tree may not be as efficient as we expect.
									
									//Here we have to be careful to first sort the three triangle vertices by their value. Then we do not
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
												
												traingleCount++;
//												sb2.append("" + v1 + " " + v2 + " " + v3 + "\n");
											}else{
												//We have discovered this triangle before. It is already recorded in the tree.
											}
										}else{
											//This means that the infrmation of the second vertex is still not added to the traingle tree.
											//This is a fresh round of accounting for v2.
											ArrayList<Long> newU = new ArrayList<Long>();
											newU.add(v3);
											itemRes.put(v2, newU);
											traingleTree.put(v1, itemRes);//here its just replacing the old itemRes
											
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
			degreeMap.put(item.getKey(), null); //Release the memory for this Key's array list. This way we will get more memory to run the algorithm.
		}
		result = traingleCount;
		
		return result;
	}
	
	public static long countTraingles(String host, int port, String graphID, String partitionID){
		long result = -1;
		
		try{
			System.out.println("host : " + host + " port : " + port);
			Socket socket = new Socket(host, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
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
			System.out.println("For host " + host + " Triangle result : " + result);
			out.close();
			
			return result;			
		}catch(UnknownHostException e){
			Logger_Java.error("Connecting to host (5) " + host + " got error message : " + e.getMessage());
		}catch(IOException ec){
			Logger_Java.error("Connecting to host (5) " + host + " got error message : " + ec.getMessage());
		}
		
		return -1;
	}
	
	public static String getFreeSpaceInfo(String host){
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_INSTANCE_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
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
		}catch(UnknownHostException e){
			Logger_Java.error("Connecting to host (6) " + host + " got error message : " + e.getMessage());
		}catch(IOException ec){
			Logger_Java.error("Connecting to host (6) " + host + " got error message : " + ec.getMessage());
		}
		
		return null;
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
	public static x10.core.Rail<java.lang.String> runSPARQL(String host, int port, String graphID, String partitionID, String query, long placeID, String placesDetails) throws UnknownHostException, IOException{
		x10.util.ArrayList<java.lang.String> result = new x10.util.ArrayList<java.lang.String>((java.lang.System[]) null, x10.rtt.Types.STRING).x10$util$ArrayList$$init$S();
		
		Socket socket = new Socket(host, port);
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String response = "";		
		int i=0;
		
		out.println(AcaciaInstanceProtocol.HANDSHAKE);
		out.flush();
		response = reader.readLine();
		
		System.out.println("resp1:" + response);
		
		if((response != null)&&(response.equals(AcaciaInstanceProtocol.HANDSHAKE_OK))){
			System.out.println("ccc");
			out.println(java.net.InetAddress.getLocalHost().getHostName());
			out.flush();
			
		out.println(AcaciaInstanceProtocol.EXECUTE_QUERY);
		out.flush();
		response = reader.readLine();
		System.out.println("ccc:"+response);
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_QUERY))){
				System.out.println("|" + query + "|");
				out.println(query);
				out.flush();
				response = reader.readLine();
				System.out.println("dddd:"+response);
				
				if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_GID))){
					System.out.println("dend-gid:"+graphID);
					out.println(graphID);
					out.flush();
					response = reader.readLine();
					System.out.println("eee:"+response);
						if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PARTITION_ID))){							
							System.out.println("partID:"+partitionID);
							out.println(partitionID);
							out.flush();
							response = reader.readLine();
							
							if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PLACEID))){		
								out.println(placeID);
								out.flush();
								response = reader.readLine();
								System.out.println("bbbbb1:"+response);
								
								if((response != null)&&(response.equals(AcaciaInstanceProtocol.SEND_PLACEDETAILS))){		
									out.println(placesDetails);
									out.flush();
									response = reader.readLine();
									System.out.println("bbbbb2:"+response);
							if(response.equals("Not empty")){
								
								out.println("Send");
								out.flush();
								
								response = reader.readLine();
								
								while(!response.equals("Finish")){
									result.add__0x10$util$ArrayList$$T$O(((java.lang.String)response));
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
		x10.core.Rail<java.lang.String> arr = null;
		if(result != null){
			arr = ((x10.util.ArrayList<java.lang.String>)result).toRail();
		}
		return arr;
	}
	
	/**
	 * This method is used to remove the vertices of a particular graph.
	 * @param host
	 */
	public static long removeVertices(String host, int port, String graphID, String partitionID){
		boolean result = true;
		
		try{
			Socket socket = new Socket(host, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";
			
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
			
		}catch(UnknownHostException e){
			Logger_Java.error("Connecting to host (7) " + host + " got error message : " + e.getMessage());
		}catch(IOException ec){
			Logger_Java.error("Connecting to host (7) " + host + " got error message : " + ec.getMessage());
		}
		
		return -1;
	}

	public static boolean downloadCentralStore(int graphID, int i) {
		boolean result = true;
		
		//First need to find where the central store partition is located.
		String host = null;
		int port = 0;
		
		try{
			Socket socket = new Socket(host, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

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
		}catch(UnknownHostException e){
			Logger_Java.error("Connecting to host (8) " + host + " got error message : " + e.getMessage());
		}catch(IOException ec){
			Logger_Java.error("Connecting to host (8) " + host + " got error message : " + ec.getMessage());
		}
		
		return result;
	}
}