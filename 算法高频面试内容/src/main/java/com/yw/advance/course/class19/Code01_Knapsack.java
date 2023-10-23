package com.yw.advance.course.class19;

/**
 * @author yangwei
 */
public class Code01_Knapsack {

	// 方法一：尝试暴力递归
	// 所有的货，重量和价值，都在w和v数组里
	// 为了方便，其中没有负数，bag背包容量，不能超过这个载重
	// 返回：不超重的情况下，能够得到的最大价值
	public static int maxValueByRecurse(int[] w, int[] v, int bag) {
		if (w == null || v == null || w.length == 0 || w.length != v.length) return 0;
		// 尝试函数
		return process(w, v, 0, bag);
	}

	private static int process(int[] w, int[] v, int idx, int bag) {
		if (idx == w.length) return 0;
		return Math.max(
				// 不选第 idx 件物品
				process(w, v, idx + 1, bag),
				// 选第 idx 件物品
				bag >= w[idx] ? v[idx] + process(w, v, idx + 1, bag - w[idx]) : 0
		);
	}

	// 方法二：动态规划
	public static int maxValue(int[] w, int[] v, int bag) {
		if (w == null || v == null || w.length == 0 || w.length != v.length) return 0;
		int n = w.length;
		// i: 0~n，剩余背包容量k: 负数~bag
		int[][] dp = new int[n + 1][bag + 1];
		// dp[n][...] = 0
		for (int i = n - 1; i >= 0; i--) {
			for (int k = 0; k <= bag; k++) {
				dp[i][k] = Math.max(dp[i + 1][k],
						k >= w[i] ? v[i] + dp[i + 1][k - w[i]] : 0);
			}
		}
		return dp[0][bag];
	}

	public static void main(String[] args) {
		int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
		int[] values = { 5, 6, 3, 19, 12, 4, 2 };
		int bag = 15;
		System.out.println(maxValueByRecurse(weights, values, bag));
		System.out.println(maxValue(weights, values, bag));
	}

}
