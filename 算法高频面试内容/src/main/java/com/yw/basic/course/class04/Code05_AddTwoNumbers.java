package com.yw.basic.course.class04;

import com.yw.entity.ListNode;

/**
 * 测试链接：https://leetcode.cn/problems/add-two-numbers/
 *
 * @author yangwei
 */
public class Code05_AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = getLength(l1) > getLength(l2) ? l1 : l2;
        ListNode p = head, q = head == l1 ? l2 : l1, pre = p;
        int c = 0;
        while (p != null) {
            int x = c + p.val + (q == null ? 0 : q.val);
            c = x / 10;
            p.val = x % 10;
            pre = p;
            p = p.next;
            if (q != null) q = q.next;
            if (q == null && c == 0) break;
        }
        if (c == 1) pre.next = new ListNode(1);
        return head;
    }

    private int getLength(ListNode head) {
        int len = 0;
        ListNode cur = head;
        while (cur != null) {
            len++;
            cur = cur.next;
        }
        return len;
    }
}
