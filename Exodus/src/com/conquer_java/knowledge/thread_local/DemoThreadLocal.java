package com.conquer_java.knowledge.thread_local;

import java.util.concurrent.CountDownLatch;

public class DemoThreadLocal {
    private static final int THREAD_NUM = 3;
    private static CountDownLatch latch = new CountDownLatch(THREAD_NUM);

    private static class Container {
        public void append(String s) {
            StringBuilder sb = Counter.counter.get();
            Counter.counter.set(sb.append(s));
            //this.set(String.valueOf(sb.append(s)));
        }

        public void set(String s) {
            Counter.counter.set(new StringBuilder(s));
            System.out.printf("Thread name: %s执行Container中set()方法——调用Counter中counter属性的set()方法：ThreadLocal hashcode: %s,  Instance hashcode: %s, Instance value: %s\n",
                    Thread.currentThread().getName(),
                    Counter.counter.hashCode(),
                    Counter.counter.get().hashCode(),
                    Counter.counter.get().toString());
        }

        public void print() {
            System.out.printf("Thread name: %s执行Container中print()方法：ThreadLocal hashcode: %s,  Instance hashcode: %s, Instance value: %s\n",
                    Thread.currentThread().getName(),
                    Counter.counter.hashCode(),
                    Counter.counter.get().hashCode(),
                    Counter.counter.get().toString());
        }
    }

    private static class Counter {
        private static ThreadLocal<StringBuilder> counter = new ThreadLocal<StringBuilder>(){
            @Override
            protected StringBuilder initialValue() {
                return new StringBuilder();
            }
        };

//        private static ThreadLocal<StringBuilder> counter1 = () -> {new StringBuilder();}
    }

    public static void main(String[] args) throws InterruptedException {
        Container container = new Container();

        int loop = 2;
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                for (int j = 1; j <= loop; j++) {
                    container.append(String.valueOf("loop" + j));
                    container.print();
                }
                container.set("Hello World!");
            }, "THREAD-" + i).start();
            latch.countDown();
        }

        //System.exit(0);
        latch.await();
    }
}
