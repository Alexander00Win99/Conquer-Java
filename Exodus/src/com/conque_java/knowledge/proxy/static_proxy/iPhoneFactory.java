package com.conque_java.knowledge.proxy.static_proxy;

public class iPhoneFactory {
    iPhone produceMobile() {
        return new iPhone(iPhone.GENERATION_X, iPhone.Color.GOLDEN);
    }
}
