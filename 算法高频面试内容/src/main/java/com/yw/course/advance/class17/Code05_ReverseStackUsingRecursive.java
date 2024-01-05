package com.yw.course.advance.class17;

import java.util.Stack;

/**
 * @author yangwei
 */
public class Code05_ReverseStackUsingRecursive {

	public static void reverse(Stack<Integer> stack) {
		if (stack.isEmpty()) return;
		int i = process(stack);
		reverse(stack);
		stack.push(i);
	}
	// 栈底元素移除掉，上面的元素盖下来
	// 返回移除掉的栈底元素
	public static int process(Stack<Integer> stack) {
		Integer val = stack.pop();
		if (stack.isEmpty()) return val;
		else {
			int next = process(stack);
			stack.push(val);
			return next;
		}
	}

	public static void main(String[] args) {
		Stack<Integer> test = new Stack<Integer>();
		test.push(1);
		test.push(2);
		test.push(3);
		test.push(4);
		test.push(5);
		reverse(test);
		while (!test.isEmpty()) {
			System.out.println(test.pop());
		}

	}

}
