package com.yw.course.coding.class04;

/**
 * 测试链接 : https://leetcode.cn/problems/candy/
 * @author yangwei
 */
public class Code05_CandyProblem {

	// 方法一：这是原问题的优良解，时间复杂度O(N)，额外空间复杂度O(N)
	public static int candy1(int[] ratings) {
		if (ratings == null || ratings.length == 0) return 0;
		int n = ratings.length;
		int[] l = new int[n], r = new int[n];
		// 这里做了一点优化，初始糖果0最后结果统一加n，避免边界判断
		for (int i = 1; i < n; i++) {
			if (ratings[i] > ratings[i - 1]) l[i] = l[i - 1] + 1;
		}
		for (int i = n - 2; i >= 0; i--) {
			if (ratings[i] > ratings[i + 1]) r[i] = r[i + 1] + 1;
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans += Math.max(l[i], r[i]);
		}
		return ans + n;
	}

	// 方法二：这是原问题空间优化后的解，时间复杂度O(N)，额外空间复杂度O(1)
	public static int candy2(int[] ratings) {
		if (ratings == null || ratings.length == 0) return 0;
		int idx = nextMinIndex(ratings, 0), ans = rightCandies(0, idx++);
		int lbase = 1, next, rcand, rbase;
		while (idx < ratings.length) {
			if (ratings[idx] > ratings[idx - 1]) {
				ans += ++lbase;
				idx++;
			} else if (ratings[idx] < ratings[idx - 1]) {
				next = nextMinIndex(ratings, idx - 1);
				rcand = rightCandies(idx - 1, next++);
				rbase = next - idx + 1;
				ans += rcand + (rbase > lbase ? -lbase : -rbase);
				lbase = 1;
				idx = next;
			} else {
				ans += 1;
				lbase = 1;
				idx++;
			}
		}
		return ans;
	}
	private static int nextMinIndex(int[] arr, int start) {
		for (int i = start; i != arr.length - 1; i++) {
			if (arr[i] <= arr[i + 1]) return i;
		}
		return arr.length - 1;
	}
	private static int rightCandies(int l, int r) {
		int n = r - l + 1;
		// 1+2+...+n 避免计算溢出
		return n + n * (n - 1) / 2;
	}

	// 这是进阶问题的最优解，不要提交这个，时间复杂度O(N), 额外空间复杂度O(1)
	public static int candy3(int[] ratings) {
		if (ratings == null || ratings.length == 0) return 0;
		int idx = nextMinIndex3(ratings, 0);
		int[] data = rightCandiesAndBase(ratings, 0, idx++);
		int ans = data[0], lbase = 1, same = 1, next;
		while (idx != ratings.length) {
			if (ratings[idx] > ratings[idx - 1]) {
				ans += ++lbase;
				same = 1;
				idx++;
			} else if (ratings[idx] < ratings[idx - 1]) {
				next = nextMinIndex3(ratings, idx - 1);
				data = rightCandiesAndBase(ratings, idx - 1, next++);
				if (data[1] <= lbase) {
					ans += data[0] - data[1];
				} else {
					ans += -lbase * same + data[0] - data[1] + data[1] * same;
				}
				idx = next;
				lbase = 1;
				same = 1;
			} else {
				ans += lbase;
				same++;
				idx++;
			}
		}
		return ans;
	}
	private static int nextMinIndex3(int[] arr, int start) {
		for (int i = start; i != arr.length - 1; i++) {
			if (arr[i] < arr[i + 1]) return i;
		}
		return arr.length - 1;
	}
	private static int[] rightCandiesAndBase(int[] arr, int l, int r) {
		int base = 1, cand = 1;
		for (int i = r - 1; i >= l; i--) {
			if (arr[i] == arr[i + 1]) cand += base;
			else cand += ++base;
		}
		return new int[] { cand, base };
	}

	public static void main(String[] args) {
//		int[] test1 = { 3, 0, 5, 5, 4, 4, 0 };
		int[] test1 = { 1, 3, 3, 2, 1, 4, 2, 2, 5, 2, 1 };
		System.out.println(candy2(test1));

		int[] test2 = { 3, 0, 5, 5, 4, 4, 0 };
		System.out.println(candy3(test2));
	}
}
