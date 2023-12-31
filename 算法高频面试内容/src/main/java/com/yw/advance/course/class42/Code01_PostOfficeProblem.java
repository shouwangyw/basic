package com.yw.advance.course.class42;

import java.util.Arrays;

import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code01_PostOfficeProblem {

	// 方法一：不优化版本的动态规划
	public static int minDistance(int[] arr, int num) {
		if (arr == null || arr.length < num || num < 1) return 0;
		int n = arr.length;
		// 预处理结构：w[i][j] 返回 [i, j] （i < j）范围只建一个邮件时的最短距离
		// w多准备一个长度，避免边界值处理
		int[][] w = new int[n + 1][n + 1];
		// 初始化w数组，只填右上半区域（从上往下、从左往右）
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				w[i][j] = w[i][j - 1] + (arr[j] - arr[(i + j) >> 1]);
			}
		}
		int[][] dp = new int[n][num + 1];
		// 初始化dp数组，第0行(即0个居民点，显然都是0，不用填)，第0列(即0个邮件，无效不用填)
		// 第1列直接从w数组拿值
		for (int i = 0; i < n; i++) dp[i][1] = w[0][i];
		for (int i = 1; i < n; i++) {
			// 优化 Math.min(i, num)，即邮件点个数如果超过居民点个数，最短距离一定是0(每个居民点都可以建一个邮件)，不用求
			for (int j = 2; j <= Math.min(i, num); j++) {
				int ans =  Integer.MAX_VALUE;
				// 不做优化，枚举每一个划分位置
				for (int k = 0; k <= i; k++) {
					ans = Math.min(ans, dp[k][j - 1] + w[k + 1][i]);
				}
				dp[i][j] = ans;
			}
		}
		return dp[n - 1][num];
	}
	// 方法二：优化枚举行为版本的动态规划
	public static int minDistanceOptimal(int[] arr, int num) {
		if (arr == null || arr.length < num || num < 1) return 0;
		int n = arr.length;
		// 预处理结构：w[i][j] 返回 [i, j] （i < j）范围只建一个邮件时的最短距离
		// w多准备一个长度，避免边界值处理
		int[][] w = new int[n + 1][n + 1];
		// 初始化w数组，只填右上半区域（从上往下、从左往右）
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				w[i][j] = w[i][j - 1] + (arr[j] - arr[(i + j) >> 1]);
			}
		}
		int[][] dp = new int[n][num + 1];
		int[][] best = new int[n][num + 1];
		// 初始化dp数组，第0行(即0个居民点，显然都是0，不用填)，第0列(即0个邮件，无效不用填)
		// 第1列直接从w数组拿值
		for (int i = 0; i < n; i++) {
			dp[i][1] = w[0][i];
			best[i][1] = -1;
		}
		for (int j = 2; j <= num; j++) {
			for (int i = n - 1; i >= j; i--) {
				int down = best[i][j - 1], up = i == n - 1 ? n - 1 : best[i + 1][j];
				int ans = Integer.MAX_VALUE, bestChoose = -1;
				for (int leftEnd = down; leftEnd <= up; leftEnd++) {
					int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
					int rightCost = leftEnd == i ? 0 : w[leftEnd + 1][i];
					int cur = leftCost + rightCost;
					// 这里 <= 或 < 都对
					if (cur <= ans) {
						ans = cur;
						bestChoose = leftEnd;
					}
				}
				dp[i][j] = ans;
				best[i][j] = bestChoose;
			}
		}
		return dp[n - 1][num];
	}

	public static void main(String[] args) {
		int N = 30;
		int maxValue = 100;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[] arr = randomSortedArray(len, maxValue);
			int num = (int) (Math.random() * N) + 1;
			int ans1 = minDistance(arr, num);
			int ans2 = minDistanceOptimal(arr, num);
			if (ans1 != ans2) {
				printArray(arr);
				System.out.println(num);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");

	}
	private static int[] randomSortedArray(int len, int range) {
		int[] arr = new int[len];
		for (int i = 0; i != len; i++) {
			arr[i] = (int) (Math.random() * range);
		}
		Arrays.sort(arr);
		return arr;
	}
}
