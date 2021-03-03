package com.conquer_java.springboot.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Pet {
    @Value("父类姓名")
    String name;
    @Value("81")
    int age;

    public Pet() {}

    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void skill() {
        System.out.println("每个宠物都有特长！");
    }
}
