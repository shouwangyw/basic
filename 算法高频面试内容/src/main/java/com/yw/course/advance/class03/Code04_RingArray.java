package com.yw.course.advance.class03;

/**
 * @author yangwei
 */
public class Code04_RingArray {

    public static class MyQueue {
        private int[] items;
        private int head;
        private int tail;
        private int size;
        private final int capacity;

        public MyQueue(int capacity) {
            this.items = new int[capacity];
            this.head = 0;
            this.tail = 0;
            this.size = 0;
            this.capacity = capacity;
        }

        public void push(int val) {
            if (isFull()) throw new RuntimeException("queue is full");
            size++;
            items[head] = val;
            head = resetOffset(head);
        }

        public int pop() {
            if (isEmpty()) throw new RuntimeException("queue is empty");
            size--;
            int item = items[tail];
            tail = resetOffset(tail);
            return item;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == capacity;
        }

        private int resetOffset(int idx) {
            return idx < capacity - 1 ? idx + 1 : 0;
        }
    }
}
