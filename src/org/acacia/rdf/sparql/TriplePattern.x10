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

package org.acacia.rdf.sparql;

import x10.util.HashSet;

import org.acacia.localstore.AcaciaHashMapNativeStore;
import x10.util.ArrayList;
import x10.util.HashMap;

public class TriplePattern {
 	private var store:AcaciaHashMapNativeStore;
 	private var localSubGraphMap:HashMap[Long, HashSet[Long]];
 	private var vertexPropertyMap:HashMap[Long,HashSet[String]];
 	private var relationshipMapWithProperties:HashMap[Long,HashSet[Long]];
 	private var attributeMap:HashMap[Long, HashMap[Int, HashSet[String]]];
 	private var predicateStore:HashMap[Int,String];
 	var resultMap:HashMap[String,HashSet[String]];
 
 	public def this(store:AcaciaHashMapNativeStore){
 		this.store = store;
 		store.loadMetaInfo();
 		store.loadPredicateStore();
 		predicateStore = store.getPredicateStore();
 		store.loadVertexPropertyMap();
 		vertexPropertyMap = store.getVertexPropertyMap();
 	}

 	public def execute(triple:Triple):HashMap[String,HashSet[String]]{
 		resultMap = new HashMap[String,HashSet[String]]();
 		if(triple.getPattern() == 0n){
 Console.OUT.println("Execute Query Pattern 0");
 			return queryPattern0(triple.getSubject(),triple.getPredicate(),triple.getObject());
 		}else if(triple.getPattern() == 1n){
 Console.OUT.println("Execute Query Pattern 1");
 			return queryPattern1(triple.getSubject(),triple.getPredicate(),triple.getObject());
 		}else if(triple.getPattern() == 2n){
 Console.OUT.println("Execute Query Pattern 2");
 			return queryPattern2(triple.getSubject(),triple.getPredicate(),triple.getObject());
 		}else if(triple.getPattern() == 3n){
 Console.OUT.println("Execute Query Pattern 3");
 			return queryPattern3(triple.getSubject(),triple.getPredicate(),triple.getObject());
 		}else{
 			return new HashMap[String,HashSet[String]]();
 		}
 	}
 
 	public def queryPattern0(val subject:String,val predicate:String,val object:String):HashMap[String,HashSet[String]]{
 		var temp:HashSet[String] = new HashSet[String]();

		val count:Int = store.getPredicateCount();
		var predId:Int = -1n;
		for(var i:Int=0n;i<count;i++){
			if(predicateStore.get(i).equals(predicate)){
		 		predId = i;
		 		break;
		 	}
		}
		if(predId!=-1n){
			store.loadRelationshipMapWithProperties(predId);
			val relationshipMapWithProperties = store.getRelationshipMapWithProperties(predId);
 			val itr1 = relationshipMapWithProperties.entries().iterator();
			var entry1:x10.util.Map.Entry[Long,HashSet[Long]];
			while(itr1.hasNext()){
				entry1 = itr1.next();;
			 	val sub = vertexPropertyMap.get(entry1.getKey()).iterator().next();
			 	val itr2 = entry1.getValue().iterator();
			 	while(itr2.hasNext()){
			 		val ob = vertexPropertyMap.get(itr2.next() as Long).iterator().next();
			 		temp.add(sub+","+ob);
			 	}
			 }
 		}
 		resultMap.put(subject+","+object,temp);
 		return resultMap;
 	}
 
 	public def queryPattern1(val subject:String,val predicate:String,val object:String):HashMap[String,HashSet[String]]{
 		var temp:HashSet[String] = new HashSet[String]();

 		val count:Int = store.getPredicateCount();
 		var predId:Int = -1n;
 		for(var i:Int=0n;i<count;i++){
 			if(predicateStore.get(i).equals(predicate)){
 				predId = i;
 				break;
 			}
 		}
 		if(predId!=-1n){
 			store.loadRelationshipMapWithProperties(predId);
 			val relationshipMapWithProperties = store.getRelationshipMapWithProperties(predId);
 			var endVertexId:Long = -1;
 			val itr = vertexPropertyMap.entries().iterator();
 			var entry:x10.util.Map.Entry[Long,HashSet[String]];
 			while(itr.hasNext()){
 				entry = itr.next();
 				if(entry.getValue().iterator().next().equals(object)){
 					endVertexId = entry.getKey() as Long;
 					break;
 				}
 			}
 			if(endVertexId!=-1){
 				val itr1 = relationshipMapWithProperties.entries().iterator();
 				var entry1:x10.util.Map.Entry[Long,HashSet[Long]];
 				while(itr1.hasNext()){
 					entry1 = itr1.next();
 					if(entry1.getValue().contains(endVertexId)){
 						temp.add(vertexPropertyMap.get(entry1.getKey()).iterator().next());
 					}
 				}
 			}
 		}
 		resultMap.put(subject,temp);
 		return resultMap;
 	}
 
 	public def queryPattern2(val subject:String,val predicate:String,val object:String):HashMap[String,HashSet[String]]{
 		var temp:HashSet[String] = new HashSet[String]();
 
 		val count:Int = store.getPredicateCount();
 		var predId:Int = -1n;
 		for(var i:Int=0n;i<count;i++){
 			if(predicateStore.get(i).equals(predicate)){
 				predId = i;
 				break;
 			}
 		}
 		if(predId!=-1n){
 			store.loadRelationshipMapWithProperties(predId);
 			val relationshipMapWithProperties = store.getRelationshipMapWithProperties(predId);
 			var startVertexId:Long = -1;
 			val itr = vertexPropertyMap.entries().iterator();
 			var entry:x10.util.Map.Entry[Long,HashSet[String]];
 			while(itr.hasNext()){
 				entry = itr.next();
 				if(entry.getValue().iterator().next().equals(subject)){
 					startVertexId = entry.getKey() as Long;
 					break;
 				}
 			}
 			if(startVertexId!=-1){
 				val hSet = relationshipMapWithProperties.get(startVertexId);
 				if(hSet!=null){
 					val itr1 = hSet.iterator();
 					while(itr1.hasNext()){
 						temp.add(vertexPropertyMap.get(itr1.next()).iterator().next());
 					}
 				}
 			}
 		}
 		resultMap.put(object,temp);
 		return resultMap;
 	}
 
 	public def queryPattern3(val subject:String,val predicate:String,val object:String):HashMap[String,HashSet[String]]{
 		return new HashMap[String,HashSet[String]]();
 	}
 
}