package com.conquer_java.springboot.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Cat extends Pet {
    @Value("Irene")
    private String name;
    @Value("36")
    private int age;

    public Cat() {}

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Cat(String name, int age, String name0, int age0) {
        super(name0, age0);
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", name='" + super.name + '\'' +
                ", age=" + super.age +
                '}';
    }

    @Override
    public void skill() {
        super.skill();
        System.out.println("猫会捉鼠！");
    }
}
