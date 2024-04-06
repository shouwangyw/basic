package com.yw.course.coding.class17;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 测试链接: https://leetcode.cn/problems/kth-smallest-element-in-a-sorted-matrix/
 * @author yangwei
 */
public class Code02_KthSmallestElementInSortedMatrix {

	// 堆的方法
	public static int kthSmallest1(int[][] matrix, int k) {
		int N = matrix.length;
		int M = matrix[0].length;
		PriorityQueue<Node> heap = new PriorityQueue<>(Comparator.comparingInt(o -> o.value));
		boolean[][] set = new boolean[N][M];
		heap.add(new Node(matrix[0][0], 0, 0));
		set[0][0] = true;
		int count = 0;
		Node ans = null;
		while (!heap.isEmpty()) {
			ans = heap.poll();
			if (++count == k) {
				break;
			}
			int row = ans.row;
			int col = ans.col;
			if (row + 1 < N && !set[row + 1][col]) {
				heap.add(new Node(matrix[row + 1][col], row + 1, col));
				set[row + 1][col] = true;
			}
			if (col + 1 < M && !set[row][col + 1]) {
				heap.add(new Node(matrix[row][col + 1], row, col + 1));
				set[row][col + 1] = true;
			}
		}
		return ans.value;
	}

	public static class Node {
		public int value;
		public int row;
		public int col;

		public Node(int v, int r, int c) {
			value = v;
			row = r;
			col = c;
		}

	}

	// 二分的方法
	public int kthSmallest(int[][] matrix, int k) {
		int n = matrix.length, l = matrix[0][0], r = matrix[n - 1][n - 1], mid, ans = 0;
		while (l <= r) {
			mid = l + ((r - l) / 2);
			Info info = getLessEqualK(matrix, mid);
			if (info.cnt < k) l = mid + 1;
			else {
				ans = info.val;
				r = mid - 1;
			}
		}
		return ans;
	}
	// 返回矩阵matrix中小于等于k的数有几个，以及最接近的数
	private Info getLessEqualK(int[][] matrix, int k) {
		int n = matrix.length, row = 0, col = n - 1;
		Info info = new Info(0, Integer.MIN_VALUE);
		while (row < n && col >= 0) {
			if (matrix[row][col] > k) col--;
			else {
				info.cnt += col + 1;
				info.val = Math.max(info.val, matrix[row][col]);
				row++;
			}
		}
		return info;
	}
	private static class Info {
		private int cnt;
		private int val;
		public Info(int cnt, int val) {
			this.cnt = cnt;
			this.val = val;
		}
	}

}
