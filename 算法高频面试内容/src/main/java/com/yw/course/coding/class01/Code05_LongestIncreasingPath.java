package com.yw.course.coding.class01;

/**
 * https://leetcode.cn/problems/fpTFWP/description/
 * @author yangwei
 */
public class Code05_LongestIncreasingPath {

	// 方法一：暴力尝试，超出时间限制
	public static int longestIncreasingPath0(int[][] matrix) {
		int ans = 0;
		// 枚举每一个出发位置，求一个最大值
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				ans = Math.max(ans, process(matrix, i, j));
			}
		}
		return ans;
	}
	// 返回 从m[i][j]开始走，走出来的最长递增链 长度
	private static int process(int[][] m, int i, int j) {
		// 遍历上下左右四个方向，从中求一个最大值
		int up = 0, down = 0, left = 0, right = 0;
		if (i > 0 && m[i][j] < m[i - 1][j]) up = process(m, i - 1, j);
		if (i < m.length - 1 && m[i][j] < m[i + 1][j]) down = process(m, i + 1, j);
		if (j > 0 && m[i][j] < m[i][j - 1]) left = process(m, i, j - 1);
		if (j < m[0].length - 1 && m[i][j] < m[i][j + 1]) right = process(m, i, j + 1);
		// +1: 算上当前自己
		return Math.max(Math.max(up, down), Math.max(left, right)) + 1;
	}

	// 方法二：基于暴力尝试 + 傻缓存
	public static int longestIncreasingPath(int[][] matrix) {
		int ans = 0;
		int[][] record = new int[matrix.length][matrix[0].length];
		// 枚举每一个出发位置，求一个最大值
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				ans = Math.max(ans, process(matrix, i, j, record));
			}
		}
		return ans;
	}
	// 返回 从m[i][j]开始走，走出来的最长递增链 长度
	private static int process(int[][] m, int i, int j, int[][] record) {
		if (record[i][j] != 0) return record[i][j];
		// 遍历上下左右四个方向，从中求一个最大值
		int up = 0, down = 0, left = 0, right = 0;
		if (i > 0 && m[i][j] < m[i - 1][j]) up = process(m, i - 1, j, record);
		if (i < m.length - 1 && m[i][j] < m[i + 1][j]) down = process(m, i + 1, j, record);
		if (j > 0 && m[i][j] < m[i][j - 1]) left = process(m, i, j - 1, record);
		if (j < m[0].length - 1 && m[i][j] < m[i][j + 1]) right = process(m, i, j + 1, record);
		// +1: 算上当前自己
		return record[i][j] = (Math.max(Math.max(up, down), Math.max(left, right)) + 1);
	}
}
