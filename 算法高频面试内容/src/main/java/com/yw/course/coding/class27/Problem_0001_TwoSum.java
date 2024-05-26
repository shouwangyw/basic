package com.yw.course.coding.class27;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Problem_0001_TwoSum {

	public int[] twoSum(int[] nums, int target) {
		// key: 某个数字，value: 这个数字出现的位置
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			Integer j = map.get(target - nums[i]);
			if (j != null) return new int[] {i, j};
			else map.put(nums[i], i);
		}
		return new int[] { -1, -1 };
	}

}
