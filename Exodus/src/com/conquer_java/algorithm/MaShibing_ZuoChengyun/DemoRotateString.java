package com.conquer_java.algorithm.MaShibing_ZuoChengyun;

/**
 * Given an array of characters and an offset, rotate string by offset(rotate the given number defined by offset of characters from leftmost to rightmost).
 * [solution01]
 * origin=[abcdef] offset=4 target=[efabcd]
 * 1) [fedcba]          ——  整个数组所有元素进行逆序
 * 2) [ef | abcd]       ——  按照(length - offset)指定位置划分两段，各段内部所有元素再次逆序
 *
 * [solution02]
 *
 */
public class DemoRotateString {
    public static void main(String[] args) {

    }
}