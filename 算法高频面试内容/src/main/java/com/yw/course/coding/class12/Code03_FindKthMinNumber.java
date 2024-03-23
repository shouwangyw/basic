package com.yw.course.coding.class12;

/**
 * 测试链接 : https://leetcode.cn/problems/median-of-two-sorted-arrays/
 * @author yangwei
 */
public class Code03_FindKthMinNumber {

	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int n1 = nums1.length, n2 = nums2.length, n = n1 + n2;
		boolean even = (n & 1) == 0;
		if (n1 != 0 && n2 != 0) {
			if (even) return (double) (findKthNum(nums1, nums2, n / 2) + findKthNum(nums1, nums2, n / 2 + 1)) / 2D;
			else return findKthNum(nums1, nums2, n / 2 + 1);
		} else if (n1 != 0) {
			if (even) return (double) (nums1[(n - 1) / 2] + nums1[n / 2]) / 2D;
			else return nums1[n / 2];
		} else if (n2 != 0) {
			if (even) return (double) (nums2[(n - 1) / 2] + nums2[n / 2]) / 2D;
			else return nums2[n / 2];
		} else return 0;
	}
	// 在两个有序数组中，找整体第k小的数，时间复杂度 O(log(min(M, N)))
	private static int findKthNum(int[] nums1, int[] nums2, int k) {
		// 重新定义长短数组
		int n1 = nums1.length, n2 = nums2.length;
		int[] longs = n1 >= n2 ? nums1 : nums2, shorts = n1 < n2 ? nums1 : nums2;
		int l = longs.length, s = shorts.length;
		// 情况一：k <= s，分别取两个数组的前 k 位等长数组区间，直接调用 `getUpMedian` 求上中位数
		if (k <= s) return getUpMedian(shorts, 0, k - 1, longs, 0, k - 1);
		// 情况二：k > l
		if (k > l) {
			// 淘汰掉短数组的前 `k-l` 个数
			if (shorts[k - l - 1] >= longs[l - 1]) return shorts[k - l - 1];
			// 淘汰掉长数组的前 `k-s` 个数
			if (longs[k - s - 1] >= shorts[s - 1]) return longs[k - s - 1];
			// 剩下长数组有 `l+s-k` 个数，和短数组的 `s+l-k` 个数(刚好等长)求上中位数
			// 求出的上中位数是 `l+s-k` ，再加上之前淘汰掉的结果正好是 `k` 个数
			return getUpMedian(shorts, k - l, s - 1, longs, k - s, l - 1);
		}
		// 情况三：s < k <= l
		if (longs[k - s - 1] >= shorts[s - 1]) return longs[k - s - 1];
		return getUpMedian(shorts, 0, s - 1, longs, k - s, k - 1);
	}
	// nums1[s1...e1] 与 nums2[s2...e2] 一定等长，返回整体的中位数
	private static int getUpMedian(int[] nums1, int s1, int e1, int[] nums2, int s2, int e2) {
		while (s1 < e1) {
			// 计算两个数组的中位数位置
			int mid1 = (s1 + e1) / 2, mid2 = (s2 + e2) / 2;
			// 1. 中位数相等直接返回
			if (nums1[mid1] == nums2[mid2]) return nums1[mid1];
			// 2. 两数组中位数不相等，分奇偶讨论
			if (((e1 - s1 + 1) & 1) == 1) { // 奇数长度
				// 则取[中位数]较大的数组中[中位数]的前一位 a，与[中位数]较小的数组的[中位数] b 再比较一次
				// 若 a <= b，则直接返回 b；否则，淘汰掉[中位数]较大的数组中大于等于其[中位数]的后半部分
				// 淘汰掉[中位数]较小的数组中小于等于其[中位数]的前半部分
				if (nums1[mid1] > nums2[mid2]) {
					if (nums1[mid1 - 1] <= nums2[mid2]) return nums2[mid2];
					e1 = mid1 - 1;
					s2 = mid2 + 1;
				} else {
					if (nums2[mid2 - 1] <= nums1[mid1]) return nums1[mid1];
					e2 = mid2 - 1;
					s1 = mid1 + 1;
				}
			} else { // 偶数长度
				// 则淘汰掉[中位数]较大的数组中大于其[中位数]的后半部分
				// 淘汰掉[中位数]较小的数组中小于等于其[中位数]的前半部分
				if (nums1[mid1] > nums2[mid2]) {
					e1 = mid1;
					s2 = mid2 + 1;
				} else {
					e2 = mid2;
					s1 = mid1 + 1;
				}
			}
		}
		// while出来表示数组这一段只有一个数，上中位数取较小的
		return Math.min(nums1[s1], nums2[s2]);
	}
}
