package com.yw.test;

import java.util.Arrays;

import static com.yw.util.CommonUtils.generateRandomArray;

/**
 * @author yangwei
 */
public abstract class FindBasicTest {

    protected void test() {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (findTarget0(arr, value) != findTarget(arr, value)) {
                System.out.println("出错了！");
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    protected abstract boolean findTarget(int[] arr, int value);

    private static boolean findTarget0(int[] sortedArr, int num) {
        for (int cur : sortedArr) {
            if (cur == num) {
                return true;
            }
        }
        return false;
    }
}
