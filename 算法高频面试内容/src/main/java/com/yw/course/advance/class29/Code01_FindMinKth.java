package com.yw.course.advance.class29;

import java.util.PriorityQueue;
import java.util.Queue;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code01_FindMinKth {

    // 方法一：利用大根堆，时间复杂度O(N*logK)
    public static int minKth1(int[] arr, int k) {
        Queue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int x : arr) {
            queue.offer(x);
            if (queue.size() > k) queue.poll();
        }
        return queue.isEmpty() ? -1 : queue.peek();
    }

    // 方法二：改写快排，时间复杂度O(N)
    public static int minKth2(int[] array, int k) {
        int[] arr = copyArray(array);
        // 递归方式
//        return process(arr, 0, arr.length - 1, k - 1);
        // 迭代方式
        int idx = k - 1, l = 0, r = arr.length - 1, pivot = 0;
        while (l < r) {
            pivot = arr[l + (int) (Math.random() * (r - l + 1))];
            int[] equals = partition3Way(arr, l, r, pivot);
            if (idx < equals[0]) r = equals[0] - 1;
            else if (idx > equals[1]) l = equals[1] + 1;
            else return pivot;
        }
        return arr[l];
    }
    // 求 arr 第k小的数，在[l,r]范围上，不是真的排序，而是找位于idx的数，idx在[l,r]
    private static int process(int[] arr, int l, int r, int idx) {
        // l == r == idx
        if (l == r) return arr[l];
        // [l,r]上随机选一个
        int pivot = arr[l + (int) (Math.random() * (r - l + 1))];
        int[] equals = partition3Way(arr, l, r, pivot);
        if (idx >= equals[0] && idx <= equals[1]) return arr[idx];
        else if (idx < equals[0]) return process(arr, l, equals[0] - 1, idx);
        else return process(arr, equals[1] + 1, r, idx);
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

    // 方法三：bfprt算法，时间复杂度O(N)
    public static int minKth3(int[] array, int k) {
        int[] arr = copyArray(array);
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }
    // arr如果在[l,r]范围排好序的话，返回idx位置的数，idx ∈ [l, r]
    private static int bfprt(int[] arr, int l, int r, int idx) {
        if (l == r) return arr[l];
        // [l,r]范围每5个数一组，每个小组排好序，小组的中位数组成新的数组，pivot就是这个新数组的中位数
        int pivot = medianOfMedians(arr, l, r);
        int[] equals = partition3Way(arr, l, r, pivot);
        if (idx >= equals[0] && idx <= equals[1]) return arr[idx];
        else if (idx < equals[0]) return bfprt(arr, l, equals[0] - 1, idx);
        else return bfprt(arr, equals[1] + 1, r, idx);
    }
    // [l,r]范围每5个数一组，每个小组排好序，小组的中位数组成新的数组m，并将中位数返回
    private static int medianOfMedians(int[] arr, int l, int r) {
        int size = r - l + 1, offset = size % 5 == 0 ? 0 : 1;
        int[] m = new int[size / 5 + offset];
        for (int i = 0; i < m.length; i++) {
            int k = l + i * 5;
            // [l,l+4]、[l+5,l+9]、[l+10,l+14]、...
            m[i] = getMedian(arr, k, Math.min(r, k + 4));
        }
        // m数组中找中位数
        return bfprt(m, 0, m.length - 1, m.length / 2);
    }
    private static int getMedian(int[] arr, int l, int r) {
        // 插入排序
        for (int i = l + 1; i <= r; i++) {
            for (int j = i - 1; j >= l && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
        return arr[(l + r) / 2];
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

    private static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }
}
