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

/**
 * This is an implementation of the Vertex interface defined in the blueprints standard.
 */

public class AcaciaVertex implements com.tinkerpop.blueprints.Vertex{
	// public def addEdge(var label:String, var inVertex:com.tinkerpop.blueprints.Vertex):com.tinkerpop.blueprints.Edge{
	// 	return null;
	// }

    public com.tinkerpop.blueprints.Edge addEdge(String label, com.tinkerpop.blueprints.Vertex inVertex){
        return null;
    }

    // public def getEdges(var direction:com.tinkerpop.blueprints.Direction, var labels:Rail[String]):java.lang.Iterable{
    //     return null;
    // }

    public Iterable<com.tinkerpop.blueprints.Edge> getEdges(com.tinkerpop.blueprints.Direction direction, String[] labels){
         return null;
    }
      
    
    // public def getVertex(var a1:com.tinkerpop.blueprints.Direction):com.tinkerpop.blueprints.Vertex{
    //     return null;
    // }
    
    public com.tinkerpop.blueprints.Vertex getVertex(com.tinkerpop.blueprints.Direction direction){
         return null;
    }
    
    // public def getVertices(var direction:com.tinkerpop.blueprints.Direction, var labels:x10.interop.Java.array[x10.lang.String]):java.lang.Iterable{
    //     return null;
    // }
    
    public Iterable<com.tinkerpop.blueprints.Vertex> getVertices(com.tinkerpop.blueprints.Direction direction, String[] labels){
         return null;
    }
    
    // public def query():com.tinkerpop.blueprints.VertexQuery{
    //     return null;
    // }
    
    public com.tinkerpop.blueprints.VertexQuery query(){
          return null;
    }
    
    // public def has(a1:x10.lang.String, a2:com.tinkerpop.blueprints.Predicate, a3:x10.lang.Any):com.tinkerpop.blueprints.GraphQuery{
    //     return null;
    // }
    
    public com.tinkerpop.blueprints.GraphQuery has(String a1, com.tinkerpop.blueprints.Predicate a2, Object a3){
          return null;
    }
    
    // public def getProperty(var a1:x10.lang.String):x10.lang.Any{
    //     return null;
    // }
    
    public <O> O getProperty(String a1){
          return null;
    }
    
    
    // public def setProperty(var a1:x10.lang.String, var a2:x10.lang.Any):void{
    //     
    // }
    
    public void setProperty(String a1, Object a2){
    
    }
    
    // public def removeProperty(var a1:x10.lang.String):x10.lang.Any{
    //     return null;
    // }  
    
    public <O> O removeProperty(String a1){
            return null;
    }
    
    public java.util.Set<String> getPropertyKeys(){
        return null;
    }
    
    public void remove(){
    
    }
    
    public Object getId(){
        return null;
    }
}