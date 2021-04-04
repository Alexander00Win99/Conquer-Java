package com.conquer_java.springboot.controller;

import com.fasterxml.jackson.databind.Module;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.pkcs11.Secmod;

@Controller
public class IndexController {
    @RequestMapping({"/", "/index.htm", "/index.html"})
    //@ResponseBody // 仅仅作为正文字符串，无法跳转主页
    public String index() {
        return "index";
    }

    @RequestMapping({"/login.htm", "/login.html", "demo.htm", "demo.html"})
    public String login(String username, String password, Module module) {

        return "login";
    }
}
