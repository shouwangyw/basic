package com.yw.course.coding.class41;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 来自网易互娱
 * N个结点之间，表世界存在双向通行的道路，里世界存在双向通行的传送门.
 * 若走表世界的道路，花费一分钟.
 * 若走里世界的传送门，不花费时间，但是接下来一分钟不能走传送门.
 * 输入： T为测试用例的组数，对于每组数据:
 * 第一行：N M1 M2 N代表结点的个数1到N
 * 接下来M1行 每行两个数，u和v，表示表世界u和v之间存在道路
 * 接下来M2行 每行两个数，u和v，表示里世界u和v之间存在传送门
 * 现在处于1号结点，最终要到达N号结点，求最小的到达时间 保证所有输入均有效，不存在环等情况
 *
 * @author yangwei
 */
public class Code03_MagicGoToAim {

	// 城市编号从0开始，n座城市对应0~n-1
	// roads[i]表示i能走路到达的城市有哪些，每条路都花费1分钟
	// gates[i]表示i能传送到达的城市有哪些，不需要花费时间
	// 返回从0到n-1的最少耗时代价
	public static int minCostToAim(int n, int[][] roads, int[][] gates) {
		int[][] distance = new int[2][n];
		// 因为从0开始走，所以distance[0][0] = 0, distance[1][0] = 0
		// distance[0][i] -> 0 : 前一个城市到达i，是走路的方式，最小代价 distance[0][i]
		// distance[1][i] -> 1 : 前一个城市到达i，是传送的方式，最小代价 distance[1][i]
		for (int i = 1; i < n; i++) {
			distance[0][i] = Integer.MAX_VALUE;
			distance[1][i] = Integer.MAX_VALUE;
		}
		// 小根堆，根据距离排序
		Queue<Node> head = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
		head.offer(new Node(0, 0, 0));
		boolean[][] visited = new boolean[2][n];
		while (!head.isEmpty()) {
			Node cur = head.poll();
			if (visited[cur.preTransfer][cur.city]) continue;
			// 走路方式
			for (int next : roads[cur.city]) {
				if (distance[0][next] > cur.cost + 1) {
					distance[0][next] = cur.cost + 1;
					head.add(new Node(0, next, distance[0][next]));
				}
			}
			// 传送方式
			if (cur.preTransfer == 0) {
				for (int next : gates[cur.city]) {
					if (distance[1][next] > cur.cost) {
						distance[1][next] = cur.cost;
						head.add(new Node(1, next, distance[1][next]));
					}
				}
			}
			visited[cur.preTransfer][cur.city] = true;
		}
		return Math.min(distance[0][n - 1], distance[1][n - 1]);
	}

	private static class Node {
		public final int preTransfer;
		public final int city;
		public final int cost;

		public Node(int preTransfer, int city, int cost) {
			this.preTransfer = preTransfer;
			this.city = city;
			this.cost = cost;
		}
	}

}