package com.conquer_java.spring.dynamic_proxy;

public class Landlord implements IRent {
    @Override
    public void rent() {
        System.out.println("房东出租房屋！");
    }
}
