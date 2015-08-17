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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import org.acacia.util.java.Utils_Java;

public class AcaciaLocalStoreFactory{
	
	/**
	 * The fillowing method loads an AcaciaLocalStore located on a given base directory. The type of the local store will be determined by
	 * reading the catalog of the local store.
	 * @param graphID
	 * @param partitionID
	 * @param baseDir
	 * @param isCentralStore
	 * @return
	 */
	public static AcaciaLocalStore load(int graphID, int partitionID, String baseDir, boolean isCentralStore){
		AcaciaLocalStore result = null;
		//We need to read the catalog and determine the type
		int storeType = Integer.parseInt(AcaciaLocalStoreCatalogManager.readCatalogRecord(baseDir + File.separator + graphID + "_" + partitionID, "head"));
		
		if(storeType == AcaciaLocalStoreTypes.HASH_MAP_LOCAL_STORE){
			result = new AcaciaHashMapLocalStore(graphID, partitionID);
		}else if(storeType == AcaciaLocalStoreTypes.HASH_MAP_NATIVE_STORE){
			result = new AcaciaHashMapNativeStore(graphID, partitionID, baseDir, isCentralStore);
		}
		
		return result;
	}
	
	public static AcaciaLocalStore create(int graphID, int partitionID, String baseDir, boolean isCentralStore, int storeType){
		AcaciaLocalStore result = null;
		
		if(storeType == AcaciaLocalStoreTypes.HASH_MAP_LOCAL_STORE){
			result = new AcaciaHashMapLocalStore(graphID, partitionID);
		}else if(storeType == AcaciaLocalStoreTypes.HASH_MAP_NATIVE_STORE){
			result = new AcaciaHashMapNativeStore(graphID, partitionID, baseDir, isCentralStore);
		}
		
		return result;
	}
}
