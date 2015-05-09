package org.acacia.partitioner.index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.acacia.log.java.Logger_Java;
import org.acacia.vertexcounter.VertexCounterProtocol;
import org.acacia.vertexcounter.VertexSingleton;

public class PartitionIndexServiceSession extends Thread{
	private Socket sessionSkt = null;
	private PartitionIndexObject obj = null;
	private PartitionIndex refToObj;
	
	public PartitionIndexServiceSession(PartitionIndexObject obj2, Socket skt, PartitionIndex ref){
		obj = obj2; 
		sessionSkt = skt;
		refToObj = ref;
	}
	
	public void run(){
		//System.out.println("Started PartitionIndexServiceSession theard : " + Thread.currentThread().getId());
		try{
			BufferedReader buff = new BufferedReader(new InputStreamReader(sessionSkt.getInputStream()));
			PrintWriter out = new PrintWriter(sessionSkt.getOutputStream());
			String msg = null;

			while((msg = buff.readLine())!= null){
				if(msg.equals(PartitionIndexProtocol.EXIT)){
//					out.println(PartitionIndexProtocol.EXIT_ACK);
//					out.flush();
					sessionSkt.close();
					break;
				}else{
					process(msg, buff, out);
				}
			}
			
//			out.println(VertexCounterProtocol.EXIT_ACK);
//			out.flush();
			sessionSkt.close();
		}catch(IOException e){
			Logger_Java.error("Error : " + e.getMessage());
		}
		
		//System.out.println("Exitting thread : " + Thread.currentThread().getId());
	}
	
	/**
	 * This method processes the query requests to AcaciaForntEnd. This is the main function that answers the queries.
	 */
	public void process(String msg, BufferedReader buff, PrintWriter out) throws IOException{
		String str = null;
		
		if(msg.equals(PartitionIndexProtocol.PARTITION_ID_REQ)){//List the graphs on Acacia
			out.println(PartitionIndexProtocol.SEND_VERT_ID); //Sometimes this might introduce additional overhead for the Hadoop job.
			out.flush();
			
			str = buff.readLine();
			
			int vval = Integer.parseInt(str);
			int vval2 = obj.getPartitionID(vval);
			
			//System.out.println(" for vertex : " + vval + " the resolved result is : " + vval2);
			
			out.println(vval2);
			out.flush();			
		}else if(msg.equals(PartitionIndexProtocol.BATCH_PARTITION_ID_REQ)){
			str = buff.readLine();
			
			int vval = -1;
			
			while(!str.equals(PartitionIndexProtocol.BATCH_PARTITION_ID_REQ_DONE)){
				vval = Integer.parseInt(str);
				vval = obj.getPartitionID(vval);
				
				out.println(vval);
				out.flush();
				
				str = buff.readLine();
			}
		}else if(msg.equals(PartitionIndexProtocol.EXIT)){
			out.println(PartitionIndexProtocol.EXIT_ACK);
			out.flush();
			sessionSkt.close();
		}else if (msg.equals(PartitionIndexProtocol.CREATE_INDEX)){
			refToObj.singletonObj = new PartitionIndexObject();
			out.println(PartitionIndexProtocol.CREATE_INDEX_ACK);
			out.flush();
		}else{
			//This is the default response
			out.println(PartitionIndexProtocol.SEND);
			out.flush();
		}
	}
}