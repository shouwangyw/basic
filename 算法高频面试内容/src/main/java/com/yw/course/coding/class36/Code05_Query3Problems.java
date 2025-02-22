package com.yw.course.coding.class36;

/**
 * 来自美团
 * 给定一个数组arr，长度为N，做出一个结构，可以高效的做如下的查询
 * 1) int querySum(L,R) : 查询arr[L...R]上的累加和
 * 2) int queryAim(L,R) : 查询arr[L...R]上的目标值，目标值定义如下：
 * 	假设arr[L...R]上的值为[a,b,c,d]，a+b+c+d = s
 * 	目标值为 : (s-a)^2 + (s-b)^2 + (s-c)^2 + (s-d)^2
 * 3) int queryMax(L,R) : 查询arr[L...R]上的最大值
 * 要求：
 * 1) 初始化该结构的时间复杂度不能超过O(N*logN)
 * 2) 三个查询的时间复杂度不能超过O(logN)
 * 3) 查询时，认为arr的下标从1开始，比如 :
 * arr = [ 1, 1, 2, 3 ];
 * querySum(1, 3) -> 4
 * queryAim(2, 4) -> 50
 * queryMax(1, 4) -> 3
 *
 * @author yangwei
 */
public class Code05_Query3Problems {

	public static class Query {

		private final int n;
		private final int[] prefixSum;
		private final int[] prefixSquareSum;
		private final SegmentTree st;
		public Query(int[] arr) {
			this.n = arr.length + 1;
			this.prefixSum = new int[n];
			this.prefixSquareSum = new int[n];
			this.st = new SegmentTree(n);
			for (int i = 0; i < arr.length; i++) {
				prefixSum[i + 1] = prefixSum[i] + arr[i];
				prefixSquareSum[i + 1] = prefixSquareSum[i] + arr[i] * arr[i];
				st.update(i + 1, i + 1, arr[i], 1, n, 1);
			}
		}
		public int querySum(int l, int r) {
			return prefixSum[r] - prefixSum[l - 1];
		}
		public int queryAim(int l, int r) {
			int preSum = querySum(l, r);
			preSum *= preSum;
			return prefixSquareSum[r] - prefixSquareSum[l - 1] + (r - l - 1) * preSum;
		}
		public int queryMax(int l, int r) {
			return st.query(l, r, 1, n, 1);
		}

		private static class SegmentTree {
			private int[] max;
			private int[] change;
			private boolean[] update;
			public SegmentTree(int size) {
				int n = (size + 1) << 2;
				max = new int[n];
				change = new int[n];
				update = new boolean[n];
			}
			public void update(int L, int R, int C, int l, int r, int rt) {
				if (L <= l && r <= R) {
					update[rt] = true;
					change[rt] = C;
					max[rt] = C;
					return;
				}
				int mid = (l + r) >> 1;
				pushDown(rt, mid - l + 1, r - mid);
				if (L <= mid) update(L, R, C, l, mid, rt << 1);
				if (R > mid) update(L, R, C, mid + 1, r, rt << 1 | 1);
				pushUp(rt);
			}
			public int query(int L, int R, int l, int r, int rt) {
				if (L <= l && r <= R) return max[rt];
				int mid = (l + r) >> 1;
				pushDown(rt, mid - l + 1, r - mid);
				int left = 0, right = 0;
				if (L <= mid) left = query(L, R, l, mid, rt << 1);
				if (R > mid) right = query(L, R, mid + 1, r, rt << 1 | 1);
				return Math.max(left, right);
			}
			private void pushUp(int rt) {
				max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
			}
			private void pushDown(int rt, int ln, int rn) {
				int left = rt << 1, right = rt << 1 | 1;
				if (update[rt]) {
					update[left] = true;
					update[right] = true;
					change[left] = change[rt];
					change[right] = change[rt];
					max[left] = change[rt];
					max[right] = change[rt];
					update[rt] = false;
				}
			}
		}
	}

	public static void main(String[] args) {
		int[] arr = { 1, 1, 2, 3 };
		Query q = new Query(arr);
		System.out.println(q.querySum(1, 3));
		System.out.println(q.queryAim(2, 4));
		System.out.println(q.queryMax(1, 4));
	}

}
