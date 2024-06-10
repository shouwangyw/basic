package com.yw.course.coding.class31;

/**
 * @author yangwei
 */
public class Problem_0130_SurroundedRegions {

//	// m -> 二维数组， 不是0就是1
//	//
//	public static void infect(int[][] m, int i, int j) {
//		if (i < 0 || i == m.length || j < 0 || j == m[0].length || m[i][j] != 1) {
//			return;
//		}
//		// m[i][j] == 1
//		m[i][j] = 2;
//		infect(m, i - 1, j);
//		infect(m, i + 1, j);
//		infect(m, i, j - 1);
//		infect(m, i, j + 1);
//	}

	public static void solve1(char[][] board) {
		boolean[] ans = new boolean[1];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 'O') {
					ans[0] = true;
					can(board, i, j, ans);
					board[i][j] = ans[0] ? 'T' : 'F';
				}
			}
		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				char can = board[i][j];
				if (can == 'T' || can == 'F') {
					board[i][j] = '.';
					change(board, i, j, can);
				}
			}
		}

	}

	public static void can(char[][] board, int i, int j, boolean[] ans) {
		if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
			ans[0] = false;
			return;
		}
		if (board[i][j] == 'O') {
			board[i][j] = '.';
			can(board, i - 1, j, ans);
			can(board, i + 1, j, ans);
			can(board, i, j - 1, ans);
			can(board, i, j + 1, ans);
		}
	}

	public static void change(char[][] board, int i, int j, char can) {
		if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
			return;
		}
		if (board[i][j] == '.') {
			board[i][j] = can == 'T' ? 'X' : 'O';
			change(board, i - 1, j, can);
			change(board, i + 1, j, can);
			change(board, i, j - 1, can);
			change(board, i, j + 1, can);
		}
	}

	// 从边界开始感染的方法，比第一种方法更好
	private int m;
	private int n;
	private int[][] dirs = { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };
	public void solve(char[][] board) {
		m = board.length;
		n = board[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				// 从边界开始感染
				if ((i == 0 || i == m - 1 || j == 0 || j == n - 1) && board[i][j] == 'O')
					infect(board, i, j);
			}
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == 'O') board[i][j] = 'X';
				else if (board[i][j] == '#') board[i][j] = 'O';
			}
		}
	}
	private void infect(char[][] board, int x, int y) {
		if (x < 0 || x >= m || y < 0 || y >= n || board[x][y] != 'O') return;
		board[x][y] = '#';
		for (int[] dir : dirs)
			infect(board, x + dir[0], y + dir[1]);
	}

}
