package com.conquer_java.design_pattern.singleton;

public class ThreadLocalSingleton {
    private ThreadLocalSingleton() {}

    private static final ThreadLocal<ThreadLocalSingleton> instanceTL = new ThreadLocal<ThreadLocalSingleton>(){
        @Override
        protected ThreadLocalSingleton initialValue(){
            return new ThreadLocalSingleton();
        }
    };

    public static ThreadLocalSingleton getInstance() {
        return instanceTL.get();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                ThreadLocalSingleton tlSingleton01 = ThreadLocalSingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " - " + tlSingleton01);
                ThreadLocalSingleton tlSingleton02 = ThreadLocalSingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " - " + tlSingleton02);
            }, "Tread" + i).start();
        }
    }
}
