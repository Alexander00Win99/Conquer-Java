package com.conquer_java.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("++++++++++++++++Begin 打印在OKServlet中通过ServletContext设置的属性++++++++++++++++");
        ServletContext ctx = this.getServletContext();
        System.out.println((String)ctx.getAttribute("username"));
        System.out.println((String)ctx.getAttribute("password"));
        System.out.println("++++++++++++++++End 打印在OKServlet中通过ServletContext设置的属性++++++++++++++++");

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        out.println("Servlet02 - 阿三去死！");
        System.out.println("Servlet02 -404 Not Found! Are you a gay?");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
