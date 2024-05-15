package com.yw.course.coding.class24;

import java.util.Arrays;
import java.util.Map;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code02_KthMinPair {

    // 方法一：暴力解，时间复杂度O(N^2 * log (N^2))
    public static int[] kthMinPair0(int[] arr, int k) {
        int n = arr.length;
        if (k > n * n) return null;
        Pair[] pairs = new Pair[n * n];
        // 暴力生成这N^2个数值对
        int idx = 0;
        for (int x : arr) {
            for (int y : arr) pairs[idx++] = new Pair(x, y);
        }
        // 排序
        Arrays.sort(pairs, (o1, o2) -> o1.x == o2.x ? o1.y - o2.y : o1.x - o2.x);
        return new int[] {pairs[k - 1].x, pairs[k - 1].y};
    }
    private static class Pair {
        private int x;
        private int y;
        public Pair(int x, int y) { this.x = x; this.y = y; }
    }

    // 方法二：时间复杂度O(N*logN)的复杂度，可以过，但还不是最优解
    public static int[] kthMinPair(int[] arr, int k) {
        int n = arr.length;
        if (k > n * n) return null;
        // 排序，时间复杂度O(N*logN)
        Arrays.sort(arr);
        // 先确定第K小的数值对的第一维数字
        int firstNum = arr[(k - 1) / n];
        // 数出比firstNum小的数有几个，以及等于firstNum的数有几个
        int lessFirstNumCnt = 0, equalFirstNumCnt = 0;
        for (int i = 0; i < n && arr[i] <= firstNum; i++) {
            if (arr[i] < firstNum) lessFirstNumCnt++;
            else equalFirstNumCnt++;
        }
        // 除去小于firstNum提供的，还需要凑几个数
        int rest = k - lessFirstNumCnt * n;
        return new int[] {firstNum, arr[(rest - 1) / equalFirstNumCnt]};
    }

    // 方法三：最优解，时间复杂度O(N)
    public static int[] kthMinPairOptimal(int[] arr, int k) {
        int n = arr.length;
        if (k > n * n) return null;
        // 在无序数组中，找到第K小的数，返回值，第K小，以1作为开始
        int firstNum = getMinKth(arr, (k - 1) / n);
        int lessFirstNumCnt = 0, equalFirstNumCnt = 0;
        for (int x : arr) {
            if (x < firstNum) lessFirstNumCnt++;
            if (x == firstNum) equalFirstNumCnt++;
        }
        // 除去小于firstNum提供的，还需要凑几个数
        int rest = k - lessFirstNumCnt * n;
        return new int[] {firstNum, getMinKth(arr, (rest - 1) / equalFirstNumCnt)};
    }
    // 改写快排，时间复杂度O(N)
    // 在无序数组arr中找到，如果排序的话，arr[idx]的数是什么？
    private static int getMinKth(int[] arr, int idx) {
        int l = 0, r = arr.length - 1, pivot = 0;
        int[] range;
        while (l < r) {
            pivot = arr[l + (int) (Math.random() * (r - l + 1))];
            range = partition3Way(arr, l, r, pivot);
            if (idx < range[0]) r = range[0] - 1;
            else if (idx > range[1]) l = range[1] + 1;
            else return pivot;
        }
        return arr[l];
    }
    private static int[] partition3Way(int[] arr, int l, int r, int pivot) {
        int lessR = l - 1, moreL = r + 1;
        while (l < moreL) {
            if (arr[l] < pivot) swap(arr, ++lessR, l++);
            else if (arr[l] > pivot) swap(arr, l, --moreL);
            else l++;
        }
        return new int[] {lessR + 1, moreL - 1};
    }

    public static void main(String[] args) {
        int max = 100;
        int len = 30;
        int testTimes = 100000;
        System.out.println("test bagin, test times : " + testTimes);
        for (int i = 0; i < testTimes; i++) {
            int[] arr = getRandomArray(max, len);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);
            int N = arr.length * arr.length;
            int k = (int) (Math.random() * N) + 1;
            int[] ans1 = kthMinPair0(arr1, k);
            int[] ans2 = kthMinPair(arr2, k);
            int[] ans3 = kthMinPairOptimal(arr3, k);
            if (ans1[0] != ans2[0] || ans2[0] != ans3[0] || ans1[1] != ans2[1] || ans2[1] != ans3[1]) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test finish");
    }
    // 为了测试，生成值也随机，长度也随机的随机数组
    // 随机测试了百万组，保证三种方法都是对的
    private static int[] getRandomArray(int max, int len) {
        int[] arr = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }
}
