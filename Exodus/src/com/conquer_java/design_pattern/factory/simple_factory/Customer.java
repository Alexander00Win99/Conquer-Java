package com.conquer_java.design_pattern.factory.simple_factory;

public class Customer {
    public static void main(String[] args) {
        System.out.println("++++++++++++++++Begin new创建对象++++++++++++++++");
        /**
         * 模式说明：用户购买手机，需要知晓如何制造手机 —— 知道Interface|AbstractClass规范 + 知道具体实现类-构造参数+构造过程
         */
        new MiMobile();
        new HuaweiMobile();
        System.out.println("----------------End new创建对象----------------");

        System.out.println("++++++++++++++++Begin 简单工厂创建对象++++++++++++++++");
        /**
         * 模式说明：
         * 简单工厂模式 又称 “静态工厂模式” —— 所有方法均为静态。用户只需提供一个品牌参数，工厂即可生成对应品牌的手机。
         *
         * 适用场景：
         * 工厂类负责的产品对象较少，创建业务逻辑不会太过复杂。
         *
         * 特点：
         * 通过接受不同的参数，返回不同的对象实例。
         *
         * 缺点：
         * 1) 如果工厂需要增加生成新的产品，必须修改工厂方法的业务逻辑，或者增加静态方法，破坏“开闭原则”。
         * 2) 生成产品的静态方法，无法继承和重写，因此工厂角色无法形成基于继承的等级体系结构。
         */
        MobileFactory.produceMobile("Mi");
        MobileFactory.produceMobile("Huawei");
        System.out.println("----------------End 简单工厂创建对象----------------");
    }
}
