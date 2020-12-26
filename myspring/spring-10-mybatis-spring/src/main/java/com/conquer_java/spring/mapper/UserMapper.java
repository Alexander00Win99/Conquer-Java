package com.conquer_java.spring.mapper;

import com.conquer_java.spring.pojo.User;
import java.util.List;

public interface UserMapper {
    public List<User> selectAll();
    public User selectOne(int id);
}

