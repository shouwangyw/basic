package com.yw.course.coding.class08;

import java.util.Arrays;

/**
 * @author yangwei
 */
public class Code04_SnakeGame {

    // 方法一：暴力尝试
    public static int walk0(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int max = 0, n = matrix.length, m = matrix[0].length;
        // 枚举每一个可能到达的位置，求最大增长值
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int[] ans = process(matrix, i, j);
                max = Math.max(max, Math.max(ans[0], ans[1]));
            }
        }
        return max;
    }
    private static int[][] dirs = { {-1, -1}, {0, -1}, {1, -1} };
    // 蛇从某一个最左列，且最优的空降点降落，必须走到终点位置(i,j)，返回两个值
    // [0]表示不使用能力获得的最大成长值，[1]表示使用能力获得的最大成长值
    private static int[] process(int[][] m, int i, int j) {
        // base case: 来到最左侧位置
        if (j == 0) return new int[] {m[i][j], -m[i][j]};
        // 获取上一步成长值，并加工出当前成长值
        int preNoMax = -1, preYesMax = -1;
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];
            if (x < 0 || x >= m.length) continue;
            int[] pre = process(m, x, y);
            preNoMax = Math.max(preNoMax, pre[0]);
            preYesMax = Math.max(preYesMax, pre[1]);
        }
        int noMax = -1, yesMax = -1;
        if (preNoMax >= 0) {
            noMax = m[i][j] + preNoMax;
            yesMax = -m[i][j] + preNoMax;
        }
        if (preYesMax >= 0) yesMax = Math.max(yesMax, m[i][j] + preYesMax);
        return new int[] {noMax, yesMax};
    }

    // 方法二：改动态规划
    public static int walk(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int max = 0, n = matrix.length, m = matrix[0].length;
        int[][][] dp = new int[n][m][2];
        for (int i = 0; i < dp.length; i++) {
            dp[i][0][0] = matrix[i][0];
            dp[i][0][1] = -matrix[i][0];
            max = Math.max(max, Math.max(dp[i][0][0], dp[i][0][1]));
        }
        for (int j = 1; j < m; j++) {
            for (int i = 0; i < n; i++) {
                int preNoMax = -1, preYesMax = -1;
                for (int[] dir : dirs) {
                    int x = i + dir[0], y = j + dir[1];
                    if (x < 0 || x >= n) continue;
                    preNoMax = Math.max(preNoMax, dp[x][y][0]);
                    preYesMax = Math.max(preYesMax, dp[x][y][1]);
                }
                dp[i][j][0] = -1;
                dp[i][j][1] = -1;
                if (preNoMax >= 0) {
                    dp[i][j][0] = matrix[i][j] + preNoMax;
                    dp[i][j][1] = -matrix[i][j] + preNoMax;
                }
                if (preYesMax >= 0) dp[i][j][1] = Math.max(dp[i][j][1], matrix[i][j] + preYesMax);
                max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int N = 7;
        int M = 7;
        int V = 10;
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            int r = (int) (Math.random() * (N + 1));
            int c = (int) (Math.random() * (M + 1));
            int[][] matrix = generateRandomArray(r, c, V);
            int ans1 = walk0(matrix);
            int ans2 = walk(matrix);
            if (ans1 != ans2) {
                for (int j = 0; j < matrix.length; j++) {
                    System.out.println(Arrays.toString(matrix[j]));
                }
                System.out.println("Oops   ans1: " + ans1 + "   ans2:" + ans2);
                break;
            }
        }
        System.out.println("finish");
    }
    private static int[][] generateRandomArray(int row, int col, int value) {
        int[][] arr = new int[row][col];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = (int) (Math.random() * value) * (Math.random() > 0.5 ? -1 : 1);
            }
        }
        return arr;
    }
}
