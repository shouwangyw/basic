package com.yw.course.coding.class42;

/**
 * @author yangwei
 */
public class Problem_0265_PaintHouseII {

	// costs[i][k]表示i号房子粉刷成颜色k的花费
	// 0~n-1的房子相邻不同色，返回最小花费
	public static int minCostII(int[][] costs) {
		int n = costs.length;
		if (n == 0) return 0;
		int k = costs[0].length;
		// 之前取得的最小代价，以及取得最小代价时的颜色
		int preMinCost = 0, preColor = -1;
		// 之前取得的次小代价，以及取得次小代价时的颜色
		int preSecMinCost = 0, preSecColor = -1;
		for (int i = 0; i < n; i++) {
			// 当前取得的最小代价，以及取得最小代价时的颜色
			int curMinCost = Integer.MAX_VALUE, curColor = -1;
			// 当前取得的次小代价，以及取得次小代价时的颜色
			int curSecMinCost = Integer.MAX_VALUE, curSecColor = -1;
			for (int color = 0; color < k; color++) {
				if (color != preColor) { // 当前颜色不是之前最优颜色
					// 【之前最优+此时代价】比当前最优更小时：当前最优 --> 次优，最优被替换
					if (preMinCost + costs[i][color] < curMinCost) {
						curSecMinCost = curMinCost;
						curSecColor = curColor;
						curMinCost = preMinCost + costs[i][color];
						curColor = color;
					} else if (preMinCost + costs[i][color] < curSecMinCost) {
						// 【之前最优+此时代价】比当前次优更小时：仅次优被替换
						curSecMinCost = preMinCost + costs[i][color];
						curSecColor = color;
					}
				} else if (color != preSecColor) { // 当前颜色不是之前次优颜色
					// 【之前次优+此时代价】比当前最优更小时：当前最优 --> 次优，最优被替换
					if (preSecMinCost + costs[i][color] < curMinCost) {
						curSecMinCost = curMinCost;
						curSecColor = curColor;
						curMinCost = preSecMinCost + costs[i][color];
						curColor = color;
					} else if (preSecMinCost + costs[i][color] < curSecMinCost) {
						// 【之前次优+此时代价】比当前次优更小时：仅次优被替换
						curSecMinCost = preSecMinCost + costs[i][color];
						curSecColor = color;
					}
				}
			}
			// 用当前最优、次优，替换之前最优、次优，进行下一轮迭代
			preMinCost = curMinCost;
			preColor = curColor;
			preSecMinCost = curSecMinCost;
			preSecColor = curSecColor;
		}
		return preMinCost;
	}
}
