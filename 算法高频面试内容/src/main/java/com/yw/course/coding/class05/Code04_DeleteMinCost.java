package com.yw.course.coding.class05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 给定两个字符串s1和s2，问s2最少删除多少字符可以成为s1的子串？
 * 比如 s1 = "abcde"，s2 = "axbc"
 * 返回 1
 *
 * @author yangwei
 */
public class Code04_DeleteMinCost {

	// 方法一：求出s2所有的子序列，然后按照长度排序，长度大的排在前面，然后考察哪个子序列字符串和s1的某个子串相等(KMP)，答案就出来了。
	// 分析：因为题目原本的样本数据中，有特别说明s2的长度很小。所以这么做也没有太大问题，也几乎不会超时。但是如果某一次考试给定的s2长度远大于s1，这么做就不合适了
	public static int minDeleteCost0(String s1, String s2) {
		List<String> subStrs = new ArrayList<>();
		process(s2.toCharArray(), 0, "", subStrs);
		subStrs.sort((o1, o2) -> o2.length() - o1.length());
		for (String s : subStrs) {
			// contains -> indexOf: 底层和KMP算法代价几乎一样，也可以用KMP代替
			if (s1.contains(s)) return s2.length() - s.length();
		}
		return s2.length();
	}
	private static void process(char[] cs, int idx, String path, List<String> subStrs) {
		if (idx == cs.length) {
			subStrs.add(path);
			return;
		}
		process(cs, idx + 1, path, subStrs);
		process(cs, idx + 1, path + cs[idx], subStrs);
	}

	// 方法二：求出s1的子串，然后考察每个子串和s2的编辑距离(假设编辑距离只有删除动作且删除一个字符的代价为1)
	// 如果s1的长度较小，s2长度较大，这个方法比较合适
	public static int minDeleteCost(String s1, String s2) {
		if (s1.length() == 0 || s2.length() == 0) return s2.length();
		int ans = Integer.MAX_VALUE;
		char[] cs2 = s2.toCharArray();
		for (int start = 0; start < s1.length(); start++) {
			for (int end = start + 1; end <= s1.length(); end++) {
				ans = Math.min(ans, minDistance(cs2, s1.substring(start, end).toCharArray()));
			}
		}
		return ans == Integer.MAX_VALUE ? s2.length() : ans;
	}
	// 求cs2到cs1sub的编辑距离，假设编辑距离只有删除动作且删除一个字符的代价为1
	private static int minDistance(char[] cs2, char[] cs1sub) {
		int row = cs2.length, col = cs1sub.length;
		// dp[i][j]: cs2从0到i这一段通过删除，变为cs1sub的最小代价
		// 1. cs2[0..i]变的过程中，不保留最后一个字符(cs2[i])
		// 那么就是通过cs2[0..i-1]变成cs1sub[0..j]之后，再最后删掉cs2[i]即可 -> dp[i][j] = dp[i-1][j] + 1
		// 2. cs2[0..i]变的过程中，想保留最后一个字符(cs2[i])，然后变成cs1sub[0..j]
		// 这要求cs2[i] == cs1sub[j]才有这种可能, 然后cs2[0..i-1]变成cs1sub[0..j-1]即可
		// 也就是cs2[i] == cs1sub[j]的条件下，dp[i][j] = dp[i-1][j-1]
		int[][] dp = new int[row][col];
		// 初始化dp表
		dp[0][0] = cs2[0] == cs1sub[0] ? 0 : Integer.MAX_VALUE;
		for (int j = 1; j < col; j++) dp[0][j] = Integer.MAX_VALUE;
		for (int i = 1; i < row; i++) dp[i][0] = (dp[i - 1][0] != Integer.MAX_VALUE || cs2[i] == cs1sub[0]) ? i : Integer.MAX_VALUE;
		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				dp[i][j] = Integer.MAX_VALUE;
				if (dp[i - 1][j] != Integer.MAX_VALUE) dp[i][j] = dp[i - 1][j] + 1;
				if (cs2[i] == cs1sub[j] && dp[i - 1][j - 1] != Integer.MAX_VALUE)
					dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
			}
		}
		return dp[row - 1][col - 1];
	}

	// 方法三：针对方法二的优化
	public static int minDeleteCostOptimal(String s1, String s2) {
		if (s1.length() == 0 || s2.length() == 0) return s2.length();
		char[] cs1 = s1.toCharArray(), cs2 = s2.toCharArray();
		int n = cs1.length, m = cs2.length;
		int[][] dp = new int[m][n];
		int ans = m;
		for (int start = 0; start < n; start++) {
			dp[0][start] = cs2[0] == cs1[start] ? 0 : m;
			for (int row = 1; row < m; row++) {
				dp[row][start] = (cs2[row] == cs1[start] || dp[row - 1][start] != m) ? row : m;
			}
			ans = Math.min(ans, dp[m - 1][start]);
			// 以上已经把start列填好，以下要把dp[...][start+1....N-1]的信息填好
			for (int end = start + 1; end < n && end - start < m; end++) {
				// 0... first-1 行 不用管
				int first = end - start;
				dp[first][end] = (cs2[first] == cs1[end] && dp[first - 1][end - 1] == 0) ? 0 : m;
				for (int row = first + 1; row < m; row++) {
					dp[row][end] = m;
					if (dp[row - 1][end] != m) dp[row][end] = dp[row - 1][end] + 1;
					if (dp[row - 1][end - 1] != m && cs2[row] == cs1[end])
						dp[row][end] = Math.min(dp[row][end], dp[row - 1][end - 1]);
				}
				ans = Math.min(ans, dp[m - 1][end]);
			}
		}
		return ans;
	}


	// 来自学生的做法，时间复杂度O(N * M平方)
	// 复杂度和方法三一样，但是思路截然不同
	public static int minCost4(String s1, String s2) {
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		HashMap<Character, ArrayList<Integer>> map1 = new HashMap<>();
		for (int i = 0; i < str1.length; i++) {
			ArrayList<Integer> list = map1.getOrDefault(str1[i], new ArrayList<>());
			list.add(i);
			map1.put(str1[i], list);
		}
		int ans = 0;
		// 假设删除后的str2必以i位置开头
		// 那么查找i位置在str1上一共有几个，并对str1上的每个位置开始遍历
		// 再次遍历str2一次，看存在对应str1中i后续连续子串可容纳的最长长度
		for (int i = 0; i < str2.length; i++) {
			if (map1.containsKey(str2[i])) {
				ArrayList<Integer> keyList = map1.get(str2[i]);
				for (Integer integer : keyList) {
					int cur1 = integer + 1;
					int cur2 = i + 1;
					int count = 1;
					for (int k = cur2; k < str2.length && cur1 < str1.length; k++) {
						if (str2[k] == str1[cur1]) {
							cur1++;
							count++;
						}
					}
					ans = Math.max(ans, count);
				}
			}
		}
		return s2.length() - ans;
	}

	// x字符串只通过删除的方式，变到y字符串
	// 返回至少要删几个字符
	// 如果变不成，返回Integer.Max
	public static int onlyDelete(char[] x, char[] y) {
		if (x.length < y.length) return Integer.MAX_VALUE;
		int n = x.length, m = y.length;
		int[][] dp = new int[n + 1][m + 1];
		for (int[] d : dp) Arrays.fill(d, Integer.MAX_VALUE);
		dp[0][0] = 0;
		// dp[i][j]表示前缀长度
		for (int i = 1; i <= n; i++) dp[i][0] = i;
		for (int xlen = 1; xlen <= n; xlen++) {
			for (int ylen = 1; ylen <= Math.min(m, xlen); ylen++) {
				if (dp[xlen - 1][ylen] != Integer.MAX_VALUE) {
					dp[xlen][ylen] = dp[xlen - 1][ylen] + 1;
				}
				if (x[xlen - 1] == y[ylen - 1] && dp[xlen - 1][ylen - 1] != Integer.MAX_VALUE) {
					dp[xlen][ylen] = Math.min(dp[xlen][ylen], dp[xlen - 1][ylen - 1]);
				}
			}
		}
		return dp[n][m];
	}

	public static void main(String[] args) {

		char[] x = { 'a', 'b', 'c', 'd' };
		char[] y = { 'a', 'd' };

		System.out.println(onlyDelete(x, y));

		int str1Len = 20;
		int str2Len = 10;
		int v = 5;
		int testTime = 10000;
		boolean pass = true;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			String str1 = generateRandomString(str1Len, v);
			String str2 = generateRandomString(str2Len, v);
			int ans1 = minDeleteCost0(str1, str2);
			int ans2 = minDeleteCost(str1, str2);
			int ans3 = minDeleteCostOptimal(str1, str2);
			int ans4 = minCost4(str1, str2);
			if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
				pass = false;
				System.out.println(str1);
				System.out.println(str2);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println(ans4);
				break;
			}
		}
		System.out.println("test pass : " + pass);
	}
	private static String generateRandomString(int l, int v) {
		int len = (int) (Math.random() * l);
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			str[i] = (char) ('a' + (int) (Math.random() * v));
		}
		return String.valueOf(str);
	}
}
