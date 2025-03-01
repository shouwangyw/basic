package com.yw.course.coding.class39;

import java.util.Arrays;

/**
 * 来自百度
 * 给定一个字符串str，和一个正数k
 * str子序列的字符种数必须是k种，返回有多少子序列满足这个条件
 * 已知str中都是小写字母
 * 原始是取mod
 * 本节在尝试上，最难的
 * 搞出桶来，组合公式
 *
 * @author yangwei
 */
public class Code03_SequenceKDifferentKinds {

	// 方法一：尝试
	public static int getWays(String s, int k) {
		// 统计每种字符出现的次数
		int[] bucket = new int[26];
		for (char c : s.toCharArray()) bucket[c - 'a']++;
		return process(bucket, 0, k);
	}
	// bucket: {6,7,0,0,6,3}
	//          0 1 2 3 4 5
	// 			a b c d e f
	// 在桶数组bucket[idx...]一定要凑出 k 种字符，返回有几种方法
	private static int process(int[] bucket, int idx, int k) {
		if (k == 0) return 1;
		if (idx == bucket.length) return 0;
		// 1. 最后形成的子序列，一个idx位置代表的字符都没有
		int ways1 = process(bucket, idx + 1, k);
		// 2. 最后形成的子序列，一定要包含idx位置代表的字符，所有可能性都要算上
		// n个不同的球
		// 挑出1个的方法数 + 挑出2个的方法数 + ... + 挑出n个的方法数为:
		// C(n,1) + C(n,2) + ... + C(n,n) == (2 ^ n) -1
		int ways2 = ((1 << bucket[idx]) - 1) * process(bucket, idx + 1, k - 1);
		return ways1 + ways2;
	}

	// 方法二：改成傻缓存
	public static int getWaysByCache(String s, int k) {
		// 统计每种字符出现的次数
		int[] bucket = new int[26];
		for (char c : s.toCharArray()) bucket[c - 'a']++;
		int[][] cache = new int[bucket.length + 1][k + 1];
		for (int[] x : cache) Arrays.fill(x, -1);
		return process(bucket, 0, k, cache);
	}
	private static int process(int[] bucket, int idx, int k, int[][] cache) {
		if (cache[idx][k] != -1) return cache[idx][k];
		if (k == 0) return cache[idx][k] = 1;
		if (idx == bucket.length) return cache[idx][k] = 0;
		// 1. 最后形成的子序列，一个idx位置代表的字符都没有
		int ways1 = process(bucket, idx + 1, k, cache);
		// 2. 最后形成的子序列，一定要包含idx位置代表的字符，所有可能性都要算上
		// n个不同的球
		// 挑出1个的方法数 + 挑出2个的方法数 + ... + 挑出n个的方法数为:
		// C(n,1) + C(n,2) + ... + C(n,n) == (2 ^ n) -1
		int ways2 = ((1 << bucket[idx]) - 1) * process(bucket, idx + 1, k - 1, cache);
		return cache[idx][k] = (ways1 + ways2);
	}

	// 方法三：改成动态规划
	public static int getWaysByDp(String s, int k) {
		// 统计每种字符出现的次数
		int[] bucket = new int[26];
		for (char c : s.toCharArray()) bucket[c - 'a']++;
		int n = bucket.length;
		int[][] dp = new int[n + 1][k + 1];
		for (int[] d : dp) d[0] = 1;
		for (int i = n - 1; i >= 0; i--) {
			for (int k0 = 1; k0 <= k; k0++) {
				dp[i][k0] = dp[i + 1][k0] + ((1 << bucket[i]) - 1) * dp[i + 1][k0 - 1];
			}
		}
		return dp[0][k];
	}

	public static void main(String[] args) {
//		String str = "acbbca";
		String str = "acbbcafwesdacadas";
		int k = 3;
		System.out.println(getWays(str, k));
		System.out.println(getWaysByCache(str, k));
		System.out.println(getWaysByDp(str, k));
	}

}
