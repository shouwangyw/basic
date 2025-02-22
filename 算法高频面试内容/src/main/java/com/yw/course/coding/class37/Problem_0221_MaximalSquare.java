package com.yw.course.coding.class37;

/**
 * @author yangwei
 */
public class Problem_0221_MaximalSquare {

	public int maximalSquare(char[][] matrix) {
		int m = matrix.length, n = matrix[0].length;
		// dp[i][j]: 表示以[i][j]位置为正方形右下角时，所能组成的最大正方形边长
		//      = min{左边、上边、左上} + 1
		int[][] dp = new int[m][n];
		int max = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == '0') continue;
				if (i == 0 || j == 0) dp[i][j] = matrix[i][j] - '0';
				else
					dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j])) + 1;
				max = Math.max(max, dp[i][j]);
			}
		}
		return max * max;
	}

}
