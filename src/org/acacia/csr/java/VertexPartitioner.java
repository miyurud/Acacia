package org.acacia.csr.java;

import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;

public class VertexPartitioner implements Partitioner<LongWritable, Text>{
	private long numberOfVerts;
	//@Override
	//Here we need to identify the vertex count/largest veretx. Then we can decide what number of vertices will be in a partition. Then what we need to do is to
	//do a for loop and increment a counter until the key is in between the two incremented values. Once we find that condition satisfied, we return
	//the counter as the partition where the vertex record should be stored.
	public int getPartition(LongWritable key, Text values, int numReduceTasks){
		int part = 0;
		long k = key.get();
		int vertsPerPart = -1;
				
		if(numReduceTasks > 1){
			vertsPerPart = (int) (numberOfVerts/(numReduceTasks-1));
			
			if(vertsPerPart != 0){
				part = (int) (k/vertsPerPart);
			}
		}
		
		//System.out.println("vertex : " + k + " part : " + part);
		
		return part;
	}

	public void configure(JobConf arg0) {
		// TODO Auto-generated method stub
		numberOfVerts = Long.parseLong(arg0.get("vertex-count"));		
	}
}