package com.conquer_java.design_pattern.factory.abstract_factory;

public class HuaweiMobile implements IMobile {
    @Override
    public void startup() {
        System.out.println("华为手机——开机");
    }

    @Override
    public void makeCall() {
        System.out.println("华为手机——电话");
    }

    @Override
    public void sendMessage() {
        System.out.println("华为手机——短信");
    }

    @Override
    public void takePhoto() {
        System.out.println("华为手机——拍照");
    }

    @Override
    public void playGame() {
        System.out.println("华为手机——游戏");
    }

    @Override
    public void shutdown() {
        System.out.println("华为手机——关机");
    }
}
