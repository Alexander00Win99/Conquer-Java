package com.conquer_java.pojo;

public enum EnumDB {
    // 驱动类：com.mysql.jdbc.Driver|com.mysql.cj.jdbc.Driver
    MYSQL(0, "mysql"),
    // 驱动类：oracle.jdbc.driver.OracleDriver
    ORACLE(1, "oracle"),
    // 驱动类：com.microsoft.sqlserver.jdbc.SQLServerDriver
    SQLSERVER(2, "sqlserver");

    private int index;
    private String type;

    /**
     * 构造方法默认是private
     * @param index
     * @param type
     */
    EnumDB(int index, String type) {
        this.index = index;
        this.type = type;
    }

    public String getDriver() {
        switch (this) {
            case MYSQL:
                return "com.mysql.cj.jdbc.Driver";
            case ORACLE:
                return "oracle.jdbc.driver.OracleDriver";
            case SQLSERVER:
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            default:
                return "unknown database!";
        }
    }

    public String getUrl() {
        switch (this) {
            case MYSQL:
                return "jdbc:mysql://localhost:3306/demo_mybatis?&useSSL=true&serverTimezone=UTC&useEncoding=true&characterEncoding=UTF-8";
            case ORACLE:
                return "jdbc:oracle:thin:@localhost:1512:dbname";
            case SQLSERVER:
                return "jdbc:sqlserver://localhost:1433;databaseName=";
            default:
                return "unknown database!";
        }
    }
}
