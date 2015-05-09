package test.acacia.vertexcounter;

import org.acacia.vertexcounter.VertexCounter;
import org.acacia.vertexcounter.VertexCounterClient;

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