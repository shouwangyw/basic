package com.yw.course.coding.class49;

import java.util.Map;

/**
 * @author yangwei
 */
public class Problem_0564_FindTheClosestPalindrome {

	public static String nearestPalindromic(String n) {
		long num = Long.parseLong(n);
		// 生成候选回文数
		long raw = getRawPalindrome(n);
		// 生成较大的回文数
		long big = raw > num ? raw : getBigPalindrome(num, n.length());
		// 生成较小的回文数
		long small = raw < num ? raw : getSmallPalindrome(num, n.length());
		// 比较差值，返回更优解
		return String.valueOf(big - num >= num - small ? small : big);
	}
	private static long getBigPalindrome(long num, int len) {
		num += (long) Math.pow(10, len / 2);
		return getRawPalindrome(String.valueOf(num));
	}
	private static long getSmallPalindrome(long num, int len) {
		if (num <= 11) return num == 11 ? 9 : num - 1;
		// 处理偶数位且中间位为0的特殊情况，调整减法量级
		int adjust = (len % 2 == 0 && String.valueOf(num).charAt(len / 2) == '0') ? 1 : 0;
		num -= (long) Math.pow(10, len / 2 - adjust);
		return getRawPalindrome(String.valueOf(num));
	}
	private static long getRawPalindrome(String s) {
		char[] cs = s.toCharArray();
		for (int i = 0; i < cs.length / 2; i++)
			cs[cs.length - 1 - i] = cs[i];
		return Long.parseLong(String.valueOf(cs));
	}

	public static void main(String[] args) {
		System.out.println(nearestPalindromic("1000"));
		System.out.println(nearestPalindromic("11011"));
	}
}
