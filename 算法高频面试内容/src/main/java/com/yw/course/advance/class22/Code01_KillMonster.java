package com.yw.course.advance.class22;

/**
 * @author yangwei
 */
public class Code01_KillMonster {

	// 方法一：尝试暴力递归
	public static double killProbability(int n, int m, int k) {
		if (n <= 0 || m <= 0 || k <= 0) return 0;
		// 总可能性(m + 1)^k
		return (double) process(k, n, m) / Math.pow(m + 1, k);
	}
	// 还有k次可以砍，怪兽还剩n滴血，流式血量[0, m]
	private static long process(int k, int n, int m) {
		if (k == 0) return n <= 0 ? 1 : 0;
		// n <= 0: 怪兽血量已经没有了，可以直接计算
		if (n <= 0) return (long) Math.pow(m + 1, k);
		long ans = 0;
		for (int i = 0; i <= m; i++) {
			ans += process(k - 1, n - i, m);
		}
		return ans;
	}

	// 方法二：改动态规划
	public static double killProbabilityDp0(int n, int m, int k) {
		if (n <= 0 || m <= 0 || k <= 0) return 0;
		long[][] dp = new long[k + 1][n + 1];
		// 第0行
		dp[0][0] = 1;  // dp[0][..] = 0
		for (int k0 = 1; k0 <= k; k0++) {
			dp[k0][0] = (long) Math.pow(m + 1, k0);
			for (int n0 = 1; n0 <= n; n0++) {
				long ans = 0;
				for (int i = 0; i <= m; i++) {
					if (n0 - i >= 0) ans += dp[k0 - 1][n0 - i];
					// 怪兽血量已经没有了，可以直接计算
					else ans += (long) Math.pow(m + 1, k0 - 1);
				}
				dp[k0][n0] = ans;
			}
		}
		return (double) dp[k][n] / Math.pow(m + 1, k);
	}

	// 方法三：优化动态规划
	public static double killProbabilityDp(int n, int m, int k) {
		if (n <= 0 || m <= 0 || k <= 0) return 0;
		long[][] dp = new long[k + 1][n + 1];
		// 第0行
		dp[0][0] = 1;  // dp[0][..] = 0
		for (int k0 = 1; k0 <= k; k0++) {
			dp[k0][0] = (long) Math.pow(m + 1, k0);
			for (int n0 = 1; n0 <= n; n0++) {
				dp[k0][n0] = dp[k0][n0 - 1]  + dp[k0 - 1][n0];
				if (n0 - 1 - m >= 0) dp[k0][n0] -= dp[k0 - 1][n0 - 1 - m];
				else dp[k0][n0] -= Math.pow(m + 1, k0 - 1);
			}
		}
		return (double) dp[k][n] / Math.pow(m + 1, k);
	}

	public static void main(String[] args) {
		int NMax = 10;
		int MMax = 10;
		int KMax = 10;
		int testTime = 200;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * NMax);
			int M = (int) (Math.random() * MMax);
			int K = (int) (Math.random() * KMax);
			double ans1 = killProbability(N, M, K);
			double ans2 = killProbabilityDp0(N, M, K);
			double ans3 = killProbabilityDp(N, M, K);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}
