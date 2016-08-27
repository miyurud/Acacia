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

package test.acacia.server;

import java.util.HashMap;

import org.acacia.centralstore.java.HSQLDBInterface;
import org.acacia.log.java.Logger_Java;

/**
 * Class TestAcaciaVertexCounter
 */
public class TestPartitionIndexClient {
    /**
     * Main method
     */
    public static void main(String[] args) {
        long result = -1;
        long fromID = -1;
        long toID = -1;
        String gid = args[0];
        String partid = args[1];
        HashMap<Long, Long> edgeList = new HashMap<Long, Long>();
        java.sql.Connection c = org.acacia.centralstore.java.HSQLDBInterface
                .getConnection(gid, partid);

        System.out
                .println("==============================================================================================7");

        try {
            // c.setAutoCommit(false);
            java.sql.Statement stmt = c.createStatement();
            // java.sql.ResultSet rs =
            // stmt.executeQuery("SELECT idfrom,idto FROM acacia_central.edgemap where idgraph="
            // + graphID + " and (idpartfrom = " + partitionID +
            // " or idpartto = " + partitionID + ");" );
            // java.sql.ResultSet rs =
            // stmt.executeQuery("SELECT idfrom,idto FROM \"ACACIA_CENTRAL\".\"EDGEMAP\" where idgraph="
            // + gid + ";" );
            // java.sql.ResultSet rs =
            // stmt.executeQuery("SELECT idfrom,idto FROM EDGEMAP where idgraph="
            // + gid + ";" );

            // SELECT * FROM INFORMATION_SCHEMA.TABLES

            // java.sql.ResultSet rs =
            // stmt.executeQuery("SELECT DISTINCT TABLE_SCHEMA FROM   INFORMATION_SCHEMA.TABLES;"
            // );
            // int counter = 0;
            // if(rs != null){
            // while(rs.next()){
            // System.out.println("column(1) : " + rs.getString(1));
            // counter++;
            // }
            // }else{
            // Logger_Java.info("========================= getIntersectingTraingles() count is null =========================");
            // }
            //
            // System.out.println("count : " + counter);

            java.sql.ResultSet rs = stmt
                    .executeQuery("SELECT idfrom,idto FROM acacia_central.edgemap where idgraph=229;");

            if (rs != null) {
                while (rs.next()) {
                    fromID = rs.getLong(1);
                    toID = rs.getLong(2);
                    edgeList.put(fromID, toID);
                }
            } else {
                Logger_Java
                        .info("========================= getIntersectingTraingles() count is null =========================");
            }

            c.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        System.out.println("size:" + edgeList.size());
    }
}