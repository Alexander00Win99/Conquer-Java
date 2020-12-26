package com.conquer_java.knowledge.boxing;

public class DemoBoxing {
    /**
     * 【使用场景】
     * 算术运算+ - * /：拆箱
     * 比较运算> >= <= < != ==：拆箱
     * equals()：装箱
     * 集合类型之中添加基础类型元素List<WrapperClass>.add(PrimitiveType)：装箱
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("++++++++++++++++Begin：比较运算导致拆箱++++++++++++++++");
        Integer a = 0;
        Integer b = new Integer(0);
        int c = 0;
        Integer d = c;
        System.out.println("a == b: " + (a == b));
        System.out.println("a == c: " + (a == c)); // 比较运算导致拆箱
        System.out.println("a == d: " + (a == d));
        System.out.println("----------------End：比较运算导致拆箱----------------");

        /**
         * 当调用valueOf()方法进行装箱操作时，尝试使用Integer的缓存（[-128, 127]），如果位于范围之内，直接取用，否则新建对象。
         */
        System.out.println("++++++++++++++++Begin：Integer缓存++++++++++++++++");
        Integer d1 = 81, d2 = 81, d3 = 256, d4 = 256;
        System.out.println("d1 == d2: " + (d1 == d2)); // true，缓存范围之内
        System.out.println("d3 == d4: " + (d3 == d4)); // false，缓存范围之外
        Integer.valueOf(10);
        System.out.println("----------------End：Integer缓存----------------");

        /**
         * i3 == i1 + i2 表达式中算术运算自动触发i1和i2的自动拆箱动作（调用intValue()方法）,同样比较运算触发i3的拆箱动作。
         * i3.equals(i1 + i2) 先+触发i1和i2拆箱动作使用intValue()获取数值，计算结合结果为3，再equals()触发装箱动作使用valueOf()包装为Integer(3)对象和i3进行比较，结果为true。
         */
        System.out.println("++++++++++++++++Begin：equals()触发装箱++++++++++++++++");
        Integer i1 = 1, i2 =2, i3 =3;
        System.out.println("i3 == i1 + i2: " + (i3 == i1 + i2)); // true
        System.out.println("i3.equals(i1 + i2): " + (i3.equals(i1 + i2))); // true
        System.out.println("----------------End：equals()触发装箱----------------");

        System.out.println("++++++++++++++++Begin：基本类型之间比较触发类型降级和类型升级++++++++++++++++");
        Long l1 = 3L, l2 = 2L;
        System.out.println("l1 == 3.0D: " + (l1 == 3.0D));
        System.out.println("3.0 == 1 + 2: " + (3.0 == 1 + 2));
        System.out.println("l1.equals(i3): " + l1.equals(i3));
        System.out.println("l1 == i1 + i2: " + (l1 == i1 + i2)); // true：i1 + i2触发i1和i2拆箱 -> l1 == i1 + i2触发l1拆箱 -> long == int判断触发long类型降级为int
        System.out.println("l1.equals(i1 + i2): " + l1.equals(i1 + i2)); // false equals比较对象，Long.equals(Integer)为false。
        System.out.println("l1.equals(i1 + l2): " + l1.equals(i1 + l2)); // true：(i1 + l2)拆箱(int + long) -> 类型升级(long + long) -> 装箱Long.equals(Long)
        System.out.println("----------------End：基本类型之间比较触发类型降级和类型升级----------------");
    }
}
