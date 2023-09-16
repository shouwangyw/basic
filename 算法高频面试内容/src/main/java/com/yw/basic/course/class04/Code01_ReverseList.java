package com.yw.basic.course.class04;

import com.yw.entity.DoubleNode;
import com.yw.entity.Node;

import java.util.ArrayList;
import java.util.List;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code01_ReverseList {

    private static Node reverseLinkedList(Node head) {
        Node pre = null, cur = head, next;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    private static DoubleNode reverseDoubleList(DoubleNode head) {
        DoubleNode pre = null, cur = head, next;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            cur.prev = next;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    private static Node testReverseLinkedList(Node head) {
        if (head == null) return null;
        List<Node> nodes = new ArrayList<>();
        while (head != null) {
            nodes.add(head);
            head = head.next;
        }
        int size = nodes.size();
        for (int i = 0; i < size; i++) {
            if (i == 0) nodes.get(i).next = null;
            else nodes.get(i).next = nodes.get(i - 1);
        }
        return nodes.get(size - 1);
    }

    public static DoubleNode testReverseDoubleList(DoubleNode head) {
        if (head == null) return null;
        List<DoubleNode> doubleNodes = new ArrayList<>();
        while (head != null) {
            doubleNodes.add(head);
            head = head.next;
        }
        int size = doubleNodes.size();
        for (int i = 0; i < size; i++) {
            DoubleNode cur = doubleNodes.get(i);
            if (i == 0) cur.next = null;
            else {
                DoubleNode pre = doubleNodes.get(i - 1);
                pre.prev = cur;
                cur.next = pre;
            }
        }
        return doubleNodes.get(size - 1);
    }

    // for test
    public static boolean checkLinkedListReverse(List<Integer> origin, Node head) {
        for (int i = origin.size() - 1; i >= 0; i--) {
            if (!origin.get(i).equals(head.val)) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    // for test
    public static boolean checkDoubleListReverse(List<Integer> origin, DoubleNode head) {
        DoubleNode end = null;
        for (int i = origin.size() - 1; i >= 0; i--) {
            if (!origin.get(i).equals(head.val)) {
                return false;
            }
            end = head;
            head = head.next;
        }
        for (int i = 0; i < origin.size(); i++) {
            if (!origin.get(i).equals(end.val)) {
                return false;
            }
            end = end.prev;
        }
        return true;
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 100000;
        // 测试反转单链表
        for (int i = 0; i < testTime; i++) {
            Node node = generateRandomLinkedList(len, value);
            List<Integer> list = getLinkedListValues(node);
            Node result = reverseLinkedList(node);
            if (!checkLinkedListReverse(list, result)) {
                System.out.println("Oops1!");
                printLinkedListValues(node);
            }
        }
        for (int i = 0; i < testTime; i++) {
            Node node = generateRandomLinkedList(len, value);
            List<Integer> list = getLinkedListValues(node);
            Node result = testReverseLinkedList(node);
            if (!checkLinkedListReverse(list, result)) {
                System.out.println("Oops1!");
                printLinkedListValues(node);
            }
        }
        // 测试反转双向链表
        for (int i = 0; i < testTime; i++) {
            DoubleNode node = generateRandomDoubleList(len, value);
            List<Integer> list = getDoubleListValues(node);
            DoubleNode result = reverseDoubleList(node);
            if (!checkDoubleListReverse(list, result)) {
                System.out.println("Oops1!");
                printDoubleListValues(node);
            }
        }
        for (int i = 0; i < testTime; i++) {
            DoubleNode node = generateRandomDoubleList(len, value);
            List<Integer> list = getDoubleListValues(node);
            DoubleNode result = testReverseDoubleList(node);
            if (!checkDoubleListReverse(list, result)) {
                System.out.println("Oops1!");
                printDoubleListValues(node);
            }
        }
        System.out.println("test all passed!");
    }

}