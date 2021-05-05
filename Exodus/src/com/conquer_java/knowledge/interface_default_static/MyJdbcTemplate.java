package com.conquer_java.knowledge.interface_default_static;

public class MyJdbcTemplate {
    public final Object execute(MyStatementCallback callback){
        String con = null;
        String stmt = null;
        try {
            con = "Mock data for getConnection()";
            stmt = "Mock data for con.createStatement()";

            System.out.println("Below is invocation of callback in template instance:");
            System.out.println("Original con in template:");
            System.out.println(con);
            System.out.println("Original stmt in template:");
            System.out.println(stmt);

            System.out.println("Below is going to invoke callback function in template instance:");
            Object retValue = callback.doWithStatement(stmt);
            return retValue;
//        } catch(SQLException e) {
//            //...
        } finally {
            //closeStatement(stmt);
            con = null;
            //releaseConnection(con);
            stmt = null;
        }
    }
}
