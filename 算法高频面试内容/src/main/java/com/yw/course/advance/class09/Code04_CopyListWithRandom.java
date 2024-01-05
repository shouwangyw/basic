package com.yw.course.advance.class09;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试链接 : https://leetcode.cn/problems/copy-list-with-random-pointer/
 *
 * @author yangwei
 */
public class Code04_CopyListWithRandom {

    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    // 方法一：借助容器Map
    public static Node copyRandomListByMap(Node head) {
        // key: 老节点，value: 新节点
        Map<Node, Node> nodeMap = new HashMap<>();
        // 1. 定义一个指针遍历原链表，将所有节点克隆一份，并和来节点建立映射关系
        Node cur = head;
        while (cur != null) {
            nodeMap.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        // 2. 再次遍历原链表，设置新链表的next和random
        cur = head;
        while (cur != null) {
            Node newNode = nodeMap.get(cur);
            newNode.next = nodeMap.get(cur.next);
            newNode.random = nodeMap.get(cur.random);
            cur = cur.next;
        }
        return nodeMap.get(head);
    }

    // 方法二：不借助容器Map
    public static Node copyRandomList(Node head) {
        if (head == null) return null;
        // 定义一个指针遍历原链表，和next记录下一步
        // 1. 将所有节点克隆一份，挂在每个节点的后面
        // 1->2->3->null  ==>> 1->1'->2->2'->3->3'->null
        Node cur = head, next;
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }
        // 2. 依次设置 1' 2' 3' random指针
        cur = head;
        while (cur != null) {
            next = cur.next.next;
            Node copy = cur.next;
            copy.random = cur.random == null ? null : cur.random.next;
            cur = next;
        }
        // 3. 分拆新老节点
        cur = head;
        Node copyHead = head.next;
        while (cur != null) {
            next = cur.next.next;
            Node copy = cur.next;
            cur.next = next;
            copy.next = next == null ? null : next.next;
            cur = next;
        }
        return copyHead;
    }
}
