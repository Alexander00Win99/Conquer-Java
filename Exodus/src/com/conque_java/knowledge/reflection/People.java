package com.conque_java.knowledge.reflection;

import java.io.Serializable;

public class People implements Serializable {
    private String name;
    private int age;
    private boolean sex;

    public People() {}

    public People(String name, int age, boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
