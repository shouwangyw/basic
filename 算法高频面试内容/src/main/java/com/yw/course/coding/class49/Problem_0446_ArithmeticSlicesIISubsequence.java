package com.yw.course.coding.class49;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Problem_0446_ArithmeticSlicesIISubsequence {

	// 时间复杂度是O(N^2)，最优解的时间复杂度
	public int numberOfArithmeticSlices(int[] nums) {
		int n = nums.length, ans = 0;
		// maps[i]表示以[i]位置数结尾的序列中，差值(key)有几个(val)
		Map<Integer, Integer>[] maps = new HashMap[n];
		for (int i = 0; i < n; i++) {
			maps[i] = new HashMap<>();
			for (int j = i - 1; j >= 0; j--) {
				long diff = (long) nums[i] - (long) nums[j];
				if (diff <= Integer.MIN_VALUE || diff > Integer.MAX_VALUE) continue;
				int dif = (int) diff;
				int cnt = maps[j].getOrDefault(dif, 0);
				ans += cnt;
				maps[i].compute(dif, (k, v) -> (v == null ? 0 : v) + cnt + 1);
			}
		}
		return ans;
	}
}
