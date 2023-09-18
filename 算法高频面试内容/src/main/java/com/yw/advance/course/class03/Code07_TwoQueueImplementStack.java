package com.yw.advance.course.class03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author yangwei
 */
public class Code07_TwoQueueImplementStack {

	public static class QueueStack<T> {
		// 定义两个队列：一个用于入栈、出栈，一个用于来回倒数据
		private Queue<T> queue;
		private Queue<T> help;
		public QueueStack() {
			this.queue = new LinkedList<>();
			this.help = new LinkedList<>();
		}
		public void push(T val) {
			// 入栈时，正常往队列加数据
			queue.offer(val);
		}
		public T pop() {
			// 出栈时，将队列中元素导入到help队列中，仅保留一个用于出栈
			while (queue.size() > 1) help.offer(queue.poll());
			// 弹出出栈元素
			T item = queue.poll();
			// 交换两个队列
			swap();
			return item;
		}
		public T peek() {
			// 查看时，将队列中元素导入到help队列中，仅保留一个用于出栈
			while (queue.size() > 1) help.offer(queue.poll());
			// 弹出peek元素
			T item = queue.poll();
			// peek完，还得放回去
			help.offer(item);
			// 交换两个队列
			swap();
			return item;
		}
		public boolean isEmpty() {
			return queue.isEmpty();
		}
		private void swap() {
			Queue<T> tmp = queue;
			queue = help;
			help = tmp;
		}
	}

	public static void main(String[] args) {
		System.out.println("test begin");
		QueueStack<Integer> myStack = new QueueStack<>();
		Stack<Integer> test = new Stack<>();
		int testTime = 1000000;
		int max = 1000000;
		for (int i = 0; i < testTime; i++) {
			if (myStack.isEmpty()) {
				if (!test.isEmpty()) {
					System.out.println("Oops");
				}
				int num = (int) (Math.random() * max);
				myStack.push(num);
				test.push(num);
			} else {
				if (Math.random() < 0.25) {
					int num = (int) (Math.random() * max);
					myStack.push(num);
					test.push(num);
				} else if (Math.random() < 0.5) {
					if (!myStack.peek().equals(test.peek())) {
						System.out.println("Oops");
					}
				} else if (Math.random() < 0.75) {
					if (!myStack.pop().equals(test.pop())) {
						System.out.println("Oops");
					}
				} else {
					if (myStack.isEmpty() != test.isEmpty()) {
						System.out.println("Oops");
					}
				}
			}
		}

		System.out.println("test finish!");

	}

}
