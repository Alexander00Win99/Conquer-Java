package com.conquer_java.knowledge.thread_pool;

public class Task implements Runnable {
    private int tid;

    public Task(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        System.out.println("执行当前任务的线程是：" + Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前任务是：" + tid + "，执行中。。。。。。");
    }
}
