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

package org.acacia.server;

import x10.compiler.Native;
import x10.util.ArrayList;
import x10.util.HashMap;
import x10.util.Set;
import x10.util.StringBuilder;

import x10.regionarray.Array;
import x10.util.Map.Entry;

import org.acacia.util.Utils;
import org.acacia.util.Conts;
import org.acacia.util.PlaceToNodeMapper;
import org.acacia.partitioner.hadoop.HadoopOrchestrator;
import org.acacia.partitioner.hadoop.HDFSInterface;
import org.acacia.partitioner.hadoop.HDFSFile;
import org.acacia.partitioner.MetisInterface;

import org.acacia.log.Logger;
import org.acacia.partitioner.hadoop.HDFSFile;
import org.acacia.metadata.db.HSQLDBInterface;
import org.acacia.frontend.AcaciaFrontEnd;
import org.acacia.backend.AcaciaBackEnd;
import org.acacia.vertexcounter.java.VertexCounter;
import org.acacia.partitioner.local.AcaciaRDFPartitioner;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;
import org.acacia.partitioner.local.MetisPartitioner;

import org.acacia.vertexcounter.java.VertexCounterClient;

import org.acacia.metadata.db.java.MetaDataDBInterface;

import java.io.IOException;
import java.io.File;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import java.net.Socket;
import java.net.ServerSocket;

import java.net.BindException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.acacia.vertexcounter.java.VertexCounter;
import org.acacia.server.migration.MigrationManager;

/**
 * The Acacia Server.
 * April 12, 2015 - Note that Acacia should be a system capable of running standalone mode. Therfore,
 * there were several changes made to the data loading pipeline to decouple the data loading process
 * from the rest of the Acacia server. This way, after copying the needed data on to some preconfigured
 * location, Acacia will be able to run even without the connectivity with Hadoop+HDFS.
 */
public class AcaciaServer {
    private static val hostPlaceMap:HashMap[String, Long] = new HashMap[String, Long]();
    private var frontend:AcaciaFrontEnd = null;
    private var backend:AcaciaBackEnd = null;
    private var runFlag:Boolean = true;
    private var srv:ServerSocket;
    private var vertcounter:VertexCounter;
    private val IS_DISTRIBUTED = Boolean.parse(Utils.call_getAcaciaProperty("org.acacia.server.mode.isdistributed"));
    
    /**
     * The main method.
     */
    public static def main(val args:Rail[String]):void {
	/*finish for (p in Place.places()) {
     	at (p) async {
		Console.OUT.println(here+" says hellowrw and ");
		test.acacia.server.x10.TestAcaciaInstance.main(null);
		while(true);
	}
     }*/
    	var server:AcaciaServer = new AcaciaServer();
        //server.run(Boolean.parse(args(0)));
	server.run();
    }
    
    
    //public def run(val standaloneFlag:Boolean):void{
    public def run():void{
    	init();
    	
    	Console.OUT.println("Running the server...");   	
        //The default behavior of the server is to upload a configured default graph to the system if it has not
        //been uploaded to the server yet.
    	var map:HashMap[String, String] = Utils.getBatchUploadFileList();
    	var keys:Set[String] = map.keySet();
    	
    	for(item in keys){   		
    		var l:Rail[String] = call_runSelect("select UPLOAD_PATH from ACACIA_META.Graph where NAME LIKE '" + item + "';");
    		
    		Console.OUT.println("Size : " + l.size);
    		val count = l.size;
    		for (var i:Int = 0n; i < count; i++){
    			Console.OUT.println("item : " + l(i));
    		}
    
    		// if ((l.size > 0)){
    		// 	Console.OUT.println("Item : " + item + " available on the DB");
    		// }else{
    		// 	AcaciaServer.uploadGraph(item, map.get(item));
    		// }
    	}
    	
    	Console.OUT.println("Done processing...");
    	
        val hostLst:x10.interop.Java.array[String] = org.acacia.util.java.Utils_Java.getPrivateHostList();
        val hostHashMap:HashMap[String, Boolean] = new HashMap[String, Boolean]();
        for(var i:Int = 0n; i < hostLst.length; i++ ){
            hostHashMap.put(hostLst(i), false);
        }
        
        //var partIndexFlags:Rail[Boolean] = new Rail[Boolean](org.acacia.util.java.Utils_Java.getPrivateHostList().length);
        //val href = GlobalRef[HashMap[String, Boolean]](hostHashMap);
        
     
    	finish{
    		//if(standaloneFlag){
			    for (p in Place.places()){
			            // finish{
				               /*   }else{*/
							   at(p) async {
							    	//PlaceToNodeMapper.getHost(p.id) + " port : " + PlaceToNodeMapper.getInstancePort(p.id)
							        //java.lang.System.setProperty("logFileName", ""+here.id);
							        //java.lang.System.setProperty("ACACIA_INSTANCE_PORT", "" + PlaceToNodeMapper.getInstancePort(p.id));
							        //ACACIA_INSTANCE_DATA_PORT
							        //java.lang.System.setProperty("ACACIA_INSTANCE_DATA_PORT", "" + PlaceToNodeMapper.getFileTransferServicePort(p.id));
							        
							        //Console.OUT.println("logFileName : " + java.lang.System.getProperty("logFileName") + " ACACIA_INSTANCE_PORT : " + PlaceToNodeMapper.getInstancePort(p.id) + " ACACIA_INSTANCE_DATA_PORT : " + PlaceToNodeMapper.getFileTransferServicePort(p.id));      
							         
							         //Logger_Java.info("logFileName : " + java.lang.System.getProperty("logFileName") + " ACACIA_INSTANCE_PORT : " + PlaceToNodeMapper.getInstancePort(p.id) + " ACACIA_INSTANCE_DATA_PORT : " + PlaceToNodeMapper.getFileTransferServicePort(here.id));
							        Console.OUT.println(here+" says hellowrw and "); 
							        test.acacia.server.x10.TestAcaciaInstance.main(null);
							    }
			               //}
						    val curHost:String = PlaceToNodeMapper.getHost(p.id);
						    
						    Console.OUT.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
						    Console.OUT.println("Current host is : " + curHost);
						    Console.OUT.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
						    if(p.id == 0){
						    	async {
						    		frontend = new AcaciaFrontEnd();
						    		frontend.run();
						    	}
						    }
			    }
			    
			  /* val pg:PlaceGroup = Place.places();
			    
			    while (true){
				    for (p in pg){
				       if(p.isDead()){
				            Console.OUT.println("--->Place ID: " + p.id + " is dead.");
				       }else{
				       		Console.OUT.println("Place ID: " + p.id + " is alive.");
				       }
				    }
				    
				    System.sleep(1000);
			    }*/
			    
			    //}
			    /*for (p in Place.places()){
				    val curHost:String = PlaceToNodeMapper.getHost(p.id);
				    
				    Console.OUT.println("XXXXXXXX Place to node mapper XXXXXXXXXXXXXXXXXXXXXX");
				    Console.OUT.println("Current host for" +p.id+" is : " + curHost);
				    Console.OUT.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
				     if(hostHashMap.get(curHost) == false){
				        hostHashMap.put(curHost, true);
				        /*if(p.id == 0){
				        Console.OUT.println("Inside place 0 2nd time");
				         async {
				         	Console.OUT.println("TestPartitioner Place 0");
				         	test.acacia.partitioner.index.TestPartitionIndex.main(null);
				         }
				        }else{
				        Console.OUT.println("Inside place other 2nd time");
				         async{ 
				         Console.OUT.println("TestPartitioner Place other inside async");*/
					   /*     try {
								//at(p)						         
							at(p) async {
						         	Console.OUT.println("TestPartitioner Place " + here.id);
						         	test.acacia.partitioner.index.TestPartitionIndex.main(null);
						         }
					         } catch (e:DeadPlaceException) {
					         	Console.OUT.println(e.place + " died in 2"); // report failure
					         }
					         
				       //  }
				       // }
				     }
                }*/
    	   
    
           //async{
            // val tw:java.lang.Thread = new java.lang.Thread(){
            // public void run(){
            // 	backend = new AcaciaBackEnd();
            // 	backend.run();
            // }
            // };
            // tw.start();
           //}
    
		   // async{
		   //  	backend = new AcaciaBackEnd();
		   //  	backend.run();
		   // }

		/*async
		{
			frontend = new AcaciaFrontEnd();
	       		frontend.run();
		}*/
	       
    	}
        

/*finish{
for (p in Place.places()) {
     	at (p) async {
		Console.OUT.println(here+" says hellowrw and ");
		test.acacia.server.x10.TestAcaciaInstance.main(null);
		while(true);
	}
     }
}*/
    }
    
    public static def uploadGraphDistributed(val item:String, val pathOnNFS:String):void{
    	Console.OUT.println("Uploading the following graph distributed : " + item);
    	
    	//This is the place where we note down the uploading start time
    	val graphID:String = call_runInsert("INSERT INTO ACACIA_META.GRAPH(NAME,UPLOAD_PATH,UPLOAD_START_TIME, UPLOAD_END_TIME,GRAPH_STATUS_IDGRAPH_STATUS,VERTEXCOUNT) VALUES('" + item + "', '" + pathOnNFS + "', '" + call_getCurrentTimeStamp() + "','" + call_getCurrentTimeStamp() + "'," + GraphStatus.LOADING + ",0 )");
    	Console.OUT.println("The new graph id : " + graphID);
    	
    	VertexCounterClient.loadVertexCountInfo();
    	
    	val hadoopNameNodeHost:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.namenode");
    	Console.OUT.println("Got the Zookeeper contact host.");

    	var eMapTableName:String = "edgemap"+graphID;
    	var vMapTableName:String = "vertexmap"+graphID;
    	
    	HDFSInterface.copyOntoHDFSfromLocal(pathOnNFS,"/user/miyuru/input");
    	
    	HDFSInterface.deleteFile("/user/miyuru/notinverts");
    	
    	HadoopOrchestrator.submitJob("jar bin/acacia.jar org.acacia.csr.java.ZeroVertexSearcher");
    	
    	var zeroVertFlag:Boolean = HDFSInterface.fileExists("/user/miyuru/zout/zout");
    	
    	if(!zeroVertFlag){
    		Console.OUT.println("The graph starts from vertex id 1");	
    	}else{
    		Console.OUT.println("The graph has zero vertex");
    	}
    	
    	org.acacia.csr.java.Utils.cleanFiles();
    	
   	    HadoopOrchestrator.submitJob("jar bin/acacia.jar org.acacia.csr.java.WordCount");

   	    HadoopOrchestrator.submitJob("jar bin/acacia.jar org.acacia.csr.java.LineCount");

   	    while(!org.acacia.csr.java.Utils.isReady()){
   		    System.sleep(1000);
   	    }
   		
   	    var vcnt:Long = org.acacia.csr.java.Utils.getTotalVertexCount();
   		
        Console.OUT.println("vcnt:" + vcnt);
   
   	    org.acacia.csr.java.Utils.createHeaderFile();
   		    	    	
    	try{
    		HadoopOrchestrator.submitJob("jar bin/acacia.jar org.acacia.csr.java.CSRConverter /user/miyuru/input " + hadoopNameNodeHost + " " + graphID + " " + java.net.InetAddress.getLocalHost().getHostName() + " " + vcnt + " " + zeroVertFlag);
    	}catch(val ec:java.net.UnknownHostException){
    		ec.printStackTrace();
    	}
    	
    	//Next we upload the file
    	HDFSInterface.copyOntoHDFSfromLocal("/tmp/firstfile", "/user/miyuru/csrconverter-output/firstfile");
    	
    	Console.OUT.println("----------------> Done the CSRJob------------------");
    	
    	//First we need to remove the temp file
    	// for(line in Runtime.execForRead("rm -rf /tmp/dgr").lines()){
    	// 	Console.OUT.println(line);
    	// }
    
        call_runProcessBuilderAndPrintToConsole("rm -rf /tmp/dgr");
    	
    	// for(line in Runtime.execForRead("mkdir /tmp/dgr").lines()){
    	// 	Console.OUT.println(line);
    	// }
        
        call_runProcessBuilderAndPrintToConsole("mkdir /tmp/dgr");
    	
    	HDFSInterface.mergeOnHDFSandCopyOntoLocal("/user/miyuru/csrconverter-output", "/tmp/dgr/grf");    	
    	MetisInterface.partitionWithParMetis("/tmp/dgr/grf", Place.places().size);
           
    	HDFSInterface.copyOntoHDFSfromLocal("/tmp/dgr/grf.part."+Place.places().size, "/user/miyuru/merged");
    	
    	//Load the metis index in to Partitioner Index service.
    	call_loadIndex();
    	
    	HadoopOrchestrator.submitJob("jar bin/acacia.jar org.acacia.partitioner.java.EdgelistPartitioner");
    	val partitionedFileCount:Int = HDFSInterface.getFileCountOnDir("/user/miyuru/merged-out");
    	
    	var ptnArrLst:ArrayList[String] = new ArrayList[String]();
    	var initlaPartitionID:String = null;
    	var initPartFlag:Boolean = false;
    	for(var i:Int = 0n; i < partitionedFileCount; i++){
    		val partitionid:String = call_runInsert("INSERT INTO ACACIA_META.PARTITION(GRAPH_IDGRAPH) VALUES(" + graphID + " )");    			
    		Console.OUT.println("The new partition id : " + partitionid);
    		
    		if(!initPartFlag){
    			initlaPartitionID = partitionid;
    			initPartFlag = true;
    		}
    		
    		ptnArrLst.add(partitionid);
    	}
    	Console.OUT.println("Getting the file list");
    	var fileList:Rail[String] = HDFSInterface.getListofFileNamesOnDir("/user/miyuru/merged-out");
        Console.OUT.println("File list size : " + fileList.size);	
    
    	// var cntr:Int = 0n;
    	val ptnArr:Rail[String] = ptnArrLst.toRail();
    	val hostIDMap:HashMap[String, String] = getLiveHostIDList();
        val initPartID:Int = Int.parseInt(initlaPartitionID);
        var placeID:Int = 0n;
    
    	for (itm in fileList.range()){
            //placeID = Int.parseInt(fileList(itm)) - initPartID;
            val strArr:Rail[String] = fileList(itm).split("_");
            placeID = Int.parseInt(strArr(1).split("-")(0));
    		//HDFSInterface.moveFileTo("/user/miyuru/merged-out/" + fileList(itm), "/user/miyuru/merged-out/" +  ptnArr(cntr));
            HDFSInterface.moveFileTo("/user/miyuru/merged-out/" + fileList(itm), "/user/miyuru/merged-out/" +  placeID);
    		//Also we make the mapping between the partitioned file and the Acacia local server host that will bear the partition.
    		//call_runInsert("INSERT INTO ACACIA_META.HOST_HAS_PARTITION(host_idhost, partition_idpartition, partition_graph_idgraph) VALUES(" + hostIDArr(cntr) + "," + ptnArr(cntr) + "," + graphID + " )");
            call_runInsert("INSERT INTO ACACIA_META.HOST_HAS_PARTITION(host_idhost, partition_idpartition, partition_graph_idgraph) VALUES(" + hostIDMap.get(PlaceToNodeMapper.getHost(placeID)) + "," + ptnArr(placeID) + "," + graphID + " )");
    		Console.OUT.println(fileList(itm));
    		// cntr++;
    	}
    	
    	Console.OUT.println("Submitting the job EdgeDistributor");
    	try{
    		HadoopOrchestrator.submitJob("jar bin/acacia.jar org.acacia.partitioner.java.EdgeDistributor " + hadoopNameNodeHost + " " + graphID + " " + java.net.InetAddress.getLocalHost().getHostName() + " " + vcnt + " " + initlaPartitionID  + " " + zeroVertFlag);
    	}catch(val ec:java.net.UnknownHostException){
    		ec.printStackTrace();
    	}
    	
    	Console.OUT.println("Done...");

    	var id:String = call_runSelect("select IDGRAPH from ACACIA_META.Graph where NAME LIKE '" + item + "'")(0);
    	System.sleep(5000);//Sleep in milliseconds, we need to stay for a while before setting the default graph. The databases take some time to initailize.
    
        //Next, we move on to downloading the partitions onto the local disk. We have to be careful in this case not to download all the
        //files at once in such a way that we may exceed the free disk space of the place 0 node.
        
        /* ------------------------------------------------------------------------------------------------------------------------ */
    
        var fileList2:Rail[String] = HDFSInterface.getListofFileNamesOnDir("/user/miyuru/edgedistributed-out");
        val listOfFiles:ArrayList[String] = new ArrayList[String]();
        val sb:StringBuilder = new StringBuilder();
        val sb2:StringBuilder = new StringBuilder();
    
        for (itm in fileList.range()) {
            val fileName:String = fileList(itm); 
            if(fileName.indexOf("part-") == -1n){
              var st2:String = null;    			
              val st:String = fileList(itm).split("partition_")(1);
              st2 = st.split("-")(0);		

              if(!listOfFiles.contains(st2)){
                 listOfFiles.add(st2);
                 sb2.add(st2 + "\n");
              }
    
              //We take the file to temp directory first
              sb.add("/user/miyuru/edgedistributed-out/"+fileName + "\n");
            }
        }
    
        var fil:java.io.File = new java.io.File("/tmp/dgr/partfilelist");
    
        try{
            var bw:java.io.BufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter(fil));
    
            bw.write(sb.toString());
            bw.flush();
            bw.close();
        }catch(val exc:java.io.IOException){
            Console.OUT.println("Could not write the file: /tmp/dgr/partfilelist");
        }

        //Need not to add nopt file separately.
        //sb2.add("nopt\n");
    
        fil = new java.io.File("/tmp/dgr/partlist");
    
        try{
            var bw:java.io.BufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter(fil));
    
            bw.write(sb2.toString());
            bw.flush();
            bw.close();
        }catch(val exc:java.io.IOException){
            Console.OUT.println("Could not write the file: /tmp/dgr/partlist");
        }
    
        HDFSInterface.copyOntoHDFSfromLocal("/tmp/dgr/partfilelist", "/user/miyuru/partfilelist");
        HDFSInterface.copyOntoHDFSfromLocal("/tmp/dgr/partlist", "/user/miyuru/partlist");
    
        HadoopOrchestrator.submitJob("jar bin/acacia.jar org.acacia.partitioner.java.FileMover");
        HadoopOrchestrator.submitJob("jar bin/acacia.jar org.acacia.partitioner.java.FileMerger");
    
        fileList = HDFSInterface.getListofFileNamesOnDir("/user/miyuru/edgedistributed-out-filtered") as Rail[String];
    
        var theDir:File = new File("/tmp/dgr");
    
        if(!theDir.exists()){
            theDir.mkdir();
        }
    
        //The format of the Quota map is <filename><filesize(vertex+edge)>
        var quotaMap:java.util.HashMap = new java.util.HashMap();
        var quotaMapIndex:java.util.HashMap = new java.util.HashMap();
        var iIndex:Long = 0;

        // //We need to pick up all the private hosts
        // val hosts:Rail[String] = org.acacia.util.Utils.getPrivateHostList();
        // for(host in hosts){
        //     
        // }
    
        Console.OUT.println("JKJKJKLLLLLLLLLLLLLLLLLLLLLLACACACA");
    
        for (itm in fileList.range()) {
            val fileName:String = fileList(itm); 

            if(!fileName.equals("nopt")){
                val fileIndex:Int = Int.parseInt(fileName) - initPartID;
                HDFSInterface.copyOntoLocal("/user/miyuru/edgedistributed-out-filtered/"+fileName, "/tmp/dgr/" + fileName);			    
                HDFSInterface.copyOntoLocal("/user/miyuru/merged-out/"+fileIndex, "/tmp/dgr/v-" + fileName);
                val fsize = new java.io.File("/tmp/dgr/v-" + fileName).length();
                val sizeV:Double = fsize as Double/(1024*1024) as Double;
                val fsize2 = new java.io.File("/tmp/dgr/" + fileName).length();
                val sizeE:Double = fsize2 as Double/(1024.0d*1024.0d) as Double;
                quotaMap.put(iIndex, (sizeV + sizeE));
                iIndex++;
            }
        }
    
        /* ------------------------------------------------------------------------------------------------------------------------ */
        //By this step we have partitioned the graph. From here onwards it will be distributing 
        //the partitions to workers and populating the central tables as well as the meta-data
        //tables
    	distributeGraphPartitions(id, vcnt, initPartID); //Next we insert edges
    	MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET UPLOAD_END_TIME='" + call_getCurrentTimeStamp() + "', GRAPH_STATUS_IDGRAPH_STATUS=" + GraphStatus.OPERATIONAL + " WHERE IDGRAPH=" + id);
    	System.sleep(5000);//Sleep in milliseconds
    }
    
    
    public static def uploadGraphLocally(val item:String, val inputFilePath:String):void{
    Console.OUT.println("Uploading the following graph locally : " + item);
    val converter:MetisPartitioner = new MetisPartitioner();
    
    val isDistrbutedCentralPartitions:Boolean = true;
    val graphID:String = call_runInsert("INSERT INTO ACACIA_META.GRAPH(NAME,UPLOAD_PATH,UPLOAD_START_TIME, UPLOAD_END_TIME,GRAPH_STATUS_IDGRAPH_STATUS,VERTEXCOUNT) VALUES('" + item + "', '" + inputFilePath + "', '" + Utils_Java.getCurrentTimeStamp() + "','" + Utils_Java.getCurrentTimeStamp() + "'," + GraphStatus.LOADING + ",0 )");
    //org.acacia.server.runtime.location
    //org.acacia.partitioner.local.threads
    val nThreads:Int = Int.parse(Utils.call_getAcaciaProperty("org.acacia.partitioner.local.threads"));//4n; //This should be ideally determined based on the number of hardware threads available on each host.
    val nPlaces:Int = AcaciaManager.getNPlaces(org.acacia.util.Utils.getPrivateHostList()(0));
    Console.OUT.println("NNNNNNNNNNNNN--->nPlaces:" + nPlaces);
    converter.convert(item, graphID, inputFilePath, Utils.call_getAcaciaProperty("org.acacia.server.runtime.location"), nPlaces, isDistrbutedCentralPartitions, nThreads, nPlaces);
    val initialPartID:Int = converter.getInitlaPartitionID();
    //val lst:x10.interop.Java.array[x10.lang.String] = converter.getPartitionFileList();
    var batchUploadFileList:Rail[String] = converter.getPartitionFileList();
                
        var ptnArrLst:Rail[String] = converter.getPartitionIDList();
        
        
        Console.OUT.println("+++++++++++++++++A");
        val itr:Iterator[Place] = Place.places().iterator();
        val placeToHostMap:HashMap[Long, String] = new HashMap[Long, String]();
         
        //while(itr.hasNext()){
        for(var p:Int = 0n; p < nPlaces; p++){
             Console.OUT.println("+++++++++++++++++K p.id " + p);
             
             val hostName:String = PlaceToNodeMapper.getHost(p);

             Console.OUT.println("+++++++++++++++++K p.id " + p + " hostName : " + hostName);
             placeToHostMap.put(p, hostName);
            Console.OUT.println("+++++++++++++++++B");
        }
        Console.OUT.println("+++++++++++++++++C");
        Console.OUT.println("placeToHostMap.entries() : " + placeToHostMap.entries().size());
        var itr2:Iterator[x10.util.Map.Entry[Long, String]] = placeToHostMap.entries().iterator();
        Console.OUT.println("+++++++++++++++++C");
        
        val hostIDMap:HashMap[String, String] = getLiveHostIDList();
        var i:Long = 0;
        val fileListLen = batchUploadFileList.size;
        
        while(itr2.hasNext()){
             val itemHost:x10.util.Map.Entry[Long, String] = itr2.next();
             if(itemHost==null){
             	return;
             }
             
             //0 : <host> : /home/miyurud/tmp/61_254.gz
             val filePath:String = batchUploadFileList(i);
             Console.OUT.println("---->>>filePath : " + filePath);
             val partitionID:String = filePath.substring(filePath.indexOf("_")+1n, filePath.indexOf("."));
             AcaciaManager.batchUploadFile(itemHost.getValue(), PlaceToNodeMapper.getInstancePort(itemHost.getKey()), Long.parse(graphID), batchUploadFileList(i), PlaceToNodeMapper.getFileTransferServicePort(itemHost.getKey()));
             Console.OUT.println("========================>Super2");
             //Once we are done with batch uploading the partition file, we need to update the related tables.
             call_runInsert("INSERT INTO ACACIA_META.HOST_HAS_PARTITION(host_idhost, partition_idpartition, partition_graph_idgraph) VALUES(" + hostIDMap.get(itemHost.getValue()) + "," + partitionID + "," + graphID + ")");
             
             val vcount:Long = AcaciaManager.countVertices(""+itemHost.getValue(), graphID, partitionID);
             Console.OUT.println("** vcnt : " + vcount);
             val ecount:Long = AcaciaManager.countEdges(""+itemHost.getValue(), graphID, partitionID);
             Console.OUT.println("** ecnt : " + ecount);
             
             val result:Boolean = call_runUpdate("UPDATE ACACIA_META.PARTITION SET VERTEXCOUNT=" + vcount + ", EDGECOUNT=" + ecount + " WHERE GRAPH_IDGRAPH=" + graphID + " and IDPARTITION=" + partitionID);
             Console.OUT.println("Result is : " + result);
             i++;
             
             if(i >= fileListLen){
                 break;
             }
        }
        Console.OUT.println("+++++++++++++++++D");
        MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET UPLOAD_END_TIME='" + call_getCurrentTimeStamp() + "', GRAPH_STATUS_IDGRAPH_STATUS=" + GraphStatus.OPERATIONAL + " WHERE IDGRAPH=" + graphID);
    }
    
    public static def uploadRDFGraphLocally(val item:String, val inputDirectory:String):void{
	    Console.OUT.println("Uploading the following graph locally : " + item);
	    val rdfPartitioner:AcaciaRDFPartitioner = new AcaciaRDFPartitioner();
	    
	    val isDistrbutedCentralPartitions:Boolean = true;
	    val graphID:String = call_runInsert("INSERT INTO ACACIA_META.GRAPH(NAME,UPLOAD_PATH,UPLOAD_START_TIME, UPLOAD_END_TIME,GRAPH_STATUS_IDGRAPH_STATUS,VERTEXCOUNT) VALUES('" + item + "', '" + inputDirectory + "', '" + Utils_Java.getCurrentTimeStamp() + "','" + Utils_Java.getCurrentTimeStamp() + "'," + GraphStatus.LOADING + ",0 )");
	    
	    rdfPartitioner.readDirectory(inputDirectory);
	    	    
	    val edgeListPath = rdfPartitioner.getEdgeList();
	    
	    val nThreads:Int = Int.parse(Utils.call_getAcaciaProperty("org.acacia.partitioner.local.threads"));//4n; //This should be ideally determined based on the number of hardware threads available on each host.

	    val nPlaces:Int = AcaciaManager.getNPlaces(org.acacia.util.Utils.getPrivateHostList()(0));
	    rdfPartitioner.convert(item, graphID, edgeListPath, Utils.call_getAcaciaProperty("org.acacia.server.runtime.location"), nPlaces, isDistrbutedCentralPartitions, nThreads, nPlaces);
	    rdfPartitioner.distributePartitionedData();
	    
	    MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET UPLOAD_END_TIME='" + call_getCurrentTimeStamp() + "', GRAPH_STATUS_IDGRAPH_STATUS=" + GraphStatus.OPERATIONAL + " WHERE IDGRAPH=" + graphID);
    }
       
    /**
     * This method distributed the graph partitions from HDFS on to the local servers.
     * This is where we need to deal with the workers. Until this point it is only the master who is doing this process.
     */
    private static def distributeGraphPartitions(val graphID:String, val vcnt:Long, val initPartID:Int){
//         var fileList:Rail[String] = HDFSInterface.getListofFileNamesOnDir("/user/miyuru/edgedistributed-out");
//     	val listOfFiles:ArrayList[String] = new ArrayList[String]();
//     	val sb:StringBuilder = new StringBuilder();
//     	val sb2:StringBuilder = new StringBuilder();
//     	
//     	for (itm in fileList.range()) {
//     		val fileName:String = fileList(itm); 
//     		if(fileName.indexOf("part-") == -1n){
//     			var st2:String = null;    			
//     			val st:String = fileList(itm).split("partition_")(1);
//     			st2 = st.split("-")(0);		
// 
//     			if(!listOfFiles.contains(st2)){
//     				listOfFiles.add(st2);
//     				sb2.add(st2 + "\n");
//     			}
//     			//We take the file to temp directory first
//     			sb.add("/user/miyuru/edgedistributed-out/"+fileName + "\n");
//     		}
//     	}
//     	
//     	var fil:java.io.File = new java.io.File("/tmp/dgr/partfilelist");
//     	
//     	try{
//     		var bw:java.io.BufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter(fil));
//     		
//     		bw.write(sb.toString());
//     		bw.flush();
//     		bw.close();
//     	}catch(val exc:java.io.IOException){
//     		Console.OUT.println("Could not write the file: /tmp/dgr/partfilelist");
//     	}
// 
//         //Need not to add nopt file separately.
//     	//sb2.add("nopt\n");
//     	
//     	fil = new java.io.File("/tmp/dgr/partlist");
//     	
//     	try{
//     		var bw:java.io.BufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter(fil));
//     		
//     		bw.write(sb2.toString());
//     		bw.flush();
//     		bw.close();
//     	}catch(val exc:java.io.IOException){
//     		Console.OUT.println("Could not write the file: /tmp/dgr/partlist");
//     	}
//     	
//     	HDFSInterface.copyOntoHDFSfromLocal("/tmp/dgr/partfilelist", "/user/miyuru/partfilelist");
//     	HDFSInterface.copyOntoHDFSfromLocal("/tmp/dgr/partlist", "/user/miyuru/partlist");
//     	
//     	HadoopOrchestrator.submitJob("jar bin/acacia.jar org.acacia.partitioner.java.FileMover");
//     	HadoopOrchestrator.submitJob("jar bin/acacia.jar org.acacia.partitioner.java.FileMerger");
//     	
//     	fileList = HDFSInterface.getListofFileNamesOnDir("/user/miyuru/edgedistributed-out-filtered") as Rail[String];
//     	
//     	var theDir:File = new File("/tmp/dgr");
//     	
//     	if(!theDir.exists()){
//     		theDir.mkdir();
//     	}
//     
//         //The format of the Quota map is <filename><filesize(vertex+edge)>
//         var quotaMap:java.util.HashMap = new java.util.HashMap();
//         var quotaMapIndex:java.util.HashMap = new java.util.HashMap();
//         var iIndex:Long = 0;
// 
//         // //We need to pick up all the private hosts
//         // val hosts:Rail[String] = org.acacia.util.Utils.getPrivateHostList();
//         // for(host in hosts){
//         //     
//         // }
//         
//         Console.OUT.println("JKJKJKLLLLLLLLLLLLLLLLLLLLLLACACACA");
//         
//         for (itm in fileList.range()) {
// 		    val fileName:String = fileList(itm); 
// 		    if(!fileName.equals("nopt")){
// 		        val fileIndex:Int = Int.parseInt(fileName) - initPartID;
// 			    HDFSInterface.copyOntoLocal("/user/miyuru/edgedistributed-out-filtered/"+fileName, "/tmp/dgr/" + fileName);			    
// 			    HDFSInterface.copyOntoLocal("/user/miyuru/merged-out/"+fileIndex, "/tmp/dgr/v-" +fileList fileName);
// 			    val fsize = new java.io.File("/tmp/dgr/v-" + fileName).length();
// 			    val sizeV:Double = fsize as Double/(1024*1024) as Double;
// 			    val fsize2 = new java.io.File("/tmp/dgr/" + fileName).length();
// 			    val sizeE:Double = fsize2 as Double/(1024.0d*1024.0d) as Double;
//                             quotaMap.put(iIndex, (sizeV + sizeE));
//                             iIndex++;
// 		    } 
// 	    }
    
        /*----------------------------------- At this point we use the temp work dir --------------------------*/
        var quotaMapIndex:java.util.HashMap = new java.util.HashMap(); 
        var quotaMap:java.util.HashMap = new java.util.HashMap();
        var iIndex:Long = 0;
        var fileList:ArrayList[String] = new ArrayList[String]();
        fileList.add("/home/miyurud/tmp/dgr/");
        
        
        //quotaMap.add();
        
	    
        Console.OUT.println("----Required storage quota is as follows------");
        var itr2:java.util.Iterator = quotaMap.entrySet().iterator();
        
        while(itr2.hasNext()){
	        val pairs = itr2.next() as java.util.Map.Entry;
	        Console.OUT.println("" + pairs.getKey() + " value : " + pairs.getValue());
        }
        Console.OUT.println("----AAAABBBBBBCCCCCCCCCCCCDDDDDDDDDDEEEE------");
        
	    /*------------------------------------*/
	    //At this point we need to check whether we have enough space in the Acacia cluster to store this new graph. We use the MigrationManager
	    //to solve the Binpacking problem and get the required partition-host allocations.
	    
	    var hMapAllocStrat:java.util.HashMap = MigrationManager.getAllocationStrategy(quotaMap);
	    var itr:java.util.Iterator = hMapAllocStrat.entrySet().iterator();
	    
	    while(itr.hasNext()){
	        val pairs = itr.next() as java.util.Map.Entry;
	        Console.OUT.println("" + pairs.getKey() + " value : " + pairs.getValue());
	    }
	    
	    Console.OUT.println("ABCD1***");
        //By this time we have the partition-host allocation plan with us. Next step is to distribute the partitions to the appropriate host
	//     for (itm in fileList.range()) {
	// 	    val fileName:String = fileList(itm); 
	// 	    Console.OUT.println("ABCD2*** : " + fileName);
	// 	    if(!fileName.equals("nopt")){
	// 	        //This host will be the one that was updated to Acacia MedaDB previously by resolving through PlaceToNodeMapper class.
	// 		    val hostName:String = call_runSelect("select name from ACACIA_META.host where idhost in (select host_idhost from ACACIA_META.host_has_partition where partition_idpartition = " + fileName + " and partition_graph_idgraph = " + graphID + ")")(0);
	// 		    //HDFSInterface.copyOntoLocal("/user/miyuru/edgedistributed-out-filtered/"+fileName, "/tmp/dgr/" + fileName);
	// 		    Console.OUT.println("ABCD3*** : " + hostName);
	// 		    //Before we do the batch uploading we need to update the vertex and edge count of each partition on the metadb partition information table
	// 		    var vcount:Long = 0;
	// 		    var ecount:Long = 0;
	// 		    val placeID:Long = Long.parseLong(fileName) - initPartID;
	// 		    
	// 		    Console.OUT.println("ABCD4***");
	// 		    
	// 		    try{
	// 		        //This is for vertices
	// 			    val r:java.lang.Runtime = java.lang.Runtime.getRuntime();
	// 			    Console.OUT.println("ABCD5***");
	// 			    var p:java.lang.Process = r.exec("wc -l /tmp/dgr/v-" + fileName);
	// 			    Console.OUT.println("ABCD6***");
	// 			    p.waitFor();
	// 			    Console.OUT.println("ABCD7***");
	// 			    var b:BufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	// 			    var line2:String = "";
	// 			    Console.OUT.println("ABCD8***");
	// 
	// 			    while((line2=b.readLine())!= null){
	// 			    	vcount = Long.parse(line2.split(" ")n(0));
	// 			    	break;
	// 			    }	
	// 			    
	// 			    //This is for edges
	// 			    p = r.exec("wc -l /tmp/dgr/" + fileName);
	// 			    p.waitFor();
	// 			    
	// 			    b = new BufferedReader(new InputStreamReader(p.getInputStream()));  
	// 			    
	// 			    Console.OUT.println("ABCD9***");
	// 			    
	// 			    while((line2=b.readLine())!= null){
	// 				    ecount = Long.parse(line2.split(" ")(0));
	// 				    break;
	// 			    }
	// 			    
	// 			    Console.OUT.println("ABCD10");
	// 		    }catch(val c:java.lang.InterruptedException){
	// 		    	c.printStackTrace();
	// 		    }catch(val d:java.io.IOException){
	// 		        d.printStackTrace();
	// 		    }
	// 		    
	// 		    val result:Boolean = call_runUpdate("UPDATE ACACIA_META.PARTITION SET VERTEXCOUNT=" + vcount + ", EDGECOUNT=" + ecount + " WHERE GRAPH_IDGRAPH=" + graphID + " and IDPARTITION=" + fileName);
	// 		    Console.OUT.println("Result is : " + result);
	// 		    
	// 		    //Next we compress the files using gzip. The system's default gzip will be used for this purpose.
	// 		    for(line in Runtime.execForRead("gzip "+ "/tmp/dgr/" + fileName).lines()){
	// 		    	Console.OUT.println(line);
	// 		    } 
	// 		    
	// 		    //call_batchUploadFile(String, int, long, String)
	// 		    //PlaceToNodeMapper.getInstancePort(p.id)
	// 		    call_batchUploadFile(hostName, PlaceToNodeMapper.getInstancePort(placeID), Long.parse(graphID), "/tmp/dgr/" + fileName + ".gz");
	// 		    
	// 		    //HDFSInterface.copyOntoLocal("/user/miyuru/merged-out/"+fileName, "/tmp/dgr/v-" + fileName);
	// 		    for(line in Runtime.execForRead("gzip "+ "/tmp/dgr/v-" + fileName).lines()){
	// 		    	Console.OUT.println(line);
	// 		    }
	// 		    
	// 		    Console.OUT.println("batch uploader connecting to : " + hostName + " : " + PlaceToNodeMapper.getFileTransferServicePort(placeID));
	// 		    
	// 		    call_batchUploadFile(hostName, PlaceToNodeMapper.getInstancePort(placeID), Long.parse(graphID), "/tmp/dgr/v-" + fileName + ".gz");
	// 	    }
	//     }
	    
    	Console.OUT.println("Done distributing partitions.");
        //Next we have to split the big nopt file located in the HDFS in to nvertex/1M number of pices
    	HadoopOrchestrator.submitJob("jar bin/acacia.jar org.acacia.partitioner.java.NoptSplitter " + ((x10.lang.Math.round(vcnt/1000000d) + 1) as Long));
    	//fileList = HDFSInterface.getListofFileNamesOnDir("/user/miyuru/nopt-distributed");
    	Console.OUT.println("Populating central tables.");
    	//var itmCntr:Int = 0n;
    	val numAsyncPerTime:Int = 4n; //We run only 4 async tasks per time.
    	//val arrLen:Long = fileList.size;
        //Console.OUT.println("----> arrLen:" + arrLen);
        //val ecnt:Long = org.acacia.csr.java.Utils.getTotalEdgeCount();
        //Console.OUT.println("Total edge count : " + ecnt);
        //We will create arrLen number of central store tables.
        //MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET CENTRALPARTITIONCOUNT=" + arrLen + ", VERTEXCOUNT=" + vcnt + ", EDGECOUNT=" + ecnt + " WHERE IDGRAPH=" + graphID);
    
    	var cntr:Int = 0n;
	    var selectedMaxJ:Long = 0;
	    //We have to be careful not to exceed the number of partitions.
	    // if(numAsyncPerTime > arrLen){
	    // 	selectedMaxJ = arrLen;
	    // }else{
	    // 	selectedMaxJ = numAsyncPerTime;
	    // }
	    //This is the main memory bottleneck. For Twitter11M it throws mem out of error.
//     	for (var i:Long = 0; i < arrLen; i++ ) {           
//     		val maxJ:Long = i + selectedMaxJ;
//     		finish{
//     			for (var j:Long = i; j < maxJ; j++ ) {
//     				val fileName:String = fileList(j);
//     				val asyncVal:Long = j;
//                     Console.OUT.println("==========>Now file is : " + fileName);
//     				async{
//     					Console.OUT.println("file is : " + "/user/miyuru/nopt-distributed/" + fileName);
//     					HDFSInterface.copyOntoLocal("/user/miyuru/nopt-distributed/" + fileName, "/tmp/dgr/nopt" + asyncVal);
//                         Console.OUT.println("AAAAAAA123");
//     					org.acacia.centralstore.java.HSQLDBInterface.initDBSchema(graphID, ""+asyncVal);
//                         Console.OUT.println("AAAAAAA456");
//     					org.acacia.centralstore.java.HSQLDBInterface.createTable(graphID, ""+asyncVal, "CREATE TABLE IF NOT EXISTS acacia_central.edgemap(idgraph INT NOT NULL, idfrom INT NOT NULL, idto INT NOT NULL, idpartfrom INT NOT NULL, idpartto INT NOT NULL);");
//     					Console.OUT.println("AAAAAAA7");
//     					var c:Connection = org.acacia.centralstore.java.HSQLDBInterface.getConnection(graphID, ""+asyncVal);
//                         Console.OUT.println("AAAAAAA8");
//     					try{
//     						c.setAutoCommit(false);
//                             Console.OUT.println("AAAAAAA9");
//     //The output format will be <startID><startPartitionID><endPartitionID><endID>
//     						var prep:PreparedStatement = c.prepareStatement("INSERT INTO acacia_central.edgemap(idgraph, idfrom, idto, idpartfrom, idpartto) VALUES (?,?,?,?,?)");
//                             Console.OUT.println("AAAAAAA10");
//        						val br:BufferedReader = new BufferedReader(new FileReader("/tmp/dgr/nopt" + asyncVal));
//     						var line:String = null;
//     						var strarr:Rail[String] = null;
//     						var lcnt:Int = 0n;
//     						var tcount:Int = 0n;
// 
//     						try{
//     							//Console.OUT.println("Start data loading for data partition : " + asyncVal);
//     							while((line=br.readLine()) != null){
//     								strarr = line.split("\t");
//     								//hMap.put(Long.parseLong(strarr(0)), Long.parseLong(strarr(1)));
//     								lcnt++;
//     
//     								if(lcnt > 100000n){ //Go by 100 thousand steps
//     									tcount+= lcnt;
//     									//Console.OUT.println("updated :" + tcount);
//     									lcnt = 0n;
//     
//     									//Console.OUT.println("Executing the batch.");
//     									prep.executeBatch();
//     									//Console.OUT.println("Done executing the batch.");
//     									//Console.OUT.println("Clearing the prepared statement.");
//     									//prep.close();
//                                         prep.clearBatch();
// 									    //prep
// 									    //prep=c.prepareStatement("INSERT INTO acacia_central.edgemap(idgraph, idfrom, idto, idpartfrom, idpartto) VALUES (?,?,?,?,?)");
//     								}
//     //The output format will be <startID><startPartitionID><endPartitionID><endID>
//     								prep.setString(1n, graphID);
//     								prep.setString(2n, strarr(1));
//     								prep.setString(3n, strarr(4));
//     								prep.setString(4n, strarr(2));
//     								prep.setString(5n, strarr(3));
//     								prep.addBatch();
//     							}
//                                 
//                                 if(lcnt > 0){ //This means we have some more records to be uploaded to the database
//                                     prep.executeBatch();
//                                     prep.close();
//                                 }
//     
//     
//     							Console.OUT.println("Completed Loading " + tcount + " records.");
//     							br.close();
//     							c.commit();
//     							Console.OUT.println("Done commit.");
//     						}catch(val e:java.sql.SQLException){
//     							e.printStackTrace();
//     						}catch(val ec:java.io.IOException){
//     							ec.printStackTrace();
//     						} 
//     					}catch(val e:java.sql.SQLException){
//     						e.printStackTrace();
//     					}catch(val ec:java.io.IOException){
//     						ec.printStackTrace();
//     					}   
//     				}
//     				//itmCntr += 1n;
//     			}
//     		}//end finish
//             i += selectedMaxJ;
//     }//end for
    
    Console.OUT.println("Done populating central tables.");
    }
     
    /**
     * This method provides a list of host ids that are online.
     * The output from this method will be of the form <host full name><host id>
     */
    public static def getLiveHostIDList():HashMap[String, String]{
        // Console.OUT.println("CCCCCCCCCCCC1");
	    val hostNameArr:Rail[String] = call_runSelect("SELECT name,idhost FROM ACACIA_META.HOST");
	    // Console.OUT.println("CCCCCCCCCCCC2");
	    //val result:Array[String] = new Array[String](Place.places().size());
	    val result:HashMap[String, String] = new HashMap[String, String]();
	    // Console.OUT.println("CCCCCCCCCCCC3");
	    // var cntr:Int = 0n;    	    	
	    var hosrArrlist:ArrayList[String] = new ArrayList[String](); 
	    // Console.OUT.println("CCCCCCCCCCCC4");
	    var nm:String = null;
	    // finish for (p in Place.places()){
	    //     Console.OUT.println("==============");
		   //  nm = at(p){
					// 		    return System.getenv("HOSTNAME");
					// 	    };
		   //  Console.OUT.println(nm);
		   //  hosrArrlist.add(nm);
		   //  Console.OUT.println("--------------");
	    // }
	    //val a:ArrayList[String] = new ArrayList[String](); 
	    
	    //The following code should have worked. But the code hangs unexpectedly.
	    
	    // val g:GlobalRef[ArrayList[String]] = new GlobalRef[ArrayList[String]](hosrArrlist);
	    // 
	    // finish for(p in Place.places()){
	    // Console.OUT.println("CCCCCCCCCCCC4 p.id : " + p.id);
	    //     async at(p){
	    //     Console.OUT.println("CCCCCCCCCCCC4-1");
	    //                val hst = System.getenv("HOSTNAME");
	    //                // at(g){
	    //                //        val item = g();
	    //                //        item.add(hst);
	    //                // }
	    //                
	    //     }
	    //     Console.OUT.println("CCCCCCCCCCCC4-2 size of arr: " + hosrArrlist.size());
	    // }

	    val hosts:Rail[String] = org.acacia.util.Utils.getPrivateHostList();//["sc01.sc.cs.titech.ac.jp", "sc02.sc.cs.titech.ac.jp", "sc03.sc.cs.titech.ac.jp", "sc04.sc.cs.titech.ac.jp"];
	    val hostListLen:Int = hosts.size as Int;//Number of hosts cannot be a long value
	    var intermRes:Rail[Long] = new Rail[Long](hostListLen);
	    
	    for(var i:Int=0n; i < hostListLen; i++){
	        hosrArrlist.add(hosts(i));
	    }

	    // Console.OUT.println("CCCCCCCCCCCC4 : " + hostNameArr.size);
	    //val hostList:Rail[String] = Utils_Java.getPrivateHostList();
	    
	    val l:Long = hostNameArr.size;
	    
	    for(var i:Long=0; i < l; i++){
	        // Console.OUT.println("CCCCCCCCCCCC5 : " + hostNameArr(i));
		    val itm:Rail[String] =  hostNameArr(i).split(",");
		    // Console.OUT.println("CCCCCCCCCCCC6 : " + itm(0));
		    
		    //For the moment we assume all the hosts listed in the DB are live.
		    if(hosrArrlist.contains(itm(0))){
			    //result(cntr) = itm(1);
		        //HashMap will be of the format <host name><host id>
		        result.put(itm(0), itm(1));
			    // cntr++;
		    }
		    //Console.OUT.println("CCCCCCCCCCCC7");
	    }

	    return result;
    }   

    
    private static def truncateLocalInstances(){
    	for (p in Place.places()){
		    try {
			    at(p){
			    //call_truncateLocalInstance(System.getenv("HOSTNAME"));
			    call_truncateLocalInstance(PlaceToNodeMapper.getHost(p.id), PlaceToNodeMapper.getInstancePort(p.id));
			    }
		    } catch (e:DeadPlaceException) {
		    	Console.OUT.println(e.place + " died"); // report failure
		    }
	    	
    	}
    }
    
    /**
     * @deprecated
     * This method must be deprecated becase its seems inserting edges in an adhoc manner.
     */
    public static def insertEdge(val host:String, val graphID:Long, val startVert:Long, val endVert:Long){
        if(hostPlaceMap.containsKey(host)){
            val selectedPlace:Long = hostPlaceMap.get(host);
            val res:Boolean = at(Place.places()(selectedPlace)){
                return AcaciaManager.insertEdge(System.getenv("HOSTNAME"), graphID, startVert, endVert);
            };
        }
    }
    
    public static def initGraph(val graphID:Int){    	
    	for (p in Place.places()){
			    try {
				    at(p){
				    Console.OUT.println("Initializing graph at : " + Utils.getHostName());
				    AcaciaManager.initializeGraphOnLocalInstance(Utils.getHostName(), graphID);
				    }
			    } catch (e:DeadPlaceException) {
			    	Console.OUT.println(e.place + " died"); // report failure
			    }
    			
    	}
    	
    }
    
    public static def setDefaultGraph(val graphID:Int){
    	for (p in Place.places()){
		    try {
			    at(p){
			    Console.OUT.println("Setting default graph at : " + Utils.getHostName());
			    //call_setGraph(Utils.getHostName(), graphID);
			    AcaciaManager.setDefaultGraph(Utils.getHostName(), graphID);
			    }
		    } catch (e:DeadPlaceException) {
		    	Console.OUT.println(e.place + " died"); // report failure
		    }
    		
    	}    	
    }
    
    /**
     * This method must be called before running the Acacia server.
     */
    public def init():void{
        var centalStoreLocation:java.io.File = new java.io.File(Utils_Java.getAcaciaProperty("org.acacia.centralstore.location"));
    
        Console.OUT.println("centalStoreLocation:"+centalStoreLocation);
        
        if(!centalStoreLocation.exists()){
           centalStoreLocation.mkdir();
        }
      	    	
    	val schemaName:Rail[String] = call_runSelect("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME LIKE 'ACACIA_META'");
    	
    	if((schemaName == null)||(schemaName.size == 0)){
    		Console.OUT.println("Acacia database does not exist.\r\nNow creating...");
    		call_setupAcaciaMetaDataDB();
    	}

        if(IS_DISTRIBUTED){
	    	//We have to keep the server properties file on HDFS during its operation.
	    	if (!HDFSInterface.fileExists("/user/miyuru/acacia-server.properties")){
	    	    HDFSInterface.copyOntoHDFSfromLocal(Conts.ACACIA_SERVER_PROPS_FILE,"/user/miyuru/acacia-server.properties");
	    	}
        }
    	
    	//var maxPlaces:Long = Place.MAX_PLACES;
        //Note: In the latest X10 API as of April 11 2015, there is no such thing called MAX_PLACES
        
    	
    	//This is for private cluster.
        //This find of dynamic hostname, IP lookup via at() might be better so that we can get to know which host is not working.
        //However this has the limitation of the assumption we make. We assume there is only one place per host...
    	finish for(p in Place.places()){
		    try {
			    val strhst:String = at(p){
			    var hst:String = null;
			    for(line in x10.xrx.Runtime.execForRead("hostname -i").lines()){
			    hst = line;
			    }
			    
			    return hst + ":" + Utils.getHostName();
			    };
			    val strArr:Rail[String] = strhst.split(":");
			    val hstName:String = strArr(1);
			    val hstIP:String = strArr(0);
			    //Need to record this for future use
			    hostPlaceMap.put(hstName, p.id);
			    
			    val insrtID:String = call_runSelect("SELECT count(name) FROM ACACIA_META.HOST WHERE name LIKE '" + hstName + "'")(0);
			    Console.OUT.println("Host status : " + insrtID + " for host " + hstName);
			    Console.OUT.println("Host name of place : " + p.id + " is " + hstName);
			    
			    if(insrtID.equals("0")){
			    val insertID:String = call_runInsert("INSERT INTO ACACIA_META.HOST(NAME, IP, IS_PUBLIC) VALUES('" + hstName + "','" + hstIP + "', " + Utils.isPublic(hstName) + ")");
			    Console.OUT.println("Result is : " + insertID);
			    }else if(insrtID.equals("1")){ //This means there is already a record for the host in the host table. Therefore, have to update that.
			    val result:Boolean = call_runUpdate("UPDATE ACACIA_META.HOST SET IP = '" + hstIP + "', IS_PUBLIC=" + Utils.isPublic(hstName) + " WHERE NAME LIKE '" + hstName + "'");
			    Console.OUT.println("Result is : " + result);
			    }
		    } catch (e:DeadPlaceException) {
		    	Console.OUT.println(e.place + " died"); // report failure
		    }            
    	}
    
        //Next we need to account for public hosts
        val publicHosts:Rail[String] = Utils.getPublicHostList();
        
        for(item in publicHosts.range()){
            if(!publicHosts(item).equals("")){
	            //For the moment we cannot update the IP address of the host because we do not know exactly the IP address. Need to connect to
	            //the public host and check this.
	        
		        val insrtID:String = call_runSelect("SELECT count(name) FROM ACACIA_META.HOST WHERE name LIKE '" + publicHosts(item) + "'")(0);
		        Console.OUT.println("Host status : " + insrtID + " for host " + publicHosts(item));
		   	        
		        if(insrtID.equals("0")){
		        //For the moment IP is set to ''
			        val insertID:String = call_runInsert("INSERT INTO ACACIA_META.HOST(NAME, IP, IS_PUBLIC) VALUES('" + publicHosts(item) + "','', " + Utils.isPublic(publicHosts(item)) + ")");
			        Console.OUT.println("Result is : " + insertID);
		        }else if(insrtID.equals("1")){ //We know its public since it is read from public hosts file.
			        val result:Boolean = call_runUpdate("UPDATE ACACIA_META.HOST SET IP = '', IS_PUBLIC=" + true + " WHERE NAME LIKE '" + publicHosts(item) + "'");
			        Console.OUT.println("Result is : " + result);
		        }
            }
        }
    
    }
    
    public static def runNeo4j(){
    	
    }
    
    //@Native("java", "System.out.println(\"Hi!\" + (#1))")
    @Native("java", "org.acacia.server.AcaciaManager.run()")
    static native def call_server():void;
    
    // @Native("java", "org.acacia.server.AcaciaManager.insertEdge(#1, #2, #3, #4)")
    // static native def call_insertEdge(String, long, long, long):Boolean;
    
    // //batchUploadFile(String host, int port, long graphID, String filePath)
    // @Native("java", "org.acacia.server.AcaciaManager.batchUploadFile(#1, #2, #3, #4, #5)")
    // static native def call_batchUploadFile(String, Int, Long, String, Int):Boolean;
    
    // @Native("java", "org.acacia.server.AcaciaManager.initializeGraphOnLocalInstance(#1, #2)")
    // static native def call_initGraph(String, Int):Boolean;
    
    // @Native("java", "org.acacia.server.AcaciaManager.setDefaultGraph(#1, #2)")
    // static native def call_setGraph(String, Int):Boolean;
    
    // @Native("java", "org.acacia.server.AcaciaManager.countVertices(#1, #2)")
    // static native def call_countVertices(String, String):Long;
    // 
    // @Native("java", "org.acacia.server.AcaciaManager.countVertices(#1, #2, #3)")
    // static native def call_countVertices(String, String, String):Long;
    
    // @Native("java", "org.acacia.server.AcaciaManager.countEdges(#1, #2)")
    // static native def call_countEdges(String, String):Long;
    // 
    // @Native("java", "org.acacia.server.AcaciaManager.countEdges(#1, #2, #3)")
    // static native def call_countEdges(String, String, String):Long;
    
    @Native("java", "org.acacia.server.AcaciaManager.truncateLocalInstance(#1, #2)")
    static native def call_truncateLocalInstance(String, Int):void; 
    
    @Native("java", "org.acacia.metadata.db.java.MetaDataDBInterface.runInsert(#1)")
    static native def call_runInsert(String):String;
    
    @Native("java", "org.acacia.metadata.db.java.MetaDataDBInterface.runUpdate(#1)")
    static native def call_runUpdate(String):Boolean;
    
    @Native("java", "org.acacia.metadata.db.java.MetaDataDBInterface.runSelect(#1)")
    static native def call_runSelect(String):Rail[String];
    
    @Native("java", "org.acacia.util.java.Utils_Java.getCurrentTimeStamp()")
    static native def call_getCurrentTimeStamp():String;
    
    @Native("java", "org.acacia.metadata.db.java.AcaciaHSQLDBComm.setupAcaciaMetaDataDB()")
    static native def call_setupAcaciaMetaDataDB():Boolean;
    
    @Native("java", "org.acacia.partitioner.index.PartitionIndexClient.loadIndex()")
    static native def call_loadIndex():void;
    
    @Native("java", "org.acacia.util.java.Utils_Java.runProcessBuilderAndPrintToConsole(#1)")
    static native def call_runProcessBuilderAndPrintToConsole(String):void;
}