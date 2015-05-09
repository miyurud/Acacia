package org.acacia.vertexcounter;

import java.net.Socket;
import java.net.ServerSocket;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;

import org.acacia.log.java.Logger_Java;

/**
 * This class implements a single vertex counter request service session.
 */
public class VertexCounterServiceSession extends Thread{
	private Socket sessionSkt = null;
	private VertexSingleton obj = null;
	
	public VertexCounterServiceSession(Socket socket, VertexSingleton singletonObj){
		//System.out.println("AAAAAAAAAA");
		sessionSkt = socket;
		obj = singletonObj;
		//obj.printContent();
	}
	
	public void run(){
		//System.out.println("BBBBBB");
		try{
			//System.out.println("BBBBBB1");
			BufferedReader buff = new BufferedReader(new InputStreamReader(sessionSkt.getInputStream()));
			//System.out.println("BBBBBB2");
			PrintWriter out = new PrintWriter(sessionSkt.getOutputStream());
			//System.out.println("BBBBBB3");
			String msg = null;
			//System.out.println("BBBBBB4");
			while((msg = buff.readLine())!= null){
				//System.out.println("BBBBBB5");
				if(msg.equals(VertexCounterProtocol.EXIT)){
					out.println(VertexCounterProtocol.EXIT_ACK);
					out.flush();
					sessionSkt.close();
					break;
				}else{
					//out.println(msg);
					//out.flush();
					process(msg, buff, out);
				}
				//System.out.println("BBBBBB6");
			}
			//System.out.println("BBBBBB7777777->>exit");
			
			out.println(VertexCounterProtocol.EXIT_ACK);
			out.flush();
			sessionSkt.close();
			
		}catch(IOException e){
			Logger_Java.error("Error : " + e.getMessage());
		}
		//System.out.println("BBBBBB888888->>exit");
	}
	
	/**
	 * This method processes the query requests to AcaciaForntEnd. This is the main function that answers the queries.
	 */
	public void process(String msg, BufferedReader buff, PrintWriter out){
		String response="";
		String str = null;
		
		//System.out.println("msg is : " + msg);
		
		if(msg.equals(VertexCounterProtocol.TICKET)){//List the graphs on Acacia
			out.println(VertexCounterProtocol.SEND);
			out.flush();
			
			try{
				str = buff.readLine();
			}catch(IOException e){
				Logger_Java.error("Error : " + e.getMessage());
			}
			
			long r = obj.getNextVertexID(Long.parseLong(str));
			
			if(r != -1){
				response = "" + r;
			}else{
				response = VertexCounterProtocol.ERROR + ":The specified graph id does not exist";
			}
			out.println(response);
			out.flush();
		}else if(msg.equals(VertexCounterProtocol.DEFAULT_GRAPH)){
			out.println(VertexCounterProtocol.SEND);
			out.flush();
			
			try{
				str = buff.readLine();
			}catch(IOException e){
				Logger_Java.error("Error : " + e.getMessage());
			}
			
			obj.setDefaultGraphID(Long.parseLong(str));
			
			out.println(VertexCounterProtocol.DONE);
			out.flush();
		}else if(msg.equals(VertexCounterProtocol.SAVE)){
			if(obj.save()){
				out.println(""+VertexCounterProtocol.DONE);
				out.flush();
			}else{
				out.println(""+VertexCounterProtocol.ERROR + ":could not complete saving the vertex count values.");
				out.flush();
			}
		}else if(msg.equals(VertexCounterProtocol.LOAD)){
			obj.load();
			out.println(""+VertexCounterProtocol.DONE);
			out.flush();
		}else{
			//This is the default response
			out.println(VertexCounterProtocol.SEND);
			out.flush();
		}
	}
}