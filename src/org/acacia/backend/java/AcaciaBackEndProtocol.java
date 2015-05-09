package org.acacia.backend;

/**
 * Class AcaciaFrontEndProtocol
 */
public class AcaciaBackEndProtocol {
    //This protocol will too have a handshaking session. This is to enable the server track with whom it communicated.
    public static String HANDSHAKE = "hske";		//To notify the server the host name of this worker
    public static String HANDSHAKE_OK = "hske-ok";		//Response to say it is ready for handshaking.
    public static String OK = "ok";		//To check if the status is ok
    public static String RUOK = "ruok";		//To check if the status is ok
	public static String IMOK = "imok";		//Response to check if the status is ok
	public static String EXIT = "exit";		//To exit from the query session
	public static String EXIT_ACK = "bye";
    public static String SEND = "send";		//Ask the client to send some data. This is used during a comminication session with the client.
    public static String OUT_DEGREE_DISTRIBUTION_FOR_PARTITION = "podg";
    public static String IN_DEGREE_DISTRIBUTION_FOR_PARTITION = "pidg"; //This commad gets the in degree distribution from the external world to this partition
    public static String WORLD_ONLY_AUTHFLOW_FOR_PARTITION="woaf";
    public static String WORLD_TO_LOCAL_AUTHFLOW_FOR_PARTITION="wlaf";
    public static String LOCAL_TO_WORLD_AUTHFLOW_FOR_PARTITION="lwaf";
    public static String WORLD_TO_LOCAL_FLOW_FROMIDS="wlfi"; //This command gets all the vertices connected with the external graph and their corresponding fromIDs
    public static String INTERSECTING_TRIANGLE_COUNT = "trii"; //This is the command to count the number of traingles available on the global graph.
    public static String DONE = "done";    
    public static String PARTITIONS_ON_HOST = "pohs"; //The command to get the list of partitions on particular host
}