package com.yw.course.advance.class15;

/**
 * 测试链接：https://leetcode.cn/problems/number-of-provinces/description/
 * @author yangwei
 */
public class Code01_FriendCircles {

	public int findCircleNum(int[][] isConnected) {
		int n = isConnected.length;
		UnionFind uf = new UnionFind(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (isConnected[i][j] == 1) uf.union(i, j);
			}
		}
		return uf.getSize();
	}

	public static class UnionFind {
		private int[] fa;
		private int size;
		public UnionFind(int n) {
			fa = new int[n + 1];
			for (int i = 0; i <= n; i++) fa[i] = i;
			size = n;
		}
		public int find(int x) {
			return fa[x] == x ? x : find(fa[x]);
		}
		public void union(int a, int b) {
			int ra = find(a), rb = find(b);
			if (ra == rb) return;
			size--;
			fa[ra] = rb;
		}
		public int getSize() {
			return size;
		}
	}


//	public static class UnionFind {
//		// parent[i] = k ： i的父亲是k
//		private int[] parent;
//		// size[i] = k ： 如果i是代表节点，size[i]才有意义，否则无意义
//		// i所在的集合大小是多少
//		private int[] size;
//		// 辅助结构
//		private int[] help;
//		// 一共有多少个集合
//		private int sets;
//
//		public UnionFind(int N) {
//			parent = new int[N];
//			size = new int[N];
//			help = new int[N];
//			sets = N;
//			for (int i = 0; i < N; i++) {
//				parent[i] = i;
//				size[i] = 1;
//			}
//		}
//
//		// 从i开始一直往上，往上到不能再往上，代表节点，返回
//		// 这个过程要做路径压缩
//		private int find(int i) {
//			int hi = 0;
//			while (i != parent[i]) {
//				help[hi++] = i;
//				i = parent[i];
//			}
//			for (hi--; hi >= 0; hi--) {
//				parent[help[hi]] = i;
//			}
//			return i;
//		}
//
//		public void union(int i, int j) {
//			int f1 = find(i);
//			int f2 = find(j);
//			if (f1 != f2) {
//				if (size[f1] >= size[f2]) {
//					size[f1] += size[f2];
//					parent[f2] = f1;
//				} else {
//					size[f2] += size[f1];
//					parent[f1] = f2;
//				}
//				sets--;
//			}
//		}
//
//		public int sets() {
//			return sets;
//		}
//	}

}
