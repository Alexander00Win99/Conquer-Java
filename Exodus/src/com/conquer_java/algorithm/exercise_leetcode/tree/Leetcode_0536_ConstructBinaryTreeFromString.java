package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.Stack;

/**
 * You need to construct a binary tree from a string consisting of parenthesis and integers.
 * The whole input represents a binary tree. It contains an integer followed by zero, one or two pairs of parenthesis. The integer represents the root's value and a pair of parenthesis contains a child binary tree with the same structure.
 * You always start to construct the left child node of the parent first if it exists.
 *
 * Example:
 *
 * Input: "4(2(3)(1))(6(5))"
 * Output: return the tree root node representing the following tree:
 *
 *        4
 *      /   \
 *     2     6
 *    / \   /
 *   3   1 5
 * Note:
 *
 * There will only be '(', ')', '-' and '0' ~ '9' in the input string.
 * An empty tree is represented by "" instead of "()".
 */
public class Leetcode_0536_ConstructBinaryTreeFromString {
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

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 【Recursive Method】
     * Define a variable count to find the end position of the outest parenthesis by recording every parenthesis symbol,
     * ++1 if meet '(' and --1 if meet ')'. When count is back to 0, that means we get the string within first parenthesis, which is left child.
     * If now, current index i is still within length of string, then the rest part before (s.length()-1) is right child.
     * Time Complexity: O(s.length * h). h is tree height.
     * Space Complexity: O(s.length * h). h is tree height.
     * @param s
     * @return
     */
    public static TreeNode genBinaryTreeFromString_Recursion(String s) {
        if (s == null || s.length() == 0) return null;

        int fistLeftParenthesisIndex = s.indexOf(LEFT_PARENTHESIS);
        int rootValue = fistLeftParenthesisIndex == -1 ? Integer.valueOf(s) : Integer.valueOf(s.substring(0,fistLeftParenthesisIndex));
        TreeNode root = new TreeNode(rootValue);
        if (fistLeftParenthesisIndex == -1) return root;

        int count = 0; // 记录'('和')'数量，初始为0，遇到'('加一，遇到')'减一，再次归零，前面部分便是其左子树，后面部分如果存在就是其右子树。
        String lChildStr = "", rChildStr = "";
        TreeNode lChild = null, rChild = null;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == LEFT_PARENTHESIS) count++;
            if (s.charAt(i) == RIGHT_PARENTHESIS) {
                count--;
                if (count == 0) {
                    lChildStr = s.substring(fistLeftParenthesisIndex + 1, i);
                    lChild = genBinaryTreeFromString_Recursion(lChildStr);
                    if (i < s.length() - 1) rChildStr = s.substring(i + 2, s.length() - 1);
                    rChild = genBinaryTreeFromString_Recursion(rChildStr);
                    break;
                }
            }
        }

        root.left = lChild;
        root.right = rChild;
        return root;
    }

    /**
     * 【Iterative Method】
     * Use stack to perform preorder iteration. The top of stack should always be current root.
     * When encountering digit, collect all of the consecutive digits and get the value, create a tree node with this value.
     * If stack is not empty, then current node could either be stack top's left child or right child. After that, push current node into stack.
     * When encountering ')', then current level(depth) in this subtree should be finished. Pop the stack.
     *
     * Time Complexity: O(n).
     * Space Complexity: O(h). Tree height.
     * @param s
     * @return
     */
    public static TreeNode genBinaryTreeFromString_Iteration(String s) {
        if (s == null || s.length() == 0) return null;

        Stack<TreeNode> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9' || c == '-') {
                int start = i;
                while (i + 1 < s.length() && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') i++;
                int end = i + 1;
                TreeNode node = new TreeNode(Integer.parseInt(s.substring(start, end)));
                if (!stack.isEmpty()) {
                    TreeNode top = stack.peek();
                    if (top.left == null) top.left = node;
                    else top.right = node;
                }
                stack.push(node);
            } else if (c == '(') {
                continue;
            } else if (c == ')') {
                stack.pop();
            }
        }

        return stack.pop();
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
        s = "4(2(3)(1))(6(5))";
        TreeNode root = genBinaryTreeFromString_Recursion(s);
        System.out.println("前序遍历：");
        dlr(root);
        System.out.println("中序遍历：");
        ldr(root);
        System.out.println("后序遍历：");
        lrd(root);

        System.out.println("Integer的parseInt()和valueOf()能夠处理负号");
        System.out.println(Integer.parseInt("-123"));
        System.out.println(Integer.valueOf("-999"));

        s = "4(2(3)(1))(6(5)(9))";
        root = genBinaryTreeFromString_Iteration(s);
        System.out.println("前序遍历：");
        dlr(root);
        System.out.println("中序遍历：");
        ldr(root);
        System.out.println("后序遍历：");
        lrd(root);
    }
}
