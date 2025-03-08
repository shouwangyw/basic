package com.yw.course.coding.class41;

/**
 * @author yangwei
 */
public class Problem_0031_NextPermutation {

	public void nextPermutation(int[] nums) {
		int n = nums.length;
		// 从右往左遍历，找到第一次降序的位置
		int firstLess = -1;
		for (int i = n - 2; i >= 0; i--) {
			if (nums[i] < nums[i + 1]) {
				firstLess = i;
				break;
			}
		}
		if (firstLess == -1) reverse(nums, 0, n - 1);
		else {
			// 找比nums[firstLess]大的且最右位置，可以用二分优化
			int rightMore = -1;
			for (int i = n - 1; i > firstLess; i--) {
				if (nums[i] > nums[firstLess]) {
					rightMore = i;
					break;
				}
			}
			swap(nums, firstLess, rightMore);
			reverse(nums, firstLess + 1, n - 1);
		}
	}
	private void reverse(int[] nums, int l, int r) {
		while (l < r) swap(nums, l++, r--);
	}
	private void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}
}
