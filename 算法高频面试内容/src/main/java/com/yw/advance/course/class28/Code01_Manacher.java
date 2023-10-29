package com.yw.advance.course.class28;

/**
 * @author yangwei
 */
public class Code01_Manacher {

	// 方法一：暴力解
	public static int longestPalindromeByForce(String s) {
		if (s == null || s.length() == 0) return 0;
		char[] cs = manacherString(s);
		int ans = 0;
		for (int i = 0; i < cs.length; i++) {
			int l = i - 1, r = i + 1;
			while (l >= 0 && r < cs.length && cs[l] == cs[r]) {
				l--;
				r++;
			}
			ans = Math.max(ans, r - l - 1);
		}
		return ans / 2;
	}
	private static char[] manacherString(String s) {
		char[] cs = s.toCharArray();
		char[] res = new char[cs.length * 2 + 1];
		for (int i = 0, j = 0; i < res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : cs[j++];
		}
		return res;
	}

	// 方法二：Manacher算法
	public static int longestPalindrome(String s) {
		if (s == null || s.length() == 0) return 0;
		char[] cs = manacherString(s);
		int ans = 0, n = cs.length;
		// 准备一个回文半径数组
		int[] pArr = new int[n];
		// c: 中心点位置，r: 中心点c所能扩成功的最右边界+1，初始化都设置为-1
		int c = -1, r = -1;
		for (int i = 0; i < n; i++) {
			// R第一个违规的位置，i>= R
			// i位置扩出来的答案，i位置扩的区域，至少是多大
			// i' = 2 * c - i
			pArr[i] = r > i ? Math.min(pArr[2 * c - i], r - i) : 1;
			while (i + pArr[i] < n && i - pArr[i] > -1) {
				// i + pArr[i]: 往右扩，i - pArr[i]: 往左扩
				if (cs[i + pArr[i]] == cs[i - pArr[i]]) pArr[i]++;
				else break;
			}
			// 若i位置能将r推的更右，则更新r、c
			if (i + pArr[i] > r) {
				r = i + pArr[i];
				c = i;
			}
			ans = Math.max(ans, pArr[i]);
		}
		return ans - 1;
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strSize = 20;
		int testTimes = 5000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strSize);
			int ans1 = longestPalindromeByForce(str);
			int ans2 = longestPalindrome(str);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				System.out.println(str + ": " + ans1 + " " + ans2);
				break;
			}
		}
		System.out.println("test finish");
	}
	private static String getRandomString(int possibilities, int size) {
		char[] ans = new char[(int) (Math.random() * size) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}
}
