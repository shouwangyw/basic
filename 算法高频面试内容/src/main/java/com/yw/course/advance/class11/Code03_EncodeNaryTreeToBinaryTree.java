package com.yw.course.advance.class11;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试链接：https://leetcode.cn/problems/encode-n-ary-tree-to-binary-tree
 *
 * @author yangwei
 */
public class Code03_EncodeNaryTreeToBinaryTree {

    // 提交时不要提交这个类
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    // 提交时不要提交这个类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // 只提交这个类即可
    class Codec {
        // Encodes an n-ary tree to a binary tree.
        public TreeNode encode(Node root) {
            if (root == null) return null;
            TreeNode newRoot = new TreeNode(root.val);
            newRoot.left = en(root.children);
            return newRoot;
        }

        private TreeNode en(List<Node> children) {
            TreeNode root = null, cur = null;
            for (Node child : children) {
                TreeNode node = new TreeNode(child.val);
                if (root == null) root = node;
                else cur.right = node;
                cur = node;
                cur.left = en(child.children);
            }
            return root;
        }

        // Decodes your binary tree to an n-ary tree.
        public Node decode(TreeNode root) {
            if (root == null) return null;

            return new Node(root.val, de(root.left));
        }

        public List<Node> de(TreeNode root) {
            List<Node> children = new ArrayList<>();
            while (root != null) {
                Node cur = new Node(root.val, de(root.left));
                children.add(cur);
                root = root.right;
            }
            return children;
        }
    }

}
