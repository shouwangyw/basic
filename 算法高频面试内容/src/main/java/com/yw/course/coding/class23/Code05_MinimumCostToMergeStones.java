package com.yw.course.coding.class23;

/**
 * 测试链接: https://leetcode.cn/problems/minimum-cost-to-merge-stones/
 *
 * @author yangwei
 */
public class Code05_MinimumCostToMergeStones {

//	// arr[L...R]一定要整出P份，合并的最小代价，返回！
//	public static int f(int[] arr, int K, int L, int R, int P) {
//		if(从L到R根本不可能弄出P份) {
//			return Integer.MAX_VALUE;
//		}
//		// 真的有可能的！
//		if(P == 1) {
//			return L == R ? 0 : (f(arr, K, L, R, K) + 最后一次大合并的代价);
//		}
//		int ans = Integer.MAX_VALUE;
//		// 真的有可能，P > 1
//		for(int i = L; i < R;i++) {
//			// L..i(1份)  i+1...R(P-1份)
//			int left = f(arr, K, L, i, 1);
//			if(left == Integer.MAX_VALUE) {
//				continue;
//			}
//			int right = f(arr, K, i+1,R,P-1);
//			if(right == Integer.MAX_VALUE) {
//				continue;
//			}
//			int all = left + right;
//			ans = Math.min(ans, all);
//		}
//		return ans;
//	}

    // 方法一：尝试
    public static int mergeStones0(int[] stones, int k) {
        int n = stones.length;
        // n个数到底能不能k个相邻数合并，最终合成1个数
        if ((n - 1) % (k - 1) > 0) return -1;
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) prefixSum[i + 1] = prefixSum[i] + stones[i];
        return process(stones, k, prefixSum, 0, n - 1, 1);
    }
    // 数组arr在l...r范围一定要弄出p份(p>=1)，返回最低代价
    private static int process(int[] arr, int k, int[] prefixSum, int l, int r, int p) {
        // base case: l...r只有1个数，若弄1份代价0，否则合成不了返回-1
        if (l == r) return p == 1 ? 0 : -1;
        // l...r不止一个数
        if (p == 1) {
            int next = process(arr, k, prefixSum, l, r, k);
            return next == -1 ? -1 : (next + prefixSum[r + 1] - prefixSum[l]);
        }
        int res = Integer.MAX_VALUE;
        // l...mid是第1份，剩下的是p-1份
        for (int mid = l; mid < r; mid += k - 1) {
            int next1 = process(arr, k, prefixSum, l, mid, 1);
            int next2 = process(arr, k, prefixSum, mid + 1, r, p - 1);
            if (next1 != -1 && next2 != -1) res = Math.min(res, next1 + next2);
        }
        return res;
    }

    // 方法二：尝试+傻缓存
    public static int mergeStones(int[] stones, int k) {
        int n = stones.length;
        // n个数到底能不能k个相邻数合并，最终合成1个数
        if ((n - 1) % (k - 1) > 0) return -1;
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) prefixSum[i + 1] = prefixSum[i] + stones[i];
        int[][][] cache = new int[n][n][k + 1];
        return process(stones, k, prefixSum, 0, n - 1, 1, cache);
    }
    // 数组arr在l...r范围一定要弄出p份(p>=1)，返回最低代价
    private static int process(int[] arr, int k, int[] prefixSum, int l, int r, int p, int[][][] cache) {
        if (cache[l][r][p] != 0) return cache[l][r][p];
        // base case: l...r只有1个数，若弄1份代价0，否则合成不了返回-1
        if (l == r) return cache[l][r][p] = (p == 1 ? 0 : -1);
        // l...r不止一个数
        if (p == 1) {
            int next = process(arr, k, prefixSum, l, r, k, cache);
            return cache[l][r][p] = (next == -1 ? -1 : (next + prefixSum[r + 1] - prefixSum[l]));
        }
        int res = Integer.MAX_VALUE;
        // l...mid是第1份，剩下的是p-1份
        for (int mid = l; mid < r; mid += k - 1) {
            int next1 = process(arr, k, prefixSum, l, mid, 1, cache);
            int next2 = process(arr, k, prefixSum, mid + 1, r, p - 1, cache);
            if (next1 != -1 && next2 != -1) res = Math.min(res, next1 + next2);
        }
        return cache[l][r][p] = res;
    }

    public static void main(String[] args) {
        int maxSize = 12;
        int maxValue = 100;
        System.out.println("Test begin");
        for (int testTime = 0; testTime < 100000; testTime++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int K = (int) (Math.random() * 7) + 2;
            int ans1 = mergeStones0(arr, K);
            int ans2 = mergeStones(arr, K);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
    }

    private static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (maxSize * Math.random()) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }
}
