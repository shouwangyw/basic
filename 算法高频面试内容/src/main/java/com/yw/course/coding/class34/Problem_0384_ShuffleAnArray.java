package com.yw.course.coding.class34;

import java.util.Random;

/**
 * @author yangwei
 */
public class Problem_0384_ShuffleAnArray {

	public class Solution {
		private int[] nums;
		private Random r = new Random();
		private int n;
		public Solution(int[] nums) {
			this.nums = nums;
			this.n = nums.length;
		}

		public int[] reset() {
			return nums;
		}

		public int[] shuffle() {
			int[] copy = new int[n];
			for (int i = 0; i < n; i++) copy[i] = nums[i];
			for (int i = n - 1; i >= 0; i--) {
				int k = r.nextInt(i + 1);
				int tmp = copy[i];
				copy[i] = copy[k];
				copy[k] = tmp;
			}
			return copy;
		}
	}

}
