package org.acacia.util.java;

public class Conts_Java {
	public static final String BATCH_UPLOAD_CONFIG_FILE = "conf/batch-upload.txt";
	public static final String ACACIA_SERVER_PROPS = "conf/acacia-server.properties";

	public static final int ACACIA_PARTITION_INDEX_PORT = 7776;
	public static final int ACACIA_FRONTEND_PORT = 7777;
	public static final int ACACIA_BACKEND_PORT = 7778;
	public static final int ACACIA_VERTEXCOUNTER_PORT = 7779;
	//The following ports will be calculated dynamically.
	public static final int ACACIA_INSTANCE_PORT = 7780; //This is the starting point for instance port numbers
	//ACACIA_INSTANCE_DATA_PORT range will stat from the place where ACACIA_INSTANCE_PORT ports end.
	public static final int ACACIA_INSTANCE_DATA_PORT = 7781;//This is the port through which the file transfer activities are conducted.	
}