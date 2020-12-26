package com.conquer_java.spring.static_proxy;

public class Realtor implements IRent {
    private Landlord landlord;

    public Realtor() {
    }

    public Realtor(Landlord landlord) {
        this.landlord = landlord;
    }

    @Override
    public void rent() {
        showHouse();
        signContract();
        charge();
        System.out.println("中介代理出租房屋！");
        landlord.rent();
    }

    public void showHouse() {
        System.out.println("中介带着客户看房！");
    }

    public void signContract() {
        System.out.println("中介主导签署居间合同！");
    }

    public void charge() {
        System.out.println("中介收中介费！");
    }
}
