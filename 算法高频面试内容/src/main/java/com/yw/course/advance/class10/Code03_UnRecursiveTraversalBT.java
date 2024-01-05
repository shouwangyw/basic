package com.yw.course.advance.class10;

import com.yw.entity.TreeNode;

import java.util.Stack;

/**
 * @author yangwei
 */
public class Code03_UnRecursiveTraversalBT {

    // 先序-非递归版
    public static void preOrder(TreeNode root) {
        System.out.print("pre-order: ");
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
        System.out.println();
    }

    // 中序-非递归版
    public static void inOrder(TreeNode root) {
        System.out.print("in-order: ");
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                System.out.print(cur.val + " ");
                cur = cur.right;
            }
        }
        System.out.println();
    }

    // 后序-非递归版(1)，基于两个栈实现
    public static void posOrder(TreeNode root) {
        System.out.print("pos-order: ");
        if (root == null) return;
        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();
        s1.push(root);
        // 先做出 `根 右 左`，压入到 s2 中
        while (!s1.isEmpty()) {
            TreeNode node = s1.pop();
            s2.push(node);
            if (node.left != null) s1.push(node.left);
            if (node.right != null) s1.push(node.right);
        }
        // 依次弹出 s2 中的节点，得到 `左 右 根` 后续
        while (!s2.isEmpty()) System.out.print(s2.pop().val + " ");
        System.out.println();
    }

    // 后序-非递归版(2)，基于一个栈实现
    public static void posOrder2(TreeNode root) {
        System.out.print("pos-order: ");
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode cur = root, node;
        while (!stack.isEmpty()) {
            node = stack.peek();
            if (node.left != null && cur != node.left && cur != node.right) stack.push(node.left);
            else if (node.right != null && node.right != cur) stack.push(node.right);
            else {
                System.out.print(stack.pop().val + " ");
                cur = node;
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        preOrder(root);
        System.out.println("========");
        inOrder(root);
        System.out.println("========");
        posOrder(root);
        System.out.println("========");
        posOrder2(root);
        System.out.println("========");
    }
}
