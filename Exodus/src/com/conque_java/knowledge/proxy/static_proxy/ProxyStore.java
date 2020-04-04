package com.conque_java.knowledge.proxy.static_proxy;

public class ProxyStore implements PhoneStore {
    private MockPhoneFactory factory = new MockPhoneFactory();
    private iPhoneFactory proxyFactory = new iPhoneFactory();

    @Override
    public Phone sellPhone() {
        if (Math.random() < 0.5) {
            return factory.produceMobile();
        } else {
            return proxyFactory.produceMobile();
        }
    }
}
