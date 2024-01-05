package com.yw.course.advance.class10;

import com.yw.entity.TreeNode;

/**
 * @author yangwei
 */
public class Code02_RecursiveTraversalBT {

	public static void f(TreeNode root) {
		if (root == null) return;
		// 1
		f(root.left);
		// 2
		f(root.right);
		// 3
	}

	// 先序打印所有节点
	public static void pre(TreeNode root) {
		if (root == null) return;
		System.out.println(root.val);
		pre(root.left);
		pre(root.right);
	}
	// 中序打印所有节点
	public static void in(TreeNode root) {
		if (root == null) return;
		in(root.left);
		System.out.println(root.val);
		in(root.right);
	}
	// 后序打印所有节点
	public static void pos(TreeNode root) {
		if (root == null) return;
		pos(root.left);
		pos(root.right);
		System.out.println(root.val);
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);

		pre(root);
		System.out.println("========");
		in(root);
		System.out.println("========");
		pos(root);
		System.out.println("========");
	}
}
