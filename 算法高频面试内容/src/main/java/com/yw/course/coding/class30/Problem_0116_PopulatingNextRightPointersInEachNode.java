package com.yw.course.coding.class30;

import com.yw.entity.Node;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yangwei
 */
public class Problem_0116_PopulatingNextRightPointersInEachNode {

	// 方法一：层序遍历
//	public Node connect(Node root) {
//		List<List<Node>> nodes = new LinkedList<>();
//		levelOrder(root, 0, nodes);
//		return root;
//	}
//	private void levelOrder(Node root, int level, List<List<Node>> nodes) {
//		if (root == null) return;
//		if (level == nodes.size()) nodes.add(new LinkedList<>());
//		List<Node> levelNodes = nodes.get(level);
//		levelNodes.add(root);
//		int size = levelNodes.size();
//		if (size > 1) levelNodes.get(size - 2).next = root;
//		levelOrder(root.left, level + 1, nodes);
//		levelOrder(root.right, level + 1, nodes);
//	}

	// 方法二：自己实现队列，宽度优先遍历
	public Node connect(Node root) {
		if (root == null) return root;
		MyQueue q = new MyQueue();
		q.offer(root);
		while (!q.isEmpty()) {
			Node pre = null;
			int size = q.size;
			for (int i = 0; i < size; i++) {
				Node cur = q.poll();
				if (cur.left != null) q.offer(cur.left);
				if (cur.right != null) q.offer(cur.right);
				if (pre != null) pre.next = cur;
				pre = cur;
			}
		}
		return root;
	}
	private static class MyQueue {
		private Node head;
		private Node tail;
		private int size;
		public MyQueue() {
			this.head = null;
			this.tail = null;
			this.size = 0;
		}
		public boolean isEmpty() {
			return size == 0;
		}
		public void offer(Node cur) {
			size++;
			if (head == null) {
				head = cur;
				tail = cur;
			} else {
				tail.next = cur;
				tail = cur;
			}
		}
		public Node poll() {
			size--;
			Node node = head;
			head = head.next;
			node.next = null;
			return node;
		}
	}

}
