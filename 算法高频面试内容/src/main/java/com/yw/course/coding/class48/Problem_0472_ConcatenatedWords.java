package com.yw.course.coding.class48;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author yangwei
 */
public class Problem_0472_ConcatenatedWords {

	// 方法一：前缀树
	public List<String> findAllConcatenatedWordsInADict(String[] words) {
		List<String> ans = new ArrayList<>();
		if (words == null || words.length < 3) return ans;
		// 字符串数量>=3个
		Arrays.sort(words, Comparator.comparingInt(String::length));
		TrieNode root = new TrieNode();
		for (String word : words) {
			char[] cs = word.toCharArray();
			// 若能分解，则加入到答案中
			if (cs.length > 0 && split(cs, root, 0)) ans.add(word);
				// 否则，加入到前缀树上
			else insert(root, cs);
		}
		return ans;
	}
	private static boolean split(char[] cs, TrieNode root, int idx) {
		if (idx == cs.length) return true;
		TrieNode cur = root;
		// 枚举[idx, cs.length)，是否可以分解
		for (int end = idx; end < cs.length; end++) {
			int path = cs[end] - 'a';
			if (cur.nexts[path] == null) break;
			cur = cur.nexts[path];
			if (cur.end && split(cs, root, end + 1)) return true;
		}
		return false;
	}
	private static void insert(TrieNode root, char[] cs) {
		for (char c : cs) {
			int path = c - 'a';
			if (root.nexts[path] == null) root.nexts[path] = new TrieNode();
			root = root.nexts[path];
		}
		root.end = true;
	}
	// 前缀树
	private static class TrieNode {
		private boolean end;
		private TrieNode[] nexts;
		public TrieNode() {
			this.end = false;
			this.nexts = new TrieNode[26];
		}
	}

	// 方法二：前缀树优化 + 傻缓存
	public List<String> findAllConcatenatedWordsInADictByCache(String[] words) {
		List<String> ans = new ArrayList<>();
		if (words == null || words.length < 3) return ans;
		// 字符串数量>=3个
		Arrays.sort(words, Comparator.comparingInt(String::length));
		TrieNode root = new TrieNode();
		for (String word : words) {
			char[] cs = word.toCharArray();
			Boolean[] cache = new Boolean[31];
			// 若能分解，则加入到答案中
			if (cs.length > 0 && split(cs, root, 0, cache)) ans.add(word);
				// 否则，加入到前缀树上
			else insert(root, cs);
		}
		return ans;
	}
	private static boolean split(char[] cs, TrieNode root, int idx, Boolean[] cache) {
		if (cache[idx] != null) return cache[idx];
		if (idx == cs.length) return cache[idx] = true;
		TrieNode cur = root;
		// 枚举[idx, cs.length)，是否可以分解
		for (int end = idx; end < cs.length; end++) {
			int path = cs[end] - 'a';
			if (cur.nexts[path] == null) break;
			cur = cur.nexts[path];
			if (cur.end && split(cs, root, end + 1, cache)) return cache[idx] = true;
		}
		return cache[idx] = false;
	}
}
