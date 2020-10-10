package com.conquer_java.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ForwardedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("++++++++++++++++Begin 通过ServletContext.getInitParameter()获取web.xml设置的初始化参数++++++++++++++++");
        System.out.println("打印MySQL数据库连接：");
        ServletContext ctx = this.getServletContext();
        String mysqlUrl = ctx.getInitParameter("mysql_url");
        System.out.println(mysqlUrl);
        System.out.println("++++++++++++++++End 通过ServletContext.getInitParameter()获取web.xml设置的初始化参数++++++++++++++++");

        System.out.println("进入中转睡眠时间。。。。。。");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("Servlet02 - 通过ServletContext.getInitParameter()打印初始化参数：" + mysqlUrl);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("到达中转睡眠时间。。。。。。");

        System.out.println("++++++++++++++++Begin 通过ServletContext.getRequestDispatch()获取请求转发器前转请求++++++++++++++++");
        RequestDispatcher requestDispatcher = ctx.getRequestDispatcher("/ok/forwarded");
        requestDispatcher.forward(req, resp);
        System.out.println("++++++++++++++++End 通过ServletContext.getRequestDispatch()获取请求转发器前转请求++++++++++++++++");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
