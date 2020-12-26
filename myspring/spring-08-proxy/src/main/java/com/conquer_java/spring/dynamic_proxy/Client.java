package com.conquer_java.spring.dynamic_proxy;

public class Client {
    public static void main(String[] args) {
        // 真实角色
        Landlord landlord = new Landlord();

        // 代理角色动态生成
        MyInvocationHandler mih = new MyInvocationHandler();
        mih.setRent(landlord);
        IRent proxy = (IRent) mih.getProxy();

        proxy.rent();
    }
}
