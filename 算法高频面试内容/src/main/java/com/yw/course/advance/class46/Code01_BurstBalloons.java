package com.yw.course.advance.class46;

/**
 * 本题测试链接 : https://leetcode.cn/problems/burst-balloons/
 * @author yangwei
 */
public class Code01_BurstBalloons {

	// 方法一：暴力尝试
	public static int maxCoins0(int[] arr) {
		int n = arr.length;
		// 假设原数组 	arr = [3,2,1,3]
		// 那么help数组	help= [1,3,2,1,3,1]
		int[] help = new int[n + 2];
		for (int i = 0; i < n; i++) help[i + 1] = arr[i];
		help[0] = 1;
		help[n + 1] = 1;
		return process0(help, 1, n);
	}
	// l-1和r-1位置永远不越界，并且l-1和r-1一定没爆
	// 返回 arr[l,r]返回打爆所有气球的最大得分
	private static int process0(int[] arr, int l, int r) {
		// base case
		if (l == r) return arr[l - 1] * arr[l] * arr[r + 1];
		int max = 0;
		// 在[l, r]范围尝试每一种情况，最后打爆的气球在什么位置
		for (int i = l; i <= r; i++) {
			int left = i == l ? 0 : process0(arr, l, i - 1);
			int right = i == r ? 0 : process0(arr, i + 1, r);
			int last = arr[l - 1] * arr[i] * arr[r + 1];
			max = Math.max(max, left + right + last);
		}
		return max;
	}

	// 方法二：改动态规划
	public static int maxCoins(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		if (arr.length == 1) return arr[0];
		int n = arr.length;
		int[] help = new int[n + 2];
		for (int i = 0; i < n; i++) help[i + 1] = arr[i];
		help[0] = 1;
		help[n + 1] = 1;
		int[][] dp = new int[n + 2][n + 2];
		for (int i = 1; i <= n; i++) dp[i][i] = help[i - 1] * help[i] * help[i + 1];
		for (int l = n; l >= 1; l--) {
			for (int r = l + 1; r <= n; r++) {
				int max = 0;
				for (int i = l; i <= r; i++) {
					int left = i == l ? 0 : dp[l][i - 1];
					int right = i == r ? 0 : dp[i + 1][r];
					int last = help[l - 1] * help[i] * help[r + 1];
					max = Math.max(max, left + right + last);
				}
				dp[l][r] = max;
			}
		}
		return dp[1][n];
	}
}
