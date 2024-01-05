package com.yw.course.advance.class24;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 测试链接：https://leetcode.cn/problems/gas-station
 * @author yangwei
 */
public class Code03_GasStation {

	// 这个方法的时间复杂度O(N)，额外空间复杂度O(N)
	public static int canCompleteCircuit(int[] gas, int[] cost) {
		boolean[] good = goodGas(gas, cost);
		for (int i = 0; i < gas.length; i++) {
			if (good[i]) return i;
		}
		return -1;
	}
	private static boolean[] goodGas(int[] gas, int[] cost) {
		int n = gas.length, m = (n << 1);
		int[] arr = new int[m];
		// 将gas、cost转换成一个数组
		for (int i = 0; i < n; i++) {
			arr[i] = gas[i] - cost[i];
			arr[i + n] = gas[i] - cost[i];
		}
		// 将arr转成累加和
		for (int i = 1; i < m; i++) arr[i] += arr[i - 1];
		// 定义一个双端队列，维护arr数组最小值位置
		Deque<Integer> qmin = new LinkedList<>();
		// 初始化 窗口长度n 范围内qmin
		for (int i = 0; i < n; i++) {
			while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[i]) qmin.pollLast();
			qmin.addLast(i);
		}
		boolean[] ans = new boolean[n];
		for (int offset = 0, i = 0, j = n; j < m; offset = arr[i++], j++) {
			if (arr[qmin.peekFirst()] - offset >= 0) ans[i] = true;
			if (qmin.peekFirst() == i) qmin.pollFirst();
			while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j]) qmin.pollLast();
			qmin.addLast(j);
		}
		return ans;
	}
}
