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

import x10.util.ArrayList;

import x10.io.File;
import x10.regionarray.Array;

import org.acacia.util.Utils;
import org.acacia.log.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import x10.compiler.Native;

/**
 * Class HDFSInterface
 */
public class HDFSInterface {
	/**
	 * This method copies a file from loacl file system to HDFFS. If the file already exists on the HDFS it will be deleted first.
	 */
	public static def copyOntoHDFSfromLocal(val localFilePath:String, val pathOnHDFS:String):void{
		val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		
		if (HDFSInterface.fileExists(pathOnHDFS)){
			HDFSInterface.deleteFile(pathOnHDFS);
		}
		
		val taskCount:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.taskcount");
		val blockSize = getBlockSize(localFilePath, Int.parseInt(taskCount));
		
		Console.OUT.println("Hadoop Map/Reduce Task count is : " + taskCount);
		Console.OUT.println("HDFS Block size is : " + blockSize);
		
		Console.OUT.println("The command is : " + hadoopLoc + "/bin/hadoop fs -Ddfs.block.size="+ blockSize + " -put "+localFilePath+" " + pathOnHDFS);
		
		// for(line in Runtime.execForRead(hadoopLoc + "/bin/hadoop fs -Ddfs.block.size="+ blockSize + " -put "+localFilePath+" " + pathOnHDFS).lines()){
		// 	Console.OUT.println(line);
		// }
		
        call_runProcessBuilderAndPrintToConsole(hadoopLoc + "/bin/hadoop fs -Ddfs.block.size="+ blockSize + " -put "+localFilePath+" " + pathOnHDFS);

		Console.OUT.println("Done executing the command...");
	}
	
	/**
	 * This method resturns the block size for an input data file based on the available maximum number of map tasks.
	 */
	private static def getBlockSize(val path:String, val mapTaskCnt:Int):Long{
		
		
		var result:Long = 0;
		val bytesPerCheckSum:Long = 512; //We must use this value as well. Otherwise we get an error: io.bytes.per.checksum(512) and blockSize(1062990) do not match. blockSize should be a multiple of io.bytes.per.checksum
		val file:File = new File(path);
		result = Math.round(Math.floor(file.size()/ (mapTaskCnt * bytesPerCheckSum * 2))) as Long; //Number 2 here is used because we want to get twice the number of maximum available map tasks to be scheduled to run with Hadoop.
		                                                                                  //In future may be we can increase this to tune performance (E.g., 4,8,...).
		
		if (result == 0l){//This is because if the file size is very small, the result will be zero. But we need to return a default block size in that case.
			return 1024 * 32;
		}
		
		return result * bytesPerCheckSum;
		
		
		//Note: The above commented code seems good algorithm for calculating appropriate block size. However, during the experiment
		//it exceeded 32 minutes limit on internet.dl with 4KB block size. Therefore, it was decided to use 4KB block size.
		
		//return 4096;
		//return 1024 * 32;
	}
	
	public static def mergeOnHDFSandCopyOntoLocal(val pathOnHDFS:String, val localPath:String):void{
		val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		
		Console.OUT.println("Running : " + hadoopLoc + "/bin/hadoop fs -getmerge "+pathOnHDFS+" " + localPath);
		
		// for(line in Runtime.execForRead(hadoopLoc + "/bin/hadoop fs -getmerge "+pathOnHDFS+" " + localPath).lines()){
		// 	Console.OUT.println(line);
		// }

         call_runProcessBuilderAndPrintToConsole(hadoopLoc + "/bin/hadoop fs -getmerge "+pathOnHDFS+" " + localPath);
	}
	
	/**
	 * This method just gets a particular file locatedo n the HDFS to a local directory.
	 */
	public static def copyOntoLocal(val pathOnHDFS:String, val localPath:String):void{
		val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		
		Console.OUT.println("Running : " + hadoopLoc + "/bin/hadoop fs -get "+pathOnHDFS+" " + localPath);
		
		// for(line in Runtime.execForRead(hadoopLoc + "/bin/hadoop fs -get "+pathOnHDFS+" " + localPath).lines()){
		// 	Console.OUT.println(line);
		// }
		
        call_runProcessBuilderAndPrintToConsole(hadoopLoc + "/bin/hadoop fs -get "+pathOnHDFS+" " + localPath);

		Console.OUT.println("Done downloading the file.");
	}
	
	public static def copyOntoLocalFromHDFS(val pathOnHDFS:String, val localPath:String):void{
		// val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		// 
		// Console.OUT.println("Running : " + hadoopLoc + "/bin/hadoop fs -get "+pathOnHDFS+" " + localPath);
		// 
		// for(line in Runtime.execForRead(hadoopLoc + "/bin/hadoop fs -get "+pathOnHDFS+" " + localPath).lines()){
		// 	Console.OUT.println(line);
		// }
		// 
		// Console.OUT.println("Done downloading the file.");
		
		val conf:Configuration = new Configuration();
		//String hadoopHome = Utils_Java.getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		//conf.addResource(new Path(""));
		val hadoopHome:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		val resPath1:String = hadoopHome + "/conf/core-site.xml";
		val resPath2:String = hadoopHome + "/conf/hdfs-site.xml";
		val resPath3:String = hadoopHome + "/conf/mapred-site.xml";
		
		conf.addResource(new Path(resPath1));
		conf.addResource(new Path(resPath2));
		conf.addResource(new Path(resPath3));
		var fileSystem:FileSystem = null;
		
		try{
			fileSystem = FileSystem.get(conf);
			val pthSrc:Path = new Path(pathOnHDFS);
			
			val pthDst:Path = new Path(localPath);
			
			// if(!(fileSystem.exists(pthDst))){
			// 	Console.OUT.println("No such destination : " + localPath);
			// 	Console.OUT.println("Now creating the folder.");
			// 	
			// }
			
			val fileName:String = pathOnHDFS.substring(pathOnHDFS.lastIndexOf('/') + 1n, pathOnHDFS.length());
			fileSystem.copyFromLocalFile(pthSrc, pthDst);
			Console.OUT.println("File " + fileName + " copied to " + localPath);
		}catch(var e:IOException){
			Console.OUT.println("Exception2 : " + e.getMessage());
		}finally{
			try{
				fileSystem.close();
			}catch(ec:IOException){
				Console.OUT.println("Exception3 : " + ec.getMessage());
			}
		}
	}
	
	/**
	 * Note that this method might get affected if the hadoop fs -count result format changes.
	 */
	public static def getFileCountOnDir(val pathOnHDFS:String):Int{
		var result:Int = 0n;
		val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
				
		// for(line in Runtime.execForRead(hadoopLoc + "/bin/hadoop fs -count -q "+pathOnHDFS).lines()){
		// 	
		// 	val res:Rail[String] = line.split(" ");
		// 	var counter:Int = 0n;
		// 	for (item in res.range()){
		// 		val v:String = res(item).trim();
		// 		if(!v.equals("")){
		// 			counter++;
		// 			//Console.OUT.println("|" + res(item).trim() + "|");
		// 			if(counter == 6n){ //This number 6 comes from the reason that hadoop fs -count -q returns number of file count at the 6th place of the result.
		// 				result = Int.parse(v) - 3n; //Reduce 3 from the file count because three directories are also taken as files by this command.
		// 				
		// 				if(result == -2n){
		// 					result = 1n; //For one file hdfs gives the result correct;y as 1
		// 				}						
		// 			}
		// 		}
		// 	}
		// }

        var intermRes:Rail[String] = call_runProcessBuilderAndGetResults(hadoopLoc + "/bin/hadoop fs -count -q "+pathOnHDFS);
        for(line in intermRes){
        	val res:Rail[String] = line.split(" ");
        	var counter:Int = 0n;
        	for (item in res.range()){
        		val v:String = res(item).trim();
        		if(!v.equals("")){
        			counter++;
        			//Console.OUT.println("|" + res(item).trim() + "|");
        			if(counter == 6n){ //This number 6 comes from the reason that hadoop fs -count -q returns number of file count at the 6th place of the result.
        				result = Int.parse(v) - 3n; //Reduce 3 from the file count because three directories are also taken as files by this command.
        				
        				if(result == -2n){
        					result = 1n; //For one file hdfs gives the result correct;y as 1
        				}						
        			}
        		}
        	}
        }
		return result;
	}
	
	/**
	 * This method returns the list of files (not directories) avaibale on a particular directory on HDFS. Note that this method does not return any files having the
	 * the following texts on their file names : '_SUCCESS', '_logs'
	 */
	public static def getListofFileNamesOnDir(val pathOnHDFS:String):Rail[String]{
		var result:ArrayList[String] = new ArrayList[String](); 
		val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		
		// for(line in Runtime.execForRead(hadoopLoc + "/bin/hadoop fs -ls "+pathOnHDFS).lines()){
		// 	if((line.indexOf("_SUCCESS") == -1n)&&(line.indexOf("_logs") == -1n)){
		// 		val idx:Int = line.lastIndexOf("/");
		// 		
		// 		if(idx != -1n){
		// 			result.add(line.substring(idx + 1n));
		// 		}
		// 	}
		// }

        var intermRes:Rail[String] = call_runProcessBuilderAndGetResults(hadoopLoc + "/bin/hadoop fs -ls "+pathOnHDFS);
		
        for(line in intermRes){
        	if((line.indexOf("_SUCCESS") == -1n)&&(line.indexOf("_logs") == -1n)){
        		val idx:Int = line.lastIndexOf("/");
        		
        		if(idx != -1n){
        			result.add(line.substring(idx + 1n));
        		}
        	}
        }
        
		return result.toRail();
	}
	
	public static def moveFileTo(val sourcePath:String, val destPath:String){
		val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		
		//Console.OUT.println("The command is |" + hadoopLoc + "/bin/hadoop fs -mv "+sourcePath + " " + destPath + "|");
		
		// for(line in Runtime.execForRead(hadoopLoc + "/bin/hadoop fs -mv "+sourcePath + " " + destPath).lines()){
		// 	Logger.info(line);
		// }

        call_runProcessBuilderAndPrintToConsole(hadoopLoc + "/bin/hadoop fs -mv "+sourcePath + " " + destPath);
	}
	
	
	
	/**
	 * Creates a directory on HDFS
	 */
	public static def mkdir(val path:String){
		val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
				
		// for(line in Runtime.execForRead(hadoopLoc + "/bin/hadoop fs -mkdir "+path).lines()){
		// 	Logger.info(line);
		// }

        call_runProcessBuilderAndPrintToConsole(hadoopLoc + "/bin/hadoop fs -mkdir "+path);
	}
	
	/**
	 * This method checks whether a file exists on HDFS in a given path.
	 */
	public static def fileExists(val filePath:String):Boolean{
		var result:Boolean = false;
		val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
				
	// 	for(line in Runtime.execForRead(hadoopLoc + "/bin/hadoop fs -ls "+filePath).lines()){
	// 		//Logger.info(line);
	// 		//Console.OUT.println(line);
	// 
	// 		//If the file path is substring of the received result probably the file should exist.
	// 		if (line.indexOf(filePath) != -1n){
	// 			result = true;
	// 		}
	// 	}
		
        var intermRes:Rail[String] = call_runProcessBuilderAndGetResults(hadoopLoc + "/bin/hadoop fs -ls "+filePath);

        for(line in intermRes){
            // Console.OUT.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            // Console.OUT.println("Command is : " + hadoopLoc + "/bin/hadoop fs -ls "+filePath);
            // Console.OUT.println(line);
            // Console.OUT.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    		//If the file path is substring of the received result probably the file should exist.
            //Also we need to makesure the text "Cannot access" not present in the returned result.
    		if ((line.indexOf(filePath) != -1n)&&(line.indexOf("Cannot access") == -1n)){
    			result = true;
    		}
        }
        
        
		return result;
	}
	
	public static def deleteFile(val filePath:String):Boolean{
		var result:Boolean = true;
		val hadoopLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		
		// for(line in Runtime.execForRead(hadoopLoc + "/bin/hadoop fs -rmr " + filePath).lines()){
		// 	Logger.info(line);
		// }

        val res:Rail[String] = call_runProcessBuilderAndGetResults(hadoopLoc + "/bin/hadoop fs -rmr " + filePath);
		
        for (line in res){
             Logger.info(line);
        }
        
		return result;
	}


	@Native("java", "org.acacia.util.java.Utils_Java.runProcessBuilderAndPrintToConsole(#1)")
	static native def call_runProcessBuilderAndPrintToConsole(String):void;

	@Native("java", "org.acacia.util.java.Utils_Java.runProcessBuilderAndGetResults(#1)")
	static native def call_runProcessBuilderAndGetResults(String):Rail[String];
}