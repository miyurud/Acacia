package org.acacia.partitioner.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.acacia.util.java.Utils_Java;

/**
 * PartitionIndex is used to quickly lookup the partition ID of a vertex.
 * At this moment this index can load upto ~4.2billion vertices graphs. This is a limitation
 * that needs to be corrected soon.
 * @author miyuru
 *
 */
public class PartitionIndexObject{
	private int[] index; //The data type of the index is a big problem. Java does not allow for array indexes with long values. Therefore,
						// we are restricted with 2^32 vertices. ~4.2 billion vertices.
	
	public PartitionIndexObject(){
		build();
	}

	private void build() {
		String hadoopLoc = Utils_Java.getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		
		Runtime r = Runtime.getRuntime();
		Process p = null;
		int counter = 0;
		
		//First we get the file from HDFS on to the local file system
		try {
			p = r.exec(hadoopLoc + "/bin/hadoop fs -get /user/miyuru/merged /tmp/dgr/partidx");
			p.waitFor();
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			
			while((line=b.readLine())!= null){
				//System.out.println(line);
				index[counter] = Integer.parseInt(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Next we count the number of lines to get the number of vertices
		try {
			p = r.exec("wc -l /tmp/dgr/partidx");
			p.waitFor();
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			
			while((line=b.readLine())!= null){
				//System.out.println("Index size : " + line);
				index = new int[Integer.parseInt(line.split(" ")[0])];
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File fil = new File("/tmp/dgr/partidx");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fil));
			
			String line = null;
			int partitionId = -1;
			
			while((line = reader.readLine()) != null){
				partitionId = Integer.parseInt(line);
				index[counter] = partitionId;
				counter++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getPartitionID(int vertID){
		int result = -1;
		
		try{
			result = index[vertID-1];
			//System.out.println("For vertex : " + vertID + " got : " + result); 
		}catch(java.lang.ArrayIndexOutOfBoundsException e){
			//System.out.println("For vertex : " + vertID + " got index out of bounds exception.");
			//just return -1
		}
		return result; //We need to reduce 1 because we are dealing with arrays, and the vertices in Acacia start from vertex id 1. Not from 0.
	}
}