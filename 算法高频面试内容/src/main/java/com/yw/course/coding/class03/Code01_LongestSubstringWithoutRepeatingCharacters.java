package com.yw.course.coding.class03;

import java.util.Arrays;

/**
 * 测试链接 : https://leetcode.cn/problems/longest-substring-without-repeating-characters/
 * @author yangwei
 */
public class Code01_LongestSubstringWithoutRepeatingCharacters {

	// 方法一：二分法
	public int lengthOfLongestSubstring0(String s) {
		int n = s.length();
		if (n == 0 || n == 1) return n;
		int l = 1, r = n, mid;
		while (l < r) {
			mid = (l + r + 1) >> 1;
			if (check(mid, s.toCharArray())) l = mid;
			else r = mid - 1;
		}
		return l;
	}
	private boolean check(int l, char[] cs) {
		int[] cnt = new int[256]; // 记录每个字符出现的次数
		int k = 0;
		for (int i = 0; i < cs.length; i++) {
			if (cnt[cs[i]] == 0) k++;
			cnt[cs[i]] += 1;
			if (i >= l) {
				cnt[cs[i - l]] -= 1;
				if (cnt[cs[i - l]] == 0) k -= 1;
			}
			if (k == l) return true;
		}
		return false;
	}

	// 方法二：动态规划
	public static int lengthOfLongestSubstring(String s) {
		if (s == null || s.length() == 0) return 0;
		char[] cs = s.toCharArray();
		// 0~255 ASCII 码
		// dp[i]: 表示i字符上次出现的位置
		int[] dp = new int[256];
		Arrays.fill(dp, -1);
		dp[cs[0]] = 0;
		// pre: 表示上一个位置向左推了多长
		int n = cs.length, ans = 1, pre = 1;
		for (int i = 1; i < n; i++) {
			// i - dp[cs[i]]: 当前位置 - 当前字符上次出现的位置
			pre = Math.min(i - dp[cs[i]], pre + 1);
			ans = Math.max(ans, pre);
			dp[cs[i]] = i;
		}
		return ans;
	}
}
