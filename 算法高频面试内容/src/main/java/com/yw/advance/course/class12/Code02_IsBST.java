package com.yw.advance.course.class12;

import com.yw.entity.TreeNode;

import java.util.ArrayList;
import java.util.List;

import static com.yw.util.CommonUtils.generateRandomBST;

/**
 * @author yangwei
 */
public class Code02_IsBST {

    // 方法一：基于中序遍历，中序收集所有值，判断是否升序
    public static boolean isBstByInOrder(TreeNode root) {
        if (root == null) return true;
        List<Integer> ins = new ArrayList<>();
        inOrder(root, ins);
        for (int i = 1; i < ins.size(); i++) {
            // !!!必须: 左边 < 根 < 右边，等于都不是搜索二叉树
            if (ins.get(i) <= ins.get(i - 1)) return false;
        }
        return true;
    }

    private static void inOrder(TreeNode root, List<Integer> ins) {
        if (root == null) return;
        inOrder(root.left, ins);
        ins.add(root.val);
        inOrder(root.right, ins);
    }

    // 方法二：基于递归套路
    public static boolean isBst(TreeNode root) {
        if (root == null) return true;
        return process(root).bst;
    }

    // 1. 封装信息
    private static class Info {
        boolean bst;
        int min;
        int max;

        public Info(boolean bst, int min, int max) {
            this.bst = bst;
            this.min = min;
            this.max = max;
        }
    }

    // 2. 实现递归
    private static Info process(TreeNode root) {
        if (root == null) return null;
        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);

        boolean bst = true;
        int max = root.val;
        int min = root.val;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
            bst = leftInfo.bst && leftInfo.max < root.val;
        }
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
            bst = bst & (rightInfo.bst && rightInfo.min > root.val);
        }

        return new Info(bst, min, max);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxVal = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode root = generateRandomBST(maxLevel, maxVal);
            if (isBstByInOrder(root) != isBst(root)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
