package com.yw.advance.course.class04;

import static com.yw.util.CommonUtils.*;

/**
 * 测试链接 : https://leetcode.cn/problems/reverse-pairs/
 *
 * @author yangwei
 */
public class Code04_BiggerThanRightTwice {

    public static int reversePairs(int[] arr) {
        if (arr == null || arr.length < 2) return 0;

        return mergeSort(arr, 0, arr.length - 1);
    }

    private static int mergeSort(int[] arr, int l, int r) {
        if (l == r) return 0;
        int mid = l + ((r - l) >> 1);
        int lPairs = mergeSort(arr, l, mid);
        int rPairs = mergeSort(arr, mid + 1, r);
        int pairs = 0;
        // ================== 收集翻转对 start ===================
        for (int i = l, j = mid + 1; i <= mid; i++) {
            // arr[j] << 1 可能越界
            while (j <= r && (long) arr[i] > ((long) arr[j] << 1)) j++;
            pairs += (j - mid - 1);
        }
        // ================== 收集翻转对 end ===================
        int[] tmp = new int[r - l + 1];
        int i = l, j = mid + 1, k = 0;
        while (i <= mid && j <= r) tmp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        while (i <= mid) tmp[k++] = arr[i++];
        while (j <= r) tmp[k++] = arr[j++];
        for (k = 0; k < tmp.length; k++) arr[l + k] = tmp[k];
        return pairs + lPairs + rPairs;
    }

    public static int reversePairs2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        // l < r
        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid) + process(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    public static int merge(int[] arr, int L, int m, int r) {
        // [L....M] [M+1....R]
        int ans = 0;
        // 目前囊括进来的数，是从[M+1, windowR)
        int windowR = m + 1;
        for (int i = L; i <= m; i++) {
            while (windowR <= r && (long) arr[i] > (long) arr[windowR] * 2) {
                windowR++;
            }
            ans += windowR - m - 1;
        }
        int[] help = new int[r - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = m + 1;
        while (p1 <= m && p2 <= r) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return ans;
    }

    // for test
    public static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > (arr[j] << 1)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            int ans1 = reversePairs(arr1);
            int ans2 = comparator(arr2);
            int ans3 = reversePairs2(arr3);
            if (ans1 != ans2) {
                System.out.println("ans1 = " + ans1 + ", ans2 = " + ans2);
                System.out.println("Oops!");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
