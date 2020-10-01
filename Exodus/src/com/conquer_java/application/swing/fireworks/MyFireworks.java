package com.conquer_java.application.swing.fireworks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 【任务目标】：熟悉swing常规用法
 *
 * 【英语词汇】：年夜饭-family reunion dinner；放爆竹-set off firecrackers；放烟花-set off fireworks；发红包-send red envelopes。
 *
 * Java GUI = AWT(Abstract Window Toolkit) + swing(JDK1.2)
 * JFrame：顶层容器
 * JPanel：中间容器
 *
 * 【画图原理】
 * paint(Graphics g)    绘制组件
 * update(Graphics g)   调用paint，刷新外观
 * repaint()            调用update，重绘外观
 *
 * Applications should not invoke <code>paint</code> directly, but should instead use the <code>repaint</code> method to schedule the component for redrawing.
 *     public void paint(Graphics g) {
 *         ......
 *     }
 *
 *     public void update(Graphics g) {
 *         paint(g);
 *     }
 *
 *     public void repaint(Rectangle r) {
 *         repaint(0,r.x,r.y,r.width,r.height);
 *     }
 *
 *     public void repaint(long tm, int x, int y, int width, int height) {
 *         RepaintManager.currentManager(SunToolkit.targetToAppContext(this))
 *                       .addDirtyRegion(this, x, y, width, height);
 *     }
 * 【功能拆解】
 * 1) 子弹(鼠标按下触发子弹，从下到上匀速上升，直至初始按下位置消失)
 * 2)
 */
public class MyFireworks {
    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
    }
}

class MyFrame extends JFrame {
    public MyFrame() {
        // 标题
        this.setTitle("烟花易冷");
        // 位置
        this.setLocation(300, 200);
        // 尺寸
        this.setSize(1000, 700);
        // 可见性
        this.setVisible(true);
        // 窗口关闭程序终止
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyPanel myPanel = new MyPanel();
        this.add(myPanel);
        this.addMouseListener(myPanel);
        myPanel.run();
    }
}

class MyPanel extends JPanel implements MouseListener {
    static ArrayList<Bullet> bullets = new ArrayList<>(10);
    static ArrayList<Fragment> fragments = new ArrayList<>(1000);
    static Map<Integer, Color> colors = new HashMap<>();
    static {
        colors.put(0, Color.RED);
        colors.put(1, Color.GREEN);
        colors.put(2, Color.BLUE);
        colors.put(3, Color.PINK);
        colors.put(4, Color.CYAN);
        colors.put(5, Color.YELLOW);
        colors.put(6, Color.ORANGE);
        colors.put(7, Color.MAGENTA);
    }

    public void paint(Graphics g) {
        super.paint(g);
        // 绘制黑色背景
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1200, 800);
        // 绘制灰色子弹
        g.setColor(Color.GRAY);
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.move();
            g.fillOval(bullet.x, bullet.y, 6, 12);
        }
        // 渲染烟花效果
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            fragment.move();
            g.setColor(colors.get(fragment.colorId));
            g.fillOval((int) fragment.x, (int) fragment.y, fragment.w, fragment.h);
        }
    }

    public void run() {
        while (true) {
            System.out.println("刷新画面！");
            this.repaint();
            // 设置刷新频率
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) { // 单击 = 按下 + 松开；如果【按下->移动位置->松开】，不会触发单击事件。

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("触发鼠标按下事件处理程序——绘制子弹");
        // 每次鼠标按下，生成一颗子弹
        Bullet bullet = new Bullet(e.getX(), 700);
        bullet.setTargetY(e.getY());
        bullets.add(bullet);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

class Bullet {
    public static final double RADIUS = 100; // 烟花绽放标称半径
    public static final double MAX_NUM = 200; // 烟花碎片标称数量
    public static Map<Integer, Double> coefficients = new HashMap<>(); // 半径长度系数
    static {
        coefficients.put(0, 1.20D);
        coefficients.put(1, 1.15D);
        coefficients.put(2, 1.10D);
        coefficients.put(3, 1.05D);
        coefficients.put(4, 1.00D);
        coefficients.put(5, 0.95D);
        coefficients.put(6, 0.90D);
        coefficients.put(7, 0.85D);
    }
    int x, y; // 烟花子弹当前坐标
    int targetX, targetY; // 烟花子弹目标坐标(爆炸位置)
    double radius; // 烟花爆炸范围半径

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        this.y -= 10; // 烟花子弹匀速向上移动(每次刷新移动10px)

        if (y <= targetY) { // 到达预定高度，子弹消失，烟花绽放，碎片四周散落
            MyPanel.bullets.remove(this);
            // 根据系数表，随机获取烟花颗数
            int num = (int) (coefficients.get(new Random().nextInt(coefficients.size())) * MAX_NUM);

            int colorId = new Random().nextInt(6);
            // 根据系数表，随机获取烟花半径
            double radius = coefficients.get(new Random().nextInt(coefficients.size())) * RADIUS;
            this.setRadius(radius);
            for (int i = 0; i < num; i++) {

                // 根据随机角度，计算各个方向的碎片运动速度
                double angle1 = new Random().nextDouble() * 2 * Math.PI;
                double angle2 = new Random().nextDouble() * 2 * Math.PI;
                double velocity = radius * Math.cos(angle1);
                double velocityX = velocity * Math.cos(angle2);
                double velocityY = velocity * Math.sin(angle2);

                Fragment fragment = new Fragment(x, y, velocityX, velocityY, 9, 9);
                fragment.setRadius(radius);
                fragment.setCenterX(x);
                fragment.setCenterY(y);
                fragment.setColorId(colorId);
                MyPanel.fragments.add(fragment);
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }
}

class Fragment {
    double radius;
    double centerX, centerY;
    double x, y;
    double velocityX, velocityY;
    int w, h;
    int colorId;
    int times;

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public Fragment(double x, double y, double velocityX, double velocityY, int w, int h) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.w = w;
        this.h = h;
    }

    public void move() {
        // 衰减效果
        velocityX *= 0.75D;
        velocityY *= 0.75D;
        // 计算位置
        x += velocityX;
        y += velocityY;
        // 位移次数(利用位移次数控制绽放半径)
//        times++;
//        if (times % 2 == 0) {
//            this.w -= 2;
//            this.h -= 2;
//        }
        this.w -= 2;
        this.h -= 2;
        // 当烟花碎片大小小于2px或者碎片运动距离大于radius时，碎片消失
        double distance = Math.sqrt(Math.pow((x - centerX), 2) + Math.pow((y - centerY), 2));
        if (this.w <= 2 || this.h <= 2 || distance > radius) {
            MyPanel.fragments.remove(this);
        }
    }
}