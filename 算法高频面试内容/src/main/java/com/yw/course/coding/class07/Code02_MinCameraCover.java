package com.yw.course.coding.class07;

import com.yw.entity.TreeNode;

/**
 * 测试链接 : https://leetcode.cn/problems/binary-tree-cameras/
 * @author yangwei
 */
public class Code02_MinCameraCover {

	// 方法一：二叉树的递归套路
	public int minCameraCover0(TreeNode root) {
		Info0 info = process0(root);
		return Math.min(info.uncovered + 1, Math.min(info.coveredNoCamera, info.coveredHasCamera));
	}
	// 潜台词：X是头节点，X下方的点都被覆盖的情况下(因为如果X下方的点没被覆盖，一旦X无相机就无法挽救)
	private static class Info0 {
		private int uncovered;          // X没被覆盖，X为头的树至少需要几个相机
		private int coveredNoCamera;    // X被覆盖，但X上没相机，X为头的树至少需要几个相机
		private int coveredHasCamera;   // X被覆盖，但X上没相机，X为头的树至少需要几个相机
		public Info0(int uncovered, int coveredNoCamera, int coveredHasCamera) {
			this.uncovered = uncovered;
			this.coveredNoCamera = coveredNoCamera;
			this.coveredHasCamera = coveredHasCamera;
		}
	}
	private static Info0 process0(TreeNode node) {
		// base case
		if (node == null) return new Info0(1000, 0, 1000);
		Info0 leftInfo = process0(node.left), rightInfo = process0(node.right);
		// X没被覆盖，则要求左右子树没相机
		int uncovered = leftInfo.coveredNoCamera + rightInfo.coveredNoCamera;
		// X被覆盖但没相机，则要求左右子树至少有相机
		int coveredNoCamera = Math.min(leftInfo.coveredHasCamera + rightInfo.coveredHasCamera,
				Math.min(leftInfo.coveredHasCamera + rightInfo.coveredNoCamera, leftInfo.coveredNoCamera + rightInfo.coveredHasCamera));
		// X被覆盖有相机，则左右子树可以有相机、也可以无相机
		int coveredHasCamera = Math.min(leftInfo.uncovered, Math.min(leftInfo.coveredNoCamera, leftInfo.coveredHasCamera))
				+ Math.min(rightInfo.uncovered, Math.min(rightInfo.coveredNoCamera, rightInfo.coveredHasCamera))
				+ 1;
		return new Info0(uncovered, coveredNoCamera, coveredHasCamera);
	}

	// 方法二：贪心+二叉树的递归套路
	public int minCameraCover(TreeNode root) {
		Info info = process(root);
		return info.cameras + (info.status == Status.UNCOVERD ? 1 : 0);
	}
	private enum Status {
		UNCOVERD, COVERD_NO_CAMERA, COVERD_HAS_CAMERA;
	}
	// X为头节点，下方节点都被覆盖，得到的最优解中：X是什么状态，以及在这种状态下至少需要几个相机
	private static class Info {
		private int cameras;        // 相机数量
		private Status status;      // X状态
		public Info(int cameras, Status status) {
			this.cameras = cameras;
			this.status = status;
		}
	}
	private static Info process(TreeNode node) {
		// base case
		if (node == null) return new Info(0, Status.COVERD_NO_CAMERA);
		Info leftInfo = process(node.left), rightInfo = process(node.right);
		int cameras = leftInfo.cameras + rightInfo.cameras;
		// 左右两边只要有一个没覆盖，则当前就需要放一个相机
		if (leftInfo.status == Status.UNCOVERD || rightInfo.status == Status.UNCOVERD) {
			return new Info(cameras + 1, Status.COVERD_HAS_CAMERA);
		}
		// 左右两边都有被覆盖，并且至少有一个相机，则当前不放相机也能被覆盖
		if (leftInfo.status == Status.COVERD_HAS_CAMERA || rightInfo.status == Status.COVERD_HAS_CAMERA) {
			return new Info(cameras, Status.COVERD_NO_CAMERA);
		}
		// 左右两边都有被覆盖，并且没有相机，则当前不能被覆盖
		return new Info(cameras, Status.UNCOVERD);
	}

//	public int minCameraCover(TreeNode root) {
//		int[][] dp = new int[2][2];
//		getDp(root, dp);
//		return Math.min(dp[0][1], dp[0][0]);
//	}
//	private void getDp(TreeNode root, int[][] dp) {
//		if (root == null) {
//			dp[0][0] = 0;
//			dp[0][1] = 10000;
//			dp[1][0] = 0;
//			dp[1][1] = 10000;
//			return;
//		}
//		if (root.left == null && root.right == null) {
//			dp[0][0] = 10000;
//			dp[0][1] = 1;
//			dp[1][0] = 0;
//			dp[1][1] = 1;
//			return;
//		}
//		int[][] l = new int[2][2], r = new int[2][2];
//		getDp(root.left, l);
//		getDp(root.right, r);
//		dp[0][0] = Math.min(Math.min(l[0][1] + r[0][0], l[0][0] + r[0][1]), l[0][1] + r[0][1]);
//		dp[0][1] = Math.min(Math.min(l[1][0] + r[1][0], l[1][1] + r[1][1]), Math.min(l[1][0] + r[1][1], l[1][1] + r[1][0])) + 1;
//		dp[1][0] = Math.min(dp[0][0], l[0][0] + r[0][0]);
//		dp[1][1] = dp[0][1];
//	}
}
