package com.yw.course.coding.class33;

import java.util.*;

/**
 * @author yangwei
 */
public class Problem_0269_AlienDictionary {

	public static String alienOrder(String[] words) {
		if (words == null || words.length == 0) return "";
		int n = words.length;
		// 记录每种字符入度
		Map<Character, Integer> inDegMap = new HashMap<>();
		for (int i = 0; i < n; i++) {
			for (char c : words[i].toCharArray()) inDegMap.put(c, 0);
		}
		Map<Character, Set<Character>> graph = new HashMap<>();
		for (int i = 0; i < n - 1; i++) {
			char[] cur = words[i].toCharArray();
			char[] next = words[i + 1].toCharArray();
			int len = Math.min(cur.length, next.length);
			int j = 0;
			for (; j < len; j++) {
				if (cur[j] == next[j]) continue;
				Set<Character> set = graph.compute(cur[j], (k, v) -> v == null ? new HashSet<>() : v);
				if (!set.contains(next[j])) {
					set.add(next[j]);
					inDegMap.compute(next[j], (k, v) -> v == null ? v : v + 1);
				}
				break;
			}
			if (j < cur.length && j == next.length) return "";
		}
		StringBuilder ans = new StringBuilder();
		Queue<Character> queue = new LinkedList<>();
		inDegMap.forEach((k, v) -> {
			if (v == 0) queue.offer(k);
		});
		while (!queue.isEmpty()) {
			Character cur = queue.poll();
			ans.append(cur);
			Set<Character> set = graph.get(cur);
			if (set == null) continue;
			for (Character next : set) {
				inDegMap.compute(next, (k, v) -> {
					v -= 1;
					if (v == 0) queue.offer(k);
					return v;
				});
			}
		}
		return ans.length() == inDegMap.size() ? ans.toString() : "";
	}

	public static void main(String[] args) {
		String[] words = {"abc", "bcd", "cd", "da", "dd"};

		System.out.println(alienOrder(words));
	}
}
