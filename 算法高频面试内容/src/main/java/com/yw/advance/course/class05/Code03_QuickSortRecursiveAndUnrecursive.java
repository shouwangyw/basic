package com.yw.advance.course.class05;

import com.yw.basic.course.class08.Code03_PartitionAndQuickSort;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code03_QuickSortRecursiveAndUnrecursive extends Code03_PartitionAndQuickSort {

	// 荷兰国旗问题
	public static int[] netherlandsFlag(int[] arr, int L, int R) {
		if (L > R) {
			return new int[] { -1, -1 };
		}
		if (L == R) {
			return new int[] { L, R };
		}
		int less = L - 1;
		int more = R;
		int index = L;
		while (index < more) {
			if (arr[index] == arr[R]) {
				index++;
			} else if (arr[index] < arr[R]) {
				swap(arr, index++, ++less);
			} else {
				swap(arr, index, --more);
			}
		}
		swap(arr, more, R);
		return new int[] { less + 1, more };
	}

	// 快排递归版本
	public static void quickSort1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}

	public static void process(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
		int[] equalArea = netherlandsFlag(arr, L, R);
		process(arr, L, equalArea[0] - 1);
		process(arr, equalArea[1] + 1, R);
	}

	/**
	 * 快排非递归版本需要的辅助类，要处理的是什么范围上的排序
	 */
	public static class Job {
		int l;
		int r;

		public Job(int l, int r) {
			this.l = l;
			this.r = r;
		}
	}

	/**
	 * 快排3.0 非递归版本 用栈来实现
	 */
	public static void quickSortByStack(int[] arr) {
		if (arr == null || arr.length < 2) return;
		int n = arr.length;
		swap(arr, (int) (Math.random() * n), n - 1);
		int[] equals = partition3Way(arr, 0, n - 1);
		Stack<Job> stack = new Stack<>();
		stack.push(new Job(0, equals[0] - 1));
		stack.push(new Job(equals[1] + 1, n - 1));
		while (!stack.isEmpty()) {
			Job job = stack.pop();
			if (job.l >= job.r) continue;
			swap(arr, job.l + (int) (Math.random() * (job.r - job.l + 1)), job.r);
			equals = partition3Way(arr, job.l, job.r);
			stack.push(new Job(job.l, equals[0] - 1));
			stack.push(new Job(equals[1] + 1, job.r));
		}
	}

	/**
	 * 快排3.0 非递归版本 用队列来执行
	 */
	public static void quickSortByQueue(int[] arr) {
		if (arr == null || arr.length < 2) return;
		int n = arr.length;
		swap(arr, (int) (Math.random() * n), n - 1);
		int[] equals = partition3Way(arr, 0, n - 1);
		Queue<Job> queue = new LinkedList<>();
		queue.offer(new Job(0, equals[0] - 1));
		queue.offer(new Job(equals[1] + 1, n - 1));
		while (!queue.isEmpty()) {
			Job job = queue.poll();
			if (job.l >= job.r) continue;
			swap(arr, job.l + (int) (Math.random() * (job.r - job.l + 1)), job.r);
			equals = partition3Way(arr, job.l, job.r);
			queue.offer(new Job(job.l, equals[0] - 1));
			queue.offer(new Job(equals[1] + 1, job.r));
		}
	}

	// 跑大样本随机测试（对数器）
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			int[] arr3 = copyArray(arr1);
			quickSort1(arr1);
			quickSortByStack(arr2);
			quickSortByQueue(arr3);
			if (!isEqual(arr1, arr2) || !isEqual(arr1, arr3)) {
				succeed = false;
				break;
			}
		}
		System.out.println("test end");
		System.out.println("测试" + testTime + "组是否全部通过：" + (succeed ? "是" : "否"));
	}
}
