package com.conquer_java.util.demo_jdbc;

import java.sql.*;

/**
 * 关系型数据库的SQL(Structured Query Language)语句包括如下几种：
 * DDL: Data Definition Language，用于创建、删除、修改数据库中的对象结构(数据库DATABASE、表TABLE、用户USER、索引INDEX、视图VIEW、存储过程PROCEDURE、触发器TRIGGER)，关键字包括：CREATE, ALTER, DROP, TRUNCATE
 * DQL: Data Query Language，用于操作数据库表中的具体数据，只包括查询操作(读)，关键字包括：SELECT, WHERE, HAVING, LIMIT, GROUP-BY, ORDER-BY。
 * DML: Data Manipulation Language，用于操作数据库表中的具体数据，只包括更新操作(写)，关键字包括：INSERT, UPDATE, DELETE。
 * DCL: Data Control Language，用于控制用户在数据库上的操作权限，控制数据库操纵事务发生的时间以及效果，对于数据库实行监视，关键字包括：授权GRANT，收回REVOKE。
 * TPL: Transaction Process Language，关键字包括：提交COMMIT、回滚ROLLBACK，还原点SAVEPOINT。
 * CCL: 用于一个或者多个数据库表的单行操作，关键字包括：DECLARE CURSOR，FETCH INTO和UPDATE WHERE CURRENT。
 *
 * 事务的四大特点ACID：
 * 原子性(Atomicity)
 * 一致性(Consistency)
 * 隔离性(Isolation)
 * 持久性(Durability)
 *
 * 提交的三种类型：
 * 显式提交(Explicitly Commit): COMMIT
 * 隐式提交(Implicitly Commit): ALTER, AUDIT, COMMENT, CONNECT, CREATE, DISCONNECT, DROP, EXIT, GRANT, NOAUDIT, QUIT, REVOKE, RENAME.
 * 自动提交(Automatically Commit): SQL>SET AUTOCOMMIT ON;
 *
 * https://blog.csdn.net/u010511598/article/details/89326265 参考
 */
public class DemoJDBC {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            // 反射加载类的三种方法：
            // 1) Class clazz = Class.forName(${fullname});
            // 2) Class clazz = ${className}.class;
            // 3) Class clazz = ${objectName}.getClass();
            // Object o = clazz.newInstance();

            // Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 使用DriverManager类registerDriver注册驱动
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            // 创建对象
            //new com.mysql.jdbc.Driver();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo_jdbc?useSSL=false&characterEncoding=utf-8", "admin", "12345");
            pstat = conn.prepareStatement("SELECT * FROM student WHERE name = ?");
            pstat.setObject(1, "Alexander Wen");
            rs = pstat.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String sex = rs.getString("sex");
                System.out.println("学号：" + id + "-姓名：" + name + "-年龄：" + age + "-性别：" + sex);
            }
            pstat = conn.prepareStatement("UPDATE student SET sex = '女' WHERE name = 'ktpd00wen99'");
            int count = pstat.executeUpdate();
            System.out.println(count + " rows in set (0.00 sec)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstat != null) {
                try {
                    pstat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
