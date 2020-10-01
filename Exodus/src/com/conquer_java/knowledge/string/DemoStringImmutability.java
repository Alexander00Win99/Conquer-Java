package com.conquer_java.knowledge.string;

/**
 * [Java运行时数据区结构]-Java内存模型
 * 栈：存放基本类型变量的数值以及引用类型变量的地址引用；
 * 堆：存放new()创建的对象；
 * 方法区的常量池：存放基本类型常量和字符串常量；
 *
 * intern()作用：如果常量池中没有该字符串，写入；如果有，返回引用地址。
 *
 * String str = "Hello World!"; // 字面量(直接量)赋值形式：若常量池中已有该字符串，则直接返回引用地址；否则新建返回； => 创建0个或者1个对象；
 * String str = new String("Hello World!"); // new新建对象形式：首先堆中新建一个String对象，然后在常量池中查找是否存在相同内容字符串，无则新建，有则忽略； => 创建1个或者2个对象；
 * 【结论】：推荐使用字面量(直接量)赋值形式，可以节省内存。
 *
 * 【Why String is immutable in Java?】
 * 1) 只有字符串是不可变的，字符串池才有可能实现。字符串池的实现可以在运行时节约很多heap空间；
 * 2) 如果字符串是可变的，那么可能引起严重的安全问题。譬如，数据库的用户名、密码；socket的主机名、端口；
 * 3) 字符串是线程安全的，不用因为线程安全问题而使用同步；
 * 4) 类加载器使用字符串，不可变性可以保证安全性；
 * 5) 因为字符串是不可变的，所以在创建时hashcode即被缓存，无需重新计算。因此，字符串非常适合作为Map中的键，字符串的处理速度要远远快过其它类型键对象，HashMap中的键通常都是使用字符串。
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
//        str1.intern(); // 将堆中"Hello World!"字符串对象地址写入常量池中
//        String str2 = "Hello World!";
//        System.out.println(str1 == str2); // true

//        String str1 = new String("Hello ") + new String("World!"); // 堆中3个对象："Hello "|"World!"|"Hello World!" + 常量池中2个对象："Hello "|"World!"
//        String str2 = "Hello World!";
//        str1.intern(); // 常量池中str2="Hello World!"字符串已经存在，返回常量池中"Hello World!"字符串引用地址，但是无人接收
//        System.out.println(str1 == str2); // false

        String str1 = new String("Hello ") + new String("World!"); // 堆中3个对象："Hello "|"World!"|"Hello World!" + 常量池中2个对象："Hello "|"World!"
        String str2 = "Hello World!";
        str1 = str1.intern(); // 常量池中str2="Hello World!"字符串已经存在，返回常量池中"Hello World!"字符串引用地址，str1接收返回的地址引用
        System.out.println(str1 == str2); // true
    }
}
