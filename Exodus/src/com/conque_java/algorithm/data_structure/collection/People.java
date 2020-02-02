package com.conque_java.algorithm.data_structure.collection;

import java.util.Objects;

public class People {
    private String name;
    private int age;
    private boolean sex;

    public People(String name) {
        this.name = name;
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

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        People people = (People) o;
        return age == people.age &&
                sex == people.sex &&
                name.equals(people.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, sex);
        //return this.name.hashCode();
    }

// 手写equals方法
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o instanceof People) {
//            People anotherPeople = (People) o;
//            if (this.name.equals(anotherPeople.name) && this.age == anotherPeople.age && this.sex == anotherPeople.sex)
//                return true;
//        }
//        return false;
//    }
}
