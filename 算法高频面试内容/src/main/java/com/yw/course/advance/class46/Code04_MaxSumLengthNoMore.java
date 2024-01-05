package com.yw.course.advance.class46;

import java.util.LinkedList;

/**
 * 给定一个数组arr，和一个正数M，返回在子数组长度不大于M的情况下，最大的子数组累加和
 *
 * @author yangwei
 */
public class Code04_MaxSumLengthNoMore {

// 方法一：暴力解，时间复杂度 O(N^2)
public static int maxSubarraySumWithinLength0(int[] arr, int m) {
    if (arr == null || arr.length == 0 || m < 1) return 0;
    int n = arr.length, max = Integer.MIN_VALUE;
    for (int l = 0; l < n; l++) {
        int sum = 0;
        for (int r = l; r < n; r++) {
            if (r - l + 1 > m) break;
            sum += arr[r];
            max = Math.max(max, sum);
        }
    }
    return max;
}

    // 方法二：最优解，时间复杂度 O(N)
    public static int maxSubarraySumWithinLength(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 1) return 0;
        int n = arr.length;
        // 前缀和数组
        int[] sum = new int[n];
        sum[0] = arr[0];
        for (int i = 1; i < n; i++) sum[i] = sum[i - 1] + arr[i];
        LinkedList<Integer> qMax = new LinkedList<>();
        int idx = 0, end = Math.min(n, m);
        for (; idx < end; idx++) {
            while (!qMax.isEmpty() && sum[qMax.peekLast()] <= sum[idx]) qMax.pollLast();
            qMax.add(idx);
        }
        int max = sum[qMax.peekFirst()];
        int l = 0;
        for (; idx < n; l++, idx++) {
            if (qMax.peekFirst() == l) qMax.pollFirst();
            while (!qMax.isEmpty() && sum[qMax.peekLast()] <= sum[idx]) qMax.pollLast();
            qMax.add(idx);
            max = Math.max(max, sum[qMax.peekFirst()] - sum[l]);
        }
        for (; l < n - 1; l++) {
            if (qMax.peekFirst() == l) qMax.pollFirst();
            max = Math.max(max, sum[qMax.peekFirst()] - sum[l]);
        }
        return max;
    }

    public static void main(String[] args) {
        int maxN = 50;
        int maxValue = 100;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxN);
            int M = (int) (Math.random() * maxN);
            int[] arr = randomArray(N, maxValue);
            int ans1 = maxSubarraySumWithinLength0(arr, M);
            int ans2 = maxSubarraySumWithinLength(arr, M);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
    private static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }
}
