package com.yw.course.coding.class15;

/**
 * leetcode 121
 * @author yangwei
 */
public class Code01_BestTimeToBuyAndSellStock {

	// 方法一：
	public int maxProfit0(int[] prices) {
		if (prices == null || prices.length <= 1) return 0;
		int minPrice = Integer.MAX_VALUE, maxProfit = 0;
		for (int i = 0; i < prices.length; i++) {
			if (prices[i] < minPrice) minPrice = prices[i];
			else if (prices[i] - minPrice > maxProfit) maxProfit = prices[i] - minPrice;
		}
		return maxProfit;
	}

	// 方法二：
	public int maxProfit(int[] prices) {
		if (prices == null || prices.length <= 1) return 0;
		// 考虑在i时刻卖出，所获得的最大收益 = i时刻股票价格 - i之前的最小股票价格
		int ans = 0, min = prices[0];
		for (int i = 1; i < prices.length; i++) {
			min = Math.min(min, prices[i]);
			ans = Math.max(ans, prices[i] - min);
		}
		return ans;
	}

}
