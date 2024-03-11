package com.yw.course.coding.class10;

/**
 * 测试链接 : https://leetcode.cn/problems/jump-game-ii/
 * @author yangwei
 */
public class Code01_JumpGame {

	public int jump(int[] nums) {
		// step: 步数, cur: 在step步以内最远能到哪个位置, next: step+1步最远能到哪个位置
		int step = 0, cur = 0, next = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (cur < i) {
				step++;
				cur = next;
			}
			next = Math.max(next, i + nums[i]);
		}
		return step;
	}

}
