package com.yw.advance.course.class03;

import java.util.Stack;

/**
 * @author yangwei
 */
public class Code06_TwoStacksImplementQueue {

	public static class StackQueue<T> {
		// 定义两个栈：一个push栈(作为队列头部加数据)、一个pop栈(作为队列尾部取数据)
		private Stack<T> pushStack;
		private Stack<T> popStack;
		public StackQueue() {
			this.pushStack = new Stack<>();
			this.popStack = new Stack<>();
		}
		public void add(T val) {
			pushStack.push(val);
			// 每次添加数据时，同时维护pop栈popStack
			// popStack为空时，才将pushStack中的数据导入到popStack
			rebalance();
		}
		public T remove() {
			if (isEmpty()) throw new RuntimeException("StackQueue is empty");
			// 每次移除数据时，同时维护pop栈popStack
			rebalance();
			return popStack.pop();
		}
		public T peek() {
			if (isEmpty()) throw new RuntimeException("StackQueue is empty");
			// 每次查看数据时，同时维护pop栈popStack
			rebalance();
			return popStack.peek();
		}
		private void rebalance() {
			if (!popStack.isEmpty()) return;
			while (!pushStack.isEmpty()) popStack.push(pushStack.pop());
		}
		private boolean isEmpty() {
			return popStack.isEmpty() && pushStack.isEmpty();
		}
	}

	public static void main(String[] args) {
		StackQueue<Integer> test = new StackQueue<>();
		test.add(1);
		test.add(2);
		test.add(3);
		System.out.println(test.peek());
		System.out.println(test.remove());
		System.out.println(test.peek());
		System.out.println(test.remove());
		System.out.println(test.peek());
		System.out.println(test.remove());
	}

}
