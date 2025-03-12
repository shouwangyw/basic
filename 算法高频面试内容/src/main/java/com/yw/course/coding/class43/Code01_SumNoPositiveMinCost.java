package com.yw.course.coding.class43;

import org.omg.CORBA.MARSHAL;

import java.util.Arrays;

import static com.yw.util.CommonUtils.copyArray;
import static com.yw.util.CommonUtils.randomArray;

/**
 * 来自微软面试
 * 给定一个正数数组arr长度为n、正数x、正数y
 * 你的目标是让arr整体的累加和<=0
 * 你可以对数组中的数num执行以下三种操作中的一种，且每个数最多能执行一次操作 :
 * 1）不变
 * 2）可以选择让num变成0，承担x的代价
 * 3）可以选择让num变成-num，承担y的代价
 * 返回你达到目标的最小代价
 * 数据规模 : 面试时面试官没有说数据规模
 *
 * @author yangwei
 */
public class Code01_SumNoPositiveMinCost {

	// 方法一：从左往右的尝试模型
	public static int minCost0(int[] arr, int x, int y) {
		int sum = 0;
		for (int a : arr) sum += a;
		return process(arr, x, y, 0, sum);
	}
	// arr[i...]自由选择，每个位置的数可以执行3种操作，不变(代价0)、变0(代价x)、变相反数(代价y)
	// 返回使得累加和sum<=0的最小代价
	private static int process(int[] arr, int x, int y, int i, int sum) {
		if (sum <= 0) return 0;
		if (i == arr.length) return Integer.MAX_VALUE;
		// 1. 不变
		int cost = process(arr, x, y, i + 1, sum);
		// 2. 变0
		int next = process(arr, x, y, i + 1, sum - arr[i]);
		if (next != Integer.MAX_VALUE)
			cost = Math.min(cost, x + next);
		// 3. 变相反数
		next = process(arr, x, y, i + 1, sum - (arr[i] << 1));
		if (next != Integer.MAX_VALUE)
			cost = Math.min(cost, y + next);
		return cost;
	}

	// 方法二：贪心+不回退（最优解）
	public static int minCost(int[] arr, int x, int y) {
		Arrays.sort(arr);
		int n = arr.length;
		if (x >= y) { // 没有任何必要执行x操作
			int sum = 0;
			for (int a : arr) sum += a;
			int cost = 0;
			// 从大到小处理
			for (int i = n - 1; i >= 0 && sum > 0; i--) {
				sum -= arr[i] << 1;
				cost += y;
			}
			return cost;
		}
		int cost = n * x; 	// 全部数都执行x操作，最大代价
		int benefit = 0, holdSum = 0;
		for (int yLeft = n - 1, holdRight = 0; yLeft >= holdRight; yLeft--) {
			benefit += arr[yLeft];
			while (holdRight < yLeft && holdSum + arr[holdRight] <= benefit)
				holdSum += arr[holdRight++];
			// 0...holdRight x yLeft....
			cost = Math.min(cost, (n - yLeft) * y + (yLeft - holdRight) * x);
		}
		return cost;
	}

	// 贪心（最优解）
	public static int minOpStep2(int[] arr, int x, int y) {
		Arrays.sort(arr); // 小 -> 大
		int n = arr.length;
		for (int l = 0, r = n - 1; l <= r; l++, r--) {
			int tmp = arr[l];
			arr[l] = arr[r];
			arr[r] = tmp;
		}
		// arr 大 -> 小
		if (x >= y) { // 没有任何必要执行x操作
			int sum = 0;
			for (int num : arr) {
				sum += num;
			}
			int cost = 0;
			for (int i = 0; i < n && sum > 0; i++) {
				sum -= arr[i] << 1;
				cost += y;
			}
			return cost;
		} else {
			for (int i = n - 2; i >= 0; i--) {
				arr[i] += arr[i + 1];
			}
			int benefit = 0;
			// 注意，可以不二分，用不回退的方式！
			// 执行Y操作的数，有0个的时候
			int left = mostLeft(arr, 0, benefit);
			int cost = left * x;
			for (int i = 0; i < n - 1; i++) {
				// 0..i 这些数，都执行Y
				benefit += arr[i] - arr[i + 1];
				left = mostLeft(arr, i + 1, benefit);
				cost = Math.min(cost, (i + 1) * y + (left - i - 1) * x);
			}
			return cost;
		}
	}

	// arr是后缀和数组， arr[l...]中找到值<=v的最左位置
	public static int mostLeft(int[] arr, int l, int v) {
		int r = arr.length - 1;
		int m = 0;
		int ans = arr.length;
		while (l <= r) {
			m = (l + r) / 2;
			if (arr[m] <= v) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	// 不回退
	public static int minOpStep3(int[] arr, int x, int y) {
		// 系统排序，小 -> 大
		Arrays.sort(arr);
		int n = arr.length;
		// 如何变成 大 -> 小
		for (int l = 0, r = n - 1; l <= r; l++, r--) {
			int tmp = arr[l];
			arr[l] = arr[r];
			arr[r] = tmp;
		}
		if (x >= y) {
			int sum = 0;
			for (int num : arr) {
				sum += num;
			}
			int cost = 0;
			for (int i = 0; i < n && sum > 0; i++) {
				sum -= arr[i] << 1;
				cost += y;
			}
			return cost;
		} else {
			// 0个数执行Y
			int benefit = 0;
			// 全部的数都需要执行x，才能让累加和<=0
			int cost = arr.length * x;
			int holdSum = 0;
			for (int yRight = 0, holdLeft = n; yRight < holdLeft - 1; yRight++) {
				benefit += arr[yRight];
				while (holdLeft - 1 > yRight && holdSum + arr[holdLeft - 1] <= benefit) {
					holdSum += arr[holdLeft - 1];
					holdLeft--;
				}
				// 0...yRight x holdLeft....
				cost = Math.min(cost, (yRight + 1) * y + (holdLeft - yRight - 1) * x);
			}
			return cost;
		}
	}

	public static void main(String[] args) {
		int n = 12;
		int v = 20;
		int c = 10;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * n);
			int[] arr = randomArray(len, v);
			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int[] arr3 = copyArray(arr);
			int x = (int) (Math.random() * c);
			int y = (int) (Math.random() * c);
			int ans1 = minCost0(arr1, x, y);
			int ans2 = minOpStep2(arr2, x, y);
			int ans3 = minCost(arr3, x, y);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("出错了! ans1 = " + ans1 + ", ans2 = " + ans2 + ",  ans3 = " + ans3);
				break;
			}
		}
		System.out.println("测试结束");

	}

}
