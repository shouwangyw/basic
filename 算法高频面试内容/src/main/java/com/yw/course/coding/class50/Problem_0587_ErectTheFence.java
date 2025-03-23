package com.yw.course.coding.class50;

import java.util.Arrays;

/**
 * @author yangwei
 */
public class Problem_0587_ErectTheFence {

	public int[][] outerTrees(int[][] trees) {
		int n = trees.length, s = 0;
		int[][] stack = new int[n << 1][];
		// 横坐标x小的排前面，x一样的纵坐标y小的排前面
		Arrays.sort(trees, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
		// 依次遍历，若当前点在右侧，则直接出栈；否则入栈——围出上半区
		for (int i = 0; i < n; i++) {
			while (s > 1 && cross(stack[s - 2], stack[s - 1], trees[i]) > 0) s--;
			stack[s++] = trees[i];
		}
		// 再反向遍历，若当前点在右侧，则直接出栈；否则入栈——围出下半区
		for (int i = n - 2; i >= 0; i--) {
			while (s > 1 && cross(stack[s - 2], stack[s - 1], trees[i]) > 0) s--;
			stack[s++] = trees[i];
		}
		// 去重返回
		Arrays.sort(stack, 0, s, (a, b) -> a[0] == b[0] ? b[1] - a[1] : b[0] - a[0]);
		n = 1;
		for (int i = 1; i < s; i++) {
			// 若i点(x,y)，与i-1点(x,y)都一样
			// i点与i-1点，在同一个位置，此时，i点不保留
			if (stack[i][0] != stack[i - 1][0] || stack[i][1] != stack[i - 1][1])
				stack[n++] = stack[i];
		}
		return Arrays.copyOf(stack, n);
	}
	// 叉乘的实现
	// 假设有a、b、c三个点，并且给出每个点的(x,y)位置，从a到c的向量AC，在从a到b的向量AB的哪一侧？
	// AB = OB - OA = {b[0]-a[0], b[1]-a[1]} = {x1, y1}
	// AC = OC - OA = {c[0]-a[0], c[1]-a[1]} = {x2, y2}
	// 叉乘 AC×AB = x2*y1 - x1*y2
	// 如果a到c的向量，在从a到b的向量右侧，返回正数
	// 如果a到c的向量，在从a到b的向量左侧，返回负数
	// 如果a到c的向量，和从a到b的向量重合，返回0
	private static int cross(int[] a, int[] b, int[] c) {
		return (b[1] - a[1]) * (c[0] - b[0]) - (b[0] - a[0]) * (c[1] - b[1]);
	}

	public static void main(String[] args) {
		int[] a = { 4, 4 };
		int[] b = { 1, 1 };
		int[] c = { 1, 5 };
		System.out.println(cross(a, b, c));
	}

}
