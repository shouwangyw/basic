package com.yw.course.coding.class21;

import java.util.HashMap;

import static com.yw.util.CommonUtils.swap;

/**
 * @author yangwei
 */
public class TreeChainPartition {

	public static class TreeChain {
		private int ts; 		// 时间戳 0 1 2 3 4
		private int n;			// 节点个数，节点编号 1~n
		private int root;		// 根节点
		private int[][] tree;	// 树结构
		private int[] w;		// 权重数组，原0节点权重6 -> w[1] = 6
		private int[] fa;		// father数组，平移标号+1
		private int[] dep;		// 节点深度数组，记录节点在第几层
		private int[] son;		// son[i]=0表示没有儿子，son[i]=j!=0表示节点i的重儿子是j
		private int[] size;		// size[i]表示以节点i为根节点的子树有多少个节点
		private int[] top;		// top[i]=j表示节点i所在重链的头节点是j
		private int[] dfn;		// dfn[i]=j表示节点i在DFS序中是第j个
		private int[] tnw;		// 若原节点a权重是10，节点a在DFS序中是第5个节点，则tnw[5]=10
//		private Code01_SegmentTree.SegmentTree seg;// 线段树，在tnw上进行连续区间的查询或更新
		private SegmentTree seg;// 线段树，在tnw上进行连续区间的查询或更新
		// 支持二叉树、多叉树
		public TreeChain(int[] father, int[] vals) {
			// 完成tree的初始化，可以从节点i找到下级的直接孩子，vals设置到w，找到了根节点
			initTree(father, vals);
			// 完成fa、dep、son、size初始化
			dfs1(root, 0);
			// 完成top、dfn、tnw初始化
			dfs2(root, root);
			// 构建线段树
//			this.seg = new Code01_SegmentTree.SegmentTree(tnw);
			this.seg = new SegmentTree(tnw);
			seg.build(1, n, 1);
		}
		private void initTree(int[] father, int[] vals) {
			this.ts = 0;
			this.n = father.length;
			this.tree = new int[n + 1][];
			this.w = new int[n + 1];
			this.fa = new int[n + 1];
			this.dep = new int[n + 1];
			this.son = new int[n + 1];
			this.size = new int[n + 1];
			this.top = new int[n + 1];
			this.dfn = new int[n + 1];
			this.tnw = new int[n + 1];
			int[] cnt = new int[n];
			for (int i = 0; i < n; i++) w[i + 1] = vals[i];
			for (int i = 0; i < n; i++) {
				if (father[i] == i) root = i + 1;
				else cnt[father[i]]++;
			}
			tree[0] = new int[0];
			for (int i = 0; i < n; i++) tree[i + 1] = new int[cnt[i]];
			for (int i = 0; i < n; i++) {
				if (i + 1 != root) tree[father[i] + 1][--cnt[father[i]]] = i + 1;
			}
		}
		// u: 当前节点，f: u的父节点
		private void dfs1(int u, int f) {
			fa[u] = f;
			dep[u] = dep[f] + 1;
			size[u] = 1;
			int maxSize = -1;
			for (int v : tree[u]) { // 遍历u节点所有的直接孩子
				dfs1(v, u);
				size[u] += size[v];
				if (size[v] > maxSize) {
					maxSize = size[v];
					son[u] = v;
				}
			}
		}
		// u: 当前节点，t: u所在重链的头部
		private void dfs2(int u, int t) {
			dfn[u] = ++ts;
			top[u] = t;
			tnw[ts] = w[u];
			if (son[u] != 0) { // 如果u有儿子 size[u] > 1
				// 给重儿子标号
				dfs2(son[u], t);
				for (int v : tree[u]) {
					// 给轻儿子标号
					if (v != son[u]) dfs2(v, v);
				}
			}
		}
		/*********************以下实现树链相关方法********************/
		// 给以head为头的所有子树上的节点值+val，因为节点经过平移，头节点需要+1
		public void addSubTree(int head, int val) {
			head++;
			// 平移编号 -> dfs编号 dfn[head]
			seg.add(dfn[head], dfn[head] + size[head] - 1, val, 1, n, 1);
		}
		// 查询以head为头的某个子树所有节点值的累加和
		public int querySubTree(int head) {
			head++;
			return (int) seg.query(dfn[head], dfn[head] + size[head] - 1, 1, n, 1);
		}
		// 在树上从 a 到 b 的整条链上都+val
		public void addChain(int a, int b, int val) {
			a++;
			b++;
			while (top[a] != top[b]) { // != 表示不在一条链上
				// 谁深谁往上跳，沿途计算
				if (dep[top[a]] > dep[top[b]]) {
					seg.add(dfn[top[a]], dfn[a], val, 1, n, 1);
					a = fa[top[a]];
				} else {
					seg.add(dfn[top[b]], dfn[b], val, 1, n, 1);
					b = fa[top[b]];
				}
			}
			// 最终一定汇聚在一条链上，判断深度进行计算
			if (dep[a] > dep[b]) seg.add(dfn[b], dfn[a], val, 1, n, 1);
			else seg.add(dfn[a], dfn[b], val, 1, n, 1);
		}
		// 查询在树上从 a 到 b 的整条链上所有节点值的累加和
		public int queryChain(int a, int b) {
			a++;
			b++;
			int ans = 0;
			while (top[a] != top[b]) {
				if (dep[top[a]] > dep[top[b]]) {
					ans += seg.query(dfn[top[a]], dfn[a], 1, n, 1);
					a = fa[top[a]];
				} else {
					ans += seg.query(dfn[top[b]], dfn[b], 1, n, 1);
					b = fa[top[b]];
				}
			}
			if (dep[a] > dep[b]) ans += seg.query(dfn[b], dfn[a], 1, n, 1);
			else ans += seg.query(dfn[a], dfn[b], 1, n, 1);
			return ans;
		}
	}

	public static class SegmentTree {
		private int maxN;
		private int[] arr;			// 原序列的信息从0开始，但在arr里是从1开始的
		private int[] sum;			// 模拟线段树维护区间和
		private int[] lazy;			// 累加和懒标记

		public SegmentTree(int[] origin) {
			this.maxN = origin.length;
			this.arr = new int[maxN];
			System.arraycopy(origin, 0, arr, 0, maxN);
			this.sum = new int[maxN << 2];
			this.lazy = new int[maxN << 2];
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
		 * 当前来到rt位置(该位置代表的范围是[l,r])时，求[L,R]范围上所有数的累加和
		 */
		public int query(int L, int R, int l, int r, int rt) {
			// 任务全包
			if (L <= l && r <= R) return sum[rt];
			// 任务没全包
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);						// 先往下发一层
			int ans = 0;
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
			if (lazy[rt] != 0) {
				lazy[left] += lazy[rt];
				lazy[right] += lazy[rt];
				sum[left] += lazy[rt] * ln;
				sum[right] += lazy[rt] * rn;
				lazy[rt] = 0;
			}
		}
	}

	// 为了测试，这个结构是暴力但正确的方法
	public static class Right {
		private int n;
		private int[][] tree;
		private int[] fa;
		private int[] val;
		private HashMap<Integer, Integer> path;

		public Right(int[] father, int[] value) {
			n = father.length;
			tree = new int[n][];
			fa = new int[n];
			val = new int[n];
			for (int i = 0; i < n; i++) {
				fa[i] = father[i];
				val[i] = value[i];
			}
			int[] help = new int[n];
			int h = 0;
			for (int i = 0; i < n; i++) {
				if (father[i] == i) {
					h = i;
				} else {
					help[father[i]]++;
				}
			}
			for (int i = 0; i < n; i++) {
				tree[i] = new int[help[i]];
			}
			for (int i = 0; i < n; i++) {
				if (i != h) {
					tree[father[i]][--help[father[i]]] = i;
				}
			}
			path = new HashMap<>();
		}

		public void addSubtree(int head, int value) {
			val[head] += value;
			for (int next : tree[head]) {
				addSubtree(next, value);
			}
		}

		public int querySubtree(int head) {
			int ans = val[head];
			for (int next : tree[head]) {
				ans += querySubtree(next);
			}
			return ans;
		}

		public void addChain(int a, int b, int v) {
			path.clear();
			path.put(a, null);
			while (a != fa[a]) {
				path.put(fa[a], a);
				a = fa[a];
			}
			while (!path.containsKey(b)) {
				val[b] += v;
				b = fa[b];
			}
			val[b] += v;
			while (path.get(b) != null) {
				b = path.get(b);
				val[b] += v;
			}
		}

		public int queryChain(int a, int b) {
			path.clear();
			path.put(a, null);
			while (a != fa[a]) {
				path.put(fa[a], a);
				a = fa[a];
			}
			int ans = 0;
			while (!path.containsKey(b)) {
				ans += val[b];
				b = fa[b];
			}
			ans += val[b];
			while (path.get(b) != null) {
				b = path.get(b);
				ans += val[b];
			}
			return ans;
		}

	}

	public static void main(String[] args) {
		int N = 50000;
		int V = 100000;
		int[] father = generateFatherArray(N);
		int[] values = generateValueArray(N, V);
		TreeChain tc = new TreeChain(father, values);
		Right right = new Right(father, values);
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			double decision = Math.random();
			if (decision < 0.25) {
				int head = (int) (Math.random() * N);
				int value = (int) (Math.random() * V);
				tc.addSubTree(head, value);
				right.addSubtree(head, value);
			} else if (decision < 0.5) {
				int head = (int) (Math.random() * N);
				int ans1 = tc.querySubTree(head);
				int ans2 = right.querySubtree(head);
				if (ans1 != ans2) {
					System.out.println("querySubTree 出错了! ans1 = " + ans1 + ", ans2 = " + ans2);
					break;
				}
			} else if (decision < 0.75) {
				int a = (int) (Math.random() * N);
				int b = (int) (Math.random() * N);
				int value = (int) (Math.random() * V);
				tc.addChain(a, b, value);
				right.addChain(a, b, value);
			} else {
				int a = (int) (Math.random() * N);
				int b = (int) (Math.random() * N);
				int ans1 = tc.queryChain(a, b);
				int ans2 = right.queryChain(a, b);
				if (ans1 != ans2) {
					System.out.println("queryChain 出错了! ans1 = " + ans1 + ", ans2 = " + ans2);
					break;
				}
			}
		}
		System.out.println("测试结束");
	}
	// 为了测试
	// 随机生成N个节点树，可能是多叉树，并且一定不是森林
	// 输入参数N要大于0
	private static int[] generateFatherArray(int N) {
		int[] order = new int[N];
		for (int i = 0; i < N; i++) {
			order[i] = i;
		}
		for (int i = N - 1; i >= 0; i--) {
			swap(order, i, (int) (Math.random() * (i + 1)));
		}
		int[] ans = new int[N];
		ans[order[0]] = order[0];
		for (int i = 1; i < N; i++) {
			ans[order[i]] = order[(int) (Math.random() * i)];
		}
		return ans;
	}
	private static int[] generateValueArray(int N, int V) {
		int[] ans = new int[N];
		for (int i = 0; i < N; i++) {
			ans[i] = (int) (Math.random() * V) + 1;
		}
		return ans;
	}
}
