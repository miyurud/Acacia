package org.acacia.metadata.db.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

import java.util.ArrayList;
import java.util.Iterator;

import x10.regionarray.Array;
//import x10.lang.Rail;

/**
 * This class acts as the metadata db interface.
 * @author miyuru
 *
 */
public class MetaDataDBInterface{
	/**
	 * Executes an insert query to the Acacia metadata database and returns null if execution was successful. IF not it returns an error message
	 * in a String object. 
	 * @param query
	 * @return On success a string with the inserted auto increment ids if any. If not returns null.  
	 */
	public static String runInsert(String query){
		String result = null;
		
		System.out.println("Insert query is |" + query + "|");
		
		AcaciaHSQLDBComm dbCon = new AcaciaHSQLDBComm();
		Connection con = dbCon.getDBConnection();
		
		PreparedStatement stmt;
		
		try {
			stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			
			boolean first = true;
			while(rs.next()){
				if(first){
					first = false;
					result = rs.getString(1);
				}else{
					result += "," + rs.getString(1);
				}
			}
			
			con.commit();
			con.close();
		} catch (SQLException e) {
			return "Error : " + e.getMessage();
		}
		
		return result;
	}
	
	public static boolean runUpdate(String query){
		boolean result = true;
		
		System.out.println("Update query is |" + query + "|");
		
		AcaciaHSQLDBComm dbCon = new AcaciaHSQLDBComm();
		Connection con = dbCon.getDBConnection();
		
		PreparedStatement stmt;
		
		try {
			stmt = con.prepareStatement(query);
			stmt.executeUpdate();
			con.commit();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	
	public static int runDelete(String query){
		int result = -1;
		
		System.out.println("Delete query is |" + query + "|");
		
		AcaciaHSQLDBComm dbCon = new AcaciaHSQLDBComm();
		Connection con = dbCon.getDBConnection();
		
		PreparedStatement stmt;
		
		try {
			stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			result = stmt.executeUpdate();
			
			con.commit();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	/**
	 * Executes a select statement and returns the result in a String array. If the results contains information of single column, they are returned as items in an array.
	 * If there are results from multiple columns, each array element will correspond to a row. Each row element will be separated by a comma. 
	 * @param query
	 * @return
	 */
	public static x10.core.Rail<java.lang.String> runSelect(String query){
		x10.util.ArrayList<java.lang.String> lst = new x10.util.ArrayList<java.lang.String>((java.lang.System[]) null, x10.rtt.Types.STRING).x10$util$ArrayList$$init$S(); 
		AcaciaHSQLDBComm db = new AcaciaHSQLDBComm();
		Connection con = db.getDBConnection();
			
		Statement stmt;
		//System.out.println("Ok1");
		
		try {
			stmt = con.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT idgraph FROM graph;");
			ResultSet rs = stmt.executeQuery(query);
			//System.out.println("Ok2");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			//System.out.println("column count : " + columnCount);
			
			if(rs != null){
				if (columnCount == 1){
			    	while(rs.next()){
			    			((x10.util.ArrayList<java.lang.String>)lst).add__0x10$util$ArrayList$$T$O(((java.lang.String)(rs.getString(1))));
			    	}
		    	}else if (columnCount > 1) {
		    		while(rs.next()){
		    			int i=0;
		    			StringBuilder result = new StringBuilder();
			    		for(i=0; i < (columnCount-1); i++){
			    			//((x10.util.ArrayList<java.lang.String>)lst).add__0x10$util$ArrayList$$T$O(((java.lang.String)(rs.getString(i+1))));
			    			//((x10.util.ArrayList<java.lang.String>)lst).add__0x10$util$ArrayList$$T$O(((java.lang.String)(",")));
			    			result.append(rs.getString(i+1));
			    			result.append(",");
			    		}
			    		//((x10.util.ArrayList<java.lang.String>)lst).add__0x10$util$ArrayList$$T$O(((java.lang.String)(rs.getString(i+1))));
			    		result.append(rs.getString(i+1));
			    		((x10.util.ArrayList<java.lang.String>)lst).add__0x10$util$ArrayList$$T$O(((java.lang.String)(result.toString())));
		    		}
		    	}
			}

			//System.out.println("Ok4");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//System.out.println("Ok5");
		//Array<java.lang.String> arr =  new x10.regionarray.Array<java.lang.String>(((x10.util.ArrayList<java.lang.String>)lst).toRail());
		x10.core.Rail<java.lang.String> arr =  ((x10.util.ArrayList<java.lang.String>)lst).toRail();
		//System.out.println("Ok6");
		return arr;
		
		/*

x10.array.Array arr =
              ((x10.array.Array<java.lang.String>)
                ((x10.util.ArrayList<java.lang.String>)t72).toArray());

		 */
		
	}
	
	public static String[] runSelectPureJava(String query){
		ArrayList<String> lst = new ArrayList<String>(); 
		AcaciaHSQLDBComm db = new AcaciaHSQLDBComm();
		Connection con = db.getDBConnection();
			
		Statement stmt;
		//System.out.println("Ok1");
		
		try {
			stmt = con.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT idgraph FROM graph;");
			ResultSet rs = stmt.executeQuery(query);
			//System.out.println("Ok2");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			System.out.println("column count : " + columnCount);
			
			if(rs != null){
		    	while(rs.next()){
		    		//System.out.println("Ok3");
		    		//lst.add(rs.getString(1));
		    		for(int i=0; i < columnCount; i++){
		    			lst.add(rs.getString(i+1));
		    		}
		    	}
			}			
			System.out.println("Ok4");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		//System.out.println("Ok5");
		String[] arr = new String[lst.size()];
		Iterator itr = lst.iterator();
		int cntr = 0;
		while(itr.hasNext()){
			arr[cntr] = (String) itr.next();
			cntr++;
		}
		
		//System.out.println("Ok6");
		return arr;
		
		/*

x10.array.Array arr =
              ((x10.array.Array<java.lang.String>)
                ((x10.util.ArrayList<java.lang.String>)t72).toArray());

		 */
		
	}
}