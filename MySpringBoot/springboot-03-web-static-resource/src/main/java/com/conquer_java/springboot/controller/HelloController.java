package com.conquer_java.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    //@GetMapping("/springboot-03-web-static-resource/hello")
    @GetMapping({"/springboot-03-web-static-resource/hello", "/fuck U!/**"}) // request-url可以是个URL数组集合
    public String hello() {
        System.out.println("Hello World! - springboot-03-web-static-resource!");
        return "Hello World! - springboot-03-web-static-resource!";
    }
}
