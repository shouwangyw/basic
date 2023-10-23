package com.yw.advance.course.class22;

/**
 * @author yangwei
 */
public class Code03_SplitNumber {

	// 方法一：尝试暴力递归
	// n为正数
	public static int ways(int n) {
		if (n <= 0) return 0;
		if (n == 1) return 1;
		return process(1, n);
	}
	// 上一个拆出来的数是pre，还剩rest需要去拆，返回拆解的方法数
	private static int process(int pre, int rest) {
		// 如果还需要拆的数为0，说明找到一个解
		if (rest == 0) return 1;
		// 如果上一个数比剩余需要拆的数大，违反题意，即没有找到解，返回0
		if (pre > rest) return 0;
		int ans = 0;
		for (int i = pre; i <= rest; i++) {
			ans += process(i, rest - i);
		}
		return ans;
	}

	// 方法二：改动态规划
	public static int waysByDp0(int n) {
		int[][] dp = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++) {
			dp[i][0] = 1;
			dp[i][i] = 1;
		}
		for (int pre = n - 1; pre >= 1; pre--) {
			for (int rest = pre + 1; rest <= n; rest++) {
				for (int i = pre; i <= rest; i++) {
					dp[pre][rest] += dp[i][rest - i];
				}
			}
		}
		return dp[1][n];
	}

	// 方法三：斜率优化
	public static int waysByDp(int n) {
		int[][] dp = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++) {
			dp[i][0] = 1;
			dp[i][i] = 1;
		}
		for (int pre = n - 1; pre >= 1; pre--) {
			for (int rest = pre + 1; rest <= n; rest++) {
				dp[pre][rest] = dp[pre + 1][rest] + dp[pre][rest - pre];
			}
		}
		return dp[1][n];
	}

	public static void main(String[] args) {
		int test = 39;
		System.out.println(ways(test));
		System.out.println(waysByDp0(test));
		System.out.println(waysByDp(test));
	}
}
