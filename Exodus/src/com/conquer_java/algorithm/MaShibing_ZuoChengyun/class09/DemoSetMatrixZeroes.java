package com.conquer_java.algorithm.MaShibing_ZuoChengyun.class09;

/**
 * 【题目】
 * 给定一个二维数组matrix，所有元素都是整数，请将其中所有的0数字做上下左右4个方向的扩展，返回处理之后的数组。
 * 要求：整个处理过程在matrix数组本身上发生，要求使用有限几个变量，也即额外空间复杂度做到O(1)。
 *
 * 【思路】
 * 1) 肯定需要遍历数组用以确定哪里有0；
 * 2) 肯定不能一边遍历一边处理，否则新扩展出的0会被当做将来的扩展原点；
 * 3) 在遍历过程中，每当找到一个0，就向0行以及0列映射，将来基于0行0列上的0数字进行单维扩展处理，即可简化处理过程(随机散落点坐标+纵横双维扩展->0行纵向0列横向单维扩展)；
 * 4) 左上顶点位置含义特殊，matrix[0][0]如果为0，代表0行0列都要清零，行列信息叠加一点，无法区分，因此，需要分拆使用clear0Row+clear0Col分别表示；
 * 5)
 */
public class DemoSetMatrixZeroes {
    public static void setZeros(int[][] matrix) {
        int i = 0;
        int j = 0;

        // 遍历0行0列设置相关变量
        boolean clear0Row = false;
        boolean clear0Col = false;
        for (i = 0; i < matrix[0].length; i++) {
             if (matrix[0][i] == 0) {
                 clear0Row = true;
                 break;
             }
        }
        for (j = 0; j < matrix.length; j++) {
            if (matrix[j][0] == 0) {
                clear0Col = true;
                break;
            }
        }

        // 跳过0行0列，遍历剩下区域，每当查找到0就向0行以及0列各自映射一个0，代表将来对应该列以及该行可以集中置0。
        for (i = 1; i < matrix.length; i++) {
            for (j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        // 根据0行0列上面映射的0置0对应的行列
//        for (i = 1; i < matrix.length; i++) {
//            for (j = 1; j < matrix[0].length; j++) {
//                if (matrix[i][0] == 0 || matrix[0][j] == 0) { // 逐行逐列扫描，如果row0或者col0映射位置元素任一为0，相应元素置0
//                    matrix[i][j] = 0;
//                }
//            }
//        }
        for (i = 1; i < matrix.length; i++) { // 遍历第一列col0
            if (matrix[i][0] == 0) { // 如果第一列col0中某个元素matrix[i][0]为0
                for (j = 1; j < matrix[0].length; j++) { // 横向扩展整行置0
                    matrix[i][j] = 0;
                }
            }
        }
        for (j = 1; j < matrix[0].length; j++) { // 遍历第一行row0
            if (matrix[0][j] == 0) { // 如果第一行row0中某个元素matrix[0][j]为0
                for (i = 1; i < matrix.length; i++) { // 纵向扩展整列置0
                    matrix[i][j] = 0;
                }
            }
        }

        // 最后处理0行0列数据
        if (clear0Row) {
            for (j = 0; j < matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }
        if (clear0Col) {
            for (i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    public static void setZerosOptimized(int[][] matrix) {
        int i = 0;
        int j = 0;
        // 剥离matrix[0][0]单独处理：为0，只能代表row0将来扩展置0，并不代表col0将来扩展置0
        // 因此，需要单独额外定义变量col0表示col0将来是否扩展置0
        boolean clear0Col = false;

        // 无需跳过0行0列，遍历整个矩阵，每当查找到0就向0行以及0列各自映射一个0，代表将来对应该列以及该行可以集中置0(0列除外)。
        for (i = 0; i < matrix.length; i++) {
            for (j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0; // 横向映射不受限制
                    //matrix[0][j] = 0;——纵向映射加以限制
                    // 1) y == 0: matrix[x][y==0] == 0 => clear0Col = true; 2) y != 0: matrix[x][y!=0] == 0 => matrix[0][y!=0] = 0;
                    if (j == 0) // 第一列某个元素为0，不再纵向映射导致matrix[0][0]置0，因此，如果matrix[0][0]为0，只可能是，matrix[0][0]初始为0或者第一行某个非左上顶点元素横向映射导致后来置0
                        clear0Col = true;
                    else // 非第一列元素为0，正常纵向映射设置第一行相应元素为0
                        matrix[0][j] = 0;
                }
            }
        }

        // 最后处理0列数据
        for (i = matrix.length - 1; i >= 0; i--) { // 必须自底向上逐行扫描
            for (j = 1; j < matrix[0].length; j++) { // 第一列col0预留将来处理
                if (matrix[i][0] == 0 || matrix[0][j] == 0) { // 逐行逐列扫描，如果row0或者col0映射位置元素任一为0，相应元素置0
                    matrix[i][j] = 0;
                }
            }
        }

        if (clear0Col) {
            for (i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][] {
                {7, 0, 1, 4, 6},
                {6, 5, 3, 2, 1},
                {4, 2, 1, 0, 3},
                {8, 2, 1, 2, 9},
                {1, 3, 5, 7, 9}
        };
        setZeros(matrix);
        matrix = new int[][] {
                {7, 0, 1, 4, 6},
                {6, 5, 3, 2, 1},
                {4, 2, 1, 0, 3},
                {8, 2, 1, 2, 9},
                {1, 3, 5, 7, 9}
        };
        setZerosOptimized(matrix);
    }
}
