package com.yw.course.advance.class39;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * 给定一个非负数组arr，和一个正数m。 返回arr的所有子序列中累加和%m之后的最大值。
 * @author yangwei
 */
public class Code01_SubSequenceMaxModM {

	// 方法一：暴力求每个子序列的累加和，然后计算累加和%m之后的最大值
	public static int maxSubSequenceSum1(int[] arr, int m) {
		// 定义一个set集合搜集所有子序列累加和
		Set<Integer> set = new HashSet<>();
		process(arr, 0, 0, set);
		// 求累加和%m之后的最大值
		int max = 0;
		for (int x : set) max = Math.max(max, x % m);
		return max;
	}
	private static void process(int[] arr, int idx, int sum, Set<Integer> set) {
		if (idx == arr.length) set.add(sum);
		else {
			process(arr, idx + 1, sum, set);
			process(arr, idx + 1, sum + arr[idx], set);
		}
	}

	// 方法二：动态规划 dp[i][j] 表示是否能从0到i的子序列累计和是j，i∈[0, n)、j∈[0, sum]
	public static int maxSubSequenceSum2(int[] arr, int m) {
		int n = arr.length, sum = 0;
		for (int x : arr) sum += x;
		boolean[][] dp = new boolean[n][sum + 1];
		for (boolean[] x : dp) x[0] = true;
		dp[0][arr[0]] = true;
		for (int i = 1; i < n; i++) {
			for (int j = 1; j <= sum; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j - arr[i] >= 0) dp[i][j] |= dp[i - 1][j - arr[i]];
			}
		}
		int ans = 0;
		for (int j = 0; j <= sum; j++) {
			if (dp[n - 1][j]) ans = Math.max(ans, j % m);
		}
		return ans;
	}

	// 方法三：如果arr数组特别大，那么累加和sum就会非常大，方法二就会失效（dp表会特别大）
	// 所以需要考虑其他的动态规划方式，dp[i][j] 表示是否存在从0到i的子序列累计和%m后余数是j，i∈[0, n)、j∈[0, m)
	public static int maxSubSequenceSum3(int[] arr, int m) {
		int n = arr.length;
		boolean[][] dp = new boolean[n][m];
		for (boolean[] x : dp) x[0] = true;
		dp[0][arr[0] % m] = true;
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				dp[i][j] = dp[i - 1][j];
				int cur = arr[i] % m;
				if (cur <= j) dp[i][j] |= dp[i - 1][j - cur];
				else dp[i][j] |= dp[i - 1][m + j - cur];
			}
		}
		int ans = 0;
		for (int j = 0; j < m; j++) {
			if (dp[n - 1][j]) ans = j;
		}
		return ans;
	}

	// 方法四：如果arr的累加和很大，m也很大，但arr的长度相对不大
	public static int maxSubSequenceSum4(int[] arr, int m) {
		int n = arr.length;
		if (n == 1) return arr[0] % m;
		int mid = (n - 1) / 2, ans = 0;
		TreeSet<Integer> set1 = new TreeSet<>();
		process(arr, 0, 0, mid, m, set1);
		TreeSet<Integer> set2 = new TreeSet<>();
		process(arr, mid + 1, 0, n - 1, m, set2);
		for (int leftMod : set1) {
			ans = Math.max(ans, leftMod + set2.floor(m - 1 - leftMod));
		}
		return ans;
	}
	// 从idx出发，最后右边界是end+1，arr[index...end]
	public static void process(int[] arr, int idx, int sum, int end, int m, Set<Integer> sortSet) {
		if (idx == end + 1) {
			sortSet.add(sum % m);
		} else {
			process(arr, idx + 1, sum, end, m, sortSet);
			process(arr, idx + 1, sum + arr[idx], end, m, sortSet);
		}
	}

	public static void main(String[] args) {
		int len = 10;
		int value = 100;
		int m = 76;
		int testTime = 500000;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(len, value);
			int ans1 = maxSubSequenceSum1(arr, m);
			int ans2 = maxSubSequenceSum2(arr, m);
			int ans3 = maxSubSequenceSum3(arr, m);
			int ans4 = maxSubSequenceSum4(arr, m);
			if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish!");

	}

	public static int[] generateRandomArray(int len, int value) {
		int[] ans = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * value);
		}
		return ans;
	}

}
