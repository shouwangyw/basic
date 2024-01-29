package com.yw.course.coding.class07;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.yw.util.CommonUtils.printArray;

/**
 * 给定一个有序数组arr，其中值可能为正、负、0。 返回arr中每个数都平方之后不同的结果有多少种？
 *
 * 给定一个数组arr，先递减然后递增，返回arr中有多少个绝对值不同的数字？
 * @author yangwei
 */
public class Code04_Power2Diffs {

	// 方法一：利用去重集合，时间复杂度O(N)，额外空间复杂度O(N)
	public static int diff1(int[] arr) {
		if (arr == null || arr.length == 0) return 0;
		Set<Integer> set = new HashSet<>();
		for (int x : arr) set.add(Math.abs(x));
		return set.size();
	}

	// 方法二：双指针法，时间复杂度O(N)，额外空间复杂度O(1)
	public static int diff2(int[] arr) {
		int n = arr.length, l = 0, r = n - 1, cnt = 0, lv, rv;
		while (l <= r) {
			cnt++;
			lv = Math.abs(arr[l]);
			rv = Math.abs(arr[r]);
			while (lv >= rv && l < n && Math.abs(arr[l]) == lv) l++;
			while (lv <= rv && r >= 0 && Math.abs(arr[r]) == rv) r--;
		}
		return cnt;
	}

	public static void main(String[] args) {
		int len = 100;
		int value = 500;
		int testTimes = 200000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = randomSortedArray(len, value);
			int ans1 = diff1(arr);
			int ans2 = diff2(arr);
			if (ans1 != ans2) {
				printArray(arr);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("test finish");
	}
	private static int[] randomSortedArray(int len, int value) {
		int[] ans = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * value) - (int) (Math.random() * value);
		}
		Arrays.sort(ans);
		return ans;
	}

}
