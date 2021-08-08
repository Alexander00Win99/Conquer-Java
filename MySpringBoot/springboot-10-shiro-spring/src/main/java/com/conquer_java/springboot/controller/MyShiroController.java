package com.conquer_java.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MyShiroController {
    @RequestMapping({"/", "/index.htm", "/index.html", "/index.jsp", "/index.php"})
    public String toIndex(Model model) {
        model.addAttribute("msg", "Hello Shiro!");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/user/delete")
    public String delete() {
        return "user/delete";
    }

    @RequestMapping("/user/list")
    public String list() {
        return "user/list";
    }

    @RequestMapping("/user/modify")
    public String modify() {
        return "user/modify";
    }
}
