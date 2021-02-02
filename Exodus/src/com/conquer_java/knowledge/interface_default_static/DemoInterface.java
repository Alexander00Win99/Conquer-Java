package com.conquer_java.knowledge.interface_default_static;

public class DemoInterface extends Parent implements IntfA, IntfB {
    public static void main(String[] args) {
        DemoInterface demoInterface = new DemoInterface();
        IntfA.staticFuncA();
        IntfB.staticFuncB();
        demoInterface.base();
        demoInterface.funcA();
        demoInterface.funcB();
        demoInterface.defaultFuncA();
        demoInterface.defaultFuncB();
        demoInterface.collisionFunc();
        demoInterface.sameFunc();
    }

    @Override
    public void funcA() {
        System.out.println("子类实现接口A方法");
    }

    @Override
    public void collisionFunc() {
        System.out.println("对于接口A和接口B同名冲突default缺省方法——collisionFunc，实现类必须单独实现！");
        System.out.println("对于接口A和接口B同名冲突default缺省方法同时存在父类继承的同名方法——sameFunc，实现类可以直接使用父类方法覆盖接口方法，无需单独实现！");
    }

    @Override
    public void funcB() {
        System.out.println("子类实现接口B方法");
    }

    @Override
    public void base() {
        System.out.println("万恶之源！");
    }
}

interface IntfA extends IntfBase {
    static void staticFuncA() {
        System.out.println("接口A的static静态方法");
    }

    void funcA();

    default void defaultFuncA() {
        System.out.println("接口A的default缺省方法");
    }

    default void collisionFunc() {
        System.out.println("接口A的接口之间发生同名冲突的缺省方法");
    }

    default void sameFunc() {
        System.out.println("接口A的父类继承发生同名冲突的缺省方法");
    }
}

interface IntfB extends IntfBase {
    static void staticFuncB() {
        System.out.println("接口B的static静态方法");
    }

    void funcB();

    default void defaultFuncB() {
        System.out.println("接口B的default缺省方法");
    }

    default void collisionFunc() {
        System.out.println("接口B的接口之间发生同名冲突的缺省方法");
    }

    default void sameFunc() {
        System.out.println("接口B的父类继承发生同名冲突的缺省方法");
    }
}

interface IntfBase {
    void base();
}

class Parent {
    public void sameFunc() {
        System.out.println("父类之中发生同名冲突的方法");
    }
}