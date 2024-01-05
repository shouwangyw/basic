package com.yw.course.advance.class16;

import java.util.*;

/**
 * OJ链接：https://www.lintcode.com/problem/topological-sorting
 *
 * @author yangwei
 */
public class Code03_TopologicalOrderBFS {

    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }

    // 提交下面的
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // 计算每个点的入度
        Map<DirectedGraphNode, Integer> inMap = new HashMap<>();
        for (DirectedGraphNode cur : graph) inMap.put(cur, 0);
        for (DirectedGraphNode cur : graph) {
            for (DirectedGraphNode next : cur.neighbors) {
                inMap.compute(next, (k, v) -> v == null ? 1 : v + 1);
            }
        }
        // 用队列记录入度为0的点
        Queue<DirectedGraphNode> zeroInQueue = new LinkedList<>();
        for (Map.Entry<DirectedGraphNode, Integer> entry : inMap.entrySet()) {
            if (entry.getValue() == 0) zeroInQueue.add(entry.getKey());
        }
        // 依次得到入度为0的点
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            DirectedGraphNode cur = zeroInQueue.poll();
            ans.add(cur);
            for (DirectedGraphNode next : cur.neighbors) {
                int in = inMap.get(next);
                inMap.put(next, --in);
                if (in == 0) zeroInQueue.add(next);
            }
        }
        return ans;
    }

}
