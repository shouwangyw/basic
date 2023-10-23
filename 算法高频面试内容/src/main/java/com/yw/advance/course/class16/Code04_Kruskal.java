package com.yw.advance.course.class16;

import com.yw.entity.Edge;
import com.yw.entity.Graph;
import com.yw.entity.Vertex;

import java.util.*;

/**
 * undirected graph only
 * @author yangwei
 */
public class Code04_Kruskal {

	public static Set<Edge> kruskalMST(Graph graph) {
		UnionFind unionFind = new UnionFind(graph.vertices);
		// 定义一个小根堆，存放每条边
		Queue<Edge> queue = new PriorityQueue<>((Comparator.comparingInt(o -> o.w)));
		for (Edge edge : graph.edges) { // M 条边
			queue.offer(edge);  			// O(logM)
		}
		Set<Edge> result = new HashSet<>();
		while (!queue.isEmpty()) { 		// M 条边
			Edge edge = queue.poll(); 	// O(logM)
			if (!unionFind.isSameSet(edge.from, edge.to)) { // O(1)
				result.add(edge);
				unionFind.union(edge.from, edge.to);
			}
		}
		return result;
	}
	// Union-Find Set
	public static class UnionFind {
		// key 某一个节点， value key节点往上的节点
		private Map<Vertex, Vertex> fatherMap;
		// key 某一个集合的代表节点, value key所在集合的节点个数

		private Map<Vertex, Integer> sizeMap;

		public UnionFind(Set<Vertex> vertices) {
			fatherMap = new HashMap<>();
			sizeMap = new HashMap<>();
			for (Vertex v : vertices) {
				fatherMap.put(v, v);
				sizeMap.put(v, 1);
			}
		}
		public Vertex find(Vertex x) {
			// 路径压缩
			Vertex v = fatherMap.get(x) == x ? x : find(fatherMap.get(x));
			fatherMap.put(x, v);
			return v;
		}
		public void union(Vertex v1, Vertex v2) {
			if (v1 == null || v2 == null) return;
			Vertex vf1 = find(v1), vf2 = find(v2);
			if (vf1 == vf2) return;
			int vr1 = sizeMap.get(vf1), vr2 = sizeMap.get(vf2);
			if (vr1 <= vr2) {
				fatherMap.put(vf1, vf2);
				sizeMap.put(vf2, vr1 + vr2);
				sizeMap.remove(vf1);
			} else {
				fatherMap.put(vf2, vf1);
				sizeMap.put(vf1, vr1 + vr2);
				sizeMap.remove(vf2);
			}
		}
		public boolean isSameSet(Vertex a, Vertex b) {
			return find(a) == find(b);
		}
	}
}
