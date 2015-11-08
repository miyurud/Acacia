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

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import x10.util.ArrayList;
import org.acacia.util.Conts;
import org.acacia.log.Logger;


/**
 * The Acacia back-end.
 */
public class AcaciaBackEnd {
	private var runFlag:Boolean = true;
	private var srv:ServerSocket;
	private var sessions:ArrayList[AcaciaBackEndServiceSession] = new ArrayList[AcaciaBackEndServiceSession]();
	
    public static def main(val args:Rail[String]) {
		var backend:AcaciaBackEnd = new AcaciaBackEnd();
		backend.run();
	}
	
    public def run():void{
    	try{
    		//Logger_Java.info("Starting the backend");
    		srv = new ServerSocket(Conts.ACACIA_BACKEND_PORT);
    		//Logger_Java.info("Done creating backend2222");

    		while(runFlag){
    				//Logger_Java.info("AAAAAAAAAAAAAA8");
	                var socket:Socket = srv.accept();
	                //Logger_Java.info("AAAAAAAAAAAAAA82");
	                var session:AcaciaBackEndServiceSession = new AcaciaBackEndServiceSession(socket);
	                //Logger_Java.info("AAAAAAAAAAAAAA833");
	                sessions.add(session);
	                //Logger_Java.info("AAAAAAAAAAAAAA84");
    				session.start();
    				//Logger_Java.info("AAAAAAAAAAAAAA866");
	    	}
    		
    		// while(runFlag){
    		// 	var socket:Socket = srv.accept();
    		// 	val skt = socket;
    		// 	val session:AcaciaFrontEndServiceSession = new AcaciaFrontEndServiceSession(skt);
    		// 	sessions.add(session);
    		// 	session.start();
    		// }
    		//Logger_Java.info("Exitting from backend...");
    	}catch(var e:BindException){
    		Logger.error("Error : " + e.getMessage());
    	} catch (var e2:IOException) {
    		Logger.error("Error : " + e2.getMessage());
    	}
    }
    
}
