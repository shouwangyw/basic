package com.yw.course.coding.class32;

import java.util.Arrays;

import static com.yw.util.CommonUtils.randomArray;
import static com.yw.util.CommonUtils.swap;

/**
 * 给定一个数组arr，arr[i] = j，表示第i号试题的难度为j。给定一个非负数M
 * 想出一张卷子，对于任何相邻的两道题目，前一题的难度不能超过后一题的难度+M
 * 返回所有可能的卷子种数
 * @author yangwei
 */
public class SequenceM {

	// 方法一：纯暴力方法，生成所有排列，一个一个验证
	public static int ways1(int[] arr, int m) {
		if (arr == null || arr.length == 0) return 0;
		return process(arr, 0, m);
	}
	private static int process(int[] arr, int idx, int m) {
		if (idx == arr.length) {
			for (int i = 1; i < idx; i++) {
				if (arr[i - 1] > arr[i] + m) return 0;
			}
			return 1;
		}
		int ans = 0;
		for (int i = idx; i < arr.length; i++) {
			swap(arr, idx, i);
			ans += process(arr, idx + 1, m);
			swap(arr, idx, i);
		}
		return ans;
	}

	// 方法二：从左往右的尝试模型，动态规划+范围上的二分，时间复杂度 O(N * logN)
	public static int ways2(int[] arr, int m) {
		if (arr == null || arr.length == 0) return 0;
		Arrays.sort(arr);
		int ans = 1;
		for (int i = 1; i < arr.length; i++) {
			ans = ans * (getWays(arr, i - 1, arr[i] - m) + 1);
		}
		return ans;
	}
	// arr[0...r]上返回>=t的数有几个，二分的方法
	// 找到 >=t 最左的位置a, 然后返回r - a + 1就是个数
	private static int getWays(int[] arr, int r, int t) {
		int i = 0, j = r, mid, a = r + 1;
		while (i <= j) {
			mid = (i + j) / 2;
			if (arr[mid] >= t) {
				a = mid;
				j = mid - 1;
			} else i = mid + 1;
		}
		return r - a + 1;
	}

	// 方法三：从左往右的动态规划 + IndexTree，时间复杂度O(N * logV)
	public static int ways3(int[] arr, int m) {
		if (arr == null || arr.length == 0) return 0;
		int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
		for (int num : arr) {
			max = Math.max(max, num);
			min = Math.min(min, num);
		}
		IndexTree tree = new IndexTree(max - min + 2);
		// 排序是为了保证之前不合法的试卷，再之后不可能变合法
		Arrays.sort(arr);
		int a, b, ans = 1;
		tree.add(arr[0] - min + 1, 1);
		for (int i = 1; i < arr.length; i++) {
			a = arr[i] - min + 1;
			b = i - (a - m - 1 >= 1 ? tree.sum(a - m - 1) : 0);
			ans = ans * (b + 1);
			tree.add(a, 1);
		}
		return ans;
	}
	// 注意开始下标是1，不是0
	public static class IndexTree {
		private int[] tree;
		private int n;
		public IndexTree(int size) {
			this.n = size;
			this.tree = new int[n + 1];
		}
		public int sum(int index) {
			int ret = 0;
			while (index > 0) {
				ret += tree[index];
				index -= index & -index;
			}
			return ret;
		}
		public void add(int index, int d) {
			while (index <= n) {
				tree[index] += d;
				index += index & -index;
			}
		}
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 10;
		int value = 20;
		int testTimes = 1000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int len = (int) (Math.random() * (N + 1));
			int[] arr = randomArray(len, value);
			int m = (int) (Math.random() * (value + 1));
			int ans1 = ways1(arr, m);
			int ans2 = ways2(arr, m);
			int ans3 = ways3(arr, m);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("出错了!");
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
			}
		}
		System.out.println("测试结束");
	}

}
