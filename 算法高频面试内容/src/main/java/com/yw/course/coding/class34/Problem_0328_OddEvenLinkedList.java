package com.yw.course.coding.class34;

import com.yw.entity.ListNode;

/**
 * @author yangwei
 */
public class Problem_0328_OddEvenLinkedList {

	public ListNode oddEvenList(ListNode head) {
		if (head == null || head.next == null) return head;
		ListNode p = head, q = head.next, even = q;
		while (p.next != null && q.next != null) {
			p.next = q.next;
			p = p.next;
			q.next = p.next;
			q = q.next;
		}
		p.next = even;
		return head;
	}

}
