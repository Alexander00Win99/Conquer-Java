package com.conquer_java.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class FileDownloadServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Welcome to download here!");
        // 1) 获取绝对路径
        String path = "D:\\Workspace\\Java\\IdeaProjects\\JavaWeb\\servlet-03-response\\target\\servlet-03-response\\WEB-INF\\classes\\kww.bmp";
        //D:\Workspace\Java\IdeaProjects\JavaWeb\servlet-03-response\src\main\resources\kww.bmp
        //D:\Workspace\Java\IdeaProjects\JavaWeb\servlet-03-response\target\classes\kww.bmp
        //D:\Workspace\Java\IdeaProjects\JavaWeb\servlet-03-response\target\servlet-03-response\WEB-INF\classes\kww.bmp
        System.out.println("ServletRealPath: " + path);
//        path = this.getServletContext().getContextPath();
//        System.out.println("ServletContextPath: " + path);
        // 2) 获取文件名
        String filename = path.substring(path.lastIndexOf("\\") + 1);
        System.out.println("Filename: " + filename);
        // 3) 设置Header(Content-Disposition)，触发浏览器/客户端下载文件
        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        // 4) 根据文件获取下载文件的输入流
        FileInputStream fis = new FileInputStream(path);
        // 5) 创建缓冲区
        int count = 0;
        byte[] buffer = new byte[1024];
        // 6) 获取OutputStream响应流对象
        ServletOutputStream out = resp.getOutputStream();
        // 7) 输出流写入buffer缓冲区，输出流读取buffer缓冲区内容输出到浏览器/客户端
        while ((count = fis.read(buffer)) != -1) {
            System.out.println("Fuck U KWW!");
            out.write(buffer, 0, count);
        }
        // 8) 关闭资源
        fis.close();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
