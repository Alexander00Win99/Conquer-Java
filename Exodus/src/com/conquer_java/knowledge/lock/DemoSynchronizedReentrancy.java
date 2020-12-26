package com.conquer_java.knowledge.lock;

import java.util.ArrayList;

public class DemoSynchronizedReentrancy {
    public static int i = 1;
    public int count;

    public DemoSynchronizedReentrancy() {
        super();
    }

    public void doSomething() {
        int time = (int) (Math.random() * 1000);
        count = i++;
        System.out.println("线程-" + count + ": " + Thread.currentThread().getName() + "进入睡眠，预计休息" + time + "毫秒！");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程-" + count + ": " + Thread.currentThread().getName() + "结束睡眠。当前进入doSomething()方法的线程总计：" + count + "个！");
    }

    public static void main(String[] args) {
        int threadNum = 10;
        DemoSynchronizedReentrancy demo = new DemoSynchronizedReentrancy();
        ArrayList<MyThread> myThreads = new ArrayList();
        for (int i = 0; i < threadNum; i++) {
            MyThread myThread = new MyThread();
            myThread.demo = demo;
            myThreads.add(myThread);
        }

        for (int i = 0; i < threadNum; i++) {
            myThreads.get(i).start();
        }
    }
}

class MyThread extends Thread {
    public DemoSynchronizedReentrancy demo;

    public MyThread() {
        super();
    }

    @Override
    public void run() {
        demo.doSomething();
    }
}