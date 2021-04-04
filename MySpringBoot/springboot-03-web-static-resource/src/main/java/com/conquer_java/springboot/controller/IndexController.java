package com.conquer_java.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller // 代表能够页面跳转，无需@RestController
public class IndexController {
    @RequestMapping("/demo-thymeleaf")
    public String index(){
        return "index";
    }

    @RequestMapping("/exercise-thymeleaf")
    public String index(Model model){
        model.addAttribute("msg", "<h3>Hello Thymeleaf! - 包裹标签能否转义？</h3>");
        model.addAttribute("usernames", Arrays.asList("Alexander Wen", "Alex 温", "Alexander00Win99", "Alex00Wen99"));
        return "index";
    }
}
