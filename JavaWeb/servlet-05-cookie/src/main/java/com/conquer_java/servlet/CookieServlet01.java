package com.conquer_java.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class CookieServlet01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter(); // 常用out，看齐JSP
        // Cookie —— 客户端携带-服务器获取
        Cookie[] cookies = req.getCookies();

        System.out.println("进入CookieServlet01！" + cookies);

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if ("last-login-time".equals(cookie.getName())) {
                    System.out.println("存在名为last-login-time的cookie！");
                    long timestamp = Long.parseLong(cookie.getValue());
                    Date date = new Date(timestamp);
                    out.write("toString输出：" + date.toString() + "\n");
                    out.write("GMT时间：" + date.toGMTString() + "\n");
                    out.write("本地时间：" + date.toLocaleString() + "\n");
                }
            }
        } else {
            out.write("这是您第一次访问本站！");
        }


        long time = System.currentTimeMillis();
        // 服务器生成Cookie，发给客户端。
        Cookie cookie = new Cookie("last-login-time", String.valueOf(time));
        cookie.setMaxAge(24 * 60 * 60); // 秒为单位，正值设置过期时间，负值代表不存，0代表删除同名cookie。
        resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
