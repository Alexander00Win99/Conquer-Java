package com.conquer_java.algorithm.exercise_leetcode;

public class Leetcode_0258_AddDigits {
    /**
     * Integer.MAX_VALUE == 2147483647; 极值包含十位digit数字，各位加和最大不会超过9 * 10 == 90，也即，
     * 相加一次之后最大只能获取一个包含两位数字的数值，对于两位数，十位数字和个位数字相加，最大是9+9，本题是8+9，
     * 显然，两个十进制数字相加，最大不会超过18，也即，最多再将结果的十位和个位相加一次即可获得最终答案。
     *
     * 注：ASCII码表常用字符索引偏移求值：
     * digit: n - 48; 0x30开始
     * lowercase: 'x' - 64; 0x40开始
     * uppercase: 'X' - 96; 0x60开始
     * @param num
     * @return
     */
    public int addDigits(int num) {
        char[] digits = new Integer(num).toString().toCharArray();
        int sum = 0;
        for (char digit : digits) {
            sum += digit - 48; // char - 48 ==> 通过ASCII表偏置获取数字
        }
        System.out.println("Step-1 sum: " + sum);
        if (sum <= 9) return sum;
        sum = sum / 10 + sum % 10;
        System.out.println("Step-2 sum: " + sum);
        if (sum <= 9) return sum;
        sum = sum / 10 + sum % 10;
        System.out.println("Step-3 sum: " + sum);
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(new Leetcode_0258_AddDigits().addDigits(1839));
        System.out.println('0' - 48);
        System.out.println('9' - 48);
        System.out.println('A' - 64);
        System.out.println('Z' - 64);
        System.out.println('a' - 96);
        System.out.println('z' - 96);
    }
}
