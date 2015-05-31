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

package org.acacia.partitioner.hadoop;

import x10.io.FileWriter;
import x10.io.File;
import x10.io.FileReader;
import x10.io.IOException;
import x10.util.StringBuilder;
import x10.regionarray.Array;

import org.acacia.util.Utils;

public class HadoopOrchestrator {
    /**
     * Default constructor
     */
    public def this() {
    	
    }
    
    /**
     * This method automatically setsup the hadoop cluster on the intendend machines using the configuration information
     * provided by the user
     * 
     * Note on 9th Feb 2013 : COnstruction of this method was postponed for getig ready for IEEE Cloud 2013.
     * 
     */
    public static def setupHadoop(){
    	val hadoopHome:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
    	
    	//First we setup the masters
    	
    	var hadoopHosts:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.masters");
    	var hadoopHostsArray:Rail[String] = hadoopHosts.split(",");
    	
    	var outFile:File = new File(hadoopHome + "/conf/masters");
    	var writer:FileWriter = new FileWriter(outFile);
    	
    	for (item in hadoopHostsArray.range()){
    		writer.write((hadoopHostsArray(item) + "\n").bytes());
    	}
    	
    	writer.close();
    	
    	//Second we setup the slaves
    	
    	hadoopHosts = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.slaves");
    	hadoopHostsArray = hadoopHosts.split(",");
    	
    	outFile = new File(hadoopHome + "/conf/slaves");
    	writer = new FileWriter(outFile);
    	
    	for (item in hadoopHostsArray.range()){
    		writer.write((hadoopHostsArray(item) + "\n").bytes());
    	}
    	
    	writer.close();
    	
    	//Next we setup the hadoop-env.conf
    	
    	//conf/hadoop-env.sh
    	
    	val data:String = readAlltext(hadoopHome + "/conf/hadoop-env.sh");
    	
    	val dataArr:Rail[String] = data.split("\n");
    	
    	val ind:Int = data.indexOf("JAVA_HOME");
    	
    	
    	//outFile = new File(hadoopHome + "conf/hadoop-env.sh");
    	//writer = new FileWriter(outFile);
    	
    	Console.OUT.println("ind : " + ind);
    	
    }
    
    private static def readAlltext(val fileName:String):String{
    	var builder:StringBuilder = new StringBuilder();
    	var reader:FileReader = new FileReader(new File(fileName)); 
    	var line:String = null;
    	
    	while (true){
    		try{
    			line = reader.readLine();
    			builder.add(line);
    		}catch(IOException){
    			//We assume that at this point we completed reading all the file contents.
    			break;
    		}
    	}
    	
    	return builder.toString();
    }
    
    public static def isHadoopRunning():Boolean{
    	var result:Boolean = false;
    	
    	return result;
    }
    
    public static def isJobDone():Boolean{
    	val fileTmp:File = new File("/tmp/jobdone");
    	
    	return fileTmp.exists();
    }
    
    public static def startHadoop():void{
    	Console.OUT.println("Starting Hadoop");
    	val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
    	val namenode:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.namenode");
    	var flag:Boolean = false;
    	
    	for(line in x10.xrx.Runtime.execForRead("ssh " + namenode + " " + hadoopLoc + "/bin/start-all.sh").lines()){
    		Console.OUT.println(line);
    		if(line.indexOf("Stop it first.") != -1n){
    			flag = true;
    		}
    	}
    	if(flag){
    		Console.OUT.println("Hadoop is already running.");    		
    	}else{
    		Console.OUT.println("Done starting Hadoop");
    	}
    }
    
    public static def submitJob(val args:String):void{
    	val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
    	Console.OUT.println("Submitting the job : " + args);    	
    	
    	for(line in x10.xrx.Runtime.execForRead(hadoopLoc + "/bin/hadoop " + args).lines()){
    		Console.OUT.println(line);
    	}
    	
    	Console.OUT.println("Submitted job to Hadoop");
    	// 
    	// while(!HadoopOrchestrator.isJobDone()){
    	// 	System.sleep(5000);
    	// }
    	// 
    	// Console.OUT.println("Done executing job on Hadoop");
    }
    
    public static def stopHadoop():void{
    	Console.OUT.println("Stopping Hadoop");
    	val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
    	val namenode:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.namenode");
    	
    	for(line in x10.xrx.Runtime.execForRead("ssh " + namenode + " " + hadoopLoc + "/bin/stop-all.sh").lines()){
    		Console.OUT.println(line);
    	}
    	Console.OUT.println("Done stopping Hadoop");
    }
    
    public static def reStartHadoop():void{
    	stopHadoop();
    	startHadoop();
    }
    
}