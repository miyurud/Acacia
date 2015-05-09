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