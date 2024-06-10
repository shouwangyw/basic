package com.yw.course.coding.class31;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * lintcode也有测试，数据量比leetcode大很多 : https://www.lintcode.com/problem/107/
 * @author yangwei
 */
public class Problem_0139_WordBreak {

	// 方法一：
	public boolean wordBreak(String s, List<String> wordDict) {
		Set<String> set = new HashSet<>(wordDict);
		int n = s.length();
		// dp[i] 表示字符串长度为i的前缀串是否可以被拼接而成
		boolean[] dp = new boolean[n + 1];
		dp[0] = true; // 长度0即空字符串认为是可以的
		// 从1到n递推
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j < i; j++) {
				// [0...j-1]可以，并且[j...i-1]
				if (dp[j] && set.contains(s.substring(j, i)))
					dp[i] = true;
			}
		}
		return dp[n];
	}

	// 方法二：
	// 定义前缀树节点
	private static class Node {
		private Node[] nexts;
		private boolean end;
		public Node() {
			this.nexts = new Node[26];
			this.end = false;
		}
	}
	public boolean wordBreak1(String s, List<String> wordDict) {
		Node root = new Node();
		for (String word : wordDict) {
			char[] cs = word.toCharArray();
			Node node = root;
			for (int i = 0; i < cs.length; i++) {
				int path = cs[i] - 'a';
				if (node.nexts[path] == null) node.nexts[path] = new Node();
				node = node.nexts[path];
			}
			node.end = true;
		}
		char[] cs = s.toCharArray();
		int n = cs.length;
		// dp[i]表示从i及其往后子串，是否能被单词表拼接而成
		boolean[] dp = new boolean[n + 1];
		dp[n] = true;
		for (int i = n - 1; i >= 0; i--) {
			Node cur = root;
			for (int end = i; end < n; end++) {
				cur = cur.nexts[cs[end] - 'a'];
				if (cur == null) break;
				if (cur.end) dp[i] |= dp[end + 1];
				if (dp[i]) break;
			}
		}
		return dp[0];
	}

	public static int wordBreak2(String s, List<String> wordDict) {
		Node root = new Node();
		for (String str : wordDict) {
			char[] chs = str.toCharArray();
			Node node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			node.end = true;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[] dp = new int[N + 1];
		dp[N] = 1;
		for (int i = N - 1; i >= 0; i--) {
			Node cur = root;
			for (int end = i; end < N; end++) {
				cur = cur.nexts[str[end] - 'a'];
				if (cur == null) {
					break;
				}
				if (cur.end) {
					dp[i] += dp[end + 1];
				}
			}
		}
		return dp[0];
	}

}
