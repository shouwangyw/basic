package com.yw.advance.course.class38;

import java.util.Arrays;

/**
 * @author yangwei
 */
public class Code04_MoneyProblem {

	// int[] d d[i]：i号怪兽的武力；int[] p p[i]：i号怪兽要求的钱
	// 暴力递归方案一
	public static long minMoney1(int[] d, int[] p) {
		return process1(d, p, 0, 0);
	}
	// 来到idx号怪兽，你的能力是ability，返回需要花的最少钱数
	private static long process1(int[] d, int[] p, int idx, int ability) {
		if (idx == d.length) return 0;
		if (ability < d[idx])
			// 如果能力比idx号怪兽弱，就只能贿赂
			return p[idx] + process1(d, p, idx + 1, ability + d[idx]);
		// 如果能力比idx号怪兽强，可以贿赂，也可以不贿赂
		return Math.min(
				p[idx] + process1(d, p, idx + 1, ability + d[idx]),
				process1(d, p, idx + 1, ability)
		);
	}
	// 暴力递归方案一改动态规划
	public static long minMoney1ByDp(int[] d, int[] p) {
		int n = d.length, sumD = 0;
		for (int x : d) sumD += x;
		long[][] dp = new long[n + 1][sumD + 1];
		for (int i = 0; i <= sumD; i++) dp[0][i] = 0;
		for (int idx = n - 1; idx >= 0; idx--) {
			for (int ability = 0; ability <= sumD; ability++) {
				// 如果这种情况发生，那么这个k必然是递归过程中不会出现的状态
				// 既然动态规划是尝试过程的优化，尝试过程碰不到的状态，不必计算
				if (ability + d[idx] > sumD) continue;
				if (ability < d[idx]) dp[idx][ability] = p[idx] + dp[idx + 1][ability + d[idx]];
				else dp[idx][ability] = Math.min(p[idx] + dp[idx + 1][ability + d[idx]], dp[idx + 1][ability]);
			}
		}
		return dp[0][0];
	}

	// 暴力递归方案二
	public static int minMoney2(int[] d, int[] p) {
		int n = p.length, sumP = 0;
		for (int x : p) sumP += x;
		for (int money = 0; money < sumP; money++) {
			if (process2(d, p, n - 1, money) != -1) return money;
		}
		return sumP;
	}
	// 从0一直打到idx号怪兽，刚好花money这么多钱，返回通过情况下的最大能力，通过不了返回-1
	public static long process2(int[] d, int[] p, int idx, int money) {
		// base case: 一个怪兽也没遇到
		if (idx == -1) return money == 0 ? 0 : -1;
		// idx >= 0
		long p1 = -1, p2 = -1;
		// 1. 不贿赂当前idx号怪兽
		long preMaxAbility = process2(d, p, idx - 1, money);
		if (preMaxAbility != -1 && preMaxAbility >= d[idx]) p1 = preMaxAbility;
		// 2. 贿赂当前idx号怪兽
		preMaxAbility = process2(d, p, idx - 1, money - p[idx]);
		if (preMaxAbility != -1) p2 = d[idx] + preMaxAbility;
		return Math.max(p1, p2);
	}
	// 暴力递归方案二改动态规划
	public static long minMoney2ByDp(int[] d, int[] p) {
		int n = p.length, sumP = 0;
		for (int x : p) sumP += x;
		// dp[i][j]含义: 能经过0～i的怪兽，且花钱为j（花钱的严格等于j）时的最大武力值
		// 如果dp[i][j]==-1，表示经过0～i的怪兽，花钱为j是无法通过的，或者之前的钱怎么组合也得不到正好为j的钱数
		int[][] dp = new int[n][sumP + 1];
		// 初始化-1
		for (int[] x : dp) Arrays.fill(x, -1);
		// 经过0～i的怪兽，花钱数一定为p[0]，达到武力值d[0]的地步。其他第0行的状态一律是无效的
		dp[0][p[0]] = d[0];
		for (int idx = 1; idx < n; idx++) {
			for (int money = 0; money <= sumP; money++) {
				// 贿赂当前idx号怪兽:
				// money-p[idx]不越界，且在花钱money-p[idx]时，要能从0到idx-1通过怪兽
				if (money >= p[idx] && dp[idx - 1][money - p[idx]] != -1)
					dp[idx][money] = dp[idx - 1][money - p[idx]] + d[idx];
				// 不贿赂当前idx号怪兽:
				// 从0到idx-1怪兽在发钱money情况下，能保证通过当前idx位置的怪兽
				if (dp[idx - 1][money] >= d[idx])
					dp[idx][money] = Math.max(dp[idx][money], dp[idx - 1][money]);
			}
		}
		// 最后一行上，最左侧的不为-1的列数(j)，就是答案
		for (int i = 0; i <= sumP; i++) {
			if (dp[n - 1][i] != -1) return i;
		}
		return 0;
	}

	public static void main(String[] args) {
		int len = 10;
		int value = 20;
		int testTimes = 10000;
		for (int i = 0; i < testTimes; i++) {
			int[][] arrs = generateTwoRandomArray(len, value);
			int[] d = arrs[0];
			int[] p = arrs[1];
			long ans1 = minMoney1(d, p);
			long ans2 = minMoney1ByDp(d, p);
			long ans3 = minMoney2(d,p);
			long ans4 = minMoney2ByDp(d, p);
			if (ans1 != ans2 || ans2 != ans3 || ans1 != ans4) {
				System.out.println("oops!");
			}
		}

	}

	public static int[][] generateTwoRandomArray(int len, int value) {
		int size = (int) (Math.random() * len) + 1;
		int[][] arrs = new int[2][size];
		for (int i = 0; i < size; i++) {
			arrs[0][i] = (int) (Math.random() * value) + 1;
			arrs[1][i] = (int) (Math.random() * value) + 1;
		}
		return arrs;
	}

}
