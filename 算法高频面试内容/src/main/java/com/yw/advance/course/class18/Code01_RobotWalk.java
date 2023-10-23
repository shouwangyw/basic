package com.yw.advance.course.class18;

import java.util.Arrays;

/**
 * @author yangwei
 */
public class Code01_RobotWalk {

	// 方法一：暴力递归
	// n 个位置，机器人开始位置 m，走 k 步到位置 p
	public static int waysByRecurse(int n, int m, int k, int p) {
		// 边界值处理
		if (n < 2 || m < 1 || m > n || p < 1 || p > n || k < 1) return -1;
		return process(m, k, p, n);
	}
	// 机器人当前来到的位置是 i，还剩 k 步需要走，最终目标是 p，总共有 n 个位置
	// 返回：机器人从 i 触发，走 k 步后，最终停在 p 的方法总数
	private static int process(int i, int k, int p, int n) {
		// 如果已经不需要走了：i == p 走到了目标位置，即找到 1 个方法，否则没找到
		if (k == 0) return i == p ? 1 : 0;
		// 如果 i == 1，那么下一步只能来到 i + 1 位置，剩余 k - 1 步
		if (i == 1) return process(2, k - 1, p, n);
		// 如果 i == n，那么下一步只能来到 i - 1 位置，剩余 k - 1 步
		if (i == n) return process(n - 1, k - 1, p, n);
		// 否则，下一步可以向左走、也可以向右走
		return process(i - 1, k - 1, p, n) + process(i + 1, k - 1, p, n);
	}

	// 方法二：缓存优化（傻缓存，自顶向下的动态规划，也叫记忆化搜索）
	// 通过上面 process 方法，可以知道影响结果的只和当前位置 i 和剩余步数 k 有关
	public static int waysUseCache(int n, int m, int k, int p) {
		if (n < 2 || m < 1 || m > n || p < 1 || p > n || k < 1) return -1;
		// 二维数组dp就是缓存表，-1表示之前没计算过
		int[][] dp = new int[n + 1][k + 1];
		for (int i = 0; i <= n; i++) Arrays.fill(dp[i], -1);
		return process(m, k, p, n, dp);
	}
	private static int process(int i, int k, int p, int n, int[][] dp) {
		// 若计算过，则直接返回结果
		if (dp[i][k] != -1) return dp[i][k];
		// 如果没算过
		int ans = 0;
		if (k == 0) ans = i == p ? 1 : 0;
		else if (i == 1) ans = process(2, k - 1, p, n, dp);
		else if (i == n) ans = process(n - 1, k - 1, p, n, dp);
		else ans = process(i - 1, k - 1, p, n, dp) + process(i + 1, k - 1, p, n, dp);
		// 返回答案前先缓存计算结果
		dp[i][k] = ans;
		return ans;
	}

	// 方法三：动态规划
	public static int ways(int n, int m, int k, int p) {
		if (n < 2 || m < 1 || m > n || p < 1 || p > n || k < 1) return -1;
		int[][] dp = new int[n + 1][k + 1];
//		int[][] dp = new int[n + 2][k + 1];		// 扩一行(默认值0)，不用对第1、n行特殊处理
		dp[p][0] = 1;
		for (int j = 1; j <= k; j++) {	// 列
			dp[1][j] = dp[2][j - 1];
			for (int i = 2; i < n; i++) {	// 行
				dp[i][j] = dp[i - 1][j - 1] + dp[i + 1][j - 1];
			}
			dp[n][j] = dp[n - 1][j - 1];

//			for (int i = 1; i <= n; i++) {
//				dp[i][j] = dp[i - 1][j - 1] + dp[i + 1][j - 1];
//			}
		}
		return dp[m][k];
	}

	public static void main(String[] args) {
		System.out.println(waysByRecurse(5, 2, 6, 4));
		System.out.println(waysUseCache(5, 2, 6, 4));
		System.out.println(ways(5, 2, 6, 4));
	}

}
