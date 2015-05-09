package org.acacia.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;

public class InstanceShutdowner{
	public static void main(String[] args){
			try {
				System.out.println("Acacia instance at " + java.net.InetAddress.getLocalHost().getHostName()+" shuttingdown.");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//First we need to shutdown the current graph db instance
			try{
				Socket socket = new Socket("127.0.0.1", Conts_Java.ACACIA_INSTANCE_PORT);
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String response = "";

				out.println(AcaciaInstanceProtocol.SHUTDOWN);
				out.flush();

				response = reader.readLine();
				
				if((response != null)&&(response.equals(AcaciaInstanceProtocol.SHUTDOWN_ACK))){
					Logger_Java.info("Connection closed.");
					out.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	}
}