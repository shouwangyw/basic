package com.yw.course.advance.class32;

/**
 * 测试链接：https://leetcode.cn/problems/range-sum-query-2d-mutable
 * @author yangwei
 */
// 但这个题是付费题目
// 提交时把类名、构造函数名从Code02_IndexTree2D改成NumMatrix
public class Code02_IndexTree2D {

	public class IndexTree2D {
		private int[][] tree;
		private int[][] nums;
		private int m;
		private int n;
		public IndexTree2D(int[][] matrix) {
			if (matrix.length == 0 || matrix[0].length == 0) return;
			this.m = matrix.length;
			this.n = matrix[0].length;
			this.tree = new int[m + 1][n + 1];
			this.nums = new int[m][n];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					update(i, j, matrix[i][j]);
				}
			}
		}
		public int sum(int row, int col) {
			int ans = 0;
			for (int i = row + 1; i > 0; i -= i & (-i)) {
				for (int j = col + 1; j > 0; j -= j & (-j)) {
					ans += tree[i][j];
				}
			}
			return ans;
		}
		public void update(int row, int col, int val) {
			if (m == 0 || n == 0) return;
			int add = val - nums[row][col];
			nums[row][col] = val;
			for (int i = row + 1; i <= m; i += i & (-i)) {
				for (int j = col + 1; j <= n; j += j & (-j)) {
					tree[i][j] += add;
				}
			}
		}
		/**
		 * 求二维数据区间 [row1, col1] ~ [row2, col2] 的累加和
		 */
		public int sumRegion(int row1, int col1, int row2, int col2) {
			if (m == 0 || n == 0) {
				return 0;
			}
			return sum(row2, col2) + sum(row1 - 1, col1 - 1) - sum(row1 - 1, col2) - sum(row2, col1 - 1);
		}
	}
}
