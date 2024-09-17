package com.yw.course.coding.class34;

/**
 * @author yangwei
 */
public class Problem_0348_DesignTicTacToe {

	public class TicTacToe {
		private int[][] rows;
		private int[][] cols;
		private int[] leftUp;
		private int[] rightUp;
		private boolean[][] matrix;
		private int n;
		public TicTacToe(int n) {
			// rows[a][1]: 1这个人，在a行上，下了几个
			this.rows = new int[n][3];
			// cols[b][2]: 2这个人，在b列上，下了几个
			this.cols = new int[n][3];
			// leftUp[2] = 7: 2这个人，在左对角线上，下了7个
			this.leftUp = new int[3];
			// rightUp[1] = 9: 1这个人，在右对角线上，下了9个
			this.rightUp = new int[3];
			this.matrix = new boolean[n][n];
			this.n = n;
		}
		public int move(int row, int col, int player) {
			if (matrix[row][col]) return 0;
			matrix[row][col] = true;
			rows[row][player]++;
			cols[col][player]++;
			if (row == col) leftUp[player]++;
			if (row + col == n - 1) rightUp[player]++;
			if (rows[row][player] == n || cols[col][player] == n
					|| leftUp[player] == n || rightUp[player] == n)
				return player;
			return 0;
		}
	}

}
