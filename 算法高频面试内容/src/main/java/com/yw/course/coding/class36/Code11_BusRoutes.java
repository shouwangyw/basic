package com.yw.course.coding.class36;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 来自三七互娱
 * Leetcode原题 : https://leetcode.com/problems/bus-routes/
 *
 * @author yangwei
 */
public class Code11_BusRoutes {

	// 返回：返回换乘几次+1 -> 返回一共坐了多少条线的公交
	public int numBusesToDestination(int[][] routes, int source, int target) {
		if (source == target) return 0;
		int n = routes.length;
		// key: 车站，value: list 表示该车站拥有哪些线路
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			final int idx = i;
			for (int j = 0; j < routes[i].length; j++) {
				map.compute(routes[i][j], (k, v) -> {
					if (v == null) v = new ArrayList<>();
					v.add(idx);
					return v;
				});
			}
		}
		if (!map.containsKey(source)) return -1;
		// 对线路进行宽度优先遍历
		List<Integer> q = new ArrayList<>();
		boolean[] visited = new boolean[n];
		for (int route : map.get(source)) {
			q.add(route);
			visited[route] = true;
		}
		int level = 1;
		while (!q.isEmpty()) {
			List<Integer> next = new ArrayList<>();
			for (int route : q) {
				for (int station : routes[route]) {
					if (station == target) return level;
					if (!map.containsKey(station)) continue;
					for (int nextRoute : map.get(station)) {
						if (visited[nextRoute]) continue;
						next.add(nextRoute);
						visited[nextRoute] = true;
					}
					// 优化
					map.remove(station);
				}
			}
			q = next;
			level++;
		}
		return -1;
	}

}
