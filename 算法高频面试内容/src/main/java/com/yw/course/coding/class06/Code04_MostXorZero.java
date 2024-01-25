package com.yw.course.coding.class06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code04_MostXorZero {

	// 方法一：暴力解，时间复杂度 O(2^N)
	public static int mostXorZero0(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		int n = arr.length;
		// 预处理一个前缀异或和数组
		int[] eor = new int[n];
		eor[0] = arr[0];
		for (int i = 1; i < n; i++) eor[i] = eor[i - 1] ^ arr[i];
		return process(eor, 1, new ArrayList<>());
	}
	// 返回能得到最多异或和为0划分情况的最多划分数量
	// 当前来到idx位置去决定，是否将前面部分放入parts中(即是否在idx位置切分)
	private static int process(int[] eor, int idx, List<Integer> parts) {
		int ans = 0;
		if (idx == eor.length) {
			parts.add(eor.length);
			ans = eorZeroParts(eor, parts);
			// 深度优先遍历，恢复现场
			parts.remove(parts.size() - 1);
		} else {
			int p1 = process(eor, idx + 1, parts);
			parts.add(idx);
			int p2 = process(eor, idx + 1, parts);
			parts.remove(parts.size() - 1);
			ans = Math.max(p1, p2);
		}
		return ans;
	}
	private static int eorZeroParts(int[] eor, List<Integer> parts) {
		int ans = 0, l = 0;
		for (int r : parts) {
			if ((eor[r - 1] ^ (l == 0 ? 0 : eor[l - 1])) == 0) ans++;
			l = r;
		}
		return ans;
	}

	// 方法二：动态规划，时间复杂度O(N)的方法
	public static int mostXorZero(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		int n = arr.length;
		int[] dp = new int[n];
		// key: 某一个前缀异或和，value: 这个前缀异或和上次出现的最晚位置
		Map<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		int xor = 0; // 记录整体异或和
		for (int i = 0; i < n; i++) {
			xor ^= arr[i];
			if (map.containsKey(xor)) { // 可能性2
				int pre = map.get(xor);
				dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
			}
			if (i > 0) dp[i] = Math.max(dp[i - 1], dp[i]);
			map.put(xor, i);
		}
		return dp[n - 1];
	}

	public static void main(String[] args) {
		int testTime = 150000;
		int maxSize = 12;
		int maxValue = 5;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int res = mostXorZero(arr);
			int comp = mostXorZero0(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}
	private static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random());
		}
		return arr;
	}

}
