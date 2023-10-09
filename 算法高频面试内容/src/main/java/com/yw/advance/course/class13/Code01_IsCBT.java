package com.yw.advance.course.class13;

import com.yw.entity.TreeNode;

import static com.yw.util.CommonUtils.generateRandomBST;

/**
 * @author yangwei
 */
public class Code01_IsCBT extends com.yw.advance.course.class12.Code01_IsCBT {

	public static boolean isCbt(TreeNode root) {
		return process(root).isCbt;
	}
	// 1. 封装信息
	public static class Info {
		boolean isFull;		// 是否满二叉树
		boolean isCbt;		// 是否完全二叉树
		int height;			// 二叉树高度
		public Info(boolean isFull, boolean isCbt, int height) {
			this.isFull = isFull;
			this.isCbt = isCbt;
			this.height = height;
		}
	}
	// 2. 处理递归
	private static Info process(TreeNode root) {
		if (root == null) return new Info(true, true, 0);
		Info leftInfo = process(root.left);
		Info rightInfo = process(root.right);

		// 左满 && 右满 && 左右高度一样 -->> 当前树满
		boolean isFull = leftInfo.isFull && rightInfo.isFull
				&& leftInfo.height == rightInfo.height;
		// isFull --> isCbt 满二叉树 一定是 完全二叉树，反之不成立
		boolean isCbt = isFull
				// 左满、右满，则左高=右高+1
				|| (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1)
				// 左满、右完全，则左高=右高
				|| (leftInfo.isFull && rightInfo.isCbt && leftInfo.height == rightInfo.height)
				// 左完全、右满，则左高=右高+1
				|| (leftInfo.isCbt && rightInfo.isFull && leftInfo.height == rightInfo.height + 1);
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;

		return new Info(isFull, isCbt, height);
	}

	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			TreeNode root = generateRandomBST(maxLevel, maxValue);
			if (isCbtByQueue(root) != isCbt(root)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}
}
