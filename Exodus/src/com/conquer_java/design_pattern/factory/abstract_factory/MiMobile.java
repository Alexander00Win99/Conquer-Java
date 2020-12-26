package com.conquer_java.design_pattern.factory.abstract_factory;

public class MiMobile implements IMobile {
    @Override
    public void startup() {
        System.out.println("小米手机——开机");
    }

    @Override
    public void makeCall() {
        System.out.println("小米手机——电话");
    }

    @Override
    public void sendMessage() {
        System.out.println("小米手机——短信");
    }

    @Override
    public void takePhoto() {
        System.out.println("小米手机——拍照");
    }

    @Override
    public void playGame() {
        System.out.println("小米手机——游戏");
    }

    @Override
    public void shutdown() {
        System.out.println("小米手机——关机");
    }
}
