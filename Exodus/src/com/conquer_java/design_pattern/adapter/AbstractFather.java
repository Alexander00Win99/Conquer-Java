package com.conquer_java.design_pattern.adapter;

public abstract class AbstractFather {
    static void congratulate() {
        System.out.println("Hello World!");
    }

    final void inprecate() {
        System.out.println("Go dead!");
    }

    abstract void abstractMethod();
}
