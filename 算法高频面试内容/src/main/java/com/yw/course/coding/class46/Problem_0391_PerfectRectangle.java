package com.yw.course.coding.class46;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Problem_0391_PerfectRectangle {

	public boolean isRectangleCover(int[][] rectangles) {
		if (rectangles == null || rectangles[0] == null) return false;
		int l = Integer.MAX_VALUE, r = Integer.MIN_VALUE, d = Integer.MAX_VALUE, u = Integer.MIN_VALUE;
		// 统计某个左边出现的次数，key: x_y，value: 次数
		Map<String, Integer> map = new HashMap<>();
		int area = 0; // 所有矩形面积之和
		for (int[] rect : rectangles) {
			// rect = {x1, y1, x2, y2};
			add(map, rect[0], rect[1]);
			add(map, rect[0], rect[3]);
			add(map, rect[2], rect[1]);
			add(map, rect[2], rect[3]);
			area += (rect[2] - rect[0]) * (rect[3] - rect[1]);
			l = Math.min(l, rect[0]);
			d = Math.min(d, rect[1]);
			r = Math.max(r, rect[2]);
			u = Math.max(u, rect[3]);
		}
		return checkPoints(map, l, d, r, u) && area == (r - l) * (u - d);
	}
	private void add(Map<String, Integer> map, int x, int y) {
		map.compute(pos(x, y), (k, v) -> v == null ? 1 : v + 1);
	}
	// 4个顶点应该只出现1次，其余点出现偶数次
	private boolean checkPoints(Map<String, Integer> map, int l, int d, int r, int u) {
		// 左下、左上、右下、右上
		String ld = pos(l, d), lu = pos(l, u), rd = pos(r, d), ru = pos(r, u);
		if (map.getOrDefault(ld, 0) != 1 || map.getOrDefault(lu, 0) != 1
				|| map.getOrDefault(rd, 0) != 1 || map.getOrDefault(ru, 0) != 1)
			return false;
		map.remove(ld);
		map.remove(lu);
		map.remove(rd);
		map.remove(ru);
		for (int val : map.values()) {
			if ((val & 1) != 0) return false;
		}
		return true;
	}
	private String pos(int x, int y) {
		return String.format("%s_%s", x, y);
	}

}
