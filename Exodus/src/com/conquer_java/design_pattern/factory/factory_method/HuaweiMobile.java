package com.conquer_java.design_pattern.factory.factory_method;

public class HuaweiMobile implements Mobile {
    public HuaweiMobile() {
        System.out.println("华为手机生成完成！");
    }

    public String brand() {
        return "华为";
    }
}