package com.conquer_java.servlet;

import com.conquer_java.pojo.Person;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class SessionServlet01 extends HttpServlet {
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
        System.out.println("进入SessionServlet01！当前session：" + id);
        // 绑定属性1 - String
        session.setAttribute("name", "Alexander00Win99");
        // 绑定属性2 - Person
        session.setAttribute("person", new Person("Alexander00Win99", 18));

        if (session.isNew()) {
            out.write("Session创建成功，当前Session-ID：" + id);
        } else {
            out.write("服务器现有Session-ID：" + id);
        }

        // 本质上说，Session在打开浏览器发送首条请求时已经自动创建，相当于如下固定名称“JSESSIONID”的Cookie。
        //Cookie cookie = new Cookie("JSESSIONID", "xxxx-xxxx-xxxx-xxxx");
        //resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
