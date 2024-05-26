package com.yw.course.coding.class28;

/**
 * @author yangwei
 */
public class Problem_0037_SudokuSolver {

	public void solveSudoku(char[][] board) {
		boolean[][] row = new boolean[9][10], col = new boolean[9][10], bucket = new boolean[9][10];
		// 初始化记录哪些位置的数字已经填过了
		init(board, row, col, bucket);
		// 深度优先遍历
		dfs(board, 0, 0, row, col, bucket);
	}
	private void init(char[][] board, boolean[][] row, boolean[][] col, boolean[][] bucket) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == '.') continue;
				int num = board[i][j] - '0';
				row[i][num] = true;
				col[j][num] = true;
				bucket[3 * (i / 3) + (j / 3)][num] = true;
			}
		}
	}
	// 当前来到(i,j)位置，如果已经有数字，就跳到下一个位置；如果没有数字，就尝试1~9，并且不能和row、col、bucket冲突
	private boolean dfs(char[][] board, int i, int j, boolean[][] row, boolean[][] col, boolean[][] bucket) {
		if (i == board.length) return true;
		// 当离开(i,j)位置时，应该去哪？(从左往右、从上往下)
		int nexti = j == 8 ? i + 1 : i, nextj = j == 8 ? 0 : j + 1;
		if (board[i][j] != '.') return dfs(board, nexti, nextj, row, col, bucket);
		int bid = 3 * (i / 3) + (j / 3);
		for (int num = 1; num <= 9; num++) {
			if (row[i][num] || col[j][num] || bucket[bid][num]) continue;
			row[i][num] = true;
			col[j][num] = true;
			bucket[bid][num] = true;
			board[i][j] = (char) (num + '0');
			if (dfs(board, nexti, nextj, row, col, bucket)) return true;
			row[i][num] = false;
			col[j][num] = false;
			bucket[bid][num] = false;
			board[i][j] = '.';
		}
		return false;
	}

}
