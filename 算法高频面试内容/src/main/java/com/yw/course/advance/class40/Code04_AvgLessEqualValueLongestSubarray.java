package com.yw.course.advance.class40;

import java.util.TreeMap;

import static com.yw.course.advance.class40.Code03_LongestLessSumSubArrayLength.maxLengthSubArraySum;
import static com.yw.util.CommonUtils.copyArray;
import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code04_AvgLessEqualValueLongestSubarray {

	// 暴力解，时间复杂度O(N^3)，用于做对数器
	public static int ways1(int[] arr, int v) {
		int ans = 0;
		for (int L = 0; L < arr.length; L++) {
			for (int R = L; R < arr.length; R++) {
				int sum = 0;
				int k = R - L + 1;
				for (int i = L; i <= R; i++) {
					sum += arr[i];
				}
				double avg = (double) sum / (double) k;
				if (avg <= v) {
					ans = Math.max(ans, k);
				}
			}
		}
		return ans;
	}

	// 想实现的解法2，时间复杂度O(N*logN)
	public static int ways2(int[] arr, int v) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		TreeMap<Integer, Integer> origins = new TreeMap<>();
		int ans = 0;
		int modify = 0;
		for (int i = 0; i < arr.length; i++) {
			int p1 = arr[i] <= v ? 1 : 0;
			int p2 = 0;
			int querry = -arr[i] - modify;
			if (origins.floorKey(querry) != null) {
				p2 = i - origins.get(origins.floorKey(querry)) + 1;
			}
			ans = Math.max(ans, Math.max(p1, p2));
			int curOrigin = -modify - v;
			if (origins.floorKey(curOrigin) == null) {
				origins.put(curOrigin, i);
			}
			modify += arr[i] - v;
		}
		return ans;
	}

	// 想实现的解法3，时间复杂度O(N)
	public static int maxLengthSubArrayAvg(int[] arr, int v) {
		if (arr == null || arr.length == 0) return 0;
		for (int i = 0; i < arr.length; i++) arr[i] -= v;
		return maxLengthSubArraySum(arr, 0);
	}

	public static void main(String[] args) {
		System.out.println("测试开始");
		int maxLen = 20;
		int maxValue = 100;
		int testTime = 500000;
		for (int i = 0; i < testTime; i++) {
			int[] arr = randomArray(maxLen, maxValue);
			int value = (int) (Math.random() * maxValue);
			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int[] arr3 = copyArray(arr);
			int ans1 = ways1(arr1, value);
			int ans2 = ways2(arr2, value);
			int ans3 = maxLengthSubArrayAvg(arr3, value);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("测试出错！");
				System.out.print("测试数组：");
				printArray(arr);
				System.out.println("子数组平均值不小于 ：" + value);
				System.out.println("方法1得到的最大长度：" + ans1);
				System.out.println("方法2得到的最大长度：" + ans2);
				System.out.println("方法3得到的最大长度：" + ans3);
				System.out.println("=========================");
			}
		}
		System.out.println("测试结束");
	}
	// 用于测试
	public static int[] randomArray(int maxLen, int maxValue) {
		int len = (int) (Math.random() * maxLen) + 1;
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * maxValue);
		}
		return ans;
	}
}
