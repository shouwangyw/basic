package com.yw.course.advance.class05;

/**
 * 测试链接：https://leetcode.cn/problems/count-of-range-sum/
 * @author yangwei
 */
public class Code01_CountOfRangeSum {

	public static int countRangeSum(int[] nums, int lower, int upper) {
		if (nums == null || nums.length == 0) return 0;
		int n = nums.length;
		// long: 避免求前缀和数组 数据越界; n + 1: 避免边界0，前缀和 [1,n]
		long[] sums = new long[n + 1];
		for (int i = 0; i < n; i++) sums[i + 1] = sums[i] + nums[i];
		return mergeSort(sums, 0, n, lower, upper);
	}

	private static int mergeSort(long[] sums, int l, int r, int lower, int upper) {
		if (l == r) return 0;
		int mid = l + ((r - l) >> 1);
		int lRangeSum = mergeSort(sums, l, mid, lower, upper);
		int rRangeSum = mergeSort(sums, mid + 1, r, lower, upper);
		// ================== 收集区间和个数 start ===================
		int rangeSum = 0, wl = l, wr = l;
		for (int i = mid + 1; i <= r; i++) {
			while (wl <= mid && sums[wl] < sums[i] - upper) wl++;
			while (wr <= mid && sums[wr] <= sums[i] - lower) wr++;
			rangeSum += wr - wl;
		}
		// ================== 收集区间和个数 end ===================
		long[] tmp = new long[r - l + 1];
		int i = l, j = mid + 1, k = 0;
		while (i <= mid && j <= r) tmp[k++] = sums[i] < sums[j] ? sums[i++] : sums[j++];
		while (i <= mid) tmp[k++] = sums[i++];
		while (j <= r) tmp[k++] = sums[j++];
		for (k = 0; k < tmp.length; k++) sums[l + k] = tmp[k];
		return rangeSum + lRangeSum + rRangeSum;
	}

	public static int countRangeSum2(int[] nums, int lower, int upper) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		long[] sum = new long[nums.length];
		sum[0] = nums[0];
		for (int i = 1; i < nums.length; i++) {
			sum[i] = sum[i - 1] + nums[i];
		}
		return process(sum, 0, sum.length - 1, lower, upper);
	}

	public static int process(long[] sum, int L, int R, int lower, int upper) {
		if (L == R) {
			return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
		}
		int M = L + ((R - L) >> 1);
		return process(sum, L, M, lower, upper) + process(sum, M + 1, R, lower, upper)
				+ merge(sum, L, M, R, lower, upper);
	}

	public static int merge(long[] arr, int L, int M, int R, int lower, int upper) {
		int ans = 0;
		int windowL = L;
		int windowR = L;
		// [windowL, windowR)
		for (int i = M + 1; i <= R; i++) {
			long min = arr[i] - upper;
			long max = arr[i] - lower;
			while (windowR <= M && arr[windowR] <= max) {
				windowR++;
			}
			while (windowL <= M && arr[windowL] < min) {
				windowL++;
			}
			ans += windowR - windowL;
		}
		long[] help = new long[R - L + 1];
		int i = 0;
		int p1 = L;
		int p2 = M + 1;
		while (p1 <= M && p2 <= R) {
			help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
		}
		while (p1 <= M) {
			help[i++] = arr[p1++];
		}
		while (p2 <= R) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}
		return ans;
	}

}
