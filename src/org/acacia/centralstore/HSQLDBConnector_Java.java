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

package org.acacia.centralstore;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Utils_Java;

/**
 * @author miyuru
 *
 */
public class HSQLDBConnector_Java {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

    public Connection getConnection(String graphID, String partID){
    	Connection c = null;
    	try{
    		String loc = Utils_Java.getAcaciaProperty("org.acacia.centralstore.location")+ "/" + graphID + "_" + partID;
    		
    		File f = new File(loc);
    		
    		if(!f.exists()){
    			f.mkdir();
    		}
    		
    		c = DriverManager.getConnection("jdbc:hsqldb:file:" + loc  + "/" + graphID + "_" + partID);
    		
    	}catch(SQLException e){
    		return null;
    	}
    	
    	return c;
    }
    
    public boolean initDBSchema(String graphID, String partID){
    	Connection dbCon = getConnection(graphID, partID);
    	
    	try {
    		Statement stmt = dbCon.createStatement();
    		
    		try{
    			stmt.executeUpdate("CREATE SCHEMA ACACIA_CENTRAL AUTHORIZATION DBA;");    			
    		}catch(SQLSyntaxErrorException ex){
    			Logger_Java.info("The ACACIA_CENTRAL schema already exists.");
    		}
    		// stmt.executeUpdate("SET SCHEMA acacia_central;");
    		// stmt.executeUpdate("CREATE TABLE IF NOT EXISTS graph_status(idgraph_status INT NOT NULL IDENTITY, description VARCHAR(45) NOT NULL);");
    		// stmt.executeUpdate("CREATE TABLE IF NOT EXISTS graph(idgraph INT NOT NULL IDENTITY, name VARCHAR(45) NULL, upload_path VARCHAR(1024) NOT NULL, upload_start_time TIMESTAMP NOT NULL, upload_end_time TIMESTAMP NOT NULL, GRAPH_STATUS_IDGRAPH_STATUS INT, vertexcount LONG NOT NULL);");
    		// stmt.executeUpdate("CREATE TABLE IF NOT EXISTS partition(idpartition INT NOT NULL IDENTITY, graph_idgraph INT, vertexcount INT, edgecount INT);");
    		// stmt.executeUpdate("CREATE TABLE IF NOT EXISTS host(idhost INT NOT NULL IDENTITY, name VARCHAR(45), ip VARCHAR(45));");
    		// stmt.executeUpdate("CREATE TABLE IF NOT EXISTS host_has_partition(host_idhost INT, partition_idpartition INT, partition_graph_idgraph INT);");
    		// System.out.println("Done creating tables");
    		dbCon.commit();
    		dbCon.close();
    	} catch (SQLException e) {
    		//Logger.getLogger(Conts.MANAGER_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
    		e.printStackTrace();
    		return false;
    	}
    	
    	return true;
    }
    
    public boolean createTable(String graphID, String partID, String qr){
    	Connection dbCon = getConnection(graphID, partID);
    	
    	try {
    		Statement stmt = dbCon.createStatement();
    		
    		// try{
    		// 	stmt.executeUpdate("CREATE SCHEMA acacia_central AUTHORIZATION DBA;");
    		// }catch(val ex:SQLSyntaxErrorException){
    		// 	Console.OUT.println("The Acacia_Central schema already exists.");
    		// }
    		stmt.executeUpdate("SET SCHEMA ACACIA_CENTRAL;");
    		stmt.executeUpdate(qr);
    		dbCon.commit();
    		dbCon.close();
    	} catch (SQLException e) {
    		//Logger.getLogger(Conts.MANAGER_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
    		e.printStackTrace();
    		return false;
    	}
    	
    	return true;
    }
}
