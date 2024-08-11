package com.yw.course.coding.class32;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class Problem_0163_MissingRanges {

	public static List<String> findMissingRanges(int[] nums, int lower, int upper) {
		List<String> ans = new ArrayList<>();
		for (int x : nums) {
			if (x > lower) ans.add(getMissingRange(lower, x - 1));
			if (x == upper) return ans;
			lower = x + 1;
		}
		if (lower <= upper) ans.add(getMissingRange(lower, upper));
		return ans;
	}

	private static String getMissingRange(int lower, int upper) {

		return upper > lower ? String.format("%s->%s", lower, upper) : String.valueOf(lower);
	}

	public static void main(String[] args) {
		int[] nums = {0, 1, 3, 50, 75};

		System.out.println(findMissingRanges(nums, 0, 99));
	}

}
