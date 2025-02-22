package com.yw.course.coding.class37;

import com.yw.entity.TreeNode;

/**
 * 注意，我们课上讲了一个别的题，并不是leetcode 114
 * 我们课上讲的是，把一棵搜索二叉树变成有序链表，怎么做
 * 而leetcode 114是，把一棵树先序遍历的结果串成链表
 * 所以我更新了代码，这个代码是leetcode 114的实现
 * 利用morris遍历
 *
 * @author yangwei
 */
public class Problem_0114_FlattenBinaryTreeToLinkedList {

	private TreeNode last;
	public void flatten(TreeNode root) {
		reversePreOrder(root);
	}
	private void reversePreOrder(TreeNode root) {
		if (root == null) return;
		reversePreOrder(root.right);
		reversePreOrder(root.left);
		if (last != null) {
			root.right = last;
			root.left = null;
		}
		last = root;
	}

	// 普通解-二叉树的递归套路
	public void flatten1(TreeNode root) {
		process(root);
	}
	private static Info process(TreeNode head) {
		if (head == null) return null;
		Info leftInfo = process(head.left), rightInfo = process(head.right);
		head.left = null;
		head.right = leftInfo == null ? null : leftInfo.head;
		TreeNode tail = leftInfo == null ? head : leftInfo.tail;
		tail.right = rightInfo == null ? null : rightInfo.head;
		tail = rightInfo == null ? tail : rightInfo.tail;
		return new Info(head, tail);
	}
	private static class Info {
		private TreeNode head;
		private TreeNode tail;
		public Info(TreeNode head, TreeNode tail) {
			this.head = head;
			this.tail = tail;
		}
	}

	// Morris遍历的解
	public void flatten2(TreeNode root) {
		// morris遍历
		if (root == null) return;
		TreeNode cur = root, mostRight, pre = null, next;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) mostRight = mostRight.right;
				if (mostRight.right == null) {
					mostRight.right = cur;
					if (pre != null) pre.left = cur;
					pre = cur;
					cur = cur.left;
					continue;
				} else mostRight.right = null;
			} else {
				if (pre != null) pre.left = cur;
				pre = cur;
			}
			cur = cur.right;
		}
		cur = root;
		while (cur != null) {
			next = cur.left;
			cur.left = null;
			cur.right = next;
			cur = next;
		}
	}

}
