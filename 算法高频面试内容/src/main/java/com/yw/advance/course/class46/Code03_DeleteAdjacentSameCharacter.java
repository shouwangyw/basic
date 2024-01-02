package com.yw.advance.course.class46;

import java.util.Arrays;

/**
 *  如果一个字符相邻的位置没有相同字符，那么这个位置的字符出现不能被消掉
 *  比如:"ab"，其中a和b都不能被消掉
 *  如果一个字符相邻的位置有相同字符，就可以一起消掉
 *  比如:"abbbc"，中间一串的b是可以被消掉的，消除之后剩下"ac"
 *  某些字符如果消掉了，剩下的字符认为重新靠在一起
 *  给定一个字符串，你可以决定每一步消除的顺序，目标是请尽可能多的消掉字符，返回最少的剩余字符数量
 *  比如："aacca", 如果先消掉最左侧的"aa"，那么将剩下"cca"，然后把"cc"消掉，剩下的"a"将无法再消除，返回1
 *  但是如果先消掉中间的"cc"，那么将剩下"aaa"，最后都消掉就一个字符也不剩了，返回0，这才是最优解。
 *  再比如："baaccabb"，
 *  如果先消除最左侧的两个a，剩下"bccabb"，
 *  如果再消除最左侧的两个c，剩下"babb"，
 *  最后消除最右侧的两个b，剩下"ba"无法再消除，返回2
 *  而最优策略是：
 *  如果先消除中间的两个c，剩下"baaabb"，
 *  如果再消除中间的三个a，剩下"bbb"，
 *  最后消除三个b，不留下任何字符，返回0，这才是最优解
 * @author yangwei
 */
public class Code03_DeleteAdjacentSameCharacter {

	// 方法一：暴力解
	public static int getMinRemainingChars0(String s) {
		if (s == null) return 0;
		int len = s.length();
		if (len < 2) return len;
		int ans = len;
		for (int l = 0; l < len; l++) {
			for (int r = l + 1; r < len; r++) {
				if (canDelete(s.substring(l, r + 1))) {
					ans = Math.min(ans, getMinRemainingChars0(s.substring(0, l) + s.substring(r + 1)));
				}
			}
		}
		return ans;
	}
	private static boolean canDelete(String s) {
		char[] cs = s.toCharArray();
		for (int i = 1; i < cs.length; i++) {
			if (cs[i - 1] != cs[i]) return false;
		}
		return true;
	}

	// 方法二：优良尝试的暴力解
	public static int getMinRemainingChars(String s) {
		if (s == null) return 0;
		int len = s.length();
		if (len < 2) return len;
		char[] cs = s.toCharArray();
		return process(cs, 0, len - 1, false);
	}
	// 在[l, r]范围，前面有没有跟着[l]位置的字符，has: T 有 F 无，返回最少能剩多少字符消不了
	private static int process(char[] cs, int l, int r, boolean has) {
		if (l > r) return 0;
		if (l == r) return has ? 0 : 1;
		int idx = l, k = has ? 1 : 0;
		while (idx <= r && cs[idx] == cs[l]) {
			k++;
			idx++;
		}
		// idx表示第一个不是[l]位置字符的位置
		int way1 = (k > 1 ? 0 : 1) + process(cs, idx, r, false);
		int way2 = Integer.MAX_VALUE;
		for (int split = idx; split <= r; split++) {
			if (cs[split] == cs[l] && cs[split] != cs[split - 1]) {
				if (process(cs, idx, split - 1, false) == 0) {
					way2 = Math.min(way2, process(cs, split, r, k != 0));
				}
			}
		}
		return Math.min(way1, way2);
	}

	// 方法三：优良尝试的动态规划版本
	public static int getMinRemainingCharsDp(String s) {
		if (s == null) return 0;
		int n = s.length();
		if (n < 2) return n;
		char[] cs = s.toCharArray();
		int[][][] dp = new int[n][n][2];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Arrays.fill(dp[i][j], -1);
			}
		}
		return process(cs, 0, n - 1, false, dp);
	}
	private static int process(char[] cs, int l, int r, boolean has, int[][][] dp) {
		if (l > r) return 0;
		int k = has ? 1 : 0;
		if (dp[l][r][k] != -1) return dp[l][r][k];
		int ans;
		if (l == r) ans = (k == 0 ? 1 : 0);
		else {
			int idx = l, all = k;
			while (idx <= r && cs[idx] == cs[l]) {
				all++;
				idx++;
			}
			int way1 = (all > 1 ? 0 : 1) + process(cs, idx, r, false, dp);
			int way2 = Integer.MAX_VALUE;
			for (int split = idx; split <= r; split++) {
				if (cs[split] == cs[l] && cs[split] != cs[split - 1]) {
					if (process(cs, idx, split - 1, false, dp) == 0) {
						way2 = Math.min(way2, process(cs, split, r, all > 0, dp));
					}
				}
			}
			ans = Math.min(way1, way2);
		}
		dp[l][r][k] = ans;
		return ans;
	}

	public static void main(String[] args) {
		int maxLen = 16;
		int variety = 3;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * maxLen);
			String str = randomString(len, variety);
			int ans1 = getMinRemainingChars0(str);
			int ans2 = getMinRemainingChars(str);
			int ans3 = getMinRemainingCharsDp(str);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println(str);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("测试结束");
	}
	private static String randomString(int len, int variety) {
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			str[i] = (char) ((int) (Math.random() * variety) + 'a');
		}
		return String.valueOf(str);
	}
}
