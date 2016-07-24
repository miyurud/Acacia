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

package org.acacia.server.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Utils_Java;
import org.acacia.events.java.ShutdownEvent;
import org.acacia.events.java.ShutdownEventListener;
import org.acacia.events.java.DBTruncateEvent;
import org.acacia.events.java.DBTruncateEventListener;

public class AcaciaInstanceFileTransferServiceSession extends Thread{
	private Socket sessionSkt;
	private ShutdownEventListener listenerShtdn;
	private String dataFolder= "/tmp/dgr";
	
	/**
	 * The constructor
	 * @param socket
	 */
	public AcaciaInstanceFileTransferServiceSession(Socket socket){
		sessionSkt = socket;
	}
	
	/**
	 * 
	 */
	public void run(){
		int b = -1;
		
		try{			
			Logger_Java.info("Started writing to file.");
			
				InputStream inpStrm = sessionSkt.getInputStream();			
				PrintWriter out = new PrintWriter(sessionSkt.getOutputStream());

				byte[] line = new byte[8*1024];
				
				inpStrm.read(line, 0, line.length);
				String idTemp = new String(line).trim(); //We must trim() here to avoid an error which indicates invalid file path.
				Logger_Java.info("File name is : " + idTemp);
				Logger_Java.info("File path is : " + dataFolder + "/" + idTemp);
				
				File fil = new File(dataFolder + "/" + idTemp);

				if(!fil.exists()){
					fil.createNewFile();
				}
				
				if(fil.canWrite()){				
					FileOutputStream foutstrm = new FileOutputStream(fil);
	
					out.println(AcaciaInstanceProtocol.SEND_FILE);
					out.flush();		
					
					while((b = inpStrm.read(line)) > 0){					
						foutstrm.write(line, 0, b);
						foutstrm.flush();
					}
	
					foutstrm.close();
				}else{
					Logger_Java.info("The file cannot be written to...");
				}
				
			sessionSkt.close();
		}catch(IOException e){
			Logger_Java.error("Error : " + e.getMessage());
		}
	}
	
	private void fireShutdownEvent(ShutdownEvent evt){
		listenerShtdn.shutdownEventOccurred(evt);
	}
		
	public void addShutdownEventListener(ShutdownEventListener listener){
		this.listenerShtdn = listener;
	}
}