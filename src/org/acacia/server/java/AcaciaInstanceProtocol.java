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

package org.acacia.server.java;

/**
 * Since this protocol represents a communication that happes between two machines rather than human/machine scenario, the commands and the
 * process of interaction need not be too expressive.
 * @author miyuru
 *
 */
public class AcaciaInstanceProtocol{
	//Its better not to have too many features on the Acacia Instance so that we can elliminate the lengthy if check for command.
//	public static final String RUOK = "ruok";
//	public static final String IMOK = "imok";

	public static final String HANDSHAKE = "hske";		//Handshaking is the first task that Acacia's main server does with an Acacia Instance once it gets connected.
//														//During the phase of Handshaking, Acacia server informs its host name to the instance so that it can connect with the server later time.
	public static final String HANDSHAKE_OK = "hske-ok";
	public static final String CLOSE = "close";
	public static final String CLOSE_ACK = "close-ok";
	public static final String SHUTDOWN = "shtdn";
	public static final String SHUTDOWN_ACK = "shtdn-ok";
	public static final String READY = "ready";
	public static final String OK = "ok";
	public static final String INSERT_EDGES = "iedge-batch"; //This is inserting a set of edges as a batch to the Instance.
	public static final String INSERT_EDGES_ACK = "iedge-batch-ok";
	public static final String INSERT_EDGES_COMPLETE = "iedge-complete"; //This is sent by the Acacia master to indicate that the batch uplocad has been compeleted.
	public static final String TRUNCATE = "truncate"; //This mesage truncates all the data on the Acacia Local Instance
	public static final String TRUNCATE_ACK = "truncate-complete";
	public static final String DELETE = "delete"; //This mesage deletes a particular graph from Acacia.
	public static final String DELETE_ACK = "delete-complete";	
	public static final String COUNT_VERTICES = "count-v";//Get the vertex count for a particular graph. 
	public static final String COUNT_EDGES = "count-e";//Get the edge count for a particular graph.
	public static final String OUT_DEGREE_DIST = "odegdist";//Get the out degree distribution for a particular graph.
	public static final String PAGE_RANK = "prnk";//Get the pagerank for a particular graph.	
	public static final String PAGE_RANK_TOP_K = "tkprnk";//Get the Top-K pagerank for a particular graph.
	public static final String TRIANGLES = "tria";//Counts the number if traiangles in a graph.
	public static final String IN_DEGREE_DIST = "idegdist";//Get the in degree distribution for a particular graph.
	public static final String LOAD_GRAPH = "load-g";//Loads a Neo4j graph object in to Acacia Instance.
	public static final String LOAD_GRAPH_ACK = "load-g-ack";
	public static final String UNLOAD_GRAPH = "unload-g";//Unloads a graph from the Acacia Instance.
	public static final String UNLOAD_GRAPH_ACK = "unload-g-ack";
	public static final String SET_GRAPH_ID = "set-g";//Sets the current graph ID
	public static final String SET_GRAPH_ID_ACK = "set-g-ack";
	public static final String BATCH_UPLOAD = "upload-g";		//This is to upload a file as a batch
	public static final String BATCH_UPLOAD_CENTRAL = "upload-g-c";		//This is to upload centralstore file as a batch
	public static final String BATCH_UPLOAD_CHK = "upload-g-chk";		//This is to check whether the upload process has finished or not.
	public static final String BATCH_UPLOAD_WAIT = "upload-g-wait";		//This is to check whether the upload process has finished or not.
	public static final String BATCH_UPLOAD_ACK = "upload-g-ack";
	public static final String SEND_FILE = "file";
	public static final String SEND_FILE_LEN = "file-len";//This is to indicate manager to send the size of the file.
	public static final String SEND_FILE_CONT = "file-cont";//This is to indicate manager to send the file contents.
	public static final String SEND_FILE_COMPLETE = "file-complete";
	public static final String SEND_FILE_NAME = "file-name";
	public static final String SEND_PARTITION_ID = "partid";//This command is used by the Instance service session to ask for partition id.
	public static final String FILE_RECV_CHK = "file-rcpt?";
	public static final String FILE_RECV_WAIT = "file-wait";
	public static final String FILE_RECV_ERROR = "file-error";
	public static final String FILE_ACK = "file-ok";
	public static final String STATUS = "status";//This is sent to the client to check its status.
	public static final String NPLACES = "nplac";//This is sent to the client to check its status.
	public static final String BATCH_UPLOAD_RDF = "btch-rdf";
	public static final String EXECUTE_QUERY= "execute-query"; // This executes the sparql query
	public static final String SEND_QUERY= "send-query";
	public static final String SEND_GID= "send-graphID";	
	public static final String SEND_PLACEDETAILS="send-placeDetails";
	public static final String SEND_PLACEID="send-placeID";
	public static final String BATCH_UPLOAD_REPLICATION="batch-replication";
	public static final String SEND_KVALUE = "kvalue";
	public static final String RUN_KCORE = "run-kcore";
	public static final String REPLICATING_ID = "replicating-id";
	public static final String EXECUTE_QUERY_WITH_REPLICATION = "execute-query-with-replication";
	public static final String BATCH_UPLOAD_REPLICATION_CENTRAL="batch-replication-central";
}