package com.yw.advance.course.class39;

/**
 * @author yangwei
 */
public class Code02_SnacksWays {

	public static int ways1(int[] arr, int w) {
		return process(arr, 0, w);
	}
	// 从左往右的经典模型，还剩的容量是rest，arr[index...]自由选择，返回选择方案
	private static int process(int[] arr, int idx, int rest) {
		// 没有容量了
		if (rest < 0) return -1;
		// rest>=0
		// 无零食可选
		if (idx == arr.length) return 1;
		// rest >=0
		// 有零食idx，要 or 不要？
		int p1 = process(arr, idx + 1, rest);
		int p2 = process(arr, idx + 1, rest - arr[idx]);
		return p1 + (p2 == -1 ? 0 : p2);
	}

	public static int ways2(int[] arr, int w) {
		int n = arr.length;
		int[][] dp = new int[n + 1][w + 1];
		for (int j = 0; j <= w; j++) dp[n][j] = 1;
		for (int i = n - 1; i >= 0; i--) {
			for (int j = 0; j <= w; j++) {
				dp[i][j] = dp[i + 1][j] + ((j - arr[i] >= 0) ? dp[i + 1][j - arr[i]] : 0);
			}
		}
		return dp[0][w];
	}

	public static int ways3(int[] arr, int w) {
		int n = arr.length;
		int[][] dp = new int[n][w + 1];
		for (int i = 0; i < n; i++) dp[i][0] = 1;
		if (arr[0] <= w) dp[0][arr[0]] = 1;
		for (int i = 1; i < n; i++) {
			for (int j = 1; j <= w; j++) {
				dp[i][j] = dp[i - 1][j] + ((j - arr[i]) >= 0 ? dp[i - 1][j - arr[i]] : 0);
			}
		}
		int ans = 0;
		for (int j = 0; j <= w; j++) {
			ans += dp[n - 1][j];
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] arr = { 4, 3, 2, 9 };
		int w = 8;
		System.out.println(ways1(arr, w));
		System.out.println(ways2(arr, w));
		System.out.println(ways3(arr, w));
	}

}
