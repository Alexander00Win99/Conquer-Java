package com.conque_java.knowledge.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class DemoThreadState {
    /**
     * 3) WAITING
     * 定义：A thread that is waiting indefinitely for another thread to perform a particular action is in this state.
     * 触发动作：
     * A thread is in the waiting state due to calling one of the following methods:
     *
     * Object.wait with no timeout
     * Thread.join with no timeout
     * LockSupport.park
     * A thread in the waiting state is waiting for another thread to perform a particular action. For example,
     * a thread that has called Object.wait() on an object is waiting for another thread to call Object.notify() or Object.notifyAll() on that object.
     * A thread that has called Thread.join() is waiting for a specified thread to terminate.
     *
     * 4) TIMED_WAITING
     * 定义：A thread that is waiting for another thread to perform an action for up to a specified waiting time is in this state.
     * 触发动作：
     * Thread state for a waiting thread with a specified waiting time. A thread is in the timed waiting state due to calling one of the following methods with a specified positive waiting time:
     *
     * Thread.sleep(x)
     * Object.wait with timeout
     * Thread.join with timeout
     * LockSupport.parkNanos
     * LockSupport.parkUntil
     * 注：WAITING VS TIMED_WAITING: 区别在于：是否具有timeout时限参数 + Thread.sleep(x)
     *
     * 场景：
     * 1. 单个线程或者多个线程的Thread.sleep(timeout)（可以视为，特殊情形的wait/notify）
     * 注：对比wait(0)，sleep(0)并非意味着一直睡到天昏地暗、头昏脑涨，相反，代表几乎不用等待，并且sleep方法没有任何“同步”语义，或者说，sleep不会释放锁，javadoc中更加准确的说法，The thread does not lose ownership of any monitors（线程不会失去任何 monitor的所有权）。
     * 《The Java Language Specification Java SE 7 Edition》中的说法是：“It is important to note that neither Thread.sleep nor Thread.yield have any synchronization semantics”（sleep和yield 均无任何同步语义），
     * 另外一个影响是，在sleep(timeout)调用前后无需关心寄存器缓存与内存数据的一致性（no flush or reload）。
     * 举例来说：
     * 1) 在同步块/方法中调用sleep(timeout)，sleep期间锁依然为当前线程所有；
     * 2) 在非同步块/方法中调用sleep(timeout)，线程本来就没有拥有过锁，sleep期间自然无所谓什么释放或者保留锁不锁的问题；
     * 2. 具备协作cooperate关系的线程之间obj.wait(timeout)和obj.notify/notifyAll + thread.join(thread执行完毕自动通知)
     * 在列车如厕场景中，假设乘务员线程在增加了厕纸后，正当它准备执行notify/notifyAll时，这个线程因为某种原因被杀死了（持有的锁当然也会随之释放）或者乘务员线程干脆因为某种原因没有执行notify/notifyAll。
     * 在这种情况下，条件虽然已经满足了，但是等待的线程却无法收到通知，只能继续傻等下去。此时，如果某个乘客线程，不是调用wait()而是调用wait(1000)，其后进入wait set睡觉等待。那么wait(1000)相当于自带闹钟双保险，她会同时等待两个通知，具体哪个唤醒自己取决于哪个通知先到：
     * 1) 如果在1000毫秒内，收到了乘务员线程的通知从而唤醒，闹钟随之失效；
     * 2) 反之，如果超过1000毫秒，还没收到通知，那么闹钟响起，她被闹钟唤醒。
     * 3. thread.join()等同object.wait()
     * 4. LockSupport.parkNanos
     * 5. LockSupport.parkUntil
     *
     * 虚假唤醒（spurious wakeup）：
     * 虽然，无参wait() <==> wait(0)，意思等到天荒地老，海枯石烂。但是，事实上存在所谓的“spurious wakeup”，也即“虚假唤醒”的情况，详见Object.wait(long timeout)中的javadoc说明：
     * A thread can also wake up without being notified, interrupted, or timing out, a so-called spurious wakeup. While this will rarely occur in practice,
     * applications must guard against it by testing for the condition that should have caused the thread to be awakened, and continuing to wait if the condition is not satisfied.
     * 一个线程也能在没有被通知、中断或超时的情况下唤醒，也即所谓的“虚假唤醒”，虽然这点在实践中很少发生，但是应用程序必须检测唤醒线程的条件是否满足，并在条件不满足的情况下继续等待，以此防止“虚假唤醒”。
     *
     * 简单地讲，我们需要避免使用if语句的方式来判断条件，否则一旦线程恢复，就会继续往下执行，不会再次检测条件是否成立，然而当时可能条件已经不再成立。由于可能存在的“虚假唤醒”，并不意味着条件是满足的，关于这点，甚至在两个具备类似“二人转”简单关系的线程之间的wait/notify场景中，也需要注意。
     * 另外，如果对于更多线程的情况，比如“生产者和消费者”问题，一个生产者，两个消费者，更加不能简单地用if进行判断。因为可能用的是notifyAll，如果两个消费者同时起来，其中一个先抢到了锁，进行了资源消费动作，等到另外一个也抢到了锁，可能条件又不满足了，所以还是要继续判断，不能简单地认为被唤醒了轮到自己就是条件满足了。
     * 由此可见，wait应该总是在循环中调用（waits should always occur in loops），对此javadoc中给出了样板代码：
     * NOK:
     * if (condition) { // if statement is NOK!!!
     *     obj.wait(timeout);
     *     ... // Perform action appropriate to condition
     * }
     * OK:
     * synchronized (obj) { // while statement is OK!!!
     *     while (<condition does not hold>)
     *     obj.wait(timeout);
     *     ... // Perform action appropriate to condition
     * }
     *
     * [BLOCKED VS WAITING VS TIMED_WAITING状态的区别和联系]
     * 1) BLOCKED可以视作是一种特殊的、隐式的wait/nofity机制，等待的条件就是“有锁还是没锁”。当锁可用时，其中的一个线程会被系统隐式通知，并被赋予锁，从而获得在同步块中的执行权限。显然，此时等待锁的线程与系统同步机制之间形成了一个协作关系。
     * 不过，这是一个不确定的等待，可能等待（当无法获取锁时），也可能不等待（当能够获取锁时）。陷入这种阻塞以后没有自主退出的机制。
     * 另外一点需要注意的是，BLOCKED状态是与Java语言级别的synchronized机制相关的，我们知道在Java 5.0之后引入了更多的机制（java.util.concurrent），除了可以用synchronized这种内部锁，也可以使用外部的显式锁。
     * 显式锁有一些更好的特性，比如能被中断，能够设置获取锁的超时，能够有多个条件等等，尽管从表面上看，当显式锁无法获取时，我们还是宣称线程被“阻塞”了，但是此时未必是BLOCKED状态。
     * 2) WAITING状态属于线程自身主动地、显式地申请阻塞(LOCKED属于被动的阻塞)，但是无论从字面还是从根本上来看，两者之间并无本质的区别。
     * 3) TIMED_WAITING相对前面两者陷入阻塞以后具备超时自主退出的机制。
     * 4) 所有三个状态可以认为是传统OS中的线程/进程状态waiting在JVM层面的一个细分。
     *
     * @throws InterruptedException
     */
    // 阻塞式I/O操作处于RUNNABLE状态
    public static void testBlockedIOState() throws InterruptedException {
        Scanner in = new Scanner(System.in);
        // 创建线程“输入输出”
        Thread readThread = new Thread(() -> {
            System.out.println("请输入一串字符：");
            // 阻塞式nextLine方法读取输入
            String input = in.nextLine();
            System.out.println(input);
        }, "输入输出");
        readThread.start();
        Thread.sleep(1000); // 确保readThread已经运行
        System.out.println("输入输出线程当前状态是：" + readThread.getState());
        assert(readThread.getState().equals(Thread.State.RUNNABLE));
    }

    // 阻塞式网络操作处于RUNNABLE状态
    public static void testBlockedSocketState() throws InterruptedException {
        Thread serverThread = new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(10086);
                while (true) {
                    // 阻塞式accept方法接受连接
                    Socket socket = serverSocket.accept();
                    // TODO......
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "Socket服务器");
        serverThread.start();
        Thread.sleep(1000); // 确保serverThread已经运行
        System.out.println("Socket服务器线程当前状态：" + serverThread.getState());
        assert(serverThread.getState().equals(Thread.State.RUNNABLE));
    }

    public static void testTimedWaitingState() throws InterruptedException {
        class Toilet {
            int paperCount = 0;
            public void pee() {
                try {
                    Thread.sleep(30000); // 研究表明，动物尿尿时间一般都在30s以内
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }

        Toilet toilet = new Toilet();

        Thread steward = new Thread(() -> {
            synchronized (toilet) {
                toilet.paperCount += 10;
                //toilet.notifyAll(); // 粗心的乘务员忘记通知处于BLOCKED队列的乘客
            }
        }, "乘客1");

        Thread passenger1 = new Thread(() -> {
            synchronized (toilet) {
                while (toilet.paperCount <= 0) {
                    try {
                        toilet.wait(); // 无人通知，不会醒来。重点注意：wait()必须位于while循环之中，以期始终会被检测条件。
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
                toilet.paperCount--;
                toilet.pee();
            }
        }, "乘客1");

        Thread passenger2 = new Thread(() -> {
            synchronized (toilet) {
                while (toilet.paperCount <= 0) {
                    try {
                        toilet.wait(60000); // 无人通知，60s后自行醒来。重点注意：wait(x)必须位于while循环之中，以期始终会被检测条件。
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
                toilet.paperCount--;
                toilet.pee();
            }
        }, "乘客2");

        passenger1.start();
        passenger2.start();
        assert(toilet.paperCount == 0);
        Thread.sleep(10000); // 等待10s，确保乘客1和乘客2线程均已运行。
        System.out.println("乘客1线程当前状态是：" + passenger1.getState());
        assert(passenger1.getState().equals(Thread.State.WAITING));
        System.out.println("乘客2线程当前状态是：" + passenger2.getState());
        assert(passenger2.getState().equals(Thread.State.TIMED_WAITING));

        steward.start();
        Thread.sleep(10000); // 等待10s，确保乘务员线程已经运行。
        System.out.println("乘务员加纸以后，当前厕纸数量：" + toilet.paperCount);
        assert(toilet.paperCount == 10);
        Thread.sleep(60000); // 等待60s，确保乘客2线程已经睡醒。
        System.out.println("乘务员加纸以后，乘客2已经如厕，当前厕纸数量：" + toilet.paperCount);
        assert(toilet.paperCount == 9);
        System.out.println("乘务员加纸以后，乘客1线程当前状态是：" + passenger1.getState());
        assert(passenger1.getState().equals(Thread.State.WAITING));
        System.out.println("乘务员加纸以后，乘客2线程当前状态是：" + passenger2.getState());
        assert(passenger2.getState().equals(Thread.State.TIMED_WAITING)); // 乘客2正在尿尿，处于TIMED_WAITING
        Thread.sleep(30000); // 确保乘客2尿尿完成
        System.out.println("乘客2如厕以后，乘客1线程当前状态是：" + passenger1.getState());
        assert(passenger1.getState().equals(Thread.State.WAITING));
        System.out.println("乘客2如厕以后，乘客2线程当前状态是：" + passenger2.getState());
        assert(passenger2.getState().equals(Thread.State.TERMINATED));
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("++++++++++++++++Begin-RUNNABLE++++++++++++++++");
        testBlockedIOState();
        testBlockedSocketState();
        System.out.println("----------------End-RUNNABLE----------------");

        System.out.println("++++++++++++++++Begin-BLOCKED++++++++++++++++");
        System.out.println("----------------End-BLOCKED----------------");

        System.out.println("++++++++++++++++Begin-WAITING++++++++++++++++");
        System.out.println("----------------End-WAITING----------------");

        System.out.println("++++++++++++++++Begin-TIMED_WAITING++++++++++++++++");
        testTimedWaitingState();
        System.out.println("----------------End-TIMED_WAITING----------------");
    }
}
