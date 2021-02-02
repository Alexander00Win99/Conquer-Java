package com.conquer_java.knowledge.operator;

public class DemoShift {
    public static void main(String[] args) {
        int i;
        System.out.println("++++++++++++++++Begin <<左移操作++++++++++++++++");
        /**
         * 说明：<<左移不分正负，无须考虑符号正负左移之后是否丢失，低位空缺补0即可，符号可能反转：大值正数左移溢出变成负数，大值负数左移以后变成0或正数。
         */
        i = 1;
        System.out.println("整数1的值为：" + i);
        System.out.println("整数1的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i <<= 2;
        System.out.println("整数1<<左移2位以后的值为：" + i);
        System.out.println("整数1<<左移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i = Integer.MAX_VALUE;
        System.out.println("整数最大值的值为：" + i);
        System.out.println("整数最大值的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i <<= 2;
        System.out.println("整数最大值<<左移2位以后的值为：" + i);
        System.out.println("整数最大值<<左移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i = -1;
        System.out.println("整数-1的值为：" + i);
        System.out.println("整数-1的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i <<= 2;
        System.out.println("整数-1<<左移2位以后的值为：" + i);
        System.out.println("整数-1<<左移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i = Integer.MIN_VALUE;
        System.out.println("整数最小值的值为：" + i);
        System.out.println("整数最小值的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i <<= 2;
        System.out.println("整数最小值<<左移2位以后的值为：" + i);
        System.out.println("整数最小值<<左移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        System.out.println("================由此可见================");
        System.out.println("[[[结论：<<左移不分正负，无须考虑符号正负左移之后是否丢失，低位空缺补0即可，符号可能反转：大值正数左移溢出变成负数，大值负数左移以后变成0或正数。]]]");
        System.out.println("----------------End <<左移操作----------------");

        System.out.println("++++++++++++++++Begin >>右移操作++++++++++++++++");
        /**
         * 说明：>>算术右移——需要考虑符号位——符号位保持不变，高位空缺补符号位（正数补0，负数补1）
         */
        i = 1;
        System.out.println("整数1的值为：" + i);
        System.out.println("整数1的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i >>= 2;
        System.out.println("整数1>>右移2位以后的值为：" + i);
        System.out.println("整数1>>右移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i = Integer.MAX_VALUE;
        System.out.println("整数最大值的值为：" + i);
        System.out.println("整数最大值的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i >>= 2;
        System.out.println("整数最大值>>右移2位以后的值为：" + i);
        System.out.println("整数最大值>>右移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i = -1;
        System.out.println("整数-1的值为：" + i);
        System.out.println("整数-1的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i >>= 2;
        System.out.println("整数-1>>右移2位以后的值为：" + i);
        System.out.println("整数-1>>右移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i = Integer.MIN_VALUE;
        System.out.println("整数最小值的值为：" + i);
        System.out.println("整数最小值的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i >>= 2;
        System.out.println("整数最小值>>右移2位以后的值为：" + i);
        System.out.println("整数最小值>>右移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        System.out.println("================由此可见================");
        System.out.println("[[[结论：>>算术右移——需要考虑符号位——符号位保持不变，高位空缺补符号位（正数补0，负数补1）]]]");
        System.out.println("----------------End >>右移操作----------------");

        System.out.println("++++++++++++++++Begin >>>右移操作++++++++++++++++");
        /**
         * 说明：>>>逻辑右移——无需考虑符号位——符号位参与右移操作，高位空缺一律补0。
         */
        i = 1;
        System.out.println("整数1的值为：" + i);
        System.out.println("整数1的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i >>>= 2;
        System.out.println("整数1>>>右移2位以后的值为：" + i);
        System.out.println("整数1>>>右移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i = Integer.MAX_VALUE;
        System.out.println("整数最大值的值为：" + i);
        System.out.println("整数最大值的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i >>>= 2;
        System.out.println("整数最大值>>>右移2位以后的值为：" + i);
        System.out.println("整数最大值>>>右移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i = -1;
        System.out.println("整数-1的值为：" + i);
        System.out.println("整数-1的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i >>>= 2;
        System.out.println("整数-1>>>右移2位以后的值为：" + i);
        System.out.println("整数-1>>>右移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i = Integer.MIN_VALUE;
        System.out.println("整数最小值的值为：" + i);
        System.out.println("整数最小值的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        i >>>= 2;
        System.out.println("整数最小值>>>右移2位以后的值为：" + i);
        System.out.println("整数最小值>>>右移2位以后的二进制表示：" + Integer.toBinaryString(i) + "；总计位数：" + Integer.toBinaryString(i).length());
        System.out.println("================由此可见================");
        System.out.println("[[[结论：>>>逻辑右移——无需考虑符号位——符号位参与右移操作，高位空缺一律补0。]]]");
        System.out.println("----------------End >>>右移操作----------------");
    }
}
