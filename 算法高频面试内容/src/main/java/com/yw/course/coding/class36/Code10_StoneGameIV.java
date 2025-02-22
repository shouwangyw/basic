package com.yw.course.coding.class36;

/**
 * 来自哈喽单车
 * 本题是leetcode原题 : https://leetcode.com/problems/stone-game-iv/
 *
 * @author yangwei
 */
public class Code10_StoneGameIV {

	// 方法一：暴力递归，会超出时间限制
	public boolean winnerSquareGame0(int n) {
		// 先手面对0，直接输
		if (n == 0) return false;
		// 先手尝试拿1、4、9、16、...
		for (int i = 1; i * i <= n; i++) {
			// 拿完后，后手如果输，则先手赢
			if (!winnerSquareGame0(n - i * i)) return true;
		}
		// 否则，先手输
		return false;
	}

	// 方法二：傻缓存
	public boolean winnerSquareGameByCache(int n) {
		int[] cache = new int[n + 1];
		cache[0] = -1; // -1 表示输，1 表示赢，0 表示未设置
		return winnerSquareGame(n, cache);
	}
	private static boolean winnerSquareGame(int n, int[] cache) {
		if (cache[n] != 0) return cache[n] == 1;
		boolean res = false;
		for (int i = 1; i * i <= n; i++) {
			if (!winnerSquareGame(n - i * i, cache)) {
				res = true;
				break;
			}
		}
		cache[n] = res ? 1 : -1;
		return res;
	}

	// 方法三：动态规划
	public static boolean winnerSquareGame(int n) {
		// dp[i]: 当前i个石子时先手是否能赢
		boolean[] dp = new boolean[n + 1];
		for (int i = 1; i <= n; i++) {
			// 先手依次尝试拿1、4、9、...颗石子
			for (int j = 1; j * j <= i; j++) {
				// 拿完j * j后剩下石子如果后手不能赢，则先手赢
				if (!dp[i - j * j]) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp[n];
	}
	
	public static void main(String[] args) {
		int n = 10000000;
		System.out.println(winnerSquareGame(n));
	}

}
