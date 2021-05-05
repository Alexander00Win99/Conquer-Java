package com.conquer_java.spring.spi.impl01;

import com.conquer_java.spring.spi.api.IParseData;

public class JsonParser implements IParseData {
    @Override
    public void parse() {
        System.out.println("JSON parser");
    }

    @Override
    public String about() {
        return "Hello JSON!";
    }
}
