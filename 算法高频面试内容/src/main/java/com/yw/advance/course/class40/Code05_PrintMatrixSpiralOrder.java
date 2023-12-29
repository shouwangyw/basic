package com.yw.advance.course.class40;

/**
 * @author yangwei
 */
public class Code05_PrintMatrixSpiralOrder {

	public static void spiralOrderPrint(int[][] matrix) {
		// 左上角: tr、tc
		// 右下角: dr、dc
		int tr = 0, tc = 0, dr = matrix.length - 1, dc = matrix[0].length - 1;
		while (tr <= dr && tc <= dc) {
			printEdge(matrix, tr++, tc++, dr--, dc--);
		}
	}
	private static void printEdge(int[][] m, int tr, int tc, int dr, int dc) {
		if (tr == dr) {
			for (int col = tc; col <= dc; col++) {
				System.out.print(m[tr][col] + " ");
			}
		} else if (tc == dc) {
			for (int row = tr; row <= dr; row++) {
				System.out.print(m[row][tc] + " ");
			}
		} else {
			int row = tr, col = tc;
			// 右
			while (col != dc) System.out.print(m[tr][col++] + " ");
			// 下
			while (row != dr) System.out.print(m[row++][dc] + " ");
			// 左
			while (col != tc) System.out.print(m[dr][col--] + " ");
			// 上
			while (row != tr) System.out.print(m[row--][tc] + " ");
		}
	}

	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
//		int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		spiralOrderPrint(matrix);
	}

}
