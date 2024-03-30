package com.yw.course.coding.class14;

/**
 * 测试链接：https://leetcode.cn/problems/first-missing-positive/
 * @author yangwei
 */
public class Code06_MissingNumber {

	public int firstMissingPositive(int[] nums) {
		int l = 0, r = nums.length;
		while (l != r) {
			if (nums[l] == l + 1) l++;
			else if (nums[l] <= l || nums[l] > r || nums[nums[l] - 1] == nums[l]) swap(nums, l, --r);
			else swap(nums, l, nums[l] - 1);
		}
		return l + 1;
	}
	private static void swap(int[] arr, int i, int j) {
		if (i == j) return;
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}
