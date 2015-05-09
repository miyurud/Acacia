package org.acacia.vertexcounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.acacia.log.java.Logger_Java;
import org.acacia.server.AcaciaInstanceProtocol;
import org.acacia.util.java.Conts_Java;

public class VertexCounterClient{
	/**
	 * This method loads the vertex count information.
	 */
	public static void loadVertexCountInfo(){
		final String host= null; //By putting null, we makesure that the connection is always made to the local host where the client JVM is running.
		//Also that means that the VertexCOunterService is also running on the same host.
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_VERTEXCOUNTER_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

			out.println(VertexCounterProtocol.LOAD);
			out.flush();
			out.close();
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}	
	}
	
	/**
	 * This method gets the next vertex id for the given graphid.
	 * @param graphID
	 * @return
	 */
	public static long getNextVertex(String graphID){
		long result = -1;
		final String host= null; //By putting null, we makesure that the connection is always made to the local host where the client JVM is running.
		//ALso that means that the VertexCOunterService is also running on the same host.
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_VERTEXCOUNTER_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

			out.println(VertexCounterProtocol.TICKET);
			out.flush();
			
			response = reader.readLine();
						
			if(response != null){
				out.close();
			}
						
			if(!response.contains(VertexCounterProtocol.ERROR)){
				result = Long.parseLong(response);
			}
			
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}
		
		return result;
	}

	/**
	 * In this method we provide the host where the VertexCounter service is running as a parameter.
	 * @param graphID
	 * @param host
	 * @return
	 */
	public static long getNextVertex(String graphID, String host){
		long result = -1;

		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_VERTEXCOUNTER_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

			out.println(VertexCounterProtocol.TICKET);
			out.flush();
			
			response = reader.readLine();
			
			if((response != null)&&(response.equals(VertexCounterProtocol.SEND))){
				out.println(graphID);//Now we send the graph id
				out.flush();
			}
			
			response = reader.readLine();
			
			//This is the culprit that closs the socket
			if(response != null){
				out.close();
			}
						
			if(!response.contains(VertexCounterProtocol.ERROR)){
				result = Long.parseLong(response);
			}
			
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
			Logger_Java.error("Host is : " + host);
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
			Logger_Java.error("Host is : " + host);
		}
		
		return result;
	}
	
	public static void setDefaultGraphID(String host, String graphID){
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_VERTEXCOUNTER_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

			out.println(VertexCounterProtocol.DEFAULT_GRAPH);
			out.flush();
			
			response = reader.readLine();
			
			if((response != null)&&(response.equals(VertexCounterProtocol.SEND))){
				out.println(graphID);//Now we send the graph id
				out.flush();
			}
			
			response = reader.readLine();
			
			//This is the culprit that closs the socket. But it seems this is very important.
			if(response != null){
				out.close();
			}			
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
			Logger_Java.error("Host is : " + host);
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
			Logger_Java.error("Host is : " + host);
		}
	}
	
	/**
	 * This method saves all the vertex information back in to the metadb.
	 */
	public static void save(){
		final String host= null; //By putting null, we makesure that the connection is always made to the local host where the client JVM is running.
		//ALso that means that the VertexCOunterService is also running on the same host.
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_VERTEXCOUNTER_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

			out.println(VertexCounterProtocol.SAVE);
			out.flush();
			out.close();
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
		}			
	}
}