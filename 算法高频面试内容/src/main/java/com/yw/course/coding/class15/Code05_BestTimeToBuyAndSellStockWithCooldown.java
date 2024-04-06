package com.yw.course.coding.class15;

/**
 * leetcode 309
 * @author yangwei
 */
public class Code05_BestTimeToBuyAndSellStockWithCooldown {

	// 方法一：尝试
	// 该方法是对的，提交之后大量的测试用例通过，最后几个会超时
	// 如果把这个方法改成动态规划，是可以通过的，但这个尝试不是最优解
	public int maxProfit(int[] prices) {
		return maxProfit(prices, false, 0, 0);
	}
	// buy == false: 当前可以交易，而且当前没有购买行为，buy == true: 已经买了，并且买入价是buyPrice，等待卖出
	// 返回0...idx可能获得的最大收益
	private static int maxProfit(int[] prices, boolean buy, int idx, int buyPrice) {
		// base case: 来到终止位置，收益为0
		if (idx >= prices.length) return 0;
		if (buy) {
			return Math.max(
					// 1. 当前idx位置选择不卖
					maxProfit(prices, true, idx + 1, buyPrice),
					// 2. 当前idx位置选择卖，那么收益 = idx位置的卖出价 - 买入价 + 后续递归收益(只能来到 idx+2 位置，且不是买入状态)
					prices[idx] - buyPrice + maxProfit(prices, false, idx + 2, 0)
			);
		}
		return Math.max(
				// 1. 当前idx位置选择买入
				maxProfit(prices, true, idx + 1, prices[idx]),
				// 2. 当前idx位置选择不买入
				maxProfit(prices, false, idx + 1, 0)
		);
	}

	// 方法二：尝试+缓存，这个是可以通过的
	public int maxProfitByRecord(int[] prices) {
		Integer[][][] record = new Integer[2][prices.length + 2][1001];
		return maxProfit(prices, 0, 0, 0, record);
	}
	// buy == false: 当前可以交易，而且当前没有购买行为，buy == true: 已经买了，并且买入价是buyPrice，等待卖出
	// 返回0...idx可能获得的最大收益
	private static int maxProfit(int[] prices, int buy, int idx, int buyPrice, Integer[][][] record) {
		if (record[buy][idx][buyPrice] != null) return record[buy][idx][buyPrice];
		// base case: 来到终止位置，收益为0
		if (idx >= prices.length) return record[buy][idx][buyPrice] = 0;
		if (buy == 1) {
			return record[buy][idx][buyPrice] = Math.max(
					// 1. 当前idx位置选择不卖
					maxProfit(prices, 1, idx + 1, buyPrice, record),
					// 2. 当前idx位置选择卖，那么收益 = idx位置的卖出价 - 买入价 + 后续递归收益(只能来到 idx+2 位置，且不是买入状态)
					prices[idx] - buyPrice + maxProfit(prices, 0, idx + 2, 0, record)
			);
		}
		return record[buy][idx][buyPrice] = Math.max(
				// 1. 当前idx位置选择买入
				maxProfit(prices, 1, idx + 1, prices[idx], record),
				// 2. 当前idx位置选择不买入
				maxProfit(prices, 0, idx + 1, 0, record)
		);
	}

	// 方法三：动态规划
	// buy[i]: 在0...i范围上，最后一次操作是购买(buy)动作，这最后一次操作有可能发生在i位置，也可能发生在i之前
	// buy[i]值的含义是 max{ 所有可能性[之前交易获得的最大收益 - 最后buy动作的收购价格] }
	// 比如: arr[0...i]假设为[1,3,4,6,2,7,1...i之后的数字不用管]
	// 什么叫: 所有可能性[之前交易获得的最大收益 - 最后buy动作的收购价格]？
	// 比如其中一种可能性：假设最后一次buy动作发生在2这个数的时候，那么之前的交易只能在[1,3,4]上结束，因为6要cooldown的。
	// 此时最大收益是多少呢？是4-1=3。那么，之前交易获得的最大收益 - 最后buy动作的收购价格 = 3 - 2 = 1
	// 另一种可能性：再比如最后一次buy动作发生在最后的1这个数的时候，那么之前的交易只能在[1,3,4,6,2]上发生，因为7要cooldown的。
	// 此时最大收益是多少呢？是6-1=5。那么，之前交易获得的最大收益 - 最后buy动作的收购价格 = 5 - 1 = 4
	// 除了上面两种可能性之外，还有很多可能性，你可以假设每个数字都是最后buy动作的时候，
	// 所有可能性中，(之前交易获得的最大收益 - 最后buy动作的收购价格)的最大值，就是buy[i]的含义
	// 为啥buy[i]要算之前的最大收益 - 最后一次收购价格？尤其是最后为什么要减这么一下？
	// 因为这样一来，当你之后以X价格做成一笔交易的时候，当前最好的总收益直接就是 X + buy[i]了
	//
	// sell[i]: 0...i范围上，最后一次操作是卖出(sell)动作，这最后一次操作有可能发生在i位置，也可能发生在之前
	// sell[i]值的含义是0...i范围上，最后一次动作是sell的情况下，最好的收益
	//
	// 于是通过分析，能得到以下的转移方程：
	// buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i])

	// 如果i位置没有发生buy行为，说明有没有i位置都一样，那么buy[i] = buy[i-1]，这显而易见
	// 如果i位置发生了buy行为, 那么buy[i] = sell[i - 2] - prices[i]
	// 因为你想在i位置买的话，你必须保证之前交易行为发生在0...i-2上，因为如果i-1位置有可能参与交易的话，i位置就要cooldown了。
	// 而且之前交易行为必须以sell结束，你才能buy，而且要保证之前交易尽可能得到最好的利润，
	// 这正好是sell[i - 2]所代表的含义，并且根据buy[i]的定义，最后一定要 - prices[i]
	//
	// sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i])
	// 如果i位置没有发生sell行为，那么sell[i] = sell[i-1]，这显而易见
	// 如果i位置发生了sell行为，那么我们一定要找到 {之前获得尽可能好的收益 - 最后一次的收购价格尽可能低}，
	// 而这正好是buy[i - 1]的含义！之前所有的"尽可能"中，最好的一个！
	public int maxProfitByDp(int[] prices) {
		if (prices.length < 2) return 0;
		int n = prices.length;
		int[] buy = new int[n], sell = new int[n];
		// buy[0] 不需要设置，buy[0] = -prices[0]
		// sell[0] = 0
		buy[1] = Math.max(-prices[0], -prices[1]); // [1]时可以选择[0]或[1]时买入
		sell[1] = Math.max(0, prices[1] - prices[0]); // [0]时卖出收益0，[1]时卖出则必然是[0]时买入
		for (int i = 2; i < n; i++) {
			// i时不买入 = buy[i - 1]，i时买入 = i-2时已经卖出 - i时买入价
			buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);
			// i时不卖出 = sell[i - 1]，i时卖出 = i-1时已经买入 + i时卖出价
			sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
		}
		// 返回n-1时卖出时最高收益
		return sell[n - 1];
	}

	// 方法四：最优解就是方法2的空间优化而已
	public int maxProfitOptimal(int[] prices) {
		if (prices.length < 2) return 0;
		int buy1 = Math.max(-prices[0], -prices[1]), sell1 = Math.max(0, prices[1] - prices[0]), sell2 = 0;
		for (int i = 2; i < prices.length; i++) {
			int tmp = sell1;
			sell1 = Math.max(sell1, buy1 + prices[i]);
			buy1 = Math.max(buy1, sell2 - prices[i]);
			sell2 = tmp;
		}
		return sell1;
	}

}
