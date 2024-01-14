package com.yw.course.coding.class04;

import java.util.*;
import java.util.Map.Entry;

/**
 * 测试链接 : https://leetcode.cn/problems/the-skyline-problem/
 *
 * @author yangwei
 */
public class Code08_TheSkylineProblem {

    public List<List<Integer>> getSkyline(int[][] buildings) {
        int n = buildings.length;
        // 将buildings[i]转换成两个对象: lefti位置增加高度heighti 和 righti位置减少高度heighti
        Node[] nodes = new Node[n << 1];
        for (int i = 0; i < n; i++) {
            nodes[i * 2] = new Node(buildings[i][0], true, buildings[i][2]);
            nodes[i * 2 + 1] = new Node(buildings[i][1], false, buildings[i][2]);
        }
        // 对nodes按位置p进行排序
        Arrays.sort(nodes, Comparator.comparingInt(o -> o.p));
        // 定义两个有序表，hTimesMap统计每个高度出现次数，pHeightMap记录每个位置最大高度
        TreeMap<Integer, Integer> hTimesMap = new TreeMap<>(), pHeightMap = new TreeMap<>();
        // 依次遍历每一个node，生成pHeightMap
        for (Node node : nodes) {
            if (node.isAdd) hTimesMap.compute(node.h, (k, v) -> v == null ? 1 : v + 1);
            else {
                // 高度次数减到0时，移除掉-避免干扰判断
                if (hTimesMap.get(node.h) == 1) hTimesMap.remove(node.h);
                else hTimesMap.put(node.h, hTimesMap.get(node.h) - 1);
            }
            pHeightMap.put(node.p, hTimesMap.isEmpty() ? 0 : hTimesMap.lastKey());
        }
        // 根据pHeightMap整理答案
        LinkedList<List<Integer>> ans = new LinkedList<>();
        for (Entry<Integer, Integer> entry : pHeightMap.entrySet()) {
            int p = entry.getKey(), h = entry.getValue();
            if (ans.isEmpty() || ans.getLast().get(1) != h)
                ans.add(new ArrayList<>(Arrays.asList(p, h)));
        }
        return ans;
    }
    private static class Node {
        private int p;
        private boolean isAdd;
        private int h;
        public Node(int p, boolean isAdd, int h) {
            this.p = p;
            this.isAdd = isAdd;
            this.h = h;
        }
    }
}
