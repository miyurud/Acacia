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

package org.acacia.tinkerpop.blueprints.impl;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Features;
import com.tinkerpop.blueprints.GraphQuery;

/**
 * The AcaciaGraph is the main class of implementation of Tinkerpop Blueprints interface.
 * There is no point of implementation of the rest of the tinker pop interfaces (e.g., vertex, Edge, etc.) in X10 which gives
 * a problem of x10.lang.Compare interface implementation (Refer the source version on Jan 18 2014).
 */
public class AcaciaGraph implements com.tinkerpop.blueprints.Graph {
    /**
     * Default constructor 
     */
    public def this() {
        
    }
    
    public def getFeatures():Features{
    	return null;
    }
    
    public def addVertex(a1:x10.lang.Any):com.tinkerpop.blueprints.Vertex{
    	return null;
    }
      
    
    public def getVertex(a1:x10.lang.Any):com.tinkerpop.blueprints.Vertex{
    	return null;
    }
    
    public def removeVertex(a1:com.tinkerpop.blueprints.Vertex):void{
    	
    }
    
    public def getVertices():java.lang.Iterable{
    	return null;
    }
    
    public def getVertices(a1:x10.lang.String, a2:x10.lang.Any):java.lang.Iterable{
    	return null;
    }
    
    public def addEdge(a1:Any, a2:com.tinkerpop.blueprints.Vertex, a3:com.tinkerpop.blueprints.Vertex, a4:String):com.tinkerpop.blueprints.Edge{
    	return null;
    }
    
    public def getEdge(a1:Any):com.tinkerpop.blueprints.Edge{
    	return null;
    }
    
    public def removeEdge(a1:com.tinkerpop.blueprints.Edge):void{
    
    }
    
    public def getEdges():java.lang.Iterable{
    	return null;
    }
    
    public def getEdges(a1:String, a2:Any):java.lang.Iterable{
    	return null;
    }
    
    public def query():AcaciaGraphQuery{
    	return null;
    }
    
    public def has(a1:x10.lang.String, a2:com.tinkerpop.blueprints.Predicate, a3:x10.lang.Any):com.tinkerpop.blueprints.GraphQuery{
        return null;
    }
    
    public def shutdown():void{
    
    }
    
}