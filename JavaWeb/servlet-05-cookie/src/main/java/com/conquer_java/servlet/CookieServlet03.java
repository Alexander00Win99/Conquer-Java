package com.conquer_java.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieServlet03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        Cookie[] cookies = req.getCookies();
        PrintWriter out = resp.getWriter();
        out.write("当前中文cookie：");
        for (Cookie cookie : cookies) {
            if ("name".equals(cookie.getName()))
                System.out.println(URLDecoder.decode(cookie.getValue(), "UTF-8"));
                out.write(URLDecoder.decode(cookie.getValue(), "UTF-8"));
        }

        Cookie cookie = new Cookie("name", URLEncoder.encode("温瑞枫", "UTF-8"));
        resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
