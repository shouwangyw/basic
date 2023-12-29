package com.yw.advance.course.class40;

/**
 * @author yangwei
 */
public class Code07_ZigZagPrintMatrix {

	public static void zigZagPrint(int[][] matrix) {
		int tr = 0, tc = 0, dr = 0, dc = 0, row = matrix.length - 1, col = matrix[0].length - 1;
		boolean up = false;
		while (tr != row + 1) {
			printLevel(matrix, tr, tc, dr, dc, up);
			tr = tc == col ? tr + 1 : tr;
			tc = tc == col ? tc : tc + 1;
			dc = dr == row ? dc + 1 : dc;
			dr = dr == row ? dr : dr + 1;
			up = !up;
		}
		System.out.println();
	}
	private static void printLevel(int[][] m, int tr, int tc, int dr, int dc, boolean up) {
		if (up) {
			while (tr != dr + 1) System.out.print(m[tr++][tc--] + " ");
		} else {
			while (dr != tr - 1) System.out.print(m[dr--][dc++] + " ");
		}
	}

	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		zigZagPrint(matrix);
	}
}
