package org.acacia.partitioner.hadoop.java;

import org.acacia.util.java.Utils_Java;

/**
 * This class is actually unnecessary because there is org.acacia.partitioner.hadoop.HDFSInterface. However for the time being I keep it here.
 * @author miyuru
 *
 */
public class HDFSInterface_Java{
	
	public static String[] listFiles(String pathOnHDFS){
		String[] result = null;
		String hadoopHome = Utils_Java.getAcaciaProperty("org.acacia.partitioner.hadoop.home");
		
		
		
		return result;
	}
}