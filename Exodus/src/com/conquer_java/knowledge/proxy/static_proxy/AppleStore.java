package com.conquer_java.knowledge.proxy.static_proxy;

public class AppleStore implements PhoneStore {
    private iPhoneFactory factory = new iPhoneFactory();

    @Override
    public Phone sellPhone() {
        return factory.produceMobile();
    }
}
