package org.acacia.server;

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

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.kernel.*;
import org.neo4j.kernel.impl.cache.CacheProvider;
import org.neo4j.kernel.impl.cache.SoftCacheProvider;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.Node;
import org.apache.commons.io.FileUtils;
import org.acacia.events.java.DBTruncateEvent;
import org.acacia.events.java.DBTruncateEventListener;
import org.acacia.events.java.ShutdownEvent;
import org.acacia.events.java.ShutdownEventListener;
import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;

public class AcaciaInstanceFileTransferService{
	private ServerSocket srv;
	private boolean runFlag = true;
	private int port;
	
	public AcaciaInstanceFileTransferService(){
		port = Integer.parseInt(System.getProperty("ACACIA_INSTANCE_DATA_PORT"));
	}
	
	public void start_running() throws UnknownHostException{
		try{
//			Logger_Java.info("Starting the file transfer service.");
			srv = new ServerSocket(port);
//			Logger_Java.info("Done creating the file transfer service.");
			
			while(runFlag){
				Socket socket = srv.accept();
				Logger_Java.info("Got a connection. Now serving...");
				AcaciaInstanceFileTransferServiceSession session = new AcaciaInstanceFileTransferServiceSession(socket);
				//session.addShutdownEventListener(new AcaciaShutdownEventListener(this));
				session.start();
				//This is something fancy we should do later in future.
				//session.addShutdownEventListener(new InstanceShutdownEventListener(this));
			}
		}catch(BindException e){
			Logger_Java.error("Error : " + e.getMessage());
		} catch (IOException e) {
			Logger_Java.error("Error : " + e.getMessage());
		}
		
		System.out.println("Exitting the AcaciaInstance server.");
	}

	public void shutdown() {
		System.out.println("Acacia instance shuttingdown.");
	}
}

//class AcaciaShutdownEventListener implements ShutdownEventListener{
//	private AcaciaInstanceFileTransferService instance;
//	
//	public AcaciaShutdownEventListener(AcaciaInstanceFileTransferService inst){
//		this.instance = inst;
//	}
//
//	public void shutdownEventOccurred(ShutdownEvent evt) {
//		instance.shutdown();
//	}
//	
//}

