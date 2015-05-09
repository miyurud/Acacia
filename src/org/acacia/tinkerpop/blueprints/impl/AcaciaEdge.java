package org.acacia.tinkerpop.blueprints.impl;

import com.tinkerpop.blueprints.Edge;

/**
 * This is an implementation of the Edge interface defined in the blueprints standard.
 */

public class AcaciaEdge implements com.tinkerpop.blueprints.Edge {
	public AcaciaEdge addEdge(String label, AcaciaVertex inVertex){
	      return null;
	}

	public Iterable<AcaciaEdge> getEdges(com.tinkerpop.blueprints.Direction direction, String[] labels){
	      return null;
	}

	public Iterable<com.tinkerpop.blueprints.Vertex> getVertices(com.tinkerpop.blueprints.Direction direction, String labels){
	      return null;
	}

    public com.tinkerpop.blueprints.Vertex getVetex(com.tinkerpop.blueprints.Direction a1){
          return null;
    }
    
    public com.tinkerpop.blueprints.Vertex getVertex(com.tinkerpop.blueprints.Direction a1){
          return null;
    }
    
    public String getLabel(){
          return null;
    }
    
    public void setProperty(String a1, Object a2){
    
    }
    
    public <O> O removeProperty(String a1){
          return null;
    }     
    
    public <O> O getProperty(String a1){
          return null;
    }
    
    public java.util.Set<String> getPropertyKeys(){
          return null;
    }
    
    public <T extends Comparable<T>> com.tinkerpop.blueprints.GraphQuery has(String a1, T t, com.tinkerpop.blueprints.Query.Compare compare){
          return null;
    }
    
    public void remove(){
    
    }
    
    public Object getId(){
          return null;
    }    
}