package com.yw.course.coding.class37;

import com.yw.entity.TreeNode;

/**
 * {@link com.yw.course.advance.class13.Code04_MaxHappy}
 *
 * @author yangwei
 */
public class Problem_0337_HouseRobberIII {

	public int rob(TreeNode root) {
		Info info = process(root);
		return Math.max(info.no, info.yes);
	}
	private static Info process(TreeNode root) {
		if (root == null) return new Info(0, 0);
		Info leftInfo = process(root.left), rightInfo = process(root.right);
		// 1. 如果不打劫当前节点，那么左右两块子树可以选择打劫或者不打劫
		int no = Math.max(leftInfo.no, leftInfo.yes) + Math.max(rightInfo.no, rightInfo.yes);
		// 2. 如果打劫当前节点，那么左右两块子树只能不打劫
		int yes = root.val + leftInfo.no + rightInfo.no;
		return new Info(no, yes);
	}
	private static class Info {
		private int no;     // 不打劫当前节点
		private int yes;    // 打劫当前节点
		public Info(int no, int yes) {
			this.no = no;
			this.yes = yes;
		}
	}

}
