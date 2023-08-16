package com.yw.basic.course.class01;

/**
 * 选择排序
 * 0~N-1 上选出最小值放到 0 位置
 * 1~N-1 上选出最小值放到 1 位置
 * 2~N-1 上选出最小值放到 2 位置
 * ...
 * i~N-1 上选出最小值放到 i 位置
 *
 * @author yangwei
 */
public class Code02_Sort {
    private static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int minValueIndex = i;
            for (int j = i + 1; j < n; j++) {
                minValueIndex = arr[j] < arr[minValueIndex] ? j : minValueIndex;
            }
            if (i != minValueIndex) {
                swap(arr, i, minValueIndex);
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {8, 2, 3, 1, 5, 9, 6, 4, 3, 7, 2, 1};
        printArray(arr);
        selectSort(arr);
        printArray(arr);
    }
}
