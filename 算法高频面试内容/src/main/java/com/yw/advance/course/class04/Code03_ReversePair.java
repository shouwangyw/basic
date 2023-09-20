package com.yw.advance.course.class04;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code03_ReversePair {

	public static int reverseOrderPair(int[] arr) {
		if (arr == null || arr.length < 2) return 0;
		return mergeSort(arr, 0, arr.length - 1);
	}

	private static int mergeSort(int[] arr, int l, int r) {
		if (l == r) return 0;
		int mid = l + ((r - l) >> 1);
		int lReverseOrder = mergeSort(arr, l, mid);
		int rReverseOrder = mergeSort(arr, mid + 1, r);
		int[] tmp = new int[r - l + 1];
		int reverseOrder = 0, i = mid, j = r, k = tmp.length - 1;
		while (i >= l && j > mid) {
			if (arr[i] > arr[j]) {
				reverseOrder += (j - mid);
				tmp[k--] = arr[i--];
			} else tmp[k--] = arr[j--];
		}
		while (i >= l) tmp[k--] = arr[i--];
		while (j > mid) tmp[k--] = arr[j--];
		for (k = 0; k < tmp.length; k++) arr[l + k] = tmp[k];
		return reverseOrder + lReverseOrder + rReverseOrder;
	}

	public static int reverPairNumber(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		return process(arr, 0, arr.length - 1);
	}

	// arr[L..R]既要排好序，也要求逆序对数量返回
	// 所有merge时，产生的逆序对数量，累加，返回
	// 左 排序 merge并产生逆序对数量
	// 右 排序 merge并产生逆序对数量
	public static int process(int[] arr, int l, int r) {
		if (l == r) {
			return 0;
		}
		// l < r
		int mid = l + ((r - l) >> 1);
		return process(arr, l, mid) + process(arr, mid + 1, r) + merge(arr, l, mid, r);
	}

	public static int merge(int[] arr, int L, int m, int r) {
		int[] help = new int[r - L + 1];
		int i = help.length - 1;
		int p1 = m;
		int p2 = r;
		int res = 0;
		while (p1 >= L && p2 > m) {
			res += arr[p1] > arr[p2] ? (p2 - m) : 0;
			help[i--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
		}
		while (p1 >= L) {
			help[i--] = arr[p1--];
		}
		while (p2 > m) {
			help[i--] = arr[p2--];
		}
		for (i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}
		return res;
	}

	// for test
	public static int comparator(int[] arr) {
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
					ans++;
				}
			}
		}
		return ans;
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			int ans1 = reverseOrderPair(arr1);
			int ans2 = comparator(arr2);
			if (ans1 != ans2 || ans1 < 0 || ans2 < 0) {
				System.out.println("Oops!");
				System.out.println("ans1 = " + ans1 + ", ans2 = " + ans2);
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}
