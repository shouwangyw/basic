package com.yw.basic.course.class08;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code02_MergeSort {

    /**
     * 方法一：基于递归实现
     */
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        mergeSort(arr, 0, arr.length - 1);
    }

    // 让arr在[l,r]范围上有序
    private static void mergeSort(int[] arr, int l, int r) {
        if (l == r) return;
        int mid = l + ((r - l) >> 1);
        // 分
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        // 合
        merge(arr, l, mid, r);
    }

    private static void merge(int[] arr, int l, int mid, int r) {
        int[] tmp = new int[r - l + 1];
        int i = l, j = mid + 1, k = 0;
        while (i <= mid && j <= r) {
            tmp[k++] = arr[i] < arr[j] ? arr[i++] : arr[j++];
        }
        // 要么i越界，要么j越界，不可能出现共同越界
        while (i <= mid) tmp[k++] = arr[i++];
        while (j <= r) tmp[k++] = arr[j++];
        // 将tmp数组拷贝回arr数组
        for (i = 0; i < tmp.length; i++) arr[l + i] = tmp[i];
    }

    /**
     * 方法二：基于迭代实现
     */
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int step = 1, n = arr.length;
        while (step < n) {
            int l = 0;
            while (l < n) {
                if (l + step > n) break;
                int mid = l + step - 1;
                int r = (mid + step <= n - 1) ? (mid + step) : n - 1;
                merge(arr, l, mid, r);
                if (r == n - 1) break;
                l = r + 1;
            }
            if (step > n / 2) break;
            step *= 2;
        }
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort(arr1);
            mergeSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("出错了！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
