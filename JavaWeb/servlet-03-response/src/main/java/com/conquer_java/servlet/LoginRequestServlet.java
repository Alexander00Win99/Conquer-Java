package com.conquer_java.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("/login请求能够进入当前Servlet！");
        System.out.println("ContextPath: " + this.getServletContext().getContextPath());
        System.out.println("username: " + req.getParameter("username"));
        System.out.println("password: " + req.getParameter("password"));
        resp.sendRedirect("/servlet-03-response-war/success.jsp");
        // 严重注意：如下导致重定向死循环——LoginServlet转向LoginServlet，切记避免。
        //resp.sendRedirect("/servlet-03-response-war/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
