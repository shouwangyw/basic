package com.yw.course.coding.class19;

import java.util.List;
import java.util.TreeSet;

/**
 * 测试链接: https://leetcode.cn/problems/smallest-range-covering-elements-from-k-lists/
 *
 * @author yangwei
 */
public class Code04_SmallestRangeCoveringElementsfromKLists {

    public int[] smallestRange(List<List<Integer>> nums) {
        // 定义一个有序表，排序规则: 值相等时按数组中索引位置从小到大，值不相等时按数值从小到大
        TreeSet<Node> orderSet = new TreeSet<>((o1, o2) -> o1.val == o2.val ? o1.arrIdx - o2.arrIdx : o1.val - o2.val);
        int size = nums.size();
        for (int i = 0; i < size; i++) orderSet.add(new Node(nums.get(i).get(0), i, 0));
        int[] ans = {0, Integer.MAX_VALUE};
        while (orderSet.size() == size) {
            Node minNode = orderSet.first(), maxNode = orderSet.last();
            if (maxNode.val - minNode.val < ans[1] - ans[0]) {
                ans[0] = minNode.val;
                ans[1] = maxNode.val;
            }
            minNode = orderSet.pollFirst();
            int arrIdx = minNode.arrIdx, idx = minNode.idx + 1;
            if (idx != nums.get(arrIdx).size()) {
                orderSet.add(new Node(nums.get(arrIdx).get(idx), arrIdx, idx));
            }
        }
        return ans;
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
}
