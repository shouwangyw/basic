package com.yw.course.advance.class16;

import com.yw.entity.Edge;
import com.yw.entity.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author yangwei
 */
public class Code06_Dijkstra {

    // 方法一：暴力贪心
    public static Map<Vertex, Integer> dijkstra0(Vertex from) {
        Map<Vertex, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);
        Set<Vertex> selectedVertices = new HashSet<>();
        Vertex minVertex = getMinDistanceAndUnVisitedVertex(distanceMap, selectedVertices);
        while (minVertex != null) {
            //  原始点  ->  minVertex(跳转点)   最小距离distance
            Integer dis = distanceMap.get(minVertex);
            for (Edge edge : minVertex.edges) {
                Vertex to = edge.to;
                if (!distanceMap.containsKey(to)) distanceMap.put(to, dis + edge.w);
                else distanceMap.put(edge.to, Math.min(distanceMap.get(to), dis + edge.w));
            }
            selectedVertices.add(minVertex);
            minVertex = getMinDistanceAndUnVisitedVertex(distanceMap, selectedVertices);
        }
        return distanceMap;
    }

    private static Vertex getMinDistanceAndUnVisitedVertex(Map<Vertex, Integer> distanceMap, Set<Vertex> selectedVertices) {
        Vertex minVertex = null;
        int minDis = Integer.MAX_VALUE;
        for (Entry<Vertex, Integer> entry : distanceMap.entrySet()) {
            Vertex vertex = entry.getKey();
            int dis = entry.getValue();
            if (!selectedVertices.contains(vertex) && dis < minDis) {
                minVertex = vertex;
                minDis = dis;
            }
        }
        return minVertex;
    }

    // 改进后的dijkstra算法
    public static Map<Vertex, Integer> dijkstra(Vertex vertex, int size) {
        HeapPlus heapPlus = new HeapPlus(size);
        heapPlus.upsert(vertex, 0);
        Map<Vertex, Integer> distanceMap = new HashMap<>();
        while (!heapPlus.isEmpty()) {
            VertexRecord record = heapPlus.poll();
            Vertex cur = record.vertex;
            int distance = record.distance;
            for (Edge edge : cur.edges) {
                heapPlus.upsert(edge.to, edge.w + distance);
            }
            distanceMap.put(cur, distance);
        }
        return distanceMap;
    }
    private static class VertexRecord {
        Vertex vertex;
        int distance;
        public VertexRecord(Vertex vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
    private static class HeapPlus {
        // 实际对结构
        private final Vertex[] vertices;
        // k: Vertex点, v: 索引位置
        private final Map<Vertex, Integer> vertexIdxMap;
        // k: Vertex点, v: 从源节点出发到该节点的最小距离
        private final Map<Vertex, Integer> distanceMap;
        // 堆上节点数量
        private int count;
        public HeapPlus(int size) {
            this.vertices = new Vertex[size];
            this.vertexIdxMap = new HashMap<>();
            this.distanceMap = new HashMap<>();
            this.count = 0;
        }
        public void upsert(Vertex vertex, int dis) {
            if (exist(vertex)) {
                distanceMap.put(vertex, Math.min(distanceMap.get(vertex), dis));
                shiftUp(vertex, vertexIdxMap.get(vertex));
            }
            if (!vertexIdxMap.containsKey(vertex)) {
                vertices[count] = vertex;
                vertexIdxMap.put(vertex, count);
                distanceMap.put(vertex, dis);
                shiftUp(vertex, count++);
            }
        }
        public VertexRecord poll() {
            Vertex vertex = vertices[0];
            VertexRecord record = new VertexRecord(vertex, distanceMap.get(vertex));
            swap(0, count - 1);
            // 逻辑删除，将索引位置置为-1
            vertexIdxMap.put(vertices[count - 1], -1);
            distanceMap.remove(vertices[count - 1]);
            vertices[count - 1] = null;
            shiftDown(0, --count);
            return record;
        }
        private void shiftUp(Vertex vertex, int idx) {
            while (distanceMap.get(vertices[idx]) < distanceMap.get(vertices[(idx - 1) / 2])) {
                swap(idx, (idx - 1) / 2);
                idx = (idx - 1) / 2;
            }
        }
        private void shiftDown(int idx, int cnt) {
            int left = idx * 2 + 1;
            while (left < cnt) {
                int smallest = left + 1 < cnt && distanceMap.get(vertices[left + 1]) < distanceMap.get(vertices[left])
                        ? left + 1 : left;
                smallest = distanceMap.get(vertices[smallest]) < distanceMap.get(vertices[idx]) ? smallest : idx;
                if (smallest == idx) break;
                swap(smallest, idx);
                idx = smallest;
                left = idx * 2 + 1;
            }
        }
        private boolean exist(Vertex v) {
            Integer idx = vertexIdxMap.get(v);
            return idx != null && idx >= 0;
        }
        public boolean isEmpty() {
            return count == 0;
        }
        private void swap(int i, int j) {
            if (i == j) return;
            vertexIdxMap.put(vertices[i], j);
            vertexIdxMap.put(vertices[j], i);
            Vertex tmp = vertices[i];
            vertices[i] = vertices[j];
            vertices[j] = tmp;
        }
    }
}
