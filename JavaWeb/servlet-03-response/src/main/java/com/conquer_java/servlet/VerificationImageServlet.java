package com.conquer_java.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class VerificationImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置刷新频率
        resp.setHeader("refresh", "10");
        //  生成图形验证码对象
        BufferedImage bufferedImage = new BufferedImage(90, 25, BufferedImage.TYPE_INT_RGB);
        // 获取画笔
        Graphics2D graphics = (Graphics2D)bufferedImage.getGraphics();
        // 设置图片背景颜色
        graphics.setBackground(Color.WHITE);
        // 设置填充区域
        graphics.fillRect(0, 0, 90, 25);
        // 设置画笔颜色
        graphics.setColor(Color.CYAN);
        // 设置字体
        graphics.setFont(new Font(null, Font.BOLD, 20));
        // 在图片上描绘字符串
        graphics.drawString(generateRandomNum(8), 0, 20);
        // 通知浏览器/客户端该个请求响应按照图片方式打开
        resp.setContentType("image/png");
        // 禁止浏览器缓存
        resp.setDateHeader("Expires", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        // 图片信息
        ImageIO.write(bufferedImage, "png", resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    // 生成总长不超过10的随机数字
    private String generateRandomNum(int length) {
        length = (length <= 10) ? length : 10;
        Random random = new Random();
        String str = String.valueOf(random.nextInt(Integer.MAX_VALUE));
        if (str.length() >= length) return str.substring(0, length);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length - str.length(); i++) {
             sb.append("0");
        }
        sb.append(str);
        return sb.toString();
    }
}
