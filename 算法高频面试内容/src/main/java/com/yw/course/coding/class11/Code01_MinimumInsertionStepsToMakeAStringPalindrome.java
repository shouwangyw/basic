package com.yw.course.coding.class11;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试链接 : https://leetcode.cn/problems/minimum-insertion-steps-to-make-a-string-palindrome/
 * @author yangwei
 */
public class Code01_MinimumInsertionStepsToMakeAStringPalindrome {

	// 问题一：一个字符串至少需要添加多少个字符能整体变成回文串
	// 范围尝试模型，尝试函数process(cs, l, r)表示从[l...r]范围最少添加几个字符能构成回文
	// 改成动态规划，dp[i][j]表示从[i...j]范围能构成回文最少添加的字符个数，i <= j
	public int minInsertions(String s) {
		int n = s.length();
		char[] cs = s.toCharArray();
		int[][] dp = new int[n][n];
		// 初始化dp表，首先左下半区域(i>j)无效，对角线(i=j)表示一个字符无需添加dp[i][j] = 0
		// i=j+1对角线表示两个字符，两个字符相等则无需添加，否则需要添加一个字符
		for (int i = 0; i < n - 1; i++) dp[i][i + 1] = cs[i] == cs[i + 1] ? 0 : 1;
		// 分析普遍位置，从下至上、从左到右，左上角的值就是我们要求的答案
		// 1) 已知[i...j-1]范围的添加数量，j位置进来时，只需要在i位置之前加一个字符即可，即 dp[i][j-1]+1
		// 2) 已知[i+1...j]范围的添加数量，i位置进来时，只需要在j位置之后加一个字符即可，即 dp[i+1][j]+1
		// 3) 已知i位置与j位置字符相等，中间需要加几个字符，即 dp[i+1][j-1]
		for (int i = n - 3; i >= 0; i--) {
			for (int j = i + 2; j < n; j++) {
				dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
				if (cs[i] == cs[j]) dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
			}
		}
		return dp[0][n - 1];
	}

	// 问题二：返回问题一的其中一种添加结果
	public static String minInsertionsOneWay(String s) {
		int n = s.length();
		char[] cs = s.toCharArray();
		int[][] dp = new int[n][n];
		for (int i = 0; i < n - 1; i++) dp[i][i + 1] = cs[i] == cs[i + 1] ? 0 : 1;
		for (int i = n - 3; i >= 0; i--) {
			for (int j = i + 2; j < n; j++) {
				if (cs[i] == cs[j]) dp[i][j] = dp[i + 1][j - 1];
				else dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
			}
		}
		int l = 0, r = n - 1;
		char[] ans = new char[n + dp[l][r]];
		int ansL = 0, ansR = ans.length - 1;
		while (l < r) {
			if (dp[l][r - 1] == dp[l][r] - 1) { // 答案来自左边
				ans[ansL++] = cs[r];
				ans[ansR--] = cs[r--];
			} else if (dp[l + 1][r] == dp[l][r] - 1) { // 答案来自下边
				ans[ansL++] = cs[l];
				ans[ansR--] = cs[l++];
			} else { // 答案来自中间
				ans[ansL++] = cs[l++];
				ans[ansR--] = cs[r--];
			}
		}
		if (l == r) ans[ansL] = cs[l];
		return String.valueOf(ans);
	}

	// 问题三：返回问题一的所有划分结果
	public static List<String> minInsertionsAllWays(String s) {
		int n = s.length();
		char[] cs = s.toCharArray();
		int[][] dp = new int[n][n];
		for (int i = 0; i < n - 1; i++) dp[i][i + 1] = cs[i] == cs[i + 1] ? 0 : 1;
		for (int i = n - 3; i >= 0; i--) {
			for (int j = i + 2; j < n; j++) {
				if (cs[i] == cs[j]) dp[i][j] = dp[i + 1][j - 1];
				else dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
			}
		}
		List<String> ans = new ArrayList<>();
		int l = 0, r = n - 1;
		char[] path = new char[n + dp[l][r]];
		int pl = 0, pr = path.length - 1;
		process(cs, dp, l, r, path, pl, pr, ans);
		return ans;
	}
	private static void process(char[] cs, int[][] dp, int l, int r, char[] path, int pl, int pr, List<String> ans) {
		if (l >= r) {
			if (l == r) path[pl] = cs[l];
			ans.add(String.valueOf(path));
		} else {
			if (dp[l][r - 1] == dp[l][r] - 1) {
				path[pl] = cs[r];
				path[pr] = cs[r];
				process(cs, dp, l, r - 1, path, pl + 1, pr - 1, ans);
			}
			if (dp[l + 1][r] == dp[l][r] - 1) {
				path[pl] = cs[l];
				path[pr] = cs[l];
				process(cs, dp, l + 1, r, path, pl + 1, pr - 1, ans);
			}
			if (cs[l] == cs[r] && (l == r - 1 || dp[l + 1][r - 1] == dp[l][r])){
				path[pl] = cs[l];
				path[pr] = cs[r];
				process(cs, dp, l + 1, r - 1, path, pl + 1, pr - 1, ans);
			}
		}
	}

	public static void main(String[] args) {
		String s = null;
		String ans2 = null;
		List<String> ans3 = null;

		System.out.println("本题第二问，返回其中一种结果测试开始");
		s = "mbadm";
		ans2 = minInsertionsOneWay(s);
		System.out.println(ans2);

		s = "leetcode";
		ans2 = minInsertionsOneWay(s);
		System.out.println(ans2);

		s = "aabaa";
		ans2 = minInsertionsOneWay(s);
		System.out.println(ans2);
		System.out.println("本题第二问，返回其中一种结果测试结束");

		System.out.println();

		System.out.println("本题第三问，返回所有可能的结果测试开始");
		s = "mbadm";
		ans3 = minInsertionsAllWays(s);
		for (String way : ans3) {
			System.out.println(way);
		}
		System.out.println();

		s = "leetcode";
		ans3 = minInsertionsAllWays(s);
		for (String way : ans3) {
			System.out.println(way);
		}
		System.out.println();

		s = "aabaa";
		ans3 = minInsertionsAllWays(s);
		for (String way : ans3) {
			System.out.println(way);
		}
		System.out.println();
		System.out.println("本题第三问，返回所有可能的结果测试结束");
	}

}
