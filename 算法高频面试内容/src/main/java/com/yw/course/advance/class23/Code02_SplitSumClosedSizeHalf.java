package com.yw.course.advance.class23;

import java.util.Arrays;

import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code02_SplitSumClosedSizeHalf {

	// 方法一：尝试暴力递归
	public static int splitArr(int[] arr) {
		if (arr == null || arr.length <= 1) return 0;
		int n = arr.length, m = n >> 1, sum = 0;
		for (int x : arr) sum += x;
		sum /= 2;
		return (n & 1) == 0 ? process(arr, 0, m, sum)
				: Math.max(process(arr, 0, m, sum), process(arr, 0, m + 1, sum));
	}
	// arr[i....]自由选择，挑选m个数，累加和<=k, 返回离k最近的累加和
	private static int process(int[] arr, int i, int m, int k) {
		// 如果没数了，并且m==0即调够了m个数，返回0，否则返回-1表示无效
		if (i == arr.length) return m == 0 ? 0 : -1;
		// 不要i位置的数
		int p1 = process(arr, i + 1, m, k);
		// 要i位置的数
		int p2 = -1;
		if (k >= arr[i]) {
			int next = process(arr, i + 1, m - 1, k - arr[i]);
			if (next != -1) p2 = arr[i] + next;
		}
		return Math.max(p1, p2);
	}

	// 方法二：改动态规划
	public static int splitArrByDp0(int[] arr) {
		if (arr == null || arr.length <= 1) return 0;
		int n = arr.length, m = (n + 1) / 2, sum = 0;
		for (int x : arr) sum += x;
		sum /= 2;
		int[][][] dp = new int[n + 1][m + 1][sum + 1];
		// 先初始化所有位置-1
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= m; j++) Arrays.fill(dp[i][j], -1);
		}
		for (int k = 0; k <= sum; k++) dp[n][0][k] = 0;
		for (int i = n - 1; i >= 0; i--) {
			for (int j = 0; j <= m; j++) {
				for (int k = 0; k <= sum; k++) {
					// 不要i位置的数
					dp[i][j][k] = dp[i + 1][j][k];
					if (j < 1 || k < arr[i]) continue;
					// 要i位置的数
					int next = dp[i + 1][j - 1][k - arr[i]];
					if (next == -1) continue;
					dp[i][j][k] = Math.max(dp[i][j][k], arr[i] + next);
				}
			}
		}
		return (n & 1) == 0 ? dp[0][n / 2][sum] : Math.max(dp[0][n / 2][sum], dp[0][(n / 2) + 1][sum]);
	}


	public static int dp2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}
		sum >>= 1;
		int N = arr.length;
		int M = (arr.length + 1) >> 1;
		int[][][] dp = new int[N][M + 1][sum + 1];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j <= M; j++) {
				for (int k = 0; k <= sum; k++) {
					dp[i][j][k] = Integer.MIN_VALUE;
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int k = 0; k <= sum; k++) {
				dp[i][0][k] = 0;
			}
		}
		for (int k = 0; k <= sum; k++) {
			dp[0][1][k] = arr[0] <= k ? arr[0] : Integer.MIN_VALUE;
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j <= Math.min(i + 1, M); j++) {
				for (int k = 0; k <= sum; k++) {
					dp[i][j][k] = dp[i - 1][j][k];
					if (k - arr[i] >= 0) {
						dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - 1][k - arr[i]] + arr[i]);
					}
				}
			}
		}
		return Math.max(dp[N - 1][M][sum], dp[N - 1][N - M][sum]);
	}

	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 50;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * maxLen);
			int[] arr = randomArray(len, maxValue);
			int ans1 = splitArr(arr);
			int ans2 = splitArrByDp0(arr);
			int ans3 = dp2(arr);
			if (ans1 != ans2 || ans1 != ans3) {
				printArray(arr);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
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