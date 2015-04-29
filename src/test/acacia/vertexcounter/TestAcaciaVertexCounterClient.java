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

package test.acacia.vertexcounter;

import org.acacia.vertexcounter.VertexCounter;
import org.acacia.vertexcounter.VertexCounterClient;

/**
 * Class TestAcaciaVertexCounter
 */
public class TestAcaciaVertexCounterClient {
    /**
     * Main method 
     */
    public static void main(String[] args) {   	
    	System.out.println("Its client");
    	VertexCounterClient.loadVertexCountInfo();
    	System.out.println("AAAAAAAAAAAA");
    	System.out.println(VertexCounterClient.getNextVertex("24"));
    	System.out.println("BBBBBBBBBBBBBB");
    	System.out.println(VertexCounterClient.getNextVertex("24"));
    	System.out.println("CCCCCCCCCCCCCC");
    	//VertexCounterClient.save();
    	VertexCounterClient.loadVertexCountInfo();
    	System.out.println(VertexCounterClient.getNextVertex("24"));
    	System.out.println("DDDDDDDDDDDDDD");
    }
}