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

package org.acacia.partitioner.local;

import x10.io.File;
import x10.util.HashMap;
import x10.util.HashSet;
import x10.util.ArrayList;

import org.acacia.util.Utils;

import java.util.LinkedList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;

import x10.util.ListIterator;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;

import org.acacia.partitioner.local.java.MetisPartitioner;
import org.acacia.partitioner.local.java.PartitionWriter;
import org.acacia.centralstore.java.AcaciaHashMapCentralStore;
import org.acacia.localstore.java.AcaciaHashMapNativeStore;
import org.acacia.metadata.db.java.MetaDataDBInterface;

/**
 * Class AcaciaRDFPartitioner
 */
public class AcaciaRDFPartitioner {
    /**
     * Default constructor 
     */
	private var nodes:HashMap[Long,String] = new HashMap[Long,String]();
	private var predicates:HashMap[Long,String] = new HashMap[Long,String]();
	private var relationsMap:HashMap[Long,HashMap[Long,LinkedList]] = new HashMap[Long,HashMap[Long,LinkedList]]();
 	private var attributeMap:HashMap[Long,HashMap[Long,LinkedList]] = new HashMap[Long,HashMap[Long,LinkedList]]();

	private val ATTRIBUTE_GENRE 	= "Attribute";
	private val RELATIONSHIP_GENRE 	= "Relationship";

 	private val location = Utils.call_getAcaciaProperty("org.acacia.server.runtime.location")+"/rdfFiles/";
 	private val edgeListPath = location+"edgeList.dl";
 
    private var converter:MetisPartitioner = null;
    private var vertexCount:Long;
    private var edgeCount:Long;
    private var outputFilePath:String;
    
    private var partitionIndex:Rail[Short];
    private var initPartFlag:Boolean;
    private var initlaPartitionID:Int;
    
    private var nParts:Int;
    private var nThreads:Int;
    private var graphID:String;
    private var partitionIDsList:ArrayList[String];
    //private var graphStorage:Rail[HashMap[Int, x10.util.HashSet[Int]]];
    private var graphStorage:HashMap[Int, x10.util.HashSet[Int]];
 
    public def this() {
    	val f = new File(location);
    	if(!f.exists()){
    		f.mkdir();
    	}else{
            	//Delete the existing files
    		val dir:java.io.File = new java.io.File(location);
            	val files:x10.interop.Java.array[java.io.File] = dir.listFiles();
            
            	for(var i:Int = 0n; i < files.length; i++ ){
            		var delStatus:Boolean = false;
            
	            	try{
		            if(files(i).isDirectory()){
			            FileUtils.deleteDirectory(files(i));
		            }else{
		            	delStatus = files(i).delete();
		            }
	            	}catch(var ex:java.io.IOException){
	            		ex.printStackTrace();
	            	}
            	}
    	}
    
        converter = new MetisPartitioner();
    }
    
    public def convert(val graphName:String, val graphID:String, val inputFilePath:String, val outputFilePath:String, val nParts:Int, val isDistributedCentralPartitions:Boolean, val nThreads:Int, val nPlaces:Int){
        this.nParts = nParts;
    	this.nThreads = nThreads;
    	this.graphID = graphID;
        this.outputFilePath = outputFilePath;
        //graphStorage = new Rail[HashMap[Int, x10.util.HashSet[Int]]](nThreads);
        
        // for(var i:Int=0n; i < nThreads; i++){
        // 	graphStorage(i) = new HashMap[Int, x10.util.HashSet[Int]]();
        // }
        
    	if(graphStorage == null){
        	graphStorage = new HashMap[Int, x10.util.HashSet[Int]]();
    	}
        
    	converter.convertWithoutDistribution(graphName, graphID, edgeListPath, Utils.call_getAcaciaProperty("org.acacia.server.runtime.location"), Place.places().size() as Int, isDistributedCentralPartitions, nThreads, Place.places().size() as Int);
        vertexCount = converter.getVertexCount();
    }
    
    public def getPartitionFileList():Rail[String]{
    	return x10.interop.Java.convert(converter.getPartitionFileList());
    }
    
    public def getPartitionIDList():Rail[String]{
       return null;
    }
       
    public def readFile(val inputFile:String):void{
    	val edgeList = new File(edgeListPath);
    	val printer = edgeList.printer();
    
    	if(graphStorage == null){
            graphStorage = new HashMap[Int, x10.util.HashSet[Int]]();
    	}
    
    	// create an empty model
    	Console.OUT.println("creating model");
    	var model:Model = ModelFactory.createDefaultModel();
    	Console.OUT.println("model created");
        var fis:java.io.FileInputStream = null;
        try{
        	fis = new java.io.FileInputStream(new java.io.File(inputFile));
        }catch(val e:java.io.FileNotFoundException){
            e.printStackTrace();
        }
    	// read the RDF/XML file
		model.read(fis, null, "RDF/XML");
 		Console.OUT.println("model created2");
		iter:StmtIterator = model.listStatements();
 		Console.OUT.println("model created3");
 
	 	while (iter.hasNext()) {
	 	    stmt:Statement		= iter.nextStatement();  // get next statement
	        //Operate on this statement
	 	    subject:Resource   	= stmt.getSubject();     // get the subject
	 	    predicate:Property 	= stmt.getPredicate();   // get the predicate
	 	    object:RDFNode    	= stmt.getObject();      // get the object
	 	    
	 	    //Console.OUT.println("Subject : "+subject.toString());
	        //Here we are creating the first vertex.
	 	    var firstVertex:Int = addToStore(nodes, subject.toString()) as Int;
	 	    
	 	    //Console.OUT.println("Predicate : "+ predicate.toString() + " ");
	 	    var relation:Long = addToStore(predicates, predicate.toString());
	 	    
	 	    //Console.OUT.println("Object : "+object.toString());
	 	    var secondVertex:Int;
		    
	 	    if (object instanceof Resource) {
	 	        //Here we are creating the second vertex. 
	 	    	secondVertex = addToStore(nodes, object.toString()) as Int;
		    	addToMap(relationsMap,firstVertex,relation,secondVertex);
		    	printer.println(firstVertex+" "+secondVertex);
		        //We also need to add this to tree
		    	//Treat the first vertex

		    	//var firstVertexIdx:Int = firstVertex%nThreads;
		    	//Console.OUT.println("firstVertexIdx:" + firstVertexIdx);
		    	//var vertexSet:x10.util.HashSet[Int] = graphStorage(firstVertexIdx).get(firstVertex);

		        var vertexSet:x10.util.HashSet[Int] = graphStorage.get(firstVertex) as x10.util.HashSet[Int] ;
			    
		    	if(vertexSet == null){
		    		vertexSet = new HashSet[Int]();
		    		vertexSet.add(secondVertex);
		    		edgeCount++;
		    
		    		//graphStorage(firstVertexIdx).put(firstVertex, vertexSet);
		            graphStorage.put(firstVertex, vertexSet);
		    	}else{		    		
		    		if(vertexSet.add(secondVertex)){
		    			edgeCount++;
		    		}
		    		//Note: we are getting a reference, so no need to put it back.
		    		//graphStorage.put(firstVertex, vertexSet);
		    	}

		    	//Next, treat the second vertex
		    	//var secondVertexIdx:Int = secondVertex%nThreads;
		    	//vertexSet = graphStorage(secondVertexIdx).get(secondVertex);

		    	vertexSet = graphStorage.get(secondVertex) as x10.util.HashSet[Int];
		    
		    	if(vertexSet == null){
		    		vertexSet = new x10.util.HashSet[Int]();
		    		vertexSet.add(firstVertex);
		    		edgeCount++;
		    		//graphStorage(secondVertexIdx).put(secondVertex, vertexSet);
		            graphStorage.put(secondVertex, vertexSet);
		    	}else{		    		
		    		if(vertexSet.add(firstVertex)){
		    			edgeCount++;
		    		}
		    		//Note: we are getting a reference, so no need to put it back.
		    		//graphStorage.put(secondVertex, vertexSet);
		    	}

		    // 	if(firstVertex > largestVertex){
		    // 		largestVertex = firstVertex;
		    // 	}
		    // 
		    // 	if(secondVertex > largestVertex){
		    // 		largestVertex = secondVertex;
		    // 	}
	 	    } else {
	 	    	// object is a literal
	 	    	addToMap(attributeMap,firstVertex,relation,object.toString());
	 	    }
	 	}
	
	 	//flush the printer
	 	printer.flush();
	 
	 	writeStore(nodes,"nodeStore");
	 	writeStore(predicates,"predicateStore");
	 	writeMap(attributeMap,"attributeMap");
	 	writeMap(relationsMap,"relationMap");
    }
    
    private def addToStore(val map:HashMap[Long,String],val URI:String):Long{
    	var itr:Iterator[x10.util.Map.Entry[Long,String]] = map.entries().iterator();
    	while(itr.hasNext()){
    		val propItem:x10.util.Map.Entry[Long,String] = itr.next();
    		if(propItem.getValue().equals(URI)){
    			return propItem.getKey();
    		}
    	}
    	val id = map.size();
    	map.put(id,URI);
    
    	return id;
    }
    
    private def addToMap(val map:HashMap[Long,HashMap[Long,LinkedList]],val vertex:Long,val relation:Long,val value:Any):void{
    	var miniMap:HashMap[Long,LinkedList] = map.get(vertex);
    	Console.OUT.println(value);
    	if(miniMap != null){
    		var list:LinkedList = miniMap.get(relation);
    		if(list != null){
    			list.addLast(value);
    		}
    		else{
    			list = new LinkedList();
    			list.addLast(value);
    			miniMap.put(relation,list);
    		}
    	}
    	else{
    		var list:LinkedList = new LinkedList();
    		list.addLast(value);
    		miniMap = new HashMap[Long,LinkedList]();
    		miniMap.put(relation,list);
    		map.put(vertex,miniMap);
    	}
    }
    
    public def writeStore(val map:HashMap[Long,String],val fileName:String):void{
    	Console.OUT.println("*****"+fileName+"*****");
    	val O = new File(location+fileName);
    	val P = O.printer();
     	var itr:Iterator[x10.util.Map.Entry[Long,String]] = map.entries().iterator();
    	while(itr.hasNext()){
    		val attributeItem:x10.util.Map.Entry[Long,String] = itr.next();
    	    	//Console.OUT.println(attributeItem.getKey()+" "+attributeItem.getValue());
    	    	P.println(attributeItem.getKey()+" "+attributeItem.getValue());
    	}
   	    P.flush();
    }
    
    public def writeMap(val map:HashMap[Long,HashMap[Long,LinkedList]],val fileName:String):void{
    	Console.OUT.println("*****"+fileName+"*****");
    	val O = new File(location+fileName);
    	val P = O.printer();
    	val itr:Iterator[x10.util.Map.Entry[Long,HashMap[Long,LinkedList]]] = map.entries().iterator();
    	while(itr.hasNext()){
    		val mapItem:x10.util.Map.Entry[Long,HashMap[Long,LinkedList]] = itr.next();
    		val itr2:Iterator[x10.util.Map.Entry[Long,LinkedList]] = mapItem.getValue().entries().iterator();
    		while(itr2.hasNext()){
    			val miniMapItem:x10.util.Map.Entry[Long,LinkedList] = itr2.next();
    			val itr3:java.util.ListIterator = miniMapItem.getValue().listIterator();
    			//Console.OUT.print(mapItem.getKey()+" "+miniMapItem.getKey());
    			P.print(mapItem.getKey()+" "+miniMapItem.getKey());
    			while(itr3.hasNext()){
    				val value = itr3.next();
    				//Console.OUT.print(" "+value);
    				P.print(" "+value);
    			}
    			//Console.OUT.println();
    			P.println();
    		}
    	}
    	P.flush();
    }
    
    public def getEdgeList():String{
    	return edgeListPath;
    }
    
    /**
     * Once an RDF graph's edge list has been partitioned by MetisPartitioner, we have to distribute
     * the contents of the RDF data set across the workers. This is different from distriuting a simple
     * edgelist because we have to handle the vertex, edge properties as well.
     */
    public def distributePartitionedData():void{
    	val partitionFilesMap:HashMap[Int, AcaciaHashMapNativeStore]  = new HashMap[Int, AcaciaHashMapNativeStore](); 
    	val centralStoresMap:HashMap[Int, AcaciaHashMapCentralStore]  = new HashMap[Int, AcaciaHashMapCentralStore]();
        //val relationships:HashMap[Long,RelationshipRecord] = new HashMap[Long,RelationshipRecord](); //This holds the relationship information
    
        partitionIDsList = new ArrayList[String]();
    	partitionIndex = new Rail[Short]((vertexCount+1) as Int);
    
    	var br:BufferedReader=null;
    	try{
    		br = new BufferedReader(new FileReader(outputFilePath+"/grf.part."+nParts), (10 * 1024 * 1024) as Int);
    		var line:String = br.readLine();
    		var counter:Int = 0n;
    		var partitionID:Int = 0n;
    
    		var refToHashMapNativeStore:AcaciaHashMapNativeStore = null;
    		initPartFlag = false;
   
    		while(line != null){		    	
    			partitionID = Int.parseInt(line);
    			partitionIndex(counter) = partitionID as Short;//This is kind of limitation at the moment.
    			refToHashMapNativeStore = partitionFilesMap.get(partitionID);
    
    			if(refToHashMapNativeStore == null){
    				val actualPartitionID:String = MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.PARTITION(GRAPH_IDGRAPH) VALUES(" + graphID + ")");
                    Console.OUT.println("actualPartitionID:" + actualPartitionID);
    				refToHashMapNativeStore = new AcaciaHashMapNativeStore(Int.parseInt(graphID), partitionID as Int);
    				partitionFilesMap.put(partitionID, refToHashMapNativeStore);
    				partitionIDsList.add(actualPartitionID);
    				if(!initPartFlag){
    					initlaPartitionID = Int.parseInt(actualPartitionID);
    					initPartFlag = true;
    				}
    			}
    
    			line = br.readLine();
    			counter++;
    		}
    	}catch(val e:IOException){
    			e.printStackTrace();
    	}
    
        Console.OUT.println("----------------------------5");
        //AcaciaHashMapNativeStore
    	var same:Int = 0n;
    	var different:Int = 0n;
    	val numberOfPartitions:Int = partitionFilesMap.keySet().size() < nThreads ? nThreads : partitionFilesMap.keySet().size() as Int;
    	val numVerts:Rail[Int] = new Rail[Int](numberOfPartitions);
    
        //finish{
            //for(var i:Int = 0n; i < nThreads; i++){
            	//var itr:x10.lang.Iterator[x10.util.Map.Entry[Int, x10.util.HashSet[Int]]] = graphStorage(i).entries().iterator() as x10.lang.Iterator[x10.util.Map.Entry[Int, x10.util.HashSet[Int]]];
                var itr:x10.lang.Iterator[x10.util.Map.Entry[Int, x10.util.HashSet[Int]]] = graphStorage.entries().iterator() as x10.lang.Iterator[x10.util.Map.Entry[Int, x10.util.HashSet[Int]]];
            	var toVertexPartition:Int = 0n;
            	var toVertex:Int = 0n;
            
            	while(itr.hasNext()){
            		var entry:x10.util.Map.Entry[Int, x10.util.HashSet[Int]] = itr.next();
            		var fromVertex:Int = entry.getKey();
            		var fromVertexPartition:Int = partitionIndex(fromVertex);
            		var hs:x10.util.HashSet[Int] = entry.getValue() as x10.util.HashSet[Int];
            
            		if(hs != null){
            			var itr2:x10.lang.Iterator[Int] = hs.iterator();
            			while(itr2.hasNext()){
            				toVertex = itr2.next();
            				toVertexPartition = partitionIndex(toVertex);
            
            				if(fromVertexPartition != toVertexPartition){
            					different++;
            				}else{
            					same++;
            					numVerts(fromVertexPartition)++;
            				}
            			}
            		}else{
            			continue;
            		}
            	}
            //}
        //}
        
        Console.OUT.println("same:" + same);
        Console.OUT.println("different:" + different);
        
        //In the second run we actually separate the graph data to multiple partitions.
        //The following code assume there is only single central partition

        val numberOfCentralPartitions:Int = nParts;
        val edgesPerCentralStore:Int = ((different / numberOfCentralPartitions) + 1n) as Int;
        
        for(var i:Int = 0n; i < numberOfCentralPartitions; i++){
        	centralStoresMap.put(i, new AcaciaHashMapCentralStore(Int.parseInt(graphID), i as Int));
        }
        
        MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET CENTRALPARTITIONCOUNT=" + numberOfCentralPartitions + ", VERTEXCOUNT=" + vertexCount + ", EDGECOUNT=" + edgeCount + " WHERE IDGRAPH=" + graphID);
        
        //for(int u = 0; u < nThreads; u++){
        	//sbCentral[u] = new StringBuilder();
        
        	//Iterator<Integer> itrN = graphStorage[u].keySet().iterator();
        var itrN:x10.lang.Iterator[Int] = graphStorage.keySet().iterator();
        Console.OUT.println("-----------------A");
        var fromVertex:Int = 0n;
        var fromVertexPartition:Int = 0n;
        toVertex = 0n;
        toVertexPartition = 0n;
        
        for(var i:Int = 0n; i < numberOfCentralPartitions; i++){
            Console.OUT.println("-----------------A"+i);
        	while(itrN.hasNext()){
        		fromVertex = itrN.next();
        		val valItem:HashSet[Int] = graphStorage.get(fromVertex);
                fromVertexPartition = partitionIndex(fromVertex) as Short;
        
                val itr2:x10.lang.Iterator[Int] = valItem.iterator();
        		while(itr2.hasNext()){
        			toVertex = itr2.next();
        			toVertexPartition = partitionIndex(toVertex);
        
        			if(fromVertexPartition != toVertexPartition){
        				val central:AcaciaHashMapCentralStore = centralStoresMap.get(fromVertexPartition) as AcaciaHashMapCentralStore;								
        				central.addEdge(x10.interop.Java.convert(fromVertex as Long), x10.interop.Java.convert(toVertex as Long));
        			}else{
        				//PartitionWriter pw = partitionFilesMap.get(new Short((short) fromVertexPartition));
        				//pw.writeEdge(fromVertex, toVertex);
                        val nativeStore:AcaciaHashMapNativeStore = partitionFilesMap.get(fromVertexPartition);
                        
                        if(!nativeStore.containsVertex(fromVertex)){
                            var idProperty:String = nodes.get(fromVertex);
                            nativeStore.addVertexWithProperty(fromVertex as Long, -1l);
                        }else{
                            //We need not to worry about adding new properties to an existing vertex in the native store.
                            //It is because we add the entire set of information of a vertex when we add a vertex at the first time.
                        }
                        // nativeStore.addNodeRecord();
                        // nativeStore.addRelationshipRecord();
        			}
        		}
        	}
        }
        Console.OUT.println("-----------------A22");
        
        
    }
    
    public def getInitlaPartitionID():Int{
         return -1n;
    }
}