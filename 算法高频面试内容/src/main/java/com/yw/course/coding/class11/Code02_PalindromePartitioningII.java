package com.yw.course.coding.class11;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试链接 : https://leetcode.cn/problems/palindrome-partitioning-ii/
 * @author yangwei
 */
public class Code02_PalindromePartitioningII {

	// 问题一：一个字符串至少要切几刀能让切出来的子串都是回文串
	// 从左往右的尝试模型，尝试函数process(cs, idx)表示从cs从[idx...]至少分割几次能构成回文
	// 改成动态规划，dp[i]表示从[i...]至少分割几次能构成回文，dp[0]就是最终答案
	public int minCut(String s) {
		int n = s.length();
		char[] cs = s.toCharArray();
		// 预处理生成一张表，返回l...r范围是否构成回文
		boolean[][] checkMap = genCheckMap(cs, n);
		int[] dp = new int[n + 1];
		for (int i = n - 1; i >= 0; i--) {
			if (checkMap[i][n - 1]) dp[i] = 1;
			else {
				int next = Integer.MAX_VALUE;
				for (int j = i; j < n; j++) {
					// 如果[i...j]这一段是回文，那么后面的字符串最少分割几次？
					if (checkMap[i][j]) next = Math.min(next, dp[j + 1]);
				}
				dp[i] = next + 1;
			}
		}
		return dp[0] - 1;
	}
	private static boolean[][] genCheckMap(char[] cs, int n) {
		boolean[][] ans = new boolean[n][n];
		// 初始化对角线
		for (int i = 0; i < n - 1; i++) {
			ans[i][i] = true;
			ans[i][i + 1] = cs[i] == cs[i + 1];
		}
		ans[n - 1][n - 1] = true;
		// 普遍位置
		for (int i = n - 3; i >= 0; i--) {
			for (int j = i + 2; j < n; j++) {
				ans[i][j] = cs[i] == cs[j] && ans[i + 1][j - 1];
			}
		}
		return ans;
	}

	// 问题二：返回问题一的其中一种划分结果
	public static List<String> minCutOneWay(String s) {
		int n = s.length();
		char[] cs = s.toCharArray();
		boolean[][] checkMap = genCheckMap(cs, n);
		int[] dp = new int[n + 1];
		for (int i = n - 1; i >= 0; i--) {
			if (checkMap[i][n - 1]) dp[i] = 1;
			else {
				int next = Integer.MAX_VALUE;
				for (int j = i; j < n; j++) {
					if (checkMap[i][j]) next = Math.min(next, dp[j + 1]);
				}
				dp[i] = next + 1;
			}
		}
		// 解释 dp[i]: 若(0...5)是回文，则dp[0] = dp[6] + 1
		List<String> ans = new ArrayList<>();
		// 依次检查每一个前缀[i...j-1]是否构成回文
		for (int i = 0, j = 1; j <= n; j++) {
			// 若[i...j-1]构成回文，且dp[i]与dp[j]正好是+1的关系，则是一种切分方式
			if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
				ans.add(s.substring(i, j));
				i = j;
			}
		}
		return ans;
	}

	// 问题三：返回问题一的所有划分结果
	public static List<List<String>> minCutAllWays(String s) {
		List<List<String>> ans = new ArrayList<>();
		int n = s.length();
		char[] cs = s.toCharArray();
		boolean[][] checkMap = genCheckMap(cs, n);
		int[] dp = new int[n + 1];
		for (int i = n - 1; i >= 0; i--) {
			if (checkMap[i][n - 1]) dp[i] = 1;
			else {
				int next = Integer.MAX_VALUE;
				for (int j = i; j < n; j++) {
					if (checkMap[i][j]) next = Math.min(next, dp[j + 1]);
				}
				dp[i] = next + 1;
			}
		}
		process(s, 0, 1, checkMap, dp, new ArrayList<>(), ans);
		return ans;
	}
	// 之前s[0....i-1]存到path里去了，考察s[i..j-1]分出来的第一份
	private static void process(String s, int i, int j, boolean[][] checkMap, int[] dp, List<String> path, List<List<String>> ans) {
		if (j == s.length()) { // s[i...n-1]
			if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
				path.add(s.substring(i, j));
				ans.add(new ArrayList<>(path));
				path.remove(path.size() - 1);
			}
		} else {// s[i...j-1]
			if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
				path.add(s.substring(i, j));
				process(s, j, j + 1, checkMap, dp, path, ans);
				path.remove(path.size() - 1);
			}
			process(s, i, j + 1, checkMap, dp, path, ans);
		}
	}

	public static void main(String[] args) {
		String s = null;
		List<String> ans2 = null;
		List<List<String>> ans3 = null;

		System.out.println("本题第二问，返回其中一种结果测试开始");
		s = "abacbc";
		ans2 = minCutOneWay(s);
		for (String str : ans2) {
			System.out.print(str + " ");
		}
		System.out.println();

		s = "aabccbac";
		ans2 = minCutOneWay(s);
		for (String str : ans2) {
			System.out.print(str + " ");
		}
		System.out.println();

		s = "aabaa";
		ans2 = minCutOneWay(s);
		for (String str : ans2) {
			System.out.print(str + " ");
		}
		System.out.println();
		System.out.println("本题第二问，返回其中一种结果测试结束");
		System.out.println();
		System.out.println("本题第三问，返回所有可能结果测试开始");
		s = "cbbbcbc";
		ans3 = minCutAllWays(s);
		for (List<String> way : ans3) {
			for (String str : way) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		System.out.println();

		s = "aaaaaa";
		ans3 = minCutAllWays(s);
		for (List<String> way : ans3) {
			for (String str : way) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		System.out.println();

		s = "fcfffcffcc";
		ans3 = minCutAllWays(s);
		for (List<String> way : ans3) {
			for (String str : way) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("本题第三问，返回所有可能结果测试结束");
	}

}
