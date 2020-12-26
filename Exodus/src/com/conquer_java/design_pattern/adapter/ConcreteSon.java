package com.conquer_java.design_pattern.adapter;

public class ConcreteSon extends AbstractFather {
    @Override
    void abstractMethod() {
        System.out.println("抽象类中父类可以拥有static方法！");
    }

    static void congratulate() {
        System.out.println("你好，世界!");
    }

    //void inprecate() {
        //System.out.println("去死！子类不能拥有父类final同名方法，无论是否static修饰！");
    //}

    //static void inprecate() {
        //System.out.println("去死！子类不能拥有父类final同名方法，无论是否static修饰！");
    //}

    public static void main(String[] args) {
        new ConcreteSon().abstractMethod();
        AbstractFather.congratulate();
        ConcreteSon.congratulate();
    }
}
