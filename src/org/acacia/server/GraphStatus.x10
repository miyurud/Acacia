package org.acacia.server;

/**
 * Class GraphStatus
 */
public class GraphStatus {
	//Note that these values must be present on the database on the GRAPH_STATUS table as well.
	public static val LOADING:Int = 1n;
	public static val OPERATIONAL:Int = 2n;
	public static val DELETED:Int = 3n;
}