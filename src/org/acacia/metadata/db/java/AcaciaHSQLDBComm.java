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

package org.acacia.metadata.db.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Dictionary;

import org.acacia.util.java.Utils_Java;

/**
 * @author miyuru
 *
 */
public class AcaciaHSQLDBComm {
	String dbDirLoc = null;
	static String connectionString = null;
	
	public AcaciaHSQLDBComm(){
		String hsqldbhost = Utils_Java.getAcaciaProperty("org.acacia.metadata.db.host");
		String hsqldbport = Utils_Java.getAcaciaProperty("org.acacia.metadata.db.port");
		connectionString = "jdbc:hsqldb:hsql://" + hsqldbhost + ":" + hsqldbport + "/acacia_meta;ifexists=true";
	}
	
	public Connection getDBConnection(){
		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			//System.out.println("Connecting to : " + connectionString);
			Connection con = DriverManager.getConnection(connectionString, "SA", "");
			//System.out.println("Connected successfully.");
		    if(con != null){
		    	return con;
		    }
		} catch (SQLException e) {
			//Logger.getLogger(Conts.MANAGER_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
			if(e.getMessage().contains("Database does not exists")){
				System.out.println("No acacia DB");
				return null;
			}else{
				System.out.println("Error : " + e.getMessage());
				return null;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public static boolean setupAcaciaMetaDataDB(){
		System.out.println("Settingup the Acacia MetaData DB");
		Connection dbCon = null;
		Statement stmt = null;
		
		try {
			dbCon = DriverManager.getConnection(connectionString, "SA", "");
		} catch (SQLException e) {
			System.out.println("Again cloud not find the db folder.");
			e.printStackTrace();
		}
		
		try{
			stmt = dbCon.createStatement();
			stmt.executeUpdate("DROP SCHEMA ACACIA_META CASCADE;");
			dbCon.commit();
			dbCon.close();
		} catch (SQLException e) {
			System.out.println("The DB schema ACACIA does not exists. Continuing...");
		}
		
		try {
			dbCon = DriverManager.getConnection(connectionString, "SA", "");
		} catch (SQLException e) {
			System.out.println("Again cloud not find the db folder.");
			e.printStackTrace();
		}
		
		try {
			stmt = dbCon.createStatement();
			
			try{
				stmt.executeUpdate("CREATE SCHEMA ACACIA_META AUTHORIZATION DBA;");
			}catch(SQLSyntaxErrorException ex){
				System.out.println("The ACACIA_META schema already exists.");
			}
			System.out.println("Set schema");
			stmt.executeUpdate("SET SCHEMA ACACIA_META;");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ACACIA_META.graph_status(idgraph_status INT NOT NULL IDENTITY, description VARCHAR(45) NOT NULL);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ACACIA_META.graph(idgraph INT NOT NULL IDENTITY, name VARCHAR(45) NULL, upload_path VARCHAR(1024) NOT NULL, upload_start_time TIMESTAMP NOT NULL, upload_end_time TIMESTAMP NOT NULL, GRAPH_STATUS_IDGRAPH_STATUS INT, vertexcount BIGINT NOT NULL, CENTRALPARTITIONCOUNT INT);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ACACIA_META.partition(idpartition INT NOT NULL IDENTITY, graph_idgraph INT, vertexcount INT, edgecount INT);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ACACIA_META.host(idhost INT NOT NULL IDENTITY, name VARCHAR(45), ip VARCHAR(45));");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ACACIA_META.host_has_partition(host_idhost INT, partition_idpartition INT, partition_graph_idgraph INT);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ACACIA_META.edgemap(idfrom INT NOT NULL, idto INT NOT NULL);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ACACIA_META.replication_stored_in(idreplication INT NOT NULL,stored_partition_id VARCHAR(45) NOT NULL,stored_host_id INT,PRIMARY KEY (idreplication, stored_partition_id));");			
			//
			System.out.println("Done creating tables");
			dbCon.commit();
			dbCon.close();
			System.out.println("Committed.");
		} catch (SQLException e) {
			//Logger.getLogger(Conts.MANAGER_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
			e.printStackTrace();
			return false;
		}		//Logger.getLogger(Conts.MANAGER_LOGGER_NAME).info("Created StreamFarm in-memory database.");
		
		try {
			dbCon = DriverManager.getConnection(connectionString, "SA", "");
		} catch (SQLException e) {
			System.out.println("Again cloud not find the db folder.");
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Altering ACACIA_META.graph");
		
		try {
			stmt = dbCon.createStatement();
			stmt.executeUpdate("ALTER TABLE ACACIA_META.GRAPH ADD FOREIGN KEY (GRAPH_STATUS_IDGRAPH_STATUS) REFERENCES ACACIA_META.GRAPH_STATUS(IDGRAPH_STATUS);");
			dbCon.commit();
			dbCon.close();
		} catch (SQLException e) {
			//Logger.getLogger(Conts.MANAGER_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Altering ACACIA_META.partition");
				
		try {
			dbCon = DriverManager.getConnection(connectionString, "SA", "");
		} catch (SQLException e) {
			System.out.println("Again cloud not find the db folder.");
			e.printStackTrace();
			return false;
		}
		
		try {
			stmt = dbCon.createStatement();
			stmt.executeUpdate("ALTER TABLE ACACIA_META.PARTITION ADD FOREIGN KEY (graph_idgraph) REFERENCES ACACIA_META.GRAPH(IDGRAPH);");
			dbCon.commit();
			dbCon.close();
		} catch (SQLException e) {
			//Logger.getLogger(Conts.MANAGER_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
			e.printStackTrace();
			return false;
		}
		
		
		System.out.println("Altering ACACIA_META.replication_stored_in");
		
		try {
			dbCon = DriverManager.getConnection(connectionString, "SA", "");
		} catch (SQLException e) {
			System.out.println("Again cloud not find the db folder.");
			e.printStackTrace();
			return false;
		}
		
		try {
			stmt = dbCon.createStatement();
			stmt.executeUpdate("ALTER TABLE ACACIA_META.REPLICATION_STORED_IN ADD FOREIGN KEY (idreplication) REFERENCES ACACIA_META.PARTITION(idpartition);");
			stmt.executeUpdate("ALTER TABLE ACACIA_META.REPLICATION_STORED_IN ADD FOREIGN KEY (stored_partition_id) REFERENCES ACACIA_META.PARTITION(idpartition);");
			stmt.executeUpdate("ALTER TABLE ACACIA_META.REPLICATION_STORED_IN ADD FOREIGN KEY (stored_host_id) REFERENCES ACACIA_META.HOST(idhost);");
			dbCon.commit();
			dbCon.close();
		} catch (SQLException e) {
			//Logger.getLogger(Conts.MANAGER_LOGGER_NAME).error("Error : " + e.getMessage() + "\r\nError Details : \r\n" + Utils.getStackTraceAsString(e));
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Completed altering tables.");
		
		return true;
	}	
}
