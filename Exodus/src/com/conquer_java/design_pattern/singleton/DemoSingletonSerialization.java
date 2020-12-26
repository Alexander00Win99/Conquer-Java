package com.conquer_java.design_pattern.singleton;

import java.io.*;
import java.util.Date;

/**
 * 【目标】：掌握单例模式设计方法及其原理
 *
 * 【结果】：完成(参考：https://blog.csdn.net/wlccomeon/article/details/86692544)
 *
 * 【模式意图】：保证类有且仅有一个实例，并且提供一个全局访问点。
 *
 * 【使用场景】：需要严格控制全局变量(例如：数据统计，需要保证全局一致)；无需多个实例的对象(例如工具类)+重量级对象(例如：线程池对象+连接池对象)->节省CPU|内存资源
 *
 * 【涉及知识】：类加载机制；JVM序列化机制；字节码指令重排机制；JDK源码|Spring框架之中单例模式的应用
 * Spring: ReactiveAdapterRegistry.getSharedInstance() + Tomcat: TomcatURLStreamHandlerFactory.getInstanceInternal()
 *
 * 【类加载过程】
 * 1) 类加载过程(a.加载=加载二进制字节码到内存中，生成对应Class数据结构；b.连接=验证+准备-静态属性|成员变量赋默认值+解析；c.初始化=静态属性|成员变量赋初始值)：
 * 2) 当且仅当《主动使用类时》(a.当前类是启动类=包含main()函数入口；b.new constructor()操作；c.访问静态属性；d.访问静态方法；e.使用反射方法访问类；f.初始化类的子类；......)，才能触发类加载过程，当然包括其中的step-c=初始化；
 *
 * 【懒汉模式】
 * 1) 线程安全问题；
 * 2) Double Check机制+加锁优化；
 * 3) JIT编译器指令重排(new指令包括：i.开辟堆空间；ii.初始化对象空间；iii.返回地址指针赋值给栈空间的局部变量)导致在多线程并发场景下可能存在某个线程获取尚未完成初始化的对象，
 * 进而引发空指针错误等异常，可以使用volatile关键字禁止指令重排；
 *
 * 【饿汉模式】
 * 1) 类加载过程(a.加载=加载二进制字节码到内存中，生成对应Class数据结构；b.连接=验证+准备-静态属性赋默认值+解析；c.初始化=静态属性赋初始值)：
 * 2) 当且仅当《主动使用类时》(a.当前类是启动类=包含main()函数入口；b.new constructor()操作；c.访问静态属性；d.访问静态方法；e.使用反射方法访问类；f.初始化类的子类；......)，才能触发类加载过程，当然包括其中的step-c=初始化；
 * 3) 饿汉模式利用《类加载过程中的初始化》阶段step-c完成《实例》的初始化，本质是借助类加载机制(类加载过程自动加锁+类初始化过程只会执行一次)，保证实例的唯一性以及实例创建过程的线程安全(JVM采用同步方式而非异步方式完成类的加载)，
 * 注意，此处线程安全是指单例对象创建过程的线程安全而非该类或者该个单例对象是线程安全的；
 *
 *
 * 【静态内部类单例】
 * 1) 本质是利用类加载机制保证单一实例创建过程的线程安全；
 * 2) 只有在实际使用时，才会触发类的初始化进而完成实例初始化，是懒加载的一种变种形式；
 * 3) 综合懒汉模式和饿汉模式的特点；
 *
 * 【Java字节码反汇编举例】
 * public class Demo {
 * 	public static void main(String[] args) {
 * 		Demo demo = new Demo();
 *        }
 * }
 *
 * D:\>javap -c -v -p Demo.class
 * Classfile /D:/Demo.class
 *   Last modified Feb 2, 2020; size 270 bytes
 *   MD5 checksum 64db768d73abb9750bd198c6655b932c
 *   Compiled from "Demo.java"
 * public class Demo
 *   minor version: 0
 *   major version: 52
 *   flags: ACC_PUBLIC, ACC_SUPER
 * Constant pool:
 *    #1 = Methodref          #4.#13         // java/lang/Object."<init>":()V
 *    #2 = Class              #14            // Demo
 *    #3 = Methodref          #2.#13         // Demo."<init>":()V
 *    #4 = Class              #15            // java/lang/Object
 *    #5 = Utf8               <init>
 *    #6 = Utf8               ()V
 *    #7 = Utf8               Code
 *    #8 = Utf8               LineNumberTable
 *    #9 = Utf8               main
 *   #10 = Utf8               ([Ljava/lang/String;)V
 *   #11 = Utf8               SourceFile
 *   #12 = Utf8               Demo.java
 *   #13 = NameAndType        #5:#6          // "<init>":()V
 *   #14 = Utf8               Demo
 *   #15 = Utf8               java/lang/Object
 * {
 *   public Demo();
 *     descriptor: ()V
 *     flags: ACC_PUBLIC
 *     Code:
 *       stack=1, locals=1, args_size=1
 *          0: aload_0
 *          1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *          4: return
 *       LineNumberTable:
 *         line 1: 0
 *
 *   public static void main(java.lang.String[]);
 *     descriptor: ([Ljava/lang/String;)V
 *     flags: ACC_PUBLIC, ACC_STATIC
 *     Code:
 *       stack=2, locals=2, args_size=1
 *          0: new           #2                  // class Demo
 *          3: dup
 *          4: invokespecial #3                  // Method "<init>":()V
 *          7: astore_1
 *          8: return
 *       LineNumberTable:
 *         line 3: 0
 *         line 4: 8
 * }
 * SourceFile: "Demo.java"
 *
 * D:\>
 *
 */
public class DemoSingletonSerialization {
    public static void main(String[] args) {
        /**
         * 序列化
         * 【使用场景】
         * 屏蔽CPU硬件差异、操作系统差异、网络字节序差异，实现对象信息(字节码形式)网络分布式存储(内存、文件、数据库等)，支持如下场景：
         * 1) RMI(参数和返回值==对象序列化信息，跨JVM)
         * 2) Java Beans(设计阶段保存状态信息，启动以后恢复状态信息)
         * 【注意事项】
         * 1) 流程：writeReplace()->writeObject()->readObject()->readResolve()，对象与替代对象必须兼容，否则报错ClassCastException；
         * 2) static|transient变量不支持状态保存，因此无法序列化；
         * 3) Socket|Thread类不支持序列化，所以，若类含有Socket|Thread类对象，则在序列化时报错；
         * 4) 只会保存对象类型及其成员变量的状态(值和类型)，不会保存成员方法(只是指令，没有状态，JVM ClassLoader在加载类时可以load方法指令)；
         * 5) 如果类未显式提供serivalVersionUID版本字段，JVM序列化机制会隐式根据编译生成的字节码文件(不同编译器实现不同，类名|接口名称|属性|方法|......)自动生成一个64位哈希字段用于版本比较。
         * 如果，原有本地类发生任何改动，根据远程序列化文件进行反序列化操作都会如下报错：
         * local class incompatible: stream classdesc serialVersionUID = -3700394507316154766, local class serialVersionUID = -8677801470232481416
         * 所以，为了操作方便，必须显示提供版本字段serialVersionUID。
         *
         *  * Classes that need to designate a replacement when an instance of it
         *  * is read from the stream should implement this special method with the
         *  * exact signature.
         *
         *   * <PRE>
         *  * ANY-ACCESS-MODIFIER static final long serialVersionUID = 42L;
         *  * </PRE>
         *
         *  1) java.io.Serializable接口没有任何属性或者方法，实现它的类只是从语义上表明自己是可以序列化的；
         *  2) 在Serializable对象装配过程中，不会调用任何构建器（甚至默认构建器）。整个对象都是通过从InputStream中取得数据进行恢复的；
         *  3) 父类是可序列化的->子类是可序列化的。
         */
        System.out.println("++++++++++++++++限制序列化破坏单例-使用readResolve()方法拦截替换++++++++++++++++");
        //HungrySingleton originalInstance = HungrySingleton.getInstance();
        LazySingleton originalInstance = LazySingleton.getInstance();
        // 打印序列化之前的静态变量值
        //System.out.println("Before serialization: HungrySingleton.author == " + HungrySingleton.author);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("D:\\Workspace\\Java\\output\\lazy_singleton_instance.obj")))) {
            // 写入作者
            oos.writeObject("Alex Wen");
            // 写入时间
            oos.writeObject(new Date());
            // 写入版本
            oos.writeInt(0);
            // 写入懒汉单例
            oos.writeObject(originalInstance);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LazySingleton deserializedInstance = null;
        // 序列化只会保存对象状态，不会保存类的状态，因此，类的静态变量不会写入序列化文件之中。
        //HungrySingleton.author = "Alexander00Win99";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("D:\\Workspace\\Java\\output\\lazy_singleton_instance.obj")))) {
            // 读出作者
            String author = (String) ois.readObject();
            System.out.println("Author: " + author);
            // 读出时间
            Date date = (Date) ois.readObject();
            System.out.println("Date: " + date);
            // 读出版本
            int version = ois.readInt();
            System.out.println("Version: " + version);
            // 读出懒汉单例
            deserializedInstance = (LazySingleton) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 打印反序列化之后的静态变量值
        //System.out.println("After deserialization: HungrySingleton.author == " + HungrySingleton.author);
        System.out.println(deserializedInstance == originalInstance);
        System.out.println("----------------限制序列化破坏单例-使用readResolve()方法拦截替换----------------");
    }
}
