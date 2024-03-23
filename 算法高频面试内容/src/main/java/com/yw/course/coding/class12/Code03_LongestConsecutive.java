package com.yw.course.coding.class12;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试链接 : https://leetcode.cn/problems/longest-consecutive-sequence/
 * @author yangwei
 */
public class Code03_LongestConsecutive {

	// 方法一：并查集
	public int longestConsecutive(int[] nums) {
		Map<Integer, Integer> map = new HashMap<>();
		UnionSet us = new UnionSet(nums.length);
		for (int i = 0; i < nums.length; i++) {
			int x = nums[i];
			if (map.get(x) != null) continue;
			// 找左右相邻的数进行合并
			Integer l = map.get(x - 1), r = map.get(x + 1);
			if (l != null) us.merge(i, l);
			if (r != null) us.merge(i, r);
			map.put(x, i);
		}
		// 整理答案
		int ans = 0;
		for (int i = 0; i < nums.length; i++) {
			if (us.get(i) == i && us.counter[i] > ans) ans = us.counter[i];
		}
		return ans;
	}
	class UnionSet {
		private int[] fa;
		private int[] counter; // 记录连续序列数量
		public UnionSet(int n) {
			fa = new int[n + 1];
			counter = new int[n + 1];
			for (int i = 0; i <= n; i++) {
				fa[i] = i;
				counter[i] = 1;
			}
		}
		public int get(int x) {
			return fa[x] = (fa[x] == x ? x : get(fa[x]));
		}
		public void merge(int a, int b) {
			if (get(a) == get(b)) return;
			counter[get(b)] += counter[get(a)];
			fa[get(a)] = get(b);
		}
	}

	// 方法二：
	public int longestConsecutive2(int[] nums) {
		// map中记录以某个数(key)开头或结尾的连续序列长度(value)
		Map<Integer, Integer> map = new HashMap<>();
		int ans = 0;
		for (int x : nums) {
			if (map.containsKey(x)) continue;
			// 先建出长度为1
			map.put(x, 1);
			// 找一下与x相邻的数 x-1、x+1 是否有连续长度
			int preLen = map.getOrDefault(x - 1, 0), postLen = map.getOrDefault(x + 1, 0);
			int len = preLen + postLen + 1;
			// 更新开头和结尾的连续序列长度
			map.put(x - preLen, len);
			map.put(x + postLen, len);
			// 更新答案
			ans = Math.max(ans, len);
		}
		return ans;
	}
}
