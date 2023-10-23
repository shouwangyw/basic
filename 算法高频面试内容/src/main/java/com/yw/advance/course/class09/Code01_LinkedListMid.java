package com.yw.advance.course.class09;

import com.yw.entity.Node;

import java.util.ArrayList;

/**
 * @author yangwei
 */
public class Code01_LinkedListMid {

    // 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    public static Node midOrUpMidNode(Node head) {
        // 空节点、1个节点、2个节点，都是返回head
        if (head == null || head.next == null || head.next.next == null) return head;
        // 3个节点或以上，定义快、慢指针
        Node slow = head, fast = head;
        // 当快指针可以走两步时，慢指针每次走一步，快指针走两步
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
    public static Node midOrDownMidNode(Node head) {
        // 空节点、1个节点，都是返回head
        if (head == null || head.next == null) return head;
        // 2个节点或以上，定义快、慢指针
        Node slow = head, fast = head;
        // 当快指针可以走两步时，慢指针每次走一步，快指针走两步
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // fast.next == null 说明是奇数个节点，否则是偶数个节点
        return fast.next == null ? slow : slow.next;
    }

    // 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
    public static Node midOrUpMidPreNode(Node head) {
        // 空节点、1个节点、2个节点，都是返回null
        if (head == null || head.next == null || head.next.next == null) return null;
        // 3个节点或以上，定义快、慢指针
        Node slow = head, fast = head;
        // fast先走2步
        fast = fast.next.next;
        // 当快指针还可以走两步时，慢指针每次走一步，快指针走两步
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
    public static Node midOrDownMidPreNode(Node head) {
        // 空节点、1个节点，都是返回null
        if (head == null || head.next == null) return null;
        // 2个节点，返回head
        if (head.next.next == null) return head;
        // 3个节点或以上，定义快、慢指针
        Node slow = head, fast = head;
        // fast先走1步
        fast = fast.next;
        // 当快指针还可以走两步时，慢指针每次走一步，快指针走两步
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static Node right1(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 1) / 2);
    }

    public static Node right2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get(arr.size() / 2);
    }

    public static Node right3(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 3) / 2);
    }

    public static Node right4(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 2) / 2);
    }

    public static void main(String[] args) {
        Node test = null;
        test = new Node(0,
                new Node(1,
                        new Node(2,
                                new Node(3,
                                        new Node(4,
                                                new Node(5,
                                                        new Node(6,
                                                                new Node(7,
                                                                        new Node(8)))))))));

        Node ans1 = null;
        Node ans2 = null;

        ans1 = midOrUpMidNode(test);
        ans2 = right1(test);
        System.out.println(ans1 != null ? ans1.val : "无");
        System.out.println(ans2 != null ? ans2.val : "无");

        ans1 = midOrDownMidNode(test);
        ans2 = right2(test);
        System.out.println(ans1 != null ? ans1.val : "无");
        System.out.println(ans2 != null ? ans2.val : "无");

        ans1 = midOrUpMidPreNode(test);
        ans2 = right3(test);
        System.out.println(ans1 != null ? ans1.val : "无");
        System.out.println(ans2 != null ? ans2.val : "无");

        ans1 = midOrDownMidPreNode(test);
        ans2 = right4(test);
        System.out.println(ans1 != null ? ans1.val : "无");
        System.out.println(ans2 != null ? ans2.val : "无");

    }

}
