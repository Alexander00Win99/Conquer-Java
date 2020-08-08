package com.conque_java.knowledge.JVM.Volatile;

/**
 * 此例证明：指令确实可以乱序执行
 * 假设：t1内部：a = 1; x = b; 以及 t2内部：b = 1; y = a; 不会发生指令重排。那么以下六种场景不会出现(0, 0)情形：
 * 1 2 3 4
 * a = 1; x = b; b = 1; y = a; => x == 0 && y == 1
 * 1 3 2 4
 * a = 1; b = 1; x = b; y = a; => x == 1 && y == 1
 * 1 3 4 2
 * a = 1; b = 1; y = a; x = b; => x == 1 && y == 1
 * 3 4 1 2
 * b = 1; y = a; a = 1; x = b; => x == 1 && y == 0
 * 3 1 4 2
 * b = 1; a = 1; y = a; x = b; => x == 1 && y == 1
 * 3 1 2 4
 * b = 1; a = 1; x = b; y = a; => x == 1 && y == 1
 * 注：若要(0, 0)情形，只能t1或者t2其中之一发生指令重排，x = b先于a = 1，或者y = a先于b = 1。
 */
public class DemoDisorder {
    private static int a = 0, b = 0;
    private static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {
        long begin = System.currentTimeMillis();
        int i = 0;
        for (;;) { // 初始化
            i++;
            a = 0; b = 0;
            x = 0; y = 0;

            Thread t1 = new Thread(() -> { // 上下两条指令相互无关
                //shortWait(100_000L);
                a = 1;
                x = b;
            });
            Thread t2 = new Thread(() -> { // 上下两条指令相互无关
                b = 1;
                y = a;
            });

            t1.start();t2.start();
            t1.join();t2.join();

            if (x == 0 && y == 0) {
                System.err.println(String.format("当执行到第%d次时，遇到(0, 0)情况，证明CPU此时发生指令重排！", i));
                break;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("总计耗时：" + (end - begin) + "ms");
    }

    private static void shortWait(long interval) {
        long begin = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while (begin + interval >= end);
    }
}
