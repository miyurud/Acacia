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

package org.acacia.centralstore.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.acacia.localstore.AcaciaLocalStore;
import org.acacia.localstore.AcaciaLocalStoreCatalogManager;
import org.acacia.localstore.AcaciaLocalStoreTypes;
import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Utils_Java;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;

public class AcaciaHashMapCentralStore  extends AcaciaLocalStore {
	private final String VERTEX_STORE_NAME = "acacia.nodestore.db";
	private final String CENTRAL_STORE_NAME = "acacia.centralstore.db";
	private final String ATTRIBUTE_STORE_NAME = "acacia.attributestore.db";//There can be vertices who are stored only in the central store.
																			//in such situations we need to store the attributes as well in the central store
	private int graphID = -1;
	private int partitionID = -1; //This is the central store partition
	private String instanceDataFolderLocation = null;
	private HashMap<Long, HashSet<Long>> localSubGraphMap = null;
	private Kryo kryo = null;
	
	private long vertexCount = 0;
	private long edgeCount = 0;
	
	public AcaciaHashMapCentralStore(int graphID, int partitionID){
		this.graphID = graphID;
		this.partitionID = partitionID;
		
		kryo = new Kryo();
		kryo.register(HashMap.class, new MapSerializer());
		String dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location");//We should create the central store on the working directory. 
		                                                                                       //After that we will transfer that to the instance local directory.
		String gid = graphID + "_" + partitionID;
		instanceDataFolderLocation= dataFolder + "/" + graphID + "_centralstore/" + gid;
		//Logger_Java.info("central store DataFolderLocation : " + instanceDataFolderLocation);
		initialize();
	}
	
	public boolean storeGraph(){
		boolean result = false;
		
        try {
            FileOutputStream stream = new FileOutputStream(instanceDataFolderLocation + File.separator + "acacia.centralstore.db");
            Output output = new Output(stream);
            this.kryo.writeObject(output, localSubGraphMap);
            stream.flush();
            output.close();
            result = true;
        } catch (Exception e) {
        	 e.printStackTrace();
        }
		return result;
	}
	
	public boolean loadGraph(){
		boolean result = false;
		String edgeStorePath = instanceDataFolderLocation + File.separator + "acacia.centralstore.db";
		File f = new File(edgeStorePath);
		
		if(!f.exists()) {
			localSubGraphMap = new HashMap<Long, HashSet<Long>>();
			return result;
		}
		
        try {
            FileInputStream stream = new FileInputStream(edgeStorePath);
            Input input = new Input(stream);
            localSubGraphMap = (HashMap<Long, HashSet<Long>>)this.kryo.readObject(input, HashMap.class);
            input.close();//This will close the FileInputStream as well.
            
            if(localSubGraphMap != null){
            	result = true;
            }else{
            	localSubGraphMap = new HashMap<Long, HashSet<Long>>();
            }
            
            result = true;
        } catch (Exception e) {
            e.printStackTrace();            
        }
        		
		return result;
	}
	
	public void addEdge(Long startVid, Long endVid){
		HashSet<Long> neighbours = localSubGraphMap.get(startVid);
		
		if(neighbours == null){
			neighbours = new HashSet<Long>();
		}
		
		neighbours.add(endVid);
		localSubGraphMap.put(startVid, neighbours);
	}

	public void initialize() {
		String dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location");//We should create the central store on the working directory. 
		File file = new File(instanceDataFolderLocation);
		
		//If the directory does not exist we need to create it first.
		if(!file.isDirectory()){
			File f = new File(dataFolder + "/" + graphID + "_centralstore");
			if(!file.isDirectory()){
				f.mkdir();
			}
			file.mkdir();
		}
		
		//We need to create an empty data structure at the begining.
		localSubGraphMap = new HashMap<Long, HashSet<Long>>();
		
		String record = AcaciaLocalStoreCatalogManager.readCatalogRecord(instanceDataFolderLocation, "head");
		
		if(record == null){
			AcaciaLocalStoreCatalogManager.writeCatalogRecord(instanceDataFolderLocation, "head", ""+AcaciaLocalStoreTypes.HASH_MAP_LOCAL_STORE);
		}
	}
	
	public HashMap<Long, HashSet<Long>> getUnderlyingHashMap(){
		return localSubGraphMap;
	}
	
	public long getVertexCount(){
		if(vertexCount == 0){
			vertexCount = localSubGraphMap.keySet().size();
		}
		
		//System.out.println("<<<< Vertex count : " + vertexCount);
		
		return vertexCount;
	}
	
	public long getEdgeCount(){
		if(edgeCount == 0){ 
			Set<Entry<Long, HashSet<Long>>> entrySet = localSubGraphMap.entrySet();
			Iterator itr = entrySet.iterator();
			
			while(itr.hasNext()){
				Entry<Long, HashSet<Long>> entry = (Entry<Long, HashSet<Long>>) itr.next();
				
				//System.out.println("entry.getValue().size() : " + entry.getValue().size());
				
				edgeCount += entry.getValue().size();
			}
		}
		
		//System.out.println("<<< Edge count : " + edgeCount);
		
		return edgeCount;
	}

	@Override
	public HashMap<Long, Long> getOutDegreeDistributionHashMap() {
		HashMap<Long, Long> result = new HashMap<Long, Long>();
		
		Iterator<Long> itr = localSubGraphMap.keySet().iterator();
		while(itr.hasNext()){
			//result[counter] = localSubGraphMap.get(itr.next()).size();
			Long vertexID = itr.next();
			result.put(vertexID, new Long(localSubGraphMap.get(vertexID).size()));
		}
		
		return result;
	}

	@Override
	public void addVertex(Object[] attributes) {
		
	}
}
