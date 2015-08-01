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
	private final String RELATIONSHIP_STORE_NAME = "acacia.relationshipstore.db";
	private final String ATTRIBUTE_STORE_NAME = "acacia.attributestore.db";
	
	private String instanceDataFolderLocation = null;
	//private HashMap<Long, NodeRecord> nodeRecordMap = null;
	//private HashMap<Long,RelationshipRecord> relationshipMap = null;
	
	//The following is just a map of the each vertex with a list of properties.
	private HashMap<Long, HashSet<Long>> vertexPropertyMap;
	//We need to keep the main graph structure as a plain adjacency list since we may want to answer some basic
	//graph algorithms which just needs the adjacency list structure of the graph.
	//This will enable fast access to relationships between vertices
	private HashMap<Long, HashSet<Long>> localSubGraphMap;
	
	//The following is an array of adjacency lists. Each array element corresponds to one type of relationship that
	//exists between two vertices. Hence in this data structure the edges are grouped based on the type of the relationship
	//(i.e., predicate) through which they are linked. Each array element correspond to a particulat predicate.
	private HashMap<Long, HashSet<Long>>[] relationshipMapWithProperties;
	
	//The following is an attribute map. The original vertex name properties lsted in nodeStore file will be mapped to
	//a property called ID in the attributeMap. In that way we need not to have a separate file called nodeStore.
	//The attributedMap may store both vertex properties as well as edge properties.
	private HashMap<Long, LinkedList<Long>> attributeMap;
	
	private HashMap<Integer, String> predicateStore; //This is exactly same as the predicate map.
	
	private Kryo kryo = null;
	
	private long vertexCount = 0;
	private long edgeCount = 0;
	
	public AcaciaHashMapNativeStore(int graphID, int partitionID){
		kryo = new Kryo();
		kryo.register(HashMap.class, new MapSerializer());
		String dataFolder = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");
		String gid = graphID + "_" + partitionID;
		instanceDataFolderLocation= dataFolder + "/" + gid;
		System.out.println("instanceDataFolderLocation:" + instanceDataFolderLocation);
		//Logger_Java.info("instanceDataFolderLocation : " + instanceDataFolderLocation);
		initialize();
	}
	
	/**
	 * This method adds a single vertex with a single property to the native store's data structure.
	 * If the native store already contains the vertex, and if the property does not exist in its vertexPropertyMap
	 * it will be added to the vertexPropertyMap.
	 * @param vertexID
	 * @param vertexProperty
	 */
	public void addVertexWithProperty(long vertexID, long vertexProperty){
		HashSet<Long> vertexSet = vertexPropertyMap.get(vertexID);
		
		if(vertexSet == null){
			vertexSet = new HashSet<Long>();
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
	public void addRelationship(long fromVertex, long toVertex, int predicateID){
		if(predicateID == -1){
			HashSet<Long> neighboursList = localSubGraphMap.get(fromVertex);
			
			if(neighboursList == null){
				neighboursList = new HashSet<Long>();
				neighboursList.add(toVertex);
				localSubGraphMap.put(toVertex, neighboursList);
			}else{
				neighboursList.add(toVertex);
			}
		}else if(predicateID > -1){
			if(predicateID < relationshipMapWithProperties.length){
				HashMap<Long, HashSet<Long>> subGraphMap = relationshipMapWithProperties[predicateID];
				HashSet<Long> neighboursList = subGraphMap.get(fromVertex);
				
				if(neighboursList == null){
					neighboursList = new HashSet<Long>();
					neighboursList.add(toVertex);
					localSubGraphMap.put(toVertex, neighboursList);
				}else{
					neighboursList.add(toVertex);
				}
			}else{
				Logger_Java.info("Error: The specified predicate ID is out of range.");
			}
		}
	}
	
	/**
	 * This method adds a predicate record to the native store.
	 * @param predicateID
	 * @param preidateString
	 */
	public void addAttributeByValue(int predicateID, String predicateString){
		predicateStore.put(predicateID, predicateString);
	}
	
	public void addAttributeByID(int vertexID, long attributedID){
		LinkedList<Long> vertexAttributeList = attributeMap.get(attributedID);
		
		if(vertexAttributeList == null){
			vertexAttributeList = new LinkedList<Long>();
			vertexAttributeList.add(attributedID);
			attributeMap.put(new Long(vertexID), vertexAttributeList);
		}else{
			vertexAttributeList.add(attributedID);
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
		vertexPropertyMap = new HashMap<Long, HashSet<Long>>();
		relationshipMapWithProperties = null;
		attributeMap = new HashMap<Long, LinkedList<Long>>();
		predicateStore = new HashMap<Integer, String>();
		System.out.println("Done init");
	}
}