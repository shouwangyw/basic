package com.yw.advance.course.class08;

import java.util.Arrays;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code04_RadixSort {

    // arr[i]是非负数
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }
    // [l, r]上排好序，digit是最大值的十进制位数
    private static void radixSort(int[] arr, int l, int r, final int digit) {
        final int radix = 10;
        // 有多少个数，准备多少个辅助空间
        int[] help = new int[r - l + 1];
        // 有多少位就进出几次
        for (int d = 1; d <= digit; d++) {
            // 准备一个长度为10的count数组
            int[] count = new int[radix];
            // 统计
            for (int i = l; i <= r; i++) count[getDigit(arr[i], d)]++;
            // 将count转成前缀和
            // count[0] 当前位(d位)是0的数字有多少个
            // count[1] 当前位(d位)是(0和1)的数字有多少个
            // count[2] 当前位(d位)是(0、1和2)的数字有多少个
            // count[i] 当前位(d位)是(0~i)的数字有多少个
            for (int i = 1; i < radix; i++) count[i] = count[i] + count[i - 1];
            // 从右向左遍历
            for (int i = r; i >= l; i--) {
                // 提取当前数在第(d)位的数字
                int x = getDigit(arr[i], d);
                // 根据count计算出存放到help数组的位置count[x] - 1
                help[count[x] - 1] = arr[i];
                // 统计数组中--
                count[x]--;
            }
            // 将help数组拷贝回原数组
            for (int i = l, j = 0; i <= r; i++, j++) arr[i] = help[j];
        }
    }
    // 计数最大值的十进制位数
    private static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) max = Math.max(max, arr[i]);
        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }
    // 提取x在第(d)位上的数字
    private static int getDigit(int x, int d) {
        return ((x / ((int) Math.pow(10, d - 1))) % 10);
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
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

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

}
