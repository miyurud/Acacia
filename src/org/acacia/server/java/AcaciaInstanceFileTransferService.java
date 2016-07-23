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

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.acacia.log.java.Logger_Java;
import org.acacia.server.java.AcaciaInstanceFileTransferServiceSession;

public class AcaciaInstanceFileTransferService{
	private ServerSocket srv;
	private boolean runFlag = true;
	private int port;
	
	public AcaciaInstanceFileTransferService(int p){
        port = p;
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

