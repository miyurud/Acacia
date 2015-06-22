package org.acacia.frontend;

import x10.compiler.Native;
import x10.io.File;

import java.net.Socket;
import java.net.ServerSocket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;

import org.acacia.log.java.Logger_Java;
import org.acacia.metadata.db.java.MetaDataDBInterface;

import org.acacia.util.PlaceToNodeMapper;
import org.acacia.util.java.Utils_Java;
import org.acacia.util.Utils;

import org.acacia.server.AcaciaServer;
import x10.util.StringBuilder;
import x10.regionarray.Array;
import x10.util.HashMap;
import x10.util.ArrayList;

//import org.acacia.partitioner.hbase.java.HBaseInterface_Java;

/**
 * Class AcaciaFrontEndServiceSession
 */
public class AcaciaFrontEndServiceSession {
	private var sessionSkt:Socket = null;
    private var gremlinInterpreter:AcaciaGremlinInterpreter = null;
    private val IS_DISTRIBUTED = Boolean.parse(Utils.call_getAcaciaProperty("org.acacia.server.mode.isdistributed"));
    
	public def this(val socket:Socket){
		sessionSkt = socket;
	}
	
	public def run():void{
		try{
			var buff:BufferedReader = new BufferedReader(new InputStreamReader(sessionSkt.getInputStream()));
			var out:PrintWriter = new PrintWriter(sessionSkt.getOutputStream());
	
			var msg:String = null;
	
			while((msg = buff.readLine())!= null){
				if(msg.equals(AcaciaFrontEndProtocol.EXIT)){
					out.println(AcaciaFrontEndProtocol.EXIT_ACK);
					out.flush();
					sessionSkt.close();
					break;
				}else if(msg.equals(AcaciaFrontEndProtocol.SHTDN)){//Shutdown Acacia. This should be implemented as Java event communication.
                    //At the moment there is no shutdown hapenning here. But we want to at least shutdown the DB connection to meta database. 
                    val result:Boolean = MetaDataDBInterface.runUpdate("SHUTDOWN;");

                    out.println(AcaciaFrontEndProtocol.EXIT_ACK);
                    out.flush();
                    try{
                        sessionSkt.close();
                        break;
                     }catch(val e:IOException){
                        Logger_Java.error("Error : " + e.getMessage());
                     }
                 }else{
					process(msg, buff, out);
				}
			}			
		}catch(val e:IOException){
			Logger_Java.error("Error : " + e.getMessage());
		}
	}
	
	/**
	 * This method processes the query requests to AcaciaForntEnd. This is the main function that answers the queries.
	 */
	public def process(val msg:String, val buff:BufferedReader, out:PrintWriter):void{
		var response:String="";
		var str:String = null;
        var query:String=null;
		
		if(msg.equals(AcaciaFrontEndProtocol.LIST)){//List the graphs on Acacia
			val resultArr:Rail[String] = call_runSelect("SELECT IDGRAPH,NAME,UPLOAD_PATH,GRAPH_STATUS_IDGRAPH_STATUS FROM ACACIA_META.GRAPH");
			response = "|";
			for (item in resultArr.range()){
				response += resultArr(item) + "|";
			}

            if(response.equals("|")){
                response += "|";
            }
			response += "\r\n";
			out.println(response);
			out.flush();
		}else if(msg.equals(AcaciaFrontEndProtocol.VCOUNT)){ //Get vertex count
			out.println(AcaciaFrontEndProtocol.GRAPHID_SEND);
			out.flush();
			
			try{
				str = buff.readLine();
			}catch(val e:IOException){
				Logger_Java.error("Error : " + e.getMessage());
			}
			Logger_Java.info("graph|" + str + "|");
			
			if(!graphExistsByID(str)){
				out.println(AcaciaFrontEndProtocol.ERROR + ":The specified graph id does not exist");
				out.flush();				
			}else{			
				val vcnt:Long = countVertices(str);
				Logger_Java.info("vertcount|" + vcnt + "|");
				out.println(vcnt);
				out.flush();
			}
		}else if(msg.equals(AcaciaFrontEndProtocol.ADGR)){ //Add graph from outside
			//we get the name and the path to graph as a pair separated by |.
			out.println(AcaciaFrontEndProtocol.SEND);
			out.flush();
			var name:String = "";
			var path:String = "";
			try{
				str = buff.readLine();
			}catch(val e:IOException){
				Logger_Java.error("Error : " + e.getMessage());
			}
			val strArr:Rail[String] = str.split("|");
			
			if(strArr.size != 2){
				out.println(AcaciaFrontEndProtocol.ERROR + ":Message format not recognized");
				out.flush();
				return;
			}
			
			name=strArr(0);
			path=strArr(1);
			
			if(graphExists(path)){
				out.println(AcaciaFrontEndProtocol.ERROR + ":Graph exists");
				out.flush();				
			}else{
				var file:File = new File(path);
				if(file.exists()){
                    if(IS_DISTRIBUTED){
					    AcaciaServer.uploadGraphDistributed(name, path);
                    }else{
                        Console.OUT.println("Uploading the graph locally.");
                        AcaciaServer.uploadGraphLocally(name, path);
                    }
				}else{
					out.println(AcaciaFrontEndProtocol.ERROR + ":Graph data file does not exist on the specified path");
					out.flush();	
				}
			}
		}else if(msg.equals(AcaciaFrontEndProtocol.RMGR)){//Remove graph from Acacia
			out.println(AcaciaFrontEndProtocol.SEND);
			out.flush();
			
			var graphID:String = "";

			try{
				graphID = buff.readLine();
			}catch(val e:IOException){
				Logger_Java.error("Error : " + e.getMessage());
			}
			
			if(!graphExistsByID(graphID)){
				out.println(AcaciaFrontEndProtocol.ERROR + ":The specified graph id does not exist");
				out.flush();
			}else{
				str=""+removeGraph(graphID);
				out.println(str);
				out.flush();
			}			
			
		}else if(msg.equals(AcaciaFrontEndProtocol.ECOUNT)){
			out.println(AcaciaFrontEndProtocol.GRAPHID_SEND);
			out.flush();
			
			try{
				str = buff.readLine();
			}catch(val e:IOException){
				Logger_Java.error("Error : " + e.getMessage());
			}
			Logger_Java.info("graph|" + str + "|");
			
			if(!graphExistsByID(str)){
				out.println(AcaciaFrontEndProtocol.ERROR + ":The specified graph id does not exist");
				out.flush();				
			}else{
				val ecnt:Long = countEdges(str);
				Logger_Java.info("edge count|" + str + "|");
				out.println(ecnt);
				out.flush();
			}
		}else if(msg.equals(AcaciaFrontEndProtocol.RUOK)){
			out.println(AcaciaFrontEndProtocol.IMOK);
			out.flush();
		}else if(msg.equals(AcaciaFrontEndProtocol.TRUNCATE)){//Truncate the whole Acacia cluster
			truncateAcacia();
			out.println(AcaciaFrontEndProtocol.DONE);
			out.flush();
		}else if(msg.equals(AcaciaFrontEndProtocol.DEBUG)){
			//debugAcacia();
			out.println(AcaciaFrontEndProtocol.DONE);
			out.flush();
		}else if(msg.equals(AcaciaFrontEndProtocol.GREM)){
            out.println(AcaciaFrontEndProtocol.GREM_ACK);
            out.println(AcaciaFrontEndProtocol.SEND);
            out.flush();
            
            //Next we get the Gremlin commands in an interactive session
            gremlinInterpreter = new AcaciaGremlinInterpreter(buff, out);
            gremlinInterpreter.run();
            out.println("Exitted here...");
            out.flush();
            
		}else if(msg.equals(AcaciaFrontEndProtocol.PAGERANK)){
            out.println(AcaciaFrontEndProtocol.GRAPHID_SEND);
            out.flush();
            //First we get the ID of the graph to calculate PageRank.
            try{
            	str = buff.readLine();
            }catch(val e:IOException){
            	Logger_Java.error("Error : " + e.getMessage());
            }
            Logger_Java.info("graph|" + str + "|");
            
            if(!graphExistsByID(str)){
            	out.println(AcaciaFrontEndProtocol.ERROR + ":The specified graph id does not exist");
            	out.flush();				
            }else{
                //(int EntireGraphSize, int localGraphSize)
            	str = getPageRank(str);
            }
            out.println("result is |" + str + "|");
            out.flush();
        }else if(msg.equals(AcaciaFrontEndProtocol.TOP_K_PAGERANK)){
	        out.println(AcaciaFrontEndProtocol.GRAPHID_SEND);
	        out.flush();
	        //First we get the ID of the graph to calculate PageRank.
	        try{
	        	str = buff.readLine();
	        
		        if(!graphExistsByID(str)){
			        out.println(AcaciaFrontEndProtocol.ERROR + ":The specified graph id does not exist");
			        out.flush();				
		        }else{
			        //(int EntireGraphSize, int localGraphSize)
		        
		            //Next we need to ask the K value from user
			        out.println(AcaciaFrontEndProtocol.TOP_K_SEND);
			        out.flush();
			        val str2:String = buff.readLine();
			        Console.OUT.println("TK Pagerank start time: " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
			        str = getTopKPageRank(str, Int.parse(str2));
			        Console.OUT.println("TK Pagerank end time: " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
		        }        
	        }catch(val e:IOException){
	        	Logger_Java.error("Error : " + e.getMessage());
	        }
	        Logger_Java.info("graph|" + str + "|");
	        out.println("result is |" + str + "|");
	        out.flush();
        }else if(msg.equals(AcaciaFrontEndProtocol.OUT_DEGREE)){
        	out.println(AcaciaFrontEndProtocol.GRAPHID_SEND);
        	out.flush();

        	//First we get the ID of the graph to calculate the out degree distribution.
        	try{
        		str = buff.readLine();
        	}catch(val e:IOException){
        		Logger_Java.error("Error : " + e.getMessage());
        	}
            Logger_Java.info("graph|" + str + "|");
        
        	if(!graphExistsByID(str)){
        		out.println(AcaciaFrontEndProtocol.ERROR + ":The specified graph id does not exist");
        		out.flush();				
        	}else{
                Console.OUT.println("Out Degree distribution is ready....");
                Console.OUT.println(getOutDegreeDistribution(str));
        	}
          }else if(msg.equals(AcaciaFrontEndProtocol.FREE_DATA_DIR_SPACE)){
            out.println(getFreeSpaceInfo());
            out.flush();
          }else if(msg.equals(AcaciaFrontEndProtocol.AVERAGE_OUT_DEGREE)){
        	out.println(AcaciaFrontEndProtocol.GRAPHID_SEND);
        	out.flush();
        
            //First we get the ID of the graph to calculate the out degree distribution.
            try{
                str = buff.readLine();
            }catch(val e:IOException){
                Logger_Java.error("Error : " + e.getMessage());
            }
        
            if(!graphExistsByID(str)){
                out.println(AcaciaFrontEndProtocol.ERROR + ":The specified graph id does not exist");
                out.flush();				
            }else{
            	org.acacia.log.java.Logger_Java.info("Average out degree start time: " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
                val outDegDist:String = getOutDegreeDistribution(str);
                var outDegreeEntireGraph:HashMap[int, int] = new x10.util.HashMap[int, int]();
                var res1:Rail[String] = outDegDist.split(";");
                var intrmRes:Rail[String] = null;
                
                
                for (item in res1.range()){
                    intrmRes = res1(item).split(":");
                    outDegreeEntireGraph.put(Int.parse(intrmRes(0)), Int.parse(intrmRes(1)));
                }
                
                //Next we need to sum-up all the degrees to get the average.
                var itr:Iterator[x10.util.Map.Entry[Int, Int]] = outDegreeEntireGraph.entries().iterator();
                var totalDeg:Long = 0;
                var vCount:Long = 0;
                
                while(itr.hasNext()){
                    val item:x10.util.Map.Entry[Int, Int] = itr.next();
                    totalDeg += item.getValue();
                    vCount++;
                }
                
                val aodeg = (totalDeg as Double/vCount);
                org.acacia.log.java.Logger_Java.info("Average out degree end time: " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
                                
                out.println(aodeg);//Write the result to the client.
                out.flush();
            }
            org.acacia.log.java.Logger_Java.info("********************* Done average out degree *****");
        }else if(msg.equals(AcaciaFrontEndProtocol.TRIANGLES)){
	        out.println(AcaciaFrontEndProtocol.GRAPHID_SEND);
	        out.flush();
	        
	        try{
	        	str = buff.readLine();
	        }catch(val e:IOException){
	        	Logger_Java.error("Error : " + e.getMessage());
	        }
	        
	        if(!graphExistsByID(str)){
		        out.println(AcaciaFrontEndProtocol.ERROR + ":The specified graph id does not exist");
		        out.flush();				
	        }else{
		        Console.OUT.println("Triangle counting start time: " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
		        val nTraingles:Long = countTraingles(str);	
		        Console.OUT.println("nTraingles : " + nTraingles);
		        Console.OUT.println("Triangle counting end time: " + org.acacia.util.java.Utils_Java.getCurrentTimeStamp());
		        		        
		        out.println(nTraingles);//Write the result to the client.
		        out.flush();
	        }
        }
else if(msg.equals(AcaciaFrontEndProtocol.SPARQL)){   	//execute sparql queries
        	out.println(AcaciaFrontEndProtocol.S_QUERY_SEND);
        	out.flush();
        	try{
        		query = buff.readLine();
        	}catch(val e:IOException){
        		Logger_Java.error("Error : " + e.getMessage());
        	}
        	
        	out.println(AcaciaFrontEndProtocol.GRAPHID_SEND);
        	out.flush();
        	
        	try{
        		str = buff.readLine();
        	}catch(val e:IOException){
        		Logger_Java.error("Error : " + e.getMessage());
        	}
        	
        	if(!graphExistsByID(str)){
        		out.println(AcaciaFrontEndProtocol.ERROR + ":The specified graph id does not exist");
        		out.flush();				
        	}else{
        		
        		val result:String =null;        
        		//should parse query to the intepreter and get the results
        		out.println(result);//print the result
        		out.flush();
        	}
        	
        }

else{
			//This is the default response
			out.println(AcaciaFrontEndProtocol.SEND);
			out.flush();
		}
	}

	private def countTraingles(val graphID:String): Long {
	    var result:Long = 0;	    
	    //The following line should ideally receive the list ofrunning hosts.
	    val hosts:Rail[String] = org.acacia.util.Utils.getPrivateHostList();//["sc01.sc.cs.titech.ac.jp", "sc02.sc.cs.titech.ac.jp", "sc03.sc.cs.titech.ac.jp", "sc04.sc.cs.titech.ac.jp"];
	    val hostListLen:Int = hosts.size as Int;//Number of hosts cannot be a long value
	    var intermRes:Rail[Long] = new Rail[Long](hostListLen);
	    
	    // finish for(var i:Int=0n; i < hostListLen; i++){
		   //  val k:Int = i;
		   //  async{
		   //  	//intermRes(k) = call_countTraingles(hosts(k), graphID);
		   //  
		   //  intermRes(k) = call_countTraingles(PlaceToNodeMapper.getHost(placeID), graphID);
		   //  }
	    // }
	    
	    //Here we need to create a map of partitions and their corresponding host names. Then for each place,
	    //we makesure that it gets a partition from the host where it is running.
	    //Here we need to makesure that we have enough number of places per host so that all the partitions will be
	    //loaded by Acacia. Otherwise there will be several records left in the HashTable without getting assigned to
	    //a particular place. Indeed this will result in inaccurate running of the algorithm.
	    
	    //var l:Rail[String] = call_runSelect("SELECT HOST_IDHOST, PARTITION_IDPARTITION FROM ACACIA_META.HOST_HAS_PARTITION WHERE PARTITION_GRAPH_IDGRAPH=" + graphID + ";");
	    //SELECT NAME,PARTITION_IDPARTITION FROM "ACACIA_META"."HOST_HAS_PARTITION" INNER JOIN "ACACIA_META"."HOST" ON HOST_IDHOST=IDHOST WHERE PARTITION_GRAPH_IDGRAPH=191;
	    Console.OUT.println("PPPPPPPPPPPPPPPPPPPPPPP");
	    var l:Rail[String] = call_runSelect("SELECT NAME,PARTITION_IDPARTITION FROM ACACIA_META.HOST_HAS_PARTITION INNER JOIN ACACIA_META.HOST ON HOST_IDHOST=IDHOST WHERE PARTITION_GRAPH_IDGRAPH=" + graphID + ";");
	    Console.OUT.println("QQQQQQQQQQQQQQQQQQQQQQQ size : " + l.size);
	    var mp:HashMap[String, ArrayList[String]] = new HashMap[String, ArrayList[String]]();
	    
	    for(var i:long=0; i<l.size; i++){
	      //Console.OUT.println("pl(i) : " + l(i));
	      val items:Rail[String] = l(i).split(",");
	      //First we have to see whether we already have the host stored inside the HashMap.
	      //var partitions:ArrayList[String] = mp.get(items(0)).value as ArrayList[String];
	      
	      // Console.OUT.println("items(0) : " + items(0));
	      // Console.OUT.println("items(1) : " + items(1));
	      val pts = mp.get(items(0));
	      var partitions:ArrayList[String] = null;
	      
	      //This means there was no ArrayList object which holds the graph partitions for the host id stored in
	      //items(0)
	      if(pts == null){
	        // Console.OUT.println("Partition count is NULL");
	        partitions = new ArrayList[String]();
	      }else{
	        partitions = pts as ArrayList[String];
	      }
	      
	      partitions.add(items(1));
	      mp.put(items(0), partitions);
	      // Console.OUT.println("put item " + items(0) + " : " + items(1));
	    }
	    
	    var cntr:Int = 0n;
	    finish for (val p in Place.places()){
	        val k:Int = cntr;
	        val host = PlaceToNodeMapper.getHost(p.id);
	        val port = PlaceToNodeMapper.getInstancePort(p.id);
	        var partitionID:String = null;
	        
	        var partitions:ArrayList[String] = mp.get(host) as ArrayList[String];
	        if(partitions.size() > 0){
	            partitionID = partitions.removeFirst();
	        }
	        
	        // Console.OUT.println("====>>Host : " + host +  " place id : " + p.id + " partitionID : " + partitionID + "");
	        
	        val ptID:String = partitionID;
	        async{
	            intermRes(k) = call_countTraingles(host, port, graphID, ptID);
	        }
	        cntr++;
	    }
	    	    
	    for(var i:Int=0n; i < hostListLen; i++){
	    	result += intermRes(i);
	    }
	    
	    Console.OUT.println("---------- Now calculating the global only traingles --------");
	    //Next we need to count the traingles in the global graph only.
	    val globalTriangleCount = call_countGlobalTraingles(graphID);
	    
	    /*
	     * This is the X10 way of doing this. But the connection with the HSQLDB is not successful.
	     * 
	    var fromID:Long = -1;
	    var toID:Long = -1;
	    //var centralPartionCount:Int = java.lang.Integer.parseInt(((String[])org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value)[(Int)0L]);
	    //var centralPartionCount:Int = Int.parse(org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).getBackingArray()(0));
	    //var itm:Rail[String] = org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String];
	    var centralPartionCount:Int = Int.parse((org.acacia.metadata.db.java.MetaDataDBInterface.runSelect("select CENTRALPARTITIONCOUNT from ACACIA_META.GRAPH where IDGRAPH=" + graphID).value as Rail[String])(0));
	    Console.OUT.println("centralPartionCount : " + centralPartionCount);
	    //var localSubGraphMap:java.util.HashMap[Long, java.util.TreeSet[Long]] = new java.util.HashMap[Long, java.util.TreeSet[Long]]();
	    var localSubGraphMap:java.util.HashMap = new java.util.HashMap();
	    var edgeCounter:Long = 0;
	    
	    
	    for(var i:Int = 0n; i < centralPartionCount; i++){
	    	val c:java.sql.Connection = org.acacia.centralstore.java.HSQLDBInterface.getConnection(graphID, ""+i);
	    	try{
	    		val stmt:java.sql.Statement = c.createStatement();
	    		//java.sql.ResultSet rs = stmt.executeQuery("SELECT idfrom,idto FROM acacia_central.edgemap where idgraph=" + graphID + " and (idpartfrom = " + partitionID + " or idpartto = " + partitionID + ");" );
	    		val rs:java.sql.ResultSet = stmt.executeQuery("SELECT idfrom,idto FROM acacia_central.edgemap where idgraph=" + graphID + ";" );
	    
	    		if(rs != null){
	    			while(rs.next()){
	    				fromID = rs.getLong(1n);
	    				toID = rs.getLong(2n);
	    				
	                    if(localSubGraphMap.containsKey(fromID)){
	                        var lst:java.util.TreeSet = localSubGraphMap.get(fromID) as java.util.TreeSet;
	                        lst.add(toID);
	                        localSubGraphMap.put(fromID, lst);
	                    }else{
	                        var lst:java.util.TreeSet = new java.util.TreeSet();
	                        lst.add(toID);
	                        localSubGraphMap.put(fromID, lst);
	                    }
	                    
	                    if(localSubGraphMap.containsKey(toID)){
	                    	var lst:java.util.TreeSet = localSubGraphMap.get(toID) as java.util.TreeSet;
	                    	lst.add(fromID);
	                    	localSubGraphMap.put(toID, lst);
	                    }else{
	                    	var lst:java.util.TreeSet = new java.util.TreeSet();
	                    	lst.add(fromID);
	                    	localSubGraphMap.put(toID, lst);
	                    }
	                    
	                    edgeCounter++;
	    			}
	    		}
		    }catch(val e:java.sql.SQLException){
		    	e.printStackTrace();
		    } 
	    }
	    */
	    
	    
	    
	    Console.OUT.println("Global traingles : " + globalTriangleCount);
	    result += globalTriangleCount;
	    return result;
	}
	
	private static def truncateAcacia(){
		//First we truncate all the AcaciaInstances
		for (p in Place.places()){
			//at(p){
//PlaceToNodeMapper.getHost(p.id), PlaceToNodeMapper.getInstancePort(p.id), graphID
				call_truncateLocalInstance(PlaceToNodeMapper.getHost(p.id), PlaceToNodeMapper.getInstancePort(p.id));
			//}
		}
		
		//Next we need to remove the tables of HBase
		var idArr:Rail[String] = call_runSelect("select IDGRAPH from ACACIA_META.Graph");
				
		//Finally we truncate the related metadb tables.	
		call_runDelete("DELETE FROM ACACIA_META.HOST_HAS_PARTITION");
		call_runDelete("DELETE FROM ACACIA_META.PARTITION");
		call_runDelete("DELETE FROM ACACIA_META.GRAPH");
		//In the case of Acacia Central store, there is no point of deleting each and every 
		//embedded graph instance. Its better to delete all the db files from the central store
		//location.
		//Here we assume that the central store is located on the node where Place 0 is running.
		//This will be done in the method "call_truncateLocalInstance()"
	}
	
	private static def removeGraph(val graphID:String):Long{	
		var result:Long = 0;
        
/*		val hosts:Rail[String] = org.acacia.util.Utils.getPrivateHostList();//["sc01.sc.cs.titech.ac.jp", "sc02.sc.cs.titech.ac.jp", "sc03.sc.cs.titech.ac.jp", "sc04.sc.cs.titech.ac.jp"];
		val hostListLen:Int = hosts.size as Int;//Number of hosts cannot be a long value
		var intermRes:Rail[Long] = new Rail[Long](hostListLen);
		
		finish for(var i:Int=0n; i < hostListLen; i++){
			val k:Int = i;
			async{
				intermRes(k) = call_removeVertices(hosts(k), graphID);
			}
		}
		
		for(var i:Int=0n; i < hostListLen; i++){
			result += intermRes(i);
		}
 * */
        //28/10/2014: Now we are dealing with places
        val numPlaces:Int = Place.places().size as Int;
        var intermRes:Rail[Long] = new Rail[Long](numPlaces);
        //SELECT NAME, PARTITION_IDPARTITION FROM ACACIA_META.HOST_HAS_PARTITION INNER JOIN ACACIA_META.HOST ON HOST_IDHOST=IDHOST WHERE PARTITION_GRAPH_IDGRAPH=197
        var hostPartitionResults:Rail[String] = call_runSelect("SELECT NAME, PARTITION_IDPARTITION FROM ACACIA_META.HOST_HAS_PARTITION INNER JOIN ACACIA_META.HOST ON HOST_IDHOST=IDHOST WHERE PARTITION_GRAPH_IDGRAPH=" + graphID);
        
        var mp:HashMap[String, String] = new HashMap[String, String]();
        
        for(var i:long=0; i<hostPartitionResults.size; i++){
        //Console.OUT.println("pl(i) : " + l(i));
        	val items:Rail[String] = hostPartitionResults(i).split(",");
            mp.put(items(0), items(1));
        }
        
        finish for(var i:Int=0n; i < numPlaces; i++){
	        val k:Int = i;
	        async{
	        	//intermRes(k) = call_removeVertices(PlaceToNodeMapper.getHost(k), PlaceToNodeMapper.getInstancePort(k), graphID, ""+k);
	            val hst:String = PlaceToNodeMapper.getHost(k);
	            intermRes(k) = call_removeVertices(hst, PlaceToNodeMapper.getInstancePort(k), graphID, mp.get(hst));
	        }
        }
        
        for(var i:Int=0n; i < numPlaces; i++){
        	result += intermRes(i);
        }
		
		//Next we need to remove the meta data database entries.
        //Aug 30 2014 : May be have to re think whether we are deleting the meta-db entries of a deleted graph or not.
		call_runDelete("DELETE FROM ACACIA_META.HOST_HAS_PARTITION WHERE PARTITION_GRAPH_IDGRAPH=" + graphID);
		call_runDelete("DELETE FROM ACACIA_META.PARTITION WHERE GRAPH_IDGRAPH=" + graphID);
		call_runDelete("DELETE FROM ACACIA_META.GRAPH WHERE IDGRAPH=" + graphID);

        //March 7, 2015: We need to delete the graph's content from the central store as well
        //org.apache.commons.io.FileUtils.deleteDirectory();
        var centralDir:java.io.File = new java.io.File(Utils_Java.getAcaciaProperty("org.acacia.centralstore.location")); 
		var fileFilter:java.io.FileFilter = new org.apache.commons.io.filefilter.WildcardFileFilter(""+graphID+"_*");
        var files:x10.interop.Java.array[java.io.File] = centralDir.listFiles(fileFilter);
        Console.OUT.println("Files to delete are");
        for(var i:Int = 0n; i < files.length; i++ ){
            //Console.OUT.println("=======>" + i + " " + files(i).getAbsolutePath());
            var delStatus:Boolean = false;
            
            if(files(i).isDirectory()){
                //org.apache.commons.io.FileUtils.deleteDirectory(files(i).getAbsolutePath());
                //(((fileName as (x10.util.Box<java.lang.String>)).value))
                try{
                    org.apache.commons.io.FileUtils.deleteDirectory(files(i));
                }catch(var ex:java.io.IOException){
                    ex.printStackTrace();
                }
            }else{
                files(i).delete();
            }
            Console.OUT.println("=======>" + i + " " + files(i).getAbsolutePath()+"--->deleted?-->" + delStatus);
        }
        Console.OUT.println("-------------------");
        
		return result;
	}
	
	/**
	 * This method counts the number of vertices in a graph stored in Acacia.
	 */
	private static def countVertices(val graphID:String):Long{
        return call_countVertices(graphID);
	}
	
	/**
	 * This method counts the number of edges in a graph stored in Acacia.
	 */
	private static def countEdges(val graphID:String):Long{
         return call_countEdges(graphID);
	}
	
/**
 * This method calculates the top-k pagerank of a graph stored in Acacia.
 */
private static def getTopKPageRank(val graphID:String, val k:Int):String{
    var result:String = "";
    val hosts:Rail[String] = org.acacia.util.Utils.getPrivateHostList();//["sc01.sc.cs.titech.ac.jp", "sc02.sc.cs.titech.ac.jp", "sc03.sc.cs.titech.ac.jp", "sc04.sc.cs.titech.ac.jp"];
    val hostListLen:Int = hosts.size as Int;//Number of hosts cannot be a long value
    var intermRes:Rail[String] = new Rail[String](hostListLen);
    var hostList:StringBuilder = new StringBuilder(); 

    for(hst in hosts){
    	hostList.add(hst);
    	hostList.add(",");
    }

    val hstLine:String = hostList.toString();    
    
    var l:Rail[String] = call_runSelect("SELECT NAME,PARTITION_IDPARTITION FROM ACACIA_META.HOST_HAS_PARTITION INNER JOIN ACACIA_META.HOST ON HOST_IDHOST=IDHOST WHERE PARTITION_GRAPH_IDGRAPH=" + graphID + ";");

    var mp:HashMap[String, ArrayList[String]] = new HashMap[String, ArrayList[String]]();
    
    for(var i:long=0; i<l.size; i++){
    val items:Rail[String] = l(i).split(",");
    //First we have to see whether we already have the host stored inside the HashMap.
    
    val pts = mp.get(items(0));
    var partitions:ArrayList[String] = null;
    
    //This means there was no ArrayList object which holds the graph partitions for the host id stored in
    //items(0)
    if(pts == null){
    	partitions = new ArrayList[String]();
    }else{
    	partitions = pts as ArrayList[String];
    }
    
    partitions.add(items(1));
    mp.put(items(0), partitions);
    }
    
    
    finish for(var i:Int=0n; i < hostListLen; i++){
	    val u:Int = i;
	    
	    var partitions:ArrayList[String] = mp.get(hosts(u)) as ArrayList[String];
	    var partitionID:String = null;
	    
	    if(partitions.size() > 0){
	    	partitionID = partitions.removeFirst();
	    }
	    
	    // Console.OUT.println("====>>Host : " + host +  " place id : " + p.id + " partitionID : " + partitionID + "");
	    
	    val ptID:String = partitionID;
	    
	    async{
	    	intermRes(u) = call_pageRankTopK(hosts(u), graphID, ptID, hstLine, "" + k);
	    }
    }
    
    for(var i:Int=0n; i < hostListLen; i++){
    	result += intermRes(i);
    }  

    //Here we need to parse the result and return only the top K vertices having the highest pagerank scores 
    val str1:Rail[String] = result.split(";");
    var current:Double = 0d;
    var largest:Double = 0d;
    var lastLargest:Double = 0d;
    var largestVertex:String = null;
    var resultMP:HashMap[String, String] = new HashMap[String, String]();
    for(var i:Int = 0n; i < k; i++){
	    for(item in str1){
	         val str2:Rail[String] = item.split(",");
	         current = Double.parse(str2(1));
	         
	         if(lastLargest == 0d){
		         if(current > largest){
		             largest = current;
		             largestVertex = str2(0);
		         }
	         }else{
	             if((lastLargest > current)&&(current > largest)){
	                 largest = current;
	                 largestVertex = str2(0);
	             }
	         }
	    }
	    lastLargest = largest;
	    largest = 0d;
	    resultMP.put(largestVertex, "" + lastLargest);
    }
    
    val itr3:x10.lang.Iterator[x10.util.Map.Entry[String, String]] = resultMP.entries().iterator();
    var sb2:StringBuilder = new StringBuilder();
    while(itr3.hasNext()){
         val entr:x10.util.Map.Entry[String, String] = itr3.next();
         sb2.add(entr.getKey() + "," + entr.getValue() + ";");
    }
    
    return sb2.toString();
}

	/**
	 * This method counts the number of edges in a graph stored in Acacia.
	 */
	private static def getPageRank(val graphID:String):String{
		var result:String = "";
		val hosts:Rail[String] = org.acacia.util.Utils.getPrivateHostList();//["sc01.sc.cs.titech.ac.jp", "sc02.sc.cs.titech.ac.jp", "sc03.sc.cs.titech.ac.jp", "sc04.sc.cs.titech.ac.jp"];
		val hostListLen:Int = hosts.size as Int;//Number of hosts cannot be a long value
		var intermRes:Rail[String] = new Rail[String](hostListLen);
		var hostList:StringBuilder = new StringBuilder(); 
		
		for(hst in hosts){
			hostList.add(hst);
			hostList.add(",");
		}
		
		val hstLine:String = hostList.toString();    
		finish for(var i:Int=0n; i < hostListLen; i++){
			val u:Int = i;
			async{
				intermRes(u) = call_pageRank(hosts(u), graphID, hstLine);//call_pageRankTopK(hosts(u), graphID, hstLine, "" + k);
			}
		}
		
		for(var i:Int=0n; i < hostListLen; i++){
			result += intermRes(i);
		}
		
		return result;
	}

    private static def getFreeSpaceInfo():String{
        var result:String = null;
        var interimres:StringBuilder = new StringBuilder();

        //This code segment assumes that there is one place running on each host. But this many not be true at all times. We may run multiple places in one host
        // for (p in Place.places()){
        //     if(p.id == 0){
        //            val freeSP = call_getFreeSpaceInfo(System.getenv("HOSTNAME"))  + ",";
        //            Console.OUT.println("freeSP (At place 0) : " + freeSP);
        //            interimres.add(freeSP);
        //     }else{
        //            val freeSP = at(p){
        //            		return call_getFreeSpaceInfo(System.getenv("HOSTNAME")) + ",";
        //            };
        //            Console.OUT.println("freeSP (At place " + p.id + ") : " + freeSP);
        //            interimres.add(freeSP);
        //     }
        // }   
        
        val hosts:Rail[String] = org.acacia.util.Utils.getPrivateHostList();
        for(host in hosts){
                   val freeSP = call_getFreeSpaceInfo(host)  + ",";
                   Console.OUT.println("freeSP (At place 0) : " + freeSP);
                   interimres.add(freeSP);
        }
        
        //This place needs improvement
        if(result == null){
        	result = interimres.toString();
        }else{
        	result += interimres.toString();
        }
        
        return result;
    }


    /**
     * This method counts the number of vertices in a graph stored in Acacia.
     */
    private static def getOutDegreeDistribution(val graphID:String):String{
         var result:String = null;
         var interimres:StringBuilder = new StringBuilder();
         
         val hosts:Rail[String] = org.acacia.util.Utils.getPrivateHostList();
         val numPlaces:Int = Place.places().size as Int;//Number of places cannot be a long value
         var intermDegRes:Rail[String] = new Rail[String](numPlaces);
                  
         finish for(var i:Int=0n; i < numPlaces; i++){
                val k:Long = i;
                async{
         				intermDegRes(k) = call_outDegree(PlaceToNodeMapper.getHost(k), "" + PlaceToNodeMapper.getInstancePort(k), graphID);
	         	}
         }
         
         val sb:StringBuilder = new StringBuilder();
         
         for(var i:Int=0n; i < numPlaces; i++){
           sb.add(intermDegRes(i));
         }
         
         result = sb.toString();

         return result;
}
	
	/**
	 * This method checks if a graph exists in Acacia. This method uses the unique path of the graph.
	 */
	private static def graphExists(val path:String):Boolean{
		var result:Boolean = true;		
		var l:Rail[String] = call_runSelect("select COUNT(*) from ACACIA_META.Graph where UPLOAD_PATH LIKE '" + path + "';");
		
		if (l(0).equals("0")){
			result = false;
		}
		
		return result;
	}
	
	/**
	 * This method checks if a graph exists in Acacia using a unique id. This is an integer value assigned by Acacia for each graph.
	 */
	private static def graphExistsByID(val id:String):Boolean{
		var result:Boolean = true;		
		var l:Rail[String] = call_runSelect("select COUNT(*) from ACACIA_META.Graph where IDGRAPH=" + id + ";");
		
		if (l(0).equals("0")){
			result = false;
		}
		
		return result;
	}
	
	
	
	
	@Native("java", "org.acacia.metadata.db.java.MetaDataDBInterface.runSelect(#1)")
	static native def call_runSelect(String):Rail[String];
	
	// @Native("java", "org.acacia.server.AcaciaManager.countVertices(#1, #2)")
	// static native def call_countVertices(String, String):Long;

	@Native("java", "org.acacia.server.AcaciaManager.countVertices(#1)")
	static native def call_countVertices(String):Long;
	
	// @Native("java", "org.acacia.server.AcaciaManager.countEdges(#1, #2)")
	// static native def call_countEdges(String, String):Long;

    @Native("java", "org.acacia.server.AcaciaManager.countEdges(#1)")
    static native def call_countEdges(String):Long;	
	
	@Native("java", "org.acacia.server.AcaciaManager.removeVertices(#1, #2, #3, #4)")
	static native def call_removeVertices(String, Int, String, String):Long;
	
	@Native("java", "org.acacia.metadata.db.java.MetaDataDBInterface.runDelete(#1)")
	static native def call_runDelete(String):Int;
	
	@Native("java", "org.acacia.server.AcaciaManager.truncateLocalInstance(#1, #2)")
	static native def call_truncateLocalInstance(String, Int):void; 

    //(int EntireGraphSize, int localGraphSize)
    //@Native("java", "org.acacia.query.algorithms.pagerank.ApproxiRank.run(#1, #2)")
	@Native("java", "org.acacia.server.AcaciaManager.pageRank(#1, #2, #3)")
    static native def call_pageRank(String, String, String):String; 
    
    @Native("java", "org.acacia.server.AcaciaManager.pageRankTopK(#1, #2, #3, #4, #5)")
    static native def call_pageRankTopK(String, String, String, String, String):String;
    
    @Native("java", "org.acacia.server.AcaciaManager.countTraingles(#1, #2, #3, #4)")
    static native def call_countTraingles(String, Int, String, String):Long;
    
    @Native("java", "org.acacia.server.AcaciaManager.countGlobalTraingles(#1)")
    static native def call_countGlobalTraingles(String):Long;
    
    @Native("java", "org.acacia.query.algorithms.degree.OutDegree.run(#1, #2, #3)")
    static native def call_outDegree(String, String, String):String; 
    
    @Native("java", "org.acacia.server.AcaciaManager.getFreeSpaceInfo(#1)")
    static native def call_getFreeSpaceInfo(String):String;
}
