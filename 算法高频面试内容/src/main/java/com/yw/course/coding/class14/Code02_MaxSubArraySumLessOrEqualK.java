package com.yw.course.coding.class14;

import java.util.TreeSet;

/**
 * 在数组 arr 中求子数组的累加和，是小于等于 K 的并且是最大的，返回这个最大的累加和。
 *
 * @author yangwei
 */
public class Code02_MaxSubArraySumLessOrEqualK {

	public static int getMaxSubArraySumLessOrEqualK(int[] arr, int k) {
		// 记录i之前的前缀和，按有序表组织
		TreeSet<Integer> set = new TreeSet<>();
		// 一个数没有时，就已经有一个前缀和是0
		set.add(0);
		int max = Integer.MAX_VALUE, sum = 0;
		for (int i = 0; i < arr.length; i++) {
			// sum 记录 arr[0...i]累加和
			sum += arr[i];
			Integer target = set.ceiling(sum - k);
			if (target != null) {
				max = Math.max(max, sum - target);
			}
			set.add(sum);
		}
		return max;
	}
}
