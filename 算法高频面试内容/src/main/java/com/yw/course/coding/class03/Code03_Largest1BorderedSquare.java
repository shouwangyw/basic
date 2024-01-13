package com.yw.course.coding.class03;

/**
 * 测试链接 : https://leetcode.cn/problems/largest-1-bordered-square/
 * @author yangwei
 */
public class Code03_Largest1BorderedSquare {

	public int largest1BorderedSquare(int[][] grid) {
		int m = grid.length, n = grid[0].length;
		// 预处理生成每个点右边和下边有多少个连续的1
		// m + 1, n + 1 避免边界值判断
		int[][] right = new int[m + 1][n + 1], down = new int[m + 1][n + 1];
		setBorder(grid, right, down);
		for (int size = Math.min(m, n); size > 0; size--) {
			if (valid(size, right, down)) return size * size;
		}
		return 0;
	}
	private static void setBorder(int[][] grid, int[][] right, int[][] down) {
		for (int i = grid.length - 1; i >= 0; i--) {
			for (int j = grid[0].length - 1; j >= 0; j--) {
				if (grid[i][j] == 0) continue;
				right[i][j] = right[i][j + 1] + 1;
				down[i][j] = down[i + 1][j] + 1;
			}
		}
	}
	private static boolean valid(int size, int[][] right, int[][] down) {
		for (int i = 0; i < right.length - size; i++) {
			for (int j = 0; j < right[0].length - size; j++) {
				// 判断四条边是否有连续1超过长度size
				if (right[i][j] >= size && down[i][j] >= size && right[i + size - 1][j] >= size && down[i][j + size - 1] >= size) return true;
			}
		}
		return false;
	}
}
