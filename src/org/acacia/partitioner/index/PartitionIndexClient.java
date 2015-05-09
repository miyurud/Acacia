package org.acacia.partitioner.index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;

public class PartitionIndexClient{
	public static void loadIndex(){
		final String host = null;
		try{
			Socket socket = new Socket(host, Conts_Java.ACACIA_PARTITION_INDEX_PORT); //This goes to the local host
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

			out.println(PartitionIndexProtocol.CREATE_INDEX);
			out.flush();

			response = reader.readLine();
			
			if((response != null)&&(response.equals(PartitionIndexProtocol.CREATE_INDEX_ACK))){
				out.println(PartitionIndexProtocol.EXIT);
				out.flush();
				
				response = reader.readLine();
				
				if((response != null)&&(response.equals(PartitionIndexProtocol.EXIT_ACK))){
					Logger_Java.info("Completed creating index.");
				}
			}
			
			out.println(PartitionIndexProtocol.EXIT);
			out.flush();
		}catch(UnknownHostException e){
			Logger_Java.error(e.getMessage());
			Logger_Java.error("==> host : " + host + " port : " + Conts_Java.ACACIA_PARTITION_INDEX_PORT);
		}catch(IOException ec){
			Logger_Java.error(ec.getMessage());
			Logger_Java.error("==> host : " + host + " port : " + Conts_Java.ACACIA_PARTITION_INDEX_PORT);
		}
	}
}