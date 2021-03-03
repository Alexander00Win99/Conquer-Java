package com.conquer_java.springboot.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Dog extends Pet {
    @Value("Sunny")
    private String name;
    @Value("36")
    private int age;

    public Dog() {}

    public Dog(String name, int age) {
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
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", name='" + super.name + '\'' +
                ", age=" + super.age +
                '}';
    }

    @Override
    public void skill() {
        super.skill();
        System.out.println("狗会看家！");
    }
}
