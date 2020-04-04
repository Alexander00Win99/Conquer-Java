package com.conque_java.knowledge.proxy.static_proxy;

public class MockPhone implements Phone {
    MockPhone() {
        System.out.println("这是一台山寨手机！");
    }

    @Override
    public void makeCall() {
        System.out.println("山寨手机打电话语音嘈杂过程卡顿");
    }

    @Override
    public void takePhoto() {
        System.out.println("山寨手机拍照片画面突兀色彩单调");
    }

    @Override
    public void playGame() {
        System.out.println("山寨手机打游戏场景单薄渲染简陋");
    }
}
