package com.yw.course.advance.class09;

import com.yw.entity.Node;

/**
 * @author yangwei
 */
public class Code03_SmallerEqualBigger {

    // 方法一：借助数组分区
    public static Node listPartitionByArray(Node head, int pivot) {
        if (head == null) return null;
        // 1. 遍历链表，计算链表节点个数
        Node cur = head;
        int cnt = 0;
        while (cur != null) {
            cnt++;
            cur = cur.next;
        }
        // 2. 创建一个Node数组，再次遍历原链表，将每个节点加入数组中
        Node[] nodes = new Node[cnt];
        cur = head;
        for (int i = 0; i < cnt; i++) {
            nodes[i] = cur;
            cur = cur.next;
        }
        // 3. 分区
        int small = -1, big = cnt, idx = 0;
        while (idx < big) {
            if (nodes[idx].val == pivot) idx++;
            else if (nodes[idx].val < pivot) swap(nodes, ++small, idx++);
            else swap(nodes, --big, idx);
        }
        // 4. 将数组中的节点串联起来
        for (int i = 1; i < cnt; i++) nodes[i - 1].next = nodes[i];
        nodes[cnt - 1].next = null;
        return nodes[0];
    }

    private static void swap(Node[] nodes, int i, int j) {
        if (i == j) return;
        Node tmp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = tmp;
    }

    // 方法二：不借助容器
    public static Node listPartition(Node head, int pivot) {
        // 定义6个变量分别是 小于区、等于区、大于区 的头和尾指针，和
        Node sh = null, st = null, eh = null, et = null, bh = null, bt = null;
        // 定义一个cur用于遍历head链表，next用于记录下一步
        Node cur = head, next;
        // 遍历原链表进行分区
        while (cur != null) {
            next = cur.next;
            cur.next = null;
            if (cur.val < pivot) {
                if (sh == null) sh = cur;
                else st.next = cur;
                st = cur;
            } else if (cur.val == pivot) {
                if (eh == null) eh = cur;
                else et.next = cur;
                et = cur;
            } else {
                if (bh == null) bh = cur;
                else bt.next = cur;
                bt = cur;
            }
            cur = next;
        }
        // 用小于区域的尾巴，去连等于区域的头
        if (st != null) {
            // 如果有小于区域
            st.next = eh;
            // 下一步，谁去连大于区域的头，谁就变成eT
            et = et == null ? st : et;
        }
        // 用等于区域的尾巴，去连大于区域的头
        if (et != null) et.next = bh;

        return sh != null ? sh : (eh != null ? eh : bh);
    }

    public static void main(String[] args) {
        Node head = new Node(7,
                new Node(9,
                        new Node(1,
                                new Node(8,
                                        new Node(5,
                                                new Node(2, new Node(5)))))));
        printLinkedList(head);
//        head = listPartitionByArray(head, 4);
		head = listPartition(head, 5);
        printLinkedList(head);
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
