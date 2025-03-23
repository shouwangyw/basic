package com.yw.course.coding.class49;

import java.util.*;

/**
 * @author yangwei
 */
public class Problem_0527_WordAbbreviation {

	public static List<String> wordsAbbreviation(List<String> words) {
		int n = words.size();
		List<String> ans = new ArrayList<>();
		// key: 单词最简缩写，val: 最简缩写对应的单词在哪些位置
		Map<String, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			String abbr = makeAbbr(words.get(i), 1);
			ans.add(abbr);
			final int idx = i;
			map.compute(abbr, (k, v) -> {
				if (v == null) v = new ArrayList<>();
				v.add(idx);
				return v;
			});
		}
		// 通过增加前缀的方式，区分开最简缩写形式超过1个的
		int[] prefix = new int[n];
		for (int i = 0; i < n; i++) {
			String abbr = ans.get(i);
			List<Integer> idxes = map.get(abbr);
			if (idxes.size() <= 1) continue;
			map.remove(abbr);

			for (Integer idx : idxes) {
				String newAbbr = makeAbbr(words.get(idx), ++prefix[idx]);
				ans.set(idx, newAbbr);
				final int newIdx = idx;
				map.compute(newAbbr, (k, v) -> {
					if (v == null) v = new ArrayList<>();
					v.add(newIdx);
					return v;
				});
			}
			i--;
		}
		return ans;
	}
	// k: 前缀长度
	private static String makeAbbr(String s, int k) {
		if (k >= s.length() - 2) return s;
		return s.substring(0, k) + (s.length() - 1 - k) + s.charAt(s.length() - 1);
	}

	public static void main(String[] args) {
		String[] words = {"like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"};
		System.out.println(wordsAbbreviation(Arrays.asList(words)));

		words = new String[]{"aa","aaa"};
		System.out.println(wordsAbbreviation(Arrays.asList(words)));
	}
}
