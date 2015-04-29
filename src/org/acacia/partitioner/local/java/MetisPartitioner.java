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

package org.acacia.partitioner.local.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.acacia.util.java.Utils_Java;
import org.acacia.metadata.db.java.MetaDataDBInterface;

import com.google.common.base.Splitter;

/**
 * This class assumes that the input file is a plain edgelist file.
 * @author miyurud
 *
 */
public class MetisPartitioner{
	private TreeMap<Long, HashSet<Long>> graphStorage = new TreeMap<Long, HashSet<Long>>(); 
	private String outputFilePath;
	private long vertexCount;
	private long edgeCount;
	private int nParts;
	boolean zeroVertFlag = false;
	private String graphName;
	private String graphID;
	private short[] partitionIndex; //We keep the partition index as short because sgraphStoragehort can store
	                                //maximum 32,767 values in a short variable.
	private boolean initPartFlag;
	private boolean isDistributedCentralPartitions;
	private int initlaPartitionID;
	private String[] partitionFileList;
	
	public void convert(String graphName, String graphID, String inputFilePath, String outputFilePath, int nParts, boolean isDistributedCentralPartitions){
		this.outputFilePath = outputFilePath;
		this.nParts = nParts;
		this.graphName = graphName;
		this.isDistributedCentralPartitions = isDistributedCentralPartitions;
		this.graphID = graphID;
				
		loadDataSet(inputFilePath);
		constructMetisFormat();
		partitionWithMetis(nParts);
		distributeEdges();
	}
	
	private void distributeEdges(){
		HashMap<Short, PartitionWriter> partitionFilesMap = new HashMap<Short, PartitionWriter>(); 
		
		//First we travers through the partitions index created by Metis and preparegraphStorage PartitionWriter objects
		//for each partition.
		//Also we have to create an index for fast reference. This is not possible with the Distributed version
		//which is aimed for large graphs. But for local mode of operation we can rely on such index.
		
		partitionIndex = new short[(int) vertexCount];
		
		BufferedReader br;
		try{
		    br = new BufferedReader(new FileReader(outputFilePath+"/grf.part."+nParts), 10 * 1024 * 1024);
		    String line = br.readLine();
		    int counter = 0;
		    short partitionID = 0;

//		    if(zeroVertFlag){
//		    	counter = 0;
//		    }else{
//		    	counter = 1;
//		    }
		    
		    PartitionWriter refToWriter = null;
		    
		    while(line != null){		    	
		    	partitionID = Short.parseShort(line);
		    	partitionIndex[counter] = partitionID;
		    	refToWriter = partitionFilesMap.get(partitionID);
		    	
		    	if(refToWriter == null){
		    		String actualPartitionID = MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.PARTITION(GRAPH_IDGRAPH) VALUES(" + graphID + ")");
		    		refToWriter = new PartitionWriter(outputFilePath+"/"+graphID+"_"+actualPartitionID);
		    		partitionFilesMap.put(partitionID, refToWriter);
		    		
		    		if(!initPartFlag){
		    			initlaPartitionID = Integer.parseInt(actualPartitionID);
		    			initPartFlag = true;
		    		}
		    	}
		    	
		    	line = br.readLine();
		    	counter++;
		    }
		}catch(IOException e){
			e.printStackTrace();
		}
		
		//Next we have to go through the original edgelist and see whether each and every edge falls into the
		//same partition. If so we can put the edge on to that partition. If ngetPartitionFileListot, we have to upload the edge
		//to the central store.
		//The first pass is just to get the information of how the data set will be partitioned  across Acacia.
		Iterator<Map.Entry<Long, HashSet<Long>>> itr = graphStorage.entrySet().iterator();
		long fromVertex = 0;
		long toVertex = 0;
		short fromVertexPartition = 0;
		short toVertexPartition = 0;
		long same = 0;
		long different = 0;
		int numberOfPartitions = partitionFilesMap.keySet().size();
		long[] numVerts = new long[numberOfPartitions];
		System.out.println("numberOfPartitions:"+numberOfPartitions);
		
		while(itr.hasNext()){
			Map.Entry<Long, HashSet<Long>> entry = itr.next();
			
			fromVertex = entry.getKey();
			fromVertexPartition = partitionIndex[(int) fromVertex];
			
			Iterator<Long> itr2 = entry.getValue().iterator();
			while(itr2.hasNext()){
				toVertex = itr2.next();
				toVertexPartition = partitionIndex[(int) toVertex];
			}
			
			if(fromVertexPartition != toVertexPartition){
				different++;
			}else{
				same++;
				numVerts[fromVertexPartition]++;
			}
		}
		
		System.out.println("---------partitioning strategy---------");
		System.out.println("number of edges for central store:"+different);
		System.out.println("number of edges for local stores:"+same);

		for(int i = 0; i < numberOfPartitions; i++){
			System.out.println("partition("+i+"):"+numVerts[i]);
		}
		System.out.println("selected central partitioning strategy : " + (isDistributedCentralPartitions ? "distributed":"single"));
		System.out.println("---------------------------------------");
		//In the second run we actually separate the graph data to multiple partitions.
		//The following code assume there is only single central partition

		int numberOfCentralPartitions = 0;
		
		if(isDistributedCentralPartitions){
			//This is kind of tricky, but we need to get the number of places from the X10 runtime to determine the number of
			//partitions that we will create from the central store.
		}else{
			numberOfCentralPartitions = 1;
		}

		MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET CENTRALPARTITIONCOUNT=" + numberOfCentralPartitions + ", VERTEXCOUNT=" + vertexCount + ", EDGECOUNT=" + edgeCount + " WHERE IDGRAPH=" + graphID);
		org.acacia.centralstore.java.HSQLDBInterface.initDBSchema(graphID, "1");
		org.acacia.centralstore.java.HSQLDBInterface.createTable(graphID, "1", "CREATE TABLE IF NOT EXISTS acacia_central.edgemap(idgraph INT NOT NULL, idfrom INT NOT NULL, idto INT NOT NULL, idpartfrom INT NOT NULL, idpartto INT NOT NULL);");
		try{
				Connection c = org.acacia.centralstore.java.HSQLDBInterface.getConnection(graphID, "1");
				c.setAutoCommit(false);
			     //The output format will be <startID><startPartitionID><endPartitionID><endID>
				PreparedStatement prep = c.prepareStatement("INSERT INTO acacia_central.edgemap(idgraph, idfrom, idto, idpartfrom, idpartto) VALUES (?,?,?,?,?)");
				
				itr = graphStorage.entrySet().iterator();
				while(itr.hasNext()){
					Map.Entry<Long, HashSet<Long>> entry = itr.next();
					
					fromVertex = entry.getKey();
					fromVertexPartition = partitionIndex[(int) fromVertex];
					
					Iterator<Long> itr2 = entry.getValue().iterator();
					while(itr2.hasNext()){
						toVertex = itr2.next();
						toVertexPartition = partitionIndex[(int) toVertex];
					}
					
					if(fromVertexPartition != toVertexPartition){
					     //The output format will be <startID><startPartitionID><endPartitionID><endID>
						prep.setString(1, graphID);
						prep.setLong(2, fromVertex);
						prep.setLong(3, fromVertexPartition);
						prep.setLong(4, toVertexPartition);
						prep.setLong(5, toVertex);
						prep.addBatch();
					}else{
						same++;
						numVerts[fromVertexPartition]++;
						PartitionWriter pw = partitionFilesMap.get(fromVertexPartition);
						pw.writeEdge(fromVertex, toVertex);
					}
				}

		      prep.executeBatch();
		      prep.close();
		      c.commit();
		      c.close();
		}catch(SQLException e){
			e.printStackTrace();
	    }
		System.out.println("DDDDDDDDDDDDD...");
		//Finalize the partitioning process.
		Iterator<Map.Entry<Short, PartitionWriter>> pItr = partitionFilesMap.entrySet().iterator();
		ArrayList<String> paths = new ArrayList<String>();
		partitionFileList = new String[partitionFilesMap.entrySet().size()];
		System.out.println("DDDDDDDDDDDDD2...");
		int k = 0;
		while(pItr.hasNext()){
			Map.Entry<Short, PartitionWriter> item = pItr.next();
			PartitionWriter pw = item.getValue();
			System.out.println("DDDDDDDDDDDDD3...");
			paths.add(pw.getOutputFilePath());
			System.out.println("DDDDDDDDDDDDD4...");
			pw.compress();
			System.out.println("DDDDDDDDDDDDD5...");
			partitionFileList[k] = pw.getOutputFilePath();
			k++;
			pw.close();
			System.out.println("DDDDDDDDDDDDD6...");
		}
		
		//partitionFileList = new String[paths.size()]; //(String[])paths.toArray();
		System.out.println("Done partitioning...");
	}
	
	private void partitionWithMetis(int nParts){
		System.out.println("nParts-->local partitioner:" + nParts);
		String metisHome = Utils_Java.getAcaciaProperty("org.acacia.partitioner.metis.home");
		
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
			p = r.exec(metisHome + "/bin/gpmetis "+outputFilePath+"/grf " + nParts);
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
	
	public int getInitlaPartitionID(){
		return initlaPartitionID;
	}
	
	public x10.core.Rail getPartitionFileList(){
		x10.util.ArrayList<java.lang.String> lst = new x10.util.ArrayList<java.lang.String>((java.lang.System[]) null, x10.rtt.Types.STRING).x10$util$ArrayList$$init$S();
		
		for(String item: partitionFileList){
			((x10.util.ArrayList<java.lang.String>)lst).add__0x10$util$ArrayList$$T$O(((java.lang.String)(item)));
		}
		
		x10.core.Rail arr =  ((x10.util.ArrayList<java.lang.String>)lst).toRail();
		return arr;
	}
	
	private void constructMetisFormat(){
    	if(!zeroVertFlag){
    		System.out.println("The graph starts from vertex id 1");	
    	}else{
    		System.out.println("The graph has zero vertex");
    	}
		
		long largestVertex = graphStorage.lastEntry().getKey();
		File file = new File(outputFilePath+"/grf");

		try{
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			if(zeroVertFlag){
				largestVertex++;
			}
			
			vertexCount = largestVertex;
			
			bw.write("" + largestVertex + " " + edgeCount);
			bw.write("\r\n");
			bw.flush();
			int cn = 0;
			//Vertex IDs must start from 1
			for(long i = 0; i < largestVertex; i++){
				HashSet<Long> itemsList = graphStorage.get(i);

				if(itemsList == null){
					bw.write("\r\n");
					bw.flush();
					cn++;
				}else{
					Iterator<Long> itr = itemsList.iterator();
					while(itr.hasNext()){
						//we have to check for zero falg over here because at the data loading phase, the zero vertex may be
						//found at the last part of an edge list.
						if(zeroVertFlag){
							bw.write(""+(itr.next() + 1l));
						}else{
							bw.write(""+(itr.next()));
						}
												
						if(!itr.hasNext()){
							bw.write("\r\n");
						}else{
							bw.write(" ");
						}
						bw.flush();
					}
				}
			}
			
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		System.out.println("Done");
	}
	
	private void loadDataSet(String filePath){
		BufferedReader br;

		Long firstVertex = -1l;
		Long secondVertex = -1l;
		
		try{
		    br = new BufferedReader(new FileReader(filePath), 10 * 1024 * 1024);
		    String line = br.readLine();
			Splitter splitter = null;
			
			if(line != null){
				if(line.contains(" ")){
					splitter = Splitter.on(' ');
				}else if(line.contains("\t")){
					splitter = Splitter.on('\t');
				}
			}
			
		    while (line != null) {
		    	Iterator<String> dataStrIterator = splitter.split(line).iterator();
		    			    	
		    	if((firstVertex == 0)||(secondVertex == 0)){
		    		zeroVertFlag = true;
		    	}				
		    	
		    	if(zeroVertFlag){
			    	firstVertex = Long.parseLong(dataStrIterator.next()) + 1;
			    	secondVertex = Long.parseLong(dataStrIterator.next()) + 1;
				}else{
			    	firstVertex = Long.parseLong(dataStrIterator.next());
			    	secondVertex = Long.parseLong(dataStrIterator.next());
				}
		    	
		    	//Treat the first vertex
		    	HashSet<Long> vertexSet = graphStorage.get(firstVertex);
		    	
		    	if(vertexSet == null){
		    		vertexSet = new HashSet<Long>();
		    	}
		    	
		    	vertexSet.add(secondVertex);
		    	graphStorage.put(firstVertex, vertexSet);
		    	
		    	//Next, treat the second vertex
		    	vertexSet = graphStorage.get(secondVertex);
		    	
		    	if(vertexSet == null){
		    		vertexSet = new HashSet<Long>();
		    	}
		    	
		    	vertexSet.add(firstVertex);
		    	graphStorage.put(secondVertex, vertexSet);
		    	
				edgeCount++;
		    	line = br.readLine();
		    }
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void printGraphContent(){
		if(graphStorage != null){
			Set<Entry<Long, HashSet<Long>>> entrySet = graphStorage.entrySet();
			Iterator itr = entrySet.iterator();
			
			while(itr.hasNext()){
				Entry<Long, HashSet<Long>> entry = (Entry<Long, HashSet<Long>>) itr.next();
				System.out.print(entry.getKey());
				System.out.print("-->[");
				
				Iterator itr2 = entry.getValue().iterator();
				
				while(itr2.hasNext()){
					System.out.print(itr2.next());
					if(itr2.hasNext()){
						System.out.print(",");
					}
				}
				
				System.out.println("]");
			}
		}else{
			System.out.println("No data on graph structure...");
		}
	}
}