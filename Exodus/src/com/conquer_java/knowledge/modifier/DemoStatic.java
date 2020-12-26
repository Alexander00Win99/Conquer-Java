package com.conquer_java.knowledge.modifier;

import java.util.Date;

public class DemoStatic {
    Person father = new Person(new Date(2000 - 1900, 1 - 1, 1, 0, 0, 0));

    {
        System.out.println("This is normal code block-01 of 父类 - DemoStatic!");
    }

    public DemoStatic() {
        System.out.println("This is constructor of 父类 - DemoStatic!");
    }

    public static void main(String[] args) {
        new SubStatic();
    }

    static {
        System.out.println("This is static code block of 父类 - DemoStatic!");
    }

    {
        System.out.println("This is normal code block-02 of 父类 - DemoStatic!");
    }
}

class SubStatic extends DemoStatic {
    Person son = new Person(new Date(2020 - 1900, 1 - 1, 1, 0, 0, 0));

    {
        System.out.println("This is normal code block-01 of 子类 - SubStatic!");
    }

    public SubStatic() {
        System.out.println("This is constructor of 子类 - SubStatic!");
    }

    static {
        System.out.println("This is static code block of 子类 - SubStatic!");
    }

    {
        System.out.println("This is normal code block-02 of 子类 - SubStatic!");
    }
}

class Person {
    private static Date startDate, endDate;
    private Date birthDate;

    static {
        startDate = new Date(1946 - 1900, 1 - 1, 1, 0, 0, 0);
        endDate = new Date(1964 - 1900, 12 - 1, 31, 23, 59, 59);
        System.out.println("婴儿潮开始日期：" + startDate);
        System.out.println("婴儿潮结束日期：" + endDate);
    }

    public Person(Date birthDate) {
        this.birthDate = birthDate;
        System.out.println("This person is born on: " + birthDate);
    }

    public boolean isBornBoomer() {
        return birthDate.compareTo(startDate) >= 0 && birthDate.compareTo(endDate) < 0;
    }

    static {
        System.out.println("This is static code block of Person! It just can be excuted only once!");
    }
}