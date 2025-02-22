package com.yw.course.coding.class36;

import java.util.TreeSet;

import static com.yw.util.CommonUtils.generateRandomString;

/**
 * 来自腾讯
 * 给定一个字符串str，和一个正数k
 * 返回长度为k的所有子序列中，字典序最大的子序列
 *
 * @author yangwei
 */
public class Code09_MaxKLenSequence {

	public static String maxkLenSeq(String s, int k) {
		if (k <= 0 || s.length() < k) return "";
		char[] cs = s.toCharArray();
		int n = cs.length;
		char[] stack = new char[n];
		int size = 0;
		for (int i = 0; i < n; i++) {
			while (size > 0 && stack[size - 1] < cs[i] && size + n - i > k) size--;
			if (size + n - i == k) return String.valueOf(stack, 0, size) + s.substring(i);
			stack[size++] = cs[i];
		}
		return String.valueOf(stack, 0, k);
	}

	public static void main(String[] args) {
		int n = 12;
		int r = 5;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * (n + 1));
			String str = generateRandomString(len, r);
			int k = (int) (Math.random() * (str.length() + 1));
			String ans1 = maxkLenSeq(str, k);
			String ans2 = test(str, k);
			if (!ans1.equals(ans2)) {
				System.out.println("出错了！");
				System.out.println(str);
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

	public static String test(String str, int k) {
		if (k <= 0 || str.length() < k) {
			return "";
		}
		TreeSet<String> ans = new TreeSet<>();
		process(0, 0, str.toCharArray(), new char[k], ans);
		return ans.last();
	}
	private static void process(int si, int pi, char[] str, char[] path, TreeSet<String> ans) {
		if (si == str.length) {
			if (pi == path.length) {
				ans.add(String.valueOf(path));
			}
		} else {
			process(si + 1, pi, str, path, ans);
			if (pi < path.length) {
				path[pi] = str[si];
				process(si + 1, pi + 1, str, path, ans);
			}
		}
	}

}
