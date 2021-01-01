package com.conquer_java.spring.dao;

public class UserDaoSqlserverImpl implements UserDao {
    @Override
    public void getUser() {
        System.out.println("查询SQL Server数据库获取用户信息！");
    }
}
