package com.yw.course.coding.class30;

import java.util.Arrays;

/**
 * @author yangwei
 */
public class Problem_0091_DecodeWays {

	// 方法一：从左到右的尝试模型
	public int numDecodings0(String s) {
		if (s == null || s.length() == 0) return 0;
		return process(s.toCharArray(), 0);
	}
	// cs[0...idx]已经转化完了，返回cs[idx...]有多少有效的方法数
	private static int process(char[] cs, int idx) {
		if (idx == cs.length) return 1;
		if (cs[idx] == '0') return 0;
		// [idx]位置单独转
		int ways = process(cs, idx + 1);
		// 如果已经没有下一个字符了
		if (idx + 1 == cs.length) return ways;
		if((cs[idx] - '0') * 10 + (cs[idx + 1] - '0') < 27)
			ways += process(cs, idx + 2);
		return ways;
	}

	// 方法二：傻缓存
	public int numDecodings1(String s) {
		if (s == null || s.length() == 0) return 0;
		int[] cache = new int[s.length() + 1];
		Arrays.fill(cache, -1);
		return process(s.toCharArray(), 0, cache);
	}
	// cs[0...idx]已经转化完了，返回cs[idx...]有多少有效的方法数
	private static int process(char[] cs, int idx, int[] cache) {
		if (cache[idx] != -1) return cache[idx];
		if (idx == cs.length) return cache[idx] = 1;
		if (cs[idx] == '0') return cache[idx] = 0;
		// [idx]位置单独转
		int ways = process(cs, idx + 1, cache);
		// 如果已经没有下一个字符了
		if (idx + 1 == cs.length) return cache[idx] = ways;
		if((cs[idx] - '0') * 10 + (cs[idx + 1] - '0') < 27)
			ways += process(cs, idx + 2, cache);
		return cache[idx] = ways;
	}

	// 方法三：动态规划
	public int numDecodingsByDp(String s) {
		if (s == null || s.length() == 0) return 0;
		char[] cs = s.toCharArray();
		int n = cs.length;
		int[] dp = new int[n + 1];
		dp[n] = 1;
		for (int i = n - 1; i >= 0; i--) {
			if (cs[i] == '0') continue;
			dp[i] = dp[i + 1];
			if (i + 1 == n) continue;
			if((cs[i] - '0') * 10 + (cs[i + 1] - '0') < 27)
				dp[i] += dp[i + 2];
		}
		return dp[0];
	}

	public static int numDecodings(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[] dp = new int[N + 1];
		dp[N] = 1;
		for (int i = N - 1; i >= 0; i--) {
			if (str[i] == '0') {
				dp[i] = 0;
			} else if (str[i] == '1') {
				dp[i] = dp[i + 1];
				if (i + 1 < N) {
					dp[i] += dp[i + 2];
				}
			} else if (str[i] == '2') {
				dp[i] = dp[i + 1];
				if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '6')) {
					dp[i] += dp[i + 2];
				}
			} else {
				dp[i] = dp[i + 1];
			}
		}
		return dp[0];
	}

}
