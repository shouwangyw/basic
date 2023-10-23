package com.yw.advance.course.class20;

/**
 * 测试链接：https://leetcode.cn/problems/longest-palindromic-subsequence/
 *
 * @author yangwei
 */
public class Code01_PalindromeSubsequence {

    // 方法一：尝试暴力递归
    public int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0) return 0;
        return process(s.toCharArray(), 0, s.length() - 1);
    }
    private static int process(char[] cs, int l, int r) {
        // 1个字符，自己就是回文
        if (l == r) return 1;
        // 2个字符，如果 cs[l] == cs[r] 回文长度就是2
        if (l == r - 1) return cs[l] == cs[r] ? 2 : 1;
        // a) 不要l，不要r
        int p1 = process(cs, l + 1, r - 1);
        // b) 要l，不要r
        int p2 = process(cs, l, r - 1);
        // a) 不要l，要r
        int p3 = process(cs, l + 1, r);
        // a) 要l，要r
        int p4 = cs[l] == cs[r] ? (2 + process(cs, l + 1, r - 1)) : 0;
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    // 方法二：基于暴力递归修改成动态规划
    public int longestPalindromeSubseq2(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] cs = s.toCharArray();
        int n = cs.length;
        int[][] dp = new int[n][n];
        // 先初始化右下角1，方便同时初始化接下来的两条对角线
        dp[n - 1][n - 1] = 1;
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = cs[i] == cs[i + 1] ? 2 : 1;
        }
        // 剩下的位置，左下角半边区域不考虑，右上角区域：从下往上、从左往右
        // n - 1、n - 2已经初始化了，从 n - 3 开始
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                // 左下角一定不比当前值大，所以不考虑左下角 dp[l + 1][r - 1]
                // 先从左边、下边取最大值
                dp[l][r] = Math.max(dp[l][r - 1], dp[l + 1][r]);
                // cs[l] == cs[r] 时，才考虑左下角
                if (cs[l] == cs[r]) {
                    dp[l][r] = Math.max(dp[l][r], 2 + dp[l + 1][r - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    public static int longestPalindromeSubseq0(String s) {
        if (s == null || s.length() == 0) return 0;
        if (s.length() == 1) return 1;
        char[] cs1 = s.toCharArray();
        char[] cs2 = reverse(cs1);
        return longestCommonSubSequence(cs1, cs2);
    }

    private static char[] reverse(char[] cs) {
        int n = cs.length;
        char[] ret = new char[n];
        for (int i = 0; i < n; i++) {
            ret[n - i - 1] = cs[i];
        }
        return ret;
    }
    private static int longestCommonSubSequence(char[] cs1, char[] cs2) {
        int m = cs1.length, n = cs2.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (cs1[i - 1] == cs2[j - 1])
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
            }
        }
        return dp[m][n];
    }
}
