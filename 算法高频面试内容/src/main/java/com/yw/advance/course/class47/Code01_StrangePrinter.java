package com.yw.advance.course.class47;

/**
 * 本题测试链接 : https://leetcode.cn/problems/strange-printer/
 * @author yangwei
 */
public class Code01_StrangePrinter {

	// 方法一：暴力尝试
	public static int strangePrinter0(String s) {
		if (s == null || s.length() == 0) return 0;
		return process(s.toCharArray(), 0, s.length() - 1);
	}
	// 返回 在[l,r]范围完成打印 的最少打印次数
	private static int process(char[] cs, int l, int r) {
		// base case: 若只剩一个字符了，最少打印1次
		if (l == r) return 1;
		int min = r - l + 1;
		// 枚举每一个划分点
		for (int i = l + 1; i <= r; i++) {
			min = Math.min(min, process(cs, l, i - 1) + process(cs, i, r)
					// !!! 若`左边开始位置字符=右边开始位置字符`，则可以合并一次
					- (cs[l] == cs[i] ? 1 : 0));
		}
		return min;
	}

	// 方法二：暴力尝试改记忆化搜索
	public static int strangePrinter(String s) {
		if (s == null || s.length() == 0) return 0;
		int n = s.length();
		return process(s.toCharArray(), 0, n - 1, new int[n][n]);
	}
	private static int process(char[] cs, int l, int r, int[][] record) {
		if (record[l][r] != 0) return record[l][r];
		// base case: 若只剩一个字符了，最少打印1次
		int min = r - l + 1;
		if (l == r) min = 1;
		else {
			// 枚举每一个划分点
			for (int i = l + 1; i <= r; i++) {
				min = Math.min(min, process(cs, l, i - 1, record) + process(cs, i, r, record)
						// !!! 若`左边开始位置字符=右边开始位置字符`，则可以合并一次
						- (cs[l] == cs[i] ? 1 : 0));
			}
		}
		record[l][r] = min;
		return min;
	}

	// 方法三：暴力尝试改动态规划
	public static int strangePrinterDp(String s) {
		if (s == null || s.length() == 0) return 0;
		char[] cs = s.toCharArray();
		int n = cs.length;
		int[][] dp = new int[n][n];
		// 初始化dp表，左下半区无效不考虑
		// 斜对角线(即只有一个字符)至少打印1次，∴ dp[i][i] = 1
		// 斜对角线上一条线(有两个个字符)，相同则至少打印1次，否则打印2次∴ dp[i][i + 1] = cs[i] == cs[i + 1] ? 1 : 2
		dp[n - 1][n - 1] = 1;
		for (int i = 0; i < n - 1; i++) {
			dp[i][i] = 1;
			dp[i][i + 1] = cs[i] == cs[i + 1] ? 1 : 2;
		}
		// 普遍位置
		for (int l = n - 3; l >= 0; l--) {
			for (int r = l + 2; r < n; r++) {
				dp[l][r] = r - l + 1;
				for (int i = l + 1; i <= r; i++) {
					dp[l][r] = Math.min(dp[l][r], dp[l][i - 1] + dp[i][r] - (cs[l] == cs[i] ? 1 : 0));
				}
			}
		}
		return dp[0][n - 1];
	}
}
