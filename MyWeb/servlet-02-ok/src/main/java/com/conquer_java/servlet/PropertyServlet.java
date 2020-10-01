package com.conquer_java.servlet;

import jdk.nashorn.internal.objects.annotations.Property;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream stream = this.getServletContext().getResourceAsStream("/WEB-INF/classes/mysql.properties");
        Properties properties = new Properties();
        properties.load(stream);
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        resp.getWriter().println(username + password);
    }
}
