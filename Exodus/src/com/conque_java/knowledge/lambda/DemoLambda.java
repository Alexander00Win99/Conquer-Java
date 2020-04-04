package com.conque_java.knowledge.lambda;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 【目标】：掌握Lambda表达式用法
 *
 * 【结果】：完成
 *
 * 【Lambda表达式的由来】
 * 简化代码，使得代码显得更加简洁优美。参考：https://www.jianshu.com/p/c22db2a91989 和 https://blog.csdn.net/u010386612/article/details/79875303
 *
 * 【Lambda表达式的本质】
 * 一种Java语法糖，本质是代码块(匿名函数，定义简单的算术规则)，藉此可以实现对于匿名内部类的简化表示。编译器在编译后将其自动转换成为常规代码。
 * 注：匿名内部类，一种只能使用一次即被销毁的没有名字的内部类，前提条件是要继承父类或者实现接口。
 * 希腊字母λ(lambda) + 符号名称(例如，变量x) + 点. + 表达式(expression) ==> 表达式被转换为期望一个参数的匿名函数
 * 例如：Lambda表达式 —— λx.x * x，因为是匿名函数，虽然无法引用，但可立即调用，(λx.x * x) 9 -> 81
 *
 * <JavaScript函数式编程语言(函数即上帝)——代码简洁优美>——闭包
 *  * 1) 函数定义：
 *  * function(x) {return x * x;}
 *  * 2) 立即执行函数：
 *  * function(x) {return x * x;} (9) // 81
 *  * 3) 函数变量：
 *  * var square = function(X) {return x * x;}
 *  * alert(square(6) + square(8)) // 100
 * <Java面向对象编程语言(万物皆对象)——思想清晰合理>——Lambda表达式(用于实现JS中的闭包功能，本质是函数是接口对象(SAM接口，Single Abstract Method Interface)，接口之中只能定义一个抽象方法)
 * * lambda表达式抽象的的做法是：在它的子表达式中绑定一个符号，以便使它成为一个可以替代的参数。这样的符号称为约束的（bound）。但是如果表达式中还有其他符号呢？例如λx.x/y+1。
 *  * 在这个表达式中，符号x是被lambda抽象λx约束的。但是另外一个符号y不受限制，他是自由的。我们不知道它是什么以及它来自哪里，所以我们不知道它代表什么，有什么价值，因此我们不能评估（evaluate）这个表达式直到我们能够找出y所代表的意义。
 *  *
 *  * 其他两个符号+和1也是一样。虽然我们人类对这两个符号非常熟悉，但是计算机并不知道它们，人们需要通过在某处定义它们来告诉计算机它们的含义。例如，在库中或者在语言本身内部。
 *  *
 *  * 可以想象自由符号是定义在表达式外面的地方，在它“周围的语境”（“surrounding context”）中，这个可以称为环境（environment）。环境可能是一个更大的表达式，这是表达式的一部分。或者在某个引入的库中，或者在原生的语言本身。
 *  * 对于Lambda表达式λx.x/y+1来说：{ y: 1, +: [built-in addition], 1: [built-in number], a: 0, b: 1 }是环境environment，子集{ y: 1, +: [built-in addition], 1: [built-in number] }是闭包closure。
 *  * lambda表达式的闭包是定义在外部上下文（环境）中特定的符号集，它们给这个表达式中的自由符号赋值。它将一个开放的、仍然包含一些未定义符号的lambda表达式变为一个关闭的lambda表达式，使得这个lambda表达式不再具有任何自由符号。
 *  * lambda表达式分为两类：
 *  * CLOSED expressions：这些表达式中出现的每一个符号都受到一些lambda抽象的约束。换句话说，它们是自己自足的，不需要评估任何周边语境。它们也被称为Combinators。
 *  * OPEN expressions：这些表达式中的某些符号没有约束，也就是说，它们中的一些符号是自由的，它们需要一些外部信息，因此只有在提供这些符号的定义后才能对它们进行评估。
 *  * 人们可以通过提供一个环境来关闭一个开放的lambda表达式，该环境通过将所有的自由符号绑定到某些值（可能是数字，字符串，匿名函数或者说lambda等）来定义它们。
 *
 * 注：lambda表达式的闭包是其环境中定义的一个子集，它给包含在lambda表达式中的自由变量赋值，从而有效的关闭表达式。
 *
 * 【Lambda表达式的结构】
 * Left: 参数列表 + Middle: ->箭头运算符 + Right：功能代码执行体(Lambda体)
 * 无参只留括号：() -> 1;
 * 只有一个参数括号可以省略：(x) -> 10 * x; x -> 10 * x;
 * 多参括号不能省略：(x, y) -> x + y;
 *
 * 【方法引用三种结构】——调用者和方法之间使用::进行分割
 * object::instanceMethod 对象的实例方法
 * Class::staticMethod 类的静态方法
 * Class::instanceMethod 类的实例方法
 * 例如：(int x, int y) -> Math.max(x, y) => Math::max;
 * 注：this和super也可用于方法引用
 */
public class DemoLambda {
    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("Alexander00Win99 is dominant!");
        }
    };

    static abstract class Person {
        abstract void eat();
    }

    static class Infant extends Person {
        @Override
        void eat() {
            System.out.println("Infant suck milk!");
        }
    }

    static class Student {
        public void learn() {
            System.out.println("学生的天职就是学习！");
        }
    }

    static class Undergraduate extends Student {
        @Override
        public void learn() { // Inner classes cannot have static declarations(non-static inner-class + static-method)
            Thread thread = new Thread(super::learn);
            System.out.println("Begin-方法引用");
            thread.start();
            System.out.println("End-方法引用");
        }
    }

    public static void main(String[] args) {
        System.out.println("++++++++++++++++Begin-Lambda表达式的由来：代码简化过程++++++++++++++++");
        List<String> stringList = Arrays.asList("Alex Wen", "Alexander Wen", "Alexander 温");
        // 为了实现使用逗号分隔方式打印stringList各个元素
        // 1) 增强for循环无法去除最后一个元素后面的","
        System.out.println("1) 增强for循环无法去除最后一个元素后面的\",\"：");
        for (String s : stringList)
            System.out.print(s + ", ");
        System.out.println();
        // 2) for循环引入累赘局部变量i，并且需要频繁使用.size()方法
        System.out.println("2) for循环引入累赘局部变量i：");
        for (int i = 0; i < stringList.size() - 1; i++)
            System.out.print(stringList.get(i) + ", ");
        if (stringList.size() > 0)
            System.out.println(stringList.get(stringList.size() - 1));
        // 3) 利用String.join方法优雅实现
        System.out.println("3) 利用String.join方法优雅实现：");
        System.out.println(String.join(", ", stringList));
        // 4) Lambda表达式的复杂形式
        System.out.println("4) Lambda表达式的复杂形式：");
        stringList.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.print(s + ", ");
            }
        });
        System.out.println();
        // 5) Lambda表达式的简易形式
        System.out.println("5) Lambda表达式的简易形式：");
        stringList.forEach((String s) -> System.out.println(s));
        // 6) Lambda表达式的极简形式
        System.out.println("6) Lambda表达式的极简形式：");
        stringList.forEach(System.out::println);
        // 7) 不用匿名内部类
        System.out.println("7) 不用匿名内部类：");
        Person p = new Infant();
        p.eat();
        // 8) 使用匿名内部类
        System.out.println("8) 使用匿名内部类：");
        Person infant = new Person() {
            @Override
            void eat() {
                System.out.println("婴儿喝奶!");
            }
        };
        infant.eat();
        System.out.println("----------------End-Lambda表达式的由来：代码简化过程----------------");

        System.out.println("++++++++++++++++Begin-数组拷贝方法++++++++++++++++");
        int[][] origin = new int[][]{{0, 1, 2, 3}, {4, 5, 6}, {7, 8}, {9}, {}};
        int[][] target = new int[origin.length][];
        for (int i = 0; i < origin.length; i++)
            target[i] = origin[i].clone();
        System.out.println(Arrays.deepToString(target));
        target[3][0] = 81;
        //taget[4][0] = 99; // 报错：java.lang.ArrayIndexOutOfBoundsException: 0
        System.out.println(Arrays.deepToString(target));
        System.out.println(Arrays.deepToString(origin));

        String[] strings = {"0123456789", "Alexander00Win99", "Alex Wen", "Alexander 温", "ABCDEFG"};
        System.out.println("0) 原始字符串：");
        System.out.println("新的字符串数组：" + Arrays.deepToString(strings));

        System.out.println("1) 通过Object.clone()进行对象深拷贝+数组浅拷贝：");
        String[] strArr = strings.clone();
        System.out.println("新的字符串数组：" + Arrays.deepToString(strArr));

        System.out.println("2) 通过System.arraycopy()进行浅拷贝：");
        String[] strArray = new String[strings.length];
        System.arraycopy(strings, 0, strArray, 0, strings.length);
        System.out.println("新的字符串数组：" + Arrays.deepToString(strArray));

        System.out.println("3) 通过Arrays.copyOf()进行对象浅拷贝：");
        String[] stringArr = Arrays.copyOf(strings, strings.length);
        System.out.println("新的字符串数组：" + Arrays.deepToString(stringArr));

        String[] stringArray = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            try {
                byte[] bytes = strings[i].getBytes("UTF-8");
                System.out.println("若未重写Object.toString()方法，一般返回getClass().getName() + '@' + Integer.toHexString(hashCode())形式：" + bytes.toString());
                stringArray[i] = new String(bytes); // 无法通过objString.copyValueOf(char[], start, end)进行赋值，因为要求char[]参数，byte[]并不符合。
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("新的字符串数组：" + Arrays.toString(stringArray));
        System.out.println("----------------End-数组拷贝方法----------------");

        System.out.println("++++++++++++++++Begin-Lambda表达式的结构演化++++++++++++++++");
        // 1) 内部类方式
        /**
         *     public static int compare(int x, int y) {
         *         return (x < y) ? -1 : ((x == y) ? 0 : 1);
         *     }
         */
        class LengthComparator implements Comparator<String> {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        }
        Arrays.sort(strings, new LengthComparator());
        System.out.println("1) 内部类方式：");
        System.out.println("根据长度从小到大进行排序：");
        System.out.println("strings排序以后：" + Arrays.toString(strings));

        // 2) 匿名内部类
        Comparator<String> cmp = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
        Arrays.sort(strArr, cmp);
        System.out.println("2) 匿名内部类：");
        System.out.println("根据ASCII码表顺序进行排序：");
        System.out.println("strArr排序以后：" + Arrays.toString(strArr));

        // 3) Lambda表达式
        //Comparator<String> comparator = (String o1, String o2) -> Integer.compare(o1.length(), o2.length());
        Arrays.sort(strings, (String o1, String o2) -> Integer.compare(o2.length(), o1.length()));

        // 参数类型之前支持修饰符(例如，final)和注解
        Comparator<String> comparator = (final String o1, final String o2) -> { // Lambda执行体代码多于一行，必须使用{}包括起来
            // 根据长度从小到大进行排序
            if (o1.length() < o2.length()) return -1;
            else if (o1.length() > o2.length()) return 1;
            else return 0;
        };
        Arrays.sort(strArr, (o1, o2) -> { // 当参数类型可以预测时，参数类型亦可省略
            if (o1.length() < o2.length()) return -1;
            else if (o1.length() > o2.length()) return 1;
            else return 0; // Lambda表达式必须覆盖所有分支的返回值，否则报错："Missing return statement"
        });
        System.out.println("3) Lambda表达式：");
        System.out.println("根据长度从小到大进行排序：");
        System.out.println("strArr排序以后：" + Arrays.toString(strArr));

        // A. Lambda表达式演进——无参无返回值
        runnable.run();
        Runnable runnable = () -> System.out.println("Hello world! 我是采用Lambda表达式实现的匿名函数类！");
        runnable.run();
        //new Thread(runnable).start();
        // B. Lambda表达式演进——无参有返回值
        // () -> 81;
        // C. Lambda表达式演进——有参有返回值
        // 当有且仅有一个参数并且类型可以推断出来时，括号可以省略。
        // (int x) -> 9 * x;
        // x -> 9 * x;
        System.out.println("排序之前：");
        System.out.println(Arrays.toString(strArray));
        Arrays.sort(strArray, (o1, o2) -> o2.length() - o1.length()); // 根据长度从大到小进行排序
        System.out.println("根据长度从大到小进行排序：");
        System.out.println("strArray排序以后：" + Arrays.toString(strArray));

        System.out.println("排序之前：");
        System.out.println(Arrays.toString(stringArr));
        Arrays.sort(stringArr, (o1, o2) -> o1.compareTo(o2)); // 根据顺序进行从前到后排序
        System.out.println("根据ASCII码表顺序进行排序：");
        System.out.println("stringArr排序以后：" + Arrays.toString(stringArr));

        // D. Lambda表达式演进——方法引用
        /**
         * 【方法引用三种结构】
         * object::instanceMethod 对象的实例方法
         * Class::staticMethod 类的静态方法
         * Class::instanceMethod 类的实例方法
         * 例如：(int x, int y) -> Math.max(x, y) => Math::max;
         */
        Consumer<String> consumer1 = x -> System.out.println(x);
        consumer1.accept("Lambda表达式：常见形式！");
        // 当Lambda表达式执行体中的方法参数与Lambda表达式参数相同时，参数可以隐藏省略
        Consumer<String> consumer2 = System.out::println;
        consumer2.accept("Lambda表达式：使用::进行省略！");
        System.out.println("----------------End-Lambda表达式的结构演化----------------");

        System.out.println("++++++++++++++++Begin-Lambda表达式的应用：方法引用++++++++++++++++");
        Student student = new Undergraduate();
        student.learn();
        System.out.println("----------------End-Lambda表达式的应用：方法引用----------------");

        System.out.println("++++++++++++++++Begin-Lambda表达式的本质：SAM接口对象++++++++++++++++");
        // 0) 自定义函数式接口SAM
        MyService service = System.out::println;
        service.print("Hello World! SAM interfaces!");
        // 1) 消费型接口Consumer<T>——有参数无返回值，通过accept()方法调用
        consume("Alex Wen", str -> System.out.println("Go Fucking! " + str));
        // 2) 函数型接口Funtion<T, R>——有参数有返回值，通过apply()方法调用
        String funcResult = subStr("月亮不睡我也不睡", str -> str.substring(0, str.length() / 2)); // 注意截取范围，否则报错：java.lang.StringIndexOutOfBoundsException: String index out of range: 9
        System.out.println(funcResult);
        // 3) 供应型接口Supplier<T>——无参数有返回值，通过get()方法调用
        String getResult = getSupp(() -> UUID.randomUUID().toString());
        System.out.println(getResult);
        // 4) 断言型接口Predicate<T>——有参数固定返回值boolean类型，通过test()方法调用，通常用于代码测试
        boolean assertion = beginsWith("alexander00win99", str -> str.startsWith("alex"));
        System.out.println(assertion);
        System.out.println("----------------End-Lambda表达式的本质：SAM接口对象----------------");
    }

    public static void consume(String str, Consumer<String> consumer) {
        consumer.accept(str);
    }

    public static String subStr(String str, Function<String, String> function) {
        return function.apply(str);
    }

    public static String getSupp(Supplier<String> supplier) {
        return supplier.get();
    }

    public static boolean beginsWith(String str, Predicate<String> predicate) {
        return predicate.test(str);
    }
}

interface MyService {
    void print(String message); // public static abstract报错：Illegal combination of modifiers: 'abstract' and 'static'
}