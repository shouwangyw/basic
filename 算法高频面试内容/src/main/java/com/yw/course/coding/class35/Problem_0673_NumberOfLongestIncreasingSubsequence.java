package com.yw.course.coding.class35;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author yangwei
 */
public class Problem_0673_NumberOfLongestIncreasingSubsequence {

	// 好理解的方法，时间复杂度O(N^2)
	public int findNumberOfLIS(int[] nums) {
		int ans = 0, n = nums.length, maxLen = 0;
		// dp[i]: 表示以nums[i]结尾的最长递增子序列，cnt[i]: 表示以nums[i]结尾的最长递增子序列个数
		int[] dp = new int[n], cnt = new int[n];
		for (int i = 0; i < n; i++) {
			dp[i] = cnt[i] = 1;
			// 遍历j...i范围
			for (int j = 0; j < i; j++) {
				if (nums[j] >= nums[i]) continue; // 过滤掉非递增
				// cnt[i]等于所有满足 dp[j]+1=dp[i] 的 cnt[j] 之和
				if (dp[j] + 1 == dp[i]) cnt[i] += cnt[j];
				else if (dp[j] + 1 > dp[i]) {
					dp[i] = dp[j] + 1;
					cnt[i] = cnt[j]; // 重置计数
				}
			}
			if (dp[i] == maxLen) ans += cnt[i];
			else if (dp[i] > maxLen) {
				maxLen = dp[i];
				ans = cnt[i]; // 重置计数
			}
		}
		return ans;
	}

	// 优化后的最优解，时间复杂度O(N*logN)
	public int findNumberOfLIS2(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		List<TreeMap<Integer, Integer>> dp = new ArrayList<>();
		int len, cnt;
		for (int x : nums) {
			// len: x 之前的长度，len+1: x到哪个长度
			len = search(dp, x);
			// cnt: 最终要加的记录
			if (len == 0) cnt = 1;
			else {
				TreeMap<Integer, Integer> p = dp.get(len - 1);
				cnt = p.firstEntry().getValue() - (p.ceilingKey(x) != null ? p.get(p.ceilingKey(x)) : 0);
			}
			if (len == dp.size()) {
				dp.add(new TreeMap<>());
				dp.get(len).put(x, cnt);
			} else dp.get(len).put(x, dp.get(len).firstEntry().getValue() + cnt);
		}
		return dp.get(dp.size() - 1).firstEntry().getValue();
	}
	// 二分查找，返回>=x最左的位置
	private static int search(List<TreeMap<Integer, Integer>> dp, int x) {
		int ans = dp.size(), l = 0, r = dp.size() - 1, mid;
		while (l <= r) {
			mid = (l + r) / 2;
			if (dp.get(mid).firstKey() < x) l = mid + 1;
			else {
				ans = mid;
				r = mid - 1;
			}
		}
		return ans;
	}

}
