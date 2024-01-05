package com.yw.course.advance.class16;

import com.yw.entity.Graph;
import com.yw.entity.Vertex;

import java.util.*;

/**
 * @author yangwei
 */
public class Code03_TopologySort {

    // directed graph and no loop
    public static List<Vertex> sortedTopology(Graph g) {
        // key 某个节点   value 剩余的入度
        Map<Vertex, Integer> inMap = new HashMap<>();
        // 只有剩余入度为0的点，才进入这个队列
        Queue<Vertex> zeroInQueue = new LinkedList<>();
        for (Vertex v : g.vertices) {
            inMap.put(v, v.in);
            if (v.in == 0) zeroInQueue.add(v);
        }
        List<Vertex> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Vertex cur = zeroInQueue.poll();
            result.add(cur);
            for (Vertex next : cur.nexts) {
                int in = inMap.get(next);
                inMap.put(next, --in);
                if (in == 0) zeroInQueue.add(next);
            }
        }
        return result;
    }
}
