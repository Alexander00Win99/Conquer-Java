package com.conquer_java.spring.dao;

public class UserDaoImpl implements UserDao {
    @Override
    public void getUser() {
        System.out.println("查询本地文件获取用户信息——默认！");
    }
}
