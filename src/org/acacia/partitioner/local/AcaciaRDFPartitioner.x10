package org.acacia.partitioner.local;
import x10.io.File;
import x10.util.HashMap;

// import com.hp.hpl.jena.rdf.model.Model;
// import com.hp.hpl.jena.rdf.model.ModelFactory;
// import com.hp.hpl.jena.util.FileManager;;

/**
 * Class AcaciaRDFPartitioner
 */
public class AcaciaRDFPartitioner {
    /**
     * Default constructor 
     */
    public def this() {
        // TODO auto-generated stub
    }
    
    public def readFile():void{
    	try{
    		var map:HashMap[Long,String] = new HashMap[Long,String]();
    
        	val outputFile:String	= "/home/isuru/output.dl";
        	val inputFile:String	= "/home/isuru/Dropbox/fyp2015_Acacia-RDF/RDF data set/mydata.rdf";
    		val I			= new File(inputFile);
    		val O  			= new File(outputFile);
    		val P			= O.printer();
        	var key:String 		= "http://www.mrt.ac.lk/student#";
        	var subjectOpened:Boolean = false;
        	var fromVertex:Long	= 0;
        	var toVertex:Long	= 0;
        
    		for (line in I.lines()) {
    			if(!subjectOpened){
    				val check = "<rdf:Description rdf:about=\""+key;
    				val i = line.indexOf(check);
    				if(i>0){
    					subjectOpened	= true;
    					val len1 	= line.length();
    					val len2 	= check.length();
    					val subStr 	= line.substring(len2+1n, len1-2n);
    					fromVertex 	= Long.parse(subStr);
    				}
    			}
    			else{
    				var check:String = "</rdf:Description>";
    				var i:Int = line.indexOf(check);
    				if(i>0){
    					subjectOpened = false;
    				}else{
    					i = line.indexOf(key);
    					if(i>0){
    						val len1	= line.length();
    						val len2 	= i + key.length();
    						val subStr 	= line.substring(len2, len1-3n);
    						toVertex 	= Long.parse(subStr);
    						
    						P.println(fromVertex+" "+toVertex);
    					}else{
    						check = "<attr:";
    						i = line.indexOf(check);
    						if(i>0){
    							val len1 		= line.indexOf(":");
    							val len2 		= line.indexOf(">");
    							var attr:String = line.substring(len1+1n,len2);
    							var temp:String = line.substring(len2+1n,line.length());
    							val len3 		= temp.indexOf("</attr");
    							val value 		= temp.substring(0n,len3);
    
    							map.put(fromVertex,attr);
    							map.put(fromVertex,attr);
    							//P.println(attr+" "+value);
    						}
    					}
    				}
    			}
    			
    			
    		}
    		var itr:Iterator[x10.util.Map.Entry[Long,String]] = map.entries().iterator();
    		while(itr.hasNext()){
    			val attributeItem:x10.util.Map.Entry[Long,String] = itr.next();
    			P.println(attributeItem.getKey()+" "+attributeItem.getValue());
    		}
    		P.flush();
    	}catch(e:Exception){
    		Console.OUT.println("Error : "+e.getMessage());
    	}
    }
    
    public def readRDF():void{
//         val inputFileName:String	= "/home/isuru/Dropbox/fyp2015_Acacia-RDF/RDF data set/1248.rdf";
//         // create an empty model
//     	var model:Model = ModelFactory.createDefaultModel();
// 
//     	// use the FileManager to find the input file
//     	var inp:InputStream = FileManager.get().open( inputFileName );
//     	if (inp == null) {
//     		throw new IllegalArgumentException("File: " + inputFileName + " not found");
//     	}
// 
//     	// read the RDF/XML file
//     	model.read(inp, null);
// 
//     	// write it to standard out
//     	model.write(Console.OUT);
    }
}