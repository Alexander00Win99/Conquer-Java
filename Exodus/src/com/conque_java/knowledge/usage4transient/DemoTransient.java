package com.conque_java.knowledge.usage4transient;

import java.io.*;

/**
 * 序列化两种方式：
 * 1)实现Serializable接口；
 * 2)实现Externalizable接口(重写readExternal和writeExternal方法，其中可以手动指定某个或者某些属性需要序列化——即使transient修饰也是无济于事)。
 *
 * Java的serialization机制提供了存储对象状态的机制，但是，可能对象的某些属性(例如，银行卡号)需要避免网络传输，因此需要使用transient关键字加以修饰。
 * transient关键字的作用：修饰属性的生命周期仅仅存在调用者的内存中，而非持久化到磁盘上。
 * 注意：静态属性无法序列化(即使没有transient修饰)，原因在于：静态属性存储在方法区而非磁盘上，。
 * 参考：https://baijiahao.baidu.com/s?id=1636557218432721275&wfr=spider&for=pc
 */
public class DemoTransient {
    private static void serializePerson() throws IOException {
        Person p = new Person();
        p.setName("Alex Wen");
        p.setAge(18);
        p.setSex("male");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\Workspace\\Java\\serialize\\person"));
        oos.writeObject(p);
        oos.close();
        System.out.println("使用transient关键字修饰Person类的age属性，实例p序列化之前结果：age=" + p.getAge());
        System.out.println("使用static transient关键字修饰Person类的sex属性，实例p序列化之前结果：sex=" + p.getSex());
    }

    private static void deSerializePerson() throws IOException, ClassNotFoundException {
        Person.setSex("female");
        File file = new File("D:\\Workspace\\Java\\serialize\\person");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Person person = (Person) ois.readObject();
        System.out.println("使用transient关键字修饰Person类的age属性，实例p序列化之后结果：age=" + person.getAge());
        System.out.println("使用static transient关键字修饰Person类的sex属性，实例p序列化之后结果：sex=" + person.getSex());
    }

    private static void serializeUser() throws IOException {
        User u = new User();
        u.setName("Alex Wen");
        u.setAge(36);
        u.setSex("male");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\Workspace\\Java\\serialize\\user"));
        oos.writeObject(u);
        oos.close();
        System.out.println("使用transient关键字修饰User类的age属性，实例u序列化之前结果：对象u=" + u.toString());
        System.out.println("使用static transient关键字修饰User类的sex属性，实例u序列化之前结果：sex=" + User.getSex());
    }

    private static void deSerializeUser() throws IOException, ClassNotFoundException {
        System.out.println(User.getSex());
        File file = new File("D:\\Workspace\\Java\\serialize\\user");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        User user = (User) ois.readObject();
        System.out.println("使用transient关键字修饰User类的age属性，实例u序列化之后结果：对象user=" + user.toString());
        System.out.println("使用static transient关键字修饰User类的sex属性，实例u序列化之后结果：sex=" + User.getSex());
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        serializePerson();
        deSerializePerson();

        serializeUser();
        deSerializeUser();
    }
}
