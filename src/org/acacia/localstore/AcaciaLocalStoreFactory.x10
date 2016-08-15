/**
 * Copyright 2015 Acacia Team

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acacia.localstore;

import java.io.File;
import org.acacia.localstore.AcaciaLocalStoreCatalogManager;
import org.acacia.localstore.AcaciaHashMapNativeStore;
import org.acacia.localstore.AcaciaHashMapIncrementalStore;

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
 	public static def load(graphID:Int, partitionID:Int, baseDir:String , isCentralStore:Boolean):AcaciaLocalStore{
  		var result:AcaciaLocalStore  = null;
 		//We need to read the catalog and determine the type
        var storeType:Int = -1n;
        
        if(!isCentralStore){
          storeType = Int.parse(AcaciaLocalStoreCatalogManager.readCatalogRecord(baseDir + File.separator + graphID + "_" + partitionID, "head"));
        }else{
          storeType = Int.parse(AcaciaLocalStoreCatalogManager.readCatalogRecord(baseDir + File.separator + graphID + "_centralstore" + File.separator + graphID + "_" + partitionID, "head"));
        }

 		if(storeType == AcaciaLocalStoreTypes.HASH_MAP_LOCAL_STORE){
 			result = new AcaciaHashMapLocalStore(graphID, partitionID);
 		}else if(storeType == AcaciaLocalStoreTypes.HASH_MAP_NATIVE_STORE){
 			result = new AcaciaHashMapNativeStore(graphID, partitionID, baseDir, isCentralStore);
 		}

 		return result;
 	}

 	public static def create(graphID:Int, partitionID:Int, baseDir:String , isCentralStore:Boolean, storeType:Int):AcaciaLocalStore{
 		var result:AcaciaLocalStore = null;

	if(storeType == AcaciaLocalStoreTypes.HASH_MAP_LOCAL_STORE){
            result = new AcaciaHashMapLocalStore(graphID, partitionID);
	}else if(storeType == AcaciaLocalStoreTypes.HASH_MAP_NATIVE_STORE){
            result = new AcaciaHashMapNativeStore(graphID, partitionID, baseDir, isCentralStore);
	}else if(storeType == AcaciaLocalStoreTypes.HASH_MAP_INCREMENTAL_STORE){
            result = new AcaciaHashMapIncrementalStore(graphID, partitionID, baseDir, isCentralStore);
	}
	
	return result;
	}
}
