package com.conquer_java.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JdbcController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // 没有实体类，数据库表数据可以通过Map获取
    @GetMapping("/userList")
    public List<Map<String, Object>> userList() {
        String sql = "SELECT * FROM demo_mybatis.demo_employee";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }

    @GetMapping("/addUser")
    public String addUser(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("title") String title, @RequestParam("age") int age, @RequestParam("gender") String gender, @RequestParam("content") String content) {
        String sql = "INSERT INTO demo_mybatis.demo_employee (id, name, title, age, gender, content) VALUE (?, ?, ?, ?, ?, ?)";
        Object[] objects = new Object[6];
        objects[0] = id;
        objects[1] = name;
        objects[2] = title;
        objects[3] = age;
        objects[4] = gender;
        objects[5] = content;
        jdbcTemplate.update(sql, objects);
        return "Success to add user " + id + "!";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        //String sql = "DELETE FROM demo_mybatis.demo_employee WHERE id=" + id;
        //jdbcTemplate.update(sql);
        String sql = "DELETE FROM demo_mybatis.demo_employee WHERE id=?";
        jdbcTemplate.update(sql, id);
        return "Success to delete user " + id + "!";
    }

    @GetMapping("/modifyUser/{id}")
    public String modifyUser(@PathVariable("id") int id) {
        String sql = "UPDATE demo_mybatis.demo_employee SET name=?, title=?, age=?, gender=?, content=? WHERE id=" + id;
        Object[] objects = new Object[5];
        objects[0] = "温瑞枫";
        objects[1] = "CEO Assist";
        objects[2] = 36;
        objects[3] = "男";
        objects[4] = "HR Manager";
        jdbcTemplate.update(sql, objects);
        return "Success to modify user " + id + "!";
    }
}
