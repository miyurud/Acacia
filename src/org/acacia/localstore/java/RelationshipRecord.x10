package org.acacia.localstore.java;

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