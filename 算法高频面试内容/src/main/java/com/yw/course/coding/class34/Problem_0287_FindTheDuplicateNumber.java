package com.yw.course.coding.class34;

/**
 * @author yangwei
 */
public class Problem_0287_FindTheDuplicateNumber {

	// 等价于找链接入环节点
	public int findDuplicate(int[] nums) {
		if (nums == null || nums.length < 2) return -1;
		int slow = 0, fast = 0;
		do {
			slow = nums[slow];
			fast = nums[nums[fast]];
		} while (slow != fast);
		fast = 0;
		while (slow != fast) {
			slow = nums[slow];
			fast = nums[fast];
		}
		return slow;
	}
}
