package com.yw.course.advance.class24;

import java.util.Deque;
import java.util.LinkedList;

import static com.yw.util.CommonUtils.generateRandomArray;
import static com.yw.util.CommonUtils.isEqual;

/**
 * @author yangwei
 */
public class Code01_SlidingWindowMaxArray {

    // 方法一：暴力的对数器方法
    public static int[] getMaxOfWindow0(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) return null;
        int n = arr.length;
        int[] ans = new int[n - w + 1];
        int idx = 0, l = 0, r = w - 1;
        while (r < n) {
            int max = arr[l];
            for (int i = l + 1; i <= r; i++) max = Math.max(max, arr[i]);
            ans[idx++] = max;
            l++;
            r++;
        }
        return ans;
    }

    // 方法二：
    public static int[] getMaxOfWindow(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) return null;
        int n = arr.length;
        int[] ans = new int[n - w + 1];
        int idx = 0; // 用于结果数组设置答案
        // 定义一个双端队列，用于存放下标
        Deque<Integer> qmax = new LinkedList<>();
        for (int r = 0; r < n; r++) {
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[r]) qmax.pollLast();
            qmax.addLast(r);
            if (qmax.peekFirst() == r - w) qmax.pollFirst();
            if (r >= w - 1) ans[idx++] = arr[qmax.peekFirst()];
        }

        return ans;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxOfWindow0(arr, w);
            int[] ans2 = getMaxOfWindow(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
