package com.yw.course.advance.class09;

import com.yw.entity.Node;

import java.util.Stack;

/**
 * @author yangwei
 */
public class Code02_IsPalindromeList {

    // 方法一：借助容器 栈，额外空间O(N)
public static boolean isPalindromeByStack(Node head) {
    Stack<Node> stack = new Stack<>();
    Node cur = head;
    while (cur != null) {
        stack.push(cur);
        cur = cur.next;
    }
    cur = head;
    while (cur != null) {
        if (cur.val != stack.pop().val) return false;
        cur = cur.next;
    }
    return true;
}

    // 方法二：还是利用栈，只将右半部分入栈，额外空间O(N/2)
    public static boolean isPalindromeByHalfStack(Node head) {
        if (head == null || head.next == null) return true;
        // 找中点
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 中点之后的节点入栈
        Stack<Node> stack = new Stack<>();
        while (slow.next != null) {
            stack.push(slow.next);
            slow = slow.next;
        }
        Node cur = head;
        // 依次弹出栈中元素比较
        while (!stack.isEmpty()) {
            if (cur.val != stack.pop().val) return false;
            cur = cur.next;
        }
        return true;
    }

    // 方法三：翻转链表中点位置以后的链表，额外空间O(1)
    public static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;
        // 1. 快慢指针找中点
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 2. 逆序中点(奇数节点时slow中点、偶数节点时slow是上中点)之后的链表
        Node revHead = null, cur = slow.next, next;
        slow.next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = revHead;
            revHead = cur;
            cur = next;
        }
        // 3. 头尾比较，判断是否是回文
        Node p = revHead;
        cur = head;
        boolean ans = true;
        while (cur != null && p != null) {
            if (cur.val != p.val) {
                ans = false;
                break;
            }
            cur = cur.next;
            p = p.next;
        }
        // 4. 恢复原链表
        Node pre = null;
        cur = revHead;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        slow.next = pre;
        return ans;
    }

    public static void main(String[] args) {

        Node head = null;
        printLinkedList(head);
        System.out.print(isPalindromeByStack(head) + " | ");
        System.out.print(isPalindromeByHalfStack(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindromeByStack(head) + " | ");
        System.out.print(isPalindromeByHalfStack(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.print(isPalindromeByStack(head) + " | ");
        System.out.print(isPalindromeByHalfStack(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindromeByStack(head) + " | ");
        System.out.print(isPalindromeByHalfStack(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindromeByStack(head) + " | ");
        System.out.print(isPalindromeByHalfStack(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindromeByStack(head) + " | ");
        System.out.print(isPalindromeByHalfStack(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindromeByStack(head) + " | ");
        System.out.print(isPalindromeByHalfStack(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindromeByStack(head) + " | ");
        System.out.print(isPalindromeByHalfStack(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindromeByStack(head) + " | ");
        System.out.print(isPalindromeByHalfStack(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

    }

    private static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
        System.out.println();
    }

}
