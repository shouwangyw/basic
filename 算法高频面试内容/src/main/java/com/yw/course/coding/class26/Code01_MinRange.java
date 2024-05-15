package com.yw.course.coding.class26;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * 测试链接: https://leetcode.cn/problems/smallest-range-covering-elements-from-k-lists/
 *
 * @author yangwei
 */
public class Code01_MinRange {

    public static int minRange(int[][] matrix) {
        int N = matrix.length;
        TreeSet<Node> orderSet = new TreeSet<>((o1, o2) -> o1.val == o2.val ? o1.arrIdx - o2.arrIdx : o1.val - o2.val);
        for (int i = 0; i < matrix.length; i++) orderSet.add(new Node(matrix[i][0], i, 0));
        int min = Integer.MAX_VALUE;
        while (orderSet.size() == N) {
            min = Math.min(min, orderSet.last().val - orderSet.first().val);
            Node cur = orderSet.pollFirst();
            if (cur.idx < matrix[cur.arrIdx].length - 1) {
                orderSet.add(new Node(matrix[cur.arrIdx][cur.idx + 1], cur.arrIdx, cur.idx + 1));
            }
        }
        return min << 1;
    }
    private static class Node {
        private int val;
        private int arrIdx;
        private int idx;
        public Node(int val, int arrIdx, int idx) {
            this.val = val;
            this.arrIdx = arrIdx;
            this.idx = idx;
        }
    }

    public static void main(String[] args) {
        int n = 20;
        int v = 200;
        int t = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < t; i++) {
            int[][] m = generateRandomMatrix(n, v);
            int ans1 = minRange1(m);
            int ans2 = minRange(m);
            if (ans1 != ans2) {
                System.out.println("出错了!");
                break;
            }
        }
        System.out.println("测试结束");
    }
    // 以下为课堂题目，在main函数里有对数器
    private static int minRange1(int[][] m) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < m[0].length; i++) {
            for (int j = 0; j < m[1].length; j++) {
                for (int k = 0; k < m[2].length; k++) {
                    min = Math.min(min,
                            Math.abs(m[0][i] - m[1][j]) + Math.abs(m[1][j] - m[2][k]) + Math.abs(m[2][k] - m[0][i]));
                }
            }
        }
        return min;
    }
    private static int[][] generateRandomMatrix(int n, int v) {
        int[][] m = new int[3][];
        int s = 0;
        for (int i = 0; i < 3; i++) {
            s = (int) (Math.random() * n) + 1;
            m[i] = new int[s];
            for (int j = 0; j < s; j++) {
                m[i][j] = (int) (Math.random() * v);
            }
            Arrays.sort(m[i]);
        }
        return m;
    }
}
