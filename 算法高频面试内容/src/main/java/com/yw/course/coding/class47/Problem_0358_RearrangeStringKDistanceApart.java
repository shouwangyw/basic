package com.yw.course.coding.class47;

import java.util.Arrays;

/**
 * 本题的解法思路与leetcode 621题 TaskScheduler
 * {@link com.yw.course.coding.class38.Problem_0621_TaskScheduler} 问题是一样的
 *
 * @author yangwei
 */
public class Problem_0358_RearrangeStringKDistanceApart {

	public static String rearrangeString(String s, int k) {
		if (s == null || s.length() < k) return s;
		char[] cs = s.toCharArray();
		// 记录每种字符、以及出现的次数 {{'d', 4}, {'a', 3}, ...}
		int[][] cnts = new int[256][2];
		// maxCnt: 记录出现次数最多的是几次，maxKinds: 记录出现最多次数的有几种
		int maxCnt = 0, maxKinds = 0;
		for (char c : cs) {
			cnts[c][0] = c;
			maxCnt = Math.max(maxCnt, ++cnts[c][1]);
		}
		for (int[] cnt : cnts) if (cnt[1] == maxCnt) maxKinds++;
		// 出现次数最多的至少需要占据位置: k * (maxCnt - 1)
		// 如果大于其它种类: cs.length - maxKinds，说明不能重排
		if (k * (maxCnt - 1) > cs.length - maxKinds) return "";
		// 对cnts排序，按出现次数从大到小
		Arrays.sort(cnts, (o1, o2) -> o2[1] - o1[1]);
		StringBuilder[] ans = new StringBuilder[maxCnt];
		for (int i = 0; i < maxCnt; i++) ans[i] = new StringBuilder();
		int i = 0, out = 0;
		while (i < cnts.length && cnts[i][1] == maxCnt) {
			for (int j = 0; j < maxCnt; j++)
				ans[j].append((char) cnts[i][0]);
			i++;
		}
		while (i < cnts.length) {
			for (int j = 0; j < cnts[i][1]; j++) {
				ans[out].append((char) cnts[i][0]);
				out = out == ans.length - 2 ? 0 : out + 1;
			}
			i++;
		}
		StringBuilder res = new StringBuilder();
		for (StringBuilder sb : ans) res.append(sb.toString());
		return res.toString();
	}

	public static void main(String[] args) {
		System.out.println(rearrangeString("aabbcc", 3));
		System.out.println(rearrangeString("aaabc", 3));
		System.out.println(rearrangeString("aaadbbcc", 2));
	}
}
