package org.acacia.partitioner.hadoop.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.acacia.util.java.Utils_Java;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;


/**
 * This is the Java class that represents a file on HDFS. 
 * @author miyuru
 *
 */
public class HDFSFile_Java{
	
	static{
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
	}
	private static BufferedReader br;
	
	/**
	 * This method opens a file on HDFS that can be read later.
	 * @param path is the full path to the file that need to be opened.
	 */
	public static void open(String path){
	    FileSystem fs;
		try {
			fs = FileSystem.get(new JobConf());
			//Master
			String masterNode = Utils_Java.getAcaciaProperty("org.acacia.partitioner.hadoop.masters");
			br = new BufferedReader(new InputStreamReader(new URL("hdfs://" + masterNode + ":9000"+path).openStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String readLine(){
		try {
			return br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void close(){
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}