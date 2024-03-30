package com.yw.course.coding.class13;

/**
 * 测试链接 : https://leetcode.cn/problems/bricks-falling-when-hit/
 * @author yangwei
 */
public class Code04_BricksFallingWhenHit {

	private static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	public int[] hitBricks(int[][] grid, int[][] hits) {
		// 将炮弹击中的位置标记成2
		for (int[] x : hits) {
			if (grid[x[0]][x[1]] == 1) grid[x[0]][x[1]] = 2;
		}
		// 建立并查集
		UnionFind uf = new UnionFind(grid);
		int[] ans = new int[hits.length];
		for (int i = hits.length - 1; i >= 0; i--) { // 倒序恢复+整理答案
			int[] hit = hits[i];
			if (grid[hit[0]][hit[1]] == 2) {
				ans[i] = uf.finger(hit[0], hit[1]);
			}
		}
		return ans;
	}
	private static class UnionFind {
		private int m;
		private int n;
		private int[][] grid; // 原始矩阵，1->2表示被炮弹击中
		private int[] fa;
		private int[] size; // 记录每个集合的数量
		private boolean[] celling; // i位置是不是天花板
		private int cellingNum; // 连到天花板砖块的数量
		public UnionFind(int[][] grid) {
			initSpace(grid);
			initConnect();
		}
		private void initSpace(int[][] grid) {
			this.m = grid.length;
			this.n = grid[0].length;
			this.grid = grid;
			this.fa = new int[m * n];
			this.size = new int[fa.length];
			this.celling = new boolean[fa.length];
			this.cellingNum = 0;
			for (int r = 0; r < m; r++) {
				for (int c = 0; c < n; c++) {
					if (grid[r][c] != 1) continue;
					int i = idx(r, c);  // 二维位置转成一维位置
					fa[i] = i;
					size[i] = 1;
					if (r == 0) {
						celling[i] = true;
						cellingNum++;
					}
				}
			}
		}
		private void initConnect() {
			for (int r = 0; r < m; r++) {
				for (int c = 0; c < n; c++) {
					for (int[] dir : dirs) {
						union(r, c, r + dir[0], c + dir[1]);
					}
				}
			}
		}
		public void union(int r1, int c1, int r2, int c2) {
			if (!valid(r1, c1) || !valid(r2, c2)) return;
			int fa1 = find(idx(r1, c1)), fa2 = find(idx(r2, c2));
			if (fa1 == fa2) return;
			int size1 = size[fa1], size2 = size[fa2];
			boolean state1 = celling[fa1], state2 = celling[fa2];
			if (size[fa1] < size[fa2]) {
				fa[fa1] = fa2;
				size[fa2] = size1 + size2;
				if (state1 ^ state2) {
					celling[fa2] = true;
					cellingNum += state1 ? size2 : size1;
				}
			} else {
				fa[fa2] = fa1;
				size[fa1] = size1 + size2;
				if (state1 ^ state2) {
					celling[fa1] = true;
					cellingNum += state1 ? size2 : size1;
				}
			}
		}
		public int find(int x) {
			if (fa[x] == x) return x;
			return find(fa[x]);
		}
		public int finger(int r, int c) {
			grid[r][c] = 1;
			int cur = idx(r, c);
			if (r == 0) {
				celling[cur] = true;
				cellingNum++;
			}
			fa[cur] = cur;
			size[cur] = 1;
			int pre = cellingNum;
			for (int[] dir : dirs) union(r, c, r + dir[0], c + dir[1]);
			int now = cellingNum;
			return r == 0 ? now - pre : (now == pre ? 0 : now - pre - 1);
		}
		private int idx(int r, int c) {
			return r * n + c;
		}
		private boolean valid(int r, int c) {
			return r >= 0 && r < m && c >= 0 && c < n && grid[r][c] == 1;
		}
	}
}
