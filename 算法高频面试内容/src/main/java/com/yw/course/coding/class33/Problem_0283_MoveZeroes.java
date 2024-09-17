package com.yw.course.coding.class33;

/**
 * @author yangwei
 */
public class Problem_0283_MoveZeroes {

	public void moveZeroes(int[] nums) {
		for (int i = 0, j = 0; i < nums.length; i++) {
			if (nums[i] == 0) continue;
			int tmp = nums[j];
			nums[j++] = nums[i];
			nums[i] = tmp;
		}
	}

}
