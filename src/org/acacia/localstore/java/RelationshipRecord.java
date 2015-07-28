package org.acacia.localstore.java;

public class RelationshipRecord {
	private int flag;
	private long firstNodeId ;
	private long secondNodeId ;
	private int relationshipTypeId ;
	private int firstNodePreviousRelationshipId ;
	private int firstNodeNextRelationshipId ;
	private int secondNodePreviousRelationshipId ;
	private int secondNodeNextRelationshipId ;

//constructor of relationshipNode

	public RelationshipRecord (int flag , long firstNodeId , long secondNodeId ,int relationshipTypeId ,
				int firstNodePreviousRelationshipId , int firstNodeNextRelationshipId ,
				int secondNodePreviousRelationshipId ,int secondNodeNextRelationshipId ) {
	this.flag=flag;
 	this.firstNodeId=firstNodeId;
	this.secondNodeId=secondNodeId;
	this.relationshipTypeId = relationshipTypeId;
	this.firstNodePreviousRelationshipId=firstNodePreviousRelationshipId;
	this.firstNodeNextRelationshipId=firstNodeNextRelationshipId;
	this.secondNodePreviousRelationshipId=secondNodePreviousRelationshipId;
	this.secondNodeNextRelationshipId=secondNodeNextRelationshipId;
}

//getters

	public int getFlag() {
		return flag;
}	

	public long getFirstNodeId() {
		return firstNodeId;
	}
	
	public long getSecondNodeId() {
		return secondNodeId;
	}
	
	public int getRelationshipTypeId(){
		return relationshipTypeId;
	}
	public int getFirstNodePreviousRelationshipId() {
		return firstNodePreviousRelationshipId;
	}
	
	public int getFirstNodeNextRelationshipId() {
		return firstNodeNextRelationshipId;
	}
	
	public int getSecondNodePreviousRelationshipId() {
		return secondNodePreviousRelationshipId;
	}
	
	public int getSecondNodeNextRelationshipId() {
		return secondNodeNextRelationshipId;
	}

}
