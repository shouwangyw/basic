package com.yw.course.coding.class32;

/**
 * @author yangwei
 */
public class Problem_0189_RotateArray {

	// 方法一：利用辅助数组
	public void rotate0(int[] nums, int k) {
		int n = nums.length, m = n - (k % n), i = 0, j = m;
		int[] tmp = new int[n];
		while (j < n) tmp[i++] = nums[j++];
		j = 0;
		while (j < m) tmp[i++] = nums[j++];
		i = 0;
		while (i < n) {
			nums[i] = tmp[i];
			i++;
		}
	}

	// 方法二：双指针反转数组
	public void rotate(int[] nums, int k) {
		int n = nums.length;
		k %= n;
		reverse(nums, 0, n - k - 1);
		reverse(nums, n - k, n - 1);
		reverse(nums, 0, n - 1);
	}
	private static void reverse(int[] nums, int l, int r) {
		while (l < r) {
			int tmp = nums[l];
			nums[l++] = nums[r];
			nums[r--] = tmp;
		}
	}

	public static void rotate2(int[] nums, int k) {
		int N = nums.length;
		k = k % N;
		if (k == 0) {
			return;
		}
		int L = 0;
		int R = N - 1;
		int lpart = N - k;
		int rpart = k;
		int same = Math.min(lpart, rpart);
		int diff = lpart - rpart;
		exchange(nums, L, R, same);
		while (diff != 0) {
			if (diff > 0) {
				L += same;
				lpart = diff;
			} else {
				R -= same;
				rpart = -diff;
			}
			same = Math.min(lpart, rpart);
			diff = lpart - rpart;
			exchange(nums, L, R, same);
		}
	}

	public static void exchange(int[] nums, int start, int end, int size) {
		int i = end - size + 1;
		int tmp = 0;
		while (size-- != 0) {
			tmp = nums[start];
			nums[start] = nums[i];
			nums[i] = tmp;
			start++;
			i++;
		}
	}

}
