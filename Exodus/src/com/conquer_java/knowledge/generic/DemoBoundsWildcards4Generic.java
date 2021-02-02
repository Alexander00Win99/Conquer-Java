package com.conquer_java.knowledge.generic;

public class DemoBoundsWildcards4Generic {
    public static void main(String[] args) {
        Plate plate = new Plate(null);
        System.out.println("未指定泛型实参的空盘子：");
        System.out.println(plate);
        Plate emptyFoodPlate = new Plate<Food>(null);
        System.out.println("指定泛型实参==Food构造函数传参null的空食物盘子：");
        System.out.println(emptyFoodPlate);
        Plate emptyFruitPlate = new Plate<Fruit>(null);
        System.out.println("指定泛型实参==Fruit构造函数传参null的空水果盘子：");
        System.out.println(emptyFruitPlate);
        Plate emptyApplePlate = new Plate<Apple>(null);
        System.out.println("指定泛型实参==Apple构造函数传参null的空苹果盘子：");
        System.out.println(emptyApplePlate);

        Food food = new Food();
        System.out.println(food);

        Fruit fruit = new Fruit();
        System.out.println(fruit);
        Apple apple = new Apple();
        System.out.println(apple);
        Banana banana = new Banana();
        System.out.println(banana);
        Pear pear = new Pear();
        System.out.println(pear);

        Meat meat = new Meat();
        System.out.println(meat);
        Beaf beaf = new Beaf();
        System.out.println(beaf);
        Chicken chicken = new Chicken();
        System.out.println(chicken);
        Pork pork = new Pork();
        System.out.println(pork);

        /**
         * 容器装的东西之间具有继承关系，装载东西的容器之间没有继承关系。
         * 可以使用<? extends T>和<? super T>通配符解决容器之间继承问题。
         * SomeClass<? extends T>是SomeClass<T>和SomeClass<ChildClassOfT>的基类。
         * SomeClass<? super T>是SomeClass<T>和SomeClass<ParentClassOfT>的基类。
         */
        Fruit appleFruit = new Apple(); // Apple IS-A Fruit，水果存在父子继承关系。
        //Plate<Fruit> fruitPlate = new Plate<Apple>(apple); // Apple Plate IS-NOT-A Fruit Plate，存放水果的盘子没有父子继承关系。

        /**
        //         * 上界<? extends T>只往外取，不往里存。
        //         * 原因在于：
        //         * 编译器知道该个容器最大能够存放T类对象，所以容器取出的内容不会高于T类，
        //         * 均可通过T类读出。但是，若要往里存入内容，如果容器是T的某个子类的容器，
        //         * 但是存入的内容是T的另一子类，两者并不兼容，无法实现。
        //         */
        //        Plate<? extends Fruit> appleFruitPlate = new Plate<Apple>(apple);
        //        Fruit fruitInPlate = appleFruitPlate.getItem();
        //        //Apple appleInPlate = appleFruitPlate.getItem(); // NOK：取出对象只能存入上界类
        //        //appleFruitPlate.setItem(new Fruit()); // NOK：不能存入任何对象
        //        //appleFruitPlate.setItem(new Apple()); // NOK：不能存入任何对象
        //
        //        /**
        //         * 下界<? super T>——能往里存，外取只有Object能收。
        //         * 原因在于：
        //         * 编译器知道该个容器最小能够存放T类对象，所以容器取出的内容不会低于T类，
        //         * 最高多高无法确定，鉴于Java类均是Object子类，唯一可以确定的是：
        //         * 容器取出的内容均可通过Object类接收（注意：如此导致元素类型信息全部丢失）。
        //         * 至于，若要往里存入元素，既然容器规定可以接收最低粒度是T类的对象，往上，并不确定，
        //         * 往下，T类子类当然也属T类，相应也能存入，那么实际效果就是，容器放松存入类型要求。
        //         */
        //        Plate<? super Fruit> fruitPlate = new Plate<Fruit>(apple); // 类型自动推断：new Plate<>即为new Plate<Fruit>
        //        fruitPlate.setItem(fruit); // 可以存入Fruit类对象
        //        fruitPlate.setItem(appleFruit); // 可以存入Fruit类对象
        //        fruitPlate.setItem(apple); // 可以存入低于Fruit类的Apple类对象
        //        //fruitPlate.setItem(food); // NOK：不能存入高于Fruit类的Food类对象
        //        Object objectInFruitPlate = fruitPlate.getItem(); // 只有Object够大能够容纳
        //        //Food foodInFruitPlate = fruitPlate.getItem(); // NOK：Food无法接收取出数据
        //        //Fruit fruitInFruitPlate = fruitPlate.getItem(); // NOK：Fruit无法接收取出数据
        //        //Apple appleInFruitPlate = fruitPlate.getItem(); // NOK：Apple无法接收取出数据
    }
}

class Plate<T> {
    private T item;

    public Plate(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Plate{" +
                "item=" + item +
                "} -> 盘子: " +
                this.getClass().getName();
    }
}

class Food {
    public String selfIntroduce(Food food) {
        return "Food{}子类自报门户: " + this.getClass().getName();
    }

    @Override
    public String toString() {
        return "Food{}: 食物";
    }

    public String toString(Food food) {
        return "Food{} == 食物: " + this.getClass().getName();
    }
}

class Fruit extends Food {
    @Override
    public String toString() {
        return "Food{} -> Fruit{} == 水果: " + this.getClass().getName();
    }
}

class Meat extends Food {
    @Override
    public String toString() {
        return "Food{} -> Meat{} == 肉类: " + this.getClass().getName();
    }
}

class Apple extends Fruit {
    @Override
    public String toString() {
        return "Apple{} - " + super.selfIntroduce(this);
    }
}

class Banana extends Fruit {
    @Override
    public String toString() {
        return "Banana{} - " + super.selfIntroduce(this);
    }
}

class Pear extends Fruit {
    @Override
    public String toString() {
        return "Pear{} - " + super.selfIntroduce(this);
    }
}

class Beaf extends Meat {
    @Override
    public String toString() {
        return "Beaf{}";
    }
}

class Chicken extends Meat {
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Chicken{");
        sb.append('}');
        return sb.toString();
    }
}

class Pork extends Meat {
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pork{");
        sb.append('}');
        return sb.toString();
    }
}
