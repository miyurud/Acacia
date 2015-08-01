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

/**
 * This class contains meta-information pertaining to just single relationship. A relationship is essentially a linkage between two
 * vertices.
 * 
 * Note that this class does not contain any payload kind of information.
 * 
 * In this data model, there can be multiple relationships having the same starting vertex ID and ending vertex ID. Because if we take one
 * single node out of the two nodes, that will have more than one neighbours. Since we are not having a list of neighbour IDs in this design,
 * we will have to have multiple records having the same starting and ending vertices.
 *
 */
public class RelationshipRecord {
	private int flag;
	private long firstNodeId ;
	private long secondNodeId ;
	private int relationshipTypeId ;
	private int firstNodePreviousRelationshipId ;
	private int firstNodeNextRelationshipId ;
	private int secondNodePreviousRelationshipId ;
	private int secondNodeNextRelationshipId ;

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
