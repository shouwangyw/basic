package com.yw.course.coding.class17;

import java.util.Arrays;

/**
 * 测试链接 : https://leetcode.cn/problems/21dk04/
 * @author yangwei
 */
public class Code04_DistinctSubseq {

    // 方法一：暴力递归尝试，会超时
    public int numDistinct(String s, String t) {
        return process(s.toCharArray(), t.toCharArray(), s.length(), t.length());
    }
    // 返回cs[0...]长度i的子串中，所有子序列中有多少子序列字面值等于 ct[0...]长度为j的前缀串
    private int process(char[] cs, char[] ct, int i, int j) {
        // base case: j==0 表示t是空串，s中空序列也认为是一种
        if (j == 0) return 1;
        if (i == 0) return 0;
        // 1. 不考虑使用字符cs[i-1]构成子序列
        int ans = process(cs, ct, i - 1, j);
        // 2. 考虑使用字符cs[i-1]构成子序列，则必须要求 cs[i-1] == ct[j-1]
        if (cs[i - 1] == ct[j - 1]) ans += process(cs, ct, i - 1, j - 1);
        return ans;
    }

	// 方法二：尝试+傻缓存
    public int numDistinctByCache(String s, String t) {
        int[][] cache = new int[s.length() + 1][t.length() + 1];
        for (int[] x : cache) Arrays.fill(x, -1);
        return process(s.toCharArray(), t.toCharArray(), s.length(), t.length(), cache);
    }
    // 返回cs[0...]长度i的子串中，所有子序列中有多少子序列字面值等于 ct[0...]长度为j的前缀串
    private int process(char[] cs, char[] ct, int i, int j, int[][] cache) {
        if (cache[i][j] != -1) return cache[i][j];
        // base case: j==0 表示t是空串，s中空序列也认为是一种
        if (j == 0) return cache[i][j] = 1;
        if (i == 0) return cache[i][j] = 0;
        // 1. 不考虑使用字符cs[i-1]构成子序列
        int ans = process(cs, ct, i - 1, j, cache);
        // 2. 考虑使用字符cs[i-1]构成子序列，则必须要求 cs[i-1] == ct[j-1]
        if (cs[i - 1] == ct[j - 1]) ans += process(cs, ct, i - 1, j - 1, cache);
        return cache[i][j] = ans;
    }

    // 方法三：改成动态规划
	// S[...i]的所有子序列中，包含多少个字面值等于T[...j]这个字符串的子序列
	// 记为dp[i][j]
	// 可能性1）S[...i]的所有子序列中，都不以s[i]结尾，则dp[i][j]肯定包含dp[i-1][j]
	// 可能性2）S[...i]的所有子序列中，都必须以s[i]结尾，
	// 这要求S[i] == T[j]，则dp[i][j]包含dp[i-1][j-1]
    public int numDistinctByDp(String s, String t) {
        char[] cs = s.toCharArray(), ct = t.toCharArray();
        int n = cs.length, m = ct.length;
        // dp[i][j]: s只拿前i个字符做子序列，有多少个子序列，字面值等于t的前j个字符的前缀串
        int[][] dp = new int[n + 1][m + 1];
        // 初始化 dp[...][0] = 1, dp[0][...] = 0
        // for (int j = 0; j <= m; j++) dp[0][j] = 0;
        for (int i = 0; i <= n; i++) dp[i][0] = 1;
        // 普遍位置
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = dp[i - 1][j] + (cs[i - 1] == ct[j - 1] ? dp[i - 1][j - 1] : 0);
            }
        }
        return dp[n][m];
    }

    public static int numDistinctDp(String s, String t) {
        char[] cs = s.toCharArray(), ct = t.toCharArray();
        int n = cs.length, m = ct.length;
        // dp[i][j]: cs[0...i]有多少子序列中出现了ct[0...j]的前缀串
        int[][] dp = new int[n][m];
        // 初始化: dp[...][0]第0列，即ct只拿一个字符ct[0]时
        dp[0][0] = cs[0] == ct[0] ? 1 : 0;
        for (int i = 1; i < n; i++) dp[i][0] = dp[i - 1][0] + (cs[i] == ct[0] ? 1 : 0);
        // 普遍位置
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= Math.min(i, m - 1); j++) {
                dp[i][j] = dp[i - 1][j] + (cs[i] == ct[j] ? dp[i - 1][j - 1] : 0);
            }
        }
        return dp[n - 1][m - 1];
    }

    // 方法四：滚动数组优化
    public static int numDistinctOptimal(String s, String t) {
        char[] cs = s.toCharArray(), ct = t.toCharArray();
        int n = cs.length, m = ct.length;
        int[] dp = new int[m + 1];
        dp[0] = 1;
        for (int j = 1; j <= m; j++) dp[j] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = m; j >= 1; j--)
                dp[j] += cs[i - 1] == ct[j - 1] ? dp[j - 1] : 0;
        }
        return dp[m];
    }

	public static void main(String[] args) {
		String S = "1212311112121132";
		String T = "13";

		System.out.println(numDistinctOptimal(S, T));
		System.out.println(numDistinctDp(S, T));
	}
}
