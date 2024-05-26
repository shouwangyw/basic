package com.yw.course.coding.class28;

/**
 * @author yangwei
 */
public class Problem_0036_ValidSudoku {

	public boolean isValidSudoku(char[][] board) {
		// 定义三个二维数组(第一维是行号、列号、桶号，第一维是数字)，分别检查每行、每列、每个桶数字有没有重复出现
		boolean[][] row = new boolean[9][10], col = new boolean[9][10], bucket = new boolean[9][10];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == '.') continue;
				int num = board[i][j] - '0', bid = 3 * (i / 3) + (j / 3);
				// 如果数字num在这一行、这一列或这个桶位置出现过，则返回false
				if (row[i][num] || col[j][num] || bucket[bid][num]) return false;
				// 否则，设置为true
				row[i][num] = true;
				col[j][num] = true;
				bucket[bid][num] = true;
			}
		}
		return true;
	}

}
