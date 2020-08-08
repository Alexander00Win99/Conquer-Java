package com.conque_java.knowledge.thread;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *
 */
public class DemoThreadLocal {
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        /**
         * @FunctionalInterface
         * public interface Supplier<T> {
         *     T get();
         * }
         */
        Supplier<Mask> supplier = () -> new Mask("3M", "N95");

        /**
         * @FunctionalInterface
         * public interface Consumer<T> {
         *     void accept(T t);
         * }
         */
        Consumer<Mask> consumer = (Mask mask) -> {
            System.out.println("Object: " + mask.hashCode() + ", Brand: " + mask.getBrand() + ", Type: " + mask.getType());
        };
        consumer.accept(supplier.get());
        consumer.accept(supplier.get());

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }).start();

    }
}

class Person {
    static {
        System.out.println("我是人类，来自火星！");
    }
}

class Mask {
    private String brand;
    private String type;

    public Mask(String brand, String type) {
        this.brand = brand;
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
