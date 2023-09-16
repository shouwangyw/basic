package com.yw.basic.course.class01;

import com.yw.test.SortBasicTest;

import static com.yw.util.CommonUtils.swap;

/**
 * @author yangwei
 */
public class Code04_BubbleSort extends SortBasicTest {

	// 冒泡排序
	// [0, len) -> 两两比较并交换 -> maxValue放到 len-1 位置
	// [0, len-1) -> 两两比较并交换 -> maxValue放到 len-2 位置
	// ...
	// [0, len-i) -> 两两比较并交换 -> maxValue放到 len-i-1 位置
	public static void bubbleSort(int[] arr) {
		if (arr == null || arr.length < 2) return;
		int len = arr.length;
		for (int i = len - 1; i >= 0; i--) {
			boolean flag = false;
			for (int j = 1; j <= i; j++) {
				if (arr[j - 1] > arr[j]) {
					swap(arr, j - 1, j);
					flag = true;
				}
			}
			if (!flag) { // 如果没发生交换，说明已经有序，可以提前结束
				break;
			}
		}
	}

	public static void main(String[] args) {
		new Code04_BubbleSort().test();
	}

	@Override
	protected void test(int[] arr) {
		bubbleSort(arr);
	}
}
