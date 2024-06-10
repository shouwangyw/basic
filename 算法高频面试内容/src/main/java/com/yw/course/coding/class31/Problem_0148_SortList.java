package com.yw.course.coding.class31;

import com.yw.entity.ListNode;

/**
 * @author yangwei
 */
public class Problem_0148_SortList {

	// 方法一：归并排序-递归版本
	public ListNode sortList(ListNode head) {
		ListNode p = head;
		int n = 0;
		while (p != null) {
			n++;
			p = p.next;
		}
		return mergeList(head, n);
	}
	private ListNode mergeList(ListNode head, int n) {
		if (head == null || head.next == null) return head;
		int l = n / 2, r = n - l;
		ListNode left = head, right = head, p = right;
		for (int i = 0; i < l; i++, right = right.next) p = right;
		p.next = null;
		left = mergeList(left, l);
		right = mergeList(right, r);
		ListNode hair = new ListNode();
		p = hair;
		while (left != null || right != null) {
			if (right == null || (left != null && left.val <= right.val)) {
				p.next = left;
				left = left.next;
			} else {
				p.next = right;
				right = right.next;
			}
			p = p.next;
		}
		return hair.next;
	}

	// 方法二：归并排序-迭代版本
	public ListNode sortList1(ListNode head) {
		ListNode p = head;
		int n = 0;
		while (p != null) {
			n++;
			p = p.next;
		}
		ListNode hair = new ListNode(0, head);
		for (int step = 1; step < n; step <<= 1) {
			ListNode pre = hair, cur = hair.next;
			while (cur != null) {
				ListNode left = cur;
				for (int i = 1; i < step && cur.next != null; i++) cur = cur.next;
				ListNode right = cur.next;
				cur.next = null;
				cur = right;
				for (int i = 1; i < step && cur != null && cur.next != null; i++) cur = cur.next;
				ListNode next = null;
				if (cur != null) {
					next = cur.next;
					cur.next = null;
				}
				pre.next = mergeList(left, right);
				while (pre.next != null) pre = pre.next;
				cur = next;
			}
		}
		return hair.next;
	}
	private ListNode mergeList(ListNode left, ListNode right) {
		ListNode hair = new ListNode(), p = hair;
		while (left != null || right != null) {
			if (right == null || (left != null && left.val <= right.val)) {
				p.next = left;
				left = left.next;
			} else {
				p.next = right;
				right = right.next;
			}
			p = p.next;
		}
		return hair.next;
	}

	// 链表的快速排序
	// 时间复杂度O(N*logN), 空间复杂度O(logN)
	public static ListNode sortList2(ListNode head) {
		int n = 0;
		ListNode cur = head;
		while (cur != null) {
			cur = cur.next;
			n++;
		}
		return process(head, n).head;
	}

	public static class HeadAndTail {
		public ListNode head;
		public ListNode tail;

		public HeadAndTail(ListNode h, ListNode t) {
			head = h;
			tail = t;
		}
	}

	public static HeadAndTail process(ListNode head, int n) {
		if (n == 0) {
			return new HeadAndTail(head, head);
		}
		int index = (int) (Math.random() * n);
		ListNode cur = head;
		while (index-- != 0) {
			cur = cur.next;
		}
		Record r = partition(head, cur);
		HeadAndTail lht = process(r.lhead, r.lsize);
		HeadAndTail rht = process(r.rhead, r.rsize);
		if (lht.tail != null) {
			lht.tail.next = r.mhead;
		}
		r.mtail.next = rht.head;
		return new HeadAndTail(lht.head != null ? lht.head : r.mhead, rht.tail != null ? rht.tail : r.mtail);
	}

	public static class Record {
		public ListNode lhead;
		public int lsize;
		public ListNode rhead;
		public int rsize;
		public ListNode mhead;
		public ListNode mtail;

		public Record(ListNode lh, int ls, ListNode rh, int rs, ListNode mh, ListNode mt) {
			lhead = lh;
			lsize = ls;
			rhead = rh;
			rsize = rs;
			mhead = mh;
			mtail = mt;
		}
	}

	public static Record partition(ListNode head, ListNode mid) {
		ListNode lh = null;
		ListNode lt = null;
		int ls = 0;
		ListNode mh = null;
		ListNode mt = null;
		ListNode rh = null;
		ListNode rt = null;
		int rs = 0;
		ListNode tmp = null;
		while (head != null) {
			tmp = head.next;
			head.next = null;
			if (head.val < mid.val) {
				if (lh == null) {
					lh = head;
					lt = head;
				} else {
					lt.next = head;
					lt = head;
				}
				ls++;
			} else if (head.val > mid.val) {
				if (rh == null) {
					rh = head;
					rt = head;
				} else {
					rt.next = head;
					rt = head;
				}
				rs++;
			} else {
				if (mh == null) {
					mh = head;
					mt = head;
				} else {
					mt.next = head;
					mt = head;
				}
			}
			head = tmp;
		}
		return new Record(lh, ls, rh, rs, mh, mt);
	}

}
