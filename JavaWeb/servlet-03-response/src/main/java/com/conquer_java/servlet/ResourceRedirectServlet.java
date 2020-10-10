package com.conquer_java.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceRedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入重定向处理程序！");
        //resp.sendRedirect("/servlet-03-response-war/verify/image");
        resp.setHeader("Location", "/servlet-03-response-war/verify/image");
        resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
