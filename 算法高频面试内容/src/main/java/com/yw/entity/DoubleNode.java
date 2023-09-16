package com.yw.entity;

/**
 * @author yangwei
 */
public class DoubleNode {
    public int val;
    public DoubleNode next;
    public DoubleNode prev;

    public DoubleNode(int val) {
        this.val = val;
    }

    public DoubleNode(int val, DoubleNode next, DoubleNode prev) {
        this.val = val;
        this.next = next;
        this.prev = prev;
    }
}
