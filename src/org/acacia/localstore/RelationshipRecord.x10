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

public class RelationshipRecord {

	//variables for relationship record
	
 	private var flag:Int;
 	private var firstNodeId:Long;
	private var secondNodeId:Long;
	private var relationshipTypeId:Int;
	private var firstNodePreviousRelationshipId:Int;
	private var firstNodeNextRelationshipId:Int;
	private var secondNodePreviousRelationshipId:Int;
	private var secondNodeNextRelationshipId:Int;
 	private var byteArrayRelationship:Rail[Byte];

//constructor of relationshipNode
	
	public def this(var flag:Int, var firstNodeId:Long, var secondNodeId:Long,var relationshipTypeId:Int,
					var firstNodePreviousRelationshipId:Int, var firstNodeNextRelationshipId:Int,
					var secondNodePreviousRelationshipId:Int,var secondNodeNextRelationshipId:Int) {
		this.flag=flag;
	 	this.firstNodeId=firstNodeId;
		this.secondNodeId=secondNodeId;
		this.firstNodePreviousRelationshipId=firstNodePreviousRelationshipId;
		this.firstNodeNextRelationshipId=firstNodeNextRelationshipId;
		this.secondNodePreviousRelationshipId=secondNodePreviousRelationshipId;
		this.secondNodeNextRelationshipId=secondNodeNextRelationshipId;
	}

	//getters
	
	public def getFlag():Int{
	 	return flag;
	}
	
	public def getFirstNodeId():Long{
		return firstNodeId;
	}
	
	public def getSecondNodeId():Long{
		return secondNodeId;
	}
	
	public def getFirstNodePreviousRelationshipId():Int{
		return firstNodePreviousRelationshipId;
	}
	
	public def getFirstNodeNextRelationshipId():Int{
		return firstNodeNextRelationshipId;
	}
	
	public def getSecondNodePreviousRelationshipId():Int{
		return secondNodePreviousRelationshipId;
	}
	
	public def getSecondNodeNextRelationshipId():Int{
		return secondNodeNextRelationshipId;
	}
	
	public def getbyteArrayRelationship():Rail[Byte]{
	 	return byteArrayRelationship;
	}
	
	public def readByteArrayRelationshipRecord(){
	 	var path:String=Utils.call_getAcaciaProperty("org.acacia.server.runtime.location");		
	 	var file:File = new File(path);
	 	for (line in file.lines()) {
	       
	 	}
	}
}