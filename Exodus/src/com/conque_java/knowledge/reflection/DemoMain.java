package com.conque_java.knowledge.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 【通常的抽象】
 * 通常来说，抽象是指在认识上把事物的规定、属性、关系从原来有机联系的整体中孤立地抽取出来；具体是指尚未经过这种抽象的感性对象。
 *
 * 【黑格尔的抽象】
 * 黑格尔承认前述所说抽象为抽象，但是并不承认感性对象为具体，认为具体是理性的具体，是以概念为本质的一切事物的多方面的规定、属性、关系的有机整体性，
 * 以及它们在认识中的反映。黑格尔在历史上首次按照上述含义，使用抽象和具体这对范畴，把孤立、割裂、片面这类思想方法称为抽象，把统一(对立面的统一、不同规定性的统一、普遍和特殊的统一)
 * 作为具体的根本特征，主张世界上客观存在的真实事物、概念、真理都是具体的，都是不同规定性的有机统一体，没有那种抽象的、孤立的、非此即彼的东西。
 *
 * 黑格尔认为，具体性是概念、真理最基本的特性，哲学的目标就是掌握具体真理、具体概念，为此，必须经历一个辩证发展的过程，也即由抽象到具体的过程。
 * 认识开始只能得到一些抽象的规定性，它们是孤立的、片面的。随着认识的前进，愈是在后的概念所包含的规定性愈多，因而内容愈丰富、愈具体、愈真实。
 * 最终的“绝对理念”就是一个最为具体的概念。
 *
 * 【辩证唯物主义的抽象】
 * 马克思在辩证唯物主义基础上对抽象和具体这对范畴作出了科学表述，认为人对客观事物的认识是在实践的基础上，由感性的具体上升为理性的抽象，
 * 进而对于各种抽象的规定通过更深刻的思维加工，达到具体的再生产，由理性的抽象上升到理性的具体，从而把握事物的内在联系和本质的过程。
 *
 * 任何事物都是共性和个性的统一，也即矛盾普遍性和特殊性的统一。矛盾的共性指矛盾的普遍性，是绝对的、无条件的；矛盾的个性指矛盾的特殊性，是相对的、有条件的。
 * 矛盾的共性与个性的辩证关系是指，共性寓于个性之中，个性又受共性的制约，共性和个性在一定条件下相互转化。
 *
 * 【类的抽象】
 * 类是对象的抽象(对象是类的实例)，类是所有具有一定共性的个体对象的抽象描述，使用属性描述特征，使用方法描述能力。
 *
 * 【反射的抽象】
 * 反射是Java语言对于所有类的抽象，使用Class|Interface|Field|Method|Constructor|Package六个独立类型描述类(Primitive Type基本类型也可描述)
 *
 * 【Modifier权值】
 * 0-DEFAULT 1-PUBLIC 2-PRIVATE 4-PROTECTED 8-STATIC 16-FINAL 32-SYNCHRONIZED 64-VOLATILE 128-TRANSIENT 256-NATIVE 512-INTERFACE 1024-ABSTRACT 2048-STRICT
 *
 */
public class DemoMain {
    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("com.conque_java.knowledge.reflection.People");
            System.out.println(clazz);
            System.out.println(clazz.getSuperclass());
            People people = (People) clazz.newInstance();
            System.out.println("Instantiation for class " + clazz + " is as blow:");
            System.out.println(people);

            System.out.println(clazz.getFields().length);
            Field[] fs = clazz.getDeclaredFields();
            System.out.println(fs.length);
            for (Field f : fs) {
                System.out.println(f);
            }
            Field fName = clazz.getDeclaredField("name");
            fName.setAccessible(true);
            fName.set(people, "Alexander 温");
            Field fAge = clazz.getDeclaredField("age");
            fAge.setAccessible(true);
            fAge.set(people, 18);
            Field fSex = clazz.getDeclaredField("sex");
            fSex.setAccessible(true);
            fSex.set(people, true);
            System.out.println(people);

            fName.set(people, "Alex Wen");
            System.out.println(people);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Class peopleClass = People.class;
        System.out.println(peopleClass);
        System.out.println(peopleClass.getModifiers());

        People p = new People("Alexander 温", 18, true);
        Class pClass = p.getClass();
        System.out.println(pClass);
        System.out.println(pClass.getName());
        System.out.println(pClass.getPackage());
        System.out.println(pClass.getSimpleName());

        ArrayList<String> list = new ArrayList<>();
        Class listClass = ArrayList.class;
        System.out.println("The hierarchy of inherited class of " + listClass + " is:");
        Class c = listClass.getSuperclass();
        while (c != null) {
            System.out.println(c);
            c = c.getSuperclass();
        }
        System.out.println("The interfaces implemented by class of " + listClass + " is:");
        Class[] interfaces = listClass.getInterfaces();
        for (Class anInterface : interfaces) {
            System.out.println(anInterface);
        }

        String fixedString = "xyz";
        System.out.println(fixedString);
        Class strCls = String.class;
        try {
            Field field = strCls.getDeclaredField("value");
            field.setAccessible(true);
            char[] value = (char[]) field.get(fixedString);
            System.out.println(value.length);
            value[0] = '温';
            value[1] = '瑞';
            value[2] = '枫';
//            value[3] = '!';
            System.out.println(fixedString);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
