package com.yw.course.coding.class18;

/**
 * 测试链接: https://leetcode.cn/problems/shortest-bridge/
 *
 * @author yangwei
 */
public class Code02_ShortestBridge {

    public int shortestBridge(int[][] grid) {
        int n = grid.length, m = grid[0].length, all = n * m, island = 0;
        int[] cur = new int[all], next = new int[all];
        int[][] dist = new int[2][all];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] != 1) continue;
                // 发现岛屿，把这一片的1都变成2，这一片1组成初始数组 cur，并且这一片1到自己的距离都设置成了1放在数组 dist
                int qSize = infect(grid, i, j, n, m, cur, 0, dist[island]);
                int v = 1;
                while (qSize != 0) { // 队列中还有，就继续广播出去
                    v++;
                    // cur里面的点，上下左右走 dist[点]==0 的位置，下一级的点收集到next中
                    qSize = bfs(m, all, v, cur, qSize, next, dist[island]);
                    // 交替使用 cur、next
                    int[] tmp = cur;
                    cur = next;
                    next = tmp;
                }
                island++;
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < all; i++) min = Math.min(min, dist[0][i] + dist[1][i]);
        return min - 3;
    }
    private static int[][] dirs = { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };
    // 当前来到grid[i][j]位置，总行数n、列数m，将grid[i][j]感染出去(找到这一片岛屿所有的1)，并把每一个1的坐标放到cur中
    // 例如：1 (a,b) -> cur[idx++] = (a * m + b)、1 (c,d) -> cur[idx++] = (c * m + d)
    // 二维变一维，1 (a,b) -> a * m + b，设置距离 dist[a * m + b] = 0
    private static int infect(int[][] grid, int i, int j, int n, int m, int[] cur, int k, int[] dist) {
        if (i < 0 || i >= n || j < 0 || j >= m || grid[i][j] != 1) return k;
        grid[i][j] = 2;
        int idx = i * m + j;
        dist[idx] = 1;
        cur[k++] = idx; // 收集不同的1
        for (int[] dir : dirs) k = infect(grid, i + dir[0], j + dir[1], n, m, cur, k, dist);
        return k;
    }
    // 二维矩阵n*m，总点数all，v是要生成的低几层，cur是v-1层，next是v层，从dist中拿距离
    private static int bfs(int m, int all, int v, int[] cur, int size, int[] next, int[] dist) {
        int k = 0; // 我要生成的下一层队列成长到哪？
        for (int i = 0; i < size; i++) {
            // 一维 推 上下左右 位置
            // (a,b) -> cur[i] = a * m + b，对应的上下左右坐标分别是
            // (a-1) * m + b = cur[i] - m
            // (a+1) * m + b = cur[i] + m
            // a * m + b - 1 = cur[i] - 1
            // a * m + b + 1 = cur[i] + 1
            int up = cur[i] - m, down = cur[i] + m, left = cur[i] - 1, right = cur[i] + 1;
            if (up >= 0 && dist[up] == 0) {
                dist[up] = v;
                next[k++] = up;
            }
            if (down < all && dist[down] == 0) {
                dist[down] = v;
                next[k++] = down;
            }
            if (cur[i] % m != 0 && dist[left] == 0) {
                dist[left] = v;
                next[k++] = left;
            }
            if (cur[i] % m != m - 1 && dist[right] == 0) {
                dist[right] = v;
                next[k++] = right;
            }
        }
        return k;
    }
}
