package com.conquer_java.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping({"/", "/index.htm", "/index.html"})
    //@ResponseBody // 仅仅作为正文字符串，无法跳转主页
    public String index() {
        return "index";
    }

    @RequestMapping({"/login-verification.htm", "/login-verification.html", "login-demo.htm", "login-demo.html"})
    public String login() {
        return "login-verification";
    }
}
