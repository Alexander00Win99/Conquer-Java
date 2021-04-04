package com.conquer_java.algorithm.exercise_interview;

public class HW_ConstructBinaryTreeFromString {
    private final static char LEFT_PARENTHESIS = '('; // 小括号|圆括号
    private final static char RIGHT_PARENTHESIS = ')';
    private final static char LEFT_BRACKET = '['; // 中括号|方括号
    private final static char RIGHT_BRACKET = ']';
    private final static char LEFT_BRACE = '{'; // 大括号|花括号
    private final static char RIGHT_BRACE = '}';
    private final static char OVERBAR = '_'; // 上划线
    private final static char HYPHEN = '-'; // 中划线|减号|负号|连字符
    private final static char UNDERSCORE = '_'; // 下划线
    private final static char DASH = '—'; // 破折号（比中划线长）
    private final static char COMMA = ',';
    private final static char SEMICOLON = ';';
    private final static char SINGLE_QUOTE = '\'';
    private final static char DOUBLE_QUOTE = '\"';
    private final static char EXCLAMATION = '!';

    public static class TreeNode {
        String val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(String val) {
            this.val = val;
        }
        TreeNode(String val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static TreeNode genBinaryTreeFromString(String s) {
        if (s == null || s.length() == 0) {
            System.out.println("触空返回null");
            return null;
        }

        int braceIndex = s.indexOf(LEFT_BRACE);
        int commaIndex = s.indexOf(COMMA);
        TreeNode root = braceIndex == -1 ? new TreeNode(s) : new TreeNode(s.substring(0, braceIndex));
        // 0-1) 情况0-1：字符串中既无花括号又无逗号：
        // 整个字符串内容作为root节点，没有左右子树
        if (braceIndex == -1 && commaIndex == -1) {
            System.out.println("字符串中没有花括号和逗号，直接返回作为root：" + s);
            return root;
        }

        // 0-2) 情况0-2：字符串中即虽无花括号但有逗号：
        // 该种情形不符合字符串输入规则
        if (braceIndex == -1 && commaIndex != -1) {
            System.out.println("字符串中虽无花括号但有逗号，逗号前后分为左右子树：真见鬼了！");
            return null;
        }

        String subStr = s.substring(braceIndex + 1, s.length() - 1); // s.length() - 1位置是'}'，对应首个'{'，需要一并去除。
        System.out.println("子字符串：" + subStr);
        String lChildStr = "", rChildStr = "";
        TreeNode lChild = null, rChild = null;
        int subBraceIndex = subStr.indexOf(LEFT_BRACE);
        int subCommaIndex = subStr.indexOf(COMMA);

        // 1) 情况一：子字符串中既无花括号又无逗号：
        // 整个子字符串作为左子树字符串
        if (subBraceIndex == -1 && subCommaIndex == -1) {
            System.out.println("情况一子字符串整个用于构建左子树：" + subStr);
            lChildStr = subStr;
        }

        // 2) 情况二：子字符串中没有花括号但有逗号：
        // 根据逗号确定左右子树分界（若有逗号，可能没有左子树，但是右子树肯定存在）
        if (subBraceIndex == -1 && subCommaIndex != -1) {
            lChildStr = subStr.substring(0, subCommaIndex);
            rChildStr = subStr.substring(subCommaIndex + 1);
            System.out.println("情况二左子字符串：" + lChildStr);
            System.out.println("情况二右子字符串：" + rChildStr);
        }

        // 3) 情况三：子字符串中没有逗号但有花括号：
        // 字符串中花括号前部分作为root
        // 子字符串中花括号前部分作为root.left
        // 子字符串中剥离花括号以后剩余部分作为root.left.left
        if (subBraceIndex != -1 && subCommaIndex == -1) {
            root = new TreeNode(s.substring(0, braceIndex));
            root.left = new TreeNode(subStr.substring(0, subBraceIndex));
            root.left.left = genBinaryTreeFromString(subStr.substring(subBraceIndex + 1, subStr.length() - 1));
            return root;
        }

        // 4) 情况四：子字符串中既有花括号又有逗号：
        // Step04-01——逗号位于花括号之前：根据首个逗号作为作为左右子树分界；
        // Step04-02——逗号位于花括号之后：根据首个花括号结束位确定左子树边界，剩余部分作为右子树；
        if (subBraceIndex != -1 && subCommaIndex != -1) {
            if (subCommaIndex < subBraceIndex) {
                lChildStr = subStr.substring(0, subCommaIndex);
                rChildStr = subStr.substring(subCommaIndex + 1);
                System.out.println("情况四左子字符串（逗号在前）：" + lChildStr);
                System.out.println("情况四右子字符串（逗号在前）：" + rChildStr);
            } else {
                int count = 0; // 记录括号数量，初始化为0 —— 用于计算左子树结束位置，如果结束位置已经到达最后，那么右子树不存在。
                for (int i = braceIndex + 1; i < s.length(); i++) {
                    if (s.charAt(i) == LEFT_BRACE) count++;
                    if (s.charAt(i) == RIGHT_BRACE) {
                        count--;
                        if (count == 0) {
                            lChildStr = s.substring(braceIndex + 1, i + 1);
                            if (i < subStr.length() - 1) rChildStr = s.substring(i + 2, s.length() - 1);
                            System.out.println("情况四左子字符串（逗号在后）：" + lChildStr);
                            System.out.println("情况四右子字符串（逗号在后）：" + rChildStr);
                            break;
                        }
                    }
                }
            }
        }

        lChild = genBinaryTreeFromString(lChildStr);
        rChild = genBinaryTreeFromString(rChildStr);
        root.left = lChild;
        root.right = rChild;
        return root;
    }

    public static void dlr(TreeNode root) {
        if (root == null) return;

        System.out.println(root.val);
        dlr(root.left);
        dlr(root.right);
    }

    public static void ldr(TreeNode root) {
        if (root == null) return;

        ldr(root.left);
        System.out.println(root.val);
        ldr(root.right);
    }

    public static void lrd(TreeNode root) {
        if (root == null) return;

        lrd(root.left);
        lrd(root.right);
        System.out.println(root.val);
    }

    public static void main(String[] args) {
        String s;
        s = "a{b{d,e{g,h{,i}}},c{f}}";

        System.out.println("substring(x,x)返回值equals空串而非null");
        System.out.println(s.substring(0,0).length());
        System.out.println(s.substring(0,0) == null);
        System.out.println(s.substring(0,0) == "");
        System.out.println(s.substring(0,0).equals(null));
        System.out.println(s.substring(0,0).equals(""));
        System.out.println(s.substring(0,0));

        System.out.println("初始字符串：" + s);
        TreeNode root = genBinaryTreeFromString(s);
        System.out.println("前序遍历：");
        dlr(root);
        System.out.println("中序遍历：");
        ldr(root);
        System.out.println("后序遍历：");
        lrd(root);
    }
}
