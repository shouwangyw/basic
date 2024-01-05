package com.yw.course.basic.class07;

import com.yw.entity.TreeNode;

/**
 * 测试链接：https://leetcode.cn/problems/validate-binary-search-tree
 * @author yangwei
 */
public class Code05_IsBinarySearchTree {

	/**
	 * 方法一：基于中序遍历
	 */
	private TreeNode pre;
	public boolean isValidBST(TreeNode root) {
		pre = null;
		return inOrder(root);
	}
	private boolean inOrder(TreeNode root) {
		if (root == null) return true;
		if (!inOrder(root.left)) return false;
		else if (pre != null && root.val <= pre.val) return false;
		pre = root;
		if (!inOrder(root.right)) return false;
		return true;
	}

	public static class Info {
		public int min;
		public int max;
		public boolean isBST;
		public Info(int min, int max, boolean isBST) {
			this.min = min;
			this.max = max;
			this.isBST = isBST;
		}
	}

	public static Info process(TreeNode root) {
		if (root == null) return null;
		Info leftInfo = process(root.left);
		Info rightInfo = process(root.right);

		int min = root.val;
		int max = root.val;
		if (leftInfo != null) {
			min = Math.min(leftInfo.min, min);
			max = Math.max(leftInfo.max, max);
		}
		if (rightInfo != null) {
			min = Math.min(rightInfo.min, min);
			max = Math.max(rightInfo.max, max);
		}
//		boolean isBST = true;
//		if (leftInfo != null && !leftInfo.isBST) isBST = false;
//		if (rightInfo != null && !rightInfo.isBST) isBST = false;
//		boolean leftIsBST = leftInfo == null ? true : (leftInfo.max < root.val);
//		boolean rightIsBST = rightInfo == null ? true : (rightInfo.min > root.val);
//		if (!leftIsBST || !rightIsBST) isBST = false;

		boolean isBST = false;
		boolean leftIsBST = leftInfo == null ? true : (leftInfo.isBST && leftInfo.max < root.val);
		boolean rightIsBST = rightInfo == null ? true : (rightInfo.isBST && rightInfo.min > root.val);
		if (leftIsBST && rightIsBST) {
			isBST = true;
		}
		return new Info(min, max, isBST);
	}
}
