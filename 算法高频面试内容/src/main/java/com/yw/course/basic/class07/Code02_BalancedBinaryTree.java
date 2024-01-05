package com.yw.course.basic.class07;

import com.yw.entity.TreeNode;

/**
 * 测试链接：https://leetcode.cn/problems/balanced-binary-tree
 *
 * @author yangwei
 */
public class Code02_BalancedBinaryTree {

    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        return isBalanced(root.left) && isBalanced(root.right)
                && Math.abs(getHeight(root.left) - getHeight(root.right)) <= 1;
    }
    private int getHeight(TreeNode root) {
        if (root == null) return 0;
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean i, int h) {
            isBalanced = i;
            height = h;
        }
    }

    public static boolean isBalanced2(TreeNode root) {
        return process(root).isBalanced;
    }

    public static Info process(TreeNode root) {
        if (root == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalanced = leftInfo.isBalanced && rightInfo.isBalanced
                && Math.abs(leftInfo.height - rightInfo.height) < 2;
        return new Info(isBalanced, height);
    }

}
