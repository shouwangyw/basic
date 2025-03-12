package com.yw.course.coding.class43;

import java.util.Arrays;

import static com.yw.util.CommonUtils.copyArray;
import static com.yw.util.CommonUtils.randomArray;

/**
 * 来自360笔试
 * 给定一个正数数组arr，长度为n，下标0~n-1
 * arr中的0、n-1位置不需要达标，它们分别是最左、最右的位置
 * 中间位置i需要达标，达标的条件是 : arr[i-1] > arr[i] 或者 arr[i+1] > arr[i]哪个都可以
 * 你每一步可以进行如下操作：对任何位置的数让其-1
 * 你的目的是让arr[1~n-2]都达标，这时arr称之为yeah！数组
 * 返回至少要多少步可以让arr变成yeah！数组
 * 数据规模 : 数组长度 <= 10000，数组中的值<=500
 *
 * @author yangwei
 */
public class Code02_MinCostToYeahArray {

	public static final int INVALID = Integer.MAX_VALUE;

	// 纯暴力方法，只是为了结果对
	// 时间复杂度极差
	public static int minCost0(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int n = arr.length;
		int min = INVALID;
		for (int num : arr) {
			min = Math.min(min, num);
		}
		int base = min - n;
		return process0(arr, base, 0);
	}

	public static int process0(int[] arr, int base, int index) {
		if (index == arr.length) {
			for (int i = 1; i < arr.length - 1; i++) {
				if (arr[i - 1] <= arr[i] && arr[i] >= arr[i + 1]) {
					return INVALID;
				}
			}
			return 0;
		} else {
			int ans = INVALID;
			int tmp = arr[index];
			for (int cost = 0; arr[index] >= base; cost++, arr[index]--) {
				int next = process0(arr, base, index + 1);
				if (next != INVALID) {
					ans = Math.min(ans, cost + next);
				}
			}
			arr[index] = tmp;
			return ans;
		}
	}

	// 暴力递归尝试，从左往右的尝试模型
	public static int minCost(int[] arr) {
		// 若数组长度不足3，不需要调整，代价0
		if (arr == null || arr.length < 3) return 0;
		// 根据题意找限制条件：为满足题意，考虑该数组中可能被调整最低下限
		// 首先，所有数都减小至数组中的最小值min；然后，min还可以再减(n-1)次，即最低下限是 min-(n-1)
		// 最后，为了方便，为了使得不管怎么调整，数组下限要大于等于0，那么需要将每个数加上 (n-min)
		int min = Integer.MAX_VALUE, n = arr.length;
		for (int a : arr) min = Math.min(min, a);
		for (int i = 0; i < n; i++) arr[i] += n - min;
		return process(arr, 1, arr[0], true);
	}
	// 当前来到idx位置，前一个位置的值是pre，preOk前一个位置的值是否被它左边的数变有效
	// 返回让arr都变有效的最小代价是什么?
	private static int process(int[] arr, int idx, int pre, boolean preOk) {
		// 已经是最后一个数了，则要么前一个数已经变有效，要么当前数能是pre变有效，否则搞不定
		if (idx == arr.length - 1) return preOk || pre < arr[idx] ? 0 : Integer.MAX_VALUE;
		// 当前不是最后一个数
		int cost = Integer.MAX_VALUE;
		// 1. 前一个数已经有效: 枚举当前数arr[idx]所有可能情况 [0, arr[idx]]，是这个范围原因是前面已经对数组进行了调整
		// 2. 前一个数没变有效，意味着当前数减小后一定不能小于等于pre，即大于等于pre+1 > pre
		for (int cur = arr[idx]; cur >= (preOk ? 0 : pre + 1); cur--) {
			int next = process(arr, idx + 1, cur, cur < pre);
			// 往下调递归，若能搞定，则更新最小代价
			if (next != Integer.MAX_VALUE)
				cost = Math.min(cost, arr[idx] - cur + next);
		}
		return cost;
	}

	// 基于递归尝试的方法改动态规划，三个可变参 idx、pre、preOk
	public static int minCostByDp(int[] arr)  {
		if (arr == null || arr.length < 3) return 0;
		int min = Integer.MAX_VALUE, n = arr.length;
		for (int a : arr) min = Math.min(min, a);
		for (int i = 0; i < n; i++) arr[i] += n - min;
		int[][][] dp = new int[n][2][]; // 三维数组，分别对应 idx、preOk、pre
		// 初始化dp表
		for (int i = 1; i < n; i++) {
			dp[i][0] = new int[arr[i - 1] + 1];
			dp[i][1] = new int[arr[i - 1] + 1];
			Arrays.fill(dp[i][0], Integer.MAX_VALUE);
			Arrays.fill(dp[i][1], Integer.MAX_VALUE);
		}
		for (int pre = 0; pre <= arr[n - 2]; pre++) {
			dp[n - 1][0][pre] = pre < arr[n - 1] ? 0 : Integer.MAX_VALUE;
			dp[n - 1][1][pre] = 0;
		}
		for (int idx = n - 2; idx >= 1; idx--) {
			for (int pre = 0; pre <= arr[idx - 1]; pre++) {
				for (int cur = arr[idx]; cur >= pre + 1; cur--) {
					int next = dp[idx + 1][cur < pre ? 1 : 0][cur];
					if (next != Integer.MAX_VALUE)
						dp[idx][0][pre] = Math.min(dp[idx][0][pre], arr[idx] - cur + next);
				}
				for (int cur = arr[idx]; cur >= 0; cur--) {
					int next = dp[idx + 1][cur < pre ? 1 : 0][cur];
					if (next != Integer.MAX_VALUE)
						dp[idx][1][pre] = Math.min(dp[idx][1][pre], arr[idx] - cur + next);
				}
			}
		}
		return dp[1][1][arr[0]];
	}

	// 改出的这个版本，需要一些技巧，但很可惜不是最优解，虽然不是最优解，也足以通过100%的case了，
	// 这种技法的练习，非常有意义
	// 优化 minCostByDp 中的枚举
	public static int minCostByOptimalDp(int[] arr) {
		if (arr == null || arr.length < 3) return 0;
		int min = Integer.MAX_VALUE, n = arr.length;
		for (int a : arr) min = Math.min(min, a);
		for (int i = 0; i < n; i++) arr[i] += n - min;
		int[][][] dp = new int[n][2][]; // 三维数组，分别对应 idx、preOk、pre
		// 初始化dp表
		for (int i = 1; i < n; i++) {
			dp[i][0] = new int[arr[i - 1] + 1];
			dp[i][1] = new int[arr[i - 1] + 1];
		}
		for (int pre = 0; pre <= arr[n - 2]; pre++)
			dp[n - 1][0][pre] = pre < arr[n - 1] ? 0 : Integer.MAX_VALUE;
		// 预处理best数组
		int[][] best = best(dp, n - 1, arr[n - 2]);
		for (int i = n - 2; i >= 1; i--) {
			for (int p = 0; p <= arr[i - 1]; p++) {
				if (arr[i] < p) dp[i][1][p] = best[1][arr[i]];
				else dp[i][1][p] = Math.min(best[0][p], p > 0 ? best[1][p - 1] : Integer.MAX_VALUE);
				dp[i][0][p] = arr[i] <= p ? Integer.MAX_VALUE : best[0][p + 1];
			}
			best = best(dp, i, arr[i - 1]);
		}
		return dp[1][1][arr[0]];
	}
	private static int[][] best(int[][][] dp, int i, int v) {
		int[][] best = new int[2][v + 1];
		best[0][v] = dp[i][0][v];
		for (int p = v - 1; p >= 0; p--) {
			best[0][p] = dp[i][0][p] == Integer.MAX_VALUE ? Integer.MAX_VALUE : v - p + dp[i][0][p];
			best[0][p] = Math.min(best[0][p], best[0][p + 1]);
		}
		best[1][0] = dp[i][1][0] == Integer.MAX_VALUE ? Integer.MAX_VALUE : v + dp[i][1][0];
		for (int p = 1; p <= v; p++) {
			best[1][p] = dp[i][1][p] == Integer.MAX_VALUE ? Integer.MAX_VALUE : v - p + dp[i][1][p];
			best[1][p] = Math.min(best[1][p], best[1][p - 1]);
		}
		return best;
	}

	// 请注意，重点看上面的方法，这个最优解容易理解，但让你学到的东西不是很多
	// 最优解-贪心，时间复杂度O(N)
	public static int minCostOfYeah(int[] arr) {
		if (arr == null || arr.length < 3) return 0;
		int n = arr.length;
		int[] nums = new int[n + 2];
		nums[0] = nums[n + 1] = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) nums[i + 1] = arr[i];
		int[] leftCost = new int[n + 2], rightCost = new int[n + 2];
		// 1. 从左往右，计算每个位置的代价
		for (int i = 1, pre = nums[0]; i <= n; i++) {
			// [i]位置代价 = ([i]值 - min(pre值-1, [i]值))+ [i-1]位置代价
			int change = Math.min(pre - 1, nums[i]);
			leftCost[i] = nums[i] - change + leftCost[i - 1];
			pre = change;
		}
		// 2. 从右往左，计算每个位置的代价
		for (int i = n, pre = nums[n + 1]; i >= 1; i--) {
			// [i]位置代价 = ([i]值 - min(pre值-1, [i]值))+ [i+1]位置代价
			int change = Math.min(pre - 1, nums[i]);
			rightCost[i] = nums[i] - change + rightCost[i + 1];
			pre = change;
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 1; i <= n; i++)
			ans = Math.min(ans, leftCost[i] + rightCost[i + 1]);
		return ans;
	}

	public static void main(String[] args) {
		int len = 7;
		int v = 10;
		int testTime = 100;
		System.out.println("==========");
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 1;
			int[] arr = randomArray(n, v);
			int[] arr0 = copyArray(arr);
			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int[] arr3 = copyArray(arr);
			int[] arr4 = copyArray(arr);
			int ans0 = minCost0(arr0);
			int ans1 = minCost(arr1);
			int ans2 = minCostByDp(arr2);
			int ans3 = minCostByOptimalDp(arr3);
			int ans4 = minCostOfYeah(arr4);
			if (ans0 != ans1 || ans0 != ans2 || ans0 != ans3 || ans0 != ans4) {
				System.out.println("出错了！ans0 = " + ans0 + ", ans1 = " + ans1 + ", ans2 = " + ans2 + ", ans3 = " + ans3 + ", ans4 = " + ans4);
				break;
			}
		}
		System.out.println("功能测试结束");
		System.out.println("==========");

		System.out.println("性能测试开始");

		len = 10000;
		v = 500;
		System.out.println("生成随机数组长度：" + len);
		System.out.println("生成随机数组值的范围：[1, " + v + "]");
		int[] arr = randomArray(len, v);
		int[] arr3 = copyArray(arr);
		int[] arrYeah = copyArray(arr);
		long start;
		long end;
		start = System.currentTimeMillis();
		int ans3 = minCostByOptimalDp(arr3);
		end = System.currentTimeMillis();
		System.out.println("minCost3方法:");
		System.out.println("运行结果: " + ans3 + ", 时间(毫秒) : " + (end - start));

		start = System.currentTimeMillis();
		int ansYeah = minCostOfYeah(arrYeah);
		end = System.currentTimeMillis();
		System.out.println("yeah方法:");
		System.out.println("运行结果: " + ansYeah + ", 时间(毫秒) : " + (end - start));

		System.out.println("性能测试结束");
		System.out.println("==========");

	}
}
