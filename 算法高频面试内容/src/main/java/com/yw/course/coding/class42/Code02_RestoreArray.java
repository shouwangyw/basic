package com.yw.course.coding.class42;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 一个数组，比如说[1 2 4]，它按照规则会得到一个生成的数组。
 * 生成数组的规则是：首先原数组的数保留。其次每一个数乘 2 之后就是新数组的元素，1 x 2=2、2x2=4、4x2=8。
 * 得到新数组[1 2 4 2 4 8]。我们把这个数组叫做加工数组。给你一个加工数组，整个数组是打乱之后的，
 * 比如 [4 2 2 8 1 4 4 2 8 1 4]，问能不能通过整个加工后的数组还原出原数组。
 *
 * @author yangwei
 */
public class Code02_RestoreArray {
    public static boolean validateDoubledArray(int[] arr) {
        if (arr == null || arr.length % 2 != 0) return false;
        Arrays.sort(arr);
        int n = arr.length;
        Map<Integer, Integer> counter = new HashMap<>();
        for (int i = n - 1; i >= 0; i--) {
            Integer cnt = counter.get(arr[i]);
            if (cnt == null) counter.compute(arr[i] / 2, (k, v) -> v == null ? 1 : v + 1);
            else {
                if (cnt == 1) counter.remove(arr[i]);
                else counter.put(arr[i], cnt - 1);
            }
        }
        return counter.isEmpty();
    }

    public static void main(String[] args) {
//        int[] arr = {4, 2, 4, 2, 2, 4, 2, 4};
//        int[] arr = {4, 4, 2, 2, 1, 8};
//        int[] arr = {4, 4, 2, 2, 1};
        int[] arr = {4, 4, 2, 2, 2, 8};

        System.out.println(validateDoubledArray(arr));
    }
}
