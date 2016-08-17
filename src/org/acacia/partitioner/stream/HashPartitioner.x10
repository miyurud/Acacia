/**
 * Copyright 2016 Acacia Team

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acacia.partitioner.stream;

import x10.compiler.Native;
import org.acacia.util.Utils;
import x10.array.Array;
import x10.util.HashMap;

import org.acacia.server.AcaciaServer;
import org.acacia.centralstore.AcaciaHashMapCentralStore;


/**
 * This class is used to partition the graph using simple hashing.
 */
public class HashPartitioner {
	
	private var nPlaces:Int;
	private var graphID:String;
	private var numberOfPartitions:Int;
	private var centralStoresMap:HashMap[Short, AcaciaHashMapCentralStore];
	private var hosts:Rail[String];

	public def this() {

	}

	/**
	 * Initialize the HashPartitioner.
	 */
	public def init(nPlaces:Int, graphID:String):void {
		this.nPlaces = nPlaces;
		this.graphID = graphID;

		// get the list of available hosts
		hosts = org.acacia.util.Utils.getPrivateHostList();
		centralStoresMap = new HashMap[Short, AcaciaHashMapCentralStore]();
 
		//numberofCentralPartitions equals to numberof hosts. Here we hardcode it for testing purposes.
		//numberOfPartitions = hosts.size();
		numberOfPartitions = 4n;		

		for(var j:short = 0s; j < numberOfPartitions; j++){
			centralStoresMap.put(j, new AcaciaHashMapCentralStore(Int.parseInt(graphID), j));
		}
	}
	
	/**
	 * This method assing given edge(fromVertex & toVertex) to a partition.
	 */	
	public def partition(fromVertex:int, toVertex:int):void {

		//Hash values of vertices
		val hashFromVertex:long = hash(fromVertex);
		val hashToVertex:long = hash(toVertex);
		
		if (hashFromVertex == hashToVertex) {
			//put both vertices in same localStore
			Console.OUT.println("Put vertices in same localstore");
			AcaciaServer.insertEdge(hosts(0), Long.parse(graphID), fromVertex, toVertex);
		} else {
			//put both vertices in centralStore
			Console.OUT.println("Put vertices in centralstore");
			
		        val central:AcaciaHashMapCentralStore = centralStoresMap.get(hashFromVertex as short);
		        central.addEdge(fromVertex, toVertex);
		        Console.OUT.println("Saved crossing edge ");
		}
	}
	
	/**
	 * Save the partitions in hosts disks
	 */
	public def saveToDisk():void {
		for( var i:Int=0n; i < numberOfPartitions; i++) {
			val central:AcaciaHashMapCentralStore = centralStoresMap.get(i as short);
			central.storeGraph();
			Console.OUT.println("Saved centralStores");
		}
	}
	
	/**
	 * Return the hash value for given vertex index & number of partitions
	 */
	private def hash(val vertexIndex:long):long {
		val hash:long;
		
		hash = ( vertexIndex % nPlaces );
		//Console.OUT.println("hash for " + vertexIndex + " = " + hash);
		return hash;
	}
}
