package com.conquer_java.spring.service;

import com.conquer_java.spring.dao.*;

/**
 * 如下所示：程序主动创建具体业务对象 ==> 用户需求变更，导致代码变更(MySQL->Oracle->SQLServer：private  UserDao userDao = new UserDaoImpl() | UserDaoMysqlImpl() | UserDaoOracleImpl() | UserDaoSqlserverImpl();)。
 * 需要利用IOC思想，如果客户需求变更，客户自行改变调用方式。
 * 1) 接口思想：程序使用setXXX()方法注入对象，不是主动创建而是被动接收对象，如此使得客户可以自行选择具体业务对象 ==> 需求改变，代码不变。
 *
 */
public class UserServiceImpl implements UserService {
    // new方式生成对象，属性对象设定固定不变 ==> 需求改变，代码改变。
    //private UserDao userDao = new UserDaoImpl();
    //private UserDao userDao = new UserDaoMysqlImpl();
    //private UserDao userDao = new UserDaoOracleImpl();
    //private UserDao userDao = new UserDaoSqlserverImpl();

    // 利用set方法，实现属性动态注入(用户可以自主控制)
    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    // 三层架构，英文层调用DAO层
    public void getUser() {
        userDao.getUser();
    }


}
