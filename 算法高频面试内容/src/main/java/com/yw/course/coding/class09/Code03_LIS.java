package com.yw.course.coding.class09;

/**
 * 测试链接 : https://leetcode.cn/problems/longest-increasing-subsequence
 * @author yangwei
 */
public class Code03_LIS {

	// 方法一：动态规划，时间复杂度O(N^2)
	// dp[i]: 以i结尾的最长递增子序列长度
	public int lengthOfLIS(int[] nums) {
		int ans = 0, n = nums.length;
		int[] dp = new int[n];
		for (int i = 0; i < n; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (nums[j] >= nums[i]) continue;
				dp[i] = Math.max(dp[i], dp[j] + 1);
			}
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

	// 方法二：二分法，时间复杂度O(N*logN)
	public int lengthOfLIS2(int[] nums) {
		int ans = 1, n = nums.length;
		// ends[i]: 目前(遍历完i位置)发现的所有长度为i+1的子序列中最小的结尾值，ends[i+1...]都是无效区
		int[] ends = new int[n];
		ends[0] = nums[0];
		int l, r, mid, right = 0;
		for (int i = 1; i < n; i++) {
			l = 0;
			r = right;
			// 在ends数组有效区[0, right]二分查找: 大于等于某个数最左的位置
			while (l <= r) {
				mid = (l + r) / 2;
				if (nums[i] > ends[mid]) l = mid + 1;
				else r = mid - 1;
			}
			right = Math.max(right, l);
			ends[l] = nums[i];
			ans = Math.max(ans, l + 1);
		}
		return ans;
	}
}