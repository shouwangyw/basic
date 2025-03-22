package com.yw.course.coding.class48;

/**
 * @author yangwei
 */
public class Problem_0483_SmallestGoodBase {

	// ""4651" -> 4651
	public String smallestGoodBase(String n) {
		long num = Long.parseLong(n);
		// num这个数需要从m位开始试，固定位数，一定要有m位
		for (int m = (int) (Math.log(num + 1) / Math.log(2)); m > 2; m--) {
			// 下界：num开m次方向下取整，上界：num开m-1次方向上取整
			long l = (long) (Math.pow(num, 1.0 / m));
			long r = (long) (Math.pow(num, 1.0 / (m - 1))) + 1;
			while (l <= r) {
				long k = l + ((r - l) >> 1);
				long sum = 0, base = 1;
				for (int i = 0; i < m && sum <= num; i++) {
					sum += base;
					base *= k;
				}
				if (sum == num) return String.valueOf(k);
				else if (sum < num) l = k + 1;
				else r = k - 1;
			}
		}
		return String.valueOf(num - 1);
	}

}
