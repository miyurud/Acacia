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

public class RelationshipRecord {

	//variables for relationship record
	
	private var firstNodeId:int;
	private var secondNodeId:int;
	private var relationshipTypeId:int;
	private var firstNodePreviousRelationshipId:int;
	private var firstNodeNextRelationshipId:int;
	private var secondNodePreviousRelationshipId:int;
	private var secondNodeNextRelationshipId:int;

//constructor of relationshipNode
	
public def this(var firstNodeId:int, var secondNodeId:int,var relationshipTypeId:int,
				var firstNodePreviousRelationshipId:int, var firstNodeNextRelationshipId:int,
				var secondNodePreviousRelationshipId:int,var secondNodeNextRelationshipId:int) {
	
	this.firstNodeId=firstNodeId;
	this.secondNodeId=secondNodeId;
	this.firstNodePreviousRelationshipId=firstNodePreviousRelationshipId;
	this.firstNodeNextRelationshipId=firstNodeNextRelationshipId;
	this.secondNodePreviousRelationshipId=secondNodePreviousRelationshipId;
	this.secondNodeNextRelationshipId=secondNodeNextRelationshipId;
}

//getters

public getFirstNodeId():int{
	return firstNodeId;
}

public getSecondNodeId():int{
	return secondNodeId;
}

public getFirstNodePreviousRelationshipId():int{
	return firstNodePreviousRelationshipId;
}

public getFirstNodeNextRelationshipId():int{
	return firstNodeNextRelationshipId;
}

public getSecondNodePreviousRelationshipId():int{
	return secondNodePreviousRelationshipId;
}

public getSecondNodeNextRelationshipId():int{
	return secondNodeNextRelationshipId;
}

}