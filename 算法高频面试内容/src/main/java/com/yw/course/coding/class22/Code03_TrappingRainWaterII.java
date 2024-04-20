package com.yw.course.coding.class22;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 测试链接: https://leetcode.cn/problems/trapping-rain-water-ii/
 *
 * @author yangwei
 */
public class Code03_TrappingRainWaterII {

    public int trapRainWater(int[][] heightMap) {
        int m = heightMap.length, n = heightMap[0].length;
        // 定义小根堆
        Queue<Info> q = new PriorityQueue<>(Comparator.comparingInt(o -> o.v));
        // 记录已经访问过的
        boolean[][] visited = new boolean[m][n];
        // 矩阵四条边加入小根堆
        for (int c = 0; c < n - 1; c++) {
            q.offer(new Info(0, c, heightMap[0][c]));
            visited[0][c] = true;
        }
        for (int r = 0; r < m - 1; r++) {
            q.offer(new Info(r, n - 1, heightMap[r][n - 1]));
            visited[r][n - 1] = true;
        }
        for (int c = n - 1; c > 0; c--) {
            q.offer(new Info(m - 1, c, heightMap[m - 1][c]));
            visited[m - 1][c] = true;
        }
        for (int r = m - 1; r > 0; r--) {
            q.offer(new Info(r, 0, heightMap[r][0]));
            visited[r][0] = true;
        }
        int ans = 0, max = 0;
        while (!q.isEmpty()) {
            // 每次弹出堆顶，更新max，向上下左右四个方向走，并结算水量
            Info cur = q.poll();
            max = Math.max(max, cur.v);
            int r0 = cur.r, c0 = cur.c;
            for (int[] dir : dirs) {
                int r = r0 + dir[0], c = c0 + dir[1];
                if (r < 0 || r > m - 1|| c < 0 || c > n - 1 || visited[r][c]) continue;
                ans += Math.max(0, max - heightMap[r][c]);
                q.offer(new Info(r, c, heightMap[r][c]));
                visited[r][c] = true;
            }
        }
        return ans;
    }
    private static int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private static class Info {
        private final int r;
        private final int c;
        private final int v;
        public Info(int r, int c, int v) {
            this.r = r;
            this.c = c;
            this.v = v;
        }
    }

}
