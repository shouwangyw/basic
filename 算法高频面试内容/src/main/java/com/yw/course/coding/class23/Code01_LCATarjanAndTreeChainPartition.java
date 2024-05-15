package com.yw.course.coding.class23;

import com.yw.course.coding.class21.TreeChainPartition;

import java.util.HashSet;
import java.util.Set;

import static com.yw.util.CommonUtils.isEqual;
import static com.yw.util.CommonUtils.swap;

/**
 * 给定数组tree大小为N，表示一共有N个节点
 * tree[i] = j 表示点i的父亲是点j，tree一定是一棵树而不是森林
 * queries是二维数组，大小为M*2，每一个长度为2的数组都表示一条查询
 * [4,9], 表示想查询4和9之间的最低公共祖先
 * [3,7], 表示想查询3和7之间的最低公共祖先
 * tree和queries里面的所有值，都一定在0~N-1之间
 * 返回一个数组ans，大小为M，ans[i]表示第i条查询的答案
 *
 * @author yangwei
 */
public class Code01_LCATarjanAndTreeChainPartition {

	// 方法一：暴力解
	public static int[] lcaQuery0(int[] father, int[][] queries) {
		int m = queries.length;
		int[] ans = new int[m];
		Set<Integer> path = new HashSet<>();
		for (int i = 0; i < m; i++) {
			int j = queries[i][0];
			while (father[j] != j) {
				path.add(j);
				j = father[j];
			}
			path.add(j);
			j = queries[i][1];
			while (!path.contains(j)) j = father[j];
			ans[i] = j;
			path.clear();
		}
		return ans;
	}

	// 方法二：Tarjan + 并查集，离线批量查询最优解，时间复杂度O(N + M)，但是必须把M条查询一次给全，不支持在线查询
	public static int[] lcaQueryOffline(int[] father, int[][] queries) {
		int n = father.length, m = queries.length;
		// help[i]表示节点i做头的孩子节点数量
		int[] help = new int[n];
		int root = 0;
		for (int i = 0; i < n; i++) {
			if (father[i] == i) root = i;
			else help[father[i]]++;
		}
		int[][] mt = new int[n][];
		for (int i = 0; i < n; i++) mt[i] = new int[help[i]];
		for (int i = 0; i < n; i++) {
			if (i != root) mt[father[i]][--help[father[i]]] = i;
		}
		for (int[] query : queries) {
			if (query[0] != query[1]) {
				help[query[0]]++;
				help[query[1]]++;
			}
		}
		int[][] mq = new int[n][], mi = new int[n][];
		for (int i = 0; i < n; i++) {
			mq[i] = new int[help[i]];
			mi[i] = new int[help[i]];
		}
		for (int i = 0; i < m; i++) {
			if (queries[i][0] != queries[i][1]) {
				mq[queries[i][0]][--help[queries[i][0]]] = queries[i][1];
				mi[queries[i][0]][help[queries[i][0]]] = i;
				mq[queries[i][1]][--help[queries[i][1]]] = queries[i][0];
				mi[queries[i][1]][help[queries[i][1]]] = i;
			}
		}
		int[] ans = new int[m];
		UnionFind uf = new UnionFind(n);
		process(root, mt, mq, mi, uf, ans);
		for (int i = 0; i < m; i++) {
			if (queries[i][0] == queries[i][1]) ans[i] = queries[i][0];
		}
		return ans;
	}
	// 当前来到head，mt是整棵树head下方有哪些点 mt[head] = {a,b,c,d}表示head的孩子是a、b、c、d
	// mq: 问题列表，head有哪些问题 mq[head] = {x,y,z} 表示 (head，x) (head，y) (head z)
	// mi: 得到问题的答案应该填在ans的什么地方 {6,12,34}
	// uf: 并查集
	private static void process(int head, int[][] mt, int[][] mq, int[][] mi, UnionFind uf, int[] ans) {
		// 遍历head的孩子
		for (int x : mt[head]) {
			process(x, mt, mq, mi, uf, ans);
			uf.union(head, x);
			uf.setTag(head, head);
		}
		// 解决head的问题
		int[] q = mq[head], i = mi[head];
		for (int k = 0; k < q.length; k++) {
			// head和谁有问题 q[k] 答案填哪 i[k]
			int tag = uf.getTag(q[k]);
			if (tag != -1) ans[i[k]] = tag;
		}
	}
	private static class UnionFind {
		private int[] f;
		private int[] t;
		public UnionFind(int n) {
			this.f = new int[n];
			this.t = new int[n];
			for (int i = 0; i < n; i++) {
				f[i] = i;
				t[i] = -1;
			}
		}
		public int find(int x) {
			return f[x] = (f[x] == x ? x : find(f[x]));
		}
		public void union(int i, int j) {
			f[find(i)] = find(j);
		}
		// 集合的某个元素是i，请把整个集合打上统一的标签 tag
		public void setTag(int i, int tag) {
			t[find(i)] = tag;
		}
		// 集合的某个元素是i，请把整个集合的tag信息返回
		public int getTag(int i) {
			return t[find(i)];
		}
	}

	// 方法三：树链剖分，在线查询最优解，空间复杂度O(N)，单次查询时间复杂度O(logN)，如果有M次查询，时间复杂度O(N + M * logN)
	public static int[] lcaQueryOnline(int[] father, int[][] queries) {
		TreeChain tc = new TreeChain(father);
		int m = queries.length;
		int[] ans = new int[m];
		for (int i = 0; i < m; i++) {
			if (queries[i][0] == queries[i][1]) ans[i] = queries[i][0];
			else ans[i] = tc.lca(queries[i][0], queries[i][1]);
		}
		return ans;
	}
	public static class TreeChain {
		private int n;			// 节点个数，节点编号 1~n
		private int root;		// 根节点
		private int[][] tree;	// 树结构
		private int[] fa;		// father数组，平移标号+1
		private int[] dep;		// 节点深度数组，记录节点在第几层
		private int[] son;		// son[i]=0表示没有儿子，son[i]=j!=0表示节点i的重儿子是j
		private int[] size;		// size[i]表示以节点i为根节点的子树有多少个节点
		private int[] top;		// top[i]=j表示节点i所在重链的头节点是j
		// 支持二叉树、多叉树
		public TreeChain(int[] father) {
			// 完成tree的初始化，可以从节点i找到下级的直接孩子
			initTree(father);
			// 完成fa、dep、son、size初始化
			dfs1(root, 0);
			// 完成top、dfn、tnw初始化
			dfs2(root, root);
		}
		private void initTree(int[] father) {
			this.n = father.length;
			this.tree = new int[n + 1][];
			this.fa = new int[n + 1];
			this.dep = new int[n + 1];
			this.son = new int[n + 1];
			this.size = new int[n + 1];
			this.top = new int[n + 1];
			int[] cnt = new int[n];
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
			top[u] = t;
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
		// 查询两个节点的最近公共祖先
		public int lca(int a, int b) {
			a++;
			b++;
			while (top[a] != top[b]) {
				if (dep[top[a]] > dep[top[b]]) a = fa[top[a]];
				else b = fa[top[b]];
			}
			return (dep[a] < dep[b] ? a : b) - 1;
		}
	}

	public static void main(String[] args) {
		int N = 1000;
		int M = 200;
		int testTime = 50000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int size = (int) (Math.random() * N) + 1;
			int ques = (int) (Math.random() * M) + 1;
			int[] father = generateFatherArray(size);
			int[][] queries = generateQueries(ques, size);
			int[] ans1 = lcaQuery0(father, queries);
			int[] ans2 = lcaQueryOffline(father, queries);
			int[] ans3 = lcaQueryOnline(father, queries);
			if (!isEqual(ans1, ans2) || !isEqual(ans1, ans3)) {
				System.out.println("出错了！");
				break;
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
	// 为了测试
	// 随机生成M条查询，点有N个，点的编号在0~N-1之间
	// 输入参数M和N都要大于0
	private static int[][] generateQueries(int M, int N) {
		int[][] ans = new int[M][2];
		for (int i = 0; i < M; i++) {
			ans[i][0] = (int) (Math.random() * N);
			ans[i][1] = (int) (Math.random() * N);
		}
		return ans;
	}
}
