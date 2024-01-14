package com.yw.course.coding.class04;

/**
 * 测试链接 : https://leetcode.cn/problems/interleaving-string/
 * @author yangwei
 */
public class Code07_InterleavingString {

	public boolean isInterleave(String s1, String s2, String s3) {
		if (s1 == null || s2 == null || s3 == null) return false;
		char[] cs1 = s1.toCharArray(), cs2 = s2.toCharArray(), cs3 = s3.toCharArray();
		int n1 = cs1.length, n2 = cs2.length, n3 = cs3.length;
		if (n1 + n2 != n3) return false;
		boolean[][] dp = new boolean[n1 + 1][n2 + 1];
		dp[0][0] = true;
		// 初始化第0列，即s3前n1个字符都来自s1
		for (int i = 1; i <= n1; i++) {
			// 字符相等则标true，否则都是false
			if (cs1[i - 1] != cs3[i - 1]) break;
			dp[i][0] = true;
		}
		// 初始化第0行，即s3前n2个字符都来自s2
		for (int i = 1; i <= n2; i++) {
			// 字符相等则标true，否则都是false
			if (cs2[i - 1] != cs3[i - 1]) break;
			dp[0][i] = true;
		}
		// 普遍位置
		for (int i = 1; i <= n1; i++) {
			for (int j = 1; j <= n2; j++) {
				if (cs1[i - 1] == cs3[i + j - 1] && dp[i - 1][j]
						|| cs2[j - 1] == cs3[i + j - 1] && dp[i][j - 1])
					dp[i][j] = true;
			}
		}
		return dp[n1][n2];
	}

}
