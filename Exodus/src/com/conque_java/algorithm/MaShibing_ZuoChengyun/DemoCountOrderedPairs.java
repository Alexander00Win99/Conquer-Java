package com.conque_java.algorithm.MaShibing_ZuoChengyun;

import java.util.Arrays;

/**
 * 【算法目标】——通过实践，了解一般算法对于时间复杂度和空间复杂度的基本要求。
 *
 * 【预处理技巧】
 * 当算法底层大量出现某种查询并且遍历代价很高时，可以尝试使用预处理数据结构简化单次查询成本，预处理数据结构五花八门，最为常见最为基础的是前缀和。
 *
 * 【腾讯原题】
 * 给定一个整数power，给定一个数组array，给定一个数组reverse。要求如下：
 * array的长度一定是2的power次方，reverse的每个数值都是位于[0, power]区间。
 * 例如：power = 2, array = {3, 1, 4, 2}, reverse = {0, 1, 0, 2}
 * 任何一个在前的数字可以和任何一个在后的数字构成一对，两者可能是：升序关系、相等关系、降序关系。
 * 比如：上述例子具有：升序对——(3, 4), (1, 4), (1, 2)——一共3个；降序对——(3, 1), (3, 2), (4, 2)——一共3个。
 *
 * 随后可以根据reverse数组对array数组进行逆序调整：
 * reverse[0] = 0，表示在array中，按照每1(2的0次方)个数字划分一组，每个小组内部进行逆序操作，那么array变为{3, 1, 4, 2}，此时有3个降序对；
 * reverse[1] = 1，表示在array中，按照每2(2的1次方)个数字划分一组，每个小组内部进行逆序操作，那么array变为{1, 3, 2, 4}，此时有1个降序对；
 * reverse[2] = 0，表示在array中，按照每1(2的0次方)个数字划分一组，每个小组内部进行逆序操作，那么array变为{1, 3, 2, 4}，此时有1个降序对；
 * reverse[3] = 2，表示在array中，按照每4(2的2次方)个数字划分一组，每个小组内部进行逆序操作，那么array变为{4, 2, 3, 1}，此时有5个降序对；
 * 所以，最终返回{3, 1, 1, 5}，表示每次逆序调整之后降序对数量。
 *
 * 输入数据限定如下：
 * power取值范围：[0, 20]
 * array长度范围：[1, 10^6]
 * reverse长度范围：[1, 10^6]
 *
 * 【分析过程】
 * 传统方法无法满足要求：
 * getUpPairs|getDownPairs的for循环遍历O(n) *
 * (reverseArray(int[], int)的O(n/groupSize) * reverseArray(int[], int, int)的O(n/groupSize)) + O(n*logn)
 * =
 * 10^6 *(10^6 + 10^6 * log10^6) > 10^8 | 10^9
 *
 * 假设array的长度为2^power次方，那么存在(power + 1)种长度依次翻倍的划分办法，依次是：2^0, 2^1, 2^2, ......, 2^power。
 * 按照每段2^i长度进行空间划分，约定upRecord[i]和downRecord[i]分别表示在当前划分方式下的前半部分数字和后半部分数字构成升序对和降序度数量，显然这种划分可以满足：
 * 2^0划分方式：没有意义，忽略不计；
 * 2^1划分方式：如若新的数组相对老的数组在当前2^1长度级别上针对所有局部区间进行逆序，只对2^0和2^1划分方式产生影响，具体结果是，新数组相对老数组升序对数量和降序对数量刚好对调；
 * 2^2划分方式：如若新的数组相对老的数组在当前2^2长度级别上针对所有局部区间进行逆序，只对2^0和2^1和2^2划分方式产生影响，具体结果是，新数组相对老数组升序对数量和降序对数量刚好对调；
 * ......
 * 2^i划分方式：如若新的数组相对老的数组在当前2^i长度级别上针对所有局部区间进行逆序，只对2^0, 2^1, 2^2, ......, 2^i划分方式产生影响，具体结果是，新数组相对老数组升序对数量和降序对数量刚好对调；
 * ......
 * 2^power划分方式：如若新的数组相对老的数组在当前2^power长度级别上针对所有局部区间进行逆序，只对2^0, 2^1, 2^2, ......, 2^power划分方式产生影响，具体结果是，新数组相对老数组升序对数量和降序对数量刚好对调；
 * 当前数组的所有升序对或者降序对数量是上述各个级别划分方式的升序对或者降序对数量之和。
 * 例如：
 * {a, b, c, d, e, f, g, h}如果在2^2长度级别上进行逆序，{h, g, f, e, d, c, b, a}，那么，2^1和2^2级别的升序对和降序对数量需要对调，
 * 2^3级别不受影响，原因在于，upRecord[3]和downRecord[3]表示2^3长度级别前后部分的升降序对数量，然后当前逆序并不影响2^3级别的前后部分的相对位置关系。
 */
public class DemoCountOrderedPairs {
    public static int[] getUpPairs(int[] originalArray, int[] reverse, int power) {
        int[] res = new int[reverse.length];
        for (int i = 0; i < res.length; i++) {
            reverseArray(originalArray, 1 << reverse[i]); // 对于reverse[i]，每2^(reverse[i])个元素一组内部逆序
            res[i] = countPair(originalArray)[0]; // 累加统计升序对
        }
        return res;
    }

    public static int[] getDownPairs(int[] originalArray, int[] reverse, int power) {
        int[] res = new int[reverse.length];
        for (int i = 0; i < res.length; i++) {
            reverseArray(originalArray, 1 << reverse[i]); // 对于reverse[i]，每2^(reverse[i])个元素一组内部逆序
            res[i] = countPair(originalArray)[2]; // 累加统计降序对
        }
        return res;
    }

    /**
     * 多个区域依次逆序 ==> 逆序操作整个数组：对于数组每groupSize个元素划分一组，每组内部各自进行逆序操作
     * 实现方法：从前到后划分为多个等长局部空间，然后逐个逆序各个空间内的元素。
     * @param array
     * @param groupSize
     */
    private static void reverseArray(int[] array, int groupSize) {
        if (groupSize <= 1) return;
        for (int i = 0; i < array.length; i += groupSize) {
            reverseArray(array, i, i + groupSize - 1);
        }
    }

    /**
     * 逆序操作数组某个局部区间上的元素：对于从from到to的局部区间的数组元素进行逆序操作
     * @param array
     * @param from
     * @param to
     */
    private static void reverseArray(int[] array, int from, int to) {
        if (from < 0 || from >= to || to > array.length - 1)
            throw new RuntimeException("Error");
        int tmp;
        while (from <= to) {
            tmp = array[from];
            array[from++] = array[to];
            array[to--] = tmp;
        }
    }

    private static int[] countPair(int[] array) {
        int up = 0, equal = 0, down =0;
        int[] res = new int[3];
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] < array[j])
                    up++;
                if (array[i] == array[j])
                    equal++;
                if (array[i] > array[j])
                    down++;
            }
        }
        res[0] = up;
        res[1] = equal;
        res[2] = down;
        return res;
    }

    public static int[][] getOrderedPairs(int[] originalArr, int[] reverse, int power) {
        int[][] res = new int[2][reverse.length]; // res[0]<==>ascendPairs存放升序对数，res[1]<==>descendPairs存放降序对数。
        //int[] ascendPairs = new int[reverse.length], descendPairs = new int[reverse.length];
        int[] array;
        int[] upRecord = new int[power + 1];
        int[] downRecord = new int[power + 1];

        array = Arrays.copyOf(originalArr, originalArr.length); // 拷贝原数组，计算原始升序对数量
        mergeSort(array, 0, array.length - 1, "up", upRecord, power);
        System.out.println("求得初始升序对，各个2^i级别具体分布如下：");
        printArray(upRecord);
        array = Arrays.copyOf(originalArr, originalArr.length); // 拷贝原数组，计算原始降序对数量
        mergeSort(array, 0, array.length - 1, "down", downRecord, power);
        System.out.println("求得初始降序对，各个2^i级别具体分布如下：");
        printArray(downRecord);

        System.out.println("逆序操作步骤如下：");
        printArray(reverse);
        for (int i = 0; i < reverse.length; i++) {
            int curPower = reverse[i];
            System.out.println(String.format("STEP-%d，在2^%d区间长度级别进行逆序操作后：", i, curPower));
            for (int j = 1; j <= curPower; j++) { // 2^curPower级别的逆序操作，只会影响当前以及以下级别的升降序对数结果
                int tmp = upRecord[j];
                upRecord[j] = downRecord[j];
                downRecord[j] = tmp;
            }
            for (int j = 1; j <= power; j++) { // 累加各个2^i(1~power)划分级别的升降序对数，upRecord[0]=0，downRecord=0，无须计算
                res[0][i] += upRecord[j];
                res[1][i] += downRecord[j];
            }
            System.out.println("当前序列全部升序对数：" + res[0][i]);
            System.out.println("当前序列全部逆序对数：" + res[1][i]);
        }
        return res;
    }

    /**
     * 利用归并排序同时计算升序对|降序对数量
     * @param array
     * @param l
     * @param r
     * @param order
     * @param record
     * @param power
     */
    public static void mergeSort(int[] array, int l, int r, String order, int[] record, int power) {
        if (l == r) {
            //System.out.println("power触底反弹：" + power); // 当(l == r)时，power == 0成立。
            record[power] = 0; // 2^0级别划分，升序对和降序对均无意义，赋值为0。
            return;
        }

        int mid = l + ((r - l) >> 1);
        mergeSort(array, l, mid, order, record, power - 1);
        mergeSort(array, mid + 1, r, order, record, power - 1);
        merge(array, l, mid, r, order, record, power - 1);
    }

    /**
     * 注意操作细节：root在于——机器模拟人类判断顺序的方式是固定不变的：从左边集合中取出一个值，依次和右边集合中的每个值进行比较，左值 VS 右值，根据结果，累加更新。
     * 右端作为比较基准，每次变动更新结果(累加当前左端长度)
     * 1) 求取升序对数量，需要使用降序排列：左值>=右值，剔除左值(pl左端等值剔除不会影响升序对数量——赋值为0，但是影响降序对数量)；左值<右值，剔除右值(此时，升序对数量已做更新：if (array[pl] < array[pr]) mid - pl + 1)；
     * 2) 求取降序对数量，需要使用升序排列：左值<=右值，剔除左值(pl左端等值剔除不会影响降序对数量——赋值为0，但是影响升序对数量)；左值>右值，剔除右值(此时，降序对数量已做更新：if (array[pl] > array[pr]) mid - pl + 1)；
     * 左端作为比较基准，每次变动更新结果(累加当前右端长度)
     * 3) 求取升序对数量，需要使用升序排列：左值>=右值，剔除右值(pr右端等值剔除不会影响升序对数量——赋值为0，但是影响降序对数量)；左值<右值，剔除左值(此时，升序对数量已做更新：if (array[pl] < array[pr]) r - pr + 1)；
     * 4) 求取降序对数量，需要使用降序排列：左值>=右值，剔除右值(pr右端等值剔除不会影响降序对数量——赋值为0，但是影响升序对数量)；左值>右值，剔除左值(此时，降序对数量已做更新：if (array[pl] > array[pr]) r - pr + 1)；
     * @param array
     * @param l
     * @param mid
     * @param r
     * @param order
     * @param record
     * @param power
     */
    public static void merge(int[] array, int l, int mid, int r, String order, int[] record, int power) {
        int[] helper = new int[r - l + 1];

        int i = 0;
        int pl = l;
        int pr = mid + 1;
        while (pl <= mid && pr <= r) {
            /*
            //----------------++++++++++++++++使用右端作为基准：剔除左值更新为0，剔除右值更新为左端当前长度(mid - pl + 1)++++++++++++++++----------------
            if (order.equals("down")) { // 数组降序排列，求解升序对数
                record[power + 1] += array[pl] >= array[pr] ? 0 : mid - pl + 1; // 当array[pl] < array[pr]时，下面array[pr++]语句即将剔除右值，但是在此之前，已经更新升序对数为(mid - pl + 1)；当array[pl] >= array[pr]时，无需更新，直接剔除。
                helper[i++] = array[pl] >= array[pr] ? array[pl++] : array[pr++]; // 当array[pl] < array[pr]时，array[pr++]剔除右值(后续没有机会参与左右比较)
            }
            if (order.equals("up")) { // 数组升序排列，求解降序对数
                record[power + 1] += array[pl] <= array[pr] ? 0 : mid - pl + 1; // 当array[pl] > array[pr]时，下面array[pr++]语句即将剔除右值，但是在此之前，已经更新降序对数为(mid - pl + 1)；当array[pl] <= array[pr]时，无需更新，直接剔除。
                helper[i++] = array[pl] <= array[pr] ? array[pl++] : array[pr++]; // 当array[pl] > array[pr]时，array[pr++]剔除右值(后续没有机会参与左右比较)
            }
            //----------------++++++++++++++++使用右端作为基准：剔除左值更新为0，剔除右值更新为左端当前长度(mid - pl + 1)++++++++++++++++----------------
             */

            //----------------++++++++++++++++使用左端作为基准：剔除右值更新为0，剔除左值更新为右端当前长度(r - pr + 1)++++++++++++++++----------------
            if (order.equals("up")) { // 数组升序排列，求解升序对数
                record[power + 1] += array[pl] < array[pr] ? r - pr + 1 : 0; // 当array[pl] < array[pr]时，下面array[pl++]语句即将剔除左值，但是在此之前，已经更新降序对数为(r - pr + 1)；当array[pl] >= array[pr]时，无需更新，直接剔除。
                helper[i++] = array[pl] < array[pr] ? array[pl++] : array[pr++]; // 当array[pl] < array[pr]时，array[pl++]剔除左值(后续没有机会参与左右比较)
            }
            if (order.equals("down")) { // 数组降序排列，求解降序对数
                record[power + 1] += array[pl] > array[pr] ? r - pr + 1 : 0; // 当array[pl] > array[pr]时，下面array[pl++]语句即将剔除左值，但是在此之前，已经更新升序对数为(r - pr + 1)；当array[pl] <= array[pr]时，无需更新，直接剔除。
                helper[i++] = array[pl] > array[pr] ? array[pl++] : array[pr++]; // 当array[pl] > array[pr]时，array[pl++]剔除左值(后续没有机会参与左右比较)
            }
            //----------------++++++++++++++++使用左端作为基准：剔除右值更新为0，剔除左值更新为右端当前长度(r - pr + 1)++++++++++++++++----------------
        }
        while (pl <= mid)
            helper[i++] = array[pl++];
        while (pr <= r)
            helper[i++] = array[pr++];
        System.arraycopy(helper, 0, array, l, r - l + 1);
    }
    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + "\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] array;

        int power = 3;
        int[] reverse = new int[]{0, 1, 0, 2};
        System.out.println("----------------++++++++++++++++Begin：暴力求解++++++++++++++++----------------");
        array = new int[]{3, 1, 2, 4, 5, 0, 6, 2};
        printArray(getUpPairs(array, reverse, power));
        array = new int[]{3, 1, 2, 4, 5, 0, 6, 2};
        printArray(getDownPairs(array, reverse, power));
        System.out.println("----------------++++++++++++++++End：暴力求解++++++++++++++++----------------");

        int[] upRecord = new int[power + 1];
        int[] downRecord = new int[power + 1];

        /*
        System.out.println("----------------++++++++++++++++升序数列求解降序对数，降序数列求解升序对数++++++++++++++++----------------");
        array = new int[]{3, 1, 2, 4, 5, 0, 6, 2};
        System.out.println("原数组：");
        printArray(array);
        System.out.println("原数组降序排列以后：");
        mergeSort(array, 0, array.length - 1, "down", upRecord, power + 1);
        printArray(array);
        System.out.println("升序对：");
        printArray(upRecord);

        array = new int[]{3, 1, 2, 4, 5, 0, 6, 2};
        System.out.println("原数组：");
        printArray(array);
        System.out.println("原数组升序排列以后：");
        mergeSort(array, 0, array.length - 1, "up", downRecord, power + 1);
        printArray(array);
        System.out.println("降序对：");
        printArray(downRecord);
        System.out.println("----------------++++++++++++++++升序数列求解降序对数，降序数列求解升序对数++++++++++++++++----------------");
         */

        System.out.println("----------------++++++++++++++++升序数列求解升序对数，降序数列求解降序对数++++++++++++++++----------------");
        array = new int[]{3, 1, 2, 4, 5, 0, 6, 2};
        System.out.println("原数组：");
        printArray(array);
        System.out.println("原数组升序排列以后：");
        mergeSort(array, 0, array.length - 1, "up", upRecord, power);
        printArray(array);
        System.out.println("各个2^i(1~power)划分级别上的升序对数：");
        printArray(upRecord);

        array = new int[]{3, 1, 2, 4, 5, 0, 6, 2};
        System.out.println("原数组：");
        printArray(array);
        System.out.println("原数组降序排列以后：");
        mergeSort(array, 0, array.length - 1, "down", downRecord, power);
        printArray(array);
        System.out.println("各个2^i(1~power)划分级别上的降序对数：");
        printArray(downRecord);
        System.out.println("----------------++++++++++++++++升序数列求解升序对数，降序数列求解降序对数++++++++++++++++----------------");

        System.out.println("----------------++++++++++++++++Begin：划级求解++++++++++++++++----------------");
        int[][] result;
        power = 2;
        array = new int[]{3, 1, 4, 2};
        reverse = new int[]{0, 1, 0, 2};
        result = new int[2][reverse.length];
        result = getOrderedPairs(array, reverse, power);
        System.out.println("各次逆序操作对应的升序对数：");
        printArray(result[0]);
        System.out.println("各次逆序操作对应的降序对数：");
        printArray(result[1]);

        power = 3;
        array = new int[]{3, 1, 2, 4, 5, 0, 6, 2};
        reverse = new int[]{0, 1, 0, 2, 0, 3};
        result = new int[2][reverse.length];
        result = getOrderedPairs(array, reverse, power);
        System.out.println("各次逆序操作对应的升序对数：");
        printArray(result[0]);
        System.out.println("各次逆序操作对应的降序对数：");
        printArray(result[1]);
        System.out.println("----------------++++++++++++++++End：划级求解++++++++++++++++----------------");
    }
}
