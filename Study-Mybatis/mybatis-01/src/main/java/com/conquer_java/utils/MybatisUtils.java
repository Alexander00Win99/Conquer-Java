package com.conquer_java.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    // Step01：获取SqlSessionFactory对象，用来生成SqlSession对象。
    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Step02：获取SqlSession对象
    // 类似JDBC的DriverManager加载的Connection对象，可以用来执行SQL语句。
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
