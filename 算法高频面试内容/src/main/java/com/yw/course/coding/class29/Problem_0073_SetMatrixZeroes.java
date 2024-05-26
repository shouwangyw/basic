package com.yw.course.coding.class29;

/**
 * @author yangwei
 */
public class Problem_0073_SetMatrixZeroes {

	// 方法一：
	public void setZeroes(int[][] matrix) {
		int m = matrix.length, n = matrix[0].length;
		// row[i] 表示第i行是否变0，col[j] 表示第j列是否变0
		boolean[] row = new boolean[m], col = new boolean[n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == 0) row[i] = col[j] = true;
			}
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (row[i] || col[j]) matrix[i][j] = 0;
			}
		}
	}

	// 方法二：
	public void setZeroes1(int[][] matrix) {
		// 单独判断第0行、第0列是否需要都变0
		boolean row0 = false, col0 = false;
		for (int j = 0; j < matrix[0].length; j++) {
			if (matrix[0][j] == 0) { row0 = true; break; }
		}
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i][0] == 0) { col0 = true; break; }
		}
		// 利用第0行、第0列标记其他位置是否都要变0
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 1; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][0] = 0;
					matrix[0][j] = 0;
				}
			}
		}
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 1; j < matrix[0].length; j++) {
				if (matrix[i][0] == 0 || matrix[0][j] == 0) matrix[i][j] = 0;
			}
		}
		if (row0) {
			for (int j = 0; j < matrix[0].length; j++) matrix[0][j] = 0;
		}
		if (col0) {
			for (int i = 0; i < matrix.length; i++) matrix[i][0] = 0;
		}
	}

	// 方法三：
	public void setZeroes2(int[][] matrix) {
		// 单独判断第0列是否需要都变0
		boolean col0 = false;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][0] = 0;
					if (j == 0) col0 = true;
					else matrix[0][j] = 0;
				}
			}
		}
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 1; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][0] = 0;
					matrix[0][j] = 0;
				}
			}
		}
		for (int i = matrix.length - 1; i >= 0; i--) {
			for (int j = 1; j < matrix[0].length; j++) {
				if (matrix[i][0] == 0 || matrix[0][j] == 0) matrix[i][j] = 0;
			}
		}
		if (col0) {
			for (int i = 0; i < matrix.length; i++) matrix[i][0] = 0;
		}
	}

}
