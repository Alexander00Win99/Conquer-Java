package com.conquer_java.knowledge.thread_pool;

import java.util.concurrent.*;

/**
 * 【题目】
 * 现有一个任务T如下：
 * T由n个子任务构成，每个子任务完成时长不同，若其中一个子任务失败，则所有任务可以结束，任务T失败。请写程序模拟该个过程，要求实现Fail Fast。
 *
 * 【分析】
 * 每个子任务需要返回结果，因此不能使用返回值为void的Runnable，只能使用Callable<T>；
 * 使用Callable<T>以后，
 */
public class DemoCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello World!";
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(callable); // 异步执行某个任务

        System.out.println(future.get()); // 同步阻塞获取结果
        executorService.shutdown();
    }
}
