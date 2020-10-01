package com.conquer_java.knowledge.usage4transient;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionID = 12345678L;
    private String name;
    private transient int age;
    private static transient String sex;

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

    public static String getSex() {
        return sex;
    }

    public static void setSex(String sex) {
        Person.sex = sex;
    }

//    @Override
//    public String toString() {
//        return "Person{" +
//                "name='" + name + '\'' +
//                ", age=" + age +
//                ", sex='" + sex + '\'' +
//                '}';
//    }

    // static属性sex，JDK的toString()自动无视
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
