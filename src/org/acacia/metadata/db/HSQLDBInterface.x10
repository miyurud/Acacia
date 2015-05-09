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