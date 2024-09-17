package com.yw.course.coding.class34;

/**
 * @author yangwei
 */
public class Problem_0326_PowerOfThree {

	// 如果一个数字是3的某次幂，那么这个数一定只含有3这个质数因子
	// 1162261467是int型范围内，最大的3的幂，它是3的19次方
	// 这个1162261467只含有3这个质数因子，如果n也是只含有3这个质数因子，那么
	// 1162261467 % n == 0
	// 反之如果1162261467 % n != 0 说明n一定含有其他因子
	public boolean isPowerOfThree(int n) {
		// int m = 1; // int范围内最大的3的幂
		// while (m * 3 > 0 && m * 3 < Integer.MAX_VALUE) m *= 3;
		int m = 1162261467;
		return n > 0 && m % n == 0;
	}
}
