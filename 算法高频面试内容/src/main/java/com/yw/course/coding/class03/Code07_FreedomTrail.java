package com.yw.course.coding.class03;

import java.util.*;

/**
 * 测试链接 : https://leetcode.cn/problems/freedom-trail/
 * @author yangwei
 */
public class Code07_FreedomTrail {

	public int findRotateSteps(String ring, String key) {
		char[] rs = ring.toCharArray(), ks = key.toCharArray();
		int n = rs.length;
		// rsMap: 记录ring中每个字符出现的位置，key-一种字符, value-哪些位置拥有这个字符
		Map<Character, List<Integer>> rsMap = new HashMap<>();
		for (int i = 0; i < n; i++) {
			final int fi = i;
			rsMap.compute(rs[i], (k, v) -> {
				if (v == null) v = new ArrayList<>();
				v.add(fi);
				return v;
			});
		}
		// 加个傻缓存
		int[][] record = new int[n][ks.length + 1];
		for (int[] x : record) Arrays.fill(x, -1);
		return process(0, 0, ks, n, rsMap, record);
	}
	// preIdx: 指针指着的上一个按键位置，idx: 当前要搞定的字符位置，ks: 目标key字符数组，n: ring的长度
	// 返回需要的步数
	private static int process(int preIdx, int idx, char[] ks, int n, Map<Character, List<Integer>> rsMap, int[][] record) {
		if (record[preIdx][idx] != -1) return record[preIdx][idx];
		// base case
		if (idx == ks.length) return (record[preIdx][idx] = 0);
		int ans = Integer.MAX_VALUE;
		// 还有字符需要搞定
		List<Integer> nextIdxes = rsMap.get(ks[idx]);
		for (Integer nextIdx : nextIdxes) {
			int cost = dial(preIdx, nextIdx, n) + 1 + process(nextIdx, idx + 1, ks, n, rsMap, record);
			ans = Math.min(ans, cost);
		}
		return (record[preIdx][idx] = ans);
	}
	// size: 环中元素个数，返回 从i1位置到i2位置 最少距离
	private static int dial(int i1, int i2, int size) {
		int abs = Math.abs(i1 - i2);
		// 顺时针: Math.abs(i1 - i2)、逆时针: size - abs
		return Math.min(abs, size - abs);
	}

}
