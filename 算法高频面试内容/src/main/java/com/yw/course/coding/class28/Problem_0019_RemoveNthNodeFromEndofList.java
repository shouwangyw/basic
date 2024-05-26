package com.yw.course.coding.class28;

import com.yw.entity.ListNode;

/**
 * @author yangwei
 */
public class Problem_0019_RemoveNthNodeFromEndofList {

	public ListNode removeNthFromEnd(ListNode head, int n) {
		// 考虑到头结点可能被删除，定义一个虚头hair
		ListNode hair = new ListNode(0, head), p = hair, cur = head;
		// 首先cur走n步
		while (n-- > 0) cur = cur.next;
		// 然后p、cur一起走
		while (cur != null) {
			cur = cur.next;
			p = p.next;
		}
		// 最后cur==null时，p刚好走到待删除节点的前一个结点
		p.next = p.next.next;
		return hair.next;
	}

}
