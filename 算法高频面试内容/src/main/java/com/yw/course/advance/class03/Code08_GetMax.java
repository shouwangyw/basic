package com.yw.course.advance.class03;

/**
 * @author yangwei
 */
public class Code08_GetMax {

    // 求arr中的最大值
    public static int getMax(int[] arr) {
        return getMax(arr, 0, arr.length - 1);
    }

    // arr[l,r]范围上求最大值  L ... R   N
    public static int getMax(int[] arr, int l, int r) {
        // arr[l,r]范围上只有一个数，直接返回，base case
        if (l == r) {
            return arr[l];
        }
        // l..r 不只一个数
        // mid = (l + r) / 2
        int mid = l + ((r - l) >> 1);
        int leftMax = getMax(arr, l, mid);
        int rightMax = getMax(arr, mid + 1, r);
        return Math.max(leftMax, rightMax);
    }

}
