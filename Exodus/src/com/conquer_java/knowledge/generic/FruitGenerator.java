package com.conquer_java.knowledge.generic;

import java.util.Random;

/**
 * 实现类在声明时若未传递泛型实参，则需一并附带声明泛型接口的泛型形参，否则报错：cannot find symbol symbol: class T；
 * 实现类在声明时若已传递泛型实参，则需使用实参替换泛型出现的所有地方。
 * @param <T>
 */
//public class FruitGenerator<T> implements Generator<T> {
//    @Override
//    public T next() {
//        return null;
//    }
//}

public class FruitGenerator implements Generator<String> {
    private String[] fruits = new String[]{"Apple", "banana", "pear"};

    @Override
    public String next() {
        Random random = new Random();
        return fruits[random.nextInt(3)];
    }
}
