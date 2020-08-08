package com.conque_java.knowledge.lock;

import java.util.concurrent.Phaser;

/**
 * 同一阶段的每个线程必须等到所有其它线程任务完成才能进入下一阶段，并且每一阶段任务完成，Phaser都会执行onAdvance方法。
 */
public class DemoPhaser {
    private static final int PHASE_NUM = 4;
    private static final int PARTY_NUM = 9;

    public static void main(String[] args) throws Exception {
        final Phaser phaser = new Phaser(PARTY_NUM) {
            @Override
            protected boolean onAdvance(int phases, int registeredParties) {
                System.out.println("======== Phases : " + phases + " ========");
                return registeredParties == 0;
            }
        };

        for(int i = 0; i < PARTY_NUM; i++) {
            final int threadID = i;
            new Thread(() -> {
                for(int j = 0; j < PHASE_NUM; j++) {
                    System.out.println(String.format("%s\tphase %s", Thread.currentThread().getName(), j));
                    phaser.arriveAndAwaitAdvance();
                }
            }, "Thread-" + threadID).start();
        }
    }
}
