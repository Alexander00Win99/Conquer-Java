package com.conque_java.knowledge.modifier;

public class DemoDefault {
    public static void main(String[] args) {
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
