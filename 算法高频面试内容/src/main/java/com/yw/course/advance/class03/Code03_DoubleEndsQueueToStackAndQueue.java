package com.yw.course.advance.class03;

import com.yw.course.basic.class04.Code03_DoubleLinkedListToDeque;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author yangwei
 */
public class Code03_DoubleEndsQueueToStackAndQueue extends Code03_DoubleLinkedListToDeque {

    public static class MyStack<T> {
        private MyDeque<T> queue;

        public MyStack() {
            queue = new MyDeque<>();
        }
        public void push(T value) {
            queue.pushHead(value);
        }
        public T pop() {
            return queue.pollHead();
        }
        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static class MyQueue<T> {
        private MyDeque<T> queue;

        public MyQueue() {
            queue = new MyDeque<>();
        }
        public void push(T value) {
            queue.pushHead(value);
        }
        public T poll() {
            return queue.pollTail();
        }
        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null && o2 != null) {
            return false;
        }
        if (o1 != null && o2 == null) {
            return false;
        }
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1.equals(o2);
    }

    public static void main(String[] args) {
        int oneTestDataNum = 100;
        int value = 10000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            MyStack<Integer> myStack = new MyStack<>();
            MyQueue<Integer> myQueue = new MyQueue<>();
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                int nums = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myStack.push(nums);
                    stack.push(nums);
                } else {
                    if (Math.random() < 0.5) {
                        myStack.push(nums);
                        stack.push(nums);
                    } else {
                        if (!isEqual(myStack.pop(), stack.pop())) {
                            System.out.println("oops!");
                        }
                    }
                }
                int numq = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myQueue.push(numq);
                    queue.offer(numq);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.push(numq);
                        queue.offer(numq);
                    } else {
                        if (!isEqual(myQueue.poll(), queue.poll())) {
                            System.out.println("oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }

}
