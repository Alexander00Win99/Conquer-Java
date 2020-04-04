package com.conque_java.knowledge.annotation;

public class Person {
    private String name;
    private String sex;
    private String age;

    @MyAnnotation(value = {"Alexander00Win99", "男", "18"})
    public Person() {};

    public Person(String name, String sex, String age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
