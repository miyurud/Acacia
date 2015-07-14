package org.acacia.resilience;

import x10.compiler.Native;
import x10.util.ArrayList;
import x10.util.HashMap;
import x10.util.Set;
import x10.util.HashSet;
import x10.util.StringBuilder;

import x10.regionarray.Array;
import x10.util.Map.Entry;

import org.acacia.util.Utils;
import org.acacia.util.Conts;
import org.acacia.util.PlaceToNodeMapper;

import org.acacia.server.AcaciaServer;

public class FaultToleranceScheduler {
	/**
	 * Default constructor 
	 */
	public def this() {
	}

	/**
	 * This method partitions the graph with replications.
	 */
	public static def mapReplicationstoPlaces(){
		//val converter:MetisPartitioner = new MetisPartitioner();
	
		val resilienceLevel:Int = Int.parse(Utils.call_getAcaciaProperty("org.acacia.resilience.FaultToleranceScheduler.resilienceLevel"));
		val nPlaces:Int = Place.places().size() as Int;
		val itr:Iterator[Place] = Place.places().iterator();
		val placeToHostMap:HashMap[Long, String] = new HashMap[Long, String]();
		val result:HashMap[Int, String] = new HashMap[Int, String]();
	
		//The three methods to get the nHost - HostLists
		val hostLst:Rail[String] = org.acacia.util.Utils.getPrivateHostList();
		val hostIDMap:HashMap[String, String] = AcaciaServer.getLiveHostIDList();
		var hostList:HashSet[String] = new HashSet[String]();
	
		while(itr.hasNext()){
			val p:Place = itr.next();
			Console.OUT.println("resilience p.id " + p.id);
		
			val hostName:String = PlaceToNodeMapper.getHost(p.id);	
			hostList.add(hostName);
			Console.OUT.println("resilience p.id " + p.id + " hostName : " + hostName);
	
			placeToHostMap.put(p.id, hostName);
			Console.OUT.println("placeToHostMap entry for place" + p.id);
		}
		Console.OUT.println("placeToHostMap Created");
	
		//The three methods to get the nHost - nHost
		val hostLstLen:Int = hostLst.size as Int;
		val hostIDMapLen:Int = hostIDMap.size() as Int;
		val hostListLen:Int = hostList.size() as Int;
		val nHosts:Int = hostListLen;
	
		Console.OUT.println("placeToHostMap.entries() : " + placeToHostMap.entries().size());
		var itr2:Iterator[x10.util.Map.Entry[Long, String]] = placeToHostMap.entries().iterator();

		if(nHosts==1n) {
val counter:Rail[Int] = new Rail[Int](nPlaces);
			var i:Int=0n;
			while(itr2.hasNext()){
				val itemHost:x10.util.Map.Entry[Long, String] = itr2.next();
				if(itemHost==null){
					return;
				}	
				//0 : <host> : /home/miyurud/tmp/61_254.gz
				//val filePath:String = batchUploadFileList(i);
				//val partitionID:String = filePath.substring(filePath.indexOf("_")+1n, filePath.indexOf("."));
				//call_batchUploadFile(itemHost.getValue(), PlaceToNodeMapper.getInstancePort(itemHost.getKey()), Long.parse(graphID), batchUploadFileList(i), PlaceToNodeMapper.getFileTransferServicePort(itemHost.getKey()));
				Console.OUT.println("========================>Super2");
				i++;
			}
		}
		if(nHosts>1) {
		
		}
	
		while(itr2.hasNext()){
			val itemHost:x10.util.Map.Entry[Long, String] = itr2.next();
			if(itemHost==null){
				return;
			}	
			//0 : <host> : /home/miyurud/tmp/61_254.gz
			//val filePath:String = batchUploadFileList(i);
			//val partitionID:String = filePath.substring(filePath.indexOf("_")+1n, filePath.indexOf("."));
			//call_batchUploadFile(itemHost.getValue(), PlaceToNodeMapper.getInstancePort(itemHost.getKey()), Long.parse(graphID), batchUploadFileList(i), PlaceToNodeMapper.getFileTransferServicePort(itemHost.getKey()));
			Console.OUT.println("========================>Super2");
		}
	
		
		//val nThreads:Int = Int.parse(Utils.call_getAcaciaProperty("org.acacia.resilience.FaultToleranceScheduler.resilienceLevel"));//4n; //This should be ideally determined based on the number of hardware threads available on each host.
		//converter.convert(item, graphID, inputFilePath, Utils.call_getAcaciaProperty("org.acacia.server.runtime.location"), Place.places().size() as Int, isDistrbutedCentralPartitions, nThreads, Place.places().size() as Int);
		//val initialPartID:Int = converter.getInitlaPartitionID();
		//val lst:x10.interop.Java.array[x10.lang.String] = converter.getPartitionFileList();
		//var batchUploadFileList:Rail[String] = x10.interop.Java.convert(converter.getPartitionFileList());
		//var ptnArrLst:Rail[String] = x10.interop.Java.convert(converter.getPartitionIDList());
		//val fileListLen = batchUploadFileList.size;
	}
}