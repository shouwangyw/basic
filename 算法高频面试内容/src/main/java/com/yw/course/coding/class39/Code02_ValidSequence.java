package com.yw.course.coding.class39;

import static com.yw.util.CommonUtils.printArray;
import static com.yw.util.CommonUtils.randomArray;

/**
 * 来自腾讯
 * 给定一个长度为n的数组arr，求有多少个子数组满足 :
 * 子数组两端的值，是这个子数组的最小值和次小值，最小值和次小值谁在最左和最右无所谓
 * n<=100000（10^5） n*logn  O(N)
 *
 * @author yangwei
 */
public class Code02_ValidSequence {
	
	public static int countSubArrays(int[] arr) {
		if (arr == null || arr.length < 2) return 0;
		int n = arr.length;
		// 定义一个单调栈，stack[i][0]: 值，stack[i][1]: 值出现的次数
		int[][] stack = new int[n][2];
		int ans = 0, size = 0;
		for (int i = 0; i < n; i++) {
			while (size != 0 && stack[size - 1][0] > arr[i]) {
				size--;
				ans += stack[size][1] + cn2(stack[size][1]);
			}
			if (size != 0 && stack[size - 1][0] == arr[i]) stack[size - 1][1]++;
			else {
				stack[size][0] = arr[i];
				stack[size++][1] = 1;
			}
		}
		while (size != 0) ans += cn2(stack[--size][1]);
		for (int i = n - 1; i >= 0; i--) {
			while (size != 0 && stack[size - 1][0] > arr[i])
				ans += stack[--size][1];
			if (size != 0 && stack[size - 1][0] == arr[i]) stack[size - 1][1]++;
			else {
				stack[size][0] = arr[i];
				stack[size++][1] = 1;
			}
		}
		return ans;
	}
	private static int cn2(int n) {
		return (n * (n - 1)) >> 1;
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 30;
		int v = 30;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int m = (int) (Math.random() * n);
			int[] arr = randomArray(m, v);
			int ans1 = countSubArrays(arr);
			int ans2 = test(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				printArray(arr);
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("测试结束");
	}
	// 为了测试
	// 暴力方法
	public static int test(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int ans = 0;
		for (int s = 0; s < arr.length; s++) {
			for (int e = s + 1; e < arr.length; e++) {
				int max = Math.max(arr[s], arr[e]);
				boolean valid = true;
				for (int i = s + 1; i < e; i++) {
					if (arr[i] < max) {
						valid = false;
						break;
					}
				}
				ans += valid ? 1 : 0;
			}
		}
		return ans;
	}
}
