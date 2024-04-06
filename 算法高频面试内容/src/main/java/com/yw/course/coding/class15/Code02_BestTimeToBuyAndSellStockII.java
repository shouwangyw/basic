package com.yw.course.coding.class15;

/**
 * leetcode 122
 * @author yangwei
 */
public class Code02_BestTimeToBuyAndSellStockII {
	// 方法一：
	public int maxProfit(int[] prices) {
		if (prices == null || prices.length <= 1) return 0;
		int profit = 0;
		for (int i = 1; i < prices.length; i++) {
			// 相邻时刻算一个股票差值: 为负数收益为0，为正数累加收益
			profit += Math.max(0, prices[i] - prices[i - 1]);
		}
		return profit;
	}

	// 方法二：
	public int maxProfit2(int[] prices) {
		if (prices == null || prices.length <= 1) return 0;
		// profit: 总收益，minPrice: i时刻之前的最低股价
		int profit = 0, minPrice = prices[0];
		for (int i = 1; i < prices.length; i++) {
			// 股价下跌，更新最低股价，不考虑卖出
			if (prices[i] <= minPrice) minPrice = prices[i];
			else {
				// 否则，先卖出获得收益，累加进总收益中
				profit += prices[i] - minPrice;
				// 然后继续买入当前股票，更新最低股价为当前股票价格
				minPrice = prices[i];
			}
		}
		return profit;
	}

	// 方法三：动态规划
	public int maxProfit3(int[] prices) {
		if (prices == null || prices.length <= 1) return 0;
		// dp[i][0]: 第i天不持股的最大收益，dp[i][1]: 第i天持股的最大收益
		int n = prices.length;
		int[][] dp = new int[2][2]; // 滚动数组
		dp[0][0] = 0;
		dp[0][1] = -prices[0];
		for (int i = 1; i < n; i++) {
			int cur = i % 2, pre = cur == 1 ? 0 : 1;
			// 第i天不持股的最大收益 = max{前一天不持股最大收益, 前一天持股最大收益 + 卖出(当前股价)}
			dp[cur][0] = Math.max(dp[pre][0], dp[pre][1] + prices[i]);
			// 第i天持股的最大收益 = max{前一天不持股最大收益 - 买入(当前股价), 前一天持股最大收益}
			dp[cur][1] = Math.max(dp[pre][0] - prices[i], dp[pre][1]);
		}
		return Math.max(dp[(n - 1) % 2][0], dp[(n - 1) % 2][1]);
	}
}
