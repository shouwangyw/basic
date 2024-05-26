package com.yw.course.coding.class29;

/**
 * @author yangwei
 */
public class Problem_0033_SearchInRotatedSortedArray {

	public int search(int[] nums, int target) {
		int l = 0, r = nums.length - 1, mid;
		while (l <= r) {
			mid = l + ((r - l) >> 1);
			if (nums[mid] == target) return mid;
			if (nums[mid] > nums[r]) {
				if (nums[mid] > target && nums[l] <= target) r = mid - 1;
				else l = mid + 1;
			} else {
				if (nums[mid] < target && nums[r] >= target) l = mid + 1;
				else r = mid - 1;
			}
		}
		return -1;
	}

}
