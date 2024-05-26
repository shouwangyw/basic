package com.yw.course.coding.class27;

/**
 * @author yangwei
 */
public class Problem_0007_ReverseInteger {

	public static int reverse0(int x) {
		boolean neg = ((x >>> 31) & 1) == 1;
		x = neg ? x : -x;
		int m = Integer.MIN_VALUE / 10;
		int o = Integer.MIN_VALUE % 10;
		int res = 0;
		while (x != 0) {
			if (res < m || (res == m && x % 10 < o)) {
				return 0;
			}
			res = res * 10 + x % 10;
			x /= 10;
		}
		return neg ? res : Math.abs(res);
	}

	public int reverse(int x) {
		int resX = 0;
		while (x != 0) {
			int tail = x % 10;
			int temp = resX * 10 + tail;
			// 如果溢出
			if ((temp - tail) / 10 != resX) return 0;
			resX = temp;
			x /= 10;
		}
		return resX;
	}

}
