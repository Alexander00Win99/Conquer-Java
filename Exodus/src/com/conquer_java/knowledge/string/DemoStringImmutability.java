package com.conquer_java.knowledge.string;

/**
 * [Java运行时数据区结构]-Java内存模型
 * 栈：存放基本类型变量的数值以及引用类型变量的地址引用；
 * 堆：存放new()创建的对象；
 * 方法区的常量池：存放基本类型常量和字符串常量；
 *
 * intern()作用——JDK1.7之后：如果常量池中没有该字符串的引用地址，写入并返回；如果有，返回引用地址。
 *
 * String str = "Hello World!"; // 字面量(直接量)赋值形式：若常量池中已有该字符串，则直接返回常量池中引用地址；否则在堆中新建指定内容字符串对象以后将对象引用地址存入字符串常量池后返回； => 创建0个或者1个对象；
 * String str = new String("Hello World!"); // 通过new关键字新建对象形式：首先堆中新建一个String对象，然后在常量池中查找是否存在相同内容字符串，有则忽略，无则新建相同内容字符串； => 创建1个或者2个对象；
 * 【结论】：推荐使用字面量(直接量)赋值形式，可以节省内存。
 *
 * 【Why String is immutable in Java?】
 * 1) 只有字符串是不可变的，字符串池才有可能实现。字符串池的实现可以在运行时节约很多heap空间；
 * 2) 如果字符串是可变的，那么可能引起严重的安全问题。譬如，数据库的用户名、密码；socket的主机名、端口；
 * 3) 字符串是线程安全的，不用因为线程安全问题而使用同步；
 * 4) 类加载器使用字符串，不可变性可以保证安全性；
 * 5) 因为字符串是不可变的，所以在创建时hashcode即被缓存hash字段，无需重新计算。因此，字符串非常适合作为Map中的键，字符串的处理速度要远远快过其它类型键对象，HashMap中的键通常都是使用字符串。
 */
public class DemoStringImmutability {
    public static void main(String[] args) {
//        String str1 = "Hello World!";
//        String str2 = "Hello World!";
//        System.out.println(str1 == str2); // true

//        String str1 = new String("Hello World!");
//        String str2 = new String("Hello World!");
//        System.out.println(str1 == str2); // false

//        String str1 = "Hello World!";
//        String str2 = new String("Hello World!");
//        System.out.println(str1 == str2); // false

//        String str1 = new String("Hello ") + new String("World!"); // 堆中3个对象："Hello "|"World!"|"Hello World!" + 常量池中2个对象："Hello "|"World!"
//        str1.intern(); // 将堆中"Hello World!"字符串对象的引用地址写入常量池中
//        String str2 = "Hello World!";
//        System.out.println(str1 == str2); // true

//        String str1 = new String("Hello ") + new String("World!"); // 堆中3个对象："Hello "|"World!"|"Hello World!" + 常量池中2个对象："Hello "|"World!"
//        String str2 = "Hello World!";
//        str1.intern(); // 常量池中str2="Hello World!"字符串已经存在，返回常量池中"Hello World!"字符串的引用地址是，但是无人接收返回值
//        System.out.println(str1 == str2); // false

        String str1 = new String("Hello ") + new String("World!"); // 堆中3个对象："Hello "|"World!"|"Hello World!" + 常量池中2个对象："Hello "|"World!"
        String str2 = "Hello World!";
        str1 = str1.intern(); // 常量池中str2="Hello World!"字符串已经存在，返回常量池中"Hello World!"字符串的引用地址，str1接收返回的地址引用
        System.out.println(str1 == str2); // true
        System.out.println("str1: " + System.identityHashCode(str1));
        System.out.println("str2: " + System.identityHashCode(str2));

        String s0 = "计算机科学与技术".intern();
        String s1 = new StringBuilder("操作").append("系统").toString(); // 常量池中，此时拥有“操作”+“系统”，但是没有“操作系统” —— 注意：JDK1.6是复制对象，返回复制对象的引用；JDK1.7是在常量池中存储s1对象的引用；
        String s2 = new String(new StringBuilder("编译").append("原理").toString()); // 常量池中，此时拥有“编译”+“原理”，但是没有“编译原理” —— 注意：JDK1.6是复制对象，返回复制对象的引用；JDK1.7是在常量池中存储s2对象的引用；
        String s3 = new String("数据结构"); // 常量池中，此时拥有“数据结构”
        String s4 = new StringBuffer("人工").append("智能").toString();
        String s5 = new StringBuilder("机器").append("学习").toString();
        String s6 = new StringBuilder("机器学习").toString();

        System.out.println("s0.intern() == s0: " + (s0.intern() == s0)); // true
        System.out.println("s1.intern() == s1: " + (s1.intern() == s1)); // true —— 注意：JDK1.6 false；JDK1.7 true
        System.out.println("s2.intern() == s2: " + (s2.intern() == s2)); // true —— 注意：JDK1.6 false；JDK1.7 true
        System.out.println("s3.intern() == s3: " + (s3.intern() == s3)); // false —— 注意：JDK1.6 & JDK1.7都是false
        System.out.println("s4.intern() == s4: " + (s4.intern() == s4)); // true —— 注意：JDK1.6 false；JDK1.7 true，证明：StringBuffer和StringBuild操作结果均不参与常量池优化处理。
        System.out.println("s5.intern() == s5: " + (s5.intern() == s5)); // false —— 证明：当s6首次遇到“机器学习”时，已经存储常量池优化处理。
        System.out.println("s6.intern() == s6: " + (s6.intern() == s6)); // false —— 证明：无论new String还是StringBuffer和StringBuild操作，都是直接堆中新建字符串对象。
        System.out.println("s5 == s6: " + (s5 == s6)); // false

        System.out.println("s0: " + System.identityHashCode(s0));
        System.out.println("s1: " + System.identityHashCode(s1));
        System.out.println("s2: " + System.identityHashCode(s2));
        System.out.println("s3: " + System.identityHashCode(s3));
        System.out.println("s4: " + System.identityHashCode(s4));
        System.out.println("s5: " + System.identityHashCode(s5));
        System.out.println("s6: " + System.identityHashCode(s6));
    }
}
