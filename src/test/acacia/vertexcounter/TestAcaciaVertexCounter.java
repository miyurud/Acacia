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

import org.acacia.vertexcounter.java.VertexCounter;
import org.acacia.vertexcounter.java.VertexCounterClient;

/**
 * Class TestAcaciaVertexCounter
 */
public class TestAcaciaVertexCounter {
    /**
     * Main method 
     */
    public static void main(String[] args) {
    	VertexCounter obj = new VertexCounter();
    	obj.run();
    	
    	// System.sleep(5000);
    	// 
    	// VertexCounterClient.loadVertexCountInfo();
    	// Console.OUT.println(VertexCounterClient.getNextVertex("24"));
    	// Console.OUT.println(VertexCounterClient.getNextVertex("24"));
    	while(true){
    		try{
    			Thread.currentThread().sleep(1000);
    		}catch(InterruptedException e){
    			//no worries
    		}
    	}
    }
}