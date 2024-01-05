package com.yw.course.advance.class16;

import com.yw.entity.Edge;
import com.yw.entity.Graph;
import com.yw.entity.Vertex;

import java.util.*;

/**
 * @author yangwei
 */
public class Code05_Prim {

    public static Set<Edge> primMST(Graph graph) {
        // 解锁的边进入小根堆
        Queue<Edge> queue = new PriorityQueue<>((Comparator.comparingInt(o -> o.w)));
        // 哪些点被解锁出来了
        Set<Vertex> nodeSet = new HashSet<>();
        // 依次挑选的的边在result里
        Set<Edge> result = new HashSet<>();
        // 随便挑了一个点
        for (Vertex x : graph.vertices) {
            // node 是开始点
            if (nodeSet.contains(x)) break;
            nodeSet.add(x);
            // 由一个点，解锁所有相连的边
            for (Edge e : x.edges) queue.add(e);
            while (!queue.isEmpty()) {
                // 弹出解锁的边中，最小的边
                Edge edge = queue.poll();
                // 可能的一个新的点
                Vertex to = edge.to;
                // 不含有的时候，就是新的点
                if (!nodeSet.contains(to)) {
                    nodeSet.add(to);
                    result.add(edge);
                    for (Edge next : to.edges) queue.add(next);
                }
            }
        }
        return result;
    }

    // 请保证graph是连通图
    // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 返回值是最小连通图的路径之和
    public static int prim(int[][] graph) {
        int size = graph.length;
        int[] distances = new int[size];
        boolean[] visit = new boolean[size];
        visit[0] = true;
        for (int i = 0; i < size; i++) {
            distances[i] = graph[0][i];
        }
        int sum = 0;
        for (int i = 1; i < size; i++) {
            int minPath = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] < minPath) {
                    minPath = distances[j];
                    minIndex = j;
                }
            }
            if (minIndex == -1) {
                return sum;
            }
            visit[minIndex] = true;
            sum += minPath;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] > graph[minIndex][j]) {
                    distances[j] = graph[minIndex][j];
                }
            }
        }
        return sum;
    }
}
