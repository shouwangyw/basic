package com.yw.advance.course.class28;

/**
 * @author yangwei
 */
public class Code02_AddShortestEnd {

	public static String shortestEnd(String s) {
		if (s == null || s.length() == 0) return null;
		char[] cs = manacherString(s);
		// max: 包含最后一个字符的最长回文长度
		int n = cs.length, c = -1, r = -1, max = -1;
		int[] pArr = new int[n];
		for (int i = 0; i < n; i++) {
			pArr[i] = r > i ? Math.min(pArr[2 * c - i], r - i) : 1;
			while (i + pArr[i] < n && i - pArr[i] > -1) {
				if (cs[i + pArr[i]] == cs[i - pArr[i]]) pArr[i]++;
				else break;
			}
			if (i + pArr[i] > r) {
				r = i + pArr[i];
				c = i;
			}
			if (r == n) {
				max = pArr[i] - 1;
				break;
			}
		}
		int resLen = s.length() - max;
		char[] res = new char[resLen];
		for (int i = 0; i < resLen; i++) {
			res[resLen - 1 - i] = cs[i * 2 + 1];
		}
		return String.valueOf(res);
	}
	private static char[] manacherString(String s) {
		char[] cs = s.toCharArray();
		char[] res = new char[cs.length * 2 + 1];
		for (int i = 0, j = 0; i < res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : cs[j++];
		}
		return res;
	}

	public static void main(String[] args) {
		String str1 = "abcd123321";
		System.out.println(shortestEnd(str1));
	}

}
