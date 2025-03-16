package com.yw.course.coding.class47;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 利用只支持单点增加 + 范围查询的动态开点线段树（累加和），解决leetcode 315
 * @author yangwei
 */
public class Problem_0315_CountOfSmallerNumbersAfterSelf {

	public static List<Integer> countSmaller(int[] nums) {
		List<Integer> ans = new ArrayList<>();
		if (nums == null || nums.length == 0) return ans;
		int n = nums.length;
		for (int i = 0; i < n; i++) ans.add(0);
		int[][] arr = new int[n][];
		for (int i = 0; i < n; i++) arr[i] = new int[] { nums[i], i };
		Arrays.sort(arr, Comparator.comparingInt(a -> a[0]));
		// 原数组nums = {7, 4, 1, 5, 3, 2, 6}
		// arr = {{7,0},{4,1},{1,2},{5,3},{3,4},{2,5},{6,6}}
		// arr[0]排序后：{{1,2},{2,5},{3,4},{4,1},{5,3},{6,6},{7,0}}
		Code01_DynamicSegmentTree.DynamicSegmentTree dst = new Code01_DynamicSegmentTree.DynamicSegmentTree(n);
		for (int[] x : arr) {
			// 依次从小到大处理每个数
			ans.set(x[1], dst.query(x[1] + 1, n));
			dst.add(x[1] + 1, 1);
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] nums = {7, 4, 1, 5, 3, 2, 6};

		System.out.println(countSmaller(nums));
	}
}
