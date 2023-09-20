package com.yw.basic.course.class08;

import java.util.Arrays;
import java.util.Stack;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code03_PartitionAndQuickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) return;

//        quickSort2Way(arr, 0, arr.length - 1);
        quickSort3Way(arr, 0, arr.length - 1);
    }

    private static void quickSort2Way(int[] arr, int l, int r) {
        if (l >= r) return;
        int lessOrEqual = partition2Way(arr, l, r);
        quickSort2Way(arr, l, lessOrEqual - 1);
        quickSort2Way(arr, lessOrEqual + 1, r);
    }

    private static void quickSort3Way(int[] arr, int l, int r) {
        if (l >= r) return;
        int[] equals = partition3Way(arr, l, r);
        quickSort3Way(arr, l, equals[0] - 1);
        quickSort3Way(arr, equals[1] + 1, r);
    }

    /**
     * 两路分区：
     * 以 arr[r] 为基准：将所有小于等于 arr[r] 的放左边，将所有大于 arr[r] 的放右边
     *
     * @return 小于等于区的右边界
     */
    private static int partition2Way(int[] arr, int l, int r) {
        int lessOrEqualR = l - 1;
        while (l <= r) {
            if (arr[l] <= arr[r]) swap(arr, ++lessOrEqualR, l);
            l++;
        }
        return lessOrEqualR;
    }

    /**
     * 三路分区：
     * 以 arr[r] 为基准：将所有小于 arr[r] 的放左边，将所有大于 arr[r] 的放右边，将所有等于 arr[r] 的放中间
     *
     * @return 等于区的索引范围
     */
    protected static int[] partition3Way(int[] arr, int l, int r) {
        int lessR = l - 1, moreL = r;
        while (l < moreL) {
            if (arr[l] < arr[r]) swap(arr, ++lessR, l++);
            else if (arr[l] > arr[r]) swap(arr, --moreL, l);
            else l++;
        }
        swap(arr, moreL, r);
        return new int[]{lessR + 1, moreL};
    }

    // 非递归版本
    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) return;
        Stack<Job> stack = new Stack<>();
        stack.push(new Job(0, arr.length - 1));
        while (!stack.isEmpty()) {
            Job cur = stack.pop();
            int[] equals = partition3Way(arr, cur.l, cur.r);
            if (equals[0] > cur.l) { // 有< 区域
                stack.push(new Job(cur.l, equals[0] - 1));
            }
            if (equals[1] < cur.r) { // 有 > 区域
                stack.push(new Job(equals[1] + 1, cur.r));
            }
        }
    }
    private static class Job {
        int l;
        int r;

        Job(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    public static void main(String[] args) {
        int[] arr = {7, 1, 3, 5, 4, 5, 1, 4, 2, 4, 2, 4};
        int lessOrEqualR = partition2Way(arr, 0, arr.length - 1);
        printArray(arr);
        System.out.println("小于等于区: " + lessOrEqualR);

        arr = new int[]{7, 1, 3, 5, 4, 5, 1, 4, 2, 4, 2, 4};
        int[] equalR = partition3Way(arr, 0, arr.length - 1);
        printArray(arr);
        System.out.println("等于区: " + Arrays.toString(equalR));


        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            quickSort(arr1);
            quickSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("Oops!");
                succeed = false;
                break;
            }
        }
        System.out.println("test end");
        System.out.println(succeed ? "Nice!" : "Oops!");

    }

}
