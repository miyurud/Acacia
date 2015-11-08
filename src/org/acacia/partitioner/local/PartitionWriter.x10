package org.acacia.partitioner.local;
import org.acacia.localstore.AcaciaHashMapLocalStore;
import java.io.File;
import java.lang.Runtime;
import java.lang.Process;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import x10.interop.Java;

public class PartitionWriter {

 	private var file:File;
 	private var bw:BufferedWriter;
 	private var fw:FileWriter;
 	private var partitionFilePath:String;
 	private var store:AcaciaHashMapLocalStore;
 
 	public def this(fileFullPath:String){
 		this.partitionFilePath = fileFullPath;

 		file = new File(fileFullPath);
 
 		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(e:java.io.IOException){

			}
			
		}
		
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
		} catch (e:java.io.IOException) {
			e.printStackTrace();
		}
 	}
	
	public def this(graphID:Int, partitionID:Int, fileFullPath:String){
		this.partitionFilePath = fileFullPath;
		store = new AcaciaHashMapLocalStore(fileFullPath);
	}
 
 	public def compress(){    
 		store.storeGraph();
  		r:Runtime = Runtime.getRuntime();
  		var p:Process;
  		try {
  			p = r.exec("zip -rj "+ partitionFilePath + ".zip " + partitionFilePath);
  			p.waitFor();

 			var b:BufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
 			var line:String = "";

 			while((line=b.readLine())!= null){
 				Console.OUT.println(line);
 			}
  		} catch (val e:java.io.IOException) {
  			e.printStackTrace();
  		} catch (e:java.lang.InterruptedException) {
  			e.printStackTrace();
  		}
 	}
 
 	public def writeEdge(firstVertex:Long, secondVertex:Long){
		// try {
		// 	bw.write(""+firstVertex+" "+secondVertex+"\r\n");
		// 	bw.flush();
		// } catch (e:java.io.IOException) {
		// 	e.printStackTrace();
		// }
	 	store.addEdge(firstVertex, secondVertex);
 	}

 	public def getOutputFilePath():String{
 		return partitionFilePath;
 	}

 	public def close(){
		// try {
		// 	bw.close();
		// 	fw.close();
		// } catch (e:java.io.IOException) {
		// 	e.printStackTrace();
		// }
 	}

}