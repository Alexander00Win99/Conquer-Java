package com.conquer_java.design_pattern.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 *  双重校验加锁DCL(Double-Checked Locking)：依然可能出现问题
 *  只有当(singleton == null)时，才会加锁(细化锁粒度，提高代码效率)，加锁以后需要再次判断是否(singleton == null)，通过才会创建对象，假设T1、T2两个线程
 *  同时到达并且通过首个判断(singleton == null)，T1获取锁创建单例对象以后释放锁，如果没有再次判断(singleton == null)，T2获取
 *  锁后会继续创建单例，造成错误。因此，在创建对象时，需要双重校验是否(singleton == null)。
 *  ClassX x = new ClassX();执行过程如下：
 *  编译源码ClassX.java->字节码ClassX.class，存储磁盘；
 *  A) 加载ClassX.class字节码到方法区，形成类元信息；
 *  B) 在方法区为静态成员分配静态空间(属性 + 方法 + 块)；
 *  C) 执行静态块，进行初始化；
 *  D) 堆区开辟对象空间；
 *  E) 在堆区为非静态成员分配空间(属性 + 方法 + 块 + 构造方法)并且进行默认初始化；
 *  F) 显式初始化非静态属性；
 *  G) 执行非静态块，进行初始化；
 *  H) 执行构造函数；
 *  I) 堆区地址引用赋值栈区变量；
 *  注：B)-H)之间语句是对象初始化工作，I)语句是地址引用赋值工作，因为JVM级别性能优化、GIT编译器级别性能优化、CPU指令级别性能优化可能使用指令重排功能，两项工作之间可能随机乱序执行，而非固定先后顺序执行。
 *  结合上述流程，DCL出现问题场景如下：
 *  1)
 *  假设两个线程A和B同时调用Singleton.getInstance()方法，同时进入第一(singleton == null)；
 *  A线程先行获取synchronized锁，返回堆区地址引用给方法区静态属性Singleton.instance，释放synchronized锁，此时堆区对象空间初始化工作可能尚未完成；
 *  B线程随后获取synchronized锁，进入第二(singleton == null)判断，条件不成立直接返回方法区静态属性Singleton.instance对象，但是此时可能出现对象中的某个属性为空的情况(空指针异常)。
 *  或者
 *  2)
 *  线程A执行instance = new LazySingleton();完毕返回单例地址，此时对象空间初始化工作尚无完成；
 *  线程B调用getInstance()到达首个判断if (instance == null)，判断失败直接返回尚未初始化完成的instance单例对象；
 *
 *  解决办法：使用volatile关键字修饰instance属性，防止实例生成过程的指令重排
 */
class LazySingleton implements Serializable {
    private static volatile LazySingleton instance = null;
    private static boolean flag = false; // 限制反射破坏单例
    private static final long serialVersionUID = 1L;
    private LazySingleton() {
        synchronized (LazySingleton.class) {
            System.out.println("执行懒汉式单例！");
            if (instance != null)
                throw new RuntimeException("严重警告：禁止使用反射技术破坏单例！");
            if (!flag)
                flag = true;
            else
                throw new RuntimeException("标志判断无法禁止黑客使用反射技术重置标志！");
        }
    }
    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) { // 双重加锁，保证：假设T1、T2同时通过第一if (instance == null)判断到达synchronized (LazySingleton.class)语句，如果不加第二if (instance == null)判断，T1释放锁，T2获取锁，导致单例不唯一。
                    instance = new LazySingleton(); // new是非原子操作，必须通过volatile禁止指令重排
                }
            }
        }
        return instance;
    }
    Object readResolve() throws ObjectStreamException {
        return getInstance();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("++++++++++++++++限制反射破坏DCL单例——非空判断=NOK+标志判断=OK++++++++++++++++");
        Class<LazySingleton> clazz = LazySingleton.class;
        Constructor<LazySingleton> constructor = clazz.getDeclaredConstructor(null);
        constructor.setAccessible(true);
        LazySingleton reflectedInstance = constructor.newInstance();
        Field flag = clazz.getDeclaredField("flag");
        flag.setAccessible(true);
        flag.set(reflectedInstance, false);
        LazySingleton instance = LazySingleton.getInstance();
        System.out.println(instance == reflectedInstance);
        if (!(instance == reflectedInstance)) System.out.println("非空判断无法阻止黑客先于用户通过反射获取单例！");

//        LazySingleton reflectedInstance1 = constructor.newInstance();
//        LazySingleton reflectedInstance2 = constructor.newInstance();
//        System.out.println(System.identityHashCode(reflectedInstance1));
//        System.out.println(System.identityHashCode(reflectedInstance2));
        System.out.println("----------------限制反射破坏DCL单例——非空判断=NOK+标志判断=OK----------------");
    }
}
