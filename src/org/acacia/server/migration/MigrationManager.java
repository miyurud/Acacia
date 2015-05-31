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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.acacia.server.AcaciaManager;
import org.acacia.util.java.Utils_Java;

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
	private long migrationPlanRefreshInterval = 5000; //This is in ms. In each and every time period we check the free space
	                                                  //A better way is to do this whenever an operation is invoked on Acacia we can run it.
	//private String[] hostList = Utils_Java.getPublicHostList();
	
	/**
	 * When there is a write operation happens into the graph dataset we need to first check whether we have
	 * the necessary space (in the local graph directory) for storing the data. When we are sending the quotaMap to this method
	 * we have to makesure that we multiply each <vfilesize>+<efileSize> file size by a factor to cater additional spaces required in the disk.
	 */
	public static HashMap getAllocationStrategy(HashMap requiredSpaceMap){
		//quotaMap should carry <partid>:<vfilesize>+<efileSize>
		HashMap result = null;
		
		HashMap availableQuotaPrivate = new HashMap();
		//This will have <host>:<used>:<Full>
		//This map is from dsfs command
//		availableQuotaPrivate.put("sc01", "11:20");
//		availableQuotaPrivate.put("sc02", "11:40");
//		availableQuotaPrivate.put("sc03", "11:40");
//		availableQuotaPrivate.put("sc04", "9:20");
		
		String[] privateHosts = Utils_Java.getPrivateHostList();
		System.out.println("aaaaa ---> ");
		for(String item : privateHosts){
			//The output format from the following list is like "sc01.sc.cs.titech.ac.jp:0:20:0,sc02.sc.cs.titech.ac.jp:0:40:0,s"
			String freeSpaceInfo = AcaciaManager.getFreeSpaceInfo(item);
			System.out.println("freeSpaceInfo-->" + freeSpaceInfo);
			String[] freeSpaceArr = freeSpaceInfo.split(":");
			//freeSpaceArr[0] is host name
			//freeSpaceArr[1] is the size of the data directory in MB
			//freeSpaceArr[2] is the configured max space in MB for the local data directory
			//freeSpaceArr[3] is the percentage of data directory usage. 
//			//<Size of the local storage usage>:<Max local storage size configured to be used>:fraction of the local storage used (This might exceed 100%)>
			availableQuotaPrivate.put(freeSpaceArr[0], freeSpaceArr[1]+":"+freeSpaceArr[2]);
			System.out.println(freeSpaceArr[0]+":"+freeSpaceArr[1]+":"+freeSpaceArr[2]);
		}
		System.out.println("bbbbb ---> ");
		
		HashMap availableQuotaPublic = new HashMap();
		//This will have <host>:<used>:<Full>
		//This map is from dsfs command
		availableQuotaPublic.put("sd01", "0:40");
		availableQuotaPublic.put("sd02", "0:40");
		
		//Here we assume that we need at least three times of each subgraph size available on the remote AcaciaInstance. Because first we
		//need to send the Zip file and the unzip it. Then have to upload to Neo4j.
		
		HashMap freeSpaceMap = new HashMap();
		Iterator itr = availableQuotaPrivate.entrySet().iterator();
		while(itr.hasNext()){
			Map.Entry pairs = (Map.Entry) itr.next();
			String[] strArr = pairs.getValue().toString().split(":");
			//freeSpaceMap.put(pairs.getKey(), (Long.parseLong(strArr[1]) - Long.parseLong(strArr[0])));
			freeSpaceMap.put(pairs.getKey(), (Double.parseDouble(strArr[1]) - Double.parseDouble(strArr[0])));
		}
		
        java.util.Iterator itr23 = freeSpaceMap.entrySet().iterator();
        System.out.println("------------>freeSpaceMap");
        
	    while(itr23.hasNext()){
	    	java.util.Map.Entry pairs = (Entry) itr23.next();
	        System.out.println("++++---+++>" + pairs.getKey() + " value : " + pairs.getValue());
	    }
	    
        java.util.Iterator itr45 = requiredSpaceMap.entrySet().iterator();
        System.out.println("------------>requiredSpaceMap");
        
	    while(itr45.hasNext()){
	    	java.util.Map.Entry pairs = (Entry) itr45.next();
	        System.out.println("====---===>" + pairs.getKey() + " value : " + pairs.getValue());
	    }
		
		System.out.println("bbbbb --->123");
		LinkedHashMap lMap = sortHashMapByValuesDecendingDouble(freeSpaceMap);
		
        java.util.Iterator itrlmap = lMap.entrySet().iterator();
        System.out.println("------------>sorted free space map");
        
	    while(itrlmap.hasNext()){
	    	java.util.Map.Entry pairs = (Entry) itrlmap.next();
	        System.out.println(">>>--->>>" + pairs.getKey() + " value : " + pairs.getValue());
	    }
		
		System.out.println("bbbbb --->456");
		LinkedHashMap pMap = sortHashMapByValuesDecendingDouble(requiredSpaceMap);
		
        java.util.Iterator itrPmap = pMap.entrySet().iterator();
        System.out.println("------------>sorted free space map");
        
	    while(itrPmap.hasNext()){
	    	java.util.Map.Entry pairs = (Entry) itrPmap.next();
	        System.out.println("<<<---<<<" + pairs.getKey() + " value : " + pairs.getValue());
	    }
		
		System.out.println("bbbbb --->789");
		
		System.out.println("Available (lMap) : " + lMap.toString());
		System.out.println("Required (pMap) : " + pMap.toString());
		
		//Now we follow first-fit decreasing heuristic
		//We pack the largest subgraph to the largest available free space. Then we re-calculate the available free space
		itr = pMap.entrySet().iterator();
		//We do this for each subgraph
		LinkedHashMap packedItemsMapPrivate = new LinkedHashMap();
		LinkedHashMap remainingItemsMap = new LinkedHashMap();
		while(itr.hasNext()){
			
			Map.Entry pairs = (Map.Entry) itr.next();
			Map.Entry lMapPair = (Map.Entry)lMap.entrySet().iterator().next();
			double newFreeSpace = (Double)(lMapPair.getValue()) - (Double)pairs.getValue();
			
			if(newFreeSpace < 0){
				System.out.println("Cannot pack : " + pairs.getValue() + ". The space is not enough in private cloud");
				remainingItemsMap.put(pairs.getKey(), pairs.getValue());
			}else{
				lMap.put(lMapPair.getKey(), newFreeSpace);
				packedItemsMapPrivate.put(pairs.getKey(),lMapPair.getKey());
				lMap = sortHashMapByValuesDecendingDouble(lMap);
			}
		}

		System.out.println("packedItemsMap : " + packedItemsMapPrivate.toString());
		System.out.println("remainingItemsMap : " + remainingItemsMap.toString());
		System.out.println("Updated Available (lMap) : " + lMap.toString());
		
		boolean flag = false;
		LinkedHashMap packedItemsMapPublic = null;
		
		if(remainingItemsMap.size() != 0){
			//Next its time to check the availability in the public cloud
			HashMap freeSpaceMapPublic = new HashMap();
			itr = availableQuotaPublic.entrySet().iterator();
			while(itr.hasNext()){
				Map.Entry pairs = (Map.Entry) itr.next();
				String[] strArr = pairs.getValue().toString().split(":");
				//freeSpaceMap.put(pairs.getKey(), (Long.parseLong(strArr[1]) - Long.parseLong(strArr[0])));
				freeSpaceMapPublic.put(pairs.getKey(), (Double.parseDouble(strArr[1]) - Double.parseDouble(strArr[0])));
			}
			LinkedHashMap lMapPublic = sortHashMapByValuesDecendingDouble(freeSpaceMapPublic);		
			
			itr = remainingItemsMap.entrySet().iterator();
			//We do this for each subgraph
			packedItemsMapPublic = new LinkedHashMap();
			LinkedHashMap remainingItemsMapPublic = new LinkedHashMap();
			while(itr.hasNext()){
				
				Map.Entry pairs = (Map.Entry) itr.next();
				Map.Entry lMapPair = (Map.Entry)lMapPublic.entrySet().iterator().next();
				double newFreeSpace = (Double)(lMapPair.getValue()) - (Double)pairs.getValue();
				
				if(newFreeSpace < 0){
					System.out.println("Cannot pack : " + pairs.getValue() + ". The space is not enough in public cloud");
					flag =true;
					remainingItemsMapPublic.put(pairs.getKey(), pairs.getValue());
				}else{
					lMapPublic.put(lMapPair.getKey(), newFreeSpace);
					//This will be the output
					
					packedItemsMapPublic.put(pairs.getKey(),lMapPair.getKey());
					lMapPublic = sortHashMapByValuesDecendingDouble(lMapPublic);
				}
			}
			
			System.out.println("packedItemsMap public : " + packedItemsMapPublic.toString());
			System.out.println("remainingItemsMap : " + remainingItemsMapPublic.toString());
			System.out.println("Updated Available (lMapPublic) : " + lMapPublic.toString());
		}
		
		if(flag){
			return null; //This means based on the available quotas and requested amounts if allocations the current allocation plan cannot be made.
			             //Either have to allocate more public cloud hosts or delete some of the data from the private cloud (which is less desirable).
		}else{
			result = new HashMap();
			result.putAll(packedItemsMapPrivate);
			if(packedItemsMapPublic != null){ //Because in some cases we might end up packing every subgraph into the private cloud.
				result.putAll(packedItemsMapPublic);
			}
		}
				
		return result;
	}
	
	private static LinkedHashMap sortHashMapByValuesDecendingLong(HashMap passedMap) {
		   List mapKeys = new ArrayList(passedMap.keySet());
		   List mapValues = new ArrayList(passedMap.values());
		   Collections.sort(mapValues, Collections.reverseOrder());
		   Collections.sort(mapKeys);

		   LinkedHashMap sortedMap = new LinkedHashMap();

		   Iterator valueIt = mapValues.iterator();
		   while (valueIt.hasNext()) {
		       Object val = valueIt.next();
		       Iterator keyIt = mapKeys.iterator();

		       while (keyIt.hasNext()) {
		           Object key = keyIt.next();
		           String comp1 = passedMap.get(key).toString();
		           String comp2 = val.toString();

		           if (comp1.equals(comp2)){
		               passedMap.remove(key);
		               mapKeys.remove(key);
		               sortedMap.put((String)key, (Long)val);
		               break;
		           }

		       }

		   }
		   return sortedMap;
		}
	
	private static LinkedHashMap sortHashMapByValuesDecendingDouble(HashMap passedMap) {
		   System.out.println("---------AAAA1-------------->");
		   List mapKeys = new ArrayList(passedMap.keySet());
		   System.out.println("---------AAAA2-------------->");
		   List mapValues = new ArrayList(passedMap.values());
		   System.out.println("---------AAAA3-------------->");
		   //Here we sort descending order of all the values.
		   Collections.sort(mapValues, Collections.reverseOrder());
		   System.out.println("---------AAAA4-------------->");
		   Collections.sort(mapKeys);
		   System.out.println("---------AAAA5-------------->");
           
		   //But then we need to figure out what are the keys corresponding to these values.
		   LinkedHashMap sortedMap = new LinkedHashMap();
		   System.out.println("---------AAAA6-------------->");
		   //We are iterating using the values map
		   Iterator valueIt = mapValues.iterator();
		   System.out.println("---------AAAA7-------------->");
		   while (valueIt.hasNext()) {
		       Object val = valueIt.next();
		       Iterator keyIt = mapKeys.iterator();
		       //Now we are iterating using the keys.
		       System.out.println("---------BBBB8-------------->");
		       while (keyIt.hasNext()) {
		           Object key = keyIt.next();
		           String comp1 = passedMap.get(key).toString();
		           String comp2 = val.toString();
		           System.out.println("---------AAAA9-------------->");
		           if (comp1.equals(comp2)){
		               passedMap.remove(key);
		               mapKeys.remove(key);
		               System.out.println("---------AAAA10--------------> key : " + key + " val:"+val);
		               sortedMap.put((String)key, (Double)val);
		               System.out.println("---------AAAA11-------------->");
		               break;
		           }
		       }
		   }
		   System.out.println("---------AAAA8-------------->");
		   return sortedMap;
		}
	
	public static void main(String[] args) {
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
	
	
//	public void run(){
//		while(true){
//			for(String host : hostList){
//				AcaciaManager.getFreeSpaceInfo(host);
//			}
//		}
//	}
}
