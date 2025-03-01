package com.yw.course.coding.class38;

/**
 * 来自字节
 * 给定两个数a和b
 * 第1轮，把1选择给a或者b
 * 第2轮，把2选择给a或者b
 * ...
 * 第i轮，把i选择给a或者b
 * 想让a和b的值一样大，请问至少需要多少轮？
 *
 * @author yangwei
 */
public class Code01_FillGapMinStep {

	// 方法一：暴力尝试解，不要让a、b过大！
	public static int minStep0(int a, int b) {
		if (a == b) return 0;
		int limit = 15;
		return process(a, b, 1, limit);
	}
	public static int process(int a, int b, int i, int n) {
		if (i > n) return Integer.MAX_VALUE;
		if (a + i == b || a == b + i) return i;
		return Math.min(process(a + i, b, i + 1, n), process(a, b + i, i + 1, n));
	}

	// 方法二：
	public static int minStep1(int a, int b) {
		if (a == b) return 0;
		int s = Math.abs(a - b);
		int num = 1;
		int sum = 0;
		for (; !(sum >= s && (sum - s) % 2 == 0); num++) sum += num;
		return num - 1;
	}

	// 方法三：最优解
	public static int minStep2(int a, int b) {
		if (a == b) return 0;
		int s = Math.abs(a - b);
		// 找到sum >= s, 最小的i
		int begin = best(s << 1);
		while ((begin * (begin + 1) / 2 - s) % 2 != 0) begin++;
		return begin;
	}
	public static int best(int s2) {
		int l = 0, r = 1;
		while (r * (r + 1) < s2) {
			l = r;
			r *= 2;
		}
		int ans = 0;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (mid * (mid + 1) >= s2) {
				ans = mid;
				r = mid - 1;
			} else l = mid + 1;
		}
		return ans;
	}

	public static void main(String[] args) {
		System.out.println("功能测试开始");
		for (int a = 1; a < 100; a++) {
			for (int b = 1; b < 100; b++) {
				int ans1 = minStep0(a, b);
				int ans2 = minStep1(a, b);
				int ans3 = minStep2(a, b);
				if (ans1 != ans2 || ans1 != ans3) {
					System.out.println("出错了！");
					System.out.println(a + " , " + b);
					break;
				}
			}
		}
		System.out.println("功能测试结束");

		int a = 19019;
		int b = 8439284;
		int ans2 = minStep1(a, b);
		int ans3 = minStep2(a, b);
		System.out.println(ans2);
		System.out.println(ans3);

	}

}
