package com.yw.course.coding.class24;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class Code04_Painting {
	// N * M的棋盘
	// 每种颜色的格子数必须相同的
	// 相邻格子染的颜色必须不同
	// 所有格子必须染色
	// 返回至少多少种颜色可以完成任务

	public static int minColorCnt0(int n, int m) {
		for (int i = 2; i < n * m; i++) {
			int[][] matrix = new int[n][m];
			// 需要的最少颜色数i，一定是N*M的某个因子
			if ((n * m) % i == 0 && check(matrix, n, m, i)) return i;
		}
		return n * m;
	}
	// 在matrix上染色，返回只用cnt种颜色是否可以做到要求
	private static boolean check(int[][] matrix, int n, int m, int cnt) {
		int all = n * m, one = all / cnt;
		List<Integer> rest = new ArrayList<>();
		rest.add(0);
		for (int i = 1; i <= cnt; i++) rest.add(one);
		return process(matrix, n, m, cnt, 0, 0, rest);
	}
	private static boolean process(int[][] matrix, int n, int m, int cnt, int r, int c, List<Integer> rest) {
		if (r == n) return true;
		if (c == m) return process(matrix, n, m, cnt, r + 1, 0, rest);
		int left = c == 0 ? 0 : matrix[r][c - 1], up = r == 0 ? 0 : matrix[r - 1][c];
		for (int color = 1; color <= cnt; color++) {
			int x = rest.get(color);
			if (color == left || color == up || x <= 0) continue;
			rest.set(color, x - 1);
			matrix[r][c] = color;
			if (process(matrix, n, m, cnt, r, c + 1, rest)) return true;
			rest.set(color, x);
			matrix[r][c] = 0;
		}
		return false;
	}

	public static int minColorCnt(int n, int m) {
		return (n * m) % 2 == 0 ? 2 : 3;
	}

	public static void main(String[] args) {
		// 根据代码16行的提示，打印出答案，看看是答案是哪个因子
		for (int N = 2; N < 10; N++) {
			for (int M = 2; M < 10; M++) {
				System.out.println("N   = " + N);
				System.out.println("M   = " + M);
				System.out.println("ans = " + minColorCnt(N, M));
				System.out.println("===========");
			}
		}
		// 打印答案，分析可知，是N*M最小的质数因子，原因不明，也不重要
		// 反正打表法猜出来了
	}

}
