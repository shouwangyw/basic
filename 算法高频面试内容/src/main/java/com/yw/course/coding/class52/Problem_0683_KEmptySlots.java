package com.yw.course.coding.class52;

/**
 * @author yangwei
 */
public class Problem_0683_KEmptySlots {

	public static int kEmptySlots1(int[] bulbs, int k) {
		int n = bulbs.length;
		int[] days = new int[n];
		for (int i = 0; i < n; i++) {
			days[bulbs[i] - 1] = i + 1;
		}
		int ans = Integer.MAX_VALUE;
		if (k == 0) {
			for (int i = 1; i < n; i++) {
				ans = Math.min(ans, Math.max(days[i - 1], days[i]));
			}
		} else {
			int[] minq = new int[n];
			int l = 0;
			int r = -1;
			for (int i = 1; i < n && i < k; i++) {
				while (l <= r && days[minq[r]] >= days[i]) {
					r--;
				}
				minq[++r] = i;
			}
			for (int i = 1, j = k; j < n - 1; i++, j++) {
				while (l <= r && days[minq[r]] >= days[j]) {
					r--;
				}
				minq[++r] = j;
				int cur = Math.max(days[i - 1], days[j + 1]);
				if (days[minq[l]] > cur) {
					ans = Math.min(ans, cur);
				}
				if (i == minq[l]) {
					l++;
				}
			}
		}
		return (ans == Integer.MAX_VALUE) ? -1 : ans;
	}

	public static int kEmptySlots(int[] bulbs, int k) {
		int n = bulbs.length;
		// 数组转换，days[i]表示位置i的灯在那一天亮
		int[] days = new int[n];
		for (int i = 0; i < n; i++) days[bulbs[i] - 1] = i + 1;
		int ans = Integer.MAX_VALUE;
		// 维护一个固定宽度的滑动窗口[left, right]
		int left = 0, mid = 1, right = k + 1;
		while (right < n) {
			// 验证指针mid，mid 永远不和left撞上的！
			// 1) mid在left和right中间验证的时候，没通过！
			// 2) mid是撞上right的时候
			if (days[mid] <= Math.max(days[left], days[right])) {
				if (mid == right) {
					// 收答案！
					ans = Math.min(ans, Math.max(days[left], days[right]));
				}
				left = mid;
				right = mid + k + 1;
			}
			mid++;
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

}
