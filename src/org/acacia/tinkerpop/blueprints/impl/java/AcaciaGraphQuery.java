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

package org.acacia.tinkerpop.blueprints.impl.java;

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