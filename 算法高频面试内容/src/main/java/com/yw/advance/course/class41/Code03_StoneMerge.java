package com.yw.advance.course.class41;

/**
 * 四边形不等式：合并石子问题
 *
 * @author yangwei
 */
public class Code03_StoneMerge {

    // 方法一：暴力递归
    public static int minStoneMerge0(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int[] sum = getPrefixSum(arr);
        return process(sum, 0, arr.length - 1);
    }
    // 返回 l~r 范围进行相邻石子合并得分的最小值
    private static int process(int[] sum, int l, int r) {
        if (l == r) return 0;
        int next = Integer.MAX_VALUE;
        for (int leftEnd = l; leftEnd < r; leftEnd++) {
            next = Math.min(next, process(sum, l, leftEnd) + process(sum, leftEnd + 1, r));
        }
        return next + rangeSum(sum, l, r);
    }

    // 方法二：动态规划，存在枚举划分位置，时间复杂度 O(N^3)
    public static int minStoneMerge(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int n = arr.length;
        int[] sum = getPrefixSum(arr);
        int[][] dp = new int[n][n];
        // 左下半边区域无效，对角线 dp[i][i] = 0;
        // 整体从下至上(n-2 ~ 0)，从左至右(l ~ n) 填写dp表
        for (int l = n - 2; l >= 0; l--) {
            for (int r = l + 1; r < n; r++) {
                int next = Integer.MAX_VALUE;
                // 枚举每一个划分位置
                for (int leftEnd = l; leftEnd < r; leftEnd++) {
                    next = Math.min(next, dp[l][leftEnd] + dp[leftEnd + 1][r]);
                }
                dp[l][r] = next + rangeSum(sum, l, r);
            }
        }
        return dp[0][n - 1];
    }

    // 方法三：四边形不等式优化-动态规划，时间复杂度 O(N^2)
    public static int minStoneMergeOptimal(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int n = arr.length;
        int[] sum = getPrefixSum(arr);
        // best: 记录取得最优划分点的位置
        int[][] dp = new int[n][n], best = new int[n][n];
        // 填写倒数第2条斜对角线的dp表和best表
        for (int i = 0; i < n - 1; i++) {
            best[i][i + 1] = i;
            dp[i][i + 1] = rangeSum(sum, i, i + 1);
        }
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                int next = Integer.MAX_VALUE, choose = -1;
                // 针对方法二`枚举每一个划分位置`进行优化: 利用best数组限制leftEnd的边界(左边, 下边)
                for (int leftEnd = best[l][r - 1]; leftEnd <= best[l + 1][r]; leftEnd++) {
                    int cur = dp[l][leftEnd] + dp[leftEnd + 1][r];
                    if (cur <= next) {
                        next = cur;
                        // 记下此时取得最优的位置
                        choose = leftEnd;
                    }
                }
                best[l][r] = choose;
                dp[l][r] = next + rangeSum(sum, l, r);
            }
        }
        return dp[0][n - 1];
    }

    // 前缀和数组
    private static int[] getPrefixSum(int[] arr) {
        int n = arr.length;
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) sum[i + 1] = sum[i] + arr[i];
        return sum;
    }
    // 区间和
    private static int rangeSum(int[] sum, int l, int r) {
        return sum[r + 1] - sum[l];
    }

    public static void main(String[] args) {
        int N = 15;
        int maxValue = 100;
        int testTime = 1000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, maxValue);
            int ans1 = minStoneMerge0(arr);
            int ans2 = minStoneMerge(arr);
            int ans3 = minStoneMergeOptimal(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }

    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }
}
