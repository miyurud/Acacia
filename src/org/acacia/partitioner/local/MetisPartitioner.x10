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

package org.acacia.partitioner.local;

import x10.util.ArrayList;
import x10.util.HashMap;
import x10.util.HashSet;
import x10.interop.Java;
import x10.core.Thread;
import x10.util.Set;
import x10.util.Map.Entry;
import x10.util.StringBuilder;

import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.Runtime;
import java.io.File;
import java.lang.Process;
import java.io.IOException;
import java.io.InputStreamReader;

//import com.google.common.base.Splitter;

import org.acacia.server.AcaciaManager;
import org.acacia.centralstore.AcaciaHashMapCentralStore;
import org.acacia.util.Utils;
import x10.interop.Java;
import org.acacia.metadata.db.java.MetaDataDBInterface;

public class MetisPartitioner {
 	//The following TreeMap loads the entire graph data file into memory. This is plausible because
 	//the size of graph data sets loaded during the non-distributed mode of operation of Acacia is small. 
 	private var graphStorage:Rail[TreeMap] = null; //Stores the entire graph used for partitioning.
  	private var outputFilePath:String;
  	private var vertexCount:int;
  	private var edgeCount:int;
  	private var nParts:int;
  	private var zeroVertFlag:boolean = false;
  	private var graphName:String;
  	private var graphID:String;
  	private var partitionIndex:Rail[short]; //We keep the partition index as short because sgraphStoragehort can store
  	//maximum 32,767 values in a short variable.
  	private var partitionIDsList:ArrayList[String] = new ArrayList[String]();
  	private var initPartFlag:boolean;
  	private var isDistributedCentralPartitions:boolean;
  	private var initlaPartitionID:int;
  	private var partitionFileList:Rail[String];
  	private var nThreads:int;
  	private var largestVertex:int;
  	private var nPlaces:int;
 
  	public def convert(graphName:String, graphID:String, inputFilePath:String, outputFilePath:String, nParts:int, isDistributedCentralPartitions:boolean, nThreads:int, nPlaces:int){		
  		convertWithoutDistribution(graphName, graphID, inputFilePath, outputFilePath, nParts, isDistributedCentralPartitions, nThreads, nPlaces);
  		distributeEdges();
  	}
  
  	public def convertWithoutDistribution(graphName:String, graphID:String, inputFilePath:String, outputFilePath:String, nParts:int, isDistributedCentralPartitions:boolean, nThreads:int, nPlaces:int){
  		this.outputFilePath = outputFilePath;
  		this.nParts = nParts;
  		this.graphName = graphName;
  		this.isDistributedCentralPartitions = isDistributedCentralPartitions;
  		this.graphID = graphID;
  		this.nThreads = nThreads;
  		this.nPlaces = nPlaces;
  
  		//The following number of Treemap instances is kind of tricky, but it was decided to use nThreads number of TreeMaps to improve the data loading performance.
  		graphStorage = new Rail[TreeMap](nThreads);
  
  		for(var i:int = 0n; i < nThreads; i++){
  			graphStorage(i) = new TreeMap();
  		}
  
  		loadDataSet(inputFilePath);
  		constructMetisFormat(-1n);
  		partitionWithMetis(nParts);
  	}
  
  	private def distributeEdges(){
  		val partitionFilesMap:HashMap[Short, PartitionWriter] = new HashMap[Short, PartitionWriter](); 
  		val centralStoresMap:HashMap[Short, AcaciaHashMapCentralStore] = new HashMap[Short, AcaciaHashMapCentralStore](); 
  
  		//First we travers through the partitions index created by Metis and preparegraphStorage PartitionWriter objects
  		//for each partition.
  		//Also we have to create an index for fast reference. This is not possible with the Distributed version
  		//which is aimed for large graphs. But for local mode of operation we can rely on such index.
  
  		partitionIndex = new Rail[short]((vertexCount + 1) as Int);
  		var br:BufferedReader;

  		try{
  			br = new BufferedReader(new FileReader(outputFilePath+"/grf.part."+nParts)); //compare this line with .java file
  			var line:String = br.readLine();
  			var counter:int = 0n;
  			var partitionID:short = 0s;
  
		  	var refToWriter:PartitionWriter = null;
		  	initPartFlag = false;
		  	while(line != null){		    	
		  		partitionID = Short.parseShort(line);
		  		partitionIndex(counter) = partitionID;
		  		refToWriter = partitionFilesMap.get(partitionID);
		  
		  		if(refToWriter == null){
  					var actualPartitionID:String = MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.PARTITION(GRAPH_IDGRAPH) VALUES(" + graphID + ")");
  					//refToWriter = new PartitionWriter(outputFilePath+"/"+graphID+"_"+actualPartitionID);
  					refToWriter = new PartitionWriter(Int.parse(graphID), Int.parse(actualPartitionID), outputFilePath+"/"+graphID+"_"+actualPartitionID);
  					partitionFilesMap.put(partitionID, refToWriter);
  
  					partitionIDsList.add(actualPartitionID);
  					if(!initPartFlag){
  						initlaPartitionID = Int.parse(actualPartitionID);
  						initPartFlag = true;
  					}
  				}
  				line = br.readLine();
  				counter++;
  			}
  		}catch(e:java.io.IOException){
  			e.printStackTrace();
  		}

  		//Next we have to go through the original edgelist and see whether each and every edge falls into the
  		//same partition. If so we can put the edge on to that partition. If not, we have to upload the edge
  		//to the central store.
  		//The first pass is just to get the information of how the data set will be partitioned  across Acacia.
  		var same:int = 0n;
  		var different:int = 0n;
		var numberOfPartitions:int ;//= partitionFilesMap.keySet().size() < nThreads ? nThreads : partitionFilesMap.keySet().size();

 		if(partitionFilesMap.keySet().size() < nThreads){
 			numberOfPartitions = nThreads;
 		}else{
 			numberOfPartitions = partitionFilesMap.keySet().size() as int;
 		}
		val numVerts:Rail[int] = new Rail[int](numberOfPartitions);
		var fromVertex :int= 0n;
 		var toVertex:int = 0n;
		var fromVertexPartition:short = 0s;
		var toVertexPartition:short = 0s;

   		tArray:Rail[CustomThread] = new Rail[CustomThread](nThreads);
   
        
 		finish for(var k:int = 0n; k < nThreads; k++){
                   val i:Int = k;
                   
                   async{
                   tArray(i) = new CustomThread(i, numberOfPartitions);
 					//val itr:java.util.Iterator[Map.Entry[Int,java.util.HashSet[Int]]] = graphStorage(i).entrySet().iterator() as java.util.Iterator[Map.Entry[Int,java.util.HashSet[Int]]];
                    val itrN:java.util.Iterator = graphStorage(i).keySet().iterator() as java.util.Iterator;
   					var toVertexPartition2:Int = 0n;
   					var toVertex2:Int = 0n;
   
   					while(itrN.hasNext()){
   						//val entry:java.util.Map.Entry[Int, HashSet[Int]] = itrN.next();
   						//var fromVertex2:Int = entry.getKey();
                                                var fromVertex2:Int = itrN.next() as Int;
   						var fromVertexPartition2:Int = partitionIndex(fromVertex2);
   						var hs:x10.util.HashSet[Int]  = graphStorage(i).get(fromVertex2) as x10.util.HashSet[Int];
   
   						if(hs != null){
   							val itr2:x10.lang.Iterator[Int] = hs.iterator() as x10.lang.Iterator[Int];
   
   							while(itr2.hasNext()){
   								toVertex2 = itr2.next() as Int;
   								toVertexPartition2 = partitionIndex(toVertex2);
  
   								if(fromVertexPartition2 != toVertexPartition2){
                                    tArray(i).different++;
   								}else{
                                    tArray(i).same++;
                                    tArray(i).numVertesPrivate(fromVertexPartition2)++;
   								}
   							}
   						}else{
   							continue;
   						}
   					}
                    tArray(i).setDone();
                   }
 		}
        
        
 		while(true){
 			var flag:boolean = true;
 			for(var x:int = 0n; x < nThreads; x++){
 				if(!tArray(x).isDone()){
 					flag = false;
 				}
 			}
 
 			if(flag){
 				break;
 			}

 			try {
                System.sleep(100);
 			} catch (e:Exception) {
 				e.printStackTrace();
 			}
 		}
   
 		for(var i:int = 0n; i < nThreads; i++){
 			different += tArray(i).different;
 			same += tArray(i).same;

 			for(var j:int = 0n; j < numberOfPartitions; j++){
 				numVerts(j) += tArray(i).numVertesPrivate(j);
 			}			
   		}
  
 		Console.OUT.println("---------partitioning strategy---------");
 		Console.OUT.println("number of edges for central store:"+different);
 		Console.OUT.println("number of edges for local stores:"+same);
 
 		for(var i:int = 0n; i < numberOfPartitions; i++){
 			Console.OUT.println("partition("+i+") edges:"+numVerts(i));
 		}
 		Console.OUT.println("selected central partitioning strategy : " + (isDistributedCentralPartitions ? "distributed":"single"));
 		Console.OUT.println("---------------------------------------");
   
 		//In the second run we actually separate the graph data to multiple partitions.
 		//The following code assume there is only single central partition

 		val numberOfCentralPartitions:int = nParts;		
 		for(var i:short = 0s; i < numberOfCentralPartitions; i++){
 			centralStoresMap.put(i, new AcaciaHashMapCentralStore(Int.parseInt(graphID), i));
 		}
   
 		val edgesPerCentralStore:int = (different / numberOfCentralPartitions) + 1n;
   
 		Console.OUT.println("--> edgesPerCentralStore : " + edgesPerCentralStore);

 		MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET CENTRALPARTITIONCOUNT=" + numberOfCentralPartitions + ", VERTEXCOUNT=" + vertexCount + ", EDGECOUNT=" + edgeCount + " WHERE IDGRAPH=" + graphID);		
 		hmapSB:HashMap[Short, StringBuilder] = new HashMap[Short, StringBuilder]();
 		hmapCentral:HashMap[Short, StringBuilder] = new HashMap[Short, StringBuilder]();
 		sbCentral:Rail[StringBuilder] = new Rail[StringBuilder](nThreads);

  
   		for(var u:int = 0n; u < nThreads; u++){	
   			val itrN:java.util.Iterator = graphStorage(u).keySet().iterator() as java.util.Iterator;
  
   			for(var i:int = 0n; i < numberOfCentralPartitions; i++){								
   				while(itrN.hasNext()){
   					fromVertex = itrN.next() as Int;
   					valItem:Set[Int] = graphStorage(u).get(fromVertex) as Set[Int];
   					fromVertexPartition = partitionIndex(fromVertex);
   
   					val itr2:Iterator[Int] = valItem.iterator();
   					while(itr2.hasNext()){
   						toVertex = itr2.next();
   						toVertexPartition = partitionIndex(toVertex);
  
   						if(fromVertexPartition != toVertexPartition){
   							//Here the assumption is that we will create same number of central store partitions as the number of local store partitions.
   							val central:AcaciaHashMapCentralStore = centralStoresMap.get(fromVertexPartition as short);								
   							central.addEdge(fromVertex as Long, toVertex as Long);
   						}else{
   							var pw:PartitionWriter = partitionFilesMap.get(fromVertexPartition as short);
   							pw.writeEdge(fromVertex, toVertex);
   						}
   					}
   				}
   			}
   		}
   
		  for(var i:int = 0n; i < numberOfCentralPartitions; i++){
			  //org.acacia.util.java.Utils_Java.writeToFile("centralStore-part-" + i + ".txt", sbCentral[i]);
			  val central:AcaciaHashMapCentralStore  = centralStoresMap.get(i as short);
			  
			  MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.CPARTITION(IDCPARTITION, IDGRAPH, VERTEXCOUNT, EDGECOUNT) VALUES(" + i + "," + graphID + ",0,0)");
			  central.storeGraph();
		  }
		  
		  //Finalize the partitioning process. In the above lines we have written the edges to PartitionWriter objects.
		  //here we are just writing the contents to file system.
		  val pItr:Iterator[Entry[Short, PartitionWriter]] = partitionFilesMap.entries().iterator();
		  val paths:ArrayList[String] = new ArrayList[String]();
		  partitionFileList = new Rail[String](partitionFilesMap.entries().size());
		  var k:int = 0n;
		  while(pItr.hasNext()){
			  val item:Entry[Short, PartitionWriter] = pItr.next();
			  val pw:PartitionWriter = item.getValue();
			  paths.add(pw.getOutputFilePath());
			  pw.compress();
			  partitionFileList(k) = pw.getOutputFilePath() + ".zip";
			  k++;
			  pw.close();
		  }
		  
		  distributeCentralStore(numberOfCentralPartitions,graphID);
		  //MetisPartitioner distributes only central store partitions.
		  //The distribution of the local store partitions happen at the AcaciaSever. See the uploadGraphLocally() method
		  //which calls this method for details.
		  Console.OUT.println("Done partitioning...");
   	}
  
  	public def distributeCentralStore(n:int, val graphID:String){	
  		try{
  			var placeToHostMap:HashMap[Long, String] = new HashMap[Long, String]();
  			val r:Runtime = Runtime.getRuntime();
  
  			var hostID:int=0n,hostCount:int = 0n,nPlaces:int=0n;
  			for(var j:Long=0;j<n;j++){		             
  				nPlaces = AcaciaManager.getNPlaces(org.acacia.util.Utils.getPrivateHostList()(0));

  				var hostList:ArrayList[String] = new ArrayList[String]();
  				val f:File = new File("machines.txt");
  				var br:BufferedReader = new BufferedReader(new FileReader(f));
  				var str:String = br.readLine();
  				while(str != null){
  					hostList.add(str.trim());
  					str = br.readLine();
  				}
  				br.close();
  
  				hostCount = hostList.size() as int;
  				hostID = (j % hostCount) as int;
  
  				var hostName:String = hostList.get(hostID);

  				placeToHostMap.put(j, hostName);
  			}
  			val itr2:Iterator[Entry[Long, String]] = placeToHostMap.entries().iterator();
  
  			var i:int = 0n;
  			var tArray:Rail[CustomThread] = new Rail[CustomThread](n);
  			while(itr2.hasNext()){
  				val filePath:String = Utils.call_getAcaciaProperty("org.acacia.server.runtime.location")+"/" + graphID + "_centralstore/"+graphID+"_"+i;
  				//i = i + 1;
  				Console.OUT.println("zip -rj "+filePath+"_trf.zip "+filePath);
  				val process:Process = r.exec("zip -rj "+filePath+"_trf.zip "+filePath);
  				val itemHost:Entry[Long, String] = itr2.next();
  				if(itemHost==null){
  					break;
  				}
  				val port:int = org.acacia.util.java.Conts_Java.ACACIA_INSTANCE_PORT;//This is the starting point
  				hostID = (itemHost.getKey() % hostCount) as int;
  				val withinPlaceIndex:int = ((itemHost.getKey() - hostID) as int)/hostCount;
  
  				val instancePort:int = port + withinPlaceIndex;
  				val fileTransferport:int = instancePort + (nPlaces/hostCount) + 1n;
                //async{
                tArray(i) = new CustomThread(i);
  				// tArray(i) = new CustomThread(i){
  				// 	public def run(){
  						AcaciaManager.batchUploadCentralStore(itemHost.getValue(), instancePort, Long.parseLong(graphID), filePath+"_trf.zip", fileTransferport);
  						hostDI:String = org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("SELECT idhost FROM ACACIA_META.HOST WHERE name LIKE '" + itemHost.getValue() + "'").getObjectArray()(0n) as String;
                        //hostDI:String = org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("SELECT idhost FROM ACACIA_META.HOST WHERE name LIKE '" + itemHost.getValue() + "'")(0).value;
  						MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.HOST_HAS_CPARTITION(HOST_IDHOST, CPARTITION_IDCPARTITION, CPARTITION_GRAPH_IDCGRAPH) VALUES(" + hostDI + "," + i + "," + graphID + ")");
                        tArray(i).setDone();
  				// 	}
  				// };
                //}
  				//tArray(i).start();
  				i = i + 1n;
  			}
  
  			Console.OUT.println("---AAAA--------");
  			while(true){
  				var flag:boolean = true;
  				for(var x:int = 0n; x < n; x++){
  					if(!tArray(x).isDone()){
  						flag = false;
  					}
  				}
  				if(flag){
  					break;
  				}
  				try {
  					Thread.currentThread().sleep(100);
  				} catch (e:Exception) {
  					e.printStackTrace();
  				}
  			}
  
            //ToDO : The functionality of the following code must be verified. It seems currently it does not work.
            //next, we need to delete the local copies of the central store to avoid the working directory get filled with temp data.
            for(var j:Long=0;j<n;j++){
            	Console.OUT.println("---BBBB--------1");
            	val filePath:String = Utils.call_getAcaciaProperty("org.acacia.server.runtime.location")+"/" + graphID + "_centralstore/"+graphID+"_"+j;
            	Console.OUT.println("file:" + filePath);
                val p1:java.lang.Process = r.exec("rm -r "+filePath);
                Console.OUT.println("---BBBB--------2");
                val p2:java.lang.Process = r.exec("rm "+filePath+"_trf.zip");
                Console.OUT.println("---BBBB--------3");
                Console.OUT.println("|rm -r "+filePath+"|");
                Console.OUT.println("|rm "+filePath+"_trf.zip|");
            }
  			Console.OUT.println("---BBBB--------");
  		}catch(e:java.io.IOException){
  			Console.OUT.println("Error : "+e.getMessage());
  		}
  	}
  
  	private def partitionWithMetis(nParts:int){
  		Console.OUT.println("nParts-->local partitioner:" + nParts);
  		val metisHome:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.metis.home");
  
  		val r:Runtime = Runtime.getRuntime();
  		val p:Process;
  		try{
  			p = r.exec(metisHome + "/bin/gpmetis "+outputFilePath+"/grf " + nParts);
  			p.waitFor();
  
  			val b:BufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
  			var line:String = "";
  			val sb:StringBuilder = new StringBuilder();
  
  			while((line=b.readLine())!= null){
  				Console.OUT.println(line);
  				sb.add(line);
  			}
  
  			//Here we have to check whether METIS throws any errors. Sometimes, the number of edges
  			//we have counted is different from what METIS finds. In that case we have to use the edge
  			//count provided by METIS.
  			val notFoundIndex:int = sb.toString().indexOf("However, I only found ");
  
  			if(notFoundIndex != -1n){
  				var str:String = sb.toString();
  				val i1:int = notFoundIndex+22n;
  				str = str.substring(i1);
  				val i2:int = str.indexOf(" ");
  				str = str.substring(0n, i2);
  
  				Console.OUT.println("Correct metis edge count : " + str);
  				constructMetisFormat(Int.parseInt(str));
  				//adjustEdgeCount(vertexCount, Long.parseLong(str));
  				partitionWithMetis(nParts);
  			}
  
  
  		}catch (e:IOException) {
  			e.printStackTrace();
  		} catch (e:java.lang.InterruptedException) {
  			e.printStackTrace();
  		}
  	}
  
  	private def adjustEdgeCount(vertexCount:int, newEdgeCount:int){
  		try{
  			val r:Runtime = Runtime.getRuntime();
  			//Note on May 30 2015 : The following call to sed cannot be executed via Java. This is strange... Therefore, have to stick to
  			//file writing base technique.
  			val cmd:Rail[String] = new Rail[String](4n);
			//{"/bin/sed", "-i",  "--expression='1s/.*/" + vertexCount + " " + newEdgeCount + "/'", outputFilePath+"/grf"};
			cmd(0n) = "/bin/sed";
			cmd(1n) = "-i";
			cmd(2n) = "--expression='1s/.*/" + vertexCount + " " + newEdgeCount + "/'";
			cmd(3n) = outputFilePath+"/grf";
  			val p:Process = r.exec(x10.interop.Java.convert(cmd));
  			p.waitFor();
  
  			val b:BufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
  			var line:String = null;						
  			while((line=b.readLine())!= null){
  				Console.OUT.println(line);
  			}
  		}catch(e:IOException){
  			e.printStackTrace();
  		}catch(e:java.lang.InterruptedException){
  			e.printStackTrace();
  		}
  	}
  
  	public def getInitlaPartitionID():int{
  		return initlaPartitionID;
  	}
  
  	public def getPartitionFileList():Rail[String]{
  		return partitionFileList;		
  	}
  
  	public def getPartitionIDList():Rail[String]{
  		val items:Rail[String] = new Rail[String](partitionIDsList.size());
  		var cntr:int = 0n;
  		for(val i:String in partitionIDsList){
  			items(cntr) = i;
  			cntr++;
  		}
  
  		return items;
  	}
  
  	public def lst():Rail[String]{
  		return null;
  	}
  
  	private def constructMetisFormat(adjustEdgestCount:int){
  		//if zeroVertFlag is true, The graph has a vertex with id zero.
  		if(!zeroVertFlag){
  			Console.OUT.println("The graph starts from vertex id 1");	
  		}else{
  			Console.OUT.println("The graph has zero vertex");
  		}
  
  		//int largestVertex = graphStorage.lastEntry().getKey();
  		file:File = new File(outputFilePath+"/grf");

  		try{
  			// if file doesnt exists, then create it
  			if (!file.exists()) {
  				file.createNewFile();
  			}
  			val fw:FileWriter = new FileWriter(file.getAbsoluteFile());
  			val bw:BufferedWriter = new BufferedWriter(fw);
  
  			//Note: in Metis The numbering of the nodes starts from 1			
  			vertexCount = largestVertex;
  
  			if(adjustEdgestCount == -1n){
  				bw.write("" + largestVertex + " " + edgeCount);
  			}else{
  				bw.write("" + largestVertex + " " + adjustEdgestCount);
  			}

  			bw.write("\r\n");
  			bw.flush();

  			//Vertex IDs must start from 1
  			var i:int = 1n;
  
  			for(; i <= largestVertex; i++){
  				val itemsList:HashSet[Int] = graphStorage(i%nThreads).get(i) as HashSet[Int];

  				if(itemsList == null){
  					if(i == 0n){
  						bw.write("1 \r\n");//if there are no edges for this vertex, we introduce a self edge to avoid Metis's complain which says it detected different number of edges
  						//this seems to be the only solution at the moment (May 2015)
  					}else{
  						bw.write("\r\n");
  					}
  					bw.flush();
  					//cn++;
  				}else{
  					val itr:Iterator[Int] = itemsList.iterator();
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
  		}catch(e:IOException){
  			e.printStackTrace();
  		}

  		Console.OUT.println("Done");
  	}
  
  	private def loadDataSet(filePath:String){
  		var br:BufferedReader;

  		var firstVertex:int = -1n;
  		var secondVertex:int = -1n;
  
  		try{
  			br = new BufferedReader(new FileReader(filePath));
  			var line:String = br.readLine();
  			var splitter:com.google.common.base.Splitter = null;
  
  			if(line != null){
  				if(line.indexOf(" ")>=0){
  					Console.OUT.println("space");
  					splitter = com.google.common.base.Splitter.on(' ');
  				}else if(line.indexOf("\t")>=0){
  					Console.OUT.println("tab");
  					splitter = com.google.common.base.Splitter.on('\t');
  				}else if(line.indexOf(",")>=0){
  					Console.OUT.println("comma");
  					splitter = com.google.common.base.Splitter.on(',');
	  			}else{
	  				Console.OUT.println("Error : Could not find the required splitter character ...");
	  			}
  			}
  			
	  		//Here first we need to scan through the file one round and see whether the file contains a zero vertex.
	  		//If it contains a zero vertex we have to treat it separately. So that will be done in the second round.
	  
		  	while (line != null) {
		  		//val dataStrIterator:Iterator[String] = splitter.split(line).iterator() as Iterator[String];
		  
		        val dataStrIterator:java.util.Iterator = splitter.split(line).iterator();
		  		        
		        // Console.OUT.println("first:" + dataStrIterator.next());
		        // Console.OUT.println("second:" + dataStrIterator.next());
		  
		  		// firstVertex = x10.interop.Java.convert(dataStrIterator.next());
		  		// secondVertex = x10.interop.Java.convert(dataStrIterator.next());
		  
		        firstVertex = Int.parse("" + dataStrIterator.next());
		        secondVertex = Int.parse("" + dataStrIterator.next());
		        
		  		// Console.OUT.println("first:" + firstVertex + " second:" + secondVertex);
		  		
		  		if((firstVertex == 0n)||(secondVertex == 0n)){
		  			zeroVertFlag = true;
					//Once we found a zero vertex, we should break
					break;
		  		}
		  
		  		line = br.readLine();
		  		while((line != null) && (line.trim().length() == 0n)){
		  			line = br.readLine();
		  		}
		  	}
		  
		  	br.close();
		  
		  	//Next, we start loading the graph
		  
		  	br = new BufferedReader(new FileReader(filePath));
		  	line = br.readLine();
		  
		  	while (line != null) {
		  		//val dataStrIterator:Iterator[String] = splitter.split(line).iterator() as Iterator[String];		
		  		// firstVertex = Int.parseInt(dataStrIterator.next());
		  		// secondVertex = Int.parseInt(dataStrIterator.next());
		  
		        val dataStrIterator:java.util.Iterator = splitter.split(line).iterator();
		        firstVertex = Int.parse("" + dataStrIterator.next());
		        secondVertex = Int.parse("" + dataStrIterator.next());
		  
		  		if(firstVertex == secondVertex){
		  			line = br.readLine();
		  			while((line != null)&&(line.trim().length() == 0n)){
		  				line = br.readLine();
		  			}
		  			continue;
		  		}
		  
		  		if(zeroVertFlag){
		  			firstVertex = firstVertex + 1n;
		  			secondVertex = secondVertex + 1n;
		  		}
		  
		  		//boolean containsFlag = false;
		  		//Treat the first vertex
		  		val firstVertexIdx:int = firstVertex%nThreads;
		  		var vertexSet:Set[Int] = graphStorage(firstVertexIdx).get(firstVertex) as Set[Int];
		  
		  		if(vertexSet == null){
		  			vertexSet = new HashSet[Int]();
		  			vertexSet.add(secondVertex);
		  			edgeCount++;
		  			graphStorage(firstVertexIdx).put(firstVertex, vertexSet);
		  		}else{		    		
		  			if(vertexSet.add(secondVertex)){
		  				edgeCount++;
		  			}
		  			//Note: we are getting a reference, so no need to put it back.
		  			//graphStorage.put(firstVertex, vertexSet);
	  			}
		  		
		  		//Next, treat the second vertex
			  	val secondVertexIdx:int = secondVertex%nThreads;
			  	vertexSet = graphStorage(secondVertexIdx).get(secondVertex) as Set[Int];
		  
			  	if(vertexSet == null){
			  		vertexSet = new HashSet[Int]();
		  			vertexSet.add(firstVertex);
		  			edgeCount++;
		  			graphStorage(secondVertexIdx).put(secondVertex, vertexSet);
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
	  			while((line != null)&&(line.trim().length() == 0n)){
	  				line = br.readLine();
	  			}
	  		}
		}catch(e:IOException){
	  		e.printStackTrace();
	  	}
	  
	  	Console.OUT.println("Loaded edges (directed) : " + edgeCount);
	  	Console.OUT.println("Loaded edges (undirected) : " + (edgeCount/2));
	  	edgeCount = edgeCount/2n;
  	}
  
  	public def printGraphContent(){
  		if(graphStorage != null){
  			for(var i:int = 0n; i < nThreads; i++){
  				val entrySet:Set[Entry[Int, Set[Int]]] = graphStorage(i).entrySet() as Set[Entry[Int, Set[Int]]];
  				val itr:Iterator[Entry[Int, Set[Int]]] = entrySet.iterator();
  
  				while(itr.hasNext()){
  					val entry:Entry[Int, Set[Int]] =  itr.next();//(Entry<Integer, HashSet<Integer>>)
  					Console.OUT.print(entry.getKey());
  					Console.OUT.print("-->[");
  
  					val itr2:Iterator[Int] = entry.getValue().iterator();
  
  					while(itr2.hasNext()){
  						Console.OUT.print(itr2.next());
  						if(itr2.hasNext()){
  							Console.OUT.print(",");
  						}
  					}
  
  					Console.OUT.println("]");
  				}
  			}
  		}else{
  			Console.OUT.println("No data on graph structure...");
  		}
  	}
  
  	public def getVertexCount():int{
  		return vertexCount;
  	}

	class CustomThread extends Thread{
	 	private var done:boolean;
	 	private var i:int = 0n;
	 	public var different:int = 0n;
	 	public var same:int = 0n;
	 	public var numVertesPrivate:Rail[int];
	
	 	public def this(i2:int){
	 		i = i2;
	 	}
	
	 	public def this(i2:int, nParts:int){
	 		i = i2;
	 		numVertesPrivate = new Rail[int](nParts);
	 	}
	
	 	public def getI():int{
	 		return i;
	 	}
	
	 	public def setDone(){
	 		this.done = true;
	 		Console.OUT.println("Done:" + i);
	 	}
	
	 	public def isDone():boolean{
	 		return done;
	 	}
	}

}