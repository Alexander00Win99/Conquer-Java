package com.conquer_java.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 正常置于Filter处理
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        System.out.println("++++++++++++++++Begin——打印相关信息++++++++++++++++");
        System.out.println(req.getAuthType());
        System.out.println(req.getContextPath());
        System.out.println(req.getPathInfo());
        req.getParameter("username");
        System.out.println(req.getParameter("password"));
        System.out.println(Arrays.toString(req.getParameterValues("hobbies")));
        System.out.println("----------------End——打印相关信息----------------");

        // 前端请求需要指明具体应用路径action="${pageContext.request.contextPath}/login"，
        // 后端处理“/”代表当前项目具体应用，一定不能重复添加，否则404 Not Found。
        req.getRequestDispatcher("/success.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
