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

package org.acacia.frontend;

public class AcaciaFrontEndProtocol {
    //Note that this protocol do not need a handshake session since the communication in most of the time is conducted
    //between Acacia and Humans.
    //The commands ending with -send are asking the graph id to be sent. The precommand
 	public static val ADGR:String = "adgr";		//To add a graph located on the NFS
    	public static val ADRDF:String = "adrdf";	//To add an RDF graph data set to Acacia
	public static val RMGR:String = "rmgr";		//To remove a graph that is stored on Acacia
	public static val TRUNCATE:String = "trnc"; //To truncate Acacia completely
	public static val DONE:String = "done";		//To indicate some operation (long running) is completed
	public static val ENVI:String = "envi";		//To check the environment information
	public static val RUOK:String = "ruok";		//To check if the status is ok
	public static val IMOK:String = "imok";		//Response to check if the status is ok
	public static val EXIT:String = "exit";		//To exit from the query session
	public static val EXIT_ACK:String = "bye";
	public static val SHTDN:String = "shdn";	//To shutdown Acacia remotely
	public static val LIST:String = "list";		//To get a list of currently available graphs
	public static val VCOUNT:String = "vcnt";	//To get the total count of vertices
	public static val ECOUNT:String = "ecnt"; //To get the total count of edges
	public static val SEND:String = "send";		//Ask the client to send some data. This is used during a comminication session with the client.
	public static val ERROR:String = "emsg";	//This is an error message sent from Acacia. Probably there is something wrong either in Acacia or in user's actions.
	                                            //The "emsg" will follow a detailed description of the error. E.g., "emsg:This is an error message"
	public static val DEBUG:String = "debg";	//This is just for testing. Can be elliminated in production.
    	public static val GREM:String = "grem";			//This starts a Gremlin session
    	public static val GREM_ACK:String = "grem-ack";			//The acknowledgement for start of the Gremlin session
    	public static val GREM_SEND:String = "grem-send"; //This requests the client to send the Gremlin query.
    	public static val GREM_DONE:String = "grem-done"; //This indicates that the Gremlin session should be completed.
    	public static val PAGERANK:String = "pagerank";    //This command calculates the pagerank score
    	public static val OUT_DEGREE:String = "odeg";    //This command calculates the out degree distribution
    	public static val IN_DEGREE:String = "ideg";
    	public static val IN_DEGREE_SEND:String = "ideg-send";
    	public static val AVERAGE_OUT_DEGREE:String = "aodeg";    //This command calculates the average out degree of a vertex
    	public static val AVERAGE_IN_DEGREE:String = "aideg";     //This command calculates the average in degree of a vertex
    	public static val TOP_K_PAGERANK:String = "tkpagerank";    //This command calculates the pagerank scores for all the graph but returns only the top K results
    	// public static val TOP_K_PAGERANK_SEND:String = "tkpagerank-send";    //Used to obtain two values. First get the target graph id, next get the value of K
    	public static val TOP_K_SEND:String = "k-send";
    	public static val FREE_DATA_DIR_SPACE = "dfdt"; //Get a list of free directory spaces on each worker node.
    	public static val GRAPHID_SEND = "graphid-send";
    	public static val TRIANGLES = "trian";       //This command counts the number of triangles in a graph.
    	public static val K_CORE = "kcore";			//This command initiates the K-Core calculation algorithm.
    	public static val K_VALUE = "kvalue";
    	public static val K_NN = "knn";				//This command initiates the K-NN calculation algorithm.
    	public static val SPARQL = "sparql";		//This command is to tell next commands are related with rdf graphs.
    	public static val S_QUERY_SEND = "s_query-send"; 	//This requets SPARQL query to be executed
    	public static val OUTPUT_FILE_NAME= "outputFile-name-send"; 	//This command requets the file name that results should be written
    	public static val OUTPUT_FILE_PATH= "outputFile-path-send"; 	//This command requets the file path
    	public static val ADD_STREAM="adstrm" ;// This command reads a stream into Acacia
    	public static val ADD_STREAM_KAFKA="adstrmk" ;// This command reads a Kafka stream into Acacia
        public static val STRM_ACK:String = "strm-ack"; //Acknowledgement for starting of streaming session.
}