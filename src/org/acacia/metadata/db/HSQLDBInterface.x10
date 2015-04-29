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

package org.acacia.metadata.db;

import org.acacia.util.Utils;
import x10.compiler.Native;

/**
 * Class HSQLDBInterface
 */
public class HSQLDBInterface {
	public static def startHsqlDB(){
		val javaHome:String = Utils.call_getAcaciaProperty("org.acacia.javahome");
		val workDir:String = Utils.call_getAcaciaProperty("org.acacia.server.runtime.location");
		val dbFileLoc:String = workDir + "/metadb";
        
        //We have to makesure that the metaDB folder exists. Otherwise, have to create it. 
        val file:java.io.File = new java.io.File(dbFileLoc);
        
        Console.OUT.println("AAAAAA");
        if(!file.isDirectory()){
            Console.OUT.println("MetaDB folder (" + dbFileLoc + ") was created.");
            file.mkdir();
        }else{
            Console.OUT.println("BBBBBBB");
        }

        call_runProcessBuilderAndPrintToConsole("ssh localhost" + javaHome + "/bin/java -cp lib/hsqldb-2.2.9.jar org.hsqldb.Server -database.0 file:" + dbFileLoc + " -dbname.0 acacia_meta &");        
	}

	@Native("java", "org.acacia.util.java.Utils_Java.runProcessBuilderAndPrintToConsole(#1)")
	static native def call_runProcessBuilderAndPrintToConsole(String):void;
}