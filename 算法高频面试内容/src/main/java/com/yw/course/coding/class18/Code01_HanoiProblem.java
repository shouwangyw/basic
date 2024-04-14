package com.yw.course.coding.class18;

/**
 * @author yangwei
 */
public class Code01_HanoiProblem {

	// 方法一：递归版本
	public static int optimalStep(int[] arr) {
		if (arr == null || arr.length == 0) return -1;
		return process(arr, arr.length - 1, 1, 2, 3);
	}
	// 目标是: 把0~i的圆盘，从from全部挪到to上，返回，根据arr中的状态arr[0..i]，它是最优解的第几步
	private static int process(int[] arr, int i, int from, int to, int other) {
		if (i == -1) return 0;
		if (arr[i] != from && arr[i] != to) return -1;
		// arr[i] == from 第一大步没走完
		if (arr[i] == from) return process(arr, i - 1, from, to, other);
		// 已经走完1，2两步了
		int rest = process(arr, i - 1, other, from, to);
		return rest == -1 ? -1 : (1 << i) + rest;
	}

	// 方法二：迭代版本
	public static int optimalStep2(int[] arr) {
		if (arr == null || arr.length == 0) return -1;
		int from = 1, to = 3, other = 2, i = arr.length - 1, ans = 0, tmp;
		while (i >= 0) {
			if (arr[i] != from && arr[i] != to) return -1;
			if (arr[i] == to) {
				ans += 1 << i;
				tmp = from;
				from = other;
			} else {
				tmp = to;
				to = other;
			}
			other = tmp;
			i--;
		}
		return ans;
	}

	public static int kth(int[] arr) {
		int N = arr.length;
		return step(arr, N - 1, 1, 3, 2);
	}

	// 0...index这些圆盘，arr[0..index] index+1层塔
	// 在哪？from 去哪？to 另一个是啥？other
	// arr[0..index]这些状态，是index+1层汉诺塔问题的，最优解第几步
	public static int step(int[] arr, int index, int from, int to, int other) {
		if (index == -1) {
			return 0;
		}
		if (arr[index] == other) {
			return -1;
		}
		// arr[index] == from arr[index] == to;
		if (arr[index] == from) {
			return step(arr, index - 1, from, other, to);
		} else {
			int p1 = (1 << index) - 1;
			int p2 = 1;
			int p3 = step(arr, index - 1, other, to, from);
			if (p3 == -1) {
				return -1;
			}
			return p1 + p2 + p3;
		}
	}

	public static void main(String[] args) {
		int[] arr = { 3, 3, 2, 1 };
		System.out.println(optimalStep(arr));
		System.out.println(optimalStep2(arr));
		System.out.println(kth(arr));
	}
}
