package com.conquer_java.spring.pojo;

public class Address { // Level-5：5级地理区划
    private static String nation ="中国";

    private String province;

    private String city;
    private String district;
    private String street;
    private String building;

    private String prefecture;
    private String county;
    private String town;
    private String village;

    private String house;

//    public Address(String province, String city, String district, String street, String building, String house) {
//        this.province = province;
//        this.city = city;
//        this.district = district;
//        this.street = street;
//        this.building = building;
//        this.house = house;
//    }


    public Address(String province, String house, String prefecture, String county, String town, String village) {
        this.province = province;
        this.house = house;
        this.prefecture = prefecture;
        this.county = county;
        this.town = town;
        this.village = village;
    }

    @Override
    public String toString() {
        return "Address{" +
                "nation=中国" +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", prefecture='" + prefecture + '\'' +
                ", county='" + county + '\'' +
                ", town='" + town + '\'' +
                ", village='" + village + '\'' +
                ", house='" + house + '\'' +
                '}';
    }
}
