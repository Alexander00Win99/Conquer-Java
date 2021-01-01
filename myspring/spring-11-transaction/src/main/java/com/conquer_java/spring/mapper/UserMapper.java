package com.conquer_java.spring.mapper;

import com.conquer_java.spring.pojo.User;

import java.util.List;

public interface UserMapper {
    public int createUser(User user);
    public int deleteUser(int id);
    public List<User> selectAll();
    public User selectOne(int id);
    // 用来验证事务，包含三块功能：1)增加用户；2)删除用户；3)显示所有用户；
    // 事务要求：要么全部成功；要么全部失败；要么全部执行；要么全不执行；
    public List<User> showUserInGroup(User user);
}

