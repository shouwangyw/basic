package com.yw.course.advance.class40;

import java.util.Arrays;

/**
 * @author yangwei
 */
public class Code08_PrintStar {

	public static void printStar(int n) {
		int leftUp = 0, rightDown = n - 1;
		char[][] m = new char[n][n];
		for (char[] x : m) Arrays.fill(x, ' ');
		while (leftUp <= rightDown) {
			setStar(m, leftUp, rightDown);
			leftUp += 2;
			rightDown -= 2;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
	}
	private static void setStar(char[][] m, int leftUp, int rightDown) {
		// 右
		for (int col = leftUp; col <= rightDown; col++) m[leftUp][col] = '*';
		// 下
		for (int row = leftUp + 1; row <= rightDown; row++) m[row][rightDown] = '*';
		// 左
		for (int col = rightDown - 1; col > leftUp; col--) m[rightDown][col] = '*';
		// 上
		for (int row = rightDown - 1; row > leftUp + 1; row--) m[row][leftUp + 1] = '*';
	}

	public static void main(String[] args) {
		printStar(5);
		System.out.println("---------------------");
		printStar(10);
	}
}
