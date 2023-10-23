package com.yw.advance.course.class23;

import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code01_SplitSumClosed {

	// 方法一：尝试暴力递归
	private static int splitArr(int[] arr) {
		if (arr == null || arr.length <= 1) return 0;
		int sum = 0;
		for (int x : arr) sum += x;
		return process(arr, 0, sum / 2);
	}
	// arr 在[i...]自由选择，返回累加和尽量接近k，但不能超过k的最接近的累加和
	private static int process(int[] arr, int i, int k) {
		if (i == arr.length) return 0;
		// 不要idx位置的数
		int p1 = process(arr, i + 1, k);
		int p2 = 0;
		// 要idx位置的数
		if (k >= arr[i]) {
			p2 = arr[i] + process(arr, i + 1, k - arr[i]);
		}
		return Math.max(p1, p2);
	}

	// 方法二：改动态规划
	public static int splitArrByDp(int[] arr) {
		if (arr == null || arr.length <= 1) return 0;
		int sum = 0, n = arr.length;
		for (int x : arr) sum += x;
		int[][] dp = new int[n + 1][sum / 2 + 1];
		for (int i = n - 1; i >= 0; i--) {
			for (int k = 0; k <= sum / 2; k++) {
				dp[i][k] = dp[i + 1][k];
				if (k >= arr[i]) {
					dp[i][k] = Math.max(dp[i][k], arr[i] + dp[i + 1][k - arr[i]]);
				}
			}
		}
		return dp[0][sum / 2];
	}

	public static void main(String[] args) {
		int maxLen = 20;
		int maxValue = 50;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * maxLen);
			int[] arr = randomArray(len, maxValue);
			int ans1 = splitArr(arr);
			int ans2 = splitArrByDp(arr);
			if (ans1 != ans2) {
				printArray(arr);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}
	private static int[] randomArray(int len, int value) {
		int[] arr = new int[len];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * value);
		}
		return arr;
	}
}
