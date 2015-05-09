package org.acacia.vertexcounter;

public class VertexCounterProtocol{
	public static String SEND = "send";		//Ask the client to send some data. This is used during a comminication session with the client.
	public static String EXIT = "exit";		//To exit from the query session
	public static String EXIT_ACK = "bye";
	public static String TICKET = "tckt";	//To request a ticket from the service. The ticket is basically a unique vertex id.
	public static String ERROR = "emsg";	//This is an error message sent from Acacia. Probably there is something wrong either in Acacia or in user's actions.
	public static String SAVE = "save";	//To persist the values recorded with the singleton on the MetaDB
	public static String DONE = "done";		//To indicate some operation (long running) is completed	
	public static String LOAD = "load";		//To make the vertexcountersession load the vertexcount information for each graph on Acacia
	public static String DEFAULT_GRAPH = "default-g"; //To set the dfault graph ID
}