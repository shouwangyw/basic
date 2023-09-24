package com.yw.advance.course.class08;

import java.util.Arrays;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code03_CountSort {

    // 计数排序，已知 0 <= arr[i] <= 200
    public static void countSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        // 遍历原数组获取最大值
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) max = Math.max(max, arr[i]);
        // 定义一个计数桶
        int[] bucket = new int[max + 1];
        // 统计原数组中元素出现次数
        for (int i = 0; i < arr.length; i++) bucket[arr[i]]++;
        // 遍历桶，得到排序后的数组
        for (int i = 0, j = 0; j < bucket.length; j++) {
            while (bucket[j]-- > 0) {
                arr[i++] = j;
            }
        }
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 150;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            countSort(arr1);
            Arrays.sort(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }
}
