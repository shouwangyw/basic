package com.yw.advance.course.class24;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.LinkedList;

import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code04_MinCoinsOnePaper {

	// 方法一：尝试暴力递归
	public static int minCoins(int[] arr, int aim) {
		return process(arr, 0, aim);
	}
	private static int process(int[] arr, int idx, int k) {
		if (k < 0) return Integer.MAX_VALUE;
		if (idx == arr.length) return k == 0 ? 0 : Integer.MAX_VALUE;
		// 不要idx位置
		int p1 = process(arr, idx + 1, k);
		// 要idx
		int p2 = process(arr, idx + 1, k - arr[idx]);
		if (p2 != Integer.MAX_VALUE) {
			// 是有效解
			p2++;
		}
		return Math.min(p1, p2);
	}

	// 方法二：改动态规划，时间复杂度为：O(arr长度 * aim)
	public static int minCoinsByDp1(int[] arr, int aim) {
		int n = arr.length;
		int[][] dp = new int[n + 1][aim + 1];
		for (int k = 1; k <= aim; k++) dp[n][k] = Integer.MAX_VALUE;
		for (int idx = n - 1; idx >= 0; idx--) {
			for (int k = 0; k <= aim; k++) {
				// 不要idx位置
				dp[idx][k] = dp[idx + 1][k];
				// 要idx
				int nextK = k - arr[idx];
				if (nextK >= 0 && dp[idx + 1][nextK] != Integer.MAX_VALUE) {
					// 是有效解
					dp[idx][k] = Math.min(dp[idx][k], dp[idx + 1][nextK] + 1);
				}
			}
		}
		return dp[0][aim];
	}

	// 方法三：针对重复货币优化，先对货币数进行统计
	// 时间复杂度为：O(arr长度) + O(货币种数 * aim * 每种货币的平均张数)
	public static int minCoinsByDp2(int[] arr, int aim) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int x : arr) map.compute(x, (k, v) -> v == null ? 1 : v + 1);
		int[] coins = map.keySet().stream().mapToInt(i -> i).toArray();
		int[] cnts = map.values().stream().mapToInt(i -> i).toArray();
		int n = coins.length;
		int[][] dp = new int[n + 1][aim + 1];
		for (int k = 1; k <= aim; k++) dp[n][k] = Integer.MAX_VALUE;
		for (int idx = n - 1; idx >= 0; idx--) {
			for (int k = 0; k <= aim; k++) {
				dp[idx][k] = dp[idx + 1][k];
				// 时间复杂度为O(货币种数 * aim * 每种货币的平均张数)
				for (int m = 1; m * coins[idx] <= aim && m <= cnts[idx]; m++) {
					int nextK = k - m * coins[idx];
					if (nextK >= 0 && dp[idx + 1][nextK] != Integer.MAX_VALUE) {
						dp[idx][k] = Math.min(dp[idx][k], m + dp[idx + 1][nextK]);
					}
				}
			}
		}
		return dp[0][aim];
	}

	// 方法四：利用窗口内最小值这种更新结构优化
	public static int minCoinsByDp(int[] arr, int aim) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int x : arr) map.compute(x, (k, v) -> v == null ? 1 : v + 1);
		int[] coins = map.keySet().stream().mapToInt(i -> i).toArray();
		int[] cnts = map.values().stream().mapToInt(i -> i).toArray();
		int n = coins.length;
		int[][] dp = new int[n + 1][aim + 1];
		for (int k = 1; k <= aim; k++) dp[n][k] = Integer.MAX_VALUE;
		// 虽然是嵌套了很多循环，但是时间复杂度为O(货币种数 * aim)
		for (int i = n - 1; i >= 0; i--) {
			for (int mod = 0; mod < Math.min(aim + 1, coins[i]); mod++) {
				// 当前面值x，按mod分组
				// 这些位置形成窗口: mod + x, mod + 2 * x, mod + 3 * x
				Deque<Integer> qmin = new LinkedList<>();
				qmin.addLast(mod);
				dp[i][mod] = dp[i + 1][mod];
				for (int r = mod + coins[i]; r <= aim; r += coins[i]) {
					while (!qmin.isEmpty() && (dp[i + 1][qmin.peekLast()] == Integer.MAX_VALUE
							|| dp[i + 1][qmin.peekLast()] + compensate(qmin.peekLast(), r, coins[i]) >= dp[i + 1][r]))
						qmin.pollLast();
					qmin.addLast(r);
					if (qmin.peekFirst() == r - coins[i] * (cnts[i] + 1)) qmin.pollFirst();
					dp[i][r] = dp[i + 1][qmin.peekFirst()] + compensate(qmin.peekFirst(), r, coins[i]);
				}
			}
		}
		return dp[0][aim];
	}
	// 补偿
	private static int compensate(int pre, int cur, int coin) {
		return (cur - pre) / coin;
	}

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
			int ans2 = minCoinsByDp1(arr, aim);
			int ans3 = minCoinsByDp2(arr, aim);
			int ans4 = minCoinsByDp(arr, aim);
			if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
				System.out.println("Oops!");
				printArray(arr);
				System.out.println(aim);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println(ans4);
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("==========");

		int aim = 0;
		int[] arr = null;
		long start;
		long end;
		int ans2;
		int ans3;

		System.out.println("性能测试开始");
		maxLen = 30000;
		maxValue = 20;
		aim = 60000;
		arr = randomArray(maxLen, maxValue);

		start = System.currentTimeMillis();
		ans2 = minCoinsByDp2(arr, aim);
		end = System.currentTimeMillis();
		System.out.println("dp2答案 : " + ans2 + ", dp2运行时间 : " + (end - start) + " ms");

		start = System.currentTimeMillis();
		ans3 = minCoinsByDp(arr, aim);
		end = System.currentTimeMillis();
		System.out.println("dp3答案 : " + ans3 + ", dp3运行时间 : " + (end - start) + " ms");
		System.out.println("性能测试结束");

		System.out.println("===========");

		System.out.println("货币大量重复出现情况下，");
		System.out.println("大数据量测试dp3开始");
		maxLen = 20000000;
		aim = 10000;
		maxValue = 10000;
		arr = randomArray(maxLen, maxValue);
		start = System.currentTimeMillis();
		ans3 = minCoinsByDp(arr, aim);
		end = System.currentTimeMillis();
		System.out.println("dp3运行时间 : " + (end - start) + " ms");
		System.out.println("大数据量测试dp3结束");

		System.out.println("===========");

		System.out.println("当货币很少出现重复，dp2比dp3有常数时间优势");
		System.out.println("当货币大量出现重复，dp3时间复杂度明显优于dp2");
		System.out.println("dp3的优化用到了窗口内最小值的更新结构");
	}

	private static int[] randomArray(int N, int maxValue) {
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = (int) (Math.random() * maxValue) + 1;
		}
		return arr;
	}
}
