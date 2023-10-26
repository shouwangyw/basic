package com.yw.advance.course.class25;

import java.util.Stack;

/**
 * @author yangwei
 */
public class Code02_AllTimesMinToMax {

	// 方法一：暴力枚举每一个子数组，计算指标A(子数组累加和*子数组最小值)
	public static int maxIndicatorA0(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				int sum = 0, minNum = Integer.MAX_VALUE;
				for (int k = i; k <= j; k++) {
					sum += arr[k];
					minNum = Math.min(minNum, arr[k]);
				}
				max = Math.max(max, sum * minNum);
			}
		}
		return max;
	}

	// 方法二：单调栈+前缀和
	public static int maxIndicatorA(int[] arr) {
		int n = arr.length, max = Integer.MIN_VALUE;
		int[] sums = new int[n + 1];
		for (int i = 0; i < n; i++) sums[i + 1] = sums[i] + arr[i];
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < n; i++) {
			// >=: 可能存在重复值，会导致计算错误，但是最后一个相等值会计算正确
			while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
				int j = stack.pop();
				max = Math.max(max, arr[j] * (stack.isEmpty() ? sums[i] : (sums[i] - sums[stack.peek() + 1])));
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int j = stack.pop();
			max = Math.max(max, arr[j] * (stack.isEmpty() ? sums[n] : (sums[n] - sums[stack.peek() + 1])));
		}
		return max;
	}

	public static void main(String[] args) {
		int testTimes = 2000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = generateRandomArray();
			if (maxIndicatorA0(arr) != maxIndicatorA(arr)) {
				System.out.println("FUCK!");
				break;
			}
		}
		System.out.println("test finish");
	}
	private static int[] generateRandomArray() {
		int[] arr = new int[(int) (Math.random() * 20) + 10];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 101);
		}
		return arr;
	}
}
