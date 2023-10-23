package com.yw.advance.course.class22;

import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code02_MinCoinsNoLimit {

	// 方法一：尝试暴力递归
	public static int minCoins(int[] arr, int aim) {
		return process(arr, 0, aim);
	}
	// arr[idx...]面值，每种面值张数自由选择，凑出k，返回最小张数
	private static int process(int[] arr, int idx, int k) {
		// Integer.MAX_VALUE: 凑不出
		if (idx == arr.length) return k == 0 ? 0 : Integer.MAX_VALUE;
		int ans = Integer.MAX_VALUE;
		for (int m = 0; m * arr[idx] <= k; m++) {
			int next = process(arr, idx + 1, k - m * arr[idx]);
			if (next != Integer.MAX_VALUE) ans = Math.min(ans, next + m);
		}
		return ans;
	}

	// 方法二：改动态规划
	public static int minCoinsByDp0(int[] arr, int aim) {
		int n = arr.length;
		int[][] dp = new int[n + 1][aim + 1];
		dp[n][0] = 0;
		for (int i = 1; i <= aim; i++) dp[n][i] = Integer.MAX_VALUE;
		for (int idx = n - 1; idx >= 0; idx--) {
			for (int k = 1; k <= aim; k++) {
				dp[idx][k] = Integer.MAX_VALUE;
				for (int m = 0; m * arr[idx] <= k; m++) {
					int next = dp[idx + 1][k - m * arr[idx]];
					if (next != Integer.MAX_VALUE) dp[idx][k] = Math.min(dp[idx][k], next + m);
				}
			}
		}
		return dp[0][aim];
	}

	// 方法三：进一步优化枚举行为的计算
	public static int minCoinsByDp(int[] arr, int aim) {
		int n = arr.length;
		int[][] dp = new int[n + 1][aim + 1];
		dp[n][0] = 0;
		for (int i = 1; i <= aim; i++) dp[n][i] = Integer.MAX_VALUE;
		for (int idx = n - 1; idx >= 0; idx--) {
			for (int k = 1; k <= aim; k++) {
				dp[idx][k] = dp[idx + 1][k];
				// k - arr[idx]: dp左边的值首先得不越界，并且是有效的(!= Integer.MAX_VALUE，即还能找到一个最小值)
				if (k - arr[idx] >= 0 && dp[idx][k - arr[idx]] != Integer.MAX_VALUE) {
					dp[idx][k] = Math.min(dp[idx][k], dp[idx][k - arr[idx]] + 1);
				}
			}
		}
		return dp[0][aim];
	}

	// 为了测试
	public static void main(String[] args) {
		int maxLen = 20;
		int maxValue = 30;
		int testTime = 300000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * maxLen);
			int[] arr = randomArray(N, maxValue);
			int aim = (int) (Math.random() * maxValue);
			int ans1 = minCoins(arr, aim);
			int ans2 = minCoinsByDp0(arr, aim);
			int ans3 = minCoinsByDp(arr, aim);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("Oops!");
				printArray(arr);
				System.out.println(aim);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("功能测试结束");
	}

	private static int[] randomArray(int maxLen, int maxValue) {
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
