package com.yw.course.advance.class06;

import java.util.Arrays;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code03_HeapSort {

    // 堆排序额外空间复杂度O(1)
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int n = arr.length;
        /**
         * 从上往下-向上调整建堆，时间复杂度 O(N*logN)，适合流处理
         * [3,1,4,5,8,0,7] => [8,5,7,1,4,0,3]
         *    3       |              8
         *  1   4     |  ===>      5   7
         * 5 8 0 7    ↓          1  4 0  3
         */
        // O(N*logN)
//        for (int i = 0; i < n; i++) shiftUp(arr, i); // O(N)

        /**
         * 从下往上-向下调整建堆，时间复杂度 O(N)，适合批处理
         * [3,1,4,5,8,0,7] => [8,5,7,1,4,0,3]
         *    3        ↑             8
         *  1   4      | ===>      5   7
         * 5 8 0 7     |         3  1 0  4
         */
        // O(N)
        for (int i = arr.length - 1; i >= 0; i--) shiftDown(arr, i, n); // O(N)
        // O(N*logN)
        while (n > 0) {
            swap(arr, 0, --n);
            shiftDown(arr, 0, n);    // O(logN)
        }
    }

    private static void shiftUp(int[] arr, int k) {
        int x = arr[k];
        while (k > 0) {
            int parent = (k - 1) >>> 1, pVal = arr[parent];
            // 按大根堆调: 如果当前节点值不大于父节点值，则不交换break结束
            if (x <= pVal) break;
            // 否则交换
            arr[k] = pVal;
            k = parent;
        }
        arr[k] = x;
    }

//    private static void shiftDown(int[] arr, int k) {
    private static void shiftDown(int[] arr, int k, int limit) {
//        int x = arr[k], half = arr.length >>> 1;
        int x = arr[k], half = limit >>> 1;
        while (k < half) {
            int l = (k << 1) + 1, r = l + 1;
            int childVal = arr[l];
            // 从左右字节先选一个大的
            if (r < limit && arr[l] < arr[r]) {
                l = r;
                childVal = arr[l];
            }
            // 按大根堆调：如果当前节点比左右子节点的值都大，则不交换break结束
            if (x >= childVal) break;
            // 交换
            arr[k] = childVal;
            k = l;
        }
        arr[k] = x;
    }


    public static void heapSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // O(N*logN)
//		for (int i = 0; i < arr.length; i++) { // O(N)
//			heapInsert(arr, i); // O(logN)
//		}
        // O(N)
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        // O(N*logN)
        while (heapSize > 0) { // O(N)
            heapify(arr, 0, heapSize); // O(logN)
            swap(arr, 0, --heapSize); // O(1)
        }
    }

    // arr[index]刚来的数，往上
    public static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    // arr[index]位置的数，能否往下移动
    public static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1; // 左孩子的下标
        while (left < heapSize) { // 下方还有孩子的时候
            // 两个孩子中，谁的值大，把下标给largest
            // 1）只有左孩子，left -> largest
            // 2) 同时有左孩子和右孩子，右孩子的值<= 左孩子的值，left -> largest
            // 3) 同时有左孩子和右孩子并且右孩子的值> 左孩子的值， right -> largest
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            // 父和较大的孩子之间，谁的值大，把下标给largest
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) {
                break;
            }
            swap(arr, largest, index);
            index = largest;
            left = index * 2 + 1;
        }
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
//            heapSort2(arr1);
            Arrays.sort(arr2);
            if (!isEqual(arr1, arr2)) {
                printArray(arr1);
                printArray(arr2);
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
