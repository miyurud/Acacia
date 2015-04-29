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

package test.acacia.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.acacia.server.AcaciaInstance;
import org.acacia.server.AcaciaInstanceFileTransferService;
import org.acacia.util.java.Utils_Java;
import org.acacia.log.java.Logger_Java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class TestAcaciaInstance{
	public static void main(String[] args){
		/*
		String batchInserter = Utils_Java.getAcaciaProperty("org.acacia.partitioner.neo4j.batchinserter");
		String batchInserterFolder = Utils_Java.getAcaciaProperty("org.acacia.partitioner.neo4j.batchinserter.folder");
		String dataFolder = "/var/tmp/acad";
		String graphID = "184";
		//Now the question is should it be place id or partition id?
		String graphPartitionID = "529";
		String fileName = "v-" + graphPartitionID;
		System.out.println("graphPartitionID : " + graphPartitionID);
		
		String cmdStr1 = "" + dataFolder + "/" + graphID + "_" + graphPartitionID + " /tmp/dgr/" + fileName + " /tmp/dgr/" + graphPartitionID + " node_index verts fulltext";
		String cmdStr2 = "" + batchInserterFolder + "";
		
		String[] args2 = new String[] {cmdStr1, cmdStr2};
		
		try{
			org.neo4j.batchimport.Importer.main(args2);
		}catch(IOException e){
			e.printStackTrace();
		}
		*/
		
		/*
		String batchInserter = Utils_Java.getAcaciaProperty("org.acacia.partitioner.neo4j.batchinserter");
		String batchInserterFolder = Utils_Java.getAcaciaProperty("org.acacia.partitioner.neo4j.batchinserter.folder");
		String dataFolder = "/var/tmp/acad";
		String graphID = "184";
		//Now the question is should it be place id or partition id?
		String graphPartitionID = "529";
		String fileName = "v-" + graphPartitionID;
		System.out.println("graphPartitionID : " + graphPartitionID);
		
		//Note that the batch.upload properties are defined based on the top level properties file located on the batch uploader directory.
		//28/10/2014 : From the place of multiple places of one node exectution pattern introduction, each subgraph located on the local file repository will have the partition id also attached to the graph id as <graphID>_<partitionID>. It is not just graph id
		//anymore because now we may have multiple subgraph partitions handled by multiple places which are running on same node. So the assumption held till now, that is one node hosts only one subgraph of a graph is no longer valid.
		String cmdStr1 = "\"" + dataFolder + "/" + graphID + "_" + graphPartitionID + " /tmp/dgr/" + fileName + " /tmp/dgr/" + graphPartitionID + " node_index verts fulltext\"";
		String cmdStr2 = "" + batchInserterFolder + "";
		
		//ProcessBuilder builder = new ProcessBuilder(batchInserter, "" + dataFolder + "/" + graphID + "_" + graphPartitionID + " /tmp/dgr/file" + fileName + " /tmp/dgr/file" + graphPartitionID + " node_index verts fulltext", "\"" + batchInserterFolder + "\"");
		//cmdStr="\"" + dataFolder + "/" + graphID + "_" + graphPartitionID + " /tmp/dgr/file" + fileName + " /tmp/dgr/file" + graphPartitionID + " node_index verts fulltext\"";
		//ProcessBuilder builder = new ProcessBuilder(batchInserter, "\"" + dataFolder + "/" + graphID + "_" + graphPartitionID + " /tmp/dgr/file" + fileName + " /tmp/dgr/file" + graphPartitionID + " node_index verts fulltext\" ", "\"" + batchInserterFolder + "\"");
		ProcessBuilder builder = new ProcessBuilder(batchInserter, cmdStr1, cmdStr2);
		//cmdStr="\"" + dataFolder + "/" + graphID + "_" + graphPartitionID + " /tmp/dgr/file" + fileName + " /tmp/dgr/file" + graphPartitionID + " node_index verts fulltext\"";		
		builder.directory(new File(batchInserterFolder));

		Process process = null;
		try {
			process = builder.start();
			process.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("==>|" + e.getMessage() + "|<==");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//		System.out.println("--->|" + cmdStr1 + "|");
//		System.out.println("--->|" + cmdStr2 + "|");
		//p.waitFor();
		
		BufferedReader b = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;
		
		try {
			while((line=b.readLine())!= null){
				System.out.println("-->" + line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		if(System.getProperty("logFileName") == null){
			System.setProperty("logFileName",System.getenv("HOSTNAME"));
		}
		
		//We need to check whether the user has specified the instance port number and data port number.
		if(System.getProperty("ACACIA_INSTANCE_PORT") == null){
			Logger_Java.info("Could not find the port number to run Acacia Instance. Now exitting ");
			System.exit(0);
		}
		
		if(System.getProperty("ACACIA_INSTANCE_DATA_PORT") == null){
			Logger_Java.info("Could not find the port number for Acacia Instance's data transfer service. Now exitting ");
			System.exit(0);
		}
		
		Logger_Java.info("---AAAA---");
		
		Thread t1 = new Thread(){
			public void run(){
				AcaciaInstance server = new AcaciaInstance();
				try {
					server.start_running();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				
//				while(true){
//					try {
//						Thread.currentThread().sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}//Sleep this many milliseconds
//				}
			}
		};

		Logger_Java.info("---BBBB---");
		
		Thread t2 = new Thread(){
			public void run(){
				AcaciaInstanceFileTransferService service = new AcaciaInstanceFileTransferService();
				try {
					service.start_running();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				
//				while(true){
//					try {
//						Thread.currentThread().sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}//Sleep this many milliseconds
//				}
			}
		};
		Logger_Java.info("---CCCC---");
		t1.start();
		Logger_Java.info("---DDDDD---");
		try{
			Logger_Java.info("Started AcaciaInstance service at " + InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Logger_Java.info("---FFFFF---");
		t2.start();
		Logger_Java.info("---EEEEE---");
		while(true){
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//Sleep this many milliseconds
		}
		
	}
}