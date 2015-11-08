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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import x10.lang.Iterator;
import x10.util.HashMap;
import x10.util.ArrayList;

import org.apache.commons.io.FileUtils;

import org.acacia.events.java.DBTruncateEvent;
import org.acacia.events.java.DBTruncateEventListener;
import org.acacia.events.java.ShutdownEvent;
import org.acacia.events.java.ShutdownEventListener;
import org.acacia.localstore.AcaciaHashMapLocalStore;
import org.acacia.localstore.AcaciaLocalStore;
import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;
import org.acacia.server.java.AcaciaInstanceProtocol;

public class AcaciaInstance{
	//private GraphDatabaseService graphDB;
	private var graphDBMap:HashMap[String, AcaciaLocalStore] = new HashMap[String, AcaciaLocalStore]();//This HashMap holds the AcaciaInstance objects. 
	private var srv:ServerSocket;
	private var sessions:ArrayList[AcaciaInstanceServiceSession];
	private var runFlag:Boolean = true;
	private var loadedGraphs:ArrayList[String] = new ArrayList[String](); 
	private var port:Int;
	
	public def this(){
		sessions = new ArrayList[AcaciaInstanceServiceSession]();
		port = Int.parseInt(java.lang.System.getProperty("ACACIA_INSTANCE_PORT"));
	}
		
	public def start_running() : void {	
		//------------------------------------------------------------------------------------------
		try{
			//Logger_Java.info("######>>>>***** -> Starting the server at : " + org.acacia.util.java.Utils_Java.getHostName());
			srv = new ServerSocket(port);
			Logger_Java.info("Done creating server");
			
			var connectionCounter:Int  = 0n;
			
			while(runFlag){
				var socket:Socket = srv.accept();
				var session:AcaciaInstanceServiceSession = new AcaciaInstanceServiceSession(socket, graphDBMap, loadedGraphs);
				session.addDBTruncateEventListener(new AcaciaDBTruncateEventListener(this));
				//session.addShutdownEventListener(new AcaciaShutdownEventListener(this));
				session.start();
				sessions.add(session);
				connectionCounter++;
				//Logger_Java.info("+++++++++++++++++++++++++++++ >> Connection count : " + connectionCounter);
				//This is something fancy we should do later in future.
				//session.addShutdownEventListener(new InstanceShutdownEventListener(this));
			}
		}catch(val e:BindException){
			Logger_Java.error("Error : " + e.getMessage());
		} catch (val e:IOException) {
			Logger_Java.error("Error : " + e.getMessage());
		}
		
		Logger_Java.info("XXXXXXXXXXXXXXXXX> Exitting the AcaciaInstance server at " + org.acacia.util.java.Utils_Java.getHostName() + " port : " + port);
	}
	
    private static def registerShutdownHook(val graphDb:AcaciaHashMapLocalStore) : void {
    	java.lang.Runtime.getRuntime().addShutdownHook(new java.lang.Thread() {
    		public def run():void{
    			graphDb.shutdown();
    		}
    	});    	
    }

	public def truncate() : void {
		val acaciaDataFolder:String = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
		val itr2:Iterator[String] = loadedGraphs.iterator();
		
		while(itr2.hasNext()){
			val itm:String = itr2.next() as String;
			//These types of places (such as "" + itm) need to be reviewed and decide whether  
			val db:AcaciaLocalStore = graphDBMap.get(itm) as AcaciaLocalStore;
			//We need to shutdown the online graph db instance
			Console.OUT.println("Shutting down graph id : " + itm);
			db.storeGraph();
		}
		
        Console.OUT.println("Done shutting down the hot Neo4j instances...");
			
		try{
			Logger_Java.info("The data folder is : " + acaciaDataFolder);
			FileUtils.deleteDirectory(new File(acaciaDataFolder));
			Logger_Java.info("Done deleting : " + acaciaDataFolder);
				
			graphDBMap = new HashMap[String, AcaciaLocalStore]();
			loadedGraphs = new ArrayList[String](); 
			
			//Next we need to distribute the new graphDB object reference to all the xisting sessions. Because they are still using the old shutdowned session.
			val itr:Iterator[AcaciaInstanceServiceSession] = sessions.iterator();
			while(itr.hasNext()){
				val obj:AcaciaInstanceServiceSession = itr.next() as AcaciaInstanceServiceSession;
				obj.setGraphDBMap(graphDBMap, loadedGraphs);
			}
		}catch(val e:UnknownHostException){
			e.printStackTrace();
		}catch(val ec:IOException){
			ec.printStackTrace();
		}
	}

	public def shutdown() : void {
        Console.OUT.println("Acacia instance shuttingdown.");
		//First we need to shutdown the loade graph dbs
		val itr2:Iterator[String] = loadedGraphs.iterator();
		
		while(itr2.hasNext()){
			val itm:String = itr2.next() as String;
			val db:AcaciaLocalStore = graphDBMap.get(itm) as AcaciaLocalStore;
			//We need to shutdown the online graph db instance
			Console.OUT.println("Shutting down graph id : " + itm);
			db.storeGraph();
		}
		
		runFlag = false;
		
		try{
			val socket:Socket = new Socket("127.0.0.1", Conts_Java.ACACIA_INSTANCE_PORT);
			val out:PrintWriter = new PrintWriter(socket.getOutputStream());
			val reader:BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			var response:String = "";

			out.println(AcaciaInstanceProtocol.CLOSE);
			out.flush();

			response = reader.readLine();
			
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.CLOSE_ACK))){
				Logger_Java.info("Connection closed.");
				out.close();
			}
        }catch(var e1:java.net.UnknownHostException){
        	e1.printStackTrace();
        }catch(var e2:java.io.IOException){
            e2.printStackTrace();
		}catch(var e:Exception){
			e.printStackTrace();
		}
	}
}

class AcaciaDBTruncateEventListener implements DBTruncateEventListener{
	private var instance:AcaciaInstance;
	
	public def this(var inst:AcaciaInstance){
		this.instance = inst;
	}
	
	public def truncateEventOccurred(var evt:DBTruncateEvent):void{
		instance.truncate();
	}
}

class AcaciaShutdownEventListener implements ShutdownEventListener{
	private var instance:AcaciaInstance;
	
	public def this(var inst:AcaciaInstance){
		this.instance = inst;
	}

	public def shutdownEventOccurred(var evt:ShutdownEvent) : void{
		instance.shutdown();
	}
}

