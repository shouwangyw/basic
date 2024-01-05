package com.yw.course.advance.class25;

import java.util.*;

import static com.yw.util.CommonUtils.isEqual;
import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code01_MonotonousStack {

	// arr = [ 3, 1, 2, 3]
	//         0  1  2  3
	//  [
	//     0 : [-1,  1]
	//     1 : [-1, -1]
	//     2 : [ 1, -1]
	//     3 : [ 2, -1]
	//  ]
	// 方法一：arr数组中无重复值
	public static int[][] getNearLessNoRepeat(int[] arr) {
		int[][] res = new int[arr.length][2];
		// 只存位置，位置所代表的值(底->顶)从小到大
		Stack<Integer> stack = new Stack<>();
		// 依次遍历到i位置的数，arr[i]
		for (int i = 0; i < arr.length; i++) {
			// 栈不空 且 栈顶的数大于此时的arr[i]，则栈顶需要弹出，并整理答案了
			while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
				int j = stack.pop();
				res[j][0] = stack.isEmpty() ? -1 : stack.peek();
				res[j][1] = i;
			}
			stack.push(i);
		}
		// 最后栈不空，单独收集答案
		while (!stack.isEmpty()) {
			int j = stack.pop();
			res[j][0] = stack.isEmpty() ? -1 : stack.peek();
			res[j][1] = -1;
		}
		return res;
	}

	// 方法二：arr数组中有重复值
	public static int[][] getNearLess(int[] arr) {
		int[][] res = new int[arr.length][2];
		Stack<List<Integer>> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			// i -> arr[i] 进栈
			while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
				List<Integer> items = stack.pop();
				int leftLessIdx = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
				for (Integer x : items) {
					res[x][0] = leftLessIdx;
					res[x][1] = i;
				}
			}
			if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
				stack.peek().add(i);
			} else {
				List<Integer> list = new ArrayList<>();
				list.add(i);
				stack.push(list);
			}
		}
		// 最后栈不空，单独收集答案
		while (!stack.isEmpty()) {
			List<Integer> items = stack.pop();
			int leftLessIdx = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
			for (Integer x : items) {
				res[x][0] = leftLessIdx;
				res[x][1] = -1;
			}
		}
		return res;
	}

	public static int[][] getNearLess2(int[] arr) {
		int[][] ans = new int[arr.length][2];
		Stack<Deque<Integer>> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			while (!stack.isEmpty() && arr[stack.peek().getFirst()] > arr[i]) {
				Deque<Integer> items = stack.pop();
				for (Integer x : items) {
					ans[x][0] = stack.isEmpty() ? -1 : stack.peek().getLast();
					ans[x][1] = i;
				}
			}
			if (!stack.isEmpty() && arr[stack.peek().getFirst()] == arr[i]) {
				stack.peek().add(i);
			} else {
				Deque<Integer> item = new LinkedList<>();
				item.add(i);
				stack.push(item);
			}
		}
		while (!stack.isEmpty()) {
			Deque<Integer> items = stack.pop();
			int leftLessIdx = stack.isEmpty() ? -1 : stack.peek().getLast();
			for (Integer x : items) {
				ans[x][0] = leftLessIdx;
				ans[x][1] = -1;
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		int size = 10;
		int max = 20;
		int testTimes = 2000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int[] arr1 = getRandomArrayNoRepeat(size);
			int[] arr2 = getRandomArray(size, max);
			if (!isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
				System.out.println("Oops!");
				printArray(arr1);
				break;
			}
			if (!isEqual(getNearLess(arr2), rightWay(arr2))) {
//			if (!isEqual(getNearLess2(arr2), rightWay(arr2))) {
				System.out.println("Oops!");
				printArray(arr2);
				break;
			}
		}
		System.out.println("测试结束");
	}

	private static int[] getRandomArrayNoRepeat(int size) {
		int[] arr = new int[(int) (Math.random() * size) + 1];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		for (int i = 0; i < arr.length; i++) {
			int swapIndex = (int) (Math.random() * arr.length);
			int tmp = arr[swapIndex];
			arr[swapIndex] = arr[i];
			arr[i] = tmp;
		}
		return arr;
	}

	private static int[] getRandomArray(int size, int max) {
		int[] arr = new int[(int) (Math.random() * size) + 1];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
		}
		return arr;
	}

	private static int[][] rightWay(int[] arr) {
		int[][] res = new int[arr.length][2];
		for (int i = 0; i < arr.length; i++) {
			int leftLessIndex = -1;
			int rightLessIndex = -1;
			int cur = i - 1;
			while (cur >= 0) {
				if (arr[cur] < arr[i]) {
					leftLessIndex = cur;
					break;
				}
				cur--;
			}
			cur = i + 1;
			while (cur < arr.length) {
				if (arr[cur] < arr[i]) {
					rightLessIndex = cur;
					break;
				}
				cur++;
			}
			res[i][0] = leftLessIndex;
			res[i][1] = rightLessIndex;
		}
		return res;
	}
}
