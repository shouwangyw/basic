package com.yw.course.basic.class01;

import com.yw.test.SortBasicTest;

import static com.yw.util.CommonUtils.swap;

/**
 * @author yangwei
 */
public class Code03_SelectionSort extends SortBasicTest {

	// 选择排序
	// [1,len) -> find minValue -> 与 0 位置交换
	// [2,len) -> find minValue -> 与 1 位置交换
	// ...
	// [i,len) -> find minValue -> 与 i - 1 位置交换
	public static void selectionSort(int[] arr) {
		if (arr == null || arr.length < 2) return;
		int len = arr.length;
		for (int i = 0; i < len; i++) {
			int minValueIndex = i;
			for (int j = i + 1; j < len; j++) {
				minValueIndex = arr[j] < arr[minValueIndex] ? j : minValueIndex;
			}
			swap(arr, i, minValueIndex);
		}
	}

	public static void main(String[] args) {
		new Code03_SelectionSort().test();
	}

	@Override
	protected void test(int[] arr) {
		selectionSort(arr);
	}
}
