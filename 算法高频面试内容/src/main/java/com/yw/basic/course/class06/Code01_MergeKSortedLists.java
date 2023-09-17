package com.yw.basic.course.class06;

import com.yw.entity.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 测试链接：https://leetcode.cn/problems/merge-k-sorted-lists/
 * @author yangwei
 */
public class Code01_MergeKSortedLists {

	public ListNode mergeKLists(ListNode[] lists) {
		if (lists == null || lists.length == 0) return null;
		Queue<ListNode> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.val));
		for (ListNode list : lists) {
			if (list != null) queue.offer(list);
		}
		ListNode head = null, cur = null;
		while (!queue.isEmpty()) {
			ListNode node = queue.poll();
			if (head == null) head = node;
			else cur.next = node;
			cur = node;
			if (node.next != null) queue.offer(node.next);
		}
		return head;
	}
}
