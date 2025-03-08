package com.yw.course.coding.class40;

/**
 * 给定两个数组A和B，长度都是N
 * A[i]不可以在A中和其他数交换，只可以选择和B[i]交换(0<=i<n)
 * 你的目的是让A有序，返回你能不能做到
 *
 * @author yangwei
 */
public class Code04_LetASorted {

	public static boolean letSorted(int[] a, int[] b) {
		return process(a, b, 0, Integer.MIN_VALUE);
	}
	// 当前数组a和b都来到i位置，a[i]的前一个数是lastA
	// 返回能否通过题意中的操作，让数组a整体有序
	private static boolean process(int[] a, int[] b, int i, int lastA) {
		if (i == a.length) return true;
		// 1. a[i]和b[i]不交换
		if (a[i] >= lastA && process(a, b, i + 1, a[i])) return true;
		// 2. a[i]和b[i]交换
		if (b[i] >= lastA && process(a, b, i + 1, b[i])) return true;
		return false;
	}

	// A B 操作 : A[i] 与 B[i] 交换！
	// 目的 : 让A和B都有序，能不能做到
//	public static boolean process(int[] A, int[] B, int i, int lastA, int lastB) {
//
//	}

}
