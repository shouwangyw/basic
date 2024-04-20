package com.yw.course.coding.class20;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试链接：https://leetcode.cn/problems/largest-component-size-by-common-factor/
 * @author yangwei
 */
public class Code02_LargestComponentSizebyCommonFactor {

	// 方法一：暴力枚举，两两gcd判断是否最大公因子不等于1，然后利用并查集连接，时间复杂度 O(N^2)
	public int largestComponentSize0(int[] nums) {
		int n = nums.length;
		UnionFind uf = new UnionFind(n);
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (gcd(nums[i], nums[j]) != 1)
					uf.union(i, j);
			}
		}
		return uf.maxSize();
	}

	// 方法二：
	public int largestComponentSize(int[] nums) {
		int n = nums.length;
		UnionFind uf = new UnionFind(n);
		// 预处理每一个数的因子(大于1)，key: 某个因子，value: 拥有这个因子的数在原数组中的位置
		Map<Integer, Integer> factorMap = new HashMap<>();
		for (int i = 0; i < n; i++) {
			int x = nums[i], limit = (int) Math.sqrt(x);
			for (int j = 1; j <= limit; j++) {
				if (x % j != 0) continue;
				if (j != 1) {
					Integer idx = factorMap.get(j);
					if (idx == null) factorMap.put(j, i);
					else uf.union(idx, i);
				}
				int y = x / j;
				if (y == 1) continue;
				Integer idx = factorMap.get(y);
				if (idx == null) factorMap.put(y, i);
				else uf.union(idx, i);
			}
		}
		return uf.maxSize();
	}

	private int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}
	// 定义并查集
	private static class UnionFind {
		private int[] fa;
		private int[] size;
		public UnionFind(int n) {
			this.fa = new int[n];
			this.size = new int[n];
			for (int i = 0 ; i < n; i++) {
				fa[i] = i;
				size[i] = 1;
			}
		}
		public void union(int a, int b) {
			int ra = find(a), rb = find(b);
			if (ra == rb) return;
			fa[ra] = rb;
			size[rb] += size[ra];
		}
		public int find(int x) {
			return fa[x] = (fa[x] == x ? x : find(fa[x]));
		}
		public int maxSize() {
			int max = 0;
			for (int x : size) max = Math.max(max, x);
			return max;
		}
	}
}
