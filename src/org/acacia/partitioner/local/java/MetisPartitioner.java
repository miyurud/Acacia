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
	//The following TreeMap loads the entire graph data file into memory. This is plausible because
	//the size of graph data sets loaded during the non-distributed mode of operation of Acacia is small. 
	private TreeMap<Integer, HashSet<Integer>> graphStorage = new TreeMap<Integer, HashSet<Integer>>(); //Stores the entire graph used for partitioning.
	private String outputFilePath;
	private int vertexCount;
	private int edgeCount;
	private int nParts;
	boolean zeroVertFlag = false;
	private String graphName;
	private String graphID;
	private short[] partitionIndex; //We keep the partition index as short because sgraphStoragehort can store
	                                //maximum 32,767 values in a short variable.
	private ArrayList<String> partitionIDsList = new ArrayList<String>();
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
		
		System.out.println("----------------------->AAAAAAA-------->");
		loadDataSet(inputFilePath);
		System.out.println("----------------------->BBBBBBB-------->");
		constructMetisFormat(-1);
		System.out.println("----------------------->DDDDDDD-------->");
		partitionWithMetis(nParts);
		System.out.println("----------------------->EEEEEEE-------->");
		distributeEdges();
	}
	
	private void distributeEdges(){
		HashMap<Short, PartitionWriter> partitionFilesMap = new HashMap<Short, PartitionWriter>(); 
		
		//First we travers through the partitions index created by Metis and preparegraphStorage PartitionWriter objects
		//for each partition.
		//Also we have to create an index for fast reference. This is not possible with the Distributed version
		//which is aimed for large graphs. But for local mode of operation we can rely on such index.
		
		partitionIndex = new short[(int) (vertexCount + 1)];
		
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
		    		partitionIDsList.add(actualPartitionID);
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
		//same partition. If so we can put the edge on to that partition. If not, we have to upload the edge
		//to the central store.
		//The first pass is just to get the information of how the data set will be partitioned  across Acacia.
		Iterator<Map.Entry<Integer, HashSet<Integer>>> itr = graphStorage.entrySet().iterator();
		int fromVertex = 0;
		int toVertex = 0;
		short fromVertexPartition = 0;
		short toVertexPartition = 0;
		int same = 0;
		int different = 0;
		int numberOfPartitions = partitionFilesMap.keySet().size();
		int[] numVerts = new int[numberOfPartitions];
		System.out.println("numberOfPartitions:"+numberOfPartitions);
		
		while(itr.hasNext()){
			Map.Entry<Integer, HashSet<Integer>> entry = itr.next();
			//System.out.println("++A");
			fromVertex = entry.getKey();
			fromVertexPartition = partitionIndex[(int) fromVertex];
			//System.out.println("++B");
			HashSet hs = entry.getValue();
			//System.out.println("++B1");
			if(hs != null){
				Iterator<Integer> itr2 = hs.iterator();
				//System.out.println("++B2");
				while(itr2.hasNext()){
					//System.out.println("++B3");
					toVertex = itr2.next();
					//System.out.println("++B4 toVertex:"+toVertex);
					toVertexPartition = partitionIndex[(int) toVertex];
					//System.out.println("++B5");
				}
				//System.out.println("++B7");
			}else{
				//System.out.println("++B6");
				continue;
			}
			//System.out.println("++C");
			if(fromVertexPartition != toVertexPartition){
				different++;
			}else{
				same++;
				numVerts[fromVertexPartition]++;
			}
			//System.out.println("++D");
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
		//int numAsyncPerTime = 4;
		//nParts
		
		if(isDistributedCentralPartitions){
			//This is kind of tricky, but we need to get the number of places from the X10 runtime to determine the number of
			//partitions that we will create from the central store. At the moment this is kept as a future work.
			numberOfCentralPartitions = nParts;
		}else{
			numberOfCentralPartitions = 1;
		}
		
		int edgesPerCentralStore = (int)((different / numberOfCentralPartitions) + 1);

		MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET CENTRALPARTITIONCOUNT=" + numberOfCentralPartitions + ", VERTEXCOUNT=" + vertexCount + ", EDGECOUNT=" + edgeCount + " WHERE IDGRAPH=" + graphID);
		
		//itr = graphStorage.entrySet().iterator();
		Iterator<Integer> itrN = graphStorage.keySet().iterator();
		int j = 0;
		int lcnt = 0;
		for(int i = 1; i <= numberOfCentralPartitions; i++){
			org.acacia.centralstore.java.HSQLDBInterface.initDBSchema(graphID, "" + i);
			org.acacia.centralstore.java.HSQLDBInterface.createTable(graphID, "" + i, "CREATE TABLE IF NOT EXISTS acacia_central.edgemap(idgraph INT NOT NULL, idfrom INT NOT NULL, idto INT NOT NULL, idpartfrom INT NOT NULL, idpartto INT NOT NULL);");
			
			lcnt = 0;
				try{
						Connection c = org.acacia.centralstore.java.HSQLDBInterface.getConnection(graphID, ""+i);
						c.setAutoCommit(false);
					     //The output format will be <startID><startPartitionID><endPartitionID><endID>
						PreparedStatement prep = c.prepareStatement("INSERT INTO acacia_central.edgemap(idgraph, idfrom, idto, idpartfrom, idpartto) VALUES (?,?,?,?,?)");
											
						while(itr.hasNext()){
							//Map.Entry<Integer, HashSet<Integer>> entry = itr.next();
							fromVertex = itrN.next();
							HashSet<Integer> valItem = graphStorage.get(fromVertex);
							
							fromVertexPartition = partitionIndex[(int) fromVertex];
							
							Iterator<Integer> itr2 = valItem.iterator();
							while(itr2.hasNext()){
								toVertex = itr2.next();
								toVertexPartition = partitionIndex[(int) toVertex];
							
								if(fromVertexPartition != toVertexPartition){
									if(lcnt > 100000){ //Go by 100 thousand steps to avoid the prepared statement getting conjested.
										lcnt = 0;
										prep.executeBatch();
										prep.clearBatch();
									}else{
										lcnt++;
									     //The output format will be <startID><startPartitionID><endPartitionID><endID>
										prep.setString(1, graphID);
										prep.setInt(2, fromVertex);
										prep.setInt(3, fromVertexPartition);
										prep.setInt(4, toVertexPartition);
										prep.setInt(5, toVertex);
										prep.addBatch();
									}
								}else{
									PartitionWriter pw = partitionFilesMap.get(fromVertexPartition);
									pw.writeEdge(fromVertex, toVertex);
								}
							}
							j++;
							if(j > edgesPerCentralStore){
								j=0;
								break;
							}
						}
						if(lcnt > 0){
							prep.executeBatch();
						}
				      prep.close();
				      c.commit();
				      c.close();
				}catch(SQLException e){
					e.printStackTrace();
			    }
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
			partitionFileList[k] = pw.getOutputFilePath() + ".gz";
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
			StringBuilder sb = new StringBuilder();
			
			while((line=b.readLine())!= null){
				System.out.println(line);
				sb.append(line);
			};
			
			//Here we have to check whether METIS throws any errors. Sometimes, the number of edges
			//we have counted is different from what METIS finds. In that case we have to use the edge
			//count provided by METIS.
			int notFoundIndex = sb.toString().indexOf("However, I only found ");
			
			if(notFoundIndex != -1){
				String str = sb.toString();
				int i1 = notFoundIndex+22;
				str = str.substring(i1);
				int i2 = str.indexOf(" ");
				str = str.substring(0, i2);
				
				System.out.println("Correct metis edge count : " + str);
				constructMetisFormat(Integer.parseInt(str));
				//adjustEdgeCount(vertexCount, Long.parseLong(str));
				partitionWithMetis(nParts);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void adjustEdgeCount(int vertexCount, int newEdgeCount){
		System.out.println("----------------------------------------------op");
		try{
			Runtime r = Runtime.getRuntime();
			//Note on May 30 2015 : The following call to sed cannot be executed via Java. This is strange... Therefore, have to stick to
			//file writing base technique.
			String[] cmd = new String[]{"/bin/sed", "-i",  "--expression='1s/.*/" + vertexCount + " " + newEdgeCount + "/'", outputFilePath+"/grf"};
			Process p = r.exec(cmd);
			p.waitFor();
							
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;						
			while((line=b.readLine())!= null){
				System.out.println(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("----------------------------------------------oq");
	}
	
	public int getInitlaPartitionID(){
		return initlaPartitionID;
	}
	
	public String[] getPartitionFileList(){
//		x10.util.ArrayList<java.lang.String> lst = new x10.util.ArrayList<java.lang.String>((java.lang.System[]) null, x10.rtt.Types.STRING).x10$util$ArrayList$$init$S();
//		
//		for(String item: partitionFileList){
//			((x10.util.ArrayList<java.lang.String>)lst).add__0x10$util$ArrayList$$T$O(((java.lang.String)(item)));
//		}
//		
//		x10.core.Rail arr =  ((x10.util.ArrayList<java.lang.String>)lst).toRail();
//		return arr;
		
		return partitionFileList;		
	}
	
	public String[] getPartitionIDList(){
		String[] items = new String[partitionIDsList.size()];
		int cntr = 0;
		for(String i : partitionIDsList){
			items[cntr] = i;
			cntr++;
		}
		
		return items;
	}
	
	public String[] lst(){
		return null;
	}
	
	private void constructMetisFormat(int adjustEdgestCount){
		//if zeroVertFlag is true, The graph has a vertex with id zero.
    	if(!zeroVertFlag){
    		System.out.println("The graph starts from vertex id 1");	
    	}else{
    		System.out.println("The graph has zero vertex");
    	}
		
		int largestVertex = graphStorage.lastEntry().getKey();
		File file = new File(outputFilePath+"/grf");

		try{
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Note: in Metis The numbering of the nodes starts from 1
			
			if(zeroVertFlag){
				largestVertex++;
			}
			
			vertexCount = largestVertex;
			
			if(adjustEdgestCount == -1){
				bw.write("" + largestVertex + " " + edgeCount);
			}else{
				bw.write("" + largestVertex + " " + adjustEdgestCount);
			}
			//2777418
			//bw.write("" + largestVertex + " 2777418");
			bw.write("\r\n");
			bw.flush();
			//int cn = 0;
			//Vertex IDs must start from 1
			//long i = 1;
			int i = 0;
			
			if(zeroVertFlag){
				i = 1;
			}
			
			int k = 0;
			
			for(; i < largestVertex; i++){
				HashSet<Integer> itemsList = graphStorage.get(i);

				if(itemsList == null){
					if(i == 0){
						//System.out.println("=========================================00000000000===========================");
						bw.write("1 \r\n");//if there are no edges for this vertex, we introduce a self edge to avoid Metis's complain which says it detected different number of edges
											 //this seems to be the only solution at the moment (May 2015)
					}else{
						//System.out.println("=========================================22222222222===========================");
						bw.write("\r\n");
					}
					bw.flush();
					//cn++;
				}else{
					Iterator<Integer> itr = itemsList.iterator();
					while(itr.hasNext()){
						k++;
						//we have to check for zero falg over here because at the data loading phase, the zero vertex may be
						//found at the last part of an edge list.
						if(zeroVertFlag){
							bw.write(""+(itr.next() + 1));
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
			
			System.out.println("total list of edges : " + k);
			
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		System.out.println("Done");
	}
	
	private void loadDataSet(String filePath){
		BufferedReader br;

		Integer firstVertex = -1;
		Integer secondVertex = -1;
		
		try{
		    br = new BufferedReader(new FileReader(filePath), 10 * 1024 * 1024);
		    String line = br.readLine();
			Splitter splitter = null;
			
			if(line != null){
				if(line.contains(" ")){
					splitter = Splitter.on(' ');
				}else if(line.contains("\t")){
					splitter = Splitter.on('\t');
				}else if(line.contains(",")){
					splitter = Splitter.on(',');
				}else{
					System.out.println("Error : Could not find the required splitter character ...");
				}
			}
			
			//Here first we need to scan through the file one round and see whether the file contains a zero vertex.
			//If it contains a zero vertex we have to treat it separately. So that will be done in the second round.
			
			while (line != null) {
		    	Iterator<String> dataStrIterator = splitter.split(line).iterator();
		    	firstVertex = Integer.parseInt(dataStrIterator.next());
		    	secondVertex = Integer.parseInt(dataStrIterator.next());
		    	
		    	if((firstVertex == 0)||(secondVertex == 0)){
		    		zeroVertFlag = true;
		    		//Once we found a zero vertex, we should break
		    		break;
		    	}
		    	
				line = br.readLine();
		    	while((line != null)&&(line.trim().length() == 0)){
		    		line = br.readLine();
		    	}
			}
			
			br.close();
			
			//Next, we start loading the graph
			
			br = new BufferedReader(new FileReader(filePath), 10 * 1024 * 1024);
			line = br.readLine();
			
		    while (line != null) {
		    	Iterator<String> dataStrIterator = splitter.split(line).iterator();		
		    	firstVertex = Integer.parseInt(dataStrIterator.next());
		    	secondVertex = Integer.parseInt(dataStrIterator.next());
		    	
		    	if(firstVertex == secondVertex){
					line = br.readLine();
			    	while((line != null)&&(line.trim().length() == 0)){
			    		line = br.readLine();
			    	}
			    	continue;
		    	}
		    	
		    	if(zeroVertFlag){
			    	firstVertex = firstVertex + 1;
			    	secondVertex = secondVertex + 1;
				}
		    	
		    	//boolean containsFlag = false;
		    	//Treat the first vertex
		    	HashSet<Integer> vertexSet = graphStorage.get(firstVertex);
		    	
		    	if(vertexSet == null){
		    		vertexSet = new HashSet<Integer>();
		    		vertexSet.add(secondVertex);
		    		edgeCount++;
			    	graphStorage.put(firstVertex, vertexSet);
		    	}else{
//		    		if(vertexSet.contains(secondVertex)){
//		    			//containsFlag = true;
//		    		}else{
//			    		vertexSet.add(secondVertex);
//			    		edgeCount++;
//				    	graphStorage.put(firstVertex, vertexSet);
//		    		}
		    		
		    		vertexSet.add(secondVertex);
		    		edgeCount++;
		    		//Note: we are getting a reference, so no need to put it back.
			    	//graphStorage.put(firstVertex, vertexSet);
		    	}
		    	
		    	//Next, treat the second vertex
		    	vertexSet = graphStorage.get(secondVertex);
		    	
		    	if(vertexSet == null){
		    		vertexSet = new HashSet<Integer>();
			    	vertexSet.add(firstVertex);
			    	edgeCount++;
			    	graphStorage.put(secondVertex, vertexSet);
		    	}else{
//		    		if(vertexSet.contains(firstVertex)){
//		    			//containsFlag = true;
//		    		}else{
//				    	vertexSet.add(firstVertex);
//				    	edgeCount++;
//				    	graphStorage.put(secondVertex, vertexSet);
//		    		}
		    		
			    	vertexSet.add(firstVertex);
			    	edgeCount++;
			    	//Note: we are getting a reference, so no need to put it back.
			    	//graphStorage.put(secondVertex, vertexSet);
		    	}
		    	

//		    	if(!containsFlag){
//		    		edgeCount++;
//		    	}
				//System.out.println("line : " + line);
				
				line = br.readLine();
		    	while((line != null)&&(line.trim().length() == 0)){
		    		line = br.readLine();
		    	}
		    }
		}catch(IOException e){
			e.printStackTrace();
		}
		
		System.out.println("Loaded edges (directed) : " + edgeCount);
		System.out.println("Loaded edges (undirected) : " + (edgeCount/2));
		edgeCount = edgeCount/2;
	}
	
	public void printGraphContent(){
		if(graphStorage != null){
			Set<Entry<Integer, HashSet<Integer>>> entrySet = graphStorage.entrySet();
			Iterator itr = entrySet.iterator();
			
			while(itr.hasNext()){
				Entry<Integer, HashSet<Integer>> entry = (Entry<Integer, HashSet<Integer>>) itr.next();
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