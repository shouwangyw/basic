package com.yw.course.coding.class34;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangwei
 */
public class Problem_0315_CountOfSmallerNumbersAfterSelf {

	public List<Integer> countSmaller(int[] nums) {
		int n = nums.length;
		Data[] datas = new Data[n];
		for (int i = 0; i < n; i++) datas[i] = new Data(nums[i], i);
		mergeSort(datas, 0, n - 1);
		return Arrays.stream(datas).sorted(Comparator.comparingInt(a -> a.idx)).map(v -> v.cnt).collect(Collectors.toList());
	}
	// 归并排序
	private void mergeSort(Data[] datas, int l, int r) {
		if (l == r) return;
		int mid = l + ((r - l) >> 1);
		mergeSort(datas, l, mid);
		mergeSort(datas, mid + 1, r);
		Data[] tmp = new Data[r - l + 1];
		int i = mid, j = r, k = tmp.length - 1;
		while (i >= l && j > mid) {
			if (datas[i].val > datas[j].val) {
				datas[i].cnt += (j - mid);
				tmp[k--] = datas[i--];
			} else tmp[k--] = datas[j--];
		}
		while (i >= l) tmp[k--] = datas[i--];
		while (j > mid) tmp[k--] = datas[j--];
		for (k = 0; k < tmp.length; k++) datas[l + k] = tmp[k];
	}
	private static class Data {
		private int val;
		private int idx;
		private int cnt; // 记录idx右侧小于当前元素的个数
		public Data(int val, int idx) {
			this.val = val;
			this.idx = idx;
			this.cnt = 0;
		}
	}

}
