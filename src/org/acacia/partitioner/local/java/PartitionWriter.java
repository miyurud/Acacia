package org.acacia.partitioner.local.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class PartitionWriter{
	private File file;
	private BufferedWriter bw;
	private FileWriter fw;
	private String partitionFilePath;
	
	public PartitionWriter(String fileFullPath){
		this.partitionFilePath = fileFullPath;
		
		file = new File(fileFullPath);
		
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void compress(){    
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
			p = r.exec("gzip "+ partitionFilePath);
			p.waitFor();
			
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			
			while((line=b.readLine())!= null){
				System.out.println(line);
			};
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void writeEdge(long firstVertex, long secondVertex){
		try {
			bw.write(""+firstVertex+" "+secondVertex+"\r\n");
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getOutputFilePath(){
		return partitionFilePath;
	}
	
	public void close(){
		try {
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}