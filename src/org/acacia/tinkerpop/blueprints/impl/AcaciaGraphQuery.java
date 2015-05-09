package org.acacia.tinkerpop.blueprints.impl;

public class AcaciaGraphQuery implements com.tinkerpop.blueprints.GraphQuery {

   public AcaciaGraphQuery has(String key){
       return null;
   }
   
   public AcaciaGraphQuery has(String key, Object value){
       return null;
   }

   public AcaciaGraphQuery has(String key, AcaciaPredicate predicate, Object value){
        return null;
   }

   public AcaciaGraphQuery hasNot(String key){
        return null;
   }
 
 	public AcaciaGraphQuery hasNot(String key, Object value){
 		return null;
 	}

    public AcaciaGraphQuery limit(int limit){
        return null;
    }
    
    public com.tinkerpop.blueprints.GraphQuery has(String a1, com.tinkerpop.blueprints.Predicate a2, Object a3){
        return null;
    }
    
   
    public <T extends Comparable<T>> com.tinkerpop.blueprints.GraphQuery has(String a1, T a2,com.tinkerpop.blueprints.Query.Compare a3){
         return null;
    } 
    
    public <T extends Comparable<?>> com.tinkerpop.blueprints.GraphQuery interval(String a1, T a2, T a3){
         return null;
    }
    
    public Iterable<com.tinkerpop.blueprints.Vertex> vertices(){
         return null;
    }
    
    public Iterable<com.tinkerpop.blueprints.Edge> edges(){
         return null;
    }
}