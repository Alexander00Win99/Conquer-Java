package com.conquer_java.spring.mapper;

import com.conquer_java.spring.pojo.User;

import java.util.List;

public interface UserMapper {
    public int createUser(User user);
    public int deleteUser(int id);
    public List<User> selectAll();
    public User selectOne(int id);
}

