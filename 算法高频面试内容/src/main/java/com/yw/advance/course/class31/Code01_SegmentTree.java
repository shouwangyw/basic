package com.yw.advance.course.class31;

/**
 * @author yangwei
 */
public class Code01_SegmentTree {

	public static class SegmentTree {
		private int[] arr;			// 原序列的信息从0开始，但在arr里是从1开始的
		private int[] sum;			// 模拟线段树维护区间和
		private int[] lazy;			// 累加和懒标记
		private int[] change;		// 更新的值
		private boolean[] update;	// 更新总懒标记

		public SegmentTree(int[] origin) {
			int maxN = origin.length + 1;
			arr = new int[maxN]; // arr[0] 不用 从1开始使用
			System.arraycopy(origin, 0, arr, 1, maxN - 1);

			sum = new int[maxN << 2]; 		// 用来支持脑补概念中，某一个范围的累加和信息
			lazy = new int[maxN << 2]; 		// 用来支持脑补概念中，某一个范围没有往下传递的累加任务
			change = new int[maxN << 2]; 	// 用来支持脑补概念中，某一个范围有没有更新操作的任务
			update = new boolean[maxN << 2];// 用来支持脑补概念中，某一个范围更新任务，更新成了什么
		}

		/**
		 * 构建线段树
		 * 在初始化阶段，先把sum数组，填好
		 * 在arr[l~r]范围上，去build 1~N，rt: 这个范围在sum中的下标
		 */
		public void build(int l, int r, int rt) {
			if (l == r) { // 叶节点
				sum[rt] = arr[l];
				return;
			}
			int mid = (l + r) >> 1;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			pushUp(rt);
		}

		/**
		 * 当前来到rt位置(该位置代表的范围是[l,r])时，在[L,R]范围将每个位置的数加C
		 */
		public void add(int L, int R, int C, int l, int r, int rt) {
			// 任务如果把此时的范围全包了！
			if (L <= l && r <= R) {
				sum[rt] += C * (r - l + 1);
				lazy[rt] += C; // 懒增加
				return;
			}
			// 任务没有把你全包！
			// l  r  mid = (l+r)/2
			int mid = (l + r) >> 1;
				pushDown(rt, mid - l + 1, r - mid);				// 先往下发一层
			if (L <= mid) add(L, R, C, l, mid, rt << 1);			 	// 任务下发给左边
			if (R > mid) add(L, R, C, mid + 1, r, rt << 1 | 1);	// 任务下发给右边
			pushUp(rt);
		}

		/**
		 * 当前来到rt位置(该位置代表的范围是[l,r])时，在[L,R]范围将每个位置的数更新成C
		 */
		public void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				update[rt] = true;
				change[rt] = C;
				sum[rt] = C * (r - l + 1);
				lazy[rt] = 0;
				return;
			}
			// 当前任务躲不掉，无法懒更新，要往下发
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);					// 先往下发一层
			if (L <= mid) update(L, R, C, l, mid, rt << 1);			// 任务下发给左边
			if (R > mid) update(L, R, C, mid + 1, r, rt << 1 | 1);	// 任务下发给右边
			pushUp(rt);
		}

		/**
		 * 当前来到rt位置(该位置代表的范围是[l,r])时，求[L,R]范围上所有数的累加和
		 */
		public long query(int L, int R, int l, int r, int rt) {
			// 任务全包
			if (L <= l && r <= R) return sum[rt];
			// 任务没全包
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);						// 先往下发一层
			long ans = 0;
			if (L <= mid) ans += query(L, R, l, mid, rt << 1);			// 任务下发给左边
			if (R > mid) ans += query(L, R, mid + 1, r, rt << 1 | 1);	// 任务下发给右边
			return ans;
		}

		private void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		}

		/**
		 * 之前的所有懒增加和懒更新，从父范围，发给左右两个子范围
		 * 分发策略是什么？
		 * ln: 表示左子树元素结点个数，rn: 表示右子树结点个数
		 */
		private void pushDown(int rt, int ln, int rn) {
			int left = rt << 1, right = rt << 1 | 1;
			if (update[rt]) {
				update[left] = true;
				update[right] = true;
				change[left] = change[rt];
				change[right] = change[rt];
				lazy[left] = 0;
				lazy[right] = 0;
				sum[left] = change[rt] * ln;
				sum[right] = change[rt] * rn;
				update[rt] = false;
			}
			if (lazy[rt] != 0) {
				lazy[left] += lazy[rt];
				lazy[right] += lazy[rt];
				sum[left] += lazy[rt] * ln;
				sum[right] += lazy[rt] * rn;
				lazy[rt] = 0;
			}
		}
	}

	public static class Right {
		public int[] arr;
		public Right(int[] origin) {
			arr = new int[origin.length + 1];
			for (int i = 0; i < origin.length; i++) {
				arr[i + 1] = origin[i];
			}
		}
		public void update(int L, int R, int C) {
			for (int i = L; i <= R; i++) arr[i] = C;
		}
		public void add(int L, int R, int C) {
			for (int i = L; i <= R; i++) arr[i] += C;
		}
		public long query(int L, int R) {
			long ans = 0;
			for (int i = L; i <= R; i++) ans += arr[i];
			return ans;
		}
	}

	public static boolean test() {
		int len = 100;
		int max = 1000;
		int testTimes = 5000;
		int addOrUpdateTimes = 1000;
		int queryTimes = 500;
		for (int i = 0; i < testTimes; i++) {
			int[] origin = generateRandomArray(len, max);
			SegmentTree seg = new SegmentTree(origin);
			int S = 1;
			int N = origin.length;
			int root = 1;
			seg.build(S, N, root);
			Right rig = new Right(origin);
			for (int j = 0; j < addOrUpdateTimes; j++) {
				int num1 = (int) (Math.random() * N) + 1;
				int num2 = (int) (Math.random() * N) + 1;
				int L = Math.min(num1, num2);
				int R = Math.max(num1, num2);
				int C = (int) (Math.random() * max) - (int) (Math.random() * max);
				if (Math.random() < 0.5) {
					seg.add(L, R, C, S, N, root);
					rig.add(L, R, C);
				} else {
					seg.update(L, R, C, S, N, root);
					rig.update(L, R, C);
				}
			}
			for (int k = 0; k < queryTimes; k++) {
				int num1 = (int) (Math.random() * N) + 1;
				int num2 = (int) (Math.random() * N) + 1;
				int L = Math.min(num1, num2);
				int R = Math.max(num1, num2);
				long ans1 = seg.query(L, R, S, N, root);
				long ans2 = rig.query(L, R);
				if (ans1 != ans2) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
		SegmentTree seg = new SegmentTree(origin);
		int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
		int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
		int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
		int L = 2; // 操作区间的开始位置 -> 可变
		int R = 5; // 操作区间的结束位置 -> 可变
		int C = 4; // 要加的数字或者要更新的数字 -> 可变
		// 区间生成，必须在[S,N]整个范围上build
		seg.build(S, N, root);
		// 区间修改，可以改变L、R和C的值，其他值不可改变
		seg.add(L, R, C, S, N, root);
		// 区间更新，可以改变L、R和C的值，其他值不可改变
		seg.update(L, R, C, S, N, root);
		// 区间查询，可以改变L和R的值，其他值不可改变
		long sum = seg.query(L, R, S, N, root);
		System.out.println(sum);

		System.out.println("对数器测试开始...");
		System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

	}

	private static int[] generateRandomArray(int len, int max) {
		int size = (int) (Math.random() * len) + 1;
		int[] origin = new int[size];
		for (int i = 0; i < size; i++) {
			origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
		}
		return origin;
	}
}
