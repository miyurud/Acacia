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

package org.acacia.partitioner.local;

import org.acacia.localstore.AcaciaHashMapLocalStore;
import org.acacia.util.Utils;

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
                 e.printStackTrace();
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
		store = new AcaciaHashMapLocalStore(graphID, partitionID);
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