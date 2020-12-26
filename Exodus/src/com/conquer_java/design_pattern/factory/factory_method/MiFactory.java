package com.conquer_java.design_pattern.factory.factory_method;

public class MiFactory implements MobileFactory {
    @Override
    public Mobile produceMobile() {
        return new MiMobile();
    }
}
