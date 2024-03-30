package com.yw.course.coding.class13;

/**
 * 测试链接 : https://leetcode.cn/problems/scramble-string/
 * @author yangwei
 */
public class Code03_ScrambleString {

	// 方法一：尝试递归
	public static boolean isScramble(String s1, String s2) {
		// 边界值判断
		if (s1 == null && s2 == null) return true;
		if (s1 == null || s2 == null) return false;
		if (s1.equals(s2)) return true;
		char[] cs1 = s1.toCharArray(), cs2 = s2.toCharArray();
		// 字符种类数统计，如果不相等直接返回false
		if (!check(cs1, cs2)) return false;
		return process(cs1, 0, cs1.length - 1, cs2, 0, cs2.length - 1);
	}
	private static boolean check(char[] cs1, char[] cs2) {
		if (cs1.length != cs2.length) return false;
		int[] map = new int[256];
		for (char c : cs1) map[c]++;
		for (char c : cs2) {
			if (--map[c] < 0) return false;
		}
		return true;
	}
	// cs1[l1...r1] 与 cs2[l2...r2]是等长的，返回是否互为旋变串
	private static boolean process(char[] cs1, int l1, int r1, char[] cs2, int l2, int r2) {
		// base case: 只有一个字符
		if (l1 == r1) return cs1[l1] == cs2[l2];
		// 枚举cs1的每一个切分位置: l1...l1, l1...l1+1, l1...l1+2, ...
		for (int leftEnd = l1; leftEnd < r1; leftEnd++) {
			// 情况一: 一一对应
			boolean p1 = process(cs1, l1, leftEnd, cs2, l2, l2  + leftEnd - l1)
					&& process(cs1, leftEnd + 1, r1, cs2, l2 + leftEnd - l1 + 1, r2);
			// 情况二: 错位交叉对应
			boolean p2 = process(cs1, l1, leftEnd, cs2, r2 - (leftEnd - l1), r2)
					&& process(cs1, leftEnd + 1, r1, cs2, l2, r2 - (leftEnd - l1) - 1);
			if (p1 || p2) return true;
		}
		return false;
	}

	// 方法二：优化参数个数(l1,r1、l2,r2) + 记忆化
	public static boolean isScrambleByRecord(String s1, String s2) {
		// 边界值判断
		if (s1 == null && s2 == null) return true;
		if (s1 == null || s2 == null) return false;
		if (s1.equals(s2)) return true;
		char[] cs1 = s1.toCharArray(), cs2 = s2.toCharArray();
		// 字符种类数统计，如果不相等直接返回false
		if (!check(cs1, cs2)) return false;
		int n = cs1.length;
		int[][][] record = new int[n][n][n + 1];
		return process(cs1, cs2, 0, 0, n, record);
	}
	// cs1从l1出发，cs2从l2出发，cs1与cs2等长长度为n，返回是否互为旋变串
	private static boolean process(char[] cs1, char[] cs2, int l1, int l2, int n, int[][][] record) {
		if (record[l1][l2][n] != 0) return record[l1][l2][n] == 1;
		boolean ans = false;
		// base case: 只有一个字符
		if (n == 1) ans = cs1[l1] == cs2[l2];
		else {
			// 枚举cs1的每一个切分位置: l1...l1, l1...l1+1, l1...l1+2, ...
			for (int leftPart = 1; leftPart < n; leftPart++) {
				// 情况一: 一一对应
				boolean p1 = process(cs1, cs2, l1, l2, leftPart, record)
						&& process(cs1, cs2, l1 + leftPart, l2 + leftPart, n - leftPart, record);
				// 情况二: 错位交叉对应
				boolean p2 = process(cs1, cs2, l1, l2 + n - leftPart, leftPart, record)
						&& process(cs1, cs2, l1 + leftPart, l2, n - leftPart, record);
				if (p1 || p2) {
					ans = true;
					break;
				}
			}
		}
		record[l1][l2][n] = ans ? 1 : -1;
		return ans;
	}

	// 方法三：改动态规划
	public static boolean isScrambleByDp(String s1, String s2) {
		// 边界值判断
		if (s1 == null && s2 == null) return true;
		if (s1 == null || s2 == null) return false;
		if (s1.equals(s2)) return true;
		char[] cs1 = s1.toCharArray(), cs2 = s2.toCharArray();
		// 字符种类数统计，如果不相等直接返回false
		if (!check(cs1, cs2)) return false;
		int n = cs1.length;
		boolean[][][] dp = new boolean[n][n][n + 1];
		// 初始化，只有一个字符的情况
		for (int l1 = 0; l1 < n; l1++) {
			for (int l2 = 0; l2 < n; l2++) {
				dp[l1][l2][1] = cs1[l1] == cs2[l2];
			}
		}
		// 分析普遍位置
		for (int k = 2; k <= n; k++) {
			for (int l1 = 0; l1 <= n - k; l1++) {
				for (int l2 = 0; l2 <= n - k; l2++) {
					for (int leftPart = 1; leftPart < k; leftPart++) {
						// 情况一: 一一对应
						boolean p1 = dp[l1][l2][leftPart] && dp[l1 + leftPart][l2 + leftPart][k - leftPart];
						// 情况二: 错位交叉对应
						boolean p2 = dp[l1][l2 + k - leftPart][leftPart] && dp[l1 + leftPart][l2][k - leftPart];
						if (p1 || p2) {
							dp[l1][l2][k] = true;
							break;
						}
					}
				}
			}
		}
		return dp[0][0][n];
	}
}
