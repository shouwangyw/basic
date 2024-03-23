package com.yw.course.coding.class12;

import java.util.Arrays;

/**
 * 测试链接 : https://leetcode.cn/problems/permutation-in-string/
 * @author yangwei
 */
public class Code01_ContainAllCharExactly {

	// 方法一：
	public boolean checkInclusion(String s1, String s2) {
		int n1 = s1.length(), n2 = s2.length();
		char[] cs1 = s1.toCharArray(), cs2 = s2.toCharArray();
		int[] cnt = new int[26];
		for (char c : cs1) cnt[c - 'a']++;
		for (int i = 0; i <= n2 - n1; i++) {
			if (check(cs2, i, n1, Arrays.copyOf(cnt, cnt.length))) return true;
		}
		return false;
	}
	private boolean check(char[] cs, int idx, int n, int[] cnt) {
		for (int i = 0; i < n; i++) cnt[cs[i + idx] - 'a']--;
		for (int x : cnt) if (x > 0) return false;
		return true;
	}

	// 方法二：词频统计+滑动窗口
	public boolean checkInclusion2(String s1, String s2) {
		if (s1.length() > s2.length()) return false;
		char[] cs1 = s1.toCharArray(), cs2 = s2.toCharArray();
		int[] cnt = new int[26];
		for (char c : cs1) cnt[c - 'a']++;
		// w: 窗口宽度，r: 窗口右边界位置，n: 总的有效字符
		int w = s1.length(), r = 0, n = w;
		for (; r < w; r++) {    // 初始化窗口
			// 窗口右边进来一个字符，若cnt>0则cnt--、n--；否则cnt--
			if (cnt[cs2[r] - 'a']-- > 0) n--;
		}
		for (; r < cs2.length; r++) {
			if (n == 0) return r - w >= 0;
			// 窗口右边进来一个字符，若cnt>0则cnt--、n--；否则cnt--
			if (cnt[cs2[r] - 'a']-- > 0) n--;
			// 窗口左边出去一个字符，若cnt>=0则cnt++、n++；否则cnt++
			if (cnt[cs2[r - w] - 'a']++ >= 0) n++;
		}
		return n == 0 && (r - w) >= 0;
	}

	public static int containExactly1(String s, String a) {
		if (s == null || a == null || s.length() < a.length()) {
			return -1;
		}
		char[] aim = a.toCharArray();
		Arrays.sort(aim);
		String aimSort = String.valueOf(aim);
		for (int L = 0; L < s.length(); L++) {
			for (int R = L; R < s.length(); R++) {
				char[] cur = s.substring(L, R + 1).toCharArray();
				Arrays.sort(cur);
				String curSort = String.valueOf(cur);
				if (curSort.equals(aimSort)) {
					return L;
				}
			}
		}
		return -1;
	}

	public static int containExactly2(String s, String a) {
		if (s == null || a == null || s.length() < a.length()) {
			return -1;
		}
		char[] str = s.toCharArray();
		char[] aim = a.toCharArray();
		for (int L = 0; L <= str.length - aim.length; L++) {
			if (isCountEqual(str, L, aim)) {
				return L;
			}
		}
		return -1;
	}

	public static boolean isCountEqual(char[] str, int L, char[] aim) {
		int[] count = new int[256];
		for (int i = 0; i < aim.length; i++) {
			count[aim[i]]++;
		}
		for (int i = 0; i < aim.length; i++) {
			if (count[str[L + i]]-- == 0) {
				return false;
			}
		}
		return true;
	}

	public static int containExactly3(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() < s2.length()) {
			return -1;
		}
		char[] str2 = s2.toCharArray();
		int M = str2.length;
		int[] count = new int[256];
		for (int i = 0; i < M; i++) {
			count[str2[i]]++;
		}
		int all = M;
		char[] str1 = s1.toCharArray();
		int R = 0;
		// 0~M-1
		for (; R < M; R++) { // 最早的M个字符，让其窗口初步形成
			if (count[str1[R]]-- > 0) {
				all--;
			}
		}
		// 窗口初步形成了，并没有判断有效无效，决定下一个位置一上来判断
		// 接下来的过程，窗口右进一个，左吐一个
		for (; R < str1.length; R++) {
			if (all == 0) { // R-1
				return R - M;
			}
			if (count[str1[R]]-- > 0) {
				all--;
			}
			if (count[str1[R - M]]++ >= 0) {
				all++;
			}
		}
		return all == 0 ? R - M : -1;
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strMaxSize = 20;
		int aimMaxSize = 10;
		int testTimes = 500000;
		System.out.println("test begin, test time : " + testTimes);
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strMaxSize);
			String aim = getRandomString(possibilities, aimMaxSize);
			int ans1 = containExactly1(str, aim);
			int ans2 = containExactly2(str, aim);
			int ans3 = containExactly3(str, aim);
			if (ans1 != ans2 || ans2 != ans3) {
				System.out.println("Oops!");
				System.out.println(str);
				System.out.println(aim);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				break;
			}
		}
		System.out.println("test finish");

	}
	private static String getRandomString(int possibilities, int maxSize) {
		char[] ans = new char[(int) (Math.random() * maxSize) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}

}
