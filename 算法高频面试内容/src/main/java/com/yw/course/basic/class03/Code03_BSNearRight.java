package com.yw.course.basic.class03;

import java.util.Arrays;

import static com.yw.util.CommonUtils.generateRandomArray;
import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code03_BSNearRight {
    // 在arr上，找满足<=value的最右位置
    public static int rightMostIndex(int[] arr, int value) {
        if (arr == null || arr.length == 0) return -1;
        int ans = -1, l = 0, r = arr.length - 1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if (arr[mid] <= value) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }

    // for test
    public static int test(int[] arr, int value) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] <= value) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != rightMostIndex(arr, value)) {
                printArray(arr);
                System.out.println(value);
                System.out.println(test(arr, value));
                System.out.println(rightMostIndex(arr, value));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
