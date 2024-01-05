package com.yw.course.basic.class07;

import com.yw.entity.TreeNode;

/**
 * 测试链接：https://leetcode.cn/problems/path-sum
 *
 * @author yangwei
 */
public class Code03_PathSum {

	/**
	 * 方法一
	 */
	public boolean hasPathSum(TreeNode root, int targetSum) {
		if (root == null) return false;
		if (root.left == null && root.right == null && root.val == targetSum) return true;
		return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
	}

	/**
	 * 方法二
	 */
	public static boolean hasPathSum2(TreeNode root, int sum) {
		if (root == null) {
			return false;
		}
		isSum = false;
		process(root, 0, sum);
		return isSum;
	}

	public static void process(TreeNode x, int preSum, int sum) {
		if (x.left == null && x.right == null) {
			if (x.val + preSum == sum) {
				isSum = true;
			}
			return;
		}
		// x是非叶节点
		preSum += x.val;
		if (x.left != null) {
			process(x.left, preSum, sum);
		}
		if (x.right != null) {
			process(x.right, preSum, sum);
		}
	}

	public static boolean isSum = false;

//	public static boolean hasPathSum(TreeNode root, int sum) {
//		if (root == null) {
//			return false;
//		}
//		return process(root, sum);
//	}
//
//	public static boolean process(TreeNode root, int rest) {
//		if (root.left == null && root.right == null) {
//			return root.val == rest;
//		}
//		boolean ans = root.left != null ? process(root.left, rest - root.val) : false;
//		ans |= root.right != null ? process(root.right, rest - root.val) : false;
//		return ans;
//	}

}
