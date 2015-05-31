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