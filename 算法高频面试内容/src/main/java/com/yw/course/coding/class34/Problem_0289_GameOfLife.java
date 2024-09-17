package com.yw.course.coding.class34;

/**
 * 有关这个游戏更有意思、更完整的内容: https://www.bilibili.com/video/BV1rJ411n7ri
 * 也推荐这个up主
 * @author yangwei
 */
public class Problem_0289_GameOfLife {

	private int[][] dirs = { {0, 1}, {1, 0}, {0, -1}, {-1, 0}, {-1, 1}, {1, -1}, {-1, -1}, {1, 1} };
	public void gameOfLife(int[][] board) {
		int m = board.length, n = board[0].length;
		int[][] cnt = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				for (int[] dir : dirs) {
					int x = i + dir[0], y = j + dir[1];
					if (x < 0 || x >= m || y < 0 || y >= n) continue;
					cnt[i][j] += board[x][y];
				}
			}
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == 0 && cnt[i][j] == 3) board[i][j] = 1;
				if (board[i][j] == 1 && (cnt[i][j] < 2 || cnt[i][j] > 3)) board[i][j] = 0;
			}
		}
	}

	public void gameOfLife2(int[][] board) {
		int m = board.length, n = board[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int cnt = 0;
				for (int[] dir : dirs) {
					int x = i + dir[0], y = j + dir[1];
					if (x < 0 || x >= m || y < 0 || y >= n) continue;
					// 利用board[x][y]二进制位的最后两位分别表示下一状态和当前状态
					cnt += ((board[x][y] & 1) == 1 ? 1 : 0);
				}
				// 满足存在条件时，或上 ....10
				if (cnt == 3 || (board[i][j] == 1 && cnt == 2)) board[i][j] |= 2;
			}
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) board[i][j] >>= 1;
		}
	}

}
