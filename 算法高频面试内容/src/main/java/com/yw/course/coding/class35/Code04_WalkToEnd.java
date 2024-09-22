package com.yw.course.coding.class35;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 来自网易
 * map[i][j] == 0，代表(i,j)是海洋，渡过的话代价是2
 * map[i][j] == 1，代表(i,j)是陆地，渡过的话代价是1
 * map[i][j] == 2，代表(i,j)是障碍，无法渡过
 * 每一步上、下、左、右都能走，返回从左上角走到右下角最小代价是多少，如果无法到达返回-1
 *
 * @author yangwei
 */
public class Code04_WalkToEnd {

    private static int[][] dirs = { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };
    public static int minCost(int[][] matrix) {
        if (matrix[0][0] == 2) return -1;
        int m = matrix.length, n = matrix[0].length;
        Queue<Data> heap = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        boolean[][] visited = new boolean[m][n];
        dfs(matrix, 0, 0, 0, heap, visited);
        while (!heap.isEmpty()) {
            Data cur = heap.poll();
            if (cur.x == m - 1 && cur.y == n - 1) return cur.cost;
            for (int[] dir : dirs)
                dfs(matrix, cur.x + dir[0], cur.y + dir[1], cur.cost, heap, visited);
        }
        return -1;
    }
    private static void dfs(int[][] matrix, int x, int y, int preCost, Queue<Data> heap, boolean[][] visited) {
        if (x == 0 || x == matrix.length || y == 0 || y == matrix[0].length || matrix[x][y] == 2 || visited[x][y]) return;
        heap.offer(new Data(x, y, preCost + (matrix[x][y] == 0 ? 2 : 1)));
        visited[x][y] = true;
    }
    private static class Data {
        private int x;
        private int y;
        private int cost;
        public Data(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}
