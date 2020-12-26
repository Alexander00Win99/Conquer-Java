package com.conquer_java.servlet;

import com.conquer_java.pojo.Person;
import org.apache.jasper.runtime.HttpJspBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.HttpJspPage;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionServlet03 extends HttpServlet {
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
        System.out.println("进入SessionServlet03！当前session：" + id);
        // 删除属性1 - String
        session.removeAttribute("name");
        // 提取属性1 - String
        String name = (String) session.getAttribute("name");
        // 提取属性2 - String
        Person person = (Person) session.getAttribute("person");

        out.write("服务器现有Session存储信息1为：" + name + "\r\n");
        out.write("服务器现有Session存储信息2为：" + person.toString() + "\r\n");
        // 手动注销Session —— 一旦注销，立即重生！<==>关闭浏览器|客户端+重新访问
        //session.invalidate();
        // 自动注销Session —— 在web.xml中通过<session-config>标签设置默认失效时间
        //HttpJspPage
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

