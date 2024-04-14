package com.yw.course.coding.class19;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试链接 : https://leetcode.cn/problems/lru-cache/
 *
 * @author yangwei
 */
public class Code01_LRUCache {
}
class LRUCache {
    // 定义一个双向链表的节点结构
    private static class Node {
        private int val;
        private Node pre;
        private Node next;
        public Node() {}
        public Node(int val) {
            this.val = val;
        }
    }
    // 定义双向链表头尾节点
    private Node head;
    private Node tail;
    // 定义一个HashMap，用于缓存数据节点
    private Map<Integer, Node> cache;
    private int capacity;
    public LRUCache(int capacity) {
        this.head = new Node();
        this.tail = new Node();
        head.next = tail;
        tail.pre = head;
        this.cache = new HashMap<>(capacity);
        this.capacity = capacity;
    }
    // 先从缓存获取，获取不到返回-1；获取到了，则将节点调整到头部，然后返回
    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) return -1;
        insertAtHead(node);
        return node.val;
    }
    // 先从缓存获取，获取到了，则进行值更新，并将节点调整到头部，返回
    // 获取不到，则检查容量是否超了，若容量超了，则从尾部删除，否则将节点从头部加入并放入缓存
    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node != null) {
            node.val = value;
            insertAtHead(node);
            return;
        }
        if (cache.size() >= capacity) deleteTail();
        cache.put(key, insertHead(new Node(value)));
    }
    // 实现双向链表的一些操作: 将一个节点调整到头部，从头部加入节点，从尾部删除节点
    private void insertAtHead(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
        insertHead(node);
    }
    private Node insertHead(Node node) {
        node.next = head.next;
        node.pre = head;
        head.next.pre = node;
        head.next = node;
        return node;
    }
    private void deleteTail() {
        if (cache.size() <= 0) return;
        Node node = tail.pre;
        Collection<Node> nodes = cache.values();
        if (nodes.contains(node)) nodes.remove(node);
        tail.pre = node.pre;
        node.pre = null;
        node.next = null;
        tail.pre.next = tail;
    }
}