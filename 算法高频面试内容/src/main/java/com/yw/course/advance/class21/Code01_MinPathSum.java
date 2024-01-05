package com.yw.course.advance.class21;

import static com.yw.util.CommonUtils.generateRandomMatrix;

/**
 * @author yangwei
 */
public class Code01_MinPathSum {
    // 方法一：普通动态规划
    public static int minPathSum0(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) return 0;
        int row = m.length, col = m[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = m[0][0];
        // 第0列
        for (int i = 1; i < row; i++) dp[i][0] = dp[i - 1][0] + m[i][0];
        // 第0行
        for (int j = 1; j < col; j++) dp[0][j] = dp[0][j - 1] + m[0][j];
        // (i, j)
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i][j - 1], dp[i - 1][j]) + m[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

    // 方法二：优化-数组压缩
    public static int minPathSum(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) return 0;
        int row = m.length, col = m[0].length;
        int[] dp = new int[col];
        dp[0] = m[0][0];
        for (int j = 1; j < col; j++) dp[j] = dp[j - 1] + m[0][j];
        for (int i = 1; i < row; i++) {
            dp[0] += m[i][0];
            for (int j = 1; j < col; j++) {
                // dp[j - 1]: dp[当前行][j - 1] 左侧的值
                // dp[j]: dp[上一行][j] 上侧的值
                dp[j] = Math.min(dp[j - 1], dp[j]) + m[i][j];
            }
        }
        return dp[col - 1];
    }

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] m = generateRandomMatrix(rowSize, colSize);
        System.out.println(minPathSum0(m));
        System.out.println(minPathSum(m));
    }
}
