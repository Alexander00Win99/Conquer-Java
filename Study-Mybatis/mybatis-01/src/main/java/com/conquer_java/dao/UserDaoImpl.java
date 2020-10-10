package com.conquer_java.dao;

import com.conquer_java.pojo.EnumDB;
import com.conquer_java.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 1、加载驱动类：Class.forName()
 * 2、获取数据库连接：DriverManager.getConnection()
 * 3、创建SQL语句执行句柄：Connection.createStatement()
 * 4、执行SQL语句：Statement.executeUpdate()
 * 5、释放数据库连接资源：finally -> Connection.close()
 *
 * 注1：MySQL的时间类型 VS Java的时间类型 VS ResultSet方法
 * Time                 java.sql.Time       ResultSet.getTime
 * Year/Date            java.sql.Date       ResultSet.getDate
 * Datetime/Timestamp   java.sql.Timestamp  ResultSet.getTimestamp
 * Datetime所能存储的时间范围为：'1000-01-01 00:00:00.000000' 到 '9999-12-31 23:59:59.999999'。
 * Timestamp所能存储的时间范围为：'1970-01-01 00:00:01.000000' 到 '2038-01-19 03:14:07.999999'。
 *
 * 注2：一般使用PreparedStatement代替Statement，前者相对后者具有如下优点：
 * 1) 提高可读性；
 *      statement.executeUpdate("INSERT INTO demo_mybatis (col1, col2, col3, col4) VALUES ('" + var1 + "', '" + var2 + "', '" + var3 + "', '" + var4 + "')");
 * 2) 提高执行效率，a) 预编译SQL语句编译以后的执行代码位于DB缓存，可被重复调用；b) addBatch()方法具有批处理功能；
 *      SQL必须完全一致，才能保证缓存命中，如下两条语句数据不同，复用失败：
 *      INSERT INTO tablename (col1, col2) VALUES ('name', 'password');
 *      INSERT INTO tablename (col1, col2) VALUES ('name', 'passwd');
 *
 *      PreparedStatement pStatement = connection.prepareStatement("INSERT INTO tablename VALUES (?, ?, ?, ?)");
 *      pStatement.setInt(1, 1);
 *      pStatement.setString(2, "Alex");
 *      pStatement.addBatch();
 *      pStatement.setInt(1, 2);
 *      pStatement.setString(2, "Alexander");
 *      pStatement.addBatch();
 *      int[] counts = pStatement.executeBatch();
 *      connection.commit();
 *
 * 3) 提高安全性，避免SQL注入；
 *      String sql = "SELECT * from tablename WHERE name= '" + varname + "' and password = '" + varpassword + "'";
 *      如果输入${varpassword} = '' or '0' = '0'，那么即可规避密码验证，因为'0' = '0'永远成立；
 *      甚至输入${varpassword} = ''; drop table tablename，那么可能直接导致删表；
 *      根本原因在于，Statement需要对拼接字符串中的关键字进行费尽心机的过滤判断。
 *
 * 注3：execute和executeQuery、executeUpdate的区别
 * 1) execute可以执行任何语句，返回值是boolean类型，表示执行结果是否返回ResultSet，是则为true，否则为false：
 * boolean hasResultSet = statement.execute(sql);
 * if (hasResultSet) {
 *     ResultSetMetaData metaData = rs.getMetaData();
 *     int columns = metaData.getColumnCount();
 *     ResultSet rs = statement.getResultSet();
 *     while (rs.next()) {
 *         for (int i = 0; i < columns; i++) {
 *             System.out.print(rs.getString(i + 1) + /t);
 *             System.out.println();
 *         }
 *     }
 * } else {
 *     System.out.print("执行此条SQL语句，影响数据记录行数：" + statement.getUpdateCount());
 * }
 * 2) executeQuery只能执行查询语句，执行结果返回单个ResultSet；
 * 3) executeUpdate可以执行查询以外的DML（INSERT、UPDATE、DELETE：返回受影响行记录条数）和DDL（CREATE TABLE、DROP TABLECREATE TABLE、ALTER TABLE：返回值永远为0）；
 * 4) executeBatch a) 返回值>0，表示成功执行，返回影响记录行数；b) SUCCESS_NO_INFO，表示成功执行，但是影响行数未知；c) EXECUTE_FAILED，表示没能成功执行；
 * 注意：当使用addBatch()缓存数据时，需要在循环中设置条件，当循环达到达指定次数后执行executeBatch()，将缓存中的sql语句全部发给数据库，然后执行clearBatch()清楚缓存，否则数据过大容易导致OutOfMemory(内存不足)。
 * Oracle环境，常量取值如下：
 * 使用PreparedStatement的executeBatch方法，如果DML操作成功，返回值[-2,-2,...]
 * an array of update counts containing one element for each command in the batch.
 * int java.sql.Statement.SUCCESS_NO_INFO = -2 [0xfffffffe]
 * The constant indicating that a batch statement executed successfully but that no count of the number of rows it affected is available.
 * int java.sql.Statement.EXECUTE_FAILED = -3 [0xfffffffd]
 * The constant indicating that an error occured while executing a batch statement.
 */
public class UserDaoImpl implements UserDao {
    private EnumDB enumDB = EnumDB.MYSQL;
    private String driver = enumDB.getDriver();
    private String url = enumDB.getUrl();

    private Connection getConnection() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println(enumDB.name() + "驱动无法加载！");
            e.printStackTrace();
        }

        //String url = "jdbc:mysql://localhost:3306/demo_mybatis?&useSSL=true&serverTimezone=UTC&useEncoding=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "123456";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<User> getUserList() {
        List<User> result = new ArrayList();
        Connection connection = getConnection();

        String sql = "SELECT * FROM demo_mybatis.user";
        ResultSet resultSet = null;

        try {
            // 开启事务（关闭自动提交）
            connection.setAutoCommit(false);
            // 创建SQL语句执行句柄
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                Timestamp timestamp = resultSet.getTimestamp(4);
                System.out.println(id + "--" + name + "--" + password  + "--" + timestamp);
                User user = new User(id, name, password);
                user.setTimestamp(timestamp);
                result.add(user);
            }

            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            System.out.println("数据库连接异常，请检查！");
            e.printStackTrace();
            // 若连接异常则回滚事务
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            // 关闭数据库连接
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public List<User> getUserById(int userID) {
        List<User> result = new ArrayList();
        Connection connection = getConnection();

        String sql = "SELECT * FROM demo_mybatis.user WHERE id >= ?";
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, userID);
            resultSet = pStatement.executeQuery();
            //System.out.println("resultSet.getFetchSize(): " + resultSet.getFetchSize());

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                Timestamp timestamp = resultSet.getTimestamp(4);
                System.out.println(id + "--" + name + "--" + password + "--" + timestamp);
                user = new User(id, name, password);
                user.setTimestamp(timestamp);
                result.add(user);
            }

            if (pStatement != null) pStatement.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public int createUser(User user) {
        Connection connection = getConnection();
        String sql = "INSERT INTO demo_mybatis.user (id, name, password, datetime) VALUES (?, ?, ?, ?)";
        int[] counts;
        int result = 0;

        try {
            // 开启事务（关闭自动提交）
            connection.setAutoCommit(false);
            // 创建SQL语句执行句柄
            PreparedStatement pStatement = connection.prepareStatement(sql);

            pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, user.getId());
            pStatement.setString(2, user.getName());
            pStatement.setString(3, user.getPassword());
            pStatement.setTimestamp(4, user.getTimestamp());
            pStatement.addBatch();

            counts = pStatement.executeBatch();
            connection.commit();

            result = counts.length;
            System.out.println("成功创建" + result + "个用户！");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public int updateUser() {
        Connection connection = getConnection();
        String sql = "";
        int[] counts;
        int result = 0;

        try {
            // 开启事务（关闭自动提交）
            connection.setAutoCommit(false);
            // 创建SQL语句执行句柄
            //Statement statement = connection.createStatement();
            // 修改表字段名称(名称：datetime -> timestamp，类型：timestamp)
            //sql = "ALTER TABLE demo_mybatis.user CHANGE datetime timestamp timestamp ";
            // 修改表字段名称(名称：timestamp -> datetime，类型：timestamp)
            //sql = "ALTER TABLE demo_mybatis.user CHANGE timestamp datetime timestamp ";
            //result = statement.executeUpdate(sql);
            //connection.commit();
            //System.out.println("成功修改表记录条数：" + result);

            // 修改表记录数据
            sql = "UPDATE demo_mybatis.user SET password = '999999' where password = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, "888888");
            result = pStatement.executeUpdate();
            connection.commit();
            System.out.println("成功修改表记录条数：" + result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public int deleteUserById(int lRange, int rRange) throws Exception {
        if (rRange < lRange) {
            throw new Exception("Wrong parameters, right paramerter should not less than left parameter!");
        }
        Connection connection = getConnection();
        String sql = "DELETE FROM demo_mybatis.user WHERE id >= ? AND id <= ?";
        int result = 0;

        try {
            // 开启事务（关闭自动提交）
            connection.setAutoCommit(false);
            // 创建SQL语句执行句柄
            PreparedStatement pStatement = connection.prepareStatement(sql);

            pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, lRange);
            pStatement.setInt(2, rRange);

            result = pStatement.executeUpdate();
            connection.commit();
            System.out.println("成功删除" + result + "个用户！");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
