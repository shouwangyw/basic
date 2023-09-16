package com.yw.basic.course.class08;

import static com.yw.util.CommonUtils.*;

public class Code02_MergeSort {

	// 递归方法实现
	public static void mergeSort1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}

	// arr[L...R]范围上，请让这个范围上的数，有序！
	public static void process(int[] arr, int L, int R) {
		if (L == R) {
			return;
		}
		// int mid = (L + R) / 2
		int mid = L + ((R - L) >> 1);
		process(arr, L, mid);
		process(arr, mid + 1, R);
		merge(arr, L, mid, R);
	}

	public static void merge(int[] arr, int L, int M, int R) {
		int[] help = new int[R - L + 1];
		int i = 0;
		int p1 = L;
		int p2 = M + 1;
		while (p1 <= M && p2 <= R) {
			help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
		}
		// 要么p1越界，要么p2越界
		// 不可能出现：共同越界
		while (p1 <= M) {
			help[i++] = arr[p1++];
		}
		while (p2 <= R) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}
	}

	public static void mergeSort2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		int step = 1;
		int N = arr.length;
		while (step < N) {
			int L = 0;
			while (L < N) {
				int M = 0;
				if (N - L >= step) {
					M = L + step - 1;
				} else {
					M = N - 1;
				}
				if (M == N - 1) {
					break;
				}
				int R = 0;
				if (N - 1 - M >= step) {
					R = M + step;
				} else {
					R = N - 1;
				}
				merge(arr, L, M, R);
				if (R == N - 1) {
					break;
				} else {
					L = R + 1;
				}
			}
			if (step > N / 2) {
				break;
			}
			step *= 2;
		}

	}

	// 非递归方法实现
//	public static void mergeSort2(int[] arr) {
//		if (arr == null || arr.length < 2) {
//			return;
//		}
//		int N = arr.length;
//		int mergeSize = 1;
//		while (mergeSize < N) {
//			int L = 0;
//			while (L < N) {
//				if (mergeSize >= N - L) {
//					break;
//				}
//				int M = L + mergeSize - 1;
//				int R = M + Math.min(mergeSize, N - M - 1);
//				merge(arr, L, M, R);
//				L = R + 1;
//			}
//			if (mergeSize > N / 2) {
//				break;
//			}
//			mergeSize <<= 1;
//		}
//	}


	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			mergeSort1(arr1);
			mergeSort2(arr2);
			if (!isEqual(arr1, arr2)) {
				System.out.println("出错了！");
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}
