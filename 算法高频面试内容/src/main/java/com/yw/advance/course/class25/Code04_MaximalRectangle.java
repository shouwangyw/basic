package com.yw.advance.course.class25;

import java.util.Stack;

/**
 * 测试链接：https://leetcode.cn/problems/maximal-rectangle/
 * @author yangwei
 */
public class Code04_MaximalRectangle {

	public static int maximalRectangle(char[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
		int ans = 0, m = matrix.length, n = matrix[0].length;
		int[] heights = new int[n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				heights[j] = matrix[i][j] == '0' ? 0 : heights[j] + 1;
			}
			ans = Math.max(ans, rectangleMaxArea(heights));
		}
		return ans;
	}

	// heights 是直方图数组
	private static int rectangleMaxArea(int[] heights) {
		int maxArea = 0, n = heights.length;
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < n; i++) {
			while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
				int j = stack.pop(), k = stack.isEmpty() ? -1 : stack.peek();
				maxArea = Math.max(maxArea, heights[j] * (i - k - 1));
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int j = stack.pop(), k = stack.isEmpty() ? -1 : stack.peek();
			maxArea = Math.max(maxArea, heights[j] * (n - k - 1));
		}
		return maxArea;
	}
}
