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

import java.io.BufferedReader;
import java.io.FileReader;


import x10.lang.Iterator;
import x10.util.ArrayList;
import x10.util.HashMap;
import x10.util.HashSet;
import x10.util.Map;
import x10.io.File;

import org.acacia.localstore.AcaciaHashMapNativeStore;
import org.acacia.util.Utils;

public class DataLoading {
	
	private var graphData:ArrayList[String];
    private var vertexPropertyMap:HashMap[Long, HashSet[String]];
	
	public def loadGraphData(val graphID:String, val partitionID:String,val placeID:String):void{
		val baseDir:String = Utils.getAcaciaProperty("org.acacia.server.instance.datafolder");
        graphData = new ArrayList[String]();
		// /var/tmp/acad-localstore
		//native store
		var nativeStore:AcaciaHashMapNativeStore = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(partitionID), baseDir, false);
		getData(nativeStore, 0n);
		
		//central store
		var centralStore:AcaciaHashMapNativeStore = new AcaciaHashMapNativeStore(Int.parseInt(graphID), Int.parseInt(placeID), baseDir, true, Int.parseInt(placeID));
		getData(centralStore, 1n);			
	}
	
	public def getData(val store:AcaciaHashMapNativeStore, val no:Int):void{
		graphData = new ArrayList[String]();
		
		store.loadGraph();
		//Vertex Id : HashSet[connected vertex ID]
		var localSubGraphMap:HashMap[Long, HashSet[Long]] = store.getlocalSubGraphMap();
		//vertexId : HashSet[Properties_URI]
		var vertexPropertyMap:HashMap[Long, HashSet[String]] = store.getvertexPropertyMap();
		//
		var relationshipMapWithProperties:Rail[HashMap[Long, HashSet[Long]]] = store.getrelationshipMapWithProperties();
		//VertexId : HashMap[Attribute Id: HashSet[AttributeValue]]
		var attributeMap:HashMap[Long, HashMap[Int, HashSet[String]]] = store.getattributeMap();
		//Predicate Id : Value
		var predicateStore:HashMap[Int, String] = store.getpredicateStore();
		
		//graph data should be aggregated and added to 'data'
		var predicateCount:Int = store.getPredicateCount();		
		
		for(var i:Int = 0n; i < predicateCount; i++){
			val hMap:HashMap[Long, HashSet[Long]] = relationshipMapWithProperties(i);
			var predicate:String = null;
			
			if(hMap != null){
				predicate = predicateStore.get(i);
				
				if(predicate == null){
					continue;
				}
			}else{
				continue;
			}
						
			var itr:Iterator[Map.Entry[Long, HashSet[Long]]] = hMap.entries().iterator() as Iterator[Map.Entry[Long, HashSet[Long]]];

			while(itr.hasNext()){
				val item:Map.Entry[Long, HashSet[Long]] = itr.next();
				var startVertexID:Long = item.getKey();
				var endVertices:HashSet[Long] = item.getValue();
				
				var firstHs:HashSet[String] = vertexPropertyMap.get(startVertexID) as HashSet[String];

				if(firstHs != null){
					var startVertexPropertyValue:String = firstHs.iterator().next() as String;
					var itr2:Iterator[Long] = endVertices.iterator();

					while(itr2.hasNext()){
                        var endVertexID:Long = itr2.next() as Long;
						var secondHs:HashSet[String] = vertexPropertyMap.get(endVertexID);

						if(secondHs != null){
							var endVertexPropertyValue:String = secondHs.iterator().next() as String;
							graphData.add(startVertexPropertyValue + "," + predicate + "," + endVertexPropertyValue);
							//data.add(startVertexPropertyValue + " " + predicate + " " + endVertexPropertyValue);								
							//System.out.println(startVertexPropertyValue + "," + predicate + "," + endVertexPropertyValue);				
							//System.out.println(startVertexPropertyValue + " " + predicate + " " + endVertexPropertyValue);
						}
					}
				}
			}
		}	
	}

	public def getGraphData():ArrayList[String]{
		return graphData;
	}

    public def getVertexPropertyMap():HashMap[Long, HashSet[String]]{
        return vertexPropertyMap;
    }
	
	public def loadInferenceData():HashMap[String, ArrayList[String]]{
		val fileName:String = "/home/yasima/Acacia/x10dt/workspace/Acacia/univ-bench.owl";
        var value:String = null;
        var key:String = null;
        var inferenceData:HashMap[String, ArrayList[String]] = null;
		        
        try {
        	var fileReader:FileReader = new FileReader(fileName);
        	var bufferedReader:BufferedReader =  new BufferedReader(fileReader);
        	inferenceData=new HashMap[String, ArrayList[String]]();
        	val input = new File(fileName);
        
            var firstFlag:Boolean = false;
        	for(nextline in input.lines()){
		        if(firstFlag){
		        		if(nextline.compareTo("<owl:Class rdf:ID=") > 0n){
		        			value=nextline.substring(nextline.indexOf("ID=")+4n,nextline.indexOf(">")-1n);
		                    firstFlag = true;
		                    continue;
		        		}
		        }else{
		                if(nextline.compareTo("rdfs:subClassOf rdf:resource=") > 0n){
		                	key=nextline.substring(nextline.indexOf("=")+3n, nextline.indexOf(">")-2n);
		                
		                	var subClasses:ArrayList[String] = inferenceData.get(key);
		
		                	if(subClasses == null){	 
		                		subClasses = new ArrayList[String]();          				
		                		subClasses.add(value);
		                		inferenceData.put(key, subClasses);
		                	}else{
		                		subClasses.add(value);
		                		inferenceData.put(key, subClasses);
		                	}
		                }
		        }
        	}
        } catch (val e1:java.io.FileNotFoundException) {
        	Console.OUT.println(e1.getMessage());
        } catch (val e1:java.io.IOException) {
        	Console.OUT.println(e1.getMessage());
        }  catch (val e:Exception) {
        	Console.OUT.println(e.getMessage());
        }
		
		return inferenceData;
	}	
}