package com.yw.course.advance.class21;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code04_CoinsWaySameValueSamePapper {

	// 方法一：尝试暴力递归
	public static int coinsWay(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) return 0;
		// 先统计每种货币的个数
		Map<Integer, Integer> counter = new HashMap<>();
		for (int x : arr) counter.compute(x, (k, v) -> v == null ? 1 : v + 1);
		int n = counter.size(), i = 0;
		int[] coins = new int[n], cnts = new int[n];
		for (Entry<Integer, Integer> entry : counter.entrySet()) {
			coins[i] = entry.getKey();
			cnts[i++] = entry.getValue();
		}
		// 从左往右递归尝试
		return process(coins, cnts, 0, aim);
	}
	private static int process(int[] coins, int[] cnts, int idx, int k) {
		if (idx == coins.length) return k == 0 ? 1 : 0;
		int ways = 0;
		for (int m = 0; m * coins[idx] <= k && m <= cnts[idx]; m++) {
			ways += process(coins, cnts, idx + 1, k - m * coins[idx]);
		}
		return ways;
	}

	// 方法二：动态规划
	public static int coinsWayByDp0(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) return 0;
		// 先统计每种货币的个数
		Map<Integer, Integer> counter = new HashMap<>();
		for (int x : arr) counter.compute(x, (k, v) -> v == null ? 1 : v + 1);
		int n = counter.size(), i = 0;
		int[] coins = new int[n], cnts = new int[n];
		for (Entry<Integer, Integer> entry : counter.entrySet()) {
			coins[i] = entry.getKey();
			cnts[i++] = entry.getValue();
		}
		int[][] dp = new int[n + 1][aim + 1];
		dp[n][0] = 1;
		for (int idx = n - 1; idx >= 0; idx--) {
			for (int k = 0; k <= aim; k++) {
				for (int m = 0; m * coins[idx] <= k && m <= cnts[idx]; m++) {
					dp[idx][k] += dp[idx + 1][k - m * coins[idx]];
				}
			}
		}
		return dp[0][aim];
	}

	// 方法三：进一步优化DP
	public static int coinsWayByDp(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) return 0;
		// 先统计每种货币的个数
		Map<Integer, Integer> counter = new HashMap<>();
		for (int x : arr) counter.compute(x, (k, v) -> v == null ? 1 : v + 1);
		int n = counter.size(), i = 0;
		int[] coins = new int[n], cnts = new int[n];
		for (Entry<Integer, Integer> entry : counter.entrySet()) {
			coins[i] = entry.getKey();
			cnts[i++] = entry.getValue();
		}
		int[][] dp = new int[n + 1][aim + 1];
		dp[n][0] = 1;
		for (int idx = n - 1; idx >= 0; idx--) {
			for (int k = 0; k <= aim; k++) {
				dp[idx][k] = dp[idx + 1][k];	// 下边
				if (k - coins[idx] >= 0) {		// 左边
					dp[idx][k] += dp[idx][k - coins[idx]];
				}
				// 如果当前币值*(最大张数+1)，还凑不齐，则所依赖的左边 dp[idx][k - coins[idx]] 有重复计算
				if (k - coins[idx] * (cnts[idx] + 1) >= 0) { // 去重
					dp[idx][k] -= dp[idx + 1][k - coins[idx] * (cnts[idx] + 1)];
				}
			}
		}
		return dp[0][aim];
	}

	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 20;
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
//				System.out.println(ans3);
				break;
			}
		}
		System.out.println("测试结束");
	}

	private static int[] randomArray(int maxLen, int maxValue) {
		int N = (int) (Math.random() * maxLen);
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = (int) (Math.random() * maxValue) + 1;
		}
		return arr;
	}
}
