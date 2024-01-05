package com.yw.course.advance.class17;

import java.util.Stack;

/**
 * @author yangwei
 */
public class Code02_Hanoi {

	// 方法一：
	public static void hanoi(int n) {
		leftToRight(n);
	}
	// 请把1~N层圆盘 从左 -> 右
	private static void leftToRight(int n) {
		// base case
		if (n == 1) {
			System.out.println("Move 1 from left to right");
			return;
		}
		leftToMid(n - 1);
		System.out.println("Move " + n + " from left to right");
		midToRight(n - 1);
	}
	// 请把1~N层圆盘 从左 -> 中
	private static void leftToMid(int n) {
		if (n == 1) {
			System.out.println("Move 1 from left to mid");
			return;
		}
		leftToRight(n - 1);
		System.out.println("Move " + n + " from left to mid");
		rightToMid(n - 1);
	}
	private static void rightToMid(int n) {
		if (n == 1) {
			System.out.println("Move 1 from right to mid");
			return;
		}
		rightToLeft(n - 1);
		System.out.println("Move " + n + " from right to mid");
		leftToMid(n - 1);
	}
	private static void midToRight(int n) {
		if (n == 1) {
			System.out.println("Move 1 from mid to right");
			return;
		}
		midToLeft(n - 1);
		System.out.println("Move " + n + " from mid to right");
		leftToRight(n - 1);
	}
	private static void midToLeft(int n) {
		if (n == 1) {
			System.out.println("Move 1 from mid to left");
			return;
		}
		midToRight(n - 1);
		System.out.println("Move " + n + " from mid to left");
		rightToLeft(n - 1);
	}
	private static void rightToLeft(int n) {
		if (n == 1) {
			System.out.println("Move 1 from right to left");
			return;
		}
		rightToMid(n - 1);
		System.out.println("Move " + n + " from right to left");
		midToLeft(n - 1);
	}
	// 方法二：
	public static void hanoi2(int n) {
		if (n <= 0) return;
		func(n, "left", "right", "mid");
	}
	// 1~n 在: from，去: to，另一个other
	private static void func(int n, String from, String to, String other) {
		if (n == 1) {
			// base
			System.out.println("Move 1 from " + from + " to " + to);
		} else {
			func(n - 1, from, other, to);
			System.out.println("Move " + n + " from " + from + " to " + to);
			func(n - 1, other, to, from);
		}
	}
	// 方法三：迭代
	public static void hanoi3(int n) {
		if (n <= 0) return;
		Stack<Record> stack = new Stack<>();
		stack.add(new Record(n, "left", "right", "mid"));
		while (!stack.isEmpty()) {
			Record cur = stack.pop();
			if (cur.base == 1) {
				System.out.println("Move 1 from " + cur.from + " to " + cur.to);
				if (!stack.isEmpty()) {
					stack.peek().finish = true;
				}
			} else {
				if (!cur.finish) {
					stack.push(cur);
					stack.push(new Record(cur.base - 1, cur.from, cur.other, cur.to));
				} else {
					System.out.println("Move " + cur.base + " from " + cur.from + " to " + cur.to);
					stack.push(new Record(cur.base - 1, cur.other, cur.to, cur.from));
				}
			}
		}
	}
	private static class Record {
		int base;
		String from;
		String to;
		String other;
		boolean finish;
		public Record(int base, String from, String to, String other) {
			this.base = base;
			this.from = from;
			this.to = to;
			this.other = other;
			finish = false;
		}
	}

	public static void main(String[] args) {
		int n = 3;
		hanoi(n);
		System.out.println("============");
		hanoi2(n);
		System.out.println("============");
		hanoi3(n);
	}
}
