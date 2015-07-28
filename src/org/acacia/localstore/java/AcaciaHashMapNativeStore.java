package org.acacia.localstore.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.acacia.util.java.Utils_Java;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;

public class AcaciaHashMapNativeStore {
	private final String VERTEX_STORE_NAME = "acacia.nodestore.db";
	private final String RELATIONSHIP_STORE_NAME = "acacia.relationshipstore.db";
	private final String ATTRIBUTE_STORE_NAME = "acacia.attributestore.db";
	
	private String instanceDataFolderLocation = null;
	private HashMap<Long, NodeRecord> nodeRecordMap = null;
	private HashMap<Long,RelationshipRecord> relationshipMap = null;
	private Kryo kryo = null;
	
	private long vertexCount = 0;
	private long edgeCount = 0;
	
	public AcaciaHashMapNativeStore(int graphID){
		kryo = new Kryo();
		kryo.register(HashMap.class, new MapSerializer());
		String dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location")+"/rdfFiles";
		String gid = Integer.toString(graphID);
		instanceDataFolderLocation= dataFolder + "/" + gid;
		//Logger_Java.info("instanceDataFolderLocation : " + instanceDataFolderLocation);
		initialize();
	}
	
	public boolean loadNodes(){
		boolean result = false;
		String nodeStorePath = instanceDataFolderLocation + File.separator + VERTEX_STORE_NAME;
		File f = new File(nodeStorePath);
		
		if(!f.exists()) {
			nodeRecordMap = new HashMap<Long, NodeRecord>();
			return result;
		}
		
        try {
            FileInputStream stream = new FileInputStream(nodeStorePath);
            Input input = new Input(stream);
            nodeRecordMap = (HashMap<Long, NodeRecord>)this.kryo.readObject(input, HashMap.class);
            input.close();//This will close the FileInputStream as well.
            
            if(nodeRecordMap != null){
            	result = true;
            }else{
            	nodeRecordMap = new HashMap<Long, NodeRecord>();
            }
            
            result = true;
        } catch (Exception e) {
            e.printStackTrace();            
        }
		
		return result;
	}
	
	public boolean loadRelationships(){
		boolean result = false;
		String relationshipStorePath = instanceDataFolderLocation + File.separator + RELATIONSHIP_STORE_NAME;
		File f = new File(relationshipStorePath);
		
		if(!f.exists()) {
			relationshipMap = new HashMap<Long, RelationshipRecord>();
			return result;
		}
		
        try {
            FileInputStream stream = new FileInputStream(relationshipStorePath);
            Input input = new Input(stream);
            relationshipMap = (HashMap<Long, RelationshipRecord>)this.kryo.readObject(input, HashMap.class);
            input.close();//This will close the FileInputStream as well.
            
            if(relationshipMap != null){
            	result = true;
            }else{
            	relationshipMap = new HashMap<Long, RelationshipRecord>();
            }
            
            result = true;
        } catch (Exception e) {
            e.printStackTrace();            
        }
		
		return result;
	}
	
	public boolean storeNodeRecord(){
		boolean result = false;
		
        try {
            FileOutputStream stream = new FileOutputStream(instanceDataFolderLocation + File.separator + VERTEX_STORE_NAME);
            Output output = new Output(stream);
            this.kryo.writeObject(output, nodeRecordMap);
            stream.flush();
            output.close();
            result = true;
        } catch (Exception e) {
        	 e.printStackTrace();
        }
		return result;
	}
	
	public boolean storeRelationshipRecord(){
		boolean result = false;
		
        try {
            FileOutputStream stream = new FileOutputStream(instanceDataFolderLocation + File.separator + RELATIONSHIP_STORE_NAME);
            Output output = new Output(stream);
            this.kryo.writeObject(output, relationshipMap);
            stream.flush();
            output.close();
            result = true;
        } catch (Exception e) {
        	 e.printStackTrace();
        }
		return result;
	}
	
	public void addNodeRecord(Long nodeId, NodeRecord nodeRecord){
		nodeRecordMap.put(nodeId, nodeRecord);
	}
	
	public void addRelationshipRecord(Long nodeId, RelationshipRecord relationshipRecord){
		relationshipMap.put(nodeId, relationshipRecord);
	}
	
	public long getNodeRecordSize(){
		if(vertexCount == 0){
			vertexCount = nodeRecordMap.keySet().size();
		}
		
		//System.out.println("<<<< Vertex count : " + vertexCount);
		
		return vertexCount;
	}

	public void initialize() {
		File file = new File(instanceDataFolderLocation);
		
		//If the directory does not exist we need to create it first.
		if(!file.isDirectory()){
			file.mkdir();
		}
		
		//We need to create an empty data structure at the begining.
		nodeRecordMap = new HashMap<Long, NodeRecord>();
		relationshipMap = new HashMap<Long, RelationshipRecord>();
	}
}
