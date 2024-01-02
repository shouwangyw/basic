package com.yw.advance.course.class46;

/**
 * 测试链接 : https://leetcode.cn/problems/remove-boxes/
 * @author yangwei
 */
public class Code02_RemoveBoxes {

	// 方法一：暴力尝试+记忆化搜索
	public static int removeBoxes0(int[] boxes) {
		int n = boxes.length;
		int[][][] record = new int[n][n][n];
		return process0(boxes, 0, n - 1, 0, record);
	}
	// 在[l,r]范围消除，前面有k个boxes[l]这样的数，返回消掉所有数的最大得分
	private static int process0(int[] boxes, int l, int r, int k, int[][][] record) {
		if (l > r) return 0;
		if (record[l][r][k] > 0) return record[l][r][k];
		int ans = process0(boxes, l + 1, r, 0, record) + (k + 1) * (k + 1);
		for (int i = l + 1; i <= r; i++) {
			if (boxes[i] == boxes[l]) {
				// 1. [l+1, i-1]范围0个boxes[l+1]这样的数
				// 2. [i, r]范围k+1个boxes[l]这样的数
				ans = Math.max(ans, process0(boxes, l + 1, i - 1, 0, record) + process0(boxes, i, r, k + 1, record));
			}
		}
		record[l][r][k] = ans;
		return ans;
	}

	// 方法二：暴力尝试优化版+记忆化搜索
	public static int removeBoxes(int[] boxes) {
		int n = boxes.length;
		int[][][] record = new int[n][n][n];
		return process(boxes, 0, n - 1, 0, record);
	}
	private static int process(int[] boxes, int l, int r, int k, int[][][] record) {
		if (l > r) return 0;
		if (record[l][r][k] > 0) return record[l][r][k];
		// 找到开头，
		// 1,1,1,1,1,5
		// 3 4 5 6 7 8
		//         !
		int last = l;
		while (last + 1 <= r && boxes[last + 1] == boxes[l]) last++;
		// k个1 -->> (k+last-l) 个
		int pre = k + last - l;
		int ans = (pre + 1) * (pre + 1) + process(boxes, last + 1, r, 0, record);
		for (int i = last + 2; i <= r; i++) {
			if (boxes[i] == boxes[l] && boxes[i - 1] != boxes[l]) {
				ans = Math.max(ans, process(boxes, last + 1, i - 1, 0, record) + process(boxes, i, r, pre + 1, record));
			}
		}
		record[l][r][k] = ans;
		return ans;
	}
}
