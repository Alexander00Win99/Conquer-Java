package com.conquer_java.knowledge.serialization;

import sun.misc.IOUtils;

import java.io.*;
import java.util.ArrayList;

/**
 * 序列化两种方式：
 * 1)实现Serializable接口；
 * 2)实现Externalizable接口(重写readExternal和writeExternal方法，其中可以手动指定某个或者某些属性需要序列化——即使transient修饰也是无济于事)。
 *
 * 注：
 * Serializable接口没有任何方法，只是表明将来可被“序列化”；
 * Externalizable extends java.io.Serializable，其中定义，readExternal + writeExternal方法，用于程序员自行控制哪些状态属性可被进行序列化。
 *
 * Java的serialization机制提供了存储对象状态的机制，但是，可能对象的某些属性(例如，银行卡号)需要避免网络传输，因此需要使用transient关键字加以修饰。
 * transient关键字的作用：修饰属性的生命周期仅仅存在调用者的内存中，而非持久化到磁盘上。
 * 注意：静态属性无法序列化(即使没有transient修饰)，原因在于：静态属性存储在方法区而非磁盘上。
 */
public class DemoTransient {
    private static void serializePerson() throws IOException {
        Person p = new Person();
        p.setName("Alex Wen");
        p.setAge(18);
        p.setSex("male");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\Workspace\\Java\\output\\serialize\\person"));
        oos.writeObject(p);
        oos.close();
        System.out.println("使用transient关键字修饰Person类的age属性，实例p序列化之前结果：age=" + p.getAge());
        System.out.println("使用static transient关键字修饰Person类的sex属性，实例p序列化之前结果：sex=" + p.getSex());
    }

    private static void deSerializePerson() throws IOException, ClassNotFoundException {
        Person.setSex("female");
        System.out.println("序列化之后反序列化之前，修改静态属性'Person.sex'为'female'！");
        File file = new File("D:\\Workspace\\Java\\output\\serialize\\person");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Person person = (Person) ois.readObject();
        System.out.println("使用transient关键字修饰Person类的age属性，实例p序列化之后结果：age=" + person.getAge());
        System.out.println("使用static transient关键字修饰Person类的sex属性，实例p序列化之后结果：sex=" + person.getSex());
        if ("female".equals(person.getSex()))
            System.out.println("对象属性取值类静态属性，说明：静态属性不会被序列化！");
    }

    private static void serializeUser() throws IOException {
        User u = new User();
        u.setName("Alex Wen");
        u.setAge(36);
        u.setSex("male");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\Workspace\\Java\\output\\serialize\\user"));
        oos.writeObject(u);
        oos.close();
        System.out.println("使用transient关键字修饰User类的age属性，实例u序列化之前结果：对象u=" + u.toString());
        System.out.println("使用static transient关键字修饰User类的sex属性，实例u序列化之前结果：sex=" + User.getSex());
    }

    private static void deSerializeUser() throws IOException, ClassNotFoundException {
        User.setSex("female");
        System.out.println("序列化之后反序列化之前，修改静态属性'User.sex'为'female'！");
        File file = new File("D:\\Workspace\\Java\\output\\serialize\\user");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        User user = (User) ois.readObject();
        System.out.println("使用transient关键字修饰User类的age属性，实例u序列化之后结果：对象user=" + user.toString());
        System.out.println("使用static transient关键字修饰User类的sex属性，实例u序列化之后结果：sex=" + User.getSex());
        System.out.println("静态属性'User.sex': " + User.getSex());
        if ("male".equals(user.getSex()))
            System.out.println("说明：虽然静态属性正常不会被序列化，但是只要readExternal和writeExternal设置即可实现序列化！");

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("++++++++++++++++使用Serializable序列化++++++++++++++++");
        serializePerson();
        deSerializePerson();
        System.out.println("----------------使用Serializable序列化----------------");

        System.out.println("++++++++++++++++使用Externalizable序列化++++++++++++++++");
        serializeUser();
        deSerializeUser();
        System.out.println("----------------使用Externalizable序列化----------------");
        new ArrayList<Integer>();
    }
}
