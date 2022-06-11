package com.yw.basic.algorithm;

import static com.yw.basic.algorithm.RedBlackTree.TreeNode.NIL;

/**
 * @author yangwei
 */
public class RedBlackTree {

    static class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;
        /**
         * 0: 红, 1: 黑, 2: 双黑
         */
        private int color;

        protected static final TreeNode NIL = new TreeNode(0, 1);

        public TreeNode(int val) {
            this.val = val;
            this.left = NIL;
            this.right = NIL;
            this.color = 0;
        }

        private TreeNode(int val, int color) {
            this.val = val;
            this.color = color;
        }
    }

    private TreeNode newTreeNode(int val) {
        return new TreeNode(val);
    }

    public TreeNode insert(TreeNode root, int val) {
        root = insert0(root, val);
        root.color = 1;
        return root;
    }

    private TreeNode insert0(TreeNode root, int val) {
        if (root == NIL) return newTreeNode(val);
        if (val == root.val) return root;
        if (val < root.val) root.left = insert0(root.left, val);
        else root.right = insert0(root.right, val);
        return adjust(root);
    }

    private TreeNode adjust(TreeNode root) {
        int flag = 0;
        if (root.left.color == 0 && hasRedChildNode(root.left)) flag = 1;
        if (root.right.color == 0 && hasRedChildNode(root.right)) flag = 2;
        if (flag == 0) return root;
        if (root.left.color == 0 && root.right.color == 0) {
            root.color = 0;
            root.left.color = 1;
            root.right.color = 1;
            return root;
        }
        if (flag == 1) {
            if (root.left.right.color == 0) {
                root.left = leftRotate(root.left);
            }
            root = rightRotate(root);
        } else {
            if (root.right.left.color == 0) {
                root.right = rightRotate(root.right);
            }
            root = leftRotate(root);
        }
        root.color = 0;
        root.left.color = 1;
        root.right.color = 1;
        return root;
    }

    private boolean hasRedChildNode(TreeNode root) {
        return root.left.color == 0 || root.right.color == 0;
    }

    private TreeNode leftRotate(TreeNode root) {
        TreeNode tmp = root.right;
        root.right = tmp.left;
        tmp.left = root;
        return tmp;
    }

    private TreeNode rightRotate(TreeNode root) {
        TreeNode tmp = root.left;
        root.left = tmp.right;
        tmp.right = root;
        return tmp;
    }

    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        TreeNode root = NIL;
        for (int i = 1; i < 10; i++) {
            root = redBlackTree.insert(root, i);
            System.out.println("==>> RedBlackTree print");
            output(root);
            System.out.println("==>> RedBlackTree print done");
        }
    }

    private static void output(TreeNode root) {
        if (root == NIL) return;
        print(root);
        output(root.left);
        output(root.right);
    }

    private static void print(TreeNode root) {
        System.out.printf("( %d | %d, %d, %d )\n", root.color, root.val, root.left.val, root.right.val);
    }
}

