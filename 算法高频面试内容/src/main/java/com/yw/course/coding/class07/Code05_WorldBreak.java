package com.yw.course.coding.class07;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 假设所有字符都是小写字母. 大字符串是str. arr是去重的单词表, 每个单词都不是空字符串且可以使用任意次
 * 使用arr中的单词有多少种拼接str的方式. 返回方法数
 * @author yangwei
 */
public class Code05_WorldBreak {

	// 方法一：暴力尝试（从左往右的尝试模型），时间复杂度O(N^3)
	public static int ways0(String s, String[] ss) {
		return process0(s, 0, new HashSet<>(Arrays.asList(ss)));
	}
	// 所有的可分解字符串，都已经放在了set中
	private static int process0(String s, int i, Set<String> set) {
		// base case: 没字符串需要分解了
		if (i == s.length()) return 1;
		int ways = 0;
		// 枚举[i...end]每一种前缀串
		for (int end = i; end < s.length(); end++) {
			if (set.contains(s.substring(i, end + 1))) {
				ways += process0(s, end + 1, set);
			}
		}
		return ways;
	}

	// 方法二：根据尝试改动态规划，dp[i]表示i位置开始及其之后的字符有多少种拼接方法数
	public static int waysDp(String s, String[] ss) {
		Set<String> set = new HashSet<>(Arrays.asList(ss));
		int n = s.length();
		int[] dp = new int[n + 1];
		dp[n] = 1;
		for (int i = n - 1; i >= 0; i--) {
			for (int end = i; end < n; end++) {
				if (set.contains(s.substring(i, end + 1))) {
					dp[i] += dp[end + 1];
				}
			}
		}
		return dp[0];
	}

	// 方法三：利用前缀树优化子串拼接，时间复杂度O(M + N^2)，M是字符串数组长度
	public static int ways(String s, String[] ss) {
		// ss构建成前缀树
		CharTrie trie = new CharTrie();
		for (String x : ss) trie.add(x);
		return process(s.toCharArray(), 0, trie.root);
	}
	private static int process(char[] cs, int i, Node root) {
		if (i == cs.length) return 1;
		int ways = 0;
		Node cur = root;
		for (int end = i; end < cs.length; end++) {
			int path = cs[end] - 'a';
			// 如果没路直接可以结束
			if (cur.nexts[path] == null) break;
			cur = cur.nexts[path];
			if (cur.end) ways += process(cs, end + 1, root);
		}
		return ways;
	}
	private static class Node {
		private Node[] nexts;
		private boolean end;
		public Node() {
			this.nexts = new Node[26];
			this.end = false;
		}
	}
	private static class CharTrie {
		private Node root;
		public CharTrie() {
			this.root = new Node();
		}
		public void add(String s) {
			Node cur = root;
			for (char c : s.toCharArray()) {
				int path = c - 'a';
				if (cur.nexts[path] == null) cur.nexts[path] = new Node();
				cur = cur.nexts[path];
			}
			cur.end = true;
		}
	}

	// 方法四：前缀树+动态规划
	public static int waysOptimal(String s, String[] ss) {
		CharTrie trie = new CharTrie();
		for (String x : ss) trie.add(x);
		char[] cs = s.toCharArray();
		int n = cs.length;
		int[] dp = new int[n + 1];
		dp[n] = 1;
		for (int i = n - 1; i >= 0; i--) {
			Node cur = trie.root;
			for (int end = i; end < n; end++) {
				int path = cs[end] - 'a';
				if (cur.nexts[path] == null) break;
				cur = cur.nexts[path];
				if (cur.end) dp[i] += dp[end + 1];
			}
		}
		return dp[0];
	}

	public static void main(String[] args) {
		char[] candidates = { 'a', 'b' };
		int num = 20;
		int len = 4;
		int joint = 5;
		int testTimes = 30000;
		boolean testResult = true;
		for (int i = 0; i < testTimes; i++) {
			RandomSample sample = generateRandomSample(candidates, num, len, joint);
			int ans1 = ways0(sample.str, sample.arr);
			int ans2 = waysDp(sample.str, sample.arr);
			int ans3 = ways(sample.str, sample.arr);
			int ans4 = waysOptimal(sample.str, sample.arr);
			if (ans1 != ans2 || ans3 != ans4 || ans2 != ans4) {
				testResult = false;
			}
		}
		System.out.println(testTimes + "次随机测试是否通过：" + testResult);
	}
	private static class RandomSample {
		public String str;
		public String[] arr;
		public RandomSample(String s, String[] a) {
			str = s;
			arr = a;
		}
	}
	// 随机样本产生器
	private static RandomSample generateRandomSample(char[] candidates, int num, int len, int joint) {
		String[] seeds = randomSeeds(candidates, num, len);
		HashSet<String> set = new HashSet<>();
		for (String str : seeds) {
			set.add(str);
		}
		String[] arr = new String[set.size()];
		int index = 0;
		for (String str : set) {
			arr[index++] = str;
		}
		StringBuilder all = new StringBuilder();
		for (int i = 0; i < joint; i++) {
			all.append(arr[(int) (Math.random() * arr.length)]);
		}
		return new RandomSample(all.toString(), arr);
	}
	private static String[] randomSeeds(char[] candidates, int num, int len) {
		String[] arr = new String[(int) (Math.random() * num) + 1];
		for (int i = 0; i < arr.length; i++) {
			char[] str = new char[(int) (Math.random() * len) + 1];
			for (int j = 0; j < str.length; j++) {
				str[j] = candidates[(int) (Math.random() * candidates.length)];
			}
			arr[i] = String.valueOf(str);
		}
		return arr;
	}

}
