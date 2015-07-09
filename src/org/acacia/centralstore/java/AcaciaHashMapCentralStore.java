package org.acacia.centralstore.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Utils_Java;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;

public class AcaciaHashMapCentralStore {
	private final String VERTEX_STORE_NAME = "acacia.nodestore.db";
	private final String CENTRAL_STORE_NAME = "acacia.centralstore.db";
	private final String ATTRIBUTE_STORE_NAME = "acacia.attributestore.db";
	
	private RandomAccessFile vertexStore;
	private RandomAccessFile centralStore;
	private RandomAccessFile attributeStore;
	
	private int graphID = -1;
	private int partitionID = -1;
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
		String dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder.central");
		String gid = graphID + "_" + partitionID;
		instanceDataFolderLocation= dataFolder + "/" + gid;
		Logger_Java.info("instanceDataFolderLocation : " + instanceDataFolderLocation);
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
			//System.out.println("new neighbour slist for :" + startVid);
		}
		
		neighbours.add(endVid);
		localSubGraphMap.put(startVid, neighbours);
	}

	public void initialize() {
		File file = new File(instanceDataFolderLocation);
		
		//If the directory does not exist we need to create it first.
		if(!file.isDirectory()){
			file.mkdir();
		}
	}
}
