package com.yw.course.coding.class05;

/**
 * @author yangwei
 */
public class Code03_EditCost {


	// 方法一：样本对应模型
	public static int minDistance(String s1, String s2, int ic, int dc, int rc) {
		if (s1 == null || s2 == null) return 0;
		char[] cs1 = s1.toCharArray(), cs2 = s2.toCharArray();
		int n = cs1.length, m = cs2.length;
		// dp[i][j]: 表示前缀长度为i的字符串s1，转换成前缀长度为j的字符串s2所需的最少代价
		int[][] dp = new int[n + 1][m + 1];
		// 初始化dp表: dp[0][0] = 0 即前缀长度都是0，代价自然是0
		// 第0行，即前缀长度为0的字符串s1，转换成前缀长度为j的字符串s2，只能插入字符
		for (int j = 1; j <= m; j++) dp[0][j] = j * ic;
		// 第0列，即前缀长度为i的字符串s1，转换成前缀长度为0的字符串s2，只能删除字符
		for (int i = 1; i <= n; i++) dp[i][0] = i * dc;
		// 普遍位置
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				// 分析可能情况:
				// ①② [i][j]可来自[i - 1][j - 1]，讨论当前结尾字符是否相等，相等代价0，否则需要交换代价rc
				dp[i][j] = dp[i - 1][j - 1] + (cs1[i - 1] == cs2[j - 1] ? 0 : rc);
				// ③④ [i][j]可来自[i][j - 1]、[i - 1][j]，PK取最小值
				dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
				dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
			}
		}
		return dp[n][m];
	}

	// 方法二：数组空间压缩
	public static int minDistanceOptimal(String s1, String s2, int ic, int dc, int rc) {
		if (s1 == null || s2 == null) return 0;
		int n = s1.length(), m = s2.length();
		// 区分长串和短串
		char[] ls = n >= m ? s1.toCharArray() : s2.toCharArray();
		char[] ss = n < m ? s1.toCharArray() : s2.toCharArray();
		if (n < m) { int tmp = ic; ic = dc; dc = tmp; }
		// 以短串长度定义dp
		int[] dp = new int[ss.length + 1];
		for (int i = 1; i <= ss.length; i++) dp[i] = i * ic;
		for (int i = 1; i <= ls.length; i++) {
			int pre = dp[0];
			dp[0] = i * dc;
			for (int j = 1; j <= ss.length; j++) {
				int tmp = dp[j];
				dp[j] = pre + (ls[i - 1] == ss[j - 1] ? 0 : rc);
				dp[j] = Math.min(dp[j], dp[j - 1] + ic);
				dp[j] = Math.min(dp[j], tmp + dc);
				pre = tmp;
			}
		}
		return dp[ss.length];
	}

	public static void main(String[] args) {
		String str1 = "ab12cd3";
		String str2 = "abcdf";
		System.out.println(minDistance(str1, str2, 5, 3, 2));
		System.out.println(minDistanceOptimal(str1, str2, 5, 3, 2));

		str1 = "abcdf";
		str2 = "ab12cd3";
		System.out.println(minDistance(str1, str2, 3, 2, 4));
		System.out.println(minDistanceOptimal(str1, str2, 3, 2, 4));

		str1 = "";
		str2 = "ab12cd3";
		System.out.println(minDistance(str1, str2, 1, 7, 5));
		System.out.println(minDistanceOptimal(str1, str2, 1, 7, 5));

		str1 = "abcdf";
		str2 = "";
		System.out.println(minDistance(str1, str2, 2, 9, 8));
		System.out.println(minDistanceOptimal(str1, str2, 2, 9, 8));
	}
}
