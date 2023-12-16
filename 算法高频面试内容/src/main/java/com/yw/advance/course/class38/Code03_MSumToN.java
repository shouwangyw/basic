package com.yw.advance.course.class38;

/**
 * @author yangwei
 */
public class Code03_MSumToN {

	// 方法一：暴力
	public static boolean isMSum(int num) {
		// 第一轮：1+2+3+...
		// 第二轮：2+3+4+...
		// 第三轮：3+4+5+...
		// ...
		for (int i = 1; i <= num; i++) {
			int sum = i;
			for (int j = i + 1; j <= num; j++) {
				if (sum + j > num) break;
				if (sum + j == num) return true;
				sum += j;
			}
		}
		return false;
	}

	public static boolean isMSum2(int num) {
//		
//		return num == (num & (~num + 1));
//		
//		return num == (num & (-num));
//		
		return (num & (num - 1)) != 0;
	}

	public static void main(String[] args) {
		for (int num = 1; num < 200; num++) {
			System.out.println(num + " : " + isMSum(num));
		}
		System.out.println("test begin");
		for (int num = 1; num < 5000; num++) {
			if (isMSum(num) != isMSum2(num)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test end");

	}
}
