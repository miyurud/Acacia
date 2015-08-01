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
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.acacia.util.java.Utils_Java;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;

import org.acacia.log.java.Logger_Java;

public class AcaciaHashMapNativeStore {
	private final String VERTEX_STORE_NAME = "acacia.nodestore.db";
	private final String EDGE_STORE_NAME = "acacia.edgestore.db";
	private final String RELATIONSHIP_STORE_NAME = "acacia.relationshipstore-";//Each of these files will have its own property extension ID
	private final String ATTRIBUTE_STORE_NAME = "acacia.attributestore.db";
	
	private String instanceDataFolderLocation = null;
	//private HashMap<Long, NodeRecord> nodeRecordMap = null;
	//private HashMap<Long,RelationshipRecord> relationshipMap = null;
	
	//The following is just a map of the each vertex with a list of properties.
	//VERTEX_STORE_NAME
	private HashMap<Long, HashSet<String>> vertexPropertyMap;
	//We need to keep the main graph structure as a plain adjacency list since we may want to answer some basic
	//graph algorithms which just needs the adjacency list structure of the graph.
	//This will enable fast access to relationships between vertices
	//EDGE_STORE_NAME
	private HashMap<Long, HashSet<Long>> localSubGraphMap;
	
	//The following is an array of adjacency lists. Each array element corresponds to one type of relationship that
	//exists between two vertices. Hence in this data structure the edges are grouped based on the type of the relationship
	//(i.e., predicate) through which they are linked. Each array element correspond to a particulat predicate.
	//RELATIONSHIP_STORE_NAME
	private HashMap<Long, HashSet<Long>>[] relationshipMapWithProperties;
	
	//The following is an attribute map. The original vertex name properties listed in nodeStore file will be mapped to
	//a property called ID in the attributeMap. In that way we need not to have a separate file called nodeStore.
	//The attributedMap may store both vertex properties as well as edge properties.
	//ATTRIBUTE_STORE_NAME
	private HashMap<Long, HashMap<Integer, HashSet<String>>> attributeMap;
	
	private HashMap<Integer, String> predicateStore; //This is exactly same as the predicate map.
	
	private Kryo kryo = null;
	
	private long vertexCount = 0;
	private long edgeCount = 0;
	private int predicateCount = 0;
	
	public AcaciaHashMapNativeStore(int graphID, int partitionID, String baseDir, boolean isCentralStore){
		kryo = new Kryo();
		kryo.register(HashMap.class, new MapSerializer());
		String dataFolder = baseDir;
		String gid = graphID + "_" + partitionID;
		
		if(!isCentralStore){
			instanceDataFolderLocation= dataFolder + "/" + gid;
		}else{
			instanceDataFolderLocation= dataFolder + "/" + graphID + "_centralstore/" + gid;
		}
		System.out.println("instanceDataFolderLocation:" + instanceDataFolderLocation);
		
		//Logger_Java.info("instanceDataFolderLocation : " + instanceDataFolderLocation);
		initialize();
	}
	
	public boolean storeGraph(){
		boolean result = true;
		
        try {
            FileOutputStream stream = new FileOutputStream(instanceDataFolderLocation + File.separator + EDGE_STORE_NAME);
            Output output = new Output(stream);
            this.kryo.writeObject(output, localSubGraphMap);
            stream.flush();
            output.close();
        } catch (Exception e) {
        	result = false;
        	 e.printStackTrace();
        }
        
        try {
            FileOutputStream stream = new FileOutputStream(instanceDataFolderLocation + File.separator + VERTEX_STORE_NAME);
            Output output = new Output(stream);
            this.kryo.writeObject(output, vertexPropertyMap);
            stream.flush();
            output.close();
        } catch (Exception e) {
        	result = false;
        	 e.printStackTrace();
        }
        
        for(int i=0; i < predicateCount; i++){
	        try {
	            FileOutputStream stream = new FileOutputStream(instanceDataFolderLocation + File.separator + RELATIONSHIP_STORE_NAME + "" + i + ".db");
	            Output output = new Output(stream);
	            this.kryo.writeObject(output, relationshipMapWithProperties[i]);
	            stream.flush();
	            output.close();
	        } catch (Exception e) {
	        	result = false;
	        	 e.printStackTrace();
	        }
        }
        
        try {
            FileOutputStream stream = new FileOutputStream(instanceDataFolderLocation + File.separator + ATTRIBUTE_STORE_NAME);
            Output output = new Output(stream);
            this.kryo.writeObject(output, attributeMap);
            stream.flush();
            output.close();
        } catch (Exception e) {
        	result = false;
        	 e.printStackTrace();
        }
        
		return result;
	}
	
	
	/**
	 * This method adds a single vertex with a single property to the native store's data structure.
	 * If the native store already contains the vertex, and if the property does not exist in its vertexPropertyMap
	 * it will be added to the vertexPropertyMap.
	 * @param vertexID
	 * @param vertexProperty
	 */
	public void addVertexWithProperty(long vertexID, String vertexProperty){
		HashSet<String> vertexSet = vertexPropertyMap.get(vertexID);
		
		if(vertexSet == null){
			vertexSet = new HashSet<String>();
		}
		
		vertexSet.add(vertexProperty);
	}
	
	/**
	 * This method adds a single relationship to the native store which has a specific relationship property. If the predicateID
	 * is a non-negative value, then a record will be added to both the localSubGraphMap as well as to vertexPropertyMap.
	 * If the predicateID is -1, then the relationship will be added to the localSubGraphMap. It will not be added to the 
	 * relationshipMapWithProperties.
	 * @param fromVertex
	 * @param toVertex
	 * @param predicateID
	 */
	public void addRelationship(Long fromVertex, Long toVertex, Integer predicateID){
		//Whether the relationship has an associated type or not, we have to add it to the general adjacency list.
		HashSet<Long> neighboursList = localSubGraphMap.get(fromVertex);
			
		if(neighboursList == null){
			neighboursList = new HashSet<Long>();
			neighboursList.add(toVertex);
			localSubGraphMap.put(toVertex, neighboursList);
		}else{
			neighboursList.add(toVertex);
		}

		if(predicateID > -1){
			if(predicateID < relationshipMapWithProperties.length){
				HashMap<Long, HashSet<Long>> subGraphMap = relationshipMapWithProperties[predicateID];
				HashSet<Long> neighboursList2 = subGraphMap.get(fromVertex);
				
				if(neighboursList2 == null){
					neighboursList2 = new HashSet<Long>();
					neighboursList2.add(toVertex);
					subGraphMap.put(toVertex, neighboursList2);
				}else{
					//Since we already have the reference in the subGraphMap, we need not add neighboursList2 again to the subGraphMap.
					neighboursList2.add(toVertex);
				}
			}else{
				Logger_Java.info("Error: The specified predicate ID is out of range.");
			}
			
			if(predicateID > predicateCount){
				predicateCount = predicateID;
			}
		}
	}
	
	/**
	 * This method adds a predicate record to the native store.
	 * @param predicateID
	 * @param preidateString
	 */
	public void addPredicate(Integer predicateID, String predicateString){
		predicateStore.put(predicateID.intValue(), predicateString);
	}
	
	public void addAttributeByValue(Integer vertexID, Integer relationshipType, String attribute){
		//String attribute = "";
		HashMap<Integer, HashSet<String>> vertexAttributeList = attributeMap.get(vertexID);
		
		if(vertexAttributeList == null){
			vertexAttributeList = new HashMap<Integer, HashSet<String>>();
			HashSet<String> hs = new HashSet<String>();
			hs.add(attribute);
			vertexAttributeList.put(relationshipType, hs);
			attributeMap.put(new Long(vertexID), vertexAttributeList);
		}else{						
			HashSet<String> hs = vertexAttributeList.get(relationshipType);
			if(hs == null){
				hs = new HashSet<String>();
			}
			
			hs.add(attribute);
			vertexAttributeList.put(relationshipType, hs);
			attributeMap.put(new Long(vertexID), vertexAttributeList);
		}
	}
	
	public boolean containsVertex(long vertexID){
		return vertexPropertyMap.containsKey(vertexID);
	}

	public void initialize() {
		File file = new File(instanceDataFolderLocation);
		
		//If the directory does not exist we need to create it first.
		if(!file.isDirectory()){
			file.mkdir();
		}
		
		//We need to create empty data structures at the begining.
//		nodeRecordMap = new HashMap<Long, NodeRecord>();
//		relationshipMap = new HashMap<Long, RelationshipRecord>();
		localSubGraphMap = new HashMap<Long, HashSet<Long>>();
		vertexPropertyMap = new HashMap<Long, HashSet<String>>();
		relationshipMapWithProperties = null;
		attributeMap = new HashMap<Long, HashMap<Integer, HashSet<String>>>();
		predicateStore = new HashMap<Integer, String>();
		System.out.println("Done init");
	}
}