package com.yw.course.coding.class29;

/**
 * @author yangwei
 */
public class Problem_0069_SqrtX {

	// x一定非负，输入可以保证
	public int mySqrt0(int x) {
		if (x == 0) return 0;
		long l = 1, r = x, mid, ans = 1;
		while (l <= r) {
			mid = l + ((r - l) >> 1);
			if (mid * mid <= x) {
				ans = mid;
				l = mid + 1;
			} else r = mid - 1;
		}
		return (int) ans;
	}

	// 方法二：
	// y = x^2 - C  ==> y' = 2x
	// y0 = 2xi·(x0 - xi) + yi = 2xi·x0 - 2xi^2 + (xi^2 - C) = 2xi·x0 - (xi^2 + C)
	// 令 y0 = 0，求解出x0：x0 = 0.5·(xi + C / xi)
	public int mySqrt(int x) {
		if (x == 0) return 0;
		double c = x, xi = x;
		while (true) {
			double x0 = 0.5 * (xi + x / xi);
			if (Math.abs(xi - x0) < 1e-7) break;
			xi = x0;
		}
		return (int) xi;
	}
}
