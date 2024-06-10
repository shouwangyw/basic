package com.yw.course.coding.class31;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class Problem_0140_WordBreakII {

	public List<String> wordBreak(String s, List<String> wordDict) {
		// 将单词字典转为前缀树
		Trie trie = new Trie();
		for (String word : wordDict) trie.insert(word);
		// 生成dp表：记录字符串s的每个位置及其往后的子串，是否能被单词表拼接而成
		char[] cs = s.toCharArray();
		boolean[] dp = getDp(cs, trie.root);
		List<String> ans = new ArrayList<>();
		process(cs, 0, trie.root, dp, new ArrayList<>(), ans);
		return ans;
	}
	// 定义前缀树节点
	private static class Node {
		private Node[] nexts;
		private boolean end;
		private String word;
		public Node() {
			this.nexts = new Node[26];
			this.end = false;
			this.word = null;
		}
	}
	// 定义前缀树
	private static class Trie {
		private Node root;
		public Trie() {
			this.root = new Node();
		}
		// 添加单词
		public void insert(String word) {
			if (word == null || word.length() == 0) return;
			Node node = root;
			for (char c : word.toCharArray()) {
				int path = c - 'a';
				if (node.nexts[path] == null) node.nexts[path] = new Node();
				node = node.nexts[path];
			}
			node.end = true;
			node.word = word;
		}
	}
	private static boolean[] getDp(char[] cs, Node root) {
		int n = cs.length;
		boolean[] dp = new boolean[n + 1];
		dp[n] = true;
		for (int i = n - 1; i >= 0; i--) {
			Node cur = root;
			for (int end = i; end < n; end++) {
				int path = cs[end] - 'a';
				if (cur.nexts[path] == null) break;
				cur = cur.nexts[path];
				if (cur.end && dp[end + 1]) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp;
	}
	private static void process(char[] cs, int idx, Node root, boolean[] dp, List<String> words, List<String> ans) {
		if (idx == cs.length) ans.add(String.join(" ", words));
		else {
			Node cur = root;
			for (int end = idx; end < cs.length; end++) {
				int path = cs[end] - 'a';
				if (cur.nexts[path] == null) break;
				cur = cur.nexts[path];
				if (cur.end && dp[end + 1]) {
					words.add(cur.word);
					process(cs, end + 1, root, dp, words, ans);
					words.remove(words.size() - 1);
				}
			}
		}
	}

}
