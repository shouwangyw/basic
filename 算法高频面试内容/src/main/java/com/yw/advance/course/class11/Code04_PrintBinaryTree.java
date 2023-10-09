package com.yw.advance.course.class11;

import com.yw.entity.TreeNode;

/**
 * @author yangwei
 */
public class Code04_PrintBinaryTree {

    public static void printTree(TreeNode root) {
        System.out.println("Binary Tree:");
        printInOrder(root, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(TreeNode root, int height, String to, int len) {
        if (root == null) return;
        printInOrder(root.right, height + 1, "v", len);
        String val = to + root.val + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(root.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

public static void main(String[] args) {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(-222222222);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(Integer.MIN_VALUE);
    root.right.left = new TreeNode(55555555);
    root.right.right = new TreeNode(66);
    root.left.left.right = new TreeNode(777);
    printTree(root);

    root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.right.left = new TreeNode(5);
    root.right.right = new TreeNode(6);
    root.left.left.right = new TreeNode(7);
    printTree(root);

    root = new TreeNode(1);
    root.left = new TreeNode(1);
    root.right = new TreeNode(1);
    root.left.left = new TreeNode(1);
    root.right.left = new TreeNode(1);
    root.right.right = new TreeNode(1);
    root.left.left.right = new TreeNode(1);
    printTree(root);

}

}
