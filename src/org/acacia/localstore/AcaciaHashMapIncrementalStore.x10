/**
Copyright 2016 Acacia Team

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
import x10.util.HashMap;
import x10.util.HashSet;
import x10.util.Map.Entry;
import x10.util.Set;
import x10.interop.Java;
import x10.compiler.NonEscaping;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import java.io.RandomAccessFile;
import java.io.FileOutputStream;
import java.lang.StringBuilder;
import java.io.FileInputStream;
import java.lang.Override;

import org.acacia.util.Utils;
import org.acacia.log.Logger;

/**
 * This class will hold only one subgraph instance of a particular graph. Its essentially the
 * analogy for Neo4j store which was used in earlier Acacia versions. 
 */

public class AcaciaHashMapIncrementalStore implements AcaciaLocalStore{
    private val VERTEX_STORE_NAME = "acacia.nodestore.db";
    private val EDGE_STORE_NAME = "acacia.edgestore.db";
    private val ATTRIBUTE_STORE_NAME = "acacia.attributestore.db";
    
    private var vertexStore:RandomAccessFile;
    private var edgeStore:RandomAccessFile;
    private var attributeStore:RandomAccessFile;
    
    private var graphID:Int = -1n;
    private var partitionID:Int = -1n;
    private var instanceDataFolderLocation:String = null;
    private var dataFolder:String = null;
    private var localSubGraphMap:HashMap[Long, HashSet[Long]] = null;
    private var kryo:Kryo = null;
    
    private var vertexCount:Long = 0;
    private var edgeCount:Long = 0;
    private val typeMap :java.util.HashMap = new java.util.HashMap();
    
    public def this(graphID:Int, partitionID:Int){
        this.graphID = graphID;
        this.partitionID = partitionID;
        
        kryo = new Kryo();
        kryo.register(Java.getClass(typeMap), new MapSerializer());
        dataFolder = Utils.getAcaciaProperty("org.acacia.server.instance.datafolder");
        var gid:String = graphID + "_" + partitionID;
        instanceDataFolderLocation = dataFolder + File.separator + gid;
        initialize();
    }
    
    public def this(instanceDataFolderLocation:String){
        this.instanceDataFolderLocation = instanceDataFolderLocation;
        this.graphID = -1n;
        this.partitionID = -1n;
        
        kryo = new Kryo();
        kryo.register(Java.getClass(typeMap), new MapSerializer());
        initialize();
    }
    
    public def loadGraph():Boolean{
        var result:Boolean = false;
        var edgeStorePath:String = instanceDataFolderLocation + File.separator + "acacia.edgestore.db";
        f:File = new File(edgeStorePath);
         
        if(!f.exists()) {
             localSubGraphMap = new HashMap[Long, HashSet[Long]]();
             return result;
        }
         
        try{
            stream:FileInputStream  = new FileInputStream(edgeStorePath);
            input:Input = new Input(stream);
            toX10LocalSubgraphMap(this.kryo.readClassAndObject(input) as java.util.HashMap);
            input.close();//This will close the FileInputStream as well.
             
            if(localSubGraphMap != null){
                result = true;
            }else{
                localSubGraphMap = new HashMap[Long, HashSet[Long]]();
            }
            
            result = true;
         }catch(e:java.io.FileNotFoundException){
            e.printStackTrace();
         }catch (e:Exception) {
            e.printStackTrace();            
         }
        
         vertexCount = localSubGraphMap.size();
         edgeCount = getEdgeCount();
         
         return result;
    }
    
    public def toX10LocalSubgraphMap(val jMap:java.util.HashMap){
        localSubGraphMap = new HashMap[Long,HashSet[Long]]();
        val itr:java.util.Iterator = jMap.entrySet().iterator();
        var entry:java.util.Map.Entry = null;
        
        while(itr.hasNext()){
            entry = itr.next() as java.util.Map.Entry;
            val jSet:java.util.HashSet = entry.getValue() as java.util.HashSet;
            val itr1:java.util.Iterator = jSet.iterator();
            val hSet:HashSet[Long] = new HashSet[Long]();
            
            while(itr1.hasNext()){
                hSet.add(itr1.next() as Long);
            }
            localSubGraphMap.put(entry.getKey() as Long,hSet);
        }
    }


    public def storeGraph():Boolean{
        var result:Boolean = false;
        
        try {
             stream:FileOutputStream  = new FileOutputStream(instanceDataFolderLocation + File.separator + "acacia.edgestore.db");
             output:Output = new Output(stream);
             //this.kryo.writeObject(output, localSubGraphMap);
             this.kryo.writeClassAndObject(output, toJavaHashMap(localSubGraphMap));
             stream.flush();
             output.close();
             result = true;
             
        }catch(e:java.io.IOException){
            e.printStackTrace();
        
        }catch (e:Exception) {
            e.printStackTrace();            
        }
        return result;
    }
    
    public def addVertex(attributes:Rail[Any]):void{
        //Do not do anything here.
    }
    
    public def addEdge(startVid:Long, endVid:Long):void{
         var neighbours:HashSet[Long]  = localSubGraphMap.get(startVid);
        
        if(neighbours == null){
            neighbours = new HashSet[Long]();
        }
        
        neighbours.add(endVid);
        localSubGraphMap.put(startVid, neighbours);
    }
    
    public def getVertexCount():Long{
        if(vertexCount == 0){
            vertexCount = localSubGraphMap.keySet().size();
        }
        
        //System.out.println("<<<< Vertex count : " + vertexCount);
        
        return vertexCount;
    }
    
    public def getEdgeCount():Long{
         if(edgeCount == 0){ 
             var itr:Iterator[x10.util.Map.Entry[Long,HashSet[Long]]] = localSubGraphMap.entries().iterator();
            //itr:Iterator = entrySet.iterator();
             
             while(itr.hasNext()){
                 entry:x10.util.Map.Entry[Long, HashSet[Long]] = itr.next();
                 
                //System.out.println("entry.getValue().size() : " + entry.getValue().size());
                
                edgeCount += entry.getValue().size();
             }
         }
        //System.out.println("<<< Edge count : " + edgeCount);
        
        return edgeCount;
    }
    
    public def getVertexList():HashSet[Long]{
         var result:HashSet[Long] = new HashSet[Long]();
        
        result = localSubGraphMap.keySet() as HashSet[Long];
        
        return result;
    }
    
     public def getOutDegreeDistribution():Rail[Int]{
        var result:Rail[Int] = new Rail[Int](vertexCount);
        
        var itr:Iterator[Long] = localSubGraphMap.keySet().iterator();
        var counter:Int = 0n;
        while(itr.hasNext()){
            result(counter) = localSubGraphMap.get(itr.next()).size() as Int;
            counter++;
        }
        return result;
     }
    
    public def getOutDegreeDistributionHashMap():HashMap[Long, Long]{
        var result:HashMap[Long, Long] = new HashMap[Long, Long]();
        
        var itr:Iterator[Long] = localSubGraphMap.keySet().iterator();
        while(itr.hasNext()){
            //result[counter] = localSubGraphMap.get(itr.next()).size();
            var vertexID:Long = itr.next();
            result.put(vertexID, localSubGraphMap.get(vertexID).size() as Long);
        }
        
        return result;
    }
    
    public def toString():String{
         var sb:StringBuilder = new StringBuilder();
         if(localSubGraphMap != null){
         var itr:Iterator[x10.util.Map.Entry[Long,HashSet[Long]]] = localSubGraphMap.entries().iterator();
             
             while(itr.hasNext()){
                 entry:x10.util.Map.Entry[Long, HashSet[Long]] = itr.next();
                 sb.append(entry.getKey());
                 sb.append("---->[");
                 
                 var itr2:x10.lang.Iterator[x10.lang.Long] = entry.getValue().iterator();
                 
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
        
    public def shutdown():void{
        
    }
    
    public def getUnderlyingHashMap():HashMap[Long, HashSet[Long]]{
        return localSubGraphMap;
    }

    @NonEscaping
    public final def initialize():void{
                val file1:File = new File(dataFolder);
                
                if(!file1.isDirectory()){
                    file1.mkdir();
                }
 
          val file:File  = new File(instanceDataFolderLocation);
         //If the directory does not exist we need to create it first.
         if(!file.isDirectory()){
             file.mkdir();
         }
         //We need to create an empty data structure at the begining.
         localSubGraphMap = new HashMap[Long, HashSet[Long]]();
                
                val record:String = AcaciaLocalStoreCatalogManager.readCatalogRecord(instanceDataFolderLocation, "head");

                if(record == null){
                    AcaciaLocalStoreCatalogManager.writeCatalogRecord(instanceDataFolderLocation, "head", ""+AcaciaLocalStoreTypes.HASH_MAP_LOCAL_STORE);
                }
    }
 
    //convert Long,HashSet[Long] HashMap to java.util.HashMap
    private def toJavaHashMap(val hMap:HashMap[Long,HashSet[Long]]):java.util.HashMap{
        val jMap:java.util.HashMap = new java.util.HashMap();
        val itr:Iterator[x10.util.Map.Entry[Long,HashSet[Long]]] = hMap.entries().iterator();
        var entry:x10.util.Map.Entry[Long,HashSet[Long]] = null;

        while(itr.hasNext()){
            entry = itr.next();
            val itr1:Iterator[Long] = entry.getValue().iterator();
            val jSet:java.util.HashSet = new java.util.HashSet();

            while(itr1.hasNext()){
                jSet.add(Java.convert(itr1.next()));
            }
            jMap.put(Java.convert(entry.getKey()),jSet);
        }
        return jMap;
    }
}
