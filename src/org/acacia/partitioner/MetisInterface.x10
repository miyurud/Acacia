package org.acacia.partitioner;

import org.acacia.util.Utils;
import x10.io.File;
import x10.io.FileWriter;
import x10.compiler.Native;

/**
 * Class MetisInterface is the interface for graph partitioning library named Metis.
 */
public class MetisInterface {
    /**
     * Default constructor 
     */
    public def this() {
        
    }
    
    /**
     * This method partitions the graph using METIS.
     */
    public static def partitionWithMetis(val filePath:String, val nParts:Long){
    	val metisLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.metis.home");
    	
    	Console.OUT.println("Partitioning the graph with metis.");
    	Console.OUT.println(metisLoc + "/bin/gpmetis "+filePath+" " + nParts);
    	
    	// for(line in Runtime.execForRead(metisLoc + "/bin/gpmetis "+filePath+" " + nParts).lines()){
    	// 	Console.OUT.println(line);
    	// }
    	
        val cmd:String = metisLoc + "/bin/gpmetis "+filePath+" " + nParts;
        val itr:Iterator[String] = (x10.xrx.Runtime.execForRead(cmd) as x10.io.Reader).lines().iterator();
        while(itr.hasNext()){
            Console.OUT.println(itr.next());
        }
    	Console.OUT.println("Done partitioning the graph");
    }
    
    /**
     * This method partitions the graph using PARMETIS.
     */
    public static def partitionWithParMetis(val filePath:String, val nParts:Long){
	    val metisLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.parmetis.home");
	    val mpiLoc:String = Utils.call_getAcaciaProperty("org.acacia.partitioner.mpi.home");
	    
	    //First we need to distribute the graph data file among each host
	    val hostList:Rail[String] = Utils.getPrivateHostList();
	    Console.OUT.println("Copying the graph file to all the remote hosts.");
	    finish for(item in hostList){
	        async{
		        val cmd:String = "scp /tmp/dgr/grf miyuru@" + item + ":/tmp/dgr/grf";
		        Console.OUT.println(cmd);
		    
		        // for(val line:String in Runtime.execForRead(cmd).lines()){
		        //    Console.OUT.println(line);
		        // }
		        
		        val itr:Iterator[String] = (x10.xrx.Runtime.execForRead(cmd) as x10.io.Reader).lines().iterator();
		        while(itr.hasNext()){
		            Console.OUT.println(itr.next());
		        }
	        }
	    }
	    
	    Console.OUT.println("Done copying the file.");

	    Console.OUT.println("Partitioning the graph with parmetis.");
	    //Here the number of pices the graph is partitioned equals to the number of X10 places used by the Acacia system.
	    //Here the leading 2 before nParts means we are using the algorithm 2 of parmetis to partition the graph. The trailing four zeros are just there for the sake.
	    val cmd:String = mpiLoc + "/mpiexec -f machines.txt -np " + nParts + " " + metisLoc + "/parmetis "+filePath+" 2 " + nParts + " 0 0 0 0";// + " > /tmp/dgr/grf.part." + Place.places().size + "";
	    // val command:String = mpiLoc + "/mpiexec";
	    // val params:String = "-f machines.txt -np " + nParts + " " + metisLoc + "/parmetis "+filePath+" 2 " + nParts + " 0 0 0 0";// + " > /tmp/dgr/grf.part." + Place.places().size + ""; 
	    Console.OUT.println(cmd);
	    
	    // Console.OUT.println("AA1");
	    //var outFile:File = new File("/tmp/dgr/grf.part." + nParts);
	    // Console.OUT.println("BB1");
	    // var writer:FileWriter = new FileWriter(outFile);
	    // Console.OUT.println("CC1");
	    // if(writer == null){
	    //         Console.OUT.println("Writer is null");
	    // }
	    
	    call_runProcessBuilderAndSaveToFile(cmd, "/tmp/dgr/grf.part." + nParts);
	    
	    
	    // val chrSeq:Rail[Char] = cmd.chars();
	    // var path:java.util.StringBuilder = new java.util.StringBuilder();
	    // for(ch in chrSeq){
	    // path.add(ch as java.lang.Char);
	    // }
	    // 
	    // var ps:java.lang.ProcessBuilder = new java.lang.ProcessBuilder(path.toString()); 
	    
	    // @Native("java", "c=cmd"){
	    //    System.out.println("CMD is : " + c);
	    // }
	    
	    /*
	    Console.OUT.println("CC2");
	    val itr:x10.io.ReaderIterator[String] = Runtime.execForRead(cmd).lines();
	    Console.OUT.println("CC3");
	    if(itr==null){
	    Console.OUT.println("Iterator is null");
	    }
	   
	    var cntr:Long = 0;
	    for (line in itr){
	    
	        if(line == null){
	        Console.OUT.println("Line is null");
	        }
	        
	        if(line == null){
	          Console.OUT.println("Out is null");
	        }
	        cntr++;
	        if(cntr > 50000){
	        writer.flush();
	        cntr = 0;
	        }
	        writer.write((line + "\n").bytes());
	     
	    }
	     */
	    /*
	    Console.OUT.println("DD1");
	    
	    writer.flush();
	    writer.close();
	    */
	    Console.OUT.println("Done partitioning the graph");
    }
        
    @Native("java", "org.acacia.util.java.Utils_Java.runProcessBuilderAndSaveToFile(#1, #2)")
    static native def call_runProcessBuilderAndSaveToFile(String, String):void;
}