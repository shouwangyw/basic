package com.yw.advance.course.class42;

/**
 * 测试链接：https://leetcode.cn/problems/super-egg-drop
 * // 方法1和方法2会超时
 * // 方法3勉强通过
 * // 方法4打败100%
 * // 方法5打败100%，方法5是在方法4的基础上做了进一步的常数优化
 * @author yangwei
 */
public class Code02_ThrowChessPiecesProblem {

	// 方法一：暴力递归，复杂度太高会超时
	public static int superEggDrop0(int k, int n) {
		if (k < 1 || n < 1) return 0;
		return process(n, k);
	}
	// 还剩k个鸡蛋去验证level层楼，返回至少需要扔几次
	private static int process(int level, int k) {
		if (level == 0) return 0;
		// 只有1个鸡蛋时，只能依次从下往上每层楼都去试
		if (k == 1) return level;
		int min = Integer.MAX_VALUE;
		for (int i = 1; i <= level; i++) {
			// 1、第i层鸡蛋碎了，则还剩k-1个鸡蛋去下面的i-1层尝试
			// 2、第i层鸡蛋没碎，则还剩k个鸡蛋去上面的level-i层尝试
			min = Math.min(min, Math.max(process(i - 1, k - 1), process(level - i, k)));
		}
		return min + 1;
	}

	// 方法二：改动态规划(不进行枚举优化)，复杂度还是太高会超时
	public static int superEggDrop(int k, int n) {
		if (k < 1 || n < 1) return 0;
		if (k == 1) return n;
		// dp[i][j]: i层楼有j个鸡蛋最多扔几次
		int[][] dp = new int[n + 1][k + 1];
		// 初始化dp，第0行(即0层楼不用尝试)都是0，第0列(即0个鸡蛋)无效
		// 第1列(即1个鸡蛋，则最差情况有多少层楼就得尝试多少次)
		for (int i = 1; i <= n; i++) dp[i][1] = i;
		// 普遍位置填写dp表，从上往下、从左往右
		for (int i = 1; i <= n; i++) {
			for (int j = 2; j <= k; j++) {
				int min = Integer.MAX_VALUE;
				// 枚举每一种情况
				for (int i0 = 1; i0 <= i; i0++) {
					min = Math.min(min, Math.max(dp[i0 - 1][j - 1], dp[i - i0][j]));
				}
				dp[i][j] = min + 1;
			}
		}
		return dp[n][k];
	}

	// 方法三：进行四边形不等式枚举优化版本的动态规划，能勉强通过
	public static int superEggDropOptimize(int k, int n) {
		if (k < 1 || n < 1) return 0;
		if (k == 1) return n;
		// dp[i][j]: i层楼有j个鸡蛋最多扔几次
		int[][] dp = new int[n + 1][k + 1];
		int[][] best = new int[n + 1][k + 1];
		// 初始化dp，第0行(即0层楼不用尝试)都是0，第0列(即0个鸡蛋)无效
		// 第1列(即1个鸡蛋，则最差情况有多少层楼就得尝试多少次)
		for (int i = 1; i <= n; i++) dp[i][1] = i;
		// 第1行(即1层楼，不管有几个鸡蛋都只需要尝试1次)
		for (int i = 1; i <= k; i++) {
			dp[1][i] = 1;
			best[1][i] = 1;
		}
		// 普遍位置填写dp表，从上往下、从右往左
		for (int i = 2; i <= n; i++) {
			for (int j = k; j > 1; j--) {
				// 依赖上边作为下限、右边作为上限
				int down = best[i - 1][j], up = j == k ? i : best[i][j + 1];
				int min = Integer.MAX_VALUE, bestChoose = -1;
				// 优化枚举
				for (int first = down; first <= up; first++) {
					int cur = Math.max(dp[first - 1][j - 1], dp[i - first][j]);
					if (cur <= min) {
						min = cur;
						bestChoose = first;
					}
				}
				dp[i][j] = min + 1;
				best[i][j] = bestChoose;
			}
		}
		return dp[n][k];
	}

	// 方法四：最优解
	public static int superEggDropOptimal(int k, int n) {
		if (k < 1 || n < 1) return 0;
		// dp[i][j]: 在有i个鸡蛋时，扔j次最多可以验多少层，首次超过n时对应的j就是答案
		// 普遍位置依赖：dp[i][j] = dp[i][j - 1] + dp[i - 1][j - 1] + 1
		// 进一步优化，用一个一维数组，滚动计算直到推出答案
		int[] dp = new int[k];
		int ans = 0;
		while (true) {
			ans++;
			int pre = 0;
			for (int i = 0; i < k; i++) {
				int tmp = dp[i];
				dp[i] = dp[i] + pre + 1;
				pre = tmp;
				if (dp[i] >= n) return ans;
			}
		}
	}

	// 方法五：在方法四的基础上做进一步的常数优化
	public static int superEggDropBest(int k, int n) {
		if (k < 1 || n < 1) return 0;
		int bestTimes = log2N(n) + 1;
		if (k >= bestTimes) return bestTimes;
		int[] dp = new int[k];
		int ans = 0;
		while (true) {
			ans++;
			int pre = 0;
			for (int i = 0; i < k; i++) {
				int tmp = dp[i];
				dp[i] = dp[i] + pre + 1;
				pre = tmp;
				if (dp[i] >= n) return ans;
			}
		}
	}
	private static int log2N(int n) {
		int res = -1;
		while (n != 0) {
			res++;
			n >>>= 1;
		}
		return res;
	}

	public static void main(String[] args) {
		int maxN = 10;
		int maxK = 3;
		int testTime = 1000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * maxN) + 1;
			int K = (int) (Math.random() * maxK) + 1;
			int ans1 = superEggDrop0(K, N);
			int ans2 = superEggDrop(K, N);
			int ans3 = superEggDropOptimize(K, N);
			int ans4 = superEggDropOptimal(K, N);
			int ans5 = superEggDropBest(K, N);
//			if (ans1 != ans2 || ans2 != ans3 || ans4 != ans5 || ans2 != ans4) {
			if (ans2 != ans3 || ans4 != ans5 || ans2 != ans4) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
