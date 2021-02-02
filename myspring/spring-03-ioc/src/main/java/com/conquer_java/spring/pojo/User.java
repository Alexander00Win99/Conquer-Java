package com.conquer_java.spring.pojo;

public class User {
    private String name;

    public User() {
        System.out.println("无参构造器");
    }

    public User(String name) {
        this.name = name;
        System.out.println("有参构造器");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void showName() {
        System.out.println("name: " +  name);
    }
}
