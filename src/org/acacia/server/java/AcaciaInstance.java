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

package org.acacia.server.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.acacia.events.java.DBTruncateEvent;
import org.acacia.events.java.DBTruncateEventListener;
import org.acacia.events.java.ShutdownEvent;
import org.acacia.events.java.ShutdownEventListener;
import org.acacia.localstore.java.AcaciaHashMapLocalStore;
import org.acacia.localstore.java.AcaciaLocalStore;
import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;

public class AcaciaInstance{
	//private GraphDatabaseService graphDB;
	private HashMap<String, AcaciaLocalStore> graphDBMap = new HashMap<String, AcaciaLocalStore>();//This HashMap holds the AcaciaInstance objects. 
	private ServerSocket srv;
	private ArrayList<AcaciaInstanceServiceSession> sessions;
	private boolean runFlag = true;
	private ArrayList<String> loadedGraphs = new ArrayList<String>(); 
	private int port;
	
	public AcaciaInstance(){
		sessions = new ArrayList<AcaciaInstanceServiceSession>();
		port = Integer.parseInt(System.getProperty("ACACIA_INSTANCE_PORT"));
	}
	
//	public void loadNeo4j(String graphID) throws UnknownHostException{
//		//System.out.println("------------ Running from BBBBBBBBBBBBBBBBBBBBBBB --------");
//		Logger_Java.info("Running local server at " + java.net.InetAddress.getLocalHost().getHostName());		
//		String instanceDataFolderLocation = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + "/" + graphID;
//		Logger_Java.info("instanceDataFolderLocation : " + instanceDataFolderLocation);
//		
//		File f = new File(instanceDataFolderLocation);
//		
//		if(!f.isDirectory()){
//			f.mkdir();//If the graph db folder does not exists we need to create it
//		}
//		
//		Logger_Java.info("Creating cache provider");
//        //the cache providers
//        ArrayList<CacheProvider> cacheList = new ArrayList<CacheProvider>();
//        cacheList.add( new SoftCacheProvider() );
//        Logger_Java.info("Done cache provider");
//        //the kernel extensions
////        LuceneKernelExtensionFactory lucene = new LuceneKernelExtensionFactory();
////        List<KernelExtensionFactory<?>> extensions = new ArrayList<KernelExtensionFactory<?>>();
////        extensions.add( lucene );
//		
//        
//        GraphDatabaseFactory gdbf = new GraphDatabaseFactory();
//        Logger_Java.info("Done gdb factory creation");
//        //gdbf.setKernelExtensions( extensions );
//        gdbf.setCacheProviders( cacheList );
//        Logger_Java.info("Added provider to list");
//        GraphDatabaseService graphDB = gdbf.newEmbeddedDatabase(instanceDataFolderLocation);
//		IndexManager index = graphDB.index();
//		Index<Node> vertices = index.forNodes("vertexids");
//		//graphDB = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(instanceDataFolderLocation).loadPropertiesFromFile("conf/neo4j.properties").newGraphDatabase();
//		graphDBMap.put(graphID, graphDB);
//		loadedGraphs.add(graphID);
//		Logger_Java.info("Loaded the graph " + graphID + " at " + java.net.InetAddress.getLocalHost().getHostName());
//		//System.out.println("------------ Done Running from BBBBBBBBBBBBBBBBBBBBBBB --------");
//	}
	
	public void start_running() throws UnknownHostException{	
		//------------------------------------------------------------------------------------------
		try{
			//Logger_Java.info("######>>>>***** -> Starting the server at : " + org.acacia.util.java.Utils_Java.getHostName());
			srv = new ServerSocket(port);
			Logger_Java.info("Done creating server");
			
			int connectionCounter = 0;
			
			while(runFlag){
				Socket socket = srv.accept();
				//Logger_Java.info("===========================>> Got a connection. Now serving...");
				AcaciaInstanceServiceSession session = new AcaciaInstanceServiceSession(socket, graphDBMap, loadedGraphs);
				session.addDBTruncateEventListener(new AcaciaDBTruncateEventListener(this));
				//session.addShutdownEventListener(new AcaciaShutdownEventListener(this));
				session.start();
				sessions.add(session);
				connectionCounter++;
				//Logger_Java.info("+++++++++++++++++++++++++++++ >> Connection count : " + connectionCounter);
				//This is something fancy we should do later in future.
				//session.addShutdownEventListener(new InstanceShutdownEventListener(this));
			}
		}catch(BindException e){
			Logger_Java.error("Error : " + e.getMessage());
		} catch (IOException e) {
			Logger_Java.error("Error : " + e.getMessage());
		}
		
		Logger_Java.info("XXXXXXXXXXXXXXXXX> Exitting the AcaciaInstance server at " + org.acacia.util.java.Utils_Java.getHostName() + " port : " + port);
	}
	
    private static void registerShutdownHook(final AcaciaHashMapLocalStore graphDb){
    	Runtime.getRuntime().addShutdownHook(new Thread() {
    		@Override
    		public void run(){
    			graphDb.shutdown();
    		}
    	});    	
    }

	public void truncate() {
		String acaciaDataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
		Iterator<String> itr2 = loadedGraphs.iterator();
		
		while(itr2.hasNext()){
			String itm = itr2.next();
			//These types of places (such as "" + itm) need to be reviewed and decide whether  
			AcaciaLocalStore db = graphDBMap.get(itm);
			//We need to shutdown the online graph db instance
			System.out.println("Shutting down graph id : " + itm);
			db.storeGraph();
		}
		
		System.out.println("Done shutting down the hot Neo4j instances...");
			
		try{
			Logger_Java.info("The data folder is : " + acaciaDataFolder);
			FileUtils.deleteDirectory(new File(acaciaDataFolder));
			Logger_Java.info("Done deleting : " + acaciaDataFolder);
				
			graphDBMap = new HashMap<String, AcaciaLocalStore>();
			loadedGraphs = new ArrayList<String>(); 
			
			//Next we need to distribute the new graphDB object reference to all the xisting sessions. Because they are still using the old shutdowned session.
			Iterator<AcaciaInstanceServiceSession> itr = sessions.iterator();
			while(itr.hasNext()){
				AcaciaInstanceServiceSession obj = (AcaciaInstanceServiceSession)itr.next();
				obj.setGraphDBMap(graphDBMap, loadedGraphs);
			}
		}catch(UnknownHostException e){
			e.printStackTrace();
		}catch(IOException ec){
			ec.printStackTrace();
		}
	}

	public void shutdown() {
		System.out.println("Acacia instance shuttingdown.");
		//First we need to shutdown the loade graph dbs
		Iterator<String> itr2 = loadedGraphs.iterator();
		
		while(itr2.hasNext()){
			String itm = itr2.next();
			AcaciaLocalStore db = graphDBMap.get(itm);
			//We need to shutdown the online graph db instance
			System.out.println("Shutting down graph id : " + itm);
			db.storeGraph();
		}
		
		runFlag = false;
		
		System.out.println("-----1------");
		try{
			Socket socket = new Socket("127.0.0.1", Conts_Java.ACACIA_INSTANCE_PORT);
			System.out.println("-----2------");
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			System.out.println("-----3------");
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("-----4------");
			String response = "";
			System.out.println("-----5------");

			out.println(AcaciaInstanceProtocol.CLOSE);
			System.out.println("-----6------");
			out.flush();
			System.out.println("-----7------");

			response = reader.readLine();
			
			System.out.println("-----8------");
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.CLOSE_ACK))){
				Logger_Java.info("Connection closed.");
				out.close();
			}
			
			System.out.println("-----9------");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

class AcaciaDBTruncateEventListener implements DBTruncateEventListener{
	private AcaciaInstance instance;
	
	public AcaciaDBTruncateEventListener(AcaciaInstance inst){
		this.instance = inst;
	}	
	
	public void truncateEventOccurred(DBTruncateEvent evt){
		instance.truncate();
	}
	
}

class AcaciaShutdownEventListener implements ShutdownEventListener{
	private AcaciaInstance instance;
	
	public AcaciaShutdownEventListener(AcaciaInstance inst){
		this.instance = inst;
	}

	public void shutdownEventOccurred(ShutdownEvent evt) {
		instance.shutdown();
	}
	
}

