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

package org.acacia.localstore.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.lang.Long;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Utils_Java;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;

/**
 * This class will hold only one subgraph instance of a particular graph. Its essentially the
 * analogy for Neo4j store which was used in earlier Acacia versions. 
 */

public class AcaciaHashMapLocalStore extends AcaciaLocalStore{
	private final String VERTEX_STORE_NAME = "acacia.nodestore.db";
	private final String EDGE_STORE_NAME = "acacia.edgestore.db";
	private final String ATTRIBUTE_STORE_NAME = "acacia.attributestore.db";
	
	private RandomAccessFile vertexStore;
	private RandomAccessFile edgeStore;
	private RandomAccessFile attributeStore;
	
	private int graphID = -1;
	private int partitionID = -1;
	private String instanceDataFolderLocation = null;
	private HashMap<Long, HashSet<Long>> localSubGraphMap = null;
	private Kryo kryo = null;
	
	private long vertexCount = 0;
	private long edgeCount = 0;
	
	public AcaciaHashMapLocalStore(int graphID, int partitionID){
		super(graphID, partitionID);
		this.graphID = graphID;
		this.partitionID = partitionID;
		
		kryo = new Kryo();
		kryo.register(HashMap.class, new MapSerializer());
		String dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
		String gid = graphID + "_" + partitionID;
		instanceDataFolderLocation= dataFolder + "/" + gid;
		Logger_Java.info("instanceDataFolderLocation : " + instanceDataFolderLocation);
		initialize();
	}
	
	public boolean loadGraph(){
		boolean result = false;
		String edgeStorePath = instanceDataFolderLocation + File.separator + "acacia.edgestore.db";
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
	
	public boolean storeGraph(){
		boolean result = false;
		
        try {
            FileOutputStream stream = new FileOutputStream(instanceDataFolderLocation + File.separator + "acacia.edgestore.db");
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
	
	public void addVertex(Object[] attributes){
		
	}
	
	public void addEdge(Long startVid, Long endVid){
		HashSet<Long> neighbours = localSubGraphMap.get(startVid);
		
		if(neighbours == null){
			neighbours = new HashSet<Long>();
			//System.out.println("new neighbour slist for :" + startVid);
		}
		
		neighbours.add(endVid);
		localSubGraphMap.put(startVid, neighbours);
	}
	
	public long getVertexCount(){
		if(vertexCount == 0){
			vertexCount = localSubGraphMap.keySet().size();
		}
		
		System.out.println("<<<< Vertex count : " + vertexCount);
		
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
		
		System.out.println("<<< Edge count : " + edgeCount);
		
		return edgeCount;
	}
	
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(localSubGraphMap != null){
			Set<Entry<Long, HashSet<Long>>> entrySet = localSubGraphMap.entrySet();
			Iterator itr = entrySet.iterator();
			
			while(itr.hasNext()){
				Entry<Long, HashSet<Long>> entry = (Entry<Long, HashSet<Long>>) itr.next();
				sb.append(entry.getKey());
				sb.append("---->[");
				
				Iterator itr2 = entry.getValue().iterator();
				
				while(itr2.hasNext()){
					sb.append(itr2.next());
					if(itr2.hasNext()){
						sb.append(",");
					}
				}
				
				sb.append("]");
				sb.append("\r\n");
			}
		}else{
			sb.append("No data on Local Store...");
		}
		
		return sb.toString();
	}
		
	public void shutdown(){
		
	}

	@Override
	public void initialize() {
		File file = new File(instanceDataFolderLocation);
		
		//If the directory does not exist we need to create it first.
		if(!file.isDirectory()){
			file.mkdir();
		}
	}
}