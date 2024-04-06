package com.yw.course.coding.class15;

/**
 * leetcode 188
 * @author yangwei
 */
public class Code04_BestTimeToBuyAndSellStockIV {

	public static int maxProfit0(int K, int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int N = prices.length;
		if (K >= N / 2) {
			return allTrans(prices);
		}
		int[][] dp = new int[K + 1][N];
		int ans = 0;
		for (int tran = 1; tran <= K; tran++) {
			int pre = dp[tran][0];
			int best = pre - prices[0];
			for (int index = 1; index < N; index++) {
				pre = dp[tran - 1][index];
				dp[tran][index] = Math.max(dp[tran][index - 1], prices[index] + best);
				best = Math.max(best, pre - prices[index]);
				ans = Math.max(dp[tran][index], ans);
			}
		}
		return ans;
	}

	public static int allTrans(int[] prices) {
		int ans = 0;
		for (int i = 1; i < prices.length; i++) ans += Math.max(prices[i] - prices[i - 1], 0);
		return ans;
	}

	public int maxProfit(int k, int[] prices) {
		if (prices == null || prices.length <= 1 || k < 1) return 0;
		int n = prices.length;
		if (k >= n / 2) return maxProfit(prices);
		// dp[i][j]: 表示0...i范围，最多完成j次交易，所能获得的最大收益
		int[][] dp = new int[n][k + 1];
		// 初始化dp
		// dp[...][0] = 0: 0次交易，收益都是0
		// dp[0][...] = 0: 0...0范围进行k次交易，收益也都是0
		// 普遍位置 填表: 从左往右(列是交易次数)、从上往下(行是时间范围)
		for (int j = 1; j <= k; j++) {
			// 先计算dp[1][j]，然后一次推导出 dp[2][j]、dp[3][j]、...
			// int p2 = dp[1][j - 1] + prices[1] - prices[1], p3 = dp[0][j - 1] + prices[1] - prices[0];
			// Math.max(p2, p3) = prices[1] + Math.max(dp[1][j - 1] - prices[1], dp[0][j - 1] - prices[0])
			int best = Math.max(dp[1][j - 1] - prices[1], dp[0][j - 1] - prices[0]);
			dp[1][j] = Math.max(dp[0][j], best + prices[1]);
			for (int i = 2; i < n; i++) {
				best = Math.max(dp[i][j - 1] - prices[i], best);
				dp[i][j] = Math.max(dp[i - 1][j], best + prices[i]);
			}
		}
		// 最后，右下角的值就是最终答案
		return dp[n - 1][k];
	}
	// 不限交易次数时，获得的最大收益问题
	private int maxProfit(int[] prices) {
		int ans = 0;
		for (int i = 1; i < prices.length; i++) ans += Math.max(0, prices[i] - prices[i - 1]);
		return ans;
	}

}
