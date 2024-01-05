package com.yw.course.advance.class21;

/**
 * @author yangwei
 */
public class Code05_BobDie {

	// 方法一：尝试暴力递归
	public static double liveProbability(int row, int col, int k, int n, int m) {
		// 总可能性 4^k
		return (double) process(row, col, k, n, m) / Math.pow(4, k);
	}
	// 定义4个方向
	private static int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	private static long process(int row, int col, int k, int n, int m) {
		if (row < 0 || row == n || col < 0 || col == m) return 0;
		if (k == 0) return 1;
		long ans = 0;
		for (int[] dir : dirs) {
			ans += process(row + dir[0], col + dir[1], k - 1, n, m);
		}
		return ans;
	}

	// 方法二：改动态规划
	public static double liveProbabilityDp(int row, int col, int k, int n, int m) {
		long[][][] dp = new long[n][m][k + 1];
		// 初始化第0层
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) dp[i][j][0] = 1;
		}
		for (int k0 = 1; k0 <= k; k0++) {
			for (int x0 = 0; x0 < n; x0++) {
				for (int y0 = 0; y0 < m; y0++) {
					for (int[] dir : dirs) {
						int x = x0 + dir[0], y = y0 + dir[1];
						if (x < 0 || x >= n || y < 0 || y >= m) continue;
						dp[x0][y0][k0] += dp[x][y][k0 - 1];
					}
				}
			}
		}
		return (double) dp[row][col][k] / Math.pow(4, k);
	}

	public static void main(String[] args) {
		System.out.println(liveProbability(6, 6, 10, 50, 50));
		System.out.println(liveProbabilityDp(6, 6, 10, 50, 50));
	}

}
