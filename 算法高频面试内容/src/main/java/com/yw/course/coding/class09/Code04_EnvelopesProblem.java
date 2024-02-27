package com.yw.course.coding.class09;

import java.util.Arrays;

/**
 * 测试链接 : https://leetcode.cn/problems/russian-doll-envelopes/
 * @author yangwei
 */
public class Code04_EnvelopesProblem {

	public int maxEnvelopes(int[][] envelopes) {
		// 先按宽度排序，宽度相等时按高度从大到小排序
		Arrays.sort(envelopes, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]);
		// 对排序后的数组求高度的最长递增子序列，就是最终答案
		int n = envelopes.length;
		int[] ends = new int[n];
		ends[0] = envelopes[0][1];
		int l, r, mid, right = 0;
		for (int i = 1; i < n; i++) {
			l = 0;
			r = right;
			while (l <= r) {
				mid = (l + r) / 2;
				if (envelopes[i][1] > ends[mid]) l = mid + 1;
				else r = mid - 1;
			}
			right = Math.max(right, l);
			ends[l] = envelopes[i][1];
		}
		return right + 1;
	}
}
