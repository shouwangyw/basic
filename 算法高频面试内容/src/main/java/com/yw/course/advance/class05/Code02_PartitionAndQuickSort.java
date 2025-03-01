package com.yw.course.advance.class05;

import com.yw.course.basic.class08.Code03_PartitionAndQuickSort;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code02_PartitionAndQuickSort extends Code03_PartitionAndQuickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        quickSortByRandom(arr, 0, arr.length - 1);
    }

    /**
     * 随机快排
     */
    private static void quickSortByRandom(int[] arr, int l, int r) {
        if (l >= r) return;
        // 在[l, r]`等概率随机`选一个基准值arr[r]
        swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
        int[] equals = partition3Way(arr, l, r);
        quickSortByRandom(arr, l, equals[0] - 1);
        quickSortByRandom(arr, equals[1] + 1, r);
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            quickSort(arr1);
            quickSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");
    }
}
