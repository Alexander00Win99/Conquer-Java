package com.conquer_java.spring.diy;

public class MyAspect {
    public void before() {
        System.out.println("================方法执行之前================");
    }

    public void after() {
        System.out.println("================方法执行之后================");
    }
}
