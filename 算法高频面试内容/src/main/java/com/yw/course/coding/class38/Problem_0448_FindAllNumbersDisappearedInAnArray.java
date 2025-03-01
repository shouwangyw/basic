package com.yw.course.coding.class38;

import java.util.ArrayList;
import java.util.List;

import static com.yw.util.CommonUtils.swap;

/**
 * @author yangwei
 */
public class Problem_0448_FindAllNumbersDisappearedInAnArray {

	public List<Integer> findDisappearedNumbers(int[] nums) {
		List<Integer> ans = new ArrayList<>();
		if (nums == null || nums.length == 0) return ans;
		int n = nums.length;
		for (int i = 0; i < n; i++)
			// 从i位置出发，坐标循环怼
			process(nums, i);
		for (int i = 0; i < n; i++) {
			if (nums[i] != i + 1) ans.add(i + 1);
		}
		return ans;
	}

	private void process(int[] nums, int i) {
		while (nums[i] != i + 1) {
			int next = nums[i] - 1;
			if (nums[next] == next + 1) break;
			swap(nums, i, next);
		}
	}

}
