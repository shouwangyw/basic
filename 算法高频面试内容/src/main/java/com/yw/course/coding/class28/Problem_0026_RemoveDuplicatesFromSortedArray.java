package com.yw.course.coding.class28;

/**
 * @author yangwei
 */
public class Problem_0026_RemoveDuplicatesFromSortedArray {

	public int removeDuplicates(int[] nums) {
		int p = 0;
		for (int i = 1; i < nums.length; i++) {
			if (nums[p] != nums[i]) nums[++p] = nums[i];
		}
		return p + 1;
	}
}
