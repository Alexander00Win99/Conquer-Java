package com.conquer_java.design_pattern.factory_method;

/**
 * 【目标】：掌握工厂方法模式
 *
 * 【结果】：完成
 *
 * 【工厂方法模式】-Factory Method
 *
 * 【特点】
 * 定义接口用于创建对象，具体子类决定实例化哪一个类，从而使得类的实例化操作延迟到子类选择。
 *
 * 【JDK例子】
 * Vector中ArrayList和LinkedList的iterator()中的next遍历方法
 */
public class DemoFactoryMethod {
    public static void main(String[] args) {
        /**
         * 原始方式：ProductA -> ProductB => Application类重写 + 客户端代码重写
         */
//        Application application = new Application();
//
//        ProductA product = application.getProduct();
//        product.methodA();
//        // ==>
//        ProductB product = application.getProduct();
//        product.methodB();
        /**
         * 简单工厂方式：ProductFactory根据产品类型新增判断分支，步骤繁琐 + 客户端代码重写
         */
//        ProductFactory productFactory = new ProductFactory();
//
//        IProduct productA = ProductFactory.getProduct("ProductA");
//        productA.method();
//        ((ProductA) productA).methodA();
//        // ==>
//        IProduct productB = ProductFactory.getProduct("ProductB");
//        productB.method();
//        ((ProductB) productB).methodB();
        /**
         * 工厂方法模式：ConcreteProductA|ConcreteProductB作为Application子类，各自实现抽象方法createProduct()返回实际类型 + 客户端只需选择
         */
//        Application application = new ConcreteProductA(); // ==> Application application = new ConcreteProductB();
//        Product product = application.getProduct();
//        product.method();
//        // 对应产品内部非接口独有方法，可以利用下转型实现多态。
//        ((ProductA) product).methodA(); // ==> ((ProductB) product).methodB();

        Application applicationA = new ConcreteProductA();
        IProduct productA = applicationA.getProduct();
        productA.method();
        // 多态
        ((ProductA) productA).methodA();
        // ==>
        Application applicationB = new ConcreteProductB();
        IProduct productB = applicationB.getProduct();
        productB.method();
        // 多态
        ((ProductB) productB).methodB();
    }
}

/**
 * 【原始方式】
 */
//class ProductA {
//    public void methodA() {
//        System.out.println("ProductA's method has been executed!");
//    }
//}
//
//class ProductB {
//    public void methodB() {
//        System.out.println("ProductB's method has been executed!");
//    }
//}
//
//class Application {
//    private ProductA createProduct() {
//        return new ProductA();
//    }
//
//    public ProductA getProduct() {
//        ProductA product = createProduct();
//        // ......
//        return product;
//    }
//    // ==>
//    private ProductB createProduct() {
//        return new ProductB();
//    }
//
//    public ProductB getProduct() {
//        ProductB product = createProduct();
//        // ......
//        return product;
//    }
//}

/**
 * 【简单工厂模式】：不是设计模式，只是一种方式。
 */
//interface IProduct {
//    void method();
//}
//
//class ProductA implements IProduct {
//    @Override
//    public void method() {
//        System.out.println("ProductA's method has been executed!");
//    }
//
//    public void methodA() {
//        System.out.println("ProductA's specific method has been executed!");
//    }
//}
//
//class ProductB implements IProduct {
//    @Override
//    public void method() {
//        System.out.println("ProductB's method has been executed!");
//    }
//
//    public void methodB() {
//        System.out.println("ProductB's specific method has been executed!");
//    }
//}
//
//class ProductFactory { // 简单工厂模式
//    public static IProduct getProduct(String type) {
//        if ("ProductA".equals(type)) {
//            return new ProductA();
//        } else if ("ProductB".equals(type)) {
//            return new ProductB();
//        } // ......
//        return null;
//    }
//}

/**
 * 【工厂方法模式】
 */
interface IProduct {
    void method();
}

class ProductA implements IProduct {
    @Override
    public void method() {
        System.out.println("ProductA's method has been executed!");
    }

    public void methodA() {
        System.out.println("ProductA's specific method has been executed!");
    }
}

class ProductB implements IProduct {
    @Override
    public void method() {
        System.out.println("ProductB's method has been executed!");
    }

    public void methodB() {
        System.out.println("ProductB's specific method has been executed!");
    }
}

abstract class Application {
    abstract IProduct createProduct();

    public IProduct getProduct() {
        IProduct product = createProduct();
        // ......
        return product;
    }
}

class ConcreteProductA extends Application {
    @Override
    IProduct createProduct() {
        return new ProductA();
    }
}

class ConcreteProductB extends Application {
    @Override
    IProduct createProduct() {
        return new ProductB();
    }
}
