package com.conquer_java.knowledge.modifier;

public class DemoDefault {
    public static void main(String[] args) {
        System.out.println("接口中Default修饰的方法必须提供方法实现！");
        IMySqrt mySqrt = new IMySqrt() {
            @Override
            public double mySqrt(int n) {
                return sqrt(n * 10);
            }
        };
        System.out.println(mySqrt.mySqrt(10));
    }
}

interface IMySqrt {
    double mySqrt(int n);
    default double sqrt(int n) {
        return Math.sqrt(n);
    }
}
