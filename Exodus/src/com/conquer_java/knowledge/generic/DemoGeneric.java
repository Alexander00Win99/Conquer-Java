package com.conquer_java.knowledge.generic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 参考：https://www.cnblogs.com/afraidToForget/p/10727014.html
 * 【泛型的历史】
 * JDK1.5引入，之前类型是原生类型(Raw Type)，之后类型是泛型(Generic Type)。
 *
 * 【泛型的使用目的】
 * 参数类型化(使得代码可以适用各种不同类型对象，便于代码重用)
 * 对比《方法定义中的方法参数》：声明时定义形参，使用时传递实参。==>方法参数是对数据的抽象，或说变量的统一。
 * 泛型是对数据类型的抽象，或说统一。尚未定型时保证泛化统一，业已定型时保证具体限制。
 *
 * 【泛型的生命周期】
 * 编译时期定义泛型；运行时期使用泛型。
 *
 * 【泛型的使用场景】
 * 泛型类 + 泛型接口 + 泛型方法
 *
 * 【泛型的特点】——体现Java的多态特性
 * 1) Java泛型是伪泛型，存在类型擦除现象：编译期间泛型的类型信息会被编译器擦除，生成的字节码中并不包含泛型的类型信息，因此，JVM无法辨别泛型的类型信息，运行期间使用泛型再行加上类型参数，此即Java泛型的类型擦除，
 * 也是Java泛型与C++模板机制实现方式之间的重要区别。
 * 2) 如果一个类型定义了类型变量(TypeVariable)，那么即可通过Class的getTypeParameters()方法获取，每个类型变量有其符号和上边界(例如，<T extends Object>中的T和Object)，
 * 类型变量的默认上边界是Object。虽然类型变量在运行期间没有下边界的定义，但是如果上边界类型使用final修饰，那么类型变量的下边界就等同于上边界。
 * PS: 原始类型是类型擦除以后，字节码中的类型变量的真正类型，无论何时何处定义泛型，相应的原始类型都会自动提供，类型擦除以后，使用其对应的限定类型进行替换(无限定类型变量使用Object替换)。
 * 例如：
 * 3) 泛型没有协变性特征(数组具有协变性特征，例如：Object是Number父类，Object[]也是Number[]父类，可以父类引用指向子类实例)，Object是Number父类，但是List<Object>和List<Integer>没有父子关系。
 * PS: 协变函数f(x)具有如下特点：如果r(a, b)关系成立，那么r(f(a), f(b))关系同样成立。
 *
 * 【泛型的优点】
 * 保证编译安全；避免类型转化；提高代码效率；
 */
public class DemoGeneric {
    static class Demo<T extends Number> { // Java通常使用诸如T(Type)、E(Element)、K(Key)、V(Value)等任意符号表示泛型参数，
        private T data;

        public Demo() {}

        public Demo(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    static class Example<T> {
        private T data;

        public Example() {}

        public Example(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static void main(String[] args) {
        System.out.println("++++++++++++++++Java泛型引入原因++++++++++++++++");
        List list = new ArrayList(); // 默认Object类型
        list.add("Alexander Wen");
        list.add(18); // 默认类型Object可以存入任何类型
        /**
         * JDK1.4引入泛型之前，程序若需泛化表示某个类对象，只能使用Object对象，如此存在缺点：虽然编译阶段正常，但是运行阶段报错。
         * 数据写入(集合存入对象)缺少类型检查，数据读出(集合取出对象)需要进行类型强转，容易产生Exception，代码缺乏健壮性。
         */
        //for (int i = 0; i < list.size(); i++) {
            //System.out.println((String) list.get(i)); // 运行报错：java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
        //}
        List<String> stringList = new ArrayList(); // 默认类型是Object，泛型只能是引用类型，不能是8种基本类型。
        stringList.add("Alexander Wen");
        /**
         * 一旦集合声明使用泛型进行类型限制，编译器就会记忆集合元素的数据类型，存入阶段只能存储指定类型元素，取出阶段自然也就无须进行强制类型转换。
         */
        //stringList.add(18);
        System.out.println("----------------Java泛型引入原因----------------");

        System.out.println("++++++++++++++++Java泛型不具协变性++++++++++++++++");
        // 1) 数组具有协变性
        Object[] objects = new Object[10];
        Number[] numbers = new Number[10];
        //objects = numbers; // 正确：数组具有协变性，因为Object和Number是父子关系，所以Object[]和Number[]依旧是父子关系
        //numbers = objects; // 报错：Incompatible types，子类引用不可指向父类对象

        // 2) 泛型不具协变性
        List<Object> objectList = new ArrayList<>();
        List<Number> numberList = new ArrayList<>();
        //objectList = numberList; // 报错：泛型不具协变性，虽然Object和Number是父子关系，但是List<Object>和List<Number>不是父子关系
        List<Double> doubleList = new ArrayList<>();
        List<Float> floatList = new ArrayList<>();
        //doubleList = floatList; // 报错：Incompatible types
        System.out.println(floatList.getClass() == doubleList.getClass()); // 类型擦除导致此处相等成立，原始类型都是Object
        System.out.println("----------------Java泛型不具协变性----------------");

        System.out.println("++++++++++++++++Java泛型的使用传参++++++++++++++++");
        /**
         * 泛型定义以后，在使用时，并非要求必须传参，如果传参，那么泛型的类型限制作用才会生效，否则，定义部分设计的数据类型可以使用任何引用类型。
         */
        Example<Float> floatExample = new Example<>(123.456F);
        System.out.println(floatExample.getData());
        Example<Double> doubleExample = new Example<>(123.456D);
        System.out.println(doubleExample.getData());
        Example integerExample = new Example(123456);
        System.out.println(integerExample.getData());
        Example stringExample = new Example("Hello World!");
        System.out.println(stringExample.getData());
        System.out.println("----------------Java泛型的使用传参----------------");

        System.out.println("++++++++++++++++Java泛型的类型变量++++++++++++++++");
        TypeVariable[] typeVariables = Demo.class.getTypeParameters();
        TypeVariable typeVariable = typeVariables[0];
        System.out.println("类型变量的符号是：" + typeVariable.getName());
        System.out.println("类型变量的边界是：" + Arrays.toString(typeVariable.getBounds()));
        System.out.println("----------------Java泛型的类型变量----------------");

        System.out.println("++++++++++++++++Java泛型上下边界的范围界定++++++++++++++++");
        List<Integer> integerList = new ArrayList<>();
        List<? extends Number> listUpToNumber = new ArrayList<>(); // <? extends Number>代表Number的子类，Number >= <? extends Number>
        List<? super Integer> listDownToInteger = new ArrayList<>(); // <? super Integer>表示Integer的父类，<? super Integer> >= Integer

        Integer integer = new Integer(1);
        Number number = new Double(1.0D);

        integerList.add(integer);
        //listUpToNumber.add(integer); // NOK，Integer只是<? extends Number>的一种可能
        listUpToNumber.add(null); // OK，null适用任何类型，包括<? extends Number>
        //listDownToInteger.add(integer); // OK，<? super Integer> >= Integer
        listDownToInteger.add(null); // OK，null适用任何类型，包括<? super Integer>
        //Number num = listUpToNumber.get(0); // OK，Number >= <? extends Number>
        //Integer i = listDownToInteger.get(0); // NOK，<? super Integer> >= Integer

        // 泛型方法使用上边界定义
        Method addMethod4List = null;
        try {
            addMethod4List = integerList.getClass().getMethod("add", Object.class);
            addMethod4List.invoke(integerList, "Alexander00Win99");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 运行时类型变量只有上边界
        for (Object o : integerList) {
            System.out.println(o);
        }
        System.out.println("----------------Java泛型上下边界的范围界定----------------");

        System.out.println("++++++++++++++++Java泛型的类型擦除++++++++++++++++");
        /**
         * Java泛型只在编译阶段有效，编译结束，擦除泛型，在泛型类|泛型接口|泛型方法中，在对象写入或者读出泛型集合|进入或者离开泛型方法时，
         * 进行类型检查以及类型转换。
         * 泛型在使用时，并非强制要求一定传入类型实参，如果传入，需要符合在泛型定义时的类型限制，如果不传，可以使用任意类型。
         */
        DemoGeneric.Demo<Float> floatDemo = new DemoGeneric.Demo<>(123.456F);
        Demo<Double> doubleDemo = new Demo<>(123.456D);
        System.out.println(floatDemo.getData());
        System.out.println(doubleDemo.getData());
        System.out.println("floatDemo.getData() instanceof Float: " + (floatDemo.getData() instanceof Float));
        System.out.println("doubleDemo.getData() instanceof Double: " + (doubleDemo.getData() instanceof Double));
        Demo demo = new Demo(123456); // 如果不传泛型实参，可以接受任何类型。
        System.out.println("----------------Java泛型的类型擦除----------------");

        System.out.println("++++++++++++++++Java泛型的范围界定++++++++++++++++");
        System.out.println("----------------Java泛型的范围界定----------------");
    }
}
