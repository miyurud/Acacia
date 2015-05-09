package test.acacia.partitioner.index;

import org.acacia.partitioner.index.PartitionIndex;
import org.acacia.vertexcounter.VertexCounter;

public class TestPartitionIndex{
    public static void main(String[] args) {
    	PartitionIndex obj = new PartitionIndex(); //This is the number of vertices for Flickr in Acacia
    	obj.run();

/*    	while(true){
    		try{
    			Thread.currentThread().sleep(1000);
    		}catch(InterruptedException e){
    			//no worries
    		}
    	}*/
    }
}