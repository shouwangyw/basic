package com.yw.course.advance.class32;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author yangwei
 */
public class Code04_AC2 {

	// 前缀树的节点
	public static class Node {
		// 对于一个node，若end为空，则不是结尾；若end不为空，表示这个点是某个字符串的结尾，end的值就是这个字符串
		private String end;
		// 当end不为空时，endUse才有意义，表示这个字符串之前有没有加入过答案
		private boolean endUse;
		private Node fail;
		public Node[] nexts;

		public Node() {
			this.endUse = false;
			this.end = null;
			this.fail = null;
			this.nexts = new Node[26];
		}
	}
	// AC自动机
	public static class ACAutomation {
		private Node root;
		public ACAutomation() {
			root = new Node();
		}
		// 依次插入若干字符串，生成前缀树
		public void insert(String s) {
			char[] cs = s.toCharArray();
			Node cur = root;
			for (int i = 0; i < cs.length; i++) {
				int idx = cs[i] - 'a';
				// 没路就新建，有路就往下走
				if (cur.nexts[idx] == null) cur.nexts[idx] = new Node();
				cur = cur.nexts[idx];
			}
			cur.end = s;
		}
		// 设置fail指针，构建自动机
		public void build() {
			// 宽度优先遍历
			Queue<Node> queue = new LinkedList<>();
			queue.add(root);
			Node cur, cfail; // 当前节点、当前节点的fail指针
			while (!queue.isEmpty()) {
				cur = queue.poll();		// cur：某个父亲
				for (int i = 0; i < 26; i++) { // 所有的路
					if (cur.nexts[i] == null) continue;
					// 如果有i号儿子，必须把i号儿子的fail指针设置好！
					cur.nexts[i].fail = root;
					cfail = cur.fail;
					while (cfail != null) {
						if (cfail.nexts[i] != null) {
							cur.nexts[i].fail = cfail.nexts[i];
							break;
						}
						cfail = cfail.fail;
					}
					queue.add(cur.nexts[i]);
				}
			}
		}
		// 大文章：content
		public List<String> containWords(String content) {
			char[] cs = content.toCharArray();
			Node cur = root, follow;
			List<String> ans = new ArrayList<>();
			for (int i = 0; i < cs.length; i++) {
				int idx = cs[i] - 'a'; // 路
				// 如果当前字符在这条路上没配出来，就随着fail方向走向下条路径
				while (cur.nexts[idx] == null && cur != root) cur = cur.fail;
				// 1) 现在来到的路径，是可以继续匹配的
				// 2) 现在来到的节点，就是前缀树的根节点
				cur = cur.nexts[idx] != null ? cur.nexts[idx] : root;
				follow = cur; // 用follow去看一下有没有匹配到敏感词，收集答案
				while (follow != root) {
					// 若这个答案收集过，就直接提前结束
					if (follow.endUse) break;
					// 不同的需求，在这一段之间修改
					if (follow.end != null) {
						ans.add(follow.end);
						follow.endUse = true;
					}
					// 不同的需求，在这一段之间修改
					follow = follow.fail;
				}
			}
			return ans;
		}
	}

	public static void main(String[] args) {
		ACAutomation ac = new ACAutomation();
		ac.insert("dhe");
		ac.insert("he");
		ac.insert("abcdheks");
		// 设置fail指针
		ac.build();

		List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
		for (String word : contains) {
			System.out.println(word);
		}
	}

}
