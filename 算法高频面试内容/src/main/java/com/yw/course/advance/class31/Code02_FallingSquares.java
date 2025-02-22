package com.yw.course.advance.class31;

import java.util.*;

/**
 * 测试链接：https://leetcode.cn/problems/falling-squares/description/
 * @author yangwei
 */
public class Code02_FallingSquares {

	public List<Integer> fallingSquares(int[][] positions) {
		Map<Integer, Integer> map = indexMap(positions);
		int n = map.size(), max = 0;
		SegmentTree segmentTree = new SegmentTree(n);
		List<Integer> ans = new ArrayList<>();
		// 每落一个正方形，收集一下所有东西组成的图像，最高高度是什么
		for (int[] x : positions) {
			int L = map.get(x[0]), R = map.get(x[0] + x[1] - 1);
			int height = segmentTree.query(L, R, 1, n, 1) + x[1];
			max = Math.max(max, height);
			ans.add(max);
			segmentTree.update(L, R, height, 1, n, 1);
		}
		return ans;
	}
	private Map<Integer, Integer> indexMap(int[][] positions) {
		// 依次收集(有序集合、去重)所有的方块的左边界和右边界-1(防止贴边的)
		Set<Integer> pos = new TreeSet<>();
		for (int[] x : positions) {
			pos.add(x[0]);
			pos.add(x[0] + x[1] - 1);
		}
		// 将原始的左边界和右边界-1 与 [1,n] 对应
		Map<Integer, Integer> map = new HashMap<>();
		int count = 0;
		for (int x : pos) map.put(x, ++count);
		return map;
	}
	public static class SegmentTree {
		private int[] max;
		private int[] change;
		private boolean[] update;
		public SegmentTree(int size) {
			int n = (size + 1) << 2;
			max = new int[n];
			change = new int[n];
			update = new boolean[n];
		}
		public void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				update[rt] = true;
				change[rt] = C;
				max[rt] = C;
				return;
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			if (L <= mid) update(L, R, C, l, mid, rt << 1);
			if (R > mid) update(L, R, C, mid + 1, r, rt << 1 | 1);
			pushUp(rt);
		}
		public int query(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) return max[rt];
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			int left = 0, right = 0;
			if (L <= mid) left = query(L, R, l, mid, rt << 1);
			if (R > mid) right = query(L, R, mid + 1, r, rt << 1 | 1);
			return Math.max(left, right);
		}
		private void pushUp(int rt) {
			max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
		}
		private void pushDown(int rt, int ln, int rn) {
			int left = rt << 1, right = rt << 1 | 1;
			if (update[rt]) {
				update[left] = true;
				update[right] = true;
				change[left] = change[rt];
				change[right] = change[rt];
				max[left] = change[rt];
				max[right] = change[rt];
				update[rt] = false;
			}
		}
	}
}
