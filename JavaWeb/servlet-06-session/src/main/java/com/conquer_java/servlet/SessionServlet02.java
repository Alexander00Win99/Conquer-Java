package com.conquer_java.servlet;

import com.conquer_java.pojo.Person;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionServlet02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter(); // 常用out，看齐JSP
        // 通过Request获取Session
        HttpSession session = req.getSession();
        // 获取SessionID
        String id = session.getId();
        System.out.println("进入SessionServlet02！当前session：" + id);
        // 提取属性1 - String
        String name = (String) session.getAttribute("name");
        // 提取属性2 - String
        Person person = (Person) session.getAttribute("person");

        out.write("服务器现有Session存储信息1为：" + name + "\r\n");
        out.write("服务器现有Session存储信息2为：" + person.toString() + "\r\n");

        // 本质上说，Session在打开浏览器发送首条请求时已经自动创建，相当于如下固定名称“JSESSIONID”的Cookie。
        //Cookie cookie = new Cookie("JSESSIONID", "xxxx-xxxx-xxxx-xxxx");
        //resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
