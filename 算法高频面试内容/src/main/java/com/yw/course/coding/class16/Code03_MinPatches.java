package com.yw.course.coding.class16;

/**
 * @author yangwei
 */
public class Code03_MinPatches {

	public static int minPatches(int[] nums, int n) {
		long range = 0;     // 已补齐1...range范围
		int patches = 0;    // 缺少patches个数
		for (int x : nums) {
			// 要求 1...x范围补齐
			while (x - 1 > range) {
				// range + 1是缺的数字
				range += range + 1; // 扩大range
				patches++;
				if (range >= n) return patches; // 只要range变大，看一下目标
			}
			range += x;
			if (range >= n) return patches; // 只要range变大，看一下目标
		}
		while (range < n) { // 还没到目标，就继续补数，扩大range
			range += range + 1;
			patches++;
		}
		return patches;
	}

	// 嘚瑟
	public static int minPatches2(int[] arr, int K) {
		int patches = 0; // 缺多少个数字
		int range = 0; // 已经完成了1 ~ range的目标
		for (int i = 0; i != arr.length; i++) {
			// 1~range
			// 1 ~ arr[i]-1
			while (arr[i] > range + 1) { // arr[i] 1 ~ arr[i]-1

				if (range > Integer.MAX_VALUE - range - 1) {
					return patches + 1;
				}

				range += range + 1; // range + 1 是缺的数字
				patches++;
				if (range >= K) {
					return patches;
				}
			}
			if (range > Integer.MAX_VALUE - arr[i]) {
				return patches;
			}
			range += arr[i];
			if (range >= K) {
				return patches;
			}
		}
		while (K >= range + 1) {
			if (K == range && K == Integer.MAX_VALUE) {
				return patches;
			}
			if (range > Integer.MAX_VALUE - range - 1) {
				return patches + 1;
			}
			range += range + 1;
			patches++;
		}
		return patches;
	}

	public static void main(String[] args) {
		int[] test = { 1, 2, 31, 33 };
		int n = 2147483647;
		System.out.println(minPatches(test, n));
	}

}
