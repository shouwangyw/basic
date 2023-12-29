package com.yw.advance.course.class40;

/**
 * @author yangwei
 */
public class Code06_RotateMatrix {

	public static void rotate(int[][] matrix) {
		int tr = 0, tc = 0, dr = matrix.length - 1, dc = matrix[0].length - 1;
		while (tr < dr) {
			rotateEdge(matrix, tr++, tc++, dr--, dc--);
		}
	}
	private static void rotateEdge(int[][] m, int tr, int tc, int dr, int dc) {
		for (int i = 0; i < dc - tc; i++) {
			int tmp = m[tr][tc + i];
			m[tr][tc + i] = m[dr - i][tc];
			m[dr - i][tc] = m[dr][dc - i];
			m[dr][dc - i] = m[tr + i][dc];
			m[tr + i][dc] = tmp;
		}
	}

	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
		printMatrix(matrix);
		rotate(matrix);
		System.out.println("=========");
		printMatrix(matrix);

	}

	public static void printMatrix(int[][] matrix) {
		for (int i = 0; i != matrix.length; i++) {
			for (int j = 0; j != matrix[0].length; j++) {
				System.out.printf("%5d", matrix[i][j]);
			}
			System.out.println();
		}
	}
}
