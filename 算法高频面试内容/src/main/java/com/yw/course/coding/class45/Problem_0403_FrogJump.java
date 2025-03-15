package com.yw.course.coding.class45;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author yangwei
 */
public class Problem_0403_FrogJump {

	public boolean canCross(int[] stones) {
		Set<Integer> set = new HashSet<>();
		for (int x : stones) set.add(x);
		Map<Integer, Map<Integer, Boolean>> cache = new HashMap<>();
		return jump(set, stones[stones.length - 1], 1, 1, cache);
	}
	private boolean jump(Set<Integer> set, int end, int cur, int k, Map<Integer, Map<Integer, Boolean>> cache) {
		if (cur == end) return true;
		if (!set.contains(cur)) return false;
		if (cache.containsKey(cur) && cache.get(cur).containsKey(k))
			return cache.get(cur).get(k);
		boolean ans = (k > 1 && jump(set, end, cur + k - 1, k - 1, cache))
				|| jump(set, end, cur + k, k, cache) || jump(set, end, cur + k + 1, k + 1, cache);
		cache.compute(cur, (key, val) -> {
			if (val == null) val = new HashMap<>();
			val.put(k, ans);
			return val;
		});
		return ans;
	}

}
