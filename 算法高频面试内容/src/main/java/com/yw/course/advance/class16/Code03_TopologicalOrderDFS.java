package com.yw.course.advance.class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OJ链接：https://www.lintcode.com/problem/topological-sorting
 *
 * @author yangwei
 */
public class Code03_TopologicalOrderDFS {

    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        Map<DirectedGraphNode, Record> recordMap = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            process(cur, recordMap);
        }
        List<Record> records = new ArrayList<>(recordMap.values());
        // 方法一：按点数逆序排
        records.sort((o1, o2) -> o2.nodeCnt - o1.nodeCnt);
//        // 方法二：按深度逆序排
//        records.sort((o1, o2) -> o2.deep - o1.deep);

        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        records.forEach(v -> ans.add(v.node));
        return ans;
    }

    public static Record process(DirectedGraphNode cur, Map<DirectedGraphNode, Record> recordMap) {
        if (recordMap.containsKey(cur)) return recordMap.get(cur);

        int nextVal = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            nextVal = Math.max(nextVal, process(next, recordMap).nodeCnt);
//            nextVal = Math.max(nextVal, process(next, recordMap).deep);
        }
        Record ans = new Record(cur, nextVal + 1);
        recordMap.put(cur, ans);
        return ans;
    }

    private static class Record {
        DirectedGraphNode node;
        int deep;       // 深度
        int nodeCnt;    // 点数

        public Record(DirectedGraphNode node, int nodeCnt) {
            this.node = node;
            this.nodeCnt = nodeCnt;
        }
//        public Record(DirectedGraphNode node, int deep) {
//            this.node = node;
//            this.deep = deep;
//        }
    }

}
