package com.yw.course.advance.class41;

/**
 * @author yangwei
 */
public class Code02_BestSplitForEveryPosition {

	// 方法一：暴力解 O(N^3)
	public static int[] bestSplit0(int[] arr) {
		if (arr == null || arr.length == 0) return new int[0];
		int n = arr.length;
		int[] ans = new int[n];
		for (int range = 1; range < n; range++) {
			for (int i = 0; i < range; i++) {
				int sumL = 0, sumR = 0;
				for (int l = 0; l <= i; l++) sumL += arr[l];
				for (int r = i + 1; r <= range; r++) sumR += arr[r];
				ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
			}
		}
		return ans;
	}
	// 方法二：利用预处理前缀和数组优化，O(N^2)
	public static int[] bestSplit(int[] arr) {
		if (arr == null || arr.length == 0) return new int[0];
		int n = arr.length;
		int[] ans = new int[n], sum = new int[n + 1];
		for (int i = 0; i < n; i++) sum[i + 1] = sum[i] + arr[i];
		for (int range = 1; range < n; range++) {
			for (int i = 0; i < range; i++) {
				int sumL = rangeSum(sum, 0, i), sumR = rangeSum(sum, i + 1, range);
				ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
			}
		}
		return ans;
	}
	private static int rangeSum(int[] sum, int l, int r) {
		return sum[r + 1] - sum[l];
	}
	// 方法三：最优解，O(N)
	public static int[] bestSplitOptimal(int[] arr) {
		if (arr == null || arr.length == 0) return new int[0];
		int n = arr.length;
		int[] ans = new int[n], sum = new int[n + 1];
		for (int i = 0; i < n; i++) sum[i + 1] = sum[i] + arr[i];
		// 最优划分 0~range-1上，最优划分是左部分[0, best]  右部分[best+1, range-1]
		int best = 0;
		for (int range = 1; range < n; range++) {
			while (best + 1 < range) {
				int before = Math.min(rangeSum(sum, 0, best), rangeSum(sum, best + 1, range));
				int after = Math.min(rangeSum(sum, 0, best + 1), rangeSum(sum, best + 2, range));
				if (after >= before) best++;
				else break;
			}
			ans[range] = Math.min(rangeSum(sum, 0, best), rangeSum(sum, best + 1, range));
		}
		return ans;
	}

	public static void main(String[] args) {
		int N = 20;
		int max = 30;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N);
			int[] arr = randomArray(len, max);
			int[] ans1 = bestSplit0(arr);
			int[] ans2 = bestSplit(arr);
			int[] ans3 = bestSplitOptimal(arr);
			if (!isSameArray(ans1, ans2) || !isSameArray(ans1, ans3)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");
	}

	public static int[] randomArray(int len, int max) {
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * max);
		}
		return ans;
	}

	public static boolean isSameArray(int[] arr1, int[] arr2) {
		if (arr1.length != arr2.length) {
			return false;
		}
		int N = arr1.length;
		for (int i = 0; i < N; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}
}
