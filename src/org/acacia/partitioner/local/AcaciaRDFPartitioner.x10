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
import x10.compiler.Native;

import org.acacia.util.Utils;

//import java.util.LinkedList;

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

import org.acacia.server.AcaciaManager;
import org.acacia.partitioner.local.MetisPartitioner;
import org.acacia.partitioner.local.PartitionWriter;
import org.acacia.util.PlaceToNodeMapper;
import org.acacia.centralstore.AcaciaHashMapCentralStore;
import org.acacia.localstore.AcaciaHashMapNativeStore;
import org.acacia.metadata.db.java.MetaDataDBInterface;

import org.acacia.util.java.Utils_Java;
import org.acacia.resilience.FaultToleranceScheduler;
import x10.core.Thread;
import x10.io.Printer;

/**
 * Class AcaciaRDFPartitioner
 */
public class AcaciaRDFPartitioner {
    /**
     * Default constructor 
     */
	private var nodes:HashMap[String,Long] = new HashMap[String,Long]();
 	private var nodesTemp:HashMap[Long,String] = new HashMap[Long,String]();
	private var predicates:HashMap[String,Long] = new HashMap[String,Long]();
 	private var predicatesTemp:HashMap[Long,String] = new HashMap[Long,String]();
	private var relationsMap:HashMap[Long,HashMap[Long,ArrayList[String]]] = new HashMap[Long,HashMap[Long,ArrayList[String]]]();
 	private var attributeMap:HashMap[Long,HashMap[Long,ArrayList[String]]] = new HashMap[Long,HashMap[Long,ArrayList[String]]]();

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
    //private var partitionIDsList:ArrayList[String];
    private var partitionIDsMap:HashMap[Int, String];
    
    //private var graphStorage:Rail[HashMap[Int, x10.util.HashSet[Int]]];
    private var graphStorage:HashMap[Int, x10.util.HashSet[Int]];
    
    var edgeList:File;// = new File(edgeListPath);
    var printer:x10.io.Printer;// = edgeList.printer();
    
    private var ontologyFileFullPath:String = null;
    private var ontologyFileName:String = null;
    
    var ppp:Printer = null;
 
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
        
    	converter.convertWithoutDistribution(graphName, graphID, edgeListPath, Utils.call_getAcaciaProperty("org.acacia.server.runtime.location"), nParts, isDistributedCentralPartitions, nThreads, nParts);
        vertexCount = converter.getVertexCount();
    }
    
    public def getPartitionFileList():Rail[String]{
    	return converter.getPartitionFileList();
    }
    
    public def getPartitionIDList():Rail[String]{
       return null;
    }
    
    public def readDirectory(val inputDirectory:String):void{
	    try{    
	    	val dir = new File(inputDirectory);
	    	val files = dir.list();
	    	edgeList = new File(edgeListPath);
	    	printer = edgeList.printer();
	        Console.OUT.println("creating model inside testing");
	        
	    	for(var i:Int=0n;i<files.size;i++){
	            if(files(i).equals("ontology")){
	              ontologyFileFullPath = inputDirectory + java.io.File.separator + files(i);
	              val dir2 = new File(ontologyFileFullPath);
	              ontologyFileFullPath = ontologyFileFullPath + java.io.File.separator + dir2.list()(0);
	              ontologyFileName = dir2.list()(0);
	              continue;
	            }
	    		readFile(inputDirectory + java.io.File.separator + files(i));
	    	}
	    	//flush the printer
	    	//printer.flush();
	    	
	    	writeNodeCSVs();
	    	writeRelationsCSVs();
	    }
	    catch(e:Exception){
	    	e.printStackTrace();
	    }    
    }
       
    public def readFile(val inputFile:String):void{
    	//val edgeList = new File(edgeListPath);
    	//val printer = edgeList.printer();
    
    	if(graphStorage == null){
            graphStorage = new HashMap[Int, x10.util.HashSet[Int]]();
    	}
    
    	// create an empty model
    	//Console.OUT.println("creating model");
    	var model:Model = ModelFactory.createDefaultModel();
    	//Console.OUT.println("model created");
        var fis:java.io.FileInputStream = null;
        try{
        	fis = new java.io.FileInputStream(new java.io.File(inputFile));
        }catch(val e:java.io.FileNotFoundException){
            e.printStackTrace();
        }
    	// read the RDF/XML file
		model.read(fis, null, "RDF/XML");
 		//Console.OUT.println("model created2");
		iter:StmtIterator = model.listStatements();
 		//Console.OUT.println("model created3");
 
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
		    	addToMap(relationsMap,firstVertex,relation,""+secondVertex);
		    	printer.println(firstVertex+" "+secondVertex);
		        printer.flush();
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

		    //Jan23 2016: We comment this line because we treat RDF data as directed graphs. In this method we basically create an edge list and store
		    //the edge list in a file. Then that file is read by Metis Partitioner into a Rail of TreeMaps. Metis needs undirected graphs
		    //for partitioning. Hence when we load the directed edge list from the disk, we need to add it to the Rail of TreeMaps as undirected.
		    /*
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
*/
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
	
    }
    
    public def writeNodeCSVs():void{
    	try{
    		val fileLocation = location+"csv/";
    		val f = new File(fileLocation);
    		if(!f.exists()){
    			f.mkdir();
    		}
    		val query = new File(location+"query.txt");
    		ppp = query.printer();
    		ppp.print("bin/neo4j-import --into acacia.db --id-type string ");
    		//val fileMap:HashMap[String,File] = new HashMap[String,File]();
		    val printerMap:HashMap[String,Printer] = new HashMap[String,Printer]();
		    val typePred = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
		    val ntype = predicates.get(typePred);
		    var file:File = null;
		    var p:Printer = null;
		    val itr:Iterator[x10.util.Map.Entry[Long,HashMap[Long,ArrayList[String]]]] = attributeMap.entries().iterator();
		    while(itr.hasNext()){
			    val mapItem:x10.util.Map.Entry[Long,HashMap[Long,ArrayList[String]]] = itr.next();
			    val attrMap:HashMap[String,String] = new HashMap[String,String]();
			    val itr2:Iterator[x10.util.Map.Entry[Long,ArrayList[String]]] = mapItem.getValue().entries().iterator();
			    while(itr2.hasNext()){
				    val miniMapItem:x10.util.Map.Entry[Long,ArrayList[String]] = itr2.next();
				    val itr3:x10.util.ListIterator[String] = miniMapItem.getValue().iterator();
				    while(itr3.hasNext()){
					    val value = itr3.next();
					    attrMap.put(predicatesTemp.get(miniMapItem.getKey()),value);
				    }
			    }
			    
			    val itr4:x10.util.ListIterator[String] = relationsMap.get(mapItem.getKey()).get(ntype).iterator();
			    while(itr4.hasNext()){
				    val nodetype = nodesTemp.get(Long.parse(itr4.next())).split("#")(1n);
				    if(!printerMap.containsKey(nodetype)){
					    file = new File(fileLocation+nodetype+".csv");
					    //fileMap.put(nodetype,file);
					    p = file.printer(true);
					    printerMap.put(nodetype,p);
					    val attrItr:Iterator[x10.util.Map.Entry[String,String]] = attrMap.entries().iterator();
					    p.print("nodeId:ID("+nodetype+")");
					    ppp.print("--nodes:"+nodetype+" "+nodetype+".csv ");
					    while(attrItr.hasNext()){
					    	p.print(","+attrItr.next().getKey().split("#")(1n));
			    		}
			    	}
			    	else{
			    		p = printerMap.get(nodetype);//.printer(true);
			    	}
			    
				    p.println();
				    val attrItr:Iterator[x10.util.Map.Entry[String,String]] = attrMap.entries().iterator();
				    p.print(mapItem.getKey());
				    if(mapItem.getKey()==5313){
				    	Console.OUT.println(mapItem.getKey()+":"+nodetype);
			    	}
				    while(attrItr.hasNext()){
				    	p.print(","+attrItr.next().getValue());
				    }
				    ppp.flush();
				    p.flush();
				    break;
			    }
			    //val nodetype = nodesTemp.get(Long.parse(relationsMap.get(mapItem.getKey()).get(ntype).getFirst())).split("#")(1n);
			    
			    
			    //p.close();
			    //file = null;//.close();
		    }
		    //p.flush();
    	}catch(e:Exception){
    		e.printStackTrace();
    	}
    }
    
    public def writeRelationsCSVs():void{
    	try{
	    	val fileLocation = location+"csv/";
		    val typePred = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
		    val ntype = predicates.get(typePred);
		    val printerMap:HashMap[String,Printer] = new HashMap[String,Printer]();
		    var file:File = null;
		    var p:Printer = null;
		    val itr:Iterator[x10.util.Map.Entry[Long,HashMap[Long,ArrayList[String]]]] = relationsMap.entries().iterator();
		    while(itr.hasNext()){
			    val mapItem:x10.util.Map.Entry[Long,HashMap[Long,ArrayList[String]]] = itr.next();
			    //val attrMap:HashMap[String,String] = new HashMap[String,String]();
			    val itr2:Iterator[x10.util.Map.Entry[Long,ArrayList[String]]] = mapItem.getValue().entries().iterator();
			    while(itr2.hasNext()){
			    	val miniMapItem:x10.util.Map.Entry[Long,ArrayList[String]] = itr2.next();
			    	if(miniMapItem.getKey() != ntype){
			    		val itr3:x10.util.ListIterator[String] = miniMapItem.getValue().iterator();
			    		while(itr3.hasNext()){
			    			val value = itr3.next();
			    			if(attributeMap.containsKey(Long.parse(value)) && attributeMap.containsKey(mapItem.getKey())){
	    
	    
				    			//attrMap.put(predicatesTemp.get(miniMapItem.getKey()),value);
					    		val startType 	= nodesTemp.get(Long.parse(relationsMap.get(mapItem.getKey()).get(ntype).getFirst())).split("#")(1n);
					    		val endType 	= nodesTemp.get(Long.parse(relationsMap.get(Long.parse(value)).get(ntype).getFirst())).split("#")(1n);
					    		val pType 		= "";
					    		//var file:File = new File(fileLocation+startType+"_"+endType+".csv");
					    		//val p = file.printer(true);
					    		if(!printerMap.containsKey(startType+"_"+endType)){
				    				file = new File(fileLocation+startType+"_"+endType+".csv");
					    			p = file.printer();
					    			printerMap.put(startType+"_"+endType,p);
					    			p.println(":START_ID("+startType+"),:TYPE,:END_ID("+endType+")");
					    
					    			ppp.print("--relationships:CONTAINS "+startType+"_"+endType+".csv ");
					    			ppp.flush();
					    			//p.flush();
					    		}
	    						else{
	    							p = printerMap.get(startType+"_"+endType);
	    						}
	    
	    
	    
							    p.println(mapItem.getKey()+","+predicatesTemp.get(miniMapItem.getKey()).split("#")(1n)+","+Long.parse(value));
							    
							    //p.println(mapItem.getKey()+","+predicatesTemp.get(miniMapItem.getKey()).split("#")(1n)+","+Long.parse(value));
							    p.flush();
	    					}
	    				}
	    			}
	    		}
	    	}
    	}catch(e:Exception){
    		e.printStackTrace();
    	}
    }
    
    private def addToStore(val map:HashMap[String,Long],val URI:String):Long{   
    	if(map.containsKey(URI)){
    		return map.get(URI);
    	}
    	val id = map.size();
    	map.put(URI,id);
    	if(map == nodes){
    		nodesTemp.put(id,URI);
    	}
    	else if(map == predicates){
    		predicatesTemp.put(id,URI);
    	}
    	return id;
    }
    
    private def addToMap(val map:HashMap[Long,HashMap[Long,ArrayList[String]]],val vertex:Long,val relation:Long,val value:String):void{
    	var miniMap:HashMap[Long,ArrayList[String]] = map.get(vertex);
    	//Console.OUT.println(value);
    	if(miniMap != null){
    		var list:ArrayList[String] = miniMap.get(relation);
    		if(list != null){
    			list.add(value);
    		}
    		else{
    			list = new ArrayList[String]();
    			list.add(""+value);
    			miniMap.put(relation,list);
    		}
    	}
    	else{
    		var list:ArrayList[String] = new ArrayList[String]();
    		list.add(""+value);
    		miniMap = new HashMap[Long,ArrayList[String]]();
    		miniMap.put(relation,list);
    		map.put(vertex,miniMap);
    	}
    }
    
    public def writeStore(val map:HashMap[String,Long],val fileName:String):void{
    	Console.OUT.println("*****"+fileName+"*****");
    	val O = new File(location+fileName);
    	val P = O.printer();
     	var itr:Iterator[x10.util.Map.Entry[String,Long]] = map.entries().iterator();
    	while(itr.hasNext()){
    		val attributeItem:x10.util.Map.Entry[String,Long] = itr.next();
    	    	//Console.OUT.println(attributeItem.getKey()+" "+attributeItem.getValue());
    	    	P.println(attributeItem.getValue()+" "+attributeItem.getKey());
    	}
   	    P.flush();
    }
    
    public def writeMap(val map:HashMap[Long,HashMap[Long,ArrayList[String]]],val fileName:String):void{
    	Console.OUT.println("*****"+fileName+"*****");
    	val O = new File(location+fileName);
    	val P = O.printer();
    	val itr:Iterator[x10.util.Map.Entry[Long,HashMap[Long,ArrayList[String]]]] = map.entries().iterator();
    	while(itr.hasNext()){
    		val mapItem:x10.util.Map.Entry[Long,HashMap[Long,ArrayList[String]]] = itr.next();
    		val itr2:Iterator[x10.util.Map.Entry[Long,ArrayList[String]]] = mapItem.getValue().entries().iterator();
    		while(itr2.hasNext()){
    			val miniMapItem:x10.util.Map.Entry[Long,ArrayList[String]] = itr2.next();
    			val itr3:x10.util.ListIterator[String] = miniMapItem.getValue().iterator();
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
    	//In the case of RDF graphs both central and native stores will be the same.
    	val partitionFilesMap:HashMap[Int, AcaciaHashMapNativeStore]  = new HashMap[Int, AcaciaHashMapNativeStore](); 
    	val centralStoresMap:HashMap[Int, AcaciaHashMapNativeStore]  = new HashMap[Int, AcaciaHashMapNativeStore]();
        Console.OUT.println("--AAAA22--:vertexCount:" + vertexCount);
        var store:AcaciaHashMapNativeStore = null;
        
        partitionIDsMap = new HashMap[Int, String]();
    	partitionIndex = new Rail[Short]((vertexCount+1) as Int);
    
    	Console.OUT.println("----------------------------5");
    	var same:Int = 0n;
    	var different:Int = 0n;
    	val numberOfPartitions:Int = partitionFilesMap.keySet().size() < nThreads ? nThreads : partitionFilesMap.keySet().size() as Int;
    	val numVerts:Rail[Int] = new Rail[Int](numberOfPartitions);
    
    	
    
    	for(var i:Int=0n;i<nParts;i++){
    		val actualPartitionID:String = MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.PARTITION(GRAPH_IDGRAPH) VALUES(" + graphID + ")");
    		store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parse(actualPartitionID) as Int, Utils.call_getAcaciaProperty("org.acacia.server.runtime.location"), false);
    		store.setOntologyFileName(ontologyFileName);
    		store.initializeRelationshipMapWithProperties(predicates.keySet().size() as Int);
    		initLocalStores(store,actualPartitionID,i);
    		var itrN:x10.lang.Iterator[Int] = graphStorage.keySet().iterator();
    		val temp = graphStorage.keySet().size();
    		var count:Int = 0n;
    		while(itrN.hasNext()){
    			val fromVertex = itrN.next();
    			val fromVertexPartition = partitionIndex(fromVertex) as Int;
    			if(fromVertexPartition == i){
    				distributeLocalData(fromVertex,store);
    			}
    		}
    		val result:Boolean = call_runUpdate("UPDATE ACACIA_META.PARTITION SET VERTEXCOUNT=" + store.getVertexCount() + ", EDGECOUNT=" + store.getEdgeCount() + " WHERE GRAPH_IDGRAPH=" + graphID + " and IDPARTITION=" + store.getPartitionID());
    		store.storeGraph();
    	}
    
    	val edgeDistribution = getEdgeDistribution();
    	same = edgeDistribution(0);
    	different = edgeDistribution(1);
    
    	Console.OUT.println("same:" + same);
    	Console.OUT.println("different:" + different);
    
    	//In the second run we actually separate the graph data to multiple partitions.
    	//The following code assume there is only single central partition

    	val numberOfCentralPartitions:Int = nParts;
    	val edgesPerCentralStore:Int = ((different / numberOfCentralPartitions) + 1n) as Int;
    
        MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET CENTRALPARTITIONCOUNT=" + numberOfCentralPartitions + ", VERTEXCOUNT=" + vertexCount + ", EDGECOUNT=" + edgeCount + " WHERE IDGRAPH=" + graphID);
        
        for(var i:Int=0n;i<nParts;i++){
        	store = new AcaciaHashMapNativeStore(Int.parseInt(graphID), i as Int, Utils.call_getAcaciaProperty("org.acacia.server.runtime.location"), true);
        	store.setOntologyFileName(ontologyFileName);
        	store.initializeRelationshipMapWithProperties(predicates.keySet().size() as Int);
        	initCentralStores(store,i);
        	var itrN:x10.lang.Iterator[Int] = graphStorage.keySet().iterator();
        	val temp = graphStorage.keySet().size();
        	while(itrN.hasNext()){
        		val fromVertex = itrN.next();
        		val fromVertexPartition = partitionIndex(fromVertex) as Int;
        		if(fromVertexPartition == i){
        			distributeCentralData(fromVertex,store);
        		}
        	}
        	MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.CPARTITION(IDCPARTITION, IDGRAPH, VERTEXCOUNT, EDGECOUNT) VALUES(" + i + "," + graphID + "," + store.getVertexCount() + "," + store.getEdgeCount() + ")");
        	store.storeGraph();
        }
        
        distributeCentralStore(numberOfCentralPartitions,graphID);
        distributeLocalStore(nParts, graphID);
        Console.OUT.println("Done partitioning...");
    }
    
    public def distributeCentralStore(val n:Int, val graphID:String){	
    	try{
    		val r:java.lang.Runtime = java.lang.Runtime.getRuntime();
    		
    		var hostID:Int = 0n;
    		var hostCount:Int = 0n;
    		var nPlaces:Int = 0n;
            var hostName:String = null;
    		
    		var hostList:ArrayList[String] = new ArrayList[String]();
    		var f:java.io.File = new java.io.File("machines.txt");
    		var br:java.io.BufferedReader = new java.io.BufferedReader(new java.io.FileReader(f));
    		var str:String = br.readLine();
    		
    		while(str != null){
    			hostList.add(str.trim());
    			str = br.readLine();
    		}
    		br.close();
    		hostCount = hostList.size() as Int;
    		
            Console.OUT.println("hostSize:" + hostCount);
            var fs:FaultToleranceScheduler = new FaultToleranceScheduler();
            
            var fsmap:HashMap[Int,String] = fs.mapReplicationstoPlaces();
            
    		for(var j:Int = 0n; j < n; j++){
                nPlaces = AcaciaManager.getNPlaces(org.acacia.util.Utils.getPrivateHostList()(0));
    			hostID = (j % hostCount) as Int;
    			hostName = hostList.get(hostID);
    			
    			val filePath:String = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location")+"/" + graphID + "_centralstore/"+graphID+"_"+j;
    			Console.OUT.println("zip -rj "+filePath+"_trf.zip "+filePath);
    			val process:java.lang.Process = r.exec("zip -rj "+filePath+"_trf.zip "+filePath);
    			process.waitFor();
    
                val instancePort:Int = PlaceToNodeMapper.getInstancePort(j);
                val fileTransferport:Int = PlaceToNodeMapper.getFileTransferServicePort(j);
    			
    			AcaciaManager.batchUploadCentralStore(hostName, instancePort, Long.parseLong(graphID), filePath+"_trf.zip", fileTransferport);
    			val hostDI:String = call_runSelect("SELECT idhost FROM ACACIA_META.HOST WHERE name LIKE '" + hostName + "'")(0);
    			MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.HOST_HAS_CPARTITION(HOST_IDHOST, CPARTITION_IDCPARTITION, CPARTITION_GRAPH_IDCGRAPH) VALUES(" + hostDI + "," + j + "," + graphID + ")");
    		
    //send replicates
    Console.OUT.println(fsmap.size()+":"+n+":"+j);
    val placeList = fsmap.get(j);
    if(placeList != null){
    val repPlaces = placeList.substring(0n,placeList.length()-1n).split(",");
    var replicationHostID:Int = 0n;
    var replicationHostName:String = null;
    for(val repPlace in repPlaces){
    val i:Int = Int.parse(repPlace);
    replicationHostID = (i % hostCount) as Int;
    replicationHostName = hostList.get(hostID);
    val replicationActualPartID:String = partitionIDsMap.get(i);
    //  val replicationWithinPlaceIndex:Int = ((i - hostID) as Int)/hostCount;
    val replicationInstancePort:Int = PlaceToNodeMapper.getInstancePort(i);
    //  Console.OUT.println("withinPlaceIndex:" + replicationWithinPlaceIndex + " instancePort:" + replicationInstancePort);
    val replicationFileTransferport:Int = PlaceToNodeMapper.getFileTransferServicePort(i);
    AcaciaManager.batchUploadCentralReplication(replicationHostName, replicationInstancePort, Long.parseLong(graphID), filePath+"_trf.zip", replicationFileTransferport,i);
    //  //val replicationHost:String = call_runSelect("SELECT idhost FROM ACACIA_META.HOST WHERE name LIKE '" + replicationHostName + "'")(0);
    MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.REPLICATION_STORED_IN(idreplication, stored_partition_id, stored_host_id) VALUES(" + j + "," + graphID + "_" + j + "," + replicationHostID + ")");
    //  
    Console.OUT.println("Send replication "+graphID+"_"+j+" to worker "+i);
    }
    }
    }

		    //delete central store files in tmp directory
		    val centralStorePath:String = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location")+"/" + graphID + "_centralstore/";
		    val p:java.lang.Process = r.exec("rm -r "+centralStorePath);
    	}catch(val e:Exception){
    		Console.OUT.println("Error : "+e.getMessage());
            	e.printStackTrace();
    	}catch(val e1:java.io.IOException){
            	Console.OUT.println("Error : "+e1.getMessage());
            	e1.printStackTrace();
        }catch(val e2:java.lang.InterruptedException){
        	Console.OUT.println("Error : "+e2.getMessage());
        	e2.printStackTrace();
        }
    }
    
    public def distributeLocalStore(val n:Int, val graphID:String){	
    	try{
    		val r:java.lang.Runtime = java.lang.Runtime.getRuntime();
    
    		var hostID:Int = 0n;
    		var hostCount:Int = 0n;
    		var nPlaces:Int = 0n;
    		var hostName:String = null;
    
    		var hostList:ArrayList[String] = new ArrayList[String]();
    		var f:java.io.File = new java.io.File("machines.txt");
    		var br:java.io.BufferedReader = new java.io.BufferedReader(new java.io.FileReader(f));
    		var str:String = br.readLine();
    
    		while(str != null){
    			hostList.add(str.trim());
    			str = br.readLine();
    		}
    
    		br.close();
    		hostCount = hostList.size() as Int;
    
    		Console.OUT.println("hostSize:" + hostCount);
                Console.OUT.println("n:" + n);

            var fs:FaultToleranceScheduler = new FaultToleranceScheduler();
            
            var fsmap:HashMap[Int,String] = fs.mapReplicationstoPlaces();    

    		for(var j:Int = 0n; j < n; j++){		             
    			nPlaces = AcaciaManager.getNPlaces(org.acacia.util.Utils.getPrivateHostList()(0));
    			hostID = (j % hostCount) as Int;
    			hostName = hostList.get(hostID);
                val actualPartID:String = partitionIDsMap.get(j);
    			val filePath:String = Utils_Java.getAcaciaProperty("org.acacia.server.runtime.location")+"/" + graphID + "_" + actualPartID;
                FileUtils.copyFile(new java.io.File(ontologyFileFullPath), new java.io.File(filePath + java.io.File.separator + ontologyFileName));
    			Console.OUT.println("zip -rj "+filePath+".zip "+filePath);
    			val process:java.lang.Process = r.exec("zip -rj "+filePath+".zip "+filePath);
    			process.waitFor();
    
			    val instancePort:Int = PlaceToNodeMapper.getInstancePort(j);
			    val fileTransferport:Int = PlaceToNodeMapper.getFileTransferServicePort(j);
    
    			AcaciaManager.batchUploadFile(hostName, instancePort, Long.parseLong(graphID), filePath+".zip", fileTransferport);
                
    			val hostDI:String = call_runSelect("SELECT idhost FROM ACACIA_META.HOST WHERE name LIKE '" + hostName + "'")(0);
    			MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.HOST_HAS_PARTITION(HOST_IDHOST, PARTITION_IDPARTITION, PARTITION_GRAPH_IDGRAPH) VALUES(" + hostDI + "," + actualPartID + "," + graphID + ")");
    
			    //send replicates
    			Console.OUT.println(fsmap.size()+":"+n+":"+j);
			     val placeList = fsmap.get(j);
			     if(placeList != null){
			     val repPlaces = placeList.substring(0n,placeList.length()-1n).split(",");
			     var replicationHostID:Int = 0n;
			     var replicationHostName:String = null;
			     for(val repPlace in repPlaces){
			     	val i:Int = Int.parse(repPlace);
			     	replicationHostID = (i % hostCount) as Int;
			     	replicationHostName = hostList.get(hostID);
				   	val replicationActualPartID:String = partitionIDsMap.get(i);
				   //  val replicationWithinPlaceIndex:Int = ((i - hostID) as Int)/hostCount;
				     val replicationInstancePort:Int = PlaceToNodeMapper.getInstancePort(i);
				   //  Console.OUT.println("withinPlaceIndex:" + replicationWithinPlaceIndex + " instancePort:" + replicationInstancePort);
				     val replicationFileTransferport:Int = PlaceToNodeMapper.getFileTransferServicePort(i);
				   AcaciaManager.batchUploadReplication(replicationHostName, replicationInstancePort, Long.parseLong(graphID), filePath+".zip", replicationFileTransferport,i);
				   //  //val replicationHost:String = call_runSelect("SELECT idhost FROM ACACIA_META.HOST WHERE name LIKE '" + replicationHostName + "'")(0);
				     MetaDataDBInterface.runInsert("INSERT INTO ACACIA_META.REPLICATION_STORED_IN(idreplication, stored_partition_id, stored_host_id) VALUES(" + j + "," + graphID + "_" + actualPartID + "," + replicationHostID + ")");
				   //  
				     Console.OUT.println("Send replication "+graphID+"_"+j+" to worker "+i);
			     }
			     }
			    //delete local store files in tmp directory
			    val p1:java.lang.Process = r.exec("rm -r "+filePath);
			    val p2:java.lang.Process = r.exec("rm "+filePath+".zip");
    		}
    	}catch(val e:Exception){
    		Console.OUT.println("Error : "+e.getMessage());
    		e.printStackTrace();
    	}catch(val e1:java.io.IOException){
    		Console.OUT.println("Error : "+e1.getMessage());
    		e1.printStackTrace();
    	}catch(val e2:java.lang.InterruptedException){
    		Console.OUT.println("Error : "+e2.getMessage());
    		e2.printStackTrace();
    	}
    }
    
    public def getInitlaPartitionID():Int{
         return -1n;
    }
    
    private def initLocalStores(store:AcaciaHashMapNativeStore,actualPartitionID:String,i:Int){
    	var br:BufferedReader=null;
    	var isFirstTime:Boolean = true;
    	try{
    		Console.OUT.println("file path: --AA-->" + outputFilePath+"/grf.part."+nParts);
    		br = new BufferedReader(new FileReader(outputFilePath+"/grf.part."+nParts));
    		var line:String = br.readLine();
    		var counter:Int = 0n;
    		var partitionID:Int = 0n;
    
    		//var refToHashMapNativeStore:AcaciaHashMapNativeStore = null;
    		initPartFlag = false;
    
    		while(line != null){		    	
    			partitionID = Int.parseInt(line);
    			//Console.OUT.println("counter:"+counter);
    			partitionIndex(counter) = partitionID as Short;//This is kind of limitation at the moment.
    			//refToHashMapNativeStore = partitionFilesMap.get(partitionID);
    			if(partitionID == i){
    				if(isFirstTime){
    					isFirstTime = false;
    					//It will be inefficient for storing the same set of predicates in each and every native store created.
    					//However, for the moment we do it because the number of predicates available is less.
    					val itr:x10.lang.Iterator[x10.util.Map.Entry[String, Long]] = predicates.entries().iterator() as x10.lang.Iterator[x10.util.Map.Entry[String, Long]];
    
    					while(itr.hasNext()){
    						val entry:x10.util.Map.Entry[String, Long] = itr.next();
    						store.addPredicate(entry.getValue() as Int, entry.getKey() as x10.lang.String);
    					}
    
    					//partitionFilesMap.put(partitionID, refToHashMapNativeStore);
    					//partitionIDsList.add(actualPartitionID);
    					partitionIDsMap.put(partitionID, actualPartitionID);
    					if(!initPartFlag){
    						initlaPartitionID = Int.parseInt(actualPartitionID);
    						initPartFlag = true;
    					}
    				}
    			}
    
    			line = br.readLine();
    			counter++;
    		}
    	}catch(val e:IOException){
    		e.printStackTrace();
    	}
    }
    
    private def getEdgeDistribution():Rail[Int]{
    	val edgeDistribution:Rail[Int] = new Rail[Int](2);
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
    					edgeDistribution(1)++;
    				}else{
    					edgeDistribution(0)++;
    				}
    			}
    		}else{
    			continue;
    		}
    	}
    	return edgeDistribution;
    }
    
    private def initCentralStores(store:AcaciaHashMapNativeStore,i:Int){
    		val itr2:x10.lang.Iterator[x10.util.Map.Entry[String, Long]] = predicates.entries().iterator() as x10.lang.Iterator[x10.util.Map.Entry[String, Long]];
    
    		while(itr2.hasNext()){
    			val entry:x10.util.Map.Entry[String, Long] = itr2.next();
    			store.addPredicate(entry.getValue() as Int, entry.getKey() as String);
    		}
    }
    
    private def distributeLocalData(fromVertex:Int,store:AcaciaHashMapNativeStore){
    	var fromVertexPartition:Int = 0n;
    	var toVertex:Int = 0n;
    	var toVertexPartition:Int = 0n;
    
    	val valItem:HashSet[Int] = graphStorage.get(fromVertex);
    	fromVertexPartition = partitionIndex(fromVertex) as Short;
    	val itr2:x10.lang.Iterator[Int] = valItem.iterator();
    	while(itr2.hasNext()){
    		toVertex = itr2.next();
    		toVertexPartition = partitionIndex(toVertex);
    
    		//Console.OUT.println("-----------------B3");
    
    		if(fromVertexPartition == toVertexPartition){
    		   
    
    		//Console.OUT.println("-----------------B8");
    
    		//We add the starting vertex of the relationship to native store
    		if(!store.containsVertex(fromVertex)){
    			var propertyValue:String = nodesTemp.get(fromVertex);
    			store.addVertexWithProperty(fromVertex as Long, propertyValue);
    		}else{
    			//We need not to worry about adding new properties to an existing vertex in the native store.
    			//It is because we add the entire set of information of a vertex when we add a vertex at the first time.
    		}
    
    		//Console.OUT.println("-----------------B9");
    
    		//The ending vertex
    		if(!store.containsVertex(toVertex)){
    			var propertyValue:String = nodesTemp.get(toVertex);
    			store.addVertexWithProperty(toVertex as Long, propertyValue);
    		}else{
    			//We need not to worry about adding new properties to an existing vertex in the native store.
    			//It is because we add the entire set of information of a vertex when we add a vertex at the first time.
    		}
    
    		//Console.OUT.println("-----------------B10 : " + fromVertex);
    
    		//Next, we add the relationship information to native store.
    		val relMap:HashMap[Long,ArrayList[String]] = relationsMap.get(fromVertex);
    
    		if(relMap != null){
    			//Console.OUT.println("-----------------B10--1 : " + relMap.entries().size());
    
    			//For each of the relationship type, we need to check whether the specified ending vertex (toVertex) is the
    			//ending vertex of the relationship and then add it to the native store.
    
    			val relIterator:x10.lang.Iterator[x10.util.Map.Entry[Long, ArrayList[String]]] = relMap.entries().iterator();
    
    			//Console.OUT.println("-----------------B10--2");
    
    			while(relIterator.hasNext()){
    				//Console.OUT.println("-----------------B10--3");
    				val item:x10.util.Map.Entry[Long, ArrayList[String]] = relIterator.next();
    				//Console.OUT.println("-----------------B10--4");
    				val key:Long = item.getKey();
    				//Console.OUT.println("-----------------B10--5");
    				val ll:ArrayList[String] = item.getValue();
    				//Console.OUT.println("-----------------B10--6");
    
    				if(ll != null){
    					if(ll.contains((""+toVertex))){
    						//Console.OUT.println("-----------------B10--6--1");
    						store.addRelationship(fromVertex as Long, toVertex as Long, key as Int);
    						//Console.OUT.println("-----------------B10--6--2");
    					}
    				}
    				//Console.OUT.println("-----------------B10--7");
    			}
    
    			//Console.OUT.println("-----------------B11");
    		}
    		//We also add the attribute information to native store.
    		val mp:HashMap[Long,ArrayList[String]] = attributeMap.get(fromVertex);
    		if(mp != null){
    			val attribIterator:x10.lang.Iterator[x10.util.Map.Entry[Long, ArrayList[String]]] = mp.entries().iterator();
    			while(attribIterator.hasNext()){
    				val entr:x10.util.Map.Entry[Long, ArrayList[String]] = attribIterator.next();
    				val retType:Int = entr.getKey() as Int;
    				val ll:ArrayList[String] = entr.getValue();
    				val itr3:x10.lang.Iterator[String] = ll.iterator();
    
    				while(itr3.hasNext()){
    					//x10.interop.Java.convert((itr3.next() as x10.lang.String).chars())
    					//x10.interop.Java.convert(new Rail[String](1))
    					store.addAttributeByValue(fromVertex as Int, retType as Int, itr3.next() as String);
    				}
    			}
    		}
    
    		//      Console.OUT.println("-----------------B12");
    
    		val mp2:HashMap[Long,ArrayList[String]] = attributeMap.get(toVertex);
    		if(mp2 != null){
    			val attribIterator:x10.lang.Iterator[x10.util.Map.Entry[Long, ArrayList[String]]] = mp2.entries().iterator();
    			while(attribIterator.hasNext()){
    				val entr:x10.util.Map.Entry[Long, ArrayList[String]] = attribIterator.next();
    				val retType:Int = entr.getKey() as Int;
    				val ll:ArrayList[String] = entr.getValue();
    				val itr3:x10.lang.Iterator[String] = ll.iterator();
    
    				while(itr3.hasNext()){
    					//x10.interop.Java.convert((itr3.next() as x10.lang.String).chars())
    					//x10.interop.Java.convert(new Rail[String](1))
    					store.addAttributeByValue(fromVertex as Int, retType as Int, itr3.next() as String);
    				}
    			}
    		}
    	}
    		//    Console.OUT.println("-----------------B13");
    	}
    }
    
    private def distributeCentralData(fromVertex:Int,store:AcaciaHashMapNativeStore){
    var fromVertexPartition:Int = 0n;
    var toVertex:Int = 0n;
    var toVertexPartition:Int = 0n;
    
    val valItem:HashSet[Int] = graphStorage.get(fromVertex);
    fromVertexPartition = partitionIndex(fromVertex) as Short;
    val itr2:x10.lang.Iterator[Int] = valItem.iterator();
    while(itr2.hasNext()){
    toVertex = itr2.next();
    toVertexPartition = partitionIndex(toVertex);
    
    //Console.OUT.println("-----------------B3");
    
    if(fromVertexPartition != toVertexPartition){
    
    
    //Console.OUT.println("-----------------B8");
    
    //We add the starting vertex of the relationship to native store
    if(!store.containsVertex(fromVertex)){
    var propertyValue:String = nodesTemp.get(fromVertex);
    store.addVertexWithProperty(fromVertex as Long, propertyValue);
    }else{
    //We need not to worry about adding new properties to an existing vertex in the native store.
    //It is because we add the entire set of information of a vertex when we add a vertex at the first time.
    }
    
    //Console.OUT.println("-----------------B9");
    
    //The ending vertex
    if(!store.containsVertex(toVertex)){
    var propertyValue:String = nodesTemp.get(toVertex);
    store.addVertexWithProperty(toVertex as Long, propertyValue);
    }else{
    //We need not to worry about adding new properties to an existing vertex in the native store.
    //It is because we add the entire set of information of a vertex when we add a vertex at the first time.
    }
    
    //Console.OUT.println("-----------------B10 : " + fromVertex);
    
    //Next, we add the relationship information to native store.
    val relMap:HashMap[Long,ArrayList[String]] = relationsMap.get(fromVertex);
    
    if(relMap != null){
    //Console.OUT.println("-----------------B10--1 : " + relMap.entries().size());
    
    //For each of the relationship type, we need to check whether the specified ending vertex (toVertex) is the
    //ending vertex of the relationship and then add it to the native store.
    
    val relIterator:x10.lang.Iterator[x10.util.Map.Entry[Long, ArrayList[String]]] = relMap.entries().iterator();
    
    //Console.OUT.println("-----------------B10--2");
    
    while(relIterator.hasNext()){
    //Console.OUT.println("-----------------B10--3");
    val item:x10.util.Map.Entry[Long, ArrayList[String]] = relIterator.next();
    //Console.OUT.println("-----------------B10--4");
    val key:Long = item.getKey();
    //Console.OUT.println("-----------------B10--5");
    val ll:ArrayList[String] = item.getValue();
    //Console.OUT.println("-----------------B10--6");
    
    if(ll != null){
    if(ll.contains((""+toVertex))){
    //Console.OUT.println("-----------------B10--6--1");
    store.addRelationship(fromVertex as Long, toVertex as Long, key as Int);
    //Console.OUT.println("-----------------B10--6--2");
    }
    }
    //Console.OUT.println("-----------------B10--7");
    }
    
    //Console.OUT.println("-----------------B11");
    }
    //We also add the attribute information to native store.
    val mp:HashMap[Long,ArrayList[String]] = attributeMap.get(fromVertex);
    if(mp != null){
    val attribIterator:x10.lang.Iterator[x10.util.Map.Entry[Long, ArrayList[String]]] = mp.entries().iterator();
    while(attribIterator.hasNext()){
    val entr:x10.util.Map.Entry[Long, ArrayList[String]] = attribIterator.next();
    val retType:Int = entr.getKey() as Int;
    val ll:ArrayList[String] = entr.getValue();
    val itr3:x10.lang.Iterator[String] = ll.iterator();
    
    while(itr3.hasNext()){
    //x10.interop.Java.convert((itr3.next() as x10.lang.String).chars())
    //x10.interop.Java.convert(new Rail[String](1))
    store.addAttributeByValue(fromVertex as Int, retType as Int, itr3.next() as String);
    }
    }
    }
    
    //      Console.OUT.println("-----------------B12");
    
    val mp2:HashMap[Long,ArrayList[String]] = attributeMap.get(toVertex);
    if(mp2 != null){
    val attribIterator:x10.lang.Iterator[x10.util.Map.Entry[Long, ArrayList[String]]] = mp2.entries().iterator();
    while(attribIterator.hasNext()){
    val entr:x10.util.Map.Entry[Long, ArrayList[String]] = attribIterator.next();
    val retType:Int = entr.getKey() as Int;
    val ll:ArrayList[String] = entr.getValue();
    val itr3:x10.lang.Iterator[String] = ll.iterator();
    
    while(itr3.hasNext()){
    //x10.interop.Java.convert((itr3.next() as x10.lang.String).chars())
    //x10.interop.Java.convert(new Rail[String](1))
    store.addAttributeByValue(fromVertex as Int, retType as Int, itr3.next() as String);
    }
    }
    }
    }
    //    Console.OUT.println("-----------------B13");
    }
    }
    
    @Native("java", "org.acacia.metadata.db.java.MetaDataDBInterface.runSelect(#1)")
    static native def call_runSelect(String):Rail[String];
    
    @Native("java", "org.acacia.metadata.db.java.MetaDataDBInterface.runUpdate(#1)")
    static native def call_runUpdate(String):Boolean;
}
