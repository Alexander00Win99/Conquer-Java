package com.conquer_java.design_pattern.factory.abstract_factory;

public class Client {
    public static void main(String[] args) {
        IProductFactory factory = null;
        IMobile mobile = null;
        IMP3 mp3 = null;

        System.out.println("++++++++++++++++Begin 抽象工厂创建对象++++++++++++++++");
        factory = new MiFactory();
        mobile = factory.produceMobile();
        mobile.playGame();
        mp3 = factory.produceMP3();
        mp3.palyMusic();

        factory = new HuaweiFactory();
        mobile = factory.produceMobile();
        mobile.playGame();
        mp3 = factory.produceMP3();
        mp3.palyMusic();
        System.out.println("----------------End 抽象工厂创建对象----------------");
    }
}
