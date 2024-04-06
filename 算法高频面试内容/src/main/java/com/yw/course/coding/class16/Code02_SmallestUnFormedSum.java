package com.yw.course.coding.class16;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code02_SmallestUnFormedSum {

	// 方法一：常规解法
	public static int minMissedSubsetSum(int[] arr) {
		if (arr == null || arr.length == 0) return 1;
		// 算出所有累加和
		Set<Integer> sumSet = new HashSet<>();
		process(arr, 0, 0, sumSet);
		int min = Integer.MAX_VALUE;
		for (int x : arr) min = Math.min(min, x);
		if (min > 1) return 1;
		for (int i = min + 1; i < Integer.MAX_VALUE; i++) {
			if (!sumSet.contains(i)) return i;
		}
		return 1;
	}
	private static void process(int[] arr, int idx, int sum, Set<Integer> sumSet) {
		if (idx == arr.length) {
			sumSet.add(sum);
			return;
		}
		process(arr, idx + 1, sum, sumSet);
		process(arr, idx + 1, sum + arr[idx], sumSet);
	}

	// 方法二：改动态规划
	public static int minMissedSubsetSumByDp(int[] arr) {
		if (arr == null || arr.length == 0) return 1;
		int sum = 0, min = Integer.MAX_VALUE, n = arr.length;
		for (int x : arr) {
			sum += x;
			min = Math.min(min, x);
		}
		if (min > 1) return 1;
		boolean[][] dp = new boolean[n][sum + 1];
		for (int i = 0; i < n; i++) dp[i][0] = true;
		dp[0][arr[0]] = true;
		for (int i = 1; i < n; i++) {
			for (int j = 1; j <= sum; j++) {
				dp[i][j] = dp[i - 1][j];
				dp[i][j] |= j - arr[i] >= 0 && dp[i - 1][j - arr[i]];
			}
		}
		for (int j = min; j <= sum; j++) {
			if (!dp[n - 1][j]) return j;
		}
		return sum + 1;
	}

	// 已知arr中肯定有1这个数
	public static int minMissedSubsetSumHas1(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		Arrays.sort(arr);
		int range = 1;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > range + 1) return range + 1;
			range += arr[i];
		}
		return range + 1;
	}

	public static void main(String[] args) {
		int len = 27;
		int max = 30;
		int[] arr = generateArray(len, max);
		printArray(arr);
		long start = System.currentTimeMillis();
		System.out.println(minMissedSubsetSum(arr));
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");
		System.out.println("======================================");

		start = System.currentTimeMillis();
		System.out.println(minMissedSubsetSumByDp(arr));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");
		System.out.println("======================================");

		System.out.println("set arr[0] to 1");
		arr[0] = 1;
		start = System.currentTimeMillis();
		System.out.println(minMissedSubsetSumHas1(arr));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");
	}
}
