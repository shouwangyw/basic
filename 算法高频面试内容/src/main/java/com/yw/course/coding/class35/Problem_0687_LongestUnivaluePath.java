package com.yw.course.coding.class35;

import com.yw.entity.TreeNode;

/**
 * @author yangwei
 */
public class Problem_0687_LongestUnivaluePath {

	public int longestUnivaluePath(TreeNode root) {
		if (root == null) return 0;
		return process(root).max - 1;
	}
	private static class Info {
		private int len; // 路径必须从x出发且只能往下走情况下的最大合法路径的节点数量
		private int max; // 路径不要求从x出发情况下，整棵树的最大合法路径的节点数量
		public Info (int len, int max) {
			this.len = len;
			this.max = max;
		}
	}
	private static Info process(TreeNode root) {
		if (root == null) return new Info(0, 0);
		TreeNode left = root.left, right = root.right;
		Info leftInfo = process(root.left), rightInfo = process(root.right);
		int len = 1;
		if (left != null && left.val == root.val) len += leftInfo.len;
		if (right != null && right.val == root.val) len += rightInfo.len;
		int max = Math.max(Math.max(leftInfo.max, rightInfo.max), len);
		if (left != null && right != null && left.val == root.val && right.val == root.val) {
			max = Math.max(max, leftInfo.len + rightInfo.len + 1);
		}
		return new Info(len, max);
	}

}
