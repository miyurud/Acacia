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
import x10.util.ArrayList;
import x10.util.HashMap;

import org.acacia.server.AcaciaServer;
import org.acacia.server.AcaciaManager;
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
	 * Assign partitions to given edges.
	 */	
	public def partition(var edges:ArrayList[String]):void {
		var fromVertex:Int = 0n;
		var toVertex:Int = 0n;
	
		var mapOfPartitions:HashMap[Int, ArrayList[String]] = new HashMap[Int, ArrayList[String]]();
		var localEdges:ArrayList[String];
	
		for (edge in edges) {
			
            val nodes = edge.split(" ");
            fromVertex = Int.parse(nodes(0));
            toVertex = Int.parse(nodes(1));
			
			//Hash values of vertices
			val hashFromVertex:long = hash(fromVertex);
			val hashToVertex:long = hash(toVertex);
		
			if (hashFromVertex == hashToVertex) {

				//put both vertices in same localStore
				//Console.OUT.println("Put vertices in same localstore");
				//AcaciaServer.insertEdge(hosts(0), Long.parse(graphID), hashFromVertex, fromVertex, toVertex);
				localEdges = mapOfPartitions.get(hashFromVertex as Int);

				if(localEdges == null){
				    	localEdges = new ArrayList[String]();
				    	mapOfPartitions.put(hashFromVertex as Int, localEdges);
				}
				   
				localEdges.add(fromVertex + " " + toVertex);

			} else {
				//put both vertices in centralStore
				//Console.OUT.println("Put vertices in centralstore");
			
				val central:AcaciaHashMapCentralStore = centralStoresMap.get(hashFromVertex as short);
				central.addEdge(fromVertex, toVertex);
				//Console.OUT.println("Saved crossing edge ");
			}
		}
		
		val itr:Iterator[x10.util.Map.Entry[Int, ArrayList[String]]] = mapOfPartitions.entries().iterator();
       
		while(itr.hasNext()){
			var entr:x10.util.Map.Entry[Int, ArrayList[String]] = itr.next();
			
			if(AcaciaManager.insertEdges(hosts(0), Long.parseLong(graphID), entr.getKey() as Long, entr.getValue())) {
				Console.OUT.println("In HashPartitioner : edges saved successfully");
			} else {
				Console.OUT.println("In HashPartitioner : failed to save egdes");
			}
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
