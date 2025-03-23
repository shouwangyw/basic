package com.yw.course.coding.class49;

import java.util.Arrays;

/**
 * @author yangwei
 */
public class Problem_0377_CombinationSumIV {

	// 方法一：暴力递归+傻缓存
	public int combinationSum4ByCache(int[] nums, int target) {
		int[] cache = new int[1001];
		Arrays.fill(cache, -1);
		return process(nums, target, cache);
	}
	private int process(int[] nums, int rest, int[] cache) {
		if (rest < 0) return 0;
		if (cache[rest] != -1) return cache[rest];
		if (rest == 0) return cache[0] = 1;
		int ways = 0;
		for (int num : nums) ways += process(nums, rest - num, cache);
		return cache[rest] = ways;
	}

	// 方法二：剪枝+严格位置依赖的动态规划
	public int combinationSum4(int[] nums, int target) {
		Arrays.sort(nums);
		int[] dp = new int[target + 1];
		dp[0] = 1;
		for (int rest = 1; rest <= target; rest++) {
			for (int i = 0; i < nums.length && nums[i] <= rest; i++) {
				dp[rest] += dp[rest - nums[i]];
			}
		}
		return dp[target];
	}

}
