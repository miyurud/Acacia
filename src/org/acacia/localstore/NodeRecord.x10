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

package org.acacia.localstore;
import x10.io.File;
import org.acacia.util.Utils;

public class NodeRecord {

	private var flag:Int;
	private var firstRelationshipId:Int;
	private var firstPropertyId:Int;
 	private var byteArrayNodes:Rail[Byte];
		
	
	public def this(flag:Int, firstRelationshipId:Int, firstPropertyId:Int){
	    this.flag = flag;
	    this.firstRelationshipId = firstRelationshipId;
	    this.firstPropertyId = firstPropertyId;
	}
	
	public def getflag():Int{
		return flag;
	}

	public def getfirstRelationshipId():Int{
		return firstRelationshipId;
	}
	
	public def getfirstPropertyId():Int{
		return firstPropertyId;
	}

 	public def byteArrayNodes():Rail[Byte]{
 		return byteArrayNodes;
 	}
 
 	public def readByteArrayNoderecord(){
 		var path:String=Utils.call_getAcaciaProperty("org.acacia.server.runtime.location");		
 		var file:File = new File(path);
 		for (line in file.lines()) {
 
 		}
 }
   
}