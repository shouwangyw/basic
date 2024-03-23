package com.yw.course.coding.class12;

import java.util.Arrays;

/**
 * 测试链接 : https://leetcode.cn/problems/regular-expression-matching/
 * @author yangwei
 */
public class Code04_RegularExpressionMatch {

	// 方法一：递归尝试
	public boolean isMatch(String s, String p) {
		return isMatch(s.toCharArray(), p.toCharArray(), 0, 0);
	}
	// s从i出发，p从j出发，返回p是否能匹配上s
	private boolean isMatch(char[] s, char[] p, int i, int j) {
		// base case: p到结束位置时，s也到结束位置，返回true，否则false
		if (j == p.length) return i == s.length;
		// 检查第一个是否匹配
		boolean firstMatch = i < s.length && (s[i] == p[j] || p[j] == '.');
		// p的下一个字符是*
		if (j + 1 < p.length && p[j + 1] == '*')
			// 1. 此时位置没对上，则s从p的下两位进行匹配
			// 2. 此时位置对上，则s从下一位和p进行匹配
			return isMatch(s, p, i, j + 2) || (firstMatch && isMatch(s, p, i + 1, j));
		// p的下一个字符不是*，则要求第一个字符能匹配上时，才能继续匹配s和p的下一位
		return firstMatch && isMatch(s, p, i + 1, j + 1);
	}

	// 方法二：尝试+记忆化
	public boolean isMatchByRecord(String s, String p) {
		int[][] record = new int[s.length() + 1][p.length() + 1];
		return isMatch(s.toCharArray(), p.toCharArray(), 0, 0, record);
	}
	// s从i出发，p从j出发，返回p是否能匹配上s
	private boolean isMatch(char[] s, char[] p, int i, int j, int[][] record) {
		if (record[i][j] != 0) return record[i][j] == 1;
		boolean ans;
		// base case: p到结束位置时，s也到结束位置，返回true，否则false
		if (j == p.length) ans = i == s.length;
		else {
			// 检查第一个是否匹配
			boolean firstMatch = i < s.length && (s[i] == p[j] || p[j] == '.');
			// p的下一个字符是*
			if (j + 1 < p.length && p[j + 1] == '*')
				// 1. 此时位置没对上，则s从p的下两位进行匹配
				// 2. 此时位置对上，则s从下一位和p进行匹配
				ans = isMatch(s, p, i, j + 2, record) || (firstMatch && isMatch(s, p, i + 1, j, record));
				// p的下一个字符不是*，则要求第一个字符能匹配上时，才能继续匹配s和p的下一位
			else ans = firstMatch && isMatch(s, p, i + 1, j + 1, record);
		}
		record[i][j] = ans ? 1 : -1;
		return ans;
	}

	// 方法三：动态规划+斜率优化
	public boolean isMatchByDp(String s, String p) {
		char[] cs = s.toCharArray(), cp = p.toCharArray();
		int m = cs.length, n = cp.length;
		// dp[i][j]: cs[i...]与cp[j...]是否能匹配上
		boolean[][] dp = new boolean[m + 1][n + 1];
		// 初始化dp表
		dp[m][n] = true; // 已经到结束位置了，认为是匹配上了
		for (int i = m; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				boolean firstMatch = i < m && (cs[i] == cp[j] || cp[j] == '.');
				if (j + 1 < n && cp[j + 1] == '*')
					dp[i][j] = dp[i][j + 2] || (firstMatch && dp[i + 1][j]);
				else dp[i][j] = firstMatch && dp[i + 1][j + 1];
			}
		}
		return dp[0][0];
	}

	private static boolean isValid(char[] s, char[] e) {
		// s中不能有'.' or '*'
		for (char c : s) {
			if (c == '*' || c == '.') {
				return false;
			}
		}
		// 开头的e[0]不能是'*'，没有相邻的'*'
		for (int i = 0; i < e.length; i++) {
			if (e[i] == '*' && (i == 0 || e[i - 1] == '*')) {
				return false;
			}
		}
		return true;
	}
//
//	// 初始尝试版本，不包含斜率优化
//	public static boolean isMatch1(String str, String exp) {
//		if (str == null || exp == null) {
//			return false;
//		}
//		char[] s = str.toCharArray();
//		char[] e = exp.toCharArray();
//		return isValid(s, e) && process(s, e, 0, 0);
//	}
//
//	// str[si.....] 能不能被 exp[ei.....]配出来！ true false
//	public static boolean process(char[] s, char[] e, int si, int ei) {
//		if (ei == e.length) { // exp 没了 str？
//			return si == s.length;
//		}
//		// exp[ei]还有字符
//		// ei + 1位置的字符，不是*
//		if (ei + 1 == e.length || e[ei + 1] != '*') {
//			// ei + 1 不是*
//			// str[si] 必须和 exp[ei] 能配上！
//			return si != s.length && (e[ei] == s[si] || e[ei] == '.') && process(s, e, si + 1, ei + 1);
//		}
//		// exp[ei]还有字符
//		// ei + 1位置的字符，是*!
//		while (si != s.length && (e[ei] == s[si] || e[ei] == '.')) {
//			if (process(s, e, si, ei + 2)) {
//				return true;
//			}
//			si++;
//		}
//		return process(s, e, si, ei + 2);
//	}
//
//	// 改记忆化搜索+斜率优化
//	public static boolean isMatch2(String str, String exp) {
//		if (str == null || exp == null) {
//			return false;
//		}
//		char[] s = str.toCharArray();
//		char[] e = exp.toCharArray();
//		if (!isValid(s, e)) {
//			return false;
//		}
//		int[][] dp = new int[s.length + 1][e.length + 1];
//		// dp[i][j] = 0, 没算过！
//		// dp[i][j] = -1 算过，返回值是false
//		// dp[i][j] = 1 算过，返回值是true
//		return isValid(s, e) && process2(s, e, 0, 0, dp);
//	}
//
//	public static boolean process2(char[] s, char[] e, int si, int ei, int[][] dp) {
//		if (dp[si][ei] != 0) {
//			return dp[si][ei] == 1;
//		}
//		boolean ans = false;
//		if (ei == e.length) {
//			ans = si == s.length;
//		} else {
//			if (ei + 1 == e.length || e[ei + 1] != '*') {
//				ans = si != s.length && (e[ei] == s[si] || e[ei] == '.') && process2(s, e, si + 1, ei + 1, dp);
//			} else {
//				if (si == s.length) { // ei ei+1 *
//					ans = process2(s, e, si, ei + 2, dp);
//				} else { // si没结束
//					if (s[si] != e[ei] && e[ei] != '.') {
//						ans = process2(s, e, si, ei + 2, dp);
//					} else { // s[si] 可以和 e[ei]配上
//						ans = process2(s, e, si, ei + 2, dp) || process2(s, e, si + 1, ei, dp);
//					}
//				}
//			}
//		}
//		dp[si][ei] = ans ? 1 : -1;
//		return ans;
//	}

	// 动态规划版本 + 斜率优化
	public static boolean isMatch3(String str, String pattern) {
		if (str == null || pattern == null) {
			return false;
		}
		char[] s = str.toCharArray();
		char[] p = pattern.toCharArray();
		if (!isValid(s, p)) {
			return false;
		}
		int N = s.length;
		int M = p.length;
		boolean[][] dp = new boolean[N + 1][M + 1];
		dp[N][M] = true;
		for (int j = M - 1; j >= 0; j--) {
			dp[N][j] = (j + 1 < M && p[j + 1] == '*') && dp[N][j + 2];
		}
		// dp[0..N-2][M-1]都等于false，只有dp[N-1][M-1]需要讨论
		if (N > 0 && M > 0) {
			dp[N - 1][M - 1] = (s[N - 1] == p[M - 1] || p[M - 1] == '.');
		}
		for (int i = N - 1; i >= 0; i--) {
			for (int j = M - 2; j >= 0; j--) {
				if (p[j + 1] != '*') {
					dp[i][j] = ((s[i] == p[j]) || (p[j] == '.')) && dp[i + 1][j + 1];
				} else {
					if ((s[i] == p[j] || p[j] == '.') && dp[i + 1][j]) {
						dp[i][j] = true;
					} else {
						dp[i][j] = dp[i][j + 2];
					}
				}
			}
		}
		return dp[0][0];
	}

}
