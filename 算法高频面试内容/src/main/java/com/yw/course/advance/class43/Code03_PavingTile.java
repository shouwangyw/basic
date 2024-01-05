package com.yw.course.advance.class43;

import java.util.Arrays;

/**
 * 2*M铺地的问题非常简单，这个是解决N*M铺地的问题
 * @author yangwei
 */
public class Code03_PavingTile {

	// 方法一：暴力递归尝试
	public static int countTilingWays0(int n, int m) {
		if (n < 1 || m < 1 || ((n * m) & 1) != 0) return 0;
		if (n == 1 || m == 1) return 1;
		// pre[i]: 0表示无砖，1表示有砖
		byte[] pre = new byte[m];
		// pre代表上一行的状况
		for (int i = 0; i < m; i++) pre[i] = 1;
		return process(pre, 0, n);
	}
	// pre表示level-1行的状态，level表示正在level行做决定，n表示一共有多少行(固定参数)
	// level-2行及其之上所有行都已经铺满砖了，在level行做决定，返回让所有区域都满的方法数
	private static int process(byte[] pre, int level, int n) {
		// base case: 第n行是无效行，上一行是终止行，如果存在0说明不是一个有效解
		if (level == n) {
			for (byte x : pre) {
				if (x == 0) return 0;
			}
			return 1;
		}
		// 没到终止行，可以选择在当前的level行摆瓷砖
		byte[] op = getOp(pre);
		return dfs(op, 0, level, n);
	}
	// 0 -> 1, 1 -> 0
	private static byte[] getOp(byte[] pre) {
		byte[] cur = new byte[pre.length];
		for (int i = 0; i < pre.length; i++) {
			cur[i] = (byte) (pre[i] ^ 1);
		}
		return cur;
	}
	// op[i] == 0 可以考虑摆砖，op[i] == 1 只能竖着向上
	private static int dfs(byte[] op, int col, int level, int n) {
		// 在列上自由发挥，玩深度优先遍历，当col来到终止列，i行的决定做完了
		if (col == op.length) {
			// 轮到i+1行，做决定
			return process(op, level + 1, n);
		}
		int ans = 0;
		// col位置不横摆
		ans += dfs(op, col + 1, level, n);
		// col位置横摆, 向右
		if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
			op[col] = 1;
			op[col + 1] = 1;
			ans += dfs(op, col + 2, level, n);
			op[col] = 0;
			op[col + 1] = 0;
		}
		return ans;
	}

	// 方法二：位运算版本-暴力递归尝试，min(m, n) 不超过32，用一个int数的位信息表示状态
	public static int countTilingWays(int m, int n) {
		if (n < 1 || m < 1 || ((n * m) & 1) != 0) return 0;
		if (n == 1 || m == 1) return 1;
		int min = Math.min(n, m), max = Math.max(n, m);
		int pre = (1 << min) - 1;
		return process(pre, 0, max, min);
	}
	private static int process(int pre, int level, int n, int m) {
		// base case
		if (level == n) {
			return pre == ((1 << m) - 1) ? 1 : 0;
		}
		int op = ((~pre) & ((1 << m) - 1));
		return dfs(op, m - 1, level, n, m);
	}
	private static int dfs(int op, int col, int level, int n, int m) {
		if (col == -1) return process(op, level + 1, n, m);
		int ans = dfs(op, col - 1, level, n, m);
		if ((op & (1 << col)) == 0 && col - 1 >= 0 && (op & (1 << (col - 1))) == 0) {
			ans += dfs((op | (3 << (col - 1))), col - 2, level, n, m);
		}
		return ans;
	}

	// 方法三：记忆化搜索版本，min(m, n) 不超过32
	public static int countTilingWaysRecord(int m, int n) {
		if (n < 1 || m < 1 || ((n * m) & 1) != 0) return 0;
		if (n == 1 || m == 1) return 1;
		int min = Math.min(n, m), max = Math.max(n, m);
		int pre = (1 << min) - 1;
		int[][] record = new int[1 << min][max + 1];
		for (int[] x : record) Arrays.fill(x, -1);
		return process(pre, 0, max, min, record);
	}
	private static int process(int pre, int level, int n, int m, int[][] record) {
		if (record[pre][level] != -1) return record[pre][level];
		int ans;
		if (level == n) ans = pre == ((1 << m) - 1) ? 1 : 0;
		else {
			int op = ((~pre) & ((1 << m) - 1));
			ans = dfs(op, m - 1, level, n, m, record);
		}
		record[pre][level] = ans;
		return ans;
	}
	private static int dfs(int op, int col, int level, int n, int m, int[][] record) {
		if (col == -1) return process(op, level + 1, n, m, record);
		int ans = dfs(op, col - 1, level, n, m, record);
		if (col > 0 && (op & (3 << (col - 1))) == 0) {
			ans += dfs((op | (3 << (col - 1))), col - 2, level, n, m, record);
		}
		return ans;
	}

	// 方法四：严格位置依赖的动态规划解
	public static int countTilingWaysDp(int n, int m) {
		if (n < 1 || m < 1 || ((n * m) & 1) != 0) return 0;
		if (n == 1 || m == 1) return 1;
		int min = Math.min(n, m), max = Math.max(n, m);
		int sn = 1 << min, limit = sn - 1;
		int[] dp = new int[sn];
		dp[limit] = 1;
		int[] cur = new int[sn];
		for (int level = 0; level < max; level++) {
			for (int status = 0; status < sn; status++) {
				if (dp[status] == 0) continue;
				int op = (~status) & limit;
				dfs(dp[status], op, 0, min - 1, cur);
			}
			for (int i = 0; i < sn; i++) dp[i] = 0;
			int[] tmp = dp;
			dp = cur;
			cur = tmp;
		}
		return dp[limit];
	}
	private static void dfs(int way, int op, int index, int end, int[] cur) {
		if (index == end) {
			cur[op] += way;
		} else {
			dfs(way, op, index + 1, end, cur);
			// 11 << index 可以放砖
			if (((3 << index) & op) == 0) {
				dfs(way, op | (3 << index), index + 1, end, cur);
			}
		}
	}

	public static void main(String[] args) {
		int N = 8;
		int M = 6;
		System.out.println(countTilingWays0(N, M));
		System.out.println(countTilingWays(N, M));
		System.out.println(countTilingWaysRecord(N, M));
		System.out.println(countTilingWaysDp(N, M));

		N = 10;
		M = 10;
		System.out.println("=========");
		System.out.println(countTilingWaysRecord(N, M));
		System.out.println(countTilingWaysDp(N, M));
	}
}
