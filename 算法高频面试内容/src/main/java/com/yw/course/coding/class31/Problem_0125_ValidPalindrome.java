package com.yw.course.coding.class31;

/**
 * @author yangwei
 */
public class Problem_0125_ValidPalindrome {

	// 忽略空格、忽略大小写 -> 是不是回文
	// 数字不在忽略大小写的范围内
	public boolean isPalindrome(String s) {
		int n = s.length();
		if (n <= 1) return true;
		int l = 0, r = n - 1;
		while (l < r) {
			while (l < r && !valid(s.charAt(l))) l++;
			while (l < r && !valid(s.charAt(r))) r--;
			if (!equals(s.charAt(l), s.charAt(r))) return false;
			l++;
			r--;
		}
		return true;
	}
	private boolean valid(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || isNumber(c);
	}
	private boolean equals(char c1, char c2) {
		if (isNumber(c1) || isNumber(c2)) return c1 == c2;
		return (c1 == c2) || (Math.max(c1, c2) - Math.min(c1, c2) == 32);
	}
	private boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}

}
