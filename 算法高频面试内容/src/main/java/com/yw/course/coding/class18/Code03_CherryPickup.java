package com.yw.course.coding.class18;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 牛客的测试链接：
 * https://www.nowcoder.com/questionTerminal/8ecfe02124674e908b2aae65aad4efdf
 * 把如下的全部代码拷贝进java编辑器
 * 把文件大类名字改成Main，可以直接通过
 *
 * @author yangwei
 */
public class Code03_CherryPickup {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt(), n = in.nextInt();
        int[][] grid = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                grid[i][j] = in.nextInt();
        }
        // 傻缓存
        int[][][] cache = new int[m][n][m];
        for (int[][] xs : cache) {
            for (int[] x : xs) Arrays.fill(x, -1);
        }
        // System.out.println(process(grid, 0, 0, 0, 0));
        System.out.println(process(grid, 0, 0, 0, cache));
        in.close();
    }
    // A来到了(x1, y1)位置，B来到了(x2, y2)位置，返回A、B到达右下角时的最大路径和
    // private static int process(int[][] grid, int x1, int y1, int x2, int y2) {
    private static int process(int[][] grid, int x1, int y1, int x2, int[][][] cache) {
        int m = grid.length, n = grid[0].length;
        if (x1 == m || y1 == n || x2 == m || x1 + y1 - x2 == n) return -1;
        if (cache[x1][y1][x2] != -1) return cache[x1][y1][x2];
        // base case: 到达右下角，此时A和B一定是同时到达
        if (x1 == m - 1 && y1 == n - 1) return cache[x1][y1][x2] = grid[x1][y1];
        int max = 0;
        // 接下来A、B可以有4种组合情况: A下B下、A下B右、A右B下、A右B右
        if (x1 < m - 1) {
            if (x2 < m - 1) max = Math.max(max, process(grid, x1 + 1, y1, x2 + 1, cache));
            if (x1 + y1 - x2 < n - 1) max = Math.max(max, process(grid, x1 + 1, y1, x2, cache));
        }
        if (y1 < n - 1) {
            if (x2 < m - 1) max = Math.max(max, process(grid, x1, y1 + 1, x2 + 1, cache));
            if (x1 + y1 - x2 < n - 1) max = Math.max(max, process(grid, x1, y1 + 1, x2, cache));
        }
        // 收集答案
        int ans = 0;
        if (x1 == x2) ans = grid[x1][y1];
        else ans = grid[x1][y1] + grid[x2][x1 + y1 - x2];
        return cache[x1][y1][x2] = (ans + max);
    }
}