package com.yw.course.advance.class21;

import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code03_CoinsWayNoLimit {

	// 方法一：尝试暴力递归
	public static int coinsWay(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) return 0;
		return process(arr, 0, aim);
	}
	// [idx, arr.length) 所有的面值，每一个面值都可以任意选择张数，组成正好 k 这么多钱，方法数多少?
	private static int process(int[] arr, int idx, int k) {
		if (idx == arr.length) return k == 0 ? 1 : 0;
		int ways = 0;
		// 选择 m 个arr[idx]
		for (int m = 0; m * arr[idx] <= k; m++) {
			ways += process(arr, idx + 1, k - m * arr[idx]);
		}
		return ways;
	}

	// 方法二：动态规划
	public static int coinsWayByDp0(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) return 0;
		int n = arr.length;
		int[][] dp = new int[n + 1][aim + 1];
		dp[n][0] = 1;
		for (int idx = n - 1; idx >= 0; idx--) {
			for (int k = 0; k <= aim; k++) {
				for (int m = 0; m * arr[idx] <= k; m++) {
					dp[idx][k] += dp[idx + 1][k - m * arr[idx]];
				}
			}
		}
		return dp[0][aim];
	}

	// 方法三：动态规划-优化
	public static int coinsWayByDp(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) return 0;
		int n = arr.length;
		int[][] dp = new int[n + 1][aim + 1];
		dp[n][0] = 1;
		for (int idx = n - 1; idx >= 0; idx--) {
			for (int k = 0; k <= aim; k++) {
				dp[idx][k] = dp[idx + 1][k];
				if (k - arr[idx] >= 0) {
					dp[idx][k] += dp[idx][k - arr[idx]];
				}
			}
		}
		return dp[0][aim];
	}

	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 30;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = randomArray(maxLen, maxValue);
			int aim = (int) (Math.random() * maxValue);
			int ans1 = coinsWay(arr, aim);
			int ans2 = coinsWayByDp0(arr, aim);
			int ans3 = coinsWayByDp(arr, aim);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("Oops!");
				printArray(arr);
				System.out.println(aim);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				break;
			}
		}
		System.out.println("测试结束");
	}
	public static int[] randomArray(int maxLen, int maxValue) {
		int N = (int) (Math.random() * maxLen);
		int[] arr = new int[N];
		boolean[] has = new boolean[maxValue + 1];
		for (int i = 0; i < N; i++) {
			do {
				arr[i] = (int) (Math.random() * maxValue) + 1;
			} while (has[arr[i]]);
			has[arr[i]] = true;
		}
		return arr;
	}
}
