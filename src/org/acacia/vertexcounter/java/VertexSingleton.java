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

package org.acacia.vertexcounter.java;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.acacia.metadata.db.java.MetaDataDBInterface;

/**
 * Class VertexSingleton is used to provide a unique counter for vertices.
 */
public class VertexSingleton {
	private long currentVertex = 0;
	private long currentGraph = 0;
	private HashMap<Long, Long> graphMap = null;

    /**
     * Default constructor 
     */
    public VertexSingleton() {
    	load();
    }
    
    public void load(){
    	synchronized(this){
	    	System.out.println("XXXXXXXX1");
	    	graphMap = new HashMap<Long, Long>();
	    	String[] result = MetaDataDBInterface.runSelectPureJava("SELECT idgraph,vertexcount FROM ACACIA_META.GRAPH");
	    	System.out.println("XXXXXXXX2");
	
	    	int c= 0;
	    	for(int i = 0; i < result.length; i+=2){    		
	    		if(result[i+1] == null){
	    			System.out.println("" + result[i] + "-->" + 0);
	    			graphMap.put(Long.parseLong(result[i]), 0l);    			
	    		}else{
	    			System.out.println("" + result[i] + "-->" + result[i+1]);
	    			graphMap.put(Long.parseLong(result[i]), Long.parseLong(result[i+1]));
	    		}
	    	}
    	}

    	
    	
//		while (itr.hasNext()){
//			System.out.println("" + c + "-->" + itr.next());
//			c++;
//		}
		System.out.println("XXXXXXXX4");
    	/*
    	String value = null;
    	
    	if(result.size > 0){   		
    		if((value=itr.next()) == null){
    			graphMap.put(Long.parse(result(0)), 0);
    		}else{
    			System.out.println("" + result(0) + " " + result(1));
    			graphMap.put(Long.parse(result(0)), Long.parse(result(1)));
    		}
    	}
    	*/
    	//currentVertex = Long.parse(result);
    }
    
    public boolean save(){  	
    	Iterator it = graphMap.entrySet().iterator();
    	boolean res = false;
    		
    	while(it.hasNext()){
    		Map.Entry<Long, Long> pairs = (Map.Entry<Long, Long>)it.next();
    		System.out.println("" + pairs.getKey() + " " + pairs.getValue());
    		
    		if(pairs.getKey()  == currentGraph){
    			res = MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET VERTEXCOUNT=" + currentVertex + " WHERE IDGRAPH=" + pairs.getKey());
    		}else{
    			res = MetaDataDBInterface.runUpdate("UPDATE ACACIA_META.GRAPH SET VERTEXCOUNT=" + pairs.getValue() + " WHERE IDGRAPH=" + pairs.getKey());
    		}    		
    		
    		if(!res){
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    public void printContent(){
    	Iterator it = graphMap.entrySet().iterator();
        
    	System.out.println("BBBBBBBBBBBBB1--XXXXXX");
    	while(it.hasNext()){
    		Map.Entry<Long, Long> pairs = (Map.Entry<Long, Long>)it.next();
    		System.out.println(">>" + pairs.getKey() + "<< >>" + pairs.getValue());
    	}
    	System.out.println("BBBBBBBBBBBBB2--XXXXXX");
    }
    
    public void setDefaultGraphID(long graphID){
    	currentGraph = graphID;
    	
    	Iterator it = graphMap.entrySet().iterator();
        	
		Long b = graphMap.get(graphID);
		
		if(b != null){
			currentVertex = b.longValue();
		}else{
			currentVertex = 0l;
		}
    }
    
    public long getNextVertexID(){
    	synchronized(this){
    		currentVertex++;
    		return currentVertex;
    	}
    }
    
    public long getNextVertexID(long graphID){
    	long result = 0;
    	
    	synchronized(this){
    		
        	Iterator it = graphMap.entrySet().iterator();
            
        	//System.out.println("VVVVVVVVVVV1");
        	//while(it.hasNext()){
        	//	Map.Entry<Long, Long> pairs = (Map.Entry<Long, Long>)it.next();
        	//	System.out.println(">>" + pairs.getKey() + "<< >>" + pairs.getValue());
        	//}
        	//System.out.println("VVVVVVVVVVV2");
    		
    		Long b = graphMap.get(graphID);
    		
    		//System.out.println("for graph : " + graphID + " got : " + b);
    		
    		if(b != null){
		    	result = b.longValue();
		    	result++;
		    	graphMap.put(graphID, result);
    		}else{
    			//The graph ID does not exists. Unknown graph id.
    			graphMap.put(graphID, 0l);
    			return 0l;
    		}
    	}
    	return result;
    }
}