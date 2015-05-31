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