package org.acacia.localstore.java;

public class AcaciaNativeLocalStore {

    private var nodeRecord:NodeRecord=null;
    private var relationshipRecord:RelationshipRecord=null;

    public def this(var nodeRecord:NodeRecord, var relationshipRecord:RelationshipRecord) {
        this.nodeRecord=nodeRecord;
        this.relationshipRecord=relationshipRecord;
    }
    
    public getNodeRecord():NodeRecord{
    	return nodeRecord;
    }
    
    public getRelationshipRecord():RelationshipRecord{
    	return relationshipRecord;
    }
    
}