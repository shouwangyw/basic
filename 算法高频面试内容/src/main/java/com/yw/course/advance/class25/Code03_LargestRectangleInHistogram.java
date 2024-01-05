package com.yw.course.advance.class25;

import java.util.Stack;

/**
 * 测试链接：https://leetcode.cn/problems/largest-rectangle-in-histogram
 * @author yangwei
 */
public class Code03_LargestRectangleInHistogram {

	// 方法一：利用单调栈结构，依次遍历每一个高度，计算基于每一个高度所能组成的矩形面积，求最大值
	// 利用单调栈结构，可以很方便得到 每一个高度 位置j离它最近且小于它的高度的 左右两边的位置(l, r)，面积=heights[j]*(r-1-l)
	public static int largestRectangleArea(int[] heights) {
		if (heights == null || heights.length == 0) return 0;
		int maxArea = 0;
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < heights.length; i++) {
			while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
				int j = stack.pop(), left = stack.isEmpty() ? -1 : stack.peek();
				maxArea = Math.max(maxArea, heights[j] * (i - 1 - left));
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int j = stack.pop(), left = stack.isEmpty() ? -1 : stack.peek();
			maxArea = Math.max(maxArea, heights[j] * (heights.length - 1 - left));
		}
		return maxArea;
	}

	// 方法二：基于数组实现单调栈
	public static int largestRectangleArea1(int[] heights) {
		if (heights == null || heights.length == 0) return 0;
		// si用于数组栈元素访问，-1: 表示栈空
		int n = heights.length, maxArea = 0, si = -1;
		int[] stack = new int[n];
		for (int i = 0; i < n; i++) {
			while (si != -1 && heights[stack[si]] >= heights[i]) {
				int j = stack[si--], k = si == -1 ? -1 : stack[si];
				maxArea = Math.max(maxArea, heights[j] * (i - k - 1));
			}
			stack[++si] = i;
		}
		while (si != -1) {
			int j = stack[si--], k = si == -1 ? -1 : stack[si];
			maxArea = Math.max(maxArea, heights[j] * (n - k - 1));
		}
		return maxArea;
	}

	// 方法三：
	public int largestRectangleArea2(int[] heights) {
		int n = heights.length, maxArea = 0;
		// arr记录每个位置i，离它最近且小于arr[i]的位置
		int[][] arr = new int[n][2];
		for (int i = 0; i < arr.length; i++) {
			arr[i][0] = -1;
			arr[i][1] = n;
		}
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < n; i++) {
			while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
				arr[stack.pop()][1] = i;
			}
			if (!stack.isEmpty()) arr[i][0] = stack.peek();
			stack.push(i);
		}
		for (int i = 0; i < n; i++) {
			maxArea = Math.max(maxArea, heights[i] * (arr[i][1] - arr[i][0] - 1));
		}
		return maxArea;
	}
}
