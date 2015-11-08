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

package org.acacia.localstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Set;

import x10.util.HashMap;
import x10.util.HashSet;
import x10.util.Map;
import x10.util.Map.Entry;
import x10.compiler.NonEscaping;
import x10.interop.Java;

import org.acacia.util.Utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;

import org.acacia.log.Logger;
import java.lang.Override;

public class AcaciaHashMapNativeStore implements AcaciaLocalStore{
	private val VERTEX_STORE_NAME:String = "acacia.nodestore.db";
	private val EDGE_STORE_NAME:String = "acacia.edgestore.db";
	private val RELATIONSHIP_STORE_NAME:String = "acacia.relationshipstore-";//Each of these files will have its own property extension ID
	private val ATTRIBUTE_STORE_NAME:String = "acacia.attributestore.db";
	private val PREDICATE_STORE_NAME:String = "acacia.predicate.db";
	private val METADATA_STORE_NAME:String = "acacia.nativemeta.db";
	
	private var instanceDataFolderLocation:String = null;
	//private HashMap<Long, NodeRecord> nodeRecordMap = null;
	//private HashMap<Long,RelationshipRecord> relationshipMap = null;
	
	//The following is just a map of the each vertex with a list of properties.
	//VERTEX_STORE_NAME
	private var vertexPropertyMap:HashMap[Long, HashSet[String]];
	//We need to keep the main graph structure as a plain adjacency list since we may want to answer some basic
	//graph algorithms which just needs the adjacency list structure of the graph.
	//This will enable fast access to relationships between vertices
	//EDGE_STORE_NAME
	private var localSubGraphMap:HashMap[Long, HashSet[Long]] ;
	
	//The following is an array of adjacency lists. Each array element corresponds to one type of relationship that
	//exists between two vertices. Hence in this data structure the edges are grouped based on the type of the relationship
	//(i.e., predicate) through which they are linked. Each array element correspond to a particulat predicate.
	//RELATIONSHIP_STORE_NAME
	//private HashMap<Long, HashSet<Long>>[] relationshipMapWithProperties;
	private var relationshipMapWithProperties:Rail[HashMap[Long, HashSet[Long]]];
	
	//private HashMap<Long, Long>[] hmp;
	
	//The following is an attribute map. The original vertex name properties listed in nodeStore file will be mapped to
	//a property called ID in the attributeMap. In that way we need not to have a separate file called nodeStore.
	//The attributedMap may store both vertex properties as well as edge properties.
	//ATTRIBUTE_STORE_NAME
	private var attributeMap:HashMap[Long, HashMap[Int, HashSet[String]]] ;
	
	private var predicateStore:HashMap[Int, String]; //This is exactly same as the predicate map.
	
	private var metaInfo:HashMap[String, String];
	
	private var kryo:Kryo = null;
	
	private var vertexCount:Long = 0;
	private var edgeCount:Long = 0;
	private var predicateCount:Int = 0n;
	private var partitionID:Int = 0n;
	private var updatedFlagVertex:Boolean = false;
	private var updatedFlagEdge:Boolean  = false;
	
	public def getPredicateCount():Int {
		return predicateCount;
	}

	private val PREDICATE_COUNT:String = "predcnt";
	private val PARTITION_ID:String = "partid";
	private var dataFolder:String;
	private var graphID:Int;
    private var placeID:Int = 0n;
 	   
	public def this(graphID:Int, partitionID:Int, baseDir:String, isCentralStore:Boolean ){
		this.partitionID = partitionID;
		kryo = new Kryo();
		//kryo.register(HashMap.class, new MapSerializer());
		dataFolder = baseDir;
		gid:String = graphID + "_" + partitionID;
		this.graphID = graphID;
		
		if(!isCentralStore){
			instanceDataFolderLocation= dataFolder + "/" + gid;
		}else{
			instanceDataFolderLocation= dataFolder + "/" + graphID + "_centralstore/" + gid;
		}
		Console.OUT.println("instanceDataFolderLocation:" + instanceDataFolderLocation);
		
		//Logger_Java.info("instanceDataFolderLocation : " + instanceDataFolderLocation);
		initialize();
	}

    public def this(graphID:Int, partitionID:Int, baseDir:String, isCentralStore:Boolean, placeID:Int){
    	this.partitionID = partitionID;
    	this.placeID = placeID;
    	kryo = new Kryo();
    	kryo.register(Java.getClass(new HashMap[String, String]()), new MapSerializer());
    	dataFolder = baseDir;
    	var gid:String = graphID + "_" + placeID;
    	this.graphID = graphID;

    	if(isCentralStore){
    		instanceDataFolderLocation= dataFolder + "/" + graphID + "_centralstore/" + gid;
    	}

    	Console.OUT.println("instanceDataFolderLocation:" + instanceDataFolderLocation);
    	initialize();
    }
 	
// 	@SuppressWarnings("unchecked")
	public def loadGraph():Boolean{
		var result:Boolean = false;
		
        var metaInoMapPath:String = instanceDataFolderLocation + File.separator + METADATA_STORE_NAME;
        var f:File  = new File(metaInoMapPath);
		
		if(!f.exists()) {
			metaInfo = new HashMap[String, String]();
			return result;
		}
		
        try {
             var stream:FileInputStream = new FileInputStream(metaInoMapPath);
             var input:Input  = new Input(stream);
             metaInfo = this.kryo.readObject(input, x10.interop.Java.getClass(new HashMap[String, String]())) as HashMap[String, String];
            input.close();//This will close the FileInputStream as well.
             
            if(metaInfo != null){
            	result = true;
            }else{
            	metaInfo = new HashMap[String, String]();
            }
        }catch(e:java.io.IOException){
        	e.printStackTrace(); 
        }
        catch (e:Exception) {
            e.printStackTrace();            
        }

        //Need to initialize the variables with the loaded info.
        Console.OUT.println("metaInfo.size():"+metaInfo.size());
        Console.OUT.println("metaInfo.get(PREDICATE_COUNT):"+metaInfo.get(PREDICATE_COUNT));
        predicateCount = Int.parse(metaInfo.get(PREDICATE_COUNT));
        partitionID = Int.parse(metaInfo.get(PARTITION_ID));
        initializeRelationshipMapWithProperties(predicateCount); //Must initialize the array
        
		edgeStorePath:String = instanceDataFolderLocation + File.separator + EDGE_STORE_NAME;
		f = new File(edgeStorePath);
		
		if(!f.exists()) {
			localSubGraphMap = new HashMap[Long, HashSet[Long]]();
			return result;
		}
		
        try {
        	stream:FileInputStream = new FileInputStream(edgeStorePath);
            input:Input  = new Input(stream);
            //localSubGraphMap = (HashMap[Long, HashSet[Long]])this.kryo.readObject(input, HashMap.class);
            input.close();//This will close the FileInputStream as well.
            
            if(localSubGraphMap != null){
            	result = true;
            }else{
            	localSubGraphMap = new HashMap[Long, HashSet[Long]]();
            }
            
            result = true;
            
        }catch(e:java.io.IOException){
        	e.printStackTrace(); 
        }
        catch (e:Exception) {
            e.printStackTrace();            
        }
        
        var vertexPropertyMapPath:String = instanceDataFolderLocation + File.separator + VERTEX_STORE_NAME;
		f = new File(vertexPropertyMapPath);
		
		if(!f.exists()) {
			vertexPropertyMap = new HashMap[Long, HashSet[String]]();
			return result;
		}
		
        try {
            var stream:FileInputStream = new FileInputStream(vertexPropertyMapPath);
            var input:Input = new Input(stream);
            //vertexPropertyMap = (HashMap[Long, HashSet[String]])this.kryo.readObject(input, HashMap.class);
            input.close();//This will close the FileInputStream as well.
            
            if(vertexPropertyMap != null){
            	result = true;
            }else{
            	vertexPropertyMap = new HashMap[Long, HashSet[String]]();
            }
            
            result = true;
        }catch(e:java.io.IOException){
        	e.printStackTrace(); 
        } catch (e:Exception) {
            e.printStackTrace();            
        }
        
        for(var i:Int = 0n; i < predicateCount; i++){
        	var relationshipMapWithPropertiesPath:String  = instanceDataFolderLocation + File.separator + RELATIONSHIP_STORE_NAME + "" + i + ".db";
        	f = new File(relationshipMapWithPropertiesPath);
    		
    		if(!f.exists()) {
    			relationshipMapWithProperties(i) = new HashMap[Long, HashSet[Long]]();
                continue;
    		}
    		
            try {
                var stream:FileInputStream = new FileInputStream(relationshipMapWithPropertiesPath);
                var input:Input  = new Input(stream);
                //relationshipMapWithProperties(i) = (HashMap[Long, HashSet[Long]])this.kryo.readObject(input, HashMap.class);
                input.close();//This will close the FileInputStream as well.
                
                if(relationshipMapWithProperties(i) != null){
                	result = true;
                }else{
                	//In this case the deserialization did not work as expected.
                	relationshipMapWithProperties(i) = new HashMap[Long, HashSet[Long]]();
                }
                
                result = true;
                
            }catch(e:java.io.IOException){
            	e.printStackTrace(); 
            } catch (e:Exception) {
                e.printStackTrace();            
            }
         }
        
        var attributeMapPath:String = instanceDataFolderLocation + File.separator + ATTRIBUTE_STORE_NAME;
		f = new File(attributeMapPath);
		
		if(!f.exists()) {
			attributeMap = new HashMap[Long, HashMap[Int,HashSet[String]]]();
			return result;
		}
		
        try {
            var stream:FileInputStream = new FileInputStream(attributeMapPath);
            var input:Input  = new Input(stream);
            //attributeMap = (HashMap[Long, HashMap[Int,HashSet[String]])this.kryo.readObject(input, HashMap.class);
            input.close();//This will close the FileInputStream as well.
            
            if(attributeMap != null){
            	result = true;
            }else{
            	attributeMap = new HashMap[Long, HashMap[Int,HashSet[String]]]();
            }
            
            result = true;
        }catch(e:java.io.IOException){
        	e.printStackTrace();
        }
        catch (e:Exception) {
            e.printStackTrace();            
        }
        
        var predicateMapPath:String  = instanceDataFolderLocation + File.separator + PREDICATE_STORE_NAME;
		f = new File(predicateMapPath);
		
		if(!f.exists()) {
			predicateStore = new HashMap[Int, String]();
			return result;
		}
		
        try {
            var stream:FileInputStream = new FileInputStream(predicateMapPath);
            var input:Input = new Input(stream);
            //predicateStore = (HashMap[Int, String])this.kryo.readObject(input, HashMap.class);
            input.close();//This will close the FileInputStream as well.
            
            if(predicateStore != null){
            		result = true;
            }else{
            		predicateStore = new HashMap[Int, String]();
            }
            
            result = true;
        }catch(e:java.io.IOException){
        	e.printStackTrace();
        } 
        catch (e:Exception) {
            e.printStackTrace();            
        }
		
		return result;
	}
	
	public def storeGraph():Boolean{
		var result:Boolean = true;
		
		if(localSubGraphMap != null){
	        try {
	            var stream:FileOutputStream = new FileOutputStream(instanceDataFolderLocation + File.separator + EDGE_STORE_NAME);
	            var output:Output = new Output(stream);
	            this.kryo.writeObject(output, localSubGraphMap);
	            stream.flush();
	            output.close();
	        }catch(e:java.io.IOException){
	        	e.printStackTrace();
	        } 
	        catch (e:Exception) {
	        	result = false;
	        	e.printStackTrace();
	        }
		}else{
			Console.OUT.println("localSubGraphMap is null.");
		}
        
		if(vertexPropertyMap != null){
	        try {
	            var stream:FileOutputStream = new FileOutputStream(instanceDataFolderLocation + File.separator + VERTEX_STORE_NAME);
	            var output:Output = new Output(stream);
	            this.kryo.writeObject(output, vertexPropertyMap);
	            stream.flush();
	            output.close();
	        }catch(e:java.io.IOException){
	        	e.printStackTrace();
	        } 
	        catch (e:Exception) {
	        	result = false;
	        	e.printStackTrace();
	        }
		}else{
			Console.OUT.println("vertexPropertyMap is null.");
		}
		
        for(var i:Int = 0n; i < predicateCount; i++){
        	if(relationshipMapWithProperties(i) != null){
		        try {
		            var stream:FileOutputStream = new FileOutputStream(instanceDataFolderLocation + File.separator + RELATIONSHIP_STORE_NAME + "" + i + ".db");
		            var output:Output = new Output(stream);
		            this.kryo.writeObject(output, relationshipMapWithProperties(i));
		            stream.flush();
		            output.close();
		        }catch(e:java.io.IOException){
		        	e.printStackTrace();
		        } 
		        catch (e:Exception) {
		        	result = false;
		        	e.printStackTrace();
		        }
        	}else{
        		Console.OUT.println("relationshipMapWithProperties["+i+"] is null.");
        	}
        }
        
        if(attributeMap != null){
	        try {
	            var stream:FileOutputStream = new FileOutputStream(instanceDataFolderLocation + File.separator + ATTRIBUTE_STORE_NAME);
	            var output:Output = new Output(stream);
	            this.kryo.writeObject(output, attributeMap);
	            stream.flush();
	            output.close();
	        }catch(e:java.io.IOException){
	        	e.printStackTrace();
	        }  
	        catch (e:Exception) {
	        	result = false;
	        	 e.printStackTrace();
	        }
        }
        
        if(predicateStore != null){
	        try {
	            var stream:FileOutputStream = new FileOutputStream(instanceDataFolderLocation + File.separator + PREDICATE_STORE_NAME);
	            var output:Output = new Output(stream);
	            this.kryo.writeObject(output, predicateStore);
	            stream.flush();
	            output.close();
	        }catch(e:java.io.IOException){
	        	e.printStackTrace();
	        }  
	        catch (e:Exception) {
	        	result = false;
	        	e.printStackTrace();
	        }
        }
        
        //We need to store the meta info to the meta info map first.
        metaInfo.put(PREDICATE_COUNT, ""+predicateCount);
        metaInfo.put(PARTITION_ID, ""+partitionID);
        
        if(metaInfo != null){
	        try {
	            var stream:FileOutputStream = new FileOutputStream(instanceDataFolderLocation + File.separator + METADATA_STORE_NAME);
	            var output:Output = new Output(stream);
	            this.kryo.writeObject(output, metaInfo);
	            stream.flush();
	            output.close();
	        }catch(e:java.io.IOException){
	        	e.printStackTrace();
	        }  
	        catch (e:Exception) {
	        	result = false;
	        	e.printStackTrace();
	        }
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
	public def addVertexWithProperty(vertexID:Long, vertexProperty:String):void{
		var vertexSet:HashSet[String] = vertexPropertyMap.get(vertexID);
		
		if(vertexSet == null){
			vertexSet = new HashSet[String]();
			vertexPropertyMap.put(vertexID, vertexSet);
		}
		
		vertexSet.add(vertexProperty);
		updatedFlagVertex = true;
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
	public def addRelationship(fromVertex:Long, toVertex:Long, predicateID:Int):void{
		//Console.OUT.println("fromVertex:" + fromVertex + " toVertex:" + toVertex);
		//Console.OUT.println("---C1-----");
		//Whether the relationship has an associated type or not, we have to add it to the general adjacency list.
		var neighboursList:HashSet[Long] = localSubGraphMap.get(fromVertex);
		//Console.OUT.println("---C2-----");
		if(neighboursList == null){
			neighboursList = new HashSet[Long]();
			neighboursList.add(toVertex);
			localSubGraphMap.put(fromVertex, neighboursList);
		}else{
			neighboursList.add(toVertex);
		}
		//Console.OUT.println("---C3-----");
		if(predicateID > -1){
			//Console.OUT.println("---C3--1-----"+predicateID);
						
			if(predicateID < relationshipMapWithProperties.size){
				//Console.OUT.println("---C5-----");
				var subGraphMap:HashMap[Long, HashSet[Long]]  = relationshipMapWithProperties(predicateID);
				//Console.OUT.println("---C5-----2");
				if(subGraphMap != null){
					 var neighboursList2:HashSet[Long] = subGraphMap.get(fromVertex);
					//Console.OUT.println("---C5-----4");
					if(neighboursList2 == null){
						neighboursList2 = new HashSet[Long]();
						neighboursList2.add(toVertex);
						subGraphMap.put(fromVertex, neighboursList2);
					}else{
						//Since we already have the reference in the subGraphMap, we need not add neighboursList2 again to the subGraphMap.
						neighboursList2.add(toVertex);
					}
				}else{
					subGraphMap = new HashMap[Long, HashSet[Long]]();
					var neighboursList2:HashSet[Long]  = new HashSet[Long]();
					neighboursList2.add(toVertex);
					subGraphMap.put(fromVertex, neighboursList2);
					relationshipMapWithProperties(predicateID) = subGraphMap;
				}
				//Console.OUT.println("---C6-----");
			}else{
				Logger.info("Error: The specified predicate ID is out of range.");
			}
			
			//Console.OUT.println("---C7-----");
			if(predicateID > predicateCount){
				predicateCount = predicateID;
			}
			//Console.OUT.println("---C8-----");
		}
		//Console.OUT.println("---C4-----");
		updatedFlagVertex = true;
		updatedFlagEdge = true;
	}
	
	/**
	 * This method adds a predicate record to the native store.
	 * @param predicateID
	 * @param preidateString
	 */
	public def addPredicate(predicateID:Int, predicateString:String):void{
		predicateStore.put(predicateID, predicateString);
	}
	
	public def addAttributeByValue(vertexID:Int, relationshipType:Int, attribute:String):void{
		//String attribute = "";
		var vertexAttributeList:HashMap[Int, HashSet[String]] = attributeMap.get(vertexID);
		
		if(vertexAttributeList == null){
			vertexAttributeList = new HashMap[Int, HashSet[String]]();
			hs:HashSet[String] = new HashSet[String]();
			hs.add(attribute);
			vertexAttributeList.put(relationshipType, hs);
			attributeMap.put(vertexID as Long, vertexAttributeList);
		}else{						
			var hs:HashSet[String] = vertexAttributeList.get(relationshipType);
			if(hs == null){
				hs = new HashSet[String]();
			}
			
			hs.add(attribute);
			vertexAttributeList.put(relationshipType, hs);
			attributeMap.put(vertexID as Long, vertexAttributeList);
		}
		
		updatedFlagVertex = true;
	}
	
	public def containsVertex(vertexID:Long):Boolean{
		return vertexPropertyMap.containsKey(vertexID);
	}

    @NonEscaping
	public final def initialize():void {
		var file:File = new File(instanceDataFolderLocation);
		
		//If the directory does not exist we need to create it first.
		if(!file.isDirectory()){
			file.mkdir();
		}
		
		//We need to create empty data structures at the begining.
//		nodeRecordMap = new HashMap[Long, NodeRecord]();
//		relationshipMap = new HashMap[Long, RelationshipRecord]();
		localSubGraphMap = new HashMap[Long, HashSet[Long]]();
		vertexPropertyMap = new HashMap[Long, HashSet[String]]();
		attributeMap = new HashMap[Long, HashMap[Int, HashSet[String]]]();
		predicateStore = new HashMap[Int, String]();
		metaInfo = new HashMap[String, String]();
 
 		//If the directory does not exist we need to create it first.
 		if(!file.isDirectory()){
 			var f:File = new File(dataFolder + "/" + graphID + "_centralstore");
			if(!file.isDirectory()){
				f.mkdir();
			}
			file.mkdir();
 		}
		
		updatedFlagVertex = true;
	}
	
	public def initializeRelationshipMapWithProperties(predicateSize:Int):void{
		//private var relationshipMapWithProperties:Rail[HashMap[Long, HashSet[Long]]];
		relationshipMapWithProperties = new Rail[HashMap[Long,HashSet[Long]]](predicateSize);
		
		//Console.OUT.println("=========Len=====>"+relationshipMapWithProperties.length);
		
		//hmp = new HashMap[predicateSize];
	}
	
	public def getlocalSubGraphMap():HashMap[Long, HashSet[Long]] {
		return localSubGraphMap;
	}
	
	public def getOutDegreeDistributionHashMap():HashMap[Long, Long]{
		result:HashMap[Long, Long] = new HashMap[Long, Long]();
		
		itr:Iterator[Long]  = localSubGraphMap.keySet().iterator();
		while(itr.hasNext()){
			vertexID:Long = itr.next();
			result.put(vertexID, localSubGraphMap.get(vertexID).size() as Long);
		}
		
		return result;
	}
	
	public def getvertexPropertyMap():HashMap[Long, HashSet[String]] {
		return vertexPropertyMap;
	}
	
    public def getrelationshipMapWithProperties():Rail[HashMap[Long, HashSet[Long]]] {
    	return relationshipMapWithProperties;
    }
	
    public def getattributeMap():HashMap[Long, HashMap[Int, HashSet[String]]]{
    	return attributeMap;
    }

    public def getpredicateStore():HashMap[Int, String] {
    	return predicateStore;
    }
    
    public def getVertexCount():Long{
    	if(updatedFlagVertex){
    		vertexCount = vertexPropertyMap.keySet().size();
    		updatedFlagVertex = false;
    	}
    	
    	return vertexCount;
    }
    
    public def getEdgeCount():Long{
    	if(updatedFlagEdge){
    		edgeCount = 0;
    		var itr:Iterator[x10.util.Map.Entry[Long,HashSet[Long]]] = localSubGraphMap.entries().iterator();
    		while(itr.hasNext()){
    			entry:Map.Entry[Long, HashSet[Long]] = itr.next();
    			edgeCount += entry.getValue().size();
    		}	
    		updatedFlagEdge = false;
    	}
    	return edgeCount;
    }
    
    public def getPartitionID():Int{
    	return partitionID;
    }

	public def getUnderlyingHashMap():HashMap[Long, HashSet[Long]] {
		return localSubGraphMap;
	}

	
	public def addVertex(attributes:Rail[Any]):void {
		// TODO Auto-generated method stub
		
	}

	public def addEdge(startVid:Long, endVid:Long):void {
		// TODO Auto-generated method stub
		
	}
}