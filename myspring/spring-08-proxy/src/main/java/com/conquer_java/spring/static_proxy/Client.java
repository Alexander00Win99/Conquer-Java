package com.conquer_java.spring.static_proxy;

public class Client {
    public static void main(String[] args) {
        Landlord landlord = new Landlord();
        landlord.rent();
        Realtor realtor = new Realtor(landlord);
        realtor.rent();
    }
}
