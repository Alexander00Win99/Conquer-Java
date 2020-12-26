package com.conquer_java.spring.dao;

import com.conquer_java.spring.entity.User;

import java.util.List;

public interface UserMapper {
    public List<User> selectAll();
}
