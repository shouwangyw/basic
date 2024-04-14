package com.yw.course.coding.class19;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试链接: https://leetcode.cn/problems/lfu-cache/
 * @author yangwei
 */
public class Code02_LFUCache {
}
class LFUCache {
    // 定义一个双向链表结构的节点
    private static class Node {
        private int key;
        private int val;
        private int cnt; // 被访问的次数
        private Node pre;
        private Node next;
        public Node() {}
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.cnt = 1;
        }
    }
    // 定义一个桶结构，相同访问次数的Node放进一个桶，在桶中以双向链表的结构相连
    private static class Bucket {
        private Node head;      // 桶的头节点
        private Node tail;      // 桶的尾节点
        private Bucket pre;     // 桶与桶之间是双向链表结构
        private Bucket next;
        public Bucket(Node node) {
            head = node;
            tail = node;
        }
        // 把一个新的节点加入到桶中，新的节点都放在桶的顶端，成为新的头部
        public void insertAtHead(Node node) {
            node.next = head;
            head.pre = node;
            head = node;
        }
        // 桶判空
        public boolean isEmpty() {
            return head == null;
        }
        // 删除节点，并保证节点的上下环境重新连接
        public void deleteNode(Node node) {
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                if (node == head) {
                    head = node.next;
                    head.pre = null;
                } else if (node == tail) {
                    tail = tail.pre;
                    tail.next = null;
                } else {
                    node.pre.next = node.next;
                    node.next.pre = node.pre;
                }
            }
            node.pre = null;
            node.next = null;
        }
    }
    // 定义一个HashMap，用于缓存数据节点
    private Map<Integer, Node> cache;
    private int capacity;
    // 定义一个HashMap，用于存储Node在哪个桶中
    private Map<Node, Bucket> nodeBucketMap;
    // 最左的桶
    private Bucket firstBucket;
    public LFUCache(int capacity) {
        this.cache = new HashMap<>();
        this.capacity = capacity;
        this.nodeBucketMap = new HashMap<>();
        this.firstBucket = null;
    }

    public int get(int key) {
        // 从缓存查询，查询不到返回-1，查询到则访问次数+1并更新节点在桶结构中的位置
        Node node = cache.get(key);
        if (node == null) return -1;
        node.cnt++;
        Bucket bucket = nodeBucketMap.get(node);
        upsert(node, bucket);
        return node.val;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        Node node = cache.get(key);
        // 若缓存中有，则更新缓存值、访问次数+1，并更新节点在桶结构中的位置
        if (node != null) {
            node.val = value;
            node.cnt++;
            Bucket bucket = nodeBucketMap.get(node);
            upsert(node, bucket);
        } else {
            // 否则，缓存中没有，就得考虑是否淘汰缓存
            if (cache.size() == capacity) {
                Node expireNode = firstBucket.tail;
                firstBucket.deleteNode(expireNode);
                modifyBucket(firstBucket);
                cache.remove(expireNode.key);
                nodeBucketMap.remove(expireNode);
            }
            node = new Node(key, value);
            if (firstBucket == null) firstBucket = new Bucket(node);
            else {
                if (firstBucket.head.cnt == node.cnt) firstBucket.insertAtHead(node);
                else {
                    Bucket newBucket = new Bucket(node);
                    newBucket.next = firstBucket;
                    firstBucket.pre = newBucket;
                    firstBucket = newBucket;
                }
            }
            cache.put(key, node);
            nodeBucketMap.put(node, firstBucket);
        }
    }
    // 入参node是当前节点，并且在bucket中，函数功能是将node的访问次数+1
    // 并将该node从bucket中删除，并放到次数+1后的桶中，整个过程既要保证桶与桶之间的双向链表，也要保证桶中节点与节点之间的双向链表
    private void upsert(Node node, Bucket bucket) {
        bucket.deleteNode(node);
        // preBucket表示次数+1的桶的前一个桶
        // 如果bucket中删掉node后：为空了，则bucket是要被删除的，前一个桶是bucket的前一个；否则bucket就是前一个桶
        Bucket preBucket = modifyBucket(bucket) ? bucket.pre : bucket;
        Bucket nextBucket = bucket.next;
        if (nextBucket == null) {
            // 没有下一个桶，就新建下一个桶
            nextBucket = new Bucket(node);
            if (preBucket != null) preBucket.next = nextBucket;
            nextBucket.pre = preBucket;
            if (firstBucket == null) firstBucket = nextBucket;
            nodeBucketMap.put(node, nextBucket);
        } else {
            if (nextBucket.head.cnt == node.cnt) {
                nextBucket.insertAtHead(node);
                nodeBucketMap.put(node, nextBucket);
            } else {
                // 有下一个桶，但是访问次数不相等，也需要新建下一个桶，并插入到 preBucket与nextBucket之间
                Bucket newBucket = new Bucket(node);
                if (preBucket != null) preBucket.next = newBucket;
                newBucket.pre = preBucket;
                newBucket.next = nextBucket;
                nextBucket.pre = newBucket;
                if (firstBucket == nextBucket) firstBucket = newBucket;
                nodeBucketMap.put(node, newBucket);
            }
        }
    }
    // 入参bucket是刚刚减少了一个节点的桶，函数功能是返回当前这个桶是否为空，空了返回true，不空返回false
    // 若不空，则什么都不做；否则，就将当前桶从整个桶的双向链表中删除，同时修正firstBucket
    private boolean modifyBucket(Bucket bucket) {
        if (!bucket.isEmpty()) return false;
        if (bucket == firstBucket) {
            firstBucket = bucket.next;
            if (firstBucket != null) firstBucket.pre = null;
        } else {
            bucket.pre.next = bucket.next;
            if (bucket.next != null) bucket.next.pre = bucket.pre;
        }
        return true;
    }
}