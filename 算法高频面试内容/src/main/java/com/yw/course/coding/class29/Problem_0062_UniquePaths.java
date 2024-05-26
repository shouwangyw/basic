package com.yw.course.coding.class29;

/**
 * @author yangwei
 */
public class Problem_0062_UniquePaths {

	// m 行
	// n 列
	// 下：m-1
	// 右：n-1
	public int uniquePaths(int m, int n) {
		// r: 往右走的步数、all: 总步数
		int r = n - 1, all = m + n - 2;
		long o1 = 1, o2 = 1;
		// 比如 all = 13，r = 5，则:
		// o1 = 6*7*8*9*10*11*12*13
		// o2 = 1*2*3*4* 5* 6* 7* 8
		// o1乘进去的个数 一定等于 o2乘进去的个数
		for (int i = r + 1, j = 1; i <= all; i++, j++) {
			o1 *= i;
			o2 *= j;
			long gcd = gcd(o1, o2);
			o1 /= gcd;
			o2 /= gcd;
		}
		return (int) o1;
	}
	private static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public int uniquePaths0(int m, int n) {
		long ans = 1;
		for (int x = n, y = 1; y < m; x++, y++)
			ans = ans * x / y;
		return (int) ans;
	}
}
