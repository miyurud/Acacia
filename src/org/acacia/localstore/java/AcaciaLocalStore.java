package org.acacia.localstore.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.acacia.util.java.Utils_Java;

/**
 * Class AcaciaLocalStore
 */

public class AcaciaLocalStore extends java.lang.Thread {
	private final String VERTEX_STORE_NAME = "acacia.nodestore.db";
	private final String EDGE_STORE_NAME = "acacia.edgestore.db";
	private final String ATTRIBUTE_STORE_NAME = "acacia.attributestore.db";
	
	private RandomAccessFile vertexStore;
	private RandomAccessFile edgeStore;
	private RandomAccessFile attributeStore;
	
	private int graphID = -1;
	private int partitionID = -1;
	String dataFolder = null;
	
	public AcaciaLocalStore(int graphID, int partitionID){
		this.graphID = graphID;
		this.partitionID = partitionID;
	}
	
	/**
	 * This method creates a local Acacia data store.
	 */
    public void initialize(){
    	if((graphID != -1) && (partitionID != -1)){
    		dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder") + File.separator + graphID + "_" + partitionID;
    	}else{
    		dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
    	}
    	
    	//First we need to check whether the Acacia's data directory exists or not.
    	File fileChk = new File(dataFolder);
    	if(fileChk.exists() && fileChk.isDirectory()){
    		
    	}else{
    		System.out.println("The folder " + dataFolder + " does not exist.");
    		
    		if(fileChk.mkdir()){
    			System.out.println("The folder was created.");	
    			//---------------------------- Vertices --------------------------------------------------------------------------
    			//A random access file behaves like a large array of bytes stored in the file system. 
    			try {
    				//"rw" mode creates the file if not exist.
					vertexStore = new RandomAccessFile(dataFolder + (String) File.separator.toString() + VERTEX_STORE_NAME, "rw");
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}finally{
					try {
						vertexStore.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
    			//---------------------------- Edges --------------------------------------------------------------------------
    			try {
    				//"rw" mode creates the file if not exist.
					edgeStore = new RandomAccessFile(dataFolder + (String) File.separator.toString() + EDGE_STORE_NAME, "rw");
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}finally{
					try {
						edgeStore.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
    			
    			//---------------------------- Attributes --------------------------------------------------------------------------
    			try {
    				//"rw" mode creates the file if not exist.
					attributeStore = new RandomAccessFile(dataFolder + (String) File.separator.toString() + ATTRIBUTE_STORE_NAME, "rw");
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}finally{
					try {
						attributeStore.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
    			
    		}else{
    			System.out.println("Could not create the folder.");
    		}
    	}
    }
    
	public void run(){
		initialize();		
	}
	
	public void addVertex(Object[] attributes){
		
	}
	
	public void addEdge(Object[] attributes){
		
	}
}