package com.conquer_java.knowledge.generic;

public class DemoGenericMethod {
    public class Plate<T> {
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

    private class Food {}
    private class Fruit extends Food {
        @Override
        public String toString() {
            return "Fruit{} -> 水果: " + this.getClass().getName();
        }
    }
    private class Meat extends Food {}
    private class Apple extends Fruit {}
    private class Banana extends Fruit {}
    private class Pear extends Fruit {}
    private class Beaf extends Meat {}
    private class Chicken extends Meat {}
    private class Pork extends Meat {}

    public static void main(String[] args) {
        // 空的盘子
        DemoGenericMethod.Plate plate = new DemoGenericMethod().new Plate(null);
        System.out.println(plate);
        // 水果
        DemoGenericMethod.Fruit fruit = new DemoGenericMethod().new Fruit();
        System.out.println(fruit);
        // 苹果
        DemoGenericMethod.Apple apple = new DemoGenericMethod().new Apple();
        System.out.println(apple);

        // OK
        DemoGenericMethod.Plate<Fruit> fruitPlate = new DemoGenericMethod().new Plate<Fruit>(new DemoGenericMethod().new Apple());
        // OK
        DemoGenericMethod.Plate<Apple> applePlate = new DemoGenericMethod().new Plate<Apple>(new DemoGenericMethod().new Apple());
        // NOK：虽然Fruit -> Apple，但是Plate<Fruit> -> Plate<Apple>
        // DemoGenericMethod.Plate<Fruit> fruitPlateForApple = new DemoGenericMethod().new Plate<Apple>(new DemoGenericMethod().new Apple());
    }
}
