package com.yw.course.coding.class16;

import java.util.*;

import static com.yw.util.CommonUtils.randomArray;

/**
 * 给定一个有正、有负、有0的数组arr，给定一个整数k，返回arr的子集是否能累加出k
 *
 * 这道题是一个小小的补充，课上没有讲，但是如果你听过体系学习班动态规划专题和本节课的话，这道题就是一道水题
 * @author yangwei
 */
public class Code01_IsSum {

	// 方法一：暴力递归方法
	public static boolean isSubsetSumIsK(int[] arr, int k) {
		// 空集认为累加和是0
		if (k == 0) return true;
		if (arr == null || arr.length == 0) return false;
		return process(arr, 0, k);
	}
	private static boolean process(int[] arr, int idx, int k) {
		if (idx == arr.length) return k == 0;
		// 要idx 位置的数和不要idx位置的数
		return process(arr, idx + 1, k)
				|| process(arr, idx + 1, k - arr[idx]);
	}

	// 方法二：记忆化缓存
	public static boolean isSubsetSumIsKByCache(int[] arr, int k) {
		if (k == 0) return true;
		if (arr == null || arr.length == 0) return false;
		HashMap[] cache = new HashMap[arr.length + 1];
		return process(arr, 0, k, cache);
	}
	private static boolean process(int[] arr, int idx, int k, HashMap[] cache) {
		Map<Integer, Boolean> map = cache[idx];
		if (map == null) map = new HashMap<>();
		if (map.get(k) != null) return map.get(k);
		boolean ans;
		if (idx == arr.length) ans = k == 0;
		else {
			ans = process(arr, idx + 1, k, cache)
					|| process(arr, idx + 1, k - arr[idx], cache);
		}
		map.put(k, ans);
		return ans;
	}

	// 方法三：动态规划
	public static boolean isSubsetSumIsKByDp(int[] arr, int k) {
		if (k == 0) return true;
		if (arr == null || arr.length == 0) return false;
		int n = arr.length, minSum = 0, maxSum = 0;
		// 因为有负数，目标累加和可能为负数
		for (int x : arr) {
			if (x < 0) minSum += x;
			if (x > 0) maxSum += x;
		}
		if (k < minSum || k > maxSum) return false;
		// minSum <= k <= maxSum
		// dp[i][j]
		//  0  1  2  3  4  5  6  7 (实际)
		// -7 -6 -5 -4 -3 -2 -1  0 (想象中)
		// dp[0][-minSum]: dp[0][7] 对应 dp[0][0]
		boolean[][] dp = new boolean[n + 1][maxSum - minSum + 1];
		dp[n][-minSum] = true;
		for (int i = n - 1; i >= 0; i--) {
			for (int j = minSum; j <= maxSum; j++) {
				int next = j - minSum - arr[i];
				dp[i][j - minSum] = dp[i + 1][j - minSum];
				dp[i][j - minSum] |= (next >= 0 && next <= maxSum - minSum && dp[i + 1][next]);
			}
		}
		return dp[0][k - minSum];
	}

	// 方法四：分治的方法
	// 如果arr中的数值特别大，动态规划方法依然会很慢
	// 此时如果arr的数字个数不算多(40以内)，哪怕其中的数值很大，分治的方法也将是最优解
	public static boolean isSubsetSumIsKOptimal(int[] arr, int k) {
		if (k == 0) return true;
		if (arr == null || arr.length == 0) return false;
		if (arr.length == 1) return arr[0] == k;
		int n = arr.length, mid = n >> 1;
		Set<Integer> leftSum = new HashSet<>(), rightSum = new HashSet<>();
		process(arr, 0, mid, 0, leftSum);
		process(arr, mid, n, 0, rightSum);
		// 单独查看只使用左部分，能不能搞出k；单独查看只使用右部分，能不能搞出k；左+右，联合能不能搞出k
		// 左部分搞出所有累加和的时候，包含左部分一个数也没有，这种情况的，leftSum表里有0
		for (int l : leftSum) {
			if (rightSum.contains(k - l)) {
				return true;
			}
		}
		return false;
	}
	// arr[0...idx-1]决定已经做完了，形成的累加和是sum，arr[idx...end-1] end(终止) 所有数字随意选择
	// arr[0...end-1]所有可能的累加和存到sumSet里去
	private static void process(int[] arr, int idx, int end, int sum, Set<Integer> sumSet) {
		if (idx == end) sumSet.add(sum);
		else {
			process(arr, idx + 1, end, sum, sumSet);
			process(arr, idx + 1, end, sum + arr[idx], sumSet);
		}
	}

	public static void main(String[] args) {
		int N = 20;
		int M = 100;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int size = (int) (Math.random() * (N + 1));
			int[] arr = randomArray(size, M);
			int sum = (int) (Math.random() * ((M << 1) + 1)) - M;
			boolean ans1 = isSubsetSumIsK(arr, sum);
			boolean ans2 = isSubsetSumIsKByCache(arr, sum);
			boolean ans3 = isSubsetSumIsKByDp(arr, sum);
			boolean ans4 = isSubsetSumIsKOptimal(arr, sum);
			if (ans1 ^ ans2 || ans3 ^ ans4 || ans1 ^ ans3) {
				System.out.println("出错了！");
				System.out.print("arr : ");
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println("sum : " + sum);
				System.out.println("方法一答案 : " + ans1);
				System.out.println("方法二答案 : " + ans2);
				System.out.println("方法三答案 : " + ans3);
				System.out.println("方法四答案 : " + ans4);
				break;
			}
		}
		System.out.println("测试结束");
	}
}
