package com.yw.basic.course.class01;

public class Code02_SumOfFactorial {
	// 方法一
	private static long f1(int N) {
		long ans = 0;
		for (int i = 1; i <= N; i++) {
			ans += factorial(i);
		}
		return ans;
	}
	private static long factorial(int N) {
		long ans = 1;
		for (int i = 1; i <= N; i++) {
			ans *= i;
		}
		return ans;
	}
	// 方法二，每次都基于上次计算结果进行计算 -> 更优
	private static long f2(int N) {
		long ans = 0;
		long cur = 1;
		for (int i = 1; i <= N; i++) {
			cur = cur * i;
			ans += cur;
		}
		return ans;
	}
	public static void main(String[] args) {
		int N = 10;
		System.out.println(f1(N));
		System.out.println(f2(N));
	}
}
