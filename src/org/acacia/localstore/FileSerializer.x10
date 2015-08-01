package org.acacia.localstore;

import x10.util.HashMap;
import org.acacia.util.Utils;
import x10.io.File;
import java.util.ListIterator;
import x10.util.HashSet;
import org.acacia.localstore.java.NodeRecord;
import org.acacia.localstore.java.RelationshipRecord;
import org.acacia.localstore.java.AcaciaHashMapNativeStore;

public class FileSerializer {
 	val nodes:HashMap[Long,org.acacia.localstore.java.NodeRecord] = new HashMap[Long,org.acacia.localstore.java.NodeRecord]();
 	val relationships:HashMap[Long,RelationshipRecord] = new HashMap[Long,RelationshipRecord]();
 	val relationshipList:HashMap[Long,HashSet[Int]] = new HashMap[Long,HashSet[Int]]();
 	//val nativeStore:AcaciaHashMapNativeStore = new AcaciaHashMapNativeStore(100n);
 
 	private val location = Utils.call_getAcaciaProperty("org.acacia.server.runtime.location")+"/rdfFiles/";
 
 	public def loadData():void{
 		fillNodes();
 		fillRelationshipList();
 		fillRelationships();
 		serializeNodes();
 		serializeRelationships();
 	}
 
 	public def serializeNodes(){
 		nativeStore.loadNodes();
 		var itr:Iterator[x10.util.Map.Entry[Long,org.acacia.localstore.java.NodeRecord]] = nodes.entries().iterator();
 		while(itr.hasNext()){
 			var item:x10.util.Map.Entry[Long,org.acacia.localstore.java.NodeRecord] = itr.next();
 			nativeStore.addNodeRecord(x10.interop.Java.convert(Long.parse(item.getKey().toString())),item.getValue() as org.acacia.localstore.java.NodeRecord);
 		}
 		nativeStore.storeNodeRecord();
 	}
 
 	public def serializeRelationships(){
 		nativeStore.loadRelationships();
 		var itr:Iterator[x10.util.Map.Entry[Long,RelationshipRecord]] = relationships.entries().iterator();
 		while(itr.hasNext()){
 			var item:x10.util.Map.Entry[Long,RelationshipRecord] = itr.next();
 			nativeStore.addRelationshipRecord(x10.interop.Java.convert(java.lang.Long.parseLong(item.getKey().toString())),item.getValue() as org.acacia.localstore.java.RelationshipRecord);
 		}
 		nativeStore.storeRelationshipRecord();
 	}
 
 	private def fillNodes():void{
 		val input = new File(location+"nodeStore");
 		val i = input.openRead();
 		for(line in i.lines()){
 			val nodeId = line.split(" ");
 			val nodeRecord:NodeRecord = new NodeRecord(1n,-1n,-1n);
 			//Console.OUT.print(nodeId(0)+" ");
 			nodes.put(Long.parse(nodeId(0)),nodeRecord);
 		}
 
 	}
 
 	private def fillRelationships():void{
 		val input = new File(location+"relationMap");
 		Console.OUT.println("Test----------------------------");
 		var relationshipID:Int = 0n;
 		val i = input.openRead();
 		for(line in i.lines()){
 			val nodeIds = line.split(" ");
  			val nodeId = Long.parse(nodeIds(0));
 			//Console.OUT.println(nodeId+" ");
 			var j:Int = 2n;
 			var relType:Int = Int.parse(nodeIds(1));
 			//var set:HashSet[Int] = relationshipList.get(nodeId);
 			while(j<nodeIds.size){
 //Console.OUT.println("Test----------------------------1:"+relationshipID);
 				val listItem = Long.parse(nodeIds(j));
 //Console.OUT.println("Test----------------------------1.5 : "+listItem);
 				val firstNodePrev = getPreviousRelationshipId(nodeId,relationshipID);
 //Console.OUT.println("Test----------------------------2 : "+firstNodePrev);
 				val firstNodeNext = getNextRelationshipId(nodeId,relationshipID);
 //Console.OUT.println("Test----------------------------3 : "+firstNodeNext);
 				val secondNodePrev = getPreviousRelationshipId(listItem,relationshipID);
 //Console.OUT.println("Test----------------------------4 : "+secondNodePrev);
 				val secondNodeNext = getNextRelationshipId(listItem,relationshipID);
 //Console.OUT.println("Test----------------------------5 : "+secondNodeNext);
 				var relationshipRecord:RelationshipRecord = new RelationshipRecord(1n,nodeId,listItem,relType,firstNodePrev,firstNodeNext,secondNodePrev,secondNodeNext);
 				relationships.put(nodeId,relationshipRecord);
 				//Console.OUT.println(relationshipID+" : "+1n+","+nodeId+","+listItem+","+relType+","+firstNodePrev+","+firstNodeNext+","+secondNodePrev+","+secondNodeNext);
 				relationshipID++;
 				j++;
 			}
 			
 			//Console.OUT.println();
 			//break;
 		}
 	}
 
 	private def getPreviousRelationshipId(val nodeId:Long, val relID:Int):Int{
 		var set:HashSet[Int] = relationshipList.get(nodeId);
 		if(set == null){
  			return -1n;
 		}
 		var itr:Iterator[Int] = set.iterator();
 		var prev:Int = -1n;
 		while(itr.hasNext()){
 			val setItem:Int = itr.next();
 			if(setItem == relID){
 				return prev;
 			}
 			prev = setItem;
 		}
 		return -1n;
 	}
 
 	private def getNextRelationshipId(val nodeId:Long, val relID:Int):Int{
 		var set:HashSet[Int] = relationshipList.get(nodeId);
 		if(set == null){
 			return -1n;
 		}
 
 		var itr:Iterator[Int] = set.iterator();
 		while(itr.hasNext()){
 			val setItem:Int = itr.next();
 			if(setItem == relID){
 				if(itr.hasNext()){
 					return itr.next();
 				}
 			}
 		}
 		return -1n;
 	}
 
 	private def fillRelationshipList():void{
 		val input = new File(location+"relationMap");
 		Console.OUT.println("Test----------------------------");
 		var relationshipID:Int = 0n;
 		val i = input.openRead();
 		for(line in i.lines()){
 			val nodeIds = line.split(" ");
 			val nodeId = Long.parse(nodeIds(0));
 			//Console.OUT.print(nodeId+" ");
 			var j:Int = 2n;
 			var set:HashSet[Int] = relationshipList.get(nodeId);
 			if(set == null){
 				set = new HashSet[Int]();
 			}
 			while(j<nodeIds.size){
 				set.add(relationshipID);
 				//Console.OUT.print(relationshipID+" ");
 				relationshipID++;
 				j++;
 			}
 			relationshipList.put(nodeId,set);
 			//Console.OUT.println();
 	 		//break;
 		}
 	}
}