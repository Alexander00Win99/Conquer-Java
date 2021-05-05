package com.conquer_java.knowledge.interface_default_static;

public class TestMain {
    public static void main(String[] args) {
        MyStatementCallback cb = new MyStatementCallback() {
            @Override
            public Object doWithStatement(String stmtMock) {
                System.out.println("After callback specific implementation:");
                System.out.println(stmtMock + " --> real data!");
                System.out.println("This is a demo for 'Template Method' design pattern!");
                return null;
            }
        };
        MyJdbcTemplate jdbcTemplate = new MyJdbcTemplate();
        jdbcTemplate.execute(cb);
    }
}
