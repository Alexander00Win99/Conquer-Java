package com.conquer_java.spring.spi.impl02;

import com.conquer_java.spring.spi.api.IParseData;

public class XmlParser implements IParseData {
    @Override
    public void parse() {
        System.out.println("XML parser");
    }

    @Override
    public String about() {
        return "Damn XML!";
    }
}
