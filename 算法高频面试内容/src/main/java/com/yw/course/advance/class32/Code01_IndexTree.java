package com.yw.course.advance.class32;

/**
 * @author yangwei
 */
public class Code01_IndexTree {

	public static class IndexTree {
		private int[] tree;
		private int n;
		public IndexTree(int n) {
			this.n = n;
			// 0位置弃而不用，下标从1开始
			this.tree = new int[n + 1];
		}
		// 求[1,idx]的累加和
		public int sum(int idx) {
			int ans = 0;
			while (idx > 0) {
				ans += tree[idx];
				// idx & -idx : 提取出idx最右侧的1
				// 比如 idx = 11001000，那么 idx & -idx = 00001000
				idx -= idx & -idx;
			}
			return ans;
		}
		// 将原数组idx位置的数加d
		public void add(int idx, int d) {
			while (idx <= n) {
				tree[idx] += d;
				idx += idx & -idx;
			}
		}
	}

	public static class Right {
		private int[] nums;
		private int N;

		public Right(int size) {
			N = size + 1;
			nums = new int[N + 1];
		}

		public int sum(int index) {
			int ret = 0;
			for (int i = 1; i <= index; i++) {
				ret += nums[i];
			}
			return ret;
		}

		public void add(int index, int d) {
			nums[index] += d;
		}

	}

	public static void main(String[] args) {
		int N = 100;
		int V = 100;
		int testTime = 2000000;
		IndexTree tree = new IndexTree(N);
		Right test = new Right(N);
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int index = (int) (Math.random() * N) + 1;
			if (Math.random() <= 0.5) {
				int add = (int) (Math.random() * V);
				tree.add(index, add);
				test.add(index, add);
			} else {
				if (tree.sum(index) != test.sum(index)) {
					System.out.println("Oops!");
				}
			}
		}
		System.out.println("test finish");
	}

}
