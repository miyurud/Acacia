package org.acacia.partitioner.hadoop;

import x10.compiler.Native;

/**
 * Class HDFSFile acts as an object that represents files on the HDFS. A HDFSFile object corresponds to a file on HDFS.
 */
public class HDFSFile {
    /**
     * Default constructor 
     */
    public def this() {
    	
    }
    
    public def open(val path:String){
    	call_open(path);
    }
    
    public def close():void{
    	call_close();
    }
    
    public def readLine():String{
    	return call_readLine();
    }
    
    
    @Native("java", "org.acacia.partitioner.hadoop.java.HDFSFile_Java.open(#1)")
    static native def call_open(String):void;
    
    @Native("java", "org.acacia.partitioner.hadoop.java.HDFSFile_Java.close()")
    static native def call_close():void;
    
    @Native("java", "org.acacia.partitioner.hadoop.java.HDFSFile_Java.readLine()")
    static native def call_readLine():String;
}