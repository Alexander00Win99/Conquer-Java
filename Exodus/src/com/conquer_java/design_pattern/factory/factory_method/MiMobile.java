package com.conquer_java.design_pattern.factory.factory_method;

public class MiMobile implements Mobile {
    public MiMobile() {
        System.out.println("小米手机生成完成！");
    }

    public String brand() {
        return "小米";
    }
}
