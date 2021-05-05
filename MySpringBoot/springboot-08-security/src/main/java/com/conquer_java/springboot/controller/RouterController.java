package com.conquer_java.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

// @RestController只能返回文本，无法返回页面，不知何故？
@Controller
public class RouterController {
    // @RestController会将返回数据转为JSON格式展示，可以手动显式指定返回ModelAndView类型对象解决问题！
    @RequestMapping({"/homepage"})
    public ModelAndView homepage() {
        return new ModelAndView("index");
    }

    // 首页
    @RequestMapping({"/", "/index"})
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    // 登录页面（注销成功返回登录页面）
    @RequestMapping({"/login-page", "/custom-logout", "/usr/login-page", "/usr/custom-logout"})
    public String login() {
        return "/views/login-page";
    }

    // 注销页面
//    @RequestMapping({"/usr/logout", "/logout"})
//    public String logout() {
//        return "/views/loginpage";
//    }

    // VIP-1页面（用户拥有normal角色，只能查看/level1/**页面）
    @RequestMapping("/level1/{pageNo}")
    public String level1(@PathVariable("pageNo") int pageNo) {
        return "/views/level1/" + pageNo;
    }

    // VIP-2页面（用户拥有admin角色，只能查看/level1(2)/**页面）
    @RequestMapping("/level2/{pageNo}")
    public String level2(@PathVariable("pageNo") int pageNo) {
        return "/views/level2/" + pageNo;
    }

    // VIP-3页面（用户拥有root角色，能够查看/level1(2,3)/**页面）
    @RequestMapping("/level3/{pageNo}")
    public String level3(@PathVariable("pageNo") int pageNo) {
        return "/views/level3/" + pageNo;
    }
}
