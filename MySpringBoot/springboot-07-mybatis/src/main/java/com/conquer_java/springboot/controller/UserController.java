package com.conquer_java.springboot.controller;

import com.conquer_java.springboot.pojo.User;
import com.conquer_java.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;

    // http://localhost:8080/userList
    @GetMapping("/userList")
    public List<User> listAll() {
        return userServiceImpl.listAll();
    }

    // http://localhost:8080/user/8888
    @GetMapping("/user/{id}")
    public User queryUser(@PathVariable("id") int id) {
        return userServiceImpl.queryUserById(id);
    }

    // http://localhost:8080/addUser?id=9999&name=alex&password=123456&date=2020-02-02+20:20:20
    @GetMapping("/addUser")
    public int addUser(@RequestParam("id") int id,
                       @RequestParam("name") String name,
                       @RequestParam("password") String password,
                       @RequestParam("date") Timestamp datetime) {
        User user = new User(id, name, password, datetime);
        return userServiceImpl.addUser(user);
    }

    // http://localhost:8080/modifyUser?id=9999&name=alexander00win99&password=123456&date=2020-02-02%2020:20:20
    @GetMapping("/modifyUser")
    public int modifyUser(@RequestParam("id") int id,
                          @RequestParam("name") String name,
                          @RequestParam("password") String password,
                          @RequestParam("date") Timestamp datetime) {
        User user = new User(id, name, password, datetime);
        return userServiceImpl.modifyUser(user);
    }

    // http://localhost:8080/deleteUser?id=9999
    @GetMapping("/deleteUser")
    public int deleteUser(@RequestParam("id") int id) {
        return userServiceImpl.deleteUser(id);
    }
}
