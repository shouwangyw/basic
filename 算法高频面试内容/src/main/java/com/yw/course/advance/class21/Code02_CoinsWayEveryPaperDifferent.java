package com.yw.course.advance.class21;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code02_CoinsWayEveryPaperDifferent {

	// 方法一：尝试暴力递归
	public static int coinWays(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) return 0;
		return process(arr, 0, aim);
	}
	// 返回 [idx, arr.length) 组成 k 的方法总数
	private static int process(int[] arr, int idx, int k) {
		if (k < 0) return 0;
		// 没钱了
		if (idx == arr.length) return k == 0 ? 1 : 0;
		// 不要idx位置的 + 要idx位置的
		return process(arr, idx + 1, k) + process(arr, idx + 1, k - arr[idx]);
	}

	// 方法二：基于暴力递归改动态规划
	public static int coinWaysByDp(int[] arr, int aim) {
		int n = arr.length;
		int[][] dp = new int[n + 1][aim + 1];
		dp[n][0] = 1;
		for (int idx = n - 1; idx >= 0; idx--) {
			for (int k = 0; k <= aim; k++) {
				// 同一行并不互相依赖，所以k的顺序也可以从右往左
				dp[idx][k] = dp[idx + 1][k];
				if (k - arr[idx] >= 0) {
					dp[idx][k] += dp[idx + 1][k - arr[idx]];
				}
			}
		}
		return dp[0][aim];
	}

	public static void main(String[] args) {
		int maxLen = 20;
		int maxValue = 30;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = randomArray(maxLen, maxValue);
			int aim = (int) (Math.random() * maxValue);
			int ans1 = coinWays(arr, aim);
			int ans2 = coinWaysByDp(arr, aim);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				printArray(arr);
				System.out.println(aim);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}
}
