package com.conquer_java.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MybatisUtil {
    private static SqlSessionFactory sqlSessionFactory;

    // Step01：获取SqlSessionFactory对象，用来生成SqlSession对象。
    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            // 如果"SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);"，则会导致空指针异常错误，因为此处sqlSessionFactory属于代码块局部变量，而非对象属性。
            //SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Step02：获取SqlSession对象
    // 类似JDBC的DriverManager加载的Connection对象，可以用来执行SQL语句。
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    public final static java.sql.Date string2Date(String dateString) throws ParseException {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
        dateFormat.setLenient(false);
        java.util.Date date = dateFormat.parse(dateString); // java类Date
        java.sql.Date datetime = new java.sql.Date(date.getTime()); // mysql类Date
        return datetime;
    }

    public final static java.sql.Timestamp string2Time(String timeString) throws ParseException {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.SSS", Locale.ENGLISH);
        dateFormat.setLenient(false);
        java.util.Date time = dateFormat.parse(timeString); // java类Date
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time.getTime()); // mysql类Timestamp
        return timestamp;
    }
}
