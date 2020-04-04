package com.conque_java.knowledge.proxy.static_proxy;

/**
 * 【静态代理】——代理类：只能代理生产同类产品
 * 利用interface接口统一规则，对于用户完全屏蔽代理类和被代理类之间的行为差距，使得用户无法感知代理类对于被代理类的功能实现的逻辑变更。
 */
public class DemoStaticProxy {
    public static void main(String[] args) {
//        PhoneStore store = new AppleStore();
//        Phone phone = store.sellPhone();
        PhoneStore store = new ProxyStore();
        Phone phone = store.sellPhone();

        phone.makeCall();
        phone.playGame();
        phone.takePhoto();
    }
}
