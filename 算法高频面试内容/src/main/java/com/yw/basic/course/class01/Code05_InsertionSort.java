package com.yw.basic.course.class01;

import com.yw.test.SortBasicTest;

import static com.yw.util.CommonUtils.swap;

/**
 * @author yangwei
 */
public class Code05_InsertionSort extends SortBasicTest {

	// 插入排序
	// [0,0] -> 有序，仅一个数，显然有序
	// [0,1] -> 有序 -> 从后往前两两比较并交换
	// [0,2] -> 有序 -> 从后往前两两比较并交换
	// ...
	// [0,len-1] -> 有序 -> 从后往前两两比较并交换
	public static void insertionSort(int[] arr) {
		if (arr == null || arr.length < 2) return;
		int len = arr.length;
		for (int i = 1; i < len; i++) {
			int pre = i - 1;
			while (pre >= 0 && arr[pre] > arr[pre + 1]) {
				swap(arr, pre, pre + 1);
				pre--;
			}

//			// 写法二：
//			for (int pre = i - 1; pre >= 0 && arr[pre] > arr[pre + 1]; pre--) {
//				swap(arr, pre, pre + 1);
//			}
		}
	}

	// for test
	public static void main(String[] args) {
		new Code05_InsertionSort().test();
	}

	@Override
	protected void test(int[] arr) {
		insertionSort(arr);
	}
}
