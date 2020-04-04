package com.conque_java.knowledge.proxy.dynamic_proxy;

import com.conque_java.knowledge.proxy.static_proxy.AppleStore;
import com.conque_java.knowledge.proxy.static_proxy.Phone;
import com.conque_java.knowledge.proxy.static_proxy.PhoneStore;

public class DemoDynamicProxy {
    public static void main(String[] args) {
        PhoneStore store = ProxyFactory.produceProxy(IAppleStore.class);
        Phone phone = store.sellPhone();

        phone.makeCall();
        phone.takePhoto();
        phone.playGame();
    }
}
