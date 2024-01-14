package com.yw.course.coding.class04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 今日头条原题
 * 数组为{3, 2, 2, 3, 1}，查询为(0, 3, 2)。意思是在数组里下标0~3这个范围上，有几个2？返回2。
 * 假设给你一个数组arr，对这个数组的查询非常频繁，请返回所有查询的结果
 * @author yangwei
 */
public class Code01_QueryHobby {

    // 方法一：范围上顺序查找
    public static class QueryBox1 {
        private int[] arr;
        public QueryBox1(int[] arr) {
            this.arr = arr;
        }
        public int query(int l, int r, int v) {
            int ans = 0;
            while (l <= r) {
                if (arr[l++] == v) ans++;
            }
            return ans;
        }
    }

    // 方法二：预处理+二分查找
    public static class QueryBox2 {
        // 记录每种数出现的索引位置
        private Map<Integer, List<Integer>> valIdxes;
        public QueryBox2(int[] arr) {
            valIdxes = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                final int fi = i;
                valIdxes.compute(arr[i], (k, v) -> {
                    if (v == null) v = new ArrayList<>();
                    v.add(fi);
                    return v;
                });
            }
        }
        public int query(int l ,int r, int v) {
            List<Integer> idxes = valIdxes.get(v);
            if (idxes == null) return 0;
            // 查询小于r+1的索引数量 - 查询小于l的索引数量
            return countLess(idxes, r + 1) - countLess(idxes, l);
        }
        // 在有序数组arr中，用二分的方法数出<limit的数有几个
        // 也就是用二分法，找到<limit的数中最右的位置
        private int countLess(List<Integer> arr, int limit) {
            int l = 0, r = arr.size() - 1, mostR = -1;
            while (l <= r) {
                int mid = l + ((r - l) >> 1);
                if (arr.get(mid) < limit) {
                    mostR = mid;
                    l = mid + 1;
                } else r = mid - 1;
            }
            return mostR + 1;
        }
    }

    public static void main(String[] args) {
        int len = 300;
        int value = 20;
        int testTimes = 1000;
        int queryTimes = 1000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(len, value);
            int N = arr.length;
            QueryBox1 box1 = new QueryBox1(arr);
            QueryBox2 box2 = new QueryBox2(arr);
            for (int j = 0; j < queryTimes; j++) {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                int L = Math.min(a, b);
                int R = Math.max(a, b);
                int v = (int) (Math.random() * value) + 1;
                if (box1.query(L, R, v) != box2.query(L, R, v)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test end");
    }

    private static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

}
