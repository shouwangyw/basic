package com.yw.advance.course.class41;

/**
 * @author yangwei
 */
public class Code01_BestSplitForAll {

	// 方法一：暴力解 O(N^2)
	public static int bestSplit0(int[] arr) {
		if (arr == null || arr.length < 2) return 0;
		int ans = 0, n = arr.length;
		// 长度n数组有n-1种划分方式
		for (int i = 0; i < n - 1; i++) {
			// 分别计算每一种划分方式下的左右两部分的累加和
			int sumL = 0, sumR = 0;
			for (int l = 0; l <= i; l++) sumL += arr[l];
			for (int r = i + 1; r < n; r++) sumR += arr[r];
			// 收集答案
			ans = Math.max(ans, Math.min(sumL, sumR));
		}
		return ans;
	}
	// 方法二：预处理 O(N)
	public static int bestSplit(int[] arr) {
		if (arr == null || arr.length < 2) return 0;
		// 先预处理计算出整个数组的累加和
		int n = arr.length, sum = 0, ans = 0, sumL = 0;
		for (int x : arr) sum += x;
		// 依次遍历每一种划分方式，计算左部分累加和、右部分累加和 = 总累加和 - 左部分累加和
		for (int i = 0; i < n - 1; i++) {
			sumL += arr[i];
			ans = Math.max(ans, Math.min(sumL, sum - sumL));
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
			int ans1 = bestSplit0(arr);
			int ans2 = bestSplit(arr);
			if (ans1 != ans2) {
				System.out.println(ans1);
				System.out.println(ans2);
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

}