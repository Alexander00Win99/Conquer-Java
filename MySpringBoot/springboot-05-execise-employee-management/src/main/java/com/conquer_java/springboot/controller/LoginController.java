package com.conquer_java.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class LoginController {
    @RequestMapping("/user/login")
    //@ResponseBody
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        String emailRegExp = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        Pattern pattern = Pattern.compile(emailRegExp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(username);

        if (matcher.matches() && password.equals("123456")) {
            // 登录成功以后浏览器url显示：“.../user/login?username=xxx&password=xxx”
            //return "dashboard";
            session.setAttribute("loginUser", username);
            // 登录成功以后浏览器url显示：“.../main.html”
            return "redirect:/main.html";
        } else {
            model.addAttribute("msg", "账号或者密码错误，请重试！");
            return "index";
        }
    }
}
