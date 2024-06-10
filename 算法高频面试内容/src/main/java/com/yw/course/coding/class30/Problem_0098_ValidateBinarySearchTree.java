package com.yw.course.coding.class30;

import com.yw.entity.TreeNode;

/**
 * @author yangwei
 */
public class Problem_0098_ValidateBinarySearchTree {
	// 方法一：中序遍历
//	private Integer pre;
//	public boolean isValidBST(TreeNode root) {
//		pre = null;
//		return inOrder(root);
//	}
//	private boolean inOrder(TreeNode root) {
//		if (root == null) return true;
//		if (!inOrder(root.left)) return false;
//		if (pre != null && pre >= root.val) return false;
//		pre = root.val;
//		if (!inOrder(root.right)) return false;
//		return true;
//	}

	// 方法二：二叉树的递归套路
//	public boolean isValidBST(TreeNode root) {
//		return process(root).isBst;
//	}
//	private static class Info {
//		private boolean isBst;
//		private int min;
//		private int max;
//		public Info(boolean isBst, int min, int max) {
//			this.isBst = isBst;
//			this.min = min;
//			this.max = max;
//		}
//	}
//	private static Info process(TreeNode root) {
//		if (root == null) return null;
//		Info leftInfo = process(root.left), rightInfo = process(root.right);
//		int min = root.val, max = root.val;
//		if (leftInfo != null) {
//			min = Math.min(min, leftInfo.min);
//			max = Math.max(max, leftInfo.max);
//		}
//		if (rightInfo != null) {
//			min = Math.min(min, rightInfo.min);
//			max = Math.max(max, rightInfo.max);
//		}
//		boolean leftIsBst = leftInfo == null || (leftInfo.isBst && leftInfo.max < root.val);
//		boolean rightIsBst = rightInfo == null || (rightInfo.isBst && root.val < rightInfo.min);
//		return new Info(leftIsBst && rightIsBst, min, max);
//	}

	// 方法三：morris遍历
	public boolean isValidBST(TreeNode root) {
		if (root == null) return true;
		TreeNode cur = root, mostRight;
		Integer pre = null;
		boolean ans = true;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) mostRight = mostRight.right;
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else mostRight.right = null;
			}
			if (pre != null && pre >= cur.val) ans = false;
			pre = cur.val;
			cur = cur.right;
		}
		return ans;
	}

}
