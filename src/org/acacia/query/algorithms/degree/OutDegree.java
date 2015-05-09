package org.acacia.query.algorithms.degree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.acacia.log.java.Logger_Java;
import org.acacia.server.AcaciaInstanceProtocol;
import org.acacia.util.java.Conts_Java;

import x10.lang.Place;

public class OutDegree{
	public static String run(String host, String port, String graphID) {
		String result = null;
		
		try{
			Socket socket = new Socket(host, Integer.parseInt(port));
			
			Logger_Java.info("****** Conencted to host : " + host + " ----------------------");
			
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = "";

			out.println(AcaciaInstanceProtocol.OUT_DEGREE_DIST);
			out.flush();

			response = reader.readLine();
			
			if((response != null)&&(response.equals(AcaciaInstanceProtocol.OK))){
				out.println(graphID);
				out.flush();
			}else{
				return "-1";
			}
			
			result = reader.readLine();
			
			//Logger_Java.info("###***### result : " + result + " for host : " + host);
			
			out.close();
			
			return result;
			
		}catch(UnknownHostException e){
			Logger_Java.error("Connecting to host (9) " + host + " got error message : " + e.getMessage());
		}catch(IOException ec){
			Logger_Java.error("Connecting to host (9) " + host + " got error message : " + ec.getMessage());
		}		
		
		return result;
	}
}
