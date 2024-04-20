package com.yw.course.coding.class20;

/**
 * @author yangwei
 */
public class Code04_PalindromeWays {

	// 方法一：暴力递归
	public static int ways0(String s) {
		if (s == null || s.length() == 0) return 0;
		char[] cs = s.toCharArray(), ps = new char[cs.length];
		return process(cs, 0, ps, 0);
	}
	private static int process(char[] cs, int ci, char[] ps, int pi) {
		if (ci == cs.length) return check(ps, pi) ? 1 : 0;
		int ways = process(cs, ci + 1, ps, pi);
		ps[pi] = cs[ci];
		ways += process(cs, ci + 1, ps, pi + 1);
		return ways;
	}
	private static boolean check(char[] ps, int pi) {
		if (pi == 0) return false;
		int l = 0, r = pi - 1;
		while (l < r) {
			if (ps[l++] != ps[r--]) return false;
		}
		return true;
	}

	// 方法二：动态规划
	public static int ways(String s) {
		if (s == null || s.length() == 0) return 0;
		char[] cs = s.toCharArray();
		int n = cs.length;
		int[][] dp = new int[n][n];
		// 初始化dp: 单个字符子序列，回文1种
		for (int i = 0; i < n; i++) dp[i][i] = 1;
		// 相邻两个字符子序列，相等3种，不等2种
		for (int i = 0; i < n - 1; i++) dp[i][i + 1] = cs[i] == cs[i + 1] ? 3 : 2;
		// 普遍位置递推，从下至上、从左至右
		for (int l = n - 3; l >= 0; l--) {
			for (int r = l + 2; r < n; r++) {
				dp[l][r] = dp[l + 1][r] + dp[l][r - 1] - dp[l + 1][r - 1];
				if (cs[l] == cs[r]) dp[l][r] += dp[l + 1][r - 1] + 1;
			}
		}
		return dp[0][n - 1];
	}

	public static void main(String[] args) {
		int N = 10;
		int types = 5;
		int testTimes = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int len = (int) (Math.random() * N);
			String str = randomString(len, types);
			int ans1 = ways0(str);
			int ans2 = ways(str);
			if (ans1 != ans2) {
				System.out.println(str);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}
	private static String randomString(int len, int types) {
		char[] str = new char[len];
		for (int i = 0; i < str.length; i++) {
			str[i] = (char) ('a' + (int) (Math.random() * types));
		}
		return String.valueOf(str);
	}
}
