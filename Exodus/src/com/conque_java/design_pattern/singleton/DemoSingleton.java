package com.conque_java.design_pattern.singleton;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 【目标】：掌握单例模式设计方法及其原理
 *
 * 【结果】：完成(参考：参考：https://blog.csdn.net/wlccomeon/article/details/86692544)
 *
 * 【模式意图】：保证类有且仅有一个实例，并且提供一个全局访问点。
 *
 * 【使用场景】：需要严格控制全局变量(例如：数据统计，需要保证全局一致)；无需多个实例的对象(例如工具类)+重量级对象(例如：线程池对象+连接池对象)->节省CPU|内存资源
 *
 * 【涉及知识】：类加载机制；JVM序列化机制；字节码指令重排机制；JDK源码|Spring框架之中单例模式的应用
 * Spring: ReactiveAdapterRegistry.getSharedInstance() + Tomcat: TomcatURLStreamHandlerFactory.getInstanceInternal()
 *
 * 【懒汉模式】
 * 1) 线程安全问题；
 * 2) Double Check机制+加锁优化；
 * 3) JIT编译器指令重排(new指令包括：i.开辟堆空间；ii.初始化对象空间；iii.返回地址指针赋值给栈空间的局部变量)导致在多线程并发场景下可能存在某个线程获取尚未完成初始化的对象，
 * 进而引发空指针错误等异常，可以使用volatile关键字禁止指令重排；
 *
 * 【饿汉模式】
 * 1) 类加载过程(a.加载=加载二进制字节码到内存中，生成对应Class数据结构；b.连接=验证+准备-静态属性赋默认值+解析；c.初始化=静态属性赋初始值)：
 * 2) 当且仅当《主动使用类时》(a.当前类是启动类=包含main()函数入口；b.new constructor()操作；c.访问静态属性；d.访问静态方法；e.使用反射方法访问类；f.初始化类的子类；......)，才能触发类加载过程中的step-c=初始化；
 * 3) 饿汉模式利用《类加载过程中的初始化》阶段step-c完成《实例》的初始化，本质是借助类加载机制(类加载过程自动加锁+类初始化过程只会执行一次)，保证实例的唯一性以及实例创建过程的线程安全(JVM采用同步方式而非异步方式完成类的加载)，
 * 注意，此处线程安全是指单例对象创建过程的线程安全而非该类或者该个单例对象是线程安全的；
 *
 * 【枚举单例】
 * 1) 线程安全问题；
 * 2)；
 * 3) 使用反射生成单例导致"Cannot reflectively create enum objects"异常；
 *     @CallerSensitive
 *     public T newInstance(Object ... initargs)
 *         throws InstantiationException, IllegalAccessException,
 *                IllegalArgumentException, InvocationTargetException
 *     {
 *         if (!override) {
 *             if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
 *                 Class<?> caller = Reflection.getCallerClass();
 *                 checkAccess(caller, clazz, null, modifiers);
 *             }
 *         }
 *         if ((clazz.getModifiers() & Modifier.ENUM) != 0)
 *             throw new IllegalArgumentException("Cannot reflectively create enum objects");
 *         ConstructorAccessor ca = constructorAccessor;   // read volatile
 *         if (ca == null) {
 *             ca = acquireConstructorAccessor();
 *         }
 *         @SuppressWarnings("unchecked")
 *         T inst = (T) ca.newInstance(initargs);
 *         return inst;
 *     }
 * 【静态内部类单例】
 * 1) 本质是利用类加载机制保证单一实例创建过程的线程安全；
 * 2) 只有在实际使用时，才会触发类的初始化进而完成实例初始化，是懒加载的一种变种形式；
 * 3)；
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
public class DemoSingleton {
    public static void main(String[] args) {
        /**
         * 利用反射技术生成的单例对象和原有单例对象不同，破会了单例特性，可以在HungrySingleton类中加入异常阻止利用反射产生单例。
         *
         *         if (instance != null)
         *             throw new RuntimeException("单例模式禁止使用反射技术生成多个实例！");
         *
         * java.lang.reflect.InvocationTargetException
         * 	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
         * 	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
         * 	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
         * 	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
         *
         * 	Caused by: java.lang.RuntimeException: 单例模式禁止使用反射技术生成多个实例
         */
        System.out.println("++++++++++++++++限制反射破坏饿汉式单例++++++++++++++++");
        // 调用静态方法触发类加载过程中的初始化行为
        HungrySingleton hungrySingleton = HungrySingleton.getInstance();
        // 使用反射方法访问类触发类加载过程中的初始化行为
        Class<HungrySingleton> clazz = HungrySingleton.class;
        Constructor<HungrySingleton> constructor = null;
        HungrySingleton reflectedInstance = null;
        try {
            constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            reflectedInstance = constructor.newInstance();
            System.out.println(reflectedInstance == hungrySingleton);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("----------------限制反射破坏饿汉式单例----------------");

        /**
         * 访问静态属性触发类加载过程中的初始化动作，完成饿汉模式实例化。
         * 单纯访问InnerClassSingleton.author不能触发private static class SingletonHolder返回单例对象
         */
        System.out.println("++++++++++++++++访问静态属性触发类加载过程中的初始化动作++++++++++++++++");
        System.out.println(InnerClassSingleton.author); // NOK
        InnerClassSingleton innerClassSingleton = InnerClassSingleton.getInstance(); // OK
        System.out.println(innerClassSingleton);
        System.out.println("----------------访问静态属性触发类加载过程中的初始化动作----------------");

        /**
         * 利用枚举类实现单例
         */
        System.out.println("++++++++++++++++利用枚举类实现单例++++++++++++++++");
        EnumSingleton enumSingleton1 = EnumSingleton.INSTANCE;
        EnumSingleton enumSingleton2 = EnumSingleton.INSTANCE;
        System.out.println(enumSingleton1 == enumSingleton2);
        enumSingleton1.work();
//        Constructor<EnumSingleton> enumConstructor = EnumSingleton.class.getDeclaredConstructor(String.class, Integer.class);
//        enumConstructor.setAccessible(true);
//        EnumSingleton enumSingleton = enumConstructor.newInstance("INSTANCE", 0);
        System.out.println("----------------利用枚举类实现单例----------------");

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
        HungrySingleton serializableInstance = HungrySingleton.getInstance();
        // 打印序列化之前的静态变量值
        System.out.println("Before serialization: HungrySingleton.author == " + HungrySingleton.author);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("D:\\Workspace\\Java\\output\\hungry_singleton_instance.obj")))) {
            // 写入作者
            oos.writeObject("Alex Wen");
            // 写入时间
            oos.writeObject(new Date());
            // 写入版本
            oos.writeInt(1);
            // 写入饿汉单例
            oos.writeObject(serializableInstance);
            // 关闭流
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream ois = null;
        HungrySingleton deserializedInstance = null;
        // 序列化只会保存对象状态，不会保存类的状态，因此，类的静态变量不会写入序列化文件之中。
        HungrySingleton.author = "Alexander00Win99";
        try {
            ois = new ObjectInputStream(new FileInputStream(new File("D:\\Workspace\\Java\\output\\hungry_singleton_instance.obj")));
            // 读出作者
            String author = (String) ois.readObject();
            System.out.println("Author: " + author);
            // 读出时间
            Date date = (Date) ois.readObject();
            System.out.println("Date: " + date);
            // 读出版本
            int version = ois.readInt();
            System.out.println("Version: " + version);
            // 读出饿汉单例
            deserializedInstance = (HungrySingleton) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 打印反序列化之后的静态变量值
        System.out.println("After deserialization: HungrySingleton.author == " + HungrySingleton.author);
        System.out.println(deserializedInstance == serializableInstance);
        System.out.println("----------------限制序列化破坏单例-使用readResolve()方法拦截替换----------------");
    }
}

/**
 *  双重校验加锁DCL(Double-Checked Locking)：依然可能出现问题
 *  只有当(singleton == null)时，才会加锁，加锁以后需要再次判断是否(singleton == null)，通过才会创建对象，假设T1、T2两个线程
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
 *  解决办法：使用volatile关键字修饰instance属性
 */
class LazySingleton {
    private static volatile LazySingleton instance = null;
    private LazySingleton() {}
    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) { // 双重加锁，保证：假设T1、T2同时通过第一if (instance == null)判断到达synchronized (LazySingleton.class)语句，如果不加第二if (instance == null)判断，T1释放锁，T2获取锁，导致单例不唯一。
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}

class HungrySingleton implements Serializable {
    public static String author = "Alexander 温"; // 访问HungrySingleton.auth静态属性，触发HungrySingleton.instance初始化。

    static {
        System.out.println("Static block in HungrySingleton is triggered for initialization!");
    }

    private static final long serialVersionUID = 1L;
    private static HungrySingleton instance = new HungrySingleton();
    private HungrySingleton() {
        System.out.println("执行单例！");
        if (instance != null)
            throw new RuntimeException("单例模式禁止使用反射技术生成多个实例！");
    }
    public static HungrySingleton getInstance() {
        return instance;
    }
    Object readResolve() throws ObjectStreamException {
        return getInstance();
    }
}

/**
 *  实现原理：
 *  利用JVM内部类加载机制(类加载过程会对类自动加锁，因此是天然线程安全)实现线程之间对于指令重排序的隔离。
 *  JVM在加载InnerClassSingleton类时尚未加载内部类SingletonHolder，当某个线程首次调用getInstance方法时才会加载内部类，装载过程会对内部类加锁，
 *  其他线程只能等待，从而实现惰性加载，同时，内部类静态属性SingletonHolder.instance对象实例化完毕才会释放锁，从而隔离指令重排序过程。另外，后续
 *  调用getInstance方法直接返回，提高效率。
 */
class InnerClassSingleton { // 特点：结合懒汉模式懒加载+饿汉模式快加载的优点
    public static String author = "Alexander 温"; // 在main函数中打印InnerClassSingleton.author属性，1)能打印，2)不打印，

    static {
        System.out.println("Static block in InnerClassSingleton is triggered for initialization!"); // 1)语句
    }

    private static class SingletonHolder {
        static {
            System.out.println("Static block in SingletonHolder is triggered for initialization!"); // 2)语句
        }

        private static final InnerClassSingleton instance = new InnerClassSingleton();
    }
    private InnerClassSingleton() {}
    public static InnerClassSingleton getInstance() { // 在main函数中调用InnerClassSingleton.getInstance()方法，才会访问SingletonHolder.instance属性，触发类加载过程的初始化，结果返回生成单例。
        return SingletonHolder.instance;
    }
}

/**
 * Enum天然禁止反射破坏单例
 * if ((clazz.getModifiers() & Modifier.ENUM) != 0)
 *     throw new IllegalArgumentException("Cannot reflectively create enum objects");
 */
enum EnumSingleton {
    INSTANCE;
    public void work() { // 单例类工作方法
        System.out.println("Here do something!" + hashCode());
    }
}
// 枚举单例变种
// 优点：枚举单例是final类型，不会被继承；防止序列化反序列化和反射对于单例的破坏；内部类保证懒加载；
// 缺点：需要使用说明文档说明单例获取方法
class Singleton {
    private enum EnumHolder { // 内部枚举类，实现懒加载效果。
        INSTANCE;
        private Singleton instance;
        EnumHolder() {
            this.instance = new Singleton();
        }
        private Singleton getInstance() {
            return this.instance;
        }
    }
    private Singleton() {}
    public static Singleton getInstance() {
        return EnumHolder.INSTANCE.getInstance();
    }
}

// 单例容器：利用类名注册一组单例实现后续按需提取(类似Spring)
//class Singleton {
//    private static String prop;
//    private static Map<String, Singleton> map = new HashMap<>();
//    static {
//        Singleton singleton = new Singleton();
//        map.put(singleton.getClass().getName(), singleton);
//    }
//    protected Singleton() {} // 默认无参构造函数
//    public static String getProp() {
//        return Singleton.prop;
//    }
//    public static void setProp(String prop) {
//        Singleton.prop = prop;
//    }
//    public static Singleton getInstance(String name) {
//        if (name == null) {
//            name = Singleton.class.getName();
//            System.out.println("name == null --> name == " + name);
//        }
//        if (map.get(name) == null) {
//            try {
//                map.put(name, (Singleton) Class.forName(name).newInstance());
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        return map.get(name);
//    }
//    public static void about() {
//        System.out.println("This is Singleton instance registration container!");
//    }
//}

// ThreadLocal单例：单个线程安全，多线程不安全
// 使用ThreadLocal锁定各个线程，虽然不能保证全局唯一，但是可以保证线程唯一。
//class Singleton {
//    private final static ThreadLocal<Singleton> tlSigleton = new ThreadLocal<Singleton>(){
//        @Override
//        protected Singleton initialValue() {
//            return new Singleton();
//        }
//    };
//    private Singleton() {}
//    public static Singleton getInstance() {
//        return tlSigleton.get();
//    }
//}