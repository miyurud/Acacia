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

package org.acacia.csr.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import org.acacia.util.java.Utils_Java;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;

public class Utils{
	/**
	 * This method returns the number of vertices available in a graph.
	 * @return
	 */
	public static long getGraphVertexCount(){
		long result = -1;
		try {
		System.out.println("======>Deleting the file : /tmp/wcout2");
		
		File fileTmp = new File("/tmp/wcout2");
		if(fileTmp.exists()){
			fileTmp.delete();
		}
		
		System.out.println("======>Done Deleting the file : /tmp/wcout2");
		System.out.println("======>Now downloading the file wcout2");
		Runtime r = Runtime.getRuntime();
		String hadoopHome = Utils_Java.getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		Process p = r.exec(hadoopHome + "/bin/hadoop fs -get /user/miyuru/wcout/part-r-00000 /tmp/wcout2");
		p.waitFor();
		System.out.println("======>Done process");
		BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line2 = "";
		
		while((line2=b.readLine())!= null){
			System.out.println("---->"+line2);
		}
		
		System.out.println("======>Completed downloading the file : /tmp/wcout2");
		
		//Next we get the vertex count
		
		r = Runtime.getRuntime();
		p = r.exec("wc -l /tmp/wcout2");
		p.waitFor();
		
		b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		line2 = "";
		

			while((line2=b.readLine())!= null){
				System.out.println(line2);
				result = Long.parseLong(line2.split(" ")[0]);
				break;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * This method returns the number of vertices that are not available in a graph.
	 * @return
	 */
	public static long getNotinVertexCount(){
		long result = -1;
		
		try {
		
		System.out.println("======>Got the line count of file : /tmp/wcout2");
		
		 //Next we find the vertex ids that are not in the edge file
		Runtime r = Runtime.getRuntime();
		String hadoopHome = Utils_Java.getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		Process p = r.exec("bin/NotInVertsSearch " + hadoopHome);
		p.waitFor();
		
		BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line2 = "";
		
		while((line2=b.readLine())!= null){
			System.out.println(line2);
		}
		
		System.out.println("======>Calculated the not in verts count");
		
		//We need to add the net in vertices also to the vertex count
		r = Runtime.getRuntime();
		p = r.exec("wc -l /tmp/notinverts");
		p.waitFor();
		
		b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		line2 = "";
		
			while((line2=b.readLine())!= null){
				System.out.println(line2);
				result = Long.parseLong(line2.split(" ")[0]);

				break;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * This method returns the number of total vertices available in a graph.
	 * @return
	 */
	public static long getTotalVertexCount(){		
		long tot = -1;
		
		 File efile = new File("/tmp/vfile");
		 
		 if(efile.exists()){
				try{
					RandomAccessFile inFile = new RandomAccessFile("/tmp/vfile", "rw");
					tot = Long.parseLong(inFile.readLine());
					inFile.close();
				}catch(IOException ex){
					ex.printStackTrace();
				} 
				
				return tot;
		 }else{
			tot = getGraphVertexCount() + getNotinVertexCount();
			
			 PrintWriter writer;
			try {
				writer = new PrintWriter("/tmp/vfile", "UTF-8");
				writer.println(tot);
				writer.flush();
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		return tot;
	}
	
	/**
	 * This method returns the number of edges.
	 * @return
	 */
	public static long getTotalEdgeCount(){
		long edgeCount = -1;
		
		try{
			RandomAccessFile inFile = new RandomAccessFile("/tmp/efile", "rw");
			edgeCount = Long.parseLong(inFile.readLine());
			inFile.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		return edgeCount;
	}
	
	public static void cleanFiles(){
		 File efile = new File("/tmp/efile");
		 
		 if(efile.exists()){
			efile.delete(); 
		 }
		 
		 File vfile = new File("/tmp/vfile");
		 
		 if(vfile.exists()){
			 vfile.delete(); 
		 }
		 
		 File wfile = new File("/tmp/wfile");
		 
		 if(wfile.exists()){
			 wfile.delete(); 
		 }
	}
	
	public static void createHeaderFile(){
		File firstFile = new File("/tmp/firstfile");
		
		if(firstFile.exists()){
			firstFile.delete();
		}
		
		String line = getTotalVertexCount() + " " + getTotalEdgeCount() + "\r\n";
		PrintWriter writer;
		try {
			writer = new PrintWriter("/tmp/firstfile", "UTF-8");
			writer.print(line);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
	    try {
			String dir1 = "/user/miyuru/csrconverter-output";
		    FileSystem fs = FileSystem.get(new JobConf());
		    String line = getTotalVertexCount() + " " + getTotalEdgeCount() + "\r\n";
		    
		    BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(fs.create(new Path(dir1 + "/firstfile"))));
		    wr.write(line);
		    wr.flush();
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    */
	}
	
	public static boolean isReady(){
		File efile = new File("/tmp/efile");
		File wfile = new File("/tmp/wfile");
		
		 //if((efile.exists() && vfile.exists())){
		if((efile.exists()) && (wfile.exists())){
			return true;
		 }else{
			 return false;
		 }
	}
}