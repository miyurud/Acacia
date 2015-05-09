package org.acacia.frontend;

import x10.util.StringBuilder;
import x10.util.ArrayList;

import x10.regionarray.Array;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;

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
	
    public def run(){
    	try{
    		Logger_Java.info("Starting the frontend");
    		srv = new ServerSocket(Conts_Java.ACACIA_FRONTEND_PORT);
    		Logger_Java.info("Done creating frontend");
    		Logger_Java.info("Place count : "+Place.places().size);
    		
    		//finish{
	    		while(runFlag){
	    			var socket:Socket = srv.accept();
	    			val skt = socket;
	                val session:AcaciaFrontEndServiceSession = new AcaciaFrontEndServiceSession(skt);
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
    		Logger_Java.error("Error : " + e.getMessage());
    	} catch (var e:IOException) {
    		Logger_Java.error("Error : " + e.getMessage());
    	}
    }
    
}