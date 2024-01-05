package com.yw.course.advance.class03;

import java.util.Stack;

/**
 * @author yangwei
 */
public class Code05_GetMinStack {

    public static class MinStack {
        // 定义两个栈：一个数据栈(用于正常存放数据)、一个最小栈(用于存放栈中的最小值)
        private Stack<Integer> dataStack;
        private Stack<Integer> minStack;
        public MinStack() {
            dataStack = new Stack<>();
            minStack = new Stack<>();
        }
        public void push(int newVal) {
            dataStack.push(newVal);
            // 每次push新值时，同时维护最小栈minStack
            // minStack如果为空，则直接放进去；如果不为空，则比较当前栈顶最小值和新值，取小的
            minStack.push(minStack.isEmpty() ? newVal : Math.min(newVal, minStack.peek()));
        }
        public int pop() {
            if (dataStack.isEmpty()) throw new RuntimeException("MinStack is Empty");
            int val = dataStack.pop();
            // 每次pop时，比较是否是栈顶的最小值，如果是，则minStack也需要出栈
            if (val == minStack.peek()) minStack.pop();
            return val;
        }
        public int getMin() {
            if (minStack.isEmpty()) throw new RuntimeException("MinStack is Empty");
            return minStack.peek();
        }
    }

    public static void main(String[] args) {
        MinStack stack1 = new MinStack();

        stack1.push(3);
        System.out.println(stack1.getMin());

        stack1.push(4);
        System.out.println(stack1.getMin());

        stack1.push(1);
        System.out.println(stack1.getMin());

        System.out.println(stack1.pop());
        System.out.println(stack1.getMin());
    }
}
