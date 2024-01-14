package com.yw.course.coding.class04;

/**
 * 测试链接 : https://leetcode.cn/problems/maximum-subarray/
 * @author yangwei
 */
public class Code02_SubArrayMaxSum {

	// 方法一：前缀和数组，暴力枚举每一个区间和计算子数组累加和最大值
	public int maxSubArray0(int[] nums) {
		int max = Integer.MIN_VALUE, n = nums.length;
		int[] sums = new int[n + 1];
		for (int i = 0; i < n; i++) { sums[i + 1] = sums[i] + nums[i]; }
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j < i; j++) max = Math.max(max, sums[i] - sums[j]);
		}
		return max;
	}

	// 方法二：
	public static int maxSubArray(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		int max = Integer.MIN_VALUE, cur = 0;
		for (int value : arr) {
			cur += value;
			max = Math.max(max, cur);
			cur = cur < 0 ? 0 : cur;
		}
		return max;
	}

	// 方法三：动态规划
	public static int maxSubArrayDp(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		int pre = arr[0], max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			pre = Math.max(arr[i], arr[i] + pre);
			max = Math.max(max, pre);
		}
		return max;
	}

}
