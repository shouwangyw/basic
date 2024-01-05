package com.yw.course.basic.class04;

import com.yw.entity.ListNode;

/**
 * 测试链接：https://leetcode.cn/problems/merge-two-sorted-lists
 *
 * @author yangwei
 */
public class Code06_MergeTwoSortedLinkedList {

    public static ListNode mergeTwoLists(ListNode head1, ListNode head2) {
        if (head1 == null) return head2;
        if (head2 == null) return head1;
        ListNode head = head1.val < head2.val ? head1 : head2, cur = head;
        ListNode p = head.next, q = head == head1 ? head2 : head1;
        while (p != null && q != null) {
            if (p.val < q.val) {
                cur.next = p;
                p = p.next;
            } else {
                cur.next = q;
                q = q.next;
            }
            cur = cur.next;
        }
        cur.next = p != null ? p : q;
        return head;
    }

}
