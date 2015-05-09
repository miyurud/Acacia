package test.acacia.metadata.db.java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.acacia.metadata.db.java.AcaciaHSQLDBComm;
import org.acacia.metadata.db.java.MetaDataDBInterface;

public class TestAcaciaHSQLDBComm{
	public static void main(String[] args){
		/*
		AcaciaHSQLDBComm db = new AcaciaHSQLDBComm();
		System.out.println("OOk111");
		Connection con = db.getDBConnection();
			
		Statement stmt;
			
		try {
			System.out.println("OOk");
			stmt = con.createStatement();
			System.out.println("OOa");
			ResultSet rs = stmt.executeQuery("SELECT idgraph FROM graph;");
			System.out.println("OOc");
			if(rs != null){
				System.out.println("OOd");
		    	while(rs.next()){
		    		System.out.println("graph id : " + rs.getInt(1));
		    	}
		    	System.out.println("OOf");
			}			
			System.out.println("OOd");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		
		//x10.array.Array<java.lang.String> runSelect(String query)
		
		/*
		x10.array.Array<java.lang.String> res = MetaDataDBInterface.runSelect("SELECT idgraph,name,upload_path FROM ACACIA.GRAPH");
		System.out.println("result size : " + res.size);
		java.util.Iterator itr = (Iterator) res.values().iterator();
		
		int c = 1;
		while (itr.hasNext()){
			System.out.println("" + c + "-->" + itr.next());
			c++;
		}
		*/
	}
}