package com.yw.course.coding.class15;

/**
 * leetcode 714
 * @author yangwei
 */
public class Code06_BestTimeToBuyAndSellStockWithTransactionFee {

	public int maxProfit(int[] prices, int fee) {
		if (prices == null || prices.length <= 1) return 0;
		int n = prices.length;
		int bestBuy = -prices[0] - fee, bestSell = 0;
		for (int i = 1; i < n; i++) {
			// 来到i位置，若必须买入，收入 - 批发价 - fee
			int curBuy = bestSell - prices[i] - fee;
			// 来到i位置，若必须卖出，整体最优(收入 - 良好批发价 - fee)
			int curSell = bestBuy + prices[i];
			bestBuy = Math.max(bestBuy, curBuy);
			bestSell = Math.max(bestSell, curSell);
		}
		return bestSell;
	}

}
