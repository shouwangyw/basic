package com.yw.course.coding.class39;

import com.yw.util.SegmentTree;

/**
 * 来自京东
 * 给定一个二维数组matrix，matrix[i][j] = k代表x
 * 从(i,j)位置可以随意往右跳<=k步，或者从(i,j)位置可以随意往下跳<=k步
 * 如果matrix[i][j] = 0，代表来到(i,j)位置必须停止
 * 返回从matrix左上角到右下角，至少要跳几次
 * 已知matrix中行数n <= 5000, 列数m <= 5000
 * matrix中的值，<= 5000
 * 最弟弟的技巧也过了。最优解 -> dp+枚举优化(线段树，体系学习班)
 *
 * @author yangwei
 */
public class Code04_JumpGameOnMatrix {

	// 方法一：暴力尝试解
	public static int jumpGame0(int[][] matrix) {
		return process(matrix, 0, 0);
	}
	// 从[row][col]位置触发，跳到右下角，返回至少需要跳几次
	// 当前位置最多跳多远，由matrix[row][col]的值决定，只能向右或者向下
	private static int process(int[][] matrix, int row, int col) {
		// 如果已经到右下角
		if (row == matrix.length - 1 && col == matrix[0].length - 1) return 0;
		// 如果没到右下角，但可跳步数为0，则返回系统最大值
		if (matrix[row][col] == 0) return Integer.MAX_VALUE;
		// 如果没到右下角，且可以往下一个位置跳
		int next = Integer.MAX_VALUE;
		// 尝试向下跳的每一种可能
		for (int down = row + 1; down < matrix.length && (down - row) <= matrix[row][col]; down++)
			next = Math.min(next, process(matrix, down, col));
		// 尝试向右跳的每一种可能
		for (int up = col + 1; up < matrix[0].length && (up - col) <= matrix[row][col]; up++)
			next = Math.min(next, process(matrix, row, up));
		return next == Integer.MAX_VALUE ? next : (next + 1);
	}

	// 方法二：动态规划+线段树优化枚举行为
	public static int jumpGame(int[][] matrix) {
		int m = matrix.length, n = matrix[0].length;
		// 因为线段树下标从1开始，所以这里将原数组也转成从1开始，添加0行0列
		int[][] map = new int[m + 1][n + 1];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) map[i + 1][j + 1] = matrix[i][j];
		}
		// 初始化每一行、每一列的线段树数组
		SegmentTree[] rowTrees = new SegmentTree[m + 1], colTrees = new SegmentTree[n + 1];
		for (int i = 1; i <= m; i++) rowTrees[i] = new SegmentTree(n);
		for (int i = 1; i <= n; i++) colTrees[i] = new SegmentTree(m);
		rowTrees[m].update(n, n, 0, 1, n, 1);
		colTrees[n].update(m, m, 0, 1, m, 1);
		// 整体从下往上 + 每一行从右往左
		// 初始化第m行
		for (int col = n - 1; col >= 1; col--) {
			if (map[m][col] == 0) continue;
			int left = col + 1, right = Math.min(col + map[m][col], n);
			int next = rowTrees[m].query(left, right, 1, n, 1);
			if (next == Integer.MAX_VALUE) continue;
			rowTrees[m].update(col, col, next + 1, 1, n, 1);
			colTrees[col].update(m, m, next + 1, 1, m, 1);
		}
		// 初始化第n列
		for (int row = m - 1; row >= 1; row--) {
			if (map[row][n] == 0) continue;
			int up = row + 1, down = Math.min(row + map[row][n], m);
			int next = colTrees[n].query(up, down, 1, m, 1);
			if (next == Integer.MAX_VALUE) continue;
			rowTrees[row].update(n, n, next + 1, 1, n, 1);
			colTrees[n].update(row, row, next + 1, 1, m, 1);
		}
		// 递推中间部分
		for (int row = m - 1; row >= 1; row--) {
			for (int col = n - 1; col >= 1; col--) {
				if (map[row][col] == 0) continue;
				// (row,col) 往右是什么范围呢？[left,right]
				int left = col + 1, right = Math.min(col + map[row][col], n);
				int next = rowTrees[row].query(left, right, 1, n, 1);
				// (row,col) 往下是什么范围呢？[up,down]
				int up = row + 1, down = Math.min(row + map[row][col], m);
				next = Math.min(next, colTrees[col].query(up, down, 1, m, 1));
				if (next == Integer.MAX_VALUE) continue;
				rowTrees[row].update(col, col, next + 1, 1, n, 1);
				colTrees[col].update(row, row, next + 1, 1, m, 1);
			}
		}
		return rowTrees[1].query(1, 1, 1, n, 1);
	}

	// 为了测试
	public static void main(String[] args) {
		// 先展示一下线段树的用法，假设N=100
		// 初始化时，1~100所有位置的值都是系统最大
		System.out.println("线段树展示开始");
		int N = 100;
		SegmentTree st = new SegmentTree(N);
		// 查询8~19范围上的最小值
		System.out.println(st.query(8, 19, 1, N, 1));
		// 把6~14范围上对应的值都修改成56
		st.update(6, 14, 56, 1, N, 1);
		// 查询8~19范围上的最小值
		System.out.println(st.query(8, 19, 1, N, 1));
		// 以上是线段树的用法，你可以随意使用update和query方法
		// 线段树的详解请看体系学习班
		System.out.println("线段树展示结束");

		// 以下为正式测试
		int len = 10;
		int value = 8;
		int testTimes = 10000;
		System.out.println("对数器测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * len) + 1;
			int m = (int) (Math.random() * len) + 1;
			int[][] map = randomMatrix(n, m, value);
			int ans1 = jumpGame0(map);
			int ans2 = jumpGame(map);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("对数器测试结束");
	}
	// 为了测试
	public static int[][] randomMatrix(int n, int m, int v) {
		int[][] ans = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ans[i][j] = (int) (Math.random() * v);
			}
		}
		return ans;
	}

}
