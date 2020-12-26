package com.conquer_java.design_pattern.factory.abstract_factory;

public class HuaweiMP3 implements IMP3 {
    @Override
    public void startup() {
        System.out.println("华为MP3——开机");
    }

    @Override
    public void palyMusic() {
        System.out.println("华为MP3——音乐");
    }

    @Override
    public void shutdown() {
        System.out.println("华为MP3——关机");
    }
}
