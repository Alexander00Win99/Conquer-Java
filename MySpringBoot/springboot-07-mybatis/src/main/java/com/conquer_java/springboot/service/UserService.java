package com.conquer_java.springboot.service;

import com.conquer_java.springboot.pojo.User;

import java.util.List;

public interface UserService {
    List<User> listAll();
    User queryUserById(int id);
    int addUser(User user);
    int modifyUser(User user);
    int deleteUser(int id);
}
