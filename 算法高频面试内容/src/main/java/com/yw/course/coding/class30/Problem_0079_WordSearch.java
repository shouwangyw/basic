package com.yw.course.coding.class30;

/**
 * @author yangwei
 */
public class Problem_0079_WordSearch {

	public boolean exist(char[][] board, String word) {
		int m = board.length, n = board[0].length;
		if (m * n < word.length()) return false;
		boolean[][] visited = new boolean[m][n];
		char[] cs = word.toCharArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] != cs[0]) continue;
				visited[i][j] = true;
				if (process(board, i, j, cs, 1, visited)) return true;
				visited[i][j] = false;
			}
		}
		return false;
	}
	private static int[][] dirs = { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };
	private static boolean process(char[][] board, int i, int j, char[] cs, int idx, boolean[][] visited) {
		if (idx >= cs.length) return true;
		for (int[] dir : dirs) {
			int x = i + dir[0], y = j + dir[1];
			if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || visited[x][y] || board[x][y] != cs[idx]) continue;
			visited[x][y] = true;
			if (process(board, x, y, cs, idx + 1, visited)) return true;
			visited[x][y] = false;
		}
		return false;
	}

}
