package com.yw.basic.algorithm;

/**
 * @author yangwei
 */
public class AVLTree {
    class Node {
        int val;
        Node left;
        Node right;
        int height;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
            this.height = 0;
        }

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }

    private Node root;

    public AVLTree() {
        this.root = null;
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node != null) return node.height;
        return 0;
    }

    private int max(int a, int b) {
        return Math.max(a, b);
    }

    /**
     * LL: 右旋
     */
    private Node rightRotation(Node root) {
        Node newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;


        return newRoot;
    }
}
