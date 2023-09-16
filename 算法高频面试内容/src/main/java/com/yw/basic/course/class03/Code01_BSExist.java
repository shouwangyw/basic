package com.yw.basic.course.class03;

import com.yw.test.FindBasicTest;

/**
 * @author yangwei
 */
public class Code01_BSExist extends FindBasicTest {
	// arr保证有序
	public boolean find(int[] arr, int num) {
		if (arr == null || arr.length == 0) return false;
		int l = 0, r = arr.length - 1;
		while (l <= r) {
			int mid = l + ((r - l) >> 1);
			if (arr[mid] == num) return true;
			else if (arr[mid] < num) l = mid + 1;
			else r = mid - 1;
		}
		return false;
	}

	public static void main(String[] args) {
		new Code01_BSExist().test();
	}

	@Override
	protected boolean findTarget(int[] arr, int value) {
		return find(arr, value);
	}
}
