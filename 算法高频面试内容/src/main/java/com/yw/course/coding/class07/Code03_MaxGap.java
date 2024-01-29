package com.yw.course.coding.class07;

/**
 * 测试链接 : https://leetcode.cn/problems/maximum-gap/
 * @author yangwei
 */
public class Code03_MaxGap {

	public int maximumGap(int[] nums) {
		if (nums == null || nums.length < 2) return 0;
		int n = nums.length, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for (int x : nums) {
			min = Math.min(min, x);
			max = Math.max(max, x);
		}
		if (min == max) return 0; // 说明只有一种数
		// 不止一种数，min~max之间一定有range，n个数准备n+1个桶，
		// bucket[i][0]==0表示该桶没进来过数、bucket[i][0]==1表示该桶进来过数，bucket[i][1]: 表示第i号桶中最大值，bucket[i][2]: 表示第i号桶中最小值
		int[][] bucket = new int[n + 1][3];
		for (int x : nums) {
			// 计算当前数字进几号桶
			int idx = idxOf(x, n, min, max);
			System.out.println("idx = " + idx);
			// 设置更新idx号桶的最大最小值
			bucket[idx][1] = bucket[idx][0] == 1 ? Math.max(bucket[idx][1], x) : x;
			bucket[idx][2] = bucket[idx][0] == 1 ? Math.min(bucket[idx][2], x) : x;
			bucket[idx][0] = 1;
		}
		// 依次考查相邻非空桶之间最大与最小数（相邻数）的差值，得到最终答案
		int ans = 0, lastMax = bucket[0][1], i = 1;
		for (; i <= n; i++) {
			if (bucket[i][0] == 1) {
				ans = Math.max(ans, bucket[i][2] - lastMax);
				lastMax = bucket[i][1];
			}
		}
		return ans;
	}
	// 计算数字num应该进几号桶
	private static int idxOf(long x, int n, long min, long max) {
		return (int) ((x - min) * n / (max - min));
	}

}
