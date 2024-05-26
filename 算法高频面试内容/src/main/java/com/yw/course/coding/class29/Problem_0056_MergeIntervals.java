package com.yw.course.coding.class29;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author yangwei
 */
public class Problem_0056_MergeIntervals {

	public int[][] merge(int[][] intervals) {
		Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
		int idx = -1;
		for (int[] x : intervals) {
			if (idx == -1 || intervals[idx][1] < x[0]) intervals[++idx] = x;
			else intervals[idx][1] = Math.max(intervals[idx][1], x[1]);
		}
		return Arrays.copyOf(intervals, idx + 1);
	}

}
