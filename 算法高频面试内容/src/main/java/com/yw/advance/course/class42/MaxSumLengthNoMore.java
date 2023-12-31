package com.yw.advance.course.class42;

/**
 * 给定一个数组arr，和一个正数m
 * 返回在子数组长度不大于m的情况下，最大的子数组累加和
 *
 * @author yangwei
 */
public class MaxSumLengthNoMore {
    // 暴力解，O(N^2) 用作对数器
    public static int test(int[] arr, int m) {
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
}
