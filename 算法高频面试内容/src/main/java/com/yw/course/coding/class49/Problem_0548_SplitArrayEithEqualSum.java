package com.yw.course.coding.class49;

/**
 * @author yangwei
 */
public class Problem_0548_SplitArrayEithEqualSum {

	public static boolean splitArray(int[] nums) {
		int n = nums.length;
		if (n < 7) return false;
		// l2rSum: 前缀和，r2lSum: 后缀和
		int[] l2rSum = new int[n], r2lSum = new int[n];
		for (int i = 0, s = 0; i < n; i++) {
			l2rSum[i] = s;
			s += nums[i];
		}
		for (int i = n - 1, s = 0; i >= 0; i--) {
			r2lSum[i] = s;
			s += nums[i];
		}
		// 尝试每一个分割点i
		for (int i = 1; i < n - 5; i++) {
			// 尝试每一个分割点k
			for (int k = n - 2; k > i + 3 ; k--) {
				if (l2rSum[i] == r2lSum[k] && find(l2rSum, r2lSum, i, k))
					return true;
			}
		}
		return false;
	}
	private static boolean find(int[] l2rSum, int[] r2lSum, int l, int r) {
		int s = l2rSum[l], prefix = l2rSum[l + 1], suffix = r2lSum[r - 1];
		// 尝试每一个分割点j
		for (int j = l + 2; j < r - 1; j++) {
			// 中间部分分割的两部分子数组累加和: s1、s2
			int s1 = l2rSum[j] - prefix, s2 = r2lSum[j] - suffix;
			if (s1 == s2 && s1 == s) return true;
		}
		return false;
	}

	public static void main(String[] args) {
		int[] nums = {1,2,1,2,1,2,1};

		System.out.println(splitArray(nums));
	}
}
