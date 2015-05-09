package org.acacia.util.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.PatternSyntaxException;

import org.acacia.log.java.Logger_Java;
import org.apache.log4j.Logger;

public class Utils_Java{
	
	public static void runProcessBuilderAndSaveToFile(String command, String filePath){
		String[] arrCmd = null; 
		try{
			arrCmd = command.split("\\s+");
		}catch(PatternSyntaxException ex){
			ex.printStackTrace();
		}
		ProcessBuilder ps = new ProcessBuilder(arrCmd);
		ps.redirectErrorStream(true);
		
		Process pr;
		try {
			pr = ps.start();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			
			String line = null;
			File file = new File(filePath);
			if(file.exists()){
				file.delete();
			}
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			while((line = in.readLine()) != null){
				bw.write(line);
				bw.newLine();
			}
			
			bw.flush();
			bw.close();
			
			pr.waitFor();
			in.close();			
		} catch (IOException e) {
			//e.printStackTrace();
			Logger_Java.error(e.getMessage());
		} catch (InterruptedException e) {
			Logger_Java.error(e.getMessage());
		}
	}
	
	/**
	 * This method is alsmost similar to the method runProcessBuilderAndSaveToFile(). However, here it does not save to a file.
	 * @param command
	 * @param filePath
	 */
	public static void runProcessBuilderAndPrintToConsole(String command){
		String[] arrCmd = null; 
		try{
			arrCmd = command.split("\\s+");
		}catch(PatternSyntaxException ex){
			ex.printStackTrace();
		}
		ProcessBuilder ps = new ProcessBuilder(arrCmd);
		ps.redirectErrorStream(true);
		
		Process pr;
		try {
			pr = ps.start();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));		
			String line = null;
			
			while((line = in.readLine()) != null){
				Logger_Java.info(line);
			}
						
			pr.waitFor();
			in.close();			
		} catch (IOException e) {
			Logger_Java.error(e.getMessage());
		} catch (InterruptedException e) {
			Logger_Java.error(e.getMessage());
		}
	}
	
	/**
	 * This is the thrid pattern. We run a process and it its console output
	 * @param command
	 * @return
	 */
	
	public static x10.core.Rail<java.lang.String> runProcessBuilderAndGetResults(String command){
		String[] arrCmd = null; 
		x10.util.ArrayList<java.lang.String> lst = new x10.util.ArrayList<java.lang.String>((java.lang.System[]) null, x10.rtt.Types.STRING).x10$util$ArrayList$$init$S();
		
		try{
			arrCmd = command.split("\\s+");
		}catch(PatternSyntaxException ex){
			Logger_Java.error(ex.getMessage());
		}
		ProcessBuilder ps = new ProcessBuilder(arrCmd);
		ps.redirectErrorStream(true);
		
		Process pr;
		try {
			pr = ps.start();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));		
			String line = null;
			
			while((line = in.readLine()) != null){
				//Logger_Java.info(line);
				((x10.util.ArrayList<java.lang.String>)lst).add__0x10$util$ArrayList$$T$O((line));
			}
						
			pr.waitFor();
			in.close();			
		} catch (IOException e) {
			Logger_Java.error(e.getMessage());
		} catch (InterruptedException e) {
			Logger_Java.error(e.getMessage());
		}
		
		x10.core.Rail<java.lang.String> arr =  ((x10.util.ArrayList<java.lang.String>)lst).toRail();

		return arr;	
	}
	
	/**
	 * Runtime location is the place where Acacia keeps the files of operation during its run time.
	 * @return
	 */
	public static String getServerHost(){
		Properties prop = new Properties();
		
		try {
			//BufferedReader br = new BufferedReader(new FileReader(Conts_JAVA.BATCH_UPLOAD_CONFIG_FILE));
			FileInputStream fi = new FileInputStream(Conts_Java.ACACIA_SERVER_PROPS);
			prop.load(fi);
			fi.close();
			
		} catch (FileNotFoundException e) {
			//Logger.getLogger(Conts.UTILS_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
			return null;
		} catch (IOException e) {
			//Logger.getLogger(Conts.UTILS_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
			return null;
		}
		
		String serverHost = prop.getProperty("org.acacia.server.host");

		return serverHost;
	}
	
	/**
	 * Runtime location is the place where Acacia keeps the files of operation during its run time.
	 * @return
	 */
	public static String getRuntimeLocation(){
		Properties prop = new Properties();
		
		try {
			//BufferedReader br = new BufferedReader(new FileReader(Conts_JAVA.BATCH_UPLOAD_CONFIG_FILE));
			FileInputStream fi = new FileInputStream(Conts_Java.ACACIA_SERVER_PROPS);
			prop.load(fi);
			fi.close();
			
		} catch (FileNotFoundException e) {
			//Logger.getLogger(Conts.UTILS_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
			return null;
		} catch (IOException e) {
			//Logger.getLogger(Conts.UTILS_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
			return null;
		}
		
		String location = prop.getProperty("org.acacia.server.runtime.location");

		return location;
	}

	/**
	 * This method returns a property of Acacia stored in the "acacia-server.properties" file.
	 * @param propStr
	 * @return
	 */
	public static String getAcaciaProperty(String propStr){
		Properties prop = new Properties();
		
		try {
			//BufferedReader br = new BufferedReader(new FileReader(Conts_JAVA.BATCH_UPLOAD_CONFIG_FILE));
			FileInputStream fi = new FileInputStream(Conts_Java.ACACIA_SERVER_PROPS);
			prop.load(fi);
			fi.close();
			
		} catch (FileNotFoundException e) {
			//Logger.getLogger(Conts.UTILS_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
			return null;
		} catch (IOException e) {
			//Logger.getLogger(Conts.UTILS_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
			return null;
		}
		
		String location = prop.getProperty(propStr);

		return location;
	}
	
	/**
	 * This method returns the current system time as a HSQLDB TimeStamp String (E.g., '2008-08-08 20:08:08')
	 * @return
	 */
	public static String getCurrentTimeStamp(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = format.format(new java.util.Date());
		
		return result;
	}
	
	/**
	 * This method returns a list of private hosts
	 * @return
	 */
	public static String[] getPrivateHostList(){
		String[] result = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("machines.txt"));
			String line = null;
			
			while((line = reader.readLine()) != null){
				sb.append(line);
				sb.append(",");
			}	
			reader.close();
		} catch (FileNotFoundException e) {
			Logger_Java.error(e.getMessage());
		} catch (IOException e) {
			Logger_Java.error(e.getMessage());
		}
		
		result = sb.toString().split(",");
		
		return result;
	}
	
	/**
	 * This method returns a list of public hosts
	 * @return
	 */
	public static String[] getPublicHostList(){
		String[] result = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("machines_public.txt"));
			String line = null;
			
			while((line = reader.readLine()) != null){
				sb.append(line);
				sb.append(",");
			}	
			reader.close();
		} catch (FileNotFoundException e) {
			Logger_Java.error(e.getMessage());
		} catch (IOException e) {
			Logger_Java.error(e.getMessage());
		}
		
		result = sb.toString().split(",");
		
		return result;
	}
	
	/**
	 * This method is used to retrieve the catalog id from the local data store.
	 * @return
	 */
	public static int getPartitionIDFromCatalog(int graphID){
        String dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
        String line = null;
        int partitionId = -1;
        try{
	        BufferedReader reader = new BufferedReader(new FileReader(dataFolder + "/catalog"));
	
	        while((line = reader.readLine()) != null){
	        	if(line.startsWith("" + graphID + ":")){
	        		break;
	        	}
	        }
	        reader.close();
        }catch(IOException ec){
        	Logger_Java.error(ec.getMessage());
        }
        
        if(line != null){
        	partitionId = Integer.parseInt(line.split(":")[1]);
        }
        
        return partitionId;
	}
	
	public static void writeToFile(String filePath, StringBuilder sb){
		File file = new File(filePath);
		
		try{
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static String getHostName(){
		String hostname = System.getenv("HOSTNAME");
		
		if(hostname != null){
			return hostname;
		}else{
			try {
				hostname = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			return hostname;
		}
	}
}