package com.yw.course.coding.class33;

/**
 * @author yangwei
 */
public class Problem_0251_Flatten2DVector {

	public static class Vector2D {
		private int[][] matrix;
		private int row;
		private int col;
		private boolean curUse;
		public Vector2D(int[][] matrix) {
			this.matrix = matrix;
			this.row = 0;
			this.col = -1;
			this.curUse = true;
			hasNext();
		}
		public boolean hasNext() {
			if (row == matrix.length) return false;
			if (!curUse) return true;
			// (row，col)用过了
			if (col < matrix[row].length - 1) col++;
			else {
				col = 0;
				do {
					row++;
				} while (row < matrix.length && matrix[row].length == 0);
			}
			// 新的(row，col)
			if (row != matrix.length) {
				curUse = false;
				return true;
			}
			return false;
		}
		public int next() {
			int ans = matrix[row][col];
			curUse = true;
			hasNext();
			return ans;
		}
	}
}
