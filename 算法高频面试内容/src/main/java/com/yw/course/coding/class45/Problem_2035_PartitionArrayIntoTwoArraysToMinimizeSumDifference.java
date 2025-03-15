package com.yw.course.coding.class45;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author yangwei
 */
public class Problem_2035_PartitionArrayIntoTwoArraysToMinimizeSumDifference {

	public int minimumDifference(int[] nums) {
		int n = nums.length, half = n >> 1;
		Map<Integer, TreeSet<Integer>> lmap = new HashMap<>(), rmap = new HashMap<>();
		process(nums, 0, half, 0, 0, lmap);
		process(nums, half, n, 0, 0, rmap);
		int sum = 0;
		for (int x : nums) sum += x;
		int ans = Integer.MAX_VALUE;
		for (int l : lmap.keySet()) {
			for (int ls : lmap.get(l)) {
				Integer rs = rmap.get(half - l).floor((sum >> 1) - ls);
				if (rs == null) continue;
				ans = Math.min(ans, sum - ((ls + rs) << 1));
			}
		}
		return ans;
	}
	// arr -> 8   0 1 2 3      4 5 6 7
	// process(arr, 0, 4)  [0,4)
	// process(arr, 4, 8)  [4,8)
	// arr[index....end-1]这个范围上，去做选择
	// pick挑了几个数！
	// sum挑的这些数，累加和是多少！
	// map记录结果
	// HashMap<Integer, TreeSet<Integer>> map
	// key -> 挑了几个数，比如挑了3个数，但是形成累加和可能多个！
	// value -> 有序表，都记下来！
	// 整个过程，纯暴力！2^15 -> 3万多，纯暴力跑完，依然很快！
	private void process(int[] arr, int idx, int end, int pick, int sum, Map<Integer, TreeSet<Integer>> map) {
		if (idx == end)
			map.compute(pick, (k, v) -> {
				if (v == null) v = new TreeSet<>();
				v.add(sum);
				return v;
			});
		else {
			process(arr, idx + 1, end, pick, sum, map);
			process(arr, idx + 1, end, pick + 1, sum + arr[idx], map);
		}
	}

}
