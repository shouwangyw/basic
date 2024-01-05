package com.yw.course.advance.class20;

/**
 * @author yangwei
 */
public class Code02_HorseJump {

    // 方法一：尝试暴力递归
    public static int jumpByRecurse(int x, int y, int k) {
        return process(0, 0, k, x, y);
    }

    // 当前位置(i, j)，还剩k步 返回 k步刚好到 (x, y) 的方法总数
    // 可变参数: i, j, k -->> 三维dp
    private static int process(int i, int j, int k, int x, int y) {
        // 棋盘大小 10 * 9，边界值处理
        if (i < 0 || i > 9 || j < 0 || j > 8) return 0;
        // 当k == 0即步数走完时，若刚好到 (x, y) 位置，则找到一种方法，返回1，否则返回0
        if (k == 0) return (i == x && j == y) ? 1 : 0;
        // "马"有8个方向可以走
        int ans = 0;
        for (int[] dir : dirs) {
            ans += process(i + dir[0], j + dir[1], k - 1, x, y);
        }
        return ans;
    }

    private static int[][] dirs = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};

    // 方法二：基于暴力递归改动态规划
    public static int jumpByDp(int x, int y, int k) {
        int m = 9, n = 8;
        int[][][] dp = new int[m + 1][n + 1][k + 1];
        dp[x][y][0] = 1;
        for (int k0 = 1; k0 <= k; k0++) {
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    for (int[] dir : dirs) {
                        int i0 = i + dir[0], j0 = j + dir[1];
                        if (i0 < 0 || i0 > m || j0 < 0 || j0 > n) continue;
                        dp[i][j][k0] += dp[i0][j0][k0 - 1];
                    }
                }
            }
        }
        return dp[0][0][k];
    }

//    public static int ways(int a, int b, int step) {
//        return f(0, 0, step, a, b);
//    }
//
//    public static int f(int i, int j, int step, int a, int b) {
//        if (i < 0 || i > 9 || j < 0 || j > 8) {
//            return 0;
//        }
//        if (step == 0) {
//            return (i == a && j == b) ? 1 : 0;
//        }
//        return f(i - 2, j + 1, step - 1, a, b) + f(i - 1, j + 2, step - 1, a, b) + f(i + 1, j + 2, step - 1, a, b)
//                + f(i + 2, j + 1, step - 1, a, b) + f(i + 2, j - 1, step - 1, a, b) + f(i + 1, j - 2, step - 1, a, b)
//                + f(i - 1, j - 2, step - 1, a, b) + f(i - 2, j - 1, step - 1, a, b);
//
//    }
//
//    public static int waysdp(int a, int b, int s) {
//        int[][][] dp = new int[10][9][s + 1];
//        dp[a][b][0] = 1;
//        for (int step = 1; step <= s; step++) { // 按层来
//            for (int i = 0; i < 10; i++) {
//                for (int j = 0; j < 9; j++) {
//                    dp[i][j][step] = getValue(dp, i - 2, j + 1, step - 1) + getValue(dp, i - 1, j + 2, step - 1)
//                            + getValue(dp, i + 1, j + 2, step - 1) + getValue(dp, i + 2, j + 1, step - 1)
//                            + getValue(dp, i + 2, j - 1, step - 1) + getValue(dp, i + 1, j - 2, step - 1)
//                            + getValue(dp, i - 1, j - 2, step - 1) + getValue(dp, i - 2, j - 1, step - 1);
//                }
//            }
//        }
//        return dp[0][0][s];
//    }
//
//    // 在dp表中，得到dp[i][j][step]的值，但如果(i，j)位置越界的话，返回0；
//    public static int getValue(int[][][] dp, int i, int j, int step) {
//        if (i < 0 || i > 9 || j < 0 || j > 8) {
//            return 0;
//        }
//        return dp[i][j][step];
//    }

    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;

        System.out.println(jumpByRecurse(x, y, step));

        System.out.println(jumpByDp(x, y, step));

//        System.out.println(ways(x, y, step));
    }
}
