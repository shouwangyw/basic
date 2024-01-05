package com.yw.course.advance.class12;

import com.yw.entity.TreeNode;

import static com.yw.util.CommonUtils.generateRandomBST;

/**
 * @author yangwei
 */
public class Code04_IsFull {

    // 满二叉树：假设二叉树高度h，节点数n，那么 n = 2^h - 1
    public static boolean isFull(TreeNode root) {
        Info info = processHeightAndNodeNums(root);
        return info.n == (1 << info.h) - 1;
    }

    public static boolean isFull2(TreeNode root) {

        return processHeightAndIsFull(root).is;
    }

    // 1. 封装信息
    private static class Info {
        int h;      // 树高度
        int n;      // 树的节点数量
        boolean is; // 是否满二叉树

        public Info(int h, int n) {
            this.h = h;
            this.n = n;
        }

        public Info(int h, boolean is) {
            this.h = h;
            this.is = is;
        }
    }

    // 2. 实现递归
    // 方法一：处理【树高度h、节点数n】两个信息
    private static Info processHeightAndNodeNums(TreeNode root) {
        if (root == null) return new Info(0, 0);
        Info leftInfo = processHeightAndNodeNums(root.left);
        Info rightInfo = processHeightAndNodeNums(root.right);

        int h = Math.max(leftInfo.h, rightInfo.h) + 1;
        int n = leftInfo.n + rightInfo.n + 1;

        return new Info(h, n);
    }

    // 方法二：处理【树高度h、是否满】两个信息
    private static Info processHeightAndIsFull(TreeNode root) {
        if (root == null) return new Info(0, true);
        Info leftInfo = processHeightAndIsFull(root.left);
        Info rightInfo = processHeightAndIsFull(root.right);

        int h = Math.max(leftInfo.h, rightInfo.h) + 1;
        boolean is = leftInfo.is && rightInfo.is && leftInfo.h == rightInfo.h;

        return new Info(h, is);
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            TreeNode root = generateRandomBST(maxLevel, maxValue);
            if (isFull(root) != isFull2(root)) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }

}
