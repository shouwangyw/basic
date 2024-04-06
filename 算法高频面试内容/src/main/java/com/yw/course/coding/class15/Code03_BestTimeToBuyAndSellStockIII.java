package com.yw.course.coding.class15;

/**
 * leetcode 123
 * @author yangwei
 */
public class Code03_BestTimeToBuyAndSellStockIII {

	public int maxProfit(int[] prices) {
		if (prices == null || prices.length <= 1) return 0;
		int n = prices.length;
		// dp[i][j]: 0...i范围进行j次交易，所获得的最大收益
		int[][] dp = new int[n][3];
		// dp[...][0] = 0, dp[0][...] = 0
		for (int j = 1; j <= 2; j++) {
			int best = Math.max(dp[1][j - 1] - prices[1], dp[0][j - 1] - prices[0]);
			dp[1][j] = Math.max(dp[0][j], best + prices[1]);
			for (int i = 2; i < n; i++) {
				best = Math.max(dp[i][j - 1] - prices[i], best);
				dp[i][j] = Math.max(dp[i - 1][j], best + prices[i]);
			}
		}
		return dp[n - 1][2];
	}

}
