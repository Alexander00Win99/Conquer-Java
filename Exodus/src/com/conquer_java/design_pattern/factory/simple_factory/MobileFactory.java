package com.conquer_java.design_pattern.factory.simple_factory;

public class MobileFactory {
    public static Mobile produceMobile(String brand) {
        switch (brand) {
            case "Mi":
                return new MiMobile();
            case "Huawei":
                return new HuaweiMobile();
            default:
                return null;
        }
    }

    public static Mobile produceMiMobile() {
        return new MiMobile();
    }

    public static Mobile produceHuaweiMobile() {
        return new HuaweiMobile();
    }
}
