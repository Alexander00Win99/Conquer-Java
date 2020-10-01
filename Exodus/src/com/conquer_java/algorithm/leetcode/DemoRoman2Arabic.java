package com.conquer_java.algorithm.leetcode;

import java.util.HashMap;

public class DemoRoman2Arabic {
    /**
     * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
     *
     * For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII, which is simply X + II.
     * The number twenty seven is written as XXVII, which is XX + V + II.
     *
     * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number
     * four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which
     * is written as IX. There are six instances where subtraction is used:
     *
     * I can be placed before V (5) and X (10) to make 4 and 9. 
     * X can be placed before L (50) and C (100) to make 40 and 90. 
     * C can be placed before D (500) and M (1000) to make 400 and 900.
     * Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.
     *
     * Symbol represent Arabic
     * I    1
     * V    5
     * X    10
     * L    50
     * C    100
     * D    500
     * M    1000
     *
     * 注意：IL不是代表49(50-1)，IC不是代表99(100-1)，因为I只能位于V或者X的前面，不能位于L或者C的前面。
     * 规律：组合数字最多两位(多于两位先拆后加)
     * 1） 组合数字位于左边的只可能是：I X C
     * 2） 组合数字左右等级相差不能大于10倍：I只能位于V和X的左边；X只能位于L和C的左边；C只能位于D和M的左边；
     *
     * 倍数表示：
     * 1-Single 2-Double 3-Triple/Treble 4-Quadruple 5-Quintuple
     * @param romanNumeral
     * @return
     */
    public static int roman2Arabic(String romanNumeral) {
        HashMap map = new HashMap(10);
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        map.put("IV", 4);
        map.put("IX", 9);
        map.put("XL", 40);
        map.put("XC", 90);
        map.put("CD", 400);
        map.put("CM", 900);

        int sum = 0;

        for (int i = 0; i < romanNumeral.length();) {
            boolean flag = true;
            if (i <= romanNumeral.length() - 2) {
                String doubleLetter = romanNumeral.substring(i, i + 2);
                if (map.containsKey(doubleLetter)) {
                    sum += (Integer) map.get(doubleLetter);
                    flag = false;
                    i += 2;
                }
            }

            if (flag) {
                String singleLetter = romanNumeral.substring(i, i + 1);
                int value = (Integer) map.get(singleLetter);
                sum += value;
                i ++;
            }
        }
        return sum;
    }

    public static String arabic2Roman(int x) {
        String str = "";
        while (x > 0)
        {
            if (x >= 1000)
            {
                str = str.concat("M");
                x -= 1000;
            }
            else if (x >= 900)
            {
                str = str.concat("C");
                x += 100;
            }
            else if (x >= 500)
            {
                str = str.concat("D");
                x -= 500;
            }
            else if (x >= 400) {
                str = str.concat("C");
                x += 100;
            }
            else if (x >= 100)
            {
                str = str.concat("C");
                x -= 100;
            }
            else if (x >= 90)
            {
                str = str.concat("X");
                x += 10;
            }
            else if (x >= 50)
            {
                str = str.concat("L");
                x -= 50;
            }
            else if (x >= 40) {
                str = str.concat("X");
                x += 10;
            }
            else if (x >= 10) {
                str = str.concat("X");
                x -= 10;
            }
            else if (x >= 9) {
                str = str.concat("I");
                x += 1;
            }
            else if (x >= 5) {
                str = str.concat("V");
                x -= 5;
            }
            else if (x >= 4) {
                str = str.concat("I");
                x += 1;
            }
            else if (x >= 1) {
                str = str.concat("I");
                x -= 1;
            }
        }
        return str;
    }

    public static String arabic2Roman(int x, String romanNumeral) {
        if (x < 0 || x >= 4000) return "";
        if (x == 0) return "";
        if (x >= 1000) {
            romanNumeral = "M" + arabic2Roman(x - 1000, romanNumeral);
        } else if (x >= 900) {
            romanNumeral = "CM" + arabic2Roman(x - 900, romanNumeral);
        } else if (x >= 500) {
            romanNumeral = "D" + arabic2Roman(x - 500, romanNumeral);
        } else if (x >= 400) {
            romanNumeral = "CD" + arabic2Roman(x - 400, romanNumeral);
        } else if (x >= 100) {
            romanNumeral = "C" + arabic2Roman(x - 100, romanNumeral);
        } else if (x >= 90) {
            romanNumeral = "XC" + arabic2Roman(x - 90, romanNumeral);
        } else if (x >= 50) {
            romanNumeral = "L" + arabic2Roman(x - 50, romanNumeral);
        } else if (x >= 40) {
            romanNumeral = "XL" + arabic2Roman(x - 40, romanNumeral);
        } else if (x >= 10) {
            romanNumeral = "X" + arabic2Roman(x - 10, romanNumeral);
        } else if (x >= 9) {
            romanNumeral = "IX" + arabic2Roman(x - 9, romanNumeral);
        } else if (x >= 5) {
            romanNumeral = "V" + arabic2Roman(x - 5, romanNumeral);
        } else if (x >= 4) {
            romanNumeral = "IV" + arabic2Roman(x - 4, romanNumeral);
        } else {
            romanNumeral = "I" + arabic2Roman(x - 1, romanNumeral);
        }
        return romanNumeral;
    }

    public static StringBuilder arabic2Roman(int x, StringBuilder romanNumeral) {
        System.out.println("RomanNumeral before this invokement: " + romanNumeral);
        if (x < 0 || x >= 4000) return null;
        if (x == 0) return new StringBuilder("");
        if (x >= 1000) {
            romanNumeral = new StringBuilder("M").append(arabic2Roman(x - 1000, romanNumeral));
        } else if (x >= 900) {
            romanNumeral = new StringBuilder("CM").append(arabic2Roman(x - 900, romanNumeral));
        } else if (x >= 500) {
            romanNumeral = new StringBuilder("D").append(arabic2Roman(x - 500, romanNumeral));
        } else if (x >= 400) {
            romanNumeral = new StringBuilder("CD").append(arabic2Roman(x - 400, romanNumeral));
        } else if (x >= 100) {
            romanNumeral = new StringBuilder("C").append(arabic2Roman(x - 100, romanNumeral));
        } else if (x >= 90) {
            romanNumeral = new StringBuilder("XC").append(arabic2Roman(x - 90, romanNumeral));
        } else if (x >= 50) {
            romanNumeral = new StringBuilder("L").append(arabic2Roman(x - 50, romanNumeral));
        } else if (x >= 40) {
            romanNumeral = new StringBuilder("XL").append(arabic2Roman(x - 40, romanNumeral));
        } else if (x >= 10) {
            romanNumeral = new StringBuilder("X").append(arabic2Roman(x - 10, romanNumeral));
        } else if (x >= 9) {
            romanNumeral = new StringBuilder("IX").append(arabic2Roman(x - 9, romanNumeral));
        } else if (x >= 5) {
            romanNumeral = new StringBuilder("V").append(arabic2Roman(x - 5, romanNumeral));
        } else if (x >= 4) {
            romanNumeral = new StringBuilder("IV").append(arabic2Roman(x - 4, romanNumeral));
        } else {
            switch (x) {
                case 1:
                    romanNumeral = new StringBuilder("I");
                    break;
                case 2:
                    romanNumeral = new StringBuilder("II");
                    break;
                case 3:
                    romanNumeral = new StringBuilder("III");
                    break;
            }
        }
        System.out.println("RomanNumeral after this invokement: " + romanNumeral);
        return romanNumeral;
    }

    public static void main(String[] args) {
        long begin, end;
        int arabicNum;
        String romanNum = null;
        StringBuilder romanNumeral = null;

        System.out.println("++++++++罗马数字-->阿拉伯数字++++++++");
        romanNum = new String("MCMXCIX");
        arabicNum = roman2Arabic(romanNum);
        System.out.println("罗马数字：" + romanNum + "转化为阿拉伯数字：");
        System.out.println(arabicNum);
        System.out.println("--------罗马数字-->阿拉伯数字--------");

        System.out.println("++++++++阿拉伯数字-->罗马数字++++++++");
        romanNum = arabic2Roman(1999);
        System.out.println("阿拉伯数字：" + 1999 + "转化为罗马数字：");
        System.out.println(romanNum);
        System.out.println("--------阿拉伯数字-->罗马数字--------");

        System.out.println("++++++++阿拉伯数字--String递归-->罗马数字++++++++");
        begin = System.currentTimeMillis();
        romanNum = arabic2Roman(3999, romanNum);
        System.out.println("阿拉伯数字：" + 3999 + "转化为罗马数字：");
        System.out.println(romanNum);
        end = System.currentTimeMillis();
        System.out.println("Time: " + (end - begin));
        System.out.println("--------阿拉伯数字--String递归-->罗马数字--------");

        System.out.println("++++++++阿拉伯数字--StringBuilder递归-->罗马数字++++++++");
        begin = System.currentTimeMillis();
        romanNumeral = arabic2Roman(3999, romanNumeral);
        System.out.println("阿拉伯数字：" + 3999 + "转化为罗马数字：");
        System.out.println(romanNumeral);
        end = System.currentTimeMillis();
        System.out.println("Time: " + (end - begin));
        System.out.println("--------阿拉伯数字--StringBuilder递归-->罗马数字--------");
    }
}
