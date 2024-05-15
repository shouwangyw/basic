package com.yw.course.coding.class24;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author yangwei
 */
public class Code01_Split4Parts {

	public static boolean canSplit(int[] arr) {
		if (arr == null || arr.length < 7) return false;
		int n = arr.length;
		int[] prefixSum = new int[n + 1];
		// key: 某一个累加和，value: 这个累加和出现的位置
		Map<Integer, Integer> prefixMap = new HashMap<>();
		for (int i = 0; i < n; i++) prefixMap.put(prefixSum[i + 1] = prefixSum[i] + arr[i], i + 1);
		// s1是第一个分割点
		for (int s1 = 1; s1 < n - 5; s1++) {
			int x = prefixSum[s1], checkSum = 2 * x + arr[s1];
			// s2是第二个分割点
			Integer s2 = prefixMap.get(checkSum);
			if (s2 == null || s2 <=2 || s2 >= n - 3) continue;
			checkSum += x + arr[s2];
			// s3是第三个分割点
			Integer s3 = prefixMap.get(checkSum);
			if (s3 == null || s3 <= 4 || s3 >= n - 1) continue;
			checkSum += x + arr[s3];
			if (checkSum == prefixSum[n]) return true;
		}
		return false;
	}

	public static boolean canSplits1(int[] arr) {
		if (arr == null || arr.length < 7) {
			return false;
		}
		HashSet<String> set = new HashSet<String>();
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		int leftSum = arr[0];
		for (int i = 1; i < arr.length - 1; i++) {
			set.add(leftSum + "_" + (sum - leftSum - arr[i]));
			leftSum += arr[i];
		}
		int l = 1;
		int lsum = arr[0];
		int r = arr.length - 2;
		int rsum = arr[arr.length - 1];
		while (l < r - 3) {
			if (lsum == rsum) {
				String lkey = String.valueOf(lsum * 2 + arr[l]);
				String rkey = String.valueOf(rsum * 2 + arr[r]);
				if (set.contains(lkey + "_" + rkey)) {
					return true;
				}
				lsum += arr[l++];
			} else if (lsum < rsum) {
				lsum += arr[l++];
			} else {
				rsum += arr[r--];
			}
		}
		return false;
	}

	public static boolean canSplits2(int[] arr) {
		if (arr == null || arr.length < 7) {
			return false;
		}
		// key 某一个累加和， value出现的位置
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int sum = arr[0];
		for (int i = 1; i < arr.length; i++) {
			map.put(sum, i);
			sum += arr[i];
		}
		int lsum = arr[0]; // 第一刀左侧的累加和
		for (int s1 = 1; s1 < arr.length - 5; s1++) { // s1是第一刀的位置
			int checkSum = lsum * 2 + arr[s1]; // 100 x 100   100*2 + x
			if (map.containsKey(checkSum)) {
				int s2 = map.get(checkSum); // j -> y
				checkSum += lsum + arr[s2];
				if (map.containsKey(checkSum)) { // 100 * 3 + x + y
					int s3 = map.get(checkSum); // k -> z
					if (checkSum + arr[s3] + lsum == sum) {
						return true;
					}
				}
			}
			lsum += arr[s1];
		}
		return false;
	}

	public static void main(String[] args) {
		int testTime = 3000000;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray();
			if (canSplits1(arr) ^ canSplits2(arr) || canSplit(arr) ^ canSplits2(arr)) {
				System.out.println("Error");
				break;
			}
		}
		System.out.println("测试结束");
	}
	private static int[] generateRandomArray() {
		int[] res = new int[(int) (Math.random() * 10) + 7];
		for (int i = 0; i < res.length; i++) {
			res[i] = (int) (Math.random() * 10) + 1;
		}
		return res;
	}
}
