package com.yw.course.coding.class04;

/**
 * 生成长度为size的达标数组
 * 达标：对于任意的 i<k<j，满足 [i] + [j] != [k] * 2
 *
 * @author yangwei
 */
public class Code06_MakeNo {

    public static int[] makeArray(int size) {
        // base case
        if (size == 1) return new int[]{1};
        // size一半长度达标，除以2向上取整
        int halfSize = (size + 1) / 2;
        int[] base = makeArray(halfSize);
        // base -> 等长奇数达标 + base -> 等长偶数达标
        int[] ans = new int[size];
        int idx = 0;
        for (; idx < halfSize; idx++) ans[idx] = base[idx] * 2 - 1;
        for (int i = 0; idx < size; idx++, i++) ans[idx] = base[i] * 2;
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int N = 1; N < 1000; N++) {
            int[] arr = makeArray(N);
            if (!isValid(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
        System.out.println(isValid(makeArray(1042)));
        System.out.println(isValid(makeArray(2981)));
    }

    private static boolean isValid(int[] arr) {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int k = i + 1; k < N; k++) {
                for (int j = k + 1; j < N; j++) {
                    if (arr[i] + arr[j] == 2 * arr[k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
