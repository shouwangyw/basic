package com.yw.advance.course.class12;

import com.yw.entity.TreeNode;

import java.util.ArrayList;
import java.util.List;

import static com.yw.util.CommonUtils.generateRandomBST;

/**
 * @author yangwei
 */
public class Code05_MaxSubBSTSize {

    // 方法一：普通递归方式
    public static int getMaxSubBstSizeByInOrder(TreeNode root) {
        if (root == null) return 0;
        // 递归求每一颗子树的最大子二叉搜索树大小，分为三种情况：左子树、右子树、包含当前根节点的树
        int bstSize = getBstSize(root);
        return Math.max(bstSize, Math.max(getMaxSubBstSizeByInOrder(root.left), getMaxSubBstSizeByInOrder(root.right)));
    }
    private static int getBstSize(TreeNode root) {
        List<Integer> ins = new ArrayList<>();
        inOrder(root, ins);
        for (int i = 1; i < ins.size(); i++) {
            // 如果不是二叉搜索树，返回特殊值0，交给上游处理
            if (ins.get(i) <= ins.get(i - 1)) return 0;
        }
        return ins.size();
    }
    private static void inOrder(TreeNode root, List<Integer> ins) {
        if (root == null) return;
        inOrder(root.left, ins);
        ins.add(root.val);
        inOrder(root.right, ins);
    }

    // 方法二：基于递归套路
    public static int getMaxSubBstSize(TreeNode root) {
        if (root == null) return 0;

        return process(root).maxBstSize;
//		return process2(root).maxBstSize;
    }
    private static class Info {
        boolean is;    // 是否二叉搜索树
        int min;        // 子树节点最小值
        int max;        // 子树节点最大值
        int size;        // 二叉树节点数量
        int maxBstSize;    // 最大二叉搜索树节点数量

        public Info(boolean is, int min, int max, int maxBstSize) {
            this.is = is;
            this.min = min;
            this.max = max;
            this.maxBstSize = maxBstSize;
        }

        public Info(int min, int max, int size, int maxBstSize) {
            this.min = min;
            this.max = max;
            this.size = size;
            this.maxBstSize = maxBstSize;
        }
    }
    // 信息处理方式一：基于 is、min、max、maxBstSize
    private static Info process(TreeNode root) {
        if (root == null) return null;
        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);

        boolean is = true;
        int min = root.val;
        int max = root.val;
        int maxBstSize = 1;
        if (leftInfo != null) {
            is = leftInfo.is && leftInfo.max < root.val;
            min = Math.min(leftInfo.min, min);
            max = Math.max(leftInfo.max, max);
            maxBstSize = Math.max(leftInfo.maxBstSize, maxBstSize);
        }
        if (rightInfo != null) {
            is &= rightInfo.is && root.val < rightInfo.min;
            min = Math.min(rightInfo.min, min);
            max = Math.max(rightInfo.max, max);
            maxBstSize = Math.max(rightInfo.maxBstSize, maxBstSize);
        }
        if (is) {
            // 如果当前是二叉搜索树，则最大值有三种情况：左+根、右+根、左+根+右
            if (leftInfo != null && rightInfo != null) {
                maxBstSize = leftInfo.maxBstSize + rightInfo.maxBstSize + 1;
            } else if (leftInfo != null) maxBstSize = Math.max(maxBstSize, leftInfo.maxBstSize + 1);
            else if (rightInfo != null) maxBstSize = Math.max(maxBstSize, rightInfo.maxBstSize + 1);
        }

        return new Info(is, min, max, maxBstSize);
    }
    // 信息处理方式二：基于 min、max、size、maxBstSize（因为size==maxBstSize等价于isBst）
    private static Info process2(TreeNode root) {
        if (root == null) return null;
        Info leftInfo = process2(root.left);
        Info rightInfo = process2(root.right);

        int min = root.val;
        int max = root.val;
        int size = 1;
        int maxBstSize = 1;
        boolean is = true;
        if (leftInfo != null) {
            min = Math.min(leftInfo.min, min);
            max = Math.max(leftInfo.max, max);
            size += leftInfo.size;
            maxBstSize = Math.max(leftInfo.maxBstSize, maxBstSize);
            is = leftInfo.size == leftInfo.maxBstSize && leftInfo.max < root.val;
        }
        if (rightInfo != null) {
            min = Math.min(rightInfo.min, min);
            max = Math.max(rightInfo.max, max);
            size += rightInfo.size;
            maxBstSize = Math.max(rightInfo.maxBstSize, maxBstSize);
            is &= rightInfo.size == rightInfo.maxBstSize && root.val < rightInfo.min;
        }
        if (is) {
            if (leftInfo != null && rightInfo != null) {
                maxBstSize = leftInfo.maxBstSize + rightInfo.maxBstSize + 1;
            } else if (leftInfo != null) maxBstSize = Math.max(maxBstSize, leftInfo.maxBstSize + 1);
            else if (rightInfo != null) maxBstSize = Math.max(maxBstSize, rightInfo.maxBstSize + 1);
        }

        return new Info(min, max, size, maxBstSize);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxVal = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode root = generateRandomBST(maxLevel, maxVal);
            if (getMaxSubBstSizeByInOrder(root) != getMaxSubBstSize(root)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
