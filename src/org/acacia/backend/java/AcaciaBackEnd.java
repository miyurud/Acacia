package org.acacia.backend;

//import x10.util.StringBuilder;
//import x10.util.ArrayList;
//
//import x10.regionarray.Array;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 * The Acacia back-end.
 */
public class AcaciaBackEnd {
	private boolean runFlag = true;
	private ServerSocket srv;
	private ArrayList<AcaciaBackEndServiceSession> sessions = new ArrayList<AcaciaBackEndServiceSession>();
	
    public void run(){
    	try{
    		Logger_Java.info("Starting the backend");
    		srv = new ServerSocket(Conts_Java.ACACIA_BACKEND_PORT);
    		Logger_Java.info("Done creating backend");

    		while(runFlag){
	                Socket socket = srv.accept();
	                AcaciaBackEndServiceSession session = new AcaciaBackEndServiceSession(socket);
	                sessions.add(session);
    				session.start();
	    	}
    		
    		// while(runFlag){
    		// 	var socket:Socket = srv.accept();
    		// 	val skt = socket;
    		// 	val session:AcaciaFrontEndServiceSession = new AcaciaFrontEndServiceSession(skt);
    		// 	sessions.add(session);
    		// 	session.start();
    		// }
    		
    	}catch(BindException e){
    		Logger_Java.error("Error : " + e.getMessage());
    	} catch (IOException e) {
    		Logger_Java.error("Error : " + e.getMessage());
    	}
    }
    
}