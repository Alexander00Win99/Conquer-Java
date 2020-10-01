package com.conquer_java.design_pattern.builder;

import java.util.HashMap;

/**
 *  * 【目标】：掌握通过建造者模式构建不可变对象
 *  *
 *  * 【结果】：完成
 */
public class DemoImmutableClass {
    private static boolean randomJudge() {
        return Math.random() < 0.5;
    }

    public static void main(String[] args) {
        /**
         * 原始方式
         * 客户端用户必须熟悉繁琐复杂的产品构造器，并且无法自由定制各个部件。
         */
        ImmutableClass obj = new ImmutableClass("DEMO", new HashMap<>(), "part1", "part2", "part3", "part4");

        /**
         * Builder方式
         * 复杂系统，各个部分是否构建，不同场景不同选择，并且各个部分相互之间存在依赖关系，导致构建顺序和构建逻辑非常复杂，只能通过Builder模式加以解决。
         */
        ImmutableClass.Builder builder = new ImmutableClass.ConcreteBuilder("DEMO");
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Alexander 温", 36);
        map.put("Alexander00Win99", 18);
        builder.setProperties(map);
        // 按照业务逻辑自由选择构建各个部分
        boolean bPart1 = randomJudge(), bPart2 = randomJudge(), bPart3 = randomJudge(), bPart4 = randomJudge();
        System.out.println("bPart1: " + bPart1);
        System.out.println("bPart2: " + bPart2);
        System.out.println("bPart3: " + bPart3);
        System.out.println("bPart4: " + bPart4);

        if (bPart1) {
            builder.buildPart1("part1");
        }
        if (bPart2 && bPart3) { // 只有两个条件同时满足，才会通过链式调用同时构建。
            builder.buildPart2("part2").buildPart3("part3");
        }
        if (bPart4) {
            builder.buildPart4("part4");
        }

        ImmutableClass product = builder.build();
        System.out.println(product);

        // 验证对象不可变性
        System.out.println("Before config: ");
        System.out.println(product.getProperties());

        HashMap hashMap = product.getProperties();
        hashMap.put("Alex Wen", 27);
        System.out.println("During configing: ");
        System.out.println(hashMap);

        System.out.println("After config: ");
        System.out.println(product.getProperties());
    }
}

class ImmutableClass { // 不可变对象-全部属性都是final类型。无法通过setter函数赋值，只能定义时初始化或者或者通过构造函数初始化。
    private final String info;
    private final HashMap properties;
    private final String part1;
    private final String part2;
    private final String part3;
    private final String part4;
//    private String info;
//    private HashMap properties;
//    private String part1;
//    private String part2;
//    private String part3;
//    private String part4;

    // 构造函数方式创建对象 <==> 必须保证构造函数形参实参一一对应，繁琐易错。
    public ImmutableClass(String info, HashMap properties, String part1, String part2, String part3, String part4) {
        this.info = info;
        this.properties = properties;
        this.part1 = part1;
        this.part2 = part2;
        this.part3 = part3;
        this.part4 = part4;
    }
    // 提供用户统一构造器
    public ImmutableClass(ImmutableClass.ConcreteBuilder builder) {
        // required parts
        this.info = builder.info;
        // optional parts
        this.properties = builder.properties;
        this.part1 = builder.part1;
        this.part2 = builder.part2;
        this.part3 = builder.part3;
        this.part4 = builder.part4;
    }

    public String getInfo() {
        return info;
    }

    public HashMap getProperties() { // 通过返回克隆对象实现避免用户修改
        return (HashMap) properties.clone();
    }

    public String getPart1() {
        return part1;
    }

    public String getPart2() {
        return part2;
    }

    public String getPart3() {
        return part3;
    }

    public String getPart4() {
        return part4;
    }

    @Override
    public String toString() {
        return "ImmutableClass{" +
                "info='" + info + '\'' +
                ", properties=" + properties +
                ", part1='" + part1 + '\'' +
                ", part2='" + part2 + '\'' +
                ", part3='" + part3 + '\'' +
                ", part4='" + part4 + '\'' +
                '}';
    }

    interface Builder {
        Builder setProperties(HashMap properties);
        Builder buildPart1(String part1);
        Builder buildPart2(String part2);
        Builder buildPart3(String part3);
        Builder buildPart4(String part4);

        ImmutableClass build();
    }

    static class ConcreteBuilder implements Builder {
        private String info;
        private HashMap properties;
        private String part1;
        private String part2;
        private String part3;
        private String part4;

        public ConcreteBuilder(String info) {
            this.info = info;
        }

        @Override
        public ImmutableClass.Builder setProperties(HashMap properties) {
            this.properties = (HashMap) properties.clone();
            return this;
        }

        @Override
        public ImmutableClass.Builder buildPart1(String part1) {
            this.part1 = part1;
            return this;
        }

        @Override
        public ImmutableClass.Builder buildPart2(String part2) {
            this.part2 = part2;
            return this;
        }

        @Override
        public ImmutableClass.Builder buildPart3(String part3) {
            this.part3 = part3;
            return this;
        }

        @Override
        public ImmutableClass.Builder buildPart4(String part4) {
            this.part4 = part4;
            return this;
        }

        @Override
        public ImmutableClass build() {
            return new ImmutableClass(this);
        }
    }
}
