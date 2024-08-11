package com.yw.course.coding.class32;

/**
 * @author yangwei
 */
public class Problem_0204_CountPrimes {

	public int countPrimes(int n) {
		if (n < 3) return 0;
		boolean[] f = new boolean[n]; // j已经不是素数了，f[j] = true
		int cnt = n / 2; // 所有偶数都不要，还剩几个数
		for (int i = 3; i * i < n; i += 2) {
			if (f[i]) continue;
			// 3 -> 3 * 3 = 9   3 * 5 = 15   3 * 7 = 21
			// 7 -> 7 * 7 = 49  7 * 9 = 63
			// 13 -> 13 * 13  13 * 15
			for (int j = i * i; j < n; j += 2 * i) {
				if (!f[j]) {
					--cnt;
					f[j] = true;
				}
			}
		}
		return cnt;
	}

}
