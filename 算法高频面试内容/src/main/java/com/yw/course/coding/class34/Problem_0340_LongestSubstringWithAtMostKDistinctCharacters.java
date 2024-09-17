package com.yw.course.coding.class34;

/**
 * @author yangwei
 */
public class Problem_0340_LongestSubstringWithAtMostKDistinctCharacters {

	public static int lengthOfLongestSubstringKDistinct(String s, int k) {
		if (s == null || s.length() == 0 || k < 1) return 0;
		char[] cs = s.toCharArray();
		// r: 窗口的右边界, diff: 在窗口内不同字符的种类
		int n = cs.length, ans = 0, r = 0, diff = 0;
		int[] cnt = new int[256];
		for (int i = 0; i < n; i++) {
			while (r < n && diff < k || (diff == k && cnt[cs[r]] > 0)) {
				diff += cnt[cs[r]] == 0 ? 1 : 0;
				cnt[cs[r++]]++;
			}
			ans = Math.max(ans, r - i);
			diff -= cnt[cs[i]] == 1 ? 1 : 0;
			cnt[cs[i]]--;
		}
		return ans;
	}

}
