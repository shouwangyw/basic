package com.yw.course.coding.class03;

import java.util.Arrays;

import static com.yw.util.CommonUtils.*;

/**
 * 给定一个数组arr，代表每个人的能力值。再给定一个非负数k。
 * 如果两个人能力差值正好为k，那么可以凑在一起比赛，一局比赛只有两个人
 * 返回最多可以同时有多少场比赛
 * @author yangwei
 */
public class Code04_MaxPairNumber {

	// 方法一：纯暴力解，全排列
	public static int maxPairNum0(int[] arr, int k) {
		if (k < 0) return -1;
		return process0(arr, 0, k);
	}
	private static int process0(int[] arr, int idx, int k) {
		int ans = 0, n = arr.length;
		if (idx == n) {
			for (int i = 1; i < n; i += 2) {
				if (arr[i] - arr[i - 1] == k) ans++;
			}
		} else {
			for (int r = idx; r < n; r++) {
				swap(arr, idx, r);
				ans = Math.max(ans, process0(arr, idx + 1, k));
				swap(arr, idx, r);
			}
		}
		return ans;
	}

	// 方法二：贪心 排序后，每次让较小的优先凑成一对，时间复杂度O(N*logN)
	public static int maxPairNum(int[] arr, int k) {
		if (k < 0 || arr == null || arr.length < 2) return 0;
		Arrays.sort(arr);
		int ans = 0, n = arr.length, l = 0, r = 0;
		boolean[] used = new boolean[n];
		while (l <= r && r < n) {
			if (used[l]) l++;
			else if (l >= r) r++;
			else {
				int dis = arr[r] - arr[l];
				if (dis == k) {
					ans++;
					used[r++] = true;
					l++;
				} else if (dis < k) r++;
				else l++;
			}
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 20;
		int maxK = 5;
		int testTime = 1000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * (maxLen + 1));
			int[] arr = randomArray(N, maxValue);
			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int k = (int) (Math.random() * (maxK + 1));
			int ans1 = maxPairNum0(arr1, k);
			int ans2 = maxPairNum(arr2, k);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				printArray(arr);
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("功能测试结束");
	}
	private static int[] randomArray(int len, int value) {
		int[] arr = new int[len];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * value);
		}
		return arr;
	}
}
