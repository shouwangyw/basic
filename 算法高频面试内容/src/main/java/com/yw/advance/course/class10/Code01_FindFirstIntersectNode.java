package com.yw.advance.course.class10;

import com.yw.entity.Node;

/**
 * @author yangwei
 */
public class Code01_FindFirstIntersectNode {

	// 返回两个链表相交节点，不相交返回null
	public static Node getIntersectNode(Node head1, Node head2) {
		if (head1 == null || head2 == null) return null;
		// 1. 分别获取两个链表的入环节点
		Node loop1 = getLoopNode(head1);
		Node loop2 = getLoopNode(head2);
		// 2. 情况一：两个链表都无环
		if (loop1 == null && loop2 == null) {
			return getIntersectWithNoLoop(head1, head2);
		}
		// 2. 情况二：两个链表都有环
		if (loop1 != null && loop2 != null) {
			return getIntersectWithBothLoop(head1, loop1, head2, loop2);
		}
		// 3. 情况三：一个有环、一个无环，一定不相交
		return null;
	}

	private static Node getLoopNode(Node head) {
		Node slow = head, fast = head;
		do {
			if (fast.next == null || fast.next.next == null) {
				return null;
			}
			slow = slow.next;
			fast = fast.next.next;
		} while (slow != fast);
		// do...while 能出来，说明slow与fast相交
		// 将fast放回到头节点，然后快慢指针一起往下走
		fast = head;
		while (slow != fast) {
			slow = slow.next;
			fast = fast.next;
		}
		// 再次相遇的点，就是入环节点
		return slow;
	}

	// 如果两个链表都无环，返回第一个相交节点，如果不相交，返回null
	private static Node getIntersectWithNoLoop(Node head1, Node head2) {
		Node cur1 = head1, cur2 = head2;
		int cnt = 0; // 用于计算两个链表节点数量的差值
		// 找到head1的最后一个节点
		while (cur1.next != null) {
			cnt++;
			cur1 = cur1.next;
		}
		// 找到head2的最后一个节点
		while (cur2.next != null) {
			cnt--;
			cur2 = cur2.next;
		}
		// 如果最后一个节点不相等，一定不相交
		if (cur1 != cur2) return null;
		cur1 = cnt > 0 ? head1 : head2;			// 谁长，谁的头变成cur1
		cur2 = cur1 == head1 ? head2 : head1;	// 谁短，谁的头变成cur2
		cnt = Math.abs(cnt);
		// cur1 先走 cnt 步
		while (cnt-- > 0) cur1 = cur1.next;
		// 然后cur1、cur2一起走
		while (cur1 != cur2) {
			cur1 = cur1.next;
			cur2 = cur2.next;
		}
		// 相遇的点，就是相交点
		return cur1;
	}

	// 如果两个链表都有环，返回第一个相交节点，如果不相交，返回null
	private static Node getIntersectWithBothLoop(Node head1, Node loop1, Node head2, Node loop2) {
		// 1. 情况一：如果两个链表的入环节点不相等
		if (loop1 != loop2) {
			// 假设从loop1下个节点开始往下走一圈
			Node cur = loop1.next;
			while (cur != loop1) {
				// 如果能和loop2相遇，则找到了相交点
				if (cur == loop2) return loop1;
				cur = cur.next;
			}
			// 否则没找到相交点
			return null;
		}
		// 2. 情况一：如果两个链表的入环节点相等
		// 计算head1、head2到入环节点的节点个数差值
		Node cur1 = head1, cur2 = head2;
		int cnt = 0;
		while (cur1 != loop1) {
			cnt++;
			cur1 = cur1.next;
		}
		while (cur2 != loop2) {
			cnt--;
			cur2 = cur2.next;
		}
		cur1 = cnt > 0 ? head1 : head2;
		cur2 = cur1 == head1 ? head2 : head1;
		cnt = Math.abs(cnt);
		// cur1 先走cnt步
		while (cnt-- > 0) cur1 = cur1.next;
		// 然后cur1、cur2一起走
		while (cur1 != cur2) {
			cur1 = cur1.next;
			cur2 = cur2.next;
		}
		// 相遇的点，就是相交点
		return cur1;
	}

	public static void main(String[] args) {
		// 1->2->3->4->5->6->7->null
		Node head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);

		// 0->9->8->6->7->null
		Node head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).val);

		// 1->2->3->4->5->6->7->4...
		head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);
		head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

		// 0->9->8->2...
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next; // 8->2
		System.out.println(getIntersectNode(head1, head2).val);

		// 0->9->8->6->4->5->6..
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).val);
	}
}
