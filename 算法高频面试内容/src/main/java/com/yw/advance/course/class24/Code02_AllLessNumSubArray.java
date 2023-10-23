package com.yw.advance.course.class24;

import java.util.Deque;
import java.util.LinkedList;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code02_AllLessNumSubArray {

	// 方法一：暴力枚举每个子数组，求最大最小值，判断是否达标
	public static int getNum0(int[] arr, int sum) {
		if (arr == null || arr.length == 0 || sum < 0) return 0;
		int n = arr.length, ans = 0;
		for (int l = 0; l < n; l++) {
			for (int r = l; r < n; r++) {
				int min = arr[l], max = arr[l];
				for (int i = l + 1; i <= r; i++) {
					min = Math.min(min, arr[i]);
					max = Math.max(max, arr[i]);
				}
				if (max - min <= sum) ans++;
			}

		}
		return ans;
	}

	// 方法二：滑动窗口
	public static int getNum(int[] arr, int sum) {
		if (arr == null || arr.length == 0 || sum < 0) return 0;
		int n = arr.length, ans = 0;
		// 定义两个双端队列，分别存最小值、最大值的位置
		Deque<Integer> qmin = new LinkedList<>();
		Deque<Integer> qmax = new LinkedList<>();
		for (int l = 0, r = 0; l < n; l++) {
			// 扩右边界，至初次不达标为止
			while (r < n) {
				while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[r]) qmax.pollLast();
				qmax.addLast(r);
				while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[r]) qmin.pollLast();
				qmin.addLast(r);
				if (arr[qmax.peekFirst()] - arr[qmin.peekFirst()] > sum) break;
				else r++;
			}
			ans += r - l;
			// 检查队列头部是否过期，过期则从头部弹出
			if (qmax.peekFirst() == l) qmax.pollFirst();
			if (qmin.peekFirst() == l) qmin.pollFirst();
		}
		return ans;
	}

	public static void main(String[] args) {
		int maxLen = 100;
		int maxValue = 200;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxLen, maxValue);
			int sum = (int) (Math.random() * (maxValue + 1));
			int ans1 = getNum0(arr, sum);
			int ans2 = getNum(arr, sum);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				printArray(arr);
				System.out.println(sum);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}
}
