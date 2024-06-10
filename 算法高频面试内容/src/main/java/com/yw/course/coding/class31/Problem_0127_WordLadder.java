package com.yw.course.coding.class31;

import java.util.*;

/**
 * @author yangwei
 */
public class Problem_0127_WordLadder {

	// start，出发的单词
	// to, 目标单位
	// list, 列表
	// to 一定属于list
	// start未必
	// 返回变幻的最短路径长度
	public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		wordList.add(beginWord);
		Map<String, List<String>> nexts = getNexts(wordList);
		Map<String, Integer> distanceMap = new HashMap<>();
		distanceMap.put(beginWord, 1);
		Set<String> visited = new HashSet<>();
		visited.add(beginWord);
		Queue<String> q = new LinkedList<>();
		q.offer(beginWord);
		while (!q.isEmpty()) {
			String cur = q.poll();
			Integer distance = distanceMap.get(cur);
			for (String next : nexts.get(cur)) {
				if (next.equals(endWord)) return distance + 1;
				if (visited.contains(next)) continue;
				visited.add(next);
				q.offer(next);
				distanceMap.put(next, distance + 1);
			}
		}
		return 0;
	}
	private static Map<String, List<String>> getNexts(List<String> words) {
		Set<String> dict = new HashSet<>(words);
		Map<String, List<String>> nexts = new HashMap<>();
		for (String word : words) {
			nexts.put(word, getNext(word, dict));
		}
		return nexts;
	}
	// 如果字符串长度比较短，字符串数量比较多，以下方法适合
	private static List<String> getNext(String word, Set<String> dict) {
		List<String> res = new ArrayList<>();
		char[] cs = word.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			for (char c = 'a'; c <= 'z'; c++) {
				if (cs[i] == c) continue;
				char tmp = cs[i];
				cs[i] = c;
				if (dict.contains(String.valueOf(cs))) {
					res.add(String.valueOf(cs));
				}
				cs[i] = tmp;
			}
		}
		return res;
	}

	public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
		Set<String> dict = new HashSet<>(wordList);
		if (!dict.contains(endWord)) return 0;
		Set<String> startSet = new HashSet<>(), endSet = new HashSet<>(), visited = new HashSet<>();
		startSet.add(beginWord);
		endSet.add(endWord);
		for (int len = 2; !startSet.isEmpty(); len++) {
			// startSet是较小的，endSet是较大的
			Set<String> nextSet = new HashSet<>();
			for (String w : startSet) {
				for (int i = 0; i < w.length(); i++) {
					char[] cs = w.toCharArray();
					for (char c = 'a'; c <= 'z'; c++) {
						cs[i] = c;
						String next = String.valueOf(cs);
						if (endSet.contains(next)) return len;
						if (dict.contains(next) && !visited.contains(next)) {
							nextSet.add(next);
							visited.add(next);
						}
					}
				}
			}
			startSet = nextSet.size() < endSet.size() ? nextSet : endSet;
			endSet = startSet == nextSet ? endSet : nextSet;
		}
		return 0;
	}

}
