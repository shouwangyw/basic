package com.yw.advance.course.class13;

import com.yw.entity.TreeNode;

import java.util.ArrayList;
import java.util.List;

import static com.yw.util.CommonUtils.generateRandomBST;

/**
 * @author yangwei
 */
public class Code02_MaxSubBSTHead {

	// 方法一：中序遍历、暴力
	public static TreeNode getMaxSubBst(TreeNode root) {
		if (root == null) return null;
		if (getBstSize(root) != 0) return root;
		TreeNode left = getMaxSubBst(root.left);
		TreeNode right = getMaxSubBst(root.right);
		return getBstSize(left) >= getBstSize(right) ? left : right;
	}
	private static int getBstSize(TreeNode root) {
		if (root == null) return 0;
		List<Integer> ins = new ArrayList<>();
		inOrder(root, ins);
		for (int i = 1; i < ins.size(); i++) {
			if (ins.get(i) <= ins.get(i - 1)) return 0;
		}
		return ins.size();
	}
	private static void inOrder(TreeNode root, List<Integer> ins) {
		if (root == null) return;
		inOrder(root.left, ins);
		ins.add(root.val);
		inOrder(root.right, ins);
	}
	// 方法二：递归套路
	public static TreeNode maxSubBst(TreeNode root) {
		if (root == null) return null;
		return process(root).maxBst;
	}
	// 1. 封装信息
	private static class Info {
		int min;
		int max;
		int maxBstSize;
		TreeNode maxBst;
		public Info(int min, int max, int maxBstSize, TreeNode maxBst) {
			this.min = min;
			this.max = max;
			this.maxBstSize = maxBstSize;
			this.maxBst = maxBst;
		}
	}
	// 2. 处理递归
	private static Info process(TreeNode root) {
		if (root == null) return null;
		Info leftInfo = process(root.left);
		Info rightInfo = process(root.right);

		int min = root.val;
		int max = root.val;
		int maxBstSize = -1;
		TreeNode maxBst = root;
		boolean isBst = true;

		if (leftInfo != null) {
			isBst = (leftInfo.maxBst == root.left && leftInfo.max < root.val);
			min = Math.min(leftInfo.min, min);
			max = Math.max(leftInfo.max, max);
			if (maxBstSize < leftInfo.maxBstSize) {
				maxBstSize = leftInfo.maxBstSize;
				maxBst = leftInfo.maxBst;
			}
		}
		if (rightInfo != null) {
			isBst &= (rightInfo.maxBst == root.right && rightInfo.min > root.val);
			min = Math.min(rightInfo.min, min);
			max = Math.max(rightInfo.max, max);
			if (maxBstSize < rightInfo.maxBstSize) {
				maxBstSize = rightInfo.maxBstSize;
				maxBst = rightInfo.maxBst;
			}
		}
		if (isBst) {
			maxBstSize = (leftInfo == null ? 0 : leftInfo.maxBstSize) +
					(rightInfo == null ? 0 : rightInfo.maxBstSize) + 1;
			maxBst = root;
		}

		return new Info(min, max, maxBstSize, maxBst);
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxVal = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			TreeNode root = generateRandomBST(maxLevel, maxVal);
			if (getMaxSubBst(root) != maxSubBst(root)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
