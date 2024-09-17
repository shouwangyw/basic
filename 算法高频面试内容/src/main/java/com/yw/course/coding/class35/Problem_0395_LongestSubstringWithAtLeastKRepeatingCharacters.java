package com.yw.course.coding.class35;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class Problem_0395_LongestSubstringWithAtLeastKRepeatingCharacters {

	public static int longestSubstring1(String s, int k) {
		char[] str = s.toCharArray();
		int N = str.length;
		int max = 0;
		for (int i = 0; i < N; i++) {
			int[] count = new int[256];
			int collect = 0;
			int satisfy = 0;
			for (int j = i; j < N; j++) {
				if (count[str[j]] == 0) {
					collect++;
				}
				if (count[str[j]] == k - 1) {
					satisfy++;
				}
				count[str[j]]++;
				if (collect == satisfy) {
					max = Math.max(max, j - i + 1);
				}
			}
		}
		return max;
	}

	public int longestSubstring2(String s, int k) {
		char[] cs = s.toCharArray();
		int n = cs.length, ans = 0;
		for (int require = 1; require <= 26; require++) {
			int[] cnt = new int[26]; // 词频统计
			int collect = 0; // 当前窗口收集了几种字符
			int satisfy = 0; // 当前窗口词频大于等于k次的字符有几种
			int r = -1;
			for (int l = 0; l < n; l++) { // l尝试每一个窗口的最左位置
				// 当前[l...r]，如果r+1位置要进来
				while (r + 1 < n && !(collect == require && cnt[cs[r + 1] - 'a'] == 0)) {
					r++;
					if (cnt[cs[r] - 'a'] == 0) collect++;
					if (cnt[cs[r] - 'a'] == k - 1) satisfy++;
					cnt[cs[r] - 'a']++;
				}
				// 当前[l...r]
				if (satisfy == require) ans = Math.max(ans, r - l + 1);
				// l++，若l位置的字符要出窗口
				if (cnt[cs[l] - 'a'] == 1) collect--; // l位置词频是1，出窗口后窗口收集的字符种类-1
				if (cnt[cs[l] - 'a'] == k) satisfy--; // l位置词频是k，出窗口后窗口内满足>=k的字符种类-1
				cnt[cs[l] - 'a']--;
			}
		}
		return ans;
	}

	// 会超时，但是思路的确是正确的
	public static int longestSubstring3(String s, int k) {
		return process(s.toCharArray(), 0, s.length() - 1, k);
	}

	public static int process(char[] str, int L, int R, int k) {
		if (L > R) {
			return 0;
		}
		int[] counts = new int[26];
		for (int i = L; i <= R; i++) {
			counts[str[i] - 'a']++;
		}
		char few = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < 26; i++) {
			if (counts[i] != 0 && min > counts[i]) {
				few = (char) (i + 'a');
				min = counts[i];
			}
		}
		if (min >= k) {
			return R - L + 1;
		}
		int pre = 0;
		int max = Integer.MIN_VALUE;
		for (int i = L; i <= R; i++) {
			if (str[i] == few) {
				max = Math.max(max, process(str, pre, i - 1, k));
				pre = i + 1;
			}
		}
		if (pre != R + 1) {
			max = Math.max(max, process(str, pre, R, k));
		}
		return max;
	}

	public int longestSubstring(String s, int k) {
		char[] cs = s.toCharArray();
		// 统计词频
		int[] cnt = new int[26];
		for (char c : cs) cnt[c - 'a']++;
		// 词频小于k的位置可作为子串的分割点
		List<Integer> splits = new ArrayList<>();
		int n = cs.length;
		for (int i = 0; i < n; i++) {
			if (cnt[cs[i] - 'a'] < k) splits.add(i);
		}
		// 若不需要分割，最长子串长度就是n
		if (splits.size() == 0) return n;
		splits.add(n);
		int pre = 0, ans = 0;
		for (int p : splits) {
			int len = p - pre;
			if (len >= k)
				ans = Math.max(ans, longestSubstring(s.substring(pre, pre + len), k));
			pre = p + 1;
		}
		return ans;
	}
}
