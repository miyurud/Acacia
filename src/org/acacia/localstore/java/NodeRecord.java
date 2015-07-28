package org.acacia.localstore.java;

public class NodeRecord {
	private int flag;
	private int firstRelationshipId;
	private int firstPropertyId;
		
	public NodeRecord(int flag, int firstRelationshipId, int firstPropertyId) {
	    this.flag = flag;
	    this.firstRelationshipId = firstRelationshipId;
	    this.firstPropertyId = firstPropertyId;
	}

	public int getflag(){
		return flag;
	}

	public int getfirstRelationshipId(){
		return firstRelationshipId;
	}

	public int getfirstPropertyId(){
		return firstPropertyId;
	}
}
