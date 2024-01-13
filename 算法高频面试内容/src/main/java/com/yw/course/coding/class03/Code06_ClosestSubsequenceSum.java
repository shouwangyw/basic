package com.yw.course.coding.class03;

import java.util.Arrays;

/**
 * 测试链接 : https://leetcode.cn/problems/closest-subsequence-sum/
 * 本题数据量描述:
 * 1 <= nums.length <= 40
 * -10^7 <= nums[i] <= 10^7
 * -10^9 <= goal <= 10^9
 * 通过这个数据量描述可知，需要用到分治，因为数组长度不大
 * 而值很大，用动态规划的话，表会爆
 *
 * @author yangwei
 */
public class Code06_ClosestSubsequenceSum {

    public int minAbsDifference(int[] nums, int goal) {
        int n = nums.length;
        // 数组长度最大40，左右各20个数暴力穷举每一种可能性，最多 2^20
        int[] l = new int[1 << 20], r = new int[1 << 20];
        // le: l数组结束位置，re: r数组结束位置，
        int le = process(nums, 0, n >> 1, 0, 0, l), re = process(nums, n >> 1, n, 0, 0, r);
        // 对 l 和 r 排序
        Arrays.sort(l, 0, le);
        Arrays.sort(r, 0, re--);
        int ans = Math.abs(goal);
        // 遍历左边每一种可能(l数组)l[i]，从右边找到最接近 goal-l[i] 的
        for (int i = 0; i < le; i++) {
            int t = goal - l[i];
            while (re > 0 && Math.abs(t - r[re - 1]) <= Math.abs(t - r[re])) re--;
            ans = Math.min(ans, Math.abs(t - r[re]));
        }
        return ans;
    }
    // 从idx开始、到end结束，所形成的累加和是sum，结果设置到 arr[k]，返回结束位置k
    private static int process(int[] nums, int idx, int end, int sum, int k, int[] arr) {
        if (idx == end) arr[k++] = sum;
        else {
            // 可以选择要idx位置的数
            k = process(nums, idx + 1, end, sum, k, arr);
            // 或者不要idx位置的数
            k = process(nums, idx + 1, end, sum + nums[idx], k, arr);
        }
        return k;
    }
}
