package com.yw.advance.course.class03;

import com.yw.entity.Node;

/**
 * @author yangwei
 */
public class Code02_DeleteGivenValue {

	public static Node removeValue(Node head, int num) {
		// head来到第一个不需要删的位置
		while (head != null) {
			if (head.val != num) break;
			head = head.next;
		}
		// 1 ) head == null
		// 2 ) head != null
		Node pre = head;
		Node cur = head;
		while (cur != null) {
			if (cur.val == num) pre.next = cur.next;
			else pre = cur;
			cur = cur.next;
		}
		return head;
	}

}
