package com.yw.course.coding.class52;

import java.util.*;

/**
 * @author yangwei
 */
public class Problem_1488_AvoidFloodInTheCity {

	public int[] avoidFlood(int[] rains) {
		int n = rains.length;
		int[] ans = new int[n];
		// 定义一个map，记录每一个湖在哪些天会下雨
		Map<Integer, LinkedList<Integer>> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			if (rains[i] == 0) continue;
			final int day = i;
			map.compute(rains[i], (k, v) -> {
				if (v == null) v = new LinkedList<>();
				v.add(day);
				return v;
			});
		}
		// 记录哪些湖水满了
		Set<Integer> set = new HashSet<>();
		// 定义小根堆: 按湖泊的下雨天排序
		Queue<Work> heap = new PriorityQueue<>();
		for (int i = 0; i < n; i++) {
			if (rains[i] != 0) { // 今天下雨
				if (set.contains(rains[i])) return new int[0];
				set.add(rains[i]);
				map.get(rains[i]).pollFirst();
				if (!map.get(rains[i]).isEmpty())
					heap.add(new Work(rains[i], map.get(rains[i]).peekFirst()));
				ans[i] = -1;
			} else { // 今天干活
				if (heap.isEmpty()) ans[i] = 1;
				else {
					Work work = heap.poll();
					set.remove(work.lake);
					ans[i] = work.lake;
				}
			}
		}
		return ans;
	}
	private static class Work implements Comparable<Work> {
		private final int lake;
		private final int rainDay;
		public Work(int lake, int rainDay) {
			this.lake = lake;
			this.rainDay = rainDay;
		}
		@Override
		public int compareTo(Work o) {
			return this.rainDay - o.rainDay;
		}
	}

}
