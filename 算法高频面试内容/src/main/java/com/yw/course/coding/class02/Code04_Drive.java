package com.yw.course.coding.class02;

import java.util.Arrays;

/**
 * @author yangwei
 */
public class Code04_Drive {

	// 方法一：暴力尝试
	public static int maxMoney0(int[][] income) {
		// 司机数量一定要是偶数
		if (income == null || income.length < 2 || (income.length & 1) != 0) return 0;
		int n = income.length;
		return process0(income, 0, n >> 1);
	}
	// 当前来到i号司机，还剩k个名额调度到A区，返回调度完i及其以后的司机获得的最大钱数
	private static int process0(int[][] income, int i, int k) {
		// base case1: 没有司机需要调度了
		if (i == income.length) return 0;
		// 还有司机需要调度
		// base case2: A区名额用完，则剩下的都是去B区的
		if (k == 0) return income[i][1] + process0(income, i + 1, 0);
		// base case3: A区名额刚好等于剩余人数，则都是去A区的
		if (k == income.length - i) return income[i][0] + process0(income, i + 1, k - 1);
		// 否则，当前司机可以去A，也可以去B
		return Math.max(income[i][0] + process0(income, i + 1, k - 1),
				income[i][1] + process0(income, i + 1, k));
	}

	// 方法二：基于尝试改成动态规划
	public static int maxMoneyDp(int[][] income) {
		if (income == null || income.length < 2 || (income.length & 1) != 0) return 0;
		int n = income.length, m = n >> 1;
		int[][] dp = new int[n + 1][m + 1];
		for (int i = n - 1; i >= 0; i--) {
			for (int j = 0; j <= m; j++) {
				if (j == 0) dp[i][j] = income[i][1] + dp[i + 1][0];
				else if (j == n - i) dp[i][j] = income[i][0] + dp[i + 1][j - 1];
				else dp[i][j] = Math.max(income[i][0] + dp[i + 1][j - 1], income[i][1] + dp[i + 1][j]);
			}
		}
		return dp[0][m];
	}

	// 方法三：贪心策略
	// 假设一共有10个司机，思路是先让所有司机去A，得到一个总收益，然后看看哪5个司机改换门庭(去B)，可以获得最大的额外收益
	// 这道题有贪心策略，打了我的脸
	// 但是我课上提到的技巧请大家重视
	// 根据数据量猜解法可以省去大量的多余分析，节省时间
	// 这里感谢卢圣文同学
	public static int maxMoney(int[][] income) {
		int n = income.length, sum = 0, m = n >> 1;
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = income[i][1] - income[i][0];
			sum += income[i][0];
		}
		Arrays.sort(arr);
		for (int i = n - 1; i >= m; i--) sum += arr[i];
		return sum;
	}

	public static void main(String[] args) {
		int N = 10;
		int value = 100;
		int testTime = 500;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[][] matrix = randomMatrix(len, value);
			int ans1 = maxMoney0(matrix);
			int ans2 = maxMoneyDp(matrix);
			int ans3 = maxMoney(matrix);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");
	}
	// 返回随机len*2大小的正数矩阵
	// 值在0~value-1之间
	private static int[][] randomMatrix(int len, int value) {
		int[][] ans = new int[len << 1][2];
		for (int i = 0; i < ans.length; i++) {
			ans[i][0] = (int) (Math.random() * value);
			ans[i][1] = (int) (Math.random() * value);
		}
		return ans;
	}
}
