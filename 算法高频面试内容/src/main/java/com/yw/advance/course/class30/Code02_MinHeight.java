package com.yw.advance.course.class30;

import com.yw.entity.TreeNode;

import static com.yw.util.CommonUtils.generateRandomBST;

/**
 * @author yangwei
 */
public class Code02_MinHeight {

	// 方法一：经典递归
	public static int minHeight(TreeNode root) {
		if (root == null) return 0;
		return process(root);
	}
	private static int process(TreeNode root) {
		if (root.left == null && root.right == null) return 1;
		int leftHeight = Integer.MAX_VALUE;
		if (root.left != null) leftHeight = process(root.left);
		int rightHeight = Integer.MAX_VALUE;
		if (root.right != null) rightHeight = process(root.right);
		return Math.min(leftHeight, rightHeight) + 1;
	}

	// 方法二：根据morris遍历改写
	public static int minHeightByMorris(TreeNode root) {
		if (root == null) return 0;
		TreeNode cur = root, mostRight;
		int level = 0, minHeight = Integer.MAX_VALUE;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				int rightSize = 1;
				while (mostRight.right != null && mostRight.right != cur) {
					rightSize++;
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					level++; // 第一次到
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					// 第二次到
					if (mostRight.left == null) {
						minHeight = Math.min(minHeight, level);
					}
					level -= rightSize;
					mostRight.right = null;
				}
			} else {
				level++; // 第一次到
			}
			cur = cur.right;
		}
		cur = root;
		int finalRight = 1;
		while (cur.right != null) {
			finalRight++;
			cur = cur.right;
		}
		if (cur.left == null) minHeight = Math.min(minHeight, finalRight);
		return minHeight;
	}

	public static void main(String[] args) {
		int treeLevel = 7;
		int nodeMaxValue = 5;
		int testTimes = 100000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			TreeNode head = generateRandomBST(treeLevel, nodeMaxValue);
			int ans1 = minHeight(head);
			int ans2 = minHeightByMorris(head);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("test finish!");
	}
}
