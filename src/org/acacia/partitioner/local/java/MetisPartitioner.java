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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
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

import org.acacia.server.AcaciaManager;
import org.acacia.util.java.Utils_Java;
import org.acacia.centralstore.java.AcaciaHashMapCentralStore;
import org.acacia.log.java.Logger_Java;
import org.acacia.metadata.db.java.MetaDataDBInterface;

import x10.lang.Place;

import com.google.common.base.Splitter;

/**
 * This class assumes that the input file is a plain edgelist file.
 * @author miyurud
 *
 */
public class MetisPartitioner{
	//The following TreeMap loads the entire graph data file into memory. This is plausible because
	//the size of graph data sets loaded during the non-distributed mode of operation of Acacia is small. 
	private TreeMap<Integer, HashSet<Integer>>[] graphStorage = null; //Stores the entire graph used for partitioning.
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
	private int nThreads;
	private int largestVertex;
	private int nPlaces;
	
	public void convert(String graphName, String graphID, String inputFilePath, String outputFilePath, int nParts, boolean isDistributedCentralPartitions, int nThreads, int nPlaces){		
		convertWithoutDistribution(graphName, graphID, inputFilePath, outputFilePath, nParts, isDistributedCentralPartitions, nThreads, nPlaces);
		distributeEdges();
	}
	
	public void convertWithoutDistribution(String graphName, String graphID, String inputFilePath, String outputFilePath, int nParts, boolean isDistributedCentralPartitions, int nThreads, int nPlaces){
		this.outputFilePath = outputFilePath;
		this.nParts = nParts;
		this.graphName = graphName;
		this.isDistributedCentralPartitions = isDistributedCentralPartitions;
		this.graphID = graphID;
		this.nThreads = nThreads;
		this.nPlaces = nPlaces;
		
		//The following number of Treemap instances is kind of tricky, but it was decided to use nThreads number of TreeMaps to improve the data loading performance.
		graphStorage = new TreeMap[nThreads];
		
		for(int i = 0; i < nThreads; i++){
			graphStorage[i] = new TreeMap<Integer, HashSet<Integer>>();
		}
		
		loadDataSet(inputFilePath);
		constructMetisFormat(-1);
		partitionWithMetis(nParts);
	}
	
	private void distributeEdges(){
		final HashMap<Short, PartitionWriter> partitionFilesMap = new HashMap<Short, PartitionWriter>(); 
		final HashMap<Short, AcaciaHashMapCentralStore> centralStoresMap = new HashMap<Short, AcaciaHashMapCentralStore>(); 
		
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
		    
		    PartitionWriter refToWriter = null;
		    initPartFlag = false;
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
		int same = 0;
		int different = 0;
		final int numberOfPartitions = partitionFilesMap.keySet().size() < nThreads ? nThreads : partitionFilesMap.keySet().size();
		final int[] numVerts = new int[numberOfPartitions];
		int fromVertex = 0;
		int toVertex = 0;
		short fromVertexPartition = 0;
		short toVertexPartition = 0;
		
		CustomThread[] tArray = new CustomThread[nThreads];
		
		for(int i = 0; i < nThreads; i++){
			
			tArray[i] = new CustomThread(i){
				public void run(){
					int i = getI();
					Iterator<Map.Entry<Integer, HashSet<Integer>>> itr = graphStorage[i].entrySet().iterator();
					int toVertexPartition = 0;
					int toVertex = 0;
					
					while(itr.hasNext()){
						Map.Entry<Integer, HashSet<Integer>> entry = itr.next();
						int fromVertex = entry.getKey();
						int fromVertexPartition = partitionIndex[(int) fromVertex];
						HashSet hs = entry.getValue();
						if(hs != null){
							Iterator<Integer> itr2 = hs.iterator();
							while(itr2.hasNext()){
								toVertex = itr2.next();
								toVertexPartition = partitionIndex[(int) toVertex];
								
								if(fromVertexPartition != toVertexPartition){
									this.different++;
								}else{
									this.same++;
									numVerts[fromVertexPartition]++;
								}
							}
						}else{
							continue;
						}
					}
					setDone();
				}
			};
			tArray[i].start();
		}
		while(true){
			boolean flag = true;
			for(int x = 0; x < nThreads; x++){
				if(!tArray[x].isDone()){
					flag = false;
				}
			}
			
			if(flag){
				break;
			}
			
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < nThreads; i++){
			different += tArray[i].different;
			same += tArray[i].same;
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

		final int numberOfCentralPartitions = nParts;		
		for(short i = 0; i < numberOfCentralPartitions; i++){
			centralStoresMap.put(new Short(i), new AcaciaHashMapCentralStore(Integer.parseInt(graphID), i));
		}
		
		final int edgesPerCentralStore = (int)((different / numberOfCentralPartitions) + 1);
		
		System.out.println("--> edgesPerCentralStore : " + edgesPerCentralStore);

		MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET CENTRALPARTITIONCOUNT=" + numberOfCentralPartitions + ", VERTEXCOUNT=" + vertexCount + ", EDGECOUNT=" + edgeCount + " WHERE IDGRAPH=" + graphID);		
		HashMap<Short, StringBuilder> hmapSB = new HashMap<Short, StringBuilder>();
		HashMap<Short, StringBuilder> hmapCentral = new HashMap<Short, StringBuilder>();
		StringBuilder[] sbCentral = new StringBuilder[nThreads];
		
		for(int u = 0; u < nThreads; u++){
			sbCentral[u] = new StringBuilder();
	
			Iterator<Integer> itrN = graphStorage[u].keySet().iterator();
			
			for(int i = 0; i < numberOfCentralPartitions; i++){								
				while(itrN.hasNext()){
					fromVertex = itrN.next();
					HashSet<Integer> valItem = graphStorage[u].get(fromVertex);
					fromVertexPartition = partitionIndex[(int) fromVertex];
					
					Iterator<Integer> itr2 = valItem.iterator();
					while(itr2.hasNext()){
						toVertex = itr2.next();
						toVertexPartition = partitionIndex[(int) toVertex];
					
						if(fromVertexPartition != toVertexPartition){
							//Here the assumption is that we will create same number of central store partitions as the number of local store partitions.
							AcaciaHashMapCentralStore central = centralStoresMap.get(new Short((short) fromVertexPartition));								
							central.addEdge((long)fromVertex, (long)toVertex);
						}else{
							PartitionWriter pw = partitionFilesMap.get(new Short((short) fromVertexPartition));
							pw.writeEdge(fromVertex, toVertex);
						}
					}
					/*
					j++;
					if(j > edgesPerCentralStore){
						j=0;
						break;
					}
					*/
				}
			}
		}

		for(int i = 0; i < numberOfCentralPartitions; i++){
	      //org.acacia.util.java.Utils_Java.writeToFile("centralStore-part-" + i + ".txt", sbCentral[i]);
			AcaciaHashMapCentralStore central = centralStoresMap.get(new Short((short) i));
			
			MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.CPARTITION(IDCPARTITION, IDGRAPH, VERTEXCOUNT, EDGECOUNT) VALUES(" + i + "," + graphID + ",0,0)");
			central.storeGraph();
		}
		
		//Finalize the partitioning process. In the above lines we have written the edges to PartitionWriter objects.
		//here we are just writing the contents to file system.
		Iterator<Map.Entry<Short, PartitionWriter>> pItr = partitionFilesMap.entrySet().iterator();
		ArrayList<String> paths = new ArrayList<String>();
		partitionFileList = new String[partitionFilesMap.entrySet().size()];
		int k = 0;
		while(pItr.hasNext()){
			Map.Entry<Short, PartitionWriter> item = pItr.next();
			PartitionWriter pw = item.getValue();
			paths.add(pw.getOutputFilePath());
			pw.compress();
			partitionFileList[k] = pw.getOutputFilePath() + ".gz";
			k++;
			pw.close();
		}
		
		distributeCentralStore(numberOfCentralPartitions,graphID);
		//MetisPartitioner distributes only central store partitions.
		//The distribution of the local store partitions happen at the AcaciaSever. See the uploadGraphLocally() method
		//which calls this method for details.
		System.out.println("Done partitioning...");
	}
	
	public void distributeCentralStore(int n, final String graphID){	
		try{
				HashMap<Long, String> placeToHostMap = new HashMap<Long, String>();
				Runtime r = Runtime.getRuntime();
				
				int hostID=0,hostCount = 0,nPlaces=0;
				for(Long j=0l;j<n;j++){		             
		             nPlaces = (int)Place.places().size$O();

		             ArrayList<String> hostList = new ArrayList<String>();
		     	     File f = new File("machines.txt");
		     	     BufferedReader br = new BufferedReader(new FileReader(f));
		     	     String str = br.readLine();
		     	     while(str != null){
		     	    	hostList.add(str.trim());
		     	    	str = br.readLine();
		     	     }
		     	     br.close();
		     		 
		            hostCount = hostList.size();
		     	    hostID = (int)(j % hostCount);
		             
		     	    String hostName = hostList.get(hostID);

		             placeToHostMap.put(j, hostName);
		        }
		        Iterator<java.util.Map.Entry<Long, String>> itr2 = placeToHostMap.entrySet().iterator();
		        
		        int i = 0;
		        CustomThread[] tArray = new CustomThread[n];
		        while(itr2.hasNext()){
		        	final String filePath = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location")+"/" + graphID + "_centralstore/"+graphID+"_"+i;
		        	//i = i + 1;
					System.out.println("zip -rj "+filePath+"_trf.zip "+filePath);
					Process process = r.exec("zip -rj "+filePath+"_trf.zip "+filePath);
		            final Map.Entry<Long, String> itemHost = itr2.next();
		            if(itemHost==null){
		            	return;
		            }
		            int port = org.acacia.util.java.Conts_Java.ACACIA_INSTANCE_PORT;//This is the starting point
		            hostID = (int)(itemHost.getKey() % hostCount);
			     	int withinPlaceIndex = ((int)(itemHost.getKey() - hostID))/hostCount;
			     	    
			     	final int instancePort = port + withinPlaceIndex;
			     	final int fileTransferport = instancePort + (nPlaces/hostCount);
			     	
			     	tArray[i] = new CustomThread(i){
			     		public void run(){
			     			AcaciaManager.batchUploadCentralStore(itemHost.getValue(), instancePort, Long.parseLong(graphID), filePath+"_trf.zip", fileTransferport);
			     			String hostDI = ((String[])MetaDataDBInterface.runSelect("SELECT idhost FROM ACACIA_META.HOST WHERE name LIKE '" + itemHost.getValue() + "'").value)[(int)0L];
				            MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.HOST_HAS_CPARTITION(HOST_IDHOST, CPARTITION_IDCPARTITION, CPARTITION_GRAPH_IDCGRAPH) VALUES(" + hostDI + "," + getI() + "," + graphID + ")");
			     			setDone();
			     		}
			     	};
			     	
			     	tArray[i].start();
			     	i = i + 1;
		        }
		        
		        System.out.println("---AAAA--------");
		    	while(true){
					boolean flag = true;
					for(int x = 0; x < n; x++){
						if(!tArray[x].isDone()){
							flag = false;
						}
					}
					if(flag){
						break;
					}
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		    	System.out.println("---BBBB--------");
		}catch(Exception e){
			System.out.println("Error : "+e.getMessage());
		}
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
	}
	
	public int getInitlaPartitionID(){
		return initlaPartitionID;
	}
	
	public String[] getPartitionFileList(){
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
		
		//int largestVertex = graphStorage.lastEntry().getKey();
		File file = new File(outputFilePath+"/grf");

		try{
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Note: in Metis The numbering of the nodes starts from 1			
			vertexCount = largestVertex;
			
			if(adjustEdgestCount == -1){
				bw.write("" + largestVertex + " " + edgeCount);
			}else{
				bw.write("" + largestVertex + " " + adjustEdgestCount);
			}

			bw.write("\r\n");
			bw.flush();

			//Vertex IDs must start from 1
			int i = 1;
			
			for(; i <= largestVertex; i++){
				HashSet<Integer> itemsList = graphStorage[i%nThreads].get(i);

				if(itemsList == null){
					if(i == 0){
						bw.write("1 \r\n");//if there are no edges for this vertex, we introduce a self edge to avoid Metis's complain which says it detected different number of edges
											 //this seems to be the only solution at the moment (May 2015)
					}else{
						bw.write("\r\n");
					}
					bw.flush();
					//cn++;
				}else{
					Iterator<Integer> itr = itemsList.iterator();
					while(itr.hasNext()){						
						bw.write(""+(itr.next()));
												
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
		    	int firstVertexIdx = firstVertex%nThreads;
		    	HashSet<Integer> vertexSet = graphStorage[firstVertexIdx].get(firstVertex);
		    	
		    	if(vertexSet == null){
		    		vertexSet = new HashSet<Integer>();
		    		vertexSet.add(secondVertex);
		    		edgeCount++;
			    	graphStorage[firstVertexIdx].put(firstVertex, vertexSet);
		    	}else{		    		
		    		if(vertexSet.add(secondVertex)){
		    			edgeCount++;
		    		}
		    		//Note: we are getting a reference, so no need to put it back.
			    	//graphStorage.put(firstVertex, vertexSet);
		    	}
		    	
		    	//Next, treat the second vertex
		    	int secondVertexIdx = secondVertex%nThreads;
		    	vertexSet = graphStorage[secondVertexIdx].get(secondVertex);
		    	
		    	if(vertexSet == null){
		    		vertexSet = new HashSet<Integer>();
			    	vertexSet.add(firstVertex);
			    	edgeCount++;
			    	graphStorage[secondVertexIdx].put(secondVertex, vertexSet);
		    	}else{		    		
			    	if(vertexSet.add(firstVertex)){
			    		edgeCount++;
			    	}
			    	//Note: we are getting a reference, so no need to put it back.
			    	//graphStorage.put(secondVertex, vertexSet);
		    	}

		    	if(firstVertex > largestVertex){
		    		largestVertex = firstVertex;
		    	}
		    	
		    	if(secondVertex > largestVertex){
		    		largestVertex = secondVertex;
		    	}
		    	
				
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
			for(int i = 0; i < nThreads; i++){
				Set<Entry<Integer, HashSet<Integer>>> entrySet = graphStorage[i].entrySet();
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
			}
		}else{
			System.out.println("No data on graph structure...");
		}
	}
	
	public int getVertexCount(){
		return vertexCount;
	}
	
	class CustomThread extends Thread{
		private boolean done;
		private int i = 0;
		public int different = 0;
		public int same = 0;
		
		public CustomThread(int i2){
			i = i2;
		}
		
		public int getI(){
			return i;
		}
		
		public void setDone(){
			this.done = true;
			System.out.println("Done:" + i);
		}
		
		public boolean isDone(){
			return done;
		}
	}
}