package com.yw.course.coding.class44;

import java.util.HashMap;

/**
 * @author yangwei
 */
public class Problem_0992_SubarraysWithKDifferentIntegers {

	// nums 数组，题目规定，nums中的数字，不会超过nums的长度
	// [ ]长度为5，0~5
	public int subarraysWithKDistinct0(int[] nums, int k) {
		int n = nums.length, ans = 0;
		// 定义两个窗口, cnt_k_1: k-1种数的频次统计，cnt_k: k种数的频次统计
		int[] cnt_k_1 = new int[n + 1], cnt_k = new int[n + 1];
		// l_k_1、c_k_1: k-1种数窗口左边界和窗口中数的种类
		// l_k、c_k: k种数窗口左边界和窗口中数的种类
		int l_k_1 = 0, c_k_1 = 0, l_k = 0, c_k = 0;
		for (int r = 0; r < n; r++) {
			if (cnt_k_1[nums[r]]++ == 0) c_k_1++;
			if (cnt_k[nums[r]]++ == 0) c_k++;
			while (c_k_1 == k) {
				if (cnt_k_1[nums[l_k_1++]]-- == 1) c_k_1--;
			}
			while (c_k > k) {
				if (cnt_k[nums[l_k++]]-- == 1) c_k--;
			}
			ans += l_k_1 - l_k;
		}
		return ans;
	}

	public int subarraysWithKDistinct(int[] nums, int k) {
		return numsMostK(nums, k) - numsMostK(nums, k - 1);
	}
	private int numsMostK(int[] nums, int k) {
		int n = nums.length, l = 0, r = 0, cnt = 0, ans = 0;
		int[] counter = new int[n + 1];
		while (r < n) {
			if (counter[nums[r++]]++ == 0) cnt++;
			while (cnt > k) {
				if (counter[nums[l++]]-- == 1) cnt--;
			}
			ans += r - l;
		}
		return ans;
	}

}
