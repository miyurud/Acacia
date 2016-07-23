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

package org.acacia.centralstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;

import x10.util.HashMap;
import x10.util.HashSet;
import x10.lang.Iterator;
import x10.util.Set;
import x10.util.Map.Entry;

import org.acacia.localstore.AcaciaLocalStore;
import org.acacia.localstore.AcaciaLocalStoreCatalogManager;
import org.acacia.localstore.AcaciaLocalStoreTypes;
import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Utils_Java;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import x10.interop.Java;
import x10.compiler.NonEscaping;

public class AcaciaHashMapCentralStore  implements AcaciaLocalStore {
	private val VERTEX_STORE_NAME:String = "acacia.nodestore.db";
	private val CENTRAL_STORE_NAME:String = "acacia.centralstore.db";
	private val ATTRIBUTE_STORE_NAME:String = "acacia.attributestore.db";//There can be vertices who are stored only in the central store.
																			//in such situations we need to store the attributes as well in the central store
	private var graphID:Int = -1n;
	private var partitionID:Int = -1n; //This is the central store partition
	private var instanceDataFolderLocation:String = null;
	private var localSubGraphMap:HashMap[Long, HashSet[Long]] = null;
	private var kryo:Kryo = null;
	private val typeMap :java.util.HashMap = new java.util.HashMap();

	private var vertexCount:Long = 0l;
	private var edgeCount:Long = 0l;
	
	public def this(var graphID:Int, var partitionID:Int){
		this.graphID = graphID;
		this.partitionID = partitionID;	

		kryo = new Kryo();
		kryo.register(Java.getClass(typeMap), new MapSerializer());
		var dataFolder:String = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location");//We should create the central store on the working directory. 
		                                                                                       //After that we will transfer that to the instance local directory.
		var gid:String = graphID + "_" + partitionID;
		instanceDataFolderLocation= dataFolder + "/" + graphID + "_centralstore/" + gid;
		//Logger_Java.info("central store DataFolderLocation : " + instanceDataFolderLocation);
		initialize();
	}
	
	public def storeGraph():Boolean{
		var result:Boolean = false;
		
        try {
            var stream:FileOutputStream = new FileOutputStream(instanceDataFolderLocation + File.separator + "acacia.centralstore.db");
            var output:Output = new Output(stream);
            this.kryo.writeObject(output, localSubGraphMap);
            stream.flush();
            output.close();
            result = true;
        } catch (var e:java.io.IOException) {
        	 e.printStackTrace();
        }
		return result;
	}
	
	public def loadGraph():Boolean{
		var result:Boolean = false;

		var edgeStorePath:String = instanceDataFolderLocation + File.separator + "acacia.centralstore.db";
		var f:File = new File(edgeStorePath);
		
		if(!f.exists()) {
			localSubGraphMap = new HashMap[Long, HashSet[Long]]();
			return result;
		}
		
        try {
            var stream:FileInputStream = new FileInputStream(edgeStorePath);
            var input:Input = new Input(stream);
            localSubGraphMap = this.kryo.readObject(input, Java.getClass(typeMap)) as HashMap[Long, HashSet[Long]];
            input.close();//This will close the FileInputStream as well.
            
            if(localSubGraphMap != null){
            	result = true;
            }else{
            	localSubGraphMap = new HashMap[Long, HashSet[Long]]();
            }
            
            result = true;
        } catch (var e1:FileNotFoundException) {
        	e1.printStackTrace();            
        } catch (var e:Exception) {
            e.printStackTrace();            
        }
        		
		return result;
	}
	
	public def addEdge(var startVid:Long, var endVid:Long) : void{
		var neighbours:HashSet[Long] = localSubGraphMap.get(startVid);
		
		if(neighbours == null){
			neighbours = new HashSet[Long]();
		}
		
		neighbours.add(endVid);
		localSubGraphMap.put(startVid, neighbours);
	}

 	@NonEscaping
	public final def initialize() : void {
		var dataFolder:String = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location");//We should create the central store on the working directory. 
		var file:File = new File(instanceDataFolderLocation);
		
		//If the directory does not exist we need to create it first.
		if(!file.isDirectory()){
			var f:File = new File(dataFolder + "/" + graphID + "_centralstore");
			if(!f.isDirectory()){
				f.mkdir();
			}
			file.mkdir();
		}
		
		//We need to create an empty data structure at the begining.
		localSubGraphMap = new HashMap[Long, HashSet[Long]]();
		
		var record:String = AcaciaLocalStoreCatalogManager.readCatalogRecord(instanceDataFolderLocation, "head");
		
		if(record == null){
			AcaciaLocalStoreCatalogManager.writeCatalogRecord(instanceDataFolderLocation, "head", ""+AcaciaLocalStoreTypes.HASH_MAP_LOCAL_STORE);
		}
	}
	
	public def getUnderlyingHashMap():HashMap[Long, HashSet[Long]]{
		return localSubGraphMap;
	}
	
	public def getVertexCount():Long{
		if(vertexCount == 0){
			vertexCount = localSubGraphMap.keySet().size();
		}
		
		//System.out.println("<<<< Vertex count : " + vertexCount);
		
		return vertexCount;
	}
	
	public def getEdgeCount():Long{
		if(edgeCount == 0){ 
			//var entrySet:Set[Entry[Long, HashSet[Long]]] = localSubGraphMap.entrySet();
			var itr:Iterator[x10.util.Map.Entry[Long,HashSet[Long]]] = localSubGraphMap.entries().iterator();
			
			while(itr.hasNext()){
				var entry:Entry[Long, HashSet[Long]] = itr.next() as Entry[Long, HashSet[Long]];
				
				//System.out.println("entry.getValue().size() : " + entry.getValue().size());
				
				edgeCount += entry.getValue().size();
			}
		}
		
		//System.out.println("<<< Edge count : " + edgeCount);
		
		return edgeCount;
	}

	public def getOutDegreeDistributionHashMap() : HashMap[Long, Long] {
		var result:HashMap[Long, Long] = new HashMap[Long, Long]();
		
		var itr:Iterator[Long] = localSubGraphMap.keySet().iterator();

		while(itr.hasNext()){
			//result[counter] = localSubGraphMap.get(itr.next()).size();
			var vertexID:Long = itr.next();
			result.put(vertexID, localSubGraphMap.get(vertexID).size());
		}
		
		return result;
	}

	public def addVertex(var attributes:Rail[Any]) : void {
		
	}
}
