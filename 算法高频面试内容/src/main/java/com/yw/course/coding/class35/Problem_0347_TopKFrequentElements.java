package com.yw.course.coding.class35;

import java.util.*;

/**
 * @author yangwei
 */
public class Problem_0347_TopKFrequentElements {

	public int[] topKFrequent(int[] nums, int k) {
		// 词频统计
		Map<Integer, Integer> counter = new HashMap<>();
		for (int x : nums) counter.compute(x, (i, v) -> v == null ? 1 : v + 1);
		// 小根堆维护词频前k高的元素
		Queue<Map.Entry<Integer, Integer>> q = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
		for (Map.Entry<Integer, Integer> e : counter.entrySet()) {
			q.offer(e);
			if (q.size() > k) q.poll();
		}
		return q.stream().mapToInt(i -> i.getKey()).toArray();
	}

}
