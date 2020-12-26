package com.conquer_java.algorithm.exercise_leetcode;

public class Leetcode_1201_UglyNumberIII_Factorization {
    // G.C.D最大公约数Greatest Common Divisor
    // 举例如下：
    // gcd(3, 5) -> gcd(2, 3) -> gcd(1, 2) -> gcd(0, 1)：最大公约数是1。
    // gcd(6, 8) -> gcd(2, 6) -> gcd(0, 2)：最大公约数是2。
    // gcd(8, 6) -> gcd(6, 8) -> gcd(2, 6) -> gcd(0, 2)：最大公约数是2
    public static long gcd(long i, long j) { // 递归方式计算gcd
        if (i == 0) return j;
        return gcd(j % i, i);
    }

    // L.C.M最小公倍数——Least Common Multiple
    public static long lcm(long i, long j) {
        return i * j / gcd(i, j);
    }

    // G.C.D最大公约数Greatest Common Divisor
    // 举例如下：
    // gcd(3, 5) -> gcd(5, 3) -> gcd(3, 2) -> gcd(2, 1) -> 2 % 1 == 0：最大公约数是1。
    // gcd(6, 8) -> gcd(8, 6) -> gcd(6, 2) -> 6 % 2 == 0：最大公约数是2。
    // gcd(8, 6) -> gcd(6, 2) -> 6 % 2 == 0：最大公约数是2。
    public static long greatestCommonDivisor(long a, long b) { // 迭代方式计算gcd
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    // 注：inclusion-exclusion principle —— “容斥原理”
    // 能被a或b或c整除的数 = 所有不能被a且不能被b且不能被c整除的数以外的数(下面的!000)
    // = (num/a + num/b + num/c)
    // - (num/lcm(a, b) + num/lcm(a, c) + num/lcm(b, c))
    // + num/lcm(a, b, c)
    // 分析如下：
    // a b c
    // 0 0 0
    // 0 0 1
    // 0 1 0
    // 0 1 1
    // 1 0 0
    // 1 0 1
    // 1 1 0
    // 1 1 1
    // 能被a整除的数num/a：100, 101, 110, 111
    // 能被b整除的数num/b：010, 011, 110, 111
    // 能被c整除的数num/c：001, 011, 101, 111
    // 能被ab整除的数num/lcm(a, b)：110, 111
    // 能被ac整除的数num/lcm(a, c)：101, 111
    // 能被bc整除的数num/lcm(b, c)：011, 111
    // 同时能被a、b、c整除的数num/lcm(a, b, c)：111
    //
    // Constraints——给定如下约束:
    // 1 <= n, a, b, c <= 10^9
    // 1 <= a * b * c <= 10^18
    // It's guaranteed that the result will be in range [1, 2 * 10^9]

    /**
     * 功能：求取在[1, num]范围区间内，多少个数能被a或b或c整除
     * @param num —— [1, num]范围区间
     * @param a 丑数因子a
     * @param b 丑数因子b
     * @param c 丑数因子c
     * @return —— 能被上述丑数因子整除的数字总数
     */
    public static long getAmount(long num, long a, long b, long c) {
        long amount = (num / a + num / b + num / c)
                - (num / lcm(a, b) + num / lcm(a, c) + num / lcm(b, c))
                + num / lcm(lcm(a, b), c);
        return amount;
    }

    /**
     * 求取nth丑数
     * 【总结规律】 —— 在一个绝对保险的区间内求值，涉及计算可能O(t)超时甚至内存溢出。
     * 1. 计算在单个最小公倍数区间[1, lcm]内的丑数数量(能够同时整除a和b和c的，有且仅有lcm(a, b, c)一个数)；
     * 2. 各个公倍数区间的丑数数量完全相同，无须重复计算；
     * 3. 将n对amountSingleLCM取模，求取对应数字，叠加前述所有最小公倍数区间即可。
     * @param n
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static long nthUglyNumber(long n, long a, long b, long c) {
        // gcd是所有数a、b、c的最大公约数
        long gcd = gcd(gcd(a, b), c);
        // lcm是所有数a、b、c的最小公倍数
        long lcm = lcm(lcm(a, b), c);
        // 求解在单个LCM区间上包含的丑数个数
        long singleLCMAmount = getAmount(lcm, a, b, c);
        System.out.println("单个最小公倍数区间内含有丑数数量——singleLCMAmount: " + singleLCMAmount);
        // 求解位于目标前面的所有LCM区间的个数
        long areaAmount = n / singleLCMAmount;
        System.out.println("总计包括完整的最小公倍数区间数量——areaAmount: " + areaAmount);
        // 求解目标所在的最后一个LCM区间中的内部偏置位置（各个区间同步）
        long offset = n % singleLCMAmount;
        System.out.println("目标数字是最后一个残缺的最小公倍数区间类的第几个丑数——offset: " + offset);

        // 绝对可以保证在[l, r]范围内存在目标，l = gcd * offset, r = min * offset。
        long min = Math.min(Math.min(a, b), c);

        // 二分法查找获取目标位于最后一个LCM区间中相应偏置位置所对应的具体数字
        long l = gcd * offset, m = 0, r = min * offset;
        while (l < r) {
            m = (int) Math.floor(l + (r - l >> 1));
            if (getAmount(m, a, b, c) < offset) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        System.out.println("通过二分查找法获取最后一个残缺最小公倍数区间内的丑数是：" + r);
        System.out.println("通过扩大范围尝试计算法计算得出的丑数是：" + nthUglyNumberHeuristic(offset, a, b, c));
        return lcm * areaAmount + r;
    }

    // 划定可能范围求解，超时报错：Time Limit Exceeded
    public static long nthUglyNumberHeuristic(long n, long a, long b, long c) {
        // gcd是所有数a、b、c的最大公约数
        long gcd = gcd(gcd(a, b), c);
        // 绝对可以保证在[l, r]范围内存在目标，l = gcd * n, r = min * n。
        long min = Math.min(Math.min(a, b), c);
        // 左值l极限位置（最好情况）是n * gcd —— [1 * gcd, n * gcd)上每个数都是所求丑数总计(n - 1)个，考虑gcd取1或者2进行理解。
        // 右值极限位置（最差情况）是Math.min(a, b, c) * n。
        long l = gcd * n, m, r = min * n;
        while (l < r) {
            m = (long) Math.floor(l + (r - l >> 1));
            if (getAmount(m, a, b, c) < n) {
                l = m + 1;
            } else { // 1) getAmount(m, a, b, c) > n : r = m - 1; 2) getAmount(m, a, b, c) = n : r = m‘，极端情况，l会逐渐逼近r，最终取值r - 1依然不行，下次l = r = m'，跳出循环（此时r = m'）。如此写法，省去>或者=分开判断。
                r = m;
            }
        }
        return r;
    }

    // 暴力破解，超时报错：Time Limit Exceeded
    public static long nthUglyNumberViolent(long n, long a, long b, long c) {
        long num = 1;
        for (long i = 0; i < n; num++) {
             if ((num % a ==0) || (num % b ==0) || (num % c ==0))
                 i++;
        }
        return num--;
    }

    public static void main(String[] args) {
        System.out.println(nthUglyNumber(1000000000, 2, 217983653, 336916467));
    }
}
