package com.yw.course.coding.class28;

/**
 * @author yangwei
 */
public class Problem_0034_FindFirstAndLastPositionOfElementInSortedArray {

	// 方法一
	public int[] searchRange0(int[] nums, int target) {
		int l = -1, r = -1;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == target) {
				l = i;
				break;
			}
		}
		for (int i = nums.length - 1; i >= 0; i--) {
			if (nums[i] == target) {
				r = i;
				break;
			}
		}
		return new int[] {l, r};
	}

	// 方法二：
	public int[] searchRange(int[] nums, int target) {
		if (nums == null || nums.length == 0) return new int[]{-1, -1};
		int l = lessMostRight(nums, target) + 1;
		if (l == nums.length || nums[l] != target) return new int[]{-1, -1};
		return new int[] {l, lessMostRight(nums, target + 1)};
	}
	// 二分法，找到小于某个数的最右位置
	private static int lessMostRight(int[] arr, int num) {
		int l = 0, r = arr.length - 1, mid;
		while (l <= r) {
			mid = l + ((r - l) >> 1);
			if (arr[mid] < num) l = mid + 1;
			else r = mid - 1;
		}
		return l - 1;
	}

}
