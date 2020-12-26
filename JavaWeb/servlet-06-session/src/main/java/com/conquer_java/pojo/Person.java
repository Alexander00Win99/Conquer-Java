package com.conquer_java.pojo;

import java.util.Objects;

public class Person {
    private String name;
    private int age;
    private Sex sex;
    private String temperament;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, Sex sex, String temperament) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.temperament = temperament;
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

    public String getSex() {
        return null != sex ? sex.getSex() : "人妖";
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getTemperament() {
        return null != this.temperament ? this.temperament : null != sex ? sex.getDefaultTemperament() : "妖里妖气";
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    @Override
    public String toString() {
        String sex = getSex();
        String temperament = getTemperament();
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", temperament='" + temperament + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                name.equals(person.name) &&
                sex == person.sex &&
                Objects.equals(temperament, person.temperament);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, sex, temperament);
    }

    private static enum Sex {
        FEMALE(0, "女"),
        MALE(1, "男"),
        THIRD_GENDER(2, "人妖");

        private int index;
        private String description;

        Sex(int index, String description) {
            this.index = index;
            this.description = description;
        }

        public String getSex() {
            return this.description;
        }

        public String getDefaultTemperament() {
            switch (this) {
                case FEMALE:
                    return "温柔似水";
                case MALE:
                    return "稳重如山";
                case THIRD_GENDER:
                    return "阴阳怪气";
                default:
                    return "人妖是人妖他妈生的";
            }
        }
    }
}
