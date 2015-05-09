package org.acacia.partitioner.index;

public class PartitionIndexProtocol{
	public static String SEND = "send";
	public static String EXIT = "exit";		//To exit from the query session
	public static String EXIT_ACK = "bye";
	public static String PARTITION_ID_REQ = "ptn";	//To request resolution of a vertex partition ID
	public static String ERROR = "emsg";	//This is an error message sent from Acacia. Probably there is something wrong either in Acacia or in user's actions.
	public static String SEND_VERT_ID = "vid?";	//To persist the values recorded with the singleton on the MetaDB
	public static String DONE = "done";		//To indicate some operation (long running) is completed	
	public static String LOAD = "load";		//To make the index loaded
	public static String BATCH_PARTITION_ID_REQ = "bptn";	//This is a continuous sequence of requests and responses.
	public static String BATCH_PARTITION_ID_REQ_DONE = "bptn-done";	//To note the end of the continupous sequence.
	public static String CREATE_INDEX = "mkidx";	//Create a PartitionIndex object
	public static String CREATE_INDEX_ACK = "mkidxack";	//Create a PartitionIndex object acknowledgement
}