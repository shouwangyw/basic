package com.yw.course.coding.class07;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Code06_SplitStringMaxValue {

	// 方法一：暴力尝试
	public static int maxScore0(String s, int k, String[] ss, int[] scores) {
		if (s == null || s.length() == 0) return 0;
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < ss.length; i++) map.put(ss[i], scores[i]);
		return process0(s, 0, k, map);
	}
	private static int process0(String s, int i, int k, Map<String, Integer> map) {
		if (k < 0) return -1;
		if (i == s.length()) return k == 0 ? 0 : -1;
		int score = -1;
		for (int end = i; end < s.length(); end++) {
			String first = s.substring(i, end + 1);
			int next = map.containsKey(first) ? process0(s, end + 1, k - 1, map) : -1;
			if (next != -1) score = Math.max(score, map.get(first) + next);
		}
		return score;
	}

	// 方法二：动态规划解
	public static int maxScoreDp(String s, int k, String[] ss, int[] scores) {
		if (s == null || s.length() == 0) return 0;
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < ss.length; i++) map.put(ss[i], scores[i]);
		int n = s.length();
		int[][] dp = new int[n + 1][k + 1];
		for (int[] x : dp) Arrays.fill(x, -1);
		dp[n][0] = 0;
		for (int i = n - 1; i >= 0; i--) {
			for (int k0 = 0; k0 <= k; k0++) {
				int score = -1;
				for (int end = i; end < s.length(); end++) {
					String first = s.substring(i, end + 1);
					int next = -1;
					if (k0 > 0 && map.containsKey(first)) {
						next = dp[end + 1][k0 - 1];
					}
					if (next != -1) score = Math.max(score, map.get(first) + next);
				}
				dp[i][k0] = score;
			}
		}
		return dp[0][k];
	}

	// 方法三：动态规划解 + 前缀树优化
	public static int maxScore(String s, int k, String[] ss, int[] scores) {
		CharTrie trie = new CharTrie();
		for (int i = 0; i < ss.length; i++) trie.add(ss[i], scores[i]);
		int n = s.length();
		int[][] dp = new int[n + 1][k + 1];
		for (int[] x : dp) Arrays.fill(x, -1);
		dp[n][0] = 0;
		char[] cs = s.toCharArray();
		for (int i = n - 1; i >= 0; i--) {
			for (int k0 = 0; k0 <= k; k0++) {
				int score = -1;
				Node cur = trie.root;
				for (int end = i; end < s.length(); end++) {
					int path = cs[end] - 'a';
					if (cur.nexts[path] == null) break;
					cur = cur.nexts[path];
					int next = -1;
					if (k0 > 0 && cur.score != -1) {
						next = dp[end + 1][k0 - 1];
					}
					if (next != -1) score = Math.max(score, cur.score + next);
				}
				dp[i][k0] = score;
			}
		}
		return dp[0][k];
	}
	private static class Node {
		private Node[] nexts;
		private int score;
		public Node() {
			this.nexts = new Node[26];
			this.score = -1;
		}
	}
	private static class CharTrie {
		private Node root;
		public CharTrie() {
			this.root = new Node();
		}
		public void add(String s, int score) {
			Node cur = root;
			for (char c : s.toCharArray()) {
				int path = c - 'a';
				if (cur.nexts[path] == null) cur.nexts[path] = new Node();
				cur = cur.nexts[path];
			}
			cur.score = score;
		}
	}

	public static void main(String[] args) {
		String str = "abcdefg";
		int K = 3;
		String[] parts = { "abc", "def", "g", "ab", "cd", "efg", "defg" };
		int[] record = { 1, 1, 1, 3, 3, 3, 2 };
		System.out.println(maxScore0(str, K, parts, record));
		System.out.println(maxScoreDp(str, K, parts, record));
		System.out.println(maxScore(str, K, parts, record));
	}
}
