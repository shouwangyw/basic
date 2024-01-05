package com.yw.course.advance.class14;

import java.util.PriorityQueue;
import java.util.Queue;

import static com.yw.util.CommonUtils.generateRandomArray;

/**
 * @author yangwei
 */
public class Code02_LessMoneySplitGold {

    // 方法一：暴力
    public static int lessMoneyTest(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        return process(arr, 0);
    }
    // 等待合并的数都在arr里，pre之前的合并行为产生了多少总代价
    // arr中只剩一个数字的时候，停止合并，返回最小的总代价
    private static int process(int[] arr, int pre) {
        if (arr.length == 1) return pre;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }
    private static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    public static int lessMoney(int[] arr) {
        Queue<Integer> queue = new PriorityQueue<>();
        for (int value : arr) {
            queue.add(value);
        }
        int sum = 0, cur;
        while (queue.size() > 1) {
            cur = queue.poll() + queue.poll();
            sum += cur;
            queue.add(cur);
        }
        return sum;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoneyTest(arr) != lessMoney(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
