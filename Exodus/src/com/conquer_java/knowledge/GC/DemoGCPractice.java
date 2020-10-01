package com.conquer_java.knowledge.GC;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Memory Leak + 可能OOM
 */
public class DemoGCPractice {
    private static class CardInfo {
        BigDecimal deposit = new BigDecimal(0.0);
        String name = "Alexander Wen";
        int age = 18;
        Date birthday = new Date();

        public void method() {}
    }

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(100, new ThreadPoolExecutor.DiscardOldestPolicy());

    private static void modelFit() {
        List<CardInfo> taskList = getAllCardInfo();

        taskList.forEach(info -> {
            // do something here
            executor.scheduleWithFixedDelay(() -> {
                // do something with info
                info.method();
            }, 2, 3, TimeUnit.SECONDS);
        });
    }

    private static List<CardInfo> getAllCardInfo() {
        List<CardInfo> taskList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            CardInfo cardInfo = new CardInfo();
            taskList.add(cardInfo);
        }

        return taskList;
    }

    public static void main(String[] args) throws InterruptedException {
        executor.setMaximumPoolSize(100);

        for (;;) {
            modelFit();
            Thread.sleep(1000);
        }
    }
}
