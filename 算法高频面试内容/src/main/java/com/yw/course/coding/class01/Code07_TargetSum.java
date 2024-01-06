package com.yw.course.coding.class01;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/target-sum/
 * @author yangwei
 */
public class Code07_TargetSum {

	// 方法一：暴力尝试
	public static int findTargetSumWays0(int[] nums, int target) {
		return process(nums, 0, target);
	}
	// 返回 使用从i位置开始及其之后的位置的数 搞定t这个数的方法数
	private static int process(int[] nums, int i, int t) {
		// base case: 没数了，若 t = 0 说明搞定了，返回1，否则返回0
		if (i == nums.length) return t == 0 ? 1 : 0;
		// 还有数，则有两种选择: -nums[i] 或 +nums[i]
		return process(nums, i + 1, t - nums[i]) + process(nums, i + 1, t + nums[i]);
	}

	// 方法二：暴力尝试 + 傻缓存
	public static int findTargetSumWaysRecord(int[] nums, int target) {
		return process(nums, 0, target, new HashMap<>());
	}
	private static int process(int[] nums, int i, int t, Map<String, Integer> record) {
		String k = key(i, t);
		Integer val = record.get(k);
		if (val != null) return val;
		// base case: 没数了，若 t = 0 说明搞定了，返回1，否则返回0
		if (i == nums.length) val = t == 0 ? 1 : 0;
		// 还有数，则有两种选择: -nums[i] 或 +nums[i]
		else val = process(nums, i + 1, t - nums[i], record) + process(nums, i + 1, t + nums[i], record);
		record.put(k, val);
		return val;
	}
	private static String key(int i, int t) { return String.format("%d#%d", i, t); }

//	优化点一: 你可以认为arr中都是非负数，因为即便是arr中有负数，比如[3,-4,2]，因为你能在每个数前面用+或者-号
//	所以[3,-4,2]其实和[3,4,2]达成一样的效果，那么我们就全把arr变成非负数，不会影响结果的
//	优化点二: 如果arr都是非负数，并且所有数的累加和是sum，那么如果target>sum，很明显没有任何方法可以达到target，可以直接返回0
//	优化点三: arr内部的数组，不管怎么+和-，最终的结果都一定不会改变奇偶性。
//	所以，如果所有数的累加和是sum，并且与target的奇偶性不一样，没有任何方法可以达到target，可以直接返回0
//	优化点四: 比如说给定一个数组, arr = [1, 2, 3, 4, 5] 并且 target = 3
//	其中一个方案是: +1 -2 +3 -4 +5 = 3，该方案中取正的集合为P = {1，3，5}，取负的集合为N = {2，4}
//	所以任何一种方案，都一定有 sum(P) - sum(N) = target
//	现在我们来处理一下这个等式，把左右两边都加上sum(P) + sum(N)，那么就会变成如下：
//	sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
//	2 * sum(P) = target + 数组所有数的累加和，∴ sum(P) = (target + 数组所有数的累加和) / 2
//	也就是说，任何一个集合，只要累加和是(target + 数组所有数的累加和) / 2，那么就一定对应一种target的方式
//	也就是说，比如非负数组arr，target = 7, 而所有数累加和是11，求有多少方法组成7，其实就是求有多少种达到累加和(7+11)/2=9的方法
//	优化点五: 二维动态规划的空间压缩技巧

	// 方法三：
	public static int findTargetSumWays(int[] nums, int target) {
		// 优化一：若nums中有负数
		for (int i = 0; i < nums.length; i++) nums[i] = Math.abs(nums[i]);
		int sum = 0;
		for (int x : nums) sum += x;
		// 优化二：sum < target
		// 优化三：((target & 1) ^ (sum & 1)) != 0 奇偶性不一样
		// 优化四：问题转换 求非负数组nums有多少个子集，累加和是s
		return sum < target || ((target & 1) ^ (sum & 1)) != 0 ? 0 : targetSumWays(nums, (target + sum) >> 1);
	}
	// 优化五：二维动态规划，用空间压缩:
	// 核心就是for循环里面的：for (int i = s; i >= n; i--) {
	// 为啥不枚举所有可能的累加和？只枚举 n...s 这些累加和？
	// 因为如果 i - n < 0，dp[i]怎么更新？和上一步的dp[i]一样！所以不用更新
	// 如果 i - n >= 0，dp[i]怎么更新？上一步的dp[i] + 上一步dp[i - n]的值，这才需要更新
	private static int targetSumWays(int[] nums, int target) {
		if (target < 0) return 0;
		int[] dp = new int[target + 1];
		dp[0] = 1;
		for (int x : nums) {
			for (int i = target; i >= x; i--) dp[i] += dp[i - x];
		}
		return dp[target];
	}

	// 求非负数组nums有多少个子集，累加和是s
	// 二维动态规划
	// 不用空间压缩
	public static int subset1(int[] nums, int s) {
		if (s < 0) {
			return 0;
		}
		int n = nums.length;
		// dp[i][j] : nums前缀长度为i的所有子集，有多少累加和是j？
		int[][] dp = new int[n + 1][s + 1];
		// nums前缀长度为0的所有子集，有多少累加和是0？一个：空集
		dp[0][0] = 1;
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= s; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j - nums[i - 1] >= 0) {
					dp[i][j] += dp[i - 1][j - nums[i - 1]];
				}
			}
		}
		return dp[n][s];
	}
}
