package com.yw.advance.course.class12;

import com.yw.entity.TreeNode;

import static com.yw.util.CommonUtils.generateRandomBST;

/**
 * @author yangwei
 */
public class Code03_IsBalanced {

	// 方法一：普通递归方式-基于平衡树定义
	public static boolean isBalancedByDef(TreeNode root) {
		if (root == null) return true;
		// 任何左右子树都是平衡树，且左右子树高度差小于等于1
		return isBalancedByDef(root.left) && isBalancedByDef(root.right)
				&& Math.abs(getHeight(root.left) - getHeight(root.right)) <= 1;
	}
	private static int getHeight(TreeNode root) {
		return root == null ? 0 : Math.max(getHeight(root.left), getHeight(root.right)) + 1;
	}

	// 方法二：基于递归套路
	public static boolean isBalanced(TreeNode root) {
		return process(root).is;
	}
	// 1. 封装信息
	private static class Info {
		boolean is;
		int height;
		public Info(boolean is, int height) {
			this.is = is;
			this.height = height;
		}
	}
	// 2. 实现递归
	private static Info process(TreeNode root) {
		if (root == null) return new Info(true, 0);
		Info leftInfo = process(root.left);
		Info rightInfo = process(root.right);

		boolean is = leftInfo.is && rightInfo.is && Math.abs((leftInfo.height - rightInfo.height)) <= 1;
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;

		return new Info(is, height);
	}

	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			TreeNode root = generateRandomBST(maxLevel, maxValue);
			if (isBalancedByDef(root) != isBalanced(root)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
