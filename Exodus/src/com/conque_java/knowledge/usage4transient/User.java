package com.conque_java.knowledge.usage4transient;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class User implements Externalizable {
    private static final long serialVersionID = 12345678L;
    private String name;
    private transient int age;
    private static transient String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static String getSex() {
        return sex;
    }

    public static void setSex(String sex) {
        User.sex = sex;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "name='" + name + '\'' +
//                ", age=" + age +
//                ", sex='" + sex + '\'' +
//                '}';
//    }

    // static属性sex，JDK的toString()自动无视
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        age = (int) in.readObject();
        // 即使Serializable接口无法序列化static属性，Externalizable接口也可在readExternal/writeExternal方法强行写入static属性，并且产生效果。
        sex = (String) in.readObject();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(age);
        // 即使Serializable接口无法序列化static属性，Externalizable接口也可在readExternal/writeExternal方法强行写入static属性，并且产生效果。
        out.writeObject(sex);
    }
}
