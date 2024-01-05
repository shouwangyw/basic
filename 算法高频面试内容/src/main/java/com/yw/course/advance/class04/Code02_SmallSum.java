package com.yw.course.advance.class04;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code02_SmallSum {

    public static long mySmallSum(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        return mergeSort(arr, 0, arr.length - 1);
    }
    private static long mergeSort(int[] arr, int l, int r) {
        if (l == r) return 0;
        int mid = l + ((r - l) >> 1);
        long lSmallSum = mergeSort(arr, l, mid);
        long rSmallSum = mergeSort(arr, mid + 1, r);
        long smallSum = 0;
        int i = l, j = mid + 1, k = 0;
        int[] tmp = new int[r - l + 1];
        while (i <= mid && j <= r) {
            if (arr[i] < arr[j]) {
                smallSum += (r - j + 1L) * arr[i];
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
            }
        }
        while (i <= mid) tmp[k++] = arr[i++];
        while (j <= r) tmp[k++] = arr[j++];
        for (k = 0; k < tmp.length; k++) arr[l + k] = tmp[k];
        return smallSum + lSmallSum + rSmallSum;
    }

//    public static int smallSum(int[] arr) {
//        if (arr == null || arr.length < 2) {
//            return 0;
//        }
//        return process(arr, 0, arr.length - 1);
//    }
//
//    // arr[L..R]既要排好序，也要求小和返回
//    // 所有merge时，产生的小和，累加
//    // 左 排序   merge
//    // 右 排序  merge
//    // merge
//    public static int process(int[] arr, int l, int r) {
//        if (l == r) {
//            return 0;
//        }
//        // l < r
//        int mid = l + ((r - l) >> 1);
//        return
//                process(arr, l, mid)
//                        +
//                        process(arr, mid + 1, r)
//                        +
//                        merge(arr, l, mid, r);
//    }
//
//    public static int merge(int[] arr, int L, int m, int r) {
//        int[] help = new int[r - L + 1];
//        int i = 0;
//        int p1 = L;
//        int p2 = m + 1;
//        int res = 0;
//        while (p1 <= m && p2 <= r) {
//            res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
//            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
//        }
//        while (p1 <= m) {
//            help[i++] = arr[p1++];
//        }
//        while (p2 <= r) {
//            help[i++] = arr[p2++];
//        }
//        for (i = 0; i < help.length; i++) {
//            arr[L + i] = help[i];
//        }
//        return res;
//    }

    // for test
    public static long comparator(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        long res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                res += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return res;
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500;
        int maxSize = 100000;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
//            long ans1 = smallSum(arr1);
            long ans2 = comparator(arr2);
            long ans3 = mySmallSum(arr3);
            if (ans2 != ans3) {
//                System.out.println("ans1 = " + ans1 + ", ans2 = " + ans2+ ", ans3 = " + ans3);
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}
