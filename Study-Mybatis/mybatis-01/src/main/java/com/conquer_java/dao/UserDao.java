package com.conquer_java.dao;

import com.conquer_java.pojo.User;

import java.util.List;

public interface UserDao {
    List<User>  getUserList();

    List<User> getUserById(int id);

    int createUser(User user);

    int updateUser();

    int deleteUserById(int id1, int id2) throws Exception;
}
