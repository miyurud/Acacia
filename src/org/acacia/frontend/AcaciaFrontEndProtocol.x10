package org.acacia.frontend;

/**
 * Class AcaciaFrontEndProtocol
 */
public class AcaciaFrontEndProtocol {
    //Note that this protocol do not need a handshake session since the communication in most of the time is conducted
    //between Acacia and Humans.
    //The commands ending with -send are asking the graph id to be sent. The precommand
	public static val ADGR:String = "adgr";		//To add a graph located on the NFS
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
    public static val SPARQL = "sparql";		//This command is to tell next commands are related with rdf graphs.
    public static val S_QUERY_SEND = "s_query-send"; 	//This requets SPARQL query to be executed
}