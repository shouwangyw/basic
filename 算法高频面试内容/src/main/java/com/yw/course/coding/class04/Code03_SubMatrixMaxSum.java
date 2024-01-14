package com.yw.course.coding.class04;

/**
 * 测试链接: https://leetcode.cn/problems/max-submatrix-lcci/description/
 * @author yangwei
 */
public class Code03_SubMatrixMaxSum {

	// 时间复杂度 O(N^2 * M)
	public static int maxSum(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
		int n = matrix.length, m = matrix[0].length, max =  Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			// 准备一个辅助数组
			int[] arr = new int[m];
			// 包含且仅包含从i到j行: 0~0, 0~1, 0~2, ..., 1~1, 1~2, ...
			for (int j = i; j < n; j++) {
				for (int k = 0; k < m; k++) {
					arr[k] += matrix[j][k];
				}
				max = Math.max(max, maxSubArray(arr));
			}
		}
		return max;
	}
	private static int maxSubArray(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		int max = Integer.MIN_VALUE, cur = 0;
		for (int i = 0; i < arr.length; i++) {
			cur += arr[i];
			max = Math.max(max, cur);
			cur = cur < 0 ? 0 : cur;
		}
		return max;
	}


	public static int[] getMaxMatrix(int[][] matrix) {
		int n = matrix.length, m = matrix[0].length, max =  Integer.MIN_VALUE;
		int cur, begin, r1 = 0, c1 = 0, r2 = 0, c2 = 0;
		for (int i = 0; i < n; i++) {
			// 准备一个辅助数组
			int[] arr = new int[m];
			// 包含且仅包含从i到j行: 0~0, 0~1, 0~2, ..., 1~1, 1~2, ...
			for (int j = i; j < n; j++) {
				cur = 0;
				begin = 0;
				for (int k = 0; k < m; k++) {
					arr[k] += matrix[j][k];
					cur += arr[k];
					if (cur > max) {
						max = cur;
						r1 = i;
						c1 = begin;
						r2 = j;
						c2 = k;
					}
					if (cur < 0) {
						cur = 0;
						begin = k + 1;
					}
				}
			}
		}
		return new int[] { r1, c1, r2, c2 };
	}
}
