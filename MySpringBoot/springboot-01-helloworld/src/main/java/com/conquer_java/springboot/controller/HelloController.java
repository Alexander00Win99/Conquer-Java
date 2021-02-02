package com.conquer_java.springboot.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class HelloController {
    // 接口：“http://localhost:8080/hello”
    // 简化成为：只需书写前端接口Controller类
    // 自动装配：无需配置DispatchServlet、SpringBoot等
    @GetMapping("/hello*")
    @ResponseBody
    public String hello() {
        return "Hello World! - Spring Boot!";
    }
}
