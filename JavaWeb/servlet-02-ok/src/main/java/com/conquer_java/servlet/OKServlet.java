package com.conquer_java.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class OKServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println(this.getServletName());
        //System.out.println(this.getServletInfo());
        //System.out.println(this.getServletContext());
        //System.out.println(this.getServletConfig());
        //System.out.println(this.getInitParameter()); // <servlet>下面设置<init-para>

        System.out.println("++++++++++++++++Begin 在OKServlet中通过ServletContext设置属性传给ErrorServlet++++++++++++++++");
        String username = "Alexander00Win99";
        String password = "123456";

        ServletContext ctx = this.getServletContext();
        ctx.setAttribute("username", username);
        ctx.setAttribute("password", password);
        ctx.getInitParameter("key");
        System.out.println("当前请求URL：" + req.getRequestURI());
        System.out.println("++++++++++++++++End 在OKServlet中通过ServletContext设置属性传给ErrorServlet++++++++++++++++");

//        resp.setContentType("text/html");
//        resp.setCharacterEncoding("utf-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("Servlet02 - 印度阿三，滚蛋垃圾！");
        System.out.println("Servlet02 - Go die Indian! Are you OK?");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
