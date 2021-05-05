package com.conquer_java.springboot;

import org.apache.log4j.LogManager;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;



public class TestMain {
    //private static final Logger logger = LoggerFactory.getLogger(TestMain.class);
    private static final org.apache.log4j.Logger logger = LogManager.getLogger(TestMain.class);

    public static void main(String[] args) {
        System.out.println("Hello Selenium!");
        System.out.println(logger.getClass());
    }
}
