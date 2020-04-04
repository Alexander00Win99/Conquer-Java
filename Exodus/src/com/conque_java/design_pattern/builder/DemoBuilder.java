package com.conque_java.design_pattern.builder;

/**
 * 【目标】：掌握建造者模式
 *
 * 【结果】：完成
 *
 * 【建造者模式】-Builder：将复杂对象的创建与其表示分离开来，使得可以使用相同的构建过程创建不同的表示。
 * 导演（Diector） -> 剧本（ConcreteBuilder） -> 大纲（Builder） -> 作品（Product）
 *
 * 【使用场景】
 * 产品内部结构复杂，具有多个成员属性，属性之间相互依赖，需要指定生成顺序；
 * 使用建造者模式，可以隔离复杂对象的创建与使用，使得相同创建过程可以创建不同产品对象。
 *
 * 【优点】
 * 1. 通过使用建造者模式，客户端不必知道产品内部构建细节，即可精细控制对象构建流程；
 * 2. 具体的建造者类之间是相互独立的，对系统的扩展非常有利；
 * 3. 由于具体的建造者是独立的，因此可以对建造过程逐步细化，而不对其他的模块产生任何影响。
 *
 * 【缺点】
 * 1. 建造者模式所创建的产品一般具有较多的共同点，其组成部分相似，如果产品之间的差异性很大，例如很多组成部分都不相同，不适合使用建造者模式，因此其使用范围受到一定的限制；
 * 2. 如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得非常庞大，增加系统的理解难度和运行成本。
 *
 * Spring例子：RequestMappingInfo中的Builder实现
 */
public class DemoBuilder {
    public static void main(String[] args) {
//        Builder builder = new DefaultBuilder();
//        Designer designer = new Designer(builder1);
//        Goods goods = designer.buildProduct();
//        System.out.println(goods);

        Builder builder = new ConcreteBuilder();
        Director director = new Director();
        director.assembleProduct(builder);
        Product product = builder.build();
        System.out.println(product);
    }
}

//interface Builder {
//    void buildPart1();
//    void buildPart2();
//    void buildPart3();
//
//    Goods build();
//}
//
//class DefaultBuilder implements Builder {
//    private String part1;
//    private String part2;
//    private String part3;
//
//    @Override
//    public void buildPart1() {
//        this.part1 = "part1";
//    }
//
//    @Override
//    public void buildPart2() {
//        this.part2 = "part2";
//    }
//
//    @Override
//    public void buildPart3() {
//        this.part3 = "part3";
//    }
//
//    @Override
//    public Goods build() { // Builder的build方法调用buildPart1()|buildPart2()|buildPart3()方法，实际违背建造者模式的Designer完全控制Builder原则。
//        buildPart1();
//        buildPart2();
//        buildPart3();
//        return new Goods(this.part3, this.part2, this.part1); // 这里乱序创建部件，体现顺序控制逻辑。
//    }
//}
//
//class Designer {
//    private Builder builder;
//    public Designer(Builder builder) {
//        this.builder = builder;
//    }
//
//    public Goods buildProduct() {
//        return builder.build();
//    }
//}
//
//class Goods {
//    private String part1;
//    private String part2;
//    private String part3;
//
//    public Goods(String part1, String part2, String part3) {
//        this.part1 = part1;
//        this.part2 = part2;
//        this.part3 = part3;
//    }
//
//    @Override
//    public String toString() {
//        return "Goods{" +
//                "hashCode='" + hashCode() + '\'' +
//                ", part1='" + part1 + '\'' +
//                ", part2='" + part2 + '\'' +
//                ", part3='" + part3 + '\'' +
//                '}';
//    }
//}

// 抽象接口，建造者建造产品各个组成部分的规范，无需涉及具体建造过程。
interface Builder {
    void buildPart1();
    void buildPart2();
    void buildPart3();

    Product build();
}

// 子类实现接口，针对不同的业务逻辑，具体负责建造产品的各个组成部分，最后返回产品整体。
class ConcreteBuilder implements Builder {
    private Product product = new Product();

    @Override
    public void buildPart1() {
        product.buildPart1("part1");
    }

    @Override
    public void buildPart2() {
        product.buildPart2("part2");
    }

    @Override
    public void buildPart3() {
        product.buildPart3("part3");
    }

    public void buildAdditionalPart() {
        product.buildAdditionalPart("additionalPart");
    }

    @Override
    public Product build() {
        return product;
    }
}

/**
 * 1) 导演类Director通过AssembleProduct()|Construct()具体控制复杂对象内部构建顺序以及依赖逻辑，客户端用户只需实现具体建造者类ConcreteBuilder便可实现复杂对象的创建；
 * 2) 各个具体建造者类ConcreteBuilder相互之间彼此独立，方便客户端用户根据业务需求增加或者替换具体不同建造者类ConcreteBuilder；
 * 3) 根据业务情况如需新增建造者类ConcreteBuilder无需修改原有类库，导演类针对抽象建造者而非具体建造者进行实现，系统扩展方便，符合“开闭原则”；
 */
// 负责规范建造流程，不会参与具体产品的创建，负责保证复杂对象的各个部分需要按照某种顺序或者遵守某种限制才能创建出来。
class Director {
    public void assembleProduct(Builder builder) { // 这里没有调用buildAdditionalPart，体现取舍控制逻辑。
        builder.buildPart3();
        builder.buildPart2();
        builder.buildPart1();
    }
}

class Product {
    private String part1;
    private String part2;
    private String part3;
    private String additionalPart;

//    public Product() {};

    public void buildPart1(String part1) {
        this.part1 = part1;
    };
    public void buildPart2(String part2) {
        this.part2 = part2;
    };
    public void buildPart3(String part3) {
        this.part3 = part3;
    };
    public void buildAdditionalPart(String additionalPart) {
        this.additionalPart = additionalPart;
    };

    @Override
    public String toString() {
        return "Product{" +
                "part1='" + part1 + '\'' +
                ", part2='" + part2 + '\'' +
                ", part3='" + part3 + '\'' +
                ", additionalPart='" + additionalPart + '\'' +
                '}';
    }

//    public Product(String part1, String part2, String part3, String additionalPart) {
//        this.part1 = part1;
//        this.part2 = part2;
//        this.part3 = part3;
//        this.additionalPart = additionalPart;
//    }
}