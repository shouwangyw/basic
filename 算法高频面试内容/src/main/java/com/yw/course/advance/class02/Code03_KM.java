package com.yw.course.advance.class02;

import java.util.HashMap;
import java.util.Map;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code03_KM {
	/**
	 * 请保证arr中，只有一种数出现了K次，其他数都出现了M次
	 */
	public static int onlyKTimes(int[] arr, int k, int m) {
		int intBits = 32;
		int[] bitCounter = new int[intBits];
		// bitCounter[0]: 0 位置的1出现了几个
		// bitCounter[i]: i 位置的1出现了几个
		for (int x : arr) {
			for (int i = 0; i < intBits; i++) {
//				if (((x >> i) & 1) != 0) {
//					bitCounter[i] += 1;
//				}
				bitCounter[i] += (x >> i) & 1;
			}
		}
		int ans = 0;
		for (int i = 0; i < intBits; i++) {
//			if (bitCounter[i] % m != 0) {
//				// 在第i位上有1
//				ans |= (1 << i);
//			}

			// 题目变一下，如果能找到出现K次的数，就返回该数；如果找不到，返回-1。
			if (bitCounter[i] % m == 0) continue;
			if (bitCounter[i] % m == k) ans |= (1 << i);
			else return -1;
		}
		// 边界处理
		if (ans == 0) {
			int cnt = 0;
			for (int x : arr) {
				if (x == 0) cnt++;
			}
			if (cnt != k) return -1;
		}
		return ans;
	}

//	public static Map<Integer, Integer> map = new HashMap<>();

//	public static int onlyKTimes(int[] arr, int k, int m) {
//		if (map.size() == 0) {
//			mapCreater(map);
//		}
//		int[] t = new int[32];
//		// t[0] 0位置的1出现了几个
//		// t[i] i位置的1出现了几个
//		for (int num : arr) {
//			while (num != 0) {
//				int rightOne = num & (-num);
//				t[map.get(rightOne)]++;
//				num ^= rightOne;
//			}
//		}
//		int ans = 0;
//		for (int i = 0; i < 32; i++) {
//			if (t[i] % m != 0) {
//				if (t[i] % m == k) {
//					ans |= (1 << i);
//				} else {
//					return -1;
//				}
//			}
//		}
//		if (ans == 0) {
//			int count = 0;
//			for (int num : arr) {
//				if (num == 0) {
//					count++;
//				}
//			}
//			if (count != k) {
//				return -1;
//			}
//		}
//		return ans;
//	}
//	public static void mapCreater(Map<Integer, Integer> map) {
//		int value = 1;
//		for (int i = 0; i < 32; i++) {
//			map.put(value, i);
//			value <<= 1;
//		}
//	}

	public static void main(String[] args) {
//		int[] arr = {-1, 3, 1, 3, 3, 1, 1, -1};
//		int k = 2;
//		int m = 3;
//		System.out.println(onlyKTimes(arr, k, m));

		int kinds = 5;
		int range = 30;
		int testTime = 100000;
		int max = 9;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int a = random1ToMax(max);
			int b = random1ToMax(max);
			int k = Math.min(a, b);
			int m = Math.max(a, b);
			if (k == m) {
				// 保证 k < m
				m++;
			}
			int[] arr = randomArrayOnlyOneKTimes(kinds, range, k, m);
			int ans1 = test(arr, k, m);
			int ans2 = onlyKTimes(arr, k, m);
			if (ans1 != ans2) {
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");
	}

	// for test
	public static int test(int[] arr, int k, int m) {
		Map<Integer, Integer> counter = new HashMap<>();
		for (int x : arr) {
			counter.compute(x, (key, v) -> v == null ? 1 : v + 1);
		}
		for (Integer num : counter.keySet()) {
			if (counter.get(num) == k) {
				return num;
			}
		}
		return -1;
	}
}
