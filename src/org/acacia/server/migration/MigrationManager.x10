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

package org.acacia.server.migration;
import java.util.HashMap;
import x10.interop.Java;
import org.acacia.util.java.Utils_Java;
import org.acacia.server.AcaciaManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Object;
import java.lang.Object;
import java.lang.Object;
import java.lang.Object;


/**
 * 
 */
//package org.acacia.server.migration;

//import org.acacia.server.AcaciaManager;
//import org.acacia.util.java.Utils_Java;

/**
 * @author miyuru
 *
 */
public class MigrationManager {// extends Thread {
	val migrationPlanRefreshInterval:long = 5000; //This is in ms. In each and every time period we check the free space
	                                                  //A better way is to do this whenever an operation is invoked on Acacia we can run it.
	//private String[] hostList = Utils_Java.getPublicHostList();
	
	/**
	 * When there is a write operation happens into the graph dataset we need to first check whether we have
	 * the necessary space (in the local graph directory) for storing the data. When we are sending the quotaMap to this method
	 * we have to makesure that we multiply each <vfilesize>+<efileSize> file size by a factor to cater additional spaces required in the disk.
	 */
	public static def getAllocationStrategy(requiredSpaceMap:HashMap):HashMap{
		//quotaMap should carry <partid>:<vfilesize>+<efileSize>
		var result:HashMap = null;
		
		val availableQuotaPrivate:HashMap = new HashMap();
		//This will have <host>:<used>:<Full>
		//This map is from dsfs command
//		availableQuotaPrivate.put("sc01", "11:20");
//		availableQuotaPrivate.put("sc02", "11:40");
//		availableQuotaPrivate.put("sc03", "11:40");
//		availableQuotaPrivate.put("sc04", "9:20");
		
		val privateHosts:Rail[String] = x10.interop.Java.convert[String](Utils_Java.getPrivateHostList());
		Console.OUT.println("aaaaa ---> ");
		for(item:String in privateHosts){
			//The output format from the following list is like "sc01.sc.cs.titech.ac.jp:0:20:0,sc02.sc.cs.titech.ac.jp:0:40:0,s"
			val freeSpaceInfo:String = AcaciaManager.getFreeSpaceInfo(item);
			Console.OUT.println("freeSpaceInfo-->" + freeSpaceInfo);
			val freeSpaceArr:Rail[String] = freeSpaceInfo.split(":");
			//freeSpaceArr[0] is host name
			//freeSpaceArr[1] is the size of the data directory in MB
			//freeSpaceArr[2] is the configured max space in MB for the local data directory
			//freeSpaceArr[3] is the percentage of data directory usage. 
//			//<Size of the local storage usage>:<Max local storage size configured to be used>:fraction of the local storage used (This might exceed 100%)>
			availableQuotaPrivate.put(freeSpaceArr(0), freeSpaceArr(1)+":"+freeSpaceArr(2));
			Console.OUT.println(freeSpaceArr(0)+":"+freeSpaceArr(1)+":"+freeSpaceArr(2));
		}
		Console.OUT.println("bbbbb ---> ");
		
		val availableQuotaPublic:HashMap = new HashMap();
		//This will have <host>:<used>:<Full>
		//This map is from dsfs command
		availableQuotaPublic.put("sd01", "0:40");
		availableQuotaPublic.put("sd02", "0:40");
		
		//Here we assume that we need at least three times of each subgraph size available on the remote AcaciaInstance. Because first we
		//need to send the Zip file and the unzip it. Then have to upload to Neo4j.
		
		val freeSpaceMap:HashMap = new HashMap();
		var itr:java.util.Iterator = availableQuotaPrivate.entrySet().iterator();
		while(itr.hasNext()){
			val pairs:java.util.Map.Entry =  itr.next() as java.util.Map.Entry ;
		val strArr:Rail[String] = pairs.getValue().toString().split(":");
			//freeSpaceMap.put(pairs.getKey(), (Long.parseLong(strArr[1]) - Long.parseLong(strArr[0])));
			freeSpaceMap.put(pairs.getKey(), (Double.parseDouble(strArr(1)) - Double.parseDouble(strArr(0))));
		}
		
        val itr23:java.util.Iterator = freeSpaceMap.entrySet().iterator();
        Console.OUT.println("------------>freeSpaceMap");
        
	    while(itr23.hasNext()){
	    	val pairs:java.util.Map.Entry = itr23.next() as java.util.Map.Entry;
	        Console.OUT.println("++++---+++>" + pairs.getKey() + " value : " + pairs.getValue());
	    }
	    
        val itr45:java.util.Iterator = requiredSpaceMap.entrySet().iterator();
        Console.OUT.println("------------>requiredSpaceMap");
        
	    while(itr45.hasNext()){
	    	val pairs:java.util.Map.Entry =  itr45.next() as java.util.Map.Entry;
	        Console.OUT.println("====---===>" + pairs.getKey() + " value : " + pairs.getValue());
	    }
		
		Console.OUT.println("bbbbb --->123");
		var lMap:LinkedHashMap = sortHashMapByValuesDecendingDouble(freeSpaceMap);
		
        val itrlmap:java.util.Iterator = lMap.entrySet().iterator();
        Console.OUT.println("------------>sorted free space map");
        
	    while(itrlmap.hasNext()){
	    	val pairs:java.util.Map.Entry =  itrlmap.next() as java.util.Map.Entry;
	        Console.OUT.println(">>>--->>>" + pairs.getKey() + " value : " + pairs.getValue());
	    }
		
		Console.OUT.println("bbbbb --->456");
		val pMap:LinkedHashMap = sortHashMapByValuesDecendingDouble(requiredSpaceMap);
		
        val itrPmap:java.util.Iterator = pMap.entrySet().iterator();
        Console.OUT.println("------------>sorted free space map");
        
	    while(itrPmap.hasNext()){
	    	val pairs:java.util.Map.Entry =  itrPmap.next() as java.util.Map.Entry;
	        Console.OUT.println("<<<---<<<" + pairs.getKey() + " value : " + pairs.getValue());
	    }
		
	    Console.OUT.println("bbbbb --->789");
		
	    Console.OUT.println("Available (lMap) : " + lMap.toString());
	    Console.OUT.println("Required (pMap) : " + pMap.toString());
		
		//Now we follow first-fit decreasing heuristic
		//We pack the largest subgraph to the largest available free space. Then we re-calculate the available free space
		itr = pMap.entrySet().iterator();
		//We do this for each subgraph
		val packedItemsMapPrivate:LinkedHashMap = new LinkedHashMap();
		val remainingItemsMap:LinkedHashMap = new LinkedHashMap();
		while(itr.hasNext()){
			
			val pairs:java.util.Map.Entry = itr.next() as java.util.Map.Entry;
			val lMapPair:java.util.Map.Entry = lMap.entrySet().iterator().next() as java.util.Map.Entry;
			val newFreeSpace:double = (lMapPair.getValue() as Double) - (pairs.getValue() as Double);//both as double
			
			if(newFreeSpace < 0){
				Console.OUT.println("Cannot pack : " + pairs.getValue() + ". The space is not enough in private cloud");
				remainingItemsMap.put(pairs.getKey(), pairs.getValue());
			}else{
				lMap.put(lMapPair.getKey(), newFreeSpace);
				packedItemsMapPrivate.put(pairs.getKey(),lMapPair.getKey());
				lMap = sortHashMapByValuesDecendingDouble(lMap);
			}
		}

 		Console.OUT.println("packedItemsMap : " + packedItemsMapPrivate.toString());
 		Console.OUT.println("remainingItemsMap : " + remainingItemsMap.toString());
 		Console.OUT.println("Updated Available (lMap) : " + lMap.toString());
		
		var flag:boolean = false;
		var packedItemsMapPublic:LinkedHashMap = null;
		
		if(remainingItemsMap.size() != 0n){
			//Next its time to check the availability in the public cloud
			val freeSpaceMapPublic:HashMap = new HashMap();
			itr = availableQuotaPublic.entrySet().iterator();
			while(itr.hasNext()){
				val pairs:java.util.Map.Entry = itr.next() as java.util.Map.Entry;
				val strArr:Rail[String] = pairs.getValue().toString().split(":");
				//freeSpaceMap.put(pairs.getKey(), (Long.parseLong(strArr[1]) - Long.parseLong(strArr[0])));
				freeSpaceMapPublic.put(pairs.getKey(), (Double.parseDouble(strArr(1)) - Double.parseDouble(strArr(0))));
			}
			var lMapPublic:LinkedHashMap = sortHashMapByValuesDecendingDouble(freeSpaceMapPublic);		
			
			itr = remainingItemsMap.entrySet().iterator();
			//We do this for each subgraph
			packedItemsMapPublic = new LinkedHashMap();
			val remainingItemsMapPublic:LinkedHashMap = new LinkedHashMap();
			while(itr.hasNext()){
				
				val pairs:java.util.Map.Entry = itr.next() as java.util.Map.Entry;
				val lMapPair:java.util.Map.Entry = lMapPublic.entrySet().iterator().next() as java.util.Map.Entry;
				val newFreeSpace:double = (lMapPair.getValue() as Double) - (pairs.getValue() as Double);//both as double
				
				if(newFreeSpace < 0){
					Console.OUT.println("Cannot pack : " + pairs.getValue() + ". The space is not enough in public cloud");
					flag =true;
					remainingItemsMapPublic.put(pairs.getKey(), pairs.getValue());
				}else{
					lMapPublic.put(lMapPair.getKey(), newFreeSpace);
					//This will be the output
					
					packedItemsMapPublic.put(pairs.getKey(),lMapPair.getKey());
					lMapPublic = sortHashMapByValuesDecendingDouble(lMapPublic);
				}
			}
			
 			Console.OUT.println("packedItemsMap public : " + packedItemsMapPublic.toString());
 			Console.OUT.println("remainingItemsMap : " + remainingItemsMapPublic.toString());
 			Console.OUT.println("Updated Available (lMapPublic) : " + lMapPublic.toString());
		}
		
		if(flag){
			return null; //This means based on the available quotas and requested amounts if allocations the current allocation plan cannot be made.
			             //Either have to allocate more public cloud hosts or delete some of the data from the private cloud (which is less desirable).
		}else{
 			result.putAll(packedItemsMapPrivate);
 			
 			if(packedItemsMapPublic != null){ //Because in some cases we might end up packing every subgraph into the private cloud.
				result.putAll(packedItemsMapPublic);
			}
		}
				
		return result;
	}
	
	private static def sortHashMapByValuesDecendingLong(passedMap:HashMap):LinkedHashMap{
		   val mapKeys:List = new ArrayList(passedMap.keySet());
		   val mapValues:List = new ArrayList(passedMap.values());
		   Collections.sort(mapValues, Collections.reverseOrder());
		   Collections.sort(mapKeys);

		   val sortedMap:LinkedHashMap = new LinkedHashMap();

		   val valueIt:java.util.Iterator = mapValues.iterator();
		   while (valueIt.hasNext()) {
		       val vall:Any = valueIt.next();
		       val keyIt:java.util.Iterator = mapKeys.iterator();

		       while (keyIt.hasNext()) {
		           val key:Any = keyIt.next();
		           val comp1:String = passedMap.get(key).toString();
		           val comp2:String = vall.toString();

		           if (comp1.equals(comp2)){
		               passedMap.remove(key);
		               mapKeys.remove(key);
		               sortedMap.put(key, vall);
		               break;
		           }

		       }

		   }
		   return sortedMap;
		}
	
	private static def sortHashMapByValuesDecendingDouble(passedMap:HashMap):LinkedHashMap {
		   Console.OUT.println("---------AAAA1-------------->");
		   val mapKeys:List = new ArrayList(passedMap.keySet());
		   Console.OUT.println("---------AAAA2-------------->");
		   val mapValues:List = new ArrayList(passedMap.values());
		   Console.OUT.println("---------AAAA3-------------->");
		   //Here we sort descending order of all the values.
		   Collections.sort(mapValues, Collections.reverseOrder());
		   Console.OUT.println("---------AAAA4-------------->");
		   Collections.sort(mapKeys);
		   Console.OUT.println("---------AAAA5-------------->");
           
		   //But then we need to figure out what are the keys corresponding to these values.
		   val sortedMap:LinkedHashMap = new LinkedHashMap();
		   Console.OUT.println("---------AAAA6-------------->");
		   //We are iterating using the values map
		   val valueIt:java.util.Iterator = mapValues.iterator();
		   Console.OUT.println("---------AAAA7-------------->");
		   while (valueIt.hasNext()) {
		       val vall:Any = valueIt.next();
		       val keyIt:java.util.Iterator = mapKeys.iterator();
		       //Now we are iterating using the keys.
		       Console.OUT.println("---------BBBB8-------------->");
		       while (keyIt.hasNext()) {
		           val key:Any = keyIt.next();
		           val comp1:String = passedMap.get(key).toString();
		           val comp2:String = vall.toString();
		           Console.OUT.println("---------AAAA9-------------->");
		           if (comp1.equals(comp2)){
		               passedMap.remove(key);
		               mapKeys.remove(key);
		               Console.OUT.println("---------AAAA10--------------> key : " + key + " val:"+vall);
		               sortedMap.put(key, vall);
		               Console.OUT.println("---------AAAA11-------------->");
		               break;
		           }
		       }
		   }
		   Console.OUT.println("---------AAAA8-------------->");
		   return sortedMap;
		}
	
	public static def main(args:Rail[String]) {
//		// TODO Auto-generated method stub
//		MigrationManager mm = new MigrationManager();
//		
//		HashMap mp = new HashMap();
//		
//		//map should carry <partid>:<vfilesize>+<efileSize>
//		mp.put("40", 21.4);
//		mp.put("41", 22.6);
//		mp.put("42", 19.3);
//		//mp.put("42", 11.0);
//		mp.put("43", 20.9);
//		//mp.put("43", 9.0);
//		
//		System.out.println(mm.getAllocationStrategy(mp));
	}
}
