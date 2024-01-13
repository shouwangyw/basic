package com.yw.course.coding.class02;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Code03_ReceiveAndPrintOrderLine {

	public static class MessageBox {
		private static class Node {
			private String info;
			private Node next;

			public Node(String info) {
				this.info = info;
			}
		}

		private Map<Integer, Node> headMap;	// 记录所有连续区间的开头<序号, 节点>
		private Map<Integer, Node> tailMap;	// 记录所有连续区间的结尾<序号, 节点>
		private int waitPoint;

		public MessageBox() {
			headMap = new HashMap<>();
			tailMap = new HashMap<>();
			waitPoint = 1;	// 消息一定从1开始
		}

		// 消息的编号，info消息的内容
		public void receive(int num, String info) {
			if (num < 1) return;
			Node cur = new Node(info);
			Node head = headMap.get(num + 1), tail = tailMap.get(num - 1);
			// 如果存在 num+1 的头，说明 cur 可成为新头
			if (head != null) {
				cur.next = head;
				headMap.remove(num + 1);
			}
			// 如果存在 num-1 的尾，说明 cur 可成为新尾
			if (tail != null) {
				tail.next = cur;
				tailMap.remove(num - 1);
			}
			headMap.put(num, cur);
			tailMap.put(num, cur);

			if (num == waitPoint) print();
		}

		private void print() {
			Node node = headMap.remove(waitPoint);
			while (node != null) {
				System.out.print(node.info + " ");
				node = node.next;
				waitPoint++;
			}
			tailMap.remove(waitPoint-1);
			System.out.println();
		}

	}

	public static void main(String[] args) {
		// MessageBox only receive 1~N
		MessageBox box = new MessageBox();
		// 1....
		System.out.println("这是2来到的时候");
		box.receive(2,"B"); // - 2"
		System.out.println("这是1来到的时候");
		box.receive(1,"A"); // 1 2 -> print, trigger is 1
		box.receive(4,"D"); // - 4
		box.receive(5,"E"); // - 4 5
		box.receive(7,"G"); // - 4 5 - 7
		box.receive(8,"H"); // - 4 5 - 7 8
		box.receive(6,"F"); // - 4 5 6 7 8
		box.receive(3,"C"); // 3 4 5 6 7 8 -> print, trigger is 3
		box.receive(9,"I"); // 9 -> print, trigger is 9
		box.receive(10,"J"); // 10 -> print, trigger is 10
		box.receive(12,"L"); // - 12
		box.receive(13,"M"); // - 12 13
		box.receive(11,"K"); // 11 12 13 -> print, trigger is 11
	}
}
