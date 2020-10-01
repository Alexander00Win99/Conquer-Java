package com.conquer_java.mapper;

import com.conquer_java.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> getUserList();

    User getUserById(int id);

    int createUser(User user);

    int addUser(Map<String, Object> map);

    int updateUser(User user);

    int deleteUser(int id);
}
