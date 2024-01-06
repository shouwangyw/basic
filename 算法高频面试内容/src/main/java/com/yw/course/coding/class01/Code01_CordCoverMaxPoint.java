package com.yw.course.coding.class01;

import java.util.Arrays;

/**
 * @author yangwei
 */
public class Code01_CordCoverMaxPoint {

	// 方法一：二分法
	public static int maxPoint1(int[] arr, int k) {
		int ans = 1;
		// 遍历每一个点arr[i]作为绳子覆盖的右边缘点r，则绳子左边最多能到arr[i]-k
		// 利用二分法找到左边点位置l，在所有覆盖点中求最大值max{r-l+1}
		for (int r = 1; r < arr.length; r++) {
			int l = binarySearch(arr, r, arr[r] - k);
			ans = Math.max(ans, r - l + 1);
		}
		return ans;
	}
	private static int binarySearch(int[] arr, int r, int val) {
		int l = 0, idx = r, mid;
		while (l <= r) {
			mid = l + ((r - l) >> 1);
			if (arr[mid] >= val) {
				idx = mid;
				r = mid - 1;
			} else l = mid + 1;
		}
		return idx;
	}

	// 方法二：滑动窗口法
	public static int maxPoint2(int[] arr, int k) {
		int l = 0, r = 0, n = arr.length, max = 0;
		while (l < n) {
			// 窗口: arr[r] - arr[l]
			while (r < n && arr[r] - arr[l] <= k) r++;
			max = Math.max(max, r - (l++));
		}
		return max;
	}

	// for test
	public static int test(int[] arr, int L) {
		int max = 0;
		for (int i = 0; i < arr.length; i++) {
			int pre = i - 1;
			while (pre >= 0 && arr[i] - arr[pre] <= L) {
				pre--;
			}
			max = Math.max(max, i - pre);
		}
		return max;
	}

	public static void main(String[] args) {
		int len = 100;
		int max = 1000;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int L = (int) (Math.random() * max);
			int[] arr = generateArray(len, max);
			int ans1 = maxPoint1(arr, L);
			int ans2 = maxPoint2(arr, L);
			int ans3 = test(arr, L);
			if (ans1 != ans2 || ans2 != ans3) {
				System.out.println("oops!");
				break;
			}
		}
	}
	private static int[] generateArray(int len, int max) {
		int[] ans = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * max);
		}
		Arrays.sort(ans);
		return ans;
	}
}
