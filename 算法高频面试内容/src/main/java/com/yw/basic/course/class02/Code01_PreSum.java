package com.yw.basic.course.class02;

public class Code01_PreSum {
	// 方法一：每次遍历L~R区间，进行累加求和
	public static class RangeSum1 {
		private int[] arr;
		public RangeSum1(int[] array) {
			arr = array;
		}
		public int rangeSum(int L, int R) {
			int sum = 0;
			for (int i = L; i <= R; i++) {
				sum += arr[i];
			}
			return sum;
		}
	}
	// 方法二：基于前缀和数组，做预处理
	public static class RangeSum2 {
		private int[] preSum;
		public RangeSum2(int[] array) {
			int N = array.length;
			preSum = new int[N];
			preSum[0] = array[0];
			for (int i = 1; i < N; i++) {
				preSum[i] = preSum[i - 1] + array[i];
			}
		}
		public int rangeSum(int L, int R) {
			return L == 0 ? preSum[R] : preSum[R] - preSum[L - 1];
		}
	}
}
