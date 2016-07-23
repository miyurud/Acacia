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

package org.acacia.frontend;

import x10.util.StringBuilder;
import x10.util.ArrayList;

import x10.regionarray.Array;

import org.acacia.log.Logger;
import org.acacia.util.Conts;
import org.acacia.util.Utils;

import java.io.IOException;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * The Acacia front-end.
 */
public class AcaciaFrontEnd {
	private var runFlag:Boolean = true;
	private var srv:ServerSocket;
	private var sessions:ArrayList[AcaciaFrontEndServiceSession] = new ArrayList[AcaciaFrontEndServiceSession]();
	
public static def main(val args:Rail[String]) {
		var frontend:AcaciaFrontEnd = new AcaciaFrontEnd();
		frontend.run();
	}    

    public def run(){
    	try{
    		Logger.info("Starting the frontend");
    		srv = new ServerSocket(Conts.ACACIA_FRONTEND_PORT);
    		Logger.info("Done creating frontend");
    		val pg = Place.places();
    		//finish{
	    		while(runFlag){
	    			var socket:Socket = srv.accept();
	    			val skt = socket;
	                val session:AcaciaFrontEndServiceSession = new AcaciaFrontEndServiceSession(skt,pg);
	                sessions.add(session);
	    			//async{
	                    //Console.OUT.println("CCCCC");
	    				session.run();
	                    //Console.OUT.println("EEEEE");
	    			//}
	    		}
    		//}
    		
    		// while(runFlag){
    		// 	var socket:Socket = srv.accept();
    		// 	val skt = socket;
    		// 	val session:AcaciaFrontEndServiceSession = new AcaciaFrontEndServiceSession(skt);
    		// 	sessions.add(session);
    		// 	session.start();
    		// }
    		
    	}catch(var e:BindException){
    		Logger.error("Error : " + e.getMessage());
    	} catch (var e:IOException) {
    		Logger.error("Error : " + e.getMessage());
    	}
    }
    
}