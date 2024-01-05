package com.yw.course.advance.class18;

import java.util.Arrays;

/**
 * @author yangwei
 */
public class Code02_CardsInLine {

    // 方法一：常识暴力递归
    public static int winByRecurse(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        return Math.max(processA(arr, 0, arr.length - 1), processB(arr, 0, arr.length - 1));
    }
    // 在 [l,r] 范围，返回先手A获得的最好分数
    private static int processA(int[] arr, int l, int r) {
        if (l == r) return arr[l];
        return Math.max(arr[l] + processB(arr, l + 1, r), arr[r] + processB(arr, l, r - 1));
    }
    // 在 [l,r] 范围，返回后手B获得的最好分数
    private static int processB(int[] arr, int l, int r) {
        if (l == r) return 0;
        // 先手拿走 l或r 位置，当前后手获得最小分数
        return Math.min(processA(arr, l + 1, r), processA(arr, l, r - 1));
    }

    // 方法二：缓存优化（傻缓存，自顶向下的动态规划，也叫记忆化搜索）
    public static int winByCache(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int n = arr.length;
        int[][] dpA = new int[n][n];
        int[][] dpB = new int[n][n];
        for (int i = 0; i < n; i++) {
            // -1: 表示没有计算过
            Arrays.fill(dpA[i], -1);
            Arrays.fill(dpB[i], -1);
        }
        return Math.max(processA(arr, 0, n - 1, dpA, dpB), processB(arr, 0, n - 1, dpA, dpB));
    }
    private static int processA(int[] arr, int l, int r, int[][] dpA, int[][] dpB) {
        if (dpA[l][r] != -1) return dpA[l][r];
        return dpA[l][r] = l == r ? arr[l] : Math.max(
                arr[l] + processB(arr, l + 1, r, dpA, dpB),
                arr[r] + processB(arr, l, r - 1, dpA, dpB));
    }
    // 在 [l,r] 范围，返回后手B获得的最好分数
    private static int processB(int[] arr, int l, int r, int[][] dpA, int[][] dpB) {
        if (dpB[l][r] != -1) return dpB[l][r];
        return dpB[l][r] = l == r ? 0 : Math.min(
                processA(arr, l + 1, r, dpA, dpB),
                processA(arr, l, r - 1, dpA, dpB));
    }

    // 方法三：动态规划
    public static int win(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int n = arr.length;
        int[][] dpA = new int[n][n];
        int[][] dpB = new int[n][n];
        // 设置dpA的对角线
        for (int i = 0; i < n; i++) dpA[i][i] = arr[i];
        for (int i = 1; i < n; i++) {
            int l = 0, r = i;
            while (r < n) {
                dpA[l][r] = Math.max(arr[l] + dpB[l + 1][r], arr[r] + dpB[l][r - 1]);
                dpB[l][r] = Math.min(dpA[l + 1][r], dpA[l][r - 1]);
                l++;
                r++;
            }
        }
        return Math.max(dpA[0][n - 1], dpB[0][n - 1]);
    }

    public static void main(String[] args) {
        int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        System.out.println(winByRecurse(arr));
        System.out.println(winByCache(arr));
        System.out.println(win(arr));
//        System.out.println(win(new int[]{7, 4, 16, 15, 1}));
    }
}
