package com.yw.course.coding.class10;

/**
 * 测试链接 : https://leetcode.cn/problems/k-inverse-pairs-array/
 * @author yangwei
 */
public class Code03_KInversePairs {

	// 样本对应模型
	// 根据提示的数据量，可以猜时间复杂度 O(n*k = 10^6) 可以过，定义dp[i][j]
	// dp[i][j]: i个数恰好拥有j个逆序对，这样的情况有几个
	public int kInversePairs(int n, int k) {
		int[][] dp = new int[n + 1][k + 1];
		// 初始化dp，第0列即恰好拥有0个逆序对，只有1种
		for (int i = 0; i <= n; i++) dp[i][0] = 1;
		// 分析普遍位置，i > j 时，举一个具体的例子 dp[5][3]，可能来自以下情况
		// 1) dp[4][3]: abcd5
		// 2) dp[4][2]: abc5d
		// 3) dp[4][1]: ab5cd
		// 4) dp[4][0]: a5bcd
		// 即 dp[5][3] = 累加 dp[4][3...0]
		// 斜率优化，分析 dp[5][4] = 累加 dp[4][4...0] = dp[4][4] + 累加 dp[4][3...0] = dp[4][4] = dp[5][3]
		// ∴ i > j 时，dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
		// 当 j >= i 时，举一个具体的例子 dp[5][7] = 累加 dp[4][7...3]
		// dp[5][8] = 累加 dp[4][8...4] = dp[4][8] + dp[5][7] - dp[4][3]
		// ∴ j >= i 时，dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - i]
		int mod = 1000000007;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= k; j++) {
				dp[i][j] = (dp[i - 1][j] + dp[i][j - 1]) % mod;
				if (j >= i) dp[i][j] = (dp[i][j] - dp[i - 1][j - i] + mod) % mod;
			}
		}
		return dp[n][k];
	}

	public static int kInversePairs2(int n, int k) {
		if (n < 1 || k < 0) {
			return 0;
		}
		int[][] dp = new int[n + 1][k + 1];
		dp[0][0] = 1;
		for (int i = 1; i <= n; i++) {
			dp[i][0] = 1;
			for (int j = 1; j <= k; j++) {
				dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
				if (j >= i) {
					dp[i][j] -= dp[i - 1][j - i];
				}
			}
		}
		return dp[n][k];
	}

}
