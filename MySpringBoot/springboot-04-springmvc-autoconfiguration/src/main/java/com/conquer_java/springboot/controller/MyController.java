package com.conquer_java.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
    @RequestMapping("/index")
    //@ResponseBody // 方法返回结果直接写入HTTP的ResponseBody之中，一般用于AJAX异步获取数据。
    public String index(Model model) {
        System.out.println("Hello SpringBoot! - Extension is so easy!");
        model.addAttribute("username", "Alexander00Win99");
        return "index";
    }
}
