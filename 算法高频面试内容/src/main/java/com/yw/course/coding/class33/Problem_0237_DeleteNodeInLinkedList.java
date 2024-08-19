package com.yw.course.coding.class33;

import com.yw.entity.ListNode;

/**
 * @author yangwei
 */
public class Problem_0237_DeleteNodeInLinkedList {

	public void deleteNode(ListNode node) {
		node.val = node.next.val;
		node.next = node.next.next;
	}

}
