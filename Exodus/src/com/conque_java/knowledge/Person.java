package com.conque_java.knowledge;

public class Person {
    private String name;
    // 对于基本类型int == null ==> 出错，对于基本的包装类Integer可以赋值null，不会报错。
    // 八个基本类型之中，除了boolean|char以外，均可通过new PrimitiveType(String)方式生成对象，因此支持Scanner(System.in)或者File里面String输入获取对象输出。
    //private int age;
    private Integer age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Person() {}

    public Person(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}