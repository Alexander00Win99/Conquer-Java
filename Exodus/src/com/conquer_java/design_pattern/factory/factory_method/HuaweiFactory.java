package com.conquer_java.design_pattern.factory.factory_method;

public class HuaweiFactory implements MobileFactory {
    @Override
    public Mobile produceMobile() {
        return new HuaweiMobile();
    }
}
