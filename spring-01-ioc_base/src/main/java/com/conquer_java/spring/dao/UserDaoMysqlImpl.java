package com.conquer_java.spring.dao;

public class UserDaoMysqlImpl implements UserDao {
    public void getUser() {
        System.out.println("查询MySQL数据库获取用户信息！");
    }
}
