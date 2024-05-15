package com.yw.course.coding.class23;

import java.util.Arrays;
import java.util.Map;

/**
 * @author yangwei
 */
public class Code02_MaxABSBetweenLeftAndRight {

	// 方法一：暴力解，枚举N-1种切法，分别求左右部分最大值，时间复杂度O(N^2)
	public static int maxAbs0(int[] arr) {
		int ans = 0, n = arr.length;
		for (int i = 0; i < n - 1; i++) {
			int leftMax = Integer.MIN_VALUE, rightMax = Integer.MIN_VALUE;
			for (int j = 0; j < i + 1; j++) leftMax = Math.max(leftMax, arr[j]);
			for (int j = i + 1; j < n; j++) rightMax = Math.max(rightMax, arr[j]);
			ans = Math.max(ans, Math.abs(leftMax - rightMax));
		}
		return ans;
	}
	// 方法二：利用辅助数组，时间复杂度O(N)
	public static int maxAbs(int[] arr) {
		int ans = 0, n = arr.length;
		// left[i]表示0~i范围的最大值，right[i]表示i~n-1范围的最大值
		int[] left = new int[n], right = new int[n];
		left[0] = arr[0];
		right[n - 1] = arr[n - 1];
		for (int i = 1; i < n; i++) left[i] = Math.max(left[i - 1], arr[i]);
		for (int i = n - 2; i >= 0; i--) right[i] = Math.max(right[i + 1], arr[i]);
		for (int i = 0; i < n - 1; i++) ans = Math.max(ans, Math.abs(left[i] - right[i + 1]));
		return ans;
	}
	// 方法三：最优解
	public static int maxAbsOptimal(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) max = Math.max(max, arr[i]);
		// 最大值 - 最小{0位置数, N-1位置数}
		return max - Math.min(arr[0], arr[arr.length - 1]);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100000; i++) {
			int[] arr = generateRandomArray(200);
			int ans1 = maxAbs0(arr);
			int ans2 = maxAbs(arr);
			int ans3 = maxAbsOptimal(arr);
			if (ans1 != ans2 || ans2 != ans3) {
				System.out.println(Arrays.toString(arr));
				System.out.println("ans1 = " + ans1 + ", ans2 = " + ans2 + ", ans3 = " + ans3);
				break;
			}
		}
	}
	private static int[] generateRandomArray(int length) {
		int[] arr = new int[length];
		for (int i = 0; i != arr.length; i++) {
			arr[i] = (int) (Math.random() * 1000) - 499;
		}
		return arr;
	}
}
