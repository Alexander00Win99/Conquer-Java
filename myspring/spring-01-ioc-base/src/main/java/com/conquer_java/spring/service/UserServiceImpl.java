package com.conquer_java.spring.service;

import com.conquer_java.spring.dao.*;

/**
 * 【实验目的】
 * 展示Spring IoC的本质：使用setter进行注入
 *
 * 三层架构：表示层（UI）+业务逻辑层（BLL）+数据访问层（DAL）
 * UI：表现层，用户所见所得。
 * BLL：业务逻辑层，根据原始数据抽象得出逻辑数据，封装成为API接口提供用户使用，业务逻辑层的根本目的是“将数据访问层的最为基础的存储逻辑组合起来形成一个合理的业务规则”。
 * DAL：定义针对数据的增删改查操作，关键在于控制访问粒度，需要保证功能操作的“原子性”。
 *
 * 1) Client客户端调用Service业务层；Service业务层调用DAO数据层；——业务层有且仅有一个具体实现
 * 2) 用户层“关联”数据层：如果使用new方式产生具体对象作为内部属性，那么用户需求变化，就会要求改变代码；如果使用setter注入方式向用户提供外部接口，那么用户可以自己选择所需方法。
 * 3) 接口思想：程序主动创建对象==>利用setter接口被动接受对象
 */
public class UserServiceImpl implements UserService {
    // 下面使用new产生固定对象方式存在弊病：需求改变，代码改变。
    //private UserDao userDao = new UserDaoImpl();
    //private UserDao userDao = new UserDaoMysqlImpl();
    //private UserDao userDao = new UserDaoOracleImpl();
    //private UserDao userDao = new UserDaoSqlserverImpl();

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
