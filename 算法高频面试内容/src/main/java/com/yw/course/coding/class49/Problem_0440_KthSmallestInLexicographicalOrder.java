package com.yw.course.coding.class49;

/**
 * 这道题在leetcode上，所有题解都只能做到O( (logN) 平方)的解
 * 我们课上讲的是O(logN)的解
 * 打败所有题解
 *
 * @author yangwei
 */
public class Problem_0440_KthSmallestInLexicographicalOrder {

	private static final int[] offset = { 0, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000 };
	private static final int[] number = { 0, 1, 11, 111, 1111, 11111, 111111, 1111111, 11111111, 111111111, 1111111111 };
	public static int findKthNumber(int n, int k) {
		// 数字num有几位len，比如：65237有5位，len = 5
		int len = len(n);
		// 65237开头数字6，first = 6
		int first = n / offset[len];
		// 65237左边有几个
		int left = (first - 1) * number[len];
		int pick, already;
		// 1. k来自左边
		if (k <= left) {
			// k / a 向上取整-> (k + a - 1) / a
			pick = (k + number[len] - 1) / number[len];
			already = (pick - 1) * number[len];
			return kth((pick + 1) * offset[len] - 1, len, k - already);
		}
		// 2. k来自中间
		int mid = number[len - 1] + (n % offset[len]) + 1;
		if (k - left <= mid) return kth(n, len, k - left);
		// 3. k来自右侧
		k -= left + mid;
		len--;
		pick = (k + number[len] - 1) / number[len] + first;
		already = (pick - first - 1) * number[len];
		return kth((pick + 1) * offset[len] - 1, len, k - already);
	}
	private static int len(int n) {
		int len = 0;
		while (n != 0) {
			n /= 10;
			len++;
		}
		return len;
	}
	private static int kth(int max, int len, int kth) {
		// 中间范围还管不管的着！有任何一步中间位置没命中，左或右命中了，那以后就都管不着了！但是开始时，肯定是管的着的！
		boolean closeToMax = true;
		int ans = max / offset[len];
		while (--kth > 0) {
			max %= offset[len--];
			int pick;
			if (!closeToMax) {
				pick = (kth - 1) / number[len];
				ans = ans * 10 + pick;
				kth -= pick * number[len];
			} else {
				int first = max / offset[len];
				int left = first * number[len];
				if (kth <= left) {
					closeToMax = false;
					pick = (kth - 1) / number[len];
					ans = ans * 10 + pick;
					kth -= pick * number[len];
					continue;
				}
				kth -= left;
				int mid = number[len - 1] + (max % offset[len]) + 1;
				if (kth <= mid) {
					ans = ans * 10 + first;
					continue;
				}
				closeToMax = false;
				kth -= mid;
				len--;
				pick = (kth + number[len] - 1) / number[len] + first;
				ans = ans * 10 + pick;
				kth -= (pick - first - 1) * number[len];
			}
		}
		return ans;
	}

}
